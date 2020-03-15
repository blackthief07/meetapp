package com.blackthief.meetapp.weather;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class WeatherNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private LocalDateTime date;

	public WeatherNotFoundException(LocalDateTime date) {
		super("Weather could not be retrieved");
		this.date = date;
	}
}