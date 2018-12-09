package utils;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.telegram.customization.dynamicadapter.data.DocAvailableInfo;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.PhotoSize;
import utils.p178a.C3791b;

/* renamed from: utils.b */
public class C5319b {
    /* renamed from: a */
    private static volatile HashMap<DocAvailableInfo, Integer> f10230a = new HashMap();
    /* renamed from: b */
    private static volatile HashMap<DocAvailableInfo, DocAvailableInfo> f10231b = new HashMap();
    /* renamed from: c */
    private static volatile HashMap<String, String> f10232c = new HashMap();

    /* renamed from: utils.b$2 */
    static class C53152 implements C2497d {
        C53152() {
        }

        public void onResult(Object obj, int i) {
        }
    }

    /* renamed from: a */
    public static int m14134a(DocAvailableInfo docAvailableInfo) {
        return (f10230a == null || docAvailableInfo == null || !f10230a.containsKey(docAvailableInfo) || f10230a.get(docAvailableInfo) == null) ? 2 : ((Integer) f10230a.get(docAvailableInfo)).intValue();
    }

    /* renamed from: a */
    public static String m14135a(int i) {
        switch (i) {
            case 0:
                return "Not Free";
            case 1:
                return "Free";
            case 2:
                return "Not In Cache";
            default:
                return "Unknown";
        }
    }

    /* renamed from: a */
    public static String m14136a(int i, long j) {
        String str = String.valueOf(i) + "." + String.valueOf(j);
        if (f10232c.containsKey(str)) {
            str = (String) f10232c.get(str);
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        }
        return TtmlNode.ANONYMOUS_REGION_ID;
    }

    /* renamed from: a */
    public static void m14137a(final Context context, final TLRPC$messages_Messages tLRPC$messages_Messages, final long j) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                C5319b.m14141b(context, tLRPC$messages_Messages, j);
            }
        }, 1000);
    }

    /* renamed from: a */
    public static void m14138a(ArrayList<DocAvailableInfo> arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            DocAvailableInfo docAvailableInfo = (DocAvailableInfo) it.next();
            if (f10230a.containsKey(docAvailableInfo)) {
                f10230a.remove(docAvailableInfo);
                f10231b.remove(docAvailableInfo);
            }
            f10230a.put(docAvailableInfo, Integer.valueOf(docAvailableInfo.exists ? 1 : 0));
            f10231b.put(docAvailableInfo, docAvailableInfo);
        }
    }

    /* renamed from: a */
    public static void m14139a(Map<String, String> map) {
        for (String str : map.keySet()) {
            if (f10232c.containsKey(str)) {
                f10232c.remove(str);
            }
            f10232c.put(str, map.get(str));
        }
    }

    /* renamed from: b */
    public static String m14140b(DocAvailableInfo docAvailableInfo) {
        try {
            if (f10231b.containsKey(docAvailableInfo)) {
                return ((DocAvailableInfo) f10231b.get(docAvailableInfo)).getLocalUrl();
            }
        } catch (Exception e) {
        }
        return null;
    }

    /* renamed from: b */
    public static void m14141b(Context context, TLRPC$messages_Messages tLRPC$messages_Messages, long j) {
        long j2;
        ArrayList arrayList = new ArrayList();
        Iterator it = tLRPC$messages_Messages.messages.iterator();
        while (it.hasNext()) {
            long j3;
            PhotoSize photoSize;
            Iterator it2;
            Message message = (Message) it.next();
            Object obj = null;
            try {
                obj = message.to_id.channel_id != 0 ? 1 : null;
            } catch (Exception e) {
            }
            if (obj == null) {
                try {
                    if (message.fwd_from.channel_id == 0) {
                        obj = null;
                        j2 = obj != null ? (long) message.to_id.channel_id : (long) message.to_id.channel_id;
                        if (message.fwd_from.channel_id != 0) {
                            j2 = (long) message.fwd_from.channel_id;
                        }
                        j3 = j2;
                        arrayList.add(new DocAvailableInfo(message.media.document.id, 0, 0, message.media.document.size, j3, null, (long) message.id));
                        photoSize = message.media.document.thumb;
                        arrayList.add(new DocAvailableInfo(0, photoSize.location.local_id, photoSize.location.volume_id, 0, j3, null, (long) message.id));
                        it2 = message.media.photo.sizes.iterator();
                        while (it2.hasNext()) {
                            photoSize = (PhotoSize) it2.next();
                            arrayList.add(new DocAvailableInfo(0, photoSize.location.local_id, photoSize.location.volume_id, 0, j3, null, (long) message.id));
                        }
                    }
                } catch (Exception e2) {
                }
            }
            obj = 1;
            if (obj != null) {
            }
            try {
                if (message.fwd_from.channel_id != 0) {
                    j2 = (long) message.fwd_from.channel_id;
                }
                j3 = j2;
            } catch (Exception e3) {
                j3 = j2;
            }
            try {
                arrayList.add(new DocAvailableInfo(message.media.document.id, 0, 0, message.media.document.size, j3, null, (long) message.id));
            } catch (Exception e4) {
            }
            try {
                photoSize = message.media.document.thumb;
                arrayList.add(new DocAvailableInfo(0, photoSize.location.local_id, photoSize.location.volume_id, 0, j3, null, (long) message.id));
            } catch (Exception e5) {
            }
            try {
                it2 = message.media.photo.sizes.iterator();
                while (it2.hasNext()) {
                    photoSize = (PhotoSize) it2.next();
                    arrayList.add(new DocAvailableInfo(0, photoSize.location.local_id, photoSize.location.volume_id, 0, j3, null, (long) message.id));
                }
            } catch (Exception e6) {
            }
        }
        if (arrayList.size() > 0) {
            ArrayList arrayList2 = new ArrayList();
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                DocAvailableInfo docAvailableInfo = (DocAvailableInfo) it3.next();
                if (!(f10230a == null || docAvailableInfo == null || f10230a.containsKey(docAvailableInfo))) {
                    arrayList2.add(docAvailableInfo);
                }
            }
            C5319b.m14138a(arrayList2);
            C2818c.a(context, new C53152()).a(arrayList2, j);
        }
    }

    /* renamed from: c */
    public static String m14142c(DocAvailableInfo docAvailableInfo) {
        try {
            if (f10231b.containsKey(docAvailableInfo)) {
                return ((DocAvailableInfo) f10231b.get(docAvailableInfo)).trafficStatusLabel;
            }
        } catch (Exception e) {
        }
        return null;
    }

    /* renamed from: d */
    public static String m14143d(DocAvailableInfo docAvailableInfo) {
        try {
            if (f10231b.containsKey(docAvailableInfo)) {
                return C3791b.e(ApplicationLoader.applicationContext, ((DocAvailableInfo) f10231b.get(docAvailableInfo)).freeState);
            }
        } catch (Exception e) {
        }
        return null;
    }
}
