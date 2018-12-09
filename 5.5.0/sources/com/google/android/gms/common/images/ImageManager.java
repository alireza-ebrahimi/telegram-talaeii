package com.google.android.gms.common.images;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.support.v4.p022f.C0471g;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.images.ImageRequest.ImageViewImageRequest;
import com.google.android.gms.common.images.ImageRequest.ListenerImageRequest;
import com.google.android.gms.common.images.internal.PostProcessedResourceCache;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.telegram.messenger.exoplayer2.source.ExtractorMediaSource;

public final class ImageManager {
    public static final int PRIORITY_HIGH = 3;
    public static final int PRIORITY_LOW = 1;
    public static final int PRIORITY_MEDIUM = 2;
    private static final Object zzov = new Object();
    private static HashSet<Uri> zzow = new HashSet();
    private static ImageManager zzox;
    private static ImageManager zzoy;
    private final Context mContext;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final ExecutorService zzoz = Executors.newFixedThreadPool(4);
    private final zza zzpa;
    private final PostProcessedResourceCache zzpb;
    private final Map<ImageRequest, ImageReceiver> zzpc;
    private final Map<Uri, ImageReceiver> zzpd;
    private final Map<Uri, Long> zzpe;

    @KeepName
    private final class ImageReceiver extends ResultReceiver {
        private final Uri mUri;
        private final ArrayList<ImageRequest> zzpf = new ArrayList();
        private final /* synthetic */ ImageManager zzpg;

        ImageReceiver(ImageManager imageManager, Uri uri) {
            this.zzpg = imageManager;
            super(new Handler(Looper.getMainLooper()));
            this.mUri = uri;
        }

        public final void onReceiveResult(int i, Bundle bundle) {
            this.zzpg.zzoz.execute(new zzb(this.zzpg, this.mUri, (ParcelFileDescriptor) bundle.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }

        public final void zza(ImageRequest imageRequest) {
            Asserts.checkMainThread("ImageReceiver.addImageRequest() must be called in the main thread");
            this.zzpf.add(imageRequest);
        }

        public final void zzb(ImageRequest imageRequest) {
            Asserts.checkMainThread("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.zzpf.remove(imageRequest);
        }

        public final void zzco() {
            Intent intent = new Intent(Constants.ACTION_LOAD_IMAGE);
            intent.putExtra(Constants.EXTRA_URI, this.mUri);
            intent.putExtra(Constants.EXTRA_RESULT_RECEIVER, this);
            intent.putExtra(Constants.EXTRA_PRIORITY, 3);
            this.zzpg.mContext.sendBroadcast(intent);
        }
    }

    public interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable, boolean z);
    }

    private static final class zza extends C0471g<zza, Bitmap> {
        public zza(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            super((int) (((float) ((((context.getApplicationInfo().flags & ExtractorMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES) != 0 ? 1 : null) != null ? activityManager.getLargeMemoryClass() : activityManager.getMemoryClass()) * ExtractorMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES)) * 0.33f));
        }

        protected final /* synthetic */ void entryRemoved(boolean z, Object obj, Object obj2, Object obj3) {
            super.entryRemoved(z, (zza) obj, (Bitmap) obj2, (Bitmap) obj3);
        }

        protected final /* synthetic */ int sizeOf(Object obj, Object obj2) {
            Bitmap bitmap = (Bitmap) obj2;
            return bitmap.getHeight() * bitmap.getRowBytes();
        }
    }

    private final class zzb implements Runnable {
        private final Uri mUri;
        private final /* synthetic */ ImageManager zzpg;
        private final ParcelFileDescriptor zzph;

        public zzb(ImageManager imageManager, Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.zzpg = imageManager;
            this.mUri = uri;
            this.zzph = parcelFileDescriptor;
        }

