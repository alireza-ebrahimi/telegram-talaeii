package org.telegram.tgnet;

import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.source.ExtractorMediaSource;
import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_user extends User {
    public static int constructor = 773059779;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        boolean z2 = true;
        this.flags = abstractSerializedData.readInt32(z);
        this.self = (this.flags & 1024) != 0;
        this.contact = (this.flags & 2048) != 0;
        this.mutual_contact = (this.flags & 4096) != 0;
        this.deleted = (this.flags & MessagesController.UPDATE_MASK_CHANNEL) != 0;
        this.bot = (this.flags & MessagesController.UPDATE_MASK_CHAT_ADMINS) != 0;
        this.bot_chat_history = (this.flags & TLRPC.MESSAGE_FLAG_EDITED) != 0;
        this.bot_nochats = (this.flags & C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) != 0;
        this.verified = (this.flags & 131072) != 0;
        this.restricted = (this.flags & 262144) != 0;
        this.min = (this.flags & ExtractorMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES) != 0;
        if ((this.flags & 2097152) == 0) {
            z2 = false;
        }
        this.bot_inline_geo = z2;
        this.id = abstractSerializedData.readInt32(z);
        if ((this.flags & 1) != 0) {
            this.access_hash = abstractSerializedData.readInt64(z);
        }
        if ((this.flags & 2) != 0) {
            this.first_name = abstractSerializedData.readString(z);
        }
        if ((this.flags & 4) != 0) {
            this.last_name = abstractSerializedData.readString(z);
        }
        if ((this.flags & 8) != 0) {
            this.username = abstractSerializedData.readString(z);
        }
        if ((this.flags & 16) != 0) {
            this.phone = abstractSerializedData.readString(z);
        }
        if ((this.flags & 32) != 0) {
            this.photo = TLRPC$UserProfilePhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 64) != 0) {
            this.status = TLRPC$UserStatus.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & MessagesController.UPDATE_MASK_CHAT_ADMINS) != 0) {
            this.bot_info_version = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 262144) != 0) {
            this.restriction_reason = abstractSerializedData.readString(z);
        }
        if ((this.flags & 524288) != 0) {
            this.bot_inline_placeholder = abstractSerializedData.readString(z);
        }
        if ((this.flags & 4194304) != 0) {
            this.lang_code = abstractSerializedData.readString(z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.self ? this.flags | 1024 : this.flags & -1025;
        this.flags = this.contact ? this.flags | 2048 : this.flags & -2049;
        this.flags = this.mutual_contact ? this.flags | 4096 : this.flags & -4097;
        this.flags = this.deleted ? this.flags | MessagesController.UPDATE_MASK_CHANNEL : this.flags & -8193;
        this.flags = this.bot ? this.flags | MessagesController.UPDATE_MASK_CHAT_ADMINS : this.flags & -16385;
        this.flags = this.bot_chat_history ? this.flags | TLRPC.MESSAGE_FLAG_EDITED : this.flags & -32769;
        this.flags = this.bot_nochats ? this.flags | C3446C.DEFAULT_BUFFER_SEGMENT_SIZE : this.flags & -65537;
        this.flags = this.verified ? this.flags | 131072 : this.flags & -131073;
        this.flags = this.restricted ? this.flags | 262144 : this.flags & -262145;
        this.flags = this.min ? this.flags | ExtractorMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES : this.flags & -1048577;
        this.flags = this.bot_inline_geo ? this.flags | 2097152 : this.flags & -2097153;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.id);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt64(this.access_hash);
        }
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeString(this.first_name);
        }
        if ((this.flags & 4) != 0) {
            abstractSerializedData.writeString(this.last_name);
        }
        if ((this.flags & 8) != 0) {
            abstractSerializedData.writeString(this.username);
        }
        if ((this.flags & 16) != 0) {
            abstractSerializedData.writeString(this.phone);
        }
        if ((this.flags & 32) != 0) {
            this.photo.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 64) != 0) {
            this.status.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & MessagesController.UPDATE_MASK_CHAT_ADMINS) != 0) {
            abstractSerializedData.writeInt32(this.bot_info_version);
        }
        if ((this.flags & 262144) != 0) {
            abstractSerializedData.writeString(this.restriction_reason);
        }
        if ((this.flags & 524288) != 0) {
            abstractSerializedData.writeString(this.bot_inline_placeholder);
        }
        if ((this.flags & 4194304) != 0) {
            abstractSerializedData.writeString(this.lang_code);
        }
    }
}
