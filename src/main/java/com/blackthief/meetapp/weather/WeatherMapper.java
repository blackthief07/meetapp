package com.blackthief.meetapp.weather;

public class WeatherMapper {
	
	private WeatherMapper() {
	}

	public static WeatherResponse toResponse(Weather weather) {
		return WeatherResponse.builder()
				.description(weather.getDescription())
				.date(weather.getDate())
				.temp(weather.getTemp())
				.id(weather.getId())
				.build();
	}

	public static Weather toDomain(WeatherRequest weatherRequest) {
		return Weather.builder()
				.date(weatherRequest.getDate())
				.build();
	}
}
