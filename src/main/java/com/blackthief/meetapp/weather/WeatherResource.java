package com.blackthief.meetapp.weather;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import com.blackthief.meetapp.meetup.MeetUpController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class WeatherResource extends ResourceSupport {

  private final WeatherResponse weatherResponse;

  public WeatherResource(final Weather weather) {
    this.weatherResponse = WeatherMapper.toResponse(weather);
    final long id = weather.getMeetUp().getId();
    add(linkTo(methodOn(MeetUpController.class).getById(id)).withRel("meetup"));
    add(linkTo(methodOn(WeatherController.class).getById(id)).withSelfRel());
  }
}