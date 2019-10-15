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

import net.atos.etax.domain.UserInfo;
import net.atos.etax.domain.*; // for static metamodels
import net.atos.etax.repository.UserInfoRepository;
import net.atos.etax.service.dto.UserInfoCriteria;

/**
 * Service for executing complex queries for {@link UserInfo} entities in the database.
 * The main input is a {@link UserInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserInfo} or a {@link Page} of {@link UserInfo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserInfoQueryService extends QueryService<UserInfo> {

    private final Logger log = LoggerFactory.getLogger(UserInfoQueryService.class);

    private final UserInfoRepository userInfoRepository;

    public UserInfoQueryService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    /**
     * Return a {@link List} of {@link UserInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserInfo> findByCriteria(UserInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserInfo> specification = createSpecification(criteria);
        return userInfoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserInfo> findByCriteria(UserInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserInfo> specification = createSpecification(criteria);
        return userInfoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserInfo> specification = createSpecification(criteria);
        return userInfoRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<UserInfo> createSpecification(UserInfoCriteria criteria) {
        Specification<UserInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserInfo_.id));
            }
            if (criteria.getCstdAdmSection() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdAdmSection(), UserInfo_.cstdAdmSection));
            }
            if (criteria.getCstdSecurityLevel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdSecurityLevel(), UserInfo_.cstdSecurityLevel));
            }
            if (criteria.getCstdUserType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdUserType(), UserInfo_.cstdUserType));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), UserInfo_.description));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), UserInfo_.middleName));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), UserInfo_.gender));
            }
            if (criteria.getPhoneNum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNum(), UserInfo_.phoneNum));
            }
            if (criteria.getFaxNum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFaxNum(), UserInfo_.faxNum));
            }
            if (criteria.getEffiectiveDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEffiectiveDate(), UserInfo_.effiectiveDate));
            }
            if (criteria.getExpiryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryDate(), UserInfo_.expiryDate));
            }
            if (criteria.getBlocked() != null) {
                specification = specification.and(buildSpecification(criteria.getBlocked(), UserInfo_.blocked));
            }
            if (criteria.getBlockedReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBlockedReason(), UserInfo_.blockedReason));
            }
            if (criteria.getForcedPwdChange() != null) {
                specification = specification.and(buildSpecification(criteria.getForcedPwdChange(), UserInfo_.forcedPwdChange));
            }
            if (criteria.getCstdTitles() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdTitles(), UserInfo_.cstdTitles));
            }
            if (criteria.getCstdStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdStatus(), UserInfo_.cstdStatus));
            }
            if (criteria.getCstdAdmDivsison() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdAdmDivsison(), UserInfo_.cstdAdmDivsison));
            }
            if (criteria.getLoginStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoginStatus(), UserInfo_.loginStatus));
            }
            if (criteria.getOfficeCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOfficeCode(), UserInfo_.officeCode));
            }
            if (criteria.getLoginTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoginTime(), UserInfo_.loginTime));
            }
            if (criteria.getAttempt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAttempt(), UserInfo_.attempt));
            }
            if (criteria.getNeedApprove() != null) {
                specification = specification.and(buildSpecification(criteria.getNeedApprove(), UserInfo_.needApprove));
            }
            if (criteria.getLogoutTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLogoutTime(), UserInfo_.logoutTime));
            }
            if (criteria.getNationalId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationalId(), UserInfo_.nationalId));
            }
            if (criteria.getCstdOrganizationGrade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdOrganizationGrade(), UserInfo_.cstdOrganizationGrade));
            }
            if (criteria.getCstdEmploymentType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCstdEmploymentType(), UserInfo_.cstdEmploymentType));
            }
            if (criteria.getCcVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCcVersion(), UserInfo_.ccVersion));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(UserInfo_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
