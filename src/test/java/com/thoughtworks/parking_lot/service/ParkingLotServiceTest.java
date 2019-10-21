package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingLotServiceTest {

    @MockBean
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingLotService parkingLotService;

    @Test
    void createParkingLot_should_return_parkinglot() {
        ParkingLot parkingLot = new ParkingLot();
        when(parkingLotRepository.save(any())).thenReturn(parkingLot);
        parkingLot = parkingLotService.createParkingLot(parkingLot);

        assertThat(parkingLot, is(notNullValue()));
    }

    @Test
    void deleteParkingLot_should_return_true_when_deleting_existing_parkinglot() {
        ParkingLot parkingLot = createParkingLot("Alpha");
        when(parkingLotRepository.findById("Alpha")).thenReturn(Optional.of(parkingLot));

        assertThat(parkingLotService.deleteParkingLotByName("Alpha"), is(true));
    }

    private ParkingLot createParkingLot(String name) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        return parkingLot;
    }
}
