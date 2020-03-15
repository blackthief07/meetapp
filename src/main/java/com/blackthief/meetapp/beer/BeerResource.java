package com.blackthief.meetapp.beer;


import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import com.blackthief.meetapp.meeting.MeetingController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class BeerResource extends ResourceSupport {

  private final BeerResponse beerResponse;

  public BeerResource(final Beer beer) {
    this.beerResponse = BeerMapper.toResponse(beer);
    final long id = beer.getMeeting().getId();
    add(linkTo(methodOn(MeetingController.class).getById(id)).withRel("meeting"));
    add(linkTo(methodOn(BeerController.class).getById(id)).withSelfRel());
  }
}