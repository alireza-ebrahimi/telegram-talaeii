package org.telegram.customization.fetch.exception;

public final class NotUsableException extends RuntimeException {
    private final int errorCode;

    public NotUsableException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
