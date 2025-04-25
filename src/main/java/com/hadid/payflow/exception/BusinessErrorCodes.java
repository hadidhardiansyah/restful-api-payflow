package com.hadid.payflow.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessErrorCodes {

    COMPANY_ID_ALREADY_EXISTS(1001, HttpStatus.CONFLICT, "Company ID already exists"),
    COMPANY_ID_NOT_FOUND(1002, HttpStatus.NOT_FOUND, "Company ID not found"),

    USERNAME_ALREADY_EXISTS(2001, HttpStatus.CONFLICT, "Username already exists"),

    ROLE_NOT_FOUND(3001, HttpStatus.NOT_FOUND, "Role not found"),

    INVALID_ACTIVATION_CODE(4001, HttpStatus.BAD_REQUEST, "Invalid activation code"),
    EXPIRED_ACTIVATION_CODE(4002, HttpStatus.BAD_REQUEST, "Expired activation code"),

    USER_NOT_FOUND(5001, HttpStatus.NOT_FOUND, "User not found"),
    ACCOUNT_NOT_ACTIVATED(5002, HttpStatus.FORBIDDEN, "Account not activated"),
    ACCOUNT_ALREADY_ACTIVATED(5003, HttpStatus.CONFLICT, "Account already activated"),

    INVALID_CREDENTIALS(5001, HttpStatus.BAD_REQUEST, "Invalid companyId or username or password");

    private final int code;

    private final String description;

    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
