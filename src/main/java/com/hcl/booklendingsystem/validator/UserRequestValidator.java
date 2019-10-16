package com.hcl.booklendingsystem.validator;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hcl.booklendingsystem.dto.UserRequest;
import com.hcl.booklendingsystem.entity.User;
import com.hcl.booklendingsystem.exception.EmailExistException;
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
	public static final Logger LOGGER = LoggerFactory.getLogger(UserRequestValidator.class);

	@Autowired
	UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UserRequest.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.debug(BookLendingSystemConstants.VALIDATING);
		UserRequest bean = (UserRequest) target;
		validateEmail(bean);
	}

	private void validateEmail(UserRequest bean) {
		Optional<User> user = userService.getUserByEmail(bean.getEmail());
		if (user.isPresent()) {
			throw new EmailExistException(BookLendingSystemConstants.EMAIL_EXIST_VALE);
		}
	}
}
