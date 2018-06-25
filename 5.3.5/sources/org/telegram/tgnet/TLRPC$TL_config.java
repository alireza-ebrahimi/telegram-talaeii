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

    public static TLRPC$TL_config TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_config result = new TLRPC$TL_config();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_config", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.phonecalls_enabled = z;
        if ((this.flags & 8) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.default_p2p_contacts = z;
        this.date = stream.readInt32(exception);
        this.expires = stream.readInt32(exception);
        this.test_mode = stream.readBool(exception);
        this.this_dc = stream.readInt32(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$TL_dcOption object = TLRPC$TL_dcOption.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.dc_options.add(object);
                    a++;
                } else {
                    return;
                }
            }
            this.chat_size_max = stream.readInt32(exception);
            this.megagroup_size_max = stream.readInt32(exception);
            this.forwarded_count_max = stream.readInt32(exception);
            this.online_update_period_ms = stream.readInt32(exception);
            this.offline_blur_timeout_ms = stream.readInt32(exception);
            this.offline_idle_timeout_ms = stream.readInt32(exception);
            this.online_cloud_timeout_ms = stream.readInt32(exception);
            this.notify_cloud_delay_ms = stream.readInt32(exception);
            this.notify_default_delay_ms = stream.readInt32(exception);
            this.chat_big_size = stream.readInt32(exception);
            this.push_chat_period_ms = stream.readInt32(exception);
            this.push_chat_limit = stream.readInt32(exception);
            this.saved_gifs_limit = stream.readInt32(exception);
            this.edit_time_limit = stream.readInt32(exception);
            this.rating_e_decay = stream.readInt32(exception);
            this.stickers_recent_limit = stream.readInt32(exception);
            this.stickers_faved_limit = stream.readInt32(exception);
            this.channels_read_media_period = stream.readInt32(exception);
            if ((this.flags & 1) != 0) {
                this.tmp_sessions = stream.readInt32(exception);
            }
            this.pinned_dialogs_count_max = stream.readInt32(exception);
            this.call_receive_timeout_ms = stream.readInt32(exception);
            this.call_ring_timeout_ms = stream.readInt32(exception);
            this.call_connect_timeout_ms = stream.readInt32(exception);
            this.call_packet_timeout_ms = stream.readInt32(exception);
            this.me_url_prefix = stream.readString(exception);
            if ((this.flags & 4) != 0) {
                this.suggested_lang_code = stream.readString(exception);
            }
            if ((this.flags & 4) != 0) {
                this.lang_pack_version = stream.readInt32(exception);
            }
            if (stream.readInt32(exception) == 481674261) {
                count = stream.readInt32(exception);
                a = 0;
                while (a < count) {
                    TLRPC$TL_disabledFeature object2 = TLRPC$TL_disabledFeature.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object2 != null) {
                        this.disabled_features.add(object2);
                        a++;
                    } else {
                        return;
                    }
                }
            } else if (exception) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int i;
        int a;
        stream.writeInt32(constructor);
        this.flags = this.phonecalls_enabled ? this.flags | 2 : this.flags & -3;
        if (this.default_p2p_contacts) {
            i = this.flags | 8;
        } else {
            i = this.flags & -9;
        }
        this.flags = i;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.date);
        stream.writeInt32(this.expires);
        stream.writeBool(this.test_mode);
        stream.writeInt32(this.this_dc);
        stream.writeInt32(481674261);
        int count = this.dc_options.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$TL_dcOption) this.dc_options.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(this.chat_size_max);
        stream.writeInt32(this.megagroup_size_max);
        stream.writeInt32(this.forwarded_count_max);
        stream.writeInt32(this.online_update_period_ms);
        stream.writeInt32(this.offline_blur_timeout_ms);
        stream.writeInt32(this.offline_idle_timeout_ms);
        stream.writeInt32(this.online_cloud_timeout_ms);
        stream.writeInt32(this.notify_cloud_delay_ms);
        stream.writeInt32(this.notify_default_delay_ms);
        stream.writeInt32(this.chat_big_size);
        stream.writeInt32(this.push_chat_period_ms);
        stream.writeInt32(this.push_chat_limit);
        stream.writeInt32(this.saved_gifs_limit);
        stream.writeInt32(this.edit_time_limit);
        stream.writeInt32(this.rating_e_decay);
        stream.writeInt32(this.stickers_recent_limit);
        stream.writeInt32(this.stickers_faved_limit);
        stream.writeInt32(this.channels_read_media_period);
        if ((this.flags & 1) != 0) {
            stream.writeInt32(this.tmp_sessions);
        }
        stream.writeInt32(this.pinned_dialogs_count_max);
        stream.writeInt32(this.call_receive_timeout_ms);
        stream.writeInt32(this.call_ring_timeout_ms);
        stream.writeInt32(this.call_connect_timeout_ms);
        stream.writeInt32(this.call_packet_timeout_ms);
        stream.writeString(this.me_url_prefix);
        if ((this.flags & 4) != 0) {
            stream.writeString(this.suggested_lang_code);
        }
        if ((this.flags & 4) != 0) {
            stream.writeInt32(this.lang_pack_version);
        }
        stream.writeInt32(481674261);
        count = this.disabled_features.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$TL_disabledFeature) this.disabled_features.get(a)).serializeToStream(stream);
        }
    }
}
