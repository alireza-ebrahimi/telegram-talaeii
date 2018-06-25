package org.telegram.tgnet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.exoplayer2.C3446C;

public class NativeByteBuffer extends AbstractSerializedData {
    private static final ThreadLocal<NativeByteBuffer> addressWrapper = new C37891();
    protected int address;
    public ByteBuffer buffer;
    private boolean justCalc;
    private int len;
    public boolean reused;

    /* renamed from: org.telegram.tgnet.NativeByteBuffer$1 */
    static class C37891 extends ThreadLocal<NativeByteBuffer> {
        C37891() {
        }

        protected NativeByteBuffer initialValue() {
            return new NativeByteBuffer(0, true);
        }
    }

    public NativeByteBuffer(int i) {
        this.reused = true;
        if (i >= 0) {
            this.address = native_getFreeBuffer(i);
            if (this.address != 0) {
                this.buffer = native_getJavaByteBuffer(this.address);
                this.buffer.position(0);
                this.buffer.limit(i);
                this.buffer.order(ByteOrder.LITTLE_ENDIAN);
                return;
            }
            return;
        }
        throw new Exception("invalid NativeByteBuffer size");
    }

    private NativeByteBuffer(int i, boolean z) {
        this.reused = true;
    }

    public NativeByteBuffer(boolean z) {
        this.reused = true;
        this.justCalc = z;
    }

    public static native int native_getFreeBuffer(int i);

    public static native ByteBuffer native_getJavaByteBuffer(int i);

    public static native int native_limit(int i);

    public static native int native_position(int i);

    public static native void native_reuse(int i);

    public static NativeByteBuffer wrap(int i) {
        NativeByteBuffer nativeByteBuffer = (NativeByteBuffer) addressWrapper.get();
        if (i != 0) {
            if (!nativeByteBuffer.reused) {
                FileLog.m13726e("forgot to reuse?");
            }
            nativeByteBuffer.address = i;
            nativeByteBuffer.reused = false;
            nativeByteBuffer.buffer = native_getJavaByteBuffer(i);
            nativeByteBuffer.buffer.limit(native_limit(i));
            int native_position = native_position(i);
            if (native_position <= nativeByteBuffer.buffer.limit()) {
                nativeByteBuffer.buffer.position(native_position);
            }
            nativeByteBuffer.buffer.order(ByteOrder.LITTLE_ENDIAN);
        }
        return nativeByteBuffer;
    }

    public int capacity() {
        return this.buffer.capacity();
    }

    public void compact() {
        this.buffer.compact();
    }

    public int getIntFromByte(byte b) {
        return b >= (byte) 0 ? b : b + 256;
    }

    public int getPosition() {
        return this.buffer.position();
    }

    public boolean hasRemaining() {
        return this.buffer.hasRemaining();
    }

    public int length() {
        return !this.justCalc ? this.buffer.position() : this.len;
    }

    public int limit() {
        return this.buffer.limit();
    }

    public void limit(int i) {
        this.buffer.limit(i);
    }

    public int position() {
        return this.buffer.position();
    }

    public void position(int i) {
        this.buffer.position(i);
    }

    public void put(ByteBuffer byteBuffer) {
        this.buffer.put(byteBuffer);
    }

    public boolean readBool(boolean z) {
        int readInt32 = readInt32(z);
        if (readInt32 == -1720552011) {
            return true;
        }
        if (readInt32 == -1132882121) {
            return false;
        }
        if (z) {
            throw new RuntimeException("Not bool value!");
        }
        FileLog.m13726e("Not bool value!");
        return false;
    }

