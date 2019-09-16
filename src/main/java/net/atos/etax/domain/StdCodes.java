package net.atos.etax.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * The StdCodes entity.
 * Holds the list of elements in a group. Groups are defined in STD_CODES_GROUP.
 * The multilingual description of elements is stored in STD_CODE_DESC.
 * Data in this table can be modified by end users only when STD_CODES_GROUP.SYSTEM_IND = ''N'' for this group,
 * i.e. it is not a system group.
 */
@ApiModel(description = "The StdCodes entity. Holds the list of elements in a group. Groups are defined in STD_CODES_GROUP. The multilingual description of elements is stored in STD_CODE_DESC. Data in this table can be modified by end users only when STD_CODES_GROUP.SYSTEM_IND = ''N'' for this group, i.e. it is not a system group.")
@Entity
@Table(name = "std_codes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StdCodes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Code of the group of codes in STD_CODES_GROUP.
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "Code of the group of codes in STD_CODES_GROUP.", required = true)
    @Column(name = "group_code", length = 40, nullable = false)
    private String groupCode;

    /**
     * Identifies uniquely an element in a group. Unlike external codes, internal codes are not subject to translation and can therefore be used in programs.
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "Identifies uniquely an element in a group. Unlike external codes, internal codes are not subject to translation and can therefore be used in programs.", required = true)
    @Column(name = "internal_code", length = 40, nullable = false)
    private String internalCode;

    /**
     * Date at which the code becomes valid (inclusive).
     */
    @NotNull
    @ApiModelProperty(value = "Date at which the code becomes valid (inclusive).", required = true)
    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    /**
     * Date at which the validity of the code ends. The code is valid on that date until midnight (hours are truncated). Must be greater than (or equal to) START_DATE.
     */
    @ApiModelProperty(value = "Date at which the validity of the code ends. The code is valid on that date until midnight (hours are truncated). Must be greater than (or equal to) START_DATE.")
    @Column(name = "end_date")
    private ZonedDateTime endDate;

    /**
     * Reference of the parent code in multi-level lists. Used together with STD_CODES_GROUP.PARENT_GROUP_CODE to find the code in STD_CODES(GROUP_CODE, INTERNAL_CODE).
     */
    @Size(max = 40)
    @ApiModelProperty(value = "Reference of the parent code in multi-level lists. Used together with STD_CODES_GROUP.PARENT_GROUP_CODE to find the code in STD_CODES(GROUP_CODE, INTERNAL_CODE).")
    @Column(name = "parent_internal_code", length = 40)
    private String parentInternalCode;

    /**
     * Internal comments. Optional. Not shown to end-users.
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Internal comments. Optional. Not shown to end-users.")
    @Column(name = "comments", length = 255)
    private String comments;

    /**
     * Security level.
     */
    @NotNull
    @Max(value = 99)
    @ApiModelProperty(value = "Security level.", required = true)
    @Column(name = "sec_level", nullable = false)
    private Integer secLevel;

    /**
     * Value of the element when the type set in STD_CODES_GROUP.VALUE_TYPE is ''D'' (Date).
     */
    @ApiModelProperty(value = "Value of the element when the type set in STD_CODES_GROUP.VALUE_TYPE is ''D'' (Date).")
    @Column(name = "code_value_date")
    private LocalDate codeValueDate;

    /**
     * Value of the element when the type set in STD_CODES_GROUP.VALUE_TYPE is ''S'' (String).
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Value of the element when the type set in STD_CODES_GROUP.VALUE_TYPE is ''S'' (String).")
    @Column(name = "code_value_string", length = 255)
    private String codeValueString;

    /**
     * Value of the element when the type set in STD_CODES_GROUP.VALUE_TYPE is ''B'' (Boolean).
     */
    @ApiModelProperty(value = "Value of the element when the type set in STD_CODES_GROUP.VALUE_TYPE is ''B'' (Boolean).")
    @Column(name = "code_value_bool")
    private Boolean codeValueBool;

    /**
     * Value of the element when the type set in STD_CODES_GROUP.VALUE_TYPE is ''N'' (Number)
     */
    @ApiModelProperty(value = "Value of the element when the type set in STD_CODES_GROUP.VALUE_TYPE is ''N'' (Number)")
    @Column(name = "code_value_number")
    private Integer codeValueNumber;

    @OneToMany(mappedBy = "stdCodes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StdCodesDesc> stdCodesDescs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("stdCodes")
    private StdCodesGroup stdCodesGroup;

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

    public StdCodes groupCode(String groupCode) {
        this.groupCode = groupCode;
        return this;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public StdCodes internalCode(String internalCode) {
        this.internalCode = internalCode;
        return this;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public StdCodes startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public StdCodes endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getParentInternalCode() {
        return parentInternalCode;
    }

    public StdCodes parentInternalCode(String parentInternalCode) {
        this.parentInternalCode = parentInternalCode;
        return this;
    }

    public void setParentInternalCode(String parentInternalCode) {
        this.parentInternalCode = parentInternalCode;
    }

    public String getComments() {
        return comments;
    }

    public StdCodes comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getSecLevel() {
        return secLevel;
    }

    public StdCodes secLevel(Integer secLevel) {
        this.secLevel = secLevel;
        return this;
    }

    public void setSecLevel(Integer secLevel) {
        this.secLevel = secLevel;
    }

    public LocalDate getCodeValueDate() {
        return codeValueDate;
    }

    public StdCodes codeValueDate(LocalDate codeValueDate) {
        this.codeValueDate = codeValueDate;
        return this;
    }

    public void setCodeValueDate(LocalDate codeValueDate) {
        this.codeValueDate = codeValueDate;
    }

    public String getCodeValueString() {
        return codeValueString;
    }

    public StdCodes codeValueString(String codeValueString) {
        this.codeValueString = codeValueString;
        return this;
    }

    public void setCodeValueString(String codeValueString) {
        this.codeValueString = codeValueString;
    }

    public Boolean isCodeValueBool() {
        return codeValueBool;
    }

    public StdCodes codeValueBool(Boolean codeValueBool) {
        this.codeValueBool = codeValueBool;
        return this;
    }

    public void setCodeValueBool(Boolean codeValueBool) {
        this.codeValueBool = codeValueBool;
    }

    public Integer getCodeValueNumber() {
        return codeValueNumber;
    }

    public StdCodes codeValueNumber(Integer codeValueNumber) {
        this.codeValueNumber = codeValueNumber;
        return this;
    }

    public void setCodeValueNumber(Integer codeValueNumber) {
        this.codeValueNumber = codeValueNumber;
    }

    public Set<StdCodesDesc> getStdCodesDescs() {
        return stdCodesDescs;
    }

    public StdCodes stdCodesDescs(Set<StdCodesDesc> stdCodesDescs) {
        this.stdCodesDescs = stdCodesDescs;
        return this;
    }

    public StdCodes addStdCodesDesc(StdCodesDesc stdCodesDesc) {
        this.stdCodesDescs.add(stdCodesDesc);
        stdCodesDesc.setStdCodes(this);
        return this;
    }

    public StdCodes removeStdCodesDesc(StdCodesDesc stdCodesDesc) {
        this.stdCodesDescs.remove(stdCodesDesc);
        stdCodesDesc.setStdCodes(null);
        return this;
    }

    public void setStdCodesDescs(Set<StdCodesDesc> stdCodesDescs) {
        this.stdCodesDescs = stdCodesDescs;
    }

    public StdCodesGroup getStdCodesGroup() {
        return stdCodesGroup;
    }

    public StdCodes stdCodesGroup(StdCodesGroup stdCodesGroup) {
        this.stdCodesGroup = stdCodesGroup;
        return this;
    }

    public void setStdCodesGroup(StdCodesGroup stdCodesGroup) {
        this.stdCodesGroup = stdCodesGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StdCodes)) {
            return false;
        }
        return id != null && id.equals(((StdCodes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StdCodes{" +
            "id=" + getId() +
            ", groupCode='" + getGroupCode() + "'" +
            ", internalCode='" + getInternalCode() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", parentInternalCode='" + getParentInternalCode() + "'" +
            ", comments='" + getComments() + "'" +
            ", secLevel=" + getSecLevel() +
            ", codeValueDate='" + getCodeValueDate() + "'" +
            ", codeValueString='" + getCodeValueString() + "'" +
            ", codeValueBool='" + isCodeValueBool() + "'" +
            ", codeValueNumber=" + getCodeValueNumber() +
            "}";
    }
}
