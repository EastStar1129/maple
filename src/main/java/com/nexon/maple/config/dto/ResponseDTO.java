package com.nexon.maple.config.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseDTO<T> {
    private String code;
    private String message;
    private T data;

    private static final String CODE_SUCCESS = "SUCCESS";
    private static final String CODE_FAIL = "FAIL";

    public static <T> ResponseDTO<T> ofFail(String message) {
        return new ResponseDTO<>(CODE_FAIL, message, null);
    }

    public static <T> ResponseDTO<T> ofFail(String message, T data) {
        return new ResponseDTO<>(CODE_FAIL, message, data);
    }

    public static <T> ResponseDTO<T> ofSuccess(String message, T data) {
        return new ResponseDTO<>(CODE_SUCCESS, message, data);
    }

    public static <T> ResponseDTO<T> ofSuccess() {
        return new ResponseDTO<>(CODE_SUCCESS, null, null);
    }

    public static <T> ResponseDTO<T> ofSuccess(String message) {
        return new ResponseDTO<>(CODE_SUCCESS, message, null);
    }
}
