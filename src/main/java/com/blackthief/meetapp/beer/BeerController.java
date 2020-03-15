package com.blackthief.meetapp.beer;


import java.time.LocalDateTime;

import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.blackthief.meetapp.meeting.Meeting;
import com.blackthief.meetapp.meeting.MeetingNotFoundException;
import com.blackthief.meetapp.meeting.MeetingService;
import com.blackthief.meetapp.weather.Weather;
import com.blackthief.meetapp.weather.WeatherNotFoundException;
import com.blackthief.meetapp.weather.WeatherService;

@RestController
@CrossOrigin
public class BeerController {

	final BeerService beerService;
	final MeetingService meetingService;
	final WeatherService weatherService;

	public BeerController(final BeerService beerService, final MeetingService meetingService, final WeatherService weatherService) {
		this.beerService = beerService;
		this.meetingService = meetingService;
		this.weatherService = weatherService;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = { "/meetings/{id}/beers" }, produces = { "application/hal+json" })
	public ResponseEntity<BeerResource> getById(@Min(1) @PathVariable final long id) {
		
		final Meeting meeting = meetingService
	        .getById(id)
	        .orElseThrow(() -> new MeetingNotFoundException(id));
		
		final Weather weather = (meeting.getWeather() == null) ? this.GetWeatherFromApi(meeting) : meeting.getWeather();
		meeting.setWeather(weather);
		
		final Beer beer = this.GetBeer(meeting);
        
		return ResponseEntity.ok(new BeerResource(beer));
	}
	
	private Beer GetBeer(Meeting meeting) {
		final Beer beer = beerService.getByMeeting(meeting)
				.orElseThrow(() -> new BeerNotFoundException(meeting.getId()));
		
		if(meeting.getBeer() == null) {
			return beerService.save(beer);
		}
		
		meeting.getBeer().setCantBeers(beer.getCantBeers());
		meeting.getBeer().setBeersPerPackage(beer.getBeersPerPackage());
		meeting.getBeer().setPacksToBuy(beer.getPacksToBuy());

		return beerService.update(meeting.getBeer());
	}
	
	private Weather GetWeatherFromApi(Meeting meeting) {
		final LocalDateTime date = meeting.getDate();
		
		return weatherService
		        .getByMeeting(meeting)
		        .orElseThrow(() -> new WeatherNotFoundException(date));
	}
}