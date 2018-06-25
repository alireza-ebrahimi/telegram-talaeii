package net.hockeyapp.android.metrics.model;

import java.io.IOException;
import java.io.Writer;
import net.hockeyapp.android.metrics.JsonHelper;
import tellh.com.stickyheaderview_rv.BuildConfig;

public class Extension implements IJsonSerializable {
    private String ver = BuildConfig.VERSION_NAME;

    public Extension() {
        InitializeFields();
    }

    public String getVer() {
        return this.ver;
    }

    public void setVer(String value) {
        this.ver = value;
    }

    public void serialize(Writer writer) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("writer");
        }
        writer.write(123);
        serializeContent(writer);
        writer.write(125);
    }

    protected String serializeContent(Writer writer) throws IOException {
        String prefix = "";
        if (this.ver == null) {
            return prefix;
        }
        writer.write(prefix + "\"ver\":");
        writer.write(JsonHelper.convert(this.ver));
        return ",";
    }

    protected void InitializeFields() {
    }
}
