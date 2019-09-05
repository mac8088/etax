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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link net.atos.etax.domain.StdCodesProp} entity. This class is used
 * in {@link net.atos.etax.web.rest.StdCodesPropResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /std-codes-props?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StdCodesPropCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter groupCode;

    private StringFilter internalCode;

    private StringFilter propCode;

    private ZonedDateTimeFilter startDate;

    private ZonedDateTimeFilter endDate;

    private LocalDateFilter valueDate;

    private StringFilter valueString;

    private BooleanFilter valueBool;

    private DoubleFilter valueNumber;

    public StdCodesPropCriteria(){
    }

    public StdCodesPropCriteria(StdCodesPropCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.groupCode = other.groupCode == null ? null : other.groupCode.copy();
        this.internalCode = other.internalCode == null ? null : other.internalCode.copy();
        this.propCode = other.propCode == null ? null : other.propCode.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.valueDate = other.valueDate == null ? null : other.valueDate.copy();
        this.valueString = other.valueString == null ? null : other.valueString.copy();
        this.valueBool = other.valueBool == null ? null : other.valueBool.copy();
        this.valueNumber = other.valueNumber == null ? null : other.valueNumber.copy();
    }

    @Override
    public StdCodesPropCriteria copy() {
        return new StdCodesPropCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(StringFilter groupCode) {
        this.groupCode = groupCode;
    }

    public StringFilter getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(StringFilter internalCode) {
        this.internalCode = internalCode;
    }

    public StringFilter getPropCode() {
        return propCode;
    }

    public void setPropCode(StringFilter propCode) {
        this.propCode = propCode;
    }

    public ZonedDateTimeFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTimeFilter startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTimeFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTimeFilter endDate) {
        this.endDate = endDate;
    }

    public LocalDateFilter getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDateFilter valueDate) {
        this.valueDate = valueDate;
    }

    public StringFilter getValueString() {
        return valueString;
    }

    public void setValueString(StringFilter valueString) {
        this.valueString = valueString;
    }

    public BooleanFilter getValueBool() {
        return valueBool;
    }

    public void setValueBool(BooleanFilter valueBool) {
        this.valueBool = valueBool;
    }

    public DoubleFilter getValueNumber() {
        return valueNumber;
    }

    public void setValueNumber(DoubleFilter valueNumber) {
        this.valueNumber = valueNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StdCodesPropCriteria that = (StdCodesPropCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(groupCode, that.groupCode) &&
            Objects.equals(internalCode, that.internalCode) &&
            Objects.equals(propCode, that.propCode) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(valueDate, that.valueDate) &&
            Objects.equals(valueString, that.valueString) &&
            Objects.equals(valueBool, that.valueBool) &&
            Objects.equals(valueNumber, that.valueNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        groupCode,
        internalCode,
        propCode,
        startDate,
        endDate,
        valueDate,
        valueString,
        valueBool,
        valueNumber
        );
    }

    @Override
    public String toString() {
        return "StdCodesPropCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (groupCode != null ? "groupCode=" + groupCode + ", " : "") +
                (internalCode != null ? "internalCode=" + internalCode + ", " : "") +
                (propCode != null ? "propCode=" + propCode + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (valueDate != null ? "valueDate=" + valueDate + ", " : "") +
                (valueString != null ? "valueString=" + valueString + ", " : "") +
                (valueBool != null ? "valueBool=" + valueBool + ", " : "") +
                (valueNumber != null ? "valueNumber=" + valueNumber + ", " : "") +
            "}";
    }

}
