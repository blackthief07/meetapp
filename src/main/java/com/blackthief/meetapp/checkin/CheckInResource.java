package com.blackthief.meetapp.checkin;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import com.blackthief.meetapp.meetup.MeetUpController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class CheckInResource extends ResourceSupport {

  private final CheckInResponse checkInResponse;

  public CheckInResource(final CheckIn checkIn) {
    this.checkInResponse = CheckInMapper.toResponse(checkIn);
    final long id = checkIn.getMeetUp().getId();
    add(linkTo(methodOn(MeetUpController.class).getById(id)).withRel("meetup"));
    add(linkTo(methodOn(CheckInController.class).getById(id)).withRel("checkins"));
  }
}