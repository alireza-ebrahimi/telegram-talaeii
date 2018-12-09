package org.telegram.news;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.C0235a;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.C0675b;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.util.C2868b;
import org.telegram.customization.util.view.Poll.C2893a;
import org.telegram.customization.util.view.Poll.C2896c;
import org.telegram.customization.util.view.Poll.PollView;
import org.telegram.messenger.exoplayer2.C3446C;

/* renamed from: org.telegram.news.b */
public class C3747b extends FrameLayout implements C0675b, C2497d {
    /* renamed from: a */
    Activity f9998a;
    /* renamed from: b */
    View f9999b;
    /* renamed from: c */
    TextView f10000c;
    /* renamed from: d */
    private View f10001d;
    /* renamed from: e */
    private SwipeRefreshLayout f10002e;
    /* renamed from: f */
    private PollView f10003f;
    /* renamed from: g */
    private TextView f10004g;
    /* renamed from: h */
    private C2893a f10005h;
    /* renamed from: i */
    private ImageView f10006i;

    /* renamed from: org.telegram.news.b$1 */
    class C37421 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C3747b f9959a;

        C37421(C3747b c3747b) {
            this.f9959a = c3747b;
        }

        public void run() {
            this.f9959a.f10005h.m13410a(1, this.f9959a.f10003f);
        }
    }

    /* renamed from: a */
    private void m13811a(int i) {
        this.f9999b.setVisibility(8);
        this.f10001d.setVisibility(0);
        TextView textView = (TextView) this.f10001d.findViewById(R.id.txt_error);
        ImageView imageView = (ImageView) this.f10001d.findViewById(R.id.iv_error);
        m13815d();
        switch (i) {
            case -3000:
                textView.setText(getResources().getString(R.string.err_empty));
                imageView.setImageDrawable(C0235a.m1066a(this.f9998a, (int) R.drawable.sad));
                break;
            case -2000:
                textView.setText(getResources().getString(R.string.err_get_data));
                imageView.setImageDrawable(C0235a.m1066a(this.f9998a, (int) R.drawable.sad));
                break;
            case C3446C.PRIORITY_DOWNLOAD /*-1000*/:
                textView.setText(getResources().getString(R.string.network_error));
                imageView.setImageDrawable(C0235a.m1066a(this.f9998a, (int) R.drawable.wifi_off));
                break;
        }
        this.f10002e.setEnabled(true);
    }

    /* renamed from: b */
    private void m13813b() {
        m13814c();
        m13816e();
        C2818c.m13087a(getContext(), (C2497d) this).m13131d(TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: c */
    private void m13814c() {
        this.f10002e.setRefreshing(true);
    }

    /* renamed from: d */
    private void m13815d() {
        this.f10002e.setRefreshing(false);
    }

    /* renamed from: e */
    private void m13816e() {
        this.f10001d.setVisibility(8);
    }

    public void j_() {
        m13813b();
    }

    public void onResult(Object obj, int i) {
        m13815d();
        switch (i) {
            case -13:
                m13811a(i);
                return;
            case 13:
                C2896c c2896c = (C2896c) obj;
                this.f10004g.setText(c2896c.m13422c() + TtmlNode.ANONYMOUS_REGION_ID);
                this.f10000c.setText(c2896c.m13420a());
                C2868b.m13329a(this.f10006i, c2896c.m13421b());
                ArrayList arrayList = new ArrayList();
                this.f10005h = new C2893a(getContext(), c2896c.m13423d(), Color.rgb(12, 89, 153), Color.rgb(12, 89, 153), Color.rgb(33, 33, 33));
                this.f10003f.setAdapter(this.f10005h);
                new Handler().postDelayed(new C37421(this), 500);
                return;
            default:
                return;
        }
    }
}
