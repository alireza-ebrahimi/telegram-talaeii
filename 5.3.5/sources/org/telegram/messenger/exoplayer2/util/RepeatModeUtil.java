package org.telegram.messenger.exoplayer2.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class RepeatModeUtil {
    public static final int REPEAT_TOGGLE_MODE_ALL = 2;
    public static final int REPEAT_TOGGLE_MODE_NONE = 0;
    public static final int REPEAT_TOGGLE_MODE_ONE = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatToggleModes {
    }

    private RepeatModeUtil() {
    }

    public static int getNextRepeatMode(int currentMode, int enabledModes) {
        for (int offset = 1; offset <= 2; offset++) {
            int proposedMode = (currentMode + offset) % 3;
            if (isRepeatModeEnabled(proposedMode, enabledModes)) {
                return proposedMode;
            }
        }
        return currentMode;
    }

    public static boolean isRepeatModeEnabled(int repeatMode, int enabledModes) {
        switch (repeatMode) {
            case 0:
                return true;
            case 1:
                if ((enabledModes & 1) == 0) {
                    return false;
                }
                return true;
            case 2:
                return (enabledModes & 2) != 0;
            default:
                return false;
        }
    }
}
