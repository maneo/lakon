package org.grejpfrut.evaluation.validators;

import org.grejpfrut.evaluation.domain.Text;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates add article form.
 * 
 * @author Adam Dudczak
 * 
 */
public class AddTextFormValidator implements Validator {

	public boolean supports(Class clazz) {
		return clazz.equals(Text.class);
	}

	public void validate(Object command, Errors errors) {

		Text art = (Text) command;
		ValidationUtils.rejectIfEmpty(errors, "title", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "text", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "extractLength", "field.required");

		final String text = art.getText();
		if (text.length() > 100000)
			errors.rejectValue("text", "text.to.long");
		if ( art.getExtractLength() < 0 )
			errors.rejectValue("extractLength", "length.below.zero");
			

	}

}
