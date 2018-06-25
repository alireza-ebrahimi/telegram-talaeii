package org.telegram.customization.util;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.messenger.C3336R;
import org.telegram.ui.ActionBar.Theme;

public class DownloadProgressView extends LinearLayout {
    /* renamed from: a */
    private final ProgressBar f9384a;
    /* renamed from: b */
    private final TextView f9385b;
    /* renamed from: c */
    private final TextView f9386c;
    /* renamed from: d */
    private final TextView f9387d;
    /* renamed from: e */
    private final Button f9388e;
    /* renamed from: f */
    private final DownloadManager f9389f;
    /* renamed from: g */
    private final Context f9390g;
    /* renamed from: h */
    private int f9391h;
    /* renamed from: i */
    private int f9392i;
    /* renamed from: j */
    private int f9393j;
    /* renamed from: k */
    private long f9394k;
    /* renamed from: l */
    private boolean f9395l;
    /* renamed from: m */
    private List<C2513a> f9396m = new ArrayList();

    /* renamed from: org.telegram.customization.util.DownloadProgressView$a */
    public interface C2513a {
        /* renamed from: a */
        void mo3420a();

        /* renamed from: a */
        void mo3421a(int i);

        /* renamed from: a */
        void mo3422a(String str);
    }

    /* renamed from: org.telegram.customization.util.DownloadProgressView$1 */
    class C28501 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ DownloadProgressView f9375a;

        C28501(DownloadProgressView downloadProgressView) {
            this.f9375a = downloadProgressView;
        }

