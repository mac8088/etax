package net.atos.bpm.model;

import java.util.Date;

public class ProcessBean {
	private String processInstanceId;
	private String executionId;
	private String name;
	private String description;
	private String businessKey;
	private Date startTime;
	private String startUser;
	private String processDefinitionId;
	private String processDefinitionName;
	private String processDefinitionKey;

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessDefinitionName() {
		return processDefinitionName;
	}

	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStartUser() {
		return startUser;
	}

	public void setStartUser(String startUser) {
		this.startUser = startUser;
	}

	@Override
	public String toString() {
		return "Process{" + "executionId='" + executionId + '\'' + ", processInstanceId='" + processInstanceId + '\''
				+ ", name='" + name + '\'' + ", description='" + description + '\'' + ", processDefinitionId='"
				+ processDefinitionId + '\'' + ", processDefinitionName='" + processDefinitionName + '\''
				+ ", processDefinitionKey='" + processDefinitionKey + '\'' + ", startTime=" + startTime
				+ ", startUser='" + startUser + '\'' + '}';
	}
}
