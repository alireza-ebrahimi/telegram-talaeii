package org.telegram.tgnet;

public class TLRPC$TL_inputReportReasonPornography extends TLRPC$ReportReason {
    public static int constructor = 777640226;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
