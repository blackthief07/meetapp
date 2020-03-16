package com.blackthief.meetapp.meetup;

public class MeetUpMapper {

	private MeetUpMapper() {
	}

	public static MeetUpResponse toResponse(MeetUp meetUp) {
		return MeetUpResponse.builder()
				.description(meetUp.getDescription())
				.date(meetUp.getDate())
				.maxAttendees(meetUp.getMaxAttendees())
				.id(meetUp.getId())
				.build();
	}

	public static MeetUp toDomain(MeetUpRequest meetUpRequest) {
		return MeetUp.builder()
				.description(meetUpRequest.getDescription())
				.date(meetUpRequest.getDate())
				.build();
	}
}