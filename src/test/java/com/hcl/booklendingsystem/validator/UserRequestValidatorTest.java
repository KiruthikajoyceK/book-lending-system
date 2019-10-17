package com.hcl.booklendingsystem.validator;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.hcl.booklendingsystem.dto.UserRequest;
import com.hcl.booklendingsystem.entity.User;
import com.hcl.booklendingsystem.exception.EmailExistException;
import com.hcl.booklendingsystem.exception.UserException;
import com.hcl.booklendingsystem.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserRequestValidatorTest {

	@InjectMocks
	UserRequestValidator userRequestValidator;
	@Mock
	UserService userService;
	UserRequest userRequest;
	User user;
	 FieldError fieldError;
	 Errors error;
	@Before
	public void setup() {
		userRequest = new UserRequest();
		userRequest.setEmail("sree@gmail.com");
		userRequest.setPassword("sree123");
		userRequest.setPhone(999999999L);
		
		user = new User();
		user.setUserId(1);
		user.setEmail("sree@gmail.com");
		user.setPassword("sree123");
		user.setPhone(999999999L);
		
		 fieldError=new FieldError("loginRequest", "userName", "Must Not empty");

	}

	@Test(expected = EmailExistException.class)
	public void testValidate() {
		error=Mockito.mock(Errors.class);
		Mockito.when(userService.getUserByEmail(userRequest.getEmail())).thenReturn(Optional.of(user));
		userRequestValidator.validate(userRequest, error);
	}
	@Test(expected = UserException.class)
	public void testUserException() {
		error=Mockito.mock(Errors.class);
		userRequest.setPhone(222L);
		Mockito.when(userService.getUserByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
		userRequestValidator.validate(userRequest, error);
	}

}
