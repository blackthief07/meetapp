package com.blackthief.meetapp.meetup;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blackthief.meetapp.user.UserNotFoundException;
import com.blackthief.meetapp.user.UserRepository;

@Service("meetUpService")
public class MeetUpServiceImpl implements MeetUpService {

	private MeetUpRepository meetUpRepository;
	private UserRepository userRepository;

	@Autowired
	public MeetUpServiceImpl(MeetUpRepository meetUpRepository, UserRepository userRepository) {
		this.meetUpRepository = meetUpRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<MeetUp> getAll() {
		return meetUpRepository.findAll();
	}
	
	@Override
	public Optional<MeetUp> getById(long id) {
		return meetUpRepository.findById(id);
	}

	@Override
	public MeetUp save(MeetUp meetUp) {
		MeetUp meetUpToSave = MeetUp.builder().description(meetUp.getDescription()).date(meetUp.getDate()).maxAttendees(meetUp.getMaxAttendees()).build();

		return meetUpRepository.save(meetUpToSave);
	}
	
	@Override
	public MeetUp update(MeetUp meetUp) {
		return meetUpRepository.save(meetUp);
	}
	
	@Override
	public List<MeetUp> getByUser(long attendeeId) {
		return userRepository
	        .findById(attendeeId)
	        .map(user ->
	        	user.getMeetUpCheckIn()
                .stream()
                .map(checkIns -> checkIns.getMeetUp())
                .collect(Collectors.toList()))
	        .orElseThrow(() -> new UserNotFoundException(attendeeId));
	}
}