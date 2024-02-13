package com.fabienit.aeroclubpassion.service;

import com.fabienit.aeroclubpassion.domain.*; // for static metamodels
import com.fabienit.aeroclubpassion.domain.Aeroclub;
import com.fabienit.aeroclubpassion.repository.AeroclubRepository;
import com.fabienit.aeroclubpassion.service.criteria.AeroclubCriteria;
import com.fabienit.aeroclubpassion.service.dto.AeroclubDTO;
import com.fabienit.aeroclubpassion.service.mapper.AeroclubMapper;
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
 * Service for executing complex queries for {@link Aeroclub} entities in the database.
 * The main input is a {@link AeroclubCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AeroclubDTO} or a {@link Page} of {@link AeroclubDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AeroclubQueryService extends QueryService<Aeroclub> {

    private final Logger log = LoggerFactory.getLogger(AeroclubQueryService.class);

    private final AeroclubRepository aeroclubRepository;

    private final AeroclubMapper aeroclubMapper;

    public AeroclubQueryService(AeroclubRepository aeroclubRepository, AeroclubMapper aeroclubMapper) {
        this.aeroclubRepository = aeroclubRepository;
        this.aeroclubMapper = aeroclubMapper;
    }

    /**
     * Return a {@link List} of {@link AeroclubDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AeroclubDTO> findByCriteria(AeroclubCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Aeroclub> specification = createSpecification(criteria);
        return aeroclubMapper.toDto(aeroclubRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AeroclubDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AeroclubDTO> findByCriteria(AeroclubCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Aeroclub> specification = createSpecification(criteria);
        return aeroclubRepository.findAll(specification, page).map(aeroclubMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AeroclubCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Aeroclub> specification = createSpecification(criteria);
        return aeroclubRepository.count(specification);
    }

    /**
     * Function to convert {@link AeroclubCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Aeroclub> createSpecification(AeroclubCriteria criteria) {
        Specification<Aeroclub> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Aeroclub_.id));
            }
            if (criteria.getOaci() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOaci(), Aeroclub_.oaci));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Aeroclub_.nom));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Aeroclub_.type));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), Aeroclub_.telephone));
            }
            if (criteria.getMail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMail(), Aeroclub_.mail));
            }
            if (criteria.getAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresse(), Aeroclub_.adresse));
            }
            if (criteria.getCodePostal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodePostal(), Aeroclub_.codePostal));
            }
            if (criteria.getCommune() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCommune(), Aeroclub_.commune));
            }
        }
        return specification;
    }
}
