package org.telegram.tgnet;

public class TLRPC$TL_inputReportReasonOther extends TLRPC$ReportReason {
    public static int constructor = -512463606;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.text = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.text);
    }
}
