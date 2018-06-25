package org.telegram.customization.dynamicadapter.data;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.telegram.customization.Internet.WSUtils;
import org.telegram.customization.Internet.WebservicePropertis;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;

public class DocAvailableInfo {
    public static final int SOURCE_TYPE_HOT = 1;
    public static final int SOURCE_TYPE_TELEGRAM = 0;
    @SerializedName("cid")
    public long channelId;
    @SerializedName("cun")
    public String channelUsername;
    @SerializedName("d")
    public long docId;
    @SerializedName("e")
    public boolean exists;
    @SerializedName("fs")
    public String freeState;
    @SerializedName("l")
    public int localId;
    @SerializedName("u")
    public String localUrl;
    @SerializedName("m")
    public long messageId;
    @SerializedName("ph")
    public String phone;
    @SerializedName("s")
    public int size;
    public int sourceType;
    @SerializedName("t")
    String stringTags;
    public ArrayList<String> tags;
    @SerializedName("lbl")
    public String trafficStatusLabel;
    @SerializedName("uid")
    public long userId;
    @SerializedName("vc")
    public int versionCode;
    @SerializedName("v")
    public long volumeId;

    public String getStringTags() {
        return this.stringTags;
    }

    public void setStringTags(String stringTags) {
        this.stringTags = stringTags;
    }

    public ArrayList<String> getTags() {
        if (TextUtils.isEmpty(this.stringTags)) {
            return new ArrayList();
        }
        try {
            String[] tmp = (String[]) new Gson().fromJson(this.stringTags, String[].class);
            ArrayList<String> arrayList = new ArrayList();
            for (String tag : tmp) {
                arrayList.add(tag);
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    public void setTag(ArrayList<String> tags) {
        this.tags = tags;
    }

    public int getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public String getLocalUrl() {
        if (TextUtils.isEmpty(this.localUrl)) {
            return this.localUrl;
        }
        String networkType = "";
        try {
            networkType = URLEncoder.encode(WSUtils.getCarrier(ApplicationLoader.applicationContext), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String str = WebservicePropertis.FILE_URL_QUERY_STRING;
        Object[] objArr = new Object[4];
        objArr[0] = Integer.valueOf(UserConfig.getClientUserId());
        objArr[1] = Integer.valueOf(ConnectionsManager.getCurrentNetworkType());
        objArr[2] = networkType;
        objArr[3] = getSourceType() == 0 ? "default" : "hot";
        return this.localUrl + String.format(str, objArr);
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public DocAvailableInfo() {
        this(0, 0, 0, 0, true);
    }

    public DocAvailableInfo(long docId, int localId, long volumeId, long messageId, boolean exists) {
        this.sourceType = 0;
        this.docId = docId;
        this.localId = localId;
        this.volumeId = volumeId;
        this.messageId = messageId;
        this.exists = exists;
    }

    public DocAvailableInfo(long docId, int localId, long volumeId, int size, long channelId, String channelUsername, long messageId) {
        this.sourceType = 0;
        this.docId = docId;
        this.localId = localId;
        this.volumeId = volumeId;
        this.messageId = messageId;
        this.channelId = channelId;
        this.size = size;
        this.channelUsername = channelUsername;
    }

    public void setValues(long docId, int localId, long volumeId, long messageId) {
        this.docId = docId;
        this.localId = localId;
        this.volumeId = volumeId;
        this.messageId = messageId;
    }

    public String getChannelUsername() {
        return this.channelUsername;
    }

    public void setChannelUsername(String channelUsername) {
        this.channelUsername = channelUsername;
    }

    public long getChannelId() {
        return this.channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public boolean equals(Object o) {
        if (!(o instanceof DocAvailableInfo)) {
            return false;
        }
        DocAvailableInfo ob = (DocAvailableInfo) o;
        if (this.volumeId == ob.volumeId && this.localId == ob.localId && this.docId == ob.docId) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ("" + this.docId + "," + this.volumeId + "," + this.localId).hashCode();
    }
}
