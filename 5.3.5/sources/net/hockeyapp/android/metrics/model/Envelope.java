package net.hockeyapp.android.metrics.model;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import net.hockeyapp.android.metrics.JsonHelper;

public class Envelope implements IJsonSerializable {
    private String appId;
    private String appVer;
    private String cV;
    private Base data;
    private String epoch;
    private Map<String, Extension> ext;
    private long flags;
    private String iKey;
    private String name;
    private String os;
    private String osVer;
    private int sampleRate = 100;
    private long seqNum;
    private Map<String, String> tags;
    private String time;
    private int ver = 1;

    public Envelope() {
        InitializeFields();
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

    public String getTime() {
        return this.time;
    }

    public void setTime(String value) {
        this.time = value;
    }

    public int getSampleRate() {
        return this.sampleRate;
    }

    public void setSampleRate(int value) {
        this.sampleRate = value;
    }

    public String getEpoch() {
        return this.epoch;
    }

    public void setEpoch(String value) {
        this.epoch = value;
    }

    public long getSeqNum() {
        return this.seqNum;
    }

    public void setSeqNum(long value) {
        this.seqNum = value;
    }

    public String getIKey() {
        return this.iKey;
    }

    public void setIKey(String value) {
        this.iKey = value;
    }

    public long getFlags() {
        return this.flags;
    }

    public void setFlags(long value) {
        this.flags = value;
    }

    public String getOs() {
        return this.os;
    }

    public void setOs(String value) {
        this.os = value;
    }

    public String getOsVer() {
        return this.osVer;
    }

    public void setOsVer(String value) {
        this.osVer = value;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String value) {
        this.appId = value;
    }

    public String getAppVer() {
        return this.appVer;
    }

    public void setAppVer(String value) {
        this.appVer = value;
    }

    public String getCV() {
        return this.cV;
    }

    public void setCV(String value) {
        this.cV = value;
    }

    public Map<String, String> getTags() {
        if (this.tags == null) {
            this.tags = new LinkedHashMap();
        }
        return this.tags;
    }

    public void setTags(Map<String, String> value) {
        this.tags = value;
    }

    public Map<String, Extension> getExt() {
        if (this.ext == null) {
            this.ext = new LinkedHashMap();
        }
        return this.ext;
    }

    public void setExt(Map<String, Extension> value) {
        this.ext = value;
    }

    public Base getData() {
        return this.data;
    }

    public void setData(Base value) {
        this.data = value;
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
        writer.write("" + "\"ver\":");
        writer.write(JsonHelper.convert(Integer.valueOf(this.ver)));
        writer.write("," + "\"name\":");
        writer.write(JsonHelper.convert(this.name));
        writer.write("," + "\"time\":");
        writer.write(JsonHelper.convert(this.time));
        String prefix = ",";
        if (((double) this.sampleRate) > 0.0d) {
            writer.write(prefix + "\"sampleRate\":");
            writer.write(JsonHelper.convert(Integer.valueOf(this.sampleRate)));
            prefix = ",";
        }
        if (this.epoch != null) {
            writer.write(prefix + "\"epoch\":");
            writer.write(JsonHelper.convert(this.epoch));
            prefix = ",";
        }
        if (this.seqNum != 0) {
            writer.write(prefix + "\"seqNum\":");
            writer.write(JsonHelper.convert(Long.valueOf(this.seqNum)));
            prefix = ",";
        }
        if (this.iKey != null) {
            writer.write(prefix + "\"iKey\":");
            writer.write(JsonHelper.convert(this.iKey));
            prefix = ",";
        }
        if (this.flags != 0) {
            writer.write(prefix + "\"flags\":");
            writer.write(JsonHelper.convert(Long.valueOf(this.flags)));
            prefix = ",";
        }
        if (this.os != null) {
            writer.write(prefix + "\"os\":");
            writer.write(JsonHelper.convert(this.os));
            prefix = ",";
        }
        if (this.osVer != null) {
            writer.write(prefix + "\"osVer\":");
            writer.write(JsonHelper.convert(this.osVer));
            prefix = ",";
        }
        if (this.appId != null) {
            writer.write(prefix + "\"appId\":");
            writer.write(JsonHelper.convert(this.appId));
            prefix = ",";
        }
        if (this.appVer != null) {
            writer.write(prefix + "\"appVer\":");
            writer.write(JsonHelper.convert(this.appVer));
            prefix = ",";
        }
        if (this.cV != null) {
            writer.write(prefix + "\"cV\":");
            writer.write(JsonHelper.convert(this.cV));
            prefix = ",";
        }
        if (this.tags != null) {
            writer.write(prefix + "\"tags\":");
            JsonHelper.writeDictionary(writer, this.tags);
            prefix = ",";
        }
        if (this.ext != null) {
            writer.write(prefix + "\"ext\":");
            JsonHelper.writeDictionary(writer, this.ext);
            prefix = ",";
        }
        if (this.data == null) {
            return prefix;
        }
        writer.write(prefix + "\"data\":");
        JsonHelper.writeJsonSerializable(writer, this.data);
        return ",";
    }

    protected void InitializeFields() {
    }
}
