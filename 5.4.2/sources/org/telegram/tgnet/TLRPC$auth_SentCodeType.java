package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.TL_auth_sentCodeTypeApp;
import org.telegram.tgnet.TLRPC.TL_auth_sentCodeTypeCall;
import org.telegram.tgnet.TLRPC.TL_auth_sentCodeTypeFlashCall;
import org.telegram.tgnet.TLRPC.TL_auth_sentCodeTypeSms;

public abstract class TLRPC$auth_SentCodeType extends TLObject {
    public int length;
    public String pattern;

    public static TLRPC$auth_SentCodeType TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$auth_SentCodeType tLRPC$auth_SentCodeType = null;
        switch (i) {
            case -1425815847:
                tLRPC$auth_SentCodeType = new TL_auth_sentCodeTypeFlashCall();
                break;
            case -1073693790:
                tLRPC$auth_SentCodeType = new TL_auth_sentCodeTypeSms();
                break;
            case 1035688326:
                tLRPC$auth_SentCodeType = new TL_auth_sentCodeTypeApp();
                break;
            case 1398007207:
                tLRPC$auth_SentCodeType = new TL_auth_sentCodeTypeCall();
                break;
        }
        if (tLRPC$auth_SentCodeType == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in auth_SentCodeType", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$auth_SentCodeType != null) {
            tLRPC$auth_SentCodeType.readParams(abstractSerializedData, z);
        }
        return tLRPC$auth_SentCodeType;
    }
}