        public void onClick(View view) {
            if (this.f9375a.f9389f != null) {
                this.f9375a.f9389f.remove(new long[]{this.f9375a.f9394k});
                try {
                    for (C2513a a : this.f9375a.f9396m) {
                        a.mo3420a();
                    }
                } catch (Exception e) {
                }
            }
            this.f9375a.setVisibility(8);
        }
    }

    /* renamed from: org.telegram.customization.util.DownloadProgressView$2 */
    class C28532 extends Thread {
        /* renamed from: a */
        final /* synthetic */ DownloadProgressView f9383a;

        /* renamed from: org.telegram.customization.util.DownloadProgressView$2$2 */
        class C28522 implements Runnable {
            /* renamed from: a */
            final /* synthetic */ C28532 f9382a;

            C28522(C28532 c28532) {
                this.f9382a = c28532;
            }

            public void run() {
                this.f9382a.f9383a.f9395l = false;
                this.f9382a.f9383a.setVisibility(8);
                try {
                    for (C2513a a : this.f9382a.f9383a.f9396m) {
                        a.mo3421a(-1);
                    }
                } catch (Exception e) {
                }
            }
        }

        C28532(DownloadProgressView downloadProgressView) {
            this.f9383a = downloadProgressView;
        }

        public void run() {
            do {
                this.f9383a.f9395l = true;
                Query query = new Query();
                query.setFilterById(new long[]{this.f9383a.f9394k});
                Cursor query2 = this.f9383a.f9389f.query(query);
                if (query2.moveToFirst()) {
                    final int i = query2.getInt(query2.getColumnIndex("status"));
                    final int i2 = query2.getInt(query2.getColumnIndex("reason"));
                    final long j = (long) query2.getInt(query2.getColumnIndex("bytes_so_far"));
                    final long j2 = (long) query2.getInt(query2.getColumnIndex("total_size"));
                    final long j3 = j2 > 0 ? (100 * j) / j2 : 0;
                    new Handler(Looper.getMainLooper()).post(new Runnable(this) {
                        /* renamed from: f */
                        final /* synthetic */ C28532 f9381f;

                        public void run() {
                            if (i == 2) {
                                this.f9381f.f9383a.f9395l = true;
                                this.f9381f.f9383a.f9384a.setIndeterminate(false);
                                this.f9381f.f9383a.f9385b.setText(String.format("%.3fMB", new Object[]{Double.valueOf(((((double) j) * 1.0d) / 1024.0d) / 1024.0d)}));
                                this.f9381f.f9383a.f9386c.setText(String.format("%.2fMB", new Object[]{Double.valueOf(((((double) j2) * 1.0d) / 1024.0d) / 1024.0d)}));
                                this.f9381f.f9383a.f9387d.setText(((int) j3) + "%");
                                this.f9381f.f9383a.f9384a.setProgress((int) j3);
                            } else if (i == 16) {
                                this.f9381f.f9383a.f9395l = false;
                                this.f9381f.f9383a.setVisibility(8);
                                try {
                                    for (C2513a a : this.f9381f.f9383a.f9396m) {
                                        a.mo3421a(i2);
                                    }
                                } catch (Exception e) {
                                }
                            } else if (i == 8) {
                                this.f9381f.f9383a.f9395l = false;
                                this.f9381f.f9383a.setVisibility(8);
                                try {
                                    Query query = new Query();
                                    query.setFilterById(new long[]{this.f9381f.f9383a.f9394k});
                                    Cursor query2 = this.f9381f.f9383a.f9389f.query(query);
                                    if (query2.moveToFirst()) {
                                        String string = query2.getString(query2.getColumnIndex("local_uri"));
                                        string = string != null ? new File(Uri.parse(string).getPath()).getAbsolutePath() : null;
                                        for (C2513a a2 : this.f9381f.f9383a.f9396m) {
                                            a2.mo3422a(string);
                                        }
                                        this.f9381f.f9383a.f9396m = null;
                                    }
                                    query2.close();
                                } catch (Exception e2) {
                                }
                            } else {
                                this.f9381f.f9383a.f9395l = true;
                                this.f9381f.f9383a.f9385b.setText(TtmlNode.ANONYMOUS_REGION_ID);
                                this.f9381f.f9383a.f9386c.setText(TtmlNode.ANONYMOUS_REGION_ID);
                                this.f9381f.f9383a.f9387d.setText(TtmlNode.ANONYMOUS_REGION_ID);
                                this.f9381f.f9383a.f9384a.setIndeterminate(true);
                            }
                        }
                    });
                } else {
                    new Handler(Looper.getMainLooper()).post(new C28522(this));
                }
                query2.close();
            } while (this.f9383a.f9395l);
        }
    }

    public DownloadProgressView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f9390g = context;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C3336R.styleable.DownloadProgressView, 0, 0);
        try {
            this.f9391h = obtainStyledAttributes.getColor(2, Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
            this.f9392i = obtainStyledAttributes.getColor(1, Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
            this.f9393j = obtainStyledAttributes.getColor(1, Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
            setOrientation(1);
            setGravity(17);
            ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.download_progres_view, this, true);
            this.f9389f = (DownloadManager) context.getSystemService("download");
            this.f9385b = (TextView) findViewById(R.id.downloadedSize);
            this.f9386c = (TextView) findViewById(R.id.totalSize);
            this.f9387d = (TextView) findViewById(R.id.percentage);
            this.f9384a = (ProgressBar) findViewById(R.id.downloadProgressBar);
            this.f9385b.setTextColor(ColorStateList.valueOf(this.f9393j));
            this.f9386c.setTextColor(ColorStateList.valueOf(this.f9393j));
            this.f9387d.setTextColor(ColorStateList.valueOf(this.f9393j));
            this.f9388e = (Button) findViewById(R.id.cancelButton);
            this.f9388e.setOnClickListener(new C28501(this));
            setVisibility(8);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* renamed from: a */
    private void m13263a() {
        setVisibility(0);
        new C28532(this).start();
    }

    /* renamed from: a */
    public void m13272a(long j, C2513a c2513a) {
        this.f9394k = j;
        this.f9396m.add(c2513a);
        m13263a();
    }

    public int getDownloadedSizeColor() {
        return this.f9391h;
    }

    public int getPercentageColor() {
        return this.f9393j;
    }

    public int getTotalSizeColor() {
        return this.f9392i;
    }

    public void setDownloadedSizeColor(int i) {
        this.f9391h = i;
        this.f9385b.setTextColor(ColorStateList.valueOf(this.f9393j));
        invalidate();
        requestLayout();
    }

    public void setPercentageColor(int i) {
        this.f9393j = i;
        this.f9387d.setTextColor(ColorStateList.valueOf(i));
        invalidate();
        requestLayout();
    }

    public void setTotalSizeColor(int i) {
        this.f9392i = i;
        this.f9386c.setTextColor(ColorStateList.valueOf(this.f9393j));
        invalidate();
        requestLayout();
    }
}
