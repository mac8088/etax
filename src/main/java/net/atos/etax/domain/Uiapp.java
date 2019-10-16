package net.atos.etax.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Table for defining and maintaining UI apps.
 */
@ApiModel(description = "Table for defining and maintaining UI apps.")
@Entity
@Table(name = "adm_uiapp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Uiapp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "app_code", length = 50, nullable = false, unique = true)
    private String appCode;

    @Size(max = 200)
    @Column(name = "app_name", length = 200)
    private String appName;

    @Size(max = 500)
    @Column(name = "app_desc", length = 500)
    private String appDesc;

    @Size(max = 40)
    @Column(name = "cstd_module", length = 40)
    private String cstdModule;

    @Size(max = 50)
    @Column(name = "app_message", length = 50)
    private String appMessage;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "adm_uiapp_resource",
               joinColumns = @JoinColumn(name = "uiapp_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "resource_id", referencedColumnName = "id"))
    private Set<Resource> resources = new HashSet<>();

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

    public Uiapp appCode(String appCode) {
        this.appCode = appCode;
        return this;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public Uiapp appName(String appName) {
        this.appName = appName;
        return this;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public Uiapp appDesc(String appDesc) {
        this.appDesc = appDesc;
        return this;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getCstdModule() {
        return cstdModule;
    }

    public Uiapp cstdModule(String cstdModule) {
        this.cstdModule = cstdModule;
        return this;
    }

    public void setCstdModule(String cstdModule) {
        this.cstdModule = cstdModule;
    }

    public String getAppMessage() {
        return appMessage;
    }

    public Uiapp appMessage(String appMessage) {
        this.appMessage = appMessage;
        return this;
    }

    public void setAppMessage(String appMessage) {
        this.appMessage = appMessage;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public Uiapp resources(Set<Resource> resources) {
        this.resources = resources;
        return this;
    }

    public Uiapp addResource(Resource resource) {
        this.resources.add(resource);
        resource.getUiapps().add(this);
        return this;
    }

    public Uiapp removeResource(Resource resource) {
        this.resources.remove(resource);
        resource.getUiapps().remove(this);
        return this;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Uiapp)) {
            return false;
        }
        return id != null && id.equals(((Uiapp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Uiapp{" +
            "id=" + getId() +
            ", appCode='" + getAppCode() + "'" +
            ", appName='" + getAppName() + "'" +
            ", appDesc='" + getAppDesc() + "'" +
            ", cstdModule='" + getCstdModule() + "'" +
            ", appMessage='" + getAppMessage() + "'" +
            "}";
    }
}
