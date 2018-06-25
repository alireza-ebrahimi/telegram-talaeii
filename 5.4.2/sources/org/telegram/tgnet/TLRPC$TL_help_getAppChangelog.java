package org.telegram.tgnet;

public class TLRPC$TL_help_getAppChangelog extends TLObject {
    public static int constructor = -1877938321;
    public String prev_app_version;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.prev_app_version);
    }
}
