package net.atos.etax.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * The Extension information for setting up and maintaining users.
 */
@ApiModel(description = "The Extension information for setting up and maintaining users.")
@Entity
@Table(name = "adm_user_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * STD[ADM_SECTION], name of the administrative Section this user belongs to
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "STD[ADM_SECTION], name of the administrative Section this user belongs to", required = true)
    @Column(name = "cstd_adm_section", length = 40, nullable = false)
    private String cstdAdmSection;

    /**
     * STD[SECURITY_LEVEL], ID of this user\'s Security Level
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "STD[SECURITY_LEVEL], ID of this user\'s Security Level", required = true)
    @Column(name = "cstd_security_level", length = 40, nullable = false)
    private String cstdSecurityLevel;

    /**
     * STD[USER_TYPE], type of user
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "STD[USER_TYPE], type of user", required = true)
    @Column(name = "cstd_user_type", length = 40, nullable = false)
    private String cstdUserType;

    /**
     * General User Description - Free Format
     */
    @Size(max = 200)
    @ApiModelProperty(value = "General User Description - Free Format")
    @Column(name = "description", length = 200)
    private String description;

    /**
     * Middle name
     */
    @Size(max = 50)
    @ApiModelProperty(value = "Middle name")
    @Column(name = "middle_name", length = 50)
    private String middleName;

    /**
     * STD[GENDER], user gender
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "STD[GENDER], user gender", required = true)
    @Column(name = "gender", length = 40, nullable = false)
    private String gender;

    /**
     * Phone number for user
     */
    @Size(max = 50)
    @ApiModelProperty(value = "Phone number for user")
    @Column(name = "phone_num", length = 50)
    private String phoneNum;

    /**
     * Fax number for user
     */
    @Size(max = 50)
    @ApiModelProperty(value = "Fax number for user")
    @Column(name = "fax_num", length = 50)
    private String faxNum;

    /**
     * Date from which the user account is active, i.e. the user can login
     */
    @ApiModelProperty(value = "Date from which the user account is active, i.e. the user can login")
    @Column(name = "effiective_date")
    private ZonedDateTime effiectiveDate;

    /**
     * Date after which the user account is no longer active, i.e. the user can no longer login
     */
    @ApiModelProperty(value = "Date after which the user account is no longer active, i.e. the user can no longer login")
    @Column(name = "expiry_date")
    private ZonedDateTime expiryDate;

    /**
     * Indicates whether this user is blocked after 3 failed login attemptsn
     */
    @NotNull
    @ApiModelProperty(value = "Indicates whether this user is blocked after 3 failed login attemptsn", required = true)
    @Column(name = "blocked", nullable = false)
    private Boolean blocked;

    /**
     * Blocked reason
     */
    @NotNull
    @Size(max = 10)
    @ApiModelProperty(value = "Blocked reason", required = true)
    @Column(name = "blocked_reason", length = 10, nullable = false)
    private String blockedReason;

    /**
     * Indicates user is forced to change password at login(changed by administrator)
     */
    @NotNull
    @ApiModelProperty(value = "Indicates user is forced to change password at login(changed by administrator)", required = true)
    @Column(name = "forced_pwd_change", nullable = false)
    private Boolean forcedPwdChange;

    /**
     * STD[TITILES], Title to address the user
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "STD[TITILES], Title to address the user", required = true)
    @Column(name = "cstd_titles", length = 40, nullable = false)
    private String cstdTitles;

    /**
     * STD[STATUS]
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "STD[STATUS]", required = true)
    @Column(name = "cstd_status", length = 40, nullable = false)
    private String cstdStatus;

    /**
     * STD[ADM_DIVISON]
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "STD[ADM_DIVISON]", required = true)
    @Column(name = "cstd_adm_divsison", length = 40, nullable = false)
    private String cstdAdmDivsison;

    /**
     * Login status
     */
    @Size(max = 20)
    @ApiModelProperty(value = "Login status")
    @Column(name = "login_status", length = 20)
    private String loginStatus;

    /**
     * Attached office code
     */
    @Size(max = 7)
    @ApiModelProperty(value = "Attached office code")
    @Column(name = "office_code", length = 7)
    private String officeCode;

    /**
     * Login time
     */
    @ApiModelProperty(value = "Login time")
    @Column(name = "login_time")
    private ZonedDateTime loginTime;

    /**
     * Times of password error when logon attempts
     */
    @NotNull
    @Max(value = 3)
    @ApiModelProperty(value = "Times of password error when logon attempts", required = true)
    @Column(name = "attempt", nullable = false)
    private Integer attempt;

    /**
     * Flag indicates if it need to approve the change of user profiles
     */
    @NotNull
    @ApiModelProperty(value = "Flag indicates if it need to approve the change of user profiles", required = true)
    @Column(name = "need_approve", nullable = false)
    private Boolean needApprove;

    /**
     * Logout time
     */
    @ApiModelProperty(value = "Logout time")
    @Column(name = "logout_time")
    private ZonedDateTime logoutTime;

    /**
     * National ID
     */
    @NotNull
    @Size(max = 10)
    @ApiModelProperty(value = "National ID", required = true)
    @Column(name = "national_id", length = 10, nullable = false)
    private String nationalId;

    /**
     * STD[ORGANIZATION_GRADE], Organizational Grade
     */
    @Size(max = 40)
    @ApiModelProperty(value = "STD[ORGANIZATION_GRADE], Organizational Grade")
    @Column(name = "cstd_organization_grade", length = 40)
    private String cstdOrganizationGrade;

    /**
     * STD[EMPLOYMENT_TYPE], Employment Type
     */
    @Size(max = 40)
    @ApiModelProperty(value = "STD[EMPLOYMENT_TYPE], Employment Type")
    @Column(name = "cstd_employment_type", length = 40)
    private String cstdEmploymentType;

    /**
     * Manuscript used to XAdES signature
     */
    @ApiModelProperty(value = "Manuscript used to XAdES signature")
    @Lob
    @Column(name = "manu_script")
    private byte[] manuScript;

    @Column(name = "manu_script_content_type")
    private String manuScriptContentType;

    /**
     * Version to control concurrency
     */
    @ApiModelProperty(value = "Version to control concurrency")
    @Column(name = "cc_version")
    private Integer ccVersion;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCstdAdmSection() {
        return cstdAdmSection;
    }

    public UserInfo cstdAdmSection(String cstdAdmSection) {
        this.cstdAdmSection = cstdAdmSection;
        return this;
    }

    public void setCstdAdmSection(String cstdAdmSection) {
        this.cstdAdmSection = cstdAdmSection;
    }

    public String getCstdSecurityLevel() {
        return cstdSecurityLevel;
    }

    public UserInfo cstdSecurityLevel(String cstdSecurityLevel) {
        this.cstdSecurityLevel = cstdSecurityLevel;
        return this;
    }

    public void setCstdSecurityLevel(String cstdSecurityLevel) {
        this.cstdSecurityLevel = cstdSecurityLevel;
    }

    public String getCstdUserType() {
        return cstdUserType;
    }

    public UserInfo cstdUserType(String cstdUserType) {
        this.cstdUserType = cstdUserType;
        return this;
    }

    public void setCstdUserType(String cstdUserType) {
        this.cstdUserType = cstdUserType;
    }

    public String getDescription() {
        return description;
    }

    public UserInfo description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMiddleName() {
        return middleName;
    }

    public UserInfo middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGender() {
        return gender;
    }

    public UserInfo gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public UserInfo phoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
        return this;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getFaxNum() {
        return faxNum;
    }

    public UserInfo faxNum(String faxNum) {
        this.faxNum = faxNum;
        return this;
    }

    public void setFaxNum(String faxNum) {
        this.faxNum = faxNum;
    }

    public ZonedDateTime getEffiectiveDate() {
        return effiectiveDate;
    }

    public UserInfo effiectiveDate(ZonedDateTime effiectiveDate) {
        this.effiectiveDate = effiectiveDate;
        return this;
    }

    public void setEffiectiveDate(ZonedDateTime effiectiveDate) {
        this.effiectiveDate = effiectiveDate;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public UserInfo expiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean isBlocked() {
        return blocked;
    }

    public UserInfo blocked(Boolean blocked) {
        this.blocked = blocked;
        return this;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public String getBlockedReason() {
        return blockedReason;
    }

    public UserInfo blockedReason(String blockedReason) {
        this.blockedReason = blockedReason;
        return this;
    }

    public void setBlockedReason(String blockedReason) {
        this.blockedReason = blockedReason;
    }

    public Boolean isForcedPwdChange() {
        return forcedPwdChange;
    }

    public UserInfo forcedPwdChange(Boolean forcedPwdChange) {
        this.forcedPwdChange = forcedPwdChange;
        return this;
    }

    public void setForcedPwdChange(Boolean forcedPwdChange) {
        this.forcedPwdChange = forcedPwdChange;
    }

    public String getCstdTitles() {
        return cstdTitles;
    }

    public UserInfo cstdTitles(String cstdTitles) {
        this.cstdTitles = cstdTitles;
        return this;
    }

    public void setCstdTitles(String cstdTitles) {
        this.cstdTitles = cstdTitles;
    }

    public String getCstdStatus() {
        return cstdStatus;
    }

    public UserInfo cstdStatus(String cstdStatus) {
        this.cstdStatus = cstdStatus;
        return this;
    }

    public void setCstdStatus(String cstdStatus) {
        this.cstdStatus = cstdStatus;
    }

    public String getCstdAdmDivsison() {
        return cstdAdmDivsison;
    }

    public UserInfo cstdAdmDivsison(String cstdAdmDivsison) {
        this.cstdAdmDivsison = cstdAdmDivsison;
        return this;
    }

    public void setCstdAdmDivsison(String cstdAdmDivsison) {
        this.cstdAdmDivsison = cstdAdmDivsison;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public UserInfo loginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
        return this;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public UserInfo officeCode(String officeCode) {
        this.officeCode = officeCode;
        return this;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public ZonedDateTime getLoginTime() {
        return loginTime;
    }

    public UserInfo loginTime(ZonedDateTime loginTime) {
        this.loginTime = loginTime;
        return this;
    }

    public void setLoginTime(ZonedDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getAttempt() {
        return attempt;
    }

    public UserInfo attempt(Integer attempt) {
        this.attempt = attempt;
        return this;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    public Boolean isNeedApprove() {
        return needApprove;
    }

    public UserInfo needApprove(Boolean needApprove) {
        this.needApprove = needApprove;
        return this;
    }

    public void setNeedApprove(Boolean needApprove) {
        this.needApprove = needApprove;
    }

    public ZonedDateTime getLogoutTime() {
        return logoutTime;
    }

    public UserInfo logoutTime(ZonedDateTime logoutTime) {
        this.logoutTime = logoutTime;
        return this;
    }

    public void setLogoutTime(ZonedDateTime logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getNationalId() {
        return nationalId;
    }

    public UserInfo nationalId(String nationalId) {
        this.nationalId = nationalId;
        return this;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getCstdOrganizationGrade() {
        return cstdOrganizationGrade;
    }

    public UserInfo cstdOrganizationGrade(String cstdOrganizationGrade) {
        this.cstdOrganizationGrade = cstdOrganizationGrade;
        return this;
    }

    public void setCstdOrganizationGrade(String cstdOrganizationGrade) {
        this.cstdOrganizationGrade = cstdOrganizationGrade;
    }

    public String getCstdEmploymentType() {
        return cstdEmploymentType;
    }

    public UserInfo cstdEmploymentType(String cstdEmploymentType) {
        this.cstdEmploymentType = cstdEmploymentType;
        return this;
    }

    public void setCstdEmploymentType(String cstdEmploymentType) {
        this.cstdEmploymentType = cstdEmploymentType;
    }

    public byte[] getManuScript() {
        return manuScript;
    }

    public UserInfo manuScript(byte[] manuScript) {
        this.manuScript = manuScript;
        return this;
    }

    public void setManuScript(byte[] manuScript) {
        this.manuScript = manuScript;
    }

    public String getManuScriptContentType() {
        return manuScriptContentType;
    }

    public UserInfo manuScriptContentType(String manuScriptContentType) {
        this.manuScriptContentType = manuScriptContentType;
        return this;
    }

    public void setManuScriptContentType(String manuScriptContentType) {
        this.manuScriptContentType = manuScriptContentType;
    }

    public Integer getCcVersion() {
        return ccVersion;
    }

    public UserInfo ccVersion(Integer ccVersion) {
        this.ccVersion = ccVersion;
        return this;
    }

    public void setCcVersion(Integer ccVersion) {
        this.ccVersion = ccVersion;
    }

    public User getUser() {
        return user;
    }

    public UserInfo user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfo)) {
            return false;
        }
        return id != null && id.equals(((UserInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
            "id=" + getId() +
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
}
