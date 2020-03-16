package com.blackthief.meetapp.beer;


import java.time.LocalDateTime;

import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.blackthief.meetapp.meetup.MeetUp;
import com.blackthief.meetapp.meetup.MeetUpNotFoundException;
import com.blackthief.meetapp.meetup.MeetUpService;
import com.blackthief.meetapp.weather.Weather;
import com.blackthief.meetapp.weather.WeatherNotFoundException;
import com.blackthief.meetapp.weather.WeatherService;

@RestController
@CrossOrigin
public class BeerController {

	final BeerService beerService;
	final MeetUpService meetUpService;
	final WeatherService weatherService;

	public BeerController(final BeerService beerService, final MeetUpService meetUpService, final WeatherService weatherService) {
		this.beerService = beerService;
		this.meetUpService = meetUpService;
		this.weatherService = weatherService;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = { "/meetups/{id}/beers" }, produces = { "application/hal+json" })
	public ResponseEntity<BeerResource> getById(@Min(1) @PathVariable final long id) {
		
		final MeetUp meetUp = meetUpService
	        .getById(id)
	        .orElseThrow(() -> new MeetUpNotFoundException(id));
		
		final Weather weather = (meetUp.getWeather() == null) ? this.GetWeatherFromApi(meetUp) : meetUp.getWeather();
		meetUp.setWeather(weather);
		
		final Beer beer = this.GetBeer(meetUp);
        
		return ResponseEntity.ok(new BeerResource(beer));
	}
	
	private Beer GetBeer(MeetUp meetUp) {
		final Beer beer = beerService.getByMeetUp(meetUp)
				.orElseThrow(() -> new BeerNotFoundException(meetUp.getId()));
		
		if(meetUp.getBeer() == null) {
			return beerService.save(beer);
		}
		
		meetUp.getBeer().setCantBeers(beer.getCantBeers());
		meetUp.getBeer().setBeersPerPackage(beer.getBeersPerPackage());
		meetUp.getBeer().setPacksToBuy(beer.getPacksToBuy());

		return beerService.update(meetUp.getBeer());
	}
	
	private Weather GetWeatherFromApi(MeetUp meetUp) {
		final LocalDateTime date = meetUp.getDate();
		
		return weatherService
		        .getByMeetUp(meetUp)
		        .orElseThrow(() -> new WeatherNotFoundException(date));
	}
}