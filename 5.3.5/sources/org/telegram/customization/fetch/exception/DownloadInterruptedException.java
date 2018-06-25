package org.telegram.customization.fetch.exception;

public class DownloadInterruptedException extends RuntimeException {
    private int errorCode;

    public DownloadInterruptedException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
