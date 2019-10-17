package com.hcl.booklendingsystem.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.dto.UserRequest;
import com.hcl.booklendingsystem.entity.User;
import com.hcl.booklendingsystem.exception.UserException;
import com.hcl.booklendingsystem.service.UserService;
import com.hcl.booklendingsystem.validator.UserRequestValidator;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	@Mock
	UserService userService;
    @Mock
	UserRequestValidator userRequestValidator;
    @InjectMocks
    UserController userController;  
    BindingResult bindingResult;
    User user;
    FieldError fieldError;
    UserRequest userRequest;
    
    @Before
    public void setup() {
 	   bindingResult = mock(BindingResult.class);
 	   user=new User();
 	   user.setUserId(1);
 	   user.setEmail("sree@gmail.com");
 	   user.setPassword("sree123");
 	   userRequest=new UserRequest();
 	   BeanUtils.copyProperties(user, userRequest);
 	   fieldError=new FieldError("loginRequest", "userName", "Must Not empty");
    }
    
    @Test
    public void testSave() {
 	   Mockito.when(bindingResult.hasErrors()).thenReturn(false);
 	   Optional<User> optionalUser=Optional.of(user);
	   Mockito.when( userService.save(Mockito.any())).thenReturn(optionalUser);
       ResponseEntity<CommonResponse> commonResponse=userController.save(userRequest, bindingResult);
       assertNotNull(commonResponse);
    }
    @Test(expected=UserException.class)
    public void testSaveBindException() {
 	   Mockito.when(bindingResult.hasErrors()).thenReturn(true);
	   Mockito.when(bindingResult.getFieldError()).thenReturn(fieldError);
 	   ResponseEntity<CommonResponse> commonResponse=userController.save(userRequest, bindingResult);
       assertNotNull(commonResponse);
    }
    
    
    
 
}
