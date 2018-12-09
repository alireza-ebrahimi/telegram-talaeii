package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ReportReason;

public class TLRPC$TL_inputReportReasonOther extends ReportReason {
    public static int constructor = -512463606;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.text = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.text);
    }
}
