package com.electronic.store.exceptions;

import lombok.Builder;

@Builder
public class BadApiRequestException extends RuntimeException {

    public BadApiRequestException(String message) {
        super(message);
    }

    public BadApiRequestException() {
        super("Bad Request");
    }
}
