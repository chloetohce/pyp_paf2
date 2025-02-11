package vttp2023.batch3.assessment.paf.bookings.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Search {
    @NotEmpty
    private String country;

    @Min(value=1, message="Number must be at least 1.")
    @Max(value = 10, message="Number cannot be more than 10.")
    private int numPerson;

    @Min(value=1, message="Number must be at least 1.")
    @Max(value = 10000, message="Number cannot be more than 10000.")
    private int priceMin;

    @Min(value=1, message="Number must be at least 1.")
    @Max(value = 10000, message="Number cannot be more than 10000.")
    private int priceMax;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getNumPerson() {
        return numPerson;
    }

    public void setNumPerson(int numPerson) {
        this.numPerson = numPerson;
    }

    public int getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(int priceMin) {
        this.priceMin = priceMin;
    }

    public int getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(int priceMax) {
        this.priceMax = priceMax;
    }

    
    
}
