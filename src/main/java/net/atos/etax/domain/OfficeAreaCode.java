package net.atos.etax.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * This entity stores all of area codes of Tax Authority offices.
 */
@ApiModel(description = "This entity stores all of area codes of Tax Authority offices.")
@Entity
@Table(name = "adm_office_areacode")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OfficeAreaCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "office_id", nullable = false)
    private Integer officeId;

    @NotNull
    @Size(max = 10)
    @Column(name = "from_pin", length = 10, nullable = false)
    private String fromPin;

    @Size(max = 10)
    @Column(name = "to_pin", length = 10)
    private String toPin;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOfficeId() {
        return officeId;
    }

    public OfficeAreaCode officeId(Integer officeId) {
        this.officeId = officeId;
        return this;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public String getFromPin() {
        return fromPin;
    }

    public OfficeAreaCode fromPin(String fromPin) {
        this.fromPin = fromPin;
        return this;
    }

    public void setFromPin(String fromPin) {
        this.fromPin = fromPin;
    }

    public String getToPin() {
        return toPin;
    }

    public OfficeAreaCode toPin(String toPin) {
        this.toPin = toPin;
        return this;
    }

    public void setToPin(String toPin) {
        this.toPin = toPin;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfficeAreaCode)) {
            return false;
        }
        return id != null && id.equals(((OfficeAreaCode) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OfficeAreaCode{" +
            "id=" + getId() +
            ", officeId=" + getOfficeId() +
            ", fromPin='" + getFromPin() + "'" +
            ", toPin='" + getToPin() + "'" +
            "}";
    }
}
