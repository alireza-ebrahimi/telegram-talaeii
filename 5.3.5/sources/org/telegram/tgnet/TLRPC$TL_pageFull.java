package org.telegram.tgnet;

public class TLRPC$TL_pageFull extends TLRPC$Page {
    public static int constructor = 1433323434;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$PageBlock object = TLRPC$PageBlock.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.blocks.add(object);
                    a++;
                } else {
                    return;
                }
            }
            if (stream.readInt32(exception) == 481674261) {
                count = stream.readInt32(exception);
                a = 0;
                while (a < count) {
                    TLRPC$Photo object2 = TLRPC$Photo.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object2 != null) {
                        this.photos.add(object2);
                        a++;
                    } else {
                        return;
                    }
                }
                if (stream.readInt32(exception) == 481674261) {
                    count = stream.readInt32(exception);
                    a = 0;
                    while (a < count) {
                        TLRPC$Document object3 = TLRPC$Document.TLdeserialize(stream, stream.readInt32(exception), exception);
                        if (object3 != null) {
                            this.documents.add(object3);
                            a++;
                        } else {
                            return;
                        }
                    }
                } else if (exception) {
                    throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
                }
            } else if (exception) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int a;
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.blocks.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$PageBlock) this.blocks.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        count = this.photos.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$Photo) this.photos.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        count = this.documents.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$Document) this.documents.get(a)).serializeToStream(stream);
        }
    }
}
