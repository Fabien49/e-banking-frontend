package com.fabienit.aeroclubpassion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fabienit.aeroclubpassion.IntegrationTest;
import com.fabienit.aeroclubpassion.domain.Aeroclub;
import com.fabienit.aeroclubpassion.repository.AeroclubRepository;
import com.fabienit.aeroclubpassion.service.criteria.AeroclubCriteria;
import com.fabienit.aeroclubpassion.service.dto.AeroclubDTO;
import com.fabienit.aeroclubpassion.service.mapper.AeroclubMapper;
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
 * Integration tests for the {@link AeroclubResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AeroclubResourceIT {

    private static final String DEFAULT_OACI = "AAAAAAAAAA";
    private static final String UPDATED_OACI = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_COMMUNE = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/aeroclubs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AeroclubRepository aeroclubRepository;

    @Autowired
    private AeroclubMapper aeroclubMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAeroclubMockMvc;

    private Aeroclub aeroclub;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aeroclub createEntity(EntityManager em) {
        Aeroclub aeroclub = new Aeroclub()
            .oaci(DEFAULT_OACI)
            .nom(DEFAULT_NOM)
            .type(DEFAULT_TYPE)
            .telephone(DEFAULT_TELEPHONE)
            .mail(DEFAULT_MAIL)
            .adresse(DEFAULT_ADRESSE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .commune(DEFAULT_COMMUNE);
        return aeroclub;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aeroclub createUpdatedEntity(EntityManager em) {
        Aeroclub aeroclub = new Aeroclub()
            .oaci(UPDATED_OACI)
            .nom(UPDATED_NOM)
            .type(UPDATED_TYPE)
            .telephone(UPDATED_TELEPHONE)
            .mail(UPDATED_MAIL)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .commune(UPDATED_COMMUNE);
        return aeroclub;
    }

    @BeforeEach
    public void initTest() {
        aeroclub = createEntity(em);
    }

    @Test
    @Transactional
    void createAeroclub() throws Exception {
        int databaseSizeBeforeCreate = aeroclubRepository.findAll().size();
        // Create the Aeroclub
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(aeroclub);
        restAeroclubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeroclubDTO)))
            .andExpect(status().isCreated());

        // Validate the Aeroclub in the database
        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeCreate + 1);
        Aeroclub testAeroclub = aeroclubList.get(aeroclubList.size() - 1);
        assertThat(testAeroclub.getOaci()).isEqualTo(DEFAULT_OACI);
        assertThat(testAeroclub.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAeroclub.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAeroclub.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testAeroclub.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testAeroclub.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testAeroclub.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testAeroclub.getCommune()).isEqualTo(DEFAULT_COMMUNE);
    }

    @Test
    @Transactional
    void createAeroclubWithExistingId() throws Exception {
        // Create the Aeroclub with an existing ID
        aeroclub.setId(1L);
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(aeroclub);

        int databaseSizeBeforeCreate = aeroclubRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAeroclubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeroclubDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Aeroclub in the database
        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOaciIsRequired() throws Exception {
        int databaseSizeBeforeTest = aeroclubRepository.findAll().size();
        // set the field null
        aeroclub.setOaci(null);

        // Create the Aeroclub, which fails.
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(aeroclub);

        restAeroclubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeroclubDTO)))
            .andExpect(status().isBadRequest());

        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = aeroclubRepository.findAll().size();
        // set the field null
        aeroclub.setNom(null);

        // Create the Aeroclub, which fails.
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(aeroclub);

        restAeroclubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeroclubDTO)))
            .andExpect(status().isBadRequest());

        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = aeroclubRepository.findAll().size();
        // set the field null
        aeroclub.setType(null);

        // Create the Aeroclub, which fails.
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(aeroclub);

        restAeroclubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeroclubDTO)))
            .andExpect(status().isBadRequest());

        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = aeroclubRepository.findAll().size();
        // set the field null
        aeroclub.setTelephone(null);

        // Create the Aeroclub, which fails.
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(aeroclub);

        restAeroclubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeroclubDTO)))
            .andExpect(status().isBadRequest());

        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMailIsRequired() throws Exception {
        int databaseSizeBeforeTest = aeroclubRepository.findAll().size();
        // set the field null
        aeroclub.setMail(null);

        // Create the Aeroclub, which fails.
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(aeroclub);

        restAeroclubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeroclubDTO)))
            .andExpect(status().isBadRequest());

        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = aeroclubRepository.findAll().size();
        // set the field null
        aeroclub.setAdresse(null);

        // Create the Aeroclub, which fails.
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(aeroclub);

        restAeroclubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeroclubDTO)))
            .andExpect(status().isBadRequest());

        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodePostalIsRequired() throws Exception {
        int databaseSizeBeforeTest = aeroclubRepository.findAll().size();
        // set the field null
        aeroclub.setCodePostal(null);

        // Create the Aeroclub, which fails.
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(aeroclub);

        restAeroclubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeroclubDTO)))
            .andExpect(status().isBadRequest());

        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCommuneIsRequired() throws Exception {
        int databaseSizeBeforeTest = aeroclubRepository.findAll().size();
        // set the field null
        aeroclub.setCommune(null);

        // Create the Aeroclub, which fails.
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(aeroclub);

        restAeroclubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeroclubDTO)))
            .andExpect(status().isBadRequest());

        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAeroclubs() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList
        restAeroclubMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aeroclub.getId().intValue())))
            .andExpect(jsonPath("$.[*].oaci").value(hasItem(DEFAULT_OACI)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].commune").value(hasItem(DEFAULT_COMMUNE)));
    }

    @Test
    @Transactional
    void getAeroclub() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get the aeroclub
        restAeroclubMockMvc
            .perform(get(ENTITY_API_URL_ID, aeroclub.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aeroclub.getId().intValue()))
            .andExpect(jsonPath("$.oaci").value(DEFAULT_OACI))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.commune").value(DEFAULT_COMMUNE));
    }

    @Test
    @Transactional
    void getAeroclubsByIdFiltering() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        Long id = aeroclub.getId();

        defaultAeroclubShouldBeFound("id.equals=" + id);
        defaultAeroclubShouldNotBeFound("id.notEquals=" + id);

        defaultAeroclubShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAeroclubShouldNotBeFound("id.greaterThan=" + id);

        defaultAeroclubShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAeroclubShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAeroclubsByOaciIsEqualToSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where oaci equals to DEFAULT_OACI
        defaultAeroclubShouldBeFound("oaci.equals=" + DEFAULT_OACI);

        // Get all the aeroclubList where oaci equals to UPDATED_OACI
        defaultAeroclubShouldNotBeFound("oaci.equals=" + UPDATED_OACI);
    }

    @Test
    @Transactional
    void getAllAeroclubsByOaciIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where oaci not equals to DEFAULT_OACI
        defaultAeroclubShouldNotBeFound("oaci.notEquals=" + DEFAULT_OACI);

        // Get all the aeroclubList where oaci not equals to UPDATED_OACI
        defaultAeroclubShouldBeFound("oaci.notEquals=" + UPDATED_OACI);
    }

    @Test
    @Transactional
    void getAllAeroclubsByOaciIsInShouldWork() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where oaci in DEFAULT_OACI or UPDATED_OACI
        defaultAeroclubShouldBeFound("oaci.in=" + DEFAULT_OACI + "," + UPDATED_OACI);

        // Get all the aeroclubList where oaci equals to UPDATED_OACI
        defaultAeroclubShouldNotBeFound("oaci.in=" + UPDATED_OACI);
    }

    @Test
    @Transactional
    void getAllAeroclubsByOaciIsNullOrNotNull() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where oaci is not null
        defaultAeroclubShouldBeFound("oaci.specified=true");

        // Get all the aeroclubList where oaci is null
        defaultAeroclubShouldNotBeFound("oaci.specified=false");
    }

    @Test
    @Transactional
    void getAllAeroclubsByOaciContainsSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where oaci contains DEFAULT_OACI
        defaultAeroclubShouldBeFound("oaci.contains=" + DEFAULT_OACI);

        // Get all the aeroclubList where oaci contains UPDATED_OACI
        defaultAeroclubShouldNotBeFound("oaci.contains=" + UPDATED_OACI);
    }

    @Test
    @Transactional
    void getAllAeroclubsByOaciNotContainsSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where oaci does not contain DEFAULT_OACI
        defaultAeroclubShouldNotBeFound("oaci.doesNotContain=" + DEFAULT_OACI);

        // Get all the aeroclubList where oaci does not contain UPDATED_OACI
        defaultAeroclubShouldBeFound("oaci.doesNotContain=" + UPDATED_OACI);
    }

    @Test
    @Transactional
    void getAllAeroclubsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where nom equals to DEFAULT_NOM
        defaultAeroclubShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the aeroclubList where nom equals to UPDATED_NOM
        defaultAeroclubShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    void getAllAeroclubsByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where nom not equals to DEFAULT_NOM
        defaultAeroclubShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the aeroclubList where nom not equals to UPDATED_NOM
        defaultAeroclubShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    void getAllAeroclubsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultAeroclubShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the aeroclubList where nom equals to UPDATED_NOM
        defaultAeroclubShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    void getAllAeroclubsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where nom is not null
        defaultAeroclubShouldBeFound("nom.specified=true");

        // Get all the aeroclubList where nom is null
        defaultAeroclubShouldNotBeFound("nom.specified=false");
    }

    @Test
    @Transactional
    void getAllAeroclubsByNomContainsSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where nom contains DEFAULT_NOM
        defaultAeroclubShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the aeroclubList where nom contains UPDATED_NOM
        defaultAeroclubShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    void getAllAeroclubsByNomNotContainsSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where nom does not contain DEFAULT_NOM
        defaultAeroclubShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the aeroclubList where nom does not contain UPDATED_NOM
        defaultAeroclubShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    void getAllAeroclubsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where type equals to DEFAULT_TYPE
        defaultAeroclubShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the aeroclubList where type equals to UPDATED_TYPE
        defaultAeroclubShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where type not equals to DEFAULT_TYPE
        defaultAeroclubShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the aeroclubList where type not equals to UPDATED_TYPE
        defaultAeroclubShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultAeroclubShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the aeroclubList where type equals to UPDATED_TYPE
        defaultAeroclubShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where type is not null
        defaultAeroclubShouldBeFound("type.specified=true");

        // Get all the aeroclubList where type is null
        defaultAeroclubShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllAeroclubsByTypeContainsSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where type contains DEFAULT_TYPE
        defaultAeroclubShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the aeroclubList where type contains UPDATED_TYPE
        defaultAeroclubShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where type does not contain DEFAULT_TYPE
        defaultAeroclubShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the aeroclubList where type does not contain UPDATED_TYPE
        defaultAeroclubShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where telephone equals to DEFAULT_TELEPHONE
        defaultAeroclubShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the aeroclubList where telephone equals to UPDATED_TELEPHONE
        defaultAeroclubShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where telephone not equals to DEFAULT_TELEPHONE
        defaultAeroclubShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the aeroclubList where telephone not equals to UPDATED_TELEPHONE
        defaultAeroclubShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultAeroclubShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the aeroclubList where telephone equals to UPDATED_TELEPHONE
        defaultAeroclubShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where telephone is not null
        defaultAeroclubShouldBeFound("telephone.specified=true");

        // Get all the aeroclubList where telephone is null
        defaultAeroclubShouldNotBeFound("telephone.specified=false");
    }

    @Test
    @Transactional
    void getAllAeroclubsByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where telephone contains DEFAULT_TELEPHONE
        defaultAeroclubShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the aeroclubList where telephone contains UPDATED_TELEPHONE
        defaultAeroclubShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where telephone does not contain DEFAULT_TELEPHONE
        defaultAeroclubShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the aeroclubList where telephone does not contain UPDATED_TELEPHONE
        defaultAeroclubShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByMailIsEqualToSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where mail equals to DEFAULT_MAIL
        defaultAeroclubShouldBeFound("mail.equals=" + DEFAULT_MAIL);

        // Get all the aeroclubList where mail equals to UPDATED_MAIL
        defaultAeroclubShouldNotBeFound("mail.equals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllAeroclubsByMailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where mail not equals to DEFAULT_MAIL
        defaultAeroclubShouldNotBeFound("mail.notEquals=" + DEFAULT_MAIL);

        // Get all the aeroclubList where mail not equals to UPDATED_MAIL
        defaultAeroclubShouldBeFound("mail.notEquals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllAeroclubsByMailIsInShouldWork() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where mail in DEFAULT_MAIL or UPDATED_MAIL
        defaultAeroclubShouldBeFound("mail.in=" + DEFAULT_MAIL + "," + UPDATED_MAIL);

        // Get all the aeroclubList where mail equals to UPDATED_MAIL
        defaultAeroclubShouldNotBeFound("mail.in=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllAeroclubsByMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where mail is not null
        defaultAeroclubShouldBeFound("mail.specified=true");

        // Get all the aeroclubList where mail is null
        defaultAeroclubShouldNotBeFound("mail.specified=false");
    }

    @Test
    @Transactional
    void getAllAeroclubsByMailContainsSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where mail contains DEFAULT_MAIL
        defaultAeroclubShouldBeFound("mail.contains=" + DEFAULT_MAIL);

        // Get all the aeroclubList where mail contains UPDATED_MAIL
        defaultAeroclubShouldNotBeFound("mail.contains=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllAeroclubsByMailNotContainsSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where mail does not contain DEFAULT_MAIL
        defaultAeroclubShouldNotBeFound("mail.doesNotContain=" + DEFAULT_MAIL);

        // Get all the aeroclubList where mail does not contain UPDATED_MAIL
        defaultAeroclubShouldBeFound("mail.doesNotContain=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllAeroclubsByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where adresse equals to DEFAULT_ADRESSE
        defaultAeroclubShouldBeFound("adresse.equals=" + DEFAULT_ADRESSE);

        // Get all the aeroclubList where adresse equals to UPDATED_ADRESSE
        defaultAeroclubShouldNotBeFound("adresse.equals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByAdresseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where adresse not equals to DEFAULT_ADRESSE
        defaultAeroclubShouldNotBeFound("adresse.notEquals=" + DEFAULT_ADRESSE);

        // Get all the aeroclubList where adresse not equals to UPDATED_ADRESSE
        defaultAeroclubShouldBeFound("adresse.notEquals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where adresse in DEFAULT_ADRESSE or UPDATED_ADRESSE
        defaultAeroclubShouldBeFound("adresse.in=" + DEFAULT_ADRESSE + "," + UPDATED_ADRESSE);

        // Get all the aeroclubList where adresse equals to UPDATED_ADRESSE
        defaultAeroclubShouldNotBeFound("adresse.in=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where adresse is not null
        defaultAeroclubShouldBeFound("adresse.specified=true");

        // Get all the aeroclubList where adresse is null
        defaultAeroclubShouldNotBeFound("adresse.specified=false");
    }

    @Test
    @Transactional
    void getAllAeroclubsByAdresseContainsSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where adresse contains DEFAULT_ADRESSE
        defaultAeroclubShouldBeFound("adresse.contains=" + DEFAULT_ADRESSE);

        // Get all the aeroclubList where adresse contains UPDATED_ADRESSE
        defaultAeroclubShouldNotBeFound("adresse.contains=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where adresse does not contain DEFAULT_ADRESSE
        defaultAeroclubShouldNotBeFound("adresse.doesNotContain=" + DEFAULT_ADRESSE);

        // Get all the aeroclubList where adresse does not contain UPDATED_ADRESSE
        defaultAeroclubShouldBeFound("adresse.doesNotContain=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByCodePostalIsEqualToSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where codePostal equals to DEFAULT_CODE_POSTAL
        defaultAeroclubShouldBeFound("codePostal.equals=" + DEFAULT_CODE_POSTAL);

        // Get all the aeroclubList where codePostal equals to UPDATED_CODE_POSTAL
        defaultAeroclubShouldNotBeFound("codePostal.equals=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    void getAllAeroclubsByCodePostalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where codePostal not equals to DEFAULT_CODE_POSTAL
        defaultAeroclubShouldNotBeFound("codePostal.notEquals=" + DEFAULT_CODE_POSTAL);

        // Get all the aeroclubList where codePostal not equals to UPDATED_CODE_POSTAL
        defaultAeroclubShouldBeFound("codePostal.notEquals=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    void getAllAeroclubsByCodePostalIsInShouldWork() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where codePostal in DEFAULT_CODE_POSTAL or UPDATED_CODE_POSTAL
        defaultAeroclubShouldBeFound("codePostal.in=" + DEFAULT_CODE_POSTAL + "," + UPDATED_CODE_POSTAL);

        // Get all the aeroclubList where codePostal equals to UPDATED_CODE_POSTAL
        defaultAeroclubShouldNotBeFound("codePostal.in=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    void getAllAeroclubsByCodePostalIsNullOrNotNull() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where codePostal is not null
        defaultAeroclubShouldBeFound("codePostal.specified=true");

        // Get all the aeroclubList where codePostal is null
        defaultAeroclubShouldNotBeFound("codePostal.specified=false");
    }

    @Test
    @Transactional
    void getAllAeroclubsByCodePostalContainsSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where codePostal contains DEFAULT_CODE_POSTAL
        defaultAeroclubShouldBeFound("codePostal.contains=" + DEFAULT_CODE_POSTAL);

        // Get all the aeroclubList where codePostal contains UPDATED_CODE_POSTAL
        defaultAeroclubShouldNotBeFound("codePostal.contains=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    void getAllAeroclubsByCodePostalNotContainsSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where codePostal does not contain DEFAULT_CODE_POSTAL
        defaultAeroclubShouldNotBeFound("codePostal.doesNotContain=" + DEFAULT_CODE_POSTAL);

        // Get all the aeroclubList where codePostal does not contain UPDATED_CODE_POSTAL
        defaultAeroclubShouldBeFound("codePostal.doesNotContain=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    void getAllAeroclubsByCommuneIsEqualToSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where commune equals to DEFAULT_COMMUNE
        defaultAeroclubShouldBeFound("commune.equals=" + DEFAULT_COMMUNE);

        // Get all the aeroclubList where commune equals to UPDATED_COMMUNE
        defaultAeroclubShouldNotBeFound("commune.equals=" + UPDATED_COMMUNE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByCommuneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where commune not equals to DEFAULT_COMMUNE
        defaultAeroclubShouldNotBeFound("commune.notEquals=" + DEFAULT_COMMUNE);

        // Get all the aeroclubList where commune not equals to UPDATED_COMMUNE
        defaultAeroclubShouldBeFound("commune.notEquals=" + UPDATED_COMMUNE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByCommuneIsInShouldWork() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where commune in DEFAULT_COMMUNE or UPDATED_COMMUNE
        defaultAeroclubShouldBeFound("commune.in=" + DEFAULT_COMMUNE + "," + UPDATED_COMMUNE);

        // Get all the aeroclubList where commune equals to UPDATED_COMMUNE
        defaultAeroclubShouldNotBeFound("commune.in=" + UPDATED_COMMUNE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByCommuneIsNullOrNotNull() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where commune is not null
        defaultAeroclubShouldBeFound("commune.specified=true");

        // Get all the aeroclubList where commune is null
        defaultAeroclubShouldNotBeFound("commune.specified=false");
    }

    @Test
    @Transactional
    void getAllAeroclubsByCommuneContainsSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where commune contains DEFAULT_COMMUNE
        defaultAeroclubShouldBeFound("commune.contains=" + DEFAULT_COMMUNE);

        // Get all the aeroclubList where commune contains UPDATED_COMMUNE
        defaultAeroclubShouldNotBeFound("commune.contains=" + UPDATED_COMMUNE);
    }

    @Test
    @Transactional
    void getAllAeroclubsByCommuneNotContainsSomething() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        // Get all the aeroclubList where commune does not contain DEFAULT_COMMUNE
        defaultAeroclubShouldNotBeFound("commune.doesNotContain=" + DEFAULT_COMMUNE);

        // Get all the aeroclubList where commune does not contain UPDATED_COMMUNE
        defaultAeroclubShouldBeFound("commune.doesNotContain=" + UPDATED_COMMUNE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAeroclubShouldBeFound(String filter) throws Exception {
        restAeroclubMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aeroclub.getId().intValue())))
            .andExpect(jsonPath("$.[*].oaci").value(hasItem(DEFAULT_OACI)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].commune").value(hasItem(DEFAULT_COMMUNE)));

        // Check, that the count call also returns 1
        restAeroclubMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAeroclubShouldNotBeFound(String filter) throws Exception {
        restAeroclubMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAeroclubMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAeroclub() throws Exception {
        // Get the aeroclub
        restAeroclubMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAeroclub() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        int databaseSizeBeforeUpdate = aeroclubRepository.findAll().size();

        // Update the aeroclub
        Aeroclub updatedAeroclub = aeroclubRepository.findById(aeroclub.getId()).get();
        // Disconnect from session so that the updates on updatedAeroclub are not directly saved in db
        em.detach(updatedAeroclub);
        updatedAeroclub
            .oaci(UPDATED_OACI)
            .nom(UPDATED_NOM)
            .type(UPDATED_TYPE)
            .telephone(UPDATED_TELEPHONE)
            .mail(UPDATED_MAIL)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .commune(UPDATED_COMMUNE);
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(updatedAeroclub);

        restAeroclubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aeroclubDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aeroclubDTO))
            )
            .andExpect(status().isOk());

        // Validate the Aeroclub in the database
        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeUpdate);
        Aeroclub testAeroclub = aeroclubList.get(aeroclubList.size() - 1);
        assertThat(testAeroclub.getOaci()).isEqualTo(UPDATED_OACI);
        assertThat(testAeroclub.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAeroclub.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAeroclub.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testAeroclub.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testAeroclub.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testAeroclub.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAeroclub.getCommune()).isEqualTo(UPDATED_COMMUNE);
    }

    @Test
    @Transactional
    void putNonExistingAeroclub() throws Exception {
        int databaseSizeBeforeUpdate = aeroclubRepository.findAll().size();
        aeroclub.setId(count.incrementAndGet());

        // Create the Aeroclub
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(aeroclub);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAeroclubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aeroclubDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aeroclubDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aeroclub in the database
        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAeroclub() throws Exception {
        int databaseSizeBeforeUpdate = aeroclubRepository.findAll().size();
        aeroclub.setId(count.incrementAndGet());

        // Create the Aeroclub
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(aeroclub);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAeroclubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aeroclubDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aeroclub in the database
        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAeroclub() throws Exception {
        int databaseSizeBeforeUpdate = aeroclubRepository.findAll().size();
        aeroclub.setId(count.incrementAndGet());

        // Create the Aeroclub
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(aeroclub);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAeroclubMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aeroclubDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aeroclub in the database
        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAeroclubWithPatch() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        int databaseSizeBeforeUpdate = aeroclubRepository.findAll().size();

        // Update the aeroclub using partial update
        Aeroclub partialUpdatedAeroclub = new Aeroclub();
        partialUpdatedAeroclub.setId(aeroclub.getId());

        partialUpdatedAeroclub.nom(UPDATED_NOM).telephone(UPDATED_TELEPHONE).adresse(UPDATED_ADRESSE);

        restAeroclubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAeroclub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAeroclub))
            )
            .andExpect(status().isOk());

        // Validate the Aeroclub in the database
        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeUpdate);
        Aeroclub testAeroclub = aeroclubList.get(aeroclubList.size() - 1);
        assertThat(testAeroclub.getOaci()).isEqualTo(DEFAULT_OACI);
        assertThat(testAeroclub.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAeroclub.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAeroclub.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testAeroclub.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testAeroclub.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testAeroclub.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testAeroclub.getCommune()).isEqualTo(DEFAULT_COMMUNE);
    }

    @Test
    @Transactional
    void fullUpdateAeroclubWithPatch() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        int databaseSizeBeforeUpdate = aeroclubRepository.findAll().size();

        // Update the aeroclub using partial update
        Aeroclub partialUpdatedAeroclub = new Aeroclub();
        partialUpdatedAeroclub.setId(aeroclub.getId());

        partialUpdatedAeroclub
            .oaci(UPDATED_OACI)
            .nom(UPDATED_NOM)
            .type(UPDATED_TYPE)
            .telephone(UPDATED_TELEPHONE)
            .mail(UPDATED_MAIL)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .commune(UPDATED_COMMUNE);

        restAeroclubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAeroclub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAeroclub))
            )
            .andExpect(status().isOk());

        // Validate the Aeroclub in the database
        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeUpdate);
        Aeroclub testAeroclub = aeroclubList.get(aeroclubList.size() - 1);
        assertThat(testAeroclub.getOaci()).isEqualTo(UPDATED_OACI);
        assertThat(testAeroclub.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAeroclub.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAeroclub.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testAeroclub.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testAeroclub.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testAeroclub.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testAeroclub.getCommune()).isEqualTo(UPDATED_COMMUNE);
    }

    @Test
    @Transactional
    void patchNonExistingAeroclub() throws Exception {
        int databaseSizeBeforeUpdate = aeroclubRepository.findAll().size();
        aeroclub.setId(count.incrementAndGet());

        // Create the Aeroclub
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(aeroclub);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAeroclubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aeroclubDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aeroclubDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aeroclub in the database
        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAeroclub() throws Exception {
        int databaseSizeBeforeUpdate = aeroclubRepository.findAll().size();
        aeroclub.setId(count.incrementAndGet());

        // Create the Aeroclub
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(aeroclub);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAeroclubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aeroclubDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Aeroclub in the database
        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAeroclub() throws Exception {
        int databaseSizeBeforeUpdate = aeroclubRepository.findAll().size();
        aeroclub.setId(count.incrementAndGet());

        // Create the Aeroclub
        AeroclubDTO aeroclubDTO = aeroclubMapper.toDto(aeroclub);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAeroclubMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(aeroclubDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Aeroclub in the database
        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAeroclub() throws Exception {
        // Initialize the database
        aeroclubRepository.saveAndFlush(aeroclub);

        int databaseSizeBeforeDelete = aeroclubRepository.findAll().size();

        // Delete the aeroclub
        restAeroclubMockMvc
            .perform(delete(ENTITY_API_URL_ID, aeroclub.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Aeroclub> aeroclubList = aeroclubRepository.findAll();
        assertThat(aeroclubList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
