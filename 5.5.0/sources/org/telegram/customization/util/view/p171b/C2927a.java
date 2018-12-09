package org.telegram.customization.util.view.p171b;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import org.telegram.ui.ActionBar.Theme;

/* renamed from: org.telegram.customization.util.view.b.a */
public class C2927a {
    /* renamed from: a */
    public static Bitmap m13545a(Context context, Bitmap bitmap) {
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, Math.round(((float) bitmap.getWidth()) * 0.2f), Math.round(((float) bitmap.getHeight()) * 0.2f), false);
        Bitmap createBitmap = Bitmap.createBitmap(createScaledBitmap);
        RenderScript create = RenderScript.create(context);
        ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
        Allocation createFromBitmap = Allocation.createFromBitmap(create, createScaledBitmap);
        Allocation createFromBitmap2 = Allocation.createFromBitmap(create, createBitmap);
        create2.setRadius(6.0f);
        create2.setInput(createFromBitmap);
        create2.forEach(createFromBitmap2);
        createFromBitmap2.copyTo(createBitmap);
        return C2927a.m13546a(createBitmap);
    }

    /* renamed from: a */
    private static Bitmap m13546a(Bitmap bitmap) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        paint.setColorFilter(new LightingColorFilter(11184810, 0));
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        return bitmap;
    }

    /* renamed from: a */
    public static Bitmap m13547a(View view) {
        return C2927a.m13545a(view.getContext(), C2927a.m13548b(view));
    }

    /* renamed from: b */
    private static Bitmap m13548b(View view) {
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        return createBitmap;
    }
}
