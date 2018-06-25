package net.hockeyapp.android.metrics.model;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.Map;
import net.hockeyapp.android.metrics.JsonHelper;

public class Internal implements IJsonSerializable, Serializable {
    private String accountId;
    private String agentVersion;
    private String applicationName;
    private String applicationType;
    private String dataCollectorReceivedTime;
    private String flowType;
    private String instrumentationKey;
    private String isAudit;
    private String profileClassId;
    private String profileId;
    private String requestSource;
    private String sdkVersion;
    private String telemetryItemId;
    private String trackingSourceId;
    private String trackingType;

    public Internal() {
        InitializeFields();
    }

    public String getSdkVersion() {
        return this.sdkVersion;
    }

    public void setSdkVersion(String value) {
        this.sdkVersion = value;
    }

    public String getAgentVersion() {
        return this.agentVersion;
    }

    public void setAgentVersion(String value) {
        this.agentVersion = value;
    }

    public String getDataCollectorReceivedTime() {
        return this.dataCollectorReceivedTime;
    }

    public void setDataCollectorReceivedTime(String value) {
        this.dataCollectorReceivedTime = value;
    }

    public String getProfileId() {
        return this.profileId;
    }

    public void setProfileId(String value) {
        this.profileId = value;
    }

    public String getProfileClassId() {
        return this.profileClassId;
    }

    public void setProfileClassId(String value) {
        this.profileClassId = value;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String value) {
        this.accountId = value;
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    public void setApplicationName(String value) {
        this.applicationName = value;
    }

    public String getInstrumentationKey() {
        return this.instrumentationKey;
    }

    public void setInstrumentationKey(String value) {
        this.instrumentationKey = value;
    }

    public String getTelemetryItemId() {
        return this.telemetryItemId;
    }

    public void setTelemetryItemId(String value) {
        this.telemetryItemId = value;
    }

    public String getApplicationType() {
        return this.applicationType;
    }

    public void setApplicationType(String value) {
        this.applicationType = value;
    }

    public String getRequestSource() {
        return this.requestSource;
    }

    public void setRequestSource(String value) {
        this.requestSource = value;
    }

    public String getFlowType() {
        return this.flowType;
    }

    public void setFlowType(String value) {
        this.flowType = value;
    }

    public String getIsAudit() {
        return this.isAudit;
    }

    public void setIsAudit(String value) {
        this.isAudit = value;
    }

    public String getTrackingSourceId() {
        return this.trackingSourceId;
    }

    public void setTrackingSourceId(String value) {
        this.trackingSourceId = value;
    }

    public String getTrackingType() {
        return this.trackingType;
    }

    public void setTrackingType(String value) {
        this.trackingType = value;
    }

    public void addToHashMap(Map<String, String> map) {
        if (this.sdkVersion != null) {
            map.put("ai.internal.sdkVersion", this.sdkVersion);
        }
        if (this.agentVersion != null) {
            map.put("ai.internal.agentVersion", this.agentVersion);
        }
        if (this.dataCollectorReceivedTime != null) {
            map.put("ai.internal.dataCollectorReceivedTime", this.dataCollectorReceivedTime);
        }
        if (this.profileId != null) {
            map.put("ai.internal.profileId", this.profileId);
        }
        if (this.profileClassId != null) {
            map.put("ai.internal.profileClassId", this.profileClassId);
        }
        if (this.accountId != null) {
            map.put("ai.internal.accountId", this.accountId);
        }
        if (this.applicationName != null) {
            map.put("ai.internal.applicationName", this.applicationName);
        }
        if (this.instrumentationKey != null) {
            map.put("ai.internal.instrumentationKey", this.instrumentationKey);
        }
        if (this.telemetryItemId != null) {
            map.put("ai.internal.telemetryItemId", this.telemetryItemId);
        }
        if (this.applicationType != null) {
            map.put("ai.internal.applicationType", this.applicationType);
        }
        if (this.requestSource != null) {
            map.put("ai.internal.requestSource", this.requestSource);
        }
        if (this.flowType != null) {
            map.put("ai.internal.flowType", this.flowType);
        }
        if (this.isAudit != null) {
            map.put("ai.internal.isAudit", this.isAudit);
        }
        if (this.trackingSourceId != null) {
            map.put("ai.internal.trackingSourceId", this.trackingSourceId);
        }
        if (this.trackingType != null) {
            map.put("ai.internal.trackingType", this.trackingType);
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
        if (this.sdkVersion != null) {
            writer.write(prefix + "\"ai.internal.sdkVersion\":");
            writer.write(JsonHelper.convert(this.sdkVersion));
            prefix = ",";
        }
        if (this.agentVersion != null) {
            writer.write(prefix + "\"ai.internal.agentVersion\":");
            writer.write(JsonHelper.convert(this.agentVersion));
            prefix = ",";
        }
        if (this.dataCollectorReceivedTime != null) {
            writer.write(prefix + "\"ai.internal.dataCollectorReceivedTime\":");
            writer.write(JsonHelper.convert(this.dataCollectorReceivedTime));
            prefix = ",";
        }
        if (this.profileId != null) {
            writer.write(prefix + "\"ai.internal.profileId\":");
            writer.write(JsonHelper.convert(this.profileId));
            prefix = ",";
        }
        if (this.profileClassId != null) {
            writer.write(prefix + "\"ai.internal.profileClassId\":");
            writer.write(JsonHelper.convert(this.profileClassId));
            prefix = ",";
        }
        if (this.accountId != null) {
            writer.write(prefix + "\"ai.internal.accountId\":");
            writer.write(JsonHelper.convert(this.accountId));
            prefix = ",";
        }
        if (this.applicationName != null) {
            writer.write(prefix + "\"ai.internal.applicationName\":");
            writer.write(JsonHelper.convert(this.applicationName));
            prefix = ",";
        }
        if (this.instrumentationKey != null) {
            writer.write(prefix + "\"ai.internal.instrumentationKey\":");
            writer.write(JsonHelper.convert(this.instrumentationKey));
            prefix = ",";
        }
        if (this.telemetryItemId != null) {
            writer.write(prefix + "\"ai.internal.telemetryItemId\":");
            writer.write(JsonHelper.convert(this.telemetryItemId));
            prefix = ",";
        }
        if (this.applicationType != null) {
            writer.write(prefix + "\"ai.internal.applicationType\":");
            writer.write(JsonHelper.convert(this.applicationType));
            prefix = ",";
        }
        if (this.requestSource != null) {
            writer.write(prefix + "\"ai.internal.requestSource\":");
            writer.write(JsonHelper.convert(this.requestSource));
            prefix = ",";
        }
        if (this.flowType != null) {
            writer.write(prefix + "\"ai.internal.flowType\":");
            writer.write(JsonHelper.convert(this.flowType));
            prefix = ",";
        }
        if (this.isAudit != null) {
            writer.write(prefix + "\"ai.internal.isAudit\":");
            writer.write(JsonHelper.convert(this.isAudit));
            prefix = ",";
        }
        if (this.trackingSourceId != null) {
            writer.write(prefix + "\"ai.internal.trackingSourceId\":");
            writer.write(JsonHelper.convert(this.trackingSourceId));
            prefix = ",";
        }
        if (this.trackingType == null) {
            return prefix;
        }
        writer.write(prefix + "\"ai.internal.trackingType\":");
        writer.write(JsonHelper.convert(this.trackingType));
        return ",";
    }

    protected void InitializeFields() {
    }
}
