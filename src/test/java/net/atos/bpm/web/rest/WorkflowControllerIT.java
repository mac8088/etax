package net.atos.bpm.web.rest;

import static net.atos.etax.web.rest.TestUtil.createFormattingConversionService;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.atos.bpm.service.ToDoService;
import net.atos.bpm.service.WorkflowService;
import net.atos.bpm.web.rest.vm.TaskRepresentation;

import net.atos.etax.EtaxApp;

import net.atos.etax.web.rest.errors.ExceptionTranslator;

@SpringBootTest(classes = EtaxApp.class)
public class WorkflowControllerIT {
	private static final Logger logger = LoggerFactory.getLogger(WorkflowControllerIT.class);

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Autowired
	private Validator validator;

	private MockMvc mockMvc;

	@Autowired
	private WorkflowService workflowService;
	
	@Autowired
	private ToDoService toDoService;

	@Before
	public void setUp() {
		WorkflowController workflowResource = new WorkflowController(workflowService, toDoService);

		this.mockMvc = MockMvcBuilders.standaloneSetup(workflowResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
				.setConversionService(createFormattingConversionService()).setMessageConverters(jacksonMessageConverter)
				.setValidator(validator).build();

		RuntimeService runtimeService = workflowService.getRuntimeService();
		for (ProcessInstance instance : runtimeService.createProcessInstanceQuery().list()) {
			runtimeService.deleteProcessInstance(instance.getId(), "Reset Processes");
		}
	}

	@Ignore
	public void givenProcess_whenStartProcess_thenIncreaseInProcessInstanceCount() throws Exception {

		String responseBody = this.mockMvc.perform(MockMvcRequestBuilders.get("/start-process")).andReturn()
				.getResponse().getContentAsString();
		assertEquals("Process started. Number of currently running process instances = 1", responseBody);

		responseBody = this.mockMvc.perform(MockMvcRequestBuilders.get("/start-process")).andReturn().getResponse()
				.getContentAsString();
		assertEquals("Process started. Number of currently running process instances = 2", responseBody);

		responseBody = this.mockMvc.perform(MockMvcRequestBuilders.get("/start-process")).andReturn().getResponse()
				.getContentAsString();
		assertEquals("Process started. Number of currently running process instances = 3", responseBody);
	}

	@Ignore
	public void givenProcess_whenProcessInstance_thenReceivedRunningTask() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/start-process")).andReturn().getResponse();
		RuntimeService runtimeService = workflowService.getRuntimeService();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().orderByProcessInstanceId().desc().list()
				.get(0);

		logger.info("process instance = " + pi.getId());
		String responseBody = this.mockMvc.perform(MockMvcRequestBuilders.get("/get-tasks/" + pi.getId())).andReturn()
				.getResponse().getContentAsString();

		ObjectMapper mapper = new ObjectMapper();
		List<TaskRepresentation> tasks = Arrays.asList(mapper.readValue(responseBody, TaskRepresentation[].class));
		assertEquals(1, tasks.size());
		assertEquals("A", tasks.get(0).getName());

	}

	@Ignore
	public void givenProcess_whenCompleteTaskA_thenReceivedNextTask() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/start-process")).andReturn().getResponse();
		RuntimeService runtimeService = workflowService.getRuntimeService();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().orderByProcessInstanceId().desc().list()
				.get(0);

		logger.info("process instance = " + pi.getId());
		this.mockMvc.perform(MockMvcRequestBuilders.get("/complete-task-A/" + pi.getId())).andReturn().getResponse()
				.getContentAsString();

		List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
		assertEquals(0, list.size());
	}
}
