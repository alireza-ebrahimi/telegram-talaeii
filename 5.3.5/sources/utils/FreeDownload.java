package utils;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import com.stripe.android.model.Card;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.dynamicadapter.data.DocAvailableInfo;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$messages_Messages;
import utils.app.AppPreferences;

public class FreeDownload {
    public static final int FREE = 1;
    public static final int NOT_FREE = 0;
    public static final int NOT_IN_CACHE = 2;
    private static volatile HashMap<DocAvailableInfo, Integer> freeCache = new HashMap();
    private static volatile HashMap<DocAvailableInfo, DocAvailableInfo> freeCacheData = new HashMap();
    private static volatile HashMap<String, String> tagData = new HashMap();

    /* renamed from: utils.FreeDownload$2 */
    static class C34652 implements IResponseReceiver {
        C34652() {
        }

        public void onResult(Object object, int StatusCode) {
        }
    }

    /* renamed from: utils.FreeDownload$3 */
    static class C34663 implements IResponseReceiver {
        C34663() {
        }

        public void onResult(Object object, int StatusCode) {
        }
    }

    /* renamed from: utils.FreeDownload$4 */
    static class C34674 implements IResponseReceiver {
        C34674() {
        }

        public void onResult(Object object, int StatusCode) {
        }
    }

    public static String getCacheHumanName(int cacheState) {
        switch (cacheState) {
            case 0:
                return "Not Free";
            case 1:
                return "Free";
            case 2:
                return "Not In Cache";
            default:
                return Card.UNKNOWN;
        }
    }

    public static int isFree(DocAvailableInfo doc) {
        if (freeCache == null || doc == null || !freeCache.containsKey(doc) || freeCache.get(doc) == null) {
            return 2;
        }
        return ((Integer) freeCache.get(doc)).intValue();
    }

