package android.support.design.widget;

import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

@TargetApi(11)
class aa {
    /* renamed from: a */
    private static final ThreadLocal<Matrix> f431a = new ThreadLocal();
    /* renamed from: b */
    private static final ThreadLocal<RectF> f432b = new ThreadLocal();

    /* renamed from: a */
    public static void m649a(ViewGroup viewGroup, View view, Rect rect) {
        Matrix matrix;
        Matrix matrix2 = (Matrix) f431a.get();
        if (matrix2 == null) {
            matrix2 = new Matrix();
            f431a.set(matrix2);
            matrix = matrix2;
        } else {
            matrix2.reset();
            matrix = matrix2;
        }
        m650a((ViewParent) viewGroup, view, matrix);
        RectF rectF = (RectF) f432b.get();
        if (rectF == null) {
            rectF = new RectF();
            f432b.set(rectF);
        }
        rectF.set(rect);
        matrix.mapRect(rectF);
        rect.set((int) (rectF.left + 0.5f), (int) (rectF.top + 0.5f), (int) (rectF.right + 0.5f), (int) (rectF.bottom + 0.5f));
    }

    /* renamed from: a */
    static void m650a(ViewParent viewParent, View view, Matrix matrix) {
        ViewParent parent = view.getParent();
        if ((parent instanceof View) && parent != viewParent) {
            View view2 = (View) parent;
            m650a(viewParent, view2, matrix);
            matrix.preTranslate((float) (-view2.getScrollX()), (float) (-view2.getScrollY()));
        }
        matrix.preTranslate((float) view.getLeft(), (float) view.getTop());
        if (!view.getMatrix().isIdentity()) {
            matrix.preConcat(view.getMatrix());
        }
    }
}
