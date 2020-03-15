package com.blackthief.meetapp.beer;

import java.util.Optional;

import com.blackthief.meetapp.meeting.Meeting;

public interface BeerService {
	
	Optional<Beer> getByMeeting(Meeting meeting);
	Beer save(Beer beer);
	Beer update(Beer beer);
}
