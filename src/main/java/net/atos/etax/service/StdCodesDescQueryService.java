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

import net.atos.etax.domain.StdCodesDesc;
import net.atos.etax.domain.*; // for static metamodels
import net.atos.etax.repository.StdCodesDescRepository;
import net.atos.etax.service.dto.StdCodesDescCriteria;

/**
 * Service for executing complex queries for {@link StdCodesDesc} entities in the database.
 * The main input is a {@link StdCodesDescCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StdCodesDesc} or a {@link Page} of {@link StdCodesDesc} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StdCodesDescQueryService extends QueryService<StdCodesDesc> {

    private final Logger log = LoggerFactory.getLogger(StdCodesDescQueryService.class);

    private final StdCodesDescRepository stdCodesDescRepository;

    public StdCodesDescQueryService(StdCodesDescRepository stdCodesDescRepository) {
        this.stdCodesDescRepository = stdCodesDescRepository;
    }

    /**
     * Return a {@link List} of {@link StdCodesDesc} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StdCodesDesc> findByCriteria(StdCodesDescCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StdCodesDesc> specification = createSpecification(criteria);
        return stdCodesDescRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StdCodesDesc} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StdCodesDesc> findByCriteria(StdCodesDescCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StdCodesDesc> specification = createSpecification(criteria);
        return stdCodesDescRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StdCodesDescCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StdCodesDesc> specification = createSpecification(criteria);
        return stdCodesDescRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<StdCodesDesc> createSpecification(StdCodesDescCriteria criteria) {
        Specification<StdCodesDesc> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StdCodesDesc_.id));
            }
            if (criteria.getGroupCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupCode(), StdCodesDesc_.groupCode));
            }
            if (criteria.getInternalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInternalCode(), StdCodesDesc_.internalCode));
            }
            if (criteria.getLangCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLangCode(), StdCodesDesc_.langCode));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), StdCodesDesc_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), StdCodesDesc_.endDate));
            }
            if (criteria.getExternalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExternalCode(), StdCodesDesc_.externalCode));
            }
            if (criteria.getCodeDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeDesc(), StdCodesDesc_.codeDesc));
            }
            if (criteria.getStdCodesId() != null) {
                specification = specification.and(buildSpecification(criteria.getStdCodesId(),
                    root -> root.join(StdCodesDesc_.stdCodes, JoinType.LEFT).get(StdCodes_.id)));
            }
        }
        return specification;
    }
}
