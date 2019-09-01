package net.atos.etax.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.atos.etax.EtaxApp;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.*;
import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = EtaxApp.class)
public class WorkflowControllerIT {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowControllerIT.class);
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    RuntimeService runtimeService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
          .build();

        for (ProcessInstance instance : runtimeService.createProcessInstanceQuery()
          .list()) {
            runtimeService.deleteProcessInstance(instance.getId(), "Reset Processes");
        }
    }

    @Ignore
    public void givenProcess_whenStartProcess_thenIncreaseInProcessInstanceCount() throws Exception {

        String responseBody = this.mockMvc.perform(MockMvcRequestBuilders.get("/start-process"))
          .andReturn()
          .getResponse()
          .getContentAsString();
        assertEquals("Process started. Number of currently running process instances = 1", responseBody);

        responseBody = this.mockMvc.perform(MockMvcRequestBuilders.get("/start-process"))
          .andReturn()
          .getResponse()
          .getContentAsString();
        assertEquals("Process started. Number of currently running process instances = 2", responseBody);

        responseBody = this.mockMvc.perform(MockMvcRequestBuilders.get("/start-process"))
          .andReturn()
          .getResponse()
          .getContentAsString();
        assertEquals("Process started. Number of currently running process instances = 3", responseBody);
    }

    @Ignore
    public void givenProcess_whenProcessInstance_thenReceivedRunningTask() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/start-process"))
          .andReturn()
          .getResponse();
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
          .orderByProcessInstanceId()
          .desc()
          .list()
          .get(0);

        logger.info("process instance = " + pi.getId());
        String responseBody = this.mockMvc.perform(MockMvcRequestBuilders.get("/get-tasks/" + pi.getId()))
          .andReturn()
          .getResponse()
          .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        List<TaskRepresentation> tasks = Arrays.asList(mapper.readValue(responseBody, TaskRepresentation[].class));
        assertEquals(1, tasks.size());
        assertEquals("A", tasks.get(0).getName());

    }

    @Ignore
    public void givenProcess_whenCompleteTaskA_thenReceivedNextTask() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/start-process"))
          .andReturn()
          .getResponse();
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
          .orderByProcessInstanceId()
          .desc()
          .list()
          .get(0);

        logger.info("process instance = " + pi.getId());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/complete-task-A/" + pi.getId()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().list();
        assertEquals(0, list.size());
    }
}
