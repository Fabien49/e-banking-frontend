package com.fabienit.aeroclubpassion.web.rest;

import static com.fabienit.aeroclubpassion.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fabienit.aeroclubpassion.IntegrationTest;
import com.fabienit.aeroclubpassion.domain.Atelier;
import com.fabienit.aeroclubpassion.domain.Avion;
import com.fabienit.aeroclubpassion.repository.AtelierRepository;
import com.fabienit.aeroclubpassion.service.criteria.AtelierCriteria;
import com.fabienit.aeroclubpassion.service.dto.AtelierDTO;
import com.fabienit.aeroclubpassion.service.mapper.AtelierMapper;
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
 * Integration tests for the {@link AtelierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AtelierResourceIT {

    private static final Integer DEFAULT_COMPTEUR_CHGT_MOTEUR = 1;
    private static final Integer UPDATED_COMPTEUR_CHGT_MOTEUR = 2;
    private static final Integer SMALLER_COMPTEUR_CHGT_MOTEUR = 1 - 1;

    private static final Integer DEFAULT_COMPTEUR_CARROSSERIE = 1;
    private static final Integer UPDATED_COMPTEUR_CARROSSERIE = 2;
    private static final Integer SMALLER_COMPTEUR_CARROSSERIE = 1 - 1;

    private static final Integer DEFAULT_COMPTEUR_HELISSE = 1;
    private static final Integer UPDATED_COMPTEUR_HELISSE = 2;
    private static final Integer SMALLER_COMPTEUR_HELISSE = 1 - 1;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/ateliers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AtelierRepository atelierRepository;

    @Autowired
    private AtelierMapper atelierMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAtelierMockMvc;

    private Atelier atelier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Atelier createEntity(EntityManager em) {
        Atelier atelier = new Atelier()
            .compteurChgtMoteur(DEFAULT_COMPTEUR_CHGT_MOTEUR)
            .compteurCarrosserie(DEFAULT_COMPTEUR_CARROSSERIE)
            .compteurHelisse(DEFAULT_COMPTEUR_HELISSE)
            .date(DEFAULT_DATE);
        return atelier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Atelier createUpdatedEntity(EntityManager em) {
        Atelier atelier = new Atelier()
            .compteurChgtMoteur(UPDATED_COMPTEUR_CHGT_MOTEUR)
            .compteurCarrosserie(UPDATED_COMPTEUR_CARROSSERIE)
            .compteurHelisse(UPDATED_COMPTEUR_HELISSE)
            .date(UPDATED_DATE);
        return atelier;
    }

    @BeforeEach
    public void initTest() {
        atelier = createEntity(em);
    }

    @Test
    @Transactional
    void createAtelier() throws Exception {
        int databaseSizeBeforeCreate = atelierRepository.findAll().size();
        // Create the Atelier
        AtelierDTO atelierDTO = atelierMapper.toDto(atelier);
        restAtelierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(atelierDTO)))
            .andExpect(status().isCreated());

        // Validate the Atelier in the database
        List<Atelier> atelierList = atelierRepository.findAll();
        assertThat(atelierList).hasSize(databaseSizeBeforeCreate + 1);
        Atelier testAtelier = atelierList.get(atelierList.size() - 1);
        assertThat(testAtelier.getCompteurChgtMoteur()).isEqualTo(DEFAULT_COMPTEUR_CHGT_MOTEUR);
        assertThat(testAtelier.getCompteurCarrosserie()).isEqualTo(DEFAULT_COMPTEUR_CARROSSERIE);
        assertThat(testAtelier.getCompteurHelisse()).isEqualTo(DEFAULT_COMPTEUR_HELISSE);
        assertThat(testAtelier.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createAtelierWithExistingId() throws Exception {
        // Create the Atelier with an existing ID
        atelier.setId(1L);
        AtelierDTO atelierDTO = atelierMapper.toDto(atelier);

        int databaseSizeBeforeCreate = atelierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtelierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(atelierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Atelier in the database
        List<Atelier> atelierList = atelierRepository.findAll();
        assertThat(atelierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAteliers() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList
        restAtelierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atelier.getId().intValue())))
            .andExpect(jsonPath("$.[*].compteurChgtMoteur").value(hasItem(DEFAULT_COMPTEUR_CHGT_MOTEUR)))
            .andExpect(jsonPath("$.[*].compteurCarrosserie").value(hasItem(DEFAULT_COMPTEUR_CARROSSERIE)))
            .andExpect(jsonPath("$.[*].compteurHelisse").value(hasItem(DEFAULT_COMPTEUR_HELISSE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }

    @Test
    @Transactional
    void getAtelier() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get the atelier
        restAtelierMockMvc
            .perform(get(ENTITY_API_URL_ID, atelier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(atelier.getId().intValue()))
            .andExpect(jsonPath("$.compteurChgtMoteur").value(DEFAULT_COMPTEUR_CHGT_MOTEUR))
            .andExpect(jsonPath("$.compteurCarrosserie").value(DEFAULT_COMPTEUR_CARROSSERIE))
            .andExpect(jsonPath("$.compteurHelisse").value(DEFAULT_COMPTEUR_HELISSE))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    void getAteliersByIdFiltering() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        Long id = atelier.getId();

        defaultAtelierShouldBeFound("id.equals=" + id);
        defaultAtelierShouldNotBeFound("id.notEquals=" + id);

        defaultAtelierShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAtelierShouldNotBeFound("id.greaterThan=" + id);

        defaultAtelierShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAtelierShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurChgtMoteurIsEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurChgtMoteur equals to DEFAULT_COMPTEUR_CHGT_MOTEUR
        defaultAtelierShouldBeFound("compteurChgtMoteur.equals=" + DEFAULT_COMPTEUR_CHGT_MOTEUR);

        // Get all the atelierList where compteurChgtMoteur equals to UPDATED_COMPTEUR_CHGT_MOTEUR
        defaultAtelierShouldNotBeFound("compteurChgtMoteur.equals=" + UPDATED_COMPTEUR_CHGT_MOTEUR);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurChgtMoteurIsNotEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurChgtMoteur not equals to DEFAULT_COMPTEUR_CHGT_MOTEUR
        defaultAtelierShouldNotBeFound("compteurChgtMoteur.notEquals=" + DEFAULT_COMPTEUR_CHGT_MOTEUR);

        // Get all the atelierList where compteurChgtMoteur not equals to UPDATED_COMPTEUR_CHGT_MOTEUR
        defaultAtelierShouldBeFound("compteurChgtMoteur.notEquals=" + UPDATED_COMPTEUR_CHGT_MOTEUR);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurChgtMoteurIsInShouldWork() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurChgtMoteur in DEFAULT_COMPTEUR_CHGT_MOTEUR or UPDATED_COMPTEUR_CHGT_MOTEUR
        defaultAtelierShouldBeFound("compteurChgtMoteur.in=" + DEFAULT_COMPTEUR_CHGT_MOTEUR + "," + UPDATED_COMPTEUR_CHGT_MOTEUR);

        // Get all the atelierList where compteurChgtMoteur equals to UPDATED_COMPTEUR_CHGT_MOTEUR
        defaultAtelierShouldNotBeFound("compteurChgtMoteur.in=" + UPDATED_COMPTEUR_CHGT_MOTEUR);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurChgtMoteurIsNullOrNotNull() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurChgtMoteur is not null
        defaultAtelierShouldBeFound("compteurChgtMoteur.specified=true");

        // Get all the atelierList where compteurChgtMoteur is null
        defaultAtelierShouldNotBeFound("compteurChgtMoteur.specified=false");
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurChgtMoteurIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurChgtMoteur is greater than or equal to DEFAULT_COMPTEUR_CHGT_MOTEUR
        defaultAtelierShouldBeFound("compteurChgtMoteur.greaterThanOrEqual=" + DEFAULT_COMPTEUR_CHGT_MOTEUR);

        // Get all the atelierList where compteurChgtMoteur is greater than or equal to UPDATED_COMPTEUR_CHGT_MOTEUR
        defaultAtelierShouldNotBeFound("compteurChgtMoteur.greaterThanOrEqual=" + UPDATED_COMPTEUR_CHGT_MOTEUR);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurChgtMoteurIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurChgtMoteur is less than or equal to DEFAULT_COMPTEUR_CHGT_MOTEUR
        defaultAtelierShouldBeFound("compteurChgtMoteur.lessThanOrEqual=" + DEFAULT_COMPTEUR_CHGT_MOTEUR);

        // Get all the atelierList where compteurChgtMoteur is less than or equal to SMALLER_COMPTEUR_CHGT_MOTEUR
        defaultAtelierShouldNotBeFound("compteurChgtMoteur.lessThanOrEqual=" + SMALLER_COMPTEUR_CHGT_MOTEUR);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurChgtMoteurIsLessThanSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurChgtMoteur is less than DEFAULT_COMPTEUR_CHGT_MOTEUR
        defaultAtelierShouldNotBeFound("compteurChgtMoteur.lessThan=" + DEFAULT_COMPTEUR_CHGT_MOTEUR);

        // Get all the atelierList where compteurChgtMoteur is less than UPDATED_COMPTEUR_CHGT_MOTEUR
        defaultAtelierShouldBeFound("compteurChgtMoteur.lessThan=" + UPDATED_COMPTEUR_CHGT_MOTEUR);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurChgtMoteurIsGreaterThanSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurChgtMoteur is greater than DEFAULT_COMPTEUR_CHGT_MOTEUR
        defaultAtelierShouldNotBeFound("compteurChgtMoteur.greaterThan=" + DEFAULT_COMPTEUR_CHGT_MOTEUR);

        // Get all the atelierList where compteurChgtMoteur is greater than SMALLER_COMPTEUR_CHGT_MOTEUR
        defaultAtelierShouldBeFound("compteurChgtMoteur.greaterThan=" + SMALLER_COMPTEUR_CHGT_MOTEUR);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurCarrosserieIsEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurCarrosserie equals to DEFAULT_COMPTEUR_CARROSSERIE
        defaultAtelierShouldBeFound("compteurCarrosserie.equals=" + DEFAULT_COMPTEUR_CARROSSERIE);

        // Get all the atelierList where compteurCarrosserie equals to UPDATED_COMPTEUR_CARROSSERIE
        defaultAtelierShouldNotBeFound("compteurCarrosserie.equals=" + UPDATED_COMPTEUR_CARROSSERIE);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurCarrosserieIsNotEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurCarrosserie not equals to DEFAULT_COMPTEUR_CARROSSERIE
        defaultAtelierShouldNotBeFound("compteurCarrosserie.notEquals=" + DEFAULT_COMPTEUR_CARROSSERIE);

        // Get all the atelierList where compteurCarrosserie not equals to UPDATED_COMPTEUR_CARROSSERIE
        defaultAtelierShouldBeFound("compteurCarrosserie.notEquals=" + UPDATED_COMPTEUR_CARROSSERIE);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurCarrosserieIsInShouldWork() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurCarrosserie in DEFAULT_COMPTEUR_CARROSSERIE or UPDATED_COMPTEUR_CARROSSERIE
        defaultAtelierShouldBeFound("compteurCarrosserie.in=" + DEFAULT_COMPTEUR_CARROSSERIE + "," + UPDATED_COMPTEUR_CARROSSERIE);

        // Get all the atelierList where compteurCarrosserie equals to UPDATED_COMPTEUR_CARROSSERIE
        defaultAtelierShouldNotBeFound("compteurCarrosserie.in=" + UPDATED_COMPTEUR_CARROSSERIE);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurCarrosserieIsNullOrNotNull() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurCarrosserie is not null
        defaultAtelierShouldBeFound("compteurCarrosserie.specified=true");

        // Get all the atelierList where compteurCarrosserie is null
        defaultAtelierShouldNotBeFound("compteurCarrosserie.specified=false");
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurCarrosserieIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurCarrosserie is greater than or equal to DEFAULT_COMPTEUR_CARROSSERIE
        defaultAtelierShouldBeFound("compteurCarrosserie.greaterThanOrEqual=" + DEFAULT_COMPTEUR_CARROSSERIE);

        // Get all the atelierList where compteurCarrosserie is greater than or equal to UPDATED_COMPTEUR_CARROSSERIE
        defaultAtelierShouldNotBeFound("compteurCarrosserie.greaterThanOrEqual=" + UPDATED_COMPTEUR_CARROSSERIE);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurCarrosserieIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurCarrosserie is less than or equal to DEFAULT_COMPTEUR_CARROSSERIE
        defaultAtelierShouldBeFound("compteurCarrosserie.lessThanOrEqual=" + DEFAULT_COMPTEUR_CARROSSERIE);

        // Get all the atelierList where compteurCarrosserie is less than or equal to SMALLER_COMPTEUR_CARROSSERIE
        defaultAtelierShouldNotBeFound("compteurCarrosserie.lessThanOrEqual=" + SMALLER_COMPTEUR_CARROSSERIE);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurCarrosserieIsLessThanSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurCarrosserie is less than DEFAULT_COMPTEUR_CARROSSERIE
        defaultAtelierShouldNotBeFound("compteurCarrosserie.lessThan=" + DEFAULT_COMPTEUR_CARROSSERIE);

        // Get all the atelierList where compteurCarrosserie is less than UPDATED_COMPTEUR_CARROSSERIE
        defaultAtelierShouldBeFound("compteurCarrosserie.lessThan=" + UPDATED_COMPTEUR_CARROSSERIE);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurCarrosserieIsGreaterThanSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurCarrosserie is greater than DEFAULT_COMPTEUR_CARROSSERIE
        defaultAtelierShouldNotBeFound("compteurCarrosserie.greaterThan=" + DEFAULT_COMPTEUR_CARROSSERIE);

        // Get all the atelierList where compteurCarrosserie is greater than SMALLER_COMPTEUR_CARROSSERIE
        defaultAtelierShouldBeFound("compteurCarrosserie.greaterThan=" + SMALLER_COMPTEUR_CARROSSERIE);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurHelisseIsEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurHelisse equals to DEFAULT_COMPTEUR_HELISSE
        defaultAtelierShouldBeFound("compteurHelisse.equals=" + DEFAULT_COMPTEUR_HELISSE);

        // Get all the atelierList where compteurHelisse equals to UPDATED_COMPTEUR_HELISSE
        defaultAtelierShouldNotBeFound("compteurHelisse.equals=" + UPDATED_COMPTEUR_HELISSE);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurHelisseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurHelisse not equals to DEFAULT_COMPTEUR_HELISSE
        defaultAtelierShouldNotBeFound("compteurHelisse.notEquals=" + DEFAULT_COMPTEUR_HELISSE);

        // Get all the atelierList where compteurHelisse not equals to UPDATED_COMPTEUR_HELISSE
        defaultAtelierShouldBeFound("compteurHelisse.notEquals=" + UPDATED_COMPTEUR_HELISSE);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurHelisseIsInShouldWork() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurHelisse in DEFAULT_COMPTEUR_HELISSE or UPDATED_COMPTEUR_HELISSE
        defaultAtelierShouldBeFound("compteurHelisse.in=" + DEFAULT_COMPTEUR_HELISSE + "," + UPDATED_COMPTEUR_HELISSE);

        // Get all the atelierList where compteurHelisse equals to UPDATED_COMPTEUR_HELISSE
        defaultAtelierShouldNotBeFound("compteurHelisse.in=" + UPDATED_COMPTEUR_HELISSE);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurHelisseIsNullOrNotNull() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurHelisse is not null
        defaultAtelierShouldBeFound("compteurHelisse.specified=true");

        // Get all the atelierList where compteurHelisse is null
        defaultAtelierShouldNotBeFound("compteurHelisse.specified=false");
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurHelisseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurHelisse is greater than or equal to DEFAULT_COMPTEUR_HELISSE
        defaultAtelierShouldBeFound("compteurHelisse.greaterThanOrEqual=" + DEFAULT_COMPTEUR_HELISSE);

        // Get all the atelierList where compteurHelisse is greater than or equal to UPDATED_COMPTEUR_HELISSE
        defaultAtelierShouldNotBeFound("compteurHelisse.greaterThanOrEqual=" + UPDATED_COMPTEUR_HELISSE);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurHelisseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurHelisse is less than or equal to DEFAULT_COMPTEUR_HELISSE
        defaultAtelierShouldBeFound("compteurHelisse.lessThanOrEqual=" + DEFAULT_COMPTEUR_HELISSE);

        // Get all the atelierList where compteurHelisse is less than or equal to SMALLER_COMPTEUR_HELISSE
        defaultAtelierShouldNotBeFound("compteurHelisse.lessThanOrEqual=" + SMALLER_COMPTEUR_HELISSE);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurHelisseIsLessThanSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurHelisse is less than DEFAULT_COMPTEUR_HELISSE
        defaultAtelierShouldNotBeFound("compteurHelisse.lessThan=" + DEFAULT_COMPTEUR_HELISSE);

        // Get all the atelierList where compteurHelisse is less than UPDATED_COMPTEUR_HELISSE
        defaultAtelierShouldBeFound("compteurHelisse.lessThan=" + UPDATED_COMPTEUR_HELISSE);
    }

    @Test
    @Transactional
    void getAllAteliersByCompteurHelisseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where compteurHelisse is greater than DEFAULT_COMPTEUR_HELISSE
        defaultAtelierShouldNotBeFound("compteurHelisse.greaterThan=" + DEFAULT_COMPTEUR_HELISSE);

        // Get all the atelierList where compteurHelisse is greater than SMALLER_COMPTEUR_HELISSE
        defaultAtelierShouldBeFound("compteurHelisse.greaterThan=" + SMALLER_COMPTEUR_HELISSE);
    }

    @Test
    @Transactional
    void getAllAteliersByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where date equals to DEFAULT_DATE
        defaultAtelierShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the atelierList where date equals to UPDATED_DATE
        defaultAtelierShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAteliersByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where date not equals to DEFAULT_DATE
        defaultAtelierShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the atelierList where date not equals to UPDATED_DATE
        defaultAtelierShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAteliersByDateIsInShouldWork() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where date in DEFAULT_DATE or UPDATED_DATE
        defaultAtelierShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the atelierList where date equals to UPDATED_DATE
        defaultAtelierShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAteliersByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where date is not null
        defaultAtelierShouldBeFound("date.specified=true");

        // Get all the atelierList where date is null
        defaultAtelierShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllAteliersByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where date is greater than or equal to DEFAULT_DATE
        defaultAtelierShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the atelierList where date is greater than or equal to UPDATED_DATE
        defaultAtelierShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAteliersByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where date is less than or equal to DEFAULT_DATE
        defaultAtelierShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the atelierList where date is less than or equal to SMALLER_DATE
        defaultAtelierShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllAteliersByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where date is less than DEFAULT_DATE
        defaultAtelierShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the atelierList where date is less than UPDATED_DATE
        defaultAtelierShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllAteliersByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        // Get all the atelierList where date is greater than DEFAULT_DATE
        defaultAtelierShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the atelierList where date is greater than SMALLER_DATE
        defaultAtelierShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllAteliersByAvionsIsEqualToSomething() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);
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
        atelier.addAvions(avions);
        atelierRepository.saveAndFlush(atelier);
        Long avionsId = avions.getId();

        // Get all the atelierList where avions equals to avionsId
        defaultAtelierShouldBeFound("avionsId.equals=" + avionsId);

        // Get all the atelierList where avions equals to (avionsId + 1)
        defaultAtelierShouldNotBeFound("avionsId.equals=" + (avionsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAtelierShouldBeFound(String filter) throws Exception {
        restAtelierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atelier.getId().intValue())))
            .andExpect(jsonPath("$.[*].compteurChgtMoteur").value(hasItem(DEFAULT_COMPTEUR_CHGT_MOTEUR)))
            .andExpect(jsonPath("$.[*].compteurCarrosserie").value(hasItem(DEFAULT_COMPTEUR_CARROSSERIE)))
            .andExpect(jsonPath("$.[*].compteurHelisse").value(hasItem(DEFAULT_COMPTEUR_HELISSE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));

        // Check, that the count call also returns 1
        restAtelierMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAtelierShouldNotBeFound(String filter) throws Exception {
        restAtelierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAtelierMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAtelier() throws Exception {
        // Get the atelier
        restAtelierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAtelier() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        int databaseSizeBeforeUpdate = atelierRepository.findAll().size();

        // Update the atelier
        Atelier updatedAtelier = atelierRepository.findById(atelier.getId()).get();
        // Disconnect from session so that the updates on updatedAtelier are not directly saved in db
        em.detach(updatedAtelier);
        updatedAtelier
            .compteurChgtMoteur(UPDATED_COMPTEUR_CHGT_MOTEUR)
            .compteurCarrosserie(UPDATED_COMPTEUR_CARROSSERIE)
            .compteurHelisse(UPDATED_COMPTEUR_HELISSE)
            .date(UPDATED_DATE);
        AtelierDTO atelierDTO = atelierMapper.toDto(updatedAtelier);

        restAtelierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, atelierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(atelierDTO))
            )
            .andExpect(status().isOk());

        // Validate the Atelier in the database
        List<Atelier> atelierList = atelierRepository.findAll();
        assertThat(atelierList).hasSize(databaseSizeBeforeUpdate);
        Atelier testAtelier = atelierList.get(atelierList.size() - 1);
        assertThat(testAtelier.getCompteurChgtMoteur()).isEqualTo(UPDATED_COMPTEUR_CHGT_MOTEUR);
        assertThat(testAtelier.getCompteurCarrosserie()).isEqualTo(UPDATED_COMPTEUR_CARROSSERIE);
        assertThat(testAtelier.getCompteurHelisse()).isEqualTo(UPDATED_COMPTEUR_HELISSE);
        assertThat(testAtelier.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingAtelier() throws Exception {
        int databaseSizeBeforeUpdate = atelierRepository.findAll().size();
        atelier.setId(count.incrementAndGet());

        // Create the Atelier
        AtelierDTO atelierDTO = atelierMapper.toDto(atelier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtelierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, atelierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(atelierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Atelier in the database
        List<Atelier> atelierList = atelierRepository.findAll();
        assertThat(atelierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAtelier() throws Exception {
        int databaseSizeBeforeUpdate = atelierRepository.findAll().size();
        atelier.setId(count.incrementAndGet());

        // Create the Atelier
        AtelierDTO atelierDTO = atelierMapper.toDto(atelier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtelierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(atelierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Atelier in the database
        List<Atelier> atelierList = atelierRepository.findAll();
        assertThat(atelierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAtelier() throws Exception {
        int databaseSizeBeforeUpdate = atelierRepository.findAll().size();
        atelier.setId(count.incrementAndGet());

        // Create the Atelier
        AtelierDTO atelierDTO = atelierMapper.toDto(atelier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtelierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(atelierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Atelier in the database
        List<Atelier> atelierList = atelierRepository.findAll();
        assertThat(atelierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAtelierWithPatch() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        int databaseSizeBeforeUpdate = atelierRepository.findAll().size();

        // Update the atelier using partial update
        Atelier partialUpdatedAtelier = new Atelier();
        partialUpdatedAtelier.setId(atelier.getId());

        partialUpdatedAtelier
            .compteurChgtMoteur(UPDATED_COMPTEUR_CHGT_MOTEUR)
            .compteurCarrosserie(UPDATED_COMPTEUR_CARROSSERIE)
            .compteurHelisse(UPDATED_COMPTEUR_HELISSE)
            .date(UPDATED_DATE);

        restAtelierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAtelier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAtelier))
            )
            .andExpect(status().isOk());

        // Validate the Atelier in the database
        List<Atelier> atelierList = atelierRepository.findAll();
        assertThat(atelierList).hasSize(databaseSizeBeforeUpdate);
        Atelier testAtelier = atelierList.get(atelierList.size() - 1);
        assertThat(testAtelier.getCompteurChgtMoteur()).isEqualTo(UPDATED_COMPTEUR_CHGT_MOTEUR);
        assertThat(testAtelier.getCompteurCarrosserie()).isEqualTo(UPDATED_COMPTEUR_CARROSSERIE);
        assertThat(testAtelier.getCompteurHelisse()).isEqualTo(UPDATED_COMPTEUR_HELISSE);
        assertThat(testAtelier.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateAtelierWithPatch() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        int databaseSizeBeforeUpdate = atelierRepository.findAll().size();

        // Update the atelier using partial update
        Atelier partialUpdatedAtelier = new Atelier();
        partialUpdatedAtelier.setId(atelier.getId());

        partialUpdatedAtelier
            .compteurChgtMoteur(UPDATED_COMPTEUR_CHGT_MOTEUR)
            .compteurCarrosserie(UPDATED_COMPTEUR_CARROSSERIE)
            .compteurHelisse(UPDATED_COMPTEUR_HELISSE)
            .date(UPDATED_DATE);

        restAtelierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAtelier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAtelier))
            )
            .andExpect(status().isOk());

        // Validate the Atelier in the database
        List<Atelier> atelierList = atelierRepository.findAll();
        assertThat(atelierList).hasSize(databaseSizeBeforeUpdate);
        Atelier testAtelier = atelierList.get(atelierList.size() - 1);
        assertThat(testAtelier.getCompteurChgtMoteur()).isEqualTo(UPDATED_COMPTEUR_CHGT_MOTEUR);
        assertThat(testAtelier.getCompteurCarrosserie()).isEqualTo(UPDATED_COMPTEUR_CARROSSERIE);
        assertThat(testAtelier.getCompteurHelisse()).isEqualTo(UPDATED_COMPTEUR_HELISSE);
        assertThat(testAtelier.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingAtelier() throws Exception {
        int databaseSizeBeforeUpdate = atelierRepository.findAll().size();
        atelier.setId(count.incrementAndGet());

        // Create the Atelier
        AtelierDTO atelierDTO = atelierMapper.toDto(atelier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtelierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, atelierDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(atelierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Atelier in the database
        List<Atelier> atelierList = atelierRepository.findAll();
        assertThat(atelierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAtelier() throws Exception {
        int databaseSizeBeforeUpdate = atelierRepository.findAll().size();
        atelier.setId(count.incrementAndGet());

        // Create the Atelier
        AtelierDTO atelierDTO = atelierMapper.toDto(atelier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtelierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(atelierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Atelier in the database
        List<Atelier> atelierList = atelierRepository.findAll();
        assertThat(atelierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAtelier() throws Exception {
        int databaseSizeBeforeUpdate = atelierRepository.findAll().size();
        atelier.setId(count.incrementAndGet());

        // Create the Atelier
        AtelierDTO atelierDTO = atelierMapper.toDto(atelier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtelierMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(atelierDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Atelier in the database
        List<Atelier> atelierList = atelierRepository.findAll();
        assertThat(atelierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAtelier() throws Exception {
        // Initialize the database
        atelierRepository.saveAndFlush(atelier);

        int databaseSizeBeforeDelete = atelierRepository.findAll().size();

        // Delete the atelier
        restAtelierMockMvc
            .perform(delete(ENTITY_API_URL_ID, atelier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Atelier> atelierList = atelierRepository.findAll();
        assertThat(atelierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
