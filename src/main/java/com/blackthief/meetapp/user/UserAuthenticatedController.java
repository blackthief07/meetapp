package com.blackthief.meetapp.user;


import java.util.stream.Collectors;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blackthief.meetapp.meetup.MeetUpResource;
import com.blackthief.meetapp.meetup.MeetUpService;

@RestController
@RequestMapping("/users/me")
@CrossOrigin
public class UserAuthenticatedController {
	
	private UserService userService;
	private MeetUpService meetUpService;

	public UserAuthenticatedController(final UserService userService, final MeetUpService meetUpService) {
		this.userService = userService;
		this.meetUpService =  meetUpService;
	}
	
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<UserAuthenticatedResource> get() {
		return userService.getByUsername(((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
				.map(user -> ResponseEntity.ok(new UserAuthenticatedResource(user)))
				.orElseThrow(() -> new UsernameNotFoundException("Incorrect username"));
	}

	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@GetMapping("/meetups")
	public ResponseEntity<Resources<MeetUpResource>> getMeetUps() {
		final User userAuthenticated = userService.getByUsername(((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("Incorrect username"));
		
		final Resources<MeetUpResource> resources = new Resources<>(meetUpService
				.getByUser(userAuthenticated.getId())
				.stream()
				.map(MeetUpResource::new)
				.collect(Collectors.toList()));
	    
	    final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
	    resources.add(new Link(uriString, "self"));
	    return ResponseEntity.ok(resources);
	}
}