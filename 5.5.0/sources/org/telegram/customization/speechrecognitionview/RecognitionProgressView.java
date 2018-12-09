package org.telegram.customization.speechrecognitionview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import org.telegram.customization.speechrecognitionview.p168b.C2844a;
import org.telegram.customization.speechrecognitionview.p168b.C2846c;
import org.telegram.customization.speechrecognitionview.p168b.C2847d;
import org.telegram.customization.speechrecognitionview.p168b.C2848e;
import org.telegram.customization.speechrecognitionview.p168b.C2849f;
import org.telegram.customization.speechrecognitionview.p168b.C2849f.C2841a;

public class RecognitionProgressView extends View implements RecognitionListener {
    /* renamed from: a */
    private static final int[] f9326a = new int[]{60, 46, 70, 54, 64};
    /* renamed from: b */
    private final List<C2843a> f9327b = new ArrayList();
    /* renamed from: c */
    private Paint f9328c;
    /* renamed from: d */
    private C2844a f9329d;
    /* renamed from: e */
    private int f9330e;
    /* renamed from: f */
    private int f9331f;
    /* renamed from: g */
    private int f9332g;
    /* renamed from: h */
    private int f9333h;
    /* renamed from: i */
    private float f9334i;
    /* renamed from: j */
    private boolean f9335j;
    /* renamed from: k */
    private boolean f9336k;
    /* renamed from: l */
    private SpeechRecognizer f9337l;
    /* renamed from: m */
    private RecognitionListener f9338m;
    /* renamed from: n */
    private int f9339n = -1;
    /* renamed from: o */
    private int[] f9340o;
    /* renamed from: p */
    private int[] f9341p;

    /* renamed from: org.telegram.customization.speechrecognitionview.RecognitionProgressView$1 */
    class C28421 implements C2841a {
        /* renamed from: a */
        final /* synthetic */ RecognitionProgressView f9325a;

        C28421(RecognitionProgressView recognitionProgressView) {
            this.f9325a = recognitionProgressView;
        }

        /* renamed from: a */
        public void mo3492a() {
            this.f9325a.m13213j();
        }
    }

    public RecognitionProgressView(Context context) {
        super(context);
        m13206c();
    }

