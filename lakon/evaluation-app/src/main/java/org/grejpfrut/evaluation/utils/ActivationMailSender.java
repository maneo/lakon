package org.grejpfrut.evaluation.utils;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.grejpfrut.evaluation.domain.User;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * This class send activational message to given user.
 * 
 * @author Adam Dudczak
 * 
 *
 */
public class ActivationMailSender {

	private static final String T_PASS = "pass";

	private static final String MESSAGE_ENCODING = "UTF-8";

	private static final String TEMPLATE_NAME = "email.vm";

	private static final String T_LINK = "link";

	private static final String T_ID = "id";

	private static final String T_USER = "user";

	private JavaMailSender mailSender;

	private VelocityEngine velocityEngine;

	private String from;

	private String hostAddress;

	private String subject;

	/**
	 * 	This class send activational message to given user.
	 * @param user - user which will be spamed.
	 */
	public void sendActivation(final User user) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						MESSAGE_ENCODING);
				message.setTo(user.getLogin());
				message.setFrom(from);
				message.setSubject(subject);
				Map model = new HashMap();
				model.put(T_USER, user.getLogin());
				model.put(T_ID, user.getLogin().hashCode());
				model.put(T_LINK, hostAddress);
				model.put(T_PASS, user.getPass());
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, TEMPLATE_NAME, model);
				message.setText(text, true);
			}
		};
		this.mailSender.send(preparator);

	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	public void setSubject(String subject) {
		this.subject = subject;

	}
}