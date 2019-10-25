package net.atos.bpm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceBuilder;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.atos.bpm.service.ProcessServiceIF;

import net.atos.bpm.model.TaskBean;
import net.atos.bpm.model.ProcessBean;

@Service
public class ProcessServiceImpl implements ProcessServiceIF {

	@Autowired
	protected RuntimeService runtimeService;

	@Autowired
	protected TaskService taskService;

	@Autowired
	protected HistoryService historyService;

	@Override
	public String hello(String name) {
		return "Hi, " + name;
	}

	@Override
	public ProcessInstanceBuilder createProcess(ProcessBean process) {
		ProcessInstanceBuilder processInstanceBuilder = runtimeService.createProcessInstanceBuilder()
				.processDefinitionKey(process.getProcessDefinitionKey());
		return processInstanceBuilder;
	}

	@Override
	public void deleteProcessByProcessInstanceId(String processInstanceId) {
		runtimeService.deleteProcessInstance(processInstanceId, "");
	}

	@Override
	public List<ProcessInstance> queryProcessesByProcessDefinitionKey(String processDefinitionKey) {
		return runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).list();
	}

	@Override
	public List<ProcessInstance> queryProcessesByProcessInstanceId(String processInstanceId) {
		return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).list();
	}
	
	private ProcessInstance queryProcessByProcessInstanceId(String processInstanceId) {
		return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	}

	@Override
	public List<ProcessInstance> queryProcessesByStarter(String starter) {
		return runtimeService.createProcessInstanceQuery().startedBy(starter).list();
	}

	@Override
	public List<ProcessInstance> queryRunningProcesses() {
		return runtimeService.createProcessInstanceQuery().list();
	}

	@Override
	public List<HistoricProcessInstance> queryFinishedProcesses() {
		return historyService.createHistoricProcessInstanceQuery().finished().list();
	}

	protected ProcessBean convertBasicProcessInstance(ProcessInstance processInstance, ProcessBean process) {
		process.setExecutionId(processInstance.getId());
		process.setProcessDefinitionId(processInstance.getProcessDefinitionId());
		process.setProcessDefinitionName(processInstance.getProcessDefinitionName());
		process.setName(processInstance.getName());
		process.setProcessInstanceId(processInstance.getProcessInstanceId());
		process.setDescription(processInstance.getDescription());
		process.setStartTime(processInstance.getStartTime());
		process.setStartUser(processInstance.getStartUserId());
		process.setProcessDefinitionKey(processInstance.getProcessDefinitionKey());
		return process;
	}

	protected ProcessBean convertBasicHistoricProcessInstance(HistoricProcessInstance historicProcessInstance,
			ProcessBean process) {
		process.setExecutionId(historicProcessInstance.getId());
		process.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());
		process.setProcessDefinitionName(historicProcessInstance.getProcessDefinitionName());
		process.setName(historicProcessInstance.getName());
		process.setProcessInstanceId(historicProcessInstance.getSuperProcessInstanceId());
		process.setDescription(historicProcessInstance.getDescription());
		process.setStartTime(historicProcessInstance.getStartTime());
		process.setStartUser(historicProcessInstance.getStartUserId());
		process.setProcessDefinitionKey(historicProcessInstance.getProcessDefinitionKey());
		return process;
	}

	private TaskBean convertTask(Task task) {
		TaskBean taskInfo = new TaskBean();
		taskInfo.setId(task.getId());
		taskInfo.setName(task.getName());
		taskInfo.setDescription(task.getDescription());
		taskInfo.setAssignee(task.getAssignee());
		taskInfo.setExecutionId(task.getExecutionId());
		taskInfo.setProcessInstanceId(task.getProcessInstanceId());
		taskInfo.setProcessDefinitionId(task.getProcessDefinitionId());
		taskInfo.setTaskDefinitionId(task.getTaskDefinitionId());
		taskInfo.setTaskDefinitionKey(task.getTaskDefinitionKey());
		taskInfo.setPriority(task.getPriority());
		taskInfo.setCreateTime(task.getCreateTime());
		
		//setup wfi.businessKey as entity identifier
		ProcessInstance pi = queryProcessByProcessInstanceId(task.getProcessInstanceId());
		if(pi != null) {
			taskInfo.setEntityIdentifier(pi.getBusinessKey());
		}
		
		return taskInfo;
	}

	@Override
	public List<TaskBean> queryTasksInProcess(String pid) {
		return taskService.createTaskQuery().processInstanceId(pid).orderByTaskCreateTime().desc().list().stream()
				.map(t -> convertTask(t)).collect(Collectors.toList());
	}

	public List<TaskBean> queryTasksByMyTasks(String user) {
		return taskService.createTaskQuery().or().taskAssignee(user).taskCandidateUser(user).endOr()
				.orderByTaskCreateTime().desc().list().stream().map(t -> convertTask(t)).collect(Collectors.toList());
	}

	protected List<TaskBean> queryTasksByUserAndGroup(String user, String group) {
//		taskService.createNativeTaskQuery().sql("selectClause").parameter("name", "value").list();
		return taskService.createTaskQuery().or().taskAssignee(user).taskCandidateUser(user).taskCandidateGroup(group)
				.endOr().orderByTaskCreateTime().desc().list().stream().map(t -> convertTask(t))
				.collect(Collectors.toList());
	}

	@Override
	public List<TaskBean> queryTasksByAssignee(String assignee) {
		return taskService.createTaskQuery().taskAssignee(assignee).orderByTaskCreateTime().desc().list().stream()
				.map(t -> convertTask(t)).collect(Collectors.toList());
	}

	// 根据用户查询任务
	public List<TaskBean> queryTasksByCandidateUser(String candidateUser) {
		return taskService.createTaskQuery().taskCandidateUser(candidateUser).orderByTaskCreateTime().desc().list()
				.stream().map(t -> convertTask(t)).collect(Collectors.toList());
	}

	@Override
	// 根据用户组查询任务
	public List<TaskBean> queryTasksByCandidateGroup(String candidateGroup) {
		return taskService.createTaskQuery().taskCandidateGroup(candidateGroup).orderByTaskCreateTime().desc().list()
				.stream().map(t -> convertTask(t)).collect(Collectors.toList());
	}

	@Override
	// 查询多个组队任务
	public List<TaskBean> queryTasksByCandidateGroups(List<String> candidateGroups) {
		return taskService.createTaskQuery().taskCandidateGroupIn(candidateGroups).orderByTaskCreateTime().desc().list()
				.stream().map(t -> convertTask(t)).collect(Collectors.toList());
	}

	@Override
	public List<TaskBean> queryTasksByTaskID(String taskId) {
		return taskService.createTaskQuery().taskId(taskId).orderByTaskCreateTime().desc().list().stream()
				.map(t -> convertTask(t)).collect(Collectors.toList());
	}

	@Override
	public void handleTask(String taskId, Boolean approve) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		if (task == null) {
			throw new RuntimeException("There is no task.");
		}

		HashMap<String, Object> map = new HashMap<>();
		map.put("approved", approve);
		taskService.complete(taskId, map);
	}

}
