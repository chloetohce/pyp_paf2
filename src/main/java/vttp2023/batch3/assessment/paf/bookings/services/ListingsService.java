package vttp2023.batch3.assessment.paf.bookings.services;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp2023.batch3.assessment.paf.bookings.models.Accommodation;
import vttp2023.batch3.assessment.paf.bookings.models.AccommodationDetails;
import vttp2023.batch3.assessment.paf.bookings.models.Booking;
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
	public AccommodationDetails getDetails(String id) throws Exception {
		Optional<Document> opt = repository.getAccomDetails(id);
		Document d = opt.orElseThrow(() -> new Exception("Accommodation with id %s not found.".formatted(id)));

		AccommodationDetails details = new AccommodationDetails();
		details.setId(d.getString("_id"));
		details.setDescription(d.getString("description"));
		details.setStreet(d.getEmbedded(List.of("address", "street"), String.class));
		details.setSuburb(d.getEmbedded(List.of("address", "suburb"), String.class));
		details.setCountry(d.getEmbedded(List.of("address", "country"), String.class));
		details.setPrice(d.getDouble("price"));
		details.setAmenities(d.getList("amenities", String.class));
		details.setImageUrl(d.getEmbedded(List.of("images", "picture_url"), String.class));
		return details;
	}

	//TODO: Task 5
	@Transactional
	public String bookReservation(Booking booking) throws Exception {
		repository.reserveBooking(booking);
		try {
			repository.updateVacancy(booking.getAccId(), booking.getDuration());
			
			return booking.getResvId();
		} catch (UncategorizedSQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Duration of stay cannot be more than the vacancy.");
		} catch (DataAccessException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

}
