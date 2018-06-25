package com.googlecode.mp4parser.boxes.microsoft;

import com.googlecode.mp4parser.AbstractBox;
import com.googlecode.mp4parser.RequiresParseDetailAspect;
import com.thin.downloadmanager.BuildConfig;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import org.apache.commons.lang3.CharEncoding;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.runtime.internal.Conversions;
import org.aspectj.runtime.reflect.Factory;

public class XtraBox extends AbstractBox {
    private static final long FILETIME_EPOCH_DIFF = 11644473600000L;
    private static final long FILETIME_ONE_MILLISECOND = 10000;
    public static final int MP4_XTRA_BT_FILETIME = 21;
    public static final int MP4_XTRA_BT_GUID = 72;
    public static final int MP4_XTRA_BT_INT64 = 19;
    public static final int MP4_XTRA_BT_UNICODE = 8;
    public static final String TYPE = "Xtra";
    private static final /* synthetic */ StaticPart ajc$tjp_0 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_1 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_10 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_2 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_3 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_4 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_5 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_6 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_7 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_8 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_9 = null;
    ByteBuffer data;
    private boolean successfulParse = false;
    Vector<XtraTag> tags = new Vector();

    private static class XtraTag {
        private int inputSize;
        private String tagName;
        private Vector<XtraValue> values;

        private XtraTag() {
            this.values = new Vector();
        }

        private XtraTag(String name) {
            this();
            this.tagName = name;
        }

        private void parse(ByteBuffer content) {
            this.inputSize = content.getInt();
            this.tagName = XtraBox.readAsciiString(content, content.getInt());
            int count = content.getInt();
            for (int i = 0; i < count; i++) {
                XtraValue val = new XtraValue();
                val.parse(content);
                this.values.addElement(val);
            }
            if (this.inputSize != getContentSize()) {
                throw new RuntimeException("Improperly handled Xtra tag: Sizes don't match ( " + this.inputSize + "/" + getContentSize() + ") on " + this.tagName);
            }
        }

        private void getContent(ByteBuffer b) {
            b.putInt(getContentSize());
            b.putInt(this.tagName.length());
            XtraBox.writeAsciiString(b, this.tagName);
            b.putInt(this.values.size());
            for (int i = 0; i < this.values.size(); i++) {
                ((XtraValue) this.values.elementAt(i)).getContent(b);
            }
        }

        private int getContentSize() {
            int size = this.tagName.length() + 12;
            for (int i = 0; i < this.values.size(); i++) {
                size += ((XtraValue) this.values.elementAt(i)).getContentSize();
            }
            return size;
        }

        public String toString() {
            StringBuffer b = new StringBuffer();
            b.append(this.tagName);
            b.append(" [");
            b.append(this.inputSize);
            b.append("/");
            b.append(this.values.size());
            b.append("]:\n");
            for (int i = 0; i < this.values.size(); i++) {
                b.append("  ");
                b.append(((XtraValue) this.values.elementAt(i)).toString());
                b.append(LogCollector.LINE_SEPARATOR);
            }
            return b.toString();
        }
    }

    private static class XtraValue {
        public Date fileTimeValue;
        public long longValue;
        public byte[] nonParsedValue;
        public String stringValue;
        public int type;

        private XtraValue() {
        }

        private XtraValue(String val) {
            this.type = 8;
            this.stringValue = val;
        }

        private XtraValue(long longVal) {
            this.type = 19;
            this.longValue = longVal;
        }

        private XtraValue(Date time) {
            this.type = 21;
            this.fileTimeValue = time;
        }

        private Object getValueAsObject() {
            switch (this.type) {
                case 8:
                    return this.stringValue;
                case 19:
                    return new Long(this.longValue);
                case 21:
                    return this.fileTimeValue;
                default:
                    return this.nonParsedValue;
            }
        }

        private void parse(ByteBuffer content) {
            int length = content.getInt() - 6;
            this.type = content.getShort();
            content.order(ByteOrder.LITTLE_ENDIAN);
            switch (this.type) {
                case 8:
                    this.stringValue = XtraBox.readUtf16String(content, length);
                    break;
                case 19:
                    this.longValue = content.getLong();
                    break;
                case 21:
                    this.fileTimeValue = new Date(XtraBox.filetimeToMillis(content.getLong()));
                    break;
                default:
                    this.nonParsedValue = new byte[length];
                    content.get(this.nonParsedValue);
                    break;
            }
            content.order(ByteOrder.BIG_ENDIAN);
        }

        private void getContent(ByteBuffer b) {
            try {
                b.putInt(getContentSize());
                b.putShort((short) this.type);
                b.order(ByteOrder.LITTLE_ENDIAN);
                switch (this.type) {
                    case 8:
                        XtraBox.writeUtf16String(b, this.stringValue);
                        break;
                    case 19:
                        b.putLong(this.longValue);
                        break;
                    case 21:
                        b.putLong(XtraBox.millisToFiletime(this.fileTimeValue.getTime()));
                        break;
                    default:
                        b.put(this.nonParsedValue);
                        break;
                }
                b.order(ByteOrder.BIG_ENDIAN);
            } catch (Throwable th) {
                b.order(ByteOrder.BIG_ENDIAN);
            }
        }

