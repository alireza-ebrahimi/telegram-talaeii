package org.telegram.customization.dynamicadapter.data;

import android.text.TextUtils;
import com.google.p098a.C1668x;
import com.google.p098a.C1670w;
import com.google.p098a.C1768f;
import com.google.p098a.C1771l;
import com.google.p098a.p099a.C1662c;
import com.google.p098a.p102d.C1678a;
import com.google.p098a.p102d.C1681c;
import com.google.p098a.p103c.C1748a;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.telegram.customization.p151g.C2822f;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.tgnet.ConnectionsManager;

public class DocAvailableInfo {
    public static final int SOURCE_TYPE_HOT = 1;
    public static final int SOURCE_TYPE_TELEGRAM = 0;
    @C1662c(a = "cid")
    public long channelId;
    @C1662c(a = "cun")
    public String channelUsername;
    @C1662c(a = "d")
    public long docId;
    @C1662c(a = "e")
    public boolean exists;
    @C1662c(a = "fs")
    public String freeState;
    @C1662c(a = "l")
    public int localId;
    @C1662c(a = "u")
    public String localUrl;
    @C1662c(a = "m")
    public long messageId;
    @C1662c(a = "ph")
    public String phone;
    @C1662c(a = "s")
    public int size;
    public int sourceType;
    @C1662c(a = "t")
    String stringTags;
    public ArrayList<String> tags;
    @C1662c(a = "lbl")
    public String trafficStatusLabel;
    @C1662c(a = "uid")
    public long userId;
    @C1662c(a = "vc")
    public int versionCode;
    @C1662c(a = "v")
    public long volumeId;

    public static class DocAvailableInfoAdapterFactory implements C1668x {

        private static class DocAvailableInfoTypeAdapter extends C1670w<DocAvailableInfo> {
            private final C1768f gson;
            private final DocAvailableInfoAdapterFactory gsonAdapterFactory;
            private final C1670w<C1771l> jsonElementAdapter;

            DocAvailableInfoTypeAdapter(DocAvailableInfoAdapterFactory docAvailableInfoAdapterFactory, C1768f c1768f, C1670w<C1771l> c1670w) {
                this.jsonElementAdapter = c1670w;
                this.gson = c1768f;
                this.gsonAdapterFactory = docAvailableInfoAdapterFactory;
            }

            public DocAvailableInfo read(C1678a c1678a) {
                return null;
            }

            public void write(C1681c c1681c, DocAvailableInfo docAvailableInfo) {
                c1681c.mo1282d();
                if (docAvailableInfo.docId != 0) {
                    c1681c.mo1275a("d").mo1273a(docAvailableInfo.docId);
                }
                if (docAvailableInfo.localId != 0) {
                    c1681c.mo1275a("l").mo1273a((long) docAvailableInfo.localId);
                }
                if (docAvailableInfo.volumeId != 0) {
                    c1681c.mo1275a("v").mo1273a(docAvailableInfo.volumeId);
                }
                if (docAvailableInfo.messageId != 0) {
                    c1681c.mo1275a("m").mo1273a(docAvailableInfo.messageId);
                }
                if (docAvailableInfo.size != 0) {
                    c1681c.mo1275a("s").mo1273a((long) docAvailableInfo.size);
                }
                if (docAvailableInfo.channelId != 0) {
                    c1681c.mo1275a("cid").mo1273a(docAvailableInfo.channelId);
                }
                if (!TextUtils.isEmpty(docAvailableInfo.channelUsername)) {
                    c1681c.mo1275a("cun").mo1279b(docAvailableInfo.channelUsername);
                }
                c1681c.mo1283e();
            }
        }

