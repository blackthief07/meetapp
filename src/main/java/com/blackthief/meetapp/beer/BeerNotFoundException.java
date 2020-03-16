package com.blackthief.meetapp.beer;

import lombok.Getter;

@Getter
public class BeerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final Long id;

	public BeerNotFoundException(final long id) {
		super("Beers could not be calculated for meetup: " + id);
		this.id = id;
	}
}