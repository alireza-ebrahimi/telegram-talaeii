package org.telegram.messenger.exoplayer2.extractor.mkv;

import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;

interface EbmlReaderOutput {
    void binaryElement(int i, int i2, ExtractorInput extractorInput);

    void endMasterElement(int i);

    void floatElement(int i, double d);

    int getElementType(int i);

    void integerElement(int i, long j);

    boolean isLevel1Element(int i);

    void startMasterElement(int i, long j, long j2);

    void stringElement(int i, String str);
}
