package net.atos.etax.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import net.atos.etax.domain.User;
import net.atos.etax.domain.*; // for static metamodels
import net.atos.etax.repository.UserRepository;
import net.atos.etax.service.dto.UserCriteria;

/**
 * Service for executing complex queries for {@link User} entities in the database.
 * The main input is a {@link UserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link User} or a {@link Page} of {@link User} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserQueryService extends QueryService<User> {

    private final Logger log = LoggerFactory.getLogger(UserQueryService.class);

    private final UserRepository UserRepository;

    public UserQueryService(UserRepository userRepository) {
        this.UserRepository = userRepository;
    }

    /**
     * Return a {@link List} of {@link User} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<User> findByCriteria(UserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<User> specification = createSpecification(criteria);
        return UserRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link User} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<User> findByCriteria(UserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<User> specification = createSpecification(criteria);
        return UserRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<User> specification = createSpecification(criteria);
        return UserRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<User> createSpecification(UserCriteria criteria) {
        Specification<User> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), User_.id));
            }
            if (criteria.getLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogin(), User_.login));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), User_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), User_.lastName));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), User_.email));
            }
            if (criteria.getImageRrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageRrl(), User_.imageUrl));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), User_.activated));
            }
            if (criteria.getLangKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLangKey(), User_.langKey));
            }
            if (criteria.getActivationKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivationKey(), User_.activationKey));
            }
            if (criteria.getResetKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResetKey(), User_.resetKey));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), User_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), User_.createdDate));
            }
            if (criteria.getResetDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResetDate(), User_.resetDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), User_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), User_.lastModifiedDate));
            }
        }
        return specification;
    }
}
