package net.hockeyapp.android.metrics.model;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.Map;
import net.hockeyapp.android.metrics.JsonHelper;

public class Device implements IJsonSerializable, Serializable {
    private String id;
    private String ip;
    private String language;
    private String locale;
    private String machineName;
    private String model;
    private String network;
    private String networkName;
    private String oemName;
    private String os;
    private String osVersion;
    private String roleInstance;
    private String roleName;
    private String screenResolution;
    private String type;
    private String vmName;

    public Device() {
        InitializeFields();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String value) {
        this.ip = value;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String value) {
        this.language = value;
    }

    public String getLocale() {
        return this.locale;
    }

    public void setLocale(String value) {
        this.locale = value;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String value) {
        this.model = value;
    }

    public String getNetwork() {
        return this.network;
    }

    public void setNetwork(String value) {
        this.network = value;
    }

    public String getNetworkName() {
        return this.networkName;
    }

    public void setNetworkName(String value) {
        this.networkName = value;
    }

    public String getOemName() {
        return this.oemName;
    }

    public void setOemName(String value) {
        this.oemName = value;
    }

    public String getOs() {
        return this.os;
    }

    public void setOs(String value) {
        this.os = value;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public void setOsVersion(String value) {
        this.osVersion = value;
    }

    public String getRoleInstance() {
        return this.roleInstance;
    }

    public void setRoleInstance(String value) {
        this.roleInstance = value;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String value) {
        this.roleName = value;
    }

    public String getScreenResolution() {
        return this.screenResolution;
    }

    public void setScreenResolution(String value) {
        this.screenResolution = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getMachineName() {
        return this.machineName;
    }

    public void setMachineName(String value) {
        this.machineName = value;
    }

    public String getVmName() {
        return this.vmName;
    }

    public void setVmName(String value) {
        this.vmName = value;
    }

    public void addToHashMap(Map<String, String> map) {
        if (this.id != null) {
            map.put("ai.device.id", this.id);
        }
        if (this.ip != null) {
            map.put("ai.device.ip", this.ip);
        }
        if (this.language != null) {
            map.put("ai.device.language", this.language);
        }
        if (this.locale != null) {
            map.put("ai.device.locale", this.locale);
        }
        if (this.model != null) {
            map.put("ai.device.model", this.model);
        }
        if (this.network != null) {
            map.put("ai.device.network", this.network);
        }
        if (this.networkName != null) {
            map.put("ai.device.networkName", this.networkName);
        }
        if (this.oemName != null) {
            map.put("ai.device.oemName", this.oemName);
        }
        if (this.os != null) {
            map.put("ai.device.os", this.os);
        }
        if (this.osVersion != null) {
            map.put("ai.device.osVersion", this.osVersion);
        }
        if (this.roleInstance != null) {
            map.put("ai.device.roleInstance", this.roleInstance);
        }
        if (this.roleName != null) {
            map.put("ai.device.roleName", this.roleName);
        }
        if (this.screenResolution != null) {
            map.put("ai.device.screenResolution", this.screenResolution);
        }
        if (this.type != null) {
            map.put("ai.device.type", this.type);
        }
        if (this.machineName != null) {
            map.put("ai.device.machineName", this.machineName);
        }
        if (this.vmName != null) {
            map.put("ai.device.vmName", this.vmName);
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
            writer.write(prefix + "\"ai.device.id\":");
            writer.write(JsonHelper.convert(this.id));
            prefix = ",";
        }
        if (this.ip != null) {
            writer.write(prefix + "\"ai.device.ip\":");
            writer.write(JsonHelper.convert(this.ip));
            prefix = ",";
        }
        if (this.language != null) {
            writer.write(prefix + "\"ai.device.language\":");
            writer.write(JsonHelper.convert(this.language));
            prefix = ",";
        }
        if (this.locale != null) {
            writer.write(prefix + "\"ai.device.locale\":");
            writer.write(JsonHelper.convert(this.locale));
            prefix = ",";
        }
        if (this.model != null) {
            writer.write(prefix + "\"ai.device.model\":");
            writer.write(JsonHelper.convert(this.model));
            prefix = ",";
        }
        if (this.network != null) {
            writer.write(prefix + "\"ai.device.network\":");
            writer.write(JsonHelper.convert(this.network));
            prefix = ",";
        }
        if (this.networkName != null) {
            writer.write(prefix + "\"ai.device.networkName\":");
            writer.write(JsonHelper.convert(this.networkName));
            prefix = ",";
        }
        if (this.oemName != null) {
            writer.write(prefix + "\"ai.device.oemName\":");
            writer.write(JsonHelper.convert(this.oemName));
            prefix = ",";
        }
        if (this.os != null) {
            writer.write(prefix + "\"ai.device.os\":");
            writer.write(JsonHelper.convert(this.os));
            prefix = ",";
        }
        if (this.osVersion != null) {
            writer.write(prefix + "\"ai.device.osVersion\":");
            writer.write(JsonHelper.convert(this.osVersion));
            prefix = ",";
        }
        if (this.roleInstance != null) {
            writer.write(prefix + "\"ai.device.roleInstance\":");
            writer.write(JsonHelper.convert(this.roleInstance));
            prefix = ",";
        }
        if (this.roleName != null) {
            writer.write(prefix + "\"ai.device.roleName\":");
            writer.write(JsonHelper.convert(this.roleName));
            prefix = ",";
        }
        if (this.screenResolution != null) {
            writer.write(prefix + "\"ai.device.screenResolution\":");
            writer.write(JsonHelper.convert(this.screenResolution));
            prefix = ",";
        }
        if (this.type != null) {
            writer.write(prefix + "\"ai.device.type\":");
            writer.write(JsonHelper.convert(this.type));
            prefix = ",";
        }
        if (this.machineName != null) {
            writer.write(prefix + "\"ai.device.machineName\":");
            writer.write(JsonHelper.convert(this.machineName));
            prefix = ",";
        }
        if (this.vmName == null) {
            return prefix;
        }
        writer.write(prefix + "\"ai.device.vmName\":");
        writer.write(JsonHelper.convert(this.vmName));
        return ",";
    }

    protected void InitializeFields() {
    }
}
