package android.support.v7.widget;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v7.content.res.AppCompatResources;
import android.widget.ImageView;

@RestrictTo({Scope.LIBRARY_GROUP})
public class AppCompatImageHelper {
    private final ImageView mView;

    public void loadFromAttributes(android.util.AttributeSet r8, int r9) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x003b in list [B:12:0x0038]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
        /*
        r7 = this;
        r6 = -1;
        r0 = 0;
        r3 = r7.mView;	 Catch:{ all -> 0x003c }
        r1 = r3.getDrawable();	 Catch:{ all -> 0x003c }
        if (r1 != 0) goto L_0x0031;	 Catch:{ all -> 0x003c }
    L_0x000a:
        r3 = r7.mView;	 Catch:{ all -> 0x003c }
        r3 = r3.getContext();	 Catch:{ all -> 0x003c }
        r4 = android.support.v7.appcompat.C0299R.styleable.AppCompatImageView;	 Catch:{ all -> 0x003c }
        r5 = 0;	 Catch:{ all -> 0x003c }
        r0 = android.support.v7.widget.TintTypedArray.obtainStyledAttributes(r3, r8, r4, r9, r5);	 Catch:{ all -> 0x003c }
        r3 = android.support.v7.appcompat.C0299R.styleable.AppCompatImageView_srcCompat;	 Catch:{ all -> 0x003c }
        r4 = -1;	 Catch:{ all -> 0x003c }
        r2 = r0.getResourceId(r3, r4);	 Catch:{ all -> 0x003c }
        if (r2 == r6) goto L_0x0031;	 Catch:{ all -> 0x003c }
    L_0x0020:
        r3 = r7.mView;	 Catch:{ all -> 0x003c }
        r3 = r3.getContext();	 Catch:{ all -> 0x003c }
        r1 = android.support.v7.content.res.AppCompatResources.getDrawable(r3, r2);	 Catch:{ all -> 0x003c }
        if (r1 == 0) goto L_0x0031;	 Catch:{ all -> 0x003c }
    L_0x002c:
        r3 = r7.mView;	 Catch:{ all -> 0x003c }
        r3.setImageDrawable(r1);	 Catch:{ all -> 0x003c }
    L_0x0031:
        if (r1 == 0) goto L_0x0036;	 Catch:{ all -> 0x003c }
    L_0x0033:
        android.support.v7.widget.DrawableUtils.fixDrawable(r1);	 Catch:{ all -> 0x003c }
    L_0x0036:
        if (r0 == 0) goto L_0x003b;
    L_0x0038:
        r0.recycle();
    L_0x003b:
        return;
    L_0x003c:
        r3 = move-exception;
        if (r0 == 0) goto L_0x0042;
    L_0x003f:
        r0.recycle();
    L_0x0042:
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.AppCompatImageHelper.loadFromAttributes(android.util.AttributeSet, int):void");
    }

    public AppCompatImageHelper(ImageView view) {
        this.mView = view;
    }

    public void setImageResource(int resId) {
        if (resId != 0) {
            Drawable d = AppCompatResources.getDrawable(this.mView.getContext(), resId);
            if (d != null) {
                DrawableUtils.fixDrawable(d);
            }
            this.mView.setImageDrawable(d);
            return;
        }
        this.mView.setImageDrawable(null);
    }

    boolean hasOverlappingRendering() {
        Drawable background = this.mView.getBackground();
        if (VERSION.SDK_INT < 21 || !(background instanceof RippleDrawable)) {
            return true;
        }
        return false;
    }
}
