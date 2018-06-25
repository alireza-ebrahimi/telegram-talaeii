package org.telegram.messenger.exoplayer2.extractor.flv;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

final class ScriptTagPayloadReader extends TagPayloadReader {
    private static final int AMF_TYPE_BOOLEAN = 1;
    private static final int AMF_TYPE_DATE = 11;
    private static final int AMF_TYPE_ECMA_ARRAY = 8;
    private static final int AMF_TYPE_END_MARKER = 9;
    private static final int AMF_TYPE_NUMBER = 0;
    private static final int AMF_TYPE_OBJECT = 3;
    private static final int AMF_TYPE_STRICT_ARRAY = 10;
    private static final int AMF_TYPE_STRING = 2;
    private static final String KEY_DURATION = "duration";
    private static final String NAME_METADATA = "onMetaData";
    private long durationUs = C0907C.TIME_UNSET;

    public ScriptTagPayloadReader(TrackOutput output) {
        super(output);
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    public void seek() {
    }

    protected boolean parseHeader(ParsableByteArray data) {
        return true;
    }

    protected void parsePayload(ParsableByteArray data, long timeUs) throws ParserException {
        if (readAmfType(data) != 2) {
            throw new ParserException();
        }
        if (NAME_METADATA.equals(readAmfString(data)) && readAmfType(data) == 8) {
            Map<String, Object> metadata = readAmfEcmaArray(data);
            if (metadata.containsKey(KEY_DURATION)) {
                double durationSeconds = ((Double) metadata.get(KEY_DURATION)).doubleValue();
                if (durationSeconds > 0.0d) {
                    this.durationUs = (long) (1000000.0d * durationSeconds);
                }
            }
        }
    }

    private static int readAmfType(ParsableByteArray data) {
        return data.readUnsignedByte();
    }

    private static Boolean readAmfBoolean(ParsableByteArray data) {
        boolean z = true;
        if (data.readUnsignedByte() != 1) {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    private static Double readAmfDouble(ParsableByteArray data) {
        return Double.valueOf(Double.longBitsToDouble(data.readLong()));
    }

    private static String readAmfString(ParsableByteArray data) {
        int size = data.readUnsignedShort();
        int position = data.getPosition();
        data.skipBytes(size);
        return new String(data.data, position, size);
    }

    private static ArrayList<Object> readAmfStrictArray(ParsableByteArray data) {
        int count = data.readUnsignedIntToInt();
        ArrayList<Object> list = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            list.add(readAmfData(data, readAmfType(data)));
        }
        return list;
    }

    private static HashMap<String, Object> readAmfObject(ParsableByteArray data) {
        HashMap<String, Object> array = new HashMap();
        while (true) {
            String key = readAmfString(data);
            int type = readAmfType(data);
            if (type == 9) {
                return array;
            }
            array.put(key, readAmfData(data, type));
        }
    }

    private static HashMap<String, Object> readAmfEcmaArray(ParsableByteArray data) {
        int count = data.readUnsignedIntToInt();
        HashMap<String, Object> array = new HashMap(count);
        for (int i = 0; i < count; i++) {
            array.put(readAmfString(data), readAmfData(data, readAmfType(data)));
        }
        return array;
    }

    private static Date readAmfDate(ParsableByteArray data) {
        Date date = new Date((long) readAmfDouble(data).doubleValue());
        data.skipBytes(2);
        return date;
    }

    private static Object readAmfData(ParsableByteArray data, int type) {
        switch (type) {
            case 0:
                return readAmfDouble(data);
            case 1:
                return readAmfBoolean(data);
            case 2:
                return readAmfString(data);
            case 3:
                return readAmfObject(data);
            case 8:
                return readAmfEcmaArray(data);
            case 10:
                return readAmfStrictArray(data);
            case 11:
                return readAmfDate(data);
            default:
                return null;
        }
    }
}
