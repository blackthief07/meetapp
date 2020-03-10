package com.blackthief.meetapp.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.blackthief.meetapp.security.AuthorizationRequest;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private UserService userService;

	public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	//@Secured("ROLE_ADMIN")
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable long id) {
		final User user = userService.getUser(id);

		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		UserResponse userResponse = UserMapper.toResponse(user);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody AuthorizationRequest userRequest) {
		userRequest.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
		final User userToSave = userService.save(UserMapper.toDomain(userRequest));

		return new ResponseEntity<>(userToSave, HttpStatus.OK);
	}
}
