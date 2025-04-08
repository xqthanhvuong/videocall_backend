package com.example.cognito.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1000, "Uncategorized exception", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1001, "User already existed", HttpStatus.CONFLICT),
    USERNAME_INVALID(1002, "Invalid email format.", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003,
            "Password must be 8-20 characters long and contain at least one lowercase letter, one uppercase letter, one digit, and one special character.",
            HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1004, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1005, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    NAME_INVALID(1006, "Name must be 2-50 characters long and not be blank", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED(1007, "You do not have permission.", HttpStatus.FORBIDDEN),
    METHOD_NOT_ALLOWED(1008, "Method not allowed.", HttpStatus.METHOD_NOT_ALLOWED),
    TOKEN_INVALID(1009, "Token invalid", HttpStatus.UNAUTHORIZED),
    ;


    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    // if err key wrong return code 1000
    public static ErrorCode getError(String errKey) {
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try {
            errorCode = ErrorCode.valueOf(errKey);
        } catch (IllegalArgumentException ignored) {

        }
        return errorCode;
    }

}

