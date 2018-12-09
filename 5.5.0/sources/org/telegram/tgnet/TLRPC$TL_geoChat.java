package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatPhoto;
import org.telegram.tgnet.TLRPC.GeoPoint;

public class TLRPC$TL_geoChat extends Chat {
    public static int constructor = 1978329690;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readInt32(z);
        this.access_hash = abstractSerializedData.readInt64(z);
        this.title = abstractSerializedData.readString(z);
        this.address = abstractSerializedData.readString(z);
        this.venue = abstractSerializedData.readString(z);
        this.geo = GeoPoint.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.photo = ChatPhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.participants_count = abstractSerializedData.readInt32(z);
        this.date = abstractSerializedData.readInt32(z);
        this.checked_in = abstractSerializedData.readBool(z);
        this.version = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.id);
        abstractSerializedData.writeInt64(this.access_hash);
        abstractSerializedData.writeString(this.title);
        abstractSerializedData.writeString(this.address);
        abstractSerializedData.writeString(this.venue);
        this.geo.serializeToStream(abstractSerializedData);
        this.photo.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.participants_count);
        abstractSerializedData.writeInt32(this.date);
        abstractSerializedData.writeBool(this.checked_in);
        abstractSerializedData.writeInt32(this.version);
    }
}
