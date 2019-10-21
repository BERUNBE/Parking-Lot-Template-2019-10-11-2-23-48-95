package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.exception.BadRequestException;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void createParkingLot_should_return_parkinglot() throws BadRequestException {
        ParkingLot parkingLot = createParkingLot("Alpha", 10);
        when(parkingLotRepository.save(any())).thenReturn(parkingLot);
        parkingLot = parkingLotService.createParkingLot(parkingLot);

        assertThat(parkingLot, is(notNullValue()));
    }

    @Test
    void createParkingLot_should_throw_BadRequestException_when_creating_parking_lot_with_negative_capacity() {
        ParkingLot parkingLot = createParkingLot("Alpha", -1);
        parkingLot.setCapacity(-10);
        when(parkingLotRepository.save(any())).thenReturn(parkingLot);

        assertThrows(BadRequestException.class, () -> parkingLotService.createParkingLot(parkingLot));
    }

    @Test
    void deleteParkingLot_should_not_return_exception_deleting_existing_parkinglot() throws NotFoundException {
        ParkingLot parkingLot = createParkingLot("Alpha", 10);
        when(parkingLotRepository.findById("Alpha")).thenReturn(Optional.of(parkingLot));

        parkingLotService.deleteParkingLotByName("Alpha");
    }

    @Test
    void deleteParkingLot_should_throw_NotFoundException_when_attempting_to_delete_non_existing_parkinglot() {
        when(parkingLotRepository.findById("Alpha")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> parkingLotService.deleteParkingLotByName("Alpha"));
    }

    @Test
    void getParkingLotByName_should_return_one_parking_lot_by_name() {
        ParkingLot parkingLot = createParkingLot("Alpha", 10);
        when(parkingLotRepository.findById("Alpha")).thenReturn(Optional.of(parkingLot));

        assertThat(parkingLotService.getParkingLotByName("Alpha"), is(parkingLot));
    }

    @Test
    void getParkingLots_should_return_page_of_parking_lots() {
        ParkingLot parkingLot1 = createParkingLot("Alpha", 10);
        ParkingLot parkingLot2 = createParkingLot("Bravo", 10);
        Page<ParkingLot> page = new PageImpl<>(Arrays.asList(parkingLot1, parkingLot2));
        when(parkingLotRepository.findAll(PageRequest.of(PAGE, PAGE_SIZE))).thenReturn(page);

        assertThat(parkingLotService.getParkingLots(PAGE), is(page));
    }

    @Test
    void updateParkingLotCapacity_should_return_updated_parking_lot() throws NotFoundException, BadRequestException {
        ParkingLot parkingLotToUpdate = createParkingLot("Alpha", 10);
        when(parkingLotRepository.findById("Alpha")).thenReturn(Optional.of(parkingLotToUpdate));
        ParkingLot updatedParkingLot = parkingLotService.updateParkingLotCapacity("Alpha", 20);

        assertThat(updatedParkingLot.getCapacity(), is(20));
    }

    @Test
    void updateParkingLotCapacity_should_throw_NotFoundException_when_attempting_to_update_non_existing_parking_lot() {
        when(parkingLotRepository.findById("Alpha")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> parkingLotService.updateParkingLotCapacity("Alpha", 20));
    }

    @Test
    void updateParkingLotCapacity_should_throw_BadRequestException_when_attempting_to_update_existing_parkinglot_with_negative_capacity() {
        when(parkingLotRepository.findById("Alpha")).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> parkingLotService.updateParkingLotCapacity("Alpha", -1));
    }

    private ParkingLot createParkingLot(String name, Integer capacity) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setCapacity(capacity);
        return parkingLot;
    }
}
