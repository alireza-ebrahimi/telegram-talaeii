package android.support.design.widget;

import android.support.v7.widget.C0193m;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

/* renamed from: android.support.design.widget.u */
public class C0194u extends C0193m {
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
        if (onCreateInputConnection != null && editorInfo.hintText == null) {
            for (ViewParent parent = getParent(); parent instanceof View; parent = parent.getParent()) {
                if (parent instanceof TextInputLayout) {
                    editorInfo.hintText = ((TextInputLayout) parent).getHint();
                    break;
                }
            }
        }
        return onCreateInputConnection;
    }
}
