package org.telegram.tgnet;

public abstract class TLRPC$payments_PaymentResult extends TLObject {
    public TLRPC$Updates updates;
    public String url;

    public static TLRPC$payments_PaymentResult TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$payments_PaymentResult result = null;
        switch (constructor) {
            case 1314881805:
                result = new TLRPC$TL_payments_paymentResult();
                break;
            case 1800845601:
                result = new TLRPC$TL_payments_paymentVerficationNeeded();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in payments_PaymentResult", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
