package com.blackthief.meetapp.beer;

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

import com.blackthief.meetapp.meeting.Meeting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Beer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beer_generator")
	@SequenceGenerator(name = "beer_generator", initialValue = 1)
	private long id;
	
	@Column(name = "cant_beers")
	private Long cantBeers;
	
	@Column(name = "beers_package")
	private Long beersPerPackage;
	
	@Column(name = "packs_buy")
	private Long packsToBuy;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "BEER_MEETING",
		joinColumns = {
			@JoinColumn(name = "BEER_ID") },
		inverseJoinColumns = {
			@JoinColumn(name = "MEETING_ID") })
	private Meeting meeting;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Beer)) return false;
        Beer beer = (Beer) o;
        return Objects.equals(getId(), beer.getId());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}