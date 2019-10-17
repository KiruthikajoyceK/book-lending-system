package com.hcl.booklendingsystem.service;

import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hcl.booklendingsystem.dto.UserRequest;
import com.hcl.booklendingsystem.entity.User;
import com.hcl.booklendingsystem.repository.UserRepository;
import com.hcl.booklendingsystem.util.BookLendingSystemConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * @since 2019-10-16 This class includes method for register user
 * @author Sreeshma S Menon
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	/**
	 * @param userRequest
	 * @return Optional<User> This method will accept userRequest as input,copy the
	 *         properties from userRequest to user object and call the save method
	 *         in repository and return back optional of User object.
	 */
	@Override
	public Optional<User> save(UserRequest userRequest) {
		log.info(BookLendingSystemConstants.SAVE_USER_DEBUG_START_SERVICE);
		User user = new User();
		BeanUtils.copyProperties(userRequest, user);
		user.setPassword(userRequest.getPassword());
		user = userRepository.save(user);
		log.info(BookLendingSystemConstants.SAVE_USER_DEBUG_END_SERVICE);
		return Optional.of(user);
	}



	/**
	 * @param email
	 * @return Optional of User
	 * This method will accept email and return respective user object.
	 */
	@Override
	public Optional<User> getUserByEmail(String email) {
		log.debug(BookLendingSystemConstants.GET_USER_BY_EMAIL_DEBUG_START_SERVICE);
         Optional<User> userOptional= userRepository.findByEmail(email);
		log.debug(BookLendingSystemConstants.GET_USER_BY_EMAIL_DEBUG_END_SERVICE);
		return userOptional;
	}

}
