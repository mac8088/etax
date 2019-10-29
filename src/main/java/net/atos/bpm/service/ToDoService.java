package net.atos.bpm.service;

import java.util.ArrayList;
import java.util.List;

import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.atos.bpm.model.TaskBean;
import net.atos.bpm.model.ValuePairBean;
import net.atos.etax.domain.Authority;

@Service
@Transactional
public class ToDoService {

	private static final Logger log = LoggerFactory.getLogger(ToDoService.class);

	@Autowired
	private OrganizationService orgService;

	@Autowired
	private ProcessServiceIF processService;
	
	@Autowired
	private TaskService taskService;

	public List<ValuePairBean> getAvailableProfiles(String who) {
		log.debug("Service get {} available profiles ", who);
		List<Authority> profileList = orgService.getCandidateProfiles(who);
		List<ValuePairBean> cnp = new ArrayList<>();
		for (Authority a : profileList) {
			cnp.add(new ValuePairBean(a.getName(), a.getDescription()));
		}
		log.debug("Service get {} available profiles with {}  ", who, cnp);
		return cnp;
	}

	public List<TaskBean> getMyTasks(String who) {
		log.debug("Service get {} mytasks ", who);
		List<TaskBean> taskList = processService.queryTasksByAssignee(who);
		log.debug("Service get {} mytasks with {}", who, taskList);
		return taskList;
	}

	public List<TaskBean> getProfileTasks(String who, String profile, boolean withReserved) {
		log.debug("Service get {} profile {} tasks ", who, profile);
		List<TaskBean> taskList = processService.queryTasksByCandidateGroup(profile);
		log.debug("Service get {} profile {} tasks with {}", who, profile, taskList);
		return taskList;
	}
	
	public TaskBean getTaskByBusinesskey(String who, String businesskey) {
		ProcessInstance processInstance = processService.queryProcessByBusinesskey(businesskey);
		if (processInstance !=null && processInstance.getId()!=null) {
			TaskBean taskBean = processService.queryTaskByProcessInstance(processInstance.getId());
			log.debug("Service get task {} with assingee {}, businesskey {}", taskBean, who, businesskey);
			if (taskBean !=null && taskBean.getAssignee()!=null ) {
				if (taskBean.getAssignee().equals(who)) {
					return taskBean;
				}
			}
			return null;
		}
		return null;
	}
	
	public void claimTaskByTaskId(String who, String taskId) {
		taskService.claim(taskId,who); 
	}

}