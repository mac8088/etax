package net.atos.etax.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * This entity stores relationships between Tax offices and functional offices.
 */
@ApiModel(description = "This entity stores relationships between Tax offices and functional offices.")
@Entity
@Table(name = "adm_office_tax_func")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OfficeTaxFunc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tax_office_id", nullable = false)
    private Integer taxOfficeId;

    @NotNull
    @Column(name = "func_office_id", nullable = false)
    private Integer funcOfficeId;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTaxOfficeId() {
        return taxOfficeId;
    }

    public OfficeTaxFunc taxOfficeId(Integer taxOfficeId) {
        this.taxOfficeId = taxOfficeId;
        return this;
    }

    public void setTaxOfficeId(Integer taxOfficeId) {
        this.taxOfficeId = taxOfficeId;
    }

    public Integer getFuncOfficeId() {
        return funcOfficeId;
    }

    public OfficeTaxFunc funcOfficeId(Integer funcOfficeId) {
        this.funcOfficeId = funcOfficeId;
        return this;
    }

    public void setFuncOfficeId(Integer funcOfficeId) {
        this.funcOfficeId = funcOfficeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public OfficeTaxFunc startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public OfficeTaxFunc endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfficeTaxFunc)) {
            return false;
        }
        return id != null && id.equals(((OfficeTaxFunc) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OfficeTaxFunc{" +
            "id=" + getId() +
            ", taxOfficeId=" + getTaxOfficeId() +
            ", funcOfficeId=" + getFuncOfficeId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
