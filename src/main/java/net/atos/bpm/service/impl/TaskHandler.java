package net.atos.bpm.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

@Service
public class TaskHandler {

	@Autowired
	private TaskService taskService;

	public long countTask(String assignee, String candidateGroup, String candidateUser, String processKey,
			String taskKey, String processInstanceId, String businessKey, Date fromDate, Date toDate,
			Map<String, Object> variables) {
		TaskQuery taskQuery = buildTaskQuery(assignee, candidateGroup, candidateUser, processKey, taskKey,
				processInstanceId, businessKey, fromDate, toDate, variables);
		return taskQuery.count();
	}

	public List<Task> findTask(String assignee, String candidateGroup, String candidateUser, String processKey,
			String taskKey, String processInstanceId, String businessKey, Date fromDate, Date toDate,
			Map<String, Object> variables, int start, int max) {
		TaskQuery taskQuery = buildTaskQuery(assignee, candidateGroup, candidateUser, processKey, taskKey,
				processInstanceId, businessKey, fromDate, toDate, variables);
		if (max > 0) {
			return taskQuery.listPage(start, max);
		} else {
			return taskQuery.list();
		}
	}

	private TaskQuery buildTaskQuery(String assignee, String candidateGroup, String candidateUser, String processKey,
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
