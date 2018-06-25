package org.telegram.customization.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.fetch.FetchConst;
import org.telegram.messenger.ApplicationLoader;

public class AppImageLoader {
    private static DisplayImageOptions adapterOptions;

    private static class AnimateFirstDisplayListener implements ImageLoadingListener, ImageLoadingProgressListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList());
        ProgressBar pbLoading;

        /* renamed from: org.telegram.customization.util.AppImageLoader$AnimateFirstDisplayListener$1 */
        class C12251 implements Runnable {
            C12251() {
            }

            public void run() {
                if (AnimateFirstDisplayListener.this.pbLoading != null) {
                    AnimateFirstDisplayListener.this.pbLoading.setVisibility(8);
                }
            }
        }

        public AnimateFirstDisplayListener(ProgressBar loading) {
            this.pbLoading = loading;
        }

        public void onLoadingStarted(String imageUri, View view) {
            ((ImageView) view).setImageResource(R.drawable.placeholderloading);
            if (this.pbLoading != null) {
                this.pbLoading.setVisibility(0);
                this.pbLoading.startAnimation(AnimationUtils.loadAnimation(ApplicationLoader.applicationContext, R.anim.rotate));
            }
        }

        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            if (failReason != null) {
                try {
                    if (!(failReason.getCause() == null || TextUtils.isEmpty(failReason.getCause().getMessage()))) {
                        failReason.getCause().printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            ((ImageView) view).setImageResource(R.drawable.placeholderno3);
        }

        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                if (!displayedImages.contains(imageUri)) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
                new Handler().postDelayed(new C12251(), FetchConst.DEFAULT_ON_UPDATE_INTERVAL);
            }
        }

        public void onLoadingCancelled(String imageUri, View view) {
        }

        public void onProgressUpdate(String imageUri, View view, int current, int total) {
            if (this.pbLoading != null) {
                this.pbLoading.setProgress(current);
            }
        }
    }

    public static void loadImage(ImageView ivNewsImge, String imageUrl) {
        if (imageUrl == null || imageUrl.length() <= 0) {
            ivNewsImge.setImageResource(R.drawable.placeholderno3);
        } else {
            ImageLoader.getInstance().displayImage(imageUrl, ivNewsImge, getImageOptionForAdapter(), new AnimateFirstDisplayListener(null));
        }
    }

    public static void loadImage(ImageView ivNewsImge, String imageUrl, ProgressBar pbLoading) {
        if (imageUrl == null || imageUrl.length() <= 0) {
            ivNewsImge.setImageResource(R.drawable.placeholderno3);
        } else {
            ImageLoader.getInstance().displayImage(imageUrl, ivNewsImge, getImageOptionForAdapter(), new AnimateFirstDisplayListener(pbLoading));
        }
    }

    public static DisplayImageOptions getImageOptionForAdapter() {
        if (adapterOptions == null) {
            adapterOptions = new Builder().showImageOnLoading(R.drawable.placeholderloading).showImageForEmptyUri(R.drawable.placeholderno3).showImageOnFail(R.drawable.placeholderno3).cacheInMemory(false).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Config.RGB_565).build();
        }
        return adapterOptions;
    }

    public static void loadImageLikeTelegram(ImageView imageView, String imageUrl, final CircularProgressBar progressBar) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageResource(R.drawable.placeholderno4);
            return;
        }
        ImageLoader imageLoader = ImageLoader.getInstance();
        final Animation rotation = AnimationUtils.loadAnimation(ApplicationLoader.applicationContext, R.anim.rotate);
        rotation.setRepeatCount(-1);
        imageLoader.displayImage(imageUrl, imageView, getImageOptionForAdapter(), new ImageLoadingListener() {
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(0);
                progressBar.startAnimation(rotation);
            }

            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.clearAnimation();
                progressBar.setVisibility(8);
            }

            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.clearAnimation();
                progressBar.setVisibility(8);
            }

            public void onLoadingCancelled(String imageUri, View view) {
                progressBar.setVisibility(8);
                progressBar.clearAnimation();
            }
        }, new ImageLoadingProgressListener() {
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                if (progressBar != null) {
                    progressBar.setProgress((float) ((current * 100) / total));
                }
            }
        });
    }

    public static void loadImageLikeTelegramForVideo(ImageView imageView, String imageUrl, final CircularProgressBar progressBar) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageResource(R.drawable.placeholderno4);
            return;
        }
        ImageLoader imageLoader = ImageLoader.getInstance();
        final Animation rotation = AnimationUtils.loadAnimation(ApplicationLoader.applicationContext, R.anim.rotate);
        rotation.setRepeatCount(-1);
        imageLoader.displayImage(imageUrl, imageView, getImageOptionForAdapter(), new ImageLoadingListener() {
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(0);
                progressBar.startAnimation(rotation);
            }

            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.clearAnimation();
                progressBar.setVisibility(8);
            }

            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.clearAnimation();
                progressBar.setVisibility(8);
                if (VERSION.SDK_INT >= 17) {
                    Bitmap blurred = AppUtilities.blurRenderScript(view.getContext(), loadedImage, 3);
                    if (blurred == null) {
                        ((ImageView) view).setImageBitmap(loadedImage);
                        return;
                    } else {
                        ((ImageView) view).setImageBitmap(blurred);
                        return;
                    }
                }
                ((ImageView) view).setImageBitmap(loadedImage);
            }

            public void onLoadingCancelled(String imageUri, View view) {
                progressBar.setVisibility(8);
                progressBar.clearAnimation();
            }
        }, new ImageLoadingProgressListener() {
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                if (progressBar != null) {
                    progressBar.setProgress((float) ((current * 100) / total));
                }
            }
        });
    }
}
