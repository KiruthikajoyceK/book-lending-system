package com.hcl.booklendingsystem.service;

import java.util.Optional;
import com.hcl.booklendingsystem.dto.UserRequest;
import com.hcl.booklendingsystem.entity.User;

public interface UserService {
	public Optional<User> save(UserRequest userRequest);
	public Optional<User> getUserByEmail(String email);

}
