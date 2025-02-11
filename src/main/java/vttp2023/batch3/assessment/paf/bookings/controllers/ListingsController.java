package vttp2023.batch3.assessment.paf.bookings.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2023.batch3.assessment.paf.bookings.models.Accommodation;
import vttp2023.batch3.assessment.paf.bookings.services.ListingsService;



@Controller
public class ListingsController {
	@Autowired
	private ListingsService service;


	//TODO: Task 2
	@GetMapping("")
	public String landingPage(Model model) {
		model.addAttribute("countries", service.getCountries());
		return "search";
	}

	@GetMapping("/search")
	public String search(@RequestParam String country, @RequestParam(required=false) Integer numPerson, 
		@RequestParam(required=false) Double priceMin, @RequestParam(required=false) Double priceMax, Model model) {

		List<String> errors = new ArrayList<>();

		if (country == null || country.equals("")) {
			errors.add("Country must be provided.");
		}
		if (numPerson == null || priceMin == null || priceMax == null) {
			errors.add("Fields cannot be empty.");
			model.addAttribute("errors", errors);
			model.addAttribute("countries", service.getCountries());
			return "search";
		}
		
		if (numPerson < 1 || numPerson > 10)
			errors.add("Number of people selected must be between 1 - 10 (inclusive).");
		if (priceMin < 1 || priceMin > 10000)
			errors.add("Min price can only be between 1 - 10000 (inclusive).");
		if (priceMax < 1 || priceMax > 10000)
			errors.add("Max price can only be between 1 - 10000 (inclusive).");
		if (priceMin > priceMax)
			errors.add("Min price cannot be more than max price.");
		
		if (!errors.isEmpty()) {
			model.addAttribute("errors", errors);
			model.addAttribute("countries", service.getCountries());
			return "search";
		}
		
		List<Accommodation> results = service.searchAccoms(country, numPerson, priceMin, priceMax);
		model.addAttribute("country", country);
		model.addAttribute("accomms", results);
		return "results";
	}
	
	
	
	//TODO: Task 3


	//TODO: Task 4
	

	//TODO: Task 5


}
