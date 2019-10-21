package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.exception.BadRequestException;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingLotService {

    private static final int PAGE_SIZE = 15;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public ParkingLot createParkingLot(ParkingLot parkingLot) throws BadRequestException {
        if (parkingLot.getCapacity() < 0) {
            throw new BadRequestException("Invalid capacity.");
        }
        return parkingLotRepository.save(parkingLot);
    }

    public void deleteParkingLotByName(String name) throws NotFoundException {
        Optional<ParkingLot> parkingLotToDelete = parkingLotRepository.findById(name);
        if (parkingLotToDelete.isPresent()) {
            parkingLotRepository.delete(parkingLotToDelete.get());
        } else {
            throw new NotFoundException("Parking lot with name: '" + name + "' not found.");
        }
    }

    public ParkingLot getParkingLotByName(String name) {
        return parkingLotRepository.findById(name).get();
    }

    public Iterable<ParkingLot> getParkingLots(int page) {
        return parkingLotRepository.findAll(PageRequest.of(page, PAGE_SIZE));
    }

    public ParkingLot updateParkingLotCapacity(String name, int capacity) throws NotFoundException, BadRequestException {
        if (capacity < 0) {
            throw new BadRequestException("Invalid capacity.");
        }
        Optional<ParkingLot> parkingLotToUpdate = parkingLotRepository.findById(name);
        if (parkingLotToUpdate.isPresent()) {
            parkingLotToUpdate.get().setCapacity(capacity);
            return parkingLotToUpdate.get();
        } else {
            throw new NotFoundException("Parking lot with name: '" + name + "' not found.");
        }
    }
}
