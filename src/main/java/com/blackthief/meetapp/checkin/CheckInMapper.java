package com.blackthief.meetapp.checkin;

public class CheckInMapper {

	private CheckInMapper() {
	}

	public static CheckInResponse toResponse(CheckIn checkIn) {
		return CheckInResponse.builder()
				.attendee(checkIn.getAttendee().getName())
				.checkedIn(checkIn.getCheckedIn())
				.build();
	}
}