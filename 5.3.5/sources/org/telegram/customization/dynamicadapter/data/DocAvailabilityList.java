package org.telegram.customization.dynamicadapter.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class DocAvailabilityList {
    @SerializedName("c")
    public String carrier;
    @SerializedName("g")
    public boolean gprs;
    @SerializedName("i")
    public ArrayList<DocAvailableInfo> inputs;
    @SerializedName("if")
    public boolean isFree;
    @SerializedName("lbl")
    public String label;
    @SerializedName("ph")
    public String phone;
    @SerializedName("uid")
    public long userId;
    @SerializedName("vc")
    public int versionCode;
}
