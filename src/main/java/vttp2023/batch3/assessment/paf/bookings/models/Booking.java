package vttp2023.batch3.assessment.paf.bookings.models;

import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

public class Booking {
    private String resvId;
    
    private String name;

    private String email;

    private String accId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date arrival;

    private int duration;

    public Booking() {
        this.resvId = UUID.randomUUID().toString().substring(0, 8);
    }

    public String getResvId() {
        return resvId;
    }

    public void setResvId(String resv_id) {
        this.resvId = resv_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String acc_id) {
        this.accId = acc_id;
    }

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Booking [resvId=" + resvId + ", name=" + name + ", email=" + email + ", accId=" + accId + ", arrival="
                + arrival + ", duration=" + duration + "]";
    }

    
}
