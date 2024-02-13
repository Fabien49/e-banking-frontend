package com.fabienit.aeroclubpassion.service;

import com.fabienit.aeroclubpassion.domain.*; // for static metamodels
import com.fabienit.aeroclubpassion.domain.Revision;
import com.fabienit.aeroclubpassion.repository.RevisionRepository;
import com.fabienit.aeroclubpassion.service.criteria.RevisionCriteria;
import com.fabienit.aeroclubpassion.service.dto.RevisionDTO;
import com.fabienit.aeroclubpassion.service.mapper.RevisionMapper;
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
 * Service for executing complex queries for {@link Revision} entities in the database.
 * The main input is a {@link RevisionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RevisionDTO} or a {@link Page} of {@link RevisionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RevisionQueryService extends QueryService<Revision> {

    private final Logger log = LoggerFactory.getLogger(RevisionQueryService.class);

    private final RevisionRepository revisionRepository;

    private final RevisionMapper revisionMapper;

    public RevisionQueryService(RevisionRepository revisionRepository, RevisionMapper revisionMapper) {
        this.revisionRepository = revisionRepository;
        this.revisionMapper = revisionMapper;
    }

    /**
     * Return a {@link List} of {@link RevisionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RevisionDTO> findByCriteria(RevisionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Revision> specification = createSpecification(criteria);
        return revisionMapper.toDto(revisionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RevisionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RevisionDTO> findByCriteria(RevisionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Revision> specification = createSpecification(criteria);
        return revisionRepository.findAll(specification, page).map(revisionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RevisionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Revision> specification = createSpecification(criteria);
        return revisionRepository.count(specification);
    }

    /**
     * Function to convert {@link RevisionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Revision> createSpecification(RevisionCriteria criteria) {
        Specification<Revision> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Revision_.id));
            }
            if (criteria.getNiveaux() != null) {
                specification = specification.and(buildSpecification(criteria.getNiveaux(), Revision_.niveaux));
            }
            if (criteria.getPression() != null) {
                specification = specification.and(buildSpecification(criteria.getPression(), Revision_.pression));
            }
            if (criteria.getCarroserie() != null) {
                specification = specification.and(buildSpecification(criteria.getCarroserie(), Revision_.carroserie));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Revision_.date));
            }
            if (criteria.getAvionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAvionsId(), root -> root.join(Revision_.avions, JoinType.LEFT).get(Avion_.id))
                    );
            }
        }
        return specification;
    }
}
