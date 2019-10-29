package net.atos.etax.action;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.atos.bpm.util.SpringContextUtil;
import net.atos.etax.domain.User;
import net.atos.etax.domain.UserInfo;
import net.atos.etax.service.UserService;
import net.atos.etax.service.dto.UserDTO;
import net.atos.etax.service.dto.UserInfoDTO;
import net.atos.etax.service.impl.UserInfoServiceImpl;

public class SaveUserDelegate implements JavaDelegate {
    
    @Override
	public void execute(DelegateExecution execution) {
        final UserService userService = SpringContextUtil.getBean(UserService.class);
        final UserInfoServiceImpl userInfoServiceImpl = SpringContextUtil.getBean(UserInfoServiceImpl.class);
        final Logger log = LoggerFactory.getLogger(SaveUserDelegate.class);
        
    	log.debug("delegateExecution to save userDTO: {}", execution.getVariable("userDTO"));
    	log.debug("delegateExecution to save userInfoDTO: {}", execution.getVariable("userInfoDTO"));
    	UserDTO userDTO = (UserDTO) execution.getVariable("userDTO");
    	UserInfoDTO userInfoDTO = (UserInfoDTO) execution.getVariable("userInfoDTO");
    	// create 
        if (userDTO.getId()!=null) {
		} else {
			 User newUser = userService.createUser(userDTO);
			 userInfoDTO.setUser(newUser);
			 UserInfo userInfo = userInfoServiceImpl.save(userInfoDTO);
			 execution.setVariable("user", newUser);
			 execution.setVariable("userInfo", userInfo);
			 userService.clearUserCaches(newUser);
		} 
        
	}

}
