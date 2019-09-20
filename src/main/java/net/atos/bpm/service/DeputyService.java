package net.atos.bpm.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.delegate.event.FlowableEntityEvent;
import org.flowable.engine.TaskService;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.atos.bpm.domain.Deputy;
import net.atos.bpm.repository.DeputyRepository;
import net.atos.etax.domain.User;

/**
 * Service Implementation for managing {@link Deputy}.
 */
@Service
@Transactional
public class DeputyService {

    private final Logger log = LoggerFactory.getLogger(DeputyService.class);

    private final DeputyRepository deputyRepository;
    
	@Autowired
	private OrganizationService orgService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private DeputyToDoService deputyToDoService;

    public DeputyService(DeputyRepository deputyRepository) {
        this.deputyRepository = deputyRepository;
    }

    /**
     * Save a deputy.
     *
     * @param deputy the entity to save.
     * @return the persisted entity.
     */
    public Deputy save(Deputy deputy) {
        log.debug("Request to save Deputy : {}", deputy);
        return deputyRepository.save(deputy);
    }

    /**
     * Get all the deputies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Deputy> findAll(Pageable pageable) {
        log.debug("Request to get all Deputies");
        return deputyRepository.findAll(pageable);
    }


    /**
     * Get one deputy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Deputy> findOne(Long id) {
        log.debug("Request to get Deputy : {}", id);
        return deputyRepository.findById(id);
    }

    /**
     * Delete the deputy by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Deputy : {}", id);
        deputyRepository.deleteById(id);
    }
    
	/**
	 * Check assignee, and update deputyToDo status
	 * 
	 * @param event
	 */
	public void updateDeputyStatus(FlowableEntityEvent event) {
		TaskEntity taskEntity = (TaskEntity) event.getEntity();
		String assignee = taskEntity.getAssignee();
		if (StringUtils.isNotEmpty(assignee)) {
			this.deputyRepository.clearStatus(taskEntity.getId());
		} else {
			this.deputyRepository.clearStatus(taskEntity.getId());
			this.deputyRepository.markStatus(taskEntity.getId(), assignee);
		}
	}

	public void setDeputyForTask(FlowableEntityEvent event) {
		TaskEntity taskEntity = (TaskEntity) event.getEntity();
		// add delegation Candidates
		List<User> users = orgService.getCandidateUsers(taskEntity);
		if (users != null && users.size() > 0) {
			List<Long> ids = new ArrayList<Long>();
			for (User u : users) {
				ids.add(u.getId());
			}

			ZonedDateTime zdt = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
			List<Deputy> deputies = this.deputyRepository.findByOwnerIdInAndPeriodFromLessThanEqualAndPeriodToGreaterThan(ids, zdt, zdt);
			for (Deputy deputy : deputies) {
				User deputyUser = this.orgService.findAssignorOrOwner(deputy.getAssignorId());
				User owner = this.orgService.findAssignorOrOwner(deputy.getOwnerId());
				if (!ids.contains(deputyUser.getId())) {
					taskService.addCandidateUser(taskEntity.getId(), deputyUser.getLogin());
					taskService.addComment(taskEntity.getId(), taskEntity.getProcessInstanceId(),  "delegation:" + deputyUser.getLogin() + ">" + owner.getLogin());
					// save a record
					deputyToDoService.createDeputyRecord(taskEntity, owner, deputyUser);
				} else {
					log.info("deputyUser [" + deputyUser.getLogin() + "] of owner[" + owner.getLogin() 
						+ "] already exist in task [" + taskEntity.getName() + ":" + taskEntity.getId() + "]");
				}
			}
		}
	}

}
