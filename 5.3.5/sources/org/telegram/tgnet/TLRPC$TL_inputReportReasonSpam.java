package org.telegram.tgnet;

public class TLRPC$TL_inputReportReasonSpam extends TLRPC$ReportReason {
    public static int constructor = 1490799288;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
