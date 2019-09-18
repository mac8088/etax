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

/**
 * Criteria class for the {@link net.atos.etax.domain.DeputyToDo} entity. This class is used
 * in {@link net.atos.etax.web.rest.DeputyToDoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /deputy-to-dos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeputyToDoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter assignorId;

    private StringFilter assignorName;

    private LongFilter ownerId;

    private StringFilter ownerName;

    private StringFilter taskId;

    private StringFilter processInstanceId;

    private StringFilter businessKey;

    private StringFilter businessClass;

    private IntegerFilter status;

    public DeputyToDoCriteria(){
    }

    public DeputyToDoCriteria(DeputyToDoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.assignorId = other.assignorId == null ? null : other.assignorId.copy();
        this.assignorName = other.assignorName == null ? null : other.assignorName.copy();
        this.ownerId = other.ownerId == null ? null : other.ownerId.copy();
        this.ownerName = other.ownerName == null ? null : other.ownerName.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
        this.processInstanceId = other.processInstanceId == null ? null : other.processInstanceId.copy();
        this.businessKey = other.businessKey == null ? null : other.businessKey.copy();
        this.businessClass = other.businessClass == null ? null : other.businessClass.copy();
        this.status = other.status == null ? null : other.status.copy();
    }

    @Override
    public DeputyToDoCriteria copy() {
        return new DeputyToDoCriteria(this);
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

    public StringFilter getAssignorName() {
        return assignorName;
    }

    public void setAssignorName(StringFilter assignorName) {
        this.assignorName = assignorName;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public StringFilter getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(StringFilter ownerName) {
        this.ownerName = ownerName;
    }

    public StringFilter getTaskId() {
        return taskId;
    }

    public void setTaskId(StringFilter taskId) {
        this.taskId = taskId;
    }

    public StringFilter getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(StringFilter processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public StringFilter getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(StringFilter businessKey) {
        this.businessKey = businessKey;
    }

    public StringFilter getBusinessClass() {
        return businessClass;
    }

    public void setBusinessClass(StringFilter businessClass) {
        this.businessClass = businessClass;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DeputyToDoCriteria that = (DeputyToDoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(assignorId, that.assignorId) &&
            Objects.equals(assignorName, that.assignorName) &&
            Objects.equals(ownerId, that.ownerId) &&
            Objects.equals(ownerName, that.ownerName) &&
            Objects.equals(taskId, that.taskId) &&
            Objects.equals(processInstanceId, that.processInstanceId) &&
            Objects.equals(businessKey, that.businessKey) &&
            Objects.equals(businessClass, that.businessClass) &&
            Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        assignorId,
        assignorName,
        ownerId,
        ownerName,
        taskId,
        processInstanceId,
        businessKey,
        businessClass,
        status
        );
    }

    @Override
    public String toString() {
        return "DeputyToDoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (assignorId != null ? "assignorId=" + assignorId + ", " : "") +
                (assignorName != null ? "assignorName=" + assignorName + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (ownerName != null ? "ownerName=" + ownerName + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
                (processInstanceId != null ? "processInstanceId=" + processInstanceId + ", " : "") +
                (businessKey != null ? "businessKey=" + businessKey + ", " : "") +
                (businessClass != null ? "businessClass=" + businessClass + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
            "}";
    }

}
