package vttp2023.batch3.assessment.paf.bookings.services;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2023.batch3.assessment.paf.bookings.models.Accommodation;
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
		List<Document> documents = repository.search(country, numPerson, priceMin, priceMax);
		
		return documents.stream()
			.map(d -> {
				Accommodation a = new Accommodation();
				a.setId(d.getString("_id"));
				a.setName(d.getString("name"));
				a.setPrice(d.getDouble("price"));
				a.setImageUrl(d.getString("image_url"));
				return a;
			})
			.toList();
	}

	//TODO: Task 4
	

	//TODO: Task 5


}
