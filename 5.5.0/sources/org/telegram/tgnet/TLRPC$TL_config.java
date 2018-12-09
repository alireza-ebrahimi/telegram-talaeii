package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_config extends TLObject {
    public static int constructor = -1669068444;
    public int call_connect_timeout_ms;
    public int call_packet_timeout_ms;
    public int call_receive_timeout_ms;
    public int call_ring_timeout_ms;
    public int channels_read_media_period;
    public int chat_big_size;
    public int chat_size_max;
    public int date;
    public ArrayList<TLRPC$TL_dcOption> dc_options = new ArrayList();
    public boolean default_p2p_contacts;
    public ArrayList<TLRPC$TL_disabledFeature> disabled_features = new ArrayList();
    public int edit_time_limit;
    public int expires;
    public int flags;
    public int forwarded_count_max;
    public int lang_pack_version;
    public String me_url_prefix;
    public int megagroup_size_max;
    public int notify_cloud_delay_ms;
    public int notify_default_delay_ms;
    public int offline_blur_timeout_ms;
    public int offline_idle_timeout_ms;
    public int online_cloud_timeout_ms;
    public int online_update_period_ms;
    public boolean phonecalls_enabled;
    public int pinned_dialogs_count_max;
    public int push_chat_limit;
    public int push_chat_period_ms;
    public int rating_e_decay;
    public int saved_gifs_limit;
    public int stickers_faved_limit;
    public int stickers_recent_limit;
    public String suggested_lang_code;
    public boolean test_mode;
    public int this_dc;
    public int tmp_sessions;

    public static TLRPC$TL_config TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_config tLRPC$TL_config = new TLRPC$TL_config();
            tLRPC$TL_config.readParams(abstractSerializedData, z);
            return tLRPC$TL_config;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_config", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.flags = abstractSerializedData.readInt32(z);
        this.phonecalls_enabled = (this.flags & 2) != 0;
        this.default_p2p_contacts = (this.flags & 8) != 0;
        this.date = abstractSerializedData.readInt32(z);
        this.expires = abstractSerializedData.readInt32(z);
        this.test_mode = abstractSerializedData.readBool(z);
        this.this_dc = abstractSerializedData.readInt32(z);
        int i2;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            int readInt32 = abstractSerializedData.readInt32(z);
            i2 = 0;
            while (i2 < readInt32) {
                TLRPC$TL_dcOption TLdeserialize = TLRPC$TL_dcOption.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.dc_options.add(TLdeserialize);
                    i2++;
                } else {
                    return;
                }
            }
            this.chat_size_max = abstractSerializedData.readInt32(z);
            this.megagroup_size_max = abstractSerializedData.readInt32(z);
            this.forwarded_count_max = abstractSerializedData.readInt32(z);
            this.online_update_period_ms = abstractSerializedData.readInt32(z);
            this.offline_blur_timeout_ms = abstractSerializedData.readInt32(z);
            this.offline_idle_timeout_ms = abstractSerializedData.readInt32(z);
            this.online_cloud_timeout_ms = abstractSerializedData.readInt32(z);
            this.notify_cloud_delay_ms = abstractSerializedData.readInt32(z);
            this.notify_default_delay_ms = abstractSerializedData.readInt32(z);
            this.chat_big_size = abstractSerializedData.readInt32(z);
            this.push_chat_period_ms = abstractSerializedData.readInt32(z);
            this.push_chat_limit = abstractSerializedData.readInt32(z);
            this.saved_gifs_limit = abstractSerializedData.readInt32(z);
            this.edit_time_limit = abstractSerializedData.readInt32(z);
            this.rating_e_decay = abstractSerializedData.readInt32(z);
            this.stickers_recent_limit = abstractSerializedData.readInt32(z);
            this.stickers_faved_limit = abstractSerializedData.readInt32(z);
            this.channels_read_media_period = abstractSerializedData.readInt32(z);
            if ((this.flags & 1) != 0) {
                this.tmp_sessions = abstractSerializedData.readInt32(z);
            }
            this.pinned_dialogs_count_max = abstractSerializedData.readInt32(z);
            this.call_receive_timeout_ms = abstractSerializedData.readInt32(z);
            this.call_ring_timeout_ms = abstractSerializedData.readInt32(z);
            this.call_connect_timeout_ms = abstractSerializedData.readInt32(z);
            this.call_packet_timeout_ms = abstractSerializedData.readInt32(z);
            this.me_url_prefix = abstractSerializedData.readString(z);
            if ((this.flags & 4) != 0) {
                this.suggested_lang_code = abstractSerializedData.readString(z);
            }
            if ((this.flags & 4) != 0) {
                this.lang_pack_version = abstractSerializedData.readInt32(z);
            }
            if (abstractSerializedData.readInt32(z) == 481674261) {
                i2 = abstractSerializedData.readInt32(z);
                while (i < i2) {
                    TLRPC$TL_disabledFeature TLdeserialize2 = TLRPC$TL_disabledFeature.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize2 != null) {
                        this.disabled_features.add(TLdeserialize2);
                        i++;
                    } else {
                        return;
                    }
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        int i;
        int i2 = 0;
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.phonecalls_enabled ? this.flags | 2 : this.flags & -3;
        this.flags = this.default_p2p_contacts ? this.flags | 8 : this.flags & -9;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.date);
        abstractSerializedData.writeInt32(this.expires);
        abstractSerializedData.writeBool(this.test_mode);
        abstractSerializedData.writeInt32(this.this_dc);
        abstractSerializedData.writeInt32(481674261);
        int size = this.dc_options.size();
        abstractSerializedData.writeInt32(size);
        for (i = 0; i < size; i++) {
            ((TLRPC$TL_dcOption) this.dc_options.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(this.chat_size_max);
        abstractSerializedData.writeInt32(this.megagroup_size_max);
        abstractSerializedData.writeInt32(this.forwarded_count_max);
        abstractSerializedData.writeInt32(this.online_update_period_ms);
        abstractSerializedData.writeInt32(this.offline_blur_timeout_ms);
        abstractSerializedData.writeInt32(this.offline_idle_timeout_ms);
        abstractSerializedData.writeInt32(this.online_cloud_timeout_ms);
        abstractSerializedData.writeInt32(this.notify_cloud_delay_ms);
        abstractSerializedData.writeInt32(this.notify_default_delay_ms);
        abstractSerializedData.writeInt32(this.chat_big_size);
        abstractSerializedData.writeInt32(this.push_chat_period_ms);
        abstractSerializedData.writeInt32(this.push_chat_limit);
        abstractSerializedData.writeInt32(this.saved_gifs_limit);
        abstractSerializedData.writeInt32(this.edit_time_limit);
        abstractSerializedData.writeInt32(this.rating_e_decay);
        abstractSerializedData.writeInt32(this.stickers_recent_limit);
        abstractSerializedData.writeInt32(this.stickers_faved_limit);
        abstractSerializedData.writeInt32(this.channels_read_media_period);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.tmp_sessions);
        }
        abstractSerializedData.writeInt32(this.pinned_dialogs_count_max);
        abstractSerializedData.writeInt32(this.call_receive_timeout_ms);
        abstractSerializedData.writeInt32(this.call_ring_timeout_ms);
        abstractSerializedData.writeInt32(this.call_connect_timeout_ms);
        abstractSerializedData.writeInt32(this.call_packet_timeout_ms);
        abstractSerializedData.writeString(this.me_url_prefix);
        if ((this.flags & 4) != 0) {
            abstractSerializedData.writeString(this.suggested_lang_code);
        }
        if ((this.flags & 4) != 0) {
            abstractSerializedData.writeInt32(this.lang_pack_version);
        }
        abstractSerializedData.writeInt32(481674261);
        i = this.disabled_features.size();
        abstractSerializedData.writeInt32(i);
        while (i2 < i) {
            ((TLRPC$TL_disabledFeature) this.disabled_features.get(i2)).serializeToStream(abstractSerializedData);
            i2++;
        }
    }
}
