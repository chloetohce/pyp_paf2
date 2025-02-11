package vttp2023.batch3.assessment.paf.bookings.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.assessment.paf.bookings.models.Search;

@Repository
public class ListingsRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	//TODO: Task 2

	/*
	 * 	db.listings.distinct('address.country')
	 */
	public List<String> getCountries() {
		return mongoTemplate.findDistinct(new Query(), "address.country", "listings", String.class);
	}

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
	public List<Document> search(Search search) {
		// Query query = Query.query(Criteria.where("address.country")
		// 		.is(search.getCountry())
		// 	.and("accommodates")
		// 		.gte(search.getNumPerson())
		// 	.and("price")
		// 		.gte(search.getPriceMin())
		// 		.lte(search.getPriceMax()));
				
		MatchOperation match = Aggregation.match(Criteria.where("address.country")
				.is(search.getCountry())
			.and("accommodates")
				.gte(search.getNumPerson())
			.and("price")
				.gte(search.getPriceMin())
				.lte(search.getPriceMax()));
		ProjectionOperation project = Aggregation.project("_id", "name", "price")
			.and("images.picture_url").as("image_url");
		Aggregation pipeline = Aggregation.newAggregation(match, project);
				
		return mongoTemplate.aggregate(pipeline, "listings", Document.class).getMappedResults();
	}
	
	//TODO: Task 3

	//TODO: Task 4
	

	//TODO: Task 5


}
