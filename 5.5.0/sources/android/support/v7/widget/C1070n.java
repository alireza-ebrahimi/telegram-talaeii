package android.support.v7.widget;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.support.v7.p027c.p028a.C0825b;
import android.widget.ImageView;

/* renamed from: android.support.v7.widget.n */
public class C1070n {
    /* renamed from: a */
    private final ImageView f3180a;

    public C1070n(ImageView imageView) {
        this.f3180a = imageView;
    }

    /* renamed from: a */
    public void m5888a(int i) {
        if (i != 0) {
            Drawable b = C0825b.m3939b(this.f3180a.getContext(), i);
            if (b != null) {
                ai.m5431a(b);
            }
            this.f3180a.setImageDrawable(b);
            return;
        }
        this.f3180a.setImageDrawable(null);
    }

    /* renamed from: a */
    public void m5889a(android.util.AttributeSet r7, int r8) {
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
        r6 = this;
        r5 = -1;
        r1 = 0;
        r0 = r6.f3180a;	 Catch:{ all -> 0x003c }
        r0 = r0.getDrawable();	 Catch:{ all -> 0x003c }
        if (r0 != 0) goto L_0x0031;	 Catch:{ all -> 0x003c }
    L_0x000a:
        r2 = r6.f3180a;	 Catch:{ all -> 0x003c }
        r2 = r2.getContext();	 Catch:{ all -> 0x003c }
        r3 = android.support.v7.p025a.C0748a.C0747j.AppCompatImageView;	 Catch:{ all -> 0x003c }
        r4 = 0;	 Catch:{ all -> 0x003c }
        r1 = android.support.v7.widget.bk.m5654a(r2, r7, r3, r8, r4);	 Catch:{ all -> 0x003c }
        r2 = android.support.v7.p025a.C0748a.C0747j.AppCompatImageView_srcCompat;	 Catch:{ all -> 0x003c }
        r3 = -1;	 Catch:{ all -> 0x003c }
        r2 = r1.m5670g(r2, r3);	 Catch:{ all -> 0x003c }
        if (r2 == r5) goto L_0x0031;	 Catch:{ all -> 0x003c }
    L_0x0020:
        r0 = r6.f3180a;	 Catch:{ all -> 0x003c }
        r0 = r0.getContext();	 Catch:{ all -> 0x003c }
        r0 = android.support.v7.p027c.p028a.C0825b.m3939b(r0, r2);	 Catch:{ all -> 0x003c }
        if (r0 == 0) goto L_0x0031;	 Catch:{ all -> 0x003c }
    L_0x002c:
        r2 = r6.f3180a;	 Catch:{ all -> 0x003c }
        r2.setImageDrawable(r0);	 Catch:{ all -> 0x003c }
    L_0x0031:
        if (r0 == 0) goto L_0x0036;	 Catch:{ all -> 0x003c }
    L_0x0033:
        android.support.v7.widget.ai.m5431a(r0);	 Catch:{ all -> 0x003c }
    L_0x0036:
        if (r1 == 0) goto L_0x003b;
    L_0x0038:
        r1.m5658a();
    L_0x003b:
        return;
    L_0x003c:
        r0 = move-exception;
        if (r1 == 0) goto L_0x0042;
    L_0x003f:
        r1.m5658a();
    L_0x0042:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.n.a(android.util.AttributeSet, int):void");
    }

    /* renamed from: a */
    boolean m5890a() {
        return VERSION.SDK_INT < 21 || !(this.f3180a.getBackground() instanceof RippleDrawable);
    }
}
