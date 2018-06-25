package org.telegram.tgnet;

import com.google.gson.annotations.SerializedName;

public class TLRPC {
    public static final int CHAT_FLAG_IS_PUBLIC = 64;
    public static final int LAYER = 73;
    public static final int MESSAGE_FLAG_EDITED = 32768;
    public static final int MESSAGE_FLAG_FWD = 4;
    public static final int MESSAGE_FLAG_HAS_BOT_ID = 2048;
    public static final int MESSAGE_FLAG_HAS_ENTITIES = 128;
    public static final int MESSAGE_FLAG_HAS_FROM_ID = 256;
    public static final int MESSAGE_FLAG_HAS_MARKUP = 64;
    public static final int MESSAGE_FLAG_HAS_MEDIA = 512;
    public static final int MESSAGE_FLAG_HAS_VIEWS = 1024;
    public static final int MESSAGE_FLAG_MEGAGROUP = Integer.MIN_VALUE;
    public static final int MESSAGE_FLAG_REPLY = 8;
    public static final int USER_FLAG_ACCESS_HASH = 1;
    public static final int USER_FLAG_FIRST_NAME = 2;
    public static final int USER_FLAG_LAST_NAME = 4;
    public static final int USER_FLAG_PHONE = 16;
    public static final int USER_FLAG_PHOTO = 32;
    public static final int USER_FLAG_STATUS = 64;
    public static final int USER_FLAG_UNUSED = 128;
    public static final int USER_FLAG_UNUSED2 = 256;
    public static final int USER_FLAG_UNUSED3 = 512;
    public static final int USER_FLAG_USERNAME = 8;

    public static class DocumentAttribute extends TLObject {
        public String alt;
        @SerializedName("documentTitle")
        public String documentTitle;
        public int duration;
        public String file_name;
        public int flags;
        /* renamed from: h */
        public int f43h;
        public boolean mask;
        public TLRPC$TL_maskCoords mask_coords;
        public String performer;
        public boolean round_message;
        public TLRPC$InputStickerSet stickerset;
        public String title;
        public boolean voice;
        /* renamed from: w */
        public int f44w;
        public byte[] waveform;

        public String getDocumentTitle() {
            return this.documentTitle;
        }

        public void setDocumentTitle(String documentTitle) {
            this.documentTitle = documentTitle;
        }

        public static DocumentAttribute TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
            DocumentAttribute result = null;
            switch (constructor) {
                case -1744710921:
                    result = new TLRPC$TL_documentAttributeHasStickers();
                    break;
                case -1739392570:
                    result = new TLRPC$TL_documentAttributeAudio();
                    break;
                case -1723033470:
                    result = new TLRPC$TL_documentAttributeSticker_old2();
                    break;
                case -556656416:
                    result = new TLRPC$TL_documentAttributeAudio_layer45();
                    break;
                case -83208409:
                    result = new TLRPC$TL_documentAttributeSticker_old();
                    break;
                case 85215461:
                    result = new TLRPC$TL_documentAttributeAudio_old();
                    break;
                case 250621158:
                    result = new TLRPC$TL_documentAttributeVideo();
                    break;
                case 297109817:
                    result = new TLRPC$TL_documentAttributeAnimated();
                    break;
                case 358154344:
                    result = new TLRPC$TL_documentAttributeFilename();
                    break;
                case 978674434:
                    result = new TLRPC$TL_documentAttributeSticker_layer55();
                    break;
                case 1494273227:
                    result = new TLRPC$TL_documentAttributeVideo_layer65();
                    break;
                case 1662637586:
                    result = new TLRPC$TL_documentAttributeSticker();
                    break;
                case 1815593308:
                    result = new TLRPC$TL_documentAttributeImageSize();
                    break;
            }
            if (result == null && exception) {
                throw new RuntimeException(String.format("can't parse magic %x in DocumentAttribute", new Object[]{Integer.valueOf(constructor)}));
            }
            if (result != null) {
                result.readParams(stream, exception);
            }
            return result;
        }
    }

    public static class User extends TLObject {
        public long access_hash;
        public boolean bot;
        public boolean bot_chat_history;
        public int bot_info_version;
        public boolean bot_inline_geo;
        public String bot_inline_placeholder;
        public boolean bot_nochats;
        public boolean contact;
        public boolean deleted;
        public boolean explicit_content;
        public String first_name;
        public int flags;
        public int id;
        public boolean inactive;
        public String lang_code;
        public String last_name;
        public boolean min;
        public boolean mutual_contact;
        public String phone;
        public TLRPC$UserProfilePhoto photo;
        public boolean restricted;
        public String restriction_reason;
        public boolean self;
        public TLRPC$UserStatus status;
        public String username;
        public boolean verified;

        public static User TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
            User result = null;
            switch (constructor) {
                case -1298475060:
                    result = new TLRPC$TL_userDeleted_old();
                    break;
                case -894214632:
                    result = new TLRPC$TL_userContact_old2();
                    break;
                case -787638374:
                    result = new TLRPC$TL_user_layer65();
                    break;
                case -704549510:
                    result = new TLRPC$TL_userDeleted_old2();
                    break;
                case -640891665:
                    result = new TLRPC$TL_userRequest_old2();
                    break;
                case -218397927:
                    result = new TLRPC$TL_userContact_old();
                    break;
                case 123533224:
                    result = new TLRPC$TL_userForeign_old2();
                    break;
                case 476112392:
                    result = new TLRPC$TL_userSelf_old3();
                    break;
                case 537022650:
                    result = new TLRPC$TL_userEmpty();
                    break;
                case 585404530:
                    result = new TLRPC$TL_user_old();
                    break;
                case 585682608:
                    result = new TLRPC$TL_userRequest_old();
                    break;
                case 773059779:
                    result = new TLRPC$TL_user();
                    break;
                case 1377093789:
                    result = new TLRPC$TL_userForeign_old();
                    break;
                case 1879553105:
                    result = new TLRPC$TL_userSelf_old2();
                    break;
                case 1912944108:
                    result = new TLRPC$TL_userSelf_old();
                    break;
            }
            if (result == null && exception) {
                throw new RuntimeException(String.format("can't parse magic %x in User", new Object[]{Integer.valueOf(constructor)}));
            }
            if (result != null) {
                result.readParams(stream, exception);
            }
            return result;
        }
    }
}