    public byte[] readByteArray(boolean z) {
        int i = 1;
        try {
            int intFromByte;
            int intFromByte2 = getIntFromByte(this.buffer.get());
            if (intFromByte2 >= 254) {
                i = 4;
                intFromByte = (getIntFromByte(this.buffer.get()) | (getIntFromByte(this.buffer.get()) << 8)) | (getIntFromByte(this.buffer.get()) << 16);
            } else {
                intFromByte = intFromByte2;
            }
            byte[] bArr = new byte[intFromByte];
            this.buffer.get(bArr);
            for (i = 
/*
Method generation error in method: org.telegram.tgnet.NativeByteBuffer.readByteArray(boolean):byte[], dex: classes.dex
jadx.core.utils.exceptions.CodegenException: Error generate insn: PHI: (r1_10 'i' int) = (r1_9 'i' int), (r1_0 'i' int) binds: {(r1_9 'i' int)=B:4:0x000f, (r1_0 'i' int)=B:15:0x0060} in method: org.telegram.tgnet.NativeByteBuffer.readByteArray(boolean):byte[], dex: classes.dex
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:184)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:61)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:279)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:187)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:320)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:257)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:220)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:110)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:75)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:12)
	at jadx.core.ProcessClass.process(ProcessClass.java:40)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
Caused by: jadx.core.utils.exceptions.CodegenException: PHI can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:537)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:509)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 20 more

*/

            public NativeByteBuffer readByteBuffer(boolean z) {
                int i = 1;
                try {
                    int intFromByte;
                    int intFromByte2 = getIntFromByte(this.buffer.get());
                    if (intFromByte2 >= 254) {
                        i = 4;
                        intFromByte = (getIntFromByte(this.buffer.get()) | (getIntFromByte(this.buffer.get()) << 8)) | (getIntFromByte(this.buffer.get()) << 16);
                    } else {
                        intFromByte = intFromByte2;
                    }
                    NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(intFromByte);
                    int limit = this.buffer.limit();
                    this.buffer.limit(this.buffer.position() + intFromByte);
                    nativeByteBuffer.buffer.put(this.buffer);
                    this.buffer.limit(limit);
                    nativeByteBuffer.buffer.position(0);
                    for (i = 
/*
Method generation error in method: org.telegram.tgnet.NativeByteBuffer.readByteBuffer(boolean):org.telegram.tgnet.NativeByteBuffer, dex: classes.dex
jadx.core.utils.exceptions.CodegenException: Error generate insn: PHI: (r1_10 'i' int) = (r1_9 'i' int), (r1_0 'i' int) binds: {(r1_9 'i' int)=B:4:0x000f, (r1_0 'i' int)=B:15:0x0080} in method: org.telegram.tgnet.NativeByteBuffer.readByteBuffer(boolean):org.telegram.tgnet.NativeByteBuffer, dex: classes.dex
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:184)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:61)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:279)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:187)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:320)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:257)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:220)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:110)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:75)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:12)
	at jadx.core.ProcessClass.process(ProcessClass.java:40)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
Caused by: jadx.core.utils.exceptions.CodegenException: PHI can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:537)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:509)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 20 more

*/

                    public void readBytes(byte[] bArr, boolean z) {
                        try {
                            this.buffer.get(bArr);
                        } catch (Throwable e) {
                            if (z) {
                                throw new RuntimeException("read raw error", e);
                            }
                            FileLog.m13726e("read raw error");
                        }
                    }

                    public byte[] readData(int i, boolean z) {
                        byte[] bArr = new byte[i];
                        readBytes(bArr, z);
                        return bArr;
                    }

                    public double readDouble(boolean z) {
                        try {
                            return Double.longBitsToDouble(readInt64(z));
                        } catch (Throwable e) {
                            if (z) {
                                throw new RuntimeException("read double error", e);
                            }
                            FileLog.m13726e("read double error");
                            return 0.0d;
                        }
                    }

                    public int readInt32(boolean z) {
                        try {
                            return this.buffer.getInt();
                        } catch (Throwable e) {
                            if (z) {
                                throw new RuntimeException("read int32 error", e);
                            }
                            FileLog.m13726e("read int32 error");
                            return 0;
                        }
                    }

                    public long readInt64(boolean z) {
                        try {
                            return this.buffer.getLong();
                        } catch (Throwable e) {
                            if (z) {
                                throw new RuntimeException("read int64 error", e);
                            }
                            FileLog.m13726e("read int64 error");
                            return 0;
                        }
                    }

                    public String readString(boolean z) {
                        int position = getPosition();
                        try {
                            int intFromByte;
                            int intFromByte2 = getIntFromByte(this.buffer.get());
                            if (intFromByte2 >= 254) {
                                intFromByte2 = 4;
                                intFromByte = (getIntFromByte(this.buffer.get()) | (getIntFromByte(this.buffer.get()) << 8)) | (getIntFromByte(this.buffer.get()) << 16);
                            } else {
                                int i = intFromByte2;
                                intFromByte2 = 1;
                                intFromByte = i;
                            }
                            byte[] bArr = new byte[intFromByte];
                            this.buffer.get(bArr);
                            for (intFromByte2 = 
/*
Method generation error in method: org.telegram.tgnet.NativeByteBuffer.readString(boolean):java.lang.String, dex: classes.dex
jadx.core.utils.exceptions.CodegenException: Error generate insn: PHI: (r0_9 'intFromByte2' int) = (r0_8 'intFromByte2' int), (r0_13 'intFromByte2' int) binds: {(r0_13 'intFromByte2' int)=B:15:0x0072, (r0_8 'intFromByte2' int)=B:4:0x0013} in method: org.telegram.tgnet.NativeByteBuffer.readString(boolean):java.lang.String, dex: classes.dex
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:184)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:61)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:279)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:187)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:320)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:257)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:220)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:110)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:75)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:12)
	at jadx.core.ProcessClass.process(ProcessClass.java:40)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
Caused by: jadx.core.utils.exceptions.CodegenException: PHI can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:537)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:509)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 20 more

*/

                            public void reuse() {
                                if (this.address != 0) {
                                    this.reused = true;
                                    native_reuse(this.address);
                                }
                            }

                            public void rewind() {
                                if (this.justCalc) {
                                    this.len = 0;
                                } else {
                                    this.buffer.rewind();
                                }
                            }

                            public void skip(int i) {
                                if (i != 0) {
                                    if (this.justCalc) {
                                        this.len += i;
                                    } else {
                                        this.buffer.position(this.buffer.position() + i);
                                    }
                                }
                            }

                            public void writeBool(boolean z) {
                                if (this.justCalc) {
                                    this.len += 4;
                                } else if (z) {
                                    writeInt32(-1720552011);
                                } else {
                                    writeInt32(-1132882121);
                                }
                            }

                            public void writeByte(byte b) {
                                try {
                                    if (this.justCalc) {
                                        this.len++;
                                    } else {
                                        this.buffer.put(b);
                                    }
                                } catch (Exception e) {
                                    FileLog.m13726e("write byte error");
                                }
                            }

                            public void writeByte(int i) {
                                writeByte((byte) i);
                            }

                            public void writeByteArray(byte[] bArr) {
                                try {
                                    if (bArr.length <= 253) {
                                        if (this.justCalc) {
                                            this.len++;
                                        } else {
                                            this.buffer.put((byte) bArr.length);
                                        }
                                    } else if (this.justCalc) {
                                        this.len += 4;
                                    } else {
                                        this.buffer.put((byte) -2);
                                        this.buffer.put((byte) bArr.length);
                                        this.buffer.put((byte) (bArr.length >> 8));
                                        this.buffer.put((byte) (bArr.length >> 16));
                                    }
                                    if (this.justCalc) {
                                        this.len += bArr.length;
                                    } else {
                                        this.buffer.put(bArr);
                                    }
                                    int i = bArr.length <= 253 ? 1 : 4;
                                    while ((bArr.length + i) % 4 != 0) {
                                        if (this.justCalc) {
                                            this.len++;
                                        } else {
                                            this.buffer.put((byte) 0);
                                        }
                                        i++;
                                    }
                                } catch (Exception e) {
                                    FileLog.m13726e("write byte array error");
                                }
                            }

                            public void writeByteArray(byte[] bArr, int i, int i2) {
                                if (i2 <= 253) {
                                    try {
                                        if (this.justCalc) {
                                            this.len++;
                                        } else {
                                            this.buffer.put((byte) i2);
                                        }
                                    } catch (Exception e) {
                                        FileLog.m13726e("write byte array error");
                                        return;
                                    }
                                } else if (this.justCalc) {
                                    this.len += 4;
                                } else {
                                    this.buffer.put((byte) -2);
                                    this.buffer.put((byte) i2);
                                    this.buffer.put((byte) (i2 >> 8));
                                    this.buffer.put((byte) (i2 >> 16));
                                }
                                if (this.justCalc) {
                                    this.len += i2;
                                } else {
                                    this.buffer.put(bArr, i, i2);
                                }
                                int i3 = i2 <= 253 ? 1 : 4;
                                while ((i2 + i3) % 4 != 0) {
                                    if (this.justCalc) {
                                        this.len++;
                                    } else {
                                        this.buffer.put((byte) 0);
                                    }
                                    i3++;
                                }
                            }

                            public void writeByteBuffer(NativeByteBuffer nativeByteBuffer) {
                                try {
                                    int limit = nativeByteBuffer.limit();
                                    if (limit <= 253) {
                                        if (this.justCalc) {
                                            this.len++;
                                        } else {
                                            this.buffer.put((byte) limit);
                                        }
                                    } else if (this.justCalc) {
                                        this.len += 4;
                                    } else {
                                        this.buffer.put((byte) -2);
                                        this.buffer.put((byte) limit);
                                        this.buffer.put((byte) (limit >> 8));
                                        this.buffer.put((byte) (limit >> 16));
                                    }
                                    if (this.justCalc) {
                                        this.len += limit;
                                    } else {
                                        nativeByteBuffer.rewind();
                                        this.buffer.put(nativeByteBuffer.buffer);
                                    }
                                    int i = limit <= 253 ? 1 : 4;
                                    while ((limit + i) % 4 != 0) {
                                        if (this.justCalc) {
                                            this.len++;
                                        } else {
                                            this.buffer.put((byte) 0);
                                        }
                                        i++;
                                    }
                                } catch (Throwable e) {
                                    FileLog.m13728e(e);
                                }
                            }

                            public void writeBytes(NativeByteBuffer nativeByteBuffer) {
                                if (this.justCalc) {
                                    this.len += nativeByteBuffer.limit();
                                    return;
                                }
                                nativeByteBuffer.rewind();
                                this.buffer.put(nativeByteBuffer.buffer);
                            }

                            public void writeBytes(byte[] bArr) {
                                try {
                                    if (this.justCalc) {
                                        this.len += bArr.length;
                                    } else {
                                        this.buffer.put(bArr);
                                    }
                                } catch (Exception e) {
                                    FileLog.m13726e("write raw error");
                                }
                            }

                            public void writeBytes(byte[] bArr, int i, int i2) {
                                try {
                                    if (this.justCalc) {
                                        this.len += i2;
                                    } else {
                                        this.buffer.put(bArr, i, i2);
                                    }
                                } catch (Exception e) {
                                    FileLog.m13726e("write raw error");
                                }
                            }

                            public void writeDouble(double d) {
                                try {
                                    writeInt64(Double.doubleToRawLongBits(d));
                                } catch (Exception e) {
                                    FileLog.m13726e("write double error");
                                }
                            }

                            public void writeInt32(int i) {
                                try {
                                    if (this.justCalc) {
                                        this.len += 4;
                                    } else {
                                        this.buffer.putInt(i);
                                    }
                                } catch (Exception e) {
                                    FileLog.m13726e("write int32 error");
                                }
                            }

                            public void writeInt64(long j) {
                                try {
                                    if (this.justCalc) {
                                        this.len += 8;
                                    } else {
                                        this.buffer.putLong(j);
                                    }
                                } catch (Exception e) {
                                    FileLog.m13726e("write int64 error");
                                }
                            }

                            public void writeString(String str) {
                                try {
                                    writeByteArray(str.getBytes(C3446C.UTF8_NAME));
                                } catch (Exception e) {
                                    FileLog.m13726e("write string error");
                                }
                            }
                        }
