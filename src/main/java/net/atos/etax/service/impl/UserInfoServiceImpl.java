package net.atos.etax.service.impl;

import net.atos.etax.service.UserInfoService;
import net.atos.etax.service.dto.UserInfoDTO;
import net.atos.etax.domain.UserInfo;
import net.atos.etax.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserInfo}.
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    private final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private final UserInfoRepository userInfoRepository;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    /**
     * Save a userInfo.
     *
     * @param userInfo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserInfo save(UserInfo userInfo) {
        log.debug("Request to save UserInfo : {}", userInfo);
        return userInfoRepository.save(userInfo);
    }

    public UserInfo save(UserInfoDTO userInfoDTO) {
        log.debug("Request to save UserInfo : {}", userInfoDTO);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userInfoDTO.getId());
        userInfo.setAttempt(userInfoDTO.getAttempt());
        userInfo.setBlocked(userInfoDTO.isBlocked());
        userInfo.setBlockedReason(userInfoDTO.getBlockedReason());
        userInfo.setCcVersion(userInfoDTO.getCcVersion());
        userInfo.setCstdAdmDivsison(userInfoDTO.getCstdAdmDivsison());
        userInfo.setCstdAdmSection(userInfoDTO.getCstdAdmSection());
        userInfo.setCstdEmploymentType(userInfoDTO.getCstdEmploymentType());
        userInfo.setCstdOrganizationGrade(userInfoDTO.getCstdOrganizationGrade());
        userInfo.setCstdSecurityLevel(userInfoDTO.getCstdSecurityLevel());
        userInfo.setCstdStatus(userInfoDTO.getCstdStatus());
        userInfo.setCstdTitles(userInfoDTO.getCstdTitles());
        userInfo.setCstdUserType(userInfoDTO.getCstdUserType());
        userInfo.setDescription(userInfoDTO.getDescription());
        userInfo.setEffiectiveDate(userInfoDTO.getEffiectiveDate());
        userInfo.setExpiryDate(userInfoDTO.getExpiryDate());
        userInfo.setFaxNum(userInfoDTO.getFaxNum());
        userInfo.setForcedPwdChange(userInfoDTO.isForcedPwdChange());
        userInfo.setGender(userInfoDTO.getGender());
        userInfo.setLoginStatus(userInfoDTO.getLoginStatus());
        userInfo.setMiddleName(userInfoDTO.getMiddleName());
        userInfo.setNationalId(userInfoDTO.getNationalId());
        userInfo.setNeedApprove(userInfoDTO.isNeedApprove());
        userInfo.setOfficeCode(userInfoDTO.getOfficeCode());
        userInfo.setPhoneNum(userInfoDTO.getPhoneNum());
        userInfo.setUser(userInfoDTO.getUser());
        if (userInfo.getId()==null) {
			userInfo.setCcVersion(0);
			userInfo.setNeedApprove(true);
		}
        return userInfoRepository.save(userInfo);
    }

    /**
     * Get all the userInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserInfo> findAll(Pageable pageable) {
        log.debug("Request to get all UserInfos");
        return userInfoRepository.findAll(pageable);
    }


    /**
     * Get one userInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserInfo> findOne(Long id) {
        log.debug("Request to get UserInfo : {}", id);
        return userInfoRepository.findById(id);
    }

    /**
     * Delete the userInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserInfo : {}", id);
        userInfoRepository.deleteById(id);
    }
}
