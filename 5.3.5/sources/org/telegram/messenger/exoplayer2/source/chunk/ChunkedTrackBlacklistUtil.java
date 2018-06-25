package org.telegram.messenger.exoplayer2.source.chunk;

import android.util.Log;
import com.google.android.gms.wallet.WalletConstants;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.upstream.HttpDataSource.InvalidResponseCodeException;

public final class ChunkedTrackBlacklistUtil {
    public static final long DEFAULT_TRACK_BLACKLIST_MS = 60000;
    private static final String TAG = "ChunkedTrackBlacklist";

    public static boolean maybeBlacklistTrack(TrackSelection trackSelection, int trackSelectionIndex, Exception e) {
        return maybeBlacklistTrack(trackSelection, trackSelectionIndex, e, 60000);
    }

    public static boolean maybeBlacklistTrack(TrackSelection trackSelection, int trackSelectionIndex, Exception e, long blacklistDurationMs) {
        if (!shouldBlacklist(e)) {
            return false;
        }
        boolean blacklisted = trackSelection.blacklist(trackSelectionIndex, blacklistDurationMs);
        int responseCode = ((InvalidResponseCodeException) e).responseCode;
        if (blacklisted) {
            Log.w(TAG, "Blacklisted: duration=" + blacklistDurationMs + ", responseCode=" + responseCode + ", format=" + trackSelection.getFormat(trackSelectionIndex));
            return blacklisted;
        }
        Log.w(TAG, "Blacklisting failed (cannot blacklist last enabled track): responseCode=" + responseCode + ", format=" + trackSelection.getFormat(trackSelectionIndex));
        return blacklisted;
    }

    public static boolean shouldBlacklist(Exception e) {
        if (!(e instanceof InvalidResponseCodeException)) {
            return false;
        }
        int responseCode = ((InvalidResponseCodeException) e).responseCode;
        if (responseCode == WalletConstants.ERROR_CODE_INVALID_PARAMETERS || responseCode == WalletConstants.ERROR_CODE_INVALID_TRANSACTION) {
            return true;
        }
        return false;
    }

    private ChunkedTrackBlacklistUtil() {
    }
}
