package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputGeoPoint;

public class TLRPC$TL_inputGeoPointEmpty extends InputGeoPoint {
    public static int constructor = -457104426;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
