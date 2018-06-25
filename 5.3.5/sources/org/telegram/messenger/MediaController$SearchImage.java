package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$InputDocument;

public class MediaController$SearchImage {
    public CharSequence caption;
    public int date;
    public TLRPC$Document document;
    public int height;
    public String id;
    public String imagePath;
    public String imageUrl;
    public boolean isCropped;
    public boolean isFiltered;
    public boolean isPainted;
    public String localUrl;
    public MediaController$SavedFilterState savedFilterState;
    public int size;
    public ArrayList<TLRPC$InputDocument> stickers = new ArrayList();
    public String thumbPath;
    public String thumbUrl;
    public int ttl;
    public int type;
    public int width;

    public void reset() {
        this.isFiltered = false;
        this.isPainted = false;
        this.isCropped = false;
        this.ttl = 0;
        this.imagePath = null;
        this.thumbPath = null;
        this.caption = null;
        this.savedFilterState = null;
        this.stickers.clear();
    }
}
