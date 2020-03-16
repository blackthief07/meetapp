package com.blackthief.meetapp.meetup;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import com.blackthief.meetapp.beer.BeerController;
import com.blackthief.meetapp.checkin.CheckInController;
import com.blackthief.meetapp.weather.WeatherController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class MeetUpResource extends ResourceSupport {

  private final MeetUpResponse meetUpResponse;

  public MeetUpResource(final MeetUp meetUp) {
    this.meetUpResponse = MeetUpMapper.toResponse(meetUp);
    final long id = meetUp.getId();
    add(linkTo(MeetUpController.class).withRel("meetups"));
    add(linkTo(methodOn(MeetUpController.class).getAttendees(id)).withRel("attendees"));
    add(linkTo(methodOn(MeetUpController.class).addAttendee(id)).withRel("add-attendee"));
    add(linkTo(methodOn(WeatherController.class).getById(id)).withRel("weather"));
    add(linkTo(methodOn(BeerController.class).getById(id)).withRel("beers"));
    add(linkTo(methodOn(CheckInController.class).getById(id)).withRel("checkins"));
    add(linkTo(methodOn(MeetUpController.class).getById(id)).withSelfRel());
  }
}