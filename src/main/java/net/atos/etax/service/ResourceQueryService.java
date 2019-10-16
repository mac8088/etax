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

import net.atos.etax.domain.Resource;
import net.atos.etax.domain.*; // for static metamodels
import net.atos.etax.repository.ResourceRepository;
import net.atos.etax.service.dto.ResourceCriteria;

/**
 * Service for executing complex queries for {@link Resource} entities in the database.
 * The main input is a {@link ResourceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Resource} or a {@link Page} of {@link Resource} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResourceQueryService extends QueryService<Resource> {

    private final Logger log = LoggerFactory.getLogger(ResourceQueryService.class);

    private final ResourceRepository resourceRepository;

    public ResourceQueryService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    /**
     * Return a {@link List} of {@link Resource} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Resource> findByCriteria(ResourceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Resource> specification = createSpecification(criteria);
        return resourceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Resource} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Resource> findByCriteria(ResourceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Resource> specification = createSpecification(criteria);
        return resourceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResourceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Resource> specification = createSpecification(criteria);
        return resourceRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Resource> createSpecification(ResourceCriteria criteria) {
        Specification<Resource> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Resource_.id));
            }
            if (criteria.getResCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResCode(), Resource_.resCode));
            }
            if (criteria.getResName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResName(), Resource_.resName));
            }
            if (criteria.getResType() != null) {
                specification = specification.and(buildSpecification(criteria.getResType(), Resource_.resType));
            }
            if (criteria.getAppDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAppDesc(), Resource_.appDesc));
            }
            if (criteria.getCstdModule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdModule(), Resource_.cstdModule));
            }
            if (criteria.getResContent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResContent(), Resource_.resContent));
            }
            if (criteria.getUiappId() != null) {
                specification = specification.and(buildSpecification(criteria.getUiappId(),
                    root -> root.join(Resource_.uiapps, JoinType.LEFT).get(Uiapp_.id)));
            }
        }
        return specification;
    }
}
