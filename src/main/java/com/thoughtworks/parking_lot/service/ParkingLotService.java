package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingLotService {

    private static final int PAGE_SIZE = 15;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public ParkingLot createParkingLot(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    public boolean deleteParkingLotByName(String name) {
        Optional<ParkingLot> parkingLotToDelete = parkingLotRepository.findById(name);
        if (parkingLotToDelete.isPresent()) {
            parkingLotRepository.delete(parkingLotToDelete.get());
            return true;
        } else {
            return false;
        }
    }

    public ParkingLot getParkingLotByName(String name) {
        return parkingLotRepository.findById(name).get();
    }

    public Iterable<ParkingLot> getParkingLots(int page) {
        return parkingLotRepository.findAll(PageRequest.of(page, PAGE_SIZE));
    }

    public ParkingLot updateParkingLotCapacity(String name, int capacity) {
        Optional<ParkingLot> parkingLotToUpdate = parkingLotRepository.findById(name);
        if (parkingLotToUpdate.isPresent()) {
            parkingLotToUpdate.get().setCapacity(capacity);
            return parkingLotToUpdate.get();
        } else {
            return null;
        }
    }
}
