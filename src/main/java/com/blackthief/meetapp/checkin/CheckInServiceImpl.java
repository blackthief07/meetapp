package com.blackthief.meetapp.checkin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("checkInService")
public class CheckInServiceImpl implements CheckInService {

	private CheckInRepository checkInRepository;

	@Autowired
	public CheckInServiceImpl(CheckInRepository checkInRepository) {
		this.checkInRepository = checkInRepository;
	}
	
	@Override
	public CheckIn update(CheckIn checkIn) {
		return checkInRepository.save(checkIn);
	}
}