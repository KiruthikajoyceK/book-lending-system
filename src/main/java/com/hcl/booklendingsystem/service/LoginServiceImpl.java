package com.hcl.booklendingsystem.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.booklendingsystem.dto.LoginRequest;
import com.hcl.booklendingsystem.entity.User;
import com.hcl.booklendingsystem.repository.UserRepository;
import com.hcl.booklendingsystem.util.BookLendingSystemConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
	public static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	/**
	 * @param loginRequest
	 * @return optional of User This method will accept loginRequest as input and
	 *         get user object for given user name and password.Then return optional
	 *         of user.
	 */
	@Override
	public Optional<User> getUerByUsernameAndPassword(LoginRequest loginRequest) {
		LOGGER.debug(BookLendingSystemConstants.LOGIN_DEBUG_START_SERVICE);
		Optional<User> optionalUser = userRepository.findByEmailAndPassword(loginRequest.getEmail(),
				loginRequest.getPassword());
		/*
		 * if(!(optionalUser.isPresent() && BCrypt.checkpw(loginRequest.getPassword(),
		 * optionalUser.get().getPassword()))) { throw new
		 * UserNotFoundException(BookLendingSystemConstants.USER_NOT_FOUND); }
		 */
		LOGGER.debug(BookLendingSystemConstants.LOGIN_DEBUG_END_SERVICE);
		return optionalUser;
	}
}
