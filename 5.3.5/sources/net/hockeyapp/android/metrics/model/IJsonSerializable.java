package net.hockeyapp.android.metrics.model;

import java.io.IOException;
import java.io.Writer;

public interface IJsonSerializable {
    void serialize(Writer writer) throws IOException;
}
