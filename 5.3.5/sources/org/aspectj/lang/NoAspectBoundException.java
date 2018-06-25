package org.aspectj.lang;

public class NoAspectBoundException extends RuntimeException {
    Throwable cause;

    public NoAspectBoundException(String aspectName, Throwable inner) {
        if (inner != null) {
            aspectName = new StringBuffer().append("Exception while initializing ").append(aspectName).append(": ").append(inner).toString();
        }
        super(aspectName);
        this.cause = inner;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
