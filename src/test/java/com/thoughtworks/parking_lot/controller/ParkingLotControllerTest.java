package com.thoughtworks.parking_lot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.exception.BadRequestException;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParkingLotController.class)
public class ParkingLotControllerTest {

    private static final Integer PAGE = 0;

    @MockBean
    private ParkingLotService parkingLotService;

    @Autowired
    private MockMvc mvc;

    @Test
    void createParkingLot_should_return_created_parking_lot_and_status_code_201() throws Exception {
        ParkingLot parkingLot = createParkingLot("Alpha", 10);
        when(parkingLotService.createParkingLot(any())).thenReturn(parkingLot);

        ResultActions result = mvc.perform(post("/parkinglots")
                .contentType(APPLICATION_JSON)
                .content(mapToJson(parkingLot)));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Alpha"));
    }

    @Test
    void createParkingLot_should_return_status_code_400_when_capacity_is_negative() throws Exception {
        ParkingLot parkingLot = createParkingLot("Alpha", -1);
        doThrow(BadRequestException.class).when(parkingLotService).createParkingLot(any());

        ResultActions result = mvc.perform(post("/parkinglots")
                .contentType(APPLICATION_JSON)
                .content(mapToJson(parkingLot)));

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void deleteParkingLot_should_return_status_code_200() throws Exception {
        ResultActions result = mvc.perform(delete("/parkinglots/Alpha"));

        result.andExpect(status().isOk());
    }

    @Test
    void deleteParkingLot_should_return_status_code_404_when_not_found() throws Exception {
        doThrow(NotFoundException.class).when(parkingLotService).deleteParkingLotByName(any());

        ResultActions result = mvc.perform(delete("/parkinglots/Alpha"));

        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void getParkingLotByName_should_return_one_parking_lot_by_name_and_status_code_200() throws Exception {
        ParkingLot parkingLot = createParkingLot("Alpha", 10);
        when(parkingLotService.getParkingLotByName("Alpha")).thenReturn(parkingLot);

        ResultActions result = mvc.perform(get("/parkinglots/Alpha"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alpha"));
    }

    @Test
    void getAllParkingLots_should_return_parking_lots_and_status_code_200() throws Exception {
        ParkingLot parkingLot1 = createParkingLot("Alpha", 10);
        ParkingLot parkingLot2 = createParkingLot("Bravo", 10);
        when(parkingLotService.getParkingLots(PAGE)).thenReturn(asList(parkingLot1, parkingLot2));

        ResultActions result = mvc.perform(get("/parkinglots")
                .param("page", PAGE.toString()));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alpha"))
                .andExpect(jsonPath("$[1].name").value("Bravo"));
    }

    @Test
    void updateParkingLotCapacity_should_update_parking_lot_capacity_and_return_updated_parking_lot_and_status_code_200() throws Exception {
        ParkingLot parkingLot = createParkingLot("Alpha", 10);
        parkingLot.setCapacity(50);
        when(parkingLotService.updateParkingLotCapacity("Alpha", 50)).thenReturn(parkingLot);

        ResultActions result = mvc.perform(patch("/parkinglots/Alpha")
                .contentType(APPLICATION_JSON)
                .content(mapToJson(Integer.valueOf("50"))));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.capacity").value(50));
    }

    @Test
    void updateParkingLotCapacity_should_return_status_code_404_when_attempting_to_update_non_existing_parkinglot() throws Exception {
        doThrow(NotFoundException.class).when(parkingLotService).updateParkingLotCapacity("Alpha", 50);

        ResultActions result = mvc.perform(patch("/parkinglots/Alpha")
                .contentType(APPLICATION_JSON)
                .content(mapToJson(Integer.valueOf("50"))));

        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void updateParkingLotCapacity_should_return_status_code_400_when_attempting_to_update_existing_parkinglot_with_negative_capacity() throws Exception {
        doThrow(BadRequestException.class).when(parkingLotService).updateParkingLotCapacity("Alpha", -1);

        ResultActions result = mvc.perform(patch("/parkinglots/Alpha")
                .contentType(APPLICATION_JSON)
                .content(mapToJson(Integer.valueOf("-1"))));

        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()));
    }

    private ParkingLot createParkingLot(String name, Integer capacity) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setCapacity(capacity);
        return parkingLot;
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
