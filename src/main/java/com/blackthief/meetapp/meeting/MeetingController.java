package com.blackthief.meetapp.meeting;


import java.net.URI;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blackthief.meetapp.user.User;
import com.blackthief.meetapp.user.UserResource;
import com.blackthief.meetapp.user.UserService;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

@RestController
@RequestMapping(value = "/meetings", produces = "application/hal+json")
@CrossOrigin
public class MeetingController {

	final MeetingService meetingService;
	final UserService userService;

	public MeetingController(final MeetingService meetingService, final UserService userSevice) {
		this.meetingService = meetingService;
		this.userService = userSevice;
	}

	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@GetMapping("/")
	public ResponseEntity<Resources<MeetingResource>> getAll() {
		final Resources<MeetingResource> resources = new Resources<>(meetingService.getAll()
				.stream()
				.map(MeetingResource::new)
				.collect(Collectors.toList()));
		
	    final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
	    resources.add(new Link(uriString, "self"));
	    return ResponseEntity.ok(resources);
	}
	
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<MeetingResource> getById(@Min(1) @PathVariable long id) {
		return meetingService
		        .getById(id)
		        .map(meeting -> ResponseEntity.ok(new MeetingResource(meeting)))
		        .orElseThrow(() -> new MeetingNotFoundException(id));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<MeetingResource> save(@RequestBody final MeetingRequest meetingRequest) {
		final Meeting meetingToSave = meetingService.save(MeetingMapper.toDomain(meetingRequest));
		
		final URI uri = MvcUriComponentsBuilder.fromController(getClass())
		            .path("/{id}")
		            .buildAndExpand(meetingToSave.getId())
		            .toUri();
		
	    return ResponseEntity.created(uri).body(new MeetingResource(meetingToSave));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}/attendees")
	public ResponseEntity<Resources<UserResource>> getAttendees(@Min(1) @PathVariable long id) {
		final Resources<UserResource> resources = new Resources<>(meetingService
	        .getById(id)
	        .orElseThrow(() -> new MeetingNotFoundException(id))
	        .getAttendees()
	        .stream()
	        .map(UserResource::new)
	        .collect(Collectors.toList()));
	        
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
	    resources.add(new Link(uriString, "self"));
	    return ResponseEntity.ok(resources);
	}
	
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@PatchMapping("/{id}/attendees/me")
	public ResponseEntity<MeetingResource> addAttendee(@Min(1) @PathVariable final long id) {
		final User userAuthenticated = userService.getByUsername(((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("Incorrect username"));
		
		Meeting meetingToSave = meetingService
		        .getById(id)
		        .orElseThrow(() -> new MeetingNotFoundException(id));
		
		meetingToSave.addAttendee(userAuthenticated);
		
		final Meeting meetingSaved = meetingService.update(meetingToSave);
		
		final URI uri = MvcUriComponentsBuilder.fromController(getClass())
		            .path("/{id}")
		            .buildAndExpand(meetingSaved.getId())
		            .toUri();
		
	    return ResponseEntity.created(uri).body(new MeetingResource(meetingSaved));
	}
}