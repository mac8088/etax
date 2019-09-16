package net.atos.bpm.web.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.atos.bpm.service.FlowServiceIF;

/**
 * REST controller for 用来测试流程是否启动成功
 */
@Controller
@RequestMapping(value = "flow")
public class FlowController {

	private static final Logger log = LoggerFactory.getLogger(FlowController.class);

	@Autowired
	private FlowServiceIF flowService;

	@RequestMapping("/create")
	@ResponseBody
	public Map<String, Object> createFlow() throws Exception {
		Map<String, Object> res = new HashMap<>();
		Map<String, Object> data = new HashMap<>();

		// 从类路径中加载, 测试BPMN模型2.bpmn 使用了assignee
		Map<String, Object> createRes = flowService.createFlow("测试BPMN模型.bpmn20.xml");
		if (null == createRes) {
			res.put("res", "0");
			res.put("msg", "创建流程失败");
			res.put("data", data);
			return res;
		}

		@SuppressWarnings("unchecked")
		List<Process> processes = (List<Process>) createRes.get("processes");
		List<String> ids = new ArrayList<>();
		for (Process process : processes) {
			log.info("iterate process: " + process.getId() + " " + process.getName());
			ids.add(process.getId());
		}

		data.put("processKeys", ids);
		data.put("deployId", ((Deployment) createRes.get("deployment")).getId());

		res.put("res", "1");
		res.put("msg", "创建流程成功");
		res.put("data", data);

		return res;

	}

	@RequestMapping("/start")
	@ResponseBody
	public Map<String, Object> startFlow(@RequestBody @RequestParam(required = false) Map<String, String> paras) {
		Map<String, Object> res = new HashMap<>();
		Map<String, String> data = new HashMap<>();

		if (paras == null || paras.isEmpty()) {
			res.put("res", "0");
			res.put("msg", "启动流程失败");
			res.put("data", data);
			return res;
		}

		String processKey = paras.get("processKey");
		log.info("get processKey: " + processKey);
		if (StringUtils.isEmpty(processKey)) {
			res.put("res", "0");
			res.put("msg", "启动流程失败");
			res.put("data", data);
			return res;
		}

		Map<String, Object> flowParas = new HashMap<>();
		flowParas.putAll(paras);
		ProcessInstance processInstance = flowService.strartFlow(processKey, flowParas);
		log.info("get processInstance: " + processInstance);
		if (null == processInstance) {
			res.put("res", "0");
			res.put("msg", "启动流程失败");
			res.put("data", data);
			return res;
		}

		data.put("processId", processInstance.getId());
		res.put("res", "1");
		res.put("msg", "启动流程成功");
		res.put("data", data);
		return res;
	}

	@RequestMapping("/complete")
	@ResponseBody
	public Map<String, Object> completeTask(@RequestBody @RequestParam(required = false) Map<String, String> paras) {

		// Map嵌套 Map
		Map<String, Object> res = new HashMap<>();
		Map<String, String> data = new HashMap<>();

		if (paras == null || paras.isEmpty()) {
			res.put("msg", "请输入任务参数");
			res.put("res", "0");
			res.put("data", data);
			return res;
		}

		String taskId = paras.get("taskId");
		if (StringUtils.isEmpty(taskId)) {
			res.put("msg", "请输入任务ID");
			res.put("res", "0");
			res.put("data", data);
			return res;
		}

		// 准备flow的参数
		Map<String, Object> flowParas = new HashMap<>();
		flowParas.putAll(paras);
		flowParas.put("outcome", new Random().nextBoolean() ? "通过":"拒绝");
		boolean flag = flowService.completeTask(taskId, flowParas);

		if (flag) {
			data.put("taskId", taskId);
			res.put("msg", "启动流程成功");
			res.put("res", "1");
		} else {
			data.put("taskId", taskId);
			res.put("msg", "启动流程失败");
			res.put("res", "0");
		}

		res.put("data", data);
		return res;
	}

	@RequestMapping("/accept")
	@ResponseBody
	public Map<String, Object> acceptTask(@RequestBody @RequestParam(required = false) Map<String, String> paras) {
		Map<String, Object> res = new HashMap<>();
		Map<String, String> data = new HashMap<>();

		if (paras == null || paras.isEmpty()) {
			res.put("msg", "请输入任务参数");
			res.put("res", "0");
			res.put("data", data);
			return res;
		}

		String taskId = paras.get("taskId");
		if (StringUtils.isEmpty(taskId)) {
			res.put("msg", "请输入任务ID");
			res.put("res", "0");
			res.put("data", data);
			return res;
		}

		Map<String, Object> flowParas = new HashMap<>();
		flowParas.putAll(paras);
		flowParas.put("outcome", "通过");
		boolean bok = flowService.completeTask(taskId, flowParas);

		if (bok) {
			data.put("taskId", taskId);
			res.put("msg", "通过任务成功");
			res.put("res", "1");
		} else {
			data.put("taskId", taskId);
			res.put("msg", "通过任务失败");
			res.put("res", "0");
		}

		res.put("data", data);
		return res;
	}

	@RequestMapping("/reject")
	@ResponseBody
	public Map<String, Object> rejectTask(@RequestBody @RequestParam(required = false) Map<String, String> paras) {
		Map<String, Object> res = new HashMap<>();
		Map<String, String> data = new HashMap<>();

		if (paras == null || paras.isEmpty()) {
			res.put("msg", "请输入任务参数");
			res.put("res", "0");
			res.put("data", data);
			return res;
		}

		String taskId = paras.get("taskId");
		if (StringUtils.isEmpty(taskId)) {
			res.put("msg", "请输入任务ID");
			res.put("res", "0");
			res.put("data", data);
			return res;
		}

		Map<String, Object> flowParas = new HashMap<>();
		flowParas.putAll(paras);
		flowParas.put("outcome", "拒绝");
		boolean bok = flowService.completeTask(taskId, flowParas);

		if (bok) {
			data.put("taskId", taskId);
			res.put("msg", "拒绝任务成功");
			res.put("res", "1");
		} else {
			data.put("taskId", taskId);
			res.put("msg", "拒绝任务失败");
			res.put("res", "0");
		}

		res.put("data", data);
		return res;
	}

	@RequestMapping(value = "processDiagram")
	public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
		flowService.genProcessDiagram(httpServletResponse, processId);
	}
}
