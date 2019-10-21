package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkinglots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<ParkingLot> createParkingLot(@RequestBody ParkingLot parkingLot) {
        return new ResponseEntity<>(parkingLotService.createParkingLot(parkingLot), HttpStatus.CREATED);
    }

    @DeleteMapping(produces = {"application/json"})
    public ResponseEntity<Void> deleteParkingLot(@RequestBody ParkingLot parkingLot) {
        if (parkingLotService.deleteParkingLot(parkingLot)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{name}", produces = {"application/json"})
    public ResponseEntity<ParkingLot> getParkingLotByName(@PathVariable String name) {
        return new ResponseEntity<>(parkingLotService.getParkingLotByName(name), HttpStatus.OK);
    }

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<ParkingLot>> getParkingLots(@RequestParam(required = false, defaultValue = "0") int page) {
        return new ResponseEntity<>(parkingLotService.getParkingLots(page), HttpStatus.OK);
    }
}
