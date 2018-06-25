package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.LangPackString;

public class TLRPC$TL_langPackStringPluralized extends LangPackString {
    public static int constructor = 1816636575;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.key = abstractSerializedData.readString(z);
        if ((this.flags & 1) != 0) {
            this.zero_value = abstractSerializedData.readString(z);
        }
        if ((this.flags & 2) != 0) {
            this.one_value = abstractSerializedData.readString(z);
        }
        if ((this.flags & 4) != 0) {
            this.two_value = abstractSerializedData.readString(z);
        }
        if ((this.flags & 8) != 0) {
            this.few_value = abstractSerializedData.readString(z);
        }
        if ((this.flags & 16) != 0) {
            this.many_value = abstractSerializedData.readString(z);
        }
        this.other_value = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeString(this.key);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeString(this.zero_value);
        }
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeString(this.one_value);
        }
        if ((this.flags & 4) != 0) {
            abstractSerializedData.writeString(this.two_value);
        }
        if ((this.flags & 8) != 0) {
            abstractSerializedData.writeString(this.few_value);
        }
        if ((this.flags & 16) != 0) {
            abstractSerializedData.writeString(this.many_value);
        }
        abstractSerializedData.writeString(this.other_value);
    }
}
