package org.telegram.messenger;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.telegram.messenger.FileLoader.FileLoaderDelegate;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.TLRPC$TL_documentEncrypted;
import org.telegram.tgnet.TLRPC$TL_fileEncryptedLocation;
import org.telegram.tgnet.TLRPC$TL_fileLocation;
import org.telegram.tgnet.TLRPC$TL_fileLocationUnavailable;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_photoCachedSize;
import org.telegram.tgnet.TLRPC$TL_photoSize;
import org.telegram.tgnet.TLRPC$TL_webDocument;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.InputEncryptedFile;
import org.telegram.tgnet.TLRPC.InputFile;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.ui.Components.AnimatedFileDrawable;

public class ImageLoader {
    private static volatile ImageLoader Instance = null;
    private static byte[] bytes;
    private static byte[] bytesThumb;
    private static byte[] header = new byte[12];
    private static byte[] headerThumb = new byte[12];
    private HashMap<String, Integer> bitmapUseCounts = new HashMap();
    private DispatchQueue cacheOutQueue = new DispatchQueue("cacheOutQueue");
    private DispatchQueue cacheThumbOutQueue = new DispatchQueue("cacheThumbOutQueue");
    private int currentHttpFileLoadTasksCount = 0;
    private int currentHttpTasksCount = 0;
    private ConcurrentHashMap<String, Float> fileProgresses = new ConcurrentHashMap();
    private HashMap<String, Integer> forceLoadingImages = new HashMap();
    private LinkedList<HttpFileTask> httpFileLoadTasks = new LinkedList();
    private HashMap<String, HttpFileTask> httpFileLoadTasksByKeys = new HashMap();
    private LinkedList<HttpImageTask> httpTasks = new LinkedList();
    private String ignoreRemoval = null;
    private DispatchQueue imageLoadQueue = new DispatchQueue("imageLoadQueue");
    private HashMap<String, CacheImage> imageLoadingByKeys = new HashMap();
    private HashMap<Integer, CacheImage> imageLoadingByTag = new HashMap();
    private HashMap<String, CacheImage> imageLoadingByUrl = new HashMap();
    private volatile long lastCacheOutTime = 0;
    private int lastImageNum = 0;
    private long lastProgressUpdateTime = 0;
    private LruCache memCache;
    private HashMap<String, Runnable> retryHttpsTasks = new HashMap();
    private File telegramPath = null;
    private HashMap<String, ThumbGenerateTask> thumbGenerateTasks = new HashMap();
    private DispatchQueue thumbGeneratingQueue = new DispatchQueue("thumbGeneratingQueue");
    private HashMap<String, ThumbGenerateInfo> waitingForQualityThumb = new HashMap();
    private HashMap<Integer, String> waitingForQualityThumbByTag = new HashMap();

    /* renamed from: org.telegram.messenger.ImageLoader$2 */
    class C30732 implements FileLoaderDelegate {
        C30732() {
        }

