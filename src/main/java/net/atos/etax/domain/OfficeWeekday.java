package net.atos.etax.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Week working day.
 */
@ApiModel(description = "Week working day.")
@Entity
@Table(name = "adm_office_weekday")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OfficeWeekday implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "office_id", nullable = false)
    private Integer officeId;

    @NotNull
    @Size(max = 40)
    @Column(name = "cstd_weekworking_day", length = 40, nullable = false)
    private String cstdWeekworkingDay;

    @Column(name = "start_date")
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

    public Integer getOfficeId() {
        return officeId;
    }

    public OfficeWeekday officeId(Integer officeId) {
        this.officeId = officeId;
        return this;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public String getCstdWeekworkingDay() {
        return cstdWeekworkingDay;
    }

    public OfficeWeekday cstdWeekworkingDay(String cstdWeekworkingDay) {
        this.cstdWeekworkingDay = cstdWeekworkingDay;
        return this;
    }

    public void setCstdWeekworkingDay(String cstdWeekworkingDay) {
        this.cstdWeekworkingDay = cstdWeekworkingDay;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public OfficeWeekday startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public OfficeWeekday endDate(LocalDate endDate) {
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
        if (!(o instanceof OfficeWeekday)) {
            return false;
        }
        return id != null && id.equals(((OfficeWeekday) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OfficeWeekday{" +
            "id=" + getId() +
            ", officeId=" + getOfficeId() +
            ", cstdWeekworkingDay='" + getCstdWeekworkingDay() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
