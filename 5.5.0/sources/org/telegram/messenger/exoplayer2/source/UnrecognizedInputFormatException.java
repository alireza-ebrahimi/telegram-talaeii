package org.telegram.messenger.exoplayer2.source;

import android.net.Uri;
import org.telegram.messenger.exoplayer2.ParserException;

public class UnrecognizedInputFormatException extends ParserException {
    public final Uri uri;

    public UnrecognizedInputFormatException(String str, Uri uri) {
        super(str);
        this.uri = uri;
    }
}
