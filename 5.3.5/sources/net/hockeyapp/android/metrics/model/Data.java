package net.hockeyapp.android.metrics.model;

import java.io.IOException;
import java.io.Writer;
import net.hockeyapp.android.metrics.JsonHelper;

public class Data<TDomain extends Domain> extends Base implements ITelemetryData {
    private TDomain baseData;

    public Data() {
        InitializeFields();
        SetupAttributes();
    }

    public TDomain getBaseData() {
        return this.baseData;
    }

    public void setBaseData(TDomain value) {
        this.baseData = value;
    }

    protected String serializeContent(Writer writer) throws IOException {
        writer.write(super.serializeContent(writer) + "\"baseData\":");
        JsonHelper.writeJsonSerializable(writer, this.baseData);
        return ",";
    }

    public void SetupAttributes() {
        this.Attributes.put("Description", "Data struct to contain both B and C sections.");
    }

    protected void InitializeFields() {
        this.QualifiedName = "com.microsoft.telemetry.Data";
    }
}
