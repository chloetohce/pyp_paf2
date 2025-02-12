package vttp2023.batch3.assessment.paf.bookings.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import vttp2023.batch3.assessment.paf.bookings.models.Accommodation;
import vttp2023.batch3.assessment.paf.bookings.models.AccommodationDetails;
import vttp2023.batch3.assessment.paf.bookings.models.Booking;
import vttp2023.batch3.assessment.paf.bookings.services.ListingsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class ListingsController {
	@Autowired
	private ListingsService service;


	//TODO: Task 2
	@GetMapping("")
	public String landingPage(HttpSession session, Model model) {
		session.removeAttribute("searchResults");
		session.removeAttribute("country");
		model.addAttribute("countries", service.getCountries());
		return "search";
	}

	//TODO: Task 3
	@GetMapping("/search")
	public String search(@RequestParam(required = false) String country,
			@RequestParam(required = false) Integer numPerson,
			@RequestParam(required = false) Double priceMin, @RequestParam(required = false) Double priceMax,
			HttpSession session,
			Model model) {

		List<Accommodation> results;
		String countryStr;
		if (session.getAttribute("searchResults") != null) {
			results = (List<Accommodation>) session.getAttribute("searchResults");
			countryStr = (String) session.getAttribute("country");
		} else {
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

			results = service.searchAccoms(country, numPerson, priceMin, priceMax);
			countryStr = country;

			session.setAttribute("country", country);
			session.setAttribute("searchResults", results);
		}
		model.addAttribute("country", countryStr);
		model.addAttribute("accomms", results);
		return "results";
	}

	//TODO: Task 4
	@GetMapping("/accommodation/details/{id}")
	public ModelAndView accommodationDetails(@PathVariable String id) {
		ModelAndView mav = new ModelAndView("details");
		try {
			AccommodationDetails details = service.getDetails(id);
			mav.addObject("details", details);
			mav.addObject("booking", new Booking());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			mav.addObject("message", e.getMessage());
		}
		return mav;
	}
	

	//TODO: Task 5
	@PostMapping("/booking/submit/{aid}")
	public ModelAndView submitBooking(@PathVariable String aid, @ModelAttribute Booking booking) throws Exception {
		ModelAndView mav = new ModelAndView("booking");
		booking.setAccId(aid);
		try {
			String resvId = service.bookReservation(booking);
			mav.addObject("resvId", resvId);
			mav.addObject("accId", aid);
			return mav;

		} catch (Exception e) {
			System.out.println(e.getCause() + ": " + e.getMessage());
			mav.addObject("bookingMsg", e.getMessage());
			mav.addObject("details", service.getDetails(aid));
			mav.setViewName("details");
			return mav;
		}

	}
	

}
