package org.telegram.tgnet;

public class TLRPC$TL_inputReportReasonViolence extends TLRPC$ReportReason {
    public static int constructor = 505595789;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
