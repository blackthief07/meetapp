package com.blackthief.meetapp.weather;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.blackthief.meetapp.meeting.Meeting;
import com.blackthief.meetapp.meeting.MeetingNotFoundException;
import com.blackthief.meetapp.meeting.MeetingService;

@RestController
@CrossOrigin
public class WeatherController {

	final WeatherService weatherService;
	final MeetingService meetingService;

	public WeatherController(final WeatherService weatherService, final MeetingService meetingService) {
		this.weatherService = weatherService;
		this.meetingService = meetingService;
	}
	
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@GetMapping(value = { "/meetings/{id}/weather" }, produces = { "application/hal+json" })
	public ResponseEntity<WeatherResource> getById(@Min(1) @PathVariable final long id) {
		
		Meeting meeting = meetingService
	        .getById(id)
	        .orElseThrow(() -> new MeetingNotFoundException(id));
		
		
		final Weather weather = (meeting.getWeather() == null) ? this.GetWeatherFromApi(meeting) : meeting.getWeather();
        
		return ResponseEntity.ok(new WeatherResource(weather));
	}
	
	private Weather GetWeatherFromApi(Meeting meeting) {
		final LocalDateTime date = meeting.getDate();
		
		return weatherService
		        .getByMeeting(meeting)
		        .orElseThrow(() -> new WeatherNotFoundException(date));
	}
}