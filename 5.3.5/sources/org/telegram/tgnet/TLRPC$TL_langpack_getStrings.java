package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_langpack_getStrings extends TLObject {
    public static int constructor = 773776152;
    public ArrayList<String> keys = new ArrayList();
    public String lang_code;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Vector vector = new TLRPC$Vector();
        int size = stream.readInt32(exception);
        for (int a = 0; a < size; a++) {
            TLRPC$LangPackString object = TLRPC$LangPackString.TLdeserialize(stream, stream.readInt32(exception), exception);
            if (object == null) {
                break;
            }
            vector.objects.add(object);
        }
        return vector;
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.lang_code);
        stream.writeInt32(481674261);
        int count = this.keys.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            stream.writeString((String) this.keys.get(a));
        }
    }
}
