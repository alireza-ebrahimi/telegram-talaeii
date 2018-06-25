package org.telegram.tgnet;

public abstract class TLRPC$InputPaymentCredentials extends TLObject {
    public TLRPC$TL_dataJSON data;
    public int flags;
    public String id;
    public TLRPC$TL_dataJSON payment_token;
    public boolean save;
    public byte[] tmp_password;

    public static TLRPC$InputPaymentCredentials TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputPaymentCredentials result = null;
        switch (constructor) {
            case -1056001329:
                result = new TLRPC$TL_inputPaymentCredentialsSaved();
                break;
            case 873977640:
                result = new TLRPC$TL_inputPaymentCredentials();
                break;
            case 2035705766:
                result = new TLRPC$TL_inputPaymentCredentialsAndroidPay();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputPaymentCredentials", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
