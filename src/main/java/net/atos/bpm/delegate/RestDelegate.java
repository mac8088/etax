package net.atos.bpm.delegate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import net.atos.bpm.util.RestUtil;

@Component("REST")
public class RestDelegate implements JavaDelegate {

	private static final Logger log = LoggerFactory.getLogger(RestDelegate.class);

	private Expression name;
	private Expression url;
	private Expression method;
	private Expression body;
	private Expression result;

	/**
	 * Send Rest Request,put response text to variable 'restResult',and can
	 * override the variable name with the Expression result
	 */
	@Override
	public void execute(DelegateExecution execution) {
		String nameStr = (String) name.getValue(execution);
		String urlStr = (String) url.getValue(execution);
		String methodStr = (String) method.getValue(execution);
		String bodyStr = (String) body.getValue(execution);
		String var = "restResult";
		if (result != null) {
			var = (String) result.getValue(execution);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", urlStr);
		map.put("body", bodyStr);
		map.put("method", methodStr);
		map.put("result", var);

		if (log.isDebugEnabled()) {
			log.debug("REST Request:");
			log.debug(JSON.toJSONString(map, true));
		}

		String text = "";
		try {
			text = RestUtil.rest(nameStr + urlStr, bodyStr, methodStr);
			if (log.isDebugEnabled()) {
				log.debug("REST Response:" + text);
			}

			execution.setVariable(var, text);
		} catch (IOException e) {
			log.error("Rest Call failed:", e);
			map.put("msg", e.getMessage());
			map.put("success", false);
			execution.setVariable(var, JSON.toJSONString(map));
		}
	}
}
