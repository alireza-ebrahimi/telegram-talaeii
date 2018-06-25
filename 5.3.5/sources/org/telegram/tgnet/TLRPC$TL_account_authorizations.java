package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_account_authorizations extends TLObject {
    public static int constructor = 307276766;
    public ArrayList<TLRPC$TL_authorization> authorizations = new ArrayList();

    public static TLRPC$TL_account_authorizations TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_account_authorizations result = new TLRPC$TL_account_authorizations();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_account_authorizations", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$TL_authorization object = TLRPC$TL_authorization.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.authorizations.add(object);
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
        stream.writeInt32(481674261);
        int count = this.authorizations.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$TL_authorization) this.authorizations.get(a)).serializeToStream(stream);
        }
    }
}
