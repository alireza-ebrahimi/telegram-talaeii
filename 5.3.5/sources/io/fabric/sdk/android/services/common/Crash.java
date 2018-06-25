package io.fabric.sdk.android.services.common;

public abstract class Crash {
    private static final String UNKNOWN_EXCEPTION = "<unknown>";
    private final String exceptionName;
    private final String sessionId;

    public static class FatalException extends Crash {
        public FatalException(String sessionId) {
            super(sessionId);
        }

        public FatalException(String sessionId, String exceptionName) {
            super(sessionId, exceptionName);
        }
    }

    public static class LoggedException extends Crash {
        public LoggedException(String sessionId) {
            super(sessionId);
        }

        public LoggedException(String sessionId, String exceptionName) {
            super(sessionId, exceptionName);
        }
    }

    public Crash(String sessionId) {
        this(sessionId, UNKNOWN_EXCEPTION);
    }

    public Crash(String sessionId, String exceptionName) {
        this.sessionId = sessionId;
        this.exceptionName = exceptionName;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getExceptionName() {
        return this.exceptionName;
    }
}
