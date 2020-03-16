package com.blackthief.meetapp.meetup;

import java.util.List;
import java.util.Optional;

public interface MeetUpService {
	
	List<MeetUp> getAll();
	Optional<MeetUp> getById(long id);
	MeetUp save(MeetUp meetUp);
	MeetUp update(MeetUp meetUp);
	List<MeetUp> getByUser(long attendeeId);
}