        private int getContentSize() {
            switch (this.type) {
                case 8:
                    return 6 + ((this.stringValue.length() * 2) + 2);
                case 19:
                case 21:
                    return 6 + 8;
                default:
                    return 6 + this.nonParsedValue.length;
            }
        }

        public String toString() {
            switch (this.type) {
                case 8:
                    return "[string]" + this.stringValue;
                case 19:
                    return "[long]" + String.valueOf(this.longValue);
                case 21:
                    return "[filetime]" + this.fileTimeValue.toString();
                default:
                    return "[GUID](nonParsed)";
            }
        }
    }

    static {
        ajc$preClinit();
    }

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("XtraBox.java", XtraBox.class);
        ajc$tjp_0 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, factory.makeMethodSig(BuildConfig.VERSION_NAME, "toString", "com.googlecode.mp4parser.boxes.microsoft.XtraBox", "", "", "", "java.lang.String"), 88);
        ajc$tjp_1 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, factory.makeMethodSig(BuildConfig.VERSION_NAME, "getAllTagNames", "com.googlecode.mp4parser.boxes.microsoft.XtraBox", "", "", "", "[Ljava.lang.String;"), 151);
        ajc$tjp_10 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, factory.makeMethodSig(BuildConfig.VERSION_NAME, "setTagValue", "com.googlecode.mp4parser.boxes.microsoft.XtraBox", "java.lang.String:long", "name:value", "", "void"), 289);
        ajc$tjp_2 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, factory.makeMethodSig(BuildConfig.VERSION_NAME, "getFirstStringValue", "com.googlecode.mp4parser.boxes.microsoft.XtraBox", "java.lang.String", "name", "", "java.lang.String"), 166);
        ajc$tjp_3 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, factory.makeMethodSig(BuildConfig.VERSION_NAME, "getFirstDateValue", "com.googlecode.mp4parser.boxes.microsoft.XtraBox", "java.lang.String", "name", "", "java.util.Date"), 183);
        ajc$tjp_4 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, factory.makeMethodSig(BuildConfig.VERSION_NAME, "getFirstLongValue", "com.googlecode.mp4parser.boxes.microsoft.XtraBox", "java.lang.String", "name", "", "java.lang.Long"), 200);
        ajc$tjp_5 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, factory.makeMethodSig(BuildConfig.VERSION_NAME, "getValues", "com.googlecode.mp4parser.boxes.microsoft.XtraBox", "java.lang.String", "name", "", "[Ljava.lang.Object;"), 216);
        ajc$tjp_6 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, factory.makeMethodSig(BuildConfig.VERSION_NAME, "removeTag", "com.googlecode.mp4parser.boxes.microsoft.XtraBox", "java.lang.String", "name", "", "void"), 236);
        ajc$tjp_7 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, factory.makeMethodSig(BuildConfig.VERSION_NAME, "setTagValues", "com.googlecode.mp4parser.boxes.microsoft.XtraBox", "java.lang.String:[Ljava.lang.String;", "name:values", "", "void"), 249);
        ajc$tjp_8 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, factory.makeMethodSig(BuildConfig.VERSION_NAME, "setTagValue", "com.googlecode.mp4parser.boxes.microsoft.XtraBox", "java.lang.String:java.lang.String", "name:value", "", "void"), 265);
        ajc$tjp_9 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, factory.makeMethodSig(BuildConfig.VERSION_NAME, "setTagValue", "com.googlecode.mp4parser.boxes.microsoft.XtraBox", "java.lang.String:java.util.Date", "name:date", "", "void"), 276);
    }

    public XtraBox() {
        super(TYPE);
    }

    public XtraBox(String type) {
        super(type);
    }

    protected long getContentSize() {
        if (this.successfulParse) {
            return (long) detailSize();
        }
        return (long) this.data.limit();
    }

    private int detailSize() {
        int size = 0;
        for (int i = 0; i < this.tags.size(); i++) {
            size += ((XtraTag) this.tags.elementAt(i)).getContentSize();
        }
        return size;
    }

    public String toString() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_0, this, this));
        if (!isParsed()) {
            parseDetails();
        }
        StringBuffer b = new StringBuffer();
        b.append("XtraBox[");
        Iterator it = this.tags.iterator();
        while (it.hasNext()) {
            XtraTag tag = (XtraTag) it.next();
            Iterator it2 = tag.values.iterator();
            while (it2.hasNext()) {
                XtraValue value = (XtraValue) it2.next();
                b.append(tag.tagName);
                b.append("=");
                b.append(value.toString());
                b.append(";");
            }
        }
        b.append("]");
        return b.toString();
    }

    public void _parseDetails(ByteBuffer content) {
        int boxSize = content.remaining();
        this.data = content.slice();
        this.successfulParse = false;
        try {
            this.tags.clear();
            while (content.remaining() > 0) {
                XtraTag tag = new XtraTag();
                tag.parse(content);
                this.tags.addElement(tag);
            }
            int calcSize = detailSize();
            if (boxSize != calcSize) {
                throw new RuntimeException("Improperly handled Xtra tag: Calculated sizes don't match ( " + boxSize + "/" + calcSize + ")");
            }
            this.successfulParse = true;
        } catch (Exception e) {
            this.successfulParse = false;
            System.err.println("Malformed Xtra Tag detected: " + e.toString());
            e.printStackTrace();
            content.position(content.position() + content.remaining());
        } finally {
            content.order(ByteOrder.BIG_ENDIAN);
        }
    }

    protected void getContent(ByteBuffer byteBuffer) {
        if (this.successfulParse) {
            for (int i = 0; i < this.tags.size(); i++) {
                ((XtraTag) this.tags.elementAt(i)).getContent(byteBuffer);
            }
            return;
        }
        this.data.rewind();
        byteBuffer.put(this.data);
    }

    public String[] getAllTagNames() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_1, this, this));
        String[] names = new String[this.tags.size()];
        for (int i = 0; i < this.tags.size(); i++) {
            names[i] = ((XtraTag) this.tags.elementAt(i)).tagName;
        }
        return names;
    }

    public String getFirstStringValue(String name) {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_2, this, this, name));
        for (Object obj : getValues(name)) {
            if (obj instanceof String) {
                return (String) obj;
            }
        }
        return null;
    }

    public Date getFirstDateValue(String name) {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_3, this, this, name));
        for (Object obj : getValues(name)) {
            if (obj instanceof Date) {
                return (Date) obj;
            }
        }
        return null;
    }

    public Long getFirstLongValue(String name) {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_4, this, this, name));
        for (Object obj : getValues(name)) {
            if (obj instanceof Long) {
                return (Long) obj;
            }
        }
        return null;
    }

    public Object[] getValues(String name) {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_5, this, this, name));
        XtraTag tag = getTagByName(name);
        if (tag == null) {
            return new Object[0];
        }
        Object[] values = new Object[tag.values.size()];
        for (int i = 0; i < tag.values.size(); i++) {
            values[i] = ((XtraValue) tag.values.elementAt(i)).getValueAsObject();
        }
        return values;
    }

    public void removeTag(String name) {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_6, this, this, name));
        XtraTag tag = getTagByName(name);
        if (tag != null) {
            this.tags.remove(tag);
        }
    }

    public void setTagValues(String name, String[] values) {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_7, this, this, name, values));
        removeTag(name);
        XtraTag tag = new XtraTag(name);
        for (String xtraValue : values) {
            tag.values.addElement(new XtraValue(xtraValue));
        }
        this.tags.addElement(tag);
    }

    public void setTagValue(String name, String value) {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_8, this, this, name, value));
        setTagValues(name, new String[]{value});
    }

    public void setTagValue(String name, Date date) {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_9, this, this, name, date));
        removeTag(name);
        XtraTag tag = new XtraTag(name);
        tag.values.addElement(new XtraValue(date));
        this.tags.addElement(tag);
    }

    public void setTagValue(String name, long value) {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_10, this, this, name, Conversions.longObject(value)));
        removeTag(name);
        XtraTag tag = new XtraTag(name);
        tag.values.addElement(new XtraValue(value));
        this.tags.addElement(tag);
    }

    private XtraTag getTagByName(String name) {
        Iterator it = this.tags.iterator();
        while (it.hasNext()) {
            XtraTag tag = (XtraTag) it.next();
            if (tag.tagName.equals(name)) {
                return tag;
            }
        }
        return null;
    }

    private static long filetimeToMillis(long filetime) {
        return (filetime / FILETIME_ONE_MILLISECOND) - FILETIME_EPOCH_DIFF;
    }

    private static long millisToFiletime(long millis) {
        return (FILETIME_EPOCH_DIFF + millis) * FILETIME_ONE_MILLISECOND;
    }

    private static void writeAsciiString(ByteBuffer dest, String s) {
        try {
            dest.put(s.getBytes(CharEncoding.US_ASCII));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Shouldn't happen", e);
        }
    }

    private static String readAsciiString(ByteBuffer content, int length) {
        byte[] s = new byte[length];
        content.get(s);
        try {
            return new String(s, CharEncoding.US_ASCII);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Shouldn't happen", e);
        }
    }

    private static String readUtf16String(ByteBuffer content, int length) {
        char[] s = new char[((length / 2) - 1)];
        for (int i = 0; i < (length / 2) - 1; i++) {
            s[i] = content.getChar();
        }
        content.getChar();
        return new String(s);
    }

    private static void writeUtf16String(ByteBuffer dest, String s) {
        char[] ar = s.toCharArray();
        for (char putChar : ar) {
            dest.putChar(putChar);
        }
        dest.putChar('\u0000');
    }
}
