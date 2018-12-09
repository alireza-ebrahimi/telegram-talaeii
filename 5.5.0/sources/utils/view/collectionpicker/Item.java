package utils.view.collectionpicker;

import org.telegram.customization.dynamicadapter.data.SlsTag;

public class Item {
    /* renamed from: a */
    public String f10523a;
    /* renamed from: b */
    public String f10524b;
    /* renamed from: c */
    public boolean f10525c;
    /* renamed from: d */
    public SlsTag f10526d;

    public Item(String str, String str2, SlsTag slsTag) {
        this(str, str2, false, slsTag);
        this.f10523a = str;
        this.f10524b = str2;
    }

    public Item(String str, String str2, boolean z, SlsTag slsTag) {
        this.f10523a = str;
        this.f10524b = str2;
        this.f10525c = z;
        this.f10526d = slsTag;
    }

    /* renamed from: a */
    public String m14395a() {
        return this.f10523a;
    }
}
