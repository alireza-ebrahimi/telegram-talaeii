package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.DocumentAttribute;

public class TLRPC$TL_webDocument extends TLObject {
    public static int constructor = -971322408;
    public long access_hash;
    public ArrayList<DocumentAttribute> attributes = new ArrayList();
    public int dc_id;
    public String mime_type;
    public int size;
    public String url;

    public static TLRPC$TL_webDocument TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_webDocument result = new TLRPC$TL_webDocument();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_webDocument", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.url = stream.readString(exception);
        this.access_hash = stream.readInt64(exception);
        this.size = stream.readInt32(exception);
        this.mime_type = stream.readString(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                DocumentAttribute object = DocumentAttribute.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.attributes.add(object);
                    a++;
                } else {
                    return;
                }
            }
            this.dc_id = stream.readInt32(exception);
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.url);
        stream.writeInt64(this.access_hash);
        stream.writeInt32(this.size);
        stream.writeString(this.mime_type);
        stream.writeInt32(481674261);
        int count = this.attributes.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((DocumentAttribute) this.attributes.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(this.dc_id);
    }
}
