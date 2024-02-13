package com.fabienit.rechercher.web.rest;

import com.fabienit.rechercher.domain.Pilote;
import com.fabienit.rechercher.repository.PiloteRepository;
import com.fabienit.rechercher.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.fabienit.rechercher.domain.Pilote}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PiloteResource {

    private final Logger log = LoggerFactory.getLogger(PiloteResource.class);

    private static final String ENTITY_NAME = "pilote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PiloteRepository piloteRepository;

    public PiloteResource(PiloteRepository piloteRepository) {
        this.piloteRepository = piloteRepository;
    }

    /**
     * {@code POST  /pilotes} : Create a new pilote.
     *
     * @param pilote the pilote to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pilote, or with status {@code 400 (Bad Request)} if the pilote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pilotes")
    public ResponseEntity<Pilote> createPilote(@RequestBody Pilote pilote) throws URISyntaxException {
        log.debug("REST request to save Pilote : {}", pilote);
        if (pilote.getId() != null) {
            throw new BadRequestAlertException("A new pilote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pilote result = piloteRepository.save(pilote);
        return ResponseEntity
            .created(new URI("/api/pilotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pilotes/:id} : Updates an existing pilote.
     *
     * @param id the id of the pilote to save.
     * @param pilote the pilote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pilote,
     * or with status {@code 400 (Bad Request)} if the pilote is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pilote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pilotes/{id}")
    public ResponseEntity<Pilote> updatePilote(@PathVariable(value = "id", required = false) final Long id, @RequestBody Pilote pilote)
        throws URISyntaxException {
        log.debug("REST request to update Pilote : {}, {}", id, pilote);
        if (pilote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pilote.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!piloteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Pilote result = piloteRepository.save(pilote);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pilote.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pilotes/:id} : Partial updates given fields of an existing pilote, field will ignore if it is null
     *
     * @param id the id of the pilote to save.
     * @param pilote the pilote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pilote,
     * or with status {@code 400 (Bad Request)} if the pilote is not valid,
     * or with status {@code 404 (Not Found)} if the pilote is not found,
     * or with status {@code 500 (Internal Server Error)} if the pilote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pilotes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pilote> partialUpdatePilote(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Pilote pilote
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pilote partially : {}, {}", id, pilote);
        if (pilote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pilote.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!piloteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pilote> result = piloteRepository
            .findById(pilote.getId())
            .map(existingPilote -> {
                if (pilote.getName() != null) {
                    existingPilote.setName(pilote.getName());
                }
                if (pilote.getEmailId() != null) {
                    existingPilote.setEmailId(pilote.getEmailId());
                }
                if (pilote.getQualification() != null) {
                    existingPilote.setQualification(pilote.getQualification());
                }

                return existingPilote;
            })
            .map(piloteRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pilote.getId().toString())
        );
    }

    /**
     * {@code GET  /pilotes} : get all the pilotes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pilotes in body.
     */
    @GetMapping("/pilotes")
    public List<Pilote> getAllPilotes() {
        log.debug("REST request to get all Pilotes");
        return piloteRepository.findAll();
    }

    /**
     * {@code GET  /pilotes/:id} : get the "id" pilote.
     *
     * @param id the id of the pilote to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pilote, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pilotes/{id}")
    public ResponseEntity<Pilote> getPilote(@PathVariable Long id) {
        log.debug("REST request to get Pilote : {}", id);
        Optional<Pilote> pilote = piloteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pilote);
    }

    /**
     * {@code DELETE  /pilotes/:id} : delete the "id" pilote.
     *
     * @param id the id of the pilote to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pilotes/{id}")
    public ResponseEntity<Void> deletePilote(@PathVariable Long id) {
        log.debug("REST request to delete Pilote : {}", id);
        piloteRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
