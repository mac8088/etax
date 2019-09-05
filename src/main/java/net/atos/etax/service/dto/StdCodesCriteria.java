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
 * Criteria class for the {@link net.atos.etax.domain.StdCodes} entity. This class is used
 * in {@link net.atos.etax.web.rest.StdCodesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /std-codes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StdCodesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter groupCode;

    private StringFilter internalCode;

    private ZonedDateTimeFilter startDate;

    private ZonedDateTimeFilter endDate;

    private StringFilter parentInternalCode;

    private StringFilter comments;

    private IntegerFilter secLevel;

    private LocalDateFilter codeValueDate;

    private StringFilter codeValueString;

    private BooleanFilter codeValueBool;

    private IntegerFilter codeValueNumber;

    private LongFilter stdCodesDescId;

    private LongFilter stdCodesGroupId;

    public StdCodesCriteria(){
    }

    public StdCodesCriteria(StdCodesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.groupCode = other.groupCode == null ? null : other.groupCode.copy();
        this.internalCode = other.internalCode == null ? null : other.internalCode.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.parentInternalCode = other.parentInternalCode == null ? null : other.parentInternalCode.copy();
        this.comments = other.comments == null ? null : other.comments.copy();
        this.secLevel = other.secLevel == null ? null : other.secLevel.copy();
        this.codeValueDate = other.codeValueDate == null ? null : other.codeValueDate.copy();
        this.codeValueString = other.codeValueString == null ? null : other.codeValueString.copy();
        this.codeValueBool = other.codeValueBool == null ? null : other.codeValueBool.copy();
        this.codeValueNumber = other.codeValueNumber == null ? null : other.codeValueNumber.copy();
        this.stdCodesDescId = other.stdCodesDescId == null ? null : other.stdCodesDescId.copy();
        this.stdCodesGroupId = other.stdCodesGroupId == null ? null : other.stdCodesGroupId.copy();
    }

    @Override
    public StdCodesCriteria copy() {
        return new StdCodesCriteria(this);
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

    public StringFilter getParentInternalCode() {
        return parentInternalCode;
    }

    public void setParentInternalCode(StringFilter parentInternalCode) {
        this.parentInternalCode = parentInternalCode;
    }

    public StringFilter getComments() {
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public IntegerFilter getSecLevel() {
        return secLevel;
    }

    public void setSecLevel(IntegerFilter secLevel) {
        this.secLevel = secLevel;
    }

    public LocalDateFilter getCodeValueDate() {
        return codeValueDate;
    }

    public void setCodeValueDate(LocalDateFilter codeValueDate) {
        this.codeValueDate = codeValueDate;
    }

    public StringFilter getCodeValueString() {
        return codeValueString;
    }

    public void setCodeValueString(StringFilter codeValueString) {
        this.codeValueString = codeValueString;
    }

    public BooleanFilter getCodeValueBool() {
        return codeValueBool;
    }

    public void setCodeValueBool(BooleanFilter codeValueBool) {
        this.codeValueBool = codeValueBool;
    }

    public IntegerFilter getCodeValueNumber() {
        return codeValueNumber;
    }

    public void setCodeValueNumber(IntegerFilter codeValueNumber) {
        this.codeValueNumber = codeValueNumber;
    }

    public LongFilter getStdCodesDescId() {
        return stdCodesDescId;
    }

    public void setStdCodesDescId(LongFilter stdCodesDescId) {
        this.stdCodesDescId = stdCodesDescId;
    }

    public LongFilter getStdCodesGroupId() {
        return stdCodesGroupId;
    }

    public void setStdCodesGroupId(LongFilter stdCodesGroupId) {
        this.stdCodesGroupId = stdCodesGroupId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StdCodesCriteria that = (StdCodesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(groupCode, that.groupCode) &&
            Objects.equals(internalCode, that.internalCode) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(parentInternalCode, that.parentInternalCode) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(secLevel, that.secLevel) &&
            Objects.equals(codeValueDate, that.codeValueDate) &&
            Objects.equals(codeValueString, that.codeValueString) &&
            Objects.equals(codeValueBool, that.codeValueBool) &&
            Objects.equals(codeValueNumber, that.codeValueNumber) &&
            Objects.equals(stdCodesDescId, that.stdCodesDescId) &&
            Objects.equals(stdCodesGroupId, that.stdCodesGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        groupCode,
        internalCode,
        startDate,
        endDate,
        parentInternalCode,
        comments,
        secLevel,
        codeValueDate,
        codeValueString,
        codeValueBool,
        codeValueNumber,
        stdCodesDescId,
        stdCodesGroupId
        );
    }

    @Override
    public String toString() {
        return "StdCodesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (groupCode != null ? "groupCode=" + groupCode + ", " : "") +
                (internalCode != null ? "internalCode=" + internalCode + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (parentInternalCode != null ? "parentInternalCode=" + parentInternalCode + ", " : "") +
                (comments != null ? "comments=" + comments + ", " : "") +
                (secLevel != null ? "secLevel=" + secLevel + ", " : "") +
                (codeValueDate != null ? "codeValueDate=" + codeValueDate + ", " : "") +
                (codeValueString != null ? "codeValueString=" + codeValueString + ", " : "") +
                (codeValueBool != null ? "codeValueBool=" + codeValueBool + ", " : "") +
                (codeValueNumber != null ? "codeValueNumber=" + codeValueNumber + ", " : "") +
                (stdCodesDescId != null ? "stdCodesDescId=" + stdCodesDescId + ", " : "") +
                (stdCodesGroupId != null ? "stdCodesGroupId=" + stdCodesGroupId + ", " : "") +
            "}";
    }

}
