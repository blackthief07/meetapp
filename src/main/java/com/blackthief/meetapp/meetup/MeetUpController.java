package com.blackthief.meetapp.meetup;

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
@RequestMapping(value = "/meetups", produces = "application/hal+json")
@CrossOrigin
public class MeetUpController {

	final MeetUpService meetUpService;
	final UserService userService;

	public MeetUpController(final MeetUpService meetUpService, final UserService userSevice) {
		this.meetUpService = meetUpService;
		this.userService = userSevice;
	}

	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@GetMapping("/")
	public ResponseEntity<Resources<MeetUpResource>> getAll() {
		final Resources<MeetUpResource> resources = new Resources<>(meetUpService.getAll()
				.stream()
				.map(MeetUpResource::new)
				.collect(Collectors.toList()));
		
	    final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
	    resources.add(new Link(uriString, "self"));
	    return ResponseEntity.ok(resources);
	}
	
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<MeetUpResource> getById(@Min(1) @PathVariable long id) {
		return meetUpService
		        .getById(id)
		        .map(meeting -> ResponseEntity.ok(new MeetUpResource(meeting)))
		        .orElseThrow(() -> new MeetUpNotFoundException(id));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<MeetUpResource> save(@RequestBody final MeetUpRequest meetUpRequest) {
		final MeetUp meetUpToSave = meetUpService.save(MeetUpMapper.toDomain(meetUpRequest));
		
		final URI uri = MvcUriComponentsBuilder.fromController(getClass())
		            .path("/{id}")
		            .buildAndExpand(meetUpToSave.getId())
		            .toUri();
		
	    return ResponseEntity.created(uri).body(new MeetUpResource(meetUpToSave));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}/attendees")
	public ResponseEntity<Resources<UserResource>> getAttendees(@Min(1) @PathVariable long id) {
		final Resources<UserResource> resources = new Resources<>(meetUpService
	        .getById(id)
	        .orElseThrow(() -> new MeetUpNotFoundException(id))
	        .getMeetUpCheckIns()
	        .stream()
	        .map(checkIns -> new UserResource(checkIns.getAttendee()))
	        .collect(Collectors.toList()));
	        
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
	    resources.add(new Link(uriString, "self"));
	    return ResponseEntity.ok(resources);
	}
	
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@PutMapping("/{id}/attendees/me")
	public ResponseEntity<MeetUpResource> addAttendee(@Min(1) @PathVariable final long id) {
		final User userAuthenticated = userService.getByUsername(((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("Incorrect username"));
		
		MeetUp meetUpToSave = meetUpService
		        .getById(id)
		        .orElseThrow(() -> new MeetUpNotFoundException(id));
		
		meetUpToSave.addAttendee(userAuthenticated);
		
		final MeetUp meetingSaved = meetUpService.update(meetUpToSave);
		
		final URI uri = MvcUriComponentsBuilder.fromController(getClass())
		            .path("/{id}")
		            .buildAndExpand(meetingSaved.getId())
		            .toUri();
		
	    return ResponseEntity.created(uri).body(new MeetUpResource(meetingSaved));
	}
}