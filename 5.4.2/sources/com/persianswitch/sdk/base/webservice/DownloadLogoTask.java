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
    /* renamed from: a */
    private final Context f7137a;
    /* renamed from: b */
    private final Config f7138b;
    /* renamed from: c */
    private final String f7139c;
    /* renamed from: d */
    private final Action1<Bitmap> f7140d;

    private DownloadLogoTask(Context context, Config config, String str, Action1<Bitmap> action1) {
        this.f7137a = context;
        this.f7138b = config;
        this.f7139c = str;
        this.f7140d = action1;
    }

    /* renamed from: a */
    public static void m10816a(Config config, String str, final ImageView imageView) {
        if (imageView != null) {
            AsyncTaskUtil.m10813a(new DownloadLogoTask(imageView.getContext(), config, str, new Action1<Bitmap>() {
                /* renamed from: a */
                public void m10814a(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }
            }), new Void[0]);
        }
    }

    /* renamed from: a */
    protected Bitmap m10817a(Void... voidArr) {
        InputStream a = Network.m10482a(this.f7137a, this.f7138b).m10825a(this.f7139c);
        Options options = new Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        return BitmapFactory.decodeStream(a, null, options);
    }

    /* renamed from: a */
    protected void m10818a(Bitmap bitmap) {
        if (bitmap != null && this.f7140d != null) {
            this.f7140d.mo3275a(bitmap);
        }
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return m10817a((Void[]) objArr);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        m10818a((Bitmap) obj);
    }
}