    public RecognitionProgressView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m13206c();
    }

    public RecognitionProgressView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m13206c();
    }

    /* renamed from: c */
    private void m13206c() {
        this.f9328c = new Paint();
        this.f9328c.setFlags(1);
        this.f9328c.setColor(-7829368);
        this.f9334i = getResources().getDisplayMetrics().density;
        this.f9330e = (int) (5.0f * this.f9334i);
        this.f9331f = (int) (11.0f * this.f9334i);
        this.f9332g = (int) (25.0f * this.f9334i);
        this.f9333h = (int) (3.0f * this.f9334i);
        if (this.f9334i <= 1.5f) {
            this.f9333h *= 2;
        }
    }

    /* renamed from: d */
    private void m13207d() {
        List e = m13208e();
        int measuredWidth = ((getMeasuredWidth() / 2) - (this.f9331f * 2)) - (this.f9330e * 4);
        for (int i = 0; i < 5; i++) {
            this.f9327b.add(new C2843a(measuredWidth + (((this.f9330e * 2) + this.f9331f) * i), getMeasuredHeight() / 2, this.f9330e * 2, ((Integer) e.get(i)).intValue(), this.f9330e));
        }
    }

    /* renamed from: e */
    private List<Integer> m13208e() {
        int i = 0;
        List<Integer> arrayList = new ArrayList();
        if (this.f9341p == null) {
            while (i < 5) {
                arrayList.add(Integer.valueOf((int) (((float) f9326a[i]) * this.f9334i)));
                i++;
            }
        } else {
            while (i < 5) {
                arrayList.add(Integer.valueOf((int) (((float) this.f9341p[i]) * this.f9334i)));
                i++;
            }
        }
        return arrayList;
    }

    /* renamed from: f */
    private void m13209f() {
        for (C2843a c2843a : this.f9327b) {
            c2843a.m13217a(c2843a.m13224f());
            c2843a.m13219b(c2843a.m13225g());
            c2843a.m13221c(this.f9330e * 2);
            c2843a.m13216a();
        }
    }

    /* renamed from: g */
    private void m13210g() {
        this.f9329d = new C2846c(this.f9327b, this.f9333h);
        this.f9329d.mo3493a();
    }

    /* renamed from: h */
    private void m13211h() {
        m13209f();
        this.f9329d = new C2847d(this.f9327b);
        this.f9329d.mo3493a();
    }

    /* renamed from: i */
    private void m13212i() {
        m13209f();
        this.f9329d = new C2849f(this.f9327b, getWidth() / 2, getHeight() / 2, this.f9332g);
        this.f9329d.mo3493a();
        ((C2849f) this.f9329d).m13258a(new C28421(this));
    }

    /* renamed from: j */
    private void m13213j() {
        this.f9329d = new C2848e(this.f9327b, getWidth() / 2, getHeight() / 2);
        this.f9329d.mo3493a();
    }

    /* renamed from: a */
    public void m13214a() {
        m13210g();
        this.f9336k = true;
    }

    /* renamed from: b */
    public void m13215b() {
        if (this.f9329d != null) {
            this.f9329d.mo3494b();
            this.f9329d = null;
        }
        this.f9336k = false;
        m13209f();
    }

    public void onBeginningOfSpeech() {
        if (this.f9338m != null) {
            this.f9338m.onBeginningOfSpeech();
        }
        this.f9335j = true;
    }

    public void onBufferReceived(byte[] bArr) {
        if (this.f9338m != null) {
            this.f9338m.onBufferReceived(bArr);
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.f9327b.isEmpty()) {
            if (this.f9336k) {
                this.f9329d.mo3495c();
            }
            for (int i = 0; i < this.f9327b.size(); i++) {
                C2843a c2843a = (C2843a) this.f9327b.get(i);
                if (this.f9340o != null) {
                    this.f9328c.setColor(this.f9340o[i]);
                } else if (this.f9339n != -1) {
                    this.f9328c.setColor(this.f9339n);
                }
                canvas.drawRoundRect(c2843a.m13226h(), (float) this.f9330e, (float) this.f9330e, this.f9328c);
            }
            if (this.f9336k) {
                invalidate();
            }
        }
    }

    public void onEndOfSpeech() {
        if (this.f9338m != null) {
            this.f9338m.onEndOfSpeech();
        }
        this.f9335j = false;
        m13212i();
    }

    public void onError(int i) {
        if (this.f9338m != null) {
            this.f9338m.onError(i);
        }
    }

    public void onEvent(int i, Bundle bundle) {
        if (this.f9338m != null) {
            this.f9338m.onEvent(i, bundle);
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.f9327b.isEmpty()) {
            m13207d();
        } else if (z) {
            this.f9327b.clear();
            m13207d();
        }
    }

    public void onPartialResults(Bundle bundle) {
        if (this.f9338m != null) {
            this.f9338m.onPartialResults(bundle);
        }
    }

    public void onReadyForSpeech(Bundle bundle) {
        if (this.f9338m != null) {
            this.f9338m.onReadyForSpeech(bundle);
        }
    }

    public void onResults(Bundle bundle) {
        if (this.f9338m != null) {
            this.f9338m.onResults(bundle);
        }
    }

    public void onRmsChanged(float f) {
        if (this.f9338m != null) {
            this.f9338m.onRmsChanged(f);
        }
        if (this.f9329d != null && f >= 1.0f) {
            if (!(this.f9329d instanceof C2847d) && this.f9335j) {
                m13211h();
            }
            if (this.f9329d instanceof C2847d) {
                ((C2847d) this.f9329d).m13246a(f);
            }
        }
    }

    public void setBarMaxHeightsInDp(int[] iArr) {
        if (iArr != null) {
            this.f9341p = new int[5];
            if (iArr.length < 5) {
                System.arraycopy(iArr, 0, this.f9341p, 0, iArr.length);
                for (int length = iArr.length; length < 5; length++) {
                    this.f9341p[length] = iArr[0];
                }
                return;
            }
            System.arraycopy(iArr, 0, this.f9341p, 0, 5);
        }
    }

    public void setCircleRadiusInDp(int i) {
        this.f9330e = (int) (((float) i) * this.f9334i);
    }

    public void setColors(int[] iArr) {
        if (iArr != null) {
            this.f9340o = new int[5];
            if (iArr.length < 5) {
                System.arraycopy(iArr, 0, this.f9340o, 0, iArr.length);
                for (int length = iArr.length; length < 5; length++) {
                    this.f9340o[length] = iArr[0];
                }
                return;
            }
            System.arraycopy(iArr, 0, this.f9340o, 0, 5);
        }
    }

    public void setIdleStateAmplitudeInDp(int i) {
        this.f9333h = (int) (((float) i) * this.f9334i);
    }

    public void setRecognitionListener(RecognitionListener recognitionListener) {
        this.f9338m = recognitionListener;
    }

    public void setRotationRadiusInDp(int i) {
        this.f9332g = (int) (((float) i) * this.f9334i);
    }

    public void setSingleColor(int i) {
        this.f9339n = i;
    }

    public void setSpacingInDp(int i) {
        this.f9331f = (int) (((float) i) * this.f9334i);
    }

    public void setSpeechRecognizer(SpeechRecognizer speechRecognizer) {
        this.f9337l = speechRecognizer;
        this.f9337l.setRecognitionListener(this);
    }
}
