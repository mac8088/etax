package net.atos.bpm.delegate;

import com.alibaba.fastjson.JSONArray;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Component("JSON")
public class JsonDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
	}

	public JSONObject from(String text) {
		return JSON.parseObject(text);
	}

	public JSONArray parse(String text) {
		return JSON.parseArray(text);
	}

	public String to(Object o) {
		return JSON.toJSONString(o);
	}

}