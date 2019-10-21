package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingLotServiceTest {

    private static final int PAGE = 0;
    private static final int PAGE_SIZE = 15;

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

    @Test
    void deleteParkingLot_should_return_false_when_attempting_to_delete_non_existing_parkinglot() {
        when(parkingLotRepository.findById("Alpha")).thenReturn(Optional.empty());

        assertThat(parkingLotService.deleteParkingLotByName("Alpha"), is(false));
    }

    @Test
    void getParkingLotByName_should_return_one_parking_lot_by_name() {
        ParkingLot parkingLot = createParkingLot("Alpha");
        when(parkingLotRepository.findById("Alpha")).thenReturn(Optional.of(parkingLot));

        assertThat(parkingLotService.getParkingLotByName("Alpha"), is(parkingLot));
    }

    @Test
    void getParkingLots_should_return_page_of_parking_lots() {
        ParkingLot parkingLot1 = createParkingLot("Alpha");
        ParkingLot parkingLot2 = createParkingLot("Bravo");
        Page<ParkingLot> page = new PageImpl<>(Arrays.asList(parkingLot1, parkingLot2));
        when(parkingLotRepository.findAll(PageRequest.of(PAGE, PAGE_SIZE))).thenReturn(page);

        assertThat(parkingLotService.getParkingLots(PAGE), is(page));
    }

    private ParkingLot createParkingLot(String name) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        return parkingLot;
    }
}
