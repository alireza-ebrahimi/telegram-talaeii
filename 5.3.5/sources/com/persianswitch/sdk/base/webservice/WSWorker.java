package com.persianswitch.sdk.base.webservice;

import android.content.Context;
import android.os.AsyncTask;
import com.persianswitch.sdk.base.Config;
import com.persianswitch.sdk.base.Injection.Network;
import com.persianswitch.sdk.base.Injection.ThreadPool;
import com.persianswitch.sdk.base.log.SDKLog;

final class WSWorker extends AsyncTask<Void, Void, HttpResult> {
    private static final String TAG = "WSWorker";
    private final byte[] mAESKey;
    private final Config mConfig;
    private final Context mContext;
    private final WSWorkerListener mListener;
    private final String mRawRequest;
    private final boolean mSilentRetry;
    private final String mUrl;
    private final long mWaitTime;

    public static final class Builder {
        private byte[] mAESKey;
        private Config mConfig;
        private Context mContext;
        private WSWorkerListener mListener;
        private String mRawRequest;
        private boolean mSilentRetry;
        private String mUrl;
        private long mWaitTime;

        public Builder(Context context, Config config) {
            this.mContext = context;
            this.mConfig = config;
        }

        public Builder url(String val) {
            this.mUrl = val;
            return this;
        }

        public Builder rawRequest(String val) {
            this.mRawRequest = val;
            return this;
        }

        public Builder silentRetry(boolean val) {
            this.mSilentRetry = val;
            return this;
        }

        public Builder listener(WSWorkerListener val) {
            this.mListener = val;
            return this;
        }

        public Builder aesKey(byte[] val) {
            this.mAESKey = val;
            return this;
        }

        public Builder waitTime(long waitTime) {
            this.mWaitTime = waitTime;
            return this;
        }

        public WSWorker build() {
            return new WSWorker();
        }
    }

    interface WSWorkerListener {
        void onPreExecute();

        void onResult(HttpResult httpResult);
    }

    private WSWorker(Builder builder) {
        this.mContext = builder.mContext;
        this.mConfig = builder.mConfig;
        this.mUrl = builder.mUrl;
        this.mRawRequest = builder.mRawRequest;
        this.mSilentRetry = builder.mSilentRetry;
        this.mListener = builder.mListener;
        this.mAESKey = builder.mAESKey;
        this.mWaitTime = builder.mWaitTime;
    }

    protected void onPreExecute() {
        if (this.mListener != null) {
            this.mListener.onPreExecute();
        }
    }

    protected HttpResult doInBackground(Void... params) {
        try {
            Thread.sleep(this.mWaitTime);
        } catch (InterruptedException e) {
            SDKLog.m37e(TAG, "error while wait for time : %d ", Long.valueOf(this.mWaitTime));
        }
        return Network.engine(this.mContext, this.mConfig).postJSON(this.mContext, this.mUrl, this.mRawRequest, this.mSilentRetry, this.mAESKey);
    }

    protected void onPostExecute(HttpResult result) {
        if (this.mListener != null) {
            this.mListener.onResult(result);
        }
    }

    public void executeOnThreadPool() {
        executeOnExecutor(ThreadPool.ws(), new Void[0]);
    }

    public void executeOnCurrentThread() {
        onPostExecute(doInBackground(new Void[0]));
    }
}
