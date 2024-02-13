package com.fabienit.aeroclubpassion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fabienit.aeroclubpassion.IntegrationTest;
import com.fabienit.aeroclubpassion.domain.Atelier;
import com.fabienit.aeroclubpassion.domain.Avion;
import com.fabienit.aeroclubpassion.domain.Revision;
import com.fabienit.aeroclubpassion.repository.AvionRepository;
import com.fabienit.aeroclubpassion.service.criteria.AvionCriteria;
import com.fabienit.aeroclubpassion.service.dto.AvionDTO;
import com.fabienit.aeroclubpassion.service.mapper.AvionMapper;
import java.time.Duration;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link AvionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AvionResourceIT {

    private static final String DEFAULT_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MOTEUR = "AAAAAAAAAA";
    private static final String UPDATED_MOTEUR = "BBBBBBBBBB";

    private static final Integer DEFAULT_PUISSANCE = 1;
    private static final Integer UPDATED_PUISSANCE = 2;
    private static final Integer SMALLER_PUISSANCE = 1 - 1;

    private static final Integer DEFAULT_PLACE = 1;
    private static final Integer UPDATED_PLACE = 2;
    private static final Integer SMALLER_PLACE = 1 - 1;

    private static final Duration DEFAULT_AUTONOMIE = Duration.ofHours(6);
    private static final Duration UPDATED_AUTONOMIE = Duration.ofHours(12);
    private static final Duration SMALLER_AUTONOMIE = Duration.ofHours(5);

    private static final String DEFAULT_USAGE = "AAAAAAAAAA";
    private static final String UPDATED_USAGE = "BBBBBBBBBB";

    private static final Duration DEFAULT_HEURES = Duration.ofHours(6);
    private static final Duration UPDATED_HEURES = Duration.ofHours(12);
    private static final Duration SMALLER_HEURES = Duration.ofHours(5);

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/avions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private AvionMapper avionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAvionMockMvc;

    private Avion avion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avion createEntity(EntityManager em) {
        Avion avion = new Avion()
            .marque(DEFAULT_MARQUE)
            .type(DEFAULT_TYPE)
            .moteur(DEFAULT_MOTEUR)
            .puissance(DEFAULT_PUISSANCE)
            .place(DEFAULT_PLACE)
            .autonomie(DEFAULT_AUTONOMIE)
            .usage(DEFAULT_USAGE)
            .heures(DEFAULT_HEURES)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return avion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avion createUpdatedEntity(EntityManager em) {
        Avion avion = new Avion()
            .marque(UPDATED_MARQUE)
            .type(UPDATED_TYPE)
            .moteur(UPDATED_MOTEUR)
            .puissance(UPDATED_PUISSANCE)
            .place(UPDATED_PLACE)
            .autonomie(UPDATED_AUTONOMIE)
            .usage(UPDATED_USAGE)
            .heures(UPDATED_HEURES)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return avion;
    }

    @BeforeEach
    public void initTest() {
        avion = createEntity(em);
    }

    @Test
    @Transactional
    void createAvion() throws Exception {
        int databaseSizeBeforeCreate = avionRepository.findAll().size();
        // Create the Avion
        AvionDTO avionDTO = avionMapper.toDto(avion);
        restAvionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avionDTO)))
            .andExpect(status().isCreated());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeCreate + 1);
        Avion testAvion = avionList.get(avionList.size() - 1);
        assertThat(testAvion.getMarque()).isEqualTo(DEFAULT_MARQUE);
        assertThat(testAvion.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAvion.getMoteur()).isEqualTo(DEFAULT_MOTEUR);
        assertThat(testAvion.getPuissance()).isEqualTo(DEFAULT_PUISSANCE);
        assertThat(testAvion.getPlace()).isEqualTo(DEFAULT_PLACE);
        assertThat(testAvion.getAutonomie()).isEqualTo(DEFAULT_AUTONOMIE);
        assertThat(testAvion.getUsage()).isEqualTo(DEFAULT_USAGE);
        assertThat(testAvion.getHeures()).isEqualTo(DEFAULT_HEURES);
        assertThat(testAvion.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testAvion.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createAvionWithExistingId() throws Exception {
        // Create the Avion with an existing ID
        avion.setId(1L);
        AvionDTO avionDTO = avionMapper.toDto(avion);

        int databaseSizeBeforeCreate = avionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMarqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = avionRepository.findAll().size();
        // set the field null
        avion.setMarque(null);

        // Create the Avion, which fails.
        AvionDTO avionDTO = avionMapper.toDto(avion);

        restAvionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avionDTO)))
            .andExpect(status().isBadRequest());

        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPlaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = avionRepository.findAll().size();
        // set the field null
        avion.setPlace(null);

        // Create the Avion, which fails.
        AvionDTO avionDTO = avionMapper.toDto(avion);

        restAvionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avionDTO)))
            .andExpect(status().isBadRequest());

        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHeuresIsRequired() throws Exception {
        int databaseSizeBeforeTest = avionRepository.findAll().size();
        // set the field null
        avion.setHeures(null);

        // Create the Avion, which fails.
        AvionDTO avionDTO = avionMapper.toDto(avion);

        restAvionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avionDTO)))
            .andExpect(status().isBadRequest());

        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAvions() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList
        restAvionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avion.getId().intValue())))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].moteur").value(hasItem(DEFAULT_MOTEUR)))
            .andExpect(jsonPath("$.[*].puissance").value(hasItem(DEFAULT_PUISSANCE)))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE)))
            .andExpect(jsonPath("$.[*].autonomie").value(hasItem(DEFAULT_AUTONOMIE.toString())))
            .andExpect(jsonPath("$.[*].usage").value(hasItem(DEFAULT_USAGE)))
            .andExpect(jsonPath("$.[*].heures").value(hasItem(DEFAULT_HEURES.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getAvion() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get the avion
        restAvionMockMvc
            .perform(get(ENTITY_API_URL_ID, avion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(avion.getId().intValue()))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.moteur").value(DEFAULT_MOTEUR))
            .andExpect(jsonPath("$.puissance").value(DEFAULT_PUISSANCE))
            .andExpect(jsonPath("$.place").value(DEFAULT_PLACE))
            .andExpect(jsonPath("$.autonomie").value(DEFAULT_AUTONOMIE.toString()))
            .andExpect(jsonPath("$.usage").value(DEFAULT_USAGE))
            .andExpect(jsonPath("$.heures").value(DEFAULT_HEURES.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getAvionsByIdFiltering() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        Long id = avion.getId();

        defaultAvionShouldBeFound("id.equals=" + id);
        defaultAvionShouldNotBeFound("id.notEquals=" + id);

        defaultAvionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAvionShouldNotBeFound("id.greaterThan=" + id);

        defaultAvionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAvionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAvionsByMarqueIsEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where marque equals to DEFAULT_MARQUE
        defaultAvionShouldBeFound("marque.equals=" + DEFAULT_MARQUE);

        // Get all the avionList where marque equals to UPDATED_MARQUE
        defaultAvionShouldNotBeFound("marque.equals=" + UPDATED_MARQUE);
    }

    @Test
    @Transactional
    void getAllAvionsByMarqueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where marque not equals to DEFAULT_MARQUE
        defaultAvionShouldNotBeFound("marque.notEquals=" + DEFAULT_MARQUE);

        // Get all the avionList where marque not equals to UPDATED_MARQUE
        defaultAvionShouldBeFound("marque.notEquals=" + UPDATED_MARQUE);
    }

    @Test
    @Transactional
    void getAllAvionsByMarqueIsInShouldWork() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where marque in DEFAULT_MARQUE or UPDATED_MARQUE
        defaultAvionShouldBeFound("marque.in=" + DEFAULT_MARQUE + "," + UPDATED_MARQUE);

        // Get all the avionList where marque equals to UPDATED_MARQUE
        defaultAvionShouldNotBeFound("marque.in=" + UPDATED_MARQUE);
    }

    @Test
    @Transactional
    void getAllAvionsByMarqueIsNullOrNotNull() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where marque is not null
        defaultAvionShouldBeFound("marque.specified=true");

        // Get all the avionList where marque is null
        defaultAvionShouldNotBeFound("marque.specified=false");
    }

    @Test
    @Transactional
    void getAllAvionsByMarqueContainsSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where marque contains DEFAULT_MARQUE
        defaultAvionShouldBeFound("marque.contains=" + DEFAULT_MARQUE);

        // Get all the avionList where marque contains UPDATED_MARQUE
        defaultAvionShouldNotBeFound("marque.contains=" + UPDATED_MARQUE);
    }

    @Test
    @Transactional
    void getAllAvionsByMarqueNotContainsSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where marque does not contain DEFAULT_MARQUE
        defaultAvionShouldNotBeFound("marque.doesNotContain=" + DEFAULT_MARQUE);

        // Get all the avionList where marque does not contain UPDATED_MARQUE
        defaultAvionShouldBeFound("marque.doesNotContain=" + UPDATED_MARQUE);
    }

    @Test
    @Transactional
    void getAllAvionsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where type equals to DEFAULT_TYPE
        defaultAvionShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the avionList where type equals to UPDATED_TYPE
        defaultAvionShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAvionsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where type not equals to DEFAULT_TYPE
        defaultAvionShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the avionList where type not equals to UPDATED_TYPE
        defaultAvionShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAvionsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultAvionShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the avionList where type equals to UPDATED_TYPE
        defaultAvionShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAvionsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where type is not null
        defaultAvionShouldBeFound("type.specified=true");

        // Get all the avionList where type is null
        defaultAvionShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllAvionsByTypeContainsSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where type contains DEFAULT_TYPE
        defaultAvionShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the avionList where type contains UPDATED_TYPE
        defaultAvionShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAvionsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where type does not contain DEFAULT_TYPE
        defaultAvionShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the avionList where type does not contain UPDATED_TYPE
        defaultAvionShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAvionsByMoteurIsEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where moteur equals to DEFAULT_MOTEUR
        defaultAvionShouldBeFound("moteur.equals=" + DEFAULT_MOTEUR);

        // Get all the avionList where moteur equals to UPDATED_MOTEUR
        defaultAvionShouldNotBeFound("moteur.equals=" + UPDATED_MOTEUR);
    }

    @Test
    @Transactional
    void getAllAvionsByMoteurIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where moteur not equals to DEFAULT_MOTEUR
        defaultAvionShouldNotBeFound("moteur.notEquals=" + DEFAULT_MOTEUR);

        // Get all the avionList where moteur not equals to UPDATED_MOTEUR
        defaultAvionShouldBeFound("moteur.notEquals=" + UPDATED_MOTEUR);
    }

    @Test
    @Transactional
    void getAllAvionsByMoteurIsInShouldWork() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where moteur in DEFAULT_MOTEUR or UPDATED_MOTEUR
        defaultAvionShouldBeFound("moteur.in=" + DEFAULT_MOTEUR + "," + UPDATED_MOTEUR);

        // Get all the avionList where moteur equals to UPDATED_MOTEUR
        defaultAvionShouldNotBeFound("moteur.in=" + UPDATED_MOTEUR);
    }

    @Test
    @Transactional
    void getAllAvionsByMoteurIsNullOrNotNull() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where moteur is not null
        defaultAvionShouldBeFound("moteur.specified=true");

        // Get all the avionList where moteur is null
        defaultAvionShouldNotBeFound("moteur.specified=false");
    }

    @Test
    @Transactional
    void getAllAvionsByMoteurContainsSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where moteur contains DEFAULT_MOTEUR
        defaultAvionShouldBeFound("moteur.contains=" + DEFAULT_MOTEUR);

        // Get all the avionList where moteur contains UPDATED_MOTEUR
        defaultAvionShouldNotBeFound("moteur.contains=" + UPDATED_MOTEUR);
    }

    @Test
    @Transactional
    void getAllAvionsByMoteurNotContainsSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where moteur does not contain DEFAULT_MOTEUR
        defaultAvionShouldNotBeFound("moteur.doesNotContain=" + DEFAULT_MOTEUR);

        // Get all the avionList where moteur does not contain UPDATED_MOTEUR
        defaultAvionShouldBeFound("moteur.doesNotContain=" + UPDATED_MOTEUR);
    }

    @Test
    @Transactional
    void getAllAvionsByPuissanceIsEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where puissance equals to DEFAULT_PUISSANCE
        defaultAvionShouldBeFound("puissance.equals=" + DEFAULT_PUISSANCE);

        // Get all the avionList where puissance equals to UPDATED_PUISSANCE
        defaultAvionShouldNotBeFound("puissance.equals=" + UPDATED_PUISSANCE);
    }

    @Test
    @Transactional
    void getAllAvionsByPuissanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where puissance not equals to DEFAULT_PUISSANCE
        defaultAvionShouldNotBeFound("puissance.notEquals=" + DEFAULT_PUISSANCE);

        // Get all the avionList where puissance not equals to UPDATED_PUISSANCE
        defaultAvionShouldBeFound("puissance.notEquals=" + UPDATED_PUISSANCE);
    }

    @Test
    @Transactional
    void getAllAvionsByPuissanceIsInShouldWork() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where puissance in DEFAULT_PUISSANCE or UPDATED_PUISSANCE
        defaultAvionShouldBeFound("puissance.in=" + DEFAULT_PUISSANCE + "," + UPDATED_PUISSANCE);

        // Get all the avionList where puissance equals to UPDATED_PUISSANCE
        defaultAvionShouldNotBeFound("puissance.in=" + UPDATED_PUISSANCE);
    }

    @Test
    @Transactional
    void getAllAvionsByPuissanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where puissance is not null
        defaultAvionShouldBeFound("puissance.specified=true");

        // Get all the avionList where puissance is null
        defaultAvionShouldNotBeFound("puissance.specified=false");
    }

    @Test
    @Transactional
    void getAllAvionsByPuissanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where puissance is greater than or equal to DEFAULT_PUISSANCE
        defaultAvionShouldBeFound("puissance.greaterThanOrEqual=" + DEFAULT_PUISSANCE);

        // Get all the avionList where puissance is greater than or equal to UPDATED_PUISSANCE
        defaultAvionShouldNotBeFound("puissance.greaterThanOrEqual=" + UPDATED_PUISSANCE);
    }

    @Test
    @Transactional
    void getAllAvionsByPuissanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where puissance is less than or equal to DEFAULT_PUISSANCE
        defaultAvionShouldBeFound("puissance.lessThanOrEqual=" + DEFAULT_PUISSANCE);

        // Get all the avionList where puissance is less than or equal to SMALLER_PUISSANCE
        defaultAvionShouldNotBeFound("puissance.lessThanOrEqual=" + SMALLER_PUISSANCE);
    }

    @Test
    @Transactional
    void getAllAvionsByPuissanceIsLessThanSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where puissance is less than DEFAULT_PUISSANCE
        defaultAvionShouldNotBeFound("puissance.lessThan=" + DEFAULT_PUISSANCE);

        // Get all the avionList where puissance is less than UPDATED_PUISSANCE
        defaultAvionShouldBeFound("puissance.lessThan=" + UPDATED_PUISSANCE);
    }

    @Test
    @Transactional
    void getAllAvionsByPuissanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where puissance is greater than DEFAULT_PUISSANCE
        defaultAvionShouldNotBeFound("puissance.greaterThan=" + DEFAULT_PUISSANCE);

        // Get all the avionList where puissance is greater than SMALLER_PUISSANCE
        defaultAvionShouldBeFound("puissance.greaterThan=" + SMALLER_PUISSANCE);
    }

    @Test
    @Transactional
    void getAllAvionsByPlaceIsEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where place equals to DEFAULT_PLACE
        defaultAvionShouldBeFound("place.equals=" + DEFAULT_PLACE);

        // Get all the avionList where place equals to UPDATED_PLACE
        defaultAvionShouldNotBeFound("place.equals=" + UPDATED_PLACE);
    }

    @Test
    @Transactional
    void getAllAvionsByPlaceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where place not equals to DEFAULT_PLACE
        defaultAvionShouldNotBeFound("place.notEquals=" + DEFAULT_PLACE);

        // Get all the avionList where place not equals to UPDATED_PLACE
        defaultAvionShouldBeFound("place.notEquals=" + UPDATED_PLACE);
    }

    @Test
    @Transactional
    void getAllAvionsByPlaceIsInShouldWork() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where place in DEFAULT_PLACE or UPDATED_PLACE
        defaultAvionShouldBeFound("place.in=" + DEFAULT_PLACE + "," + UPDATED_PLACE);

        // Get all the avionList where place equals to UPDATED_PLACE
        defaultAvionShouldNotBeFound("place.in=" + UPDATED_PLACE);
    }

    @Test
    @Transactional
    void getAllAvionsByPlaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where place is not null
        defaultAvionShouldBeFound("place.specified=true");

        // Get all the avionList where place is null
        defaultAvionShouldNotBeFound("place.specified=false");
    }

    @Test
    @Transactional
    void getAllAvionsByPlaceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where place is greater than or equal to DEFAULT_PLACE
        defaultAvionShouldBeFound("place.greaterThanOrEqual=" + DEFAULT_PLACE);

        // Get all the avionList where place is greater than or equal to UPDATED_PLACE
        defaultAvionShouldNotBeFound("place.greaterThanOrEqual=" + UPDATED_PLACE);
    }

    @Test
    @Transactional
    void getAllAvionsByPlaceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where place is less than or equal to DEFAULT_PLACE
        defaultAvionShouldBeFound("place.lessThanOrEqual=" + DEFAULT_PLACE);

        // Get all the avionList where place is less than or equal to SMALLER_PLACE
        defaultAvionShouldNotBeFound("place.lessThanOrEqual=" + SMALLER_PLACE);
    }

    @Test
    @Transactional
    void getAllAvionsByPlaceIsLessThanSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where place is less than DEFAULT_PLACE
        defaultAvionShouldNotBeFound("place.lessThan=" + DEFAULT_PLACE);

        // Get all the avionList where place is less than UPDATED_PLACE
        defaultAvionShouldBeFound("place.lessThan=" + UPDATED_PLACE);
    }

    @Test
    @Transactional
    void getAllAvionsByPlaceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where place is greater than DEFAULT_PLACE
        defaultAvionShouldNotBeFound("place.greaterThan=" + DEFAULT_PLACE);

        // Get all the avionList where place is greater than SMALLER_PLACE
        defaultAvionShouldBeFound("place.greaterThan=" + SMALLER_PLACE);
    }

    @Test
    @Transactional
    void getAllAvionsByAutonomieIsEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where autonomie equals to DEFAULT_AUTONOMIE
        defaultAvionShouldBeFound("autonomie.equals=" + DEFAULT_AUTONOMIE);

        // Get all the avionList where autonomie equals to UPDATED_AUTONOMIE
        defaultAvionShouldNotBeFound("autonomie.equals=" + UPDATED_AUTONOMIE);
    }

    @Test
    @Transactional
    void getAllAvionsByAutonomieIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where autonomie not equals to DEFAULT_AUTONOMIE
        defaultAvionShouldNotBeFound("autonomie.notEquals=" + DEFAULT_AUTONOMIE);

        // Get all the avionList where autonomie not equals to UPDATED_AUTONOMIE
        defaultAvionShouldBeFound("autonomie.notEquals=" + UPDATED_AUTONOMIE);
    }

    @Test
    @Transactional
    void getAllAvionsByAutonomieIsInShouldWork() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where autonomie in DEFAULT_AUTONOMIE or UPDATED_AUTONOMIE
        defaultAvionShouldBeFound("autonomie.in=" + DEFAULT_AUTONOMIE + "," + UPDATED_AUTONOMIE);

        // Get all the avionList where autonomie equals to UPDATED_AUTONOMIE
        defaultAvionShouldNotBeFound("autonomie.in=" + UPDATED_AUTONOMIE);
    }

    @Test
    @Transactional
    void getAllAvionsByAutonomieIsNullOrNotNull() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where autonomie is not null
        defaultAvionShouldBeFound("autonomie.specified=true");

        // Get all the avionList where autonomie is null
        defaultAvionShouldNotBeFound("autonomie.specified=false");
    }

    @Test
    @Transactional
    void getAllAvionsByAutonomieIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where autonomie is greater than or equal to DEFAULT_AUTONOMIE
        defaultAvionShouldBeFound("autonomie.greaterThanOrEqual=" + DEFAULT_AUTONOMIE);

        // Get all the avionList where autonomie is greater than or equal to UPDATED_AUTONOMIE
        defaultAvionShouldNotBeFound("autonomie.greaterThanOrEqual=" + UPDATED_AUTONOMIE);
    }

    @Test
    @Transactional
    void getAllAvionsByAutonomieIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where autonomie is less than or equal to DEFAULT_AUTONOMIE
        defaultAvionShouldBeFound("autonomie.lessThanOrEqual=" + DEFAULT_AUTONOMIE);

        // Get all the avionList where autonomie is less than or equal to SMALLER_AUTONOMIE
        defaultAvionShouldNotBeFound("autonomie.lessThanOrEqual=" + SMALLER_AUTONOMIE);
    }

    @Test
    @Transactional
    void getAllAvionsByAutonomieIsLessThanSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where autonomie is less than DEFAULT_AUTONOMIE
        defaultAvionShouldNotBeFound("autonomie.lessThan=" + DEFAULT_AUTONOMIE);

        // Get all the avionList where autonomie is less than UPDATED_AUTONOMIE
        defaultAvionShouldBeFound("autonomie.lessThan=" + UPDATED_AUTONOMIE);
    }

    @Test
    @Transactional
    void getAllAvionsByAutonomieIsGreaterThanSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where autonomie is greater than DEFAULT_AUTONOMIE
        defaultAvionShouldNotBeFound("autonomie.greaterThan=" + DEFAULT_AUTONOMIE);

        // Get all the avionList where autonomie is greater than SMALLER_AUTONOMIE
        defaultAvionShouldBeFound("autonomie.greaterThan=" + SMALLER_AUTONOMIE);
    }

    @Test
    @Transactional
    void getAllAvionsByUsageIsEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where usage equals to DEFAULT_USAGE
        defaultAvionShouldBeFound("usage.equals=" + DEFAULT_USAGE);

        // Get all the avionList where usage equals to UPDATED_USAGE
        defaultAvionShouldNotBeFound("usage.equals=" + UPDATED_USAGE);
    }

    @Test
    @Transactional
    void getAllAvionsByUsageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where usage not equals to DEFAULT_USAGE
        defaultAvionShouldNotBeFound("usage.notEquals=" + DEFAULT_USAGE);

        // Get all the avionList where usage not equals to UPDATED_USAGE
        defaultAvionShouldBeFound("usage.notEquals=" + UPDATED_USAGE);
    }

    @Test
    @Transactional
    void getAllAvionsByUsageIsInShouldWork() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where usage in DEFAULT_USAGE or UPDATED_USAGE
        defaultAvionShouldBeFound("usage.in=" + DEFAULT_USAGE + "," + UPDATED_USAGE);

        // Get all the avionList where usage equals to UPDATED_USAGE
        defaultAvionShouldNotBeFound("usage.in=" + UPDATED_USAGE);
    }

    @Test
    @Transactional
    void getAllAvionsByUsageIsNullOrNotNull() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where usage is not null
        defaultAvionShouldBeFound("usage.specified=true");

        // Get all the avionList where usage is null
        defaultAvionShouldNotBeFound("usage.specified=false");
    }

    @Test
    @Transactional
    void getAllAvionsByUsageContainsSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where usage contains DEFAULT_USAGE
        defaultAvionShouldBeFound("usage.contains=" + DEFAULT_USAGE);

        // Get all the avionList where usage contains UPDATED_USAGE
        defaultAvionShouldNotBeFound("usage.contains=" + UPDATED_USAGE);
    }

    @Test
    @Transactional
    void getAllAvionsByUsageNotContainsSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where usage does not contain DEFAULT_USAGE
        defaultAvionShouldNotBeFound("usage.doesNotContain=" + DEFAULT_USAGE);

        // Get all the avionList where usage does not contain UPDATED_USAGE
        defaultAvionShouldBeFound("usage.doesNotContain=" + UPDATED_USAGE);
    }

    @Test
    @Transactional
    void getAllAvionsByHeuresIsEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where heures equals to DEFAULT_HEURES
        defaultAvionShouldBeFound("heures.equals=" + DEFAULT_HEURES);

        // Get all the avionList where heures equals to UPDATED_HEURES
        defaultAvionShouldNotBeFound("heures.equals=" + UPDATED_HEURES);
    }

    @Test
    @Transactional
    void getAllAvionsByHeuresIsNotEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where heures not equals to DEFAULT_HEURES
        defaultAvionShouldNotBeFound("heures.notEquals=" + DEFAULT_HEURES);

        // Get all the avionList where heures not equals to UPDATED_HEURES
        defaultAvionShouldBeFound("heures.notEquals=" + UPDATED_HEURES);
    }

    @Test
    @Transactional
    void getAllAvionsByHeuresIsInShouldWork() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where heures in DEFAULT_HEURES or UPDATED_HEURES
        defaultAvionShouldBeFound("heures.in=" + DEFAULT_HEURES + "," + UPDATED_HEURES);

        // Get all the avionList where heures equals to UPDATED_HEURES
        defaultAvionShouldNotBeFound("heures.in=" + UPDATED_HEURES);
    }

    @Test
    @Transactional
    void getAllAvionsByHeuresIsNullOrNotNull() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where heures is not null
        defaultAvionShouldBeFound("heures.specified=true");

        // Get all the avionList where heures is null
        defaultAvionShouldNotBeFound("heures.specified=false");
    }

    @Test
    @Transactional
    void getAllAvionsByHeuresIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where heures is greater than or equal to DEFAULT_HEURES
        defaultAvionShouldBeFound("heures.greaterThanOrEqual=" + DEFAULT_HEURES);

        // Get all the avionList where heures is greater than or equal to UPDATED_HEURES
        defaultAvionShouldNotBeFound("heures.greaterThanOrEqual=" + UPDATED_HEURES);
    }

    @Test
    @Transactional
    void getAllAvionsByHeuresIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where heures is less than or equal to DEFAULT_HEURES
        defaultAvionShouldBeFound("heures.lessThanOrEqual=" + DEFAULT_HEURES);

        // Get all the avionList where heures is less than or equal to SMALLER_HEURES
        defaultAvionShouldNotBeFound("heures.lessThanOrEqual=" + SMALLER_HEURES);
    }

    @Test
    @Transactional
    void getAllAvionsByHeuresIsLessThanSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where heures is less than DEFAULT_HEURES
        defaultAvionShouldNotBeFound("heures.lessThan=" + DEFAULT_HEURES);

        // Get all the avionList where heures is less than UPDATED_HEURES
        defaultAvionShouldBeFound("heures.lessThan=" + UPDATED_HEURES);
    }

    @Test
    @Transactional
    void getAllAvionsByHeuresIsGreaterThanSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        // Get all the avionList where heures is greater than DEFAULT_HEURES
        defaultAvionShouldNotBeFound("heures.greaterThan=" + DEFAULT_HEURES);

        // Get all the avionList where heures is greater than SMALLER_HEURES
        defaultAvionShouldBeFound("heures.greaterThan=" + SMALLER_HEURES);
    }

    @Test
    @Transactional
    void getAllAvionsByAtelierIsEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);
        Atelier atelier;
        if (TestUtil.findAll(em, Atelier.class).isEmpty()) {
            atelier = AtelierResourceIT.createEntity(em);
            em.persist(atelier);
            em.flush();
        } else {
            atelier = TestUtil.findAll(em, Atelier.class).get(0);
        }
        em.persist(atelier);
        em.flush();
        avion.setAtelier(atelier);
        avionRepository.saveAndFlush(avion);
        Long atelierId = atelier.getId();

        // Get all the avionList where atelier equals to atelierId
        defaultAvionShouldBeFound("atelierId.equals=" + atelierId);

        // Get all the avionList where atelier equals to (atelierId + 1)
        defaultAvionShouldNotBeFound("atelierId.equals=" + (atelierId + 1));
    }

    @Test
    @Transactional
    void getAllAvionsByRevisionIsEqualToSomething() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);
        Revision revision;
        if (TestUtil.findAll(em, Revision.class).isEmpty()) {
            revision = RevisionResourceIT.createEntity(em);
            em.persist(revision);
            em.flush();
        } else {
            revision = TestUtil.findAll(em, Revision.class).get(0);
        }
        em.persist(revision);
        em.flush();
        avion.setRevision(revision);
        avionRepository.saveAndFlush(avion);
        Long revisionId = revision.getId();

        // Get all the avionList where revision equals to revisionId
        defaultAvionShouldBeFound("revisionId.equals=" + revisionId);

        // Get all the avionList where revision equals to (revisionId + 1)
        defaultAvionShouldNotBeFound("revisionId.equals=" + (revisionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAvionShouldBeFound(String filter) throws Exception {
        restAvionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avion.getId().intValue())))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].moteur").value(hasItem(DEFAULT_MOTEUR)))
            .andExpect(jsonPath("$.[*].puissance").value(hasItem(DEFAULT_PUISSANCE)))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE)))
            .andExpect(jsonPath("$.[*].autonomie").value(hasItem(DEFAULT_AUTONOMIE.toString())))
            .andExpect(jsonPath("$.[*].usage").value(hasItem(DEFAULT_USAGE)))
            .andExpect(jsonPath("$.[*].heures").value(hasItem(DEFAULT_HEURES.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));

        // Check, that the count call also returns 1
        restAvionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAvionShouldNotBeFound(String filter) throws Exception {
        restAvionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAvionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAvion() throws Exception {
        // Get the avion
        restAvionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAvion() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        int databaseSizeBeforeUpdate = avionRepository.findAll().size();

        // Update the avion
        Avion updatedAvion = avionRepository.findById(avion.getId()).get();
        // Disconnect from session so that the updates on updatedAvion are not directly saved in db
        em.detach(updatedAvion);
        updatedAvion
            .marque(UPDATED_MARQUE)
            .type(UPDATED_TYPE)
            .moteur(UPDATED_MOTEUR)
            .puissance(UPDATED_PUISSANCE)
            .place(UPDATED_PLACE)
            .autonomie(UPDATED_AUTONOMIE)
            .usage(UPDATED_USAGE)
            .heures(UPDATED_HEURES)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        AvionDTO avionDTO = avionMapper.toDto(updatedAvion);

        restAvionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
        Avion testAvion = avionList.get(avionList.size() - 1);
        assertThat(testAvion.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testAvion.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAvion.getMoteur()).isEqualTo(UPDATED_MOTEUR);
        assertThat(testAvion.getPuissance()).isEqualTo(UPDATED_PUISSANCE);
        assertThat(testAvion.getPlace()).isEqualTo(UPDATED_PLACE);
        assertThat(testAvion.getAutonomie()).isEqualTo(UPDATED_AUTONOMIE);
        assertThat(testAvion.getUsage()).isEqualTo(UPDATED_USAGE);
        assertThat(testAvion.getHeures()).isEqualTo(UPDATED_HEURES);
        assertThat(testAvion.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testAvion.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingAvion() throws Exception {
        int databaseSizeBeforeUpdate = avionRepository.findAll().size();
        avion.setId(count.incrementAndGet());

        // Create the Avion
        AvionDTO avionDTO = avionMapper.toDto(avion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, avionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAvion() throws Exception {
        int databaseSizeBeforeUpdate = avionRepository.findAll().size();
        avion.setId(count.incrementAndGet());

        // Create the Avion
        AvionDTO avionDTO = avionMapper.toDto(avion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(avionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAvion() throws Exception {
        int databaseSizeBeforeUpdate = avionRepository.findAll().size();
        avion.setId(count.incrementAndGet());

        // Create the Avion
        AvionDTO avionDTO = avionMapper.toDto(avion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAvionWithPatch() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        int databaseSizeBeforeUpdate = avionRepository.findAll().size();

        // Update the avion using partial update
        Avion partialUpdatedAvion = new Avion();
        partialUpdatedAvion.setId(avion.getId());

        partialUpdatedAvion.marque(UPDATED_MARQUE).puissance(UPDATED_PUISSANCE).autonomie(UPDATED_AUTONOMIE).usage(UPDATED_USAGE);

        restAvionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvion))
            )
            .andExpect(status().isOk());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
        Avion testAvion = avionList.get(avionList.size() - 1);
        assertThat(testAvion.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testAvion.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAvion.getMoteur()).isEqualTo(DEFAULT_MOTEUR);
        assertThat(testAvion.getPuissance()).isEqualTo(UPDATED_PUISSANCE);
        assertThat(testAvion.getPlace()).isEqualTo(DEFAULT_PLACE);
        assertThat(testAvion.getAutonomie()).isEqualTo(UPDATED_AUTONOMIE);
        assertThat(testAvion.getUsage()).isEqualTo(UPDATED_USAGE);
        assertThat(testAvion.getHeures()).isEqualTo(DEFAULT_HEURES);
        assertThat(testAvion.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testAvion.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateAvionWithPatch() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        int databaseSizeBeforeUpdate = avionRepository.findAll().size();

        // Update the avion using partial update
        Avion partialUpdatedAvion = new Avion();
        partialUpdatedAvion.setId(avion.getId());

        partialUpdatedAvion
            .marque(UPDATED_MARQUE)
            .type(UPDATED_TYPE)
            .moteur(UPDATED_MOTEUR)
            .puissance(UPDATED_PUISSANCE)
            .place(UPDATED_PLACE)
            .autonomie(UPDATED_AUTONOMIE)
            .usage(UPDATED_USAGE)
            .heures(UPDATED_HEURES)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restAvionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAvion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAvion))
            )
            .andExpect(status().isOk());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
        Avion testAvion = avionList.get(avionList.size() - 1);
        assertThat(testAvion.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testAvion.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAvion.getMoteur()).isEqualTo(UPDATED_MOTEUR);
        assertThat(testAvion.getPuissance()).isEqualTo(UPDATED_PUISSANCE);
        assertThat(testAvion.getPlace()).isEqualTo(UPDATED_PLACE);
        assertThat(testAvion.getAutonomie()).isEqualTo(UPDATED_AUTONOMIE);
        assertThat(testAvion.getUsage()).isEqualTo(UPDATED_USAGE);
        assertThat(testAvion.getHeures()).isEqualTo(UPDATED_HEURES);
        assertThat(testAvion.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testAvion.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingAvion() throws Exception {
        int databaseSizeBeforeUpdate = avionRepository.findAll().size();
        avion.setId(count.incrementAndGet());

        // Create the Avion
        AvionDTO avionDTO = avionMapper.toDto(avion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAvionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, avionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAvion() throws Exception {
        int databaseSizeBeforeUpdate = avionRepository.findAll().size();
        avion.setId(count.incrementAndGet());

        // Create the Avion
        AvionDTO avionDTO = avionMapper.toDto(avion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(avionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAvion() throws Exception {
        int databaseSizeBeforeUpdate = avionRepository.findAll().size();
        avion.setId(count.incrementAndGet());

        // Create the Avion
        AvionDTO avionDTO = avionMapper.toDto(avion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAvionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(avionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Avion in the database
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAvion() throws Exception {
        // Initialize the database
        avionRepository.saveAndFlush(avion);

        int databaseSizeBeforeDelete = avionRepository.findAll().size();

        // Delete the avion
        restAvionMockMvc
            .perform(delete(ENTITY_API_URL_ID, avion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Avion> avionList = avionRepository.findAll();
        assertThat(avionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
