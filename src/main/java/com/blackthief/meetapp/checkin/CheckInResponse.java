package com.blackthief.meetapp.checkin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class CheckInResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String attendee;
	private Boolean checkedIn;
}