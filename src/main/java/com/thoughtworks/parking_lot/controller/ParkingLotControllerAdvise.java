package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.exception.BadRequestException;
import com.thoughtworks.parking_lot.model.CustomError;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ParkingLotControllerAdvise {

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public CustomError handleNotFoundException(NotFoundException e) {
        CustomError customError = new CustomError();
        customError.setErrorMessage(e.getMessage());
        customError.setCode(HttpStatus.NOT_FOUND.value());
        return customError;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public CustomError handleBadRequestException(BadRequestException e) {
        CustomError customError = new CustomError();
        customError.setErrorMessage(e.getMessage());
        customError.setCode(HttpStatus.NOT_FOUND.value());
        return customError;
    }
}