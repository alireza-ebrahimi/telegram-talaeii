package utils.view.tagview;

import android.graphics.Color;

public class Constants {
    public static final float DEFAULT_LINE_MARGIN = 5.0f;
    public static final String DEFAULT_TAG_DELETE_ICON = "×";
    public static final int DEFAULT_TAG_DELETE_INDICATOR_COLOR = Color.parseColor("#ffffff");
    public static final float DEFAULT_TAG_DELETE_INDICATOR_SIZE = 16.0f;
    public static final boolean DEFAULT_TAG_IS_DELETABLE = false;
    public static final int DEFAULT_TAG_LAYOUT_BORDER_COLOR = Color.parseColor("#ffffff");
    public static final float DEFAULT_TAG_LAYOUT_BORDER_SIZE = 0.0f;
    public static final int DEFAULT_TAG_LAYOUT_COLOR = Color.parseColor("#1c65b9");
    public static final int DEFAULT_TAG_LAYOUT_COLOR_PRESS = Color.parseColor("#88363636");
    public static final float DEFAULT_TAG_MARGIN = 5.0f;
    public static final float DEFAULT_TAG_RADIUS = 24.0f;
    public static final int DEFAULT_TAG_TEXT_COLOR = Color.parseColor("#ffffff");
    public static final float DEFAULT_TAG_TEXT_PADDING_BOTTOM = 5.0f;
    public static final float DEFAULT_TAG_TEXT_PADDING_LEFT = 8.0f;
    public static final float DEFAULT_TAG_TEXT_PADDING_RIGHT = 8.0f;
    public static final float DEFAULT_TAG_TEXT_PADDING_TOP = 5.0f;
    public static final float DEFAULT_TAG_TEXT_SIZE = 16.0f;
    public static final float LAYOUT_WIDTH_OFFSET = 2.0f;

    private Constants() throws InstantiationException {
        throw new InstantiationException("This class is not for instantiation");
    }
}
