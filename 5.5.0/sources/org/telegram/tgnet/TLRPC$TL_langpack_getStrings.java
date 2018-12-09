package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.LangPackString;

public class TLRPC$TL_langpack_getStrings extends TLObject {
    public static int constructor = 773776152;
    public ArrayList<String> keys = new ArrayList();
    public String lang_code;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLObject tLRPC$Vector = new TLRPC$Vector();
        int readInt32 = abstractSerializedData.readInt32(z);
        for (int i2 = 0; i2 < readInt32; i2++) {
            LangPackString TLdeserialize = LangPackString.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            if (TLdeserialize == null) {
                break;
            }
            tLRPC$Vector.objects.add(TLdeserialize);
        }
        return tLRPC$Vector;
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.lang_code);
        abstractSerializedData.writeInt32(481674261);
        int size = this.keys.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            abstractSerializedData.writeString((String) this.keys.get(i));
        }
    }
}
