package net.atos.bpm.web.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.atos.bpm.service.WorkflowService;

@RestController
@RequestMapping("/api")
public class WorkflowController {
	private static final Logger logger = LoggerFactory.getLogger(WorkflowController.class);

	@Autowired
	private WorkflowService workflowService;

	public WorkflowController(WorkflowService workflowService) {
		this.workflowService = workflowService;
	}

	// @GetMapping("/start-process")
	// public String startProcess() {
	// runtimeService.startProcessInstanceByKey("my-process");
	// return "Process started. Number of currently running process instances =
	// " + runtimeService.createProcessInstanceQuery()
	// .count();
	// }
	//
	// @GetMapping("/get-tasks/{processInstanceId}")
	// public List<TaskRepresentation> getTasks(@PathVariable String
	// processInstanceId) {
	// List<Task> usertasks = taskService.createTaskQuery()
	// .processInstanceId(processInstanceId)
	// .list();
	//
	// return usertasks.stream()
	// .map(task -> new TaskRepresentation(task.getId(), task.getName(),
	// task.getProcessInstanceId()))
	// .collect(Collectors.toList());
	// }
	//
	// @GetMapping("/complete-task-A/{processInstanceId}")
	// public void completeTaskA(@PathVariable String processInstanceId) {
	// Task task = taskService.createTaskQuery()
	// .processInstanceId(processInstanceId)
	// .singleResult();
	// taskService.complete(task.getId());
	// logger.info("Task completed");
	// }
}
