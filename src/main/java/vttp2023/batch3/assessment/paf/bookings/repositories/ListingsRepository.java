package vttp2023.batch3.assessment.paf.bookings.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.assessment.paf.bookings.models.Booking;

@Repository
public class ListingsRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	//TODO: Task 2

	/*
	 * 	db.listings.distinct('address.country')
	 */
	public List<String> getCountries() {
		return mongoTemplate.findDistinct(new Query(), "address.country", "listings", String.class);
	}

	//TODO: Task 3

	/*
	 * 	db.listings.find(
	 * 		{$and: [
	 * 			{'address.country': <country>},
	 * 			{'accommodates': {$gte: <numPerson>}},
	 * 			{'price': {$lte: <priceMax>, $gte: <priceMin}}
	 * 		]},
	 * 		{_id: 1, name: 1, price: 1, 'image_url': '$images.picture_url'}
	 * 	)
	 */
	public List<Document> search(String country, int numPerson, double priceMin, double priceMax) {
		// Query query = Query.query(Criteria.where("address.country")
		// 		.is(search.getCountry())
		// 	.and("accommodates")
		// 		.gte(search.getNumPerson())
		// 	.and("price")
		// 		.gte(search.getPriceMin())
		// 		.lte(search.getPriceMax()));
				
		MatchOperation match = Aggregation.match(Criteria.where("address.country")
				.is(country)
			.and("accommodates")
				.gte(numPerson)
			.and("price")
				.gte(priceMin)
				.lte(priceMax));
		ProjectionOperation project = Aggregation.project("_id", "name", "price")
			.and("images.picture_url").as("image_url");
		SortOperation sortPrice = Aggregation.sort(Sort.by(Sort.Direction.DESC, "price"));
		Aggregation pipeline = Aggregation.newAggregation(match, project, sortPrice);
				
		return mongoTemplate.aggregate(pipeline, "listings", Document.class).getMappedResults();
	}
	

	//TODO: Task 4
	/*
	 * 	db.listings.find(
	 * 		{_id: <id>},
	 * 		{_id:1, description:1, 'address.street':1, 'address.suburb':1, 'address.country':1, 
	 * 		'image_url':'$images.picture_url', price:1, amenities:1}
	 * 	)
	 */
	public Optional<Document> getAccomDetails(String id) {
		Criteria criteria = Criteria.where("_id").is(id);
		Query q = Query.query(criteria);
		q.fields()
			.include("_id", "description", "address.street", "address.suburb", "address.country", "price", "amenities", "images.picture_url");
		return Optional.ofNullable(mongoTemplate.findOne(q, Document.class, 
			"listings"));
	}

	//TODO: Task 5
	/*
	 * 	insert into reservations 
	 * 	values(resv_id, name, email, acc_id, arrival, duration)
	 */
	public void reserveBooking(Booking booking) {
		String SQL_RESERVATION = "insert into reservations values(?, ?, ?, ?, ?, ?)";

		jdbcTemplate.update(SQL_RESERVATION, 
			booking.getResvId(), booking.getName(), booking.getEmail(), booking.getAccId(), 
			booking.getArrival(), booking.getDuration());
		
	}

	/*
	 * 	update acc_occupancy
	 * 	set vacancy = vacancy - ?
	 * 	where acc_id = ?;
	 */
	public void updateVacancy(String id, int duration) {
		String SQL_UPDATE_VACANCY = "update acc_occupancy set vacancy = vacancy - ? where acc_id = ?";
		jdbcTemplate.update(SQL_UPDATE_VACANCY, duration, id); // Throws Uncategorized SQL Exception
	}


}
