package net.atos.demo.model;

import net.atos.bpm.model.ProcessInfo;

public class HolidayRequestProcess extends ProcessInfo {

	String employName;
	Integer requestDays;
	String reason;

	public String getEmployName() {
		return employName;
	}

	public void setEmployName(String employName) {
		this.employName = employName;
	}

	public Integer getRequestDays() {
		return requestDays;
	}

	public void setRequestDays(Integer requestDays) {
		this.requestDays = requestDays;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
        return "HolidayRequestProcess{" +
                "process='" + super.toString() + '\'' +
                "employName='" + employName + '\'' +
                ", requestDays=" + requestDays +
                ", reason='" + reason + '\'' +
                '}';
	}
}
