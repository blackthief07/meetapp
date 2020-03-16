package com.blackthief.meetapp.meetup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetUpRepository extends JpaRepository<MeetUp, Long> {
	List<MeetUp> getByMeetUpCheckIns(long userId);
}