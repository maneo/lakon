package org.grejpfrut.evaluation.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.grejpfrut.evaluation.dao.EvaluationsManager;
import org.grejpfrut.evaluation.domain.Evaluation;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * 
 * @author Adam Dudczak
 */
public class EvaluationMailSenderTask extends TimerTask {

	/**
	 * Template variable which holds {@link Evaluation}s list.
	 */
	private static final String T_EVALS = "evals";

	private static final String MESSAGE_ENCODING = "UTF-8";

	private static final String TEMPLATE_NAME = "evals-export.vm";

	private VelocityEngine velocityEngine;

	private EvaluationsManager evaluationsManager;
	
	private JavaMailSender mailSender;

	private String mailAccount;

	@Override
	public void run() {
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						MESSAGE_ENCODING);
				message.setTo(mailAccount);
				message.setFrom(mailAccount);
				message.setSubject("Evaluations "+new Date().toString());
				Map model = new HashMap();
				model.put(T_EVALS, evaluationsManager.getEvaluations());
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, TEMPLATE_NAME, model);
				message.setText(text, true);
			}
		};
		this.mailSender.send(preparator);
		
	}

	public EvaluationsManager getEvaluationsManager() {
		return evaluationsManager;
	}

	public void setEvaluationsManager(EvaluationsManager evaluationsManager) {
		this.evaluationsManager = evaluationsManager;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public String getMailAccount() {
		return mailAccount;
	}

	public void setMailAccount(String mailAccount) {
		this.mailAccount = mailAccount;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

}
