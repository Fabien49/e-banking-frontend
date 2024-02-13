package com.fabienit.aeroclubpassion.service;

import com.fabienit.aeroclubpassion.domain.*; // for static metamodels
import com.fabienit.aeroclubpassion.domain.Tarif;
import com.fabienit.aeroclubpassion.repository.TarifRepository;
import com.fabienit.aeroclubpassion.service.criteria.TarifCriteria;
import com.fabienit.aeroclubpassion.service.dto.TarifDTO;
import com.fabienit.aeroclubpassion.service.mapper.TarifMapper;
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
 * Service for executing complex queries for {@link Tarif} entities in the database.
 * The main input is a {@link TarifCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TarifDTO} or a {@link Page} of {@link TarifDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TarifQueryService extends QueryService<Tarif> {

    private final Logger log = LoggerFactory.getLogger(TarifQueryService.class);

    private final TarifRepository tarifRepository;

    private final TarifMapper tarifMapper;

    public TarifQueryService(TarifRepository tarifRepository, TarifMapper tarifMapper) {
        this.tarifRepository = tarifRepository;
        this.tarifMapper = tarifMapper;
    }

    /**
     * Return a {@link List} of {@link TarifDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TarifDTO> findByCriteria(TarifCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tarif> specification = createSpecification(criteria);
        return tarifMapper.toDto(tarifRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TarifDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TarifDTO> findByCriteria(TarifCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tarif> specification = createSpecification(criteria);
        return tarifRepository.findAll(specification, page).map(tarifMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TarifCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tarif> specification = createSpecification(criteria);
        return tarifRepository.count(specification);
    }

    /**
     * Function to convert {@link TarifCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tarif> createSpecification(TarifCriteria criteria) {
        Specification<Tarif> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tarif_.id));
            }
            if (criteria.getTaxeAtterrissage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxeAtterrissage(), Tarif_.taxeAtterrissage));
            }
            if (criteria.getTaxeParking() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxeParking(), Tarif_.taxeParking));
            }
            if (criteria.getCarburant() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCarburant(), Tarif_.carburant));
            }
        }
        return specification;
    }
}
