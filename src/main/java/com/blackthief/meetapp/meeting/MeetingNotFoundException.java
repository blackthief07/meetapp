package com.blackthief.meetapp.meeting;

import lombok.Getter;

@Getter
public class MeetingNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final Long id;

	public MeetingNotFoundException(final long id) {
		super("Meetings could not be found with id: " + id);
		this.id = id;
	}
}