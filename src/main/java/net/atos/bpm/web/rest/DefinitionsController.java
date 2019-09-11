package net.atos.bpm.web.rest;

//import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DefinitionsController {

    protected final RepositoryService repositoryService;

    public DefinitionsController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

//	@GetMapping("/hello")
//	public String hello(Principal principal) {
//		return "Hello " + principal.getName() + ", welcome FlowFest 2018";
//	}
    
    @GetMapping("/flow/latest-definitions")
    public List<?> latestDefinitions() {
        return repositoryService.createProcessDefinitionQuery()
            .latestVersion()
            .list()
            .stream()
            .map(ProcessDefinition::getKey)
            .collect(Collectors.toList());
    }

}