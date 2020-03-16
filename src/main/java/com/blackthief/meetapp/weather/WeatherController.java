package com.blackthief.meetapp.weather;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.blackthief.meetapp.meetup.MeetUp;
import com.blackthief.meetapp.meetup.MeetUpNotFoundException;
import com.blackthief.meetapp.meetup.MeetUpService;

@RestController
@CrossOrigin
public class WeatherController {

	final WeatherService weatherService;
	final MeetUpService meetUpService;

	public WeatherController(final WeatherService weatherService, final MeetUpService meetUpService) {
		this.weatherService = weatherService;
		this.meetUpService = meetUpService;
	}
	
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	@GetMapping(value = { "/meetups/{id}/weather" }, produces = { "application/hal+json" })
	public ResponseEntity<WeatherResource> getById(@Min(1) @PathVariable final long id) {
		
		MeetUp meetUp = meetUpService
	        .getById(id)
	        .orElseThrow(() -> new MeetUpNotFoundException(id));
		
		
		final Weather weather = (meetUp.getWeather() == null) ? this.GetWeatherFromApi(meetUp) : meetUp.getWeather();
        
		return ResponseEntity.ok(new WeatherResource(weather));
	}
	
	private Weather GetWeatherFromApi(MeetUp meetUp) {
		final LocalDateTime date = meetUp.getDate();
		
		return weatherService
		        .getByMeetUp(meetUp)
		        .orElseThrow(() -> new WeatherNotFoundException(date));
	}
}