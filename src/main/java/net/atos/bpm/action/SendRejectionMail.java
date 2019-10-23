package net.atos.bpm.action;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class SendRejectionMail implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) {
		// System.out.println("Sending the reject mail to employee " + execution.getVariable("employee"));
		System.out.println("Sending the mail ");
	}

}