    public static String getFreeUrl(DocAvailableInfo doc) {
        try {
            if (freeCacheData.containsKey(doc)) {
                return ((DocAvailableInfo) freeCacheData.get(doc)).getLocalUrl();
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static String getTag(DocAvailableInfo doc) {
        try {
            if (freeCacheData.containsKey(doc) && ((DocAvailableInfo) freeCacheData.get(doc)).getTags() != null && ((DocAvailableInfo) freeCacheData.get(doc)).getTags().size() > 0) {
                return (String) ((DocAvailableInfo) freeCacheData.get(doc)).getTags().get(0);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static String getTag(int messageId, long channelId) {
        String messageKey = String.valueOf(messageId) + "." + String.valueOf(channelId);
        if (tagData.containsKey(messageKey)) {
            String tag = (String) tagData.get(messageKey);
            if (!TextUtils.isEmpty(tag)) {
                return tag;
            }
        }
        return "";
    }

    public static String getTrafficStatusLabel(DocAvailableInfo doc) {
        try {
            if (freeCacheData.containsKey(doc)) {
                return ((DocAvailableInfo) freeCacheData.get(doc)).trafficStatusLabel;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static String getFreeState(DocAvailableInfo doc) {
        try {
            if (freeCacheData.containsKey(doc)) {
                return ((DocAvailableInfo) freeCacheData.get(doc)).freeState;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static String getFreeStateText(DocAvailableInfo doc) {
        try {
            if (freeCacheData.containsKey(doc)) {
                return AppPreferences.getFreeStateText(ApplicationLoader.applicationContext, ((DocAvailableInfo) freeCacheData.get(doc)).freeState);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static void addDocs(ArrayList<DocAvailableInfo> docs) {
        Iterator it = docs.iterator();
        while (it.hasNext()) {
            DocAvailableInfo doc = (DocAvailableInfo) it.next();
            if (freeCache.containsKey(doc)) {
                freeCache.remove(doc);
                freeCacheData.remove(doc);
            }
            freeCache.put(doc, Integer.valueOf(doc.exists ? 1 : 0));
            freeCacheData.put(doc, doc);
        }
    }

    public static void addTags(Map<String, String> tags) {
        for (String key : tags.keySet()) {
            if (tagData.containsKey(key)) {
                tagData.remove(key);
            }
            tagData.put(key, tags.get(key));
        }
    }

    public static void checkMessages(final Context ctx, final TLRPC$messages_Messages messagesRes, final long dialog_id) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                FreeDownload.checkMessagesInternal(ctx, messagesRes, dialog_id);
            }
        }, 1000);
    }

    public static void checkMessagesInternal(Context ctx, TLRPC$messages_Messages messagesRes, long dialog_id) {
        ArrayList<DocAvailableInfo> docs = new ArrayList();
        Iterator it = messagesRes.messages.iterator();
        while (it.hasNext()) {
            long channelID;
            TLRPC$PhotoSize tps;
            Iterator it2;
            TLRPC$PhotoSize ps;
            TLRPC$Message message = (TLRPC$Message) it.next();
            boolean shouldAdd = false;
            if (null == null) {
                try {
                    if (message.to_id.channel_id == 0) {
                        shouldAdd = false;
                        if (!shouldAdd) {
                            try {
                                if (message.fwd_from.channel_id == 0) {
                                    shouldAdd = false;
                                    channelID = shouldAdd ? (long) message.to_id.channel_id : (long) message.to_id.channel_id;
                                    if (message.fwd_from.channel_id != 0) {
                                        channelID = (long) message.fwd_from.channel_id;
                                    }
                                    docs.add(new DocAvailableInfo(message.media.document.id, 0, 0, message.media.document.size, channelID, null, (long) message.id));
                                    tps = message.media.document.thumb;
                                    docs.add(new DocAvailableInfo(0, tps.location.local_id, tps.location.volume_id, 0, channelID, null, (long) message.id));
                                    it2 = message.media.photo.sizes.iterator();
                                    while (it2.hasNext()) {
                                        ps = (TLRPC$PhotoSize) it2.next();
                                        docs.add(new DocAvailableInfo(0, ps.location.local_id, ps.location.volume_id, 0, channelID, null, (long) message.id));
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        shouldAdd = true;
                        if (shouldAdd) {
                        }
                        if (message.fwd_from.channel_id != 0) {
                            channelID = (long) message.fwd_from.channel_id;
                        }
                        docs.add(new DocAvailableInfo(message.media.document.id, 0, 0, message.media.document.size, channelID, null, (long) message.id));
                        tps = message.media.document.thumb;
                        docs.add(new DocAvailableInfo(0, tps.location.local_id, tps.location.volume_id, 0, channelID, null, (long) message.id));
                        it2 = message.media.photo.sizes.iterator();
                        while (it2.hasNext()) {
                            ps = (TLRPC$PhotoSize) it2.next();
                            docs.add(new DocAvailableInfo(0, ps.location.local_id, ps.location.volume_id, 0, channelID, null, (long) message.id));
                        }
                    }
                } catch (Exception e2) {
                }
            }
            shouldAdd = true;
            if (shouldAdd) {
                if (message.fwd_from.channel_id == 0) {
                    shouldAdd = false;
                    if (shouldAdd) {
                    }
                    if (message.fwd_from.channel_id != 0) {
                        channelID = (long) message.fwd_from.channel_id;
                    }
                    docs.add(new DocAvailableInfo(message.media.document.id, 0, 0, message.media.document.size, channelID, null, (long) message.id));
                    tps = message.media.document.thumb;
                    docs.add(new DocAvailableInfo(0, tps.location.local_id, tps.location.volume_id, 0, channelID, null, (long) message.id));
                    it2 = message.media.photo.sizes.iterator();
                    while (it2.hasNext()) {
                        ps = (TLRPC$PhotoSize) it2.next();
                        docs.add(new DocAvailableInfo(0, ps.location.local_id, ps.location.volume_id, 0, channelID, null, (long) message.id));
                    }
                }
            }
            shouldAdd = true;
            if (shouldAdd) {
            }
            try {
                if (message.fwd_from.channel_id != 0) {
                    channelID = (long) message.fwd_from.channel_id;
                }
            } catch (Exception e3) {
            }
            try {
                docs.add(new DocAvailableInfo(message.media.document.id, 0, 0, message.media.document.size, channelID, null, (long) message.id));
            } catch (Exception e4) {
            }
            try {
                tps = message.media.document.thumb;
                docs.add(new DocAvailableInfo(0, tps.location.local_id, tps.location.volume_id, 0, channelID, null, (long) message.id));
            } catch (Exception e5) {
            }
            try {
                it2 = message.media.photo.sizes.iterator();
                while (it2.hasNext()) {
                    ps = (TLRPC$PhotoSize) it2.next();
                    docs.add(new DocAvailableInfo(0, ps.location.local_id, ps.location.volume_id, 0, channelID, null, (long) message.id));
                }
            } catch (Exception e6) {
            }
        }
        if (docs.size() > 0) {
            ArrayList<DocAvailableInfo> uniqueDocs = new ArrayList();
            it2 = docs.iterator();
            while (it2.hasNext()) {
                DocAvailableInfo d = (DocAvailableInfo) it2.next();
                if (!(freeCache == null || d == null || freeCache.containsKey(d))) {
                    uniqueDocs.add(d);
                }
            }
            addDocs(uniqueDocs);
            HandleRequest.getNew(ctx, new C34652()).checkUrl(uniqueDocs, dialog_id);
        }
    }

    public static void checkMessagesInternalNew(Context ctx, TLRPC$messages_Messages messagesRes) {
        ArrayList<DocAvailableInfo> docs = new ArrayList();
        Iterator it = messagesRes.messages.iterator();
        while (it.hasNext()) {
            long channelID;
            TLRPC$PhotoSize tps;
            ArrayList<DocAvailableInfo> arrayList;
            Iterator it2;
            TLRPC$PhotoSize ps;
            TLRPC$Message message = (TLRPC$Message) it.next();
            boolean shouldAdd = false;
            if (null == null) {
                try {
                    if (message.to_id.channel_id == 0) {
                        shouldAdd = false;
                        if (!shouldAdd) {
                            try {
                                if (message.fwd_from.channel_id == 0) {
                                    channelID = (long) message.to_id.channel_id;
                                    if (message.fwd_from.channel_id != 0) {
                                        channelID = (long) message.fwd_from.channel_id;
                                    }
                                    docs.add(new DocAvailableInfo(message.media.document.id, 0, 0, message.media.document.size, channelID, null, (long) message.id));
                                    tps = message.media.document.thumb;
                                    arrayList = docs;
                                    arrayList.add(new DocAvailableInfo(0, tps.location.local_id, tps.location.volume_id, 0, channelID, null, (long) message.id));
                                    it2 = message.media.photo.sizes.iterator();
                                    while (it2.hasNext()) {
                                        ps = (TLRPC$PhotoSize) it2.next();
                                        arrayList = docs;
                                        arrayList.add(new DocAvailableInfo(0, ps.location.local_id, ps.location.volume_id, 0, channelID, null, (long) message.id));
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        channelID = (long) message.to_id.channel_id;
                        if (message.fwd_from.channel_id != 0) {
                            channelID = (long) message.fwd_from.channel_id;
                        }
                        docs.add(new DocAvailableInfo(message.media.document.id, 0, 0, message.media.document.size, channelID, null, (long) message.id));
                        tps = message.media.document.thumb;
                        arrayList = docs;
                        arrayList.add(new DocAvailableInfo(0, tps.location.local_id, tps.location.volume_id, 0, channelID, null, (long) message.id));
                        it2 = message.media.photo.sizes.iterator();
                        while (it2.hasNext()) {
                            ps = (TLRPC$PhotoSize) it2.next();
                            arrayList = docs;
                            arrayList.add(new DocAvailableInfo(0, ps.location.local_id, ps.location.volume_id, 0, channelID, null, (long) message.id));
                        }
                    }
                } catch (Exception e2) {
                }
            }
            shouldAdd = true;
            if (shouldAdd) {
                if (message.fwd_from.channel_id == 0) {
                    channelID = (long) message.to_id.channel_id;
                    if (message.fwd_from.channel_id != 0) {
                        channelID = (long) message.fwd_from.channel_id;
                    }
                    docs.add(new DocAvailableInfo(message.media.document.id, 0, 0, message.media.document.size, channelID, null, (long) message.id));
                    tps = message.media.document.thumb;
                    arrayList = docs;
                    arrayList.add(new DocAvailableInfo(0, tps.location.local_id, tps.location.volume_id, 0, channelID, null, (long) message.id));
                    it2 = message.media.photo.sizes.iterator();
                    while (it2.hasNext()) {
                        ps = (TLRPC$PhotoSize) it2.next();
                        arrayList = docs;
                        arrayList.add(new DocAvailableInfo(0, ps.location.local_id, ps.location.volume_id, 0, channelID, null, (long) message.id));
                    }
                }
            }
            channelID = (long) message.to_id.channel_id;
            try {
                if (message.fwd_from.channel_id != 0) {
                    channelID = (long) message.fwd_from.channel_id;
                }
            } catch (Exception e3) {
            }
            try {
                docs.add(new DocAvailableInfo(message.media.document.id, 0, 0, message.media.document.size, channelID, null, (long) message.id));
            } catch (Exception e4) {
            }
            try {
                tps = message.media.document.thumb;
                arrayList = docs;
                arrayList.add(new DocAvailableInfo(0, tps.location.local_id, tps.location.volume_id, 0, channelID, null, (long) message.id));
            } catch (Exception e5) {
            }
            try {
                it2 = message.media.photo.sizes.iterator();
                while (it2.hasNext()) {
                    ps = (TLRPC$PhotoSize) it2.next();
                    arrayList = docs;
                    arrayList.add(new DocAvailableInfo(0, ps.location.local_id, ps.location.volume_id, 0, channelID, null, (long) message.id));
                }
            } catch (Exception e6) {
            }
        }
        if (docs.size() > 0) {
            HandleRequest.getNew(ctx, new C34663()).checkUrl(docs);
        }
    }

    public static void updateAll(Context context) {
        FileLog.d("cchange updateAll 1");
        if (context != null && freeCacheData != null && !freeCacheData.isEmpty()) {
            FileLog.d("cchange updateAll 2");
            ArrayList<DocAvailableInfo> docs = new ArrayList();
            for (Entry<DocAvailableInfo, DocAvailableInfo> map : freeCacheData.entrySet()) {
                docs.add(map.getValue());
            }
            FileLog.d("cchange updateAll 3 " + docs.size());
            if (docs.size() > 0) {
                FileLog.d("cchange updateAll 4 ");
                HandleRequest.getNew(context, new C34674()).checkUrl(docs);
            }
        }
    }
}
