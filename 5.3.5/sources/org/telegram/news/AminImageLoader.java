package org.telegram.news;

import android.graphics.Bitmap;
import android.view.View;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AminImageLoader {
    public static AminImageLoader AminImageLoader;
    public ImageLoader imageLoader = ImageLoader.getInstance();
    public Map<String, MImageLoader> mImageLoaderMap = new HashMap();

    class MImageLoader {
        ArrayList<ImageLoadingListener> imageLoadingListeners = new ArrayList();
        String imageUrl;

        MImageLoader(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        void addImageLoadingListener(ImageLoadingListener imageLoadingListener) {
            this.imageLoadingListeners.add(imageLoadingListener);
        }
    }

    public static AminImageLoader getInstance() {
        if (AminImageLoader == null) {
            AminImageLoader = new AminImageLoader();
        }
        return AminImageLoader;
    }

    public void loadImage(final String url, DisplayImageOptions options, ImageLoadingListener imageLoadingListener) {
        MImageLoader mImageLoader = (MImageLoader) this.mImageLoaderMap.get(url);
        if (mImageLoader == null) {
            mImageLoader = new MImageLoader(url);
            mImageLoader.addImageLoadingListener(imageLoadingListener);
            this.mImageLoaderMap.put(url, mImageLoader);
            this.imageLoader.loadImage(url, options, new SimpleImageLoadingListener() {
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    AminImageLoader.this.setAllMImageLoader(url, loadedImage);
                }
            });
            return;
        }
        mImageLoader.addImageLoadingListener(imageLoadingListener);
    }

    public void setAllMImageLoader(String url, Bitmap bitmap) {
        ArrayList<ImageLoadingListener> imageLoadingListeners = ((MImageLoader) this.mImageLoaderMap.remove(url)).imageLoadingListeners;
        for (int i = 0; i < imageLoadingListeners.size(); i++) {
            ((ImageLoadingListener) imageLoadingListeners.get(i)).onLoadingComplete("", null, bitmap);
        }
        imageLoadingListeners.clear();
    }
}
