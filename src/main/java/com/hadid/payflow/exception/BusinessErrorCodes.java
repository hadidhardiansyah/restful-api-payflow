package com.hadid.payflow.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessErrorCodes {

    COMPANY_ID_ALREADY_EXISTS(1001, HttpStatus.CONFLICT, "Company ID already exists");

    private final int code;

    private final String description;

    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
