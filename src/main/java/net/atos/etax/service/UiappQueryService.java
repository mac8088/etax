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

import net.atos.etax.domain.Uiapp;
import net.atos.etax.domain.*; // for static metamodels
import net.atos.etax.repository.UiappRepository;
import net.atos.etax.service.dto.UiappCriteria;

/**
 * Service for executing complex queries for {@link Uiapp} entities in the database.
 * The main input is a {@link UiappCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Uiapp} or a {@link Page} of {@link Uiapp} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UiappQueryService extends QueryService<Uiapp> {

    private final Logger log = LoggerFactory.getLogger(UiappQueryService.class);

    private final UiappRepository uiappRepository;

    public UiappQueryService(UiappRepository uiappRepository) {
        this.uiappRepository = uiappRepository;
    }

    /**
     * Return a {@link List} of {@link Uiapp} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Uiapp> findByCriteria(UiappCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Uiapp> specification = createSpecification(criteria);
        return uiappRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Uiapp} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Uiapp> findByCriteria(UiappCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Uiapp> specification = createSpecification(criteria);
        return uiappRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UiappCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Uiapp> specification = createSpecification(criteria);
        return uiappRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Uiapp> createSpecification(UiappCriteria criteria) {
        Specification<Uiapp> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Uiapp_.id));
            }
            if (criteria.getAppCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAppCode(), Uiapp_.appCode));
            }
            if (criteria.getAppName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAppName(), Uiapp_.appName));
            }
            if (criteria.getAppDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAppDesc(), Uiapp_.appDesc));
            }
            if (criteria.getCstdModule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdModule(), Uiapp_.cstdModule));
            }
            if (criteria.getAppMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAppMessage(), Uiapp_.appMessage));
            }
            if (criteria.getResourceId() != null) {
                specification = specification.and(buildSpecification(criteria.getResourceId(),
                    root -> root.join(Uiapp_.resources, JoinType.LEFT).get(Resource_.id)));
            }
        }
        return specification;
    }
}
