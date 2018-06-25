package utils.view.collectionpicker;

import org.telegram.customization.dynamicadapter.data.SlsTag;

public class Item {
    public String id;
    public boolean isSelected;
    public SlsTag tag;
    public String text;

    public Item(String id, String text, SlsTag tag) {
        this(id, text, false, tag);
        this.id = id;
        this.text = text;
    }

    public Item(String id, String text, boolean isSelected, SlsTag tag) {
        this.id = id;
        this.text = text;
        this.isSelected = isSelected;
        this.tag = tag;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
