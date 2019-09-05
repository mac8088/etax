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

import net.atos.etax.domain.Office;
import net.atos.etax.domain.*; // for static metamodels
import net.atos.etax.repository.OfficeRepository;
import net.atos.etax.service.dto.OfficeCriteria;

/**
 * Service for executing complex queries for {@link Office} entities in the database.
 * The main input is a {@link OfficeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Office} or a {@link Page} of {@link Office} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OfficeQueryService extends QueryService<Office> {

    private final Logger log = LoggerFactory.getLogger(OfficeQueryService.class);

    private final OfficeRepository officeRepository;

    public OfficeQueryService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    /**
     * Return a {@link List} of {@link Office} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Office> findByCriteria(OfficeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Office> specification = createSpecification(criteria);
        return officeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Office} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Office> findByCriteria(OfficeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Office> specification = createSpecification(criteria);
        return officeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OfficeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Office> specification = createSpecification(criteria);
        return officeRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Office> createSpecification(OfficeCriteria criteria) {
        Specification<Office> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Office_.id));
            }
            if (criteria.getCstdOfficeType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdOfficeType(), Office_.cstdOfficeType));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Office_.name));
            }
            if (criteria.getCstdClassifierCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdClassifierCode(), Office_.cstdClassifierCode));
            }
            if (criteria.getEffectiveDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffectiveDate(), Office_.effectiveDate));
            }
            if (criteria.getExpiryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryDate(), Office_.expiryDate));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Office_.phone));
            }
            if (criteria.getFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFax(), Office_.fax));
            }
            if (criteria.getStl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStl(), Office_.stl));
            }
            if (criteria.getMngOffice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMngOffice(), Office_.mngOffice));
            }
            if (criteria.getPinCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPinCode(), Office_.pinCode));
            }
            if (criteria.getCstdWeekWorkingDay() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdWeekWorkingDay(), Office_.cstdWeekWorkingDay));
            }
            if (criteria.getOfficeCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOfficeCode(), Office_.officeCode));
            }
            if (criteria.getCstdOfficeSubType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdOfficeSubType(), Office_.cstdOfficeSubType));
            }
            if (criteria.getCstdOfficeFuncType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdOfficeFuncType(), Office_.cstdOfficeFuncType));
            }
            if (criteria.getCcVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCcVersion(), Office_.ccVersion));
            }
        }
        return specification;
    }
}
