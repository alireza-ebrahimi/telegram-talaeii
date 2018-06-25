package org.telegram.tgnet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import org.telegram.messenger.FileLog;

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

    public SerializedData(boolean calculate) {
        this.isOut = true;
        this.justCalc = false;
        if (!calculate) {
            this.outbuf = new ByteArrayOutputStream();
            this.out = new DataOutputStream(this.outbuf);
        }
        this.justCalc = calculate;
        this.len = 0;
    }

    public SerializedData(int size) {
        this.isOut = true;
        this.justCalc = false;
        this.outbuf = new ByteArrayOutputStream(size);
        this.out = new DataOutputStream(this.outbuf);
    }

    public SerializedData(byte[] data) {
        this.isOut = true;
        this.justCalc = false;
        this.isOut = false;
        this.inbuf = new ByteArrayInputStream(data);
        this.in = new DataInputStream(this.inbuf);
        this.len = 0;
    }

    public void cleanup() {
        try {
            if (this.inbuf != null) {
                this.inbuf.close();
                this.inbuf = null;
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        try {
            if (this.in != null) {
                this.in.close();
                this.in = null;
            }
        } catch (Exception e2) {
            FileLog.e(e2);
        }
        try {
            if (this.outbuf != null) {
                this.outbuf.close();
                this.outbuf = null;
            }
        } catch (Exception e22) {
            FileLog.e(e22);
        }
        try {
            if (this.out != null) {
                this.out.close();
                this.out = null;
            }
        } catch (Exception e222) {
            FileLog.e(e222);
        }
    }

    public SerializedData(File file) throws Exception {
        this.isOut = true;
        this.justCalc = false;
        FileInputStream is = new FileInputStream(file);
        byte[] data = new byte[((int) file.length())];
        new DataInputStream(is).readFully(data);
        is.close();
        this.isOut = false;
        this.inbuf = new ByteArrayInputStream(data);
        this.in = new DataInputStream(this.inbuf);
    }

    public void writeInt32(int x) {
        if (this.justCalc) {
            this.len += 4;
        } else {
            writeInt32(x, this.out);
        }
    }

    private void writeInt32(int x, DataOutputStream out) {
        int i = 0;
        while (i < 4) {
            try {
                out.write(x >> (i * 8));
                i++;
            } catch (Exception e) {
                FileLog.e("write int32 error");
                return;
            }
        }
    }

    public void writeInt64(long i) {
        if (this.justCalc) {
            this.len += 8;
        } else {
            writeInt64(i, this.out);
        }
    }

    private void writeInt64(long x, DataOutputStream out) {
        int i = 0;
        while (i < 8) {
            try {
                out.write((int) (x >> (i * 8)));
                i++;
            } catch (Exception e) {
                FileLog.e("write int64 error");
                return;
            }
        }
    }

    public void writeBool(boolean value) {
        if (this.justCalc) {
            this.len += 4;
        } else if (value) {
            writeInt32(-1720552011);
        } else {
            writeInt32(-1132882121);
        }
    }

    public void writeBytes(byte[] b) {
        try {
            if (this.justCalc) {
                this.len += b.length;
            } else {
                this.out.write(b);
            }
        } catch (Exception e) {
            FileLog.e("write raw error");
        }
    }

    public void writeBytes(byte[] b, int offset, int count) {
        try {
            if (this.justCalc) {
                this.len += count;
            } else {
                this.out.write(b, offset, count);
            }
        } catch (Exception e) {
            FileLog.e("write bytes error");
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
            FileLog.e("write byte error");
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
            FileLog.e("write byte error");
        }
    }

    public void writeByteArray(byte[] b) {
        try {
            if (b.length <= 253) {
                if (this.justCalc) {
                    this.len++;
                } else {
                    this.out.write(b.length);
                }
            } else if (this.justCalc) {
                this.len += 4;
            } else {
                this.out.write(254);
                this.out.write(b.length);
                this.out.write(b.length >> 8);
                this.out.write(b.length >> 16);
            }
            if (this.justCalc) {
                this.len += b.length;
            } else {
                this.out.write(b);
            }
            int i = b.length <= 253 ? 1 : 4;
            while ((b.length + i) % 4 != 0) {
                if (this.justCalc) {
                    this.len++;
                } else {
                    this.out.write(0);
                }
                i++;
            }
        } catch (Exception e) {
            FileLog.e("write byte array error");
        }
    }

    public void writeString(String s) {
        try {
            writeByteArray(s.getBytes("UTF-8"));
        } catch (Exception e) {
            FileLog.e("write string error");
        }
    }

    public void writeByteArray(byte[] b, int offset, int count) {
        if (count <= 253) {
            try {
                if (this.justCalc) {
                    this.len++;
                } else {
                    this.out.write(count);
                }
            } catch (Exception e) {
                FileLog.e("write byte array error");
                return;
            }
        } else if (this.justCalc) {
            this.len += 4;
        } else {
            this.out.write(254);
            this.out.write(count);
            this.out.write(count >> 8);
            this.out.write(count >> 16);
        }
        if (this.justCalc) {
            this.len += count;
        } else {
            this.out.write(b, offset, count);
        }
        int i = count <= 253 ? 1 : 4;
        while ((count + i) % 4 != 0) {
            if (this.justCalc) {
                this.len++;
            } else {
                this.out.write(0);
            }
            i++;
        }
    }

    public void writeDouble(double d) {
        try {
            writeInt64(Double.doubleToRawLongBits(d));
        } catch (Exception e) {
            FileLog.e("write double error");
        }
    }

    public int length() {
        if (this.justCalc) {
            return this.len;
        }
        return this.isOut ? this.outbuf.size() : this.inbuf.available();
    }

    protected void set(byte[] newData) {
        this.isOut = false;
        this.inbuf = new ByteArrayInputStream(newData);
        this.in = new DataInputStream(this.inbuf);
    }

    public byte[] toByteArray() {
        return this.outbuf.toByteArray();
    }

    public void skip(int count) {
        if (count != 0) {
            if (this.justCalc) {
                this.len += count;
            } else if (this.in != null) {
                try {
                    this.in.skipBytes(count);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        }
    }

    public int getPosition() {
        return this.len;
    }

    public boolean readBool(boolean exception) {
        int consructor = readInt32(exception);
        if (consructor == -1720552011) {
            return true;
        }
        if (consructor == -1132882121) {
            return false;
        }
        if (exception) {
            throw new RuntimeException("Not bool value!");
        }
        FileLog.e("Not bool value!");
        return false;
    }

    public void readBytes(byte[] b, boolean exception) {
        try {
            this.in.read(b);
            this.len += b.length;
        } catch (Exception e) {
            if (exception) {
                throw new RuntimeException("read bytes error", e);
            }
            FileLog.e("read bytes error");
        }
    }

    public byte[] readData(int count, boolean exception) {
        byte[] arr = new byte[count];
        readBytes(arr, exception);
        return arr;
    }

    public String readString(boolean exception) {
        int sl = 1;
        try {
            int l = this.in.read();
            this.len++;
            if (l >= 254) {
                l = (this.in.read() | (this.in.read() << 8)) | (this.in.read() << 16);
                this.len += 3;
                sl = 4;
            }
            byte[] b = new byte[l];
            this.in.read(b);
            this.len++;
            for (int i = sl; (l + i) % 4 != 0; i++) {
                this.in.read();
                this.len++;
            }
            return new String(b, "UTF-8");
        } catch (Exception e) {
            if (exception) {
                throw new RuntimeException("read string error", e);
            }
            FileLog.e("read string error");
            return null;
        }
    }

    public byte[] readByteArray(boolean exception) {
        int sl = 1;
        try {
            int l = this.in.read();
            this.len++;
            if (l >= 254) {
                l = (this.in.read() | (this.in.read() << 8)) | (this.in.read() << 16);
                this.len += 3;
                sl = 4;
            }
            byte[] bArr = new byte[l];
            this.in.read(bArr);
            this.len++;
            for (int i = sl; (l + i) % 4 != 0; i++) {
                this.in.read();
                this.len++;
            }
            return bArr;
        } catch (Exception e) {
            if (exception) {
                throw new RuntimeException("read byte array error", e);
            }
            FileLog.e("read byte array error");
            return null;
        }
    }

    public double readDouble(boolean exception) {
        try {
            return Double.longBitsToDouble(readInt64(exception));
        } catch (Exception e) {
            if (exception) {
                throw new RuntimeException("read double error", e);
            }
            FileLog.e("read double error");
            return 0.0d;
        }
    }

    public int readInt32(boolean exception) {
        int i = 0;
        int j = 0;
        while (j < 4) {
            try {
                i |= this.in.read() << (j * 8);
                this.len++;
                j++;
            } catch (Exception e) {
                if (exception) {
                    throw new RuntimeException("read int32 error", e);
                }
                FileLog.e("read int32 error");
                return 0;
            }
        }
        return i;
    }

    public long readInt64(boolean exception) {
        long i = 0;
        int j = 0;
        while (j < 8) {
            try {
                i |= ((long) this.in.read()) << (j * 8);
                this.len++;
                j++;
            } catch (Exception e) {
                if (exception) {
                    throw new RuntimeException("read int64 error", e);
                }
                FileLog.e("read int64 error");
                return 0;
            }
        }
        return i;
    }

    public void writeByteBuffer(NativeByteBuffer buffer) {
    }

    public NativeByteBuffer readByteBuffer(boolean exception) {
        return null;
    }
}
