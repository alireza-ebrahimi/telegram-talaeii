package org.telegram.customization.fetch.exception;

public final class InvalidStatusException extends RuntimeException {
    private final int errorCode;

    public InvalidStatusException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
