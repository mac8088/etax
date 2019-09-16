package net.atos.etax.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * The Office data segment holds the information of all the Tax Authority's offices that access the e-tax system.
 */
@ApiModel(description = "The Office data segment holds the information of all the Tax Authority's offices that access the e-tax system.")
@Entity
@Table(name = "adm_office")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Office implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * STD[OFFICE_TYPE]
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "STD[OFFICE_TYPE]", required = true)
    @Column(name = "cstd_office_type", length = 40, nullable = false)
    private String cstdOfficeType;

    /**
     * Office name
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "Office name", required = true)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * STD[CLASSIFIER_CODE]
     */
    @Size(max = 40)
    @ApiModelProperty(value = "STD[CLASSIFIER_CODE]")
    @Column(name = "cstd_classifier_code", length = 40)
    private String cstdClassifierCode;

    /**
     * Effective date
     */
    @NotNull
    @ApiModelProperty(value = "Effective date", required = true)
    @Column(name = "effective_date", nullable = false)
    private ZonedDateTime effectiveDate;

    /**
     * Expiry date
     */
    @ApiModelProperty(value = "Expiry date")
    @Column(name = "expiry_date")
    private ZonedDateTime expiryDate;

    /**
     * Office telephone
     */
    @Size(max = 50)
    @ApiModelProperty(value = "Office telephone")
    @Column(name = "phone", length = 50)
    private String phone;

    /**
     * Office fax number
     */
    @Size(max = 50)
    @ApiModelProperty(value = "Office fax number")
    @Column(name = "fax", length = 50)
    private String fax;

    /**
     * Office manager for signature
     */
    @Size(max = 100)
    @ApiModelProperty(value = "Office manager for signature")
    @Column(name = "stl", length = 100)
    private String stl;

    /**
     * Foreign key to another office that manages users of this office, It must have office type that allows Office Administrator(ADM-CFG: Allowed Users in Offices)
     */
    @ApiModelProperty(value = "Foreign key to another office that manages users of this office, It must have office type that allows Office Administrator(ADM-CFG: Allowed Users in Offices)")
    @Column(name = "mng_office")
    private Integer mngOffice;

    /**
     * Physical Address
     */
    @ApiModelProperty(value = "Physical Address")
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "physical_adr")
    private String physicalAdr;

    /**
     * Postal Address
     */
    @ApiModelProperty(value = "Postal Address")
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "postal_aadr")
    private String postalAadr;

    /**
     * Postal Identification Number
     */
    @Size(max = 15)
    @ApiModelProperty(value = "Postal Identification Number")
    @Column(name = "pin_code", length = 15)
    private String pinCode;

    /**
     * Effective date
     */
    @Size(max = 40)
    @ApiModelProperty(value = "Effective date")
    @Column(name = "cstd_week_working_day", length = 40)
    private String cstdWeekWorkingDay;

    /**
     * Unique Office codee
     */
    @Size(max = 7)
    @ApiModelProperty(value = "Unique Office codee")
    @Column(name = "office_code", length = 7)
    private String officeCode;

    /**
     * STD[OFFICE_SUB_TYPE]
     */
    @Size(max = 40)
    @ApiModelProperty(value = "STD[OFFICE_SUB_TYPE]")
    @Column(name = "cstd_office_sub_type", length = 40)
    private String cstdOfficeSubType;

    /**
     * STD[OFFICE_FUNC_TYPE],functional office type
     */
    @Size(max = 40)
    @ApiModelProperty(value = "STD[OFFICE_FUNC_TYPE],functional office type")
    @Column(name = "cstd_office_func_type", length = 40)
    private String cstdOfficeFuncType;

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

    public String getCstdOfficeType() {
        return cstdOfficeType;
    }

    public Office cstdOfficeType(String cstdOfficeType) {
        this.cstdOfficeType = cstdOfficeType;
        return this;
    }

    public void setCstdOfficeType(String cstdOfficeType) {
        this.cstdOfficeType = cstdOfficeType;
    }

    public String getName() {
        return name;
    }

    public Office name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCstdClassifierCode() {
        return cstdClassifierCode;
    }

    public Office cstdClassifierCode(String cstdClassifierCode) {
        this.cstdClassifierCode = cstdClassifierCode;
        return this;
    }

    public void setCstdClassifierCode(String cstdClassifierCode) {
        this.cstdClassifierCode = cstdClassifierCode;
    }

    public ZonedDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public Office effectiveDate(ZonedDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(ZonedDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public Office expiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPhone() {
        return phone;
    }

    public Office phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public Office fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getStl() {
        return stl;
    }

    public Office stl(String stl) {
        this.stl = stl;
        return this;
    }

    public void setStl(String stl) {
        this.stl = stl;
    }

    public Integer getMngOffice() {
        return mngOffice;
    }

    public Office mngOffice(Integer mngOffice) {
        this.mngOffice = mngOffice;
        return this;
    }

    public void setMngOffice(Integer mngOffice) {
        this.mngOffice = mngOffice;
    }

    public String getPhysicalAdr() {
        return physicalAdr;
    }

    public Office physicalAdr(String physicalAdr) {
        this.physicalAdr = physicalAdr;
        return this;
    }

    public void setPhysicalAdr(String physicalAdr) {
        this.physicalAdr = physicalAdr;
    }

    public String getPostalAadr() {
        return postalAadr;
    }

    public Office postalAadr(String postalAadr) {
        this.postalAadr = postalAadr;
        return this;
    }

    public void setPostalAadr(String postalAadr) {
        this.postalAadr = postalAadr;
    }

    public String getPinCode() {
        return pinCode;
    }

    public Office pinCode(String pinCode) {
        this.pinCode = pinCode;
        return this;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getCstdWeekWorkingDay() {
        return cstdWeekWorkingDay;
    }

    public Office cstdWeekWorkingDay(String cstdWeekWorkingDay) {
        this.cstdWeekWorkingDay = cstdWeekWorkingDay;
        return this;
    }

    public void setCstdWeekWorkingDay(String cstdWeekWorkingDay) {
        this.cstdWeekWorkingDay = cstdWeekWorkingDay;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public Office officeCode(String officeCode) {
        this.officeCode = officeCode;
        return this;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getCstdOfficeSubType() {
        return cstdOfficeSubType;
    }

    public Office cstdOfficeSubType(String cstdOfficeSubType) {
        this.cstdOfficeSubType = cstdOfficeSubType;
        return this;
    }

    public void setCstdOfficeSubType(String cstdOfficeSubType) {
        this.cstdOfficeSubType = cstdOfficeSubType;
    }

    public String getCstdOfficeFuncType() {
        return cstdOfficeFuncType;
    }

    public Office cstdOfficeFuncType(String cstdOfficeFuncType) {
        this.cstdOfficeFuncType = cstdOfficeFuncType;
        return this;
    }

    public void setCstdOfficeFuncType(String cstdOfficeFuncType) {
        this.cstdOfficeFuncType = cstdOfficeFuncType;
    }

    public Integer getCcVersion() {
        return ccVersion;
    }

    public Office ccVersion(Integer ccVersion) {
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
        if (!(o instanceof Office)) {
            return false;
        }
        return id != null && id.equals(((Office) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Office{" +
            "id=" + getId() +
            ", cstdOfficeType='" + getCstdOfficeType() + "'" +
            ", name='" + getName() + "'" +
            ", cstdClassifierCode='" + getCstdClassifierCode() + "'" +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", phone='" + getPhone() + "'" +
            ", fax='" + getFax() + "'" +
            ", stl='" + getStl() + "'" +
            ", mngOffice=" + getMngOffice() +
            ", physicalAdr='" + getPhysicalAdr() + "'" +
            ", postalAadr='" + getPostalAadr() + "'" +
            ", pinCode='" + getPinCode() + "'" +
            ", cstdWeekWorkingDay='" + getCstdWeekWorkingDay() + "'" +
            ", officeCode='" + getOfficeCode() + "'" +
            ", cstdOfficeSubType='" + getCstdOfficeSubType() + "'" +
            ", cstdOfficeFuncType='" + getCstdOfficeFuncType() + "'" +
            ", ccVersion=" + getCcVersion() +
            "}";
    }
}
