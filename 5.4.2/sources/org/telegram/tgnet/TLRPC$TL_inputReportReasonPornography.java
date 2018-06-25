package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ReportReason;

public class TLRPC$TL_inputReportReasonPornography extends ReportReason {
    public static int constructor = 777640226;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
