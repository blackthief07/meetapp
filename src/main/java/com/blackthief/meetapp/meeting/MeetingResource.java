package com.blackthief.meetapp.meeting;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

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
    add(linkTo(methodOn(MeetingController.class).getById(id)).withSelfRel());
  }
}