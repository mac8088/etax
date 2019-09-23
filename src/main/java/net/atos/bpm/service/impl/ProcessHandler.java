package net.atos.bpm.service.impl;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessHandler {

	@Autowired
	private HistoryService historyService;
	
	public HistoricProcessInstance getProcessById(String processInstanceId) {
		return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	}

	/**
	 * @param status
	 *            可选 finished，deleted,unfinished,notDeleted
	 * @param username
	 * @param startUsername
	 * @return
	 */
	private HistoricProcessInstanceQuery buildInvolvedHistoricProcessInstancesQuery(String status, String processKey,
			String businessKey, String username, String startUsername, Date fromDate, Date toDate,
			Map<String, Object> variables) {
		HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
		if (status == null) {
			status = "";
		}

		switch (status) {
		case "finished":
			query = query.finished();
			break;
		case "deleted":
			query = query.deleted();
			break;
		case "unfinished":
			query = query.unfinished();
			break;
		case "notDeleted":
			query = query.notDeleted();
			break;
		default:
			query = query.notDeleted();
		}

		if (!isBlank(processKey)) {
			query = query.processDefinitionKey(processKey);
		}

		if (!isBlank(username)) {
			query = query.involvedUser(username).variableValueNotEquals("initiator", username);
		}

		if (!isBlank(startUsername)) {
			query = query.startedBy(startUsername);
		}

		if (!isBlank(businessKey)) {
			query = query.processInstanceBusinessKey(businessKey);
		}

		if (variables != null && variables.size() > 0) {
			for (Map.Entry<String, Object> entry : variables.entrySet()) {
				query = query.variableValueEquals(entry.getKey(), entry.getValue());
			}
		}

		if (toDate != null) {
			query.startedBefore(toDate);
		}

		if (fromDate != null) {
			query.startedAfter(fromDate);
		}

		query = query.orderByProcessInstanceStartTime().desc();
		return query;
	}

	/**
	 * @param status
	 *            可选 finished，deleted,unfinished,notDeleted
	 * @param username
	 * @param startUsername
	 * @return
	 */
	public long countInvolvedHistoricProcessInstances(String status, String processKey, String businessKey,
			String username, String startUsername, Date fromDate, Date toDate, Map<String, Object> variables) {
		HistoricProcessInstanceQuery query = buildInvolvedHistoricProcessInstancesQuery(status, processKey, businessKey,
				username, startUsername, fromDate, toDate, variables);
		return query.count();
	}

	/**
	 * @param status
	 *            可选 finished，deleted,unfinished,notDeleted
	 * @param username
	 * @param startUsername
	 * @param start
	 * @param max
	 * @return
	 */
	public List<HistoricProcessInstance> findInvolvedHistoricProcessInstances(String status, String processKey,
			String businessKey, String username, String startUsername, Date fromDate, Date toDate,
			Map<String, Object> variables, int start, int max) {
		HistoricProcessInstanceQuery query = buildInvolvedHistoricProcessInstancesQuery(status, processKey, businessKey,
				username, startUsername, fromDate, toDate, variables);
		if (max > 0) {
			return query.listPage(start, max);
		} else {
			return query.list();
		}
	}
}
