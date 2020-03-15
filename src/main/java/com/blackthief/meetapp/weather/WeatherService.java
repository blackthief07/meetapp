package com.blackthief.meetapp.weather;

import java.util.Optional;

import com.blackthief.meetapp.meeting.Meeting;

public interface WeatherService {
	
	Optional<Weather> getByMeeting(Meeting meeting);
	Weather save(Weather weather);
}
