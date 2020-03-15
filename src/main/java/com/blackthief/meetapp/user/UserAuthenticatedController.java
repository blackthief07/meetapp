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

import com.blackthief.meetapp.meeting.MeetingResource;
import com.blackthief.meetapp.meeting.MeetingService;

@RestController
@RequestMapping("/users/me")
@CrossOrigin
public class UserAuthenticatedController {
	
	private UserService userService;
	private MeetingService meetingService;

	public UserAuthenticatedController(final UserService userService, final MeetingService meetingService) {
		this.userService = userService;
		this.meetingService =  meetingService;
	}
	
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<UserAuthenticatedResource> get() {
		return userService.getByUsername(((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
				.map(user -> ResponseEntity.ok(new UserAuthenticatedResource(user)))
				.orElseThrow(() -> new UsernameNotFoundException("Incorrect username"));
	}

	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@GetMapping("/meetings")
	public ResponseEntity<Resources<MeetingResource>> getMeetings() {
		final User userAuthenticated = userService.getByUsername(((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("Incorrect username"));
		
		final Resources<MeetingResource> resources = new Resources<>(meetingService
				.getByUser(userAuthenticated.getId())
				.stream()
				.map(MeetingResource::new)
				.collect(Collectors.toList()));
	    
	    final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
	    resources.add(new Link(uriString, "self"));
	    return ResponseEntity.ok(resources);
	}
}