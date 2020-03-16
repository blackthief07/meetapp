package com.blackthief.meetapp.weather;

import java.util.Optional;

import com.blackthief.meetapp.meetup.MeetUp;

public interface WeatherService {
	
	Optional<Weather> getByMeetUp(MeetUp meetUp);
	Weather save(Weather weather);
}
