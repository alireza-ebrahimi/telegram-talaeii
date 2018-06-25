package net.hockeyapp.android.metrics.model;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.Map;
import net.hockeyapp.android.metrics.JsonHelper;

public class Session implements IJsonSerializable, Serializable {
    private String id;
    private String isFirst;
    private String isNew;

    public Session() {
        InitializeFields();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getIsFirst() {
        return this.isFirst;
    }

    public void setIsFirst(String value) {
        this.isFirst = value;
    }

    public String getIsNew() {
        return this.isNew;
    }

    public void setIsNew(String value) {
        this.isNew = value;
    }

    public void addToHashMap(Map<String, String> map) {
        if (this.id != null) {
            map.put("ai.session.id", this.id);
        }
        if (this.isFirst != null) {
            map.put("ai.session.isFirst", this.isFirst);
        }
        if (this.isNew != null) {
            map.put("ai.session.isNew", this.isNew);
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
        if (this.id != null) {
            writer.write(prefix + "\"ai.session.id\":");
            writer.write(JsonHelper.convert(this.id));
            prefix = ",";
        }
        if (this.isFirst != null) {
            writer.write(prefix + "\"ai.session.isFirst\":");
            writer.write(JsonHelper.convert(this.isFirst));
            prefix = ",";
        }
        if (this.isNew == null) {
            return prefix;
        }
        writer.write(prefix + "\"ai.session.isNew\":");
        writer.write(JsonHelper.convert(this.isNew));
        return ",";
    }

    protected void InitializeFields() {
    }
}
