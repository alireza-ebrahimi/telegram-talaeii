package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ReportReason;

public class TLRPC$TL_inputReportReasonViolence extends ReportReason {
    public static int constructor = 505595789;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
