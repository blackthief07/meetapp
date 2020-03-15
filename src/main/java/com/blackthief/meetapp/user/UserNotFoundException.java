package com.blackthief.meetapp.user;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final Long id;

	public UserNotFoundException(final long id) {
		super("User could not be found with id: " + id);
		this.id = id;
	}
}