package com.fabienit.aeroclubpassion.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fabienit.aeroclubpassion.IntegrationTest;
import com.fabienit.aeroclubpassion.domain.User;
import com.fabienit.aeroclubpassion.domain.UserRegistered;
import com.fabienit.aeroclubpassion.repository.UserRegisteredRepository;
import com.fabienit.aeroclubpassion.service.criteria.UserRegisteredCriteria;
import com.fabienit.aeroclubpassion.service.dto.UserRegisteredDTO;
import com.fabienit.aeroclubpassion.service.mapper.UserRegisteredMapper;
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

/**
 * Integration tests for the {@link UserRegisteredResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserRegisteredResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTAL = "AAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBB";

    private static final String DEFAULT_COMMUNE = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNE = "BBBBBBBBBB";

    private static final Duration DEFAULT_HEURE_VOL = Duration.ofHours(6);
    private static final Duration UPDATED_HEURE_VOL = Duration.ofHours(12);
    private static final Duration SMALLER_HEURE_VOL = Duration.ofHours(5);

    private static final String ENTITY_API_URL = "/api/user-registereds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserRegisteredRepository userRegisteredRepository;

    @Autowired
    private UserRegisteredMapper userRegisteredMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserRegisteredMockMvc;

    private UserRegistered userRegistered;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRegistered createEntity(EntityManager em) {
        UserRegistered userRegistered = new UserRegistered()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .telephone(DEFAULT_TELEPHONE)
            .mail(DEFAULT_MAIL)
            .adresse(DEFAULT_ADRESSE)
            .codePostal(DEFAULT_CODE_POSTAL)
            .commune(DEFAULT_COMMUNE)
            .heureVol(DEFAULT_HEURE_VOL);
        return userRegistered;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserRegistered createUpdatedEntity(EntityManager em) {
        UserRegistered userRegistered = new UserRegistered()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .mail(UPDATED_MAIL)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .commune(UPDATED_COMMUNE)
            .heureVol(UPDATED_HEURE_VOL);
        return userRegistered;
    }

    @BeforeEach
    public void initTest() {
        userRegistered = createEntity(em);
    }

    @Test
    @Transactional
    void createUserRegistered() throws Exception {
        int databaseSizeBeforeCreate = userRegisteredRepository.findAll().size();
        // Create the UserRegistered
        UserRegisteredDTO userRegisteredDTO = userRegisteredMapper.toDto(userRegistered);
        restUserRegisteredMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userRegisteredDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserRegistered in the database
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeCreate + 1);
        UserRegistered testUserRegistered = userRegisteredList.get(userRegisteredList.size() - 1);
        assertThat(testUserRegistered.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testUserRegistered.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testUserRegistered.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testUserRegistered.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testUserRegistered.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testUserRegistered.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testUserRegistered.getCommune()).isEqualTo(DEFAULT_COMMUNE);
        assertThat(testUserRegistered.getHeureVol()).isEqualTo(DEFAULT_HEURE_VOL);
    }

    @Test
    @Transactional
    void createUserRegisteredWithExistingId() throws Exception {
        // Create the UserRegistered with an existing ID
        userRegistered.setId(1L);
        UserRegisteredDTO userRegisteredDTO = userRegisteredMapper.toDto(userRegistered);

        int databaseSizeBeforeCreate = userRegisteredRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserRegisteredMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userRegisteredDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRegistered in the database
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserRegistereds() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList
        restUserRegisteredMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRegistered.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].commune").value(hasItem(DEFAULT_COMMUNE)))
            .andExpect(jsonPath("$.[*].heureVol").value(hasItem(DEFAULT_HEURE_VOL.toString())));
    }

    @Test
    @Transactional
    void getUserRegistered() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get the userRegistered
        restUserRegisteredMockMvc
            .perform(get(ENTITY_API_URL_ID, userRegistered.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userRegistered.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.commune").value(DEFAULT_COMMUNE))
            .andExpect(jsonPath("$.heureVol").value(DEFAULT_HEURE_VOL.toString()));
    }

    @Test
    @Transactional
    void getUserRegisteredsByIdFiltering() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        Long id = userRegistered.getId();

        defaultUserRegisteredShouldBeFound("id.equals=" + id);
        defaultUserRegisteredShouldNotBeFound("id.notEquals=" + id);

        defaultUserRegisteredShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserRegisteredShouldNotBeFound("id.greaterThan=" + id);

        defaultUserRegisteredShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserRegisteredShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where nom equals to DEFAULT_NOM
        defaultUserRegisteredShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the userRegisteredList where nom equals to UPDATED_NOM
        defaultUserRegisteredShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where nom not equals to DEFAULT_NOM
        defaultUserRegisteredShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the userRegisteredList where nom not equals to UPDATED_NOM
        defaultUserRegisteredShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultUserRegisteredShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the userRegisteredList where nom equals to UPDATED_NOM
        defaultUserRegisteredShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where nom is not null
        defaultUserRegisteredShouldBeFound("nom.specified=true");

        // Get all the userRegisteredList where nom is null
        defaultUserRegisteredShouldNotBeFound("nom.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByNomContainsSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where nom contains DEFAULT_NOM
        defaultUserRegisteredShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the userRegisteredList where nom contains UPDATED_NOM
        defaultUserRegisteredShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByNomNotContainsSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where nom does not contain DEFAULT_NOM
        defaultUserRegisteredShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the userRegisteredList where nom does not contain UPDATED_NOM
        defaultUserRegisteredShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByPrenomIsEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where prenom equals to DEFAULT_PRENOM
        defaultUserRegisteredShouldBeFound("prenom.equals=" + DEFAULT_PRENOM);

        // Get all the userRegisteredList where prenom equals to UPDATED_PRENOM
        defaultUserRegisteredShouldNotBeFound("prenom.equals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByPrenomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where prenom not equals to DEFAULT_PRENOM
        defaultUserRegisteredShouldNotBeFound("prenom.notEquals=" + DEFAULT_PRENOM);

        // Get all the userRegisteredList where prenom not equals to UPDATED_PRENOM
        defaultUserRegisteredShouldBeFound("prenom.notEquals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByPrenomIsInShouldWork() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where prenom in DEFAULT_PRENOM or UPDATED_PRENOM
        defaultUserRegisteredShouldBeFound("prenom.in=" + DEFAULT_PRENOM + "," + UPDATED_PRENOM);

        // Get all the userRegisteredList where prenom equals to UPDATED_PRENOM
        defaultUserRegisteredShouldNotBeFound("prenom.in=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByPrenomIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where prenom is not null
        defaultUserRegisteredShouldBeFound("prenom.specified=true");

        // Get all the userRegisteredList where prenom is null
        defaultUserRegisteredShouldNotBeFound("prenom.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByPrenomContainsSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where prenom contains DEFAULT_PRENOM
        defaultUserRegisteredShouldBeFound("prenom.contains=" + DEFAULT_PRENOM);

        // Get all the userRegisteredList where prenom contains UPDATED_PRENOM
        defaultUserRegisteredShouldNotBeFound("prenom.contains=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByPrenomNotContainsSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where prenom does not contain DEFAULT_PRENOM
        defaultUserRegisteredShouldNotBeFound("prenom.doesNotContain=" + DEFAULT_PRENOM);

        // Get all the userRegisteredList where prenom does not contain UPDATED_PRENOM
        defaultUserRegisteredShouldBeFound("prenom.doesNotContain=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where telephone equals to DEFAULT_TELEPHONE
        defaultUserRegisteredShouldBeFound("telephone.equals=" + DEFAULT_TELEPHONE);

        // Get all the userRegisteredList where telephone equals to UPDATED_TELEPHONE
        defaultUserRegisteredShouldNotBeFound("telephone.equals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where telephone not equals to DEFAULT_TELEPHONE
        defaultUserRegisteredShouldNotBeFound("telephone.notEquals=" + DEFAULT_TELEPHONE);

        // Get all the userRegisteredList where telephone not equals to UPDATED_TELEPHONE
        defaultUserRegisteredShouldBeFound("telephone.notEquals=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where telephone in DEFAULT_TELEPHONE or UPDATED_TELEPHONE
        defaultUserRegisteredShouldBeFound("telephone.in=" + DEFAULT_TELEPHONE + "," + UPDATED_TELEPHONE);

        // Get all the userRegisteredList where telephone equals to UPDATED_TELEPHONE
        defaultUserRegisteredShouldNotBeFound("telephone.in=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where telephone is not null
        defaultUserRegisteredShouldBeFound("telephone.specified=true");

        // Get all the userRegisteredList where telephone is null
        defaultUserRegisteredShouldNotBeFound("telephone.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByTelephoneContainsSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where telephone contains DEFAULT_TELEPHONE
        defaultUserRegisteredShouldBeFound("telephone.contains=" + DEFAULT_TELEPHONE);

        // Get all the userRegisteredList where telephone contains UPDATED_TELEPHONE
        defaultUserRegisteredShouldNotBeFound("telephone.contains=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where telephone does not contain DEFAULT_TELEPHONE
        defaultUserRegisteredShouldNotBeFound("telephone.doesNotContain=" + DEFAULT_TELEPHONE);

        // Get all the userRegisteredList where telephone does not contain UPDATED_TELEPHONE
        defaultUserRegisteredShouldBeFound("telephone.doesNotContain=" + UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByMailIsEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where mail equals to DEFAULT_MAIL
        defaultUserRegisteredShouldBeFound("mail.equals=" + DEFAULT_MAIL);

        // Get all the userRegisteredList where mail equals to UPDATED_MAIL
        defaultUserRegisteredShouldNotBeFound("mail.equals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByMailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where mail not equals to DEFAULT_MAIL
        defaultUserRegisteredShouldNotBeFound("mail.notEquals=" + DEFAULT_MAIL);

        // Get all the userRegisteredList where mail not equals to UPDATED_MAIL
        defaultUserRegisteredShouldBeFound("mail.notEquals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByMailIsInShouldWork() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where mail in DEFAULT_MAIL or UPDATED_MAIL
        defaultUserRegisteredShouldBeFound("mail.in=" + DEFAULT_MAIL + "," + UPDATED_MAIL);

        // Get all the userRegisteredList where mail equals to UPDATED_MAIL
        defaultUserRegisteredShouldNotBeFound("mail.in=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where mail is not null
        defaultUserRegisteredShouldBeFound("mail.specified=true");

        // Get all the userRegisteredList where mail is null
        defaultUserRegisteredShouldNotBeFound("mail.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByMailContainsSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where mail contains DEFAULT_MAIL
        defaultUserRegisteredShouldBeFound("mail.contains=" + DEFAULT_MAIL);

        // Get all the userRegisteredList where mail contains UPDATED_MAIL
        defaultUserRegisteredShouldNotBeFound("mail.contains=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByMailNotContainsSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where mail does not contain DEFAULT_MAIL
        defaultUserRegisteredShouldNotBeFound("mail.doesNotContain=" + DEFAULT_MAIL);

        // Get all the userRegisteredList where mail does not contain UPDATED_MAIL
        defaultUserRegisteredShouldBeFound("mail.doesNotContain=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where adresse equals to DEFAULT_ADRESSE
        defaultUserRegisteredShouldBeFound("adresse.equals=" + DEFAULT_ADRESSE);

        // Get all the userRegisteredList where adresse equals to UPDATED_ADRESSE
        defaultUserRegisteredShouldNotBeFound("adresse.equals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByAdresseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where adresse not equals to DEFAULT_ADRESSE
        defaultUserRegisteredShouldNotBeFound("adresse.notEquals=" + DEFAULT_ADRESSE);

        // Get all the userRegisteredList where adresse not equals to UPDATED_ADRESSE
        defaultUserRegisteredShouldBeFound("adresse.notEquals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where adresse in DEFAULT_ADRESSE or UPDATED_ADRESSE
        defaultUserRegisteredShouldBeFound("adresse.in=" + DEFAULT_ADRESSE + "," + UPDATED_ADRESSE);

        // Get all the userRegisteredList where adresse equals to UPDATED_ADRESSE
        defaultUserRegisteredShouldNotBeFound("adresse.in=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where adresse is not null
        defaultUserRegisteredShouldBeFound("adresse.specified=true");

        // Get all the userRegisteredList where adresse is null
        defaultUserRegisteredShouldNotBeFound("adresse.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByAdresseContainsSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where adresse contains DEFAULT_ADRESSE
        defaultUserRegisteredShouldBeFound("adresse.contains=" + DEFAULT_ADRESSE);

        // Get all the userRegisteredList where adresse contains UPDATED_ADRESSE
        defaultUserRegisteredShouldNotBeFound("adresse.contains=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where adresse does not contain DEFAULT_ADRESSE
        defaultUserRegisteredShouldNotBeFound("adresse.doesNotContain=" + DEFAULT_ADRESSE);

        // Get all the userRegisteredList where adresse does not contain UPDATED_ADRESSE
        defaultUserRegisteredShouldBeFound("adresse.doesNotContain=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByCodePostalIsEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where codePostal equals to DEFAULT_CODE_POSTAL
        defaultUserRegisteredShouldBeFound("codePostal.equals=" + DEFAULT_CODE_POSTAL);

        // Get all the userRegisteredList where codePostal equals to UPDATED_CODE_POSTAL
        defaultUserRegisteredShouldNotBeFound("codePostal.equals=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByCodePostalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where codePostal not equals to DEFAULT_CODE_POSTAL
        defaultUserRegisteredShouldNotBeFound("codePostal.notEquals=" + DEFAULT_CODE_POSTAL);

        // Get all the userRegisteredList where codePostal not equals to UPDATED_CODE_POSTAL
        defaultUserRegisteredShouldBeFound("codePostal.notEquals=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByCodePostalIsInShouldWork() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where codePostal in DEFAULT_CODE_POSTAL or UPDATED_CODE_POSTAL
        defaultUserRegisteredShouldBeFound("codePostal.in=" + DEFAULT_CODE_POSTAL + "," + UPDATED_CODE_POSTAL);

        // Get all the userRegisteredList where codePostal equals to UPDATED_CODE_POSTAL
        defaultUserRegisteredShouldNotBeFound("codePostal.in=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByCodePostalIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where codePostal is not null
        defaultUserRegisteredShouldBeFound("codePostal.specified=true");

        // Get all the userRegisteredList where codePostal is null
        defaultUserRegisteredShouldNotBeFound("codePostal.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByCodePostalContainsSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where codePostal contains DEFAULT_CODE_POSTAL
        defaultUserRegisteredShouldBeFound("codePostal.contains=" + DEFAULT_CODE_POSTAL);

        // Get all the userRegisteredList where codePostal contains UPDATED_CODE_POSTAL
        defaultUserRegisteredShouldNotBeFound("codePostal.contains=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByCodePostalNotContainsSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where codePostal does not contain DEFAULT_CODE_POSTAL
        defaultUserRegisteredShouldNotBeFound("codePostal.doesNotContain=" + DEFAULT_CODE_POSTAL);

        // Get all the userRegisteredList where codePostal does not contain UPDATED_CODE_POSTAL
        defaultUserRegisteredShouldBeFound("codePostal.doesNotContain=" + UPDATED_CODE_POSTAL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByCommuneIsEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where commune equals to DEFAULT_COMMUNE
        defaultUserRegisteredShouldBeFound("commune.equals=" + DEFAULT_COMMUNE);

        // Get all the userRegisteredList where commune equals to UPDATED_COMMUNE
        defaultUserRegisteredShouldNotBeFound("commune.equals=" + UPDATED_COMMUNE);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByCommuneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where commune not equals to DEFAULT_COMMUNE
        defaultUserRegisteredShouldNotBeFound("commune.notEquals=" + DEFAULT_COMMUNE);

        // Get all the userRegisteredList where commune not equals to UPDATED_COMMUNE
        defaultUserRegisteredShouldBeFound("commune.notEquals=" + UPDATED_COMMUNE);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByCommuneIsInShouldWork() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where commune in DEFAULT_COMMUNE or UPDATED_COMMUNE
        defaultUserRegisteredShouldBeFound("commune.in=" + DEFAULT_COMMUNE + "," + UPDATED_COMMUNE);

        // Get all the userRegisteredList where commune equals to UPDATED_COMMUNE
        defaultUserRegisteredShouldNotBeFound("commune.in=" + UPDATED_COMMUNE);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByCommuneIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where commune is not null
        defaultUserRegisteredShouldBeFound("commune.specified=true");

        // Get all the userRegisteredList where commune is null
        defaultUserRegisteredShouldNotBeFound("commune.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByCommuneContainsSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where commune contains DEFAULT_COMMUNE
        defaultUserRegisteredShouldBeFound("commune.contains=" + DEFAULT_COMMUNE);

        // Get all the userRegisteredList where commune contains UPDATED_COMMUNE
        defaultUserRegisteredShouldNotBeFound("commune.contains=" + UPDATED_COMMUNE);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByCommuneNotContainsSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where commune does not contain DEFAULT_COMMUNE
        defaultUserRegisteredShouldNotBeFound("commune.doesNotContain=" + DEFAULT_COMMUNE);

        // Get all the userRegisteredList where commune does not contain UPDATED_COMMUNE
        defaultUserRegisteredShouldBeFound("commune.doesNotContain=" + UPDATED_COMMUNE);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByHeureVolIsEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where heureVol equals to DEFAULT_HEURE_VOL
        defaultUserRegisteredShouldBeFound("heureVol.equals=" + DEFAULT_HEURE_VOL);

        // Get all the userRegisteredList where heureVol equals to UPDATED_HEURE_VOL
        defaultUserRegisteredShouldNotBeFound("heureVol.equals=" + UPDATED_HEURE_VOL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByHeureVolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where heureVol not equals to DEFAULT_HEURE_VOL
        defaultUserRegisteredShouldNotBeFound("heureVol.notEquals=" + DEFAULT_HEURE_VOL);

        // Get all the userRegisteredList where heureVol not equals to UPDATED_HEURE_VOL
        defaultUserRegisteredShouldBeFound("heureVol.notEquals=" + UPDATED_HEURE_VOL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByHeureVolIsInShouldWork() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where heureVol in DEFAULT_HEURE_VOL or UPDATED_HEURE_VOL
        defaultUserRegisteredShouldBeFound("heureVol.in=" + DEFAULT_HEURE_VOL + "," + UPDATED_HEURE_VOL);

        // Get all the userRegisteredList where heureVol equals to UPDATED_HEURE_VOL
        defaultUserRegisteredShouldNotBeFound("heureVol.in=" + UPDATED_HEURE_VOL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByHeureVolIsNullOrNotNull() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where heureVol is not null
        defaultUserRegisteredShouldBeFound("heureVol.specified=true");

        // Get all the userRegisteredList where heureVol is null
        defaultUserRegisteredShouldNotBeFound("heureVol.specified=false");
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByHeureVolIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where heureVol is greater than or equal to DEFAULT_HEURE_VOL
        defaultUserRegisteredShouldBeFound("heureVol.greaterThanOrEqual=" + DEFAULT_HEURE_VOL);

        // Get all the userRegisteredList where heureVol is greater than or equal to UPDATED_HEURE_VOL
        defaultUserRegisteredShouldNotBeFound("heureVol.greaterThanOrEqual=" + UPDATED_HEURE_VOL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByHeureVolIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where heureVol is less than or equal to DEFAULT_HEURE_VOL
        defaultUserRegisteredShouldBeFound("heureVol.lessThanOrEqual=" + DEFAULT_HEURE_VOL);

        // Get all the userRegisteredList where heureVol is less than or equal to SMALLER_HEURE_VOL
        defaultUserRegisteredShouldNotBeFound("heureVol.lessThanOrEqual=" + SMALLER_HEURE_VOL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByHeureVolIsLessThanSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where heureVol is less than DEFAULT_HEURE_VOL
        defaultUserRegisteredShouldNotBeFound("heureVol.lessThan=" + DEFAULT_HEURE_VOL);

        // Get all the userRegisteredList where heureVol is less than UPDATED_HEURE_VOL
        defaultUserRegisteredShouldBeFound("heureVol.lessThan=" + UPDATED_HEURE_VOL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByHeureVolIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        // Get all the userRegisteredList where heureVol is greater than DEFAULT_HEURE_VOL
        defaultUserRegisteredShouldNotBeFound("heureVol.greaterThan=" + DEFAULT_HEURE_VOL);

        // Get all the userRegisteredList where heureVol is greater than SMALLER_HEURE_VOL
        defaultUserRegisteredShouldBeFound("heureVol.greaterThan=" + SMALLER_HEURE_VOL);
    }

    @Test
    @Transactional
    void getAllUserRegisteredsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            user = UserResourceIT.createEntity(em);
            em.persist(user);
            em.flush();
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        userRegistered.setUser(user);
        userRegisteredRepository.saveAndFlush(userRegistered);
        Long userId = user.getId();

        // Get all the userRegisteredList where user equals to userId
        defaultUserRegisteredShouldBeFound("userId.equals=" + userId);

        // Get all the userRegisteredList where user equals to (userId + 1)
        defaultUserRegisteredShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserRegisteredShouldBeFound(String filter) throws Exception {
        restUserRegisteredMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userRegistered.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].commune").value(hasItem(DEFAULT_COMMUNE)))
            .andExpect(jsonPath("$.[*].heureVol").value(hasItem(DEFAULT_HEURE_VOL.toString())));

        // Check, that the count call also returns 1
        restUserRegisteredMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserRegisteredShouldNotBeFound(String filter) throws Exception {
        restUserRegisteredMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserRegisteredMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserRegistered() throws Exception {
        // Get the userRegistered
        restUserRegisteredMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserRegistered() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        int databaseSizeBeforeUpdate = userRegisteredRepository.findAll().size();

        // Update the userRegistered
        UserRegistered updatedUserRegistered = userRegisteredRepository.findById(userRegistered.getId()).get();
        // Disconnect from session so that the updates on updatedUserRegistered are not directly saved in db
        em.detach(updatedUserRegistered);
        updatedUserRegistered
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .mail(UPDATED_MAIL)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .commune(UPDATED_COMMUNE)
            .heureVol(UPDATED_HEURE_VOL);
        UserRegisteredDTO userRegisteredDTO = userRegisteredMapper.toDto(updatedUserRegistered);

        restUserRegisteredMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userRegisteredDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRegisteredDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserRegistered in the database
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeUpdate);
        UserRegistered testUserRegistered = userRegisteredList.get(userRegisteredList.size() - 1);
        assertThat(testUserRegistered.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testUserRegistered.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testUserRegistered.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testUserRegistered.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testUserRegistered.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testUserRegistered.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testUserRegistered.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testUserRegistered.getHeureVol()).isEqualTo(UPDATED_HEURE_VOL);
    }

    @Test
    @Transactional
    void putNonExistingUserRegistered() throws Exception {
        int databaseSizeBeforeUpdate = userRegisteredRepository.findAll().size();
        userRegistered.setId(count.incrementAndGet());

        // Create the UserRegistered
        UserRegisteredDTO userRegisteredDTO = userRegisteredMapper.toDto(userRegistered);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRegisteredMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userRegisteredDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRegisteredDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRegistered in the database
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserRegistered() throws Exception {
        int databaseSizeBeforeUpdate = userRegisteredRepository.findAll().size();
        userRegistered.setId(count.incrementAndGet());

        // Create the UserRegistered
        UserRegisteredDTO userRegisteredDTO = userRegisteredMapper.toDto(userRegistered);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRegisteredMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userRegisteredDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRegistered in the database
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserRegistered() throws Exception {
        int databaseSizeBeforeUpdate = userRegisteredRepository.findAll().size();
        userRegistered.setId(count.incrementAndGet());

        // Create the UserRegistered
        UserRegisteredDTO userRegisteredDTO = userRegisteredMapper.toDto(userRegistered);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRegisteredMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userRegisteredDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserRegistered in the database
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserRegisteredWithPatch() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        int databaseSizeBeforeUpdate = userRegisteredRepository.findAll().size();

        // Update the userRegistered using partial update
        UserRegistered partialUpdatedUserRegistered = new UserRegistered();
        partialUpdatedUserRegistered.setId(userRegistered.getId());

        partialUpdatedUserRegistered
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .commune(UPDATED_COMMUNE)
            .heureVol(UPDATED_HEURE_VOL);

        restUserRegisteredMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserRegistered.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserRegistered))
            )
            .andExpect(status().isOk());

        // Validate the UserRegistered in the database
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeUpdate);
        UserRegistered testUserRegistered = userRegisteredList.get(userRegisteredList.size() - 1);
        assertThat(testUserRegistered.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testUserRegistered.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testUserRegistered.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testUserRegistered.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testUserRegistered.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testUserRegistered.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testUserRegistered.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testUserRegistered.getHeureVol()).isEqualTo(UPDATED_HEURE_VOL);
    }

    @Test
    @Transactional
    void fullUpdateUserRegisteredWithPatch() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        int databaseSizeBeforeUpdate = userRegisteredRepository.findAll().size();

        // Update the userRegistered using partial update
        UserRegistered partialUpdatedUserRegistered = new UserRegistered();
        partialUpdatedUserRegistered.setId(userRegistered.getId());

        partialUpdatedUserRegistered
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .telephone(UPDATED_TELEPHONE)
            .mail(UPDATED_MAIL)
            .adresse(UPDATED_ADRESSE)
            .codePostal(UPDATED_CODE_POSTAL)
            .commune(UPDATED_COMMUNE)
            .heureVol(UPDATED_HEURE_VOL);

        restUserRegisteredMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserRegistered.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserRegistered))
            )
            .andExpect(status().isOk());

        // Validate the UserRegistered in the database
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeUpdate);
        UserRegistered testUserRegistered = userRegisteredList.get(userRegisteredList.size() - 1);
        assertThat(testUserRegistered.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testUserRegistered.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testUserRegistered.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testUserRegistered.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testUserRegistered.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testUserRegistered.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testUserRegistered.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testUserRegistered.getHeureVol()).isEqualTo(UPDATED_HEURE_VOL);
    }

    @Test
    @Transactional
    void patchNonExistingUserRegistered() throws Exception {
        int databaseSizeBeforeUpdate = userRegisteredRepository.findAll().size();
        userRegistered.setId(count.incrementAndGet());

        // Create the UserRegistered
        UserRegisteredDTO userRegisteredDTO = userRegisteredMapper.toDto(userRegistered);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserRegisteredMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userRegisteredDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRegisteredDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRegistered in the database
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserRegistered() throws Exception {
        int databaseSizeBeforeUpdate = userRegisteredRepository.findAll().size();
        userRegistered.setId(count.incrementAndGet());

        // Create the UserRegistered
        UserRegisteredDTO userRegisteredDTO = userRegisteredMapper.toDto(userRegistered);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRegisteredMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRegisteredDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserRegistered in the database
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserRegistered() throws Exception {
        int databaseSizeBeforeUpdate = userRegisteredRepository.findAll().size();
        userRegistered.setId(count.incrementAndGet());

        // Create the UserRegistered
        UserRegisteredDTO userRegisteredDTO = userRegisteredMapper.toDto(userRegistered);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserRegisteredMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userRegisteredDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserRegistered in the database
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserRegistered() throws Exception {
        // Initialize the database
        userRegisteredRepository.saveAndFlush(userRegistered);

        int databaseSizeBeforeDelete = userRegisteredRepository.findAll().size();

        // Delete the userRegistered
        restUserRegisteredMockMvc
            .perform(delete(ENTITY_API_URL_ID, userRegistered.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserRegistered> userRegisteredList = userRegisteredRepository.findAll();
        assertThat(userRegisteredList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
