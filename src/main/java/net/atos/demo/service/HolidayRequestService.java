package net.atos.demo.service;

import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.atos.bpm.service.impl.ProcessServiceImpl;
import net.atos.demo.model.HolidayRequestProcess;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for managing HolidayRequest.
 */
@Service
public class HolidayRequestService extends ProcessServiceImpl {

	private final static Logger logger = LoggerFactory.getLogger(HolidayRequestService.class);

	private final static String REQUESTOR = "requestor";
	private final static String REQUEST_DAY = "requestDay";
	private final static String REASON = "reason";

	// 类似于从上下文中提取DF
	private HolidayRequestProcess convertProcessInstance(ProcessInstance processInstance) {
		HolidayRequestProcess holidayRequestProcess = new HolidayRequestProcess();

		convertBasicProcessInstance(processInstance, holidayRequestProcess);

		Map<String, Object> variables = runtimeService.getVariables(processInstance.getId());
		holidayRequestProcess.setEmployName(variables.get(REQUESTOR).toString());
		holidayRequestProcess.setRequestDays(Integer.valueOf(variables.get(REQUEST_DAY).toString()));
		holidayRequestProcess.setReason(variables.get(REASON).toString());

		return holidayRequestProcess;
	}

	// 从历史实例中提取DF
	private HolidayRequestProcess convertHistoricProcessInstance(HistoricProcessInstance historicProcessInstance) {
		HolidayRequestProcess holidayRequestProcess = new HolidayRequestProcess();

		convertBasicHistoricProcessInstance(historicProcessInstance, holidayRequestProcess);

		Map<String, Object> variables = historyService.createHistoricVariableInstanceQuery()
				.processInstanceId(historicProcessInstance.getId()).list().stream()
				.collect(Collectors.toMap(h -> h.getVariableName(), h -> h.getValue()));

		logger.warn("the variables: " + variables);

		holidayRequestProcess.setEmployName(variables.get(REQUESTOR).toString());
		holidayRequestProcess.setRequestDays(Integer.valueOf(variables.get(REQUEST_DAY).toString()));
		holidayRequestProcess.setReason(variables.get(REASON).toString());

		return holidayRequestProcess;
	}

	// 类似于往上下文中设置DF
	public HolidayRequestProcess createHolidayRequestProcess(HolidayRequestProcess process) {
		logger.debug("createHolidayRequestProcess:" + process.toString());

		ProcessInstance processInstance = createProcess(process).variable(REQUESTOR, process.getEmployName())
				.variable(REQUEST_DAY, process.getRequestDays()).variable(REASON, process.getReason()).start();

		return convertProcessInstance(processInstance);
	}

	private List<HolidayRequestProcess> getHolidayRequestProcess(List<ProcessInstance> processInstanceList) {
		return processInstanceList.stream().map(p -> convertProcessInstance(p)).collect(Collectors.toList());
	}

	public List<HolidayRequestProcess> getProcessesByProcessDefinitionKey(String processDefinitionKey) {
		return getHolidayRequestProcess(queryProcessesByProcessDefinitionKey(processDefinitionKey));
	}

	public List<HolidayRequestProcess> getProcessesByProcessInstanceId(String processInstanceId) {
		return getHolidayRequestProcess(queryProcessesByProcessInstanceId(processInstanceId));
	}

	public List<HolidayRequestProcess> getProcessesByStarter(String starter) {
		return getHolidayRequestProcess(queryProcessesByStarter(starter));
	}

	private List<HolidayRequestProcess> getHolidayRequestProcessFromHistoric(
			List<HistoricProcessInstance> historicProcessInstanceList) {
		return historicProcessInstanceList.stream().map(p -> convertHistoricProcessInstance(p))
				.collect(Collectors.toList());
	}

	public List<HolidayRequestProcess> getRunningProcesses() {
		return getHolidayRequestProcess(queryRunningProcesses());
	}

	public List<HolidayRequestProcess> getFinishedProcesses() {
		return getHolidayRequestProcessFromHistoric(queryFinishedProcesses());
	}

	public List<HolidayRequestProcess> getAllProcesses() {
		List<HolidayRequestProcess> holidayRequestProcessList = getRunningProcesses();
		holidayRequestProcessList.addAll(getFinishedProcesses());
		return holidayRequestProcessList;
	}

}
