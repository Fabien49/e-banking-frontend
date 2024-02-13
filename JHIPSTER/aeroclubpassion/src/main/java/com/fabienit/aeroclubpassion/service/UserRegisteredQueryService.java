package com.fabienit.aeroclubpassion.service;

import com.fabienit.aeroclubpassion.domain.*; // for static metamodels
import com.fabienit.aeroclubpassion.domain.UserRegistered;
import com.fabienit.aeroclubpassion.repository.UserRegisteredRepository;
import com.fabienit.aeroclubpassion.service.criteria.UserRegisteredCriteria;
import com.fabienit.aeroclubpassion.service.dto.UserRegisteredDTO;
import com.fabienit.aeroclubpassion.service.mapper.UserRegisteredMapper;
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
 * Service for executing complex queries for {@link UserRegistered} entities in the database.
 * The main input is a {@link UserRegisteredCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserRegisteredDTO} or a {@link Page} of {@link UserRegisteredDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserRegisteredQueryService extends QueryService<UserRegistered> {

    private final Logger log = LoggerFactory.getLogger(UserRegisteredQueryService.class);

    private final UserRegisteredRepository userRegisteredRepository;

    private final UserRegisteredMapper userRegisteredMapper;

    public UserRegisteredQueryService(UserRegisteredRepository userRegisteredRepository, UserRegisteredMapper userRegisteredMapper) {
        this.userRegisteredRepository = userRegisteredRepository;
        this.userRegisteredMapper = userRegisteredMapper;
    }

    /**
     * Return a {@link List} of {@link UserRegisteredDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserRegisteredDTO> findByCriteria(UserRegisteredCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserRegistered> specification = createSpecification(criteria);
        return userRegisteredMapper.toDto(userRegisteredRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserRegisteredDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserRegisteredDTO> findByCriteria(UserRegisteredCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserRegistered> specification = createSpecification(criteria);
        return userRegisteredRepository.findAll(specification, page).map(userRegisteredMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserRegisteredCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserRegistered> specification = createSpecification(criteria);
        return userRegisteredRepository.count(specification);
    }

    /**
     * Function to convert {@link UserRegisteredCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserRegistered> createSpecification(UserRegisteredCriteria criteria) {
        Specification<UserRegistered> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserRegistered_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), UserRegistered_.nom));
            }
            if (criteria.getPrenom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenom(), UserRegistered_.prenom));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), UserRegistered_.telephone));
            }
            if (criteria.getMail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMail(), UserRegistered_.mail));
            }
            if (criteria.getAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresse(), UserRegistered_.adresse));
            }
            if (criteria.getCodePostal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodePostal(), UserRegistered_.codePostal));
            }
            if (criteria.getCommune() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCommune(), UserRegistered_.commune));
            }
            if (criteria.getHeureVol() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeureVol(), UserRegistered_.heureVol));
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUserId(), root -> root.join(UserRegistered_.user, JoinType.LEFT).get(User_.id))
                    );
            }
        }
        return specification;
    }
}
