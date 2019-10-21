package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.exception.BadRequestException;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkinglots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<ParkingLot> createParkingLot(@RequestBody ParkingLot parkingLot) throws BadRequestException {
        return new ResponseEntity<>(parkingLotService.createParkingLot(parkingLot), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{name}", produces = {"application/json"})
    public ResponseEntity<Void> deleteParkingLot(@PathVariable String name) throws NotFoundException {
        parkingLotService.deleteParkingLotByName(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{name}", produces = {"application/json"})
    public ResponseEntity<ParkingLot> getParkingLotByName(@PathVariable String name) {
        return new ResponseEntity<>(parkingLotService.getParkingLotByName(name), HttpStatus.OK);
    }

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<Iterable<ParkingLot>> getParkingLots(@RequestParam(required = false, defaultValue = "0") int page) {
        return new ResponseEntity<>(parkingLotService.getParkingLots(page), HttpStatus.OK);
    }

    @PatchMapping(path = "/{name}", produces = {"application/json"})
    public ResponseEntity<ParkingLot> updateParkingLotCapacity(@PathVariable String name, @RequestBody int capacity) throws NotFoundException, BadRequestException {
        ParkingLot updatedParkingLot = parkingLotService.updateParkingLotCapacity(name, capacity);
        return new ResponseEntity<>(updatedParkingLot, HttpStatus.OK);
    }
}
