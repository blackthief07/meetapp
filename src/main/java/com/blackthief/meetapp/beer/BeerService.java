package com.blackthief.meetapp.beer;

import java.util.Optional;

import com.blackthief.meetapp.meetup.MeetUp;

public interface BeerService {
	
	Optional<Beer> getByMeetUp(MeetUp meetUp);
	Beer save(Beer beer);
	Beer update(Beer beer);
}
