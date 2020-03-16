package com.blackthief.meetapp.beer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.blackthief.meetapp.meetup.MeetUp;

@Service("beerService")
public class BeerServiceImpl implements BeerService {
	
	private BeerRepository beerRepository;
	
	@Value( "${beer.package.quantity}" )
	private Long packageQuantity;
	
	@Value( "${beer.maxtemp.perperson}" )
	private Long additionalPerPerson;
	
	@Value( "${beer.deftemp.perperson}" )
	private Long qtyPerPerson;
	
	@Value( "${beer.deftemp}" )
	private Double maxTemp;

	@Autowired
	public BeerServiceImpl(BeerRepository beerRepository) {
		this.beerRepository = beerRepository;
	}
	
	@Override
	public Optional<Beer> getByMeetUp(MeetUp meetUp) {
		Beer retVal;
		
		try {
			final boolean addBeers = Double.compare(meetUp.getWeather().getTemp(), maxTemp) > 0;
			final Long cantAttendees = (long) ((meetUp.getMeetUpCheckIns().size() > 0) ? meetUp.getMeetUpCheckIns().size() : meetUp.getMaxAttendees());
			final Long cantBeers = cantAttendees * (qtyPerPerson + (addBeers ? additionalPerPerson : 0));
			final Long packsToBuy = (long) Math.ceil(cantBeers*1.0/packageQuantity);
			
			retVal = Beer.builder().cantBeers(cantBeers).beersPerPackage(packageQuantity).packsToBuy(packsToBuy).build();
			retVal.setMeetUp(meetUp);
			
			return Optional.ofNullable(retVal);
			
		} catch (Exception e) {
			retVal = null;
		}
		
		return Optional.ofNullable(retVal);
	}
	
	@Override
	public Beer save(Beer beer) {
		Beer beerToSave = Beer.builder().cantBeers(beer.getCantBeers()).beersPerPackage(beer.getBeersPerPackage()).packsToBuy(beer.getPacksToBuy()).meetUp(beer.getMeetUp()).build();

		return beerRepository.save(beerToSave);
	}
	
	@Override
	public Beer update(Beer beer) {
		return beerRepository.save(beer);
	}
}