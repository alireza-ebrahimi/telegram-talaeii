package utils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.C0235a;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.C0908i;
import android.support.v7.widget.RecyclerView.C0935g;
import android.support.v7.widget.RecyclerView.C0952s;
import android.view.View;
import org.ir.talaeii.R;

public class SimpleDividerItemDecoration extends C0935g {
    /* renamed from: a */
    private Drawable f10289a;

    public SimpleDividerItemDecoration(Context context) {
        this.f10289a = C0235a.a(context, R.drawable.line_divider);
    }

    /* renamed from: b */
    public void m14176b(Canvas canvas, RecyclerView recyclerView, C0952s c0952s) {
        int paddingLeft = recyclerView.getPaddingLeft();
        int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            C0908i c0908i = (C0908i) childAt.getLayoutParams();
            int bottom = c0908i.bottomMargin + childAt.getBottom();
            this.f10289a.setBounds(paddingLeft, bottom, width, this.f10289a.getIntrinsicHeight() + bottom);
            this.f10289a.draw(canvas);
        }
    }
}
