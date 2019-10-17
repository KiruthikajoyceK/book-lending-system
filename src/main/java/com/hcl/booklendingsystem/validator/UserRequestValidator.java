package com.hcl.booklendingsystem.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hcl.booklendingsystem.dto.UserRequest;
import com.hcl.booklendingsystem.entity.User;
import com.hcl.booklendingsystem.exception.EmailExistException;
import com.hcl.booklendingsystem.exception.UserException;
import com.hcl.booklendingsystem.service.UserService;
import com.hcl.booklendingsystem.util.BookLendingSystemConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * @since 2019-10-14 This class will validate the userRequest properties.
 * @author Sreeshma S Menon
 */
@Slf4j
@Component
public class UserRequestValidator implements Validator {

	@Autowired
	UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UserRequest.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		log.debug(BookLendingSystemConstants.VALIDATING);
		UserRequest bean = (UserRequest) target;
		validateEmail(bean);
		validatePhoneNumber(bean.getPhone());

	}

	private void validateEmail(UserRequest bean) {
		Optional<User> user = userService.getUserByEmail(bean.getEmail());
		if (user.isPresent()) {
			throw new EmailExistException(BookLendingSystemConstants.EMAIL_EXIST_EXCEPTION);
		}
	}

	private void validatePhoneNumber(Long phoneNumber) {
		if (phoneNumber == null || phoneNumber.toString().length() != 10) {
			throw new UserException(BookLendingSystemConstants.INVALID_PHONE_NO);
		}
	}
}
