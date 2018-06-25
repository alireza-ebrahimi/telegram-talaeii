package net.hockeyapp.android.metrics.model;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import net.hockeyapp.android.metrics.JsonHelper;

public class Base implements IJsonSerializable {
    public LinkedHashMap<String, String> Attributes = new LinkedHashMap();
    public String QualifiedName;
    private String baseType;

    public Base() {
        InitializeFields();
    }

    public String getBaseType() {
        return this.baseType;
    }

    public void setBaseType(String value) {
        this.baseType = value;
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
        if (this.baseType == null) {
            return prefix;
        }
        writer.write(prefix + "\"baseType\":");
        writer.write(JsonHelper.convert(this.baseType));
        return ",";
    }

    protected void InitializeFields() {
    }
}