        public void fileDidFailedLoad(final String str, final int i) {
            ImageLoader.this.fileProgresses.remove(str);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    ImageLoader.this.fileDidFailedLoad(str, i);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.FileDidFailedLoad, str, Integer.valueOf(i));
                }
            });
        }

        public void fileDidFailedUpload(final String str, final boolean z) {
            Utilities.stageQueue.postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.ImageLoader$2$3$1 */
                class C30681 implements Runnable {
                    C30681() {
                    }

                    public void run() {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.FileDidFailUpload, str, Boolean.valueOf(z));
                    }
                }

                public void run() {
                    AndroidUtilities.runOnUIThread(new C30681());
                    ImageLoader.this.fileProgresses.remove(str);
                }
            });
        }

        public void fileDidLoaded(final String str, final File file, final int i) {
            ImageLoader.this.fileProgresses.remove(str);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    if (MediaController.getInstance().canSaveToGallery() && ImageLoader.this.telegramPath != null && file != null && ((str.endsWith(".mp4") || str.endsWith(".jpg")) && file.toString().startsWith(ImageLoader.this.telegramPath.toString()))) {
                        AndroidUtilities.addMediaToGallery(file.toString());
                    }
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.FileDidLoaded, str);
                    ImageLoader.this.fileDidLoaded(str, file, i);
                }
            });
        }

        public void fileDidUploaded(String str, InputFile inputFile, InputEncryptedFile inputEncryptedFile, byte[] bArr, byte[] bArr2, long j) {
            final String str2 = str;
            final InputFile inputFile2 = inputFile;
            final InputEncryptedFile inputEncryptedFile2 = inputEncryptedFile;
            final byte[] bArr3 = bArr;
            final byte[] bArr4 = bArr2;
            final long j2 = j;
            Utilities.stageQueue.postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.ImageLoader$2$2$1 */
                class C30661 implements Runnable {
                    C30661() {
                    }

                    public void run() {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.FileDidUpload, str2, inputFile2, inputEncryptedFile2, bArr3, bArr4, Long.valueOf(j2));
                    }
                }

                public void run() {
                    AndroidUtilities.runOnUIThread(new C30661());
                    ImageLoader.this.fileProgresses.remove(str2);
                }
            });
        }

        public void fileLoadProgressChanged(final String str, final float f) {
            ImageLoader.this.fileProgresses.put(str, Float.valueOf(f));
            long currentTimeMillis = System.currentTimeMillis();
            if (ImageLoader.this.lastProgressUpdateTime == 0 || ImageLoader.this.lastProgressUpdateTime < currentTimeMillis - 500) {
                ImageLoader.this.lastProgressUpdateTime = currentTimeMillis;
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.FileLoadProgressChanged, str, Float.valueOf(f));
                    }
                });
            }
        }

        public void fileUploadProgressChanged(final String str, final float f, final boolean z) {
            ImageLoader.this.fileProgresses.put(str, Float.valueOf(f));
            long currentTimeMillis = System.currentTimeMillis();
            if (ImageLoader.this.lastProgressUpdateTime == 0 || ImageLoader.this.lastProgressUpdateTime < currentTimeMillis - 500) {
                ImageLoader.this.lastProgressUpdateTime = currentTimeMillis;
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.FileUploadProgressChanged, str, Float.valueOf(f), Boolean.valueOf(z));
                    }
                });
            }
        }
    }

    /* renamed from: org.telegram.messenger.ImageLoader$3 */
    class C30753 extends BroadcastReceiver {

        /* renamed from: org.telegram.messenger.ImageLoader$3$1 */
        class C30741 implements Runnable {
            C30741() {
            }

            public void run() {
                ImageLoader.this.checkMediaPaths();
            }
        }

        C30753() {
        }

        public void onReceive(Context context, Intent intent) {
            FileLog.m13726e("file system changed");
            Runnable c30741 = new C30741();
            if ("android.intent.action.MEDIA_UNMOUNTED".equals(intent.getAction())) {
                AndroidUtilities.runOnUIThread(c30741, 1000);
            } else {
                c30741.run();
            }
        }
    }

    /* renamed from: org.telegram.messenger.ImageLoader$4 */
    class C30774 implements Runnable {
        C30774() {
        }

        public void run() {
            final HashMap createMediaPaths = ImageLoader.this.createMediaPaths();
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    FileLoader.getInstance().setMediaDirs(createMediaPaths);
                }
            });
        }
    }

    private class CacheImage {
        protected boolean animatedFile;
        protected CacheOutTask cacheTask;
        protected File encryptionKeyPath;
        protected String ext;
        protected String filter;
        protected ArrayList<String> filters;
        protected File finalFilePath;
        protected HttpImageTask httpTask;
        protected String httpUrl;
        protected ArrayList<ImageReceiver> imageReceiverArray;
        protected String key;
        protected ArrayList<String> keys;
        protected TLObject location;
        protected boolean selfThumb;
        protected File tempFilePath;
        protected ArrayList<Boolean> thumbs;
        protected String url;

        private CacheImage() {
            this.imageReceiverArray = new ArrayList();
            this.keys = new ArrayList();
            this.filters = new ArrayList();
            this.thumbs = new ArrayList();
        }

        public void addImageReceiver(ImageReceiver imageReceiver, String str, String str2, boolean z) {
            if (!this.imageReceiverArray.contains(imageReceiver)) {
                this.imageReceiverArray.add(imageReceiver);
                this.keys.add(str);
                this.filters.add(str2);
                this.thumbs.add(Boolean.valueOf(z));
                ImageLoader.this.imageLoadingByTag.put(imageReceiver.getTag(z), this);
            }
        }

        public void removeImageReceiver(ImageReceiver imageReceiver) {
            int i = 0;
            int i2 = 0;
            Boolean valueOf = Boolean.valueOf(this.selfThumb);
            while (i2 < this.imageReceiverArray.size()) {
                ImageReceiver imageReceiver2 = (ImageReceiver) this.imageReceiverArray.get(i2);
                if (imageReceiver2 == null || imageReceiver2 == imageReceiver) {
                    this.imageReceiverArray.remove(i2);
                    this.keys.remove(i2);
                    this.filters.remove(i2);
                    valueOf = (Boolean) this.thumbs.remove(i2);
                    if (imageReceiver2 != null) {
                        ImageLoader.this.imageLoadingByTag.remove(imageReceiver2.getTag(valueOf.booleanValue()));
                    }
                    i2--;
                }
                i2++;
            }
            if (this.imageReceiverArray.size() == 0) {
                while (i < this.imageReceiverArray.size()) {
                    ImageLoader.this.imageLoadingByTag.remove(((ImageReceiver) this.imageReceiverArray.get(i)).getTag(valueOf.booleanValue()));
                    i++;
                }
                this.imageReceiverArray.clear();
                if (!(this.location == null || ImageLoader.this.forceLoadingImages.containsKey(this.key))) {
                    if (this.location instanceof FileLocation) {
                        FileLoader.getInstance().cancelLoadFile((FileLocation) this.location, this.ext);
                    } else if (this.location instanceof Document) {
                        FileLoader.getInstance().cancelLoadFile((Document) this.location);
                    } else if (this.location instanceof TLRPC$TL_webDocument) {
                        FileLoader.getInstance().cancelLoadFile((TLRPC$TL_webDocument) this.location);
                    }
                }
                if (this.cacheTask != null) {
                    if (this.selfThumb) {
                        ImageLoader.this.cacheThumbOutQueue.cancelRunnable(this.cacheTask);
                    } else {
                        ImageLoader.this.cacheOutQueue.cancelRunnable(this.cacheTask);
                    }
                    this.cacheTask.cancel();
                    this.cacheTask = null;
                }
                if (this.httpTask != null) {
                    ImageLoader.this.httpTasks.remove(this.httpTask);
                    this.httpTask.cancel(true);
                    this.httpTask = null;
                }
                if (this.url != null) {
                    ImageLoader.this.imageLoadingByUrl.remove(this.url);
                }
                if (this.key != null) {
                    ImageLoader.this.imageLoadingByKeys.remove(this.key);
                }
            }
        }

        public void replaceImageReceiver(ImageReceiver imageReceiver, String str, String str2, boolean z) {
            int indexOf = this.imageReceiverArray.indexOf(imageReceiver);
            if (indexOf != -1) {
                int indexOf2;
                if (((Boolean) this.thumbs.get(indexOf)).booleanValue() != z) {
                    indexOf2 = this.imageReceiverArray.subList(indexOf + 1, this.imageReceiverArray.size()).indexOf(imageReceiver);
                    if (indexOf2 == -1) {
                        return;
                    }
                }
                indexOf2 = indexOf;
                this.keys.set(indexOf2, str);
                this.filters.set(indexOf2, str2);
            }
        }

        public void setImageAndClear(final BitmapDrawable bitmapDrawable) {
            if (bitmapDrawable != null) {
                final ArrayList arrayList = new ArrayList(this.imageReceiverArray);
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        if (bitmapDrawable instanceof AnimatedFileDrawable) {
                            BitmapDrawable bitmapDrawable = (AnimatedFileDrawable) bitmapDrawable;
                            int i = 0;
                            boolean z = false;
                            while (i < arrayList.size()) {
                                if (((ImageReceiver) arrayList.get(i)).setImageBitmapByKey(i == 0 ? bitmapDrawable : bitmapDrawable.makeCopy(), CacheImage.this.key, CacheImage.this.selfThumb, false)) {
                                    z = true;
                                }
                                i++;
                            }
                            if (!z) {
                                ((AnimatedFileDrawable) bitmapDrawable).recycle();
                                return;
                            }
                            return;
                        }
                        for (int i2 = 0; i2 < arrayList.size(); i2++) {
                            ((ImageReceiver) arrayList.get(i2)).setImageBitmapByKey(bitmapDrawable, CacheImage.this.key, CacheImage.this.selfThumb, false);
                        }
                    }
                });
            }
            for (int i = 0; i < this.imageReceiverArray.size(); i++) {
                ImageLoader.this.imageLoadingByTag.remove(((ImageReceiver) this.imageReceiverArray.get(i)).getTag(this.selfThumb));
            }
            this.imageReceiverArray.clear();
            if (this.url != null) {
                ImageLoader.this.imageLoadingByUrl.remove(this.url);
            }
            if (this.key != null) {
                ImageLoader.this.imageLoadingByKeys.remove(this.key);
            }
        }
    }

    private class CacheOutTask implements Runnable {
        private CacheImage cacheImage;
        private boolean isCancelled;
        private Thread runningThread;
        private final Object sync = new Object();

        public CacheOutTask(CacheImage cacheImage) {
            this.cacheImage = cacheImage;
        }

        private void onPostExecute(final BitmapDrawable bitmapDrawable) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    BitmapDrawable bitmapDrawable = null;
                    if (bitmapDrawable instanceof AnimatedFileDrawable) {
                        bitmapDrawable = bitmapDrawable;
                    } else if (bitmapDrawable != null) {
                        bitmapDrawable = ImageLoader.this.memCache.get(CacheOutTask.this.cacheImage.key);
                        if (bitmapDrawable == null) {
                            ImageLoader.this.memCache.put(CacheOutTask.this.cacheImage.key, bitmapDrawable);
                            bitmapDrawable = bitmapDrawable;
                        } else {
                            bitmapDrawable.getBitmap().recycle();
                        }
                    }
                    ImageLoader.this.imageLoadQueue.postRunnable(new Runnable() {
                        public void run() {
                            CacheOutTask.this.cacheImage.setImageAndClear(bitmapDrawable);
                        }
                    });
                }
            });
        }

        public void cancel() {
            synchronized (this.sync) {
                try {
                    this.isCancelled = true;
                    if (this.runningThread != null) {
                        this.runningThread.interrupt();
                    }
                } catch (Exception e) {
                }
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r22 = this;
            r0 = r22;
            r5 = r0.sync;
            monitor-enter(r5);
            r4 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x0031 }
            r0 = r22;
            r0.runningThread = r4;	 Catch:{ all -> 0x0031 }
            java.lang.Thread.interrupted();	 Catch:{ all -> 0x0031 }
            r0 = r22;
            r4 = r0.isCancelled;	 Catch:{ all -> 0x0031 }
            if (r4 == 0) goto L_0x0018;
        L_0x0016:
            monitor-exit(r5);	 Catch:{ all -> 0x0031 }
        L_0x0017:
            return;
        L_0x0018:
            monitor-exit(r5);	 Catch:{ all -> 0x0031 }
            r0 = r22;
            r4 = r0.cacheImage;
            r4 = r4.animatedFile;
            if (r4 == 0) goto L_0x0063;
        L_0x0021:
            r0 = r22;
            r5 = r0.sync;
            monitor-enter(r5);
            r0 = r22;
            r4 = r0.isCancelled;	 Catch:{ all -> 0x002e }
            if (r4 == 0) goto L_0x0034;
        L_0x002c:
            monitor-exit(r5);	 Catch:{ all -> 0x002e }
            goto L_0x0017;
        L_0x002e:
            r4 = move-exception;
            monitor-exit(r5);	 Catch:{ all -> 0x002e }
            throw r4;
        L_0x0031:
            r4 = move-exception;
            monitor-exit(r5);	 Catch:{ all -> 0x0031 }
            throw r4;
        L_0x0034:
            monitor-exit(r5);	 Catch:{ all -> 0x002e }
            r5 = new org.telegram.ui.Components.AnimatedFileDrawable;
            r0 = r22;
            r4 = r0.cacheImage;
            r6 = r4.finalFilePath;
            r0 = r22;
            r4 = r0.cacheImage;
            r4 = r4.filter;
            if (r4 == 0) goto L_0x0061;
        L_0x0045:
            r0 = r22;
            r4 = r0.cacheImage;
            r4 = r4.filter;
            r7 = "d";
            r4 = r4.equals(r7);
            if (r4 == 0) goto L_0x0061;
        L_0x0054:
            r4 = 1;
        L_0x0055:
            r5.<init>(r6, r4);
            java.lang.Thread.interrupted();
            r0 = r22;
            r0.onPostExecute(r5);
            goto L_0x0017;
        L_0x0061:
            r4 = 0;
            goto L_0x0055;
        L_0x0063:
            r8 = 0;
            r7 = 0;
            r10 = 0;
            r0 = r22;
            r4 = r0.cacheImage;
            r0 = r4.finalFilePath;
            r17 = r0;
            r0 = r22;
            r4 = r0.cacheImage;
            r4 = r4.encryptionKeyPath;
            if (r4 == 0) goto L_0x0128;
        L_0x0076:
            if (r17 == 0) goto L_0x0128;
        L_0x0078:
            r4 = r17.getAbsolutePath();
            r5 = ".enc";
            r4 = r4.endsWith(r5);
            if (r4 == 0) goto L_0x0128;
        L_0x0085:
            r4 = 1;
        L_0x0086:
            r12 = 1;
            r5 = 0;
            r6 = android.os.Build.VERSION.SDK_INT;
            r9 = 19;
            if (r6 >= r9) goto L_0x00d2;
        L_0x008e:
            r11 = 0;
            r9 = new java.io.RandomAccessFile;	 Catch:{ Exception -> 0x0136, all -> 0x0146 }
            r6 = "r";
            r0 = r17;
            r9.<init>(r0, r6);	 Catch:{ Exception -> 0x0136, all -> 0x0146 }
            r0 = r22;
            r6 = r0.cacheImage;	 Catch:{ Exception -> 0x06e6 }
            r6 = r6.selfThumb;	 Catch:{ Exception -> 0x06e6 }
            if (r6 == 0) goto L_0x012b;
        L_0x00a1:
            r6 = org.telegram.messenger.ImageLoader.headerThumb;	 Catch:{ Exception -> 0x06e6 }
        L_0x00a5:
            r11 = 0;
            r13 = r6.length;	 Catch:{ Exception -> 0x06e6 }
            r9.readFully(r6, r11, r13);	 Catch:{ Exception -> 0x06e6 }
            r11 = new java.lang.String;	 Catch:{ Exception -> 0x06e6 }
            r11.<init>(r6);	 Catch:{ Exception -> 0x06e6 }
            r6 = r11.toLowerCase();	 Catch:{ Exception -> 0x06e6 }
            r6 = r6.toLowerCase();	 Catch:{ Exception -> 0x06e6 }
            r11 = "riff";
            r11 = r6.startsWith(r11);	 Catch:{ Exception -> 0x06e6 }
            if (r11 == 0) goto L_0x00ca;
        L_0x00c0:
            r11 = "webp";
            r6 = r6.endsWith(r11);	 Catch:{ Exception -> 0x06e6 }
            if (r6 == 0) goto L_0x00ca;
        L_0x00c9:
            r5 = 1;
        L_0x00ca:
            r9.close();	 Catch:{ Exception -> 0x06e6 }
            if (r9 == 0) goto L_0x00d2;
        L_0x00cf:
            r9.close();	 Catch:{ Exception -> 0x0131 }
        L_0x00d2:
            r0 = r22;
            r6 = r0.cacheImage;
            r6 = r6.selfThumb;
            if (r6 == 0) goto L_0x02fd;
        L_0x00da:
            r6 = 0;
            r0 = r22;
            r7 = r0.cacheImage;
            r7 = r7.filter;
            if (r7 == 0) goto L_0x06fd;
        L_0x00e3:
            r0 = r22;
            r7 = r0.cacheImage;
            r7 = r7.filter;
            r8 = "b2";
            r7 = r7.contains(r8);
            if (r7 == 0) goto L_0x0153;
        L_0x00f2:
            r6 = 3;
            r11 = r6;
        L_0x00f4:
            r0 = r22;
            r6 = org.telegram.messenger.ImageLoader.this;	 Catch:{ Throwable -> 0x0110 }
            r8 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x0110 }
            r6.lastCacheOutTime = r8;	 Catch:{ Throwable -> 0x0110 }
            r0 = r22;
            r6 = r0.sync;	 Catch:{ Throwable -> 0x0110 }
            monitor-enter(r6);	 Catch:{ Throwable -> 0x0110 }
            r0 = r22;
            r7 = r0.isCancelled;	 Catch:{ all -> 0x010d }
            if (r7 == 0) goto L_0x0178;
        L_0x010a:
            monitor-exit(r6);	 Catch:{ all -> 0x010d }
            goto L_0x0017;
        L_0x010d:
            r4 = move-exception;
            monitor-exit(r6);	 Catch:{ all -> 0x010d }
            throw r4;	 Catch:{ Throwable -> 0x0110 }
        L_0x0110:
            r4 = move-exception;
            r5 = r4;
            r4 = r10;
        L_0x0113:
            org.telegram.messenger.FileLog.m13728e(r5);
        L_0x0116:
            java.lang.Thread.interrupted();
            if (r4 == 0) goto L_0x06d7;
        L_0x011b:
            r5 = new android.graphics.drawable.BitmapDrawable;
            r5.<init>(r4);
            r4 = r5;
        L_0x0121:
            r0 = r22;
            r0.onPostExecute(r4);
            goto L_0x0017;
        L_0x0128:
            r4 = 0;
            goto L_0x0086;
        L_0x012b:
            r6 = org.telegram.messenger.ImageLoader.header;	 Catch:{ Exception -> 0x06e6 }
            goto L_0x00a5;
        L_0x0131:
            r6 = move-exception;
            org.telegram.messenger.FileLog.m13728e(r6);
            goto L_0x00d2;
        L_0x0136:
            r6 = move-exception;
            r9 = r11;
        L_0x0138:
            org.telegram.messenger.FileLog.m13728e(r6);	 Catch:{ all -> 0x06e3 }
            if (r9 == 0) goto L_0x00d2;
        L_0x013d:
            r9.close();	 Catch:{ Exception -> 0x0141 }
            goto L_0x00d2;
        L_0x0141:
            r6 = move-exception;
            org.telegram.messenger.FileLog.m13728e(r6);
            goto L_0x00d2;
        L_0x0146:
            r4 = move-exception;
            r9 = r11;
        L_0x0148:
            if (r9 == 0) goto L_0x014d;
        L_0x014a:
            r9.close();	 Catch:{ Exception -> 0x014e }
        L_0x014d:
            throw r4;
        L_0x014e:
            r5 = move-exception;
            org.telegram.messenger.FileLog.m13728e(r5);
            goto L_0x014d;
        L_0x0153:
            r0 = r22;
            r7 = r0.cacheImage;
            r7 = r7.filter;
            r8 = "b1";
            r7 = r7.contains(r8);
            if (r7 == 0) goto L_0x0165;
        L_0x0162:
            r6 = 2;
            r11 = r6;
            goto L_0x00f4;
        L_0x0165:
            r0 = r22;
            r7 = r0.cacheImage;
            r7 = r7.filter;
            r8 = "b";
            r7 = r7.contains(r8);
            if (r7 == 0) goto L_0x06fd;
        L_0x0174:
            r6 = 1;
            r11 = r6;
            goto L_0x00f4;
        L_0x0178:
            monitor-exit(r6);	 Catch:{ all -> 0x010d }
            r12 = new android.graphics.BitmapFactory$Options;	 Catch:{ Throwable -> 0x0110 }
            r12.<init>();	 Catch:{ Throwable -> 0x0110 }
            r6 = 1;
            r12.inSampleSize = r6;	 Catch:{ Throwable -> 0x0110 }
            r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Throwable -> 0x0110 }
            r7 = 21;
            if (r6 >= r7) goto L_0x018a;
        L_0x0187:
            r6 = 1;
            r12.inPurgeable = r6;	 Catch:{ Throwable -> 0x0110 }
        L_0x018a:
            if (r5 == 0) goto L_0x01f0;
        L_0x018c:
            r13 = new java.io.RandomAccessFile;	 Catch:{ Throwable -> 0x0110 }
            r4 = "r";
            r0 = r17;
            r13.<init>(r0, r4);	 Catch:{ Throwable -> 0x0110 }
            r4 = r13.getChannel();	 Catch:{ Throwable -> 0x0110 }
            r5 = java.nio.channels.FileChannel.MapMode.READ_ONLY;	 Catch:{ Throwable -> 0x0110 }
            r6 = 0;
            r8 = r17.length();	 Catch:{ Throwable -> 0x0110 }
            r5 = r4.map(r5, r6, r8);	 Catch:{ Throwable -> 0x0110 }
            r4 = new android.graphics.BitmapFactory$Options;	 Catch:{ Throwable -> 0x0110 }
            r4.<init>();	 Catch:{ Throwable -> 0x0110 }
            r6 = 1;
            r4.inJustDecodeBounds = r6;	 Catch:{ Throwable -> 0x0110 }
            r6 = 0;
            r7 = r5.limit();	 Catch:{ Throwable -> 0x0110 }
            r8 = 1;
            org.telegram.messenger.Utilities.loadWebpImage(r6, r5, r7, r4, r8);	 Catch:{ Throwable -> 0x0110 }
            r6 = r4.outWidth;	 Catch:{ Throwable -> 0x0110 }
            r4 = r4.outHeight;	 Catch:{ Throwable -> 0x0110 }
            r7 = android.graphics.Bitmap.Config.ARGB_8888;	 Catch:{ Throwable -> 0x0110 }
            r10 = org.telegram.messenger.Bitmaps.createBitmap(r6, r4, r7);	 Catch:{ Throwable -> 0x0110 }
            r6 = r5.limit();	 Catch:{ Throwable -> 0x06de }
            r7 = 0;
            r4 = r12.inPurgeable;	 Catch:{ Throwable -> 0x06de }
            if (r4 != 0) goto L_0x01ee;
        L_0x01ca:
            r4 = 1;
        L_0x01cb:
            org.telegram.messenger.Utilities.loadWebpImage(r10, r5, r6, r7, r4);	 Catch:{ Throwable -> 0x06de }
            r13.close();	 Catch:{ Throwable -> 0x06de }
            r4 = r10;
        L_0x01d2:
            if (r4 != 0) goto L_0x0258;
        L_0x01d4:
            r6 = r17.length();	 Catch:{ Throwable -> 0x01eb }
            r8 = 0;
            r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
            if (r5 == 0) goto L_0x01e6;
        L_0x01de:
            r0 = r22;
            r5 = r0.cacheImage;	 Catch:{ Throwable -> 0x01eb }
            r5 = r5.filter;	 Catch:{ Throwable -> 0x01eb }
            if (r5 != 0) goto L_0x0116;
        L_0x01e6:
            r17.delete();	 Catch:{ Throwable -> 0x01eb }
            goto L_0x0116;
        L_0x01eb:
            r5 = move-exception;
            goto L_0x0113;
        L_0x01ee:
            r4 = 0;
            goto L_0x01cb;
        L_0x01f0:
            r5 = r12.inPurgeable;	 Catch:{ Throwable -> 0x0110 }
            if (r5 == 0) goto L_0x0237;
        L_0x01f4:
            r6 = new java.io.RandomAccessFile;	 Catch:{ Throwable -> 0x0110 }
            r5 = "r";
            r0 = r17;
            r6.<init>(r0, r5);	 Catch:{ Throwable -> 0x0110 }
            r8 = r6.length();	 Catch:{ Throwable -> 0x0110 }
            r7 = (int) r8;	 Catch:{ Throwable -> 0x0110 }
            r5 = org.telegram.messenger.ImageLoader.bytesThumb;	 Catch:{ Throwable -> 0x0110 }
            if (r5 == 0) goto L_0x0235;
        L_0x0209:
            r5 = org.telegram.messenger.ImageLoader.bytesThumb;	 Catch:{ Throwable -> 0x0110 }
            r5 = r5.length;	 Catch:{ Throwable -> 0x0110 }
            if (r5 < r7) goto L_0x0235;
        L_0x0210:
            r5 = org.telegram.messenger.ImageLoader.bytesThumb;	 Catch:{ Throwable -> 0x0110 }
        L_0x0214:
            if (r5 != 0) goto L_0x021b;
        L_0x0216:
            r5 = new byte[r7];	 Catch:{ Throwable -> 0x0110 }
            org.telegram.messenger.ImageLoader.bytesThumb = r5;	 Catch:{ Throwable -> 0x0110 }
        L_0x021b:
            r8 = 0;
            r6.readFully(r5, r8, r7);	 Catch:{ Throwable -> 0x0110 }
            r6.close();	 Catch:{ Throwable -> 0x0110 }
            if (r4 == 0) goto L_0x022e;
        L_0x0224:
            r4 = 0;
            r0 = r22;
            r6 = r0.cacheImage;	 Catch:{ Throwable -> 0x0110 }
            r6 = r6.encryptionKeyPath;	 Catch:{ Throwable -> 0x0110 }
            org.telegram.messenger.secretmedia.EncryptedFileInputStream.decryptBytesWithKeyFile(r5, r4, r7, r6);	 Catch:{ Throwable -> 0x0110 }
        L_0x022e:
            r4 = 0;
            r10 = android.graphics.BitmapFactory.decodeByteArray(r5, r4, r7, r12);	 Catch:{ Throwable -> 0x0110 }
            r4 = r10;
            goto L_0x01d2;
        L_0x0235:
            r5 = 0;
            goto L_0x0214;
        L_0x0237:
            if (r4 == 0) goto L_0x0250;
        L_0x0239:
            r4 = new org.telegram.messenger.secretmedia.EncryptedFileInputStream;	 Catch:{ Throwable -> 0x0110 }
            r0 = r22;
            r5 = r0.cacheImage;	 Catch:{ Throwable -> 0x0110 }
            r5 = r5.encryptionKeyPath;	 Catch:{ Throwable -> 0x0110 }
            r0 = r17;
            r4.<init>(r0, r5);	 Catch:{ Throwable -> 0x0110 }
        L_0x0246:
            r5 = 0;
            r10 = android.graphics.BitmapFactory.decodeStream(r4, r5, r12);	 Catch:{ Throwable -> 0x0110 }
            r4.close();	 Catch:{ Throwable -> 0x06de }
            r4 = r10;
            goto L_0x01d2;
        L_0x0250:
            r4 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x0110 }
            r0 = r17;
            r4.<init>(r0);	 Catch:{ Throwable -> 0x0110 }
            goto L_0x0246;
        L_0x0258:
            r5 = 1;
            if (r11 != r5) goto L_0x027c;
        L_0x025b:
            r5 = r4.getConfig();	 Catch:{ Throwable -> 0x01eb }
            r6 = android.graphics.Bitmap.Config.ARGB_8888;	 Catch:{ Throwable -> 0x01eb }
            if (r5 != r6) goto L_0x0116;
        L_0x0263:
            r5 = 3;
            r6 = r12.inPurgeable;	 Catch:{ Throwable -> 0x01eb }
            if (r6 == 0) goto L_0x027a;
        L_0x0268:
            r6 = 0;
        L_0x0269:
            r7 = r4.getWidth();	 Catch:{ Throwable -> 0x01eb }
            r8 = r4.getHeight();	 Catch:{ Throwable -> 0x01eb }
            r9 = r4.getRowBytes();	 Catch:{ Throwable -> 0x01eb }
            org.telegram.messenger.Utilities.blurBitmap(r4, r5, r6, r7, r8, r9);	 Catch:{ Throwable -> 0x01eb }
            goto L_0x0116;
        L_0x027a:
            r6 = 1;
            goto L_0x0269;
        L_0x027c:
            r5 = 2;
            if (r11 != r5) goto L_0x02a0;
        L_0x027f:
            r5 = r4.getConfig();	 Catch:{ Throwable -> 0x01eb }
            r6 = android.graphics.Bitmap.Config.ARGB_8888;	 Catch:{ Throwable -> 0x01eb }
            if (r5 != r6) goto L_0x0116;
        L_0x0287:
            r5 = 1;
            r6 = r12.inPurgeable;	 Catch:{ Throwable -> 0x01eb }
            if (r6 == 0) goto L_0x029e;
        L_0x028c:
            r6 = 0;
        L_0x028d:
            r7 = r4.getWidth();	 Catch:{ Throwable -> 0x01eb }
            r8 = r4.getHeight();	 Catch:{ Throwable -> 0x01eb }
            r9 = r4.getRowBytes();	 Catch:{ Throwable -> 0x01eb }
            org.telegram.messenger.Utilities.blurBitmap(r4, r5, r6, r7, r8, r9);	 Catch:{ Throwable -> 0x01eb }
            goto L_0x0116;
        L_0x029e:
            r6 = 1;
            goto L_0x028d;
        L_0x02a0:
            r5 = 3;
            if (r11 != r5) goto L_0x02f2;
        L_0x02a3:
            r5 = r4.getConfig();	 Catch:{ Throwable -> 0x01eb }
            r6 = android.graphics.Bitmap.Config.ARGB_8888;	 Catch:{ Throwable -> 0x01eb }
            if (r5 != r6) goto L_0x0116;
        L_0x02ab:
            r5 = 7;
            r6 = r12.inPurgeable;	 Catch:{ Throwable -> 0x01eb }
            if (r6 == 0) goto L_0x02ec;
        L_0x02b0:
            r6 = 0;
        L_0x02b1:
            r7 = r4.getWidth();	 Catch:{ Throwable -> 0x01eb }
            r8 = r4.getHeight();	 Catch:{ Throwable -> 0x01eb }
            r9 = r4.getRowBytes();	 Catch:{ Throwable -> 0x01eb }
            org.telegram.messenger.Utilities.blurBitmap(r4, r5, r6, r7, r8, r9);	 Catch:{ Throwable -> 0x01eb }
            r5 = 7;
            r6 = r12.inPurgeable;	 Catch:{ Throwable -> 0x01eb }
            if (r6 == 0) goto L_0x02ee;
        L_0x02c5:
            r6 = 0;
        L_0x02c6:
            r7 = r4.getWidth();	 Catch:{ Throwable -> 0x01eb }
            r8 = r4.getHeight();	 Catch:{ Throwable -> 0x01eb }
            r9 = r4.getRowBytes();	 Catch:{ Throwable -> 0x01eb }
            org.telegram.messenger.Utilities.blurBitmap(r4, r5, r6, r7, r8, r9);	 Catch:{ Throwable -> 0x01eb }
            r5 = 7;
            r6 = r12.inPurgeable;	 Catch:{ Throwable -> 0x01eb }
            if (r6 == 0) goto L_0x02f0;
        L_0x02da:
            r6 = 0;
        L_0x02db:
            r7 = r4.getWidth();	 Catch:{ Throwable -> 0x01eb }
            r8 = r4.getHeight();	 Catch:{ Throwable -> 0x01eb }
            r9 = r4.getRowBytes();	 Catch:{ Throwable -> 0x01eb }
            org.telegram.messenger.Utilities.blurBitmap(r4, r5, r6, r7, r8, r9);	 Catch:{ Throwable -> 0x01eb }
            goto L_0x0116;
        L_0x02ec:
            r6 = 1;
            goto L_0x02b1;
        L_0x02ee:
            r6 = 1;
            goto L_0x02c6;
        L_0x02f0:
            r6 = 1;
            goto L_0x02db;
        L_0x02f2:
            if (r11 != 0) goto L_0x0116;
        L_0x02f4:
            r5 = r12.inPurgeable;	 Catch:{ Throwable -> 0x01eb }
            if (r5 == 0) goto L_0x0116;
        L_0x02f8:
            org.telegram.messenger.Utilities.pinBitmap(r4);	 Catch:{ Throwable -> 0x01eb }
            goto L_0x0116;
        L_0x02fd:
            r6 = 0;
            r0 = r22;
            r9 = r0.cacheImage;	 Catch:{ Throwable -> 0x039d }
            r9 = r9.httpUrl;	 Catch:{ Throwable -> 0x039d }
            if (r9 == 0) goto L_0x06f6;
        L_0x0306:
            r0 = r22;
            r9 = r0.cacheImage;	 Catch:{ Throwable -> 0x039d }
            r9 = r9.httpUrl;	 Catch:{ Throwable -> 0x039d }
            r11 = "thumb://";
            r9 = r9.startsWith(r11);	 Catch:{ Throwable -> 0x039d }
            if (r9 == 0) goto L_0x03a1;
        L_0x0315:
            r0 = r22;
            r9 = r0.cacheImage;	 Catch:{ Throwable -> 0x039d }
            r9 = r9.httpUrl;	 Catch:{ Throwable -> 0x039d }
            r11 = ":";
            r12 = 8;
            r9 = r9.indexOf(r11, r12);	 Catch:{ Throwable -> 0x039d }
            if (r9 < 0) goto L_0x0347;
        L_0x0326:
            r0 = r22;
            r6 = r0.cacheImage;	 Catch:{ Throwable -> 0x039d }
            r6 = r6.httpUrl;	 Catch:{ Throwable -> 0x039d }
            r7 = 8;
            r6 = r6.substring(r7, r9);	 Catch:{ Throwable -> 0x039d }
            r6 = java.lang.Long.parseLong(r6);	 Catch:{ Throwable -> 0x039d }
            r8 = java.lang.Long.valueOf(r6);	 Catch:{ Throwable -> 0x039d }
            r7 = 0;
            r0 = r22;
            r6 = r0.cacheImage;	 Catch:{ Throwable -> 0x039d }
            r6 = r6.httpUrl;	 Catch:{ Throwable -> 0x039d }
            r9 = r9 + 1;
            r6 = r6.substring(r9);	 Catch:{ Throwable -> 0x039d }
        L_0x0347:
            r9 = 0;
            r13 = r6;
            r14 = r9;
            r15 = r7;
            r16 = r8;
        L_0x034d:
            r6 = 20;
            if (r16 == 0) goto L_0x0352;
        L_0x0351:
            r6 = 0;
        L_0x0352:
            if (r6 == 0) goto L_0x0381;
        L_0x0354:
            r0 = r22;
            r7 = org.telegram.messenger.ImageLoader.this;	 Catch:{ Throwable -> 0x039d }
            r8 = r7.lastCacheOutTime;	 Catch:{ Throwable -> 0x039d }
            r18 = 0;
            r7 = (r8 > r18 ? 1 : (r8 == r18 ? 0 : -1));
            if (r7 == 0) goto L_0x0381;
        L_0x0362:
            r0 = r22;
            r7 = org.telegram.messenger.ImageLoader.this;	 Catch:{ Throwable -> 0x039d }
            r8 = r7.lastCacheOutTime;	 Catch:{ Throwable -> 0x039d }
            r18 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x039d }
            r0 = (long) r6;	 Catch:{ Throwable -> 0x039d }
            r20 = r0;
            r18 = r18 - r20;
            r7 = (r8 > r18 ? 1 : (r8 == r18 ? 0 : -1));
            if (r7 <= 0) goto L_0x0381;
        L_0x0377:
            r7 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Throwable -> 0x039d }
            r8 = 21;
            if (r7 >= r8) goto L_0x0381;
        L_0x037d:
            r6 = (long) r6;	 Catch:{ Throwable -> 0x039d }
            java.lang.Thread.sleep(r6);	 Catch:{ Throwable -> 0x039d }
        L_0x0381:
            r0 = r22;
            r6 = org.telegram.messenger.ImageLoader.this;	 Catch:{ Throwable -> 0x039d }
            r8 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x039d }
            r6.lastCacheOutTime = r8;	 Catch:{ Throwable -> 0x039d }
            r0 = r22;
            r6 = r0.sync;	 Catch:{ Throwable -> 0x039d }
            monitor-enter(r6);	 Catch:{ Throwable -> 0x039d }
            r0 = r22;
            r7 = r0.isCancelled;	 Catch:{ all -> 0x039a }
            if (r7 == 0) goto L_0x03f5;
        L_0x0397:
            monitor-exit(r6);	 Catch:{ all -> 0x039a }
            goto L_0x0017;
        L_0x039a:
            r4 = move-exception;
            monitor-exit(r6);	 Catch:{ all -> 0x039a }
            throw r4;	 Catch:{ Throwable -> 0x039d }
        L_0x039d:
            r4 = move-exception;
            r4 = r10;
            goto L_0x0116;
        L_0x03a1:
            r0 = r22;
            r9 = r0.cacheImage;	 Catch:{ Throwable -> 0x039d }
            r9 = r9.httpUrl;	 Catch:{ Throwable -> 0x039d }
            r11 = "vthumb://";
            r9 = r9.startsWith(r11);	 Catch:{ Throwable -> 0x039d }
            if (r9 == 0) goto L_0x03de;
        L_0x03b0:
            r0 = r22;
            r9 = r0.cacheImage;	 Catch:{ Throwable -> 0x039d }
            r9 = r9.httpUrl;	 Catch:{ Throwable -> 0x039d }
            r11 = ":";
            r12 = 9;
            r9 = r9.indexOf(r11, r12);	 Catch:{ Throwable -> 0x039d }
            if (r9 < 0) goto L_0x03d6;
        L_0x03c1:
            r0 = r22;
            r7 = r0.cacheImage;	 Catch:{ Throwable -> 0x039d }
            r7 = r7.httpUrl;	 Catch:{ Throwable -> 0x039d }
            r8 = 9;
            r7 = r7.substring(r8, r9);	 Catch:{ Throwable -> 0x039d }
            r8 = java.lang.Long.parseLong(r7);	 Catch:{ Throwable -> 0x039d }
            r8 = java.lang.Long.valueOf(r8);	 Catch:{ Throwable -> 0x039d }
            r7 = 1;
        L_0x03d6:
            r9 = 0;
            r13 = r6;
            r14 = r9;
            r15 = r7;
            r16 = r8;
            goto L_0x034d;
        L_0x03de:
            r0 = r22;
            r9 = r0.cacheImage;	 Catch:{ Throwable -> 0x039d }
            r9 = r9.httpUrl;	 Catch:{ Throwable -> 0x039d }
            r11 = "http";
            r9 = r9.startsWith(r11);	 Catch:{ Throwable -> 0x039d }
            if (r9 != 0) goto L_0x06f6;
        L_0x03ed:
            r9 = 0;
            r13 = r6;
            r14 = r9;
            r15 = r7;
            r16 = r8;
            goto L_0x034d;
        L_0x03f5:
            monitor-exit(r6);	 Catch:{ all -> 0x039a }
            r18 = new android.graphics.BitmapFactory$Options;	 Catch:{ Throwable -> 0x039d }
            r18.<init>();	 Catch:{ Throwable -> 0x039d }
            r6 = 1;
            r0 = r18;
            r0.inSampleSize = r6;	 Catch:{ Throwable -> 0x039d }
            r7 = 0;
            r8 = 0;
            r6 = 0;
            r0 = r22;
            r9 = r0.cacheImage;	 Catch:{ Throwable -> 0x039d }
            r9 = r9.filter;	 Catch:{ Throwable -> 0x039d }
            if (r9 == 0) goto L_0x04db;
        L_0x040b:
            r0 = r22;
            r9 = r0.cacheImage;	 Catch:{ Throwable -> 0x039d }
            r9 = r9.filter;	 Catch:{ Throwable -> 0x039d }
            r11 = "_";
            r9 = r9.split(r11);	 Catch:{ Throwable -> 0x039d }
            r11 = r9.length;	 Catch:{ Throwable -> 0x039d }
            r12 = 2;
            if (r11 < r12) goto L_0x06f2;
        L_0x041c:
            r7 = 0;
            r7 = r9[r7];	 Catch:{ Throwable -> 0x039d }
            r7 = java.lang.Float.parseFloat(r7);	 Catch:{ Throwable -> 0x039d }
            r8 = org.telegram.messenger.AndroidUtilities.density;	 Catch:{ Throwable -> 0x039d }
            r8 = r8 * r7;
            r7 = 1;
            r7 = r9[r7];	 Catch:{ Throwable -> 0x039d }
            r7 = java.lang.Float.parseFloat(r7);	 Catch:{ Throwable -> 0x039d }
            r9 = org.telegram.messenger.AndroidUtilities.density;	 Catch:{ Throwable -> 0x039d }
            r7 = r7 * r9;
            r9 = r7;
        L_0x0431:
            r0 = r22;
            r7 = r0.cacheImage;	 Catch:{ Throwable -> 0x039d }
            r7 = r7.filter;	 Catch:{ Throwable -> 0x039d }
            r11 = "b";
            r7 = r7.contains(r11);	 Catch:{ Throwable -> 0x039d }
            if (r7 == 0) goto L_0x06ef;
        L_0x0440:
            r7 = 1;
        L_0x0441:
            r6 = 0;
            r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
            if (r6 == 0) goto L_0x06ec;
        L_0x0446:
            r6 = 0;
            r6 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1));
            if (r6 == 0) goto L_0x06ec;
        L_0x044b:
            r6 = 1;
            r0 = r18;
            r0.inJustDecodeBounds = r6;	 Catch:{ Throwable -> 0x039d }
            if (r16 == 0) goto L_0x04b8;
        L_0x0452:
            if (r13 != 0) goto L_0x04b8;
        L_0x0454:
            if (r15 == 0) goto L_0x04a4;
        L_0x0456:
            r6 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Throwable -> 0x039d }
            r6 = r6.getContentResolver();	 Catch:{ Throwable -> 0x039d }
            r20 = r16.longValue();	 Catch:{ Throwable -> 0x039d }
            r11 = 1;
            r0 = r20;
            r2 = r18;
            android.provider.MediaStore.Video.Thumbnails.getThumbnail(r6, r0, r11, r2);	 Catch:{ Throwable -> 0x039d }
            r6 = r10;
        L_0x0469:
            r0 = r18;
            r10 = r0.outWidth;	 Catch:{ Throwable -> 0x06da }
            r10 = (float) r10;	 Catch:{ Throwable -> 0x06da }
            r0 = r18;
            r11 = r0.outHeight;	 Catch:{ Throwable -> 0x06da }
            r11 = (float) r11;	 Catch:{ Throwable -> 0x06da }
            r10 = r10 / r8;
            r9 = r11 / r9;
            r9 = java.lang.Math.max(r10, r9);	 Catch:{ Throwable -> 0x06da }
            r10 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
            r10 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1));
            if (r10 >= 0) goto L_0x0482;
        L_0x0480:
            r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        L_0x0482:
            r10 = 0;
            r0 = r18;
            r0.inJustDecodeBounds = r10;	 Catch:{ Throwable -> 0x06da }
            r9 = (int) r9;	 Catch:{ Throwable -> 0x06da }
            r0 = r18;
            r0.inSampleSize = r9;	 Catch:{ Throwable -> 0x06da }
        L_0x048c:
            r11 = r7;
            r12 = r8;
            r10 = r6;
        L_0x048f:
            r0 = r22;
            r6 = r0.sync;	 Catch:{ Throwable -> 0x04a0 }
            monitor-enter(r6);	 Catch:{ Throwable -> 0x04a0 }
            r0 = r22;
            r7 = r0.isCancelled;	 Catch:{ all -> 0x049d }
            if (r7 == 0) goto L_0x0527;
        L_0x049a:
            monitor-exit(r6);	 Catch:{ all -> 0x049d }
            goto L_0x0017;
        L_0x049d:
            r4 = move-exception;
            monitor-exit(r6);	 Catch:{ all -> 0x049d }
            throw r4;	 Catch:{ Throwable -> 0x04a0 }
        L_0x04a0:
            r4 = move-exception;
            r4 = r10;
            goto L_0x0116;
        L_0x04a4:
            r6 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Throwable -> 0x039d }
            r6 = r6.getContentResolver();	 Catch:{ Throwable -> 0x039d }
            r20 = r16.longValue();	 Catch:{ Throwable -> 0x039d }
            r11 = 1;
            r0 = r20;
            r2 = r18;
            android.provider.MediaStore.Images.Thumbnails.getThumbnail(r6, r0, r11, r2);	 Catch:{ Throwable -> 0x039d }
            r6 = r10;
            goto L_0x0469;
        L_0x04b8:
            if (r4 == 0) goto L_0x04d3;
        L_0x04ba:
            r6 = new org.telegram.messenger.secretmedia.EncryptedFileInputStream;	 Catch:{ Throwable -> 0x039d }
            r0 = r22;
            r11 = r0.cacheImage;	 Catch:{ Throwable -> 0x039d }
            r11 = r11.encryptionKeyPath;	 Catch:{ Throwable -> 0x039d }
            r0 = r17;
            r6.<init>(r0, r11);	 Catch:{ Throwable -> 0x039d }
        L_0x04c7:
            r11 = 0;
            r0 = r18;
            r10 = android.graphics.BitmapFactory.decodeStream(r6, r11, r0);	 Catch:{ Throwable -> 0x039d }
            r6.close();	 Catch:{ Throwable -> 0x04a0 }
            r6 = r10;
            goto L_0x0469;
        L_0x04d3:
            r6 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x039d }
            r0 = r17;
            r6.<init>(r0);	 Catch:{ Throwable -> 0x039d }
            goto L_0x04c7;
        L_0x04db:
            if (r13 == 0) goto L_0x0523;
        L_0x04dd:
            r8 = 1;
            r0 = r18;
            r0.inJustDecodeBounds = r8;	 Catch:{ Throwable -> 0x039d }
            r8 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x039d }
            r0 = r17;
            r8.<init>(r0);	 Catch:{ Throwable -> 0x039d }
            r9 = 0;
            r0 = r18;
            r10 = android.graphics.BitmapFactory.decodeStream(r8, r9, r0);	 Catch:{ Throwable -> 0x039d }
            r8.close();	 Catch:{ Throwable -> 0x04a0 }
            r0 = r18;
            r8 = r0.outWidth;	 Catch:{ Throwable -> 0x04a0 }
            r8 = (float) r8;	 Catch:{ Throwable -> 0x04a0 }
            r0 = r18;
            r9 = r0.outHeight;	 Catch:{ Throwable -> 0x04a0 }
            r9 = (float) r9;	 Catch:{ Throwable -> 0x04a0 }
            r11 = 1140850688; // 0x44000000 float:512.0 double:5.63655132E-315;
            r8 = r8 / r11;
            r11 = 1136656384; // 0x43c00000 float:384.0 double:5.615828705E-315;
            r9 = r9 / r11;
            r8 = java.lang.Math.max(r8, r9);	 Catch:{ Throwable -> 0x04a0 }
            r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
            r9 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1));
            if (r9 >= 0) goto L_0x06e9;
        L_0x050d:
            r8 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
            r9 = r8;
        L_0x0510:
            r8 = 0;
            r0 = r18;
            r0.inJustDecodeBounds = r8;	 Catch:{ Throwable -> 0x04a0 }
            r8 = 1;
        L_0x0516:
            r8 = r8 * 2;
            r11 = r8 * 2;
            r11 = (float) r11;	 Catch:{ Throwable -> 0x04a0 }
            r11 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1));
            if (r11 < 0) goto L_0x0516;
        L_0x051f:
            r0 = r18;
            r0.inSampleSize = r8;	 Catch:{ Throwable -> 0x04a0 }
        L_0x0523:
            r11 = r6;
            r12 = r7;
            goto L_0x048f;
        L_0x0527:
            monitor-exit(r6);	 Catch:{ all -> 0x049d }
            r0 = r22;
            r6 = r0.cacheImage;	 Catch:{ Throwable -> 0x04a0 }
            r6 = r6.filter;	 Catch:{ Throwable -> 0x04a0 }
            if (r6 == 0) goto L_0x053a;
        L_0x0530:
            if (r11 != 0) goto L_0x053a;
        L_0x0532:
            r0 = r22;
            r6 = r0.cacheImage;	 Catch:{ Throwable -> 0x04a0 }
            r6 = r6.httpUrl;	 Catch:{ Throwable -> 0x04a0 }
            if (r6 == 0) goto L_0x05d1;
        L_0x053a:
            r6 = android.graphics.Bitmap.Config.ARGB_8888;	 Catch:{ Throwable -> 0x04a0 }
            r0 = r18;
            r0.inPreferredConfig = r6;	 Catch:{ Throwable -> 0x04a0 }
        L_0x0540:
            r6 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Throwable -> 0x04a0 }
            r7 = 21;
            if (r6 >= r7) goto L_0x054b;
        L_0x0546:
            r6 = 1;
            r0 = r18;
            r0.inPurgeable = r6;	 Catch:{ Throwable -> 0x04a0 }
        L_0x054b:
            r6 = 0;
            r0 = r18;
            r0.inDither = r6;	 Catch:{ Throwable -> 0x04a0 }
            if (r16 == 0) goto L_0x0567;
        L_0x0552:
            if (r13 != 0) goto L_0x0567;
        L_0x0554:
            if (r15 == 0) goto L_0x05d9;
        L_0x0556:
            r6 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Throwable -> 0x04a0 }
            r6 = r6.getContentResolver();	 Catch:{ Throwable -> 0x04a0 }
            r8 = r16.longValue();	 Catch:{ Throwable -> 0x04a0 }
            r7 = 1;
            r0 = r18;
            r10 = android.provider.MediaStore.Video.Thumbnails.getThumbnail(r6, r8, r7, r0);	 Catch:{ Throwable -> 0x04a0 }
        L_0x0567:
            if (r10 != 0) goto L_0x0653;
        L_0x0569:
            if (r5 == 0) goto L_0x05ee;
        L_0x056b:
            r13 = new java.io.RandomAccessFile;	 Catch:{ Throwable -> 0x04a0 }
            r4 = "r";
            r0 = r17;
            r13.<init>(r0, r4);	 Catch:{ Throwable -> 0x04a0 }
            r4 = r13.getChannel();	 Catch:{ Throwable -> 0x04a0 }
            r5 = java.nio.channels.FileChannel.MapMode.READ_ONLY;	 Catch:{ Throwable -> 0x04a0 }
            r6 = 0;
            r8 = r17.length();	 Catch:{ Throwable -> 0x04a0 }
            r5 = r4.map(r5, r6, r8);	 Catch:{ Throwable -> 0x04a0 }
            r4 = new android.graphics.BitmapFactory$Options;	 Catch:{ Throwable -> 0x04a0 }
            r4.<init>();	 Catch:{ Throwable -> 0x04a0 }
            r6 = 1;
            r4.inJustDecodeBounds = r6;	 Catch:{ Throwable -> 0x04a0 }
            r6 = 0;
            r7 = r5.limit();	 Catch:{ Throwable -> 0x04a0 }
            r8 = 1;
            org.telegram.messenger.Utilities.loadWebpImage(r6, r5, r7, r4, r8);	 Catch:{ Throwable -> 0x04a0 }
            r6 = r4.outWidth;	 Catch:{ Throwable -> 0x04a0 }
            r4 = r4.outHeight;	 Catch:{ Throwable -> 0x04a0 }
            r7 = android.graphics.Bitmap.Config.ARGB_8888;	 Catch:{ Throwable -> 0x04a0 }
            r10 = org.telegram.messenger.Bitmaps.createBitmap(r6, r4, r7);	 Catch:{ Throwable -> 0x04a0 }
            r6 = r5.limit();	 Catch:{ Throwable -> 0x04a0 }
            r7 = 0;
            r0 = r18;
            r4 = r0.inPurgeable;	 Catch:{ Throwable -> 0x04a0 }
            if (r4 != 0) goto L_0x05ec;
        L_0x05ab:
            r4 = 1;
        L_0x05ac:
            org.telegram.messenger.Utilities.loadWebpImage(r10, r5, r6, r7, r4);	 Catch:{ Throwable -> 0x04a0 }
            r13.close();	 Catch:{ Throwable -> 0x04a0 }
            r4 = r10;
        L_0x05b3:
            if (r4 != 0) goto L_0x065e;
        L_0x05b5:
            if (r14 == 0) goto L_0x0116;
        L_0x05b7:
            r6 = r17.length();	 Catch:{ Throwable -> 0x05ce }
            r8 = 0;
            r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
            if (r5 == 0) goto L_0x05c9;
        L_0x05c1:
            r0 = r22;
            r5 = r0.cacheImage;	 Catch:{ Throwable -> 0x05ce }
            r5 = r5.filter;	 Catch:{ Throwable -> 0x05ce }
            if (r5 != 0) goto L_0x0116;
        L_0x05c9:
            r17.delete();	 Catch:{ Throwable -> 0x05ce }
            goto L_0x0116;
        L_0x05ce:
            r5 = move-exception;
            goto L_0x0116;
        L_0x05d1:
            r6 = android.graphics.Bitmap.Config.RGB_565;	 Catch:{ Throwable -> 0x04a0 }
            r0 = r18;
            r0.inPreferredConfig = r6;	 Catch:{ Throwable -> 0x04a0 }
            goto L_0x0540;
        L_0x05d9:
            r6 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Throwable -> 0x04a0 }
            r6 = r6.getContentResolver();	 Catch:{ Throwable -> 0x04a0 }
            r8 = r16.longValue();	 Catch:{ Throwable -> 0x04a0 }
            r7 = 1;
            r0 = r18;
            r10 = android.provider.MediaStore.Images.Thumbnails.getThumbnail(r6, r8, r7, r0);	 Catch:{ Throwable -> 0x04a0 }
            goto L_0x0567;
        L_0x05ec:
            r4 = 0;
            goto L_0x05ac;
        L_0x05ee:
            r0 = r18;
            r5 = r0.inPurgeable;	 Catch:{ Throwable -> 0x04a0 }
            if (r5 == 0) goto L_0x063a;
        L_0x05f4:
            r6 = new java.io.RandomAccessFile;	 Catch:{ Throwable -> 0x04a0 }
            r5 = "r";
            r0 = r17;
            r6.<init>(r0, r5);	 Catch:{ Throwable -> 0x04a0 }
            r8 = r6.length();	 Catch:{ Throwable -> 0x04a0 }
            r7 = (int) r8;	 Catch:{ Throwable -> 0x04a0 }
            r5 = org.telegram.messenger.ImageLoader.bytes;	 Catch:{ Throwable -> 0x04a0 }
            if (r5 == 0) goto L_0x0638;
        L_0x0609:
            r5 = org.telegram.messenger.ImageLoader.bytes;	 Catch:{ Throwable -> 0x04a0 }
            r5 = r5.length;	 Catch:{ Throwable -> 0x04a0 }
            if (r5 < r7) goto L_0x0638;
        L_0x0610:
            r5 = org.telegram.messenger.ImageLoader.bytes;	 Catch:{ Throwable -> 0x04a0 }
        L_0x0614:
            if (r5 != 0) goto L_0x061b;
        L_0x0616:
            r5 = new byte[r7];	 Catch:{ Throwable -> 0x04a0 }
            org.telegram.messenger.ImageLoader.bytes = r5;	 Catch:{ Throwable -> 0x04a0 }
        L_0x061b:
            r8 = 0;
            r6.readFully(r5, r8, r7);	 Catch:{ Throwable -> 0x04a0 }
            r6.close();	 Catch:{ Throwable -> 0x04a0 }
            if (r4 == 0) goto L_0x062e;
        L_0x0624:
            r4 = 0;
            r0 = r22;
            r6 = r0.cacheImage;	 Catch:{ Throwable -> 0x04a0 }
            r6 = r6.encryptionKeyPath;	 Catch:{ Throwable -> 0x04a0 }
            org.telegram.messenger.secretmedia.EncryptedFileInputStream.decryptBytesWithKeyFile(r5, r4, r7, r6);	 Catch:{ Throwable -> 0x04a0 }
        L_0x062e:
            r4 = 0;
            r0 = r18;
            r10 = android.graphics.BitmapFactory.decodeByteArray(r5, r4, r7, r0);	 Catch:{ Throwable -> 0x04a0 }
            r4 = r10;
            goto L_0x05b3;
        L_0x0638:
            r5 = 0;
            goto L_0x0614;
        L_0x063a:
            if (r4 == 0) goto L_0x0656;
        L_0x063c:
            r4 = new org.telegram.messenger.secretmedia.EncryptedFileInputStream;	 Catch:{ Throwable -> 0x04a0 }
            r0 = r22;
            r5 = r0.cacheImage;	 Catch:{ Throwable -> 0x04a0 }
            r5 = r5.encryptionKeyPath;	 Catch:{ Throwable -> 0x04a0 }
            r0 = r17;
            r4.<init>(r0, r5);	 Catch:{ Throwable -> 0x04a0 }
        L_0x0649:
            r5 = 0;
            r0 = r18;
            r10 = android.graphics.BitmapFactory.decodeStream(r4, r5, r0);	 Catch:{ Throwable -> 0x04a0 }
            r4.close();	 Catch:{ Throwable -> 0x04a0 }
        L_0x0653:
            r4 = r10;
            goto L_0x05b3;
        L_0x0656:
            r4 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x04a0 }
            r0 = r17;
            r4.<init>(r0);	 Catch:{ Throwable -> 0x04a0 }
            goto L_0x0649;
        L_0x065e:
            r5 = 0;
            r0 = r22;
            r6 = r0.cacheImage;	 Catch:{ Throwable -> 0x05ce }
            r6 = r6.filter;	 Catch:{ Throwable -> 0x05ce }
            if (r6 == 0) goto L_0x06c8;
        L_0x0667:
            r6 = r4.getWidth();	 Catch:{ Throwable -> 0x05ce }
            r7 = (float) r6;	 Catch:{ Throwable -> 0x05ce }
            r6 = r4.getHeight();	 Catch:{ Throwable -> 0x05ce }
            r8 = (float) r6;	 Catch:{ Throwable -> 0x05ce }
            r0 = r18;
            r6 = r0.inPurgeable;	 Catch:{ Throwable -> 0x05ce }
            if (r6 != 0) goto L_0x0698;
        L_0x0677:
            r6 = 0;
            r6 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1));
            if (r6 == 0) goto L_0x0698;
        L_0x067c:
            r6 = (r7 > r12 ? 1 : (r7 == r12 ? 0 : -1));
            if (r6 == 0) goto L_0x0698;
        L_0x0680:
            r6 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
            r6 = r6 + r12;
            r6 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1));
            if (r6 <= 0) goto L_0x0698;
        L_0x0687:
            r6 = r7 / r12;
            r9 = (int) r12;	 Catch:{ Throwable -> 0x05ce }
            r6 = r8 / r6;
            r6 = (int) r6;	 Catch:{ Throwable -> 0x05ce }
            r10 = 1;
            r6 = org.telegram.messenger.Bitmaps.createScaledBitmap(r4, r9, r6, r10);	 Catch:{ Throwable -> 0x05ce }
            if (r4 == r6) goto L_0x0698;
        L_0x0694:
            r4.recycle();	 Catch:{ Throwable -> 0x05ce }
            r4 = r6;
        L_0x0698:
            if (r4 == 0) goto L_0x06c8;
        L_0x069a:
            if (r11 == 0) goto L_0x06c8;
        L_0x069c:
            r6 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
            r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1));
            if (r6 >= 0) goto L_0x06c8;
        L_0x06a2:
            r6 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
            r6 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1));
            if (r6 >= 0) goto L_0x06c8;
        L_0x06a8:
            r5 = r4.getConfig();	 Catch:{ Throwable -> 0x05ce }
            r6 = android.graphics.Bitmap.Config.ARGB_8888;	 Catch:{ Throwable -> 0x05ce }
            if (r5 != r6) goto L_0x06c7;
        L_0x06b0:
            r5 = 3;
            r0 = r18;
            r6 = r0.inPurgeable;	 Catch:{ Throwable -> 0x05ce }
            if (r6 == 0) goto L_0x06d5;
        L_0x06b7:
            r6 = 0;
        L_0x06b8:
            r7 = r4.getWidth();	 Catch:{ Throwable -> 0x05ce }
            r8 = r4.getHeight();	 Catch:{ Throwable -> 0x05ce }
            r9 = r4.getRowBytes();	 Catch:{ Throwable -> 0x05ce }
            org.telegram.messenger.Utilities.blurBitmap(r4, r5, r6, r7, r8, r9);	 Catch:{ Throwable -> 0x05ce }
        L_0x06c7:
            r5 = 1;
        L_0x06c8:
            if (r5 != 0) goto L_0x0116;
        L_0x06ca:
            r0 = r18;
            r5 = r0.inPurgeable;	 Catch:{ Throwable -> 0x05ce }
            if (r5 == 0) goto L_0x0116;
        L_0x06d0:
            org.telegram.messenger.Utilities.pinBitmap(r4);	 Catch:{ Throwable -> 0x05ce }
            goto L_0x0116;
        L_0x06d5:
            r6 = 1;
            goto L_0x06b8;
        L_0x06d7:
            r4 = 0;
            goto L_0x0121;
        L_0x06da:
            r4 = move-exception;
            r4 = r6;
            goto L_0x0116;
        L_0x06de:
            r4 = move-exception;
            r5 = r4;
            r4 = r10;
            goto L_0x0113;
        L_0x06e3:
            r4 = move-exception;
            goto L_0x0148;
        L_0x06e6:
            r6 = move-exception;
            goto L_0x0138;
        L_0x06e9:
            r9 = r8;
            goto L_0x0510;
        L_0x06ec:
            r6 = r10;
            goto L_0x048c;
        L_0x06ef:
            r7 = r6;
            goto L_0x0441;
        L_0x06f2:
            r9 = r8;
            r8 = r7;
            goto L_0x0431;
        L_0x06f6:
            r13 = r6;
            r14 = r12;
            r15 = r7;
            r16 = r8;
            goto L_0x034d;
        L_0x06fd:
            r11 = r6;
            goto L_0x00f4;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.ImageLoader.CacheOutTask.run():void");
        }
    }

    private class HttpFileTask extends AsyncTask<Void, Void, Boolean> {
        private boolean canRetry = true;
        private String ext;
        private RandomAccessFile fileOutputStream = null;
        private int fileSize;
        private long lastProgressTime;
        private File tempFile;
        private String url;

        public HttpFileTask(String str, File file, String str2) {
            this.url = str;
            this.tempFile = file;
            this.ext = str2;
        }

        private void reportProgress(final float f) {
            long currentTimeMillis = System.currentTimeMillis();
            if (f == 1.0f || this.lastProgressTime == 0 || this.lastProgressTime < currentTimeMillis - 500) {
                this.lastProgressTime = currentTimeMillis;
                Utilities.stageQueue.postRunnable(new Runnable() {

                    /* renamed from: org.telegram.messenger.ImageLoader$HttpFileTask$1$1 */
                    class C30861 implements Runnable {
                        C30861() {
                        }

                        public void run() {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.FileLoadProgressChanged, HttpFileTask.this.url, Float.valueOf(f));
                        }
                    }

                    public void run() {
                        ImageLoader.this.fileProgresses.put(HttpFileTask.this.url, Float.valueOf(f));
                        AndroidUtilities.runOnUIThread(new C30861());
                    }
                });
            }
        }

        protected Boolean doInBackground(Void... voidArr) {
            URLConnection openConnection;
            int responseCode;
            String headerField;
            Throwable th;
            Throwable th2;
            Throwable th3;
            InputStream inputStream;
            int responseCode2;
            Map headerFields;
            List list;
            byte[] bArr;
            boolean z;
            boolean z2 = false;
            InputStream inputStream2;
            try {
                openConnection = new URL(this.url).openConnection();
                try {
                    openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 10_0 like Mac OS X) AppleWebKit/602.1.38 (KHTML, like Gecko) Version/10.0 Mobile/14A5297c Safari/602.1");
                    openConnection.setConnectTimeout(DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS);
                    openConnection.setReadTimeout(DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS);
                    if (openConnection instanceof HttpURLConnection) {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) openConnection;
                        httpURLConnection.setInstanceFollowRedirects(true);
                        responseCode = httpURLConnection.getResponseCode();
                        if (responseCode == 302 || responseCode == 301 || responseCode == 303) {
                            String headerField2 = httpURLConnection.getHeaderField("Location");
                            headerField = httpURLConnection.getHeaderField("Set-Cookie");
                            openConnection = new URL(headerField2).openConnection();
                            openConnection.setRequestProperty("Cookie", headerField);
                            openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 10_0 like Mac OS X) AppleWebKit/602.1.38 (KHTML, like Gecko) Version/10.0 Mobile/14A5297c Safari/602.1");
                        }
                    }
                    openConnection.connect();
                    inputStream2 = openConnection.getInputStream();
                } catch (Throwable th22) {
                    th = th22;
                    inputStream2 = null;
                    th3 = th;
                    if (th3 instanceof SocketTimeoutException) {
                        if (ConnectionsManager.isNetworkOnline()) {
                            this.canRetry = false;
                        }
                    } else if (!(th3 instanceof UnknownHostException)) {
                        this.canRetry = false;
                    } else if (!(th3 instanceof SocketException)) {
                        if (th3.getMessage() != null && th3.getMessage().contains("ECONNRESET")) {
                            this.canRetry = false;
                        }
                    } else if (th3 instanceof FileNotFoundException) {
                        this.canRetry = false;
                    }
                    FileLog.m13728e(th3);
                    inputStream = inputStream2;
                    if (this.canRetry) {
                        if (openConnection != null) {
                            try {
                                if (openConnection instanceof HttpURLConnection) {
                                    responseCode2 = ((HttpURLConnection) openConnection).getResponseCode();
                                    this.canRetry = false;
                                }
                            } catch (Throwable th222) {
                                FileLog.m13728e(th222);
                            }
                        }
                        if (openConnection != null) {
                            try {
                                headerFields = openConnection.getHeaderFields();
                                if (headerFields != null) {
                                    list = (List) headerFields.get("content-Length");
                                    headerField = (String) list.get(0);
                                    if (headerField != null) {
                                        this.fileSize = Utilities.parseInt(headerField).intValue();
                                    }
                                }
                            } catch (Throwable th2222) {
                                FileLog.m13728e(th2222);
                            }
                        }
                        if (inputStream != null) {
                            try {
                                bArr = new byte[TLRPC.MESSAGE_FLAG_EDITED];
                                responseCode2 = 0;
                                while (!isCancelled()) {
                                    try {
                                        responseCode = inputStream.read(bArr);
                                        if (responseCode <= 0) {
                                            if (responseCode != -1) {
                                                z = false;
                                            } else {
                                                try {
                                                    if (this.fileSize != 0) {
                                                        reportProgress(1.0f);
                                                    }
                                                    z = true;
                                                } catch (Throwable th22222) {
                                                    r2 = th22222;
                                                    z = true;
                                                    Throwable th4;
                                                    try {
                                                        FileLog.m13728e(th4);
                                                        z2 = z;
                                                    } catch (Throwable th42) {
                                                        z2 = z;
                                                        th22222 = th42;
                                                        FileLog.m13728e(th22222);
                                                        if (this.fileOutputStream != null) {
                                                            this.fileOutputStream.close();
                                                            this.fileOutputStream = null;
                                                        }
                                                        if (inputStream != null) {
                                                            try {
                                                                inputStream.close();
                                                            } catch (Throwable th222222) {
                                                                FileLog.m13728e(th222222);
                                                            }
                                                        }
                                                        return Boolean.valueOf(z2);
                                                    }
                                                    if (this.fileOutputStream != null) {
                                                        this.fileOutputStream.close();
                                                        this.fileOutputStream = null;
                                                    }
                                                    if (inputStream != null) {
                                                        inputStream.close();
                                                    }
                                                    return Boolean.valueOf(z2);
                                                } catch (Throwable th5) {
                                                    th222222 = th5;
                                                    z2 = true;
                                                    FileLog.m13728e(th222222);
                                                    if (this.fileOutputStream != null) {
                                                        this.fileOutputStream.close();
                                                        this.fileOutputStream = null;
                                                    }
                                                    if (inputStream != null) {
                                                        inputStream.close();
                                                    }
                                                    return Boolean.valueOf(z2);
                                                }
                                            }
                                            z2 = z;
                                        } else {
                                            this.fileOutputStream.write(bArr, 0, responseCode);
                                            responseCode2 += responseCode;
                                            if (this.fileSize <= 0) {
                                                reportProgress(((float) responseCode2) / ((float) this.fileSize));
                                            }
                                        }
                                    } catch (Throwable th2222222) {
                                        th42 = th2222222;
                                        z = false;
                                    }
                                }
                                z = false;
                                z2 = z;
                            } catch (Throwable th6) {
                                th2222222 = th6;
                                FileLog.m13728e(th2222222);
                                if (this.fileOutputStream != null) {
                                    this.fileOutputStream.close();
                                    this.fileOutputStream = null;
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                return Boolean.valueOf(z2);
                            }
                        }
                        try {
                            if (this.fileOutputStream != null) {
                                this.fileOutputStream.close();
                                this.fileOutputStream = null;
                            }
                        } catch (Throwable th22222222) {
                            FileLog.m13728e(th22222222);
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                    return Boolean.valueOf(z2);
                }
                try {
                    this.fileOutputStream = new RandomAccessFile(this.tempFile, "rws");
                    inputStream = inputStream2;
                } catch (Throwable th7) {
                    th3 = th7;
                    if (th3 instanceof SocketTimeoutException) {
                        if (ConnectionsManager.isNetworkOnline()) {
                            this.canRetry = false;
                        }
                    } else if (!(th3 instanceof UnknownHostException)) {
                        this.canRetry = false;
                    } else if (!(th3 instanceof SocketException)) {
                        this.canRetry = false;
                    } else if (th3 instanceof FileNotFoundException) {
                        this.canRetry = false;
                    }
                    FileLog.m13728e(th3);
                    inputStream = inputStream2;
                    if (this.canRetry) {
                        if (openConnection != null) {
                            if (openConnection instanceof HttpURLConnection) {
                                responseCode2 = ((HttpURLConnection) openConnection).getResponseCode();
                                this.canRetry = false;
                            }
                        }
                        if (openConnection != null) {
                            headerFields = openConnection.getHeaderFields();
                            if (headerFields != null) {
                                list = (List) headerFields.get("content-Length");
                                headerField = (String) list.get(0);
                                if (headerField != null) {
                                    this.fileSize = Utilities.parseInt(headerField).intValue();
                                }
                            }
                        }
                        if (inputStream != null) {
                            bArr = new byte[TLRPC.MESSAGE_FLAG_EDITED];
                            responseCode2 = 0;
                            while (!isCancelled()) {
                                responseCode = inputStream.read(bArr);
                                if (responseCode <= 0) {
                                    if (responseCode != -1) {
                                        z = false;
                                    } else {
                                        if (this.fileSize != 0) {
                                            reportProgress(1.0f);
                                        }
                                        z = true;
                                    }
                                    z2 = z;
                                } else {
                                    this.fileOutputStream.write(bArr, 0, responseCode);
                                    responseCode2 += responseCode;
                                    if (this.fileSize <= 0) {
                                        reportProgress(((float) responseCode2) / ((float) this.fileSize));
                                    }
                                }
                            }
                            z = false;
                            z2 = z;
                        }
                        if (this.fileOutputStream != null) {
                            this.fileOutputStream.close();
                            this.fileOutputStream = null;
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                    return Boolean.valueOf(z2);
                }
            } catch (Throwable th222222222) {
                openConnection = null;
                th = th222222222;
                inputStream2 = null;
                th3 = th;
                if (th3 instanceof SocketTimeoutException) {
                    if (!(th3 instanceof UnknownHostException)) {
                        this.canRetry = false;
                    } else if (!(th3 instanceof SocketException)) {
                        this.canRetry = false;
                    } else if (th3 instanceof FileNotFoundException) {
                        this.canRetry = false;
                    }
                } else if (ConnectionsManager.isNetworkOnline()) {
                    this.canRetry = false;
                }
                FileLog.m13728e(th3);
                inputStream = inputStream2;
                if (this.canRetry) {
                    if (openConnection != null) {
                        if (openConnection instanceof HttpURLConnection) {
                            responseCode2 = ((HttpURLConnection) openConnection).getResponseCode();
                            this.canRetry = false;
                        }
                    }
                    if (openConnection != null) {
                        headerFields = openConnection.getHeaderFields();
                        if (headerFields != null) {
                            list = (List) headerFields.get("content-Length");
                            headerField = (String) list.get(0);
                            if (headerField != null) {
                                this.fileSize = Utilities.parseInt(headerField).intValue();
                            }
                        }
                    }
                    if (inputStream != null) {
                        bArr = new byte[TLRPC.MESSAGE_FLAG_EDITED];
                        responseCode2 = 0;
                        while (!isCancelled()) {
                            responseCode = inputStream.read(bArr);
                            if (responseCode <= 0) {
                                this.fileOutputStream.write(bArr, 0, responseCode);
                                responseCode2 += responseCode;
                                if (this.fileSize <= 0) {
                                    reportProgress(((float) responseCode2) / ((float) this.fileSize));
                                }
                            } else {
                                if (responseCode != -1) {
                                    if (this.fileSize != 0) {
                                        reportProgress(1.0f);
                                    }
                                    z = true;
                                } else {
                                    z = false;
                                }
                                z2 = z;
                            }
                        }
                        z = false;
                        z2 = z;
                    }
                    if (this.fileOutputStream != null) {
                        this.fileOutputStream.close();
                        this.fileOutputStream = null;
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
                return Boolean.valueOf(z2);
            }
            if (this.canRetry) {
                if (openConnection != null) {
                    if (openConnection instanceof HttpURLConnection) {
                        responseCode2 = ((HttpURLConnection) openConnection).getResponseCode();
                        if (!(responseCode2 == Callback.DEFAULT_DRAG_ANIMATION_DURATION || responseCode2 == 202 || responseCode2 == 304)) {
                            this.canRetry = false;
                        }
                    }
                }
                if (openConnection != null) {
                    headerFields = openConnection.getHeaderFields();
                    if (headerFields != null) {
                        list = (List) headerFields.get("content-Length");
                        if (!(list == null || list.isEmpty())) {
                            headerField = (String) list.get(0);
                            if (headerField != null) {
                                this.fileSize = Utilities.parseInt(headerField).intValue();
                            }
                        }
                    }
                }
                if (inputStream != null) {
                    bArr = new byte[TLRPC.MESSAGE_FLAG_EDITED];
                    responseCode2 = 0;
                    while (!isCancelled()) {
                        responseCode = inputStream.read(bArr);
                        if (responseCode <= 0) {
                            this.fileOutputStream.write(bArr, 0, responseCode);
                            responseCode2 += responseCode;
                            if (this.fileSize <= 0) {
                                reportProgress(((float) responseCode2) / ((float) this.fileSize));
                            }
                        } else {
                            if (responseCode != -1) {
                                if (this.fileSize != 0) {
                                    reportProgress(1.0f);
                                }
                                z = true;
                            } else {
                                z = false;
                            }
                            z2 = z;
                        }
                    }
                    z = false;
                    z2 = z;
                }
                if (this.fileOutputStream != null) {
                    this.fileOutputStream.close();
                    this.fileOutputStream = null;
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return Boolean.valueOf(z2);
        }

        protected void onCancelled() {
            ImageLoader.this.runHttpFileLoadTasks(this, 2);
        }

        protected void onPostExecute(Boolean bool) {
            ImageLoader.this.runHttpFileLoadTasks(this, bool.booleanValue() ? 2 : 1);
        }
    }

    private class HttpImageTask extends AsyncTask<Void, Void, Boolean> {
        private CacheImage cacheImage = null;
        private boolean canRetry = true;
        private RandomAccessFile fileOutputStream = null;
        private HttpURLConnection httpConnection = null;
        private int imageSize;
        private long lastProgressTime;

        /* renamed from: org.telegram.messenger.ImageLoader$HttpImageTask$3 */
        class C30923 implements Runnable {
            C30923() {
            }

            public void run() {
                ImageLoader.this.runHttpTasks(true);
            }
        }

        /* renamed from: org.telegram.messenger.ImageLoader$HttpImageTask$4 */
        class C30934 implements Runnable {
            C30934() {
            }

            public void run() {
                ImageLoader.this.runHttpTasks(true);
            }
        }

        /* renamed from: org.telegram.messenger.ImageLoader$HttpImageTask$5 */
        class C30955 implements Runnable {

            /* renamed from: org.telegram.messenger.ImageLoader$HttpImageTask$5$1 */
            class C30941 implements Runnable {
                C30941() {
                }

                public void run() {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.FileDidFailedLoad, HttpImageTask.this.cacheImage.url, Integer.valueOf(1));
                }
            }

            C30955() {
            }

            public void run() {
                ImageLoader.this.fileProgresses.remove(HttpImageTask.this.cacheImage.url);
                AndroidUtilities.runOnUIThread(new C30941());
            }
        }

        public HttpImageTask(CacheImage cacheImage, int i) {
            this.cacheImage = cacheImage;
            this.imageSize = i;
        }

        private void reportProgress(final float f) {
            long currentTimeMillis = System.currentTimeMillis();
            if (f == 1.0f || this.lastProgressTime == 0 || this.lastProgressTime < currentTimeMillis - 500) {
                this.lastProgressTime = currentTimeMillis;
                Utilities.stageQueue.postRunnable(new Runnable() {

                    /* renamed from: org.telegram.messenger.ImageLoader$HttpImageTask$1$1 */
                    class C30881 implements Runnable {
                        C30881() {
                        }

                        public void run() {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.FileLoadProgressChanged, HttpImageTask.this.cacheImage.url, Float.valueOf(f));
                        }
                    }

                    public void run() {
                        ImageLoader.this.fileProgresses.put(HttpImageTask.this.cacheImage.url, Float.valueOf(f));
                        AndroidUtilities.runOnUIThread(new C30881());
                    }
                });
            }
        }

        protected Boolean doInBackground(Void... voidArr) {
            InputStream inputStream;
            Throwable th;
            int responseCode;
            Throwable e;
            Map headerFields;
            List list;
            String str;
            byte[] bArr;
            int read;
            boolean z;
            Throwable th2;
            Throwable th3;
            InputStream inputStream2 = null;
            boolean z2 = false;
            if (!isCancelled()) {
                try {
                    this.httpConnection = (HttpURLConnection) new URL(this.cacheImage.httpUrl).openConnection();
                    this.httpConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 10_0 like Mac OS X) AppleWebKit/602.1.38 (KHTML, like Gecko) Version/10.0 Mobile/14A5297c Safari/602.1");
                    this.httpConnection.setConnectTimeout(DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS);
                    this.httpConnection.setReadTimeout(DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS);
                    this.httpConnection.setInstanceFollowRedirects(true);
                    if (isCancelled()) {
                        inputStream = null;
                    } else {
                        this.httpConnection.connect();
                        inputStream = this.httpConnection.getInputStream();
                        try {
                            this.fileOutputStream = new RandomAccessFile(this.cacheImage.tempFilePath, "rws");
                        } catch (Throwable th4) {
                            th = th4;
                            if (th instanceof SocketTimeoutException) {
                                if (ConnectionsManager.isNetworkOnline()) {
                                    this.canRetry = false;
                                }
                            } else if (th instanceof UnknownHostException) {
                                this.canRetry = false;
                            } else if (th instanceof SocketException) {
                                this.canRetry = false;
                            } else if (th instanceof FileNotFoundException) {
                                this.canRetry = false;
                            }
                            FileLog.m13728e(th);
                            inputStream2 = inputStream;
                            if (!isCancelled()) {
                                try {
                                    responseCode = this.httpConnection.getResponseCode();
                                    this.canRetry = false;
                                } catch (Throwable e2) {
                                    FileLog.m13728e(e2);
                                }
                                try {
                                    headerFields = this.httpConnection.getHeaderFields();
                                    if (headerFields != null) {
                                        list = (List) headerFields.get("content-Length");
                                        str = (String) list.get(0);
                                        if (str != null) {
                                            this.imageSize = Utilities.parseInt(str).intValue();
                                        }
                                    }
                                } catch (Throwable e22) {
                                    FileLog.m13728e(e22);
                                }
                                if (inputStream2 != null) {
                                    try {
                                        bArr = new byte[MessagesController.UPDATE_MASK_CHANNEL];
                                        responseCode = 0;
                                        while (!isCancelled()) {
                                            try {
                                                read = inputStream2.read(bArr);
                                                if (read > 0) {
                                                    responseCode += read;
                                                    this.fileOutputStream.write(bArr, 0, read);
                                                    if (this.imageSize == 0) {
                                                        reportProgress(((float) responseCode) / ((float) this.imageSize));
                                                    }
                                                } else {
                                                    if (read == -1) {
                                                        try {
                                                            if (this.imageSize != 0) {
                                                                reportProgress(1.0f);
                                                            }
                                                            z = true;
                                                        } catch (Throwable e222) {
                                                            th2 = e222;
                                                            z = true;
                                                            try {
                                                                FileLog.m13728e(th2);
                                                                z2 = z;
                                                            } catch (Throwable th22) {
                                                                th3 = th22;
                                                                z2 = z;
                                                                e222 = th3;
                                                                FileLog.m13728e(e222);
                                                                if (this.fileOutputStream != null) {
                                                                    this.fileOutputStream.close();
                                                                    this.fileOutputStream = null;
                                                                }
                                                                if (this.httpConnection != null) {
                                                                    this.httpConnection.disconnect();
                                                                }
                                                                if (inputStream2 != null) {
                                                                    try {
                                                                        inputStream2.close();
                                                                    } catch (Throwable e2222) {
                                                                        FileLog.m13728e(e2222);
                                                                    }
                                                                }
                                                                this.cacheImage.finalFilePath = this.cacheImage.tempFilePath;
                                                                return Boolean.valueOf(z2);
                                                            }
                                                            if (this.fileOutputStream != null) {
                                                                this.fileOutputStream.close();
                                                                this.fileOutputStream = null;
                                                            }
                                                            if (this.httpConnection != null) {
                                                                this.httpConnection.disconnect();
                                                            }
                                                            if (inputStream2 != null) {
                                                                inputStream2.close();
                                                            }
                                                            this.cacheImage.finalFilePath = this.cacheImage.tempFilePath;
                                                            return Boolean.valueOf(z2);
                                                        } catch (Throwable th5) {
                                                            e2222 = th5;
                                                            z2 = true;
                                                            FileLog.m13728e(e2222);
                                                            if (this.fileOutputStream != null) {
                                                                this.fileOutputStream.close();
                                                                this.fileOutputStream = null;
                                                            }
                                                            if (this.httpConnection != null) {
                                                                this.httpConnection.disconnect();
                                                            }
                                                            if (inputStream2 != null) {
                                                                inputStream2.close();
                                                            }
                                                            this.cacheImage.finalFilePath = this.cacheImage.tempFilePath;
                                                            return Boolean.valueOf(z2);
                                                        }
                                                    }
                                                    z = false;
                                                    z2 = z;
                                                }
                                            } catch (Throwable e22222) {
                                                th3 = e22222;
                                                z = false;
                                                th22 = th3;
                                            }
                                        }
                                        z = false;
                                        z2 = z;
                                    } catch (Throwable th6) {
                                        e22222 = th6;
                                        FileLog.m13728e(e22222);
                                        if (this.fileOutputStream != null) {
                                            this.fileOutputStream.close();
                                            this.fileOutputStream = null;
                                        }
                                        if (this.httpConnection != null) {
                                            this.httpConnection.disconnect();
                                        }
                                        if (inputStream2 != null) {
                                            inputStream2.close();
                                        }
                                        this.cacheImage.finalFilePath = this.cacheImage.tempFilePath;
                                        return Boolean.valueOf(z2);
                                    }
                                }
                            }
                            if (this.fileOutputStream != null) {
                                this.fileOutputStream.close();
                                this.fileOutputStream = null;
                            }
                            if (this.httpConnection != null) {
                                this.httpConnection.disconnect();
                            }
                            if (inputStream2 != null) {
                                inputStream2.close();
                            }
                            this.cacheImage.finalFilePath = this.cacheImage.tempFilePath;
                            return Boolean.valueOf(z2);
                        }
                    }
                    inputStream2 = inputStream;
                } catch (Throwable e222222) {
                    th3 = e222222;
                    inputStream = null;
                    th = th3;
                    if (th instanceof SocketTimeoutException) {
                        if (ConnectionsManager.isNetworkOnline()) {
                            this.canRetry = false;
                        }
                    } else if (th instanceof UnknownHostException) {
                        this.canRetry = false;
                    } else if (th instanceof SocketException) {
                        if (th.getMessage() != null && th.getMessage().contains("ECONNRESET")) {
                            this.canRetry = false;
                        }
                    } else if (th instanceof FileNotFoundException) {
                        this.canRetry = false;
                    }
                    FileLog.m13728e(th);
                    inputStream2 = inputStream;
                    if (isCancelled()) {
                        responseCode = this.httpConnection.getResponseCode();
                        this.canRetry = false;
                        headerFields = this.httpConnection.getHeaderFields();
                        if (headerFields != null) {
                            list = (List) headerFields.get("content-Length");
                            str = (String) list.get(0);
                            if (str != null) {
                                this.imageSize = Utilities.parseInt(str).intValue();
                            }
                        }
                        if (inputStream2 != null) {
                            bArr = new byte[MessagesController.UPDATE_MASK_CHANNEL];
                            responseCode = 0;
                            while (!isCancelled()) {
                                read = inputStream2.read(bArr);
                                if (read > 0) {
                                    if (read == -1) {
                                        z = false;
                                    } else {
                                        if (this.imageSize != 0) {
                                            reportProgress(1.0f);
                                        }
                                        z = true;
                                    }
                                    z2 = z;
                                } else {
                                    responseCode += read;
                                    this.fileOutputStream.write(bArr, 0, read);
                                    if (this.imageSize == 0) {
                                        reportProgress(((float) responseCode) / ((float) this.imageSize));
                                    }
                                }
                            }
                            z = false;
                            z2 = z;
                        }
                    }
                    if (this.fileOutputStream != null) {
                        this.fileOutputStream.close();
                        this.fileOutputStream = null;
                    }
                    if (this.httpConnection != null) {
                        this.httpConnection.disconnect();
                    }
                    if (inputStream2 != null) {
                        inputStream2.close();
                    }
                    this.cacheImage.finalFilePath = this.cacheImage.tempFilePath;
                    return Boolean.valueOf(z2);
                }
            }
            if (isCancelled()) {
                if (this.httpConnection != null && (this.httpConnection instanceof HttpURLConnection)) {
                    responseCode = this.httpConnection.getResponseCode();
                    if (!(responseCode == Callback.DEFAULT_DRAG_ANIMATION_DURATION || responseCode == 202 || responseCode == 304)) {
                        this.canRetry = false;
                    }
                }
                if (this.imageSize == 0 && this.httpConnection != null) {
                    headerFields = this.httpConnection.getHeaderFields();
                    if (headerFields != null) {
                        list = (List) headerFields.get("content-Length");
                        if (!(list == null || list.isEmpty())) {
                            str = (String) list.get(0);
                            if (str != null) {
                                this.imageSize = Utilities.parseInt(str).intValue();
                            }
                        }
                    }
                }
                if (inputStream2 != null) {
                    bArr = new byte[MessagesController.UPDATE_MASK_CHANNEL];
                    responseCode = 0;
                    while (!isCancelled()) {
                        read = inputStream2.read(bArr);
                        if (read > 0) {
                            responseCode += read;
                            this.fileOutputStream.write(bArr, 0, read);
                            if (this.imageSize == 0) {
                                reportProgress(((float) responseCode) / ((float) this.imageSize));
                            }
                        } else {
                            if (read == -1) {
                                if (this.imageSize != 0) {
                                    reportProgress(1.0f);
                                }
                                z = true;
                            } else {
                                z = false;
                            }
                            z2 = z;
                        }
                    }
                    z = false;
                    z2 = z;
                }
            }
            try {
                if (this.fileOutputStream != null) {
                    this.fileOutputStream.close();
                    this.fileOutputStream = null;
                }
            } catch (Throwable e2222222) {
                FileLog.m13728e(e2222222);
            }
            try {
                if (this.httpConnection != null) {
                    this.httpConnection.disconnect();
                }
            } catch (Throwable th7) {
            }
            if (inputStream2 != null) {
                inputStream2.close();
            }
            if (!(!z2 || this.cacheImage.tempFilePath == null || this.cacheImage.tempFilePath.renameTo(this.cacheImage.finalFilePath))) {
                this.cacheImage.finalFilePath = this.cacheImage.tempFilePath;
            }
            return Boolean.valueOf(z2);
        }

        protected void onCancelled() {
            ImageLoader.this.imageLoadQueue.postRunnable(new C30934());
            Utilities.stageQueue.postRunnable(new C30955());
        }

        protected void onPostExecute(final Boolean bool) {
            if (bool.booleanValue() || !this.canRetry) {
                ImageLoader.this.fileDidLoaded(this.cacheImage.url, this.cacheImage.finalFilePath, 0);
            } else {
                ImageLoader.this.httpFileLoadError(this.cacheImage.url);
            }
            Utilities.stageQueue.postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.ImageLoader$HttpImageTask$2$1 */
                class C30901 implements Runnable {
                    C30901() {
                    }

                    public void run() {
                        if (bool.booleanValue()) {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.FileDidLoaded, HttpImageTask.this.cacheImage.url);
                            return;
                        }
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.FileDidFailedLoad, HttpImageTask.this.cacheImage.url, Integer.valueOf(2));
                    }
                }

                public void run() {
                    ImageLoader.this.fileProgresses.remove(HttpImageTask.this.cacheImage.url);
                    AndroidUtilities.runOnUIThread(new C30901());
                }
            });
            ImageLoader.this.imageLoadQueue.postRunnable(new C30923());
        }
    }

    private class ThumbGenerateInfo {
        private int count;
        private FileLocation fileLocation;
        private String filter;

        private ThumbGenerateInfo() {
        }
    }

    private class ThumbGenerateTask implements Runnable {
        private String filter;
        private int mediaType;
        private File originalPath;
        private FileLocation thumbLocation;

        public ThumbGenerateTask(int i, File file, FileLocation fileLocation, String str) {
            this.mediaType = i;
            this.originalPath = file;
            this.thumbLocation = fileLocation;
            this.filter = str;
        }

        private void removeTask() {
            if (this.thumbLocation != null) {
                final String attachFileName = FileLoader.getAttachFileName(this.thumbLocation);
                ImageLoader.this.imageLoadQueue.postRunnable(new Runnable() {
                    public void run() {
                        ImageLoader.this.thumbGenerateTasks.remove(attachFileName);
                    }
                });
            }
        }

        public void run() {
            Bitmap bitmap = null;
            try {
                if (this.thumbLocation == null) {
                    removeTask();
                    return;
                }
                final String str = this.thumbLocation.volume_id + "_" + this.thumbLocation.local_id;
                File file = new File(FileLoader.getInstance().getDirectory(4), "q_" + str + ".jpg");
                if (file.exists() || !this.originalPath.exists()) {
                    removeTask();
                    return;
                }
                int min = Math.min(180, Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y) / 4);
                if (this.mediaType == 0) {
                    bitmap = ImageLoader.loadBitmap(this.originalPath.toString(), null, (float) min, (float) min, false);
                } else if (this.mediaType == 2) {
                    bitmap = ThumbnailUtils.createVideoThumbnail(this.originalPath.toString(), 1);
                } else if (this.mediaType == 3) {
                    String toLowerCase = this.originalPath.toString().toLowerCase();
                    if (toLowerCase.endsWith(".jpg") || toLowerCase.endsWith(".jpeg") || toLowerCase.endsWith(".png") || toLowerCase.endsWith(".gif")) {
                        bitmap = ImageLoader.loadBitmap(toLowerCase, null, (float) min, (float) min, false);
                    } else {
                        removeTask();
                        return;
                    }
                }
                if (bitmap == null) {
                    removeTask();
                    return;
                }
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                if (width == 0 || height == 0) {
                    removeTask();
                    return;
                }
                float min2 = Math.min(((float) width) / ((float) min), ((float) height) / ((float) min));
                Bitmap createScaledBitmap = Bitmaps.createScaledBitmap(bitmap, (int) (((float) width) / min2), (int) (((float) height) / min2), true);
                if (createScaledBitmap != bitmap) {
                    bitmap.recycle();
                }
                OutputStream fileOutputStream = new FileOutputStream(file);
                createScaledBitmap.compress(CompressFormat.JPEG, 60, fileOutputStream);
                fileOutputStream.close();
                final BitmapDrawable bitmapDrawable = new BitmapDrawable(createScaledBitmap);
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        ThumbGenerateTask.this.removeTask();
                        String str = str;
                        if (ThumbGenerateTask.this.filter != null) {
                            str = str + "@" + ThumbGenerateTask.this.filter;
                        }
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messageThumbGenerated, bitmapDrawable, str);
                        ImageLoader.this.memCache.put(str, bitmapDrawable);
                    }
                });
            } catch (Throwable e) {
                FileLog.m13728e(e);
            } catch (Throwable e2) {
                FileLog.m13728e(e2);
                removeTask();
            }
        }
    }

    public ImageLoader() {
        this.cacheOutQueue.setPriority(1);
        this.cacheThumbOutQueue.setPriority(1);
        this.thumbGeneratingQueue.setPriority(1);
        this.imageLoadQueue.setPriority(1);
        this.memCache = new LruCache((Math.min(15, ((ActivityManager) ApplicationLoader.applicationContext.getSystemService("activity")).getMemoryClass() / 7) * 1024) * 1024) {
            protected void entryRemoved(boolean z, String str, BitmapDrawable bitmapDrawable, BitmapDrawable bitmapDrawable2) {
                if (ImageLoader.this.ignoreRemoval == null || str == null || !ImageLoader.this.ignoreRemoval.equals(str)) {
                    Integer num = (Integer) ImageLoader.this.bitmapUseCounts.get(str);
                    if (num == null || num.intValue() == 0) {
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        if (!bitmap.isRecycled()) {
                            bitmap.recycle();
                        }
                    }
                }
            }

            protected int sizeOf(String str, BitmapDrawable bitmapDrawable) {
                return bitmapDrawable.getBitmap().getByteCount();
            }
        };
        FileLoader.getInstance().setDelegate(new C30732());
        BroadcastReceiver c30753 = new C30753();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MEDIA_BAD_REMOVAL");
        intentFilter.addAction("android.intent.action.MEDIA_CHECKING");
        intentFilter.addAction("android.intent.action.MEDIA_EJECT");
        intentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
        intentFilter.addAction("android.intent.action.MEDIA_NOFS");
        intentFilter.addAction("android.intent.action.MEDIA_REMOVED");
        intentFilter.addAction("android.intent.action.MEDIA_SHARED");
        intentFilter.addAction("android.intent.action.MEDIA_UNMOUNTABLE");
        intentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
        intentFilter.addDataScheme("file");
        ApplicationLoader.applicationContext.registerReceiver(c30753, intentFilter);
        HashMap hashMap = new HashMap();
        File cacheDir = AndroidUtilities.getCacheDir();
        if (!cacheDir.isDirectory()) {
            try {
                cacheDir.mkdirs();
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
        try {
            new File(cacheDir, ".nomedia").createNewFile();
        } catch (Throwable e2) {
            FileLog.m13728e(e2);
        }
        hashMap.put(Integer.valueOf(4), cacheDir);
        FileLoader.getInstance().setMediaDirs(hashMap);
        checkMediaPaths();
    }

    private boolean canMoveFiles(File file, File file2, int i) {
        File file3;
        File file4;
        Throwable e;
        RandomAccessFile randomAccessFile = null;
        if (i == 0) {
            try {
                file3 = new File(file, "000000000_999999_temp.jpg");
                file4 = file3;
                file3 = new File(file2, "000000000_999999.jpg");
            } catch (Exception e2) {
                e = e2;
                try {
                    FileLog.m13728e(e);
                    if (randomAccessFile != null) {
                        try {
                            randomAccessFile.close();
                        } catch (Throwable e3) {
                            FileLog.m13728e(e3);
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    e3 = th;
                    if (randomAccessFile != null) {
                        try {
                            randomAccessFile.close();
                        } catch (Throwable e4) {
                            FileLog.m13728e(e4);
                        }
                    }
                    throw e3;
                }
            }
        } else if (i == 3) {
            file3 = new File(file, "000000000_999999_temp.doc");
            file4 = file3;
            file3 = new File(file2, "000000000_999999.doc");
        } else if (i == 1) {
            file3 = new File(file, "000000000_999999_temp.ogg");
            file4 = file3;
            file3 = new File(file2, "000000000_999999.ogg");
        } else if (i == 2) {
            file4 = new File(file, "000000000_999999_temp.mp4");
            file3 = new File(file2, "000000000_999999.mp4");
        } else {
            file3 = null;
            file4 = null;
        }
        byte[] bArr = new byte[1024];
        file4.createNewFile();
        RandomAccessFile randomAccessFile2 = new RandomAccessFile(file4, "rws");
        try {
            randomAccessFile2.write(bArr);
            randomAccessFile2.close();
            randomAccessFile2 = null;
            boolean renameTo = file4.renameTo(file3);
            file4.delete();
            file3.delete();
            if (!renameTo) {
                if (null != null) {
                    try {
                        randomAccessFile2.close();
                    } catch (Throwable e32) {
                        FileLog.m13728e(e32);
                    }
                }
                return false;
            } else if (null == null) {
                return true;
            } else {
                try {
                    randomAccessFile2.close();
                    return true;
                } catch (Throwable e42) {
                    FileLog.m13728e(e42);
                    return true;
                }
            }
        } catch (Exception e5) {
            e32 = e5;
            randomAccessFile = randomAccessFile2;
            FileLog.m13728e(e32);
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
            return false;
        } catch (Throwable th2) {
            e32 = th2;
            randomAccessFile = randomAccessFile2;
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
            throw e32;
        }
    }

    private void createLoadOperationForImageReceiver(ImageReceiver imageReceiver, String str, String str2, String str3, TLObject tLObject, String str4, String str5, int i, int i2, int i3) {
        if (imageReceiver != null && str2 != null && str != null) {
            Integer tag = imageReceiver.getTag(i3 != 0);
            if (tag == null) {
                tag = Integer.valueOf(this.lastImageNum);
                imageReceiver.setTag(tag, i3 != 0);
                this.lastImageNum++;
                if (this.lastImageNum == Integer.MAX_VALUE) {
                    this.lastImageNum = 0;
                }
            }
            final boolean isNeedsQualityThumb = imageReceiver.isNeedsQualityThumb();
            final MessageObject parentMessageObject = imageReceiver.getParentMessageObject();
            final boolean isShouldGenerateQualityThumb = imageReceiver.isShouldGenerateQualityThumb();
            final int i4 = i3;
            final String str6 = str2;
            final String str7 = str;
            final ImageReceiver imageReceiver2 = imageReceiver;
            final String str8 = str5;
            final String str9 = str4;
            final TLObject tLObject2 = tLObject;
            final int i5 = i2;
            final int i6 = i;
            final String str10 = str3;
            this.imageLoadQueue.postRunnable(new Runnable() {
                public void run() {
                    int i;
                    int i2;
                    int i3 = 1;
                    if (i4 != 2) {
                        int i4;
                        CacheImage cacheImage = (CacheImage) ImageLoader.this.imageLoadingByUrl.get(str6);
                        CacheImage cacheImage2 = (CacheImage) ImageLoader.this.imageLoadingByKeys.get(str7);
                        CacheImage cacheImage3 = (CacheImage) ImageLoader.this.imageLoadingByTag.get(tag);
                        if (cacheImage3 != null) {
                            if (cacheImage3 == cacheImage2) {
                                i4 = 1;
                            } else if (cacheImage3 == cacheImage) {
                                if (cacheImage2 == null) {
                                    cacheImage3.replaceImageReceiver(imageReceiver2, str7, str8, i4 != 0);
                                }
                                i4 = 1;
                            } else {
                                cacheImage3.removeImageReceiver(imageReceiver2);
                            }
                            if (i4 == 0 || cacheImage2 == null) {
                                i = i4;
                            } else {
                                cacheImage2.addImageReceiver(imageReceiver2, str7, str8, i4 != 0);
                                i = 1;
                            }
                            if (i == 0 || cacheImage == null) {
                                i2 = i;
                            } else {
                                cacheImage.addImageReceiver(imageReceiver2, str7, str8, i4 != 0);
                                i2 = 1;
                            }
                        }
                        boolean z = false;
                        if (i4 == 0) {
                        }
                        i = i4;
                        if (i == 0) {
                        }
                        i2 = i;
                    } else {
                        boolean z2 = false;
                    }
                    if (i2 == 0) {
                        boolean z3;
                        File file;
                        int i5;
                        File file2;
                        boolean z4;
                        if (str9 != null) {
                            if (!str9.startsWith("http")) {
                                if (str9.startsWith("thumb://")) {
                                    i = str9.indexOf(":", 8);
                                    file2 = i >= 0 ? new File(str9.substring(i + 1)) : null;
                                    z3 = false;
                                    file = file2;
                                    i5 = 1;
                                } else if (str9.startsWith("vthumb://")) {
                                    i = str9.indexOf(":", 9);
                                    file2 = i >= 0 ? new File(str9.substring(i + 1)) : null;
                                    z3 = false;
                                    file = file2;
                                    i5 = 1;
                                } else {
                                    file = new File(str9);
                                    z3 = false;
                                    i5 = 1;
                                }
                            }
                            z3 = false;
                            file = null;
                            z4 = false;
                        } else {
                            if (i4 != 0) {
                                if (isNeedsQualityThumb) {
                                    file2 = new File(FileLoader.getInstance().getDirectory(4), "q_" + str6);
                                    if (file2.exists()) {
                                        z3 = true;
                                        file = file2;
                                    } else {
                                        z3 = false;
                                        file = null;
                                    }
                                } else {
                                    z3 = false;
                                    file = null;
                                }
                                if (parentMessageObject != null) {
                                    if (parentMessageObject.messageOwner.attachPath == null || parentMessageObject.messageOwner.attachPath.length() <= 0) {
                                        file2 = null;
                                    } else {
                                        file2 = new File(parentMessageObject.messageOwner.attachPath);
                                        if (!file2.exists()) {
                                            file2 = null;
                                        }
                                    }
                                    File pathToMessage = file2 == null ? FileLoader.getPathToMessage(parentMessageObject.messageOwner) : file2;
                                    if (isNeedsQualityThumb && r2 == null) {
                                        String fileName = parentMessageObject.getFileName();
                                        ThumbGenerateInfo thumbGenerateInfo = (ThumbGenerateInfo) ImageLoader.this.waitingForQualityThumb.get(fileName);
                                        if (thumbGenerateInfo == null) {
                                            ThumbGenerateInfo thumbGenerateInfo2 = new ThumbGenerateInfo();
                                            thumbGenerateInfo2.fileLocation = (TLRPC$TL_fileLocation) tLObject2;
                                            thumbGenerateInfo2.filter = str8;
                                            ImageLoader.this.waitingForQualityThumb.put(fileName, thumbGenerateInfo2);
                                            thumbGenerateInfo = thumbGenerateInfo2;
                                        }
                                        thumbGenerateInfo.count = thumbGenerateInfo.count + 1;
                                        ImageLoader.this.waitingForQualityThumbByTag.put(tag, fileName);
                                    }
                                    if (pathToMessage.exists() && isShouldGenerateQualityThumb) {
                                        ImageLoader.this.generateThumb(parentMessageObject.getFileType(), pathToMessage, (TLRPC$TL_fileLocation) tLObject2, str8);
                                    }
                                }
                                z4 = false;
                            }
                            z3 = false;
                            file = null;
                            z4 = false;
                        }
                        if (i4 != 2) {
                            boolean z5 = (tLObject2 instanceof TLRPC$TL_documentEncrypted) || (tLObject2 instanceof TLRPC$TL_fileEncryptedLocation);
                            CacheImage cacheImage4 = new CacheImage();
                            if (str9 != null && !str9.startsWith("vthumb") && !str9.startsWith("thumb")) {
                                String httpUrlExtension = ImageLoader.getHttpUrlExtension(str9, "jpg");
                                if (httpUrlExtension.equals("mp4") || httpUrlExtension.equals("gif")) {
                                    cacheImage4.animatedFile = true;
                                }
                            } else if (((tLObject2 instanceof TLRPC$TL_webDocument) && ((TLRPC$TL_webDocument) tLObject2).mime_type.equals("image/gif")) || ((tLObject2 instanceof Document) && (MessageObject.isGifDocument((Document) tLObject2) || MessageObject.isRoundVideoDocument((Document) tLObject2)))) {
                                cacheImage4.animatedFile = true;
                            }
                            if (file == null) {
                                if (i5 != 0 || i6 <= 0 || str9 != null || z5) {
                                    file = new File(FileLoader.getInstance().getDirectory(4), str6);
                                    if (file.exists()) {
                                        z3 = true;
                                    } else if (i5 == 2) {
                                        file = new File(FileLoader.getInstance().getDirectory(4), str6 + ".enc");
                                    }
                                } else {
                                    file = tLObject2 instanceof Document ? MessageObject.isVideoDocument((Document) tLObject2) ? new File(FileLoader.getInstance().getDirectory(2), str6) : new File(FileLoader.getInstance().getDirectory(3), str6) : tLObject2 instanceof TLRPC$TL_webDocument ? new File(FileLoader.getInstance().getDirectory(3), str6) : new File(FileLoader.getInstance().getDirectory(0), str6);
                                }
                            }
                            cacheImage4.selfThumb = i4 != 0;
                            cacheImage4.key = str7;
                            cacheImage4.filter = str8;
                            cacheImage4.httpUrl = str9;
                            cacheImage4.ext = str10;
                            if (i5 == 2) {
                                cacheImage4.encryptionKeyPath = new File(FileLoader.getInternalCacheDir(), str6 + ".enc.key");
                            }
                            cacheImage4.addImageReceiver(imageReceiver2, str7, str8, i4 != 0);
                            if (i5 != 0 || r1 || file.exists()) {
                                cacheImage4.finalFilePath = file;
                                cacheImage4.cacheTask = new CacheOutTask(cacheImage4);
                                ImageLoader.this.imageLoadingByKeys.put(str7, cacheImage4);
                                if (i4 != 0) {
                                    ImageLoader.this.cacheThumbOutQueue.postRunnable(cacheImage4.cacheTask);
                                    return;
                                } else {
                                    ImageLoader.this.cacheOutQueue.postRunnable(cacheImage4.cacheTask);
                                    return;
                                }
                            }
                            cacheImage4.url = str6;
                            cacheImage4.location = tLObject2;
                            ImageLoader.this.imageLoadingByUrl.put(str6, cacheImage4);
                            if (str9 == null) {
                                if (tLObject2 instanceof FileLocation) {
                                    FileLocation fileLocation = (FileLocation) tLObject2;
                                    i = i5;
                                    if (i != 0 || (i6 > 0 && fileLocation.key == null)) {
                                        i3 = i;
                                    }
                                    FileLoader.getInstance().loadFile(fileLocation, str10, i6, i3);
                                } else if (tLObject2 instanceof Document) {
                                    FileLoader.getInstance().loadFile((Document) tLObject2, true, i5);
                                } else if (tLObject2 instanceof TLRPC$TL_webDocument) {
                                    FileLoader.getInstance().loadFile((TLRPC$TL_webDocument) tLObject2, true, i5);
                                }
                                if (imageReceiver2.isForceLoding()) {
                                    ImageLoader.this.forceLoadingImages.put(cacheImage4.key, Integer.valueOf(0));
                                    return;
                                }
                                return;
                            }
                            cacheImage4.tempFilePath = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(str9) + "_temp.jpg");
                            cacheImage4.finalFilePath = file;
                            cacheImage4.httpTask = new HttpImageTask(cacheImage4, i6);
                            ImageLoader.this.httpTasks.add(cacheImage4.httpTask);
                            ImageLoader.this.runHttpTasks(false);
                        }
                    }
                }
            });
        }
    }

    private void fileDidFailedLoad(final String str, int i) {
        if (i != 1) {
            this.imageLoadQueue.postRunnable(new Runnable() {
                public void run() {
                    CacheImage cacheImage = (CacheImage) ImageLoader.this.imageLoadingByUrl.get(str);
                    if (cacheImage != null) {
                        cacheImage.setImageAndClear(null);
                    }
                }
            });
        }
    }

    private void fileDidLoaded(final String str, final File file, final int i) {
        this.imageLoadQueue.postRunnable(new Runnable() {
            public void run() {
                int i = 0;
                ThumbGenerateInfo thumbGenerateInfo = (ThumbGenerateInfo) ImageLoader.this.waitingForQualityThumb.get(str);
                if (thumbGenerateInfo != null) {
                    ImageLoader.this.generateThumb(i, file, thumbGenerateInfo.fileLocation, thumbGenerateInfo.filter);
                    ImageLoader.this.waitingForQualityThumb.remove(str);
                }
                CacheImage cacheImage = (CacheImage) ImageLoader.this.imageLoadingByUrl.get(str);
                if (cacheImage != null) {
                    ImageLoader.this.imageLoadingByUrl.remove(str);
                    ArrayList arrayList = new ArrayList();
                    for (int i2 = 0; i2 < cacheImage.imageReceiverArray.size(); i2++) {
                        String str = (String) cacheImage.keys.get(i2);
                        String str2 = (String) cacheImage.filters.get(i2);
                        Boolean bool = (Boolean) cacheImage.thumbs.get(i2);
                        ImageReceiver imageReceiver = (ImageReceiver) cacheImage.imageReceiverArray.get(i2);
                        CacheImage cacheImage2 = (CacheImage) ImageLoader.this.imageLoadingByKeys.get(str);
                        if (cacheImage2 == null) {
                            cacheImage2 = new CacheImage();
                            cacheImage2.finalFilePath = file;
                            cacheImage2.key = str;
                            cacheImage2.httpUrl = cacheImage.httpUrl;
                            cacheImage2.selfThumb = bool.booleanValue();
                            cacheImage2.ext = cacheImage.ext;
                            cacheImage2.encryptionKeyPath = cacheImage.encryptionKeyPath;
                            cacheImage2.cacheTask = new CacheOutTask(cacheImage2);
                            cacheImage2.filter = str2;
                            cacheImage2.animatedFile = cacheImage.animatedFile;
                            ImageLoader.this.imageLoadingByKeys.put(str, cacheImage2);
                            arrayList.add(cacheImage2.cacheTask);
                        }
                        cacheImage2.addImageReceiver(imageReceiver, str, str2, bool.booleanValue());
                    }
                    while (i < arrayList.size()) {
                        CacheOutTask cacheOutTask = (CacheOutTask) arrayList.get(i);
                        if (cacheOutTask.cacheImage.selfThumb) {
                            ImageLoader.this.cacheThumbOutQueue.postRunnable(cacheOutTask);
                        } else {
                            ImageLoader.this.cacheOutQueue.postRunnable(cacheOutTask);
                        }
                        i++;
                    }
                }
            }
        });
    }

    public static void fillPhotoSizeWithBytes(PhotoSize photoSize) {
        if (photoSize != null && photoSize.bytes == null) {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(FileLoader.getPathToAttach(photoSize, true), "r");
                if (((int) randomAccessFile.length()) < 20000) {
                    photoSize.bytes = new byte[((int) randomAccessFile.length())];
                    randomAccessFile.readFully(photoSize.bytes, 0, photoSize.bytes.length);
                }
            } catch (Throwable th) {
                FileLog.m13728e(th);
            }
        }
    }

    private void generateThumb(int i, File file, FileLocation fileLocation, String str) {
        if ((i == 0 || i == 2 || i == 3) && file != null && fileLocation != null) {
            if (((ThumbGenerateTask) this.thumbGenerateTasks.get(FileLoader.getAttachFileName(fileLocation))) == null) {
                this.thumbGeneratingQueue.postRunnable(new ThumbGenerateTask(i, file, fileLocation, str));
            }
        }
    }

    public static String getHttpUrlExtension(String str, String str2) {
        String lastPathSegment = Uri.parse(str).getLastPathSegment();
        if (!TextUtils.isEmpty(lastPathSegment) && lastPathSegment.length() > 1) {
            str = lastPathSegment;
        }
        int lastIndexOf = str.lastIndexOf(46);
        lastPathSegment = lastIndexOf != -1 ? str.substring(lastIndexOf + 1) : null;
        return (lastPathSegment == null || lastPathSegment.length() == 0 || lastPathSegment.length() > 4) ? str2 : lastPathSegment;
    }

    public static ImageLoader getInstance() {
        ImageLoader imageLoader = Instance;
        if (imageLoader == null) {
            synchronized (ImageLoader.class) {
                imageLoader = Instance;
                if (imageLoader == null) {
                    imageLoader = new ImageLoader();
                    Instance = imageLoader;
                }
            }
        }
        return imageLoader;
    }

    private void httpFileLoadError(final String str) {
        this.imageLoadQueue.postRunnable(new Runnable() {
            public void run() {
                CacheImage cacheImage = (CacheImage) ImageLoader.this.imageLoadingByUrl.get(str);
                if (cacheImage != null) {
                    HttpImageTask httpImageTask = cacheImage.httpTask;
                    cacheImage.httpTask = new HttpImageTask(httpImageTask.cacheImage, httpImageTask.imageSize);
                    ImageLoader.this.httpTasks.add(cacheImage.httpTask);
                    ImageLoader.this.runHttpTasks(false);
                }
            }
        });
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Bitmap loadBitmap(java.lang.String r11, android.net.Uri r12, float r13, float r14, boolean r15) {
        /*
        r2 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = 0;
        r3 = 1;
        r4 = 0;
        r9 = new android.graphics.BitmapFactory$Options;
        r9.<init>();
        r9.inJustDecodeBounds = r3;
        if (r11 != 0) goto L_0x0060;
    L_0x000e:
        if (r12 == 0) goto L_0x0060;
    L_0x0010:
        r1 = r12.getScheme();
        if (r1 == 0) goto L_0x0060;
    L_0x0016:
        r1 = r12.getScheme();
        r5 = "file";
        r1 = r1.contains(r5);
        if (r1 == 0) goto L_0x0056;
    L_0x0023:
        r11 = r12.getPath();
        r8 = r11;
    L_0x0028:
        if (r8 == 0) goto L_0x0062;
    L_0x002a:
        android.graphics.BitmapFactory.decodeFile(r8, r9);
        r7 = r0;
    L_0x002e:
        r1 = r9.outWidth;
        r1 = (float) r1;
        r5 = r9.outHeight;
        r5 = (float) r5;
        if (r15 == 0) goto L_0x0086;
    L_0x0036:
        r1 = r1 / r13;
        r5 = r5 / r14;
        r1 = java.lang.Math.max(r1, r5);
    L_0x003c:
        r5 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1));
        if (r5 >= 0) goto L_0x0041;
    L_0x0040:
        r1 = r2;
    L_0x0041:
        r9.inJustDecodeBounds = r4;
        r1 = (int) r1;
        r9.inSampleSize = r1;
        r1 = r9.inSampleSize;
        r1 = r1 % 2;
        if (r1 == 0) goto L_0x008f;
    L_0x004c:
        r1 = r3;
    L_0x004d:
        r2 = r1 * 2;
        r5 = r9.inSampleSize;
        if (r2 >= r5) goto L_0x008d;
    L_0x0053:
        r1 = r1 * 2;
        goto L_0x004d;
    L_0x0056:
        r11 = org.telegram.messenger.AndroidUtilities.getPath(r12);	 Catch:{ Throwable -> 0x005c }
        r8 = r11;
        goto L_0x0028;
    L_0x005c:
        r1 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r1);
    L_0x0060:
        r8 = r11;
        goto L_0x0028;
    L_0x0062:
        if (r12 == 0) goto L_0x0190;
    L_0x0064:
        r1 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Throwable -> 0x0081 }
        r1 = r1.getContentResolver();	 Catch:{ Throwable -> 0x0081 }
        r1 = r1.openInputStream(r12);	 Catch:{ Throwable -> 0x0081 }
        r5 = 0;
        android.graphics.BitmapFactory.decodeStream(r1, r5, r9);	 Catch:{ Throwable -> 0x0081 }
        r1.close();	 Catch:{ Throwable -> 0x0081 }
        r1 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Throwable -> 0x0081 }
        r1 = r1.getContentResolver();	 Catch:{ Throwable -> 0x0081 }
        r1 = r1.openInputStream(r12);	 Catch:{ Throwable -> 0x0081 }
        r7 = r1;
        goto L_0x002e;
    L_0x0081:
        r1 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r1);
    L_0x0085:
        return r0;
    L_0x0086:
        r1 = r1 / r13;
        r5 = r5 / r14;
        r1 = java.lang.Math.min(r1, r5);
        goto L_0x003c;
    L_0x008d:
        r9.inSampleSize = r1;
    L_0x008f:
        r1 = android.os.Build.VERSION.SDK_INT;
        r2 = 21;
        if (r1 >= r2) goto L_0x00d7;
    L_0x0095:
        r9.inPurgeable = r3;
        if (r8 == 0) goto L_0x00d9;
    L_0x0099:
        r1 = r8;
    L_0x009a:
        if (r1 == 0) goto L_0x018a;
    L_0x009c:
        r2 = new android.media.ExifInterface;	 Catch:{ Throwable -> 0x0186 }
        r2.<init>(r1);	 Catch:{ Throwable -> 0x0186 }
        r1 = "Orientation";
        r3 = 1;
        r2 = r2.getAttributeInt(r1, r3);	 Catch:{ Throwable -> 0x0186 }
        r1 = new android.graphics.Matrix;	 Catch:{ Throwable -> 0x0186 }
        r1.<init>();	 Catch:{ Throwable -> 0x0186 }
        switch(r2) {
            case 3: goto L_0x00e9;
            case 4: goto L_0x00b1;
            case 5: goto L_0x00b1;
            case 6: goto L_0x00e0;
            case 7: goto L_0x00b1;
            case 8: goto L_0x00ef;
            default: goto L_0x00b1;
        };
    L_0x00b1:
        r5 = r1;
    L_0x00b2:
        if (r8 == 0) goto L_0x0135;
    L_0x00b4:
        r0 = android.graphics.BitmapFactory.decodeFile(r8, r9);	 Catch:{ Throwable -> 0x00f5 }
        if (r0 == 0) goto L_0x0085;
    L_0x00ba:
        r1 = r9.inPurgeable;	 Catch:{ Throwable -> 0x0180 }
        if (r1 == 0) goto L_0x00c1;
    L_0x00be:
        org.telegram.messenger.Utilities.pinBitmap(r0);	 Catch:{ Throwable -> 0x0180 }
    L_0x00c1:
        r1 = 0;
        r2 = 0;
        r3 = r0.getWidth();	 Catch:{ Throwable -> 0x0180 }
        r4 = r0.getHeight();	 Catch:{ Throwable -> 0x0180 }
        r6 = 1;
        r1 = org.telegram.messenger.Bitmaps.createBitmap(r0, r1, r2, r3, r4, r5, r6);	 Catch:{ Throwable -> 0x0180 }
        if (r1 == r0) goto L_0x0085;
    L_0x00d2:
        r0.recycle();	 Catch:{ Throwable -> 0x0180 }
        r0 = r1;
        goto L_0x0085;
    L_0x00d7:
        r3 = r4;
        goto L_0x0095;
    L_0x00d9:
        if (r12 == 0) goto L_0x018d;
    L_0x00db:
        r1 = org.telegram.messenger.AndroidUtilities.getPath(r12);
        goto L_0x009a;
    L_0x00e0:
        r2 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
        r1.postRotate(r2);	 Catch:{ Throwable -> 0x00e6 }
        goto L_0x00b1;
    L_0x00e6:
        r2 = move-exception;
    L_0x00e7:
        r5 = r1;
        goto L_0x00b2;
    L_0x00e9:
        r2 = 1127481344; // 0x43340000 float:180.0 double:5.570497984E-315;
        r1.postRotate(r2);	 Catch:{ Throwable -> 0x00e6 }
        goto L_0x00b1;
    L_0x00ef:
        r2 = 1132920832; // 0x43870000 float:270.0 double:5.597372625E-315;
        r1.postRotate(r2);	 Catch:{ Throwable -> 0x00e6 }
        goto L_0x00b1;
    L_0x00f5:
        r1 = move-exception;
        r10 = r1;
        r1 = r0;
        r0 = r10;
    L_0x00f9:
        org.telegram.messenger.FileLog.m13728e(r0);
        r0 = getInstance();
        r0.clearMemory();
        if (r1 != 0) goto L_0x0112;
    L_0x0105:
        r1 = android.graphics.BitmapFactory.decodeFile(r8, r9);	 Catch:{ Throwable -> 0x012c }
        if (r1 == 0) goto L_0x0112;
    L_0x010b:
        r0 = r9.inPurgeable;	 Catch:{ Throwable -> 0x012c }
        if (r0 == 0) goto L_0x0112;
    L_0x010f:
        org.telegram.messenger.Utilities.pinBitmap(r1);	 Catch:{ Throwable -> 0x012c }
    L_0x0112:
        r0 = r1;
        if (r0 == 0) goto L_0x0085;
    L_0x0115:
        r1 = 0;
        r2 = 0;
        r3 = r0.getWidth();	 Catch:{ Throwable -> 0x017e }
        r4 = r0.getHeight();	 Catch:{ Throwable -> 0x017e }
        r6 = 1;
        r1 = org.telegram.messenger.Bitmaps.createBitmap(r0, r1, r2, r3, r4, r5, r6);	 Catch:{ Throwable -> 0x017e }
        if (r1 == r0) goto L_0x0085;
    L_0x0126:
        r0.recycle();	 Catch:{ Throwable -> 0x017e }
        r0 = r1;
        goto L_0x0085;
    L_0x012c:
        r0 = move-exception;
        r10 = r0;
        r0 = r1;
        r1 = r10;
    L_0x0130:
        org.telegram.messenger.FileLog.m13728e(r1);
        goto L_0x0085;
    L_0x0135:
        if (r12 == 0) goto L_0x0085;
    L_0x0137:
        r1 = 0;
        r0 = android.graphics.BitmapFactory.decodeStream(r7, r1, r9);	 Catch:{ Throwable -> 0x0165 }
        if (r0 == 0) goto L_0x015a;
    L_0x013e:
        r1 = r9.inPurgeable;	 Catch:{ Throwable -> 0x0165 }
        if (r1 == 0) goto L_0x0145;
    L_0x0142:
        org.telegram.messenger.Utilities.pinBitmap(r0);	 Catch:{ Throwable -> 0x0165 }
    L_0x0145:
        r1 = 0;
        r2 = 0;
        r3 = r0.getWidth();	 Catch:{ Throwable -> 0x0165 }
        r4 = r0.getHeight();	 Catch:{ Throwable -> 0x0165 }
        r6 = 1;
        r1 = org.telegram.messenger.Bitmaps.createBitmap(r0, r1, r2, r3, r4, r5, r6);	 Catch:{ Throwable -> 0x0165 }
        if (r1 == r0) goto L_0x015a;
    L_0x0156:
        r0.recycle();	 Catch:{ Throwable -> 0x0165 }
        r0 = r1;
    L_0x015a:
        r7.close();	 Catch:{ Throwable -> 0x015f }
        goto L_0x0085;
    L_0x015f:
        r1 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r1);
        goto L_0x0085;
    L_0x0165:
        r1 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r1);	 Catch:{ all -> 0x0174 }
        r7.close();	 Catch:{ Throwable -> 0x016e }
        goto L_0x0085;
    L_0x016e:
        r1 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r1);
        goto L_0x0085;
    L_0x0174:
        r0 = move-exception;
        r7.close();	 Catch:{ Throwable -> 0x0179 }
    L_0x0178:
        throw r0;
    L_0x0179:
        r1 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r1);
        goto L_0x0178;
    L_0x017e:
        r1 = move-exception;
        goto L_0x0130;
    L_0x0180:
        r1 = move-exception;
        r10 = r1;
        r1 = r0;
        r0 = r10;
        goto L_0x00f9;
    L_0x0186:
        r1 = move-exception;
        r1 = r0;
        goto L_0x00e7;
    L_0x018a:
        r5 = r0;
        goto L_0x00b2;
    L_0x018d:
        r1 = r0;
        goto L_0x009a;
    L_0x0190:
        r7 = r0;
        goto L_0x002e;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.ImageLoader.loadBitmap(java.lang.String, android.net.Uri, float, float, boolean):android.graphics.Bitmap");
    }

    private void performReplace(String str, String str2) {
        BitmapDrawable bitmapDrawable = this.memCache.get(str);
        if (bitmapDrawable != null) {
            BitmapDrawable bitmapDrawable2 = this.memCache.get(str2);
            Object obj = null;
            if (!(bitmapDrawable2 == null || bitmapDrawable2.getBitmap() == null || bitmapDrawable.getBitmap() == null)) {
                Bitmap bitmap = bitmapDrawable2.getBitmap();
                Bitmap bitmap2 = bitmapDrawable.getBitmap();
                if (bitmap.getWidth() > bitmap2.getWidth() || bitmap.getHeight() > bitmap2.getHeight()) {
                    obj = 1;
                }
            }
            if (obj == null) {
                this.ignoreRemoval = str;
                this.memCache.remove(str);
                this.memCache.put(str2, bitmapDrawable);
                this.ignoreRemoval = null;
            } else {
                this.memCache.remove(str);
            }
        }
        Integer num = (Integer) this.bitmapUseCounts.get(str);
        if (num != null) {
            this.bitmapUseCounts.put(str2, num);
            this.bitmapUseCounts.remove(str);
        }
    }

    private void removeFromWaitingForThumb(Integer num) {
        String str = (String) this.waitingForQualityThumbByTag.get(num);
        if (str != null) {
            ThumbGenerateInfo thumbGenerateInfo = (ThumbGenerateInfo) this.waitingForQualityThumb.get(str);
            if (thumbGenerateInfo != null) {
                thumbGenerateInfo.count = thumbGenerateInfo.count - 1;
                if (thumbGenerateInfo.count == 0) {
                    this.waitingForQualityThumb.remove(str);
                }
            }
            this.waitingForQualityThumbByTag.remove(num);
        }
    }

    private void replaceImageInCacheInternal(String str, String str2, FileLocation fileLocation) {
        ArrayList filterKeys = this.memCache.getFilterKeys(str);
        if (filterKeys != null) {
            for (int i = 0; i < filterKeys.size(); i++) {
                String str3 = (String) filterKeys.get(i);
                performReplace(str + "@" + str3, str2 + "@" + str3);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.didReplacedPhotoInMemCache, r4, str3, fileLocation);
            }
            return;
        }
        performReplace(str, str2);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.didReplacedPhotoInMemCache, str, str2, fileLocation);
    }

    private void runHttpFileLoadTasks(final HttpFileTask httpFileTask, final int i) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (httpFileTask != null) {
                    ImageLoader.this.currentHttpFileLoadTasksCount = ImageLoader.this.currentHttpFileLoadTasksCount - 1;
                }
                if (httpFileTask != null) {
                    if (i == 1) {
                        if (httpFileTask.canRetry) {
                            final HttpFileTask httpFileTask = new HttpFileTask(httpFileTask.url, httpFileTask.tempFile, httpFileTask.ext);
                            Runnable c30631 = new Runnable() {
                                public void run() {
                                    ImageLoader.this.httpFileLoadTasks.add(httpFileTask);
                                    ImageLoader.this.runHttpFileLoadTasks(null, 0);
                                }
                            };
                            ImageLoader.this.retryHttpsTasks.put(httpFileTask.url, c30631);
                            AndroidUtilities.runOnUIThread(c30631, 1000);
                        } else {
                            ImageLoader.this.httpFileLoadTasksByKeys.remove(httpFileTask.url);
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.httpFileDidFailedLoad, httpFileTask.url, Integer.valueOf(0));
                        }
                    } else if (i == 2) {
                        ImageLoader.this.httpFileLoadTasksByKeys.remove(httpFileTask.url);
                        File file = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(httpFileTask.url) + "." + httpFileTask.ext);
                        String file2 = httpFileTask.tempFile.renameTo(file) ? file.toString() : httpFileTask.tempFile.toString();
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.httpFileDidLoaded, httpFileTask.url, file2);
                    }
                }
                while (ImageLoader.this.currentHttpFileLoadTasksCount < 2 && !ImageLoader.this.httpFileLoadTasks.isEmpty()) {
                    ((HttpFileTask) ImageLoader.this.httpFileLoadTasks.poll()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
                    ImageLoader.this.currentHttpFileLoadTasksCount = ImageLoader.this.currentHttpFileLoadTasksCount + 1;
                }
            }
        });
    }

    private void runHttpTasks(boolean z) {
        if (z) {
            this.currentHttpTasksCount--;
        }
        while (this.currentHttpTasksCount < 4 && !this.httpTasks.isEmpty()) {
            ((HttpImageTask) this.httpTasks.poll()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{null, null, null});
            this.currentHttpTasksCount++;
        }
    }

    public static void saveMessageThumbs(Message message) {
        TLObject tLObject;
        int i = 0;
        Iterator it;
        TLObject tLObject2;
        if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
            it = message.media.photo.sizes.iterator();
            while (it.hasNext()) {
                tLObject2 = (PhotoSize) it.next();
                if (tLObject2 instanceof TLRPC$TL_photoCachedSize) {
                    break;
                }
            }
            tLObject2 = null;
            tLObject = tLObject2;
        } else {
            if (!(message.media instanceof TLRPC$TL_messageMediaDocument)) {
                if ((message.media instanceof TLRPC$TL_messageMediaWebPage) && message.media.webpage.photo != null) {
                    it = message.media.webpage.photo.sizes.iterator();
                    while (it.hasNext()) {
                        tLObject2 = (PhotoSize) it.next();
                        if (tLObject2 instanceof TLRPC$TL_photoCachedSize) {
                            tLObject = tLObject2;
                            break;
                        }
                    }
                }
            } else if (message.media.document.thumb instanceof TLRPC$TL_photoCachedSize) {
                tLObject = message.media.document.thumb;
            }
            tLObject = null;
        }
        if (tLObject != null && tLObject.bytes != null && tLObject.bytes.length != 0) {
            File file;
            boolean z;
            if (tLObject.location instanceof TLRPC$TL_fileLocationUnavailable) {
                tLObject.location = new TLRPC$TL_fileLocation();
                tLObject.location.volume_id = -2147483648L;
                tLObject.location.dc_id = Integer.MIN_VALUE;
                tLObject.location.local_id = UserConfig.lastLocalId;
                UserConfig.lastLocalId--;
            }
            File pathToAttach = FileLoader.getPathToAttach(tLObject, true);
            if (MessageObject.shouldEncryptPhotoOrVideo(message)) {
                file = new File(pathToAttach.getAbsolutePath() + ".enc");
                z = true;
            } else {
                z = false;
                file = pathToAttach;
            }
            if (!file.exists()) {
                if (z) {
                    try {
                        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(FileLoader.getInternalCacheDir(), file.getName() + ".key"), "rws");
                        long length = randomAccessFile.length();
                        byte[] bArr = new byte[32];
                        byte[] bArr2 = new byte[16];
                        if (length <= 0 || length % 48 != 0) {
                            Utilities.random.nextBytes(bArr);
                            Utilities.random.nextBytes(bArr2);
                            randomAccessFile.write(bArr);
                            randomAccessFile.write(bArr2);
                        } else {
                            randomAccessFile.read(bArr, 0, 32);
                            randomAccessFile.read(bArr2, 0, 16);
                        }
                        randomAccessFile.close();
                        Utilities.aesCtrDecryptionByteArray(tLObject.bytes, bArr, bArr2, 0, tLObject.bytes.length, 0);
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
                RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "rws");
                randomAccessFile2.write(tLObject.bytes);
                randomAccessFile2.close();
            }
            PhotoSize tLRPC$TL_photoSize = new TLRPC$TL_photoSize();
            tLRPC$TL_photoSize.w = tLObject.f10147w;
            tLRPC$TL_photoSize.h = tLObject.f10146h;
            tLRPC$TL_photoSize.location = tLObject.location;
            tLRPC$TL_photoSize.size = tLObject.size;
            tLRPC$TL_photoSize.type = tLObject.type;
            if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
                while (i < message.media.photo.sizes.size()) {
                    if (message.media.photo.sizes.get(i) instanceof TLRPC$TL_photoCachedSize) {
                        message.media.photo.sizes.set(i, tLRPC$TL_photoSize);
                        return;
                    }
                    i++;
                }
            } else if (message.media instanceof TLRPC$TL_messageMediaDocument) {
                message.media.document.thumb = tLRPC$TL_photoSize;
            } else if (message.media instanceof TLRPC$TL_messageMediaWebPage) {
                while (i < message.media.webpage.photo.sizes.size()) {
                    if (message.media.webpage.photo.sizes.get(i) instanceof TLRPC$TL_photoCachedSize) {
                        message.media.webpage.photo.sizes.set(i, tLRPC$TL_photoSize);
                        return;
                    }
                    i++;
                }
            }
        }
    }

    public static void saveMessagesThumbs(ArrayList<Message> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            for (int i = 0; i < arrayList.size(); i++) {
                saveMessageThumbs((Message) arrayList.get(i));
            }
        }
    }

    public static PhotoSize scaleAndSaveImage(Bitmap bitmap, float f, float f2, int i, boolean z) {
        return scaleAndSaveImage(bitmap, f, f2, i, z, 0, 0);
    }

    public static PhotoSize scaleAndSaveImage(Bitmap bitmap, float f, float f2, int i, boolean z, int i2, int i3) {
        if (bitmap == null) {
            return null;
        }
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        if (width == BitmapDescriptorFactory.HUE_RED || height == BitmapDescriptorFactory.HUE_RED) {
            return null;
        }
        boolean z2 = false;
        float max = Math.max(width / f, height / f2);
        if (!(i2 == 0 || i3 == 0 || (width >= ((float) i2) && height >= ((float) i3)))) {
            float max2 = (width >= ((float) i2) || height <= ((float) i3)) ? (width <= ((float) i2) || height >= ((float) i3)) ? Math.max(width / ((float) i2), height / ((float) i3)) : height / ((float) i3) : width / ((float) i2);
            z2 = true;
            max = max2;
        }
        int i4 = (int) (width / max);
        int i5 = (int) (height / max);
        if (i5 == 0 || i4 == 0) {
            return null;
        }
        try {
            return scaleAndSaveImageInternal(bitmap, i4, i5, width, height, max, i, z, z2);
        } catch (Throwable th) {
            FileLog.m13728e(th);
            return null;
        }
    }

    private static PhotoSize scaleAndSaveImageInternal(Bitmap bitmap, int i, int i2, float f, float f2, float f3, int i3, boolean z, boolean z2) {
        Bitmap createScaledBitmap = (f3 > 1.0f || z2) ? Bitmaps.createScaledBitmap(bitmap, i, i2, true) : bitmap;
        FileLocation tLRPC$TL_fileLocation = new TLRPC$TL_fileLocation();
        tLRPC$TL_fileLocation.volume_id = -2147483648L;
        tLRPC$TL_fileLocation.dc_id = Integer.MIN_VALUE;
        tLRPC$TL_fileLocation.local_id = UserConfig.lastLocalId;
        UserConfig.lastLocalId--;
        PhotoSize tLRPC$TL_photoSize = new TLRPC$TL_photoSize();
        tLRPC$TL_photoSize.location = tLRPC$TL_fileLocation;
        tLRPC$TL_photoSize.f10147w = createScaledBitmap.getWidth();
        tLRPC$TL_photoSize.f10146h = createScaledBitmap.getHeight();
        if (tLRPC$TL_photoSize.f10147w <= 100 && tLRPC$TL_photoSize.f10146h <= 100) {
            tLRPC$TL_photoSize.type = "s";
        } else if (tLRPC$TL_photoSize.f10147w <= 320 && tLRPC$TL_photoSize.f10146h <= 320) {
            tLRPC$TL_photoSize.type = "m";
        } else if (tLRPC$TL_photoSize.f10147w <= 800 && tLRPC$TL_photoSize.f10146h <= 800) {
            tLRPC$TL_photoSize.type = "x";
        } else if (tLRPC$TL_photoSize.f10147w > 1280 || tLRPC$TL_photoSize.f10146h > 1280) {
            tLRPC$TL_photoSize.type = "w";
        } else {
            tLRPC$TL_photoSize.type = "y";
        }
        OutputStream fileOutputStream = new FileOutputStream(new File(FileLoader.getInstance().getDirectory(4), tLRPC$TL_fileLocation.volume_id + "_" + tLRPC$TL_fileLocation.local_id + ".jpg"));
        createScaledBitmap.compress(CompressFormat.JPEG, i3, fileOutputStream);
        if (z) {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            createScaledBitmap.compress(CompressFormat.JPEG, i3, byteArrayOutputStream);
            tLRPC$TL_photoSize.bytes = byteArrayOutputStream.toByteArray();
            tLRPC$TL_photoSize.size = tLRPC$TL_photoSize.bytes.length;
            byteArrayOutputStream.close();
        } else {
            tLRPC$TL_photoSize.size = (int) fileOutputStream.getChannel().size();
        }
        fileOutputStream.close();
        if (createScaledBitmap != bitmap) {
            createScaledBitmap.recycle();
        }
        return tLRPC$TL_photoSize;
    }

    public void cancelForceLoadingForImageReceiver(ImageReceiver imageReceiver) {
        if (imageReceiver != null) {
            final String key = imageReceiver.getKey();
            if (key != null) {
                this.imageLoadQueue.postRunnable(new Runnable() {
                    public void run() {
                        ImageLoader.this.forceLoadingImages.remove(key);
                    }
                });
            }
        }
    }

    public void cancelLoadHttpFile(String str) {
        HttpFileTask httpFileTask = (HttpFileTask) this.httpFileLoadTasksByKeys.get(str);
        if (httpFileTask != null) {
            httpFileTask.cancel(true);
            this.httpFileLoadTasksByKeys.remove(str);
            this.httpFileLoadTasks.remove(httpFileTask);
        }
        Runnable runnable = (Runnable) this.retryHttpsTasks.get(str);
        if (runnable != null) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
        }
        runHttpFileLoadTasks(null, 0);
    }

    public void cancelLoadingForImageReceiver(final ImageReceiver imageReceiver, final int i) {
        if (imageReceiver != null) {
            this.imageLoadQueue.postRunnable(new Runnable() {
                public void run() {
                    int i;
                    int i2;
                    if (i == 1) {
                        i = 1;
                        i2 = 0;
                    } else if (i == 2) {
                        i = 2;
                        i2 = 1;
                    } else {
                        i = 2;
                        i2 = 0;
                    }
                    int i3 = i2;
                    while (i3 < i) {
                        Integer tag = imageReceiver.getTag(i3 == 0);
                        if (i3 == 0) {
                            ImageLoader.this.removeFromWaitingForThumb(tag);
                        }
                        if (tag != null) {
                            CacheImage cacheImage = (CacheImage) ImageLoader.this.imageLoadingByTag.get(tag);
                            if (cacheImage != null) {
                                cacheImage.removeImageReceiver(imageReceiver);
                            }
                        }
                        i3++;
                    }
                }
            });
        }
    }

    public void checkMediaPaths() {
        this.cacheOutQueue.postRunnable(new C30774());
    }

    public void clearMemory() {
        this.memCache.evictAll();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.HashMap<java.lang.Integer, java.io.File> createMediaPaths() {
        /*
        r5 = this;
        r1 = new java.util.HashMap;
        r1.<init>();
        r2 = org.telegram.messenger.AndroidUtilities.getCacheDir();
        r0 = r2.isDirectory();
        if (r0 != 0) goto L_0x0012;
    L_0x000f:
        r2.mkdirs();	 Catch:{ Exception -> 0x0166 }
    L_0x0012:
        r0 = new java.io.File;	 Catch:{ Exception -> 0x016c }
        r3 = ".nomedia";
        r0.<init>(r2, r3);	 Catch:{ Exception -> 0x016c }
        r0.createNewFile();	 Catch:{ Exception -> 0x016c }
    L_0x001d:
        r0 = 4;
        r0 = java.lang.Integer.valueOf(r0);
        r1.put(r0, r2);
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r3 = "cache path = ";
        r0 = r0.append(r3);
        r0 = r0.append(r2);
        r0 = r0.toString();
        org.telegram.messenger.FileLog.m13726e(r0);
        r0 = "mounted";
        r3 = android.os.Environment.getExternalStorageState();	 Catch:{ Exception -> 0x0178 }
        r0 = r0.equals(r3);	 Catch:{ Exception -> 0x0178 }
        if (r0 == 0) goto L_0x018d;
    L_0x0049:
        r0 = new java.io.File;	 Catch:{ Exception -> 0x0178 }
        r3 = android.os.Environment.getExternalStorageDirectory();	 Catch:{ Exception -> 0x0178 }
        r4 = "Telegram";
        r0.<init>(r3, r4);	 Catch:{ Exception -> 0x0178 }
        r5.telegramPath = r0;	 Catch:{ Exception -> 0x0178 }
        r0 = r5.telegramPath;	 Catch:{ Exception -> 0x0178 }
        r0.mkdirs();	 Catch:{ Exception -> 0x0178 }
        r0 = r5.telegramPath;	 Catch:{ Exception -> 0x0178 }
        r0 = r0.isDirectory();	 Catch:{ Exception -> 0x0178 }
        if (r0 == 0) goto L_0x015e;
    L_0x0064:
        r0 = new java.io.File;	 Catch:{ Exception -> 0x0172 }
        r3 = r5.telegramPath;	 Catch:{ Exception -> 0x0172 }
        r4 = "Telegram Images";
        r0.<init>(r3, r4);	 Catch:{ Exception -> 0x0172 }
        r0.mkdir();	 Catch:{ Exception -> 0x0172 }
        r3 = r0.isDirectory();	 Catch:{ Exception -> 0x0172 }
        if (r3 == 0) goto L_0x009d;
    L_0x0077:
        r3 = 0;
        r3 = r5.canMoveFiles(r2, r0, r3);	 Catch:{ Exception -> 0x0172 }
        if (r3 == 0) goto L_0x009d;
    L_0x007e:
        r3 = 0;
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ Exception -> 0x0172 }
        r1.put(r3, r0);	 Catch:{ Exception -> 0x0172 }
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0172 }
        r3.<init>();	 Catch:{ Exception -> 0x0172 }
        r4 = "image path = ";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x0172 }
        r0 = r3.append(r0);	 Catch:{ Exception -> 0x0172 }
        r0 = r0.toString();	 Catch:{ Exception -> 0x0172 }
        org.telegram.messenger.FileLog.m13726e(r0);	 Catch:{ Exception -> 0x0172 }
    L_0x009d:
        r0 = new java.io.File;	 Catch:{ Exception -> 0x017d }
        r3 = r5.telegramPath;	 Catch:{ Exception -> 0x017d }
        r4 = "Telegram Video";
        r0.<init>(r3, r4);	 Catch:{ Exception -> 0x017d }
        r0.mkdir();	 Catch:{ Exception -> 0x017d }
        r3 = r0.isDirectory();	 Catch:{ Exception -> 0x017d }
        if (r3 == 0) goto L_0x00d6;
    L_0x00b0:
        r3 = 2;
        r3 = r5.canMoveFiles(r2, r0, r3);	 Catch:{ Exception -> 0x017d }
        if (r3 == 0) goto L_0x00d6;
    L_0x00b7:
        r3 = 2;
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ Exception -> 0x017d }
        r1.put(r3, r0);	 Catch:{ Exception -> 0x017d }
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x017d }
        r3.<init>();	 Catch:{ Exception -> 0x017d }
        r4 = "video path = ";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x017d }
        r0 = r3.append(r0);	 Catch:{ Exception -> 0x017d }
        r0 = r0.toString();	 Catch:{ Exception -> 0x017d }
        org.telegram.messenger.FileLog.m13726e(r0);	 Catch:{ Exception -> 0x017d }
    L_0x00d6:
        r0 = new java.io.File;	 Catch:{ Exception -> 0x0183 }
        r3 = r5.telegramPath;	 Catch:{ Exception -> 0x0183 }
        r4 = "Telegram Audio";
        r0.<init>(r3, r4);	 Catch:{ Exception -> 0x0183 }
        r0.mkdir();	 Catch:{ Exception -> 0x0183 }
        r3 = r0.isDirectory();	 Catch:{ Exception -> 0x0183 }
        if (r3 == 0) goto L_0x011a;
    L_0x00e9:
        r3 = 1;
        r3 = r5.canMoveFiles(r2, r0, r3);	 Catch:{ Exception -> 0x0183 }
        if (r3 == 0) goto L_0x011a;
    L_0x00f0:
        r3 = new java.io.File;	 Catch:{ Exception -> 0x0183 }
        r4 = ".nomedia";
        r3.<init>(r0, r4);	 Catch:{ Exception -> 0x0183 }
        r3.createNewFile();	 Catch:{ Exception -> 0x0183 }
        r3 = 1;
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ Exception -> 0x0183 }
        r1.put(r3, r0);	 Catch:{ Exception -> 0x0183 }
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0183 }
        r3.<init>();	 Catch:{ Exception -> 0x0183 }
        r4 = "audio path = ";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x0183 }
        r0 = r3.append(r0);	 Catch:{ Exception -> 0x0183 }
        r0 = r0.toString();	 Catch:{ Exception -> 0x0183 }
        org.telegram.messenger.FileLog.m13726e(r0);	 Catch:{ Exception -> 0x0183 }
    L_0x011a:
        r0 = new java.io.File;	 Catch:{ Exception -> 0x0188 }
        r3 = r5.telegramPath;	 Catch:{ Exception -> 0x0188 }
        r4 = "Telegram Documents";
        r0.<init>(r3, r4);	 Catch:{ Exception -> 0x0188 }
        r0.mkdir();	 Catch:{ Exception -> 0x0188 }
        r3 = r0.isDirectory();	 Catch:{ Exception -> 0x0188 }
        if (r3 == 0) goto L_0x015e;
    L_0x012d:
        r3 = 3;
        r2 = r5.canMoveFiles(r2, r0, r3);	 Catch:{ Exception -> 0x0188 }
        if (r2 == 0) goto L_0x015e;
    L_0x0134:
        r2 = new java.io.File;	 Catch:{ Exception -> 0x0188 }
        r3 = ".nomedia";
        r2.<init>(r0, r3);	 Catch:{ Exception -> 0x0188 }
        r2.createNewFile();	 Catch:{ Exception -> 0x0188 }
        r2 = 3;
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ Exception -> 0x0188 }
        r1.put(r2, r0);	 Catch:{ Exception -> 0x0188 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0188 }
        r2.<init>();	 Catch:{ Exception -> 0x0188 }
        r3 = "documents path = ";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0188 }
        r0 = r2.append(r0);	 Catch:{ Exception -> 0x0188 }
        r0 = r0.toString();	 Catch:{ Exception -> 0x0188 }
        org.telegram.messenger.FileLog.m13726e(r0);	 Catch:{ Exception -> 0x0188 }
    L_0x015e:
        r0 = org.telegram.messenger.MediaController.getInstance();	 Catch:{ Exception -> 0x0178 }
        r0.checkSaveToGalleryFiles();	 Catch:{ Exception -> 0x0178 }
    L_0x0165:
        return r1;
    L_0x0166:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);
        goto L_0x0012;
    L_0x016c:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);
        goto L_0x001d;
    L_0x0172:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Exception -> 0x0178 }
        goto L_0x009d;
    L_0x0178:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);
        goto L_0x0165;
    L_0x017d:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Exception -> 0x0178 }
        goto L_0x00d6;
    L_0x0183:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Exception -> 0x0178 }
        goto L_0x011a;
    L_0x0188:
        r0 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Exception -> 0x0178 }
        goto L_0x015e;
    L_0x018d:
        r0 = "this Android can't rename files";
        org.telegram.messenger.FileLog.m13726e(r0);	 Catch:{ Exception -> 0x0178 }
        goto L_0x015e;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.ImageLoader.createMediaPaths():java.util.HashMap<java.lang.Integer, java.io.File>");
    }

    public boolean decrementUseCount(String str) {
        Integer num = (Integer) this.bitmapUseCounts.get(str);
        if (num == null) {
            return true;
        }
        if (num.intValue() == 1) {
            this.bitmapUseCounts.remove(str);
            return true;
        }
        this.bitmapUseCounts.put(str, Integer.valueOf(num.intValue() - 1));
        return false;
    }

    public Float getFileProgress(String str) {
        return str == null ? null : (Float) this.fileProgresses.get(str);
    }

    public BitmapDrawable getImageFromMemory(String str) {
        return this.memCache.get(str);
    }

    public BitmapDrawable getImageFromMemory(TLObject tLObject, String str, String str2) {
        String str3 = null;
        if (tLObject == null && str == null) {
            return null;
        }
        if (str != null) {
            str3 = Utilities.MD5(str);
        } else if (tLObject instanceof FileLocation) {
            FileLocation fileLocation = (FileLocation) tLObject;
            str3 = fileLocation.volume_id + "_" + fileLocation.local_id;
        } else if (tLObject instanceof Document) {
            Document document = (Document) tLObject;
            str3 = document.version == 0 ? document.dc_id + "_" + document.id : document.dc_id + "_" + document.id + "_" + document.version;
        } else if (tLObject instanceof TLRPC$TL_webDocument) {
            str3 = Utilities.MD5(((TLRPC$TL_webDocument) tLObject).url);
        }
        if (str2 != null) {
            str3 = str3 + "@" + str2;
        }
        return this.memCache.get(str3);
    }

    public void incrementUseCount(String str) {
        Integer num = (Integer) this.bitmapUseCounts.get(str);
        if (num == null) {
            this.bitmapUseCounts.put(str, Integer.valueOf(1));
        } else {
            this.bitmapUseCounts.put(str, Integer.valueOf(num.intValue() + 1));
        }
    }

    public boolean isInCache(String str) {
        return this.memCache.get(str) != null;
    }

    public boolean isLoadingHttpFile(String str) {
        return this.httpFileLoadTasksByKeys.containsKey(str);
    }

    public void loadHttpFile(String str, String str2) {
        if (str != null && str.length() != 0 && !this.httpFileLoadTasksByKeys.containsKey(str)) {
            String httpUrlExtension = getHttpUrlExtension(str, str2);
            File file = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(str) + "_temp." + httpUrlExtension);
            file.delete();
            HttpFileTask httpFileTask = new HttpFileTask(str, file, httpUrlExtension);
            this.httpFileLoadTasks.add(httpFileTask);
            this.httpFileLoadTasksByKeys.put(str, httpFileTask);
            runHttpFileLoadTasks(null, 0);
        }
    }

    public void loadImageForImageReceiver(ImageReceiver imageReceiver) {
        if (imageReceiver != null) {
            Object obj;
            TLObject thumbLocation;
            TLObject imageLocation;
            String httpImageLocation;
            Object obj2;
            String str;
            String str2;
            String str3;
            String ext;
            String str4;
            TLObject tLObject;
            Object obj3;
            FileLocation fileLocation;
            String str5;
            TLRPC$TL_webDocument tLRPC$TL_webDocument;
            Document document;
            String filter;
            String thumbFilter;
            String str6;
            int cacheType;
            Object obj4 = null;
            String key = imageReceiver.getKey();
            if (key != null) {
                BitmapDrawable bitmapDrawable = this.memCache.get(key);
                if (bitmapDrawable != null) {
                    cancelLoadingForImageReceiver(imageReceiver, 0);
                    imageReceiver.setImageBitmapByKey(bitmapDrawable, key, false, true);
                    obj4 = 1;
                    if (!imageReceiver.isForcePreview()) {
                        return;
                    }
                }
            }
            String thumbKey = imageReceiver.getThumbKey();
            if (thumbKey != null) {
                BitmapDrawable bitmapDrawable2 = this.memCache.get(thumbKey);
                if (bitmapDrawable2 != null) {
                    imageReceiver.setImageBitmapByKey(bitmapDrawable2, thumbKey, true, true);
                    cancelLoadingForImageReceiver(imageReceiver, 1);
                    if (obj4 == null || !imageReceiver.isForcePreview()) {
                        obj = 1;
                        thumbLocation = imageReceiver.getThumbLocation();
                        imageLocation = imageReceiver.getImageLocation();
                        httpImageLocation = imageReceiver.getHttpImageLocation();
                        obj2 = null;
                        str = null;
                        str2 = null;
                        str3 = null;
                        ext = imageReceiver.getExt();
                        if (ext == null) {
                            ext = "jpg";
                        }
                        if (httpImageLocation != null) {
                            str3 = Utilities.MD5(httpImageLocation);
                            str4 = str3 + "." + getHttpUrlExtension(httpImageLocation, "jpg");
                            tLObject = imageLocation;
                            key = str3;
                            str3 = null;
                            obj3 = null;
                        } else if (imageLocation == null) {
                            if (imageLocation instanceof FileLocation) {
                                fileLocation = (FileLocation) imageLocation;
                                str5 = fileLocation.volume_id + "_" + fileLocation.local_id;
                                str3 = str5 + "." + ext;
                                obj4 = (imageReceiver.getExt() == null || fileLocation.key != null || (fileLocation.volume_id == -2147483648L && fileLocation.local_id < 0)) ? 1 : null;
                                obj2 = obj4;
                                str = str3;
                                str3 = str5;
                            } else if (imageLocation instanceof TLRPC$TL_webDocument) {
                                tLRPC$TL_webDocument = (TLRPC$TL_webDocument) imageLocation;
                                str5 = FileLoader.getExtensionByMime(tLRPC$TL_webDocument.mime_type);
                                str3 = Utilities.MD5(tLRPC$TL_webDocument.url);
                                str = str3 + "." + getHttpUrlExtension(tLRPC$TL_webDocument.url, str5);
                            } else if (imageLocation instanceof Document) {
                                document = (Document) imageLocation;
                                if (document.id != 0 && document.dc_id != 0) {
                                    thumbKey = document.version == 0 ? document.dc_id + "_" + document.id : document.dc_id + "_" + document.id + "_" + document.version;
                                    str3 = FileLoader.getDocumentFileName(document);
                                    if (str3 != null) {
                                        int lastIndexOf = str3.lastIndexOf(46);
                                        if (lastIndexOf != -1) {
                                            str3 = str3.substring(lastIndexOf);
                                            if (str3.length() <= 1) {
                                                str3 = (document.mime_type == null && document.mime_type.equals(MimeTypes.VIDEO_MP4)) ? ".mp4" : TtmlNode.ANONYMOUS_REGION_ID;
                                            }
                                            str5 = thumbKey + str3;
                                            str3 = null == null ? null + "." + ext : null;
                                            obj4 = (MessageObject.isGifDocument(document) || MessageObject.isRoundVideoDocument((Document) imageLocation)) ? null : 1;
                                            str2 = str3;
                                            str3 = thumbKey;
                                            obj2 = obj4;
                                            str = str5;
                                        }
                                    }
                                    str3 = TtmlNode.ANONYMOUS_REGION_ID;
                                    if (str3.length() <= 1) {
                                        if (document.mime_type == null) {
                                        }
                                    }
                                    str5 = thumbKey + str3;
                                    if (null == null) {
                                    }
                                    if (!MessageObject.isGifDocument(document)) {
                                    }
                                    str2 = str3;
                                    str3 = thumbKey;
                                    obj2 = obj4;
                                    str = str5;
                                } else {
                                    return;
                                }
                            }
                            if (imageLocation != thumbLocation) {
                                str4 = null;
                                tLObject = null;
                                key = null;
                                str3 = str2;
                                obj3 = obj2;
                            } else {
                                str4 = str;
                                tLObject = imageLocation;
                                key = str3;
                                str3 = str2;
                                obj3 = obj2;
                            }
                        } else {
                            str4 = null;
                            tLObject = imageLocation;
                            key = null;
                            str3 = null;
                            obj3 = null;
                        }
                        if (thumbLocation == null) {
                            str = thumbLocation.volume_id + "_" + thumbLocation.local_id;
                            str3 = str + "." + ext;
                        } else {
                            str = null;
                        }
                        filter = imageReceiver.getFilter();
                        thumbFilter = imageReceiver.getThumbFilter();
                        str6 = (key != null || filter == null) ? key : key + "@" + filter;
                        thumbKey = (str != null || thumbFilter == null) ? str : str + "@" + thumbFilter;
                        if (httpImageLocation == null) {
                            createLoadOperationForImageReceiver(imageReceiver, thumbKey, str3, ext, thumbLocation, null, thumbFilter, 0, 1, obj == null ? 2 : 1);
                            createLoadOperationForImageReceiver(imageReceiver, str6, str4, ext, null, httpImageLocation, filter, 0, 1, 0);
                        }
                        cacheType = imageReceiver.getCacheType();
                        if (cacheType == 0 && r8 != null) {
                            cacheType = 1;
                        }
                        createLoadOperationForImageReceiver(imageReceiver, thumbKey, str3, ext, thumbLocation, null, thumbFilter, 0, cacheType != 0 ? 1 : cacheType, obj == null ? 2 : 1);
                        createLoadOperationForImageReceiver(imageReceiver, str6, str4, ext, tLObject, null, filter, imageReceiver.getSize(), cacheType, 0);
                        return;
                    }
                    return;
                }
            }
            obj = null;
            thumbLocation = imageReceiver.getThumbLocation();
            imageLocation = imageReceiver.getImageLocation();
            httpImageLocation = imageReceiver.getHttpImageLocation();
            obj2 = null;
            str = null;
            str2 = null;
            str3 = null;
            ext = imageReceiver.getExt();
            if (ext == null) {
                ext = "jpg";
            }
            if (httpImageLocation != null) {
                str3 = Utilities.MD5(httpImageLocation);
                str4 = str3 + "." + getHttpUrlExtension(httpImageLocation, "jpg");
                tLObject = imageLocation;
                key = str3;
                str3 = null;
                obj3 = null;
            } else if (imageLocation == null) {
                str4 = null;
                tLObject = imageLocation;
                key = null;
                str3 = null;
                obj3 = null;
            } else {
                if (imageLocation instanceof FileLocation) {
                    fileLocation = (FileLocation) imageLocation;
                    str5 = fileLocation.volume_id + "_" + fileLocation.local_id;
                    str3 = str5 + "." + ext;
                    if (imageReceiver.getExt() == null) {
                    }
                    obj2 = obj4;
                    str = str3;
                    str3 = str5;
                } else if (imageLocation instanceof TLRPC$TL_webDocument) {
                    tLRPC$TL_webDocument = (TLRPC$TL_webDocument) imageLocation;
                    str5 = FileLoader.getExtensionByMime(tLRPC$TL_webDocument.mime_type);
                    str3 = Utilities.MD5(tLRPC$TL_webDocument.url);
                    str = str3 + "." + getHttpUrlExtension(tLRPC$TL_webDocument.url, str5);
                } else if (imageLocation instanceof Document) {
                    document = (Document) imageLocation;
                    if (document.id != 0) {
                        return;
                    }
                    return;
                }
                if (imageLocation != thumbLocation) {
                    str4 = str;
                    tLObject = imageLocation;
                    key = str3;
                    str3 = str2;
                    obj3 = obj2;
                } else {
                    str4 = null;
                    tLObject = null;
                    key = null;
                    str3 = str2;
                    obj3 = obj2;
                }
            }
            if (thumbLocation == null) {
                str = null;
            } else {
                str = thumbLocation.volume_id + "_" + thumbLocation.local_id;
                str3 = str + "." + ext;
            }
            filter = imageReceiver.getFilter();
            thumbFilter = imageReceiver.getThumbFilter();
            if (key != null) {
            }
            if (str != null) {
            }
            if (httpImageLocation == null) {
                cacheType = imageReceiver.getCacheType();
                cacheType = 1;
                if (cacheType != 0) {
                }
                if (obj == null) {
                }
                createLoadOperationForImageReceiver(imageReceiver, thumbKey, str3, ext, thumbLocation, null, thumbFilter, 0, cacheType != 0 ? 1 : cacheType, obj == null ? 2 : 1);
                createLoadOperationForImageReceiver(imageReceiver, str6, str4, ext, tLObject, null, filter, imageReceiver.getSize(), cacheType, 0);
                return;
            }
            if (obj == null) {
            }
            createLoadOperationForImageReceiver(imageReceiver, thumbKey, str3, ext, thumbLocation, null, thumbFilter, 0, 1, obj == null ? 2 : 1);
            createLoadOperationForImageReceiver(imageReceiver, str6, str4, ext, null, httpImageLocation, filter, 0, 1, 0);
        }
    }

    public void putImageToCache(BitmapDrawable bitmapDrawable, String str) {
        this.memCache.put(str, bitmapDrawable);
    }

    public void removeImage(String str) {
        this.bitmapUseCounts.remove(str);
        this.memCache.remove(str);
    }

    public void replaceImageInCache(final String str, final String str2, final FileLocation fileLocation, boolean z) {
        if (z) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    ImageLoader.this.replaceImageInCacheInternal(str, str2, fileLocation);
                }
            });
        } else {
            replaceImageInCacheInternal(str, str2, fileLocation);
        }
    }
}
