package com.hadid.payflow.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessErrorCodes {

    COMPANY_ID_ALREADY_EXISTS(1001, HttpStatus.CONFLICT, "Company ID already exists"),
    COMPANY_ID_NOT_FOUND(1002, HttpStatus.NOT_FOUND, "Company ID not found"),
    USERNAME_ALREADY_EXISTS(1003, HttpStatus.CONFLICT, "Username already exists"),
    ROLE_NOT_FOUND(1004, HttpStatus.NOT_FOUND, "Role not found"),
    INVALID_CREDENTIALS(1005, HttpStatus.BAD_REQUEST, "Invalid companyId or username or password"),
    ACCOUNT_NOT_ACTIVATED(1006, HttpStatus.FORBIDDEN, "Account not activated");

    private final int code;

    private final String description;

    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
