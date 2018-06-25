package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_langPackDifference extends TLObject {
    public static int constructor = -209337866;
    public int from_version;
    public String lang_code;
    public ArrayList<TLRPC$LangPackString> strings = new ArrayList();
    public int version;

    public static TLRPC$TL_langPackDifference TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_langPackDifference result = new TLRPC$TL_langPackDifference();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_langPackDifference", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.lang_code = stream.readString(exception);
        this.from_version = stream.readInt32(exception);
        this.version = stream.readInt32(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$LangPackString object = TLRPC$LangPackString.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.strings.add(object);
                    a++;
                } else {
                    return;
                }
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.lang_code);
        stream.writeInt32(this.from_version);
        stream.writeInt32(this.version);
        stream.writeInt32(481674261);
        int count = this.strings.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$LangPackString) this.strings.get(a)).serializeToStream(stream);
        }
    }
}
