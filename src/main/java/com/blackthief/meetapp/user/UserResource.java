package com.blackthief.meetapp.user;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class UserResource extends ResourceSupport {

  private final UserResponse userResponse;

  public UserResource(final User user) {
    this.userResponse = UserMapper.toResponse(user);
    final long id = user.getId();
    add(linkTo(UserController.class).withRel("users"));
    add(linkTo(methodOn(UserController.class).getMeetingForUser(id)).withRel("meetings"));
    add(linkTo(methodOn(UserController.class).getById(id)).withSelfRel());
  }
}