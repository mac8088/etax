package net.atos.bpm.listener;

import java.util.Set;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

public class CommonUserStartListener implements TaskListener {

	private static final long serialVersionUID = 1L;

	private final static Logger log = LoggerFactory.getLogger(CommonUserStartListener.class);

	@Override
	public void notify(DelegateTask delegateTask) {
		log.warn("进入通用用户任务启动监听器");

		// 查询信息
		log.info("任务执行人：" + delegateTask.getAssignee());
		log.info("任务配置ID: " + delegateTask.getTaskDefinitionKey());
		log.info("任务ID: " + delegateTask.getId());
		
		// 查询变量
		Set<String> setNames = delegateTask.getVariableNames();
		if (!CollectionUtils.isEmpty(setNames)) {
			log.info("任务变量:" + setNames.toString());
		}

		for (String varName : setNames) {
			Object varValue = delegateTask.getVariable(varName);
			log.info("变量名:" + varName + " 变量值:" + JSON.toJSONString(varValue));
		}

		// 修改变量
		delegateTask.setVariable("Test_Var", "测试变量");

		log.warn("退出通用用户任务启动监听器");
	}

}
