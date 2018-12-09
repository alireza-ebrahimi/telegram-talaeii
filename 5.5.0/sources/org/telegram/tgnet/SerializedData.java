package org.telegram.tgnet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.exoplayer2.C3446C;

public class SerializedData extends AbstractSerializedData {
    private DataInputStream in;
    private ByteArrayInputStream inbuf;
    protected boolean isOut;
    private boolean justCalc;
    private int len;
    private DataOutputStream out;
    private ByteArrayOutputStream outbuf;

    public SerializedData() {
        this.isOut = true;
        this.justCalc = false;
        this.outbuf = new ByteArrayOutputStream();
        this.out = new DataOutputStream(this.outbuf);
    }

    public SerializedData(int i) {
        this.isOut = true;
        this.justCalc = false;
        this.outbuf = new ByteArrayOutputStream(i);
        this.out = new DataOutputStream(this.outbuf);
    }

    public SerializedData(File file) {
        this.isOut = true;
        this.justCalc = false;
        InputStream fileInputStream = new FileInputStream(file);
        byte[] bArr = new byte[((int) file.length())];
        new DataInputStream(fileInputStream).readFully(bArr);
        fileInputStream.close();
        this.isOut = false;
        this.inbuf = new ByteArrayInputStream(bArr);
        this.in = new DataInputStream(this.inbuf);
    }

    public SerializedData(boolean z) {
        this.isOut = true;
        this.justCalc = false;
        if (!z) {
            this.outbuf = new ByteArrayOutputStream();
            this.out = new DataOutputStream(this.outbuf);
        }
        this.justCalc = z;
        this.len = 0;
    }

    public SerializedData(byte[] bArr) {
        this.isOut = true;
        this.justCalc = false;
        this.isOut = false;
        this.inbuf = new ByteArrayInputStream(bArr);
        this.in = new DataInputStream(this.inbuf);
        this.len = 0;
    }

    private void writeInt32(int i, DataOutputStream dataOutputStream) {
        int i2 = 0;
        while (i2 < 4) {
            try {
                dataOutputStream.write(i >> (i2 * 8));
                i2++;
            } catch (Exception e) {
                FileLog.m13726e("write int32 error");
                return;
            }
        }
    }

    private void writeInt64(long j, DataOutputStream dataOutputStream) {
        int i = 0;
        while (i < 8) {
            try {
                dataOutputStream.write((int) (j >> (i * 8)));
                i++;
            } catch (Exception e) {
                FileLog.m13726e("write int64 error");
                return;
            }
        }
    }

