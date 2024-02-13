package com.fabienit.rechercher.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fabienit.rechercher.IntegrationTest;
import com.fabienit.rechercher.domain.Pilote;
import com.fabienit.rechercher.repository.PiloteRepository;
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
 * Integration tests for the {@link PiloteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PiloteResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_QUALIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_QUALIFICATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pilotes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PiloteRepository piloteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPiloteMockMvc;

    private Pilote pilote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pilote createEntity(EntityManager em) {
        Pilote pilote = new Pilote().name(DEFAULT_NAME).emailId(DEFAULT_EMAIL_ID).qualification(DEFAULT_QUALIFICATION);
        return pilote;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pilote createUpdatedEntity(EntityManager em) {
        Pilote pilote = new Pilote().name(UPDATED_NAME).emailId(UPDATED_EMAIL_ID).qualification(UPDATED_QUALIFICATION);
        return pilote;
    }

    @BeforeEach
    public void initTest() {
        pilote = createEntity(em);
    }

    @Test
    @Transactional
    void createPilote() throws Exception {
        int databaseSizeBeforeCreate = piloteRepository.findAll().size();
        // Create the Pilote
        restPiloteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pilote)))
            .andExpect(status().isCreated());

        // Validate the Pilote in the database
        List<Pilote> piloteList = piloteRepository.findAll();
        assertThat(piloteList).hasSize(databaseSizeBeforeCreate + 1);
        Pilote testPilote = piloteList.get(piloteList.size() - 1);
        assertThat(testPilote.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPilote.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testPilote.getQualification()).isEqualTo(DEFAULT_QUALIFICATION);
    }

    @Test
    @Transactional
    void createPiloteWithExistingId() throws Exception {
        // Create the Pilote with an existing ID
        pilote.setId(1L);

        int databaseSizeBeforeCreate = piloteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPiloteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pilote)))
            .andExpect(status().isBadRequest());

        // Validate the Pilote in the database
        List<Pilote> piloteList = piloteRepository.findAll();
        assertThat(piloteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPilotes() throws Exception {
        // Initialize the database
        piloteRepository.saveAndFlush(pilote);

        // Get all the piloteList
        restPiloteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pilote.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].emailId").value(hasItem(DEFAULT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].qualification").value(hasItem(DEFAULT_QUALIFICATION)));
    }

    @Test
    @Transactional
    void getPilote() throws Exception {
        // Initialize the database
        piloteRepository.saveAndFlush(pilote);

        // Get the pilote
        restPiloteMockMvc
            .perform(get(ENTITY_API_URL_ID, pilote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pilote.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.emailId").value(DEFAULT_EMAIL_ID))
            .andExpect(jsonPath("$.qualification").value(DEFAULT_QUALIFICATION));
    }

    @Test
    @Transactional
    void getNonExistingPilote() throws Exception {
        // Get the pilote
        restPiloteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPilote() throws Exception {
        // Initialize the database
        piloteRepository.saveAndFlush(pilote);

        int databaseSizeBeforeUpdate = piloteRepository.findAll().size();

        // Update the pilote
        Pilote updatedPilote = piloteRepository.findById(pilote.getId()).get();
        // Disconnect from session so that the updates on updatedPilote are not directly saved in db
        em.detach(updatedPilote);
        updatedPilote.name(UPDATED_NAME).emailId(UPDATED_EMAIL_ID).qualification(UPDATED_QUALIFICATION);

        restPiloteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPilote.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPilote))
            )
            .andExpect(status().isOk());

        // Validate the Pilote in the database
        List<Pilote> piloteList = piloteRepository.findAll();
        assertThat(piloteList).hasSize(databaseSizeBeforeUpdate);
        Pilote testPilote = piloteList.get(piloteList.size() - 1);
        assertThat(testPilote.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPilote.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testPilote.getQualification()).isEqualTo(UPDATED_QUALIFICATION);
    }

    @Test
    @Transactional
    void putNonExistingPilote() throws Exception {
        int databaseSizeBeforeUpdate = piloteRepository.findAll().size();
        pilote.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPiloteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pilote.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pilote))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pilote in the database
        List<Pilote> piloteList = piloteRepository.findAll();
        assertThat(piloteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPilote() throws Exception {
        int databaseSizeBeforeUpdate = piloteRepository.findAll().size();
        pilote.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPiloteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pilote))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pilote in the database
        List<Pilote> piloteList = piloteRepository.findAll();
        assertThat(piloteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPilote() throws Exception {
        int databaseSizeBeforeUpdate = piloteRepository.findAll().size();
        pilote.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPiloteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pilote)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pilote in the database
        List<Pilote> piloteList = piloteRepository.findAll();
        assertThat(piloteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePiloteWithPatch() throws Exception {
        // Initialize the database
        piloteRepository.saveAndFlush(pilote);

        int databaseSizeBeforeUpdate = piloteRepository.findAll().size();

        // Update the pilote using partial update
        Pilote partialUpdatedPilote = new Pilote();
        partialUpdatedPilote.setId(pilote.getId());

        partialUpdatedPilote.name(UPDATED_NAME);

        restPiloteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPilote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPilote))
            )
            .andExpect(status().isOk());

        // Validate the Pilote in the database
        List<Pilote> piloteList = piloteRepository.findAll();
        assertThat(piloteList).hasSize(databaseSizeBeforeUpdate);
        Pilote testPilote = piloteList.get(piloteList.size() - 1);
        assertThat(testPilote.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPilote.getEmailId()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testPilote.getQualification()).isEqualTo(DEFAULT_QUALIFICATION);
    }

    @Test
    @Transactional
    void fullUpdatePiloteWithPatch() throws Exception {
        // Initialize the database
        piloteRepository.saveAndFlush(pilote);

        int databaseSizeBeforeUpdate = piloteRepository.findAll().size();

        // Update the pilote using partial update
        Pilote partialUpdatedPilote = new Pilote();
        partialUpdatedPilote.setId(pilote.getId());

        partialUpdatedPilote.name(UPDATED_NAME).emailId(UPDATED_EMAIL_ID).qualification(UPDATED_QUALIFICATION);

        restPiloteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPilote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPilote))
            )
            .andExpect(status().isOk());

        // Validate the Pilote in the database
        List<Pilote> piloteList = piloteRepository.findAll();
        assertThat(piloteList).hasSize(databaseSizeBeforeUpdate);
        Pilote testPilote = piloteList.get(piloteList.size() - 1);
        assertThat(testPilote.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPilote.getEmailId()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testPilote.getQualification()).isEqualTo(UPDATED_QUALIFICATION);
    }

    @Test
    @Transactional
    void patchNonExistingPilote() throws Exception {
        int databaseSizeBeforeUpdate = piloteRepository.findAll().size();
        pilote.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPiloteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pilote.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pilote))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pilote in the database
        List<Pilote> piloteList = piloteRepository.findAll();
        assertThat(piloteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPilote() throws Exception {
        int databaseSizeBeforeUpdate = piloteRepository.findAll().size();
        pilote.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPiloteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pilote))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pilote in the database
        List<Pilote> piloteList = piloteRepository.findAll();
        assertThat(piloteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPilote() throws Exception {
        int databaseSizeBeforeUpdate = piloteRepository.findAll().size();
        pilote.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPiloteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pilote)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pilote in the database
        List<Pilote> piloteList = piloteRepository.findAll();
        assertThat(piloteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePilote() throws Exception {
        // Initialize the database
        piloteRepository.saveAndFlush(pilote);

        int databaseSizeBeforeDelete = piloteRepository.findAll().size();

        // Delete the pilote
        restPiloteMockMvc
            .perform(delete(ENTITY_API_URL_ID, pilote.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pilote> piloteList = piloteRepository.findAll();
        assertThat(piloteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
