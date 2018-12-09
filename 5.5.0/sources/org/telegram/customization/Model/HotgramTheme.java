package org.telegram.customization.Model;

public class HotgramTheme {
    String name;
    String previewUrl;
    boolean selected;
    String serial;

    public String getName() {
        return this.name;
    }

    public String getPreviewUrl() {
        return this.previewUrl;
    }

    public String getSerial() {
        return this.serial;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setPreviewUrl(String str) {
        this.previewUrl = str;
    }

    public void setSelected(boolean z) {
        this.selected = z;
    }

    public void setSerial(String str) {
        this.serial = str;
    }
}
