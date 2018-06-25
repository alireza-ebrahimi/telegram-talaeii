package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.TL_auth_codeTypeCall;
import org.telegram.tgnet.TLRPC.TL_auth_codeTypeFlashCall;
import org.telegram.tgnet.TLRPC.TL_auth_codeTypeSms;

public abstract class TLRPC$auth_CodeType extends TLObject {
    public static TLRPC$auth_CodeType TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$auth_CodeType tLRPC$auth_CodeType = null;
        switch (i) {
            case 577556219:
                tLRPC$auth_CodeType = new TL_auth_codeTypeFlashCall();
                break;
            case 1923290508:
                tLRPC$auth_CodeType = new TL_auth_codeTypeSms();
                break;
            case 1948046307:
                tLRPC$auth_CodeType = new TL_auth_codeTypeCall();
                break;
        }
        if (tLRPC$auth_CodeType == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in auth_CodeType", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$auth_CodeType != null) {
            tLRPC$auth_CodeType.readParams(abstractSerializedData, z);
        }
        return tLRPC$auth_CodeType;
    }
}