        public final void run() {
            Asserts.checkNotMainThread("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            boolean z = false;
            Bitmap bitmap = null;
            if (this.zzph != null) {
                try {
                    bitmap = BitmapFactory.decodeFileDescriptor(this.zzph.getFileDescriptor());
                } catch (Throwable e) {
                    String valueOf = String.valueOf(this.mUri);
                    Log.e("ImageManager", new StringBuilder(String.valueOf(valueOf).length() + 34).append("OOM while loading bitmap for uri: ").append(valueOf).toString(), e);
                    z = true;
                }
                try {
                    this.zzph.close();
                } catch (Throwable e2) {
                    Log.e("ImageManager", "closed failed", e2);
                }
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            this.zzpg.mHandler.post(new zze(this.zzpg, this.mUri, bitmap, z, countDownLatch));
            try {
                countDownLatch.await();
            } catch (InterruptedException e3) {
                String valueOf2 = String.valueOf(this.mUri);
                Log.w("ImageManager", new StringBuilder(String.valueOf(valueOf2).length() + 32).append("Latch interrupted while posting ").append(valueOf2).toString());
            }
        }
    }

    private final class zzc implements Runnable {
        private final /* synthetic */ ImageManager zzpg;
        private final ImageRequest zzpi;

        public zzc(ImageManager imageManager, ImageRequest imageRequest) {
            this.zzpg = imageManager;
            this.zzpi = imageRequest;
        }

        public final void run() {
            Asserts.checkMainThread("LoadImageRunnable must be executed on the main thread");
            ImageReceiver imageReceiver = (ImageReceiver) this.zzpg.zzpc.get(this.zzpi);
            if (imageReceiver != null) {
                this.zzpg.zzpc.remove(this.zzpi);
                imageReceiver.zzb(this.zzpi);
            }
            zza zza = this.zzpi.zzpk;
            if (zza.uri == null) {
                this.zzpi.zza(this.zzpg.mContext, this.zzpg.zzpb, true);
                return;
            }
            Bitmap zza2 = this.zzpg.zza(zza);
            if (zza2 != null) {
                this.zzpi.zza(this.zzpg.mContext, zza2, true);
                return;
            }
            Long l = (Long) this.zzpg.zzpe.get(zza.uri);
            if (l != null) {
                if (SystemClock.elapsedRealtime() - l.longValue() < 3600000) {
                    this.zzpi.zza(this.zzpg.mContext, this.zzpg.zzpb, true);
                    return;
                }
                this.zzpg.zzpe.remove(zza.uri);
            }
            this.zzpi.zza(this.zzpg.mContext, this.zzpg.zzpb);
            imageReceiver = (ImageReceiver) this.zzpg.zzpd.get(zza.uri);
            if (imageReceiver == null) {
                imageReceiver = new ImageReceiver(this.zzpg, zza.uri);
                this.zzpg.zzpd.put(zza.uri, imageReceiver);
            }
            imageReceiver.zza(this.zzpi);
            if (!(this.zzpi instanceof ListenerImageRequest)) {
                this.zzpg.zzpc.put(this.zzpi, imageReceiver);
            }
            synchronized (ImageManager.zzov) {
                if (!ImageManager.zzow.contains(zza.uri)) {
                    ImageManager.zzow.add(zza.uri);
                    imageReceiver.zzco();
                }
            }
        }
    }

    private static final class zzd implements ComponentCallbacks2 {
        private final zza zzpa;

        public zzd(zza zza) {
            this.zzpa = zza;
        }

        public final void onConfigurationChanged(Configuration configuration) {
        }

        public final void onLowMemory() {
            this.zzpa.evictAll();
        }

        public final void onTrimMemory(int i) {
            if (i >= 60) {
                this.zzpa.evictAll();
            } else if (i >= 20) {
                this.zzpa.trimToSize(this.zzpa.size() / 2);
            }
        }
    }

    private final class zze implements Runnable {
        private final Bitmap mBitmap;
        private final Uri mUri;
        private final CountDownLatch zzfd;
        private final /* synthetic */ ImageManager zzpg;
        private boolean zzpj;

        public zze(ImageManager imageManager, Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
            this.zzpg = imageManager;
            this.mUri = uri;
            this.mBitmap = bitmap;
            this.zzpj = z;
            this.zzfd = countDownLatch;
        }

        public final void run() {
            Asserts.checkMainThread("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean z = this.mBitmap != null;
            if (this.zzpg.zzpa != null) {
                if (this.zzpj) {
                    this.zzpg.zzpa.evictAll();
                    System.gc();
                    this.zzpj = false;
                    this.zzpg.mHandler.post(this);
                    return;
                } else if (z) {
                    this.zzpg.zzpa.put(new zza(this.mUri), this.mBitmap);
                }
            }
            ImageReceiver imageReceiver = (ImageReceiver) this.zzpg.zzpd.remove(this.mUri);
            if (imageReceiver != null) {
                ArrayList zza = imageReceiver.zzpf;
                int size = zza.size();
                for (int i = 0; i < size; i++) {
                    ImageRequest imageRequest = (ImageRequest) zza.get(i);
                    if (z) {
                        imageRequest.zza(this.zzpg.mContext, this.mBitmap, false);
                    } else {
                        this.zzpg.zzpe.put(this.mUri, Long.valueOf(SystemClock.elapsedRealtime()));
                        imageRequest.zza(this.zzpg.mContext, this.zzpg.zzpb, false);
                    }
                    if (!(imageRequest instanceof ListenerImageRequest)) {
                        this.zzpg.zzpc.remove(imageRequest);
                    }
                }
            }
            this.zzfd.countDown();
            synchronized (ImageManager.zzov) {
                ImageManager.zzow.remove(this.mUri);
            }
        }
    }

    private ImageManager(Context context, boolean z) {
        this.mContext = context.getApplicationContext();
        if (z) {
            this.zzpa = new zza(this.mContext);
            this.mContext.registerComponentCallbacks(new zzd(this.zzpa));
        } else {
            this.zzpa = null;
        }
        this.zzpb = new PostProcessedResourceCache();
        this.zzpc = new HashMap();
        this.zzpd = new HashMap();
        this.zzpe = new HashMap();
    }

    public static ImageManager create(Context context) {
        return create(context, false);
    }

    public static ImageManager create(Context context, boolean z) {
        if (z) {
            if (zzoy == null) {
                zzoy = new ImageManager(context, true);
            }
            return zzoy;
        }
        if (zzox == null) {
            zzox = new ImageManager(context, false);
        }
        return zzox;
    }

    private final Bitmap zza(zza zza) {
        return this.zzpa == null ? null : (Bitmap) this.zzpa.get(zza);
    }

    public final void loadImage(ImageView imageView, int i) {
        loadImage(new ImageViewImageRequest(imageView, i));
    }

    public final void loadImage(ImageView imageView, Uri uri) {
        loadImage(new ImageViewImageRequest(imageView, uri));
    }

    public final void loadImage(ImageView imageView, Uri uri, int i) {
        ImageRequest imageViewImageRequest = new ImageViewImageRequest(imageView, uri);
        imageViewImageRequest.setNoDataPlaceholder(i);
        loadImage(imageViewImageRequest);
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        loadImage(new ListenerImageRequest(onImageLoadedListener, uri));
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri, int i) {
        ImageRequest listenerImageRequest = new ListenerImageRequest(onImageLoadedListener, uri);
        listenerImageRequest.setNoDataPlaceholder(i);
        loadImage(listenerImageRequest);
    }

    public final void loadImage(ImageRequest imageRequest) {
        Asserts.checkMainThread("ImageManager.loadImage() must be called in the main thread");
        new zzc(this, imageRequest).run();
    }
}
