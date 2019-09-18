package net.atos.bpm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * A Deputy.
 */
@Entity
@Table(name = "bpm_deputy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Deputy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "assignor_id")
    private Long assignorId;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "deputy_type")
    private String deputyType;

    @Column(name = "period_from")
    private ZonedDateTime periodFrom;

    @Column(name = "period_to")
    private ZonedDateTime periodTo;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssignorId() {
        return assignorId;
    }

    public Deputy assignorId(Long assignorId) {
        this.assignorId = assignorId;
        return this;
    }

    public void setAssignorId(Long assignorId) {
        this.assignorId = assignorId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Deputy ownerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getDeputyType() {
        return deputyType;
    }

    public Deputy deputyType(String deputyType) {
        this.deputyType = deputyType;
        return this;
    }

    public void setDeputyType(String deputyType) {
        this.deputyType = deputyType;
    }

    public ZonedDateTime getPeriodFrom() {
        return periodFrom;
    }

    public Deputy periodFrom(ZonedDateTime periodFrom) {
        this.periodFrom = periodFrom;
        return this;
    }

    public void setPeriodFrom(ZonedDateTime periodFrom) {
        this.periodFrom = periodFrom;
    }

    public ZonedDateTime getPeriodTo() {
        return periodTo;
    }

    public Deputy periodTo(ZonedDateTime periodTo) {
        this.periodTo = periodTo;
        return this;
    }

    public void setPeriodTo(ZonedDateTime periodTo) {
        this.periodTo = periodTo;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Deputy createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Deputy lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deputy)) {
            return false;
        }
        return id != null && id.equals(((Deputy) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Deputy{" +
            "id=" + getId() +
            ", assignorId=" + getAssignorId() +
            ", ownerId=" + getOwnerId() +
            ", deputyType='" + getDeputyType() + "'" +
            ", periodFrom='" + getPeriodFrom() + "'" +
            ", periodTo='" + getPeriodTo() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