        public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
            return !DocAvailableInfo.class.isAssignableFrom(c1748a.m8359a()) ? null : new DocAvailableInfoTypeAdapter(this, c1768f, c1768f.m8389a(C1771l.class)).nullSafe();
        }
    }

    public static class NewDocAvailableInfoAdapterFactory implements C1668x {

        private static class NewDocAvailableInfoTypeAdapter extends C1670w<DocAvailableInfo> {
            private final C1768f gson;
            private final NewDocAvailableInfoAdapterFactory gsonAdapterFactory;
            private final C1670w<C1771l> jsonElementAdapter;

            NewDocAvailableInfoTypeAdapter(NewDocAvailableInfoAdapterFactory newDocAvailableInfoAdapterFactory, C1768f c1768f, C1670w<C1771l> c1670w) {
                this.jsonElementAdapter = c1670w;
                this.gson = c1768f;
                this.gsonAdapterFactory = newDocAvailableInfoAdapterFactory;
            }

            public DocAvailableInfo read(C1678a c1678a) {
                return null;
            }

            public void write(C1681c c1681c, DocAvailableInfo docAvailableInfo) {
                c1681c.mo1282d();
                if (docAvailableInfo.docId != 0) {
                    c1681c.mo1275a("fk").mo1273a(docAvailableInfo.docId);
                }
                if (!(docAvailableInfo.localId == 0 || docAvailableInfo.volumeId == 0)) {
                    c1681c.mo1275a("fk").mo1279b(String.valueOf(docAvailableInfo.localId) + "." + String.valueOf(docAvailableInfo.volumeId));
                }
                if (!(docAvailableInfo.messageId == 0 || docAvailableInfo.channelId == 0)) {
                    c1681c.mo1275a("mk").mo1279b(String.valueOf(docAvailableInfo.messageId) + "." + String.valueOf(docAvailableInfo.channelId));
                }
                if (docAvailableInfo.size != 0) {
                    c1681c.mo1275a("s").mo1273a((long) docAvailableInfo.size);
                }
                c1681c.mo1283e();
            }
        }

        public <T> C1670w<T> create(C1768f c1768f, C1748a<T> c1748a) {
            return !DocAvailableInfo.class.isAssignableFrom(c1748a.m8359a()) ? null : new NewDocAvailableInfoTypeAdapter(this, c1768f, c1768f.m8389a(C1771l.class)).nullSafe();
        }
    }

    public DocAvailableInfo() {
        this(0, 0, 0, 0, true);
    }

    public DocAvailableInfo(long j, int i, long j2, int i2, long j3, String str, long j4) {
        this.sourceType = 0;
        this.docId = j;
        this.localId = i;
        this.volumeId = j2;
        this.messageId = j4;
        this.channelId = j3;
        this.size = i2;
        this.channelUsername = str;
    }

    public DocAvailableInfo(long j, int i, long j2, long j3, boolean z) {
        this.sourceType = 0;
        this.docId = j;
        this.localId = i;
        this.volumeId = j2;
        this.messageId = j3;
        this.exists = z;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DocAvailableInfo)) {
            return false;
        }
        DocAvailableInfo docAvailableInfo = (DocAvailableInfo) obj;
        return this.volumeId == docAvailableInfo.volumeId && this.localId == docAvailableInfo.localId && this.docId == docAvailableInfo.docId;
    }

    public long getChannelId() {
        return this.channelId;
    }

    public String getChannelUsername() {
        return this.channelUsername;
    }

    public String getLocalUrl() {
        if (TextUtils.isEmpty(this.localUrl)) {
            return this.localUrl;
        }
        String str = TtmlNode.ANONYMOUS_REGION_ID;
        try {
            str = URLEncoder.encode(C2822f.m13157b(ApplicationLoader.applicationContext), C3446C.UTF8_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String str2 = "?uId=%s&type=%s&value=%s&source=%s";
        Object[] objArr = new Object[4];
        objArr[0] = Integer.valueOf(UserConfig.getClientUserId());
        objArr[1] = Integer.valueOf(ConnectionsManager.getCurrentNetworkType());
        objArr[2] = str;
        objArr[3] = getSourceType() == 0 ? "default" : "hot";
        return this.localUrl + String.format(str2, objArr);
    }

    public int getSourceType() {
        return this.sourceType;
    }

    public String getStringTags() {
        return this.stringTags;
    }

    public ArrayList<String> getTags() {
        if (TextUtils.isEmpty(this.stringTags)) {
            return new ArrayList();
        }
        try {
            String[] strArr = (String[]) new C1768f().m8392a(this.stringTags, String[].class);
            ArrayList<String> arrayList = new ArrayList();
            for (Object add : strArr) {
                arrayList.add(add);
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    public int hashCode() {
        return (TtmlNode.ANONYMOUS_REGION_ID + this.docId + "," + this.volumeId + "," + this.localId).hashCode();
    }

    public void setChannelId(long j) {
        this.channelId = j;
    }

    public void setChannelUsername(String str) {
        this.channelUsername = str;
    }

    public void setLocalUrl(String str) {
        this.localUrl = str;
    }

    public void setSourceType(int i) {
        this.sourceType = i;
    }

    public void setStringTags(String str) {
        this.stringTags = str;
    }

    public void setTag(ArrayList<String> arrayList) {
        this.tags = arrayList;
    }

    public void setValues(long j, int i, long j2, long j3) {
        this.docId = j;
        this.localId = i;
        this.volumeId = j2;
        this.messageId = j3;
    }
}
