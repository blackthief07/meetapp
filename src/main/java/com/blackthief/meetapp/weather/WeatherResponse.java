package com.blackthief.meetapp.weather;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WeatherResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String description;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDateTime date;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Double temp;
}
