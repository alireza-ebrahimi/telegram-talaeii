package com.googlecode.mp4parser.authoring.tracks.h265;

import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.BitReaderBuffer;
import java.io.IOException;

public class SEIMessage {
    public SEIMessage(BitReaderBuffer bsr) throws IOException {
        int payloadType = 0;
        while (((long) bsr.readBits(8)) == 255) {
            payloadType += 255;
        }
        payloadType += bsr.readBits(8);
        do {
        } while (((long) bsr.readBits(8)) == 255);
        int last_payload_size_byte = bsr.readBits(8);
        System.err.println("payloadType " + payloadType);
    }
}
