package net.atos.bpm.listener;

import java.util.List;

import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEntityEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.identitylink.service.impl.persistence.entity.IdentityLinkEntity;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.atos.bpm.service.DeputyService;
import net.atos.bpm.util.SpringContextUtil;

public class CommonEventListener implements FlowableEventListener {

	private static final Logger log = LoggerFactory.getLogger(CommonEventListener.class);

	public void onEvent(FlowableEvent event) {
		if (event.getType().equals(FlowableEngineEventType.JOB_EXECUTION_SUCCESS)) {
			log.debug("A job well done!");
		} else if (event.getType().equals(FlowableEngineEventType.JOB_EXECUTION_FAILURE)) {
			log.debug("A job has failed...");
		} else if (event.getType().equals(FlowableEngineEventType.TASK_CREATED)) {
			log.debug("A task created!");
			this.onTaskCreated((FlowableEntityEvent) event);
		} else if (event.getType().equals(FlowableEngineEventType.TASK_ASSIGNED)) {
			log.debug("A task assigned!");
			this.onTaskAssigned((FlowableEntityEvent) event);
		} else if (event.getType().equals(FlowableEngineEventType.TASK_COMPLETED)) {
			log.debug("A task completed!");
			this.onTaskCompleted((FlowableEntityEvent) event);
		} else {
			log.debug("Event received: " + event.getType());
		}
	}

	private void onTaskCreated(FlowableEntityEvent event) {
		TaskEntity taskEntity = (TaskEntity) event.getEntity();
		String taskId = taskEntity.getId();
		String name = taskEntity.getName();
		String assignee = taskEntity.getAssignee();
		String owner = taskEntity.getOwner();
		if (log.isDebugEnabled()) {
			log.debug("event: {}", event);
			log.debug("task {} {} created by ownner {} and assignee {}", taskId, name, owner, assignee);
		}

		DeputyService ds = SpringContextUtil.getBean(DeputyService.class);
		ds.setDeputyForTask(event);
	}

	private void onTaskAssigned(FlowableEntityEvent event) {
		TaskEntity taskEntity = (TaskEntity) event.getEntity();
		log.info("task Assigned:" + taskEntity.getName() + "-" + taskEntity.getAssignee() + "-" + taskEntity.getId() + "-" + taskEntity.getOwner());
		log.info("task {} - {} Assigned with ");
		DeputyService ds = SpringContextUtil.getBean(DeputyService.class);
		ds.updateDeputyStatus(event);
	}

	private void onTaskCompleted(FlowableEntityEvent event) {
		TaskEntity taskEntity = (TaskEntity) event.getEntity();
		log.info("task Completed:" + taskEntity.getName() + "-" + taskEntity.getAssignee() + "-" + taskEntity.getId()
				+ "-" + taskEntity.getOwner());
		List<IdentityLinkEntity> links = taskEntity.getIdentityLinks();
		for (IdentityLinkEntity link : links) {
			log.info(link.getType() + "-" + link.getUserId() + "-" + link.getGroupId() + "-" + link.getTaskId());
		}
	}

	// 该isFailOnException()方法确定onEvent(..)方法在调度事件时抛出异常时的行为。
	public boolean isFailOnException() {
		// 当false返回，异常被忽略。当true返回，异常不会被忽略
		return false;
	}

	// 该isFireOnTransactionLifecycleEvent()方法确定此事件侦听器是在事件发生时立即触发还是由getOnTransaction()方法确定的事务生命周期事件触发。
	public boolean isFireOnTransactionLifecycleEvent() {
		// 支持的事务生命周期事件的值是：COMMITTED，ROLLED_BACK，COMMITTING，ROLLINGBACK。
		return false;
	}

	public String getOnTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

}
