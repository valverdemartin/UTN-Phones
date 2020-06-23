package edu.utn.TpFinal.controller.web;


import edu.utn.TpFinal.Exceptions.*;
import edu.utn.TpFinal.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.text.ParseException;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponseDto handleValidationException(ValidationException exc) {
        return new ErrorResponseDto(2, exc.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotExists.class)
    public ErrorResponseDto handleUserNotExists() {
        return new ErrorResponseDto(3, "User not exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClientNotExists.class)
    public ErrorResponseDto handleClientNotExists() {
        return new ErrorResponseDto(4, "Client not exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LineNotExists.class)
    public ErrorResponseDto handleLineNotExists() {
        return new ErrorResponseDto(5, "Line not exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CityNotExists.class)
    public ErrorResponseDto handleCityNotExists() {
        return new ErrorResponseDto(6, "City not exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParseException.class)
    public ErrorResponseDto handleParseException() {
        return new ErrorResponseDto(7, "Not valid dates");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CallAlreadyExists.class)
    public ErrorResponseDto handleCallAlreadyExists() {
        return new ErrorResponseDto(8, "Call already exists");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CityPrefixAlreadyExists.class)
    public ErrorResponseDto handleCityPrefixAlreadyExists() {
        return new ErrorResponseDto(9, "Prefix already exists");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ProvinceIsNotEmpty.class)
    public ErrorResponseDto handleProvinceIsNotEmpty() {
        return new ErrorResponseDto(10, "Province is not empty");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserDniAlreadyExist.class)
    public ErrorResponseDto handleUserDniAlreadyExist() {
        return new ErrorResponseDto(11, "DNI Already in use");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserNameAlreadyExist.class)
    public ErrorResponseDto handleUserNameAlreadyExist() {
        return new ErrorResponseDto(12, "User Name Already in use");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyDeleted.class)
    public ErrorResponseDto handleUserAlreadyDeleted() {
        return new ErrorResponseDto(13, "User Already deleted");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyActive.class)
    public ErrorResponseDto handleUserAlreadyActive() {
        return new ErrorResponseDto(14, "User Already active");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(DeletionNotAllowed.class)
    public ErrorResponseDto handleDeletionNotAllowed() {
        return new ErrorResponseDto(15, "You cannot delete the record");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InvalidPrefix.class)
    public ErrorResponseDto handleInvalidPrefix() {
        return new ErrorResponseDto(16, "Invalid Prefix");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InvalidStatus.class)
    public ErrorResponseDto handleInvalidStatus() {
        return new ErrorResponseDto(17, "Invalid Status");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InvalidType.class)
    public ErrorResponseDto handleInvalidType() {
        return new ErrorResponseDto(18, "Invalid type");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InvalidPhoneNumber.class)
    public ErrorResponseDto handleInvalidPhoneNumber() {
        return new ErrorResponseDto(19, "Invalid Phone Number");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CityNameAlreadyExists.class)
    public ErrorResponseDto handleCityNameAlreadyExists() {
        return new ErrorResponseDto(20, "City Name already exists");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CityShortNameAlreadyExists.class)
    public ErrorResponseDto handleCityShortNameAlreadyExists() {
        return new ErrorResponseDto(20, "City Short Name already exists");
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ProvinceNotExist.class)
    public ErrorResponseDto handleProvinceNotExist() {
        return new ErrorResponseDto(21, "Province Not exists");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidLoginException.class)
    public ErrorResponseDto handleLoginException() {
        return new ErrorResponseDto(22, "Invalid login");
    }



}
