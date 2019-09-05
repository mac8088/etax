package net.atos.etax.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link net.atos.etax.domain.ExchangeRate} entity. This class is used
 * in {@link net.atos.etax.web.rest.ExchangeRateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /exchange-rates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExchangeRateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cstdCurrencyA;

    private StringFilter cstdCurrencyB;

    private DoubleFilter rate;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private IntegerFilter ccVersion;

    public ExchangeRateCriteria(){
    }

    public ExchangeRateCriteria(ExchangeRateCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.cstdCurrencyA = other.cstdCurrencyA == null ? null : other.cstdCurrencyA.copy();
        this.cstdCurrencyB = other.cstdCurrencyB == null ? null : other.cstdCurrencyB.copy();
        this.rate = other.rate == null ? null : other.rate.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.ccVersion = other.ccVersion == null ? null : other.ccVersion.copy();
    }

    @Override
    public ExchangeRateCriteria copy() {
        return new ExchangeRateCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCstdCurrencyA() {
        return cstdCurrencyA;
    }

    public void setCstdCurrencyA(StringFilter cstdCurrencyA) {
        this.cstdCurrencyA = cstdCurrencyA;
    }

    public StringFilter getCstdCurrencyB() {
        return cstdCurrencyB;
    }

    public void setCstdCurrencyB(StringFilter cstdCurrencyB) {
        this.cstdCurrencyB = cstdCurrencyB;
    }

    public DoubleFilter getRate() {
        return rate;
    }

    public void setRate(DoubleFilter rate) {
        this.rate = rate;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public IntegerFilter getCcVersion() {
        return ccVersion;
    }

    public void setCcVersion(IntegerFilter ccVersion) {
        this.ccVersion = ccVersion;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExchangeRateCriteria that = (ExchangeRateCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cstdCurrencyA, that.cstdCurrencyA) &&
            Objects.equals(cstdCurrencyB, that.cstdCurrencyB) &&
            Objects.equals(rate, that.rate) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(ccVersion, that.ccVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cstdCurrencyA,
        cstdCurrencyB,
        rate,
        startDate,
        endDate,
        ccVersion
        );
    }

    @Override
    public String toString() {
        return "ExchangeRateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cstdCurrencyA != null ? "cstdCurrencyA=" + cstdCurrencyA + ", " : "") +
                (cstdCurrencyB != null ? "cstdCurrencyB=" + cstdCurrencyB + ", " : "") +
                (rate != null ? "rate=" + rate + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (ccVersion != null ? "ccVersion=" + ccVersion + ", " : "") +
            "}";
    }

}
