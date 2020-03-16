package com.blackthief.meetapp.checkin;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.blackthief.meetapp.meetup.MeetUp;
import com.blackthief.meetapp.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckIn {
 
    @EmbeddedId
    private CheckInKey id;
 
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("meetup_id")
    @JoinColumn(name = "meetup_id")
    private MeetUp meetUp;
 
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User attendee;

    @Column(name = "checked_in")
    private Boolean checkedIn;
}