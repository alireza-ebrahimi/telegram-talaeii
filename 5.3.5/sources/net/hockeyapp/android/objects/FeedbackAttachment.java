package net.hockeyapp.android.objects;

import com.persianswitch.sdk.base.log.LogCollector;
import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import net.hockeyapp.android.Constants;

public class FeedbackAttachment implements Serializable {
    private static final long serialVersionUID = 5059651319640956830L;
    private String mCreatedAt;
    private String mFilename;
    private int mId;
    private int mMessageId;
    private String mUpdatedAt;
    private String mUrl;

    /* renamed from: net.hockeyapp.android.objects.FeedbackAttachment$1 */
    class C09751 implements FilenameFilter {
        C09751() {
        }

        public boolean accept(File dir, String filename) {
            return filename.equals(FeedbackAttachment.this.getCacheId());
        }
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getMessageId() {
        return this.mMessageId;
    }

    public void setMessageId(int messageId) {
        this.mMessageId = messageId;
    }

    public String getFilename() {
        return this.mFilename;
    }

    public void setFilename(String filename) {
        this.mFilename = filename;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getCreatedAt() {
        return this.mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        this.mCreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return this.mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.mUpdatedAt = updatedAt;
    }

    public String getCacheId() {
        return "" + this.mMessageId + this.mId;
    }

    public boolean isAvailableInCache() {
        File folder = Constants.getHockeyAppStorageDir();
        if (!folder.exists() || !folder.isDirectory()) {
            return false;
        }
        File[] match = folder.listFiles(new C09751());
        if (match == null || match.length != 1) {
            return false;
        }
        return true;
    }

    public String toString() {
        return LogCollector.LINE_SEPARATOR + FeedbackAttachment.class.getSimpleName() + LogCollector.LINE_SEPARATOR + "id         " + this.mId + LogCollector.LINE_SEPARATOR + "message id " + this.mMessageId + LogCollector.LINE_SEPARATOR + "filename   " + this.mFilename + LogCollector.LINE_SEPARATOR + "url        " + this.mUrl + LogCollector.LINE_SEPARATOR + "createdAt  " + this.mCreatedAt + LogCollector.LINE_SEPARATOR + "updatedAt  " + this.mUpdatedAt;
    }
}
