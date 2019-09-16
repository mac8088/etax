package net.atos.etax.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Exchange Rate.
 */
@ApiModel(description = "Exchange Rate.")
@Entity
@Table(name = "adm_exchange_rate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExchangeRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * STD[CURRENCY], Source currency, for conversion considerations
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "STD[CURRENCY], Source currency, for conversion considerations", required = true)
    @Column(name = "cstd_currency_a", length = 40, nullable = false)
    private String cstdCurrencyA;

    /**
     * STD[CURRENCY], Source currency, for conversion considerations
     */
    @NotNull
    @Size(max = 40)
    @ApiModelProperty(value = "STD[CURRENCY], Source currency, for conversion considerations", required = true)
    @Column(name = "cstd_currency_b", length = 40, nullable = false)
    private String cstdCurrencyB;

    /**
     * Rate to apply (multiply) to the source currency amount to
     */
    @NotNull
    @ApiModelProperty(value = "Rate to apply (multiply) to the source currency amount to", required = true)
    @Column(name = "rate", nullable = false)
    private Double rate;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

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

    public String getCstdCurrencyA() {
        return cstdCurrencyA;
    }

    public ExchangeRate cstdCurrencyA(String cstdCurrencyA) {
        this.cstdCurrencyA = cstdCurrencyA;
        return this;
    }

    public void setCstdCurrencyA(String cstdCurrencyA) {
        this.cstdCurrencyA = cstdCurrencyA;
    }

    public String getCstdCurrencyB() {
        return cstdCurrencyB;
    }

    public ExchangeRate cstdCurrencyB(String cstdCurrencyB) {
        this.cstdCurrencyB = cstdCurrencyB;
        return this;
    }

    public void setCstdCurrencyB(String cstdCurrencyB) {
        this.cstdCurrencyB = cstdCurrencyB;
    }

    public Double getRate() {
        return rate;
    }

    public ExchangeRate rate(Double rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public ExchangeRate startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ExchangeRate endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getCcVersion() {
        return ccVersion;
    }

    public ExchangeRate ccVersion(Integer ccVersion) {
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
        if (!(o instanceof ExchangeRate)) {
            return false;
        }
        return id != null && id.equals(((ExchangeRate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
            "id=" + getId() +
            ", cstdCurrencyA='" + getCstdCurrencyA() + "'" +
            ", cstdCurrencyB='" + getCstdCurrencyB() + "'" +
            ", rate=" + getRate() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", ccVersion=" + getCcVersion() +
            "}";
    }
}
