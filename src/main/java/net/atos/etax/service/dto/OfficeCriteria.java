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
 * Criteria class for the {@link net.atos.etax.domain.Office} entity. This class is used
 * in {@link net.atos.etax.web.rest.OfficeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /offices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OfficeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cstdOfficeType;

    private StringFilter name;

    private StringFilter cstdClassifierCode;

    private ZonedDateTimeFilter effectiveDate;

    private ZonedDateTimeFilter expiryDate;

    private StringFilter phone;

    private StringFilter fax;

    private StringFilter stl;

    private IntegerFilter mngOffice;

    private StringFilter pinCode;

    private StringFilter cstdWeekWorkingDay;

    private StringFilter officeCode;

    private StringFilter cstdOfficeSubType;

    private StringFilter cstdOfficeFuncType;

    private IntegerFilter ccVersion;

    public OfficeCriteria(){
    }

    public OfficeCriteria(OfficeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.cstdOfficeType = other.cstdOfficeType == null ? null : other.cstdOfficeType.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.cstdClassifierCode = other.cstdClassifierCode == null ? null : other.cstdClassifierCode.copy();
        this.effectiveDate = other.effectiveDate == null ? null : other.effectiveDate.copy();
        this.expiryDate = other.expiryDate == null ? null : other.expiryDate.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.stl = other.stl == null ? null : other.stl.copy();
        this.mngOffice = other.mngOffice == null ? null : other.mngOffice.copy();
        this.pinCode = other.pinCode == null ? null : other.pinCode.copy();
        this.cstdWeekWorkingDay = other.cstdWeekWorkingDay == null ? null : other.cstdWeekWorkingDay.copy();
        this.officeCode = other.officeCode == null ? null : other.officeCode.copy();
        this.cstdOfficeSubType = other.cstdOfficeSubType == null ? null : other.cstdOfficeSubType.copy();
        this.cstdOfficeFuncType = other.cstdOfficeFuncType == null ? null : other.cstdOfficeFuncType.copy();
        this.ccVersion = other.ccVersion == null ? null : other.ccVersion.copy();
    }

    @Override
    public OfficeCriteria copy() {
        return new OfficeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCstdOfficeType() {
        return cstdOfficeType;
    }

    public void setCstdOfficeType(StringFilter cstdOfficeType) {
        this.cstdOfficeType = cstdOfficeType;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getCstdClassifierCode() {
        return cstdClassifierCode;
    }

    public void setCstdClassifierCode(StringFilter cstdClassifierCode) {
        this.cstdClassifierCode = cstdClassifierCode;
    }

    public ZonedDateTimeFilter getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(ZonedDateTimeFilter effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public ZonedDateTimeFilter getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(ZonedDateTimeFilter expiryDate) {
        this.expiryDate = expiryDate;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getFax() {
        return fax;
    }

    public void setFax(StringFilter fax) {
        this.fax = fax;
    }

    public StringFilter getStl() {
        return stl;
    }

    public void setStl(StringFilter stl) {
        this.stl = stl;
    }

    public IntegerFilter getMngOffice() {
        return mngOffice;
    }

    public void setMngOffice(IntegerFilter mngOffice) {
        this.mngOffice = mngOffice;
    }

    public StringFilter getPinCode() {
        return pinCode;
    }

    public void setPinCode(StringFilter pinCode) {
        this.pinCode = pinCode;
    }

    public StringFilter getCstdWeekWorkingDay() {
        return cstdWeekWorkingDay;
    }

    public void setCstdWeekWorkingDay(StringFilter cstdWeekWorkingDay) {
        this.cstdWeekWorkingDay = cstdWeekWorkingDay;
    }

    public StringFilter getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(StringFilter officeCode) {
        this.officeCode = officeCode;
    }

    public StringFilter getCstdOfficeSubType() {
        return cstdOfficeSubType;
    }

    public void setCstdOfficeSubType(StringFilter cstdOfficeSubType) {
        this.cstdOfficeSubType = cstdOfficeSubType;
    }

    public StringFilter getCstdOfficeFuncType() {
        return cstdOfficeFuncType;
    }

    public void setCstdOfficeFuncType(StringFilter cstdOfficeFuncType) {
        this.cstdOfficeFuncType = cstdOfficeFuncType;
    }

    public IntegerFilter getCcVersion() {
        return ccVersion;
    }

    public void setCcVersion(IntegerFilter ccVersion) {
        this.ccVersion = ccVersion;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OfficeCriteria that = (OfficeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cstdOfficeType, that.cstdOfficeType) &&
            Objects.equals(name, that.name) &&
            Objects.equals(cstdClassifierCode, that.cstdClassifierCode) &&
            Objects.equals(effectiveDate, that.effectiveDate) &&
            Objects.equals(expiryDate, that.expiryDate) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(stl, that.stl) &&
            Objects.equals(mngOffice, that.mngOffice) &&
            Objects.equals(pinCode, that.pinCode) &&
            Objects.equals(cstdWeekWorkingDay, that.cstdWeekWorkingDay) &&
            Objects.equals(officeCode, that.officeCode) &&
            Objects.equals(cstdOfficeSubType, that.cstdOfficeSubType) &&
            Objects.equals(cstdOfficeFuncType, that.cstdOfficeFuncType) &&
            Objects.equals(ccVersion, that.ccVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cstdOfficeType,
        name,
        cstdClassifierCode,
        effectiveDate,
        expiryDate,
        phone,
        fax,
        stl,
        mngOffice,
        pinCode,
        cstdWeekWorkingDay,
        officeCode,
        cstdOfficeSubType,
        cstdOfficeFuncType,
        ccVersion
        );
    }

    @Override
    public String toString() {
        return "OfficeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cstdOfficeType != null ? "cstdOfficeType=" + cstdOfficeType + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (cstdClassifierCode != null ? "cstdClassifierCode=" + cstdClassifierCode + ", " : "") +
                (effectiveDate != null ? "effectiveDate=" + effectiveDate + ", " : "") +
                (expiryDate != null ? "expiryDate=" + expiryDate + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (fax != null ? "fax=" + fax + ", " : "") +
                (stl != null ? "stl=" + stl + ", " : "") +
                (mngOffice != null ? "mngOffice=" + mngOffice + ", " : "") +
                (pinCode != null ? "pinCode=" + pinCode + ", " : "") +
                (cstdWeekWorkingDay != null ? "cstdWeekWorkingDay=" + cstdWeekWorkingDay + ", " : "") +
                (officeCode != null ? "officeCode=" + officeCode + ", " : "") +
                (cstdOfficeSubType != null ? "cstdOfficeSubType=" + cstdOfficeSubType + ", " : "") +
                (cstdOfficeFuncType != null ? "cstdOfficeFuncType=" + cstdOfficeFuncType + ", " : "") +
                (ccVersion != null ? "ccVersion=" + ccVersion + ", " : "") +
            "}";
    }

}
