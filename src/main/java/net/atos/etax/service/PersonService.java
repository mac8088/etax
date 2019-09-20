package net.atos.etax.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.atos.etax.domain.Person;
import net.atos.etax.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private PersonRepository personRepository;

	/**
	 * @return the personRepository
	 */
	public PersonRepository getPersonRepository() {
		return personRepository;
	}

	public void startProcess(String assignee) {
		Person person = personRepository.findByUserName(assignee);

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("person", person);
		runtimeService.startProcessInstanceByKey("oneTaskProcess", variables);
	}

	public List<Task> getTasks(String assignee) {
		return taskService.createTaskQuery().taskAssignee(assignee).list();
	}

	@Transactional
	public void createDemoUsers() {
		if (personRepository.findAll().size() == 0) {
			personRepository.save(new Person("jbarrez", "Joram", "Barrez", Instant.now()));
			personRepository.save(new Person("trademakers", "Tijs", "Rademakers", Instant.now()));
		}
	}

}