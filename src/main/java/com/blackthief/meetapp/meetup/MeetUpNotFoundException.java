package com.blackthief.meetapp.meetup;

import lombok.Getter;

@Getter
public class MeetUpNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final Long id;

	public MeetUpNotFoundException(final long id) {
		super("MeetUps could not be found with id: " + id);
		this.id = id;
	}
}