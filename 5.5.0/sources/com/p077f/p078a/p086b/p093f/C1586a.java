package com.p077f.p078a.p086b.p093f;

import android.graphics.Bitmap;
import android.view.View;
import com.p077f.p078a.p086b.p087a.C1550b;

/* renamed from: com.f.a.b.f.a */
public interface C1586a {
    void onLoadingCancelled(String str, View view);

    void onLoadingComplete(String str, View view, Bitmap bitmap);

    void onLoadingFailed(String str, View view, C1550b c1550b);

    void onLoadingStarted(String str, View view);
}
