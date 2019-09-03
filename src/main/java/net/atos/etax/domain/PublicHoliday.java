package net.atos.etax.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Public holiday.
 */
@ApiModel(description = "Public holiday.")
@Entity
@Table(name = "adm_public_holiday")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PublicHoliday implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 40)
    @Column(name = "cstd_holiday_types", length = 40)
    private String cstdHolidayTypes;

    @Size(max = 70)
    @Column(name = "description", length = 70)
    private String description;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "working_flag")
    private Boolean workingFlag;

    @Column(name = "country_wide")
    private Boolean countryWide;

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

    public String getCstdHolidayTypes() {
        return cstdHolidayTypes;
    }

    public PublicHoliday cstdHolidayTypes(String cstdHolidayTypes) {
        this.cstdHolidayTypes = cstdHolidayTypes;
        return this;
    }

    public void setCstdHolidayTypes(String cstdHolidayTypes) {
        this.cstdHolidayTypes = cstdHolidayTypes;
    }

    public String getDescription() {
        return description;
    }

    public PublicHoliday description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public PublicHoliday date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isWorkingFlag() {
        return workingFlag;
    }

    public PublicHoliday workingFlag(Boolean workingFlag) {
        this.workingFlag = workingFlag;
        return this;
    }

    public void setWorkingFlag(Boolean workingFlag) {
        this.workingFlag = workingFlag;
    }

    public Boolean isCountryWide() {
        return countryWide;
    }

    public PublicHoliday countryWide(Boolean countryWide) {
        this.countryWide = countryWide;
        return this;
    }

    public void setCountryWide(Boolean countryWide) {
        this.countryWide = countryWide;
    }

    public Integer getCcVersion() {
        return ccVersion;
    }

    public PublicHoliday ccVersion(Integer ccVersion) {
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
        if (!(o instanceof PublicHoliday)) {
            return false;
        }
        return id != null && id.equals(((PublicHoliday) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PublicHoliday{" +
            "id=" + getId() +
            ", cstdHolidayTypes='" + getCstdHolidayTypes() + "'" +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", workingFlag='" + isWorkingFlag() + "'" +
            ", countryWide='" + isCountryWide() + "'" +
            ", ccVersion=" + getCcVersion() +
            "}";
    }
}
