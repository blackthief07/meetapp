package com.blackthief.meetapp.weather;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.blackthief.meetapp.meetup.MeetUp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Weather {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_generator")
	@SequenceGenerator(name = "weather_generator", initialValue = 1)
	private long id;

	@Column(length = 50)
	private String description;

	@Column(name = "date")
	private LocalDateTime date;
	
	@Column(name = "temp")
	private Double temp;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "MEETUP_WEATHER",
		joinColumns = {
			@JoinColumn(name = "WEATHER_ID") },
		inverseJoinColumns = {
			@JoinColumn(name = "MEETUP_ID") })
	private MeetUp meetUp;
	
	public Weather(WeatherApiResponse apiWeather) {
		this.setDescription(apiWeather.getDaily().getData().get(0).getSummary());
		this.setTemp(apiWeather.getDaily().getData().get(0).getTemperatureMax());
		this.setDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(apiWeather.getDaily().getData().get(0).getTime()), ZoneId.systemDefault()));
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weather)) return false;
        Weather weather = (Weather) o;
        return Objects.equals(getId(), weather.getId());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}