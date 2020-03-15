package com.blackthief.meetapp.beer;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BeerResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private Long cantBeers;
	
	private Long beersPerPackage;
	
	private Long packsToBuy;
}