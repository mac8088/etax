package net.atos.bpm.web.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.atos.bpm.model.ValuePairBean;
import net.atos.bpm.service.ToDoService;
import net.atos.bpm.service.WorkflowService;
import net.atos.bpm.service.impl.ProcessHandler;
import net.atos.bpm.service.impl.TaskHandler;
import net.atos.bpm.web.rest.vm.TaskRepresentation;
import net.atos.etax.security.SecurityUtils;

@RestController
@RequestMapping("/api")
public class WorkflowController {

	private static final Logger logger = LoggerFactory.getLogger(WorkflowController.class);

	@Autowired
	private WorkflowService workflowService;
	
	@Autowired
	private ToDoService toDoService;

	public WorkflowController(WorkflowService workflowService, ToDoService toDoService) {
		this.workflowService = workflowService;
		this.toDoService = toDoService;
	}

	@ResponseBody
	@RequestMapping(value = "/flow/involvedProcess", method = RequestMethod.POST)
	public Map<String, Object> involvedProcess(String status, String processKey, String businessKey, String username,
			String startUsername, long fromDateTime, long toDateTime, @RequestBody Map<String, Object> variables,
			int start, int max) {
		Map<String, Object> result = new HashMap<>();
		Date fromDate = null;
		Date toDate = null;
		if (fromDateTime > 0l) {
			fromDate = new Date(fromDateTime);
		}
		if (toDateTime > 0l) {
			toDate = new Date(toDateTime);
		}

		ProcessHandler ph = workflowService.getProcessHandler();
		long count = ph.countInvolvedHistoricProcessInstances(status, processKey, businessKey, username, startUsername,
				fromDate, toDate, variables);
		List<HistoricProcessInstance> data = ph.findInvolvedHistoricProcessInstances(status, processKey, businessKey,
				username, startUsername, fromDate, toDate, variables, start, max);
		List<Map<String, Object>> maps = new ArrayList<>();
		for (HistoricProcessInstance instance : data) {
			maps.add(workflowService.processToMap(instance));
		}
		result.put("data", maps);
		result.put("total", count);
		result.put("start", start);
		result.put("max", max);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/flow/task", method = RequestMethod.POST)
	public Map<String, Object> task(String assignee, String candidateGroup, String candidateUser, String processKey,
			String taskKey, String processInstanceId, String businessKey, long fromDateTime, long toDateTime, int start,
			int max, @RequestBody Map<String, Object> variables) {
		Date fromDate = null;
		Date toDate = null;
		if (fromDateTime > 0l) {
			fromDate = new Date(fromDateTime);
		}
		if (toDateTime > 0l) {
			toDate = new Date(toDateTime);
		}
		Map<String, Object> result = new HashMap<>();
		TaskHandler th = workflowService.getTaskHandler();
		long count = th.countTask(assignee, candidateGroup, candidateUser, processKey, taskKey, processInstanceId,
				businessKey, fromDate, toDate, variables);
		List<Task> data = th.findTask(assignee, candidateGroup, candidateUser, processKey, taskKey, processInstanceId,
				businessKey, fromDate, toDate, variables, start, max);
		List<Map<String, Object>> maps = new ArrayList<>();
		for (Task task : data) {
			maps.add(workflowService.taskToMap(task));
		}
		result.put("data", maps);
		result.put("total", count);
		result.put("start", start);
		result.put("max", max);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/flow/task/rollback", method = RequestMethod.POST)
	public Map<String, Object> rollBackTask(String processInstanceId, String activityId,
			Map<String, Object> variables) {
		Map<String, Object> result = new HashMap<>();
		workflowService.rollBackTask(processInstanceId, activityId, variables);
		result.put("result", true);
		return result;
	}

	@GetMapping("/flow/start-process")
	public String startProcess() {
		workflowService.getRuntimeService().startProcessInstanceByKey("my-process");
		long count = workflowService.getRuntimeService().createProcessInstanceQuery().count();
		return "Process started. Number of currently running process instances = " + count;
	}

	@GetMapping("/flow/get-tasks/{processInstanceId}")
	public List<TaskRepresentation> getTasks(@PathVariable String processInstanceId) {
		List<Task> usertasks = workflowService.getTaskService().createTaskQuery().processInstanceId(processInstanceId).list();
		return usertasks.stream().map(task -> new TaskRepresentation(task.getId(), task.getName(), task.getProcessInstanceId())).collect(Collectors.toList());
	}

	@GetMapping("/flow/complete-task-A/{processInstanceId}")
	public void completeTaskA(@PathVariable String processInstanceId) {
		Task task = workflowService.getTaskService().createTaskQuery().processInstanceId(processInstanceId).singleResult();
		workflowService.getTaskService().complete(task.getId());
		logger.info("Task completed");
	}
	
	@GetMapping("/flow/todo/profiles")
	public List<ValuePairBean> getMyProfiles() {
		String who = SecurityUtils.getCurrentUserLogin().get();
		logger.info("get {} available profiles ... ", who);
		return toDoService.getAvailableProfiles(who);
	}
}
