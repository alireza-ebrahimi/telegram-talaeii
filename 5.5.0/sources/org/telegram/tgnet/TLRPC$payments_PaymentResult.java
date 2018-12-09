package org.telegram.tgnet;

public abstract class TLRPC$payments_PaymentResult extends TLObject {
    public TLRPC$Updates updates;
    public String url;

    public static TLRPC$payments_PaymentResult TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$payments_PaymentResult tLRPC$payments_PaymentResult = null;
        switch (i) {
            case 1314881805:
                tLRPC$payments_PaymentResult = new TLRPC$TL_payments_paymentResult();
                break;
            case 1800845601:
                tLRPC$payments_PaymentResult = new TLRPC$TL_payments_paymentVerficationNeeded();
                break;
        }
        if (tLRPC$payments_PaymentResult == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in payments_PaymentResult", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$payments_PaymentResult != null) {
            tLRPC$payments_PaymentResult.readParams(abstractSerializedData, z);
        }
        return tLRPC$payments_PaymentResult;
    }
}
