package org.telegram.tgnet;

class TLObject$1 extends ThreadLocal<NativeByteBuffer> {
    TLObject$1() {
    }

    protected NativeByteBuffer initialValue() {
        return new NativeByteBuffer(true);
    }
}
