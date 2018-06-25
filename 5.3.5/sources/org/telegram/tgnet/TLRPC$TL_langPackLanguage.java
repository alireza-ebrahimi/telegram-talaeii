package org.telegram.tgnet;

public class TLRPC$TL_langPackLanguage extends TLObject {
    public static int constructor = 292985073;
    public String lang_code;
    public String name;
    public String native_name;

    public static TLRPC$TL_langPackLanguage TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_langPackLanguage result = new TLRPC$TL_langPackLanguage();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_langPackLanguage", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.name = stream.readString(exception);
        this.native_name = stream.readString(exception);
        this.lang_code = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.name);
        stream.writeString(this.native_name);
        stream.writeString(this.lang_code);
    }
}
