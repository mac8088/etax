package net.atos.demo.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.atos.bpm.model.TaskInfo;
import net.atos.demo.model.HolidayRequestProcess;
import net.atos.demo.service.HolidayRequestService;


@RestController
@RequestMapping("/flow/holidayRequest")
public class HolidayRequestController {

    @Autowired
    private HolidayRequestService holidayRequestService;

    @GetMapping(value = {"/hello"})
    public String hello(@RequestParam(value = "name") String name){
        return holidayRequestService.hello(name);
    }

    @PostMapping(value = {"/processes"})
    public HolidayRequestProcess createProcess(@RequestBody HolidayRequestProcess process){
        HolidayRequestProcess holidayRequestProcess = holidayRequestService.createHolidayRequestProcess(process);
        return holidayRequestProcess;
    }

    @DeleteMapping(value = {"/processes/{pid}"})
    public void deleteProcess(@PathVariable String pid){
        holidayRequestService.deleteProcessByProcessInstanceId(pid);
    }

    @GetMapping(value = {"/processes/all"})
    public  List<HolidayRequestProcess> getAllProcesses(){
        List<HolidayRequestProcess> holidayRequestProcesses = holidayRequestService.getAllProcesses();

        return holidayRequestProcesses;
    }

    @GetMapping(value = {"/processes/running"})
    public List<HolidayRequestProcess> getRunningProcesses(){
        List<HolidayRequestProcess> holidayRequestProcesses = holidayRequestService.getRunningProcesses();

        return holidayRequestProcesses;
    }

    @GetMapping(value = {"/processes/finished"})
    public List<HolidayRequestProcess> getFinishedProcesses(){
        List<HolidayRequestProcess> holidayRequestProcesses = holidayRequestService.getFinishedProcesses();

        return holidayRequestProcesses;
    }

    @GetMapping(value = {"/processes/definitionKey"})
    public List<HolidayRequestProcess> getProcessesByProcessDefinitionKey(@RequestParam("processDefinitionKey") String processDefinitionKey){
        List<HolidayRequestProcess> holidayRequestProcesses = holidayRequestService.getProcessesByProcessDefinitionKey(processDefinitionKey);

        return holidayRequestProcesses;
    }

    @GetMapping(value = {"/processes/instanceId"})
    public List<HolidayRequestProcess> getProcessesByProcessInstanceId(@RequestParam("processInstanceId") String processInstanceId){
        List<HolidayRequestProcess> holidayRequestProcesses = holidayRequestService.getProcessesByProcessInstanceId(processInstanceId);

        return holidayRequestProcesses;
    }

    @GetMapping(value = {"/processes/starter"})
    public List<HolidayRequestProcess> getProcessesByStarter(@RequestParam("starter") String starter){
        List<HolidayRequestProcess> holidayRequestProcesses = holidayRequestService.getProcessesByStarter(starter);

        return holidayRequestProcesses;
    }

    @GetMapping(value = {"/{pid}/tasks"})
    public List<TaskInfo> getTasksInProcess(@PathVariable String pid){
        List<TaskInfo> tasks = holidayRequestService.queryTasksInProcess(pid);

        return tasks;
    }

    @GetMapping(value = {"/tasks/assignee"})
    public List<TaskInfo> getTasksByAssignee(@RequestParam("assignee") String assignee){
        List<TaskInfo> tasks = holidayRequestService.queryTasksByAssignee(assignee);

        return tasks;
    }

    @GetMapping(value = {"/tasks/group"})
    public List<TaskInfo> getTasksByCandidateGroup(@RequestParam("candidateGroup") String candidateGroup){
        List<TaskInfo> tasks = holidayRequestService.queryTasksByCandidateGroup(candidateGroup);

        return tasks;
    }

    @GetMapping(value = {"/tasks/groups"})
    public List<TaskInfo> getTasksByCandidateGroups(@RequestParam("candidateGroups") List<String> candidateGroups){
        List<TaskInfo> tasks = holidayRequestService.queryTasksByCandidateGroups(candidateGroups);

        return tasks;
    }

    @GetMapping(value = {"/tasks/taskId"})
    public List<TaskInfo> getTasksByTaskId(@RequestParam("taskId") String taskId){
        List<TaskInfo> tasks = holidayRequestService.queryTasksByTaskID(taskId);

        return tasks;
    }

    @PostMapping(value = {"/tasks/{taskId}"})
    public void handleTask(@PathVariable String taskId, @RequestParam Boolean approve){
        holidayRequestService.handleTask(taskId, approve);
    }

}
