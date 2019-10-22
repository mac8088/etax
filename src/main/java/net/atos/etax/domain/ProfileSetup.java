package net.atos.etax.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * Defines which type of user can be created in which type of office-Allowed Profiles for User Types and Office Types.
 */
@ApiModel(description = "Defines which type of user can be created in which type of office-Allowed Profiles for User Types and Office Types.")
@Entity
@Table(name = "adm_profile_setup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProfileSetup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "profile_code", length = 50, nullable = false)
    private String profileCode;

    @NotNull
    @Size(max = 40)
    @Column(name = "cstd_office_type", length = 40, nullable = false)
    private String cstdOfficeType;

    @NotNull
    @Size(max = 40)
    @Column(name = "cstd_user_type", length = 40, nullable = false)
    private String cstdUserType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfileCode() {
        return profileCode;
    }

    public ProfileSetup profileCode(String profileCode) {
        this.profileCode = profileCode;
        return this;
    }

    public void setProfileCode(String profileCode) {
        this.profileCode = profileCode;
    }

    public String getCstdOfficeType() {
        return cstdOfficeType;
    }

    public ProfileSetup cstdOfficeType(String cstdOfficeType) {
        this.cstdOfficeType = cstdOfficeType;
        return this;
    }

    public void setCstdOfficeType(String cstdOfficeType) {
        this.cstdOfficeType = cstdOfficeType;
    }

    public String getCstdUserType() {
        return cstdUserType;
    }

    public ProfileSetup cstdUserType(String cstdUserType) {
        this.cstdUserType = cstdUserType;
        return this;
    }

    public void setCstdUserType(String cstdUserType) {
        this.cstdUserType = cstdUserType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfileSetup)) {
            return false;
        }
        return id != null && id.equals(((ProfileSetup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProfileSetup{" +
            "id=" + getId() +
            ", profileCode='" + getProfileCode() + "'" +
            ", cstdOfficeType='" + getCstdOfficeType() + "'" +
            ", cstdUserType='" + getCstdUserType() + "'" +
            "}";
    }
}
