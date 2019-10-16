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

/**
 * Criteria class for the {@link net.atos.etax.domain.Uiapp} entity. This class is used
 * in {@link net.atos.etax.web.rest.UiappResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /uiapps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UiappCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter appCode;

    private StringFilter appName;

    private StringFilter appDesc;

    private StringFilter cstdModule;

    private StringFilter appMessage;

    private LongFilter resourceId;

    public UiappCriteria(){
    }

    public UiappCriteria(UiappCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.appCode = other.appCode == null ? null : other.appCode.copy();
        this.appName = other.appName == null ? null : other.appName.copy();
        this.appDesc = other.appDesc == null ? null : other.appDesc.copy();
        this.cstdModule = other.cstdModule == null ? null : other.cstdModule.copy();
        this.appMessage = other.appMessage == null ? null : other.appMessage.copy();
        this.resourceId = other.resourceId == null ? null : other.resourceId.copy();
    }

    @Override
    public UiappCriteria copy() {
        return new UiappCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAppCode() {
        return appCode;
    }

    public void setAppCode(StringFilter appCode) {
        this.appCode = appCode;
    }

    public StringFilter getAppName() {
        return appName;
    }

    public void setAppName(StringFilter appName) {
        this.appName = appName;
    }

    public StringFilter getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(StringFilter appDesc) {
        this.appDesc = appDesc;
    }

    public StringFilter getCstdModule() {
        return cstdModule;
    }

    public void setCstdModule(StringFilter cstdModule) {
        this.cstdModule = cstdModule;
    }

    public StringFilter getAppMessage() {
        return appMessage;
    }

    public void setAppMessage(StringFilter appMessage) {
        this.appMessage = appMessage;
    }

    public LongFilter getResourceId() {
        return resourceId;
    }

    public void setResourceId(LongFilter resourceId) {
        this.resourceId = resourceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UiappCriteria that = (UiappCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(appCode, that.appCode) &&
            Objects.equals(appName, that.appName) &&
            Objects.equals(appDesc, that.appDesc) &&
            Objects.equals(cstdModule, that.cstdModule) &&
            Objects.equals(appMessage, that.appMessage) &&
            Objects.equals(resourceId, that.resourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        appCode,
        appName,
        appDesc,
        cstdModule,
        appMessage,
        resourceId
        );
    }

    @Override
    public String toString() {
        return "UiappCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (appCode != null ? "appCode=" + appCode + ", " : "") +
                (appName != null ? "appName=" + appName + ", " : "") +
                (appDesc != null ? "appDesc=" + appDesc + ", " : "") +
                (cstdModule != null ? "cstdModule=" + cstdModule + ", " : "") +
                (appMessage != null ? "appMessage=" + appMessage + ", " : "") +
                (resourceId != null ? "resourceId=" + resourceId + ", " : "") +
            "}";
    }

}
