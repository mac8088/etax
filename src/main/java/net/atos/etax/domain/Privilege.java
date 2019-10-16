package net.atos.etax.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * The UIAPP to Profile Link data segment holds data related to links between applications and profiles.
 */
@ApiModel(description = "The UIAPP to Profile Link data segment holds data related to links between applications and profiles.")
@Entity
@Table(name = "adm_uiapp_privilege")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Privilege implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "app_code", length = 50, nullable = false)
    private String appCode;

    @NotNull
    @Size(max = 50)
    @Column(name = "user_name", length = 50, nullable = false)
    private String userName;

    @NotNull
    @Size(max = 50)
    @Column(name = "profile_name", length = 50, nullable = false)
    private String profileName;

    @Column(name = "jhi_limit")
    private Integer limit;

    @Size(max = 20)
    @Column(name = "confirm_status", length = 20)
    private String confirmStatus;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppCode() {
        return appCode;
    }

    public Privilege appCode(String appCode) {
        this.appCode = appCode;
        return this;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getUserName() {
        return userName;
    }

    public Privilege userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileName() {
        return profileName;
    }

    public Privilege profileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public Integer getLimit() {
        return limit;
    }

    public Privilege limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public Privilege confirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
        return this;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public ZonedDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public Privilege effectiveDate(ZonedDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(ZonedDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public Privilege expiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Privilege)) {
            return false;
        }
        return id != null && id.equals(((Privilege) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Privilege{" +
            "id=" + getId() +
            ", appCode='" + getAppCode() + "'" +
            ", userName='" + getUserName() + "'" +
            ", profileName='" + getProfileName() + "'" +
            ", limit=" + getLimit() +
            ", confirmStatus='" + getConfirmStatus() + "'" +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            "}";
    }
}
