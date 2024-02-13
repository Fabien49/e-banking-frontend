package com.fabienit.aeroclubpassion.service;

import com.fabienit.aeroclubpassion.domain.*; // for static metamodels
import com.fabienit.aeroclubpassion.domain.Atelier;
import com.fabienit.aeroclubpassion.repository.AtelierRepository;
import com.fabienit.aeroclubpassion.service.criteria.AtelierCriteria;
import com.fabienit.aeroclubpassion.service.dto.AtelierDTO;
import com.fabienit.aeroclubpassion.service.mapper.AtelierMapper;
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
 * Service for executing complex queries for {@link Atelier} entities in the database.
 * The main input is a {@link AtelierCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AtelierDTO} or a {@link Page} of {@link AtelierDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AtelierQueryService extends QueryService<Atelier> {

    private final Logger log = LoggerFactory.getLogger(AtelierQueryService.class);

    private final AtelierRepository atelierRepository;

    private final AtelierMapper atelierMapper;

    public AtelierQueryService(AtelierRepository atelierRepository, AtelierMapper atelierMapper) {
        this.atelierRepository = atelierRepository;
        this.atelierMapper = atelierMapper;
    }

    /**
     * Return a {@link List} of {@link AtelierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AtelierDTO> findByCriteria(AtelierCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Atelier> specification = createSpecification(criteria);
        return atelierMapper.toDto(atelierRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AtelierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AtelierDTO> findByCriteria(AtelierCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Atelier> specification = createSpecification(criteria);
        return atelierRepository.findAll(specification, page).map(atelierMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AtelierCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Atelier> specification = createSpecification(criteria);
        return atelierRepository.count(specification);
    }

    /**
     * Function to convert {@link AtelierCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Atelier> createSpecification(AtelierCriteria criteria) {
        Specification<Atelier> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Atelier_.id));
            }
            if (criteria.getCompteurChgtMoteur() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompteurChgtMoteur(), Atelier_.compteurChgtMoteur));
            }
            if (criteria.getCompteurCarrosserie() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompteurCarrosserie(), Atelier_.compteurCarrosserie));
            }
            if (criteria.getCompteurHelisse() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompteurHelisse(), Atelier_.compteurHelisse));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Atelier_.date));
            }
            if (criteria.getAvionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAvionsId(), root -> root.join(Atelier_.avions, JoinType.LEFT).get(Avion_.id))
                    );
            }
        }
        return specification;
    }
}
