package com.fabienit.aeroclubpassion.service;

import com.fabienit.aeroclubpassion.domain.*; // for static metamodels
import com.fabienit.aeroclubpassion.domain.Reservation;
import com.fabienit.aeroclubpassion.repository.ReservationRepository;
import com.fabienit.aeroclubpassion.service.criteria.ReservationCriteria;
import com.fabienit.aeroclubpassion.service.dto.ReservationDTO;
import com.fabienit.aeroclubpassion.service.mapper.ReservationMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Reservation} entities in the database.
 * The main input is a {@link ReservationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReservationDTO} or a {@link Page} of {@link ReservationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReservationQueryService extends QueryService<Reservation> {

    private final Logger log = LoggerFactory.getLogger(ReservationQueryService.class);

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    public ReservationQueryService(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    /**
     * Return a {@link List} of {@link ReservationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReservationDTO> findByCriteria(ReservationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Reservation> specification = createSpecification(criteria);
        return reservationMapper.toDto(reservationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReservationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReservationDTO> findByCriteria(ReservationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Reservation> specification = createSpecification(criteria);
        return reservationRepository.findAll(specification, page).map(reservationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReservationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Reservation> specification = createSpecification(criteria);
        return reservationRepository.count(specification);
    }

    /**
     * Function to convert {@link ReservationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Reservation> createSpecification(ReservationCriteria criteria) {
        Specification<Reservation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Reservation_.id));
            }
            if (criteria.getDateEmprunt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateEmprunt(), Reservation_.dateEmprunt));
            }
            if (criteria.getDateRetour() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateRetour(), Reservation_.dateRetour));
            }
            if (criteria.getAvionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAvionsId(), root -> root.join(Reservation_.avions, JoinType.LEFT).get(Avion_.id))
                    );
            }
            if (criteria.getUserRegisteredsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserRegisteredsId(),
                            root -> root.join(Reservation_.userRegistereds, JoinType.LEFT).get(UserRegistered_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
