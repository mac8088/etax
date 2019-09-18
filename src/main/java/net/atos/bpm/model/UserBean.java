package net.atos.bpm.model;

import org.flowable.idm.api.Picture;
import org.flowable.idm.engine.impl.persistence.entity.ByteArrayRef;
import org.flowable.idm.engine.impl.persistence.entity.UserEntity;

/**
 * Created by ahoo on 2017/7/18.
 */
public class UserBean implements UserEntity {

	private static final long serialVersionUID = 1L;

	private String id;
	private String password;

	private String displayName;
	private String firstName;
	private String lastName;

	private String email;
	private Picture picture;

	private String tenantId;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getFirstName() {
		return this.firstName;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Picture getPicture() {
		return picture;
	}

	@Override
	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	@Override
	public String getTenantId() {
		return tenantId;
	}

	@Override
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@Override
	public boolean isInserted() {
		return false;
	}

	@Override
	public void setInserted(boolean b) {
	}

	@Override
	public boolean isUpdated() {
		return false;
	}

	@Override
	public void setUpdated(boolean b) {
	}

	@Override
	public boolean isDeleted() {
		return false;
	}

	@Override
	public void setDeleted(boolean b) {
	}

	@Override
	public Object getPersistentState() {
		return null;
	}

	@Override
	public boolean isPictureSet() {
		return false;
	}

	@Override
	public ByteArrayRef getPictureByteArrayRef() {
		return null;
	}

	@Override
	public void setRevision(int i) {
	}

	@Override
	public int getRevision() {
		return 0;
	}

	@Override
	public int getRevisionNext() {
		return 0;
	}

	@Override
	public String getIdPrefix() {
		return null;
	}

	@Override
	public Object getOriginalPersistentState() {
		return null;
	}

	@Override
	public void setOriginalPersistentState(Object persistentState) {
	}

}
