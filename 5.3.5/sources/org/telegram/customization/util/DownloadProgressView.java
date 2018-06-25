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
import org.telegram.messenger.C0906R;

public class DownloadProgressView extends LinearLayout {
    private final Button cancelButton;
    private final Context context;
    private long downloadID;
    private final DownloadManager downloadManager;
    private final ProgressBar downloadProgressBar;
    private int downloadedSizeColor;
    private final TextView downloadedSizeView;
    private boolean downloading;
    private List<DownloadStatusListener> listeners = new ArrayList();
    private int percentageColor;
    private final TextView percentageView;
    private int totalSizeColor;
    private final TextView totalSizeView;

    public interface DownloadStatusListener {
        void downloadCancelled();

        void downloadFailed(int i);

        void downloadSuccessful(String str);
    }

    /* renamed from: org.telegram.customization.util.DownloadProgressView$1 */
    class C12261 implements OnClickListener {
        C12261() {
        }

        public void onClick(View view) {
            if (DownloadProgressView.this.downloadManager != null) {
                DownloadProgressView.this.downloadManager.remove(new long[]{DownloadProgressView.this.downloadID});
                try {
                    for (DownloadStatusListener downloadStatusListener : DownloadProgressView.this.listeners) {
                        downloadStatusListener.downloadCancelled();
                    }
                } catch (Exception e) {
                }
            }
            DownloadProgressView.this.setVisibility(8);
        }
    }

    /* renamed from: org.telegram.customization.util.DownloadProgressView$2 */
    class C12292 extends Thread {

        /* renamed from: org.telegram.customization.util.DownloadProgressView$2$2 */
        class C12282 implements Runnable {
            C12282() {
            }

            public void run() {
                DownloadProgressView.this.downloading = false;
                DownloadProgressView.this.setVisibility(8);
                try {
                    for (DownloadStatusListener downloadStatusListener : DownloadProgressView.this.listeners) {
                        downloadStatusListener.downloadFailed(-1);
                    }
                } catch (Exception e) {
                }
            }
        }

        C12292() {
        }

