package org.telegram.messenger;

import android.content.SharedPreferences.Editor;
import android.util.Base64;
import java.util.Iterator;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLRPC$TL_config;
import org.telegram.tgnet.TLRPC$TL_disabledFeature;

class MessagesController$4 implements Runnable {
    final /* synthetic */ MessagesController this$0;
    final /* synthetic */ TLRPC$TL_config val$config;

    MessagesController$4(MessagesController this$0, TLRPC$TL_config tLRPC$TL_config) {
        this.this$0 = this$0;
        this.val$config = tLRPC$TL_config;
    }

    public void run() {
        LocaleController.getInstance().loadRemoteLanguages();
        this.this$0.maxMegagroupCount = this.val$config.megagroup_size_max;
        this.this$0.maxGroupCount = this.val$config.chat_size_max;
        this.this$0.groupBigSize = this.val$config.chat_big_size;
        MessagesController.access$202(this.this$0, this.val$config.disabled_features);
        this.this$0.maxEditTime = this.val$config.edit_time_limit;
        this.this$0.ratingDecay = this.val$config.rating_e_decay;
        this.this$0.maxRecentGifsCount = this.val$config.saved_gifs_limit;
        this.this$0.maxRecentStickersCount = this.val$config.stickers_recent_limit;
        this.this$0.maxFaveStickersCount = this.val$config.stickers_faved_limit;
        this.this$0.linkPrefix = this.val$config.me_url_prefix;
        if (this.this$0.linkPrefix.endsWith("/")) {
            this.this$0.linkPrefix = this.this$0.linkPrefix.substring(0, this.this$0.linkPrefix.length() - 1);
        }
        if (this.this$0.linkPrefix.startsWith("https://")) {
            this.this$0.linkPrefix = this.this$0.linkPrefix.substring(8);
        } else if (this.this$0.linkPrefix.startsWith("http://")) {
            this.this$0.linkPrefix = this.this$0.linkPrefix.substring(7);
        }
        this.this$0.callReceiveTimeout = this.val$config.call_receive_timeout_ms;
        this.this$0.callRingTimeout = this.val$config.call_ring_timeout_ms;
        this.this$0.callConnectTimeout = this.val$config.call_connect_timeout_ms;
        this.this$0.callPacketTimeout = this.val$config.call_packet_timeout_ms;
        this.this$0.maxPinnedDialogsCount = this.val$config.pinned_dialogs_count_max;
        this.this$0.defaultP2pContacts = this.val$config.default_p2p_contacts;
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        editor.putInt("maxGroupCount", this.this$0.maxGroupCount);
        editor.putInt("maxMegagroupCount", this.this$0.maxMegagroupCount);
        editor.putInt("groupBigSize", this.this$0.groupBigSize);
        editor.putInt("maxEditTime", this.this$0.maxEditTime);
        editor.putInt("ratingDecay", this.this$0.ratingDecay);
        editor.putInt("maxRecentGifsCount", this.this$0.maxRecentGifsCount);
        editor.putInt("maxRecentStickersCount", this.this$0.maxRecentStickersCount);
        editor.putInt("maxFaveStickersCount", this.this$0.maxFaveStickersCount);
        editor.putInt("callReceiveTimeout", this.this$0.callReceiveTimeout);
        editor.putInt("callRingTimeout", this.this$0.callRingTimeout);
        editor.putInt("callConnectTimeout", this.this$0.callConnectTimeout);
        editor.putInt("callPacketTimeout", this.this$0.callPacketTimeout);
        editor.putString("linkPrefix", this.this$0.linkPrefix);
        editor.putInt("maxPinnedDialogsCount", this.this$0.maxPinnedDialogsCount);
        editor.putBoolean("defaultP2pContacts", this.this$0.defaultP2pContacts);
        try {
            SerializedData data = new SerializedData();
            data.writeInt32(MessagesController.access$200(this.this$0).size());
            Iterator it = MessagesController.access$200(this.this$0).iterator();
            while (it.hasNext()) {
                ((TLRPC$TL_disabledFeature) it.next()).serializeToStream(data);
            }
            String string = Base64.encodeToString(data.toByteArray(), 0);
            if (string.length() != 0) {
                editor.putString("disabledFeatures", string);
            }
        } catch (Exception e) {
            editor.remove("disabledFeatures");
            FileLog.e(e);
        }
        editor.commit();
    }
}
