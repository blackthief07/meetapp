package com.blackthief.meetapp.meeting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.blackthief.meetapp.user.User;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Meeting {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meeting_generator")
	@SequenceGenerator(name = "meeting_generator", initialValue = 10)
	private long id;

	@Column(length = 50)
	private String description;

	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	  private LocalDateTime date;
	
	@Column(name = "max_attendees")
	private Integer maxAttendees;
	
	@Column(name = "needed_beers", nullable = true)
	private Integer neededBeers;
	
	@Column(name = "weather_temp", nullable = true)
	private Integer weatherTemp;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "MEETING_USERS",
		joinColumns = {
			@JoinColumn(name = "MEETING_ID") },
		inverseJoinColumns = {
			@JoinColumn(name = "USER_ID") })
	private Set<User> attendees;
	
	public void addAttendee(User attendee) {
		this.attendees.add(attendee);
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(getId(), meeting.getId());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}