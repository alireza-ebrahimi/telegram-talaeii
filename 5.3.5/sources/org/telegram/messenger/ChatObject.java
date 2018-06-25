package org.telegram.messenger;

import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$TL_channel;
import org.telegram.tgnet.TLRPC$TL_channelForbidden;
import org.telegram.tgnet.TLRPC$TL_chatEmpty;
import org.telegram.tgnet.TLRPC$TL_chatForbidden;

public class ChatObject {
    public static final int CHAT_TYPE_BROADCAST = 1;
    public static final int CHAT_TYPE_CHANNEL = 2;
    public static final int CHAT_TYPE_CHAT = 0;
    public static final int CHAT_TYPE_MEGAGROUP = 4;
    public static final int CHAT_TYPE_USER = 3;

    public static boolean isLeftFromChat(TLRPC$Chat chat) {
        return chat == null || (chat instanceof TLRPC$TL_chatEmpty) || (chat instanceof TLRPC$TL_chatForbidden) || (chat instanceof TLRPC$TL_channelForbidden) || chat.left || chat.deactivated;
    }

    public static boolean isKickedFromChat(TLRPC$Chat chat) {
        return chat == null || (chat instanceof TLRPC$TL_chatEmpty) || (chat instanceof TLRPC$TL_chatForbidden) || (chat instanceof TLRPC$TL_channelForbidden) || chat.kicked || chat.deactivated || (chat.banned_rights != null && chat.banned_rights.view_messages);
    }

    public static boolean isNotInChat(TLRPC$Chat chat) {
        return chat == null || (chat instanceof TLRPC$TL_chatEmpty) || (chat instanceof TLRPC$TL_chatForbidden) || (chat instanceof TLRPC$TL_channelForbidden) || chat.left || chat.kicked || chat.deactivated;
    }

    public static boolean isChannel(TLRPC$Chat chat) {
        return (chat instanceof TLRPC$TL_channel) || (chat instanceof TLRPC$TL_channelForbidden);
    }

    public static boolean isMegagroup(TLRPC$Chat chat) {
        return ((chat instanceof TLRPC$TL_channel) || (chat instanceof TLRPC$TL_channelForbidden)) && chat.megagroup;
    }

    public static boolean hasAdminRights(TLRPC$Chat chat) {
        return chat != null && (chat.creator || !(chat.admin_rights == null || chat.admin_rights.flags == 0));
    }

    public static boolean canChangeChatInfo(TLRPC$Chat chat) {
        return chat != null && (chat.creator || (chat.admin_rights != null && chat.admin_rights.change_info));
    }

    public static boolean canAddAdmins(TLRPC$Chat chat) {
        return chat != null && (chat.creator || (chat.admin_rights != null && chat.admin_rights.add_admins));
    }

    public static boolean canBlockUsers(TLRPC$Chat chat) {
        return chat != null && (chat.creator || (chat.admin_rights != null && chat.admin_rights.ban_users));
    }

    public static boolean canSendStickers(TLRPC$Chat chat) {
        return chat == null || (chat != null && (chat.banned_rights == null || !(chat.banned_rights.send_media || chat.banned_rights.send_stickers)));
    }

    public static boolean canSendEmbed(TLRPC$Chat chat) {
        return chat == null || (chat != null && (chat.banned_rights == null || !(chat.banned_rights.send_media || chat.banned_rights.embed_links)));
    }

    public static boolean canSendMessages(TLRPC$Chat chat) {
        return chat == null || (chat != null && (chat.banned_rights == null || !chat.banned_rights.send_messages));
    }

    public static boolean canPost(TLRPC$Chat chat) {
        return chat != null && (chat.creator || (chat.admin_rights != null && chat.admin_rights.post_messages));
    }

    public static boolean canAddViaLink(TLRPC$Chat chat) {
        return chat != null && (chat.creator || (chat.admin_rights != null && chat.admin_rights.invite_link));
    }

    public static boolean canAddUsers(TLRPC$Chat chat) {
        return chat != null && (chat.creator || (chat.admin_rights != null && chat.admin_rights.invite_users));
    }

    public static boolean canEditInfo(TLRPC$Chat chat) {
        return chat != null && (chat.creator || (chat.admin_rights != null && chat.admin_rights.change_info));
    }

    public static boolean isChannel(int chatId) {
        TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(chatId));
        return (chat instanceof TLRPC$TL_channel) || (chat instanceof TLRPC$TL_channelForbidden);
    }

    public static boolean isCanWriteToChannel(int chatId) {
        TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(chatId));
        return chat != null && (chat.creator || ((chat.admin_rights != null && chat.admin_rights.post_messages) || chat.megagroup));
    }

    public static boolean canWriteToChat(TLRPC$Chat chat) {
        return !isChannel(chat) || chat.creator || ((chat.admin_rights != null && chat.admin_rights.post_messages) || !chat.broadcast);
    }

    public static TLRPC$Chat getChatByDialog(long did) {
        int lower_id = (int) did;
        int high_id = (int) (did >> 32);
        if (lower_id < 0) {
            return MessagesController.getInstance().getChat(Integer.valueOf(-lower_id));
        }
        return null;
    }
}
