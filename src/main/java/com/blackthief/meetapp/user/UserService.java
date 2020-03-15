package com.blackthief.meetapp.user;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	List<User> getAll();
	Optional<User> getById(long id);
	Optional<User> getByUsername(String userName);
	User save(User user);
}
