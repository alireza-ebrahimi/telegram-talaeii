package com.persianswitch.sdk.base.webservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.Injection.Network;
import com.persianswitch.sdk.base.utils.func.Action1;
import java.io.InputStream;

public final class DownloadLogoTask extends AsyncTask<Void, Void, Bitmap> {
    private final Action1<Bitmap> mCallback;
    private final Config mConfig;
    private final Context mContext;
    private final String mURL;

    private DownloadLogoTask(Context context, Config config, String URL, Action1<Bitmap> callback) {
        this.mContext = context;
        this.mConfig = config;
        this.mURL = URL;
        this.mCallback = callback;
    }

    public static void loadInImageView(Config config, String url, final ImageView imageView) {
        if (imageView != null) {
            AsyncTaskUtil.executeParallel(new DownloadLogoTask(imageView.getContext(), config, url, new Action1<Bitmap>() {
                public void call(Bitmap input) {
                    imageView.setImageBitmap(input);
                }
            }), new Void[0]);
        }
    }

    protected Bitmap doInBackground(Void... params) {
        InputStream bitmapStream = Network.engine(this.mContext, this.mConfig).getResourceStream(this.mURL);
        Options options = new Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        return BitmapFactory.decodeStream(bitmapStream, null, options);
    }

    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null && this.mCallback != null) {
            this.mCallback.call(bitmap);
        }
    }
}
