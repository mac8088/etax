package net.atos.bpm.model;

import org.flowable.idm.engine.impl.persistence.entity.GroupEntity;

/**
 * Created by ahoo on 2017/7/18.
 */
public class GroupBean implements GroupEntity {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String type;

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getIdPrefix() {
		return null;
	}

	@Override
	public boolean isInserted() {
		return false;
	}

	@Override
	public void setInserted(boolean inserted) {
	}

	@Override
	public boolean isUpdated() {
		return false;
	}

	@Override
	public void setUpdated(boolean updated) {
	}

	@Override
	public boolean isDeleted() {
		return false;
	}

	@Override
	public void setDeleted(boolean deleted) {
	}

	@Override
	public Object getPersistentState() {
		return null;
	}

	@Override
	public Object getOriginalPersistentState() {
		return null;
	}

	@Override
	public void setOriginalPersistentState(Object persistentState) {
	}

	@Override
	public void setRevision(int revision) {
	}

	@Override
	public int getRevision() {
		return 0;
	}

	@Override
	public int getRevisionNext() {
		return 0;
	}

}
