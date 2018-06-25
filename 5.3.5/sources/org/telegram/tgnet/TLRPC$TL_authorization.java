package org.telegram.tgnet;

public class TLRPC$TL_authorization extends TLObject {
    public static int constructor = 2079516406;
    public int api_id;
    public String app_name;
    public String app_version;
    public String country;
    public int date_active;
    public int date_created;
    public String device_model;
    public int flags;
    public long hash;
    public String ip;
    public String platform;
    public String region;
    public String system_version;

    public static TLRPC$TL_authorization TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_authorization result = new TLRPC$TL_authorization();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_authorization", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.hash = stream.readInt64(exception);
        this.flags = stream.readInt32(exception);
        this.device_model = stream.readString(exception);
        this.platform = stream.readString(exception);
        this.system_version = stream.readString(exception);
        this.api_id = stream.readInt32(exception);
        this.app_name = stream.readString(exception);
        this.app_version = stream.readString(exception);
        this.date_created = stream.readInt32(exception);
        this.date_active = stream.readInt32(exception);
        this.ip = stream.readString(exception);
        this.country = stream.readString(exception);
        this.region = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.hash);
        stream.writeInt32(this.flags);
        stream.writeString(this.device_model);
        stream.writeString(this.platform);
        stream.writeString(this.system_version);
        stream.writeInt32(this.api_id);
        stream.writeString(this.app_name);
        stream.writeString(this.app_version);
        stream.writeInt32(this.date_created);
        stream.writeInt32(this.date_active);
        stream.writeString(this.ip);
        stream.writeString(this.country);
        stream.writeString(this.region);
    }
}
