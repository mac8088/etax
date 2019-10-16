package net.atos.etax.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import net.atos.etax.domain.enumeration.ResourceType;

/**
 * The Resource segment holds all data related to a \"resource\": a resource is a functional unit on which access is controlled.
 */
@ApiModel(description = "The Resource segment holds all data related to a \"resource\": a resource is a functional unit on which access is controlled.")
@Entity
@Table(name = "adm_resource")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "res_code", length = 100, nullable = false, unique = true)
    private String resCode;

    @Size(max = 200)
    @Column(name = "res_name", length = 200)
    private String resName;

    @Enumerated(EnumType.STRING)
    @Column(name = "res_type")
    private ResourceType resType;

    @Size(max = 500)
    @Column(name = "app_desc", length = 500)
    private String appDesc;

    @Size(max = 40)
    @Column(name = "cstd_module", length = 40)
    private String cstdModule;

    @Size(max = 500)
    @Column(name = "res_content", length = 500)
    private String resContent;

    @ManyToMany(mappedBy = "resources")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Uiapp> uiapps = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResCode() {
        return resCode;
    }

    public Resource resCode(String resCode) {
        this.resCode = resCode;
        return this;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResName() {
        return resName;
    }

    public Resource resName(String resName) {
        this.resName = resName;
        return this;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public ResourceType getResType() {
        return resType;
    }

    public Resource resType(ResourceType resType) {
        this.resType = resType;
        return this;
    }

    public void setResType(ResourceType resType) {
        this.resType = resType;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public Resource appDesc(String appDesc) {
        this.appDesc = appDesc;
        return this;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getCstdModule() {
        return cstdModule;
    }

    public Resource cstdModule(String cstdModule) {
        this.cstdModule = cstdModule;
        return this;
    }

    public void setCstdModule(String cstdModule) {
        this.cstdModule = cstdModule;
    }

    public String getResContent() {
        return resContent;
    }

    public Resource resContent(String resContent) {
        this.resContent = resContent;
        return this;
    }

    public void setResContent(String resContent) {
        this.resContent = resContent;
    }

    public Set<Uiapp> getUiapps() {
        return uiapps;
    }

    public Resource uiapps(Set<Uiapp> uiapps) {
        this.uiapps = uiapps;
        return this;
    }

    public Resource addUiapp(Uiapp uiapp) {
        this.uiapps.add(uiapp);
        uiapp.getResources().add(this);
        return this;
    }

    public Resource removeUiapp(Uiapp uiapp) {
        this.uiapps.remove(uiapp);
        uiapp.getResources().remove(this);
        return this;
    }

    public void setUiapps(Set<Uiapp> uiapps) {
        this.uiapps = uiapps;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resource)) {
            return false;
        }
        return id != null && id.equals(((Resource) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Resource{" +
            "id=" + getId() +
            ", resCode='" + getResCode() + "'" +
            ", resName='" + getResName() + "'" +
            ", resType='" + getResType() + "'" +
            ", appDesc='" + getAppDesc() + "'" +
            ", cstdModule='" + getCstdModule() + "'" +
            ", resContent='" + getResContent() + "'" +
            "}";
    }
}
