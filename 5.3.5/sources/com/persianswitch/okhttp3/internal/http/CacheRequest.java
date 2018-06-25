package com.persianswitch.okhttp3.internal.http;

import com.persianswitch.okio.Sink;
import java.io.IOException;

public interface CacheRequest {
    void abort();

    Sink body() throws IOException;
}
