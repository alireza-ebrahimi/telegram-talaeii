package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_account_setPrivacy extends TLObject {
    public static int constructor = -906486552;
    public TLRPC$InputPrivacyKey key;
    public ArrayList<TLRPC$InputPrivacyRule> rules = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_account_privacyRules.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.key.serializeToStream(stream);
        stream.writeInt32(481674261);
        int count = this.rules.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$InputPrivacyRule) this.rules.get(a)).serializeToStream(stream);
        }
    }
}
