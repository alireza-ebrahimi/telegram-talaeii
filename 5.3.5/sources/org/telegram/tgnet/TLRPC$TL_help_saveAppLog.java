package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_help_saveAppLog extends TLObject {
    public static int constructor = 1862465352;
    public ArrayList<TLRPC$TL_inputAppEvent> events = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.events.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$TL_inputAppEvent) this.events.get(a)).serializeToStream(stream);
        }
    }
}
