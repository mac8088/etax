package net.atos.bpm.service.dto;

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
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link net.atos.etax.domain.Deputy} entity. This class is used
 * in {@link net.atos.etax.web.rest.DeputyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /deputies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeputyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter assignorId;

    private LongFilter ownerId;

    private StringFilter deputyType;

    private ZonedDateTimeFilter periodFrom;

    private ZonedDateTimeFilter periodTo;

    private InstantFilter createdDate;

    private InstantFilter lastModifiedDate;

    public DeputyCriteria(){
    }

    public DeputyCriteria(DeputyCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.assignorId = other.assignorId == null ? null : other.assignorId.copy();
        this.ownerId = other.ownerId == null ? null : other.ownerId.copy();
        this.deputyType = other.deputyType == null ? null : other.deputyType.copy();
        this.periodFrom = other.periodFrom == null ? null : other.periodFrom.copy();
        this.periodTo = other.periodTo == null ? null : other.periodTo.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
    }

    @Override
    public DeputyCriteria copy() {
        return new DeputyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getAssignorId() {
        return assignorId;
    }

    public void setAssignorId(LongFilter assignorId) {
        this.assignorId = assignorId;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public StringFilter getDeputyType() {
        return deputyType;
    }

    public void setDeputyType(StringFilter deputyType) {
        this.deputyType = deputyType;
    }

    public ZonedDateTimeFilter getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(ZonedDateTimeFilter periodFrom) {
        this.periodFrom = periodFrom;
    }

    public ZonedDateTimeFilter getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(ZonedDateTimeFilter periodTo) {
        this.periodTo = periodTo;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DeputyCriteria that = (DeputyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(assignorId, that.assignorId) &&
            Objects.equals(ownerId, that.ownerId) &&
            Objects.equals(deputyType, that.deputyType) &&
            Objects.equals(periodFrom, that.periodFrom) &&
            Objects.equals(periodTo, that.periodTo) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        assignorId,
        ownerId,
        deputyType,
        periodFrom,
        periodTo,
        createdDate,
        lastModifiedDate
        );
    }

    @Override
    public String toString() {
        return "DeputyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (assignorId != null ? "assignorId=" + assignorId + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (deputyType != null ? "deputyType=" + deputyType + ", " : "") +
                (periodFrom != null ? "periodFrom=" + periodFrom + ", " : "") +
                (periodTo != null ? "periodTo=" + periodTo + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            "}";
    }

}
