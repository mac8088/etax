package net.atos.bpm.service;

import java.io.Serializable;
import java.util.Map;

import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;

public class TaskCommitCmd implements Command<Void>, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 流程实例ID
	 */
	private String processInstanceId;

	/**
	 * 目标节点Id
	 */
	private String activityId;
	
	/**
	 * 参数
	 */
	protected Map variables;

	public TaskCommitCmd(String _processInstanceId, String _activityId, Map _variables) {
		this.processInstanceId = _processInstanceId;
		this.activityId = _activityId;
		this.variables = _variables;
	}

	@Override
	public Void execute(CommandContext commandContext) {
		// TODO Auto-generated method stub
		return null;
	}

}
