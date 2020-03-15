package com.blackthief.meetapp.meeting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blackthief.meetapp.user.UserNotFoundException;
import com.blackthief.meetapp.user.UserRepository;

@Service("meetingService")
public class MeetingServiceImpl implements MeetingService {

	private MeetingRepository meetingRepository;
	private UserRepository userRepository;

	@Autowired
	public MeetingServiceImpl(MeetingRepository meetingRepository, UserRepository userRepository) {
		this.meetingRepository = meetingRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<Meeting> getAll() {
		return meetingRepository.findAll();
	}
	
	@Override
	public Optional<Meeting> getById(long id) {
		return meetingRepository.findById(id);
	}

	@Override
	public Meeting save(Meeting meeting) {
		Meeting meetingToSave = Meeting.builder().description(meeting.getDescription()).date(meeting.getDate()).maxAttendees(meeting.getMaxAttendees()).build();

		return meetingRepository.save(meetingToSave);
	}
	
	@Override
	public Meeting update(Meeting meeting) {
		return meetingRepository.save(meeting);
	}
	
	@Override
	public List<Meeting> getByUser(long attendeeId) {
		return userRepository
	        .findById(attendeeId)
	        .map(user ->
	        	user.getMeetings()
                .stream()
                .collect(Collectors.toList()))
	        .orElseThrow(() -> new UserNotFoundException(attendeeId));
	}
}