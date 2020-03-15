package com.blackthief.meetapp.meeting;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import com.blackthief.meetapp.beer.BeerController;
import com.blackthief.meetapp.weather.WeatherController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class MeetingResource extends ResourceSupport {

  private final MeetingResponse meetingResponse;

  public MeetingResource(final Meeting meeting) {
    this.meetingResponse = MeetingMapper.toResponse(meeting);
    final long id = meeting.getId();
    add(linkTo(MeetingController.class).withRel("meetings"));
    add(linkTo(methodOn(MeetingController.class).getAttendees(id)).withRel("attendees"));
    add(linkTo(methodOn(MeetingController.class).addAttendee(id)).withRel("add-attendee"));
    add(linkTo(methodOn(WeatherController.class).getById(id)).withRel("weather"));
    add(linkTo(methodOn(BeerController.class).getById(id)).withRel("beer"));
    add(linkTo(methodOn(MeetingController.class).getById(id)).withSelfRel());
  }
}