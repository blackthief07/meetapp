package com.blackthief.meetapp.meeting;

import java.util.List;
import java.util.Optional;

public interface MeetingService {
	
	List<Meeting> getAll();
	Optional<Meeting> getById(long id);
	Meeting save(Meeting meeting);
	Meeting update(Meeting meeting);
	List<Meeting> getByUser(long attendeeId);
}