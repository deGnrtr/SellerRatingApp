package com.leverx.javacourse.seller.rating.app.dto;

public class ExceptionResponseDto {

    private String message;

    public ExceptionResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
