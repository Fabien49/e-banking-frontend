package com.fabienit.aeroclubpassion.web.rest;

import com.fabienit.aeroclubpassion.repository.RevisionRepository;
import com.fabienit.aeroclubpassion.service.RevisionQueryService;
import com.fabienit.aeroclubpassion.service.RevisionService;
import com.fabienit.aeroclubpassion.service.criteria.RevisionCriteria;
import com.fabienit.aeroclubpassion.service.dto.RevisionDTO;
import com.fabienit.aeroclubpassion.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.fabienit.aeroclubpassion.domain.Revision}.
 */
@RestController
@RequestMapping("/api")
public class RevisionResource {

    private final Logger log = LoggerFactory.getLogger(RevisionResource.class);

    private static final String ENTITY_NAME = "revision";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RevisionService revisionService;

    private final RevisionRepository revisionRepository;

    private final RevisionQueryService revisionQueryService;

    public RevisionResource(
        RevisionService revisionService,
        RevisionRepository revisionRepository,
        RevisionQueryService revisionQueryService
    ) {
        this.revisionService = revisionService;
        this.revisionRepository = revisionRepository;
        this.revisionQueryService = revisionQueryService;
    }

    /**
     * {@code POST  /revisions} : Create a new revision.
     *
     * @param revisionDTO the revisionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new revisionDTO, or with status {@code 400 (Bad Request)} if the revision has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/revisions")
    public ResponseEntity<RevisionDTO> createRevision(@RequestBody RevisionDTO revisionDTO) throws URISyntaxException {
        log.debug("REST request to save Revision : {}", revisionDTO);
        if (revisionDTO.getId() != null) {
            throw new BadRequestAlertException("A new revision cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RevisionDTO result = revisionService.save(revisionDTO);
        return ResponseEntity
            .created(new URI("/api/revisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /revisions/:id} : Updates an existing revision.
     *
     * @param id the id of the revisionDTO to save.
     * @param revisionDTO the revisionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated revisionDTO,
     * or with status {@code 400 (Bad Request)} if the revisionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the revisionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/revisions/{id}")
    public ResponseEntity<RevisionDTO> updateRevision(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RevisionDTO revisionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Revision : {}, {}", id, revisionDTO);
        if (revisionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, revisionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!revisionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RevisionDTO result = revisionService.save(revisionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, revisionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /revisions/:id} : Partial updates given fields of an existing revision, field will ignore if it is null
     *
     * @param id the id of the revisionDTO to save.
     * @param revisionDTO the revisionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated revisionDTO,
     * or with status {@code 400 (Bad Request)} if the revisionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the revisionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the revisionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/revisions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RevisionDTO> partialUpdateRevision(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RevisionDTO revisionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Revision partially : {}, {}", id, revisionDTO);
        if (revisionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, revisionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!revisionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RevisionDTO> result = revisionService.partialUpdate(revisionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, revisionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /revisions} : get all the revisions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of revisions in body.
     */
    @GetMapping("/revisions")
    public ResponseEntity<List<RevisionDTO>> getAllRevisions(RevisionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Revisions by criteria: {}", criteria);
        Page<RevisionDTO> page = revisionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /revisions/count} : count all the revisions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/revisions/count")
    public ResponseEntity<Long> countRevisions(RevisionCriteria criteria) {
        log.debug("REST request to count Revisions by criteria: {}", criteria);
        return ResponseEntity.ok().body(revisionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /revisions/:id} : get the "id" revision.
     *
     * @param id the id of the revisionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the revisionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/revisions/{id}")
    public ResponseEntity<RevisionDTO> getRevision(@PathVariable Long id) {
        log.debug("REST request to get Revision : {}", id);
        Optional<RevisionDTO> revisionDTO = revisionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(revisionDTO);
    }

    /**
     * {@code DELETE  /revisions/:id} : delete the "id" revision.
     *
     * @param id the id of the revisionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/revisions/{id}")
    public ResponseEntity<Void> deleteRevision(@PathVariable Long id) {
        log.debug("REST request to delete Revision : {}", id);
        revisionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
