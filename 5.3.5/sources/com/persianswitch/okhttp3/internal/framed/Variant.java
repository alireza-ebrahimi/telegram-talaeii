package com.persianswitch.okhttp3.internal.framed;

import com.persianswitch.okhttp3.Protocol;
import com.persianswitch.okio.BufferedSink;
import com.persianswitch.okio.BufferedSource;

public interface Variant {
    Protocol getProtocol();

    FrameReader newReader(BufferedSource bufferedSource, boolean z);

    FrameWriter newWriter(BufferedSink bufferedSink, boolean z);
}
