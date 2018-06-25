package com.p077f.p078a.p086b.p091d;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore.Video.Thumbnails;
import android.webkit.MimeTypeMap;
import com.p077f.p078a.p086b.p087a.C1548a;
import com.p077f.p078a.p086b.p091d.C1572b.C1574a;
import com.p077f.p078a.p095c.C1601b;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.TLRPC;

/* renamed from: com.f.a.b.d.a */
public class C1573a implements C1572b {
    /* renamed from: a */
    protected final Context f4788a;
    /* renamed from: b */
    protected final int f4789b;
    /* renamed from: c */
    protected final int f4790c;

    public C1573a(Context context) {
        this(context, DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS, 20000);
    }

    public C1573a(Context context, int i, int i2) {
        this.f4788a = context.getApplicationContext();
        this.f4789b = i;
        this.f4790c = i2;
    }

    @TargetApi(8)
    /* renamed from: a */
    private InputStream m7789a(String str) {
        if (VERSION.SDK_INT >= 8) {
            Bitmap createVideoThumbnail = ThumbnailUtils.createVideoThumbnail(str, 2);
            if (createVideoThumbnail != null) {
                OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                createVideoThumbnail.compress(CompressFormat.PNG, 0, byteArrayOutputStream);
                return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            }
        }
        return null;
    }

    /* renamed from: b */
    private boolean m7790b(Uri uri) {
        String type = this.f4788a.getContentResolver().getType(uri);
        return type != null && type.startsWith("video/");
    }

    /* renamed from: b */
    private boolean m7791b(String str) {
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(str));
        return mimeTypeFromExtension != null && mimeTypeFromExtension.startsWith("video/");
    }

    @TargetApi(14)
    /* renamed from: a */
    protected InputStream m7792a(Uri uri) {
        ContentResolver contentResolver = this.f4788a.getContentResolver();
        return VERSION.SDK_INT >= 14 ? Contacts.openContactPhotoInputStream(contentResolver, uri, true) : Contacts.openContactPhotoInputStream(contentResolver, uri);
    }

    /* renamed from: a */
    public InputStream mo1227a(String str, Object obj) {
        switch (C1574a.m7802a(str)) {
            case HTTP:
            case HTTPS:
                return m7795b(str, obj);
            case FILE:
                return m7797d(str, obj);
            case CONTENT:
                return m7798e(str, obj);
            case ASSETS:
                return m7799f(str, obj);
            case DRAWABLE:
                return m7800g(str, obj);
            default:
                return m7801h(str, obj);
        }
    }

    /* renamed from: a */
    protected boolean m7794a(HttpURLConnection httpURLConnection) {
        return httpURLConnection.getResponseCode() == Callback.DEFAULT_DRAG_ANIMATION_DURATION;
    }

    /* renamed from: b */
    protected InputStream m7795b(String str, Object obj) {
        HttpURLConnection c = m7796c(str, obj);
        int i = 0;
        while (c.getResponseCode() / 100 == 3 && i < 5) {
            c = m7796c(c.getHeaderField("Location"), obj);
            i++;
        }
        try {
            Closeable inputStream = c.getInputStream();
            if (m7794a(c)) {
                return new C1548a(new BufferedInputStream(inputStream, TLRPC.MESSAGE_FLAG_EDITED), c.getContentLength());
            }
            C1601b.m7930a(inputStream);
            throw new IOException("Image request failed with response code " + c.getResponseCode());
        } catch (IOException e) {
            C1601b.m7931a(c.getErrorStream());
            throw e;
        }
    }

    /* renamed from: c */
    protected HttpURLConnection m7796c(String str, Object obj) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(Uri.encode(str, "@#&=*+-_.,:!?()/~'%")).openConnection();
        httpURLConnection.setConnectTimeout(this.f4789b);
        httpURLConnection.setReadTimeout(this.f4790c);
        return httpURLConnection;
    }

    /* renamed from: d */
    protected InputStream m7797d(String str, Object obj) {
        String c = C1574a.FILE.m7805c(str);
        return m7791b(str) ? m7789a(c) : new C1548a(new BufferedInputStream(new FileInputStream(c), TLRPC.MESSAGE_FLAG_EDITED), (int) new File(c).length());
    }

    /* renamed from: e */
    protected InputStream m7798e(String str, Object obj) {
        ContentResolver contentResolver = this.f4788a.getContentResolver();
        Uri parse = Uri.parse(str);
        if (m7790b(parse)) {
            Bitmap thumbnail = Thumbnails.getThumbnail(contentResolver, Long.valueOf(parse.getLastPathSegment()).longValue(), 1, null);
            if (thumbnail != null) {
                OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumbnail.compress(CompressFormat.PNG, 0, byteArrayOutputStream);
                return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            }
        } else if (str.startsWith("content://com.android.contacts/")) {
            return m7792a(parse);
        }
        return contentResolver.openInputStream(parse);
    }

    /* renamed from: f */
    protected InputStream m7799f(String str, Object obj) {
        return this.f4788a.getAssets().open(C1574a.ASSETS.m7805c(str));
    }

    /* renamed from: g */
    protected InputStream m7800g(String str, Object obj) {
        return this.f4788a.getResources().openRawResource(Integer.parseInt(C1574a.DRAWABLE.m7805c(str)));
    }

    /* renamed from: h */
    protected InputStream m7801h(String str, Object obj) {
        throw new UnsupportedOperationException(String.format("UIL doesn't support scheme(protocol) by default [%s]. You should implement this support yourself (BaseImageDownloader.getStreamFromOtherSource(...))", new Object[]{str}));
    }
}
