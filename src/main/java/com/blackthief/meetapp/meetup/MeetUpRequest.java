package com.blackthief.meetapp.meetup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetUpRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 1, max = 50, message = "Description should have between 1 and 50 characters")
	private String description;
	
	@NotNull
	@Future(message = "Date should be gretear than today")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime date;
	
	@NotNull
	@Size(min = 1, max = 200, message = "MaxAtendees should be atleast 1 and not greater than 200")
	private Integer maxAttendees;
}