package org.telegram.messenger;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC$InputDocument;

public class MediaController$PhotoEntry {
    public int bucketId;
    public CharSequence caption;
    public long dateTaken;
    public int duration;
    public VideoEditedInfo editedInfo;
    public int imageId;
    public String imagePath;
    public boolean isCropped;
    public boolean isFiltered;
    public boolean isMuted;
    public boolean isPainted;
    public boolean isVideo;
    public int orientation;
    public String path;
    public MediaController$SavedFilterState savedFilterState;
    public ArrayList<TLRPC$InputDocument> stickers = new ArrayList();
    public String thumbPath;
    public int ttl;

    public MediaController$PhotoEntry(int bucketId, int imageId, long dateTaken, String path, int orientation, boolean isVideo) {
        this.bucketId = bucketId;
        this.imageId = imageId;
        this.dateTaken = dateTaken;
        this.path = path;
        if (isVideo) {
            this.duration = orientation;
        } else {
            this.orientation = orientation;
        }
        this.isVideo = isVideo;
    }

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
