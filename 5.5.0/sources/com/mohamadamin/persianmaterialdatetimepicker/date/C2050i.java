package com.mohamadamin.persianmaterialdatetimepicker.date;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2023b;
import com.mohamadamin.persianmaterialdatetimepicker.C2028b.C2026e;
import com.mohamadamin.persianmaterialdatetimepicker.date.C2036b.C2034a;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2017a;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.i */
public class C2050i extends ListView implements OnItemClickListener, C2034a {
    /* renamed from: a */
    private final C2031a f6071a;
    /* renamed from: b */
    private C2049a f6072b;
    /* renamed from: c */
    private int f6073c;
    /* renamed from: d */
    private int f6074d;
    /* renamed from: e */
    private TextViewWithCircularIndicator f6075e;

    /* renamed from: com.mohamadamin.persianmaterialdatetimepicker.date.i$a */
    private class C2049a extends ArrayAdapter<String> {
        /* renamed from: a */
        final /* synthetic */ C2050i f6070a;

        public C2049a(C2050i c2050i, Context context, int i, List<String> list) {
            this.f6070a = c2050i;
            super(context, i, list);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            TextViewWithCircularIndicator textViewWithCircularIndicator = (TextViewWithCircularIndicator) super.getView(i, view, viewGroup);
            textViewWithCircularIndicator.requestLayout();
            boolean z = this.f6070a.f6071a.mo3061a().f6009a == C2050i.m9214b(textViewWithCircularIndicator);
            textViewWithCircularIndicator.m9119a(z);
            if (z) {
                this.f6070a.f6075e = textViewWithCircularIndicator;
            }
            return textViewWithCircularIndicator;
        }
    }

    public C2050i(Context context, C2031a c2031a) {
        super(context);
        this.f6071a = c2031a;
        this.f6071a.mo3064a((C2034a) this);
        setLayoutParams(new LayoutParams(-1, -2));
        Resources resources = context.getResources();
        this.f6073c = resources.getDimensionPixelOffset(C2023b.mdtp_date_picker_view_animator_height);
        this.f6074d = resources.getDimensionPixelOffset(C2023b.mdtp_year_label_height);
        setVerticalFadingEdgeEnabled(true);
        setFadingEdgeLength(this.f6074d / 3);
        m9213a(context);
        setOnItemClickListener(this);
        setSelector(new StateListDrawable());
        setDividerHeight(0);
        mo3074a();
    }

    /* renamed from: a */
    private void m9213a(Context context) {
        ArrayList arrayList = new ArrayList();
        for (int f = this.f6071a.mo3069f(); f <= this.f6071a.mo3070g(); f++) {
            arrayList.add(String.format("%d", new Object[]{Integer.valueOf(f)}));
        }
        this.f6072b = new C2049a(this, context, C2026e.mdtp_year_label_text_view, C2017a.m9088a(arrayList));
        setAdapter(this.f6072b);
    }

    /* renamed from: b */
    private static int m9214b(TextView textView) {
        return Integer.valueOf(C2017a.m9090b(textView.getText().toString())).intValue();
    }

    /* renamed from: a */
    public void mo3074a() {
        this.f6072b.notifyDataSetChanged();
        m9216a(this.f6071a.mo3061a().f6009a - this.f6071a.mo3069f());
    }

    /* renamed from: a */
    public void m9216a(int i) {
        m9217a(i, (this.f6073c / 2) - (this.f6074d / 2));
    }

    /* renamed from: a */
    public void m9217a(final int i, final int i2) {
        post(new Runnable(this) {
            /* renamed from: c */
            final /* synthetic */ C2050i f6069c;

            public void run() {
                this.f6069c.setSelectionFromTop(i, i2);
                this.f6069c.requestLayout();
            }
        });
    }

    public int getFirstPositionOffset() {
        View childAt = getChildAt(0);
        return childAt == null ? 0 : childAt.getTop();
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (accessibilityEvent.getEventType() == 4096) {
            accessibilityEvent.setFromIndex(0);
            accessibilityEvent.setToIndex(0);
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.f6071a.mo3073j();
        TextViewWithCircularIndicator textViewWithCircularIndicator = (TextViewWithCircularIndicator) view;
        if (textViewWithCircularIndicator != null) {
            if (textViewWithCircularIndicator != this.f6075e) {
                if (this.f6075e != null) {
                    this.f6075e.m9119a(false);
                    this.f6075e.requestLayout();
                }
                textViewWithCircularIndicator.m9119a(true);
                textViewWithCircularIndicator.requestLayout();
                this.f6075e = textViewWithCircularIndicator;
            }
            this.f6071a.mo3062a(C2050i.m9214b(textViewWithCircularIndicator));
            this.f6072b.notifyDataSetChanged();
        }
    }
}
