package net.atos.etax.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import net.atos.etax.domain.enumeration.OptionIndicator;
import net.atos.etax.domain.enumeration.OptionIndicator;
import net.atos.etax.domain.enumeration.OptionIndicator;
import net.atos.etax.domain.enumeration.ValueTypeIndicator;
import net.atos.etax.domain.enumeration.OptionIndicator;
import net.atos.etax.domain.enumeration.OptionIndicator;
import net.atos.etax.domain.enumeration.OptionIndicator;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link net.atos.etax.domain.StdCodesGroup} entity. This class is used
 * in {@link net.atos.etax.web.rest.StdCodesGroupResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /std-codes-groups?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StdCodesGroupCriteria implements Serializable, Criteria {
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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter groupCode;

    private StringFilter groupDesc;

    private OptionIndicatorFilter systemInd;

    private OptionIndicatorFilter secLevelRequired;

    private OptionIndicatorFilter valueRequired;

    private ValueTypeIndicatorFilter valueType;

    private OptionIndicatorFilter descriptionRequired;

    private OptionIndicatorFilter externalCodeRequired;

    private IntegerFilter externalCodeLength;

    private OptionIndicatorFilter parentGroupRequired;

    private StringFilter parentGroupCode;

    private BooleanFilter touppercase;

    private LongFilter stdCodesId;

    public StdCodesGroupCriteria(){
    }

    public StdCodesGroupCriteria(StdCodesGroupCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.groupCode = other.groupCode == null ? null : other.groupCode.copy();
        this.groupDesc = other.groupDesc == null ? null : other.groupDesc.copy();
        this.systemInd = other.systemInd == null ? null : other.systemInd.copy();
        this.secLevelRequired = other.secLevelRequired == null ? null : other.secLevelRequired.copy();
        this.valueRequired = other.valueRequired == null ? null : other.valueRequired.copy();
        this.valueType = other.valueType == null ? null : other.valueType.copy();
        this.descriptionRequired = other.descriptionRequired == null ? null : other.descriptionRequired.copy();
        this.externalCodeRequired = other.externalCodeRequired == null ? null : other.externalCodeRequired.copy();
        this.externalCodeLength = other.externalCodeLength == null ? null : other.externalCodeLength.copy();
        this.parentGroupRequired = other.parentGroupRequired == null ? null : other.parentGroupRequired.copy();
        this.parentGroupCode = other.parentGroupCode == null ? null : other.parentGroupCode.copy();
        this.touppercase = other.touppercase == null ? null : other.touppercase.copy();
        this.stdCodesId = other.stdCodesId == null ? null : other.stdCodesId.copy();
    }

    @Override
    public StdCodesGroupCriteria copy() {
        return new StdCodesGroupCriteria(this);
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

    public StringFilter getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(StringFilter groupDesc) {
        this.groupDesc = groupDesc;
    }

    public OptionIndicatorFilter getSystemInd() {
        return systemInd;
    }

    public void setSystemInd(OptionIndicatorFilter systemInd) {
        this.systemInd = systemInd;
    }

    public OptionIndicatorFilter getSecLevelRequired() {
        return secLevelRequired;
    }

    public void setSecLevelRequired(OptionIndicatorFilter secLevelRequired) {
        this.secLevelRequired = secLevelRequired;
    }

    public OptionIndicatorFilter getValueRequired() {
        return valueRequired;
    }

    public void setValueRequired(OptionIndicatorFilter valueRequired) {
        this.valueRequired = valueRequired;
    }

    public ValueTypeIndicatorFilter getValueType() {
        return valueType;
    }

    public void setValueType(ValueTypeIndicatorFilter valueType) {
        this.valueType = valueType;
    }

    public OptionIndicatorFilter getDescriptionRequired() {
        return descriptionRequired;
    }

    public void setDescriptionRequired(OptionIndicatorFilter descriptionRequired) {
        this.descriptionRequired = descriptionRequired;
    }

    public OptionIndicatorFilter getExternalCodeRequired() {
        return externalCodeRequired;
    }

    public void setExternalCodeRequired(OptionIndicatorFilter externalCodeRequired) {
        this.externalCodeRequired = externalCodeRequired;
    }

    public IntegerFilter getExternalCodeLength() {
        return externalCodeLength;
    }

    public void setExternalCodeLength(IntegerFilter externalCodeLength) {
        this.externalCodeLength = externalCodeLength;
    }

    public OptionIndicatorFilter getParentGroupRequired() {
        return parentGroupRequired;
    }

    public void setParentGroupRequired(OptionIndicatorFilter parentGroupRequired) {
        this.parentGroupRequired = parentGroupRequired;
    }

    public StringFilter getParentGroupCode() {
        return parentGroupCode;
    }

    public void setParentGroupCode(StringFilter parentGroupCode) {
        this.parentGroupCode = parentGroupCode;
    }

    public BooleanFilter getTouppercase() {
        return touppercase;
    }

    public void setTouppercase(BooleanFilter touppercase) {
        this.touppercase = touppercase;
    }

    public LongFilter getStdCodesId() {
        return stdCodesId;
    }

    public void setStdCodesId(LongFilter stdCodesId) {
        this.stdCodesId = stdCodesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StdCodesGroupCriteria that = (StdCodesGroupCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(groupCode, that.groupCode) &&
            Objects.equals(groupDesc, that.groupDesc) &&
            Objects.equals(systemInd, that.systemInd) &&
            Objects.equals(secLevelRequired, that.secLevelRequired) &&
            Objects.equals(valueRequired, that.valueRequired) &&
            Objects.equals(valueType, that.valueType) &&
            Objects.equals(descriptionRequired, that.descriptionRequired) &&
            Objects.equals(externalCodeRequired, that.externalCodeRequired) &&
            Objects.equals(externalCodeLength, that.externalCodeLength) &&
            Objects.equals(parentGroupRequired, that.parentGroupRequired) &&
            Objects.equals(parentGroupCode, that.parentGroupCode) &&
            Objects.equals(touppercase, that.touppercase) &&
            Objects.equals(stdCodesId, that.stdCodesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        groupCode,
        groupDesc,
        systemInd,
        secLevelRequired,
        valueRequired,
        valueType,
        descriptionRequired,
        externalCodeRequired,
        externalCodeLength,
        parentGroupRequired,
        parentGroupCode,
        touppercase,
        stdCodesId
        );
    }

    @Override
    public String toString() {
        return "StdCodesGroupCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (groupCode != null ? "groupCode=" + groupCode + ", " : "") +
                (groupDesc != null ? "groupDesc=" + groupDesc + ", " : "") +
                (systemInd != null ? "systemInd=" + systemInd + ", " : "") +
                (secLevelRequired != null ? "secLevelRequired=" + secLevelRequired + ", " : "") +
                (valueRequired != null ? "valueRequired=" + valueRequired + ", " : "") +
                (valueType != null ? "valueType=" + valueType + ", " : "") +
                (descriptionRequired != null ? "descriptionRequired=" + descriptionRequired + ", " : "") +
                (externalCodeRequired != null ? "externalCodeRequired=" + externalCodeRequired + ", " : "") +
                (externalCodeLength != null ? "externalCodeLength=" + externalCodeLength + ", " : "") +
                (parentGroupRequired != null ? "parentGroupRequired=" + parentGroupRequired + ", " : "") +
                (parentGroupCode != null ? "parentGroupCode=" + parentGroupCode + ", " : "") +
                (touppercase != null ? "touppercase=" + touppercase + ", " : "") +
                (stdCodesId != null ? "stdCodesId=" + stdCodesId + ", " : "") +
            "}";
    }

}
