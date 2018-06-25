package com.google.android.gms.common.images;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbgk;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.lang3.time.DateUtils;

public final class ImageManager {
    private static final Object zzgdb = new Object();
    private static HashSet<Uri> zzgdc = new HashSet();
    private static ImageManager zzgdd;
    private final Context mContext;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final ExecutorService zzgde = Executors.newFixedThreadPool(4);
    private final zza zzgdf = null;
    private final zzbgk zzgdg = new zzbgk();
    private final Map<zza, ImageReceiver> zzgdh = new HashMap();
    private final Map<Uri, ImageReceiver> zzgdi = new HashMap();
    private final Map<Uri, Long> zzgdj = new HashMap();

    @KeepName
    final class ImageReceiver extends ResultReceiver {
        private final Uri mUri;
        private final ArrayList<zza> zzgdk = new ArrayList();
        private /* synthetic */ ImageManager zzgdl;

        ImageReceiver(ImageManager imageManager, Uri uri) {
            this.zzgdl = imageManager;
            super(new Handler(Looper.getMainLooper()));
            this.mUri = uri;
        }

        public final void onReceiveResult(int i, Bundle bundle) {
            this.zzgdl.zzgde.execute(new zzb(this.zzgdl, this.mUri, (ParcelFileDescriptor) bundle.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }

        public final void zzalm() {
            Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            intent.putExtra("com.google.android.gms.extras.uri", this.mUri);
            intent.putExtra("com.google.android.gms.extras.resultReceiver", this);
            intent.putExtra("com.google.android.gms.extras.priority", 3);
            this.zzgdl.mContext.sendBroadcast(intent);
        }

        public final void zzb(zza zza) {
            com.google.android.gms.common.internal.zzc.zzgn("ImageReceiver.addImageRequest() must be called in the main thread");
            this.zzgdk.add(zza);
        }

        public final void zzc(zza zza) {
            com.google.android.gms.common.internal.zzc.zzgn("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.zzgdk.remove(zza);
        }
    }

    public interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable, boolean z);
    }

    static final class zza extends LruCache<zzb, Bitmap> {
        protected final /* synthetic */ void entryRemoved(boolean z, Object obj, Object obj2, Object obj3) {
            super.entryRemoved(z, (zzb) obj, (Bitmap) obj2, (Bitmap) obj3);
        }

        protected final /* synthetic */ int sizeOf(Object obj, Object obj2) {
            Bitmap bitmap = (Bitmap) obj2;
            return bitmap.getHeight() * bitmap.getRowBytes();
        }
    }

    final class zzb implements Runnable {
        private final Uri mUri;
        private /* synthetic */ ImageManager zzgdl;
        private final ParcelFileDescriptor zzgdm;

        public zzb(ImageManager imageManager, Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.zzgdl = imageManager;
            this.mUri = uri;
            this.zzgdm = parcelFileDescriptor;
        }

        public final void run() {
            String str = "LoadBitmapFromDiskRunnable can't be executed in the main thread";
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                String valueOf = String.valueOf(Thread.currentThread());
                String valueOf2 = String.valueOf(Looper.getMainLooper().getThread());
                Log.e("Asserts", new StringBuilder((String.valueOf(valueOf).length() + 56) + String.valueOf(valueOf2).length()).append("checkNotMainThread: current thread ").append(valueOf).append(" IS the main thread ").append(valueOf2).append("!").toString());
                throw new IllegalStateException(str);
            }
            boolean z = false;
            Bitmap bitmap = null;
            if (this.zzgdm != null) {
                try {
                    bitmap = BitmapFactory.decodeFileDescriptor(this.zzgdm.getFileDescriptor());
                } catch (Throwable e) {
                    String valueOf3 = String.valueOf(this.mUri);
                    Log.e("ImageManager", new StringBuilder(String.valueOf(valueOf3).length() + 34).append("OOM while loading bitmap for uri: ").append(valueOf3).toString(), e);
                    z = true;
                }
                try {
                    this.zzgdm.close();
                } catch (Throwable e2) {
                    Log.e("ImageManager", "closed failed", e2);
                }
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            this.zzgdl.mHandler.post(new zzd(this.zzgdl, this.mUri, bitmap, z, countDownLatch));
            try {
                countDownLatch.await();
            } catch (InterruptedException e3) {
                String valueOf4 = String.valueOf(this.mUri);
                Log.w("ImageManager", new StringBuilder(String.valueOf(valueOf4).length() + 32).append("Latch interrupted while posting ").append(valueOf4).toString());
            }
        }
    }

    final class zzc implements Runnable {
        private /* synthetic */ ImageManager zzgdl;
        private final zza zzgdn;

        public zzc(ImageManager imageManager, zza zza) {
            this.zzgdl = imageManager;
            this.zzgdn = zza;
        }

