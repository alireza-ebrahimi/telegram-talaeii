package net.hockeyapp.android.tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.Queue;
import net.hockeyapp.android.Constants;
import net.hockeyapp.android.objects.FeedbackAttachment;
import net.hockeyapp.android.utils.AsyncTaskUtils;
import net.hockeyapp.android.utils.HockeyLog;
import net.hockeyapp.android.utils.ImageUtils;
import net.hockeyapp.android.views.AttachmentView;

public class AttachmentDownloader {
    private boolean downloadRunning;
    private Queue<DownloadJob> queue;

    /* renamed from: net.hockeyapp.android.tasks.AttachmentDownloader$1 */
    class C09771 extends Handler {
        C09771() {
        }

        public void handleMessage(Message msg) {
            final DownloadJob retryCandidate = (DownloadJob) AttachmentDownloader.this.queue.poll();
            if (!retryCandidate.isSuccess() && retryCandidate.consumeRetry()) {
                postDelayed(new Runnable() {
                    public void run() {
                        AttachmentDownloader.this.queue.add(retryCandidate);
                        AttachmentDownloader.this.downloadNext();
                    }
                }, 3000);
            }
            AttachmentDownloader.this.downloadRunning = false;
            AttachmentDownloader.this.downloadNext();
        }
    }

    private static class AttachmentDownloaderHolder {
        public static final AttachmentDownloader INSTANCE = new AttachmentDownloader();

        private AttachmentDownloaderHolder() {
        }
    }

    private static class DownloadJob {
        private final AttachmentView attachmentView;
        private final FeedbackAttachment feedbackAttachment;
        private int remainingRetries;
        private boolean success;

        private DownloadJob(FeedbackAttachment feedbackAttachment, AttachmentView attachmentView) {
            this.feedbackAttachment = feedbackAttachment;
            this.attachmentView = attachmentView;
            this.success = false;
            this.remainingRetries = 2;
        }

        public FeedbackAttachment getFeedbackAttachment() {
            return this.feedbackAttachment;
        }

        public AttachmentView getAttachmentView() {
            return this.attachmentView;
        }

        public boolean isSuccess() {
            return this.success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public boolean hasRetry() {
            return this.remainingRetries > 0;
        }

        public boolean consumeRetry() {
            int i = this.remainingRetries - 1;
            this.remainingRetries = i;
            return i >= 0;
        }
    }

    private static class DownloadTask extends AsyncTask<Void, Integer, Boolean> {
        private Bitmap bitmap = null;
        private int bitmapOrientation = 0;
        private final DownloadJob downloadJob;
        private File dropFolder = Constants.getHockeyAppStorageDir();
        private final Handler handler;

        public DownloadTask(DownloadJob downloadJob, Handler handler) {
            this.downloadJob = downloadJob;
            this.handler = handler;
        }

        protected void onPreExecute() {
        }

        protected Boolean doInBackground(Void... args) {
            FeedbackAttachment attachment = this.downloadJob.getFeedbackAttachment();
            if (attachment.isAvailableInCache()) {
                HockeyLog.error("Cached...");
                loadImageThumbnail();
                return Boolean.valueOf(true);
            }
            HockeyLog.error("Downloading...");
            boolean success = downloadAttachment(attachment.getUrl(), attachment.getCacheId());
            if (success) {
                loadImageThumbnail();
            }
            return Boolean.valueOf(success);
        }

        protected void onProgressUpdate(Integer... values) {
        }

        protected void onPostExecute(Boolean success) {
            AttachmentView attachmentView = this.downloadJob.getAttachmentView();
            this.downloadJob.setSuccess(success.booleanValue());
            if (success.booleanValue()) {
                attachmentView.setImage(this.bitmap, this.bitmapOrientation);
            } else if (!this.downloadJob.hasRetry()) {
                attachmentView.signalImageLoadingError();
            }
            this.handler.sendEmptyMessage(0);
        }

        private void loadImageThumbnail() {
            try {
                String filename = this.downloadJob.getFeedbackAttachment().getCacheId();
                AttachmentView attachmentView = this.downloadJob.getAttachmentView();
                this.bitmapOrientation = ImageUtils.determineOrientation(new File(this.dropFolder, filename));
                this.bitmap = ImageUtils.decodeSampledBitmap(new File(this.dropFolder, filename), this.bitmapOrientation == 1 ? attachmentView.getWidthLandscape() : attachmentView.getWidthPortrait(), this.bitmapOrientation == 1 ? attachmentView.getMaxHeightLandscape() : attachmentView.getMaxHeightPortrait());
            } catch (IOException e) {
                e.printStackTrace();
                this.bitmap = null;
            }
        }

        private boolean downloadAttachment(String urlString, String filename) {
            try {
                URLConnection connection = createConnection(new URL(urlString));
                connection.connect();
                int lengthOfFile = connection.getContentLength();
                String status = connection.getHeaderField("Status");
                if (status != null && !status.startsWith("200")) {
                    return false;
                }
                File file = new File(this.dropFolder, filename);
                InputStream input = new BufferedInputStream(connection.getInputStream());
                OutputStream output = new FileOutputStream(file);
                byte[] data = new byte[1024];
                long total = 0;
                while (true) {
                    int count = input.read(data);
                    if (count == -1) {
                        break;
                    }
                    total += (long) count;
                    publishProgress(new Integer[]{Integer.valueOf((int) ((100 * total) / ((long) lengthOfFile)))});
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
                return total > 0;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        private URLConnection createConnection(URL url) throws IOException {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", Constants.SDK_USER_AGENT);
            connection.setInstanceFollowRedirects(true);
            if (VERSION.SDK_INT <= 9) {
                connection.setRequestProperty("connection", "close");
            }
            return connection;
        }
    }

    public static AttachmentDownloader getInstance() {
        return AttachmentDownloaderHolder.INSTANCE;
    }

    private AttachmentDownloader() {
        this.queue = new LinkedList();
        this.downloadRunning = false;
    }

    public void download(FeedbackAttachment feedbackAttachment, AttachmentView attachmentView) {
        this.queue.add(new DownloadJob(feedbackAttachment, attachmentView));
        downloadNext();
    }

    private void downloadNext() {
        if (!this.downloadRunning) {
            DownloadJob downloadJob = (DownloadJob) this.queue.peek();
            if (downloadJob != null) {
                DownloadTask downloadTask = new DownloadTask(downloadJob, new C09771());
                this.downloadRunning = true;
                AsyncTaskUtils.execute(downloadTask);
            }
        }
    }
}
