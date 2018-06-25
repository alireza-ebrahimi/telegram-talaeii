package net.hockeyapp.android.metrics.model;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.Map;
import net.hockeyapp.android.metrics.JsonHelper;

public class User implements IJsonSerializable, Serializable {
    private String accountAcquisitionDate;
    private String accountId;
    private String anonUserAcquisitionDate;
    private String authUserAcquisitionDate;
    private String authUserId;
    private String id;
    private String storeRegion;
    private String userAgent;

    public User() {
        InitializeFields();
    }

    public String getAccountAcquisitionDate() {
        return this.accountAcquisitionDate;
    }

    public void setAccountAcquisitionDate(String value) {
        this.accountAcquisitionDate = value;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String value) {
        this.accountId = value;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public void setUserAgent(String value) {
        this.userAgent = value;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getStoreRegion() {
        return this.storeRegion;
    }

    public void setStoreRegion(String value) {
        this.storeRegion = value;
    }

    public String getAuthUserId() {
        return this.authUserId;
    }

    public void setAuthUserId(String value) {
        this.authUserId = value;
    }

    public String getAnonUserAcquisitionDate() {
        return this.anonUserAcquisitionDate;
    }

    public void setAnonUserAcquisitionDate(String value) {
        this.anonUserAcquisitionDate = value;
    }

    public String getAuthUserAcquisitionDate() {
        return this.authUserAcquisitionDate;
    }

    public void setAuthUserAcquisitionDate(String value) {
        this.authUserAcquisitionDate = value;
    }

    public void addToHashMap(Map<String, String> map) {
        if (this.accountAcquisitionDate != null) {
            map.put("ai.user.accountAcquisitionDate", this.accountAcquisitionDate);
        }
        if (this.accountId != null) {
            map.put("ai.user.accountId", this.accountId);
        }
        if (this.userAgent != null) {
            map.put("ai.user.userAgent", this.userAgent);
        }
        if (this.id != null) {
            map.put("ai.user.id", this.id);
        }
        if (this.storeRegion != null) {
            map.put("ai.user.storeRegion", this.storeRegion);
        }
        if (this.authUserId != null) {
            map.put("ai.user.authUserId", this.authUserId);
        }
        if (this.anonUserAcquisitionDate != null) {
            map.put("ai.user.anonUserAcquisitionDate", this.anonUserAcquisitionDate);
        }
        if (this.authUserAcquisitionDate != null) {
            map.put("ai.user.authUserAcquisitionDate", this.authUserAcquisitionDate);
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
        if (this.accountAcquisitionDate != null) {
            writer.write(prefix + "\"ai.user.accountAcquisitionDate\":");
            writer.write(JsonHelper.convert(this.accountAcquisitionDate));
            prefix = ",";
        }
        if (this.accountId != null) {
            writer.write(prefix + "\"ai.user.accountId\":");
            writer.write(JsonHelper.convert(this.accountId));
            prefix = ",";
        }
        if (this.userAgent != null) {
            writer.write(prefix + "\"ai.user.userAgent\":");
            writer.write(JsonHelper.convert(this.userAgent));
            prefix = ",";
        }
        if (this.id != null) {
            writer.write(prefix + "\"ai.user.id\":");
            writer.write(JsonHelper.convert(this.id));
            prefix = ",";
        }
        if (this.storeRegion != null) {
            writer.write(prefix + "\"ai.user.storeRegion\":");
            writer.write(JsonHelper.convert(this.storeRegion));
            prefix = ",";
        }
        if (this.authUserId != null) {
            writer.write(prefix + "\"ai.user.authUserId\":");
            writer.write(JsonHelper.convert(this.authUserId));
            prefix = ",";
        }
        if (this.anonUserAcquisitionDate != null) {
            writer.write(prefix + "\"ai.user.anonUserAcquisitionDate\":");
            writer.write(JsonHelper.convert(this.anonUserAcquisitionDate));
            prefix = ",";
        }
        if (this.authUserAcquisitionDate == null) {
            return prefix;
        }
        writer.write(prefix + "\"ai.user.authUserAcquisitionDate\":");
        writer.write(JsonHelper.convert(this.authUserAcquisitionDate));
        return ",";
    }

    protected void InitializeFields() {
    }
}
