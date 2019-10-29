package net.atos.etax.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import net.atos.etax.domain.User;

/**
 * A user and user-info
 */
public class UserInfoDTO implements Serializable {
  
    private Long id;
    
    /**
     * STD[ADM_SECTION], name of the administrative Section this user belongs to
     */
    @NotNull
    @Size(max = 40)
    private String cstdAdmSection;

    /**
     * STD[SECURITY_LEVEL], ID of this user\'s Security Level
     */
    @NotNull
    @Size(max = 40)
    private String cstdSecurityLevel;

    /**
     * STD[USER_TYPE], type of user
     */
    @NotNull
    @Size(max = 40)
    private String cstdUserType;

    /**
     * General User Description - Free Format
     */
    @Size(max = 200)
    private String description;

    /**
     * Middle name
     */
    @Size(max = 50)
    private String middleName;

    /**
     * STD[GENDER], user gender
     */
    @NotNull
    @Size(max = 40)
    private String gender;

    /**
     * Phone number for user
     */
    @Size(max = 50)
    private String phoneNum;

    /**
     * Fax number for user
     */
    @Size(max = 50)
    private String faxNum;

    /**
     * Date from which the user account is active, i.e. the user can login
     */
    private ZonedDateTime effiectiveDate;

    /**
     * Date after which the user account is no longer active, i.e. the user can no longer login
     */
    private ZonedDateTime expiryDate;

    /**
     * Indicates whether this user is blocked after 3 failed login attemptsn
     */
    @NotNull
    private Boolean blocked;

    /**
     * Blocked reason
     */
    @NotNull
    @Size(max = 10)
    private String blockedReason;

    /**
     * Indicates user is forced to change password at login(changed by administrator)
     */
    @NotNull
    private Boolean forcedPwdChange;

    /**
     * STD[TITILES], Title to address the user
     */
    @NotNull
    @Size(max = 40)
    private String cstdTitles;

    /**
     * STD[STATUS]
     */
    @NotNull
    @Size(max = 40)
    private String cstdStatus;

    /**
     * STD[ADM_DIVISON]
     */
    @NotNull
    @Size(max = 40)
    private String cstdAdmDivsison;

    /**
     * Login status
     */
    @Size(max = 20)
    private String loginStatus;

    /**
     * Attached office code
     */
    @Size(max = 7)
    private String officeCode;

    /**
     * Login time
     */
    private ZonedDateTime loginTime;

    /**
     * Times of password error when logon attempts
     */
    @NotNull
    @Max(value = 3)
    private Integer attempt;

    /**
     * Flag indicates if it need to approve the change of user profiles
     */
    @NotNull
    private Boolean needApprove;

    /**
     * Logout time
     */
    private ZonedDateTime logoutTime;

    /**
     * National ID
     */
    @NotNull
    @Size(max = 10)
    private String nationalId;

    /**
     * STD[ORGANIZATION_GRADE], Organizational Grade
     */
    @Size(max = 40)
    private String cstdOrganizationGrade;

    /**
     * STD[EMPLOYMENT_TYPE], Employment Type
     */
    @Size(max = 40)
    private String cstdEmploymentType;

    /**
     * Manuscript used to XAdES signature
     */
    private byte[] manuScript;

    private String manuScriptContentType;

    /**
     * Version to control concurrency
     */
    private Integer ccVersion;
    
    private User user;
    
    private Set<String> authorities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCstdAdmSection() {
        return cstdAdmSection;
    }

    public UserInfoDTO cstdAdmSection(String cstdAdmSection) {
        this.cstdAdmSection = cstdAdmSection;
        return this;
    }

    public void setCstdAdmSection(String cstdAdmSection) {
        this.cstdAdmSection = cstdAdmSection;
    }

    public String getCstdSecurityLevel() {
        return cstdSecurityLevel;
    }

    public UserInfoDTO cstdSecurityLevel(String cstdSecurityLevel) {
        this.cstdSecurityLevel = cstdSecurityLevel;
        return this;
    }

