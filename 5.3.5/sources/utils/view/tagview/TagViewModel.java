package utils.view.tagview;

import android.graphics.drawable.Drawable;

public class TagViewModel {
    public Drawable background;
    public String deleteIcon;
    public int deleteIndicatorColor;
    public float deleteIndicatorSize;
    public long id;
    public boolean isDeletable;
    public int layoutBorderColor;
    public float layoutBorderSize;
    public int layoutColor;
    public int layoutColorPress;
    public float radius;
    public int tagTextColor;
    public float tagTextSize;
    public String text;

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TagViewModel(String text) {
        init(0, text, Constants.DEFAULT_TAG_TEXT_COLOR, 16.0f, Constants.DEFAULT_TAG_LAYOUT_COLOR, Constants.DEFAULT_TAG_LAYOUT_COLOR_PRESS, false, Constants.DEFAULT_TAG_DELETE_INDICATOR_COLOR, 16.0f, 24.0f, Constants.DEFAULT_TAG_DELETE_ICON, 0.0f, Constants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);
    }

    public TagViewModel(String text, boolean isDeletable) {
        init(0, text, Constants.DEFAULT_TAG_TEXT_COLOR, 16.0f, Constants.DEFAULT_TAG_LAYOUT_COLOR, Constants.DEFAULT_TAG_LAYOUT_COLOR_PRESS, isDeletable, Constants.DEFAULT_TAG_DELETE_INDICATOR_COLOR, 16.0f, 24.0f, Constants.DEFAULT_TAG_DELETE_ICON, 0.0f, Constants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);
    }

    public TagViewModel(long id, String text, boolean isDeletable) {
        init(id, text, Constants.DEFAULT_TAG_TEXT_COLOR, 16.0f, Constants.DEFAULT_TAG_LAYOUT_COLOR, Constants.DEFAULT_TAG_LAYOUT_COLOR_PRESS, isDeletable, Constants.DEFAULT_TAG_DELETE_INDICATOR_COLOR, 16.0f, 24.0f, Constants.DEFAULT_TAG_DELETE_ICON, 0.0f, Constants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);
    }

    private void init(long id, String text, int tagTextColor, float tagTextSize, int layoutColor, int layoutColorPress, boolean isDeletable, int deleteIndicatorColor, float deleteIndicatorSize, float radius, String deleteIcon, float layoutBorderSize, int layoutBorderColor) {
        this.id = id;
        this.text = text;
        this.tagTextColor = tagTextColor;
        this.tagTextSize = tagTextSize;
        this.layoutColor = layoutColor;
        this.layoutColorPress = layoutColorPress;
        this.isDeletable = isDeletable;
        this.deleteIndicatorColor = deleteIndicatorColor;
        this.deleteIndicatorSize = deleteIndicatorSize;
        this.radius = radius;
        this.deleteIcon = deleteIcon;
        this.layoutBorderSize = layoutBorderSize;
        this.layoutBorderColor = layoutBorderColor;
    }
}
