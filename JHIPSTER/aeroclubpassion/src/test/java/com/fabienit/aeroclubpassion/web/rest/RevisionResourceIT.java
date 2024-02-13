package com.fabienit.aeroclubpassion.web.rest;

import static com.fabienit.aeroclubpassion.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fabienit.aeroclubpassion.IntegrationTest;
import com.fabienit.aeroclubpassion.domain.Avion;
import com.fabienit.aeroclubpassion.domain.Revision;
import com.fabienit.aeroclubpassion.repository.RevisionRepository;
import com.fabienit.aeroclubpassion.service.criteria.RevisionCriteria;
import com.fabienit.aeroclubpassion.service.dto.RevisionDTO;
import com.fabienit.aeroclubpassion.service.mapper.RevisionMapper;
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
 * Integration tests for the {@link RevisionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RevisionResourceIT {

    private static final Boolean DEFAULT_NIVEAUX = false;
    private static final Boolean UPDATED_NIVEAUX = true;

    private static final Boolean DEFAULT_PRESSION = false;
    private static final Boolean UPDATED_PRESSION = true;

    private static final Boolean DEFAULT_CARROSERIE = false;
    private static final Boolean UPDATED_CARROSERIE = true;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/revisions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RevisionRepository revisionRepository;

    @Autowired
    private RevisionMapper revisionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRevisionMockMvc;

    private Revision revision;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Revision createEntity(EntityManager em) {
        Revision revision = new Revision()
            .niveaux(DEFAULT_NIVEAUX)
            .pression(DEFAULT_PRESSION)
            .carroserie(DEFAULT_CARROSERIE)
            .date(DEFAULT_DATE);
        return revision;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Revision createUpdatedEntity(EntityManager em) {
        Revision revision = new Revision()
            .niveaux(UPDATED_NIVEAUX)
            .pression(UPDATED_PRESSION)
            .carroserie(UPDATED_CARROSERIE)
            .date(UPDATED_DATE);
        return revision;
    }

    @BeforeEach
    public void initTest() {
        revision = createEntity(em);
    }

    @Test
    @Transactional
    void createRevision() throws Exception {
        int databaseSizeBeforeCreate = revisionRepository.findAll().size();
        // Create the Revision
        RevisionDTO revisionDTO = revisionMapper.toDto(revision);
        restRevisionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(revisionDTO)))
            .andExpect(status().isCreated());

        // Validate the Revision in the database
        List<Revision> revisionList = revisionRepository.findAll();
        assertThat(revisionList).hasSize(databaseSizeBeforeCreate + 1);
        Revision testRevision = revisionList.get(revisionList.size() - 1);
        assertThat(testRevision.getNiveaux()).isEqualTo(DEFAULT_NIVEAUX);
        assertThat(testRevision.getPression()).isEqualTo(DEFAULT_PRESSION);
        assertThat(testRevision.getCarroserie()).isEqualTo(DEFAULT_CARROSERIE);
        assertThat(testRevision.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createRevisionWithExistingId() throws Exception {
        // Create the Revision with an existing ID
        revision.setId(1L);
        RevisionDTO revisionDTO = revisionMapper.toDto(revision);

        int databaseSizeBeforeCreate = revisionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRevisionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(revisionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Revision in the database
        List<Revision> revisionList = revisionRepository.findAll();
        assertThat(revisionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRevisions() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList
        restRevisionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(revision.getId().intValue())))
            .andExpect(jsonPath("$.[*].niveaux").value(hasItem(DEFAULT_NIVEAUX.booleanValue())))
            .andExpect(jsonPath("$.[*].pression").value(hasItem(DEFAULT_PRESSION.booleanValue())))
            .andExpect(jsonPath("$.[*].carroserie").value(hasItem(DEFAULT_CARROSERIE.booleanValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    void getRevision() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get the revision
        restRevisionMockMvc
            .perform(get(ENTITY_API_URL_ID, revision.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(revision.getId().intValue()))
            .andExpect(jsonPath("$.niveaux").value(DEFAULT_NIVEAUX.booleanValue()))
            .andExpect(jsonPath("$.pression").value(DEFAULT_PRESSION.booleanValue()))
            .andExpect(jsonPath("$.carroserie").value(DEFAULT_CARROSERIE.booleanValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    void getRevisionsByIdFiltering() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        Long id = revision.getId();

        defaultRevisionShouldBeFound("id.equals=" + id);
        defaultRevisionShouldNotBeFound("id.notEquals=" + id);

        defaultRevisionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRevisionShouldNotBeFound("id.greaterThan=" + id);

        defaultRevisionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRevisionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRevisionsByNiveauxIsEqualToSomething() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where niveaux equals to DEFAULT_NIVEAUX
        defaultRevisionShouldBeFound("niveaux.equals=" + DEFAULT_NIVEAUX);

        // Get all the revisionList where niveaux equals to UPDATED_NIVEAUX
        defaultRevisionShouldNotBeFound("niveaux.equals=" + UPDATED_NIVEAUX);
    }

    @Test
    @Transactional
    void getAllRevisionsByNiveauxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where niveaux not equals to DEFAULT_NIVEAUX
        defaultRevisionShouldNotBeFound("niveaux.notEquals=" + DEFAULT_NIVEAUX);

        // Get all the revisionList where niveaux not equals to UPDATED_NIVEAUX
        defaultRevisionShouldBeFound("niveaux.notEquals=" + UPDATED_NIVEAUX);
    }

    @Test
    @Transactional
    void getAllRevisionsByNiveauxIsInShouldWork() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where niveaux in DEFAULT_NIVEAUX or UPDATED_NIVEAUX
        defaultRevisionShouldBeFound("niveaux.in=" + DEFAULT_NIVEAUX + "," + UPDATED_NIVEAUX);

        // Get all the revisionList where niveaux equals to UPDATED_NIVEAUX
        defaultRevisionShouldNotBeFound("niveaux.in=" + UPDATED_NIVEAUX);
    }

    @Test
    @Transactional
    void getAllRevisionsByNiveauxIsNullOrNotNull() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where niveaux is not null
        defaultRevisionShouldBeFound("niveaux.specified=true");

        // Get all the revisionList where niveaux is null
        defaultRevisionShouldNotBeFound("niveaux.specified=false");
    }

    @Test
    @Transactional
    void getAllRevisionsByPressionIsEqualToSomething() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where pression equals to DEFAULT_PRESSION
        defaultRevisionShouldBeFound("pression.equals=" + DEFAULT_PRESSION);

        // Get all the revisionList where pression equals to UPDATED_PRESSION
        defaultRevisionShouldNotBeFound("pression.equals=" + UPDATED_PRESSION);
    }

    @Test
    @Transactional
    void getAllRevisionsByPressionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where pression not equals to DEFAULT_PRESSION
        defaultRevisionShouldNotBeFound("pression.notEquals=" + DEFAULT_PRESSION);

        // Get all the revisionList where pression not equals to UPDATED_PRESSION
        defaultRevisionShouldBeFound("pression.notEquals=" + UPDATED_PRESSION);
    }

    @Test
    @Transactional
    void getAllRevisionsByPressionIsInShouldWork() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where pression in DEFAULT_PRESSION or UPDATED_PRESSION
        defaultRevisionShouldBeFound("pression.in=" + DEFAULT_PRESSION + "," + UPDATED_PRESSION);

        // Get all the revisionList where pression equals to UPDATED_PRESSION
        defaultRevisionShouldNotBeFound("pression.in=" + UPDATED_PRESSION);
    }

    @Test
    @Transactional
    void getAllRevisionsByPressionIsNullOrNotNull() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where pression is not null
        defaultRevisionShouldBeFound("pression.specified=true");

        // Get all the revisionList where pression is null
        defaultRevisionShouldNotBeFound("pression.specified=false");
    }

    @Test
    @Transactional
    void getAllRevisionsByCarroserieIsEqualToSomething() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where carroserie equals to DEFAULT_CARROSERIE
        defaultRevisionShouldBeFound("carroserie.equals=" + DEFAULT_CARROSERIE);

        // Get all the revisionList where carroserie equals to UPDATED_CARROSERIE
        defaultRevisionShouldNotBeFound("carroserie.equals=" + UPDATED_CARROSERIE);
    }

    @Test
    @Transactional
    void getAllRevisionsByCarroserieIsNotEqualToSomething() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where carroserie not equals to DEFAULT_CARROSERIE
        defaultRevisionShouldNotBeFound("carroserie.notEquals=" + DEFAULT_CARROSERIE);

        // Get all the revisionList where carroserie not equals to UPDATED_CARROSERIE
        defaultRevisionShouldBeFound("carroserie.notEquals=" + UPDATED_CARROSERIE);
    }

    @Test
    @Transactional
    void getAllRevisionsByCarroserieIsInShouldWork() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where carroserie in DEFAULT_CARROSERIE or UPDATED_CARROSERIE
        defaultRevisionShouldBeFound("carroserie.in=" + DEFAULT_CARROSERIE + "," + UPDATED_CARROSERIE);

        // Get all the revisionList where carroserie equals to UPDATED_CARROSERIE
        defaultRevisionShouldNotBeFound("carroserie.in=" + UPDATED_CARROSERIE);
    }

    @Test
    @Transactional
    void getAllRevisionsByCarroserieIsNullOrNotNull() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where carroserie is not null
        defaultRevisionShouldBeFound("carroserie.specified=true");

        // Get all the revisionList where carroserie is null
        defaultRevisionShouldNotBeFound("carroserie.specified=false");
    }

    @Test
    @Transactional
    void getAllRevisionsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where date equals to DEFAULT_DATE
        defaultRevisionShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the revisionList where date equals to UPDATED_DATE
        defaultRevisionShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllRevisionsByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where date not equals to DEFAULT_DATE
        defaultRevisionShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the revisionList where date not equals to UPDATED_DATE
        defaultRevisionShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllRevisionsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where date in DEFAULT_DATE or UPDATED_DATE
        defaultRevisionShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the revisionList where date equals to UPDATED_DATE
        defaultRevisionShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllRevisionsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where date is not null
        defaultRevisionShouldBeFound("date.specified=true");

        // Get all the revisionList where date is null
        defaultRevisionShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllRevisionsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where date is greater than or equal to DEFAULT_DATE
        defaultRevisionShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the revisionList where date is greater than or equal to UPDATED_DATE
        defaultRevisionShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllRevisionsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where date is less than or equal to DEFAULT_DATE
        defaultRevisionShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the revisionList where date is less than or equal to SMALLER_DATE
        defaultRevisionShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllRevisionsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where date is less than DEFAULT_DATE
        defaultRevisionShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the revisionList where date is less than UPDATED_DATE
        defaultRevisionShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllRevisionsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        // Get all the revisionList where date is greater than DEFAULT_DATE
        defaultRevisionShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the revisionList where date is greater than SMALLER_DATE
        defaultRevisionShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllRevisionsByAvionsIsEqualToSomething() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);
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
        revision.addAvions(avions);
        revisionRepository.saveAndFlush(revision);
        Long avionsId = avions.getId();

        // Get all the revisionList where avions equals to avionsId
        defaultRevisionShouldBeFound("avionsId.equals=" + avionsId);

        // Get all the revisionList where avions equals to (avionsId + 1)
        defaultRevisionShouldNotBeFound("avionsId.equals=" + (avionsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRevisionShouldBeFound(String filter) throws Exception {
        restRevisionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(revision.getId().intValue())))
            .andExpect(jsonPath("$.[*].niveaux").value(hasItem(DEFAULT_NIVEAUX.booleanValue())))
            .andExpect(jsonPath("$.[*].pression").value(hasItem(DEFAULT_PRESSION.booleanValue())))
            .andExpect(jsonPath("$.[*].carroserie").value(hasItem(DEFAULT_CARROSERIE.booleanValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));

        // Check, that the count call also returns 1
        restRevisionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRevisionShouldNotBeFound(String filter) throws Exception {
        restRevisionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRevisionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRevision() throws Exception {
        // Get the revision
        restRevisionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRevision() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        int databaseSizeBeforeUpdate = revisionRepository.findAll().size();

        // Update the revision
        Revision updatedRevision = revisionRepository.findById(revision.getId()).get();
        // Disconnect from session so that the updates on updatedRevision are not directly saved in db
        em.detach(updatedRevision);
        updatedRevision.niveaux(UPDATED_NIVEAUX).pression(UPDATED_PRESSION).carroserie(UPDATED_CARROSERIE).date(UPDATED_DATE);
        RevisionDTO revisionDTO = revisionMapper.toDto(updatedRevision);

        restRevisionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, revisionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(revisionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Revision in the database
        List<Revision> revisionList = revisionRepository.findAll();
        assertThat(revisionList).hasSize(databaseSizeBeforeUpdate);
        Revision testRevision = revisionList.get(revisionList.size() - 1);
        assertThat(testRevision.getNiveaux()).isEqualTo(UPDATED_NIVEAUX);
        assertThat(testRevision.getPression()).isEqualTo(UPDATED_PRESSION);
        assertThat(testRevision.getCarroserie()).isEqualTo(UPDATED_CARROSERIE);
        assertThat(testRevision.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingRevision() throws Exception {
        int databaseSizeBeforeUpdate = revisionRepository.findAll().size();
        revision.setId(count.incrementAndGet());

        // Create the Revision
        RevisionDTO revisionDTO = revisionMapper.toDto(revision);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRevisionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, revisionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(revisionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Revision in the database
        List<Revision> revisionList = revisionRepository.findAll();
        assertThat(revisionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRevision() throws Exception {
        int databaseSizeBeforeUpdate = revisionRepository.findAll().size();
        revision.setId(count.incrementAndGet());

        // Create the Revision
        RevisionDTO revisionDTO = revisionMapper.toDto(revision);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRevisionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(revisionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Revision in the database
        List<Revision> revisionList = revisionRepository.findAll();
        assertThat(revisionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRevision() throws Exception {
        int databaseSizeBeforeUpdate = revisionRepository.findAll().size();
        revision.setId(count.incrementAndGet());

        // Create the Revision
        RevisionDTO revisionDTO = revisionMapper.toDto(revision);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRevisionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(revisionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Revision in the database
        List<Revision> revisionList = revisionRepository.findAll();
        assertThat(revisionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRevisionWithPatch() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        int databaseSizeBeforeUpdate = revisionRepository.findAll().size();

        // Update the revision using partial update
        Revision partialUpdatedRevision = new Revision();
        partialUpdatedRevision.setId(revision.getId());

        partialUpdatedRevision.carroserie(UPDATED_CARROSERIE);

        restRevisionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRevision.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRevision))
            )
            .andExpect(status().isOk());

        // Validate the Revision in the database
        List<Revision> revisionList = revisionRepository.findAll();
        assertThat(revisionList).hasSize(databaseSizeBeforeUpdate);
        Revision testRevision = revisionList.get(revisionList.size() - 1);
        assertThat(testRevision.getNiveaux()).isEqualTo(DEFAULT_NIVEAUX);
        assertThat(testRevision.getPression()).isEqualTo(DEFAULT_PRESSION);
        assertThat(testRevision.getCarroserie()).isEqualTo(UPDATED_CARROSERIE);
        assertThat(testRevision.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateRevisionWithPatch() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        int databaseSizeBeforeUpdate = revisionRepository.findAll().size();

        // Update the revision using partial update
        Revision partialUpdatedRevision = new Revision();
        partialUpdatedRevision.setId(revision.getId());

        partialUpdatedRevision.niveaux(UPDATED_NIVEAUX).pression(UPDATED_PRESSION).carroserie(UPDATED_CARROSERIE).date(UPDATED_DATE);

        restRevisionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRevision.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRevision))
            )
            .andExpect(status().isOk());

        // Validate the Revision in the database
        List<Revision> revisionList = revisionRepository.findAll();
        assertThat(revisionList).hasSize(databaseSizeBeforeUpdate);
        Revision testRevision = revisionList.get(revisionList.size() - 1);
        assertThat(testRevision.getNiveaux()).isEqualTo(UPDATED_NIVEAUX);
        assertThat(testRevision.getPression()).isEqualTo(UPDATED_PRESSION);
        assertThat(testRevision.getCarroserie()).isEqualTo(UPDATED_CARROSERIE);
        assertThat(testRevision.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingRevision() throws Exception {
        int databaseSizeBeforeUpdate = revisionRepository.findAll().size();
        revision.setId(count.incrementAndGet());

        // Create the Revision
        RevisionDTO revisionDTO = revisionMapper.toDto(revision);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRevisionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, revisionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(revisionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Revision in the database
        List<Revision> revisionList = revisionRepository.findAll();
        assertThat(revisionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRevision() throws Exception {
        int databaseSizeBeforeUpdate = revisionRepository.findAll().size();
        revision.setId(count.incrementAndGet());

        // Create the Revision
        RevisionDTO revisionDTO = revisionMapper.toDto(revision);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRevisionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(revisionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Revision in the database
        List<Revision> revisionList = revisionRepository.findAll();
        assertThat(revisionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRevision() throws Exception {
        int databaseSizeBeforeUpdate = revisionRepository.findAll().size();
        revision.setId(count.incrementAndGet());

        // Create the Revision
        RevisionDTO revisionDTO = revisionMapper.toDto(revision);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRevisionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(revisionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Revision in the database
        List<Revision> revisionList = revisionRepository.findAll();
        assertThat(revisionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRevision() throws Exception {
        // Initialize the database
        revisionRepository.saveAndFlush(revision);

        int databaseSizeBeforeDelete = revisionRepository.findAll().size();

        // Delete the revision
        restRevisionMockMvc
            .perform(delete(ENTITY_API_URL_ID, revision.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Revision> revisionList = revisionRepository.findAll();
        assertThat(revisionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
