package com.fabienit.aeroclubpassion.web.rest;

import static com.fabienit.aeroclubpassion.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fabienit.aeroclubpassion.IntegrationTest;
import com.fabienit.aeroclubpassion.domain.Avion;
import com.fabienit.aeroclubpassion.domain.Reservation;
import com.fabienit.aeroclubpassion.domain.UserRegistered;
import com.fabienit.aeroclubpassion.repository.ReservationRepository;
import com.fabienit.aeroclubpassion.service.criteria.ReservationCriteria;
import com.fabienit.aeroclubpassion.service.dto.ReservationDTO;
import com.fabienit.aeroclubpassion.service.mapper.ReservationMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ReservationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReservationResourceIT {

    private static final ZonedDateTime DEFAULT_DATE_EMPRUNT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_EMPRUNT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_EMPRUNT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATE_RETOUR = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_RETOUR = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_RETOUR = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/reservations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReservationMockMvc;

    private Reservation reservation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reservation createEntity(EntityManager em) {
        Reservation reservation = new Reservation().dateEmprunt(DEFAULT_DATE_EMPRUNT).dateRetour(DEFAULT_DATE_RETOUR);
        return reservation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reservation createUpdatedEntity(EntityManager em) {
        Reservation reservation = new Reservation().dateEmprunt(UPDATED_DATE_EMPRUNT).dateRetour(UPDATED_DATE_RETOUR);
        return reservation;
    }

    @BeforeEach
    public void initTest() {
        reservation = createEntity(em);
    }

    @Test
    @Transactional
    void createReservation() throws Exception {
        int databaseSizeBeforeCreate = reservationRepository.findAll().size();
        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);
        restReservationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeCreate + 1);
        Reservation testReservation = reservationList.get(reservationList.size() - 1);
        assertThat(testReservation.getDateEmprunt()).isEqualTo(DEFAULT_DATE_EMPRUNT);
        assertThat(testReservation.getDateRetour()).isEqualTo(DEFAULT_DATE_RETOUR);
    }

    @Test
    @Transactional
    void createReservationWithExistingId() throws Exception {
        // Create the Reservation with an existing ID
        reservation.setId(1L);
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        int databaseSizeBeforeCreate = reservationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReservations() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList
        restReservationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservation.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateEmprunt").value(hasItem(sameInstant(DEFAULT_DATE_EMPRUNT))))
            .andExpect(jsonPath("$.[*].dateRetour").value(hasItem(sameInstant(DEFAULT_DATE_RETOUR))));
    }

    @Test
    @Transactional
    void getReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get the reservation
        restReservationMockMvc
            .perform(get(ENTITY_API_URL_ID, reservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reservation.getId().intValue()))
            .andExpect(jsonPath("$.dateEmprunt").value(sameInstant(DEFAULT_DATE_EMPRUNT)))
            .andExpect(jsonPath("$.dateRetour").value(sameInstant(DEFAULT_DATE_RETOUR)));
    }

    @Test
    @Transactional
    void getReservationsByIdFiltering() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        Long id = reservation.getId();

        defaultReservationShouldBeFound("id.equals=" + id);
        defaultReservationShouldNotBeFound("id.notEquals=" + id);

        defaultReservationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReservationShouldNotBeFound("id.greaterThan=" + id);

        defaultReservationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReservationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllReservationsByDateEmpruntIsEqualToSomething() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList where dateEmprunt equals to DEFAULT_DATE_EMPRUNT
        defaultReservationShouldBeFound("dateEmprunt.equals=" + DEFAULT_DATE_EMPRUNT);

        // Get all the reservationList where dateEmprunt equals to UPDATED_DATE_EMPRUNT
        defaultReservationShouldNotBeFound("dateEmprunt.equals=" + UPDATED_DATE_EMPRUNT);
    }

    @Test
    @Transactional
    void getAllReservationsByDateEmpruntIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList where dateEmprunt not equals to DEFAULT_DATE_EMPRUNT
        defaultReservationShouldNotBeFound("dateEmprunt.notEquals=" + DEFAULT_DATE_EMPRUNT);

        // Get all the reservationList where dateEmprunt not equals to UPDATED_DATE_EMPRUNT
        defaultReservationShouldBeFound("dateEmprunt.notEquals=" + UPDATED_DATE_EMPRUNT);
    }

    @Test
    @Transactional
    void getAllReservationsByDateEmpruntIsInShouldWork() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList where dateEmprunt in DEFAULT_DATE_EMPRUNT or UPDATED_DATE_EMPRUNT
        defaultReservationShouldBeFound("dateEmprunt.in=" + DEFAULT_DATE_EMPRUNT + "," + UPDATED_DATE_EMPRUNT);

        // Get all the reservationList where dateEmprunt equals to UPDATED_DATE_EMPRUNT
        defaultReservationShouldNotBeFound("dateEmprunt.in=" + UPDATED_DATE_EMPRUNT);
    }

    @Test
    @Transactional
    void getAllReservationsByDateEmpruntIsNullOrNotNull() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList where dateEmprunt is not null
        defaultReservationShouldBeFound("dateEmprunt.specified=true");

        // Get all the reservationList where dateEmprunt is null
        defaultReservationShouldNotBeFound("dateEmprunt.specified=false");
    }

    @Test
    @Transactional
    void getAllReservationsByDateEmpruntIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList where dateEmprunt is greater than or equal to DEFAULT_DATE_EMPRUNT
        defaultReservationShouldBeFound("dateEmprunt.greaterThanOrEqual=" + DEFAULT_DATE_EMPRUNT);

        // Get all the reservationList where dateEmprunt is greater than or equal to UPDATED_DATE_EMPRUNT
        defaultReservationShouldNotBeFound("dateEmprunt.greaterThanOrEqual=" + UPDATED_DATE_EMPRUNT);
    }

    @Test
    @Transactional
    void getAllReservationsByDateEmpruntIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList where dateEmprunt is less than or equal to DEFAULT_DATE_EMPRUNT
        defaultReservationShouldBeFound("dateEmprunt.lessThanOrEqual=" + DEFAULT_DATE_EMPRUNT);

        // Get all the reservationList where dateEmprunt is less than or equal to SMALLER_DATE_EMPRUNT
        defaultReservationShouldNotBeFound("dateEmprunt.lessThanOrEqual=" + SMALLER_DATE_EMPRUNT);
    }

    @Test
    @Transactional
    void getAllReservationsByDateEmpruntIsLessThanSomething() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList where dateEmprunt is less than DEFAULT_DATE_EMPRUNT
        defaultReservationShouldNotBeFound("dateEmprunt.lessThan=" + DEFAULT_DATE_EMPRUNT);

        // Get all the reservationList where dateEmprunt is less than UPDATED_DATE_EMPRUNT
        defaultReservationShouldBeFound("dateEmprunt.lessThan=" + UPDATED_DATE_EMPRUNT);
    }

    @Test
    @Transactional
    void getAllReservationsByDateEmpruntIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList where dateEmprunt is greater than DEFAULT_DATE_EMPRUNT
        defaultReservationShouldNotBeFound("dateEmprunt.greaterThan=" + DEFAULT_DATE_EMPRUNT);

        // Get all the reservationList where dateEmprunt is greater than SMALLER_DATE_EMPRUNT
        defaultReservationShouldBeFound("dateEmprunt.greaterThan=" + SMALLER_DATE_EMPRUNT);
    }

    @Test
    @Transactional
    void getAllReservationsByDateRetourIsEqualToSomething() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList where dateRetour equals to DEFAULT_DATE_RETOUR
        defaultReservationShouldBeFound("dateRetour.equals=" + DEFAULT_DATE_RETOUR);

        // Get all the reservationList where dateRetour equals to UPDATED_DATE_RETOUR
        defaultReservationShouldNotBeFound("dateRetour.equals=" + UPDATED_DATE_RETOUR);
    }

    @Test
    @Transactional
    void getAllReservationsByDateRetourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList where dateRetour not equals to DEFAULT_DATE_RETOUR
        defaultReservationShouldNotBeFound("dateRetour.notEquals=" + DEFAULT_DATE_RETOUR);

        // Get all the reservationList where dateRetour not equals to UPDATED_DATE_RETOUR
        defaultReservationShouldBeFound("dateRetour.notEquals=" + UPDATED_DATE_RETOUR);
    }

    @Test
    @Transactional
    void getAllReservationsByDateRetourIsInShouldWork() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList where dateRetour in DEFAULT_DATE_RETOUR or UPDATED_DATE_RETOUR
        defaultReservationShouldBeFound("dateRetour.in=" + DEFAULT_DATE_RETOUR + "," + UPDATED_DATE_RETOUR);

        // Get all the reservationList where dateRetour equals to UPDATED_DATE_RETOUR
        defaultReservationShouldNotBeFound("dateRetour.in=" + UPDATED_DATE_RETOUR);
    }

    @Test
    @Transactional
    void getAllReservationsByDateRetourIsNullOrNotNull() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList where dateRetour is not null
        defaultReservationShouldBeFound("dateRetour.specified=true");

        // Get all the reservationList where dateRetour is null
        defaultReservationShouldNotBeFound("dateRetour.specified=false");
    }

    @Test
    @Transactional
    void getAllReservationsByDateRetourIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList where dateRetour is greater than or equal to DEFAULT_DATE_RETOUR
        defaultReservationShouldBeFound("dateRetour.greaterThanOrEqual=" + DEFAULT_DATE_RETOUR);

        // Get all the reservationList where dateRetour is greater than or equal to UPDATED_DATE_RETOUR
        defaultReservationShouldNotBeFound("dateRetour.greaterThanOrEqual=" + UPDATED_DATE_RETOUR);
    }

    @Test
    @Transactional
    void getAllReservationsByDateRetourIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList where dateRetour is less than or equal to DEFAULT_DATE_RETOUR
        defaultReservationShouldBeFound("dateRetour.lessThanOrEqual=" + DEFAULT_DATE_RETOUR);

        // Get all the reservationList where dateRetour is less than or equal to SMALLER_DATE_RETOUR
        defaultReservationShouldNotBeFound("dateRetour.lessThanOrEqual=" + SMALLER_DATE_RETOUR);
    }

    @Test
    @Transactional
    void getAllReservationsByDateRetourIsLessThanSomething() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList where dateRetour is less than DEFAULT_DATE_RETOUR
        defaultReservationShouldNotBeFound("dateRetour.lessThan=" + DEFAULT_DATE_RETOUR);

        // Get all the reservationList where dateRetour is less than UPDATED_DATE_RETOUR
        defaultReservationShouldBeFound("dateRetour.lessThan=" + UPDATED_DATE_RETOUR);
    }

    @Test
    @Transactional
    void getAllReservationsByDateRetourIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList where dateRetour is greater than DEFAULT_DATE_RETOUR
        defaultReservationShouldNotBeFound("dateRetour.greaterThan=" + DEFAULT_DATE_RETOUR);

        // Get all the reservationList where dateRetour is greater than SMALLER_DATE_RETOUR
        defaultReservationShouldBeFound("dateRetour.greaterThan=" + SMALLER_DATE_RETOUR);
    }

    @Test
    @Transactional
    void getAllReservationsByAvionsIsEqualToSomething() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);
        Avion avions;
        if (TestUtil.findAll(em, Avion.class).isEmpty()) {
            avions = AvionResourceIT.createEntity(em);
            em.persist(avions);
            em.flush();
        } else {
            avions = TestUtil.findAll(em, Avion.class).get(0);
        }
        em.persist(avions);
        em.flush();
        reservation.setAvions(avions);
        reservationRepository.saveAndFlush(reservation);
        Long avionsId = avions.getId();

        // Get all the reservationList where avions equals to avionsId
        defaultReservationShouldBeFound("avionsId.equals=" + avionsId);

        // Get all the reservationList where avions equals to (avionsId + 1)
        defaultReservationShouldNotBeFound("avionsId.equals=" + (avionsId + 1));
    }

    @Test
    @Transactional
    void getAllReservationsByUserRegisteredsIsEqualToSomething() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);
        UserRegistered userRegistereds;
        if (TestUtil.findAll(em, UserRegistered.class).isEmpty()) {
            userRegistereds = UserRegisteredResourceIT.createEntity(em);
            em.persist(userRegistereds);
            em.flush();
        } else {
            userRegistereds = TestUtil.findAll(em, UserRegistered.class).get(0);
        }
        em.persist(userRegistereds);
        em.flush();
        reservation.setUserRegistereds(userRegistereds);
        reservationRepository.saveAndFlush(reservation);
        Long userRegisteredsId = userRegistereds.getId();

        // Get all the reservationList where userRegistereds equals to userRegisteredsId
        defaultReservationShouldBeFound("userRegisteredsId.equals=" + userRegisteredsId);

        // Get all the reservationList where userRegistereds equals to (userRegisteredsId + 1)
        defaultReservationShouldNotBeFound("userRegisteredsId.equals=" + (userRegisteredsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReservationShouldBeFound(String filter) throws Exception {
        restReservationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservation.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateEmprunt").value(hasItem(sameInstant(DEFAULT_DATE_EMPRUNT))))
            .andExpect(jsonPath("$.[*].dateRetour").value(hasItem(sameInstant(DEFAULT_DATE_RETOUR))));

        // Check, that the count call also returns 1
        restReservationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReservationShouldNotBeFound(String filter) throws Exception {
        restReservationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReservationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingReservation() throws Exception {
        // Get the reservation
        restReservationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Update the reservation
        Reservation updatedReservation = reservationRepository.findById(reservation.getId()).get();
        // Disconnect from session so that the updates on updatedReservation are not directly saved in db
        em.detach(updatedReservation);
        updatedReservation.dateEmprunt(UPDATED_DATE_EMPRUNT).dateRetour(UPDATED_DATE_RETOUR);
        ReservationDTO reservationDTO = reservationMapper.toDto(updatedReservation);

        restReservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
        Reservation testReservation = reservationList.get(reservationList.size() - 1);
        assertThat(testReservation.getDateEmprunt()).isEqualTo(UPDATED_DATE_EMPRUNT);
        assertThat(testReservation.getDateRetour()).isEqualTo(UPDATED_DATE_RETOUR);
    }

    @Test
    @Transactional
    void putNonExistingReservation() throws Exception {
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();
        reservation.setId(count.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReservation() throws Exception {
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();
        reservation.setId(count.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReservation() throws Exception {
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();
        reservation.setId(count.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reservationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReservationWithPatch() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Update the reservation using partial update
        Reservation partialUpdatedReservation = new Reservation();
        partialUpdatedReservation.setId(reservation.getId());

        restReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReservation))
            )
            .andExpect(status().isOk());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
        Reservation testReservation = reservationList.get(reservationList.size() - 1);
        assertThat(testReservation.getDateEmprunt()).isEqualTo(DEFAULT_DATE_EMPRUNT);
        assertThat(testReservation.getDateRetour()).isEqualTo(DEFAULT_DATE_RETOUR);
    }

    @Test
    @Transactional
    void fullUpdateReservationWithPatch() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Update the reservation using partial update
        Reservation partialUpdatedReservation = new Reservation();
        partialUpdatedReservation.setId(reservation.getId());

        partialUpdatedReservation.dateEmprunt(UPDATED_DATE_EMPRUNT).dateRetour(UPDATED_DATE_RETOUR);

        restReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReservation))
            )
            .andExpect(status().isOk());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
        Reservation testReservation = reservationList.get(reservationList.size() - 1);
        assertThat(testReservation.getDateEmprunt()).isEqualTo(UPDATED_DATE_EMPRUNT);
        assertThat(testReservation.getDateRetour()).isEqualTo(UPDATED_DATE_RETOUR);
    }

    @Test
    @Transactional
    void patchNonExistingReservation() throws Exception {
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();
        reservation.setId(count.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reservationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReservation() throws Exception {
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();
        reservation.setId(count.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReservation() throws Exception {
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();
        reservation.setId(count.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(reservationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        int databaseSizeBeforeDelete = reservationRepository.findAll().size();

        // Delete the reservation
        restReservationMockMvc
            .perform(delete(ENTITY_API_URL_ID, reservation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
