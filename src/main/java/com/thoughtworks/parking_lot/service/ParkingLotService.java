package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public ParkingLot createParkingLot(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    public boolean deleteParkingLot(ParkingLot parkingLot) {
        return false;
    }

    public ParkingLot getParkingLotByName(String name) {
        return null;
    }

    public List<ParkingLot> getParkingLots(int page) {
        return new ArrayList<>();
    }

    public ParkingLot updateParkingLotCapacity(String name, int capacity) {
        return null;
    }
}
