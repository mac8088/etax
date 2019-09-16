package net.atos.etax.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * The StdCodesDesc entity.
 * Multilingual descriptions of the codes.
 */
@ApiModel(description = "The StdCodesDesc entity. Multilingual descriptions of the codes.")
@Entity
@Table(name = "std_codes_desc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StdCodesDesc implements Serializable {

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
     * Internal code of the element in STD_CODES.
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "Internal code of the element in STD_CODES.", required = true)
    @Column(name = "internal_code", length = 40, nullable = false)
    private String internalCode;

    /**
     * Language of the description.
     */
    @NotNull
    @Size(max = 10)
    @ApiModelProperty(value = "Language of the description.", required = true)
    @Column(name = "lang_code", length = 10, nullable = false)
    private String langCode;

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
     * External code displayed to the user for this language. Used in entry fields next to drop-down lists of values. Used only when STD_CODES_GROUP.EXTERNAL_CODE_REQUIRED = ''Y''.
     */
    @Size(max = 40)
    @ApiModelProperty(value = "External code displayed to the user for this language. Used in entry fields next to drop-down lists of values. Used only when STD_CODES_GROUP.EXTERNAL_CODE_REQUIRED = ''Y''.")
    @Column(name = "external_code", length = 40)
    private String externalCode;

    /**
     * Multilingual description of the element for this language. Used to populate lists of values.
     */
    @Size(max = 500)
    @ApiModelProperty(value = "Multilingual description of the element for this language. Used to populate lists of values.")
    @Column(name = "code_desc", length = 500)
    private String codeDesc;

    @ManyToOne
    @JsonIgnoreProperties("stdCodesDescs")
    private StdCodes stdCodes;

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

    public StdCodesDesc groupCode(String groupCode) {
        this.groupCode = groupCode;
        return this;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public StdCodesDesc internalCode(String internalCode) {
        this.internalCode = internalCode;
        return this;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public String getLangCode() {
        return langCode;
    }

    public StdCodesDesc langCode(String langCode) {
        this.langCode = langCode;
        return this;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public StdCodesDesc startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public StdCodesDesc endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getExternalCode() {
        return externalCode;
    }

    public StdCodesDesc externalCode(String externalCode) {
        this.externalCode = externalCode;
        return this;
    }

    public void setExternalCode(String externalCode) {
        this.externalCode = externalCode;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public StdCodesDesc codeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
        return this;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public StdCodes getStdCodes() {
        return stdCodes;
    }

    public StdCodesDesc stdCodes(StdCodes stdCodes) {
        this.stdCodes = stdCodes;
        return this;
    }

    public void setStdCodes(StdCodes stdCodes) {
        this.stdCodes = stdCodes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StdCodesDesc)) {
            return false;
        }
        return id != null && id.equals(((StdCodesDesc) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StdCodesDesc{" +
            "id=" + getId() +
            ", groupCode='" + getGroupCode() + "'" +
            ", internalCode='" + getInternalCode() + "'" +
            ", langCode='" + getLangCode() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", externalCode='" + getExternalCode() + "'" +
            ", codeDesc='" + getCodeDesc() + "'" +
            "}";
    }
}
