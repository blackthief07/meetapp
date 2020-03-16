package com.blackthief.meetapp.checkin;

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

import com.blackthief.meetapp.meetup.MeetUp;
import com.blackthief.meetapp.meetup.MeetUpNotFoundException;
import com.blackthief.meetapp.meetup.MeetUpService;
import com.blackthief.meetapp.user.User;
import com.blackthief.meetapp.user.UserService;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

@RestController
@RequestMapping(produces = "application/hal+json")
@CrossOrigin
public class CheckInController {

	final MeetUpService meetUpService;
	final UserService userService;
	final CheckInService checkInService;

	public CheckInController(final MeetUpService meetUpService, final UserService userSevice, final CheckInService checkInService) {
		this.meetUpService = meetUpService;
		this.userService = userSevice;
		this.checkInService = checkInService;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/meetups/{id}/checkins")
	public ResponseEntity<Resources<CheckInResource>> getById(@Min(1) @PathVariable final long id) {
		final Resources<CheckInResource> resources = new Resources<>(meetUpService
	        .getById(id)
	        .orElseThrow(() -> new MeetUpNotFoundException(id))
	        .getMeetUpCheckIns()
	        .stream()
	        .map(CheckInResource::new)
	        .collect(Collectors.toList()));
	    
	    final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
	    resources.add(new Link(uriString, "self"));
	    return ResponseEntity.ok(resources);
	}
	
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@PatchMapping("/meetups/{id}/checkins/me")
	public ResponseEntity<CheckInResource> addAttendee(@Min(1) @PathVariable final long id) {
		final User userAuthenticated = userService.getByUsername(((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("Incorrect username"));
		
		MeetUp meetUp = meetUpService
		        .getById(id)
		        .orElseThrow(() -> new MeetUpNotFoundException(id));
		
		CheckIn checkInToSave = meetUp.getMeetUpCheckIns()
			.stream()
			.filter(checkIn -> Long.compare(checkIn.getAttendee().getId(), userAuthenticated.getId()) == 0)
			.findFirst()
			.orElseThrow(() -> new CheckInNotFoundException(id));
		
		checkInToSave.setCheckedIn(true);
		
		final CheckIn checkInSaved = checkInService.update(checkInToSave);
		
		final URI uri = MvcUriComponentsBuilder.fromController(getClass())
		            .path("/{id}")
		            .buildAndExpand(checkInSaved.getId())
		            .toUri();
		
	    return ResponseEntity.created(uri).body(new CheckInResource(checkInSaved));
	}
}