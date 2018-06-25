package net.hockeyapp.android.metrics.model;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import net.hockeyapp.android.metrics.JsonHelper;

public class EventData extends TelemetryData {
    private Map<String, Double> measurements;
    private String name;
    private Map<String, String> properties;
    private int ver = 2;

    public EventData() {
        InitializeFields();
        SetupAttributes();
    }

    public String getEnvelopeName() {
        return "Microsoft.ApplicationInsights.Event";
    }

    public String getBaseType() {
        return "EventData";
    }

    public int getVer() {
        return this.ver;
    }

    public void setVer(int value) {
        this.ver = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Map<String, String> getProperties() {
        if (this.properties == null) {
            this.properties = new LinkedHashMap();
        }
        return this.properties;
    }

    public void setProperties(Map<String, String> value) {
        this.properties = value;
    }

    public Map<String, Double> getMeasurements() {
        if (this.measurements == null) {
            this.measurements = new LinkedHashMap();
        }
        return this.measurements;
    }

    public void setMeasurements(Map<String, Double> value) {
        this.measurements = value;
    }

    protected String serializeContent(Writer writer) throws IOException {
        writer.write(super.serializeContent(writer) + "\"ver\":");
        writer.write(JsonHelper.convert(Integer.valueOf(this.ver)));
        writer.write("," + "\"name\":");
        writer.write(JsonHelper.convert(this.name));
        String prefix = ",";
        if (this.properties != null) {
            writer.write(prefix + "\"properties\":");
            JsonHelper.writeDictionary(writer, this.properties);
            prefix = ",";
        }
        if (this.measurements == null) {
            return prefix;
        }
        writer.write(prefix + "\"measurements\":");
        JsonHelper.writeDictionary(writer, this.measurements);
        return ",";
    }

    public void SetupAttributes() {
    }

    protected void InitializeFields() {
        this.QualifiedName = "com.microsoft.applicationinsights.contracts.EventData";
    }
}
