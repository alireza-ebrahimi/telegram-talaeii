package org.telegram.tgnet;

public class TLRPC$TL_help_inviteText extends TLObject {
    public static int constructor = 415997816;
    public String message;

    public static TLRPC$TL_help_inviteText TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_help_inviteText tLRPC$TL_help_inviteText = new TLRPC$TL_help_inviteText();
            tLRPC$TL_help_inviteText.readParams(abstractSerializedData, z);
            return tLRPC$TL_help_inviteText;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_help_inviteText", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.message = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.message);
    }
}
