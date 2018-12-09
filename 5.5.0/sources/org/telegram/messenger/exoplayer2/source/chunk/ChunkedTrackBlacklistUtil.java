package org.telegram.messenger.exoplayer2.source.chunk;

import android.util.Log;
import com.google.android.gms.wallet.WalletConstants;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.upstream.HttpDataSource.InvalidResponseCodeException;

public final class ChunkedTrackBlacklistUtil {
    public static final long DEFAULT_TRACK_BLACKLIST_MS = 60000;
    private static final String TAG = "ChunkedTrackBlacklist";

    private ChunkedTrackBlacklistUtil() {
    }

    public static boolean maybeBlacklistTrack(TrackSelection trackSelection, int i, Exception exception) {
        return maybeBlacklistTrack(trackSelection, i, exception, DEFAULT_TRACK_BLACKLIST_MS);
    }

    public static boolean maybeBlacklistTrack(TrackSelection trackSelection, int i, Exception exception, long j) {
        if (!shouldBlacklist(exception)) {
            return false;
        }
        boolean blacklist = trackSelection.blacklist(i, j);
        int i2 = ((InvalidResponseCodeException) exception).responseCode;
        if (blacklist) {
            Log.w(TAG, "Blacklisted: duration=" + j + ", responseCode=" + i2 + ", format=" + trackSelection.getFormat(i));
            return blacklist;
        }
        Log.w(TAG, "Blacklisting failed (cannot blacklist last enabled track): responseCode=" + i2 + ", format=" + trackSelection.getFormat(i));
        return blacklist;
    }

    public static boolean shouldBlacklist(Exception exception) {
        if (!(exception instanceof InvalidResponseCodeException)) {
            return false;
        }
        int i = ((InvalidResponseCodeException) exception).responseCode;
        return i == WalletConstants.ERROR_CODE_INVALID_PARAMETERS || i == WalletConstants.ERROR_CODE_INVALID_TRANSACTION;
    }
}
