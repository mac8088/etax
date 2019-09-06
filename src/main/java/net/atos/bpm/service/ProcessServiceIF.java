package net.atos.bpm.service;

import java.util.List;

import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceBuilder;

import net.atos.bpm.model.TaskInfo;
import net.atos.bpm.model.ProcessInfo;

public interface ProcessServiceIF {

	String hello(String name);
	
	ProcessInstanceBuilder createProcess(ProcessInfo process);

	void deleteProcessByProcessInstanceId(String processInstanceId);

	List<ProcessInstance> queryProcessesByProcessDefinitionKey(String processDefinitionKey);

	List<ProcessInstance> queryProcessesByProcessInstanceId(String processInstanceId);

	List<ProcessInstance> queryProcessesByStarter(String starter);

	List<ProcessInstance> queryRunningProcesses();

	List<HistoricProcessInstance> queryFinishedProcesses();

	List<TaskInfo> queryTasksInProcess(String pid);

	List<TaskInfo> queryTasksByAssignee(String assignee);

	List<TaskInfo> queryTasksByCandidateGroup(String candidateGroup);

	List<TaskInfo> queryTasksByCandidateGroups(List<String> candidateGroups);

	List<TaskInfo> queryTasksByTaskID(String taskId);

	void handleTask(String taskId, Boolean approve);
	
}
