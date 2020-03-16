package com.blackthief.meetapp.checkin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepository extends JpaRepository<CheckIn, CheckInKey> {

}
