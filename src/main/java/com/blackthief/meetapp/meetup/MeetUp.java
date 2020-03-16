package com.blackthief.meetapp.meetup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.blackthief.meetapp.beer.Beer;
import com.blackthief.meetapp.checkin.CheckIn;
import com.blackthief.meetapp.checkin.CheckInKey;
import com.blackthief.meetapp.user.User;
import com.blackthief.meetapp.weather.Weather;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MeetUp {

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
	
	@OneToMany(mappedBy = "meetUp", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<CheckIn> meetUpCheckIns;
	
	@OneToOne(mappedBy = "meetUp")
	private Weather weather;
	
	@OneToOne(mappedBy = "meetUp")
	private Beer beer;
	
	public void addAttendee(User attendee) {
		final CheckIn meetUpCheckIn = CheckIn.builder().attendee(attendee).checkedIn(false).meetUp(this).id(new CheckInKey(this.getId(), attendee.getId())).build();
		
		this.meetUpCheckIns.add(meetUpCheckIn);
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeetUp)) return false;
        MeetUp meeting = (MeetUp) o;
        return Objects.equals(getId(), meeting.getId());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}