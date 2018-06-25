package org.telegram.messenger;

import android.database.Cursor;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import org.ir.talaeii.R;

class MediaController$28 implements Runnable {
    final /* synthetic */ int val$guid;

    /* renamed from: org.telegram.messenger.MediaController$28$1 */
    class C14301 implements Comparator<MediaController$PhotoEntry> {
        C14301() {
        }

        public int compare(MediaController$PhotoEntry o1, MediaController$PhotoEntry o2) {
            if (o1.dateTaken < o2.dateTaken) {
                return 1;
            }
            if (o1.dateTaken > o2.dateTaken) {
                return -1;
            }
            return 0;
        }
    }

    MediaController$28(int i) {
        this.val$guid = i;
    }

    public void run() {
        int imageIdColumn;
        int bucketIdColumn;
        int bucketNameColumn;
        int dataColumn;
        int dateColumn;
        MediaController$AlbumEntry allMediaAlbum;
        int imageId;
        int bucketId;
        String bucketName;
        String path;
        long dateTaken;
        MediaController$AlbumEntry mediaController$AlbumEntry;
        Throwable e;
        Throwable th;
        MediaController$AlbumEntry albumEntry;
        Integer mediaCameraAlbumId;
        int durationColumn;
        long duration;
        MediaController$PhotoEntry mediaController$PhotoEntry;
        int a;
        ArrayList<MediaController$AlbumEntry> mediaAlbumsSorted = new ArrayList();
        ArrayList<MediaController$AlbumEntry> photoAlbumsSorted = new ArrayList();
        HashMap<Integer, MediaController$AlbumEntry> mediaAlbums = new HashMap();
        HashMap<Integer, MediaController$AlbumEntry> photoAlbums = new HashMap();
        MediaController$AlbumEntry mediaController$AlbumEntry2 = null;
        MediaController$AlbumEntry mediaController$AlbumEntry3 = null;
        String cameraFolder = null;
        try {
            cameraFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/" + "Camera/";
        } catch (Exception e2) {
            FileLog.e(e2);
        }
        Integer num = null;
        Integer photoCameraAlbumId = null;
        Cursor cursor = null;
        if (VERSION.SDK_INT < 23 || (VERSION.SDK_INT >= 23 && ApplicationLoader.applicationContext.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0)) {
            cursor = Media.query(ApplicationLoader.applicationContext.getContentResolver(), Media.EXTERNAL_CONTENT_URI, MediaController.access$7300(), null, null, "datetaken DESC");
            if (cursor != null) {
                imageIdColumn = cursor.getColumnIndex("_id");
                bucketIdColumn = cursor.getColumnIndex("bucket_id");
                bucketNameColumn = cursor.getColumnIndex("bucket_display_name");
                dataColumn = cursor.getColumnIndex("_data");
                dateColumn = cursor.getColumnIndex("datetaken");
                int orientationColumn = cursor.getColumnIndex("orientation");
                allMediaAlbum = null;
                MediaController$AlbumEntry allPhotosAlbum = null;
                while (cursor.moveToNext()) {
                    try {
                        imageId = cursor.getInt(imageIdColumn);
                        bucketId = cursor.getInt(bucketIdColumn);
                        bucketName = cursor.getString(bucketNameColumn);
                        path = cursor.getString(dataColumn);
                        dateTaken = cursor.getLong(dateColumn);
                        int orientation = cursor.getInt(orientationColumn);
                        if (!(path == null || path.length() == 0)) {
                            MediaController$PhotoEntry photoEntry = new MediaController$PhotoEntry(bucketId, imageId, dateTaken, path, orientation, false);
                            if (allPhotosAlbum == null) {
                                mediaController$AlbumEntry = new MediaController$AlbumEntry(0, LocaleController.getString("AllPhotos", R.string.AllPhotos), photoEntry);
                                try {
                                    photoAlbumsSorted.add(0, mediaController$AlbumEntry);
                                } catch (Throwable th2) {
                                    th = th2;
                                    mediaController$AlbumEntry3 = allMediaAlbum;
                                }
                            } else {
                                mediaController$AlbumEntry2 = allPhotosAlbum;
                            }
                            if (allMediaAlbum == null) {
                                mediaController$AlbumEntry3 = new MediaController$AlbumEntry(0, LocaleController.getString("AllMedia", R.string.AllMedia), photoEntry);
                                mediaAlbumsSorted.add(0, mediaController$AlbumEntry3);
                            } else {
                                mediaController$AlbumEntry3 = allMediaAlbum;
                            }
                            mediaController$AlbumEntry2.addPhoto(photoEntry);
                            mediaController$AlbumEntry3.addPhoto(photoEntry);
                            albumEntry = (MediaController$AlbumEntry) mediaAlbums.get(Integer.valueOf(bucketId));
                            if (albumEntry == null) {
                                mediaController$AlbumEntry = new MediaController$AlbumEntry(bucketId, bucketName, photoEntry);
                                mediaAlbums.put(Integer.valueOf(bucketId), mediaController$AlbumEntry);
                                if (num != null || cameraFolder == null || path == null || !path.startsWith(cameraFolder)) {
                                    try {
                                        mediaAlbumsSorted.add(mediaController$AlbumEntry);
                                    } catch (Throwable th3) {
                                        e = th3;
                                    }
                                } else {
                                    mediaAlbumsSorted.add(0, mediaController$AlbumEntry);
                                    num = Integer.valueOf(bucketId);
                                }
                            }
                            albumEntry.addPhoto(photoEntry);
                            albumEntry = (MediaController$AlbumEntry) photoAlbums.get(Integer.valueOf(bucketId));
                            if (albumEntry == null) {
                                mediaController$AlbumEntry = new MediaController$AlbumEntry(bucketId, bucketName, photoEntry);
                                photoAlbums.put(Integer.valueOf(bucketId), mediaController$AlbumEntry);
                                if (photoCameraAlbumId != null || cameraFolder == null || path == null || !path.startsWith(cameraFolder)) {
                                    photoAlbumsSorted.add(mediaController$AlbumEntry);
                                } else {
                                    photoAlbumsSorted.add(0, mediaController$AlbumEntry);
                                    photoCameraAlbumId = Integer.valueOf(bucketId);
                                }
                            }
                            albumEntry.addPhoto(photoEntry);
                            allMediaAlbum = mediaController$AlbumEntry3;
                            allPhotosAlbum = mediaController$AlbumEntry2;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        mediaController$AlbumEntry3 = allMediaAlbum;
                        mediaController$AlbumEntry2 = allPhotosAlbum;
                    }
                }
                mediaController$AlbumEntry3 = allMediaAlbum;
                mediaController$AlbumEntry2 = allPhotosAlbum;
            }
        }
        if (cursor != null) {
            try {
                cursor.close();
                mediaCameraAlbumId = num;
                allMediaAlbum = mediaController$AlbumEntry3;
            } catch (Exception e22) {
                FileLog.e(e22);
                mediaCameraAlbumId = num;
                allMediaAlbum = mediaController$AlbumEntry3;
            }
            if (VERSION.SDK_INT < 23 || (VERSION.SDK_INT >= 23 && ApplicationLoader.applicationContext.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0)) {
                cursor = Media.query(ApplicationLoader.applicationContext.getContentResolver(), Video.Media.EXTERNAL_CONTENT_URI, MediaController.access$7400(), null, null, "datetaken DESC");
                if (cursor != null) {
                    imageIdColumn = cursor.getColumnIndex("_id");
                    bucketIdColumn = cursor.getColumnIndex("bucket_id");
                    bucketNameColumn = cursor.getColumnIndex("bucket_display_name");
                    dataColumn = cursor.getColumnIndex("_data");
                    dateColumn = cursor.getColumnIndex("datetaken");
                    durationColumn = cursor.getColumnIndex("duration");
                    while (cursor.moveToNext()) {
                        imageId = cursor.getInt(imageIdColumn);
                        bucketId = cursor.getInt(bucketIdColumn);
                        bucketName = cursor.getString(bucketNameColumn);
                        path = cursor.getString(dataColumn);
                        dateTaken = cursor.getLong(dateColumn);
                        duration = cursor.getLong(durationColumn);
                        if (!(path == null || path.length() == 0)) {
                            mediaController$PhotoEntry = new MediaController$PhotoEntry(bucketId, imageId, dateTaken, path, (int) (duration / 1000), true);
                            if (allMediaAlbum != null) {
                                mediaController$AlbumEntry3 = new MediaController$AlbumEntry(0, LocaleController.getString("AllMedia", R.string.AllMedia), mediaController$PhotoEntry);
                                try {
                                    mediaAlbumsSorted.add(0, mediaController$AlbumEntry3);
                                } catch (Throwable th5) {
                                    th = th5;
                                    num = mediaCameraAlbumId;
                                }
                            } else {
                                mediaController$AlbumEntry3 = allMediaAlbum;
                            }
                            mediaController$AlbumEntry3.addPhoto(mediaController$PhotoEntry);
                            albumEntry = (MediaController$AlbumEntry) mediaAlbums.get(Integer.valueOf(bucketId));
                            if (albumEntry == null) {
                                mediaController$AlbumEntry = new MediaController$AlbumEntry(bucketId, bucketName, mediaController$PhotoEntry);
                                mediaAlbums.put(Integer.valueOf(bucketId), mediaController$AlbumEntry);
                                if (mediaCameraAlbumId == null || cameraFolder == null || path == null || !path.startsWith(cameraFolder)) {
                                    mediaAlbumsSorted.add(mediaController$AlbumEntry);
                                } else {
                                    mediaAlbumsSorted.add(0, mediaController$AlbumEntry);
                                    num = Integer.valueOf(bucketId);
                                    albumEntry.addPhoto(mediaController$PhotoEntry);
                                    mediaCameraAlbumId = num;
                                    allMediaAlbum = mediaController$AlbumEntry3;
                                }
                            }
                            num = mediaCameraAlbumId;
                            try {
                                albumEntry.addPhoto(mediaController$PhotoEntry);
                                mediaCameraAlbumId = num;
                                allMediaAlbum = mediaController$AlbumEntry3;
                            } catch (Throwable th6) {
                                e = th6;
                            }
                        }
                    }
                }
            }
            num = mediaCameraAlbumId;
            mediaController$AlbumEntry3 = allMediaAlbum;
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e222) {
                    FileLog.e(e222);
                }
            }
            for (a = 0; a < mediaAlbumsSorted.size(); a++) {
                Collections.sort(((MediaController$AlbumEntry) mediaAlbumsSorted.get(a)).photos, new C14301());
            }
            MediaController.access$7500(this.val$guid, mediaAlbumsSorted, photoAlbumsSorted, num, mediaController$AlbumEntry3, mediaController$AlbumEntry2, 0);
        }
        mediaCameraAlbumId = num;
        allMediaAlbum = mediaController$AlbumEntry3;
        try {
            cursor = Media.query(ApplicationLoader.applicationContext.getContentResolver(), Video.Media.EXTERNAL_CONTENT_URI, MediaController.access$7400(), null, null, "datetaken DESC");
            if (cursor != null) {
                imageIdColumn = cursor.getColumnIndex("_id");
                bucketIdColumn = cursor.getColumnIndex("bucket_id");
                bucketNameColumn = cursor.getColumnIndex("bucket_display_name");
                dataColumn = cursor.getColumnIndex("_data");
                dateColumn = cursor.getColumnIndex("datetaken");
                durationColumn = cursor.getColumnIndex("duration");
                while (cursor.moveToNext()) {
                    imageId = cursor.getInt(imageIdColumn);
                    bucketId = cursor.getInt(bucketIdColumn);
                    bucketName = cursor.getString(bucketNameColumn);
                    path = cursor.getString(dataColumn);
                    dateTaken = cursor.getLong(dateColumn);
                    duration = cursor.getLong(durationColumn);
                    mediaController$PhotoEntry = new MediaController$PhotoEntry(bucketId, imageId, dateTaken, path, (int) (duration / 1000), true);
                    if (allMediaAlbum != null) {
                        mediaController$AlbumEntry3 = allMediaAlbum;
                    } else {
                        mediaController$AlbumEntry3 = new MediaController$AlbumEntry(0, LocaleController.getString("AllMedia", R.string.AllMedia), mediaController$PhotoEntry);
                        mediaAlbumsSorted.add(0, mediaController$AlbumEntry3);
                    }
                    mediaController$AlbumEntry3.addPhoto(mediaController$PhotoEntry);
                    albumEntry = (MediaController$AlbumEntry) mediaAlbums.get(Integer.valueOf(bucketId));
                    if (albumEntry == null) {
                        mediaController$AlbumEntry = new MediaController$AlbumEntry(bucketId, bucketName, mediaController$PhotoEntry);
                        mediaAlbums.put(Integer.valueOf(bucketId), mediaController$AlbumEntry);
                        if (mediaCameraAlbumId == null) {
                        }
                        mediaAlbumsSorted.add(mediaController$AlbumEntry);
                    }
                    num = mediaCameraAlbumId;
                    albumEntry.addPhoto(mediaController$PhotoEntry);
                    mediaCameraAlbumId = num;
                    allMediaAlbum = mediaController$AlbumEntry3;
                }
            }
            num = mediaCameraAlbumId;
            mediaController$AlbumEntry3 = allMediaAlbum;
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th7) {
            th = th7;
            num = mediaCameraAlbumId;
            mediaController$AlbumEntry3 = allMediaAlbum;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        for (a = 0; a < mediaAlbumsSorted.size(); a++) {
            Collections.sort(((MediaController$AlbumEntry) mediaAlbumsSorted.get(a)).photos, new C14301());
        }
        MediaController.access$7500(this.val$guid, mediaAlbumsSorted, photoAlbumsSorted, num, mediaController$AlbumEntry3, mediaController$AlbumEntry2, 0);
        try {
            FileLog.e(e);
            if (cursor != null) {
                try {
                    cursor.close();
                    mediaCameraAlbumId = num;
                    allMediaAlbum = mediaController$AlbumEntry3;
                } catch (Exception e2222) {
                    FileLog.e(e2222);
                    mediaCameraAlbumId = num;
                    allMediaAlbum = mediaController$AlbumEntry3;
                }
                cursor = Media.query(ApplicationLoader.applicationContext.getContentResolver(), Video.Media.EXTERNAL_CONTENT_URI, MediaController.access$7400(), null, null, "datetaken DESC");
                if (cursor != null) {
                    imageIdColumn = cursor.getColumnIndex("_id");
                    bucketIdColumn = cursor.getColumnIndex("bucket_id");
                    bucketNameColumn = cursor.getColumnIndex("bucket_display_name");
                    dataColumn = cursor.getColumnIndex("_data");
                    dateColumn = cursor.getColumnIndex("datetaken");
                    durationColumn = cursor.getColumnIndex("duration");
                    while (cursor.moveToNext()) {
                        imageId = cursor.getInt(imageIdColumn);
                        bucketId = cursor.getInt(bucketIdColumn);
                        bucketName = cursor.getString(bucketNameColumn);
                        path = cursor.getString(dataColumn);
                        dateTaken = cursor.getLong(dateColumn);
                        duration = cursor.getLong(durationColumn);
                        mediaController$PhotoEntry = new MediaController$PhotoEntry(bucketId, imageId, dateTaken, path, (int) (duration / 1000), true);
                        if (allMediaAlbum != null) {
                            mediaController$AlbumEntry3 = new MediaController$AlbumEntry(0, LocaleController.getString("AllMedia", R.string.AllMedia), mediaController$PhotoEntry);
                            mediaAlbumsSorted.add(0, mediaController$AlbumEntry3);
                        } else {
                            mediaController$AlbumEntry3 = allMediaAlbum;
                        }
                        mediaController$AlbumEntry3.addPhoto(mediaController$PhotoEntry);
                        albumEntry = (MediaController$AlbumEntry) mediaAlbums.get(Integer.valueOf(bucketId));
                        if (albumEntry == null) {
                            mediaController$AlbumEntry = new MediaController$AlbumEntry(bucketId, bucketName, mediaController$PhotoEntry);
                            mediaAlbums.put(Integer.valueOf(bucketId), mediaController$AlbumEntry);
                            if (mediaCameraAlbumId == null) {
                            }
                            mediaAlbumsSorted.add(mediaController$AlbumEntry);
                        }
                        num = mediaCameraAlbumId;
                        albumEntry.addPhoto(mediaController$PhotoEntry);
                        mediaCameraAlbumId = num;
                        allMediaAlbum = mediaController$AlbumEntry3;
                    }
                }
                num = mediaCameraAlbumId;
                mediaController$AlbumEntry3 = allMediaAlbum;
                if (cursor != null) {
                    cursor.close();
                }
                for (a = 0; a < mediaAlbumsSorted.size(); a++) {
                    Collections.sort(((MediaController$AlbumEntry) mediaAlbumsSorted.get(a)).photos, new C14301());
                }
                MediaController.access$7500(this.val$guid, mediaAlbumsSorted, photoAlbumsSorted, num, mediaController$AlbumEntry3, mediaController$AlbumEntry2, 0);
            }
            mediaCameraAlbumId = num;
            allMediaAlbum = mediaController$AlbumEntry3;
            cursor = Media.query(ApplicationLoader.applicationContext.getContentResolver(), Video.Media.EXTERNAL_CONTENT_URI, MediaController.access$7400(), null, null, "datetaken DESC");
            if (cursor != null) {
                imageIdColumn = cursor.getColumnIndex("_id");
                bucketIdColumn = cursor.getColumnIndex("bucket_id");
                bucketNameColumn = cursor.getColumnIndex("bucket_display_name");
                dataColumn = cursor.getColumnIndex("_data");
                dateColumn = cursor.getColumnIndex("datetaken");
                durationColumn = cursor.getColumnIndex("duration");
                while (cursor.moveToNext()) {
                    imageId = cursor.getInt(imageIdColumn);
                    bucketId = cursor.getInt(bucketIdColumn);
                    bucketName = cursor.getString(bucketNameColumn);
                    path = cursor.getString(dataColumn);
                    dateTaken = cursor.getLong(dateColumn);
                    duration = cursor.getLong(durationColumn);
                    mediaController$PhotoEntry = new MediaController$PhotoEntry(bucketId, imageId, dateTaken, path, (int) (duration / 1000), true);
                    if (allMediaAlbum != null) {
                        mediaController$AlbumEntry3 = allMediaAlbum;
                    } else {
                        mediaController$AlbumEntry3 = new MediaController$AlbumEntry(0, LocaleController.getString("AllMedia", R.string.AllMedia), mediaController$PhotoEntry);
                        mediaAlbumsSorted.add(0, mediaController$AlbumEntry3);
                    }
                    mediaController$AlbumEntry3.addPhoto(mediaController$PhotoEntry);
                    albumEntry = (MediaController$AlbumEntry) mediaAlbums.get(Integer.valueOf(bucketId));
                    if (albumEntry == null) {
                        mediaController$AlbumEntry = new MediaController$AlbumEntry(bucketId, bucketName, mediaController$PhotoEntry);
                        mediaAlbums.put(Integer.valueOf(bucketId), mediaController$AlbumEntry);
                        if (mediaCameraAlbumId == null) {
                        }
                        mediaAlbumsSorted.add(mediaController$AlbumEntry);
                    }
                    num = mediaCameraAlbumId;
                    albumEntry.addPhoto(mediaController$PhotoEntry);
                    mediaCameraAlbumId = num;
                    allMediaAlbum = mediaController$AlbumEntry3;
                }
            }
            num = mediaCameraAlbumId;
            mediaController$AlbumEntry3 = allMediaAlbum;
            if (cursor != null) {
                cursor.close();
            }
            for (a = 0; a < mediaAlbumsSorted.size(); a++) {
                Collections.sort(((MediaController$AlbumEntry) mediaAlbumsSorted.get(a)).photos, new C14301());
            }
            MediaController.access$7500(this.val$guid, mediaAlbumsSorted, photoAlbumsSorted, num, mediaController$AlbumEntry3, mediaController$AlbumEntry2, 0);
        } catch (Throwable th8) {
            th = th8;
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e22222) {
                    FileLog.e(e22222);
                }
            }
            throw th;
        }
    }
}
