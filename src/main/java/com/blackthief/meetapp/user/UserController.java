package com.blackthief.meetapp.user;

import java.net.URI;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blackthief.meetapp.meeting.MeetingResource;
import com.blackthief.meetapp.meeting.MeetingService;
import com.blackthief.meetapp.security.AuthorizationRequest;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private UserService userService;
	private MeetingService meetingService;

	public UserController(final UserService userService, final MeetingService meetingService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.meetingService =  meetingService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<Resources<UserResource>> getAll() {
		final Resources<UserResource> resources = new Resources<>(userService.getAll()
				.stream()
				.map(UserResource::new)
				.collect(Collectors.toList()));
		
		final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
	    resources.add(new Link(uriString, "self"));
	    return ResponseEntity.ok(resources);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<UserResource> getById(@Min(1) @PathVariable long id) {
		return userService
		        .getById(id)
		        .map(user -> ResponseEntity.ok(new UserResource(user)))
		        .orElseThrow(() -> new UserNotFoundException(id));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<UserResource> save(@Valid @RequestBody AuthorizationRequest userRequest) {
		userRequest.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
		final User userToSave = userService.save(UserMapper.toDomain(userRequest));
		
		final URI uri = MvcUriComponentsBuilder.fromController(getClass())
		            .path("/{id}")
		            .buildAndExpand(userToSave.getId())
		            .toUri();
		
	    return ResponseEntity.created(uri).body(new UserResource(userToSave));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}/meetings")
	public ResponseEntity<Resources<MeetingResource>> getMeetingForUser(@Min(1) @PathVariable final long id) {
		final Resources<MeetingResource> resources = new Resources<>(meetingService
				.getByUser(id)
				.stream()
				.map(MeetingResource::new)
				.collect(Collectors.toList()));
	    
	    final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
	    resources.add(new Link(uriString, "self"));
	    return ResponseEntity.ok(resources);
	}
}
