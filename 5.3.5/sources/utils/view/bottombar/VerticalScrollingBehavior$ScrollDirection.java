package utils.view.bottombar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@interface VerticalScrollingBehavior$ScrollDirection {
    public static final int SCROLL_DIRECTION_DOWN = -1;
    public static final int SCROLL_DIRECTION_UP = 1;
    public static final int SCROLL_NONE = 0;
}
