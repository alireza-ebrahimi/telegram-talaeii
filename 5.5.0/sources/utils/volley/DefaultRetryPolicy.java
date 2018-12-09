package utils.volley;

public class DefaultRetryPolicy implements RetryPolicy {
    public static final float DEFAULT_BACKOFF_MULT = 1.0f;
    public static final int DEFAULT_MAX_RETRIES = 1;
    public static final int DEFAULT_TIMEOUT_MS = 2500;
    private final float mBackoffMultiplier;
    private int mCurrentRetryCount;
    private int mCurrentTimeoutMs;
    private final int mMaxNumRetries;

    public DefaultRetryPolicy() {
        this(2500, 1, 1.0f);
    }

    public DefaultRetryPolicy(int i, int i2, float f) {
        this.mCurrentTimeoutMs = i;
        this.mMaxNumRetries = i2;
        this.mBackoffMultiplier = f;
    }

    public float getBackoffMultiplier() {
        return this.mBackoffMultiplier;
    }

    public int getCurrentRetryCount() {
        return this.mCurrentRetryCount;
    }

    public int getCurrentTimeout() {
        return this.mCurrentTimeoutMs;
    }

    protected boolean hasAttemptRemaining() {
        return this.mCurrentRetryCount <= this.mMaxNumRetries;
    }

    public void retry(VolleyError volleyError) {
        this.mCurrentRetryCount++;
        this.mCurrentTimeoutMs += (int) (((float) this.mCurrentTimeoutMs) * this.mBackoffMultiplier);
        if (!hasAttemptRemaining()) {
            throw volleyError;
        }
    }
}