    public void cleanup() {
        try {
            if (this.inbuf != null) {
                this.inbuf.close();
                this.inbuf = null;
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        try {
            if (this.in != null) {
                this.in.close();
                this.in = null;
            }
        } catch (Throwable e2) {
            FileLog.m13728e(e2);
        }
        try {
            if (this.outbuf != null) {
                this.outbuf.close();
                this.outbuf = null;
            }
        } catch (Throwable e22) {
            FileLog.m13728e(e22);
        }
        try {
            if (this.out != null) {
                this.out.close();
                this.out = null;
            }
        } catch (Throwable e222) {
            FileLog.m13728e(e222);
        }
    }

    public int getPosition() {
        return this.len;
    }

    public int length() {
        return !this.justCalc ? this.isOut ? this.outbuf.size() : this.inbuf.available() : this.len;
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
            int i2;
            int read = this.in.read();
            this.len++;
            if (read >= 254) {
                read = (this.in.read() | (this.in.read() << 8)) | (this.in.read() << 16);
                this.len += 3;
                i = 4;
                i2 = read;
            } else {
                i2 = read;
            }
            byte[] bArr = new byte[i2];
            this.in.read(bArr);
            this.len++;
            for (i = 
/*
Method generation error in method: org.telegram.tgnet.SerializedData.readByteArray(boolean):byte[], dex: classes.dex
jadx.core.utils.exceptions.CodegenException: Error generate insn: PHI: (r1_10 'i' int) = (r1_9 'i' int), (r1_0 'i' int) binds: {(r1_9 'i' int)=B:4:0x0011, (r1_0 'i' int)=B:15:0x0066} in method: org.telegram.tgnet.SerializedData.readByteArray(boolean):byte[], dex: classes.dex
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
                return null;
            }

            public void readBytes(byte[] bArr, boolean z) {
                try {
                    this.in.read(bArr);
                    this.len += bArr.length;
                } catch (Throwable e) {
                    if (z) {
                        throw new RuntimeException("read bytes error", e);
                    }
                    FileLog.m13726e("read bytes error");
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
                int i = 0;
                int i2 = 0;
                while (i < 4) {
                    try {
                        int read = (this.in.read() << (i * 8)) | i2;
                        this.len++;
                        i++;
                        i2 = read;
                    } catch (Throwable e) {
                        if (z) {
                            throw new RuntimeException("read int32 error", e);
                        }
                        FileLog.m13726e("read int32 error");
                        return 0;
                    }
                }
                return i2;
            }

            public long readInt64(boolean z) {
                int i = 0;
                long j = 0;
                while (i < 8) {
                    try {
                        long read = (((long) this.in.read()) << (i * 8)) | j;
                        this.len++;
                        i++;
                        j = read;
                    } catch (Throwable e) {
                        if (z) {
                            throw new RuntimeException("read int64 error", e);
                        }
                        FileLog.m13726e("read int64 error");
                        return 0;
                    }
                }
                return j;
            }

            public String readString(boolean z) {
                try {
                    int i;
                    int read = this.in.read();
                    this.len++;
                    int i2;
                    if (read >= 254) {
                        read = (this.in.read() | (this.in.read() << 8)) | (this.in.read() << 16);
                        this.len += 3;
                        i2 = read;
                        read = 4;
                        i = i2;
                    } else {
                        i2 = read;
                        read = 1;
                        i = i2;
                    }
                    byte[] bArr = new byte[i];
                    this.in.read(bArr);
                    this.len++;
                    for (read = 
/*
Method generation error in method: org.telegram.tgnet.SerializedData.readString(boolean):java.lang.String, dex: classes.dex
jadx.core.utils.exceptions.CodegenException: Error generate insn: PHI: (r0_7 'read' int) = (r0_6 'read' int), (r0_11 'read' int) binds: {(r0_6 'read' int)=B:4:0x0011, (r0_11 'read' int)=B:15:0x0071} in method: org.telegram.tgnet.SerializedData.readString(boolean):java.lang.String, dex: classes.dex
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

                    protected void set(byte[] bArr) {
                        this.isOut = false;
                        this.inbuf = new ByteArrayInputStream(bArr);
                        this.in = new DataInputStream(this.inbuf);
                    }

                    public void skip(int i) {
                        if (i != 0) {
                            if (this.justCalc) {
                                this.len += i;
                            } else if (this.in != null) {
                                try {
                                    this.in.skipBytes(i);
                                } catch (Throwable e) {
                                    FileLog.m13728e(e);
                                }
                            }
                        }
                    }

                    public byte[] toByteArray() {
                        return this.outbuf.toByteArray();
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
                                this.out.writeByte(b);
                            }
                        } catch (Exception e) {
                            FileLog.m13726e("write byte error");
                        }
                    }

                    public void writeByte(int i) {
                        try {
                            if (this.justCalc) {
                                this.len++;
                            } else {
                                this.out.writeByte((byte) i);
                            }
                        } catch (Exception e) {
                            FileLog.m13726e("write byte error");
                        }
                    }

                    public void writeByteArray(byte[] bArr) {
                        try {
                            if (bArr.length <= 253) {
                                if (this.justCalc) {
                                    this.len++;
                                } else {
                                    this.out.write(bArr.length);
                                }
                            } else if (this.justCalc) {
                                this.len += 4;
                            } else {
                                this.out.write(254);
                                this.out.write(bArr.length);
                                this.out.write(bArr.length >> 8);
                                this.out.write(bArr.length >> 16);
                            }
                            if (this.justCalc) {
                                this.len += bArr.length;
                            } else {
                                this.out.write(bArr);
                            }
                            int i = bArr.length <= 253 ? 1 : 4;
                            while ((bArr.length + i) % 4 != 0) {
                                if (this.justCalc) {
                                    this.len++;
                                } else {
                                    this.out.write(0);
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
                                    this.out.write(i2);
                                }
                            } catch (Exception e) {
                                FileLog.m13726e("write byte array error");
                                return;
                            }
                        } else if (this.justCalc) {
                            this.len += 4;
                        } else {
                            this.out.write(254);
                            this.out.write(i2);
                            this.out.write(i2 >> 8);
                            this.out.write(i2 >> 16);
                        }
                        if (this.justCalc) {
                            this.len += i2;
                        } else {
                            this.out.write(bArr, i, i2);
                        }
                        int i3 = i2 <= 253 ? 1 : 4;
                        while ((i2 + i3) % 4 != 0) {
                            if (this.justCalc) {
                                this.len++;
                            } else {
                                this.out.write(0);
                            }
                            i3++;
                        }
                    }

                    public void writeByteBuffer(NativeByteBuffer nativeByteBuffer) {
                    }

                    public void writeBytes(byte[] bArr) {
                        try {
                            if (this.justCalc) {
                                this.len += bArr.length;
                            } else {
                                this.out.write(bArr);
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
                                this.out.write(bArr, i, i2);
                            }
                        } catch (Exception e) {
                            FileLog.m13726e("write bytes error");
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
                        if (this.justCalc) {
                            this.len += 4;
                        } else {
                            writeInt32(i, this.out);
                        }
                    }

                    public void writeInt64(long j) {
                        if (this.justCalc) {
                            this.len += 8;
                        } else {
                            writeInt64(j, this.out);
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
