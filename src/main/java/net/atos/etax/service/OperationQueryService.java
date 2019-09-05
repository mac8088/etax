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

import net.atos.etax.domain.Operation;
import net.atos.etax.domain.*; // for static metamodels
import net.atos.etax.repository.OperationRepository;
import net.atos.etax.service.dto.OperationCriteria;

/**
 * Service for executing complex queries for {@link Operation} entities in the database.
 * The main input is a {@link OperationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Operation} or a {@link Page} of {@link Operation} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OperationQueryService extends QueryService<Operation> {

    private final Logger log = LoggerFactory.getLogger(OperationQueryService.class);

    private final OperationRepository operationRepository;

    public OperationQueryService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    /**
     * Return a {@link List} of {@link Operation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Operation> findByCriteria(OperationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Operation> specification = createSpecification(criteria);
        return operationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Operation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Operation> findByCriteria(OperationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Operation> specification = createSpecification(criteria);
        return operationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OperationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Operation> specification = createSpecification(criteria);
        return operationRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Operation> createSpecification(OperationCriteria criteria) {
        Specification<Operation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Operation_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Operation_.date));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Operation_.description));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Operation_.amount));
            }
            if (criteria.getLabelId() != null) {
                specification = specification.and(buildSpecification(criteria.getLabelId(),
                    root -> root.join(Operation_.labels, JoinType.LEFT).get(Label_.id)));
            }
            if (criteria.getBankAccountId() != null) {
                specification = specification.and(buildSpecification(criteria.getBankAccountId(),
                    root -> root.join(Operation_.bankAccount, JoinType.LEFT).get(BankAccount_.id)));
            }
        }
        return specification;
    }
}
