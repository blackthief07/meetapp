package com.blackthief.meetapp.user;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class UserAuthenticatedResource extends ResourceSupport {

  private final UserResponse userResponse;

  public UserAuthenticatedResource(final User user) {
    this.userResponse = UserMapper.toResponse(user);
    add(linkTo(UserController.class).withRel("users"));
    add(linkTo(methodOn(UserAuthenticatedController.class).getMeetUps()).withRel("meetups"));
  }
}