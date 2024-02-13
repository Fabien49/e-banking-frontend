package com.fabienit.aeroclubpassion.service;

import com.fabienit.aeroclubpassion.domain.*; // for static metamodels
import com.fabienit.aeroclubpassion.domain.Avion;
import com.fabienit.aeroclubpassion.repository.AvionRepository;
import com.fabienit.aeroclubpassion.service.criteria.AvionCriteria;
import com.fabienit.aeroclubpassion.service.dto.AvionDTO;
import com.fabienit.aeroclubpassion.service.mapper.AvionMapper;
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
 * Service for executing complex queries for {@link Avion} entities in the database.
 * The main input is a {@link AvionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AvionDTO} or a {@link Page} of {@link AvionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AvionQueryService extends QueryService<Avion> {

    private final Logger log = LoggerFactory.getLogger(AvionQueryService.class);

    private final AvionRepository avionRepository;

    private final AvionMapper avionMapper;

    public AvionQueryService(AvionRepository avionRepository, AvionMapper avionMapper) {
        this.avionRepository = avionRepository;
        this.avionMapper = avionMapper;
    }

    /**
     * Return a {@link List} of {@link AvionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AvionDTO> findByCriteria(AvionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Avion> specification = createSpecification(criteria);
        return avionMapper.toDto(avionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AvionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AvionDTO> findByCriteria(AvionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Avion> specification = createSpecification(criteria);
        return avionRepository.findAll(specification, page).map(avionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AvionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Avion> specification = createSpecification(criteria);
        return avionRepository.count(specification);
    }

    /**
     * Function to convert {@link AvionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Avion> createSpecification(AvionCriteria criteria) {
        Specification<Avion> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Avion_.id));
            }
            if (criteria.getMarque() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMarque(), Avion_.marque));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Avion_.type));
            }
            if (criteria.getMoteur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMoteur(), Avion_.moteur));
            }
            if (criteria.getPuissance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPuissance(), Avion_.puissance));
            }
            if (criteria.getPlace() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPlace(), Avion_.place));
            }
            if (criteria.getAutonomie() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAutonomie(), Avion_.autonomie));
            }
            if (criteria.getUsage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsage(), Avion_.usage));
            }
            if (criteria.getHeures() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeures(), Avion_.heures));
            }
            if (criteria.getAtelierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAtelierId(), root -> root.join(Avion_.atelier, JoinType.LEFT).get(Atelier_.id))
                    );
            }
            if (criteria.getRevisionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getRevisionId(), root -> root.join(Avion_.revision, JoinType.LEFT).get(Revision_.id))
                    );
            }
        }
        return specification;
    }
}
