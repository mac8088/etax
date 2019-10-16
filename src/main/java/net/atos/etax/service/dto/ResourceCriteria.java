package net.atos.etax.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import net.atos.etax.domain.enumeration.ResourceType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link net.atos.etax.domain.Resource} entity. This class is used
 * in {@link net.atos.etax.web.rest.ResourceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /resources?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResourceCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ResourceType
     */
    public static class ResourceTypeFilter extends Filter<ResourceType> {

        public ResourceTypeFilter() {
        }

        public ResourceTypeFilter(ResourceTypeFilter filter) {
            super(filter);
        }

        @Override
        public ResourceTypeFilter copy() {
            return new ResourceTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter resCode;

    private StringFilter resName;

    private ResourceTypeFilter resType;

    private StringFilter appDesc;

    private StringFilter cstdModule;

    private StringFilter resContent;

    private LongFilter uiappId;

    public ResourceCriteria(){
    }

    public ResourceCriteria(ResourceCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.resCode = other.resCode == null ? null : other.resCode.copy();
        this.resName = other.resName == null ? null : other.resName.copy();
        this.resType = other.resType == null ? null : other.resType.copy();
        this.appDesc = other.appDesc == null ? null : other.appDesc.copy();
        this.cstdModule = other.cstdModule == null ? null : other.cstdModule.copy();
        this.resContent = other.resContent == null ? null : other.resContent.copy();
        this.uiappId = other.uiappId == null ? null : other.uiappId.copy();
    }

    @Override
    public ResourceCriteria copy() {
        return new ResourceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getResCode() {
        return resCode;
    }

    public void setResCode(StringFilter resCode) {
        this.resCode = resCode;
    }

    public StringFilter getResName() {
        return resName;
    }

    public void setResName(StringFilter resName) {
        this.resName = resName;
    }

    public ResourceTypeFilter getResType() {
        return resType;
    }

    public void setResType(ResourceTypeFilter resType) {
        this.resType = resType;
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

    public StringFilter getResContent() {
        return resContent;
    }

    public void setResContent(StringFilter resContent) {
        this.resContent = resContent;
    }

    public LongFilter getUiappId() {
        return uiappId;
    }

    public void setUiappId(LongFilter uiappId) {
        this.uiappId = uiappId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ResourceCriteria that = (ResourceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(resCode, that.resCode) &&
            Objects.equals(resName, that.resName) &&
            Objects.equals(resType, that.resType) &&
            Objects.equals(appDesc, that.appDesc) &&
            Objects.equals(cstdModule, that.cstdModule) &&
            Objects.equals(resContent, that.resContent) &&
            Objects.equals(uiappId, that.uiappId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        resCode,
        resName,
        resType,
        appDesc,
        cstdModule,
        resContent,
        uiappId
        );
    }

    @Override
    public String toString() {
        return "ResourceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (resCode != null ? "resCode=" + resCode + ", " : "") +
                (resName != null ? "resName=" + resName + ", " : "") +
                (resType != null ? "resType=" + resType + ", " : "") +
                (appDesc != null ? "appDesc=" + appDesc + ", " : "") +
                (cstdModule != null ? "cstdModule=" + cstdModule + ", " : "") +
                (resContent != null ? "resContent=" + resContent + ", " : "") +
                (uiappId != null ? "uiappId=" + uiappId + ", " : "") +
            "}";
    }

}
