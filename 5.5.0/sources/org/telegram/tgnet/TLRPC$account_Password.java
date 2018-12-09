package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.TL_account_noPassword;
import org.telegram.tgnet.TLRPC.TL_account_password;

public abstract class TLRPC$account_Password extends TLObject {
    public byte[] current_salt;
    public String email_unconfirmed_pattern;
    public boolean has_recovery;
    public String hint;
    public byte[] new_salt;

    public static TLRPC$account_Password TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$account_Password tLRPC$account_Password = null;
        switch (i) {
            case -1764049896:
                tLRPC$account_Password = new TL_account_noPassword();
                break;
            case 2081952796:
                tLRPC$account_Password = new TL_account_password();
                break;
        }
        if (tLRPC$account_Password == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in account_Password", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$account_Password != null) {
            tLRPC$account_Password.readParams(abstractSerializedData, z);
        }
        return tLRPC$account_Password;
    }
}
