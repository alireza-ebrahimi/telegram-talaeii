package com.googlecode.mp4parser.contentprotection;

import com.googlecode.mp4parser.boxes.piff.ProtectionSpecificHeader;
import java.nio.ByteBuffer;
import java.util.UUID;
import utils.view.Constants;

public class GenericHeader extends ProtectionSpecificHeader {
    public static UUID PROTECTION_SYSTEM_ID = UUID.fromString(Constants.EMPTY_GUID);
    ByteBuffer data;

    static {
        ProtectionSpecificHeader.uuidRegistry.put(PROTECTION_SYSTEM_ID, GenericHeader.class);
    }

    public UUID getSystemId() {
        return PROTECTION_SYSTEM_ID;
    }

    public void parse(ByteBuffer buffer) {
        this.data = buffer;
    }

    public ByteBuffer getData() {
        return this.data;
    }
}
