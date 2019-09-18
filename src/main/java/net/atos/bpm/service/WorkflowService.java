package net.atos.bpm.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.app.engine.impl.ServiceImpl;
import org.flowable.common.engine.api.delegate.event.FlowableEntityEvent;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.ProcessEngineImpl;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.task.api.Task;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.atos.bpm.domain.Deputy;
import net.atos.bpm.repository.DeputyRepository;
import net.atos.bpm.repository.DeputyToDoRepository;
import net.atos.etax.domain.User;

@Service
public class WorkflowService extends ServiceImpl {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private IdentityService identityService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ProcessEngineImpl processEngine;

	@Autowired
	private TaskHandler TaskHandler;

	@Autowired
	private ProcessHandler processHandler;

	@Autowired
	private DeputyRepository deputyRepository;

	@Autowired
	private DeputyToDoRepository deputyToDoRepository;

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
	 * @return the identityService
	 */
	public IdentityService getIdentityService() {
		return identityService;
	}

	/**
	 * @return the taskService
	 */
	public TaskService getTaskService() {
		return taskService;
	}

	public ProcessHandler getProcessHandler() {
		return processHandler;
	}

	public TaskHandler getTaskHandler() {
		return TaskHandler;
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
		HistoricProcessInstance instance = processHandler.getProcessById(task.getProcessInstanceId());
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

	/**
	 * 回滚操作
	 * 
	 * @param processInstanceId
	 * @param activityId
	 * @param variables
	 */
	public void rollBackTask(String processInstanceId, String activityId, Map<String, Object> variables) {
		if (commandExecutor == null) {
			ProcessEngineConfigurationImpl pecImpl = processEngine.getProcessEngineConfiguration();
			// pecImpl.initService(this);
			// TODO
			pecImpl.init();
		}
		commandExecutor.execute(new TaskCommitCmd(processInstanceId, activityId, variables));
	}

	private void createDeputyRecord(TaskEntity taskEntity, User owner, User deputyUser) {
		
	}

	private List<User> getCandidateUsers(TaskEntity taskEntity) {
		return null;
	}

	public void setDeputyForTask(FlowableEntityEvent event) {
//		// Try to add delegation Candidates
//		TaskEntity taskEntity = (TaskEntity) event.getEntity();
//		List<User> users = this.getCandidateUsers(taskEntity);
//		if (users != null && users.size() > 0) {
//			List<Long> ids = new ArrayList<Long>();
//			for (User u : users) {
//				ids.add(u.getId());
//			}
//			
//			ZonedDateTime zdt = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
//			List<Deputy> deputies = this.deputyRepository.findByOwnerInAndPeriodFromLessThanEqualAndPeriodToGreaterThan(ids, zdt, zdt);
//			for (Deputy deputy : deputies) {
//				
//			}
//		}
	}

}
