package com.example.cognito.demo.dto.response;

import com.example.cognito.demo.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResponse<T> {
    int code;
    String message;
    T result;

    public JsonResponse(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static <T> JsonResponse<T> success(T result) {
        return new JsonResponse<>(200, null, result);
    }

    public static <T> JsonResponse<T> success(String message) {
        return new JsonResponse<>(200, message, null);
    }

    public static <T> JsonResponse<T> success(String message, T result) {
        return new JsonResponse<>(200, message, result);
    }

    public static <T> JsonResponse<T> error(ErrorCode errorCode) {
        return new JsonResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }
}
