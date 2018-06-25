package org.telegram.tgnet;

public abstract class TLRPC$account_Password extends TLObject {
    public byte[] current_salt;
    public String email_unconfirmed_pattern;
    public boolean has_recovery;
    public String hint;
    public byte[] new_salt;

    public static TLRPC$account_Password TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$account_Password result = null;
        switch (constructor) {
            case -1764049896:
                result = new TLRPC$TL_account_noPassword();
                break;
            case 2081952796:
                result = new TLRPC$TL_account_password();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in account_Password", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
