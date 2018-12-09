package org.telegram.messenger;

import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.TL_channel;
import org.telegram.tgnet.TLRPC.TL_channelForbidden;
import org.telegram.tgnet.TLRPC.TL_chatEmpty;
import org.telegram.tgnet.TLRPC.TL_chatForbidden;

public class ChatObject {
    public static final int CHAT_TYPE_BROADCAST = 1;
    public static final int CHAT_TYPE_CHANNEL = 2;
    public static final int CHAT_TYPE_CHAT = 0;
    public static final int CHAT_TYPE_MEGAGROUP = 4;
    public static final int CHAT_TYPE_USER = 3;

    public static boolean canAddAdmins(Chat chat) {
        return chat != null && (chat.creator || (chat.admin_rights != null && chat.admin_rights.add_admins));
    }

    public static boolean canAddUsers(Chat chat) {
        return chat != null && (chat.creator || (chat.admin_rights != null && chat.admin_rights.invite_users));
    }

    public static boolean canAddViaLink(Chat chat) {
        return chat != null && (chat.creator || (chat.admin_rights != null && chat.admin_rights.invite_link));
    }

    public static boolean canBlockUsers(Chat chat) {
        return chat != null && (chat.creator || (chat.admin_rights != null && chat.admin_rights.ban_users));
    }

    public static boolean canChangeChatInfo(Chat chat) {
        return chat != null && (chat.creator || (chat.admin_rights != null && chat.admin_rights.change_info));
    }

    public static boolean canEditInfo(Chat chat) {
        return chat != null && (chat.creator || (chat.admin_rights != null && chat.admin_rights.change_info));
    }

    public static boolean canPost(Chat chat) {
        return chat != null && (chat.creator || (chat.admin_rights != null && chat.admin_rights.post_messages));
    }

    public static boolean canSendEmbed(Chat chat) {
        return chat == null || (chat != null && (chat.banned_rights == null || !(chat.banned_rights.send_media || chat.banned_rights.embed_links)));
    }

    public static boolean canSendMessages(Chat chat) {
        return chat == null || (chat != null && (chat.banned_rights == null || !chat.banned_rights.send_messages));
    }

    public static boolean canSendStickers(Chat chat) {
        return chat == null || (chat != null && (chat.banned_rights == null || !(chat.banned_rights.send_media || chat.banned_rights.send_stickers)));
    }

    public static boolean canWriteToChat(Chat chat) {
        return !isChannel(chat) || chat.creator || ((chat.admin_rights != null && chat.admin_rights.post_messages) || !chat.broadcast);
    }

    public static Chat getChatByDialog(long j) {
        int i = (int) j;
        int i2 = (int) (j >> 32);
        return i < 0 ? MessagesController.getInstance().getChat(Integer.valueOf(-i)) : null;
    }

    public static boolean hasAdminRights(Chat chat) {
        return chat != null && (chat.creator || !(chat.admin_rights == null || chat.admin_rights.flags == 0));
    }

    public static boolean isCanWriteToChannel(int i) {
        Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(i));
        return chat != null && (chat.creator || ((chat.admin_rights != null && chat.admin_rights.post_messages) || chat.megagroup));
    }

    public static boolean isChannel(int i) {
        Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(i));
        return (chat instanceof TL_channel) || (chat instanceof TL_channelForbidden);
    }

    public static boolean isChannel(Chat chat) {
        return (chat instanceof TL_channel) || (chat instanceof TL_channelForbidden);
    }

    public static boolean isKickedFromChat(Chat chat) {
        return chat == null || (chat instanceof TL_chatEmpty) || (chat instanceof TL_chatForbidden) || (chat instanceof TL_channelForbidden) || chat.kicked || chat.deactivated || (chat.banned_rights != null && chat.banned_rights.view_messages);
    }

    public static boolean isLeftFromChat(Chat chat) {
        return chat == null || (chat instanceof TL_chatEmpty) || (chat instanceof TL_chatForbidden) || (chat instanceof TL_channelForbidden) || chat.left || chat.deactivated;
    }

    public static boolean isMegagroup(Chat chat) {
        return ((chat instanceof TL_channel) || (chat instanceof TL_channelForbidden)) && chat.megagroup;
    }

    public static boolean isNotInChat(Chat chat) {
        return chat == null || (chat instanceof TL_chatEmpty) || (chat instanceof TL_chatForbidden) || (chat instanceof TL_channelForbidden) || chat.left || chat.kicked || chat.deactivated;
    }
}
