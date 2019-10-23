package net.atos.bpm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.atos.bpm.model.ValuePairBean;
import net.atos.etax.domain.Authority;

@Service
@Transactional
public class ToDoService {

	@Autowired
	private OrganizationService orgService;

	public List<ValuePairBean> getAvailableProfiles(String login) {
		List<Authority> profileList = orgService.getCandidateProfiles(login);
		List<ValuePairBean> cnp = new ArrayList<>();
		for (Authority a : profileList) {
			cnp.add(new ValuePairBean(a.getName(), a.getDescription()));
		}
		return cnp;
	}

	public List<?> getMyTasks() {
		return null;
	}

	public List<?> getProfileTasks(String profile, boolean withReserved) {
		return null;
	}

}
