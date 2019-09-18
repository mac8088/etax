package net.atos.bpm.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.atos.bpm.web.rest.vm.StartProcessRepresentation;
import net.atos.bpm.web.rest.vm.TaskRepresentation;
import net.atos.etax.service.PersonService;

@RestController
@RequestMapping("/api")
public class PersonController {

	@Autowired
	private PersonService personService;

	@RequestMapping(value = "/personPocess", method = RequestMethod.POST)
	public void startProcessInstance(@RequestBody StartProcessRepresentation startProcessRepresentation) {
		personService.startProcess(startProcessRepresentation.getAssignee());
	}

	@RequestMapping(value = "/personTasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
		List<Task> tasks = personService.getTasks(assignee);
		List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
		for (Task task : tasks) {
			dtos.add(new TaskRepresentation(task.getId(), task.getName(), null));
		}
		return dtos;
	}
}