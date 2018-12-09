package com.persianswitch.sdk.base.jsevaluator;

import android.content.Context;
import com.persianswitch.sdk.base.jsevaluator.interfaces.CallJavaResultInterface;
import com.persianswitch.sdk.base.jsevaluator.interfaces.HandlerWrapperInterface;
import com.persianswitch.sdk.base.jsevaluator.interfaces.JsCallback;
import com.persianswitch.sdk.base.jsevaluator.interfaces.JsEvaluatorInterface;
import com.persianswitch.sdk.base.jsevaluator.interfaces.WebViewWrapperInterface;
import java.util.ArrayList;

public class JsEvaluator implements CallJavaResultInterface, JsEvaluatorInterface {
    /* renamed from: a */
    protected WebViewWrapperInterface f7061a;
    /* renamed from: b */
    private final Context f7062b;
    /* renamed from: c */
    private final ArrayList<JsCallback> f7063c = new ArrayList();
    /* renamed from: d */
    private HandlerWrapperInterface f7064d;

    public JsEvaluator(Context context) {
        this.f7062b = context;
        this.f7064d = new HandlerWrapper();
    }

    /* renamed from: a */
    public static String m10635a(String str) {
        return str.replace("\r", "\\r");
    }

    /* renamed from: a */
    public static String m10636a(String str, int i) {
        String a = m10635a(m10638c(m10637b(m10639d(m10640e(str)))));
        return String.format("%s.returnResultToJava(eval('%s'), %s);", new Object[]{"evgeniiJsEvaluator", a, Integer.valueOf(i)});
    }

    /* renamed from: b */
    public static String m10637b(String str) {
        return str.replace("</", "<\\/");
    }

    /* renamed from: c */
    public static String m10638c(String str) {
        return str.replace("\n", "\\n");
    }

    /* renamed from: d */
    public static String m10639d(String str) {
        return str.replace("'", "\\'");
    }

    /* renamed from: e */
    public static String m10640e(String str) {
        return str.replace("\\", "\\\\");
    }

    /* renamed from: a */
    public WebViewWrapperInterface m10641a() {
        if (this.f7061a == null) {
            this.f7061a = new WebViewWrapper(this.f7062b, this);
        }
        return this.f7061a;
    }

    /* renamed from: a */
    public void m10642a(String str, JsCallback jsCallback) {
        int size = this.f7063c.size();
        if (jsCallback == null) {
            size = -1;
        }
        String a = m10636a(str, size);
        if (jsCallback != null) {
            this.f7063c.add(jsCallback);
        }
        m10641a().mo3248a(a);
    }

    /* renamed from: a */
    public void m10643a(String str, JsCallback jsCallback, String str2, Object... objArr) {
        m10642a(str + "; " + JsFunctionCallFormatter.m10646a(str2, objArr), jsCallback);
    }

    /* renamed from: a */
    public void mo3247a(final String str, Integer num) {
        if (num.intValue() != -1) {
            final JsCallback jsCallback = (JsCallback) this.f7063c.get(num.intValue());
            this.f7064d.mo3246a(new Runnable(this) {
                /* renamed from: c */
                final /* synthetic */ JsEvaluator f7060c;

                public void run() {
                    jsCallback.mo3345a(str);
                }
            });
        }
    }
}
