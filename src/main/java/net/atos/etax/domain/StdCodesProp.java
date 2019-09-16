package net.atos.etax.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * The StdCodesProp entity.
 * all properties have been added to the standard code
 */
@ApiModel(description = "The StdCodesProp entity. all properties have been added to the standard code")
@Entity
@Table(name = "std_codes_prop")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StdCodesProp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Code of the group of codes
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "Code of the group of codes", required = true)
    @Column(name = "group_code", length = 40, nullable = false)
    private String groupCode;

    /**
     * Internal code of the element in STD_CODES.
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "Internal code of the element in STD_CODES.", required = true)
    @Column(name = "internal_code", length = 40, nullable = false)
    private String internalCode;

    /**
     * Property code
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "Property code", required = true)
    @Column(name = "prop_code", length = 40, nullable = false)
    private String propCode;

    /**
     * Date at which the description of the element becomes valid (inclusive). Must be within the validity period of the element.
     */
    @NotNull
    @ApiModelProperty(value = "Date at which the description of the element becomes valid (inclusive). Must be within the validity period of the element.", required = true)
    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    /**
     * Date at which the validity of the description ends. Must be greater than START_DATE and within the validity period of the code.
     */
    @ApiModelProperty(value = "Date at which the validity of the description ends. Must be greater than START_DATE and within the validity period of the code.")
    @Column(name = "end_date")
    private ZonedDateTime endDate;

    /**
     * Date value for the property with a date property type
     */
    @ApiModelProperty(value = "Date value for the property with a date property type")
    @Column(name = "value_date")
    private LocalDate valueDate;

    /**
     * String value for the property with a string property type
     */
    @Size(max = 255)
    @ApiModelProperty(value = "String value for the property with a string property type")
    @Column(name = "value_string", length = 255)
    private String valueString;

    /**
     * Boolean value for the property with a boolean property type
     */
    @ApiModelProperty(value = "Boolean value for the property with a boolean property type")
    @Column(name = "value_bool")
    private Boolean valueBool;

    /**
     * Numeric value for the property with a numeric property type
     */
    @ApiModelProperty(value = "Numeric value for the property with a numeric property type")
    @Column(name = "value_number")
    private Double valueNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public StdCodesProp groupCode(String groupCode) {
        this.groupCode = groupCode;
        return this;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public StdCodesProp internalCode(String internalCode) {
        this.internalCode = internalCode;
        return this;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public String getPropCode() {
        return propCode;
    }

    public StdCodesProp propCode(String propCode) {
        this.propCode = propCode;
        return this;
    }

    public void setPropCode(String propCode) {
        this.propCode = propCode;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public StdCodesProp startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public StdCodesProp endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDate getValueDate() {
        return valueDate;
    }

    public StdCodesProp valueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
        return this;
    }

    public void setValueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
    }

    public String getValueString() {
        return valueString;
    }

    public StdCodesProp valueString(String valueString) {
        this.valueString = valueString;
        return this;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public Boolean isValueBool() {
        return valueBool;
    }

    public StdCodesProp valueBool(Boolean valueBool) {
        this.valueBool = valueBool;
        return this;
    }

    public void setValueBool(Boolean valueBool) {
        this.valueBool = valueBool;
    }

    public Double getValueNumber() {
        return valueNumber;
    }

    public StdCodesProp valueNumber(Double valueNumber) {
        this.valueNumber = valueNumber;
        return this;
    }

    public void setValueNumber(Double valueNumber) {
        this.valueNumber = valueNumber;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StdCodesProp)) {
            return false;
        }
        return id != null && id.equals(((StdCodesProp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StdCodesProp{" +
            "id=" + getId() +
            ", groupCode='" + getGroupCode() + "'" +
            ", internalCode='" + getInternalCode() + "'" +
            ", propCode='" + getPropCode() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", valueDate='" + getValueDate() + "'" +
            ", valueString='" + getValueString() + "'" +
            ", valueBool='" + isValueBool() + "'" +
            ", valueNumber=" + getValueNumber() +
            "}";
    }
}
