package net.hockeyapp.android.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import net.hockeyapp.android.C2417f.C2413a;
import net.hockeyapp.android.C2417f.C2414b;
import net.hockeyapp.android.C2417f.C2415c;
import net.hockeyapp.android.p135c.C2372c;
import net.hockeyapp.android.p135c.C2373d;
import net.hockeyapp.android.p136d.C2382a;

/* renamed from: net.hockeyapp.android.views.b */
public class C2425b extends LinearLayout {
    @SuppressLint({"SimpleDateFormat"})
    /* renamed from: a */
    private static final SimpleDateFormat f8130a = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    @SuppressLint({"SimpleDateFormat"})
    /* renamed from: b */
    private static final SimpleDateFormat f8131b = new SimpleDateFormat("d MMM h:mm a");
    /* renamed from: c */
    private TextView f8132c = ((TextView) findViewById(C2414b.label_author));
    /* renamed from: d */
    private TextView f8133d = ((TextView) findViewById(C2414b.label_date));
    /* renamed from: e */
    private TextView f8134e = ((TextView) findViewById(C2414b.label_text));
    /* renamed from: f */
    private AttachmentListView f8135f = ((AttachmentListView) findViewById(C2414b.list_attachments));
    /* renamed from: g */
    private C2373d f8136g;
    /* renamed from: h */
    private final Context f8137h;

    public C2425b(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f8137h = context;
        LayoutInflater.from(context).inflate(C2415c.hockeyapp_view_feedback_message, this);
    }

    public void setFeedbackMessage(C2373d c2373d) {
        this.f8136g = c2373d;
        try {
            this.f8133d.setText(f8131b.format(f8130a.parse(this.f8136g.m11751b())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.f8132c.setText(this.f8136g.m11756d());
        this.f8134e.setText(this.f8136g.m11747a());
        this.f8135f.removeAllViews();
        for (C2372c c2372c : this.f8136g.m11758e()) {
            C2424a c2424a = new C2424a(this.f8137h, this.f8135f, c2372c, false);
            C2382a.m11787a().m11791a(c2372c, c2424a);
            this.f8135f.addView(c2424a);
        }
    }

    public void setIndex(int i) {
        if (i % 2 == 0) {
            setBackgroundColor(getResources().getColor(C2413a.hockeyapp_background_light));
            this.f8132c.setTextColor(getResources().getColor(C2413a.hockeyapp_text_white));
            this.f8133d.setTextColor(getResources().getColor(C2413a.hockeyapp_text_white));
        } else {
            setBackgroundColor(getResources().getColor(C2413a.hockeyapp_background_white));
            this.f8132c.setTextColor(getResources().getColor(C2413a.hockeyapp_text_light));
            this.f8133d.setTextColor(getResources().getColor(C2413a.hockeyapp_text_light));
        }
        this.f8134e.setTextColor(getResources().getColor(C2413a.hockeyapp_text_black));
    }
}
