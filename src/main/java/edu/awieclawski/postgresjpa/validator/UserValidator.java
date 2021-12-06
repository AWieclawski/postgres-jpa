package edu.awieclawski.postgresjpa.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.awieclawski.postgresjpa.credentials.entities.User;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {

		User user = (User) o;

		if (user.getUserExists()) {
			errors.rejectValue("username", "Reject.userForm.userExists");
			return;
		}

		// if placed by @Size annotation at entity - DemoBeans can not save it encrypted
		if (user.getPassword().length() < 8 || user.getPassword().length() > 20) {
			errors.rejectValue("passwordConfirm", "Size.userForm.password");
		}

		if (!user.getPasswordConfirm().equals(user.getPassword()))
			errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
	}

}
