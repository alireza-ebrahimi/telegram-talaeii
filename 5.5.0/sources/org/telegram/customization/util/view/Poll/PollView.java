package org.telegram.customization.util.view.Poll;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import utils.view.ExpandedGridView;

public class PollView extends ExpandedGridView {
    public PollView(Context context) {
        super(context);
        m13408a();
    }

    public PollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m13408a();
    }

    @TargetApi(11)
    public PollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m13408a();
    }

    /* renamed from: a */
    protected void m13408a() {
        setNumColumns(1);
        setStretchMode(2);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
    }
}
