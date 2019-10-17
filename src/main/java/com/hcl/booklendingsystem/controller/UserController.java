package com.hcl.booklendingsystem.controller;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hcl.booklendingsystem.dto.CommonResponse;
import com.hcl.booklendingsystem.dto.UserRequest;
import com.hcl.booklendingsystem.entity.User;
import com.hcl.booklendingsystem.exception.UserException;
import com.hcl.booklendingsystem.service.UserService;
import com.hcl.booklendingsystem.util.BookLendingSystemConstants;
import com.hcl.booklendingsystem.validator.UserRequestValidator;

import lombok.extern.slf4j.Slf4j;

/**
 * @since 2019-10-16 
 * This class includes method for register user
 * @author Sreeshma S Menon
 */
@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class UserController {

   @Autowired
   UserService userService;
   
   @Autowired
   UserRequestValidator userRequestValidator;
   
   @InitBinder("userRequest")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(userRequestValidator);
	}
   
	/**
	 * @param userRequest
	 * @param bindingResult
	 * @return ResponseEntity of String which includes the message that user created
	 *         successfully or not. 
	 *         This method will accept userRequest and
	 *         bindingResult as inputs and call the save method in service layer if
	 *         there is no binding errors, otherwise throw an exception.
	 */
	@PostMapping(value = "/")
	public ResponseEntity<CommonResponse> save(@Valid @RequestBody UserRequest userRequest,
			BindingResult bindingResult) {
		log.debug(BookLendingSystemConstants.SAVE_USER_DEBUG_START_CONTROLLER);
		CommonResponse commonResponse = new CommonResponse();
		if (bindingResult.hasErrors()) {
			throw new UserException(
					bindingResult.getFieldError().getField() + " " + bindingResult.getFieldError().getDefaultMessage());
		}
		Optional<User> optionalUser = userService.save(userRequest);
		if (optionalUser.isPresent() && optionalUser.get().getUserId() != null) {
			commonResponse.setStatusCode(HttpStatus.OK.value());
			commonResponse.setMessage(BookLendingSystemConstants.CREATE_SUCESS_MESSAGE);
		}
		log.debug(BookLendingSystemConstants.SAVE_USER_DEBUG_END_CONTROLLER);
		return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
	}

   
}
