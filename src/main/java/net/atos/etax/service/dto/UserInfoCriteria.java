package net.atos.etax.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link net.atos.etax.domain.UserInfo} entity. This class is used
 * in {@link net.atos.etax.web.rest.UserInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cstdAdmSection;

    private StringFilter cstdSecurityLevel;

    private StringFilter cstdUserType;

    private StringFilter description;

    private StringFilter middleName;

    private StringFilter gender;

    private StringFilter phoneNum;

    private StringFilter faxNum;

    private ZonedDateTimeFilter effiectiveDate;

    private ZonedDateTimeFilter expiryDate;

    private BooleanFilter blocked;

    private StringFilter blockedReason;

    private BooleanFilter forcedPwdChange;

    private StringFilter cstdTitles;

    private StringFilter cstdStatus;

    private StringFilter cstdAdmDivsison;

    private StringFilter loginStatus;

    private ZonedDateTimeFilter loginTime;

    private IntegerFilter attempt;

    private BooleanFilter needApprove;

    private ZonedDateTimeFilter logoutTime;

    private StringFilter nationalId;

    private StringFilter cstdOrganizationGrade;

    private StringFilter cstdEmploymentType;

    private IntegerFilter ccVersion;

    private LongFilter userId;

    public UserInfoCriteria(){
    }

    public UserInfoCriteria(UserInfoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.cstdAdmSection = other.cstdAdmSection == null ? null : other.cstdAdmSection.copy();
        this.cstdSecurityLevel = other.cstdSecurityLevel == null ? null : other.cstdSecurityLevel.copy();
        this.cstdUserType = other.cstdUserType == null ? null : other.cstdUserType.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.phoneNum = other.phoneNum == null ? null : other.phoneNum.copy();
        this.faxNum = other.faxNum == null ? null : other.faxNum.copy();
        this.effiectiveDate = other.effiectiveDate == null ? null : other.effiectiveDate.copy();
        this.expiryDate = other.expiryDate == null ? null : other.expiryDate.copy();
        this.blocked = other.blocked == null ? null : other.blocked.copy();
        this.blockedReason = other.blockedReason == null ? null : other.blockedReason.copy();
        this.forcedPwdChange = other.forcedPwdChange == null ? null : other.forcedPwdChange.copy();
        this.cstdTitles = other.cstdTitles == null ? null : other.cstdTitles.copy();
        this.cstdStatus = other.cstdStatus == null ? null : other.cstdStatus.copy();
        this.cstdAdmDivsison = other.cstdAdmDivsison == null ? null : other.cstdAdmDivsison.copy();
        this.loginStatus = other.loginStatus == null ? null : other.loginStatus.copy();
        this.loginTime = other.loginTime == null ? null : other.loginTime.copy();
        this.attempt = other.attempt == null ? null : other.attempt.copy();
        this.needApprove = other.needApprove == null ? null : other.needApprove.copy();
        this.logoutTime = other.logoutTime == null ? null : other.logoutTime.copy();
        this.nationalId = other.nationalId == null ? null : other.nationalId.copy();
        this.cstdOrganizationGrade = other.cstdOrganizationGrade == null ? null : other.cstdOrganizationGrade.copy();
        this.cstdEmploymentType = other.cstdEmploymentType == null ? null : other.cstdEmploymentType.copy();
        this.ccVersion = other.ccVersion == null ? null : other.ccVersion.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public UserInfoCriteria copy() {
        return new UserInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCstdAdmSection() {
        return cstdAdmSection;
    }

    public void setCstdAdmSection(StringFilter cstdAdmSection) {
        this.cstdAdmSection = cstdAdmSection;
    }

    public StringFilter getCstdSecurityLevel() {
        return cstdSecurityLevel;
    }

    public void setCstdSecurityLevel(StringFilter cstdSecurityLevel) {
        this.cstdSecurityLevel = cstdSecurityLevel;
    }

    public StringFilter getCstdUserType() {
        return cstdUserType;
    }

    public void setCstdUserType(StringFilter cstdUserType) {
        this.cstdUserType = cstdUserType;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getMiddleName() {
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
    }

    public StringFilter getGender() {
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public StringFilter getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(StringFilter phoneNum) {
        this.phoneNum = phoneNum;
    }

    public StringFilter getFaxNum() {
        return faxNum;
    }

    public void setFaxNum(StringFilter faxNum) {
        this.faxNum = faxNum;
    }

    public ZonedDateTimeFilter getEffiectiveDate() {
        return effiectiveDate;
    }

    public void setEffiectiveDate(ZonedDateTimeFilter effiectiveDate) {
        this.effiectiveDate = effiectiveDate;
    }

    public ZonedDateTimeFilter getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(ZonedDateTimeFilter expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BooleanFilter getBlocked() {
        return blocked;
    }

    public void setBlocked(BooleanFilter blocked) {
        this.blocked = blocked;
    }

    public StringFilter getBlockedReason() {
        return blockedReason;
    }

    public void setBlockedReason(StringFilter blockedReason) {
        this.blockedReason = blockedReason;
    }

    public BooleanFilter getForcedPwdChange() {
        return forcedPwdChange;
    }

    public void setForcedPwdChange(BooleanFilter forcedPwdChange) {
        this.forcedPwdChange = forcedPwdChange;
    }

    public StringFilter getCstdTitles() {
        return cstdTitles;
    }

    public void setCstdTitles(StringFilter cstdTitles) {
        this.cstdTitles = cstdTitles;
    }

    public StringFilter getCstdStatus() {
        return cstdStatus;
    }

    public void setCstdStatus(StringFilter cstdStatus) {
        this.cstdStatus = cstdStatus;
    }

    public StringFilter getCstdAdmDivsison() {
        return cstdAdmDivsison;
    }

    public void setCstdAdmDivsison(StringFilter cstdAdmDivsison) {
        this.cstdAdmDivsison = cstdAdmDivsison;
    }

    public StringFilter getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(StringFilter loginStatus) {
        this.loginStatus = loginStatus;
    }

    public ZonedDateTimeFilter getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(ZonedDateTimeFilter loginTime) {
        this.loginTime = loginTime;
    }

    public IntegerFilter getAttempt() {
        return attempt;
    }

    public void setAttempt(IntegerFilter attempt) {
        this.attempt = attempt;
    }

    public BooleanFilter getNeedApprove() {
        return needApprove;
    }

    public void setNeedApprove(BooleanFilter needApprove) {
        this.needApprove = needApprove;
    }

    public ZonedDateTimeFilter getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(ZonedDateTimeFilter logoutTime) {
        this.logoutTime = logoutTime;
    }

    public StringFilter getNationalId() {
        return nationalId;
    }

    public void setNationalId(StringFilter nationalId) {
        this.nationalId = nationalId;
    }

    public StringFilter getCstdOrganizationGrade() {
        return cstdOrganizationGrade;
    }

    public void setCstdOrganizationGrade(StringFilter cstdOrganizationGrade) {
        this.cstdOrganizationGrade = cstdOrganizationGrade;
    }

    public StringFilter getCstdEmploymentType() {
        return cstdEmploymentType;
    }

    public void setCstdEmploymentType(StringFilter cstdEmploymentType) {
        this.cstdEmploymentType = cstdEmploymentType;
    }

    public IntegerFilter getCcVersion() {
        return ccVersion;
    }

    public void setCcVersion(IntegerFilter ccVersion) {
        this.ccVersion = ccVersion;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserInfoCriteria that = (UserInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cstdAdmSection, that.cstdAdmSection) &&
            Objects.equals(cstdSecurityLevel, that.cstdSecurityLevel) &&
            Objects.equals(cstdUserType, that.cstdUserType) &&
            Objects.equals(description, that.description) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(phoneNum, that.phoneNum) &&
            Objects.equals(faxNum, that.faxNum) &&
            Objects.equals(effiectiveDate, that.effiectiveDate) &&
            Objects.equals(expiryDate, that.expiryDate) &&
            Objects.equals(blocked, that.blocked) &&
            Objects.equals(blockedReason, that.blockedReason) &&
            Objects.equals(forcedPwdChange, that.forcedPwdChange) &&
            Objects.equals(cstdTitles, that.cstdTitles) &&
            Objects.equals(cstdStatus, that.cstdStatus) &&
            Objects.equals(cstdAdmDivsison, that.cstdAdmDivsison) &&
            Objects.equals(loginStatus, that.loginStatus) &&
            Objects.equals(loginTime, that.loginTime) &&
            Objects.equals(attempt, that.attempt) &&
            Objects.equals(needApprove, that.needApprove) &&
            Objects.equals(logoutTime, that.logoutTime) &&
            Objects.equals(nationalId, that.nationalId) &&
            Objects.equals(cstdOrganizationGrade, that.cstdOrganizationGrade) &&
            Objects.equals(cstdEmploymentType, that.cstdEmploymentType) &&
            Objects.equals(ccVersion, that.ccVersion) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cstdAdmSection,
        cstdSecurityLevel,
        cstdUserType,
        description,
        middleName,
        gender,
        phoneNum,
        faxNum,
        effiectiveDate,
        expiryDate,
        blocked,
        blockedReason,
        forcedPwdChange,
        cstdTitles,
        cstdStatus,
        cstdAdmDivsison,
        loginStatus,
        loginTime,
        attempt,
        needApprove,
        logoutTime,
        nationalId,
        cstdOrganizationGrade,
        cstdEmploymentType,
        ccVersion,
        userId
        );
    }

    @Override
    public String toString() {
        return "UserInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cstdAdmSection != null ? "cstdAdmSection=" + cstdAdmSection + ", " : "") +
                (cstdSecurityLevel != null ? "cstdSecurityLevel=" + cstdSecurityLevel + ", " : "") +
                (cstdUserType != null ? "cstdUserType=" + cstdUserType + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (middleName != null ? "middleName=" + middleName + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (phoneNum != null ? "phoneNum=" + phoneNum + ", " : "") +
                (faxNum != null ? "faxNum=" + faxNum + ", " : "") +
                (effiectiveDate != null ? "effiectiveDate=" + effiectiveDate + ", " : "") +
                (expiryDate != null ? "expiryDate=" + expiryDate + ", " : "") +
                (blocked != null ? "blocked=" + blocked + ", " : "") +
                (blockedReason != null ? "blockedReason=" + blockedReason + ", " : "") +
                (forcedPwdChange != null ? "forcedPwdChange=" + forcedPwdChange + ", " : "") +
                (cstdTitles != null ? "cstdTitles=" + cstdTitles + ", " : "") +
                (cstdStatus != null ? "cstdStatus=" + cstdStatus + ", " : "") +
                (cstdAdmDivsison != null ? "cstdAdmDivsison=" + cstdAdmDivsison + ", " : "") +
                (loginStatus != null ? "loginStatus=" + loginStatus + ", " : "") +
                (loginTime != null ? "loginTime=" + loginTime + ", " : "") +
                (attempt != null ? "attempt=" + attempt + ", " : "") +
                (needApprove != null ? "needApprove=" + needApprove + ", " : "") +
                (logoutTime != null ? "logoutTime=" + logoutTime + ", " : "") +
                (nationalId != null ? "nationalId=" + nationalId + ", " : "") +
                (cstdOrganizationGrade != null ? "cstdOrganizationGrade=" + cstdOrganizationGrade + ", " : "") +
                (cstdEmploymentType != null ? "cstdEmploymentType=" + cstdEmploymentType + ", " : "") +
                (ccVersion != null ? "ccVersion=" + ccVersion + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
