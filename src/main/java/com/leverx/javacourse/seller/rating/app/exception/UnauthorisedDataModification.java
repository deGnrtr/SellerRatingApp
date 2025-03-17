package com.leverx.javacourse.seller.rating.app.exception;

public class UnauthorisedDataModification extends RuntimeException {
    public UnauthorisedDataModification() {
        super();
    }

    public UnauthorisedDataModification(String message) {
        super(message);
    }
}
