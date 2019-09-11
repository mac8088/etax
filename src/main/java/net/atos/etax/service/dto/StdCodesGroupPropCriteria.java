package net.atos.etax.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import net.atos.etax.domain.enumeration.ValueTypeIndicator;
import net.atos.etax.domain.enumeration.OptionIndicator;
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
 * Criteria class for the {@link net.atos.etax.domain.StdCodesGroupProp} entity. This class is used
 * in {@link net.atos.etax.web.rest.StdCodesGroupPropResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /std-codes-group-props?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StdCodesGroupPropCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ValueTypeIndicator
     */
    public static class ValueTypeIndicatorFilter extends Filter<ValueTypeIndicator> {

        public ValueTypeIndicatorFilter() {
        }

        public ValueTypeIndicatorFilter(ValueTypeIndicatorFilter filter) {
            super(filter);
        }

        @Override
        public ValueTypeIndicatorFilter copy() {
            return new ValueTypeIndicatorFilter(this);
        }

    }
    /**
     * Class for filtering OptionIndicator
     */
    public static class OptionIndicatorFilter extends Filter<OptionIndicator> {

        public OptionIndicatorFilter() {
        }

        public OptionIndicatorFilter(OptionIndicatorFilter filter) {
            super(filter);
        }

        @Override
        public OptionIndicatorFilter copy() {
            return new OptionIndicatorFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter groupCode;

    private StringFilter propCode;

    private StringFilter propDesc;

    private ZonedDateTimeFilter startDate;

    private ZonedDateTimeFilter endDate;

    private ValueTypeIndicatorFilter propType;

    private OptionIndicatorFilter propMdtr;

    private LocalDateFilter dfltValueDate;

    private StringFilter dfltValueString;

    private BooleanFilter dfltValueBool;

    private DoubleFilter dfltValueNumber;

    public StdCodesGroupPropCriteria(){
    }

    public StdCodesGroupPropCriteria(StdCodesGroupPropCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.groupCode = other.groupCode == null ? null : other.groupCode.copy();
        this.propCode = other.propCode == null ? null : other.propCode.copy();
        this.propDesc = other.propDesc == null ? null : other.propDesc.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.propType = other.propType == null ? null : other.propType.copy();
        this.propMdtr = other.propMdtr == null ? null : other.propMdtr.copy();
        this.dfltValueDate = other.dfltValueDate == null ? null : other.dfltValueDate.copy();
        this.dfltValueString = other.dfltValueString == null ? null : other.dfltValueString.copy();
        this.dfltValueBool = other.dfltValueBool == null ? null : other.dfltValueBool.copy();
        this.dfltValueNumber = other.dfltValueNumber == null ? null : other.dfltValueNumber.copy();
    }

    @Override
    public StdCodesGroupPropCriteria copy() {
        return new StdCodesGroupPropCriteria(this);
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

    public StringFilter getPropCode() {
        return propCode;
    }

    public void setPropCode(StringFilter propCode) {
        this.propCode = propCode;
    }

    public StringFilter getPropDesc() {
        return propDesc;
    }

    public void setPropDesc(StringFilter propDesc) {
        this.propDesc = propDesc;
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

    public ValueTypeIndicatorFilter getPropType() {
        return propType;
    }

    public void setPropType(ValueTypeIndicatorFilter propType) {
        this.propType = propType;
    }

    public OptionIndicatorFilter getPropMdtr() {
        return propMdtr;
    }

    public void setPropMdtr(OptionIndicatorFilter propMdtr) {
        this.propMdtr = propMdtr;
    }

    public LocalDateFilter getDfltValueDate() {
        return dfltValueDate;
    }

    public void setDfltValueDate(LocalDateFilter dfltValueDate) {
        this.dfltValueDate = dfltValueDate;
    }

    public StringFilter getDfltValueString() {
        return dfltValueString;
    }

    public void setDfltValueString(StringFilter dfltValueString) {
        this.dfltValueString = dfltValueString;
    }

    public BooleanFilter getDfltValueBool() {
        return dfltValueBool;
    }

    public void setDfltValueBool(BooleanFilter dfltValueBool) {
        this.dfltValueBool = dfltValueBool;
    }

    public DoubleFilter getDfltValueNumber() {
        return dfltValueNumber;
    }

    public void setDfltValueNumber(DoubleFilter dfltValueNumber) {
        this.dfltValueNumber = dfltValueNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StdCodesGroupPropCriteria that = (StdCodesGroupPropCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(groupCode, that.groupCode) &&
            Objects.equals(propCode, that.propCode) &&
            Objects.equals(propDesc, that.propDesc) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(propType, that.propType) &&
            Objects.equals(propMdtr, that.propMdtr) &&
            Objects.equals(dfltValueDate, that.dfltValueDate) &&
            Objects.equals(dfltValueString, that.dfltValueString) &&
            Objects.equals(dfltValueBool, that.dfltValueBool) &&
            Objects.equals(dfltValueNumber, that.dfltValueNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        groupCode,
        propCode,
        propDesc,
        startDate,
        endDate,
        propType,
        propMdtr,
        dfltValueDate,
        dfltValueString,
        dfltValueBool,
        dfltValueNumber
        );
    }

    @Override
    public String toString() {
        return "StdCodesGroupPropCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (groupCode != null ? "groupCode=" + groupCode + ", " : "") +
                (propCode != null ? "propCode=" + propCode + ", " : "") +
                (propDesc != null ? "propDesc=" + propDesc + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (propType != null ? "propType=" + propType + ", " : "") +
                (propMdtr != null ? "propMdtr=" + propMdtr + ", " : "") +
                (dfltValueDate != null ? "dfltValueDate=" + dfltValueDate + ", " : "") +
                (dfltValueString != null ? "dfltValueString=" + dfltValueString + ", " : "") +
                (dfltValueBool != null ? "dfltValueBool=" + dfltValueBool + ", " : "") +
                (dfltValueNumber != null ? "dfltValueNumber=" + dfltValueNumber + ", " : "") +
            "}";
    }

}