        public void run() {
            do {
                DownloadProgressView.this.downloading = true;
                Query query = new Query();
                query.setFilterById(new long[]{DownloadProgressView.this.downloadID});
                Cursor c = DownloadProgressView.this.downloadManager.query(query);
                if (c.moveToFirst()) {
                    long download_percentage;
                    final int status = c.getInt(c.getColumnIndex("status"));
                    final int reason = c.getInt(c.getColumnIndex("reason"));
                    final long bytes_downloaded = (long) c.getInt(c.getColumnIndex("bytes_so_far"));
                    final long bytes_total = (long) c.getInt(c.getColumnIndex("total_size"));
                    if (bytes_total > 0) {
                        download_percentage = (100 * bytes_downloaded) / bytes_total;
                    } else {
                        download_percentage = 0;
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            if (status == 2) {
                                DownloadProgressView.this.downloading = true;
                                DownloadProgressView.this.downloadProgressBar.setIndeterminate(false);
                                DownloadProgressView.this.downloadedSizeView.setText(String.format("%.3fMB", new Object[]{Double.valueOf(((((double) bytes_downloaded) * 1.0d) / 1024.0d) / 1024.0d)}));
                                DownloadProgressView.this.totalSizeView.setText(String.format("%.2fMB", new Object[]{Double.valueOf(((((double) bytes_total) * 1.0d) / 1024.0d) / 1024.0d)}));
                                DownloadProgressView.this.percentageView.setText(((int) download_percentage) + "%");
                                DownloadProgressView.this.downloadProgressBar.setProgress((int) download_percentage);
                            } else if (status == 16) {
                                DownloadProgressView.this.downloading = false;
                                DownloadProgressView.this.setVisibility(8);
                                try {
                                    for (DownloadStatusListener downloadStatusListener : DownloadProgressView.this.listeners) {
                                        downloadStatusListener.downloadFailed(reason);
                                    }
                                } catch (Exception e) {
                                }
                            } else if (status == 8) {
                                DownloadProgressView.this.downloading = false;
                                DownloadProgressView.this.setVisibility(8);
                                try {
                                    Query query = new Query();
                                    query.setFilterById(new long[]{DownloadProgressView.this.downloadID});
                                    Cursor cursor = DownloadProgressView.this.downloadManager.query(query);
                                    if (cursor.moveToFirst()) {
                                        String downloadFilePath = null;
                                        String downloadFileLocalUri = cursor.getString(cursor.getColumnIndex("local_uri"));
                                        if (downloadFileLocalUri != null) {
                                            downloadFilePath = new File(Uri.parse(downloadFileLocalUri).getPath()).getAbsolutePath();
                                        }
                                        for (DownloadStatusListener downloadStatusListener2 : DownloadProgressView.this.listeners) {
                                            downloadStatusListener2.downloadSuccessful(downloadFilePath);
                                        }
                                        DownloadProgressView.this.listeners = null;
                                    }
                                    cursor.close();
                                } catch (Exception e2) {
                                }
                            } else {
                                DownloadProgressView.this.downloading = true;
                                DownloadProgressView.this.downloadedSizeView.setText("");
                                DownloadProgressView.this.totalSizeView.setText("");
                                DownloadProgressView.this.percentageView.setText("");
                                DownloadProgressView.this.downloadProgressBar.setIndeterminate(true);
                            }
                        }
                    });
                } else {
                    new Handler(Looper.getMainLooper()).post(new C12282());
                }
                c.close();
            } while (DownloadProgressView.this.downloading);
        }
    }

    public DownloadProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, C0906R.styleable.DownloadProgressView, 0, 0);
        try {
            this.downloadedSizeColor = typedArray.getColor(2, -16777216);
            this.totalSizeColor = typedArray.getColor(1, -16777216);
            this.percentageColor = typedArray.getColor(1, -16777216);
            setOrientation(1);
            setGravity(17);
            ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.download_progres_view, this, true);
            this.downloadManager = (DownloadManager) context.getSystemService("download");
            this.downloadedSizeView = (TextView) findViewById(R.id.downloadedSize);
            this.totalSizeView = (TextView) findViewById(R.id.totalSize);
            this.percentageView = (TextView) findViewById(R.id.percentage);
            this.downloadProgressBar = (ProgressBar) findViewById(R.id.downloadProgressBar);
            this.downloadedSizeView.setTextColor(ColorStateList.valueOf(this.percentageColor));
            this.totalSizeView.setTextColor(ColorStateList.valueOf(this.percentageColor));
            this.percentageView.setTextColor(ColorStateList.valueOf(this.percentageColor));
            this.cancelButton = (Button) findViewById(R.id.cancelButton);
            this.cancelButton.setOnClickListener(new C12261());
            setVisibility(8);
        } finally {
            typedArray.recycle();
        }
    }

    public int getDownloadedSizeColor() {
        return this.downloadedSizeColor;
    }

    public void setDownloadedSizeColor(int downloadedSizeColor) {
        this.downloadedSizeColor = downloadedSizeColor;
        this.downloadedSizeView.setTextColor(ColorStateList.valueOf(this.percentageColor));
        invalidate();
        requestLayout();
    }

    public int getTotalSizeColor() {
        return this.totalSizeColor;
    }

    public void setTotalSizeColor(int totalSizeColor) {
        this.totalSizeColor = totalSizeColor;
        this.totalSizeView.setTextColor(ColorStateList.valueOf(this.percentageColor));
        invalidate();
        requestLayout();
    }

    public int getPercentageColor() {
        return this.percentageColor;
    }

    public void setPercentageColor(int percentageColor) {
        this.percentageColor = percentageColor;
        this.percentageView.setTextColor(ColorStateList.valueOf(percentageColor));
        invalidate();
        requestLayout();
    }

    public void show(long downloadID, DownloadStatusListener downloadStatusListener) {
        this.downloadID = downloadID;
        this.listeners.add(downloadStatusListener);
        showDownloadProgress();
    }

    private void showDownloadProgress() {
        setVisibility(0);
        new C12292().start();
    }
}
