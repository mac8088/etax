package net.atos.etax.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Authority (Profile) Entity
 * An authority (a security Profile) used by Spring Security.
 */
@Entity
@Table(name = "jhi_authority")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max = 50)
	@Id
	@Column(length = 50)
	/** name of the profile. */
	private String name;

	@Size(max = 200)
	@Column(length = 200)
	/** Description of the profile. */
	private String description;

	@NotNull
	@Size(max = 40)
	@Column(length = 40)
	/** STD[MODULE], name of the module this application belongs to. */
	private String cstd_module;
	
    /**
     * Effective date
     */
    @NotNull
    @ApiModelProperty(value = "Effective date", required = true)
    @Column(name = "effective_date", nullable = false)
    private ZonedDateTime effectiveDate;

    /**
     * Expired date
     */
    @ApiModelProperty(value = "Expiry date")
    @Column(name = "expiry_date")
    private ZonedDateTime expiryDate;
	
	@Size(max = 40)
	@Column(length = 40)
	/** STD[STATUS], the status of the current profile. */
	private String cstd_status;

	@Size(max = 50)
	@Column(length = 50)
	/** If defined, ID of the Supervisor Profile. */
	private String supvr_profile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCstd_module() {
		return cstd_module;
	}

	public void setCstd_module(String cstd_module) {
		this.cstd_module = cstd_module;
	}

    public ZonedDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public Authority effectiveDate(ZonedDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(ZonedDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public Authority expiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

	public String getCstd_status() {
		return cstd_status;
	}

	public void setCstd_status(String cstd_status) {
		this.cstd_status = cstd_status;
	}

	public String getSupvr_profile() {
		return supvr_profile;
	}

	public void setSupvr_profile(String supvr_profile) {
		this.supvr_profile = supvr_profile;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Authority)) {
			return false;
		}
		return Objects.equals(name, ((Authority) o).name);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}

	@Override
	public String toString() {
        return "Authority{" +
	        "name='" + name + '\'' +
	        ", desc='" + description + '\'' +
	        ", module='" + cstd_module + '\'' +
	        ", superior='" + supvr_profile + '\'' +
        "}";
	}
}
