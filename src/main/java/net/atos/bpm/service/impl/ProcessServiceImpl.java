package net.atos.bpm.service.impl;

import net.atos.bpm.service.ProcessServiceIF;

public class ProcessServiceImpl implements ProcessServiceIF {
	
	
	@Override
	public String hello(String name) {
		return "Hi, " + name;
	}
	
	
}
