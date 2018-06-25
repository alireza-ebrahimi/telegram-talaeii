package org.telegram.messenger.exoplayer2;

import java.io.IOException;

public class ParserException extends IOException {
    public ParserException(String message) {
        super(message);
    }

    public ParserException(Throwable cause) {
        super(cause);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
