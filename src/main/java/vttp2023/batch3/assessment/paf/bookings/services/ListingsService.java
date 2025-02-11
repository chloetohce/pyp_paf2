package vttp2023.batch3.assessment.paf.bookings.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2023.batch3.assessment.paf.bookings.repositories.ListingsRepository;

@Service
public class ListingsService {

	@Autowired
	private ListingsRepository repository;
	
	//TODO: Task 2

	public List<String> getCountries() {
		return repository.getCountries();
	}
	
	//TODO: Task 3

	public List<Accommodation> searchAccoms(String country, int numPerson, double priceMin, double priceMax) {
		return null;
	}

	//TODO: Task 4
	

	//TODO: Task 5


}
