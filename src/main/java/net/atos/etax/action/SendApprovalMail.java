package net.atos.etax.action;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.atos.bpm.util.SpringContextUtil;
import net.atos.etax.domain.User;
import net.atos.etax.service.UserService;

public class SendApprovalMail implements JavaDelegate {
		
	@Override
	public void execute(DelegateExecution execution) {
	    final UserService userService = SpringContextUtil.getBean(UserService.class);
	    final Logger log = LoggerFactory.getLogger(SendApprovalMail.class);
	    
    	log.debug("delegateExecution to show approval user: {}", execution.getVariable("user"));
    	User user = (User) execution.getVariable("user");
    	// update user active 
    	user.setActivated(true);
    	User updatedUser = userService.updateUser(user);
    	userService.clearUserCaches(updatedUser);
    	log.debug("updated user approval: {}", updatedUser);
	}

}
