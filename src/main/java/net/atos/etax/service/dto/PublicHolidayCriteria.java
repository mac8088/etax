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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link net.atos.etax.domain.PublicHoliday} entity. This class is used
 * in {@link net.atos.etax.web.rest.PublicHolidayResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /public-holidays?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PublicHolidayCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cstdHolidayTypes;

    private StringFilter description;

    private LocalDateFilter date;

    private BooleanFilter workingFlag;

    private BooleanFilter countryWide;

    private IntegerFilter ccVersion;

    public PublicHolidayCriteria(){
    }

    public PublicHolidayCriteria(PublicHolidayCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.cstdHolidayTypes = other.cstdHolidayTypes == null ? null : other.cstdHolidayTypes.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.workingFlag = other.workingFlag == null ? null : other.workingFlag.copy();
        this.countryWide = other.countryWide == null ? null : other.countryWide.copy();
        this.ccVersion = other.ccVersion == null ? null : other.ccVersion.copy();
    }

    @Override
    public PublicHolidayCriteria copy() {
        return new PublicHolidayCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCstdHolidayTypes() {
        return cstdHolidayTypes;
    }

    public void setCstdHolidayTypes(StringFilter cstdHolidayTypes) {
        this.cstdHolidayTypes = cstdHolidayTypes;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public BooleanFilter getWorkingFlag() {
        return workingFlag;
    }

    public void setWorkingFlag(BooleanFilter workingFlag) {
        this.workingFlag = workingFlag;
    }

    public BooleanFilter getCountryWide() {
        return countryWide;
    }

    public void setCountryWide(BooleanFilter countryWide) {
        this.countryWide = countryWide;
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
        final PublicHolidayCriteria that = (PublicHolidayCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cstdHolidayTypes, that.cstdHolidayTypes) &&
            Objects.equals(description, that.description) &&
            Objects.equals(date, that.date) &&
            Objects.equals(workingFlag, that.workingFlag) &&
            Objects.equals(countryWide, that.countryWide) &&
            Objects.equals(ccVersion, that.ccVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cstdHolidayTypes,
        description,
        date,
        workingFlag,
        countryWide,
        ccVersion
        );
    }

    @Override
    public String toString() {
        return "PublicHolidayCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cstdHolidayTypes != null ? "cstdHolidayTypes=" + cstdHolidayTypes + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (workingFlag != null ? "workingFlag=" + workingFlag + ", " : "") +
                (countryWide != null ? "countryWide=" + countryWide + ", " : "") +
                (ccVersion != null ? "ccVersion=" + ccVersion + ", " : "") +
            "}";
    }

}
