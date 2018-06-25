package org.telegram.tgnet;

public abstract class TLRPC$ReportReason extends TLObject {
    public String text;

    public static TLRPC$ReportReason TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$ReportReason result = null;
        switch (constructor) {
            case -512463606:
                result = new TLRPC$TL_inputReportReasonOther();
                break;
            case 505595789:
                result = new TLRPC$TL_inputReportReasonViolence();
                break;
            case 777640226:
                result = new TLRPC$TL_inputReportReasonPornography();
                break;
            case 1490799288:
                result = new TLRPC$TL_inputReportReasonSpam();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in ReportReason", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
