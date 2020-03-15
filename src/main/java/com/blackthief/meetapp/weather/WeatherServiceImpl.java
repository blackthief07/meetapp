package com.blackthief.meetapp.weather;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.blackthief.meetapp.meeting.Meeting;
import com.fasterxml.jackson.databind.ObjectMapper;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

@Service("weatherService")
public class WeatherServiceImpl implements WeatherService {

	private WeatherRepository weatherRepository;
	
	@Value( "${weather.api.latitude}" )
	private String apiLatitude;
	
	@Value( "${weather.api.longitude}" )
	private String apiLongitude;
	
	@Value( "${weather.api.key}" )
	private String apiKey;

	@Autowired
	public WeatherServiceImpl(WeatherRepository weatherRepository) {
		this.weatherRepository = weatherRepository;
	}
	
	@Override
	public Optional<Weather> getByMeeting(Meeting meeting) {
		final LocalDateTime date = meeting.getDate();
		ObjectMapper objectMapper = new ObjectMapper();
		Weather retVal;
		
		try {
			HttpResponse<String> response = Unirest.get(String.format("https://api.darksky.net/forecast/%s/%s,%s,%s?exclude=currently,minutely,hourly,alerts,flags&units=ca",
					this.apiKey, this.apiLatitude, this.apiLongitude, date.atZone(ZoneId.systemDefault()).toEpochSecond()))
					.header("x-rapidapi-host", "dark-sky.p.rapidapi.com")
					.header("x-rapidapi-key", this.apiKey)
					.asString();
			
			WeatherApiResponse apiValue = objectMapper.readValue(response.getBody(), WeatherApiResponse.class);
			retVal = new Weather(apiValue);
			retVal.setMeeting(meeting);
			
			return Optional.ofNullable(this.save(retVal));
			
		} catch (Exception e) {
			retVal = null;
		}
		
		return Optional.ofNullable(retVal);
	}
	
	@Override
	public Weather save(Weather weather) {
		Weather weatherToSave = Weather.builder().description(weather.getDescription()).date(weather.getDate()).temp(weather.getTemp()).meeting(weather.getMeeting()).build();

		return weatherRepository.save(weatherToSave);
	}
}