        public final void run() {
            com.google.android.gms.common.internal.zzc.zzgn("LoadImageRunnable must be executed on the main thread");
            ImageReceiver imageReceiver = (ImageReceiver) this.zzgdl.zzgdh.get(this.zzgdn);
            if (imageReceiver != null) {
                this.zzgdl.zzgdh.remove(this.zzgdn);
                imageReceiver.zzc(this.zzgdn);
            }
            zzb zzb = this.zzgdn.zzgdp;
            if (zzb.uri == null) {
                this.zzgdn.zza(this.zzgdl.mContext, this.zzgdl.zzgdg, true);
                return;
            }
            Bitmap zza = this.zzgdl.zza(zzb);
            if (zza != null) {
                this.zzgdn.zza(this.zzgdl.mContext, zza, true);
                return;
            }
            Long l = (Long) this.zzgdl.zzgdj.get(zzb.uri);
            if (l != null) {
                if (SystemClock.elapsedRealtime() - l.longValue() < DateUtils.MILLIS_PER_HOUR) {
                    this.zzgdn.zza(this.zzgdl.mContext, this.zzgdl.zzgdg, true);
                    return;
                }
                this.zzgdl.zzgdj.remove(zzb.uri);
            }
            this.zzgdn.zza(this.zzgdl.mContext, this.zzgdl.zzgdg);
            imageReceiver = (ImageReceiver) this.zzgdl.zzgdi.get(zzb.uri);
            if (imageReceiver == null) {
                imageReceiver = new ImageReceiver(this.zzgdl, zzb.uri);
                this.zzgdl.zzgdi.put(zzb.uri, imageReceiver);
            }
            imageReceiver.zzb(this.zzgdn);
            if (!(this.zzgdn instanceof zzd)) {
                this.zzgdl.zzgdh.put(this.zzgdn, imageReceiver);
            }
            synchronized (ImageManager.zzgdb) {
                if (!ImageManager.zzgdc.contains(zzb.uri)) {
                    ImageManager.zzgdc.add(zzb.uri);
                    imageReceiver.zzalm();
                }
            }
        }
    }

    final class zzd implements Runnable {
        private final Bitmap mBitmap;
        private final Uri mUri;
        private final CountDownLatch zzapc;
        private /* synthetic */ ImageManager zzgdl;
        private boolean zzgdo;

        public zzd(ImageManager imageManager, Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
            this.zzgdl = imageManager;
            this.mUri = uri;
            this.mBitmap = bitmap;
            this.zzgdo = z;
            this.zzapc = countDownLatch;
        }

        public final void run() {
            com.google.android.gms.common.internal.zzc.zzgn("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean z = this.mBitmap != null;
            if (this.zzgdl.zzgdf != null) {
                if (this.zzgdo) {
                    this.zzgdl.zzgdf.evictAll();
                    System.gc();
                    this.zzgdo = false;
                    this.zzgdl.mHandler.post(this);
                    return;
                } else if (z) {
                    this.zzgdl.zzgdf.put(new zzb(this.mUri), this.mBitmap);
                }
            }
            ImageReceiver imageReceiver = (ImageReceiver) this.zzgdl.zzgdi.remove(this.mUri);
            if (imageReceiver != null) {
                ArrayList zza = imageReceiver.zzgdk;
                int size = zza.size();
                for (int i = 0; i < size; i++) {
                    zza zza2 = (zza) zza.get(i);
                    if (z) {
                        zza2.zza(this.zzgdl.mContext, this.mBitmap, false);
                    } else {
                        this.zzgdl.zzgdj.put(this.mUri, Long.valueOf(SystemClock.elapsedRealtime()));
                        zza2.zza(this.zzgdl.mContext, this.zzgdl.zzgdg, false);
                    }
                    if (!(zza2 instanceof zzd)) {
                        this.zzgdl.zzgdh.remove(zza2);
                    }
                }
            }
            this.zzapc.countDown();
            synchronized (ImageManager.zzgdb) {
                ImageManager.zzgdc.remove(this.mUri);
            }
        }
    }

    private ImageManager(Context context, boolean z) {
        this.mContext = context.getApplicationContext();
    }

    public static ImageManager create(Context context) {
        if (zzgdd == null) {
            zzgdd = new ImageManager(context, false);
        }
        return zzgdd;
    }

    private final Bitmap zza(zzb zzb) {
        return this.zzgdf == null ? null : (Bitmap) this.zzgdf.get(zzb);
    }

    @Hide
    private final void zza(zza zza) {
        com.google.android.gms.common.internal.zzc.zzgn("ImageManager.loadImage() must be called in the main thread");
        new zzc(this, zza).run();
    }

    public final void loadImage(ImageView imageView, int i) {
        zza(new zzc(imageView, i));
    }

    public final void loadImage(ImageView imageView, Uri uri) {
        zza(new zzc(imageView, uri));
    }

    public final void loadImage(ImageView imageView, Uri uri, int i) {
        zza zzc = new zzc(imageView, uri);
        zzc.zzgdr = i;
        zza(zzc);
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        zza(new zzd(onImageLoadedListener, uri));
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri, int i) {
        zza zzd = new zzd(onImageLoadedListener, uri);
        zzd.zzgdr = i;
        zza(zzd);
    }
}
