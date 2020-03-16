package com.blackthief.meetapp.meetup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Builder
@AllArgsConstructor
public class MeetUpResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String description;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime date;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer maxAttendees;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer neededBeers;
}