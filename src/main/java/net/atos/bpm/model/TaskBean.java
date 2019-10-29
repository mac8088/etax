package net.atos.bpm.model;

import java.util.Date;

public class TaskBean {
    private String id;
    private String name;  // pending activity
    private String description; // or this store messageId
    private Date createTime;  // pending since
    private String assignee;
    private Integer priority;
    private String processInstanceId;
    private String processDefinitionId;
    private String executionId;
    private String taskDefinitionId;
    private String taskDefinitionKey;
    
    private String entityIdentifier;
    private String pendingSince;
    private String dueBy;
    
    private String formKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getTaskDefinitionId() {
        return taskDefinitionId;
    }

    public void setTaskDefinitionId(String taskDefinitionId) {
        this.taskDefinitionId = taskDefinitionId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getEntityIdentifier() {
		return entityIdentifier;
	}

	public void setEntityIdentifier(String entityIdentifier) {
		this.entityIdentifier = entityIdentifier;
	}

	public String getPendingSince() {
		return pendingSince;
	}

	public void setPendingSince(String pendingSince) {
		this.pendingSince = pendingSince;
	}
	
	public String getDueBy() {
		return dueBy;
	}

	public void setDueBy(String dueBy) {
		this.dueBy = dueBy;
	}
		
	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	@Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", assignee='" + assignee + '\'' +
                ", entityIdentifier='" + entityIdentifier + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", executionId='" + executionId + '\'' +
                ", taskDefinitionId='" + taskDefinitionId + '\'' +
                ", processDefinitionId='" + processDefinitionId + '\'' +
                ", createTime=" + createTime +
                ", taskDefinitionKey='" + taskDefinitionKey + '\'' +
				", formKey='" + formKey + '\'' +
                '}';
    }
}
