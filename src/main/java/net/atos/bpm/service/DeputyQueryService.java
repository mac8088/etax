package net.atos.bpm.service;

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

import net.atos.bpm.domain.Deputy;
import net.atos.bpm.domain.*; // for static metamodels
import net.atos.bpm.repository.DeputyRepository;
import net.atos.bpm.service.dto.DeputyCriteria;

/**
 * Service for executing complex queries for {@link Deputy} entities in the database.
 * The main input is a {@link DeputyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Deputy} or a {@link Page} of {@link Deputy} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeputyQueryService extends QueryService<Deputy> {

    private final Logger log = LoggerFactory.getLogger(DeputyQueryService.class);

    private final DeputyRepository deputyRepository;

    public DeputyQueryService(DeputyRepository deputyRepository) {
        this.deputyRepository = deputyRepository;
    }

    /**
     * Return a {@link List} of {@link Deputy} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Deputy> findByCriteria(DeputyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Deputy> specification = createSpecification(criteria);
        return deputyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Deputy} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Deputy> findByCriteria(DeputyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Deputy> specification = createSpecification(criteria);
        return deputyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeputyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Deputy> specification = createSpecification(criteria);
        return deputyRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Deputy> createSpecification(DeputyCriteria criteria) {
        Specification<Deputy> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Deputy_.id));
            }
            if (criteria.getAssignorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssignorId(), Deputy_.assignorId));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOwnerId(), Deputy_.ownerId));
            }
            if (criteria.getDeputyType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeputyType(), Deputy_.deputyType));
            }
            if (criteria.getPeriodFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodFrom(), Deputy_.periodFrom));
            }
            if (criteria.getPeriodTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeriodTo(), Deputy_.periodTo));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Deputy_.createdDate));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Deputy_.lastModifiedDate));
            }
        }
        return specification;
    }
}