    public void setCstdSecurityLevel(String cstdSecurityLevel) {
        this.cstdSecurityLevel = cstdSecurityLevel;
    }

    public String getCstdUserType() {
        return cstdUserType;
    }

    public UserInfoDTO cstdUserType(String cstdUserType) {
        this.cstdUserType = cstdUserType;
        return this;
    }

    public void setCstdUserType(String cstdUserType) {
        this.cstdUserType = cstdUserType;
    }

    public String getDescription() {
        return description;
    }

    public UserInfoDTO description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMiddleName() {
        return middleName;
    }

    public UserInfoDTO middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGender() {
        return gender;
    }

    public UserInfoDTO gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public UserInfoDTO phoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
        return this;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getFaxNum() {
        return faxNum;
    }

    public UserInfoDTO faxNum(String faxNum) {
        this.faxNum = faxNum;
        return this;
    }

    public void setFaxNum(String faxNum) {
        this.faxNum = faxNum;
    }

    public ZonedDateTime getEffiectiveDate() {
        return effiectiveDate;
    }

    public UserInfoDTO effiectiveDate(ZonedDateTime effiectiveDate) {
        this.effiectiveDate = effiectiveDate;
        return this;
    }

    public void setEffiectiveDate(ZonedDateTime effiectiveDate) {
        this.effiectiveDate = effiectiveDate;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public UserInfoDTO expiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean isBlocked() {
        return blocked;
    }

    public UserInfoDTO blocked(Boolean blocked) {
        this.blocked = blocked;
        return this;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public String getBlockedReason() {
        return blockedReason;
    }

    public UserInfoDTO blockedReason(String blockedReason) {
        this.blockedReason = blockedReason;
        return this;
    }

    public void setBlockedReason(String blockedReason) {
        this.blockedReason = blockedReason;
    }

    public Boolean isForcedPwdChange() {
        return forcedPwdChange;
    }

    public UserInfoDTO forcedPwdChange(Boolean forcedPwdChange) {
        this.forcedPwdChange = forcedPwdChange;
        return this;
    }

    public void setForcedPwdChange(Boolean forcedPwdChange) {
        this.forcedPwdChange = forcedPwdChange;
    }

    public String getCstdTitles() {
        return cstdTitles;
    }

    public UserInfoDTO cstdTitles(String cstdTitles) {
        this.cstdTitles = cstdTitles;
        return this;
    }

    public void setCstdTitles(String cstdTitles) {
        this.cstdTitles = cstdTitles;
    }

    public String getCstdStatus() {
        return cstdStatus;
    }

    public UserInfoDTO cstdStatus(String cstdStatus) {
        this.cstdStatus = cstdStatus;
        return this;
    }

    public void setCstdStatus(String cstdStatus) {
        this.cstdStatus = cstdStatus;
    }

    public String getCstdAdmDivsison() {
        return cstdAdmDivsison;
    }

    public UserInfoDTO cstdAdmDivsison(String cstdAdmDivsison) {
        this.cstdAdmDivsison = cstdAdmDivsison;
        return this;
    }

    public void setCstdAdmDivsison(String cstdAdmDivsison) {
        this.cstdAdmDivsison = cstdAdmDivsison;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public UserInfoDTO loginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
        return this;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public UserInfoDTO officeCode(String officeCode) {
        this.officeCode = officeCode;
        return this;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public ZonedDateTime getLoginTime() {
        return loginTime;
    }

    public UserInfoDTO loginTime(ZonedDateTime loginTime) {
        this.loginTime = loginTime;
        return this;
    }

    public void setLoginTime(ZonedDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getAttempt() {
        return attempt;
    }

    public UserInfoDTO attempt(Integer attempt) {
        this.attempt = attempt;
        return this;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    public Boolean isNeedApprove() {
        return needApprove;
    }

    public UserInfoDTO needApprove(Boolean needApprove) {
        this.needApprove = needApprove;
        return this;
    }

    public void setNeedApprove(Boolean needApprove) {
        this.needApprove = needApprove;
    }

    public ZonedDateTime getLogoutTime() {
        return logoutTime;
    }

    public UserInfoDTO logoutTime(ZonedDateTime logoutTime) {
        this.logoutTime = logoutTime;
        return this;
    }

    public void setLogoutTime(ZonedDateTime logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getNationalId() {
        return nationalId;
    }

    public UserInfoDTO nationalId(String nationalId) {
        this.nationalId = nationalId;
        return this;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getCstdOrganizationGrade() {
        return cstdOrganizationGrade;
    }

    public UserInfoDTO cstdOrganizationGrade(String cstdOrganizationGrade) {
        this.cstdOrganizationGrade = cstdOrganizationGrade;
        return this;
    }

    public void setCstdOrganizationGrade(String cstdOrganizationGrade) {
        this.cstdOrganizationGrade = cstdOrganizationGrade;
    }

    public String getCstdEmploymentType() {
        return cstdEmploymentType;
    }

    public UserInfoDTO cstdEmploymentType(String cstdEmploymentType) {
        this.cstdEmploymentType = cstdEmploymentType;
        return this;
    }

    public void setCstdEmploymentType(String cstdEmploymentType) {
        this.cstdEmploymentType = cstdEmploymentType;
    }

    public byte[] getManuScript() {
        return manuScript;
    }

    public UserInfoDTO manuScript(byte[] manuScript) {
        this.manuScript = manuScript;
        return this;
    }

    public void setManuScript(byte[] manuScript) {
        this.manuScript = manuScript;
    }

    public String getManuScriptContentType() {
        return manuScriptContentType;
    }

    public UserInfoDTO manuScriptContentType(String manuScriptContentType) {
        this.manuScriptContentType = manuScriptContentType;
        return this;
    }

    public void setManuScriptContentType(String manuScriptContentType) {
        this.manuScriptContentType = manuScriptContentType;
    }

    public Integer getCcVersion() {
        return ccVersion;
    }

    public UserInfoDTO ccVersion(Integer ccVersion) {
        this.ccVersion = ccVersion;
        return this;
    }

    public void setCcVersion(Integer ccVersion) {
        this.ccVersion = ccVersion;
    }
    
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfoDTO)) {
            return false;
        }
        return id != null && id.equals(((UserInfoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
             ", cstdAdmSection='" + getCstdAdmSection() + "'" +
            ", cstdSecurityLevel='" + getCstdSecurityLevel() + "'" +
            ", cstdUserType='" + getCstdUserType() + "'" +
            ", description='" + getDescription() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", gender='" + getGender() + "'" +
            ", phoneNum='" + getPhoneNum() + "'" +
            ", faxNum='" + getFaxNum() + "'" +
            ", effiectiveDate='" + getEffiectiveDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", blocked='" + isBlocked() + "'" +
            ", blockedReason='" + getBlockedReason() + "'" +
            ", forcedPwdChange='" + isForcedPwdChange() + "'" +
            ", cstdTitles='" + getCstdTitles() + "'" +
            ", cstdStatus='" + getCstdStatus() + "'" +
            ", cstdAdmDivsison='" + getCstdAdmDivsison() + "'" +
            ", loginStatus='" + getLoginStatus() + "'" +
            ", officeCode='" + getOfficeCode() + "'" +
            ", loginTime='" + getLoginTime() + "'" +
            ", attempt=" + getAttempt() +
            ", needApprove='" + isNeedApprove() + "'" +
            ", logoutTime='" + getLogoutTime() + "'" +
            ", nationalId='" + getNationalId() + "'" +
            ", cstdOrganizationGrade='" + getCstdOrganizationGrade() + "'" +
            ", cstdEmploymentType='" + getCstdEmploymentType() + "'" +
            ", manuScript='" + getManuScript() + "'" +
            ", manuScriptContentType='" + getManuScriptContentType() + "'" +
            ", ccVersion=" + getCcVersion() +
            "}";
    }

	public Set<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}
}
