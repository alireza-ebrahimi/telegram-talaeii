package org.telegram.customization.util.view.Poll;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import utils.view.ExpandedGridView;

public class PollView extends ExpandedGridView {
    public PollView(Context context) {
        super(context);
        init();
    }

    public PollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(11)
    public PollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        setNumColumns(1);
        setStretchMode(2);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
    }
}
