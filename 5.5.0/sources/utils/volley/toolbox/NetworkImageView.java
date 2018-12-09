package utils.volley.toolbox;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import utils.volley.VolleyError;
import utils.volley.toolbox.ImageLoader.ImageContainer;
import utils.volley.toolbox.ImageLoader.ImageListener;

public class NetworkImageView extends ImageView {
    private int mDefaultImageId;
    private int mErrorImageId;
    private ImageContainer mImageContainer;
    private ImageLoader mImageLoader;
    private String mUrl;

    public NetworkImageView(Context context) {
        this(context, null);
    }

    public NetworkImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NetworkImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void setDefaultImageOrNull() {
        if (this.mDefaultImageId != 0) {
            setImageResource(this.mDefaultImageId);
        } else {
            setImageBitmap(null);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    void loadImageIfNecessary(final boolean z) {
        Object obj;
        Object obj2;
        Object obj3 = 1;
        int width = getWidth();
        int height = getHeight();
        ScaleType scaleType = getScaleType();
        if (getLayoutParams() != null) {
            Object obj4 = getLayoutParams().height == -2 ? 1 : null;
            obj = getLayoutParams().width == -2 ? 1 : null;
            obj2 = obj4;
        } else {
            obj2 = null;
            obj = null;
        }
        if (obj == null || obj2 == null) {
            obj3 = null;
        }
        if (width != 0 || height != 0 || r1 != null) {
            if (TextUtils.isEmpty(this.mUrl)) {
                if (this.mImageContainer != null) {
                    this.mImageContainer.cancelRequest();
                    this.mImageContainer = null;
                }
                setDefaultImageOrNull();
                return;
            }
            if (!(this.mImageContainer == null || this.mImageContainer.getRequestUrl() == null)) {
                if (!this.mImageContainer.getRequestUrl().equals(this.mUrl)) {
                    this.mImageContainer.cancelRequest();
                    setDefaultImageOrNull();
                } else {
                    return;
                }
            }
            int i = obj != null ? 0 : width;
            if (obj2 != null) {
                height = 0;
            }
            this.mImageContainer = this.mImageLoader.get(this.mUrl, new ImageListener() {
                public void onErrorResponse(VolleyError volleyError) {
                    if (NetworkImageView.this.mErrorImageId != 0) {
                        NetworkImageView.this.setImageResource(NetworkImageView.this.mErrorImageId);
                    }
                }

                public void onResponse(final ImageContainer imageContainer, boolean z) {
                    if (z && z) {
                        NetworkImageView.this.post(new Runnable() {
                            public void run() {
                                C53691.this.onResponse(imageContainer, false);
                            }
                        });
                    } else if (imageContainer.getBitmap() != null) {
                        NetworkImageView.this.setImageBitmap(imageContainer.getBitmap());
                    } else if (NetworkImageView.this.mDefaultImageId != 0) {
                        NetworkImageView.this.setImageResource(NetworkImageView.this.mDefaultImageId);
                    }
                }
            }, i, height, scaleType);
        }
    }

    protected void onDetachedFromWindow() {
        if (this.mImageContainer != null) {
            this.mImageContainer.cancelRequest();
            setImageBitmap(null);
            this.mImageContainer = null;
        }
        super.onDetachedFromWindow();
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        loadImageIfNecessary(true);
    }

    public void setDefaultImageResId(int i) {
        this.mDefaultImageId = i;
    }

    public void setErrorImageResId(int i) {
        this.mErrorImageId = i;
    }

    public void setImageUrl(String str, ImageLoader imageLoader) {
        this.mUrl = str;
        this.mImageLoader = imageLoader;
        loadImageIfNecessary(false);
    }
}
