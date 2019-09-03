package net.atos.etax.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * Authority (Role) Entity
 * An authority (a security role) used by Spring Security.
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
	/** name of the role. */
	private String name;

	@Size(max = 200)
	@Column(length = 200)
	/** Description of the role. */
	private String desc;

	@NotNull
	@Size(max = 40)
	@Column(length = 40)
	/** STD[MODULE], name of the module this application belongs to. */
	private String module;

	@Size(max = 50)
	@Column(length = 50)
	/** Application Message ID in FF_MESSAGE. */
	private String appMessage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getAppMessage() {
		return appMessage;
	}

	public void setAppMessage(String appMessage) {
		this.appMessage = appMessage;
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
	        ", desc='" + desc + '\'' +
	        ", module='" + module + '\'' +
	        ", appMessage='" + appMessage + '\'' +
        "}";
	}
}
