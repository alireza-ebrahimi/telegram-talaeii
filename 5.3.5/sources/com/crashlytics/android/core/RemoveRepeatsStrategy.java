package com.crashlytics.android.core;

import java.util.HashMap;
import java.util.Map;

class RemoveRepeatsStrategy implements StackTraceTrimmingStrategy {
    private final int maxRepetitions;

    public RemoveRepeatsStrategy() {
        this(1);
    }

    public RemoveRepeatsStrategy(int maxRepetitions) {
        this.maxRepetitions = maxRepetitions;
    }

    public StackTraceElement[] getTrimmedStackTrace(StackTraceElement[] stacktrace) {
        StackTraceElement[] trimmed = trimRepeats(stacktrace, this.maxRepetitions);
        return trimmed.length < stacktrace.length ? trimmed : stacktrace;
    }

    private static StackTraceElement[] trimRepeats(StackTraceElement[] stacktrace, int maxRepetitions) {
        Map<StackTraceElement, Integer> mostRecentIndices = new HashMap();
        StackTraceElement[] buffer = new StackTraceElement[stacktrace.length];
        int trimmedLength = 0;
        int numRepeats = 1;
        int i = 0;
        while (i < stacktrace.length) {
            int currentIndex = i;
            StackTraceElement currentFrame = stacktrace[i];
            Integer previousIndex = (Integer) mostRecentIndices.get(currentFrame);
            if (previousIndex == null || !isRepeatingSequence(stacktrace, previousIndex.intValue(), i)) {
                numRepeats = 1;
                buffer[trimmedLength] = stacktrace[i];
                trimmedLength++;
            } else {
                int windowSize = i - previousIndex.intValue();
                if (numRepeats < maxRepetitions) {
                    System.arraycopy(stacktrace, i, buffer, trimmedLength, windowSize);
                    trimmedLength += windowSize;
                    numRepeats++;
                }
                i += windowSize - 1;
            }
            mostRecentIndices.put(currentFrame, Integer.valueOf(currentIndex));
            i++;
        }
        StackTraceElement[] trimmed = new StackTraceElement[trimmedLength];
        System.arraycopy(buffer, 0, trimmed, 0, trimmed.length);
        return trimmed;
    }

    private static boolean isRepeatingSequence(StackTraceElement[] stacktrace, int prevIndex, int currentIndex) {
        int windowSize = currentIndex - prevIndex;
        if (currentIndex + windowSize > stacktrace.length) {
            return false;
        }
        for (int i = 0; i < windowSize; i++) {
            if (!stacktrace[prevIndex + i].equals(stacktrace[currentIndex + i])) {
                return false;
            }
        }
        return true;
    }
}
