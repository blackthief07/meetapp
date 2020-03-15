package com.blackthief.meetapp.weather;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Future(message = "Date should be gretear than today")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime date;
}