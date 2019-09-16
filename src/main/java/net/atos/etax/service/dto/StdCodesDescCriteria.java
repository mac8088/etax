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
 * Criteria class for the {@link net.atos.etax.domain.StdCodesDesc} entity. This class is used
 * in {@link net.atos.etax.web.rest.StdCodesDescResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /std-codes-descs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StdCodesDescCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter groupCode;

    private StringFilter internalCode;

    private StringFilter langCode;

    private ZonedDateTimeFilter startDate;

    private ZonedDateTimeFilter endDate;

    private StringFilter externalCode;

    private StringFilter codeDesc;

    private LongFilter stdCodesId;

    public StdCodesDescCriteria(){
    }

    public StdCodesDescCriteria(StdCodesDescCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.groupCode = other.groupCode == null ? null : other.groupCode.copy();
        this.internalCode = other.internalCode == null ? null : other.internalCode.copy();
        this.langCode = other.langCode == null ? null : other.langCode.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.externalCode = other.externalCode == null ? null : other.externalCode.copy();
        this.codeDesc = other.codeDesc == null ? null : other.codeDesc.copy();
        this.stdCodesId = other.stdCodesId == null ? null : other.stdCodesId.copy();
    }

    @Override
    public StdCodesDescCriteria copy() {
        return new StdCodesDescCriteria(this);
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

    public StringFilter getLangCode() {
        return langCode;
    }

    public void setLangCode(StringFilter langCode) {
        this.langCode = langCode;
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

    public StringFilter getExternalCode() {
        return externalCode;
    }

    public void setExternalCode(StringFilter externalCode) {
        this.externalCode = externalCode;
    }

    public StringFilter getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(StringFilter codeDesc) {
        this.codeDesc = codeDesc;
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
        final StdCodesDescCriteria that = (StdCodesDescCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(groupCode, that.groupCode) &&
            Objects.equals(internalCode, that.internalCode) &&
            Objects.equals(langCode, that.langCode) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(externalCode, that.externalCode) &&
            Objects.equals(codeDesc, that.codeDesc) &&
            Objects.equals(stdCodesId, that.stdCodesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        groupCode,
        internalCode,
        langCode,
        startDate,
        endDate,
        externalCode,
        codeDesc,
        stdCodesId
        );
    }

    @Override
    public String toString() {
        return "StdCodesDescCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (groupCode != null ? "groupCode=" + groupCode + ", " : "") +
                (internalCode != null ? "internalCode=" + internalCode + ", " : "") +
                (langCode != null ? "langCode=" + langCode + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (externalCode != null ? "externalCode=" + externalCode + ", " : "") +
                (codeDesc != null ? "codeDesc=" + codeDesc + ", " : "") +
                (stdCodesId != null ? "stdCodesId=" + stdCodesId + ", " : "") +
            "}";
    }

}
