package com.fabienit.aeroclubpassion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fabienit.aeroclubpassion.IntegrationTest;
import com.fabienit.aeroclubpassion.domain.Tarif;
import com.fabienit.aeroclubpassion.repository.TarifRepository;
import com.fabienit.aeroclubpassion.service.criteria.TarifCriteria;
import com.fabienit.aeroclubpassion.service.dto.TarifDTO;
import com.fabienit.aeroclubpassion.service.mapper.TarifMapper;
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
 * Integration tests for the {@link TarifResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TarifResourceIT {

    private static final Double DEFAULT_TAXE_ATTERRISSAGE = 1D;
    private static final Double UPDATED_TAXE_ATTERRISSAGE = 2D;
    private static final Double SMALLER_TAXE_ATTERRISSAGE = 1D - 1D;

    private static final Double DEFAULT_TAXE_PARKING = 1D;
    private static final Double UPDATED_TAXE_PARKING = 2D;
    private static final Double SMALLER_TAXE_PARKING = 1D - 1D;

    private static final Double DEFAULT_CARBURANT = 1D;
    private static final Double UPDATED_CARBURANT = 2D;
    private static final Double SMALLER_CARBURANT = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/tarifs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TarifRepository tarifRepository;

    @Autowired
    private TarifMapper tarifMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarifMockMvc;

    private Tarif tarif;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarif createEntity(EntityManager em) {
        Tarif tarif = new Tarif()
            .taxeAtterrissage(DEFAULT_TAXE_ATTERRISSAGE)
            .taxeParking(DEFAULT_TAXE_PARKING)
            .carburant(DEFAULT_CARBURANT);
        return tarif;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarif createUpdatedEntity(EntityManager em) {
        Tarif tarif = new Tarif()
            .taxeAtterrissage(UPDATED_TAXE_ATTERRISSAGE)
            .taxeParking(UPDATED_TAXE_PARKING)
            .carburant(UPDATED_CARBURANT);
        return tarif;
    }

    @BeforeEach
    public void initTest() {
        tarif = createEntity(em);
    }

    @Test
    @Transactional
    void createTarif() throws Exception {
        int databaseSizeBeforeCreate = tarifRepository.findAll().size();
        // Create the Tarif
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);
        restTarifMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isCreated());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeCreate + 1);
        Tarif testTarif = tarifList.get(tarifList.size() - 1);
        assertThat(testTarif.getTaxeAtterrissage()).isEqualTo(DEFAULT_TAXE_ATTERRISSAGE);
        assertThat(testTarif.getTaxeParking()).isEqualTo(DEFAULT_TAXE_PARKING);
        assertThat(testTarif.getCarburant()).isEqualTo(DEFAULT_CARBURANT);
    }

    @Test
    @Transactional
    void createTarifWithExistingId() throws Exception {
        // Create the Tarif with an existing ID
        tarif.setId(1L);
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        int databaseSizeBeforeCreate = tarifRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTarifs() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList
        restTarifMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarif.getId().intValue())))
            .andExpect(jsonPath("$.[*].taxeAtterrissage").value(hasItem(DEFAULT_TAXE_ATTERRISSAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].taxeParking").value(hasItem(DEFAULT_TAXE_PARKING.doubleValue())))
            .andExpect(jsonPath("$.[*].carburant").value(hasItem(DEFAULT_CARBURANT.doubleValue())));
    }

    @Test
    @Transactional
    void getTarif() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get the tarif
        restTarifMockMvc
            .perform(get(ENTITY_API_URL_ID, tarif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarif.getId().intValue()))
            .andExpect(jsonPath("$.taxeAtterrissage").value(DEFAULT_TAXE_ATTERRISSAGE.doubleValue()))
            .andExpect(jsonPath("$.taxeParking").value(DEFAULT_TAXE_PARKING.doubleValue()))
            .andExpect(jsonPath("$.carburant").value(DEFAULT_CARBURANT.doubleValue()));
    }

    @Test
    @Transactional
    void getTarifsByIdFiltering() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        Long id = tarif.getId();

        defaultTarifShouldBeFound("id.equals=" + id);
        defaultTarifShouldNotBeFound("id.notEquals=" + id);

        defaultTarifShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTarifShouldNotBeFound("id.greaterThan=" + id);

        defaultTarifShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTarifShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTarifsByTaxeAtterrissageIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where taxeAtterrissage equals to DEFAULT_TAXE_ATTERRISSAGE
        defaultTarifShouldBeFound("taxeAtterrissage.equals=" + DEFAULT_TAXE_ATTERRISSAGE);

        // Get all the tarifList where taxeAtterrissage equals to UPDATED_TAXE_ATTERRISSAGE
        defaultTarifShouldNotBeFound("taxeAtterrissage.equals=" + UPDATED_TAXE_ATTERRISSAGE);
    }

    @Test
    @Transactional
    void getAllTarifsByTaxeAtterrissageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where taxeAtterrissage not equals to DEFAULT_TAXE_ATTERRISSAGE
        defaultTarifShouldNotBeFound("taxeAtterrissage.notEquals=" + DEFAULT_TAXE_ATTERRISSAGE);

        // Get all the tarifList where taxeAtterrissage not equals to UPDATED_TAXE_ATTERRISSAGE
        defaultTarifShouldBeFound("taxeAtterrissage.notEquals=" + UPDATED_TAXE_ATTERRISSAGE);
    }

    @Test
    @Transactional
    void getAllTarifsByTaxeAtterrissageIsInShouldWork() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where taxeAtterrissage in DEFAULT_TAXE_ATTERRISSAGE or UPDATED_TAXE_ATTERRISSAGE
        defaultTarifShouldBeFound("taxeAtterrissage.in=" + DEFAULT_TAXE_ATTERRISSAGE + "," + UPDATED_TAXE_ATTERRISSAGE);

        // Get all the tarifList where taxeAtterrissage equals to UPDATED_TAXE_ATTERRISSAGE
        defaultTarifShouldNotBeFound("taxeAtterrissage.in=" + UPDATED_TAXE_ATTERRISSAGE);
    }

    @Test
    @Transactional
    void getAllTarifsByTaxeAtterrissageIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where taxeAtterrissage is not null
        defaultTarifShouldBeFound("taxeAtterrissage.specified=true");

        // Get all the tarifList where taxeAtterrissage is null
        defaultTarifShouldNotBeFound("taxeAtterrissage.specified=false");
    }

    @Test
    @Transactional
    void getAllTarifsByTaxeAtterrissageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where taxeAtterrissage is greater than or equal to DEFAULT_TAXE_ATTERRISSAGE
        defaultTarifShouldBeFound("taxeAtterrissage.greaterThanOrEqual=" + DEFAULT_TAXE_ATTERRISSAGE);

        // Get all the tarifList where taxeAtterrissage is greater than or equal to UPDATED_TAXE_ATTERRISSAGE
        defaultTarifShouldNotBeFound("taxeAtterrissage.greaterThanOrEqual=" + UPDATED_TAXE_ATTERRISSAGE);
    }

    @Test
    @Transactional
    void getAllTarifsByTaxeAtterrissageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where taxeAtterrissage is less than or equal to DEFAULT_TAXE_ATTERRISSAGE
        defaultTarifShouldBeFound("taxeAtterrissage.lessThanOrEqual=" + DEFAULT_TAXE_ATTERRISSAGE);

        // Get all the tarifList where taxeAtterrissage is less than or equal to SMALLER_TAXE_ATTERRISSAGE
        defaultTarifShouldNotBeFound("taxeAtterrissage.lessThanOrEqual=" + SMALLER_TAXE_ATTERRISSAGE);
    }

    @Test
    @Transactional
    void getAllTarifsByTaxeAtterrissageIsLessThanSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where taxeAtterrissage is less than DEFAULT_TAXE_ATTERRISSAGE
        defaultTarifShouldNotBeFound("taxeAtterrissage.lessThan=" + DEFAULT_TAXE_ATTERRISSAGE);

        // Get all the tarifList where taxeAtterrissage is less than UPDATED_TAXE_ATTERRISSAGE
        defaultTarifShouldBeFound("taxeAtterrissage.lessThan=" + UPDATED_TAXE_ATTERRISSAGE);
    }

    @Test
    @Transactional
    void getAllTarifsByTaxeAtterrissageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where taxeAtterrissage is greater than DEFAULT_TAXE_ATTERRISSAGE
        defaultTarifShouldNotBeFound("taxeAtterrissage.greaterThan=" + DEFAULT_TAXE_ATTERRISSAGE);

        // Get all the tarifList where taxeAtterrissage is greater than SMALLER_TAXE_ATTERRISSAGE
        defaultTarifShouldBeFound("taxeAtterrissage.greaterThan=" + SMALLER_TAXE_ATTERRISSAGE);
    }

    @Test
    @Transactional
    void getAllTarifsByTaxeParkingIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where taxeParking equals to DEFAULT_TAXE_PARKING
        defaultTarifShouldBeFound("taxeParking.equals=" + DEFAULT_TAXE_PARKING);

        // Get all the tarifList where taxeParking equals to UPDATED_TAXE_PARKING
        defaultTarifShouldNotBeFound("taxeParking.equals=" + UPDATED_TAXE_PARKING);
    }

    @Test
    @Transactional
    void getAllTarifsByTaxeParkingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where taxeParking not equals to DEFAULT_TAXE_PARKING
        defaultTarifShouldNotBeFound("taxeParking.notEquals=" + DEFAULT_TAXE_PARKING);

        // Get all the tarifList where taxeParking not equals to UPDATED_TAXE_PARKING
        defaultTarifShouldBeFound("taxeParking.notEquals=" + UPDATED_TAXE_PARKING);
    }

    @Test
    @Transactional
    void getAllTarifsByTaxeParkingIsInShouldWork() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where taxeParking in DEFAULT_TAXE_PARKING or UPDATED_TAXE_PARKING
        defaultTarifShouldBeFound("taxeParking.in=" + DEFAULT_TAXE_PARKING + "," + UPDATED_TAXE_PARKING);

        // Get all the tarifList where taxeParking equals to UPDATED_TAXE_PARKING
        defaultTarifShouldNotBeFound("taxeParking.in=" + UPDATED_TAXE_PARKING);
    }

    @Test
    @Transactional
    void getAllTarifsByTaxeParkingIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where taxeParking is not null
        defaultTarifShouldBeFound("taxeParking.specified=true");

        // Get all the tarifList where taxeParking is null
        defaultTarifShouldNotBeFound("taxeParking.specified=false");
    }

    @Test
    @Transactional
    void getAllTarifsByTaxeParkingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where taxeParking is greater than or equal to DEFAULT_TAXE_PARKING
        defaultTarifShouldBeFound("taxeParking.greaterThanOrEqual=" + DEFAULT_TAXE_PARKING);

        // Get all the tarifList where taxeParking is greater than or equal to UPDATED_TAXE_PARKING
        defaultTarifShouldNotBeFound("taxeParking.greaterThanOrEqual=" + UPDATED_TAXE_PARKING);
    }

    @Test
    @Transactional
    void getAllTarifsByTaxeParkingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where taxeParking is less than or equal to DEFAULT_TAXE_PARKING
        defaultTarifShouldBeFound("taxeParking.lessThanOrEqual=" + DEFAULT_TAXE_PARKING);

        // Get all the tarifList where taxeParking is less than or equal to SMALLER_TAXE_PARKING
        defaultTarifShouldNotBeFound("taxeParking.lessThanOrEqual=" + SMALLER_TAXE_PARKING);
    }

    @Test
    @Transactional
    void getAllTarifsByTaxeParkingIsLessThanSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where taxeParking is less than DEFAULT_TAXE_PARKING
        defaultTarifShouldNotBeFound("taxeParking.lessThan=" + DEFAULT_TAXE_PARKING);

        // Get all the tarifList where taxeParking is less than UPDATED_TAXE_PARKING
        defaultTarifShouldBeFound("taxeParking.lessThan=" + UPDATED_TAXE_PARKING);
    }

    @Test
    @Transactional
    void getAllTarifsByTaxeParkingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where taxeParking is greater than DEFAULT_TAXE_PARKING
        defaultTarifShouldNotBeFound("taxeParking.greaterThan=" + DEFAULT_TAXE_PARKING);

        // Get all the tarifList where taxeParking is greater than SMALLER_TAXE_PARKING
        defaultTarifShouldBeFound("taxeParking.greaterThan=" + SMALLER_TAXE_PARKING);
    }

    @Test
    @Transactional
    void getAllTarifsByCarburantIsEqualToSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where carburant equals to DEFAULT_CARBURANT
        defaultTarifShouldBeFound("carburant.equals=" + DEFAULT_CARBURANT);

        // Get all the tarifList where carburant equals to UPDATED_CARBURANT
        defaultTarifShouldNotBeFound("carburant.equals=" + UPDATED_CARBURANT);
    }

    @Test
    @Transactional
    void getAllTarifsByCarburantIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where carburant not equals to DEFAULT_CARBURANT
        defaultTarifShouldNotBeFound("carburant.notEquals=" + DEFAULT_CARBURANT);

        // Get all the tarifList where carburant not equals to UPDATED_CARBURANT
        defaultTarifShouldBeFound("carburant.notEquals=" + UPDATED_CARBURANT);
    }

    @Test
    @Transactional
    void getAllTarifsByCarburantIsInShouldWork() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where carburant in DEFAULT_CARBURANT or UPDATED_CARBURANT
        defaultTarifShouldBeFound("carburant.in=" + DEFAULT_CARBURANT + "," + UPDATED_CARBURANT);

        // Get all the tarifList where carburant equals to UPDATED_CARBURANT
        defaultTarifShouldNotBeFound("carburant.in=" + UPDATED_CARBURANT);
    }

    @Test
    @Transactional
    void getAllTarifsByCarburantIsNullOrNotNull() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where carburant is not null
        defaultTarifShouldBeFound("carburant.specified=true");

        // Get all the tarifList where carburant is null
        defaultTarifShouldNotBeFound("carburant.specified=false");
    }

    @Test
    @Transactional
    void getAllTarifsByCarburantIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where carburant is greater than or equal to DEFAULT_CARBURANT
        defaultTarifShouldBeFound("carburant.greaterThanOrEqual=" + DEFAULT_CARBURANT);

        // Get all the tarifList where carburant is greater than or equal to UPDATED_CARBURANT
        defaultTarifShouldNotBeFound("carburant.greaterThanOrEqual=" + UPDATED_CARBURANT);
    }

    @Test
    @Transactional
    void getAllTarifsByCarburantIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where carburant is less than or equal to DEFAULT_CARBURANT
        defaultTarifShouldBeFound("carburant.lessThanOrEqual=" + DEFAULT_CARBURANT);

        // Get all the tarifList where carburant is less than or equal to SMALLER_CARBURANT
        defaultTarifShouldNotBeFound("carburant.lessThanOrEqual=" + SMALLER_CARBURANT);
    }

    @Test
    @Transactional
    void getAllTarifsByCarburantIsLessThanSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where carburant is less than DEFAULT_CARBURANT
        defaultTarifShouldNotBeFound("carburant.lessThan=" + DEFAULT_CARBURANT);

        // Get all the tarifList where carburant is less than UPDATED_CARBURANT
        defaultTarifShouldBeFound("carburant.lessThan=" + UPDATED_CARBURANT);
    }

    @Test
    @Transactional
    void getAllTarifsByCarburantIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList where carburant is greater than DEFAULT_CARBURANT
        defaultTarifShouldNotBeFound("carburant.greaterThan=" + DEFAULT_CARBURANT);

        // Get all the tarifList where carburant is greater than SMALLER_CARBURANT
        defaultTarifShouldBeFound("carburant.greaterThan=" + SMALLER_CARBURANT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTarifShouldBeFound(String filter) throws Exception {
        restTarifMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarif.getId().intValue())))
            .andExpect(jsonPath("$.[*].taxeAtterrissage").value(hasItem(DEFAULT_TAXE_ATTERRISSAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].taxeParking").value(hasItem(DEFAULT_TAXE_PARKING.doubleValue())))
            .andExpect(jsonPath("$.[*].carburant").value(hasItem(DEFAULT_CARBURANT.doubleValue())));

        // Check, that the count call also returns 1
        restTarifMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTarifShouldNotBeFound(String filter) throws Exception {
        restTarifMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTarifMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTarif() throws Exception {
        // Get the tarif
        restTarifMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTarif() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();

        // Update the tarif
        Tarif updatedTarif = tarifRepository.findById(tarif.getId()).get();
        // Disconnect from session so that the updates on updatedTarif are not directly saved in db
        em.detach(updatedTarif);
        updatedTarif.taxeAtterrissage(UPDATED_TAXE_ATTERRISSAGE).taxeParking(UPDATED_TAXE_PARKING).carburant(UPDATED_CARBURANT);
        TarifDTO tarifDTO = tarifMapper.toDto(updatedTarif);

        restTarifMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tarifDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tarifDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate);
        Tarif testTarif = tarifList.get(tarifList.size() - 1);
        assertThat(testTarif.getTaxeAtterrissage()).isEqualTo(UPDATED_TAXE_ATTERRISSAGE);
        assertThat(testTarif.getTaxeParking()).isEqualTo(UPDATED_TAXE_PARKING);
        assertThat(testTarif.getCarburant()).isEqualTo(UPDATED_CARBURANT);
    }

    @Test
    @Transactional
    void putNonExistingTarif() throws Exception {
        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();
        tarif.setId(count.incrementAndGet());

        // Create the Tarif
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarifMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tarifDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tarifDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTarif() throws Exception {
        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();
        tarif.setId(count.incrementAndGet());

        // Create the Tarif
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarifMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tarifDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTarif() throws Exception {
        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();
        tarif.setId(count.incrementAndGet());

        // Create the Tarif
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarifMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTarifWithPatch() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();

        // Update the tarif using partial update
        Tarif partialUpdatedTarif = new Tarif();
        partialUpdatedTarif.setId(tarif.getId());

        partialUpdatedTarif.taxeAtterrissage(UPDATED_TAXE_ATTERRISSAGE).taxeParking(UPDATED_TAXE_PARKING).carburant(UPDATED_CARBURANT);

        restTarifMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarif.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTarif))
            )
            .andExpect(status().isOk());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate);
        Tarif testTarif = tarifList.get(tarifList.size() - 1);
        assertThat(testTarif.getTaxeAtterrissage()).isEqualTo(UPDATED_TAXE_ATTERRISSAGE);
        assertThat(testTarif.getTaxeParking()).isEqualTo(UPDATED_TAXE_PARKING);
        assertThat(testTarif.getCarburant()).isEqualTo(UPDATED_CARBURANT);
    }

    @Test
    @Transactional
    void fullUpdateTarifWithPatch() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();

        // Update the tarif using partial update
        Tarif partialUpdatedTarif = new Tarif();
        partialUpdatedTarif.setId(tarif.getId());

        partialUpdatedTarif.taxeAtterrissage(UPDATED_TAXE_ATTERRISSAGE).taxeParking(UPDATED_TAXE_PARKING).carburant(UPDATED_CARBURANT);

        restTarifMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarif.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTarif))
            )
            .andExpect(status().isOk());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate);
        Tarif testTarif = tarifList.get(tarifList.size() - 1);
        assertThat(testTarif.getTaxeAtterrissage()).isEqualTo(UPDATED_TAXE_ATTERRISSAGE);
        assertThat(testTarif.getTaxeParking()).isEqualTo(UPDATED_TAXE_PARKING);
        assertThat(testTarif.getCarburant()).isEqualTo(UPDATED_CARBURANT);
    }

    @Test
    @Transactional
    void patchNonExistingTarif() throws Exception {
        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();
        tarif.setId(count.incrementAndGet());

        // Create the Tarif
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarifMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tarifDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tarifDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTarif() throws Exception {
        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();
        tarif.setId(count.incrementAndGet());

        // Create the Tarif
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarifMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tarifDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTarif() throws Exception {
        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();
        tarif.setId(count.incrementAndGet());

        // Create the Tarif
        TarifDTO tarifDTO = tarifMapper.toDto(tarif);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarifMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tarifDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTarif() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        int databaseSizeBeforeDelete = tarifRepository.findAll().size();

        // Delete the tarif
        restTarifMockMvc
            .perform(delete(ENTITY_API_URL_ID, tarif.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
