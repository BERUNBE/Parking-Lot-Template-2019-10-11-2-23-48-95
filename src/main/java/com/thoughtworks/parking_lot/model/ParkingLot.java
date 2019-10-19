package com.thoughtworks.parking_lot.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ParkingLot {

    @Id
    private String name;

    private Integer capacity;

    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
