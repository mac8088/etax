package net.atos.bpm.service;

import java.util.List;

import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceBuilder;

import net.atos.bpm.model.TaskBean;
import net.atos.bpm.model.ProcessBean;

public interface ProcessServiceIF {

	String hello(String name);
	
	ProcessInstanceBuilder createProcess(ProcessBean process);

	void deleteProcessByProcessInstanceId(String processInstanceId);

	List<ProcessInstance> queryProcessesByProcessDefinitionKey(String processDefinitionKey);

	List<ProcessInstance> queryProcessesByProcessInstanceId(String processInstanceId);

	List<ProcessInstance> queryProcessesByStarter(String starter);

	List<ProcessInstance> queryRunningProcesses();

	List<HistoricProcessInstance> queryFinishedProcesses();

	List<TaskBean> queryTasksInProcess(String pid);

	List<TaskBean> queryTasksByAssignee(String assignee);

	List<TaskBean> queryTasksByCandidateGroup(String candidateGroup);

	List<TaskBean> queryTasksByCandidateGroups(List<String> candidateGroups);

	List<TaskBean> queryTasksByTaskID(String taskId);

	void handleTask(String taskId, Boolean approve);
	
}
