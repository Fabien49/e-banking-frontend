package com.fabienit.aeroclubpassion.web.rest;

import com.fabienit.aeroclubpassion.repository.ReservationRepository;
import com.fabienit.aeroclubpassion.service.ReservationQueryService;
import com.fabienit.aeroclubpassion.service.ReservationService;
import com.fabienit.aeroclubpassion.service.criteria.ReservationCriteria;
import com.fabienit.aeroclubpassion.service.dto.ReservationDTO;
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
 * REST controller for managing {@link com.fabienit.aeroclubpassion.domain.Reservation}.
 */
@RestController
@RequestMapping("/api")
public class ReservationResource {

    private final Logger log = LoggerFactory.getLogger(ReservationResource.class);

    private static final String ENTITY_NAME = "reservation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReservationService reservationService;

    private final ReservationRepository reservationRepository;

    private final ReservationQueryService reservationQueryService;

    public ReservationResource(
        ReservationService reservationService,
        ReservationRepository reservationRepository,
        ReservationQueryService reservationQueryService
    ) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
        this.reservationQueryService = reservationQueryService;
    }

    /**
     * {@code POST  /reservations} : Create a new reservation.
     *
     * @param reservationDTO the reservationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reservationDTO, or with status {@code 400 (Bad Request)} if the reservation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reservations")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) throws URISyntaxException {
        log.debug("REST request to save Reservation : {}", reservationDTO);
        if (reservationDTO.getId() != null) {
            throw new BadRequestAlertException("A new reservation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReservationDTO result = reservationService.save(reservationDTO);
        return ResponseEntity
            .created(new URI("/api/reservations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reservations/:id} : Updates an existing reservation.
     *
     * @param id the id of the reservationDTO to save.
     * @param reservationDTO the reservationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservationDTO,
     * or with status {@code 400 (Bad Request)} if the reservationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reservationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reservations/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReservationDTO reservationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Reservation : {}, {}", id, reservationDTO);
        if (reservationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reservationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reservationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReservationDTO result = reservationService.save(reservationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reservations/:id} : Partial updates given fields of an existing reservation, field will ignore if it is null
     *
     * @param id the id of the reservationDTO to save.
     * @param reservationDTO the reservationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservationDTO,
     * or with status {@code 400 (Bad Request)} if the reservationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reservationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reservationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reservations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReservationDTO> partialUpdateReservation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReservationDTO reservationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Reservation partially : {}, {}", id, reservationDTO);
        if (reservationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reservationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reservationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReservationDTO> result = reservationService.partialUpdate(reservationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /reservations} : get all the reservations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reservations in body.
     */
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDTO>> getAllReservations(ReservationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Reservations by criteria: {}", criteria);
        Page<ReservationDTO> page = reservationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reservations/count} : count all the reservations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/reservations/count")
    public ResponseEntity<Long> countReservations(ReservationCriteria criteria) {
        log.debug("REST request to count Reservations by criteria: {}", criteria);
        return ResponseEntity.ok().body(reservationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /reservations/:id} : get the "id" reservation.
     *
     * @param id the id of the reservationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reservationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {
        log.debug("REST request to get Reservation : {}", id);
        Optional<ReservationDTO> reservationDTO = reservationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reservationDTO);
    }

    /**
     * {@code DELETE  /reservations/:id} : delete the "id" reservation.
     *
     * @param id the id of the reservationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        log.debug("REST request to delete Reservation : {}", id);
        reservationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
