package com.blackthief.meetapp.meeting;

public class MeetingMapper {

	private MeetingMapper() {
	}

	public static MeetingResponse toResponse(Meeting meeting) {
		return MeetingResponse.builder()
				.description(meeting.getDescription())
				.date(meeting.getDate())
				.weatherTemp(meeting.getWeatherTemp())
				.maxAttendees(meeting.getMaxAttendees())
				.id(meeting.getId())
				.build();
	}

	public static Meeting toDomain(MeetingRequest meetingRequest) {
		return Meeting.builder()
				.description(meetingRequest.getDescription())
				.date(meetingRequest.getDate())
				.build();
	}
}