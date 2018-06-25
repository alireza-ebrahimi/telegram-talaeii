package net.hockeyapp.android.listeners;

import net.hockeyapp.android.tasks.DownloadFileTask;

public abstract class DownloadFileListener {
    public void downloadFailed(DownloadFileTask task, Boolean userWantsRetry) {
    }

    public void downloadSuccessful(DownloadFileTask task) {
    }
}
