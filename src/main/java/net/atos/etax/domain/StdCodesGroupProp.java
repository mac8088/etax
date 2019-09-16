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

import net.atos.etax.domain.enumeration.ValueTypeIndicator;

import net.atos.etax.domain.enumeration.OptionIndicator;

/**
 * The StdCodesGroupProp entity.
 * all default stardard code properties base on group
 */
@ApiModel(description = "The StdCodesGroupProp entity. all default stardard code properties base on group")
@Entity
@Table(name = "std_codes_group_prop")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StdCodesGroupProp implements Serializable {

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
     * Property code
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "Property code", required = true)
    @Column(name = "prop_code", length = 40, nullable = false)
    private String propCode;

    /**
     * Property description
     */
    @NotNull
    @Size(max = 255)
    @ApiModelProperty(value = "Property description", required = true)
    @Column(name = "prop_desc", length = 255, nullable = false)
    private String propDesc;

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
     * Type of the prop value Can be one of ''D'' (Date), ''S'' (String), ''N'' (Number) or ''B'' (Boolean).
     */
    @ApiModelProperty(value = "Type of the prop value Can be one of ''D'' (Date), ''S'' (String), ''N'' (Number) or ''B'' (Boolean).")
    @Enumerated(EnumType.STRING)
    @Column(name = "prop_type")
    private ValueTypeIndicator propType;

    /**
     * Accessibility of Property: mandatory or not
     */
    @ApiModelProperty(value = "Accessibility of Property: mandatory or not")
    @Enumerated(EnumType.STRING)
    @Column(name = "prop_mdtr")
    private OptionIndicator propMdtr;

    /**
     * Default date value for the property with a date property type
     */
    @ApiModelProperty(value = "Default date value for the property with a date property type")
    @Column(name = "dflt_value_date")
    private LocalDate dfltValueDate;

    /**
     * Default string value for the property with a string property type
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Default string value for the property with a string property type")
    @Column(name = "dflt_value_string", length = 255)
    private String dfltValueString;

    /**
     * Default boolean value for the property with a boolean property type
     */
    @ApiModelProperty(value = "Default boolean value for the property with a boolean property type")
    @Column(name = "dflt_value_bool")
    private Boolean dfltValueBool;

    /**
     * Default numeric value for the property with a numeric property type
     */
    @ApiModelProperty(value = "Default numeric value for the property with a numeric property type")
    @Column(name = "dflt_value_number")
    private Double dfltValueNumber;

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

    public StdCodesGroupProp groupCode(String groupCode) {
        this.groupCode = groupCode;
        return this;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getPropCode() {
        return propCode;
    }

    public StdCodesGroupProp propCode(String propCode) {
        this.propCode = propCode;
        return this;
    }

    public void setPropCode(String propCode) {
        this.propCode = propCode;
    }

    public String getPropDesc() {
        return propDesc;
    }

    public StdCodesGroupProp propDesc(String propDesc) {
        this.propDesc = propDesc;
        return this;
    }

    public void setPropDesc(String propDesc) {
        this.propDesc = propDesc;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public StdCodesGroupProp startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public StdCodesGroupProp endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public ValueTypeIndicator getPropType() {
        return propType;
    }

    public StdCodesGroupProp propType(ValueTypeIndicator propType) {
        this.propType = propType;
        return this;
    }

    public void setPropType(ValueTypeIndicator propType) {
        this.propType = propType;
    }

    public OptionIndicator getPropMdtr() {
        return propMdtr;
    }

    public StdCodesGroupProp propMdtr(OptionIndicator propMdtr) {
        this.propMdtr = propMdtr;
        return this;
    }

    public void setPropMdtr(OptionIndicator propMdtr) {
        this.propMdtr = propMdtr;
    }

    public LocalDate getDfltValueDate() {
        return dfltValueDate;
    }

    public StdCodesGroupProp dfltValueDate(LocalDate dfltValueDate) {
        this.dfltValueDate = dfltValueDate;
        return this;
    }

    public void setDfltValueDate(LocalDate dfltValueDate) {
        this.dfltValueDate = dfltValueDate;
    }

    public String getDfltValueString() {
        return dfltValueString;
    }

    public StdCodesGroupProp dfltValueString(String dfltValueString) {
        this.dfltValueString = dfltValueString;
        return this;
    }

    public void setDfltValueString(String dfltValueString) {
        this.dfltValueString = dfltValueString;
    }

    public Boolean isDfltValueBool() {
        return dfltValueBool;
    }

    public StdCodesGroupProp dfltValueBool(Boolean dfltValueBool) {
        this.dfltValueBool = dfltValueBool;
        return this;
    }

    public void setDfltValueBool(Boolean dfltValueBool) {
        this.dfltValueBool = dfltValueBool;
    }

    public Double getDfltValueNumber() {
        return dfltValueNumber;
    }

    public StdCodesGroupProp dfltValueNumber(Double dfltValueNumber) {
        this.dfltValueNumber = dfltValueNumber;
        return this;
    }

    public void setDfltValueNumber(Double dfltValueNumber) {
        this.dfltValueNumber = dfltValueNumber;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StdCodesGroupProp)) {
            return false;
        }
        return id != null && id.equals(((StdCodesGroupProp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StdCodesGroupProp{" +
            "id=" + getId() +
            ", groupCode='" + getGroupCode() + "'" +
            ", propCode='" + getPropCode() + "'" +
            ", propDesc='" + getPropDesc() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", propType='" + getPropType() + "'" +
            ", propMdtr='" + getPropMdtr() + "'" +
            ", dfltValueDate='" + getDfltValueDate() + "'" +
            ", dfltValueString='" + getDfltValueString() + "'" +
            ", dfltValueBool='" + isDfltValueBool() + "'" +
            ", dfltValueNumber=" + getDfltValueNumber() +
            "}";
    }
}
