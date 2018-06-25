package net.hockeyapp.android.metrics.model;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.Map;
import net.hockeyapp.android.metrics.JsonHelper;

public class Application implements IJsonSerializable, Serializable {
    private String build;
    private String typeId;
    private String ver;

    public Application() {
        InitializeFields();
    }

    public String getVer() {
        return this.ver;
    }

    public void setVer(String value) {
        this.ver = value;
    }

    public String getBuild() {
        return this.build;
    }

    public void setBuild(String value) {
        this.build = value;
    }

    public String getTypeId() {
        return this.typeId;
    }

    public void setTypeId(String value) {
        this.typeId = value;
    }

    public void addToHashMap(Map<String, String> map) {
        if (this.ver != null) {
            map.put("ai.application.ver", this.ver);
        }
        if (this.build != null) {
            map.put("ai.application.build", this.build);
        }
        if (this.typeId != null) {
            map.put("ai.application.typeId", this.typeId);
        }
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
        if (this.ver != null) {
            writer.write(prefix + "\"ai.application.ver\":");
            writer.write(JsonHelper.convert(this.ver));
            prefix = ",";
        }
        if (this.build != null) {
            writer.write(prefix + "\"ai.application.build\":");
            writer.write(JsonHelper.convert(this.build));
            prefix = ",";
        }
        if (this.typeId == null) {
            return prefix;
        }
        writer.write(prefix + "\"ai.application.typeId\":");
        writer.write(JsonHelper.convert(this.typeId));
        return ",";
    }

    protected void InitializeFields() {
    }
}
