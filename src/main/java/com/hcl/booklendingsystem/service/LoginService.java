package com.hcl.booklendingsystem.service;

import java.util.Optional;

import com.hcl.booklendingsystem.dto.LoginRequest;
import com.hcl.booklendingsystem.entity.User;

public interface LoginService {
	  public Optional<User> getUerByUsernameAndPassword(LoginRequest loginRequest);

}
