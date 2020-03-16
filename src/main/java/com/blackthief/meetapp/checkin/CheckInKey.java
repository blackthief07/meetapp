package com.blackthief.meetapp.checkin;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckInKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "meetup_id")
    Long meetUpId;
 
    @Column(name = "user_id")
    Long userId;
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckInKey)) return false;
        CheckInKey key = (CheckInKey) o;
        return Objects.equals(getMeetUpId()+getUserId(), key.getMeetUpId()+key.getUserId());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getMeetUpId()+getUserId());
    }
}