package net.atos.bpm.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.flowable.task.api.TaskQuery;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

@Service
public class WorkflowService {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private IdentityService identityService;

	/**
	 * @return the runtimeService
	 */
	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	/**
	 * @return the historyService
	 */
	public HistoryService getHistoryService() {
		return historyService;
	}

	/**
	 * @return the taskService
	 */
	public TaskService getTaskService() {
		return taskService;
	}

	/**
	 * @return the identityService
	 */
	public IdentityService getIdentityService() {
		return identityService;
	}

	private HistoricProcessInstance getProcessById(String processInstanceId) {
		return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	}

	/**
	 * convert task to <K, V> map
	 * 
	 * @return the map
	 */
	public Map<String, Object> taskToMap(Task task) {
		Map<String, Object> data = new HashMap<>();
		data.put("assignee", task.getAssignee());
		data.put("taskKey", task.getTaskDefinitionKey());
		data.put("taskName", task.getName());
		data.put("createTime", task.getCreateTime());
		data.put("formKey", task.getFormKey());
		data.put("taskId", task.getId());
		data.put("description", task.getDescription());
		data.put("claimTime", task.getClaimTime());
		HistoricProcessInstance instance = getProcessById(task.getProcessInstanceId());
		data.putAll(processToMap(instance));
		return data;
	}

	/**
	 * convert Historic Process Instance to <K, V> map
	 * 
	 * @return the map
	 */
	public Map<String, Object> processToMap(HistoricProcessInstance instance) {
		Map<String, Object> data = new HashMap<>();
		data.put("businessKey", instance.getBusinessKey());
		data.put("processDescription", instance.getDescription());
		data.put("processKey", instance.getProcessDefinitionKey());
		data.put("processInstanceId", instance.getId());
		data.put("processName", instance.getProcessDefinitionName());
		data.put("startTime", instance.getStartTime());
		data.put("startUserId", instance.getStartUserId());
		data.put("endTime", instance.getEndTime());
		data.put("deleteReason", instance.getDeleteReason());
		return data;
	}
	
	public TaskQuery buildTaskQuery(String assignee, String candidateGroup, String candidateUser, String processKey,
			String taskKey, String processInstanceId, String businessKey, Date fromDate, Date toDate,
			Map<String, Object> variables) {
		TaskQuery taskQuery = taskService.createTaskQuery();
		taskQuery = taskQuery.or();
		if (!Strings.isNullOrEmpty(assignee)) {
			if (assignee.indexOf(",") > -1) {
				taskQuery = taskQuery.taskAssigneeIds(Arrays.asList(assignee.split(",")));
			} else {
				taskQuery = taskQuery.taskAssignee(assignee);
			}
		}
		if (!Strings.isNullOrEmpty(candidateGroup)) {
			if (candidateGroup.indexOf(",") > -1) {
				taskQuery = taskQuery.taskCandidateGroupIn(Arrays.asList(candidateGroup.split(",")));
			} else {
				taskQuery = taskQuery.taskCandidateGroup(candidateGroup);
			}
		}
		if (!Strings.isNullOrEmpty(candidateUser)) {
			taskQuery = taskQuery.taskCandidateUser(candidateUser);
		}
		taskQuery.endOr();
		if (!Strings.isNullOrEmpty(processKey)) {
			taskQuery = taskQuery.processDefinitionKey(processKey);
		}
		if (!Strings.isNullOrEmpty(taskKey)) {
			taskQuery = taskQuery.taskDefinitionKey(taskKey);
		}
		if (!Strings.isNullOrEmpty(businessKey)) {
			taskQuery = taskQuery.processInstanceBusinessKey(taskKey);
		}
		if (!Strings.isNullOrEmpty(processInstanceId)) {
			taskQuery = taskQuery.processInstanceId(processInstanceId);
		}
		if (fromDate != null) {
			taskQuery.taskCreatedAfter(fromDate);
		}
		if (toDate != null) {
			taskQuery.taskCreatedBefore(toDate);
		}
		if (variables != null && variables.size() > 0) {
			for (Map.Entry<String, Object> entry : variables.entrySet()) {
				taskQuery = taskQuery.processVariableValueEquals(entry.getKey(), entry.getValue());
			}
		}
		return taskQuery;
	}
}
