package net.atos.bpm.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A DeputyToDo.
 */
@Entity
@Table(name = "bpm_deputy_todo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeputyToDo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "assignor_name")
    private String assignorName;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "process_instance_id")
    private String processInstanceId;

    @Column(name = "business_key")
    private String businessKey;

    @Column(name = "business_class")
    private String businessClass;

    @Column(name = "status")
    private Integer status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssignorName() {
        return assignorName;
    }

    public DeputyToDo assignorName(String assignorName) {
        this.assignorName = assignorName;
        return this;
    }

    public void setAssignorName(String assignorName) {
        this.assignorName = assignorName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public DeputyToDo ownerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getTaskId() {
        return taskId;
    }

    public DeputyToDo taskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public DeputyToDo processInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public DeputyToDo businessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getBusinessClass() {
        return businessClass;
    }

    public DeputyToDo businessClass(String businessClass) {
        this.businessClass = businessClass;
        return this;
    }

    public void setBusinessClass(String businessClass) {
        this.businessClass = businessClass;
    }

    public Integer getStatus() {
        return status;
    }

    public DeputyToDo status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeputyToDo)) {
            return false;
        }
        return id != null && id.equals(((DeputyToDo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DeputyToDo{" +
            "id=" + getId() +
            ", assignorName='" + getAssignorName() + "'" +
            ", ownerName='" + getOwnerName() + "'" +
            ", taskId='" + getTaskId() + "'" +
            ", processInstanceId='" + getProcessInstanceId() + "'" +
            ", businessKey='" + getBusinessKey() + "'" +
            ", businessClass='" + getBusinessClass() + "'" +
            ", status=" + getStatus() +
            "}";
    }
}
