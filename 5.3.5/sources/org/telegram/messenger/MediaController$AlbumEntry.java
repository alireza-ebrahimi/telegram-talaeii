package org.telegram.messenger;

import java.util.ArrayList;
import java.util.HashMap;

public class MediaController$AlbumEntry {
    public int bucketId;
    public String bucketName;
    public MediaController$PhotoEntry coverPhoto;
    public ArrayList<MediaController$PhotoEntry> photos = new ArrayList();
    public HashMap<Integer, MediaController$PhotoEntry> photosByIds = new HashMap();

    public MediaController$AlbumEntry(int bucketId, String bucketName, MediaController$PhotoEntry coverPhoto) {
        this.bucketId = bucketId;
        this.bucketName = bucketName;
        this.coverPhoto = coverPhoto;
    }

    public void addPhoto(MediaController$PhotoEntry photoEntry) {
        this.photos.add(photoEntry);
        this.photosByIds.put(Integer.valueOf(photoEntry.imageId), photoEntry);
    }
}
