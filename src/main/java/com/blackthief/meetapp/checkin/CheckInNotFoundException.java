package com.blackthief.meetapp.checkin;

import lombok.Getter;

@Getter
public class CheckInNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final Long id;

	public CheckInNotFoundException(final long id) {
		super("CheckIns could not be found for meetup id: " + id);
		this.id = id;
	}
}