package net.atos.bpm.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.flowable.engine.runtime.ProcessInstance;

public interface FlowServiceIF {

	/**
	 * 部署工作流
	 */

	Map<String, Object> createFlow(String filePath);

	/**
	 * 启动工作流
	 */
	ProcessInstance strartFlow(String processKey, Map<String, Object> paras);

	/**
	 * 完成任务
	 */

	boolean completeTask(String taskId, Map<String, Object> flowParas);

	/**
	 * 进程的流程生成
	 */
	public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId);

}
