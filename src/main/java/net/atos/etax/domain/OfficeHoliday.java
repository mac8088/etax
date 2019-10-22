package net.atos.etax.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * The tables used for office id and holiday id relationship table..
 */
@ApiModel(description = "The tables used for office id and holiday id relationship table..")
@Entity
@Table(name = "adm_office_holiday")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OfficeHoliday implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "office_id", nullable = false)
    private Integer officeId;

    /**
     * Holiday ID
     */
    @NotNull
    @Size(max = 7)
    @ApiModelProperty(value = "Holiday ID", required = true)
    @Column(name = "holiday_id", length = 7, nullable = false)
    private String holidayId;

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

    public OfficeHoliday officeId(Integer officeId) {
        this.officeId = officeId;
        return this;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public String getHolidayId() {
        return holidayId;
    }

    public OfficeHoliday holidayId(String holidayId) {
        this.holidayId = holidayId;
        return this;
    }

    public void setHolidayId(String holidayId) {
        this.holidayId = holidayId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfficeHoliday)) {
            return false;
        }
        return id != null && id.equals(((OfficeHoliday) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OfficeHoliday{" +
            "id=" + getId() +
            ", officeId=" + getOfficeId() +
            ", holidayId='" + getHolidayId() + "'" +
            "}";
    }
}
