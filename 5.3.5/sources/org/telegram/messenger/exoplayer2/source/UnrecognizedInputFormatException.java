package org.telegram.messenger.exoplayer2.source;

import android.net.Uri;
import org.telegram.messenger.exoplayer2.ParserException;

public class UnrecognizedInputFormatException extends ParserException {
    public final Uri uri;

    public UnrecognizedInputFormatException(String message, Uri uri) {
        super(message);
        this.uri = uri;
    }
}
