package com.h6ah4i.android.widget.advrecyclerview.swipeable;

class SwipeReactionUtils {
    SwipeReactionUtils() {
    }

    public static int extractLeftReaction(int type) {
        return (type >>> 0) & 3;
    }

    public static int extractUpReaction(int type) {
        return (type >>> 6) & 3;
    }

    public static int extractRightReaction(int type) {
        return (type >>> 12) & 3;
    }

    public static int extractDownReaction(int type) {
        return (type >>> 18) & 3;
    }

    public static boolean canSwipeLeft(int reactionType) {
        return extractLeftReaction(reactionType) == 2;
    }

    public static boolean canSwipeUp(int reactionType) {
        return extractUpReaction(reactionType) == 2;
    }

    public static boolean canSwipeRight(int reactionType) {
        return extractRightReaction(reactionType) == 2;
    }

    public static boolean canSwipeDown(int reactionType) {
        return extractDownReaction(reactionType) == 2;
    }
}
