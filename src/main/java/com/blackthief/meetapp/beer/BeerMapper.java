package com.blackthief.meetapp.beer;

public class BeerMapper {
	
	private BeerMapper() {
	}

	public static BeerResponse toResponse(Beer beer) {
		return BeerResponse.builder()
			.cantBeers(beer.getCantBeers())
			.beersPerPackage(beer.getBeersPerPackage())
			.packsToBuy(beer.getPacksToBuy())
			.id(beer.getId())
			.build();
	}
}