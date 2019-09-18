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

import net.atos.bpm.domain.DeputyToDo;
import net.atos.bpm.domain.*; // for static metamodels
import net.atos.bpm.repository.DeputyToDoRepository;
import net.atos.bpm.service.dto.DeputyToDoCriteria;

/**
 * Service for executing complex queries for {@link DeputyToDo} entities in the database.
 * The main input is a {@link DeputyToDoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeputyToDo} or a {@link Page} of {@link DeputyToDo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeputyToDoQueryService extends QueryService<DeputyToDo> {

    private final Logger log = LoggerFactory.getLogger(DeputyToDoQueryService.class);

    private final DeputyToDoRepository deputyToDoRepository;

    public DeputyToDoQueryService(DeputyToDoRepository deputyToDoRepository) {
        this.deputyToDoRepository = deputyToDoRepository;
    }

    /**
     * Return a {@link List} of {@link DeputyToDo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeputyToDo> findByCriteria(DeputyToDoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DeputyToDo> specification = createSpecification(criteria);
        return deputyToDoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DeputyToDo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeputyToDo> findByCriteria(DeputyToDoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DeputyToDo> specification = createSpecification(criteria);
        return deputyToDoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeputyToDoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DeputyToDo> specification = createSpecification(criteria);
        return deputyToDoRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<DeputyToDo> createSpecification(DeputyToDoCriteria criteria) {
        Specification<DeputyToDo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DeputyToDo_.id));
            }
            if (criteria.getAssignorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssignorId(), DeputyToDo_.assignorId));
            }
            if (criteria.getAssignorName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssignorName(), DeputyToDo_.assignorName));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOwnerId(), DeputyToDo_.ownerId));
            }
            if (criteria.getOwnerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOwnerName(), DeputyToDo_.ownerName));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaskId(), DeputyToDo_.taskId));
            }
            if (criteria.getProcessInstanceId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProcessInstanceId(), DeputyToDo_.processInstanceId));
            }
            if (criteria.getBusinessKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBusinessKey(), DeputyToDo_.businessKey));
            }
            if (criteria.getBusinessClass() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBusinessClass(), DeputyToDo_.businessClass));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), DeputyToDo_.status));
            }
        }
        return specification;
    }
}
