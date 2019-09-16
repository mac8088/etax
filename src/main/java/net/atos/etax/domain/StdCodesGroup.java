package net.atos.etax.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import net.atos.etax.domain.enumeration.OptionIndicator;

import net.atos.etax.domain.enumeration.ValueTypeIndicator;

/**
 * The StdCodesGroup entity.
 * This table holds the definition and characteristics of the groups of standard codes used throughout e-TAX.
 * Code groups represent lists of elements. Data in this table cannot be modified by end users.
 */
@ApiModel(description = "The StdCodesGroup entity. This table holds the definition and characteristics of the groups of standard codes used throughout e-TAX. Code groups represent lists of elements. Data in this table cannot be modified by end users.")
@Entity
@Table(name = "std_codes_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StdCodesGroup implements Serializable {

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
    @Column(name = "group_code", length = 40, nullable = false, unique = true)
    private String groupCode;

    /**
     * Group description (name of the list of codes)
     */
    @NotNull
    @Size(max = 255)
    @ApiModelProperty(value = "Group description (name of the list of codes)", required = true)
    @Column(name = "group_desc", length = 255, nullable = false)
    private String groupDesc;

    /**
     * System indicator. Can hold values ''Y'' or ''N''. When ''Y'', the elements of the group cannot be modified by end-users using the system configuration module. However, their description can be changed and they can be translated.
     */
    @NotNull
    @ApiModelProperty(value = "System indicator. Can hold values ''Y'' or ''N''. When ''Y'', the elements of the group cannot be modified by end-users using the system configuration module. However, their description can be changed and they can be translated.", required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "system_ind", nullable = false)
    private OptionIndicator systemInd;

    /**
     * If the elements in this group have an associated security level. Can be set to ''Y'' or ''N''. When ''Y'', all the elements of the group must have a security level in STD_CODES.SEC_LEVEL.
     */
    @NotNull
    @ApiModelProperty(value = "If the elements in this group have an associated security level. Can be set to ''Y'' or ''N''. When ''Y'', all the elements of the group must have a security level in STD_CODES.SEC_LEVEL.", required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "sec_level_required", nullable = false)
    private OptionIndicator secLevelRequired;

    /**
     * If the elements in this group have a value. Can be set to ''Y'' or ''N''. When ''Y'', all the elements of the group must have a value and their type must be specified in VALUE_TYPE.
     */
    @NotNull
    @ApiModelProperty(value = "If the elements in this group have a value. Can be set to ''Y'' or ''N''. When ''Y'', all the elements of the group must have a value and their type must be specified in VALUE_TYPE.", required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "value_required", nullable = false)
    private OptionIndicator valueRequired;

    /**
     * Type of the value of all the elements in this group. Can be one of ''D'' (Date), ''S'' (String), ''N'' (Number) or ''B'' (Boolean). Can be set only when CODES_VALUE_REQUIRED = ''Y''.
     */
    @ApiModelProperty(value = "Type of the value of all the elements in this group. Can be one of ''D'' (Date), ''S'' (String), ''N'' (Number) or ''B'' (Boolean). Can be set only when CODES_VALUE_REQUIRED = ''Y''.")
    @Enumerated(EnumType.STRING)
    @Column(name = "value_type")
    private ValueTypeIndicator valueType;

    /**
     * Can hold values ''Y'' or ''N''. When ''N'', codes are never shown to end-users and don''t require an entry in STD_CODES_DESC. When ''Y'', EXTERNAL_CODE_REQUIRED must be set.
     */
    @NotNull
    @ApiModelProperty(value = "Can hold values ''Y'' or ''N''. When ''N'', codes are never shown to end-users and don''t require an entry in STD_CODES_DESC. When ''Y'', EXTERNAL_CODE_REQUIRED must be set.", required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "description_required", nullable = false)
    private OptionIndicator descriptionRequired;

    /**
     * If the elements in this group are identified by a multilingual code shown to the user. Can hold values ''Y'' or ''N''. When set to ''Y'', the length of external codes must be specified in EXTERNAL_CODE_LENGTH. External codes are used as shortcuts in entry fields linked to drop-down lists of values.
     */
    @ApiModelProperty(value = "If the elements in this group are identified by a multilingual code shown to the user. Can hold values ''Y'' or ''N''. When set to ''Y'', the length of external codes must be specified in EXTERNAL_CODE_LENGTH. External codes are used as shortcuts in entry fields linked to drop-down lists of values.")
    @Enumerated(EnumType.STRING)
    @Column(name = "external_code_required")
    private OptionIndicator externalCodeRequired;

    /**
     * Number of characters of the external codes. Can be set only when EXTERNAL_CODE_REQUIRED is ''Y''.
     */
    @ApiModelProperty(value = "Number of characters of the external codes. Can be set only when EXTERNAL_CODE_REQUIRED is ''Y''.")
    @Column(name = "external_code_length")
    private Integer externalCodeLength;

    /**
     * If this group has a parent group. Can be set to ''Y'' or ''N''. When ''Y'', the parent group code must be specified in PARENT_GROUP_CODE. Used for multi-level lists of values.
     */
    @NotNull
    @ApiModelProperty(value = "If this group has a parent group. Can be set to ''Y'' or ''N''. When ''Y'', the parent group code must be specified in PARENT_GROUP_CODE. Used for multi-level lists of values.", required = true)
    @Enumerated(EnumType.STRING)
    @Column(name = "parent_group_required", nullable = false)
    private OptionIndicator parentGroupRequired;

    /**
     * The code of the parent group. Must be set when PARENT_GROUP_REQUIRED is ''Y''.
     */
    @ApiModelProperty(value = "The code of the parent group. Must be set when PARENT_GROUP_REQUIRED is ''Y''.")
    @Column(name = "parent_group_code")
    private String parentGroupCode;

    /**
     * The shortcut code is shown by uppercase or not
     */
    @ApiModelProperty(value = "The shortcut code is shown by uppercase or not")
    @Column(name = "touppercase")
    private Boolean touppercase;

    @OneToMany(mappedBy = "stdCodesGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StdCodes> stdCodes = new HashSet<>();

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

    public StdCodesGroup groupCode(String groupCode) {
        this.groupCode = groupCode;
        return this;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public StdCodesGroup groupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
        return this;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public OptionIndicator getSystemInd() {
        return systemInd;
    }

    public StdCodesGroup systemInd(OptionIndicator systemInd) {
        this.systemInd = systemInd;
        return this;
    }

    public void setSystemInd(OptionIndicator systemInd) {
        this.systemInd = systemInd;
    }

    public OptionIndicator getSecLevelRequired() {
        return secLevelRequired;
    }

    public StdCodesGroup secLevelRequired(OptionIndicator secLevelRequired) {
        this.secLevelRequired = secLevelRequired;
        return this;
    }

    public void setSecLevelRequired(OptionIndicator secLevelRequired) {
        this.secLevelRequired = secLevelRequired;
    }

    public OptionIndicator getValueRequired() {
        return valueRequired;
    }

    public StdCodesGroup valueRequired(OptionIndicator valueRequired) {
        this.valueRequired = valueRequired;
        return this;
    }

    public void setValueRequired(OptionIndicator valueRequired) {
        this.valueRequired = valueRequired;
    }

    public ValueTypeIndicator getValueType() {
        return valueType;
    }

    public StdCodesGroup valueType(ValueTypeIndicator valueType) {
        this.valueType = valueType;
        return this;
    }

    public void setValueType(ValueTypeIndicator valueType) {
        this.valueType = valueType;
    }

    public OptionIndicator getDescriptionRequired() {
        return descriptionRequired;
    }

    public StdCodesGroup descriptionRequired(OptionIndicator descriptionRequired) {
        this.descriptionRequired = descriptionRequired;
        return this;
    }

    public void setDescriptionRequired(OptionIndicator descriptionRequired) {
        this.descriptionRequired = descriptionRequired;
    }

    public OptionIndicator getExternalCodeRequired() {
        return externalCodeRequired;
    }

    public StdCodesGroup externalCodeRequired(OptionIndicator externalCodeRequired) {
        this.externalCodeRequired = externalCodeRequired;
        return this;
    }

    public void setExternalCodeRequired(OptionIndicator externalCodeRequired) {
        this.externalCodeRequired = externalCodeRequired;
    }

    public Integer getExternalCodeLength() {
        return externalCodeLength;
    }

    public StdCodesGroup externalCodeLength(Integer externalCodeLength) {
        this.externalCodeLength = externalCodeLength;
        return this;
    }

    public void setExternalCodeLength(Integer externalCodeLength) {
        this.externalCodeLength = externalCodeLength;
    }

    public OptionIndicator getParentGroupRequired() {
        return parentGroupRequired;
    }

    public StdCodesGroup parentGroupRequired(OptionIndicator parentGroupRequired) {
        this.parentGroupRequired = parentGroupRequired;
        return this;
    }

    public void setParentGroupRequired(OptionIndicator parentGroupRequired) {
        this.parentGroupRequired = parentGroupRequired;
    }

    public String getParentGroupCode() {
        return parentGroupCode;
    }

    public StdCodesGroup parentGroupCode(String parentGroupCode) {
        this.parentGroupCode = parentGroupCode;
        return this;
    }

    public void setParentGroupCode(String parentGroupCode) {
        this.parentGroupCode = parentGroupCode;
    }

    public Boolean isTouppercase() {
        return touppercase;
    }

    public StdCodesGroup touppercase(Boolean touppercase) {
        this.touppercase = touppercase;
        return this;
    }

    public void setTouppercase(Boolean touppercase) {
        this.touppercase = touppercase;
    }

    public Set<StdCodes> getStdCodes() {
        return stdCodes;
    }

    public StdCodesGroup stdCodes(Set<StdCodes> stdCodes) {
        this.stdCodes = stdCodes;
        return this;
    }

    public StdCodesGroup addStdCodes(StdCodes stdCodes) {
        this.stdCodes.add(stdCodes);
        stdCodes.setStdCodesGroup(this);
        return this;
    }

    public StdCodesGroup removeStdCodes(StdCodes stdCodes) {
        this.stdCodes.remove(stdCodes);
        stdCodes.setStdCodesGroup(null);
        return this;
    }

    public void setStdCodes(Set<StdCodes> stdCodes) {
        this.stdCodes = stdCodes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StdCodesGroup)) {
            return false;
        }
        return id != null && id.equals(((StdCodesGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StdCodesGroup{" +
            "id=" + getId() +
            ", groupCode='" + getGroupCode() + "'" +
            ", groupDesc='" + getGroupDesc() + "'" +
            ", systemInd='" + getSystemInd() + "'" +
            ", secLevelRequired='" + getSecLevelRequired() + "'" +
            ", valueRequired='" + getValueRequired() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", descriptionRequired='" + getDescriptionRequired() + "'" +
            ", externalCodeRequired='" + getExternalCodeRequired() + "'" +
            ", externalCodeLength=" + getExternalCodeLength() +
            ", parentGroupRequired='" + getParentGroupRequired() + "'" +
            ", parentGroupCode='" + getParentGroupCode() + "'" +
            ", touppercase='" + isTouppercase() + "'" +
            "}";
    }
}
