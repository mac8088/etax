package net.atos.etax.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * The relationship of Parent office and its children.
 */
@ApiModel(description = "The relationship of Parent office and its children.")
@Entity
@Table(name = "adm_office_relationship")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OfficeRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "parent_id", nullable = false)
    private Integer parentId;

    @NotNull
    @Column(name = "chile_id", nullable = false)
    private Integer chileId;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    /**
     * Version to control concurrency
     */
    @ApiModelProperty(value = "Version to control concurrency")
    @Column(name = "cc_version")
    private Integer ccVersion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public OfficeRelationship parentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getChileId() {
        return chileId;
    }

    public OfficeRelationship chileId(Integer chileId) {
        this.chileId = chileId;
        return this;
    }

    public void setChileId(Integer chileId) {
        this.chileId = chileId;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public OfficeRelationship startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public OfficeRelationship endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getCcVersion() {
        return ccVersion;
    }

    public OfficeRelationship ccVersion(Integer ccVersion) {
        this.ccVersion = ccVersion;
        return this;
    }

    public void setCcVersion(Integer ccVersion) {
        this.ccVersion = ccVersion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfficeRelationship)) {
            return false;
        }
        return id != null && id.equals(((OfficeRelationship) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OfficeRelationship{" +
            "id=" + getId() +
            ", parentId=" + getParentId() +
            ", chileId=" + getChileId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", ccVersion=" + getCcVersion() +
            "}";
    }
}
