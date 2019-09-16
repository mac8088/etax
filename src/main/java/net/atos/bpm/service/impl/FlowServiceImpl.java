package net.atos.bpm.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
//import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import net.atos.bpm.service.FlowServiceIF;

//@Primary
@Service
public class FlowServiceImpl implements FlowServiceIF {

	private static final Logger log = LoggerFactory.getLogger(FlowServiceImpl.class);

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private TaskService taskService;

	@Autowired
	ProcessEngine processEngine;

	@Override
	public Map<String, Object> createFlow(String filePath) {
		Map<String, Object> res = new HashMap<>();
		// 解析BPMN模型看是否成功
		XMLStreamReader reader = null;
		try {

			Resource cpr = new ClassPathResource(filePath);

			// 读入流程文件
			XMLInputFactory factory = XMLInputFactory.newInstance();
			reader = factory.createXMLStreamReader(cpr.getInputStream());

			// /把文件转换为BPMNModel来校验
			BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
			BpmnModel model = bpmnXMLConverter.convertToBpmnModel(reader);
			List<Process> processes = model.getProcesses();
			Process curProcess = null;
			if (CollectionUtils.isEmpty(processes)) {
				log.error("BPMN模型没有配置流程");
				return null;
			}

			res.put("processes", processes);
			curProcess = processes.get(0);
	

			// 通过创建DeploymentBuilder来完成部署
			DeploymentBuilder deploymentBuilder = null;
			deploymentBuilder = repositoryService.createDeployment().name("TEST_FLOW").addInputStream(filePath, cpr.getInputStream());

			Deployment deployment = deploymentBuilder.deploy();
			res.put("deployment", deployment);

			log.warn("部署流程 file: " + filePath);
			log.warn("部署流程 name: " + curProcess.getName() + " / " + curProcess.getId() + " --> " + deployment);

			return res;
		} catch (Exception e) {
			log.error("BPMN模型创建流程异常", e);
			return null;
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				log.error("关闭异常", e);
			}
		}
	}

	@Override
	public ProcessInstance strartFlow(String processKey, Map<String, Object> paras) {
		if (StringUtils.isEmpty(processKey)) {
			return null;
		}

		if (null == paras) {
			paras = new HashMap<>();
		}

		Deployment deployment = repositoryService.createDeploymentQuery().processDefinitionKey(processKey)
				.singleResult();

		// 为了防止没有部署就去启动，加入了流程是否存在的检查
		if (deployment == null) {
			log.error("没有该流程");
			return null;
		}

		// 运行时服务调用startProcessInstanceByKey启动一个流程
		return runtimeService.startProcessInstanceByKey(processKey, paras);
	}

	private boolean isFinished(String processInstanceId) {
		return historyService.createHistoricProcessInstanceQuery().finished().processInstanceId(processInstanceId)
				.count() > 0;

	}

	@Override
	/**
	 * 完成任务
	 */
	public boolean completeTask(String taskId, Map<String, Object> flowParas) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			return false;
		}

		if (null == flowParas) {
			taskService.complete(taskId);
		} else {
			taskService.complete(taskId, flowParas);
		}

		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) {
		/**
		 * 获得当前活动的节点
		 */
		String processDefinitionId = "";
		if (this.isFinished(processId)) {// 如果流程已经结束，则得到结束节点
			HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(processId).singleResult();
			processDefinitionId = pi.getProcessDefinitionId();
		} else {// 如果流程没有结束，则取当前活动节点
				// 根据流程实例ID获得当前处于活动状态的ActivityId合集
			ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId)
					.singleResult();
			processDefinitionId = pi.getProcessDefinitionId();
		}

		/**
		 * 获得活动的节点
		 */
		List<String> highLightedActivitis = new ArrayList<String>();
		List<HistoricActivityInstance> highLightedActivitList = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(processId).orderByHistoricActivityInstanceStartTime().asc().list();
		for (HistoricActivityInstance tempActivity : highLightedActivitList) {
			String activityId = tempActivity.getActivityId();
			highLightedActivitis.add(activityId);
		}

		// 获取流程图
		List<String> flows = new ArrayList<>();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);

		ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();

		ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
		InputStream in = diagramGenerator.generateDiagram(bpmnModel, "bmp", highLightedActivitis, flows,
				engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(),
				engconf.getClassLoader(), 1.0, true);

		OutputStream out = null;
		byte[] buf = new byte[1024];
		int legth = 0;
		try {
			out = httpServletResponse.getOutputStream();
			while ((legth = in.read(buf)) != -1) {
				out.write(buf, 0, legth);
			}
		} catch (IOException e) {
			log.error("操作异常", e);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
		}
	}

}
