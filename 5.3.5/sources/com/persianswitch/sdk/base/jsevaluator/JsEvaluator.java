package com.persianswitch.sdk.base.jsevaluator;

import android.content.Context;
import android.webkit.WebView;
import com.persianswitch.sdk.base.jsevaluator.interfaces.CallJavaResultInterface;
import com.persianswitch.sdk.base.jsevaluator.interfaces.HandlerWrapperInterface;
import com.persianswitch.sdk.base.jsevaluator.interfaces.JsCallback;
import com.persianswitch.sdk.base.jsevaluator.interfaces.JsEvaluatorInterface;
import com.persianswitch.sdk.base.jsevaluator.interfaces.WebViewWrapperInterface;
import java.util.ArrayList;

public class JsEvaluator implements CallJavaResultInterface, JsEvaluatorInterface {
    public static final String JS_NAMESPACE = "evgeniiJsEvaluator";
    private final Context mContext;
    private HandlerWrapperInterface mHandler;
    private final ArrayList<JsCallback> mResultCallbacks = new ArrayList();
    protected WebViewWrapperInterface mWebViewWrapper;

    public static String escapeCarriageReturn(String str) {
        return str.replace("\r", "\\r");
    }

    public static String escapeClosingScript(String str) {
        return str.replace("</", "<\\/");
    }

    public static String escapeNewLines(String str) {
        return str.replace(LogCollector.LINE_SEPARATOR, "\\n");
    }

    public static String escapeSingleQuotes(String str) {
        return str.replace("'", "\\'");
    }

    public static String escapeSlash(String str) {
        return str.replace("\\", "\\\\");
    }

    public static String getJsForEval(String jsCode, int callbackIndex) {
        jsCode = escapeCarriageReturn(escapeNewLines(escapeClosingScript(escapeSingleQuotes(escapeSlash(jsCode)))));
        return String.format("%s.returnResultToJava(eval('%s'), %s);", new Object[]{JS_NAMESPACE, jsCode, Integer.valueOf(callbackIndex)});
    }

    public JsEvaluator(Context context) {
        this.mContext = context;
        this.mHandler = new HandlerWrapper();
    }

    public void callFunction(String jsCode, JsCallback resultCallback, String name, Object... args) {
        evaluate(jsCode + "; " + JsFunctionCallFormatter.toString(name, args), resultCallback);
    }

    public void evaluate(String jsCode) {
        evaluate(jsCode, null);
    }

    public void evaluate(String jsCode, JsCallback resultCallback) {
        int callbackIndex = this.mResultCallbacks.size();
        if (resultCallback == null) {
            callbackIndex = -1;
        }
        String js = getJsForEval(jsCode, callbackIndex);
        if (resultCallback != null) {
            this.mResultCallbacks.add(resultCallback);
        }
        getWebViewWrapper().loadJavaScript(js);
    }

    public void destroy() {
        getWebViewWrapper().destroy();
    }

    public WebView getWebView() {
        return getWebViewWrapper().getWebView();
    }

    public ArrayList<JsCallback> getResultCallbacks() {
        return this.mResultCallbacks;
    }

    public WebViewWrapperInterface getWebViewWrapper() {
        if (this.mWebViewWrapper == null) {
            this.mWebViewWrapper = new WebViewWrapper(this.mContext, this);
        }
        return this.mWebViewWrapper;
    }

    public void jsCallFinished(final String value, Integer callIndex) {
        if (callIndex.intValue() != -1) {
            final JsCallback callback = (JsCallback) this.mResultCallbacks.get(callIndex.intValue());
            this.mHandler.post(new Runnable() {
                public void run() {
                    callback.onResult(value);
                }
            });
        }
    }

    public void setHandler(HandlerWrapperInterface handlerWrapperInterface) {
        this.mHandler = handlerWrapperInterface;
    }

    public void setWebViewWrapper(WebViewWrapperInterface webViewWrapper) {
        this.mWebViewWrapper = webViewWrapper;
    }
}
