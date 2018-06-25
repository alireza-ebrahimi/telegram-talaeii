package br.com.goncalves.pugnotification.notification;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.Spanned;
import android.widget.RemoteViews;
import br.com.goncalves.pugnotification.C0402R;
import br.com.goncalves.pugnotification.interfaces.ImageLoader;
import br.com.goncalves.pugnotification.interfaces.OnImageLoadingCompleted;

@TargetApi(16)
public class Custom extends Builder implements OnImageLoadingCompleted {
    private static final String TAG = Custom.class.getSimpleName();
    private int mBackgroundResId;
    private ImageLoader mImageLoader;
    private String mMessage;
    private Spanned mMessageSpanned;
    private int mPlaceHolderResourceId;
    private RemoteViews mRemoteView = new RemoteViews(PugNotification.mSingleton.mContext.getPackageName(), C0402R.layout.pugnotification_custom);
    private int mSmallIcon;
    private String mTitle;
    private String mUri;

    public Custom(Builder builder, int identifier, String title, String message, Spanned messageSpanned, int smallIcon, String tag) {
        super(builder, identifier, tag);
        this.mTitle = title;
        this.mMessage = message;
        this.mMessageSpanned = messageSpanned;
        this.mSmallIcon = smallIcon;
        this.mPlaceHolderResourceId = C0402R.drawable.pugnotification_ic_placeholder;
        init();
    }

    private void init() {
        setTitle();
        setMessage();
        setSmallIcon();
    }

    private void setTitle() {
        this.mRemoteView.setTextViewText(C0402R.id.notification_text_title, this.mTitle);
    }

    private void setMessage() {
        if (this.mMessageSpanned != null) {
            this.mRemoteView.setTextViewText(C0402R.id.notification_text_message, this.mMessageSpanned);
        } else {
            this.mRemoteView.setTextViewText(C0402R.id.notification_text_message, this.mMessage);
        }
    }

    private void setSmallIcon() {
        if (this.mSmallIcon <= 0) {
            this.mRemoteView.setImageViewResource(C0402R.id.notification_img_icon, C0402R.drawable.pugnotification_ic_launcher);
        }
        this.mRemoteView.setImageViewResource(C0402R.id.notification_img_icon, this.mSmallIcon);
    }

    public Custom background(@DrawableRes int resource) {
        if (resource <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        } else if (this.mUri != null) {
            throw new IllegalStateException("Background Already Set!");
        } else {
            this.mBackgroundResId = resource;
            return this;
        }
    }

    public Custom setPlaceholder(@DrawableRes int resource) {
        if (resource <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        this.mPlaceHolderResourceId = resource;
        return this;
    }

    public Custom setImageLoader(ImageLoader imageLoader) {
        this.mImageLoader = imageLoader;
        return this;
    }

    public Custom background(String uri) {
        if (this.mBackgroundResId > 0) {
            throw new IllegalStateException("Background Already Set!");
        } else if (this.mUri != null) {
            throw new IllegalStateException("Background Already Set!");
        } else if (uri == null) {
            throw new IllegalArgumentException("Path Must Not Be Null!");
        } else if (uri.trim().length() == 0) {
            throw new IllegalArgumentException("Path Must Not Be Empty!");
        } else if (this.mImageLoader == null) {
            throw new IllegalStateException("You have to set an ImageLoader!");
        } else {
            this.mUri = uri;
            return this;
        }
    }

    public void build() {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException("Method call should happen from the main thread.");
        }
        super.build();
        setBigContentView(this.mRemoteView);
        loadImageBackground();
    }

    private void loadImageBackground() {
        this.mRemoteView.setImageViewResource(C0402R.id.notification_img_background, this.mPlaceHolderResourceId);
        if (this.mUri != null) {
            this.mImageLoader.load(this.mUri, (OnImageLoadingCompleted) this);
        } else {
            this.mImageLoader.load(this.mBackgroundResId, (OnImageLoadingCompleted) this);
        }
    }

    public void imageLoadingCompleted(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("bitmap cannot be null");
        }
        this.mRemoteView.setImageViewBitmap(C0402R.id.notification_img_background, bitmap);
        super.notificationNotify();
    }
}
