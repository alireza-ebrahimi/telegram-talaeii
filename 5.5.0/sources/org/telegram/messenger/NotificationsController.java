package org.telegram.messenger;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.provider.Settings.System;
import android.support.v4.app.al.C0262a;
import android.support.v4.app.al.C0262a.C0260a;
import android.support.v4.app.al.C0263s;
import android.support.v4.app.al.C0265c;
import android.support.v4.app.al.C0266d;
import android.support.v4.app.al.C0273g;
import android.support.v4.app.al.C0274f;
import android.support.v4.app.al.C0274f.C0272a.C0270a;
import android.support.v4.app.al.C0277i;
import android.support.v4.app.al.C0287t;
import android.support.v4.app.au;
import android.support.v4.app.ay.C0307a;
import android.text.TextUtils;
import android.util.SparseArray;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import org.ir.talaeii.R;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputNotifyPeer;
import org.telegram.tgnet.TLRPC$TL_inputPeerNotifySettings;
import org.telegram.tgnet.TLRPC$TL_messageActionChannelCreate;
import org.telegram.tgnet.TLRPC$TL_messageActionChannelMigrateFrom;
import org.telegram.tgnet.TLRPC$TL_messageActionChatAddUser;
import org.telegram.tgnet.TLRPC$TL_messageActionChatCreate;
import org.telegram.tgnet.TLRPC$TL_messageActionChatDeletePhoto;
import org.telegram.tgnet.TLRPC$TL_messageActionChatDeleteUser;
import org.telegram.tgnet.TLRPC$TL_messageActionChatEditPhoto;
import org.telegram.tgnet.TLRPC$TL_messageActionChatEditTitle;
import org.telegram.tgnet.TLRPC$TL_messageActionChatJoinedByLink;
import org.telegram.tgnet.TLRPC$TL_messageActionChatMigrateTo;
import org.telegram.tgnet.TLRPC$TL_messageActionEmpty;
import org.telegram.tgnet.TLRPC$TL_messageActionGameScore;
import org.telegram.tgnet.TLRPC$TL_messageActionLoginUnknownLocation;
import org.telegram.tgnet.TLRPC$TL_messageActionPaymentSent;
import org.telegram.tgnet.TLRPC$TL_messageActionPhoneCall;
import org.telegram.tgnet.TLRPC$TL_messageActionPinMessage;
import org.telegram.tgnet.TLRPC$TL_messageActionScreenshotTaken;
import org.telegram.tgnet.TLRPC$TL_messageActionUserJoined;
import org.telegram.tgnet.TLRPC$TL_messageActionUserUpdatedPhoto;
import org.telegram.tgnet.TLRPC$TL_messageMediaContact;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_messageMediaGame;
import org.telegram.tgnet.TLRPC$TL_messageMediaGeo;
import org.telegram.tgnet.TLRPC$TL_messageMediaGeoLive;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_messageMediaVenue;
import org.telegram.tgnet.TLRPC$TL_messageService;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonMissed;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.PhoneCallDiscardReason;
import org.telegram.tgnet.TLRPC.TL_account_updateNotifySettings;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.LaunchActivity;
import org.telegram.ui.PopupNotificationActivity;

public class NotificationsController {
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";
    private static volatile NotificationsController Instance = null;
    private AlarmManager alarmManager;
    protected AudioManager audioManager;
    private ArrayList<MessageObject> delayedPushMessages;
    private boolean inChatSoundEnabled;
    private int lastBadgeCount;
    private int lastOnlineFromOtherDevice;
    private long lastSoundOutPlay;
    private long lastSoundPlay;
    private String launcherClassName;
    private Runnable notificationDelayRunnable;
    private WakeLock notificationDelayWakelock;
    private au notificationManager;
    private DispatchQueue notificationsQueue;
    private boolean notifyCheck;
    private long opened_dialog_id;
    private int personal_count;
    public ArrayList<MessageObject> popupMessages;
    public ArrayList<MessageObject> popupReplyMessages;
    private HashMap<Long, Integer> pushDialogs;
    private HashMap<Long, Integer> pushDialogsOverrideMention;
    private ArrayList<MessageObject> pushMessages;
    private HashMap<Long, MessageObject> pushMessagesDict;
    private HashMap<Long, Point> smartNotificationsDialogs;
    private int soundIn;
    private boolean soundInLoaded;
    private int soundOut;
    private boolean soundOutLoaded;
    private SoundPool soundPool;
    private int soundRecord;
    private boolean soundRecordLoaded;
    private int total_unread_count;
    private HashMap<Long, Integer> wearNotificationsIds;

    /* renamed from: org.telegram.messenger.NotificationsController$1 */
    class C33231 implements Runnable {
        C33231() {
        }

        public void run() {
            FileLog.m13726e("delay reached");
            if (!NotificationsController.this.delayedPushMessages.isEmpty()) {
                NotificationsController.this.showOrUpdateNotification(true);
                NotificationsController.this.delayedPushMessages.clear();
            }
            try {
                if (NotificationsController.this.notificationDelayWakelock.isHeld()) {
                    NotificationsController.this.notificationDelayWakelock.release();
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    /* renamed from: org.telegram.messenger.NotificationsController$2 */
    class C33242 implements Runnable {
        C33242() {
        }

        public void run() {
            NotificationsController.this.opened_dialog_id = 0;
            NotificationsController.this.total_unread_count = 0;
            NotificationsController.this.personal_count = 0;
            NotificationsController.this.pushMessages.clear();
            NotificationsController.this.pushMessagesDict.clear();
            NotificationsController.this.pushDialogs.clear();
            NotificationsController.this.wearNotificationsIds.clear();
            NotificationsController.this.delayedPushMessages.clear();
            NotificationsController.this.notifyCheck = false;
            NotificationsController.this.lastBadgeCount = 0;
            try {
                if (NotificationsController.this.notificationDelayWakelock.isHeld()) {
                    NotificationsController.this.notificationDelayWakelock.release();
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
            NotificationsController.this.setBadge(0);
            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            edit.clear();
            edit.commit();
        }
    }

    /* renamed from: org.telegram.messenger.NotificationsController$5 */
    class C33275 implements Runnable {
        C33275() {
        }

        public void run() {
            Intent intent;
            CharSequence string;
            int i = 0;
            try {
                if (ApplicationLoader.mainInterfacePaused) {
                    int i2;
                    int i3;
                    String str;
                    int i4;
                    C0266d e;
                    SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                    Object obj = !sharedPreferences.getBoolean("EnableAll", true) ? 1 : null;
                    String path = System.DEFAULT_NOTIFICATION_URI.getPath();
                    if (obj == null) {
                        Object obj2;
                        String str2 = null;
                        String string2 = (null == null || !str2.equals(path)) ? null == null ? sharedPreferences.getString("GlobalSoundPath", path) : null : null;
                        i2 = sharedPreferences.getInt("vibrate_messages", 0);
                        i3 = sharedPreferences.getInt("priority_group", 1);
                        int i5 = sharedPreferences.getInt("MessagesLed", -16776961);
                        if (i2 == 4) {
                            obj2 = 1;
                        } else {
                            i = i2;
                            obj2 = null;
                        }
                        if (i == 2) {
                            if (i == 2) {
                                if (!(obj2 == null || i == 2)) {
                                    i2 = NotificationsController.this.audioManager.getRingerMode();
                                    if (!(i2 == 0 || i2 == 1)) {
                                        i = 2;
                                    }
                                    i2 = i3;
                                    i3 = i5;
                                    str = string2;
                                    i4 = i;
                                }
                            }
                        } else if (i == 2) {
                        }
                        try {
                            i2 = NotificationsController.this.audioManager.getRingerMode();
                            i = 2;
                            i2 = i3;
                            i3 = i5;
                            str = string2;
                            i4 = i;
                        } catch (Throwable e2) {
                            FileLog.m13728e(e2);
                            i2 = i3;
                            i3 = i5;
                            str = string2;
                            i4 = i;
                            intent = new Intent(ApplicationLoader.applicationContext, LaunchActivity.class);
                            intent.setAction("com.tmessages.openchat" + Math.random() + Integer.MAX_VALUE);
                            intent.setFlags(TLRPC.MESSAGE_FLAG_EDITED);
                            e = new C0266d(ApplicationLoader.applicationContext).m1238a(LocaleController.getString("AppName", R.string.AppName)).m1227a((int) R.drawable.notification).m1240a(true).m1243b(NotificationsController.this.total_unread_count).m1232a(PendingIntent.getActivity(ApplicationLoader.applicationContext, 0, intent, 1073741824)).m1250c("messages").m1251c(true).m1255e(-13851168);
                            e.m1239a("msg");
                            string = LocaleController.getString("YouHaveNewMessage", R.string.YouHaveNewMessage);
                            e.m1245b(string);
                            e.m1237a(new C0265c().m1223a(string));
                            if (i2 == 0) {
                                e.m1253d(0);
                            } else if (i2 == 1) {
                                e.m1253d(1);
                            } else if (i2 == 2) {
                                e.m1253d(2);
                            }
                            if (obj != null) {
                                e.m1241a(new long[]{0, 0});
                            } else {
                                if (string.length() > 100) {
                                    string = string.substring(0, 100).replace('\n', ' ').trim() + "...";
                                }
                                e.m1249c(string);
                                if (str.equals(path)) {
                                    e.m1235a(System.DEFAULT_NOTIFICATION_URI, 5);
                                } else {
                                    e.m1235a(Uri.parse(str), 5);
                                }
                                if (i3 != 0) {
                                    e.m1228a(i3, 1000, 1000);
                                }
                                if (i4 != 2) {
                                }
                                e.m1241a(new long[]{0, 0});
                            }
                            NotificationsController.this.notificationManager.m1383a(1, e.m1242b());
                        }
                    }
                    str = null;
                    i4 = 0;
                    i3 = -16776961;
                    i2 = 0;
                    intent = new Intent(ApplicationLoader.applicationContext, LaunchActivity.class);
                    intent.setAction("com.tmessages.openchat" + Math.random() + Integer.MAX_VALUE);
                    intent.setFlags(TLRPC.MESSAGE_FLAG_EDITED);
                    e = new C0266d(ApplicationLoader.applicationContext).m1238a(LocaleController.getString("AppName", R.string.AppName)).m1227a((int) R.drawable.notification).m1240a(true).m1243b(NotificationsController.this.total_unread_count).m1232a(PendingIntent.getActivity(ApplicationLoader.applicationContext, 0, intent, 1073741824)).m1250c("messages").m1251c(true).m1255e(-13851168);
                    e.m1239a("msg");
                    string = LocaleController.getString("YouHaveNewMessage", R.string.YouHaveNewMessage);
                    e.m1245b(string);
                    e.m1237a(new C0265c().m1223a(string));
                    if (i2 == 0) {
                        e.m1253d(0);
                    } else if (i2 == 1) {
                        e.m1253d(1);
                    } else if (i2 == 2) {
                        e.m1253d(2);
                    }
                    if (obj != null) {
                        if (string.length() > 100) {
                            string = string.substring(0, 100).replace('\n', ' ').trim() + "...";
                        }
                        e.m1249c(string);
                        if (!(str == null || str.equals("NoSound"))) {
                            if (str.equals(path)) {
                                e.m1235a(System.DEFAULT_NOTIFICATION_URI, 5);
                            } else {
                                e.m1235a(Uri.parse(str), 5);
                            }
                        }
                        if (i3 != 0) {
                            e.m1228a(i3, 1000, 1000);
                        }
                        if (i4 != 2 || MediaController.getInstance().isRecordingAudio()) {
                            e.m1241a(new long[]{0, 0});
                        } else if (i4 == 1) {
                            e.m1241a(new long[]{0, 100, 0, 100});
                        } else if (i4 == 0 || i4 == 4) {
                            e.m1248c(2);
                        } else if (i4 == 3) {
                            e.m1241a(new long[]{0, 1000});
                        }
                    } else {
                        e.m1241a(new long[]{0, 0});
                    }
                    NotificationsController.this.notificationManager.m1383a(1, e.m1242b());
                }
            } catch (Throwable e3) {
                FileLog.m13728e(e3);
            }
        }
    }

    /* renamed from: org.telegram.messenger.NotificationsController$6 */
    class C33296 implements Runnable {
        C33296() {
        }

        public void run() {
            final ArrayList arrayList = new ArrayList();
            for (int i = 0; i < NotificationsController.this.pushMessages.size(); i++) {
                MessageObject messageObject = (MessageObject) NotificationsController.this.pushMessages.get(i);
                long dialogId = messageObject.getDialogId();
                if (!((messageObject.messageOwner.mentioned && (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionPinMessage)) || ((int) dialogId) == 0 || (messageObject.messageOwner.to_id.channel_id != 0 && !messageObject.isMegagroup()))) {
                    arrayList.add(0, messageObject);
                }
            }
            if (!arrayList.isEmpty() && !AndroidUtilities.needShowPasscode(false)) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        NotificationsController.this.popupReplyMessages = arrayList;
                        Intent intent = new Intent(ApplicationLoader.applicationContext, PopupNotificationActivity.class);
                        intent.putExtra("force", true);
                        intent.setFlags(268763140);
                        ApplicationLoader.applicationContext.startActivity(intent);
                        ApplicationLoader.applicationContext.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
                    }
                });
            }
        }
    }

    public NotificationsController() {
        this.notificationsQueue = new DispatchQueue("notificationsQueue");
        this.pushMessages = new ArrayList();
        this.delayedPushMessages = new ArrayList();
        this.pushMessagesDict = new HashMap();
        this.smartNotificationsDialogs = new HashMap();
        this.notificationManager = null;
        this.pushDialogs = new HashMap();
        this.wearNotificationsIds = new HashMap();
        this.pushDialogsOverrideMention = new HashMap();
        this.popupMessages = new ArrayList();
        this.popupReplyMessages = new ArrayList();
        this.opened_dialog_id = 0;
        this.total_unread_count = 0;
        this.personal_count = 0;
        this.notifyCheck = false;
        this.lastOnlineFromOtherDevice = 0;
        this.inChatSoundEnabled = true;
        this.lastBadgeCount = -1;
        this.notificationManager = au.m1378a(ApplicationLoader.applicationContext);
        this.inChatSoundEnabled = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("EnableInChatSound", true);
        try {
            this.audioManager = (AudioManager) ApplicationLoader.applicationContext.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
        try {
            this.alarmManager = (AlarmManager) ApplicationLoader.applicationContext.getSystemService("alarm");
        } catch (Throwable e2) {
            FileLog.m13728e(e2);
        }
        try {
            this.notificationDelayWakelock = ((PowerManager) ApplicationLoader.applicationContext.getSystemService("power")).newWakeLock(1, "lock");
            this.notificationDelayWakelock.setReferenceCounted(false);
        } catch (Throwable e22) {
            FileLog.m13728e(e22);
        }
        this.notificationDelayRunnable = new C33231();
    }

    private void dismissNotification() {
        try {
            this.notificationManager.m1382a(1);
            this.pushMessages.clear();
            this.pushMessagesDict.clear();
            for (Entry value : this.wearNotificationsIds.entrySet()) {
                this.notificationManager.m1382a(((Integer) value.getValue()).intValue());
            }
            this.wearNotificationsIds.clear();
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.pushMessagesUpdated, new Object[0]);
                }
            });
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    public static NotificationsController getInstance() {
        NotificationsController notificationsController = Instance;
        if (notificationsController == null) {
            synchronized (NotificationsController.class) {
                notificationsController = Instance;
                if (notificationsController == null) {
                    notificationsController = new NotificationsController();
                    Instance = notificationsController;
                }
            }
        }
        return notificationsController;
    }

    private int getNotifyOverride(SharedPreferences sharedPreferences, long j) {
        int i = sharedPreferences.getInt("notify2_" + j, 0);
        return (i != 3 || sharedPreferences.getInt("notifyuntil_" + j, 0) < ConnectionsManager.getInstance().getCurrentTime()) ? i : 2;
    }

    private String getStringForMessage(MessageObject messageObject, boolean z, boolean[] zArr) {
        long j;
        String str;
        User user;
        Object obj;
        Chat chat;
        Chat chat2;
        long j2 = messageObject.messageOwner.dialog_id;
        int i = messageObject.messageOwner.to_id.chat_id != 0 ? messageObject.messageOwner.to_id.chat_id : messageObject.messageOwner.to_id.channel_id;
        int i2 = messageObject.messageOwner.to_id.user_id;
        int i3 = i2 == 0 ? (messageObject.isFromUser() || messageObject.getId() < 0) ? messageObject.messageOwner.from_id : -i : i2 == UserConfig.getClientUserId() ? messageObject.messageOwner.from_id : i2;
        if (j2 == 0) {
            if (i != 0) {
                j = (long) (-i);
            } else if (i3 != 0) {
                j = (long) i3;
            }
            str = null;
            if (i3 <= 0) {
                user = MessagesController.getInstance().getUser(Integer.valueOf(i3));
                if (user != null) {
                    str = UserObject.getUserName(user);
                }
                obj = str;
            } else {
                chat = MessagesController.getInstance().getChat(Integer.valueOf(-i3));
                String str2 = chat == null ? chat.title : null;
            }
            if (obj == null) {
                return null;
            }
            if (i == 0) {
                chat2 = MessagesController.getInstance().getChat(Integer.valueOf(i));
                if (chat2 == null) {
                    return null;
                }
                chat = chat2;
            } else {
                chat = null;
            }
            if (((int) j) != 0 || AndroidUtilities.needShowPasscode(false) || UserConfig.isWaitingForPasscodeEnter) {
                return LocaleController.getString("YouHaveNewMessage", R.string.YouHaveNewMessage);
            }
            String userName;
            if (i != 0 || i3 == 0) {
                if (i != 0) {
                    if (ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("EnablePreviewGroup", true)) {
                        if (messageObject.messageOwner instanceof TLRPC$TL_messageService) {
                            if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatAddUser) {
                                i = messageObject.messageOwner.action.user_id;
                                if (i == 0 && messageObject.messageOwner.action.users.size() == 1) {
                                    i = ((Integer) messageObject.messageOwner.action.users.get(0)).intValue();
                                }
                                User user2;
                                if (i == 0) {
                                    StringBuilder stringBuilder = new StringBuilder(TtmlNode.ANONYMOUS_REGION_ID);
                                    for (i2 = 0; i2 < messageObject.messageOwner.action.users.size(); i2++) {
                                        user2 = MessagesController.getInstance().getUser((Integer) messageObject.messageOwner.action.users.get(i2));
                                        if (user2 != null) {
                                            userName = UserObject.getUserName(user2);
                                            if (stringBuilder.length() != 0) {
                                                stringBuilder.append(", ");
                                            }
                                            stringBuilder.append(userName);
                                        }
                                    }
                                    return LocaleController.formatString("NotificationGroupAddMember", R.string.NotificationGroupAddMember, obj, chat.title, stringBuilder.toString());
                                } else if (messageObject.messageOwner.to_id.channel_id != 0 && !chat.megagroup) {
                                    return LocaleController.formatString("ChannelAddedByNotification", R.string.ChannelAddedByNotification, obj, chat.title);
                                } else if (i == UserConfig.getClientUserId()) {
                                    return LocaleController.formatString("NotificationInvitedToGroup", R.string.NotificationInvitedToGroup, obj, chat.title);
                                } else {
                                    user2 = MessagesController.getInstance().getUser(Integer.valueOf(i));
                                    if (user2 == null) {
                                        return null;
                                    }
                                    if (i3 != user2.id) {
                                        return LocaleController.formatString("NotificationGroupAddMember", R.string.NotificationGroupAddMember, obj, chat.title, UserObject.getUserName(user2));
                                    } else if (chat.megagroup) {
                                        return LocaleController.formatString("NotificationGroupAddSelfMega", R.string.NotificationGroupAddSelfMega, obj, chat.title);
                                    } else {
                                        return LocaleController.formatString("NotificationGroupAddSelf", R.string.NotificationGroupAddSelf, obj, chat.title);
                                    }
                                }
                            } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatJoinedByLink) {
                                return LocaleController.formatString("NotificationInvitedToGroupByLink", R.string.NotificationInvitedToGroupByLink, obj, chat.title);
                            } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatEditTitle) {
                                return LocaleController.formatString("NotificationEditedGroupName", R.string.NotificationEditedGroupName, obj, messageObject.messageOwner.action.title);
                            } else if ((messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatEditPhoto) || (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatDeletePhoto)) {
                                if (messageObject.messageOwner.to_id.channel_id == 0 || chat.megagroup) {
                                    return LocaleController.formatString("NotificationEditedGroupPhoto", R.string.NotificationEditedGroupPhoto, obj, chat.title);
                                }
                                return LocaleController.formatString("ChannelPhotoEditNotification", R.string.ChannelPhotoEditNotification, chat.title);
                            } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatDeleteUser) {
                                if (messageObject.messageOwner.action.user_id == UserConfig.getClientUserId()) {
                                    return LocaleController.formatString("NotificationGroupKickYou", R.string.NotificationGroupKickYou, obj, chat.title);
                                } else if (messageObject.messageOwner.action.user_id == i3) {
                                    return LocaleController.formatString("NotificationGroupLeftMember", R.string.NotificationGroupLeftMember, obj, chat.title);
                                } else {
                                    if (MessagesController.getInstance().getUser(Integer.valueOf(messageObject.messageOwner.action.user_id)) == null) {
                                        return null;
                                    }
                                    return LocaleController.formatString("NotificationGroupKickMember", R.string.NotificationGroupKickMember, obj, chat.title, UserObject.getUserName(MessagesController.getInstance().getUser(Integer.valueOf(messageObject.messageOwner.action.user_id))));
                                }
                            } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatCreate) {
                                return messageObject.messageText.toString();
                            } else {
                                if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChannelCreate) {
                                    return messageObject.messageText.toString();
                                }
                                if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatMigrateTo) {
                                    return LocaleController.formatString("ActionMigrateFromGroupNotify", R.string.ActionMigrateFromGroupNotify, chat.title);
                                } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChannelMigrateFrom) {
                                    return LocaleController.formatString("ActionMigrateFromGroupNotify", R.string.ActionMigrateFromGroupNotify, messageObject.messageOwner.action.title);
                                } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionScreenshotTaken) {
                                    return messageObject.messageText.toString();
                                } else {
                                    if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionPinMessage) {
                                        MessageObject messageObject2;
                                        CharSequence charSequence;
                                        if (chat == null || !chat.megagroup) {
                                            if (messageObject.replyMessageObject == null) {
                                                return LocaleController.formatString("NotificationActionPinnedNoTextChannel", R.string.NotificationActionPinnedNoTextChannel, chat.title);
                                            }
                                            messageObject2 = messageObject.replyMessageObject;
                                            if (messageObject2.isMusic()) {
                                                return LocaleController.formatString("NotificationActionPinnedMusicChannel", R.string.NotificationActionPinnedMusicChannel, chat.title);
                                            } else if (messageObject2.isVideo()) {
                                                if (VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject2.messageOwner.media.caption)) {
                                                    return LocaleController.formatString("NotificationActionPinnedVideoChannel", R.string.NotificationActionPinnedVideoChannel, chat.title);
                                                }
                                                userName = "📹 " + messageObject2.messageOwner.media.caption;
                                                return LocaleController.formatString("NotificationActionPinnedTextChannel", R.string.NotificationActionPinnedTextChannel, chat.title, userName);
                                            } else if (messageObject2.isGif()) {
                                                if (VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject2.messageOwner.media.caption)) {
                                                    return LocaleController.formatString("NotificationActionPinnedGifChannel", R.string.NotificationActionPinnedGifChannel, chat.title);
                                                }
                                                userName = "🎬 " + messageObject2.messageOwner.media.caption;
                                                return LocaleController.formatString("NotificationActionPinnedTextChannel", R.string.NotificationActionPinnedTextChannel, chat.title, userName);
                                            } else if (messageObject2.isVoice()) {
                                                return LocaleController.formatString("NotificationActionPinnedVoiceChannel", R.string.NotificationActionPinnedVoiceChannel, chat.title);
                                            } else if (messageObject2.isRoundVideo()) {
                                                return LocaleController.formatString("NotificationActionPinnedRoundChannel", R.string.NotificationActionPinnedRoundChannel, chat.title);
                                            } else if (messageObject2.isSticker()) {
                                                if (messageObject2.getStickerEmoji() != null) {
                                                    return LocaleController.formatString("NotificationActionPinnedStickerEmojiChannel", R.string.NotificationActionPinnedStickerEmojiChannel, chat.title, messageObject2.getStickerEmoji());
                                                }
                                                return LocaleController.formatString("NotificationActionPinnedStickerChannel", R.string.NotificationActionPinnedStickerChannel, chat.title);
                                            } else if (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaDocument) {
                                                if (VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject2.messageOwner.media.caption)) {
                                                    return LocaleController.formatString("NotificationActionPinnedFileChannel", R.string.NotificationActionPinnedFileChannel, chat.title);
                                                }
                                                userName = "📎 " + messageObject2.messageOwner.media.caption;
                                                return LocaleController.formatString("NotificationActionPinnedTextChannel", R.string.NotificationActionPinnedTextChannel, chat.title, userName);
                                            } else if (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaGeo) {
                                                return LocaleController.formatString("NotificationActionPinnedGeoChannel", R.string.NotificationActionPinnedGeoChannel, chat.title);
                                            } else if (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaGeoLive) {
                                                return LocaleController.formatString("NotificationActionPinnedGeoLiveChannel", R.string.NotificationActionPinnedGeoLiveChannel, chat.title);
                                            } else if (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaContact) {
                                                return LocaleController.formatString("NotificationActionPinnedContactChannel", R.string.NotificationActionPinnedContactChannel, chat.title);
                                            } else if (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) {
                                                if (VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject2.messageOwner.media.caption)) {
                                                    return LocaleController.formatString("NotificationActionPinnedPhotoChannel", R.string.NotificationActionPinnedPhotoChannel, chat.title);
                                                }
                                                userName = "🖼 " + messageObject2.messageOwner.media.caption;
                                                return LocaleController.formatString("NotificationActionPinnedTextChannel", R.string.NotificationActionPinnedTextChannel, chat.title, userName);
                                            } else if (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                                                return LocaleController.formatString("NotificationActionPinnedGameChannel", R.string.NotificationActionPinnedGameChannel, chat.title);
                                            } else if (messageObject2.messageText == null || messageObject2.messageText.length() <= 0) {
                                                return LocaleController.formatString("NotificationActionPinnedNoTextChannel", R.string.NotificationActionPinnedNoTextChannel, chat.title);
                                            } else {
                                                charSequence = messageObject2.messageText;
                                                if (charSequence.length() > 20) {
                                                    charSequence = charSequence.subSequence(0, 20) + "...";
                                                }
                                                return LocaleController.formatString("NotificationActionPinnedTextChannel", R.string.NotificationActionPinnedTextChannel, chat.title, charSequence);
                                            }
                                        } else if (messageObject.replyMessageObject == null) {
                                            return LocaleController.formatString("NotificationActionPinnedNoText", R.string.NotificationActionPinnedNoText, obj, chat.title);
                                        } else {
                                            messageObject2 = messageObject.replyMessageObject;
                                            if (messageObject2.isMusic()) {
                                                return LocaleController.formatString("NotificationActionPinnedMusic", R.string.NotificationActionPinnedMusic, obj, chat.title);
                                            } else if (messageObject2.isVideo()) {
                                                if (VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject2.messageOwner.media.caption)) {
                                                    return LocaleController.formatString("NotificationActionPinnedVideo", R.string.NotificationActionPinnedVideo, obj, chat.title);
                                                }
                                                userName = "📹 " + messageObject2.messageOwner.media.caption;
                                                return LocaleController.formatString("NotificationActionPinnedText", R.string.NotificationActionPinnedText, obj, userName, chat.title);
                                            } else if (messageObject2.isGif()) {
                                                if (VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject2.messageOwner.media.caption)) {
                                                    return LocaleController.formatString("NotificationActionPinnedGif", R.string.NotificationActionPinnedGif, obj, chat.title);
                                                }
                                                userName = "🎬 " + messageObject2.messageOwner.media.caption;
                                                return LocaleController.formatString("NotificationActionPinnedText", R.string.NotificationActionPinnedText, obj, userName, chat.title);
                                            } else if (messageObject2.isVoice()) {
                                                return LocaleController.formatString("NotificationActionPinnedVoice", R.string.NotificationActionPinnedVoice, obj, chat.title);
                                            } else if (messageObject2.isRoundVideo()) {
                                                return LocaleController.formatString("NotificationActionPinnedRound", R.string.NotificationActionPinnedRound, obj, chat.title);
                                            } else if (messageObject2.isSticker()) {
                                                if (messageObject2.getStickerEmoji() != null) {
                                                    return LocaleController.formatString("NotificationActionPinnedStickerEmoji", R.string.NotificationActionPinnedStickerEmoji, obj, chat.title, messageObject2.getStickerEmoji());
                                                }
                                                return LocaleController.formatString("NotificationActionPinnedSticker", R.string.NotificationActionPinnedSticker, obj, chat.title);
                                            } else if (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaDocument) {
                                                if (VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject2.messageOwner.media.caption)) {
                                                    return LocaleController.formatString("NotificationActionPinnedFile", R.string.NotificationActionPinnedFile, obj, chat.title);
                                                }
                                                userName = "📎 " + messageObject2.messageOwner.media.caption;
                                                return LocaleController.formatString("NotificationActionPinnedText", R.string.NotificationActionPinnedText, obj, userName, chat.title);
                                            } else if (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaGeo) {
                                                return LocaleController.formatString("NotificationActionPinnedGeo", R.string.NotificationActionPinnedGeo, obj, chat.title);
                                            } else if (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaGeoLive) {
                                                return LocaleController.formatString("NotificationActionPinnedGeoLive", R.string.NotificationActionPinnedGeoLive, obj, chat.title);
                                            } else if (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaContact) {
                                                return LocaleController.formatString("NotificationActionPinnedContact", R.string.NotificationActionPinnedContact, obj, chat.title);
                                            } else if (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) {
                                                if (VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject2.messageOwner.media.caption)) {
                                                    return LocaleController.formatString("NotificationActionPinnedPhoto", R.string.NotificationActionPinnedPhoto, obj, chat.title);
                                                }
                                                userName = "🖼 " + messageObject2.messageOwner.media.caption;
                                                return LocaleController.formatString("NotificationActionPinnedText", R.string.NotificationActionPinnedText, obj, userName, chat.title);
                                            } else if (messageObject2.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                                                return LocaleController.formatString("NotificationActionPinnedGame", R.string.NotificationActionPinnedGame, obj, chat.title);
                                            } else if (messageObject2.messageText == null || messageObject2.messageText.length() <= 0) {
                                                return LocaleController.formatString("NotificationActionPinnedNoText", R.string.NotificationActionPinnedNoText, obj, chat.title);
                                            } else {
                                                charSequence = messageObject2.messageText;
                                                if (charSequence.length() > 20) {
                                                    charSequence = charSequence.subSequence(0, 20) + "...";
                                                }
                                                return LocaleController.formatString("NotificationActionPinnedText", R.string.NotificationActionPinnedText, obj, charSequence, chat.title);
                                            }
                                        }
                                    } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionGameScore) {
                                        return messageObject.messageText.toString();
                                    }
                                }
                            }
                        } else if (!ChatObject.isChannel(chat) || chat.megagroup) {
                            if (messageObject.isMediaEmpty()) {
                                if (z || messageObject.messageOwner.message == null || messageObject.messageOwner.message.length() == 0) {
                                    return LocaleController.formatString("NotificationMessageGroupNoText", R.string.NotificationMessageGroupNoText, obj, chat.title);
                                }
                                return LocaleController.formatString("NotificationMessageGroupText", R.string.NotificationMessageGroupText, obj, chat.title, messageObject.messageOwner.message);
                            } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) {
                                if (z || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                                    return LocaleController.formatString("NotificationMessageGroupPhoto", R.string.NotificationMessageGroupPhoto, obj, chat.title);
                                }
                                return LocaleController.formatString("NotificationMessageGroupText", R.string.NotificationMessageGroupText, obj, chat.title, "🖼 " + messageObject.messageOwner.media.caption);
                            } else if (messageObject.isVideo()) {
                                if (z || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                                    return LocaleController.formatString("NotificationMessageGroupVideo", R.string.NotificationMessageGroupVideo, obj, chat.title);
                                }
                                return LocaleController.formatString("NotificationMessageGroupText", R.string.NotificationMessageGroupText, obj, chat.title, "📹 " + messageObject.messageOwner.media.caption);
                            } else if (messageObject.isVoice()) {
                                return LocaleController.formatString("NotificationMessageGroupAudio", R.string.NotificationMessageGroupAudio, obj, chat.title);
                            } else if (messageObject.isRoundVideo()) {
                                return LocaleController.formatString("NotificationMessageGroupRound", R.string.NotificationMessageGroupRound, obj, chat.title);
                            } else if (messageObject.isMusic()) {
                                return LocaleController.formatString("NotificationMessageGroupMusic", R.string.NotificationMessageGroupMusic, obj, chat.title);
                            } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaContact) {
                                return LocaleController.formatString("NotificationMessageGroupContact", R.string.NotificationMessageGroupContact, obj, chat.title);
                            } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                                return LocaleController.formatString("NotificationMessageGroupGame", R.string.NotificationMessageGroupGame, obj, chat.title, messageObject.messageOwner.media.game.title);
                            } else if ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeo) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaVenue)) {
                                return LocaleController.formatString("NotificationMessageGroupMap", R.string.NotificationMessageGroupMap, obj, chat.title);
                            } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaDocument) {
                                if (messageObject.isSticker()) {
                                    if (messageObject.getStickerEmoji() != null) {
                                        return LocaleController.formatString("NotificationMessageGroupStickerEmoji", R.string.NotificationMessageGroupStickerEmoji, obj, chat.title, messageObject.getStickerEmoji());
                                    }
                                    return LocaleController.formatString("NotificationMessageGroupSticker", R.string.NotificationMessageGroupSticker, obj, chat.title);
                                } else if (messageObject.isGif()) {
                                    if (z || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                                        return LocaleController.formatString("NotificationMessageGroupGif", R.string.NotificationMessageGroupGif, obj, chat.title);
                                    }
                                    return LocaleController.formatString("NotificationMessageGroupText", R.string.NotificationMessageGroupText, obj, chat.title, "🎬 " + messageObject.messageOwner.media.caption);
                                } else if (z || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                                    return LocaleController.formatString("NotificationMessageGroupDocument", R.string.NotificationMessageGroupDocument, obj, chat.title);
                                } else {
                                    return LocaleController.formatString("NotificationMessageGroupText", R.string.NotificationMessageGroupText, obj, chat.title, "📎 " + messageObject.messageOwner.media.caption);
                                }
                            }
                        } else if (messageObject.isMediaEmpty()) {
                            if (z || messageObject.messageOwner.message == null || messageObject.messageOwner.message.length() == 0) {
                                return LocaleController.formatString("ChannelMessageNoText", R.string.ChannelMessageNoText, obj);
                            }
                            userName = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, obj, messageObject.messageOwner.message);
                            zArr[0] = true;
                            return userName;
                        } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) {
                            if (z || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                                return LocaleController.formatString("ChannelMessagePhoto", R.string.ChannelMessagePhoto, obj);
                            }
                            userName = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, obj, "🖼 " + messageObject.messageOwner.media.caption);
                            zArr[0] = true;
                            return userName;
                        } else if (messageObject.isVideo()) {
                            if (z || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                                return LocaleController.formatString("ChannelMessageVideo", R.string.ChannelMessageVideo, obj);
                            }
                            userName = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, obj, "📹 " + messageObject.messageOwner.media.caption);
                            zArr[0] = true;
                            return userName;
                        } else if (messageObject.isVoice()) {
                            return LocaleController.formatString("ChannelMessageAudio", R.string.ChannelMessageAudio, obj);
                        } else if (messageObject.isRoundVideo()) {
                            return LocaleController.formatString("ChannelMessageRound", R.string.ChannelMessageRound, obj);
                        } else if (messageObject.isMusic()) {
                            return LocaleController.formatString("ChannelMessageMusic", R.string.ChannelMessageMusic, obj);
                        } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaContact) {
                            return LocaleController.formatString("ChannelMessageContact", R.string.ChannelMessageContact, obj);
                        } else if ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeo) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaVenue)) {
                            return LocaleController.formatString("ChannelMessageMap", R.string.ChannelMessageMap, obj);
                        } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaDocument) {
                            if (messageObject.isSticker()) {
                                if (messageObject.getStickerEmoji() != null) {
                                    return LocaleController.formatString("ChannelMessageStickerEmoji", R.string.ChannelMessageStickerEmoji, obj, messageObject.getStickerEmoji());
                                }
                                return LocaleController.formatString("ChannelMessageSticker", R.string.ChannelMessageSticker, obj);
                            } else if (messageObject.isGif()) {
                                if (z || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                                    return LocaleController.formatString("ChannelMessageGIF", R.string.ChannelMessageGIF, obj);
                                }
                                userName = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, obj, "🎬 " + messageObject.messageOwner.media.caption);
                                zArr[0] = true;
                                return userName;
                            } else if (z || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                                return LocaleController.formatString("ChannelMessageDocument", R.string.ChannelMessageDocument, obj);
                            } else {
                                userName = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, obj, "📎 " + messageObject.messageOwner.media.caption);
                                zArr[0] = true;
                                return userName;
                            }
                        }
                    } else if (!ChatObject.isChannel(chat) || chat.megagroup) {
                        return LocaleController.formatString("NotificationMessageGroupNoText", R.string.NotificationMessageGroupNoText, obj, chat.title);
                    } else {
                        return LocaleController.formatString("ChannelMessageNoText", R.string.ChannelMessageNoText, obj, chat.title);
                    }
                }
            } else if (!ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("EnablePreviewAll", true)) {
                return LocaleController.formatString("NotificationMessageNoText", R.string.NotificationMessageNoText, obj);
            } else if (messageObject.messageOwner instanceof TLRPC$TL_messageService) {
                if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionUserJoined) {
                    return LocaleController.formatString("NotificationContactJoined", R.string.NotificationContactJoined, obj);
                } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionUserUpdatedPhoto) {
                    return LocaleController.formatString("NotificationContactNewPhoto", R.string.NotificationContactNewPhoto, obj);
                } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionLoginUnknownLocation) {
                    userName = LocaleController.formatString("formatDateAtTime", R.string.formatDateAtTime, LocaleController.getInstance().formatterYear.format(((long) messageObject.messageOwner.date) * 1000), LocaleController.getInstance().formatterDay.format(((long) messageObject.messageOwner.date) * 1000));
                    return LocaleController.formatString("NotificationUnrecognizedDevice", R.string.NotificationUnrecognizedDevice, UserConfig.getCurrentUser().first_name, userName, messageObject.messageOwner.action.title, messageObject.messageOwner.action.address);
                } else if ((messageObject.messageOwner.action instanceof TLRPC$TL_messageActionGameScore) || (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionPaymentSent)) {
                    return messageObject.messageText.toString();
                } else {
                    if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionPhoneCall) {
                        PhoneCallDiscardReason phoneCallDiscardReason = messageObject.messageOwner.action.reason;
                        if (!messageObject.isOut() && (phoneCallDiscardReason instanceof TLRPC$TL_phoneCallDiscardReasonMissed)) {
                            return LocaleController.getString("CallMessageIncomingMissed", R.string.CallMessageIncomingMissed);
                        }
                    }
                }
            } else if (messageObject.isMediaEmpty()) {
                if (z) {
                    return LocaleController.formatString("NotificationMessageNoText", R.string.NotificationMessageNoText, obj);
                } else if (messageObject.messageOwner.message == null || messageObject.messageOwner.message.length() == 0) {
                    return LocaleController.formatString("NotificationMessageNoText", R.string.NotificationMessageNoText, obj);
                } else {
                    userName = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, obj, messageObject.messageOwner.message);
                    zArr[0] = true;
                    return userName;
                }
            } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) {
                if (!z && VERSION.SDK_INT >= 19 && !TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                    userName = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, obj, "🖼 " + messageObject.messageOwner.media.caption);
                    zArr[0] = true;
                    return userName;
                } else if (messageObject.messageOwner.media.ttl_seconds != 0) {
                    return LocaleController.formatString("NotificationMessageSDPhoto", R.string.NotificationMessageSDPhoto, obj);
                } else {
                    return LocaleController.formatString("NotificationMessagePhoto", R.string.NotificationMessagePhoto, obj);
                }
            } else if (messageObject.isVideo()) {
                if (!z && VERSION.SDK_INT >= 19 && !TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                    userName = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, obj, "📹 " + messageObject.messageOwner.media.caption);
                    zArr[0] = true;
                    return userName;
                } else if (messageObject.messageOwner.media.ttl_seconds != 0) {
                    return LocaleController.formatString("NotificationMessageSDVideo", R.string.NotificationMessageSDVideo, obj);
                } else {
                    return LocaleController.formatString("NotificationMessageVideo", R.string.NotificationMessageVideo, obj);
                }
            } else if (messageObject.isGame()) {
                return LocaleController.formatString("NotificationMessageGame", R.string.NotificationMessageGame, obj, messageObject.messageOwner.media.game.title);
            } else if (messageObject.isVoice()) {
                return LocaleController.formatString("NotificationMessageAudio", R.string.NotificationMessageAudio, obj);
            } else if (messageObject.isRoundVideo()) {
                return LocaleController.formatString("NotificationMessageRound", R.string.NotificationMessageRound, obj);
            } else if (messageObject.isMusic()) {
                return LocaleController.formatString("NotificationMessageMusic", R.string.NotificationMessageMusic, obj);
            } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaContact) {
                return LocaleController.formatString("NotificationMessageContact", R.string.NotificationMessageContact, obj);
            } else if ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeo) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaVenue)) {
                return LocaleController.formatString("NotificationMessageMap", R.string.NotificationMessageMap, obj);
            } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaDocument) {
                if (messageObject.isSticker()) {
                    if (messageObject.getStickerEmoji() != null) {
                        return LocaleController.formatString("NotificationMessageStickerEmoji", R.string.NotificationMessageStickerEmoji, obj, messageObject.getStickerEmoji());
                    }
                    return LocaleController.formatString("NotificationMessageSticker", R.string.NotificationMessageSticker, obj);
                } else if (messageObject.isGif()) {
                    if (z || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                        return LocaleController.formatString("NotificationMessageGif", R.string.NotificationMessageGif, obj);
                    }
                    userName = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, obj, "🎬 " + messageObject.messageOwner.media.caption);
                    zArr[0] = true;
                    return userName;
                } else if (z || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                    return LocaleController.formatString("NotificationMessageDocument", R.string.NotificationMessageDocument, obj);
                } else {
                    userName = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, obj, "📎 " + messageObject.messageOwner.media.caption);
                    zArr[0] = true;
                    return userName;
                }
            }
            return null;
        }
        j = j2;
        str = null;
        if (i3 <= 0) {
            chat = MessagesController.getInstance().getChat(Integer.valueOf(-i3));
            if (chat == null) {
            }
        } else {
            user = MessagesController.getInstance().getUser(Integer.valueOf(i3));
            if (user != null) {
                str = UserObject.getUserName(user);
            }
            obj = str;
        }
        if (obj == null) {
            return null;
        }
        if (i == 0) {
            chat = null;
        } else {
            chat2 = MessagesController.getInstance().getChat(Integer.valueOf(i));
            if (chat2 == null) {
                return null;
            }
            chat = chat2;
        }
        if (((int) j) != 0) {
        }
        return LocaleController.getString("YouHaveNewMessage", R.string.YouHaveNewMessage);
    }

    private boolean isPersonalMessage(MessageObject messageObject) {
        return messageObject.messageOwner.to_id != null && messageObject.messageOwner.to_id.chat_id == 0 && messageObject.messageOwner.to_id.channel_id == 0 && (messageObject.messageOwner.action == null || (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionEmpty));
    }

    private void playInChatSound() {
        if (this.inChatSoundEnabled && !MediaController.getInstance().isRecordingAudio()) {
            try {
                if (this.audioManager.getRingerMode() == 0) {
                    return;
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
            try {
                if (getNotifyOverride(ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0), this.opened_dialog_id) != 2) {
                    this.notificationsQueue.postRunnable(new Runnable() {

                        /* renamed from: org.telegram.messenger.NotificationsController$15$1 */
                        class C33211 implements OnLoadCompleteListener {
                            C33211() {
                            }

                            public void onLoadComplete(SoundPool soundPool, int i, int i2) {
                                if (i2 == 0) {
                                    try {
                                        soundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
                                    } catch (Throwable e) {
                                        FileLog.m13728e(e);
                                    }
                                }
                            }
                        }

                        public void run() {
                            if (Math.abs(System.currentTimeMillis() - NotificationsController.this.lastSoundPlay) > 500) {
                                try {
                                    if (NotificationsController.this.soundPool == null) {
                                        NotificationsController.this.soundPool = new SoundPool(3, 1, 0);
                                        NotificationsController.this.soundPool.setOnLoadCompleteListener(new C33211());
                                    }
                                    if (NotificationsController.this.soundIn == 0 && !NotificationsController.this.soundInLoaded) {
                                        NotificationsController.this.soundInLoaded = true;
                                        NotificationsController.this.soundIn = NotificationsController.this.soundPool.load(ApplicationLoader.applicationContext, R.raw.sound_in, 1);
                                    }
                                    if (NotificationsController.this.soundIn != 0) {
                                        try {
                                            NotificationsController.this.soundPool.play(NotificationsController.this.soundIn, 1.0f, 1.0f, 1, 0, 1.0f);
                                        } catch (Throwable e) {
                                            FileLog.m13728e(e);
                                        }
                                    }
                                } catch (Throwable e2) {
                                    FileLog.m13728e(e2);
                                }
                            }
                        }
                    });
                }
            } catch (Throwable e2) {
                FileLog.m13728e(e2);
            }
        }
    }

    private void scheduleNotificationDelay(boolean z) {
        try {
            FileLog.m13726e("delay notification start, onlineReason = " + z);
            this.notificationDelayWakelock.acquire(10000);
            AndroidUtilities.cancelRunOnUIThread(this.notificationDelayRunnable);
            AndroidUtilities.runOnUIThread(this.notificationDelayRunnable, (long) (z ? 3000 : 1000));
        } catch (Throwable e) {
            FileLog.m13728e(e);
            showOrUpdateNotification(this.notifyCheck);
        }
    }

    private void scheduleNotificationRepeat() {
        try {
            PendingIntent service = PendingIntent.getService(ApplicationLoader.applicationContext, 0, new Intent(ApplicationLoader.applicationContext, NotificationRepeat.class), 0);
            int i = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getInt("repeat_messages", 60);
            if (i <= 0 || this.personal_count <= 0) {
                this.alarmManager.cancel(service);
            } else {
                this.alarmManager.set(2, SystemClock.elapsedRealtime() + ((long) ((i * 60) * 1000)), service);
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    private void setBadge(final int i) {
        this.notificationsQueue.postRunnable(new Runnable() {
            public void run() {
                if (NotificationsController.this.lastBadgeCount != i) {
                    NotificationsController.this.lastBadgeCount = i;
                    NotificationBadge.applyCount(i);
                }
            }
        });
    }

    @SuppressLint({"InlinedApi"})
    private void showExtraNotifications(C0266d c0266d, boolean z) {
        if (VERSION.SDK_INT >= 18) {
            int i;
            ArrayList arrayList = new ArrayList();
            HashMap hashMap = new HashMap();
            for (i = 0; i < this.pushMessages.size(); i++) {
                MessageObject messageObject = (MessageObject) this.pushMessages.get(i);
                long dialogId = messageObject.getDialogId();
                if (((int) dialogId) != 0) {
                    ArrayList arrayList2 = (ArrayList) hashMap.get(Long.valueOf(dialogId));
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList();
                        hashMap.put(Long.valueOf(dialogId), arrayList2);
                        arrayList.add(0, Long.valueOf(dialogId));
                    }
                    arrayList2.add(messageObject);
                }
            }
            HashMap hashMap2 = new HashMap();
            hashMap2.putAll(this.wearNotificationsIds);
            this.wearNotificationsIds.clear();
            for (i = 0; i < arrayList.size(); i++) {
                long longValue = ((Long) arrayList.get(i)).longValue();
                ArrayList arrayList3 = (ArrayList) hashMap.get(Long.valueOf(longValue));
                int id = ((MessageObject) arrayList3.get(0)).getId();
                int i2 = ((MessageObject) arrayList3.get(0)).messageOwner.date;
                Chat chat;
                User user;
                CharSequence string;
                TLObject tLObject;
                Integer num;
                Integer valueOf;
                C0270a a;
                Intent intent;
                C0262a c0262a;
                C0277i c0277i;
                CharSequence[] charSequenceArr;
                C0263s a2;
                StringBuilder stringBuilder;
                boolean[] zArr;
                int size;
                MessageObject messageObject2;
                String stringForMessage;
                PendingIntent activity;
                C0273g c0287t;
                String str;
                C0273g c0287t2;
                C0266d a3;
                BitmapDrawable imageFromMemory;
                File pathToAttach;
                float dp;
                Options options;
                Bitmap decodeFile;
                if (longValue > 0) {
                    User user2 = MessagesController.getInstance().getUser(Integer.valueOf((int) longValue));
                    if (user2 != null) {
                        chat = null;
                        user = user2;
                        if (!AndroidUtilities.needShowPasscode(false) || UserConfig.isWaitingForPasscodeEnter) {
                            string = LocaleController.getString("AppName", R.string.AppName);
                            tLObject = null;
                        } else {
                            Object obj;
                            String userName = chat != null ? chat.title : UserObject.getUserName(user);
                            if (chat != null) {
                                if (!(chat.photo == null || chat.photo.photo_small == null || chat.photo.photo_small.volume_id == 0 || chat.photo.photo_small.local_id == 0)) {
                                    obj = userName;
                                    tLObject = chat.photo.photo_small;
                                }
                            } else if (!(user.photo == null || user.photo.photo_small == null || user.photo.photo_small.volume_id == 0 || user.photo.photo_small.local_id == 0)) {
                                obj = userName;
                                tLObject = user.photo.photo_small;
                            }
                            obj = userName;
                            tLObject = null;
                        }
                        num = (Integer) hashMap2.get(Long.valueOf(longValue));
                        if (num != null) {
                            valueOf = Integer.valueOf((int) longValue);
                        } else {
                            hashMap2.remove(Long.valueOf(longValue));
                            valueOf = num;
                        }
                        a = new C0270a(string).m1258a(((long) i2) * 1000);
                        intent = new Intent(ApplicationLoader.applicationContext, AutoMessageHeardReceiver.class);
                        intent.addFlags(32);
                        intent.setAction("org.telegram.messenger.ACTION_MESSAGE_HEARD");
                        intent.putExtra("dialog_id", longValue);
                        intent.putExtra("max_id", id);
                        a.m1259a(PendingIntent.getBroadcast(ApplicationLoader.applicationContext, valueOf.intValue(), intent, 134217728));
                        if ((!ChatObject.isChannel(chat) && (chat == null || !chat.megagroup)) || AndroidUtilities.needShowPasscode(false) || UserConfig.isWaitingForPasscodeEnter) {
                            c0262a = null;
                        } else {
                            intent = new Intent(ApplicationLoader.applicationContext, AutoMessageReplyReceiver.class);
                            intent.addFlags(32);
                            intent.setAction("org.telegram.messenger.ACTION_MESSAGE_REPLY");
                            intent.putExtra("dialog_id", longValue);
                            intent.putExtra("max_id", id);
                            a.m1260a(PendingIntent.getBroadcast(ApplicationLoader.applicationContext, valueOf.intValue(), intent, 134217728), new C0307a(EXTRA_VOICE_REPLY).m1391a(LocaleController.getString("Reply", R.string.Reply)).m1392a());
                            intent = new Intent(ApplicationLoader.applicationContext, WearReplyReceiver.class);
                            intent.putExtra("dialog_id", longValue);
                            intent.putExtra("max_id", id);
                            c0262a = new C0260a(R.drawable.ic_reply_icon, chat == null ? LocaleController.formatString("ReplyToGroup", R.string.ReplyToGroup, string) : LocaleController.formatString("ReplyToUser", R.string.ReplyToUser, string), PendingIntent.getBroadcast(ApplicationLoader.applicationContext, valueOf.intValue(), intent, 134217728)).m1203a(true).m1202a(new C0307a(EXTRA_VOICE_REPLY).m1391a(LocaleController.getString("Reply", R.string.Reply)).m1392a()).m1204a();
                        }
                        num = (Integer) this.pushDialogs.get(Long.valueOf(longValue));
                        if (num == null) {
                            num = Integer.valueOf(0);
                        }
                        c0277i = new C0277i(null);
                        charSequenceArr = new Object[2];
                        charSequenceArr[0] = string;
                        charSequenceArr[1] = LocaleController.formatPluralString("NewMessages", Math.max(num.intValue(), arrayList3.size()));
                        a2 = c0277i.m1289a(String.format("%1$s (%2$s)", charSequenceArr));
                        stringBuilder = new StringBuilder();
                        zArr = new boolean[1];
                        for (size = arrayList3.size() - 1; size >= 0; size--) {
                            messageObject2 = (MessageObject) arrayList3.get(size);
                            stringForMessage = getStringForMessage(messageObject2, false, zArr);
                            if (stringForMessage != null) {
                                stringForMessage = chat == null ? stringForMessage.replace(" @ " + string, TtmlNode.ANONYMOUS_REGION_ID) : zArr[0] ? stringForMessage.replace(string + ": ", TtmlNode.ANONYMOUS_REGION_ID) : stringForMessage.replace(string + " ", TtmlNode.ANONYMOUS_REGION_ID);
                                if (stringBuilder.length() > 0) {
                                    stringBuilder.append("\n\n");
                                }
                                stringBuilder.append(stringForMessage);
                                a.m1261a(stringForMessage);
                                a2.m1290a(stringForMessage, ((long) messageObject2.messageOwner.date) * 1000, null);
                            }
                        }
                        intent = new Intent(ApplicationLoader.applicationContext, LaunchActivity.class);
                        intent.setAction("com.tmessages.openchat" + Math.random() + Integer.MAX_VALUE);
                        intent.setFlags(TLRPC.MESSAGE_FLAG_EDITED);
                        if (chat != null) {
                            intent.putExtra("chatId", chat.id);
                        } else if (user != null) {
                            intent.putExtra("userId", user.id);
                        }
                        activity = PendingIntent.getActivity(ApplicationLoader.applicationContext, 0, intent, 1073741824);
                        c0287t = new C0287t();
                        if (c0262a != null) {
                            c0287t.m1314a(c0262a);
                        }
                        str = null;
                        if (chat != null) {
                            str = "tgchat" + chat.id + "_" + id;
                        } else if (user != null) {
                            str = "tguser" + user.id + "_" + id;
                        }
                        c0287t.m1315a(str);
                        c0287t2 = new C0287t();
                        c0287t2.m1315a("summary_" + str);
                        c0266d.m1236a(c0287t2);
                        a3 = new C0266d(ApplicationLoader.applicationContext).m1238a(string).m1227a((int) R.drawable.notification).m1250c("messages").m1245b(stringBuilder.toString()).m1240a(true).m1243b(arrayList3.size()).m1255e(-13851168).m1251c(false).m1231a(((long) ((MessageObject) arrayList3.get(0)).messageOwner.date) * 1000).m1237a(a2).m1232a(activity).m1236a(c0287t).m1236a(new C0274f().m1278a(a.m1262a())).m1239a("msg");
                        if (tLObject != null) {
                            imageFromMemory = ImageLoader.getInstance().getImageFromMemory(tLObject, null, "50_50");
                            if (imageFromMemory == null) {
                                a3.m1233a(imageFromMemory.getBitmap());
                            } else {
                                try {
                                    pathToAttach = FileLoader.getPathToAttach(tLObject, true);
                                    if (pathToAttach.exists()) {
                                        dp = 160.0f / ((float) AndroidUtilities.dp(50.0f));
                                        options = new Options();
                                        options.inSampleSize = dp >= 1.0f ? 1 : (int) dp;
                                        decodeFile = BitmapFactory.decodeFile(pathToAttach.getAbsolutePath(), options);
                                        if (decodeFile != null) {
                                            a3.m1233a(decodeFile);
                                        }
                                    }
                                } catch (Throwable th) {
                                }
                            }
                        }
                        if (chat == null && user != null && user.phone != null && user.phone.length() > 0) {
                            a3.m1246b("tel:+" + user.phone);
                        }
                        this.notificationManager.m1383a(valueOf.intValue(), a3.m1242b());
                        this.wearNotificationsIds.put(Long.valueOf(longValue), valueOf);
                    }
                } else {
                    Chat chat2 = MessagesController.getInstance().getChat(Integer.valueOf(-((int) longValue)));
                    if (chat2 != null) {
                        chat = chat2;
                        user = null;
                        if (AndroidUtilities.needShowPasscode(false)) {
                        }
                        string = LocaleController.getString("AppName", R.string.AppName);
                        tLObject = null;
                        num = (Integer) hashMap2.get(Long.valueOf(longValue));
                        if (num != null) {
                            hashMap2.remove(Long.valueOf(longValue));
                            valueOf = num;
                        } else {
                            valueOf = Integer.valueOf((int) longValue);
                        }
                        a = new C0270a(string).m1258a(((long) i2) * 1000);
                        intent = new Intent(ApplicationLoader.applicationContext, AutoMessageHeardReceiver.class);
                        intent.addFlags(32);
                        intent.setAction("org.telegram.messenger.ACTION_MESSAGE_HEARD");
                        intent.putExtra("dialog_id", longValue);
                        intent.putExtra("max_id", id);
                        a.m1259a(PendingIntent.getBroadcast(ApplicationLoader.applicationContext, valueOf.intValue(), intent, 134217728));
                        if (!ChatObject.isChannel(chat)) {
                        }
                        intent = new Intent(ApplicationLoader.applicationContext, AutoMessageReplyReceiver.class);
                        intent.addFlags(32);
                        intent.setAction("org.telegram.messenger.ACTION_MESSAGE_REPLY");
                        intent.putExtra("dialog_id", longValue);
                        intent.putExtra("max_id", id);
                        a.m1260a(PendingIntent.getBroadcast(ApplicationLoader.applicationContext, valueOf.intValue(), intent, 134217728), new C0307a(EXTRA_VOICE_REPLY).m1391a(LocaleController.getString("Reply", R.string.Reply)).m1392a());
                        intent = new Intent(ApplicationLoader.applicationContext, WearReplyReceiver.class);
                        intent.putExtra("dialog_id", longValue);
                        intent.putExtra("max_id", id);
                        if (chat == null) {
                        }
                        c0262a = new C0260a(R.drawable.ic_reply_icon, chat == null ? LocaleController.formatString("ReplyToGroup", R.string.ReplyToGroup, string) : LocaleController.formatString("ReplyToUser", R.string.ReplyToUser, string), PendingIntent.getBroadcast(ApplicationLoader.applicationContext, valueOf.intValue(), intent, 134217728)).m1203a(true).m1202a(new C0307a(EXTRA_VOICE_REPLY).m1391a(LocaleController.getString("Reply", R.string.Reply)).m1392a()).m1204a();
                        num = (Integer) this.pushDialogs.get(Long.valueOf(longValue));
                        if (num == null) {
                            num = Integer.valueOf(0);
                        }
                        c0277i = new C0277i(null);
                        charSequenceArr = new Object[2];
                        charSequenceArr[0] = string;
                        charSequenceArr[1] = LocaleController.formatPluralString("NewMessages", Math.max(num.intValue(), arrayList3.size()));
                        a2 = c0277i.m1289a(String.format("%1$s (%2$s)", charSequenceArr));
                        stringBuilder = new StringBuilder();
                        zArr = new boolean[1];
                        for (size = arrayList3.size() - 1; size >= 0; size--) {
                            messageObject2 = (MessageObject) arrayList3.get(size);
                            stringForMessage = getStringForMessage(messageObject2, false, zArr);
                            if (stringForMessage != null) {
                                if (chat == null) {
                                    if (zArr[0]) {
                                    }
                                }
                                if (stringBuilder.length() > 0) {
                                    stringBuilder.append("\n\n");
                                }
                                stringBuilder.append(stringForMessage);
                                a.m1261a(stringForMessage);
                                a2.m1290a(stringForMessage, ((long) messageObject2.messageOwner.date) * 1000, null);
                            }
                        }
                        intent = new Intent(ApplicationLoader.applicationContext, LaunchActivity.class);
                        intent.setAction("com.tmessages.openchat" + Math.random() + Integer.MAX_VALUE);
                        intent.setFlags(TLRPC.MESSAGE_FLAG_EDITED);
                        if (chat != null) {
                            intent.putExtra("chatId", chat.id);
                        } else if (user != null) {
                            intent.putExtra("userId", user.id);
                        }
                        activity = PendingIntent.getActivity(ApplicationLoader.applicationContext, 0, intent, 1073741824);
                        c0287t = new C0287t();
                        if (c0262a != null) {
                            c0287t.m1314a(c0262a);
                        }
                        str = null;
                        if (chat != null) {
                            str = "tgchat" + chat.id + "_" + id;
                        } else if (user != null) {
                            str = "tguser" + user.id + "_" + id;
                        }
                        c0287t.m1315a(str);
                        c0287t2 = new C0287t();
                        c0287t2.m1315a("summary_" + str);
                        c0266d.m1236a(c0287t2);
                        a3 = new C0266d(ApplicationLoader.applicationContext).m1238a(string).m1227a((int) R.drawable.notification).m1250c("messages").m1245b(stringBuilder.toString()).m1240a(true).m1243b(arrayList3.size()).m1255e(-13851168).m1251c(false).m1231a(((long) ((MessageObject) arrayList3.get(0)).messageOwner.date) * 1000).m1237a(a2).m1232a(activity).m1236a(c0287t).m1236a(new C0274f().m1278a(a.m1262a())).m1239a("msg");
                        if (tLObject != null) {
                            imageFromMemory = ImageLoader.getInstance().getImageFromMemory(tLObject, null, "50_50");
                            if (imageFromMemory == null) {
                                pathToAttach = FileLoader.getPathToAttach(tLObject, true);
                                if (pathToAttach.exists()) {
                                    dp = 160.0f / ((float) AndroidUtilities.dp(50.0f));
                                    options = new Options();
                                    if (dp >= 1.0f) {
                                    }
                                    options.inSampleSize = dp >= 1.0f ? 1 : (int) dp;
                                    decodeFile = BitmapFactory.decodeFile(pathToAttach.getAbsolutePath(), options);
                                    if (decodeFile != null) {
                                        a3.m1233a(decodeFile);
                                    }
                                }
                            } else {
                                a3.m1233a(imageFromMemory.getBitmap());
                            }
                        }
                        a3.m1246b("tel:+" + user.phone);
                        this.notificationManager.m1383a(valueOf.intValue(), a3.m1242b());
                        this.wearNotificationsIds.put(Long.valueOf(longValue), valueOf);
                    }
                }
            }
            for (Entry value : hashMap2.entrySet()) {
                this.notificationManager.m1382a(((Integer) value.getValue()).intValue());
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void showOrUpdateNotification(boolean r34) {
        /*
        r33 = this;
        r4 = org.telegram.messenger.UserConfig.isClientActivated();
        if (r4 == 0) goto L_0x0010;
    L_0x0006:
        r0 = r33;
        r4 = r0.pushMessages;
        r4 = r4.isEmpty();
        if (r4 == 0) goto L_0x0014;
    L_0x0010:
        r33.dismissNotification();
    L_0x0013:
        return;
    L_0x0014:
        r4 = org.telegram.tgnet.ConnectionsManager.getInstance();	 Catch:{ Exception -> 0x0046 }
        r4.resumeNetworkMaybe();	 Catch:{ Exception -> 0x0046 }
        r0 = r33;
        r4 = r0.pushMessages;	 Catch:{ Exception -> 0x0046 }
        r5 = 0;
        r4 = r4.get(r5);	 Catch:{ Exception -> 0x0046 }
        r4 = (org.telegram.messenger.MessageObject) r4;	 Catch:{ Exception -> 0x0046 }
        r5 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0046 }
        r6 = "Notifications";
        r7 = 0;
        r16 = r5.getSharedPreferences(r6, r7);	 Catch:{ Exception -> 0x0046 }
        r5 = "dismissDate";
        r6 = 0;
        r0 = r16;
        r23 = r0.getInt(r5, r6);	 Catch:{ Exception -> 0x0046 }
        r5 = r4.messageOwner;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.date;	 Catch:{ Exception -> 0x0046 }
        r0 = r23;
        if (r5 > r0) goto L_0x004b;
    L_0x0042:
        r33.dismissNotification();	 Catch:{ Exception -> 0x0046 }
        goto L_0x0013;
    L_0x0046:
        r4 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r4);
        goto L_0x0013;
    L_0x004b:
        r24 = r4.getDialogId();	 Catch:{ Exception -> 0x0046 }
        r5 = r4.messageOwner;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.mentioned;	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x0964;
    L_0x0055:
        r5 = r4.messageOwner;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.from_id;	 Catch:{ Exception -> 0x0046 }
        r6 = (long) r5;	 Catch:{ Exception -> 0x0046 }
        r14 = r6;
    L_0x005b:
        r4.getId();	 Catch:{ Exception -> 0x0046 }
        r5 = r4.messageOwner;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.to_id;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.chat_id;	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x0598;
    L_0x0066:
        r5 = r4.messageOwner;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.to_id;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.chat_id;	 Catch:{ Exception -> 0x0046 }
        r22 = r5;
    L_0x006e:
        r5 = r4.messageOwner;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.to_id;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.user_id;	 Catch:{ Exception -> 0x0046 }
        if (r5 != 0) goto L_0x05a2;
    L_0x0076:
        r5 = r4.messageOwner;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.from_id;	 Catch:{ Exception -> 0x0046 }
        r21 = r5;
    L_0x007c:
        r5 = org.telegram.messenger.MessagesController.getInstance();	 Catch:{ Exception -> 0x0046 }
        r6 = java.lang.Integer.valueOf(r21);	 Catch:{ Exception -> 0x0046 }
        r26 = r5.getUser(r6);	 Catch:{ Exception -> 0x0046 }
        r5 = 0;
        if (r22 == 0) goto L_0x095c;
    L_0x008b:
        r5 = org.telegram.messenger.MessagesController.getInstance();	 Catch:{ Exception -> 0x0046 }
        r6 = java.lang.Integer.valueOf(r22);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.getChat(r6);	 Catch:{ Exception -> 0x0046 }
        r20 = r5;
    L_0x0099:
        if (r26 == 0) goto L_0x00b0;
    L_0x009b:
        r5 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0046 }
        r0 = r26;
        r6 = r0.id;	 Catch:{ Exception -> 0x0046 }
        r6 = (long) r6;	 Catch:{ Exception -> 0x0046 }
        r5 = utils.p178a.C3791b.m13957c(r5, r6);	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x00b0;
    L_0x00a8:
        r5 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0046 }
        r5 = utils.p178a.C3791b.m14060x(r5);	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x0013;
    L_0x00b0:
        if (r20 == 0) goto L_0x00c7;
    L_0x00b2:
        r5 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0046 }
        r0 = r20;
        r6 = r0.id;	 Catch:{ Exception -> 0x0046 }
        r6 = (long) r6;	 Catch:{ Exception -> 0x0046 }
        r5 = utils.p178a.C3791b.m13957c(r5, r6);	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x00c7;
    L_0x00bf:
        r5 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0046 }
        r5 = utils.p178a.C3791b.m14060x(r5);	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x0013;
    L_0x00c7:
        r7 = 0;
        r12 = 0;
        r11 = 0;
        r9 = 0;
        r10 = -16776961; // 0xffffffffff0000ff float:-1.7014636E38 double:NaN;
        r8 = 0;
        r6 = 0;
        r0 = r33;
        r1 = r16;
        r5 = r0.getNotifyOverride(r1, r14);	 Catch:{ Exception -> 0x0046 }
        if (r34 == 0) goto L_0x00ff;
    L_0x00da:
        r13 = 2;
        if (r5 == r13) goto L_0x00ff;
    L_0x00dd:
        r13 = "EnableAll";
        r17 = 1;
        r0 = r16;
        r1 = r17;
        r13 = r0.getBoolean(r13, r1);	 Catch:{ Exception -> 0x0046 }
        if (r13 == 0) goto L_0x00fd;
    L_0x00ec:
        if (r22 == 0) goto L_0x0100;
    L_0x00ee:
        r13 = "EnableGroup";
        r17 = 1;
        r0 = r16;
        r1 = r17;
        r13 = r0.getBoolean(r13, r1);	 Catch:{ Exception -> 0x0046 }
        if (r13 != 0) goto L_0x0100;
    L_0x00fd:
        if (r5 != 0) goto L_0x0100;
    L_0x00ff:
        r12 = 1;
    L_0x0100:
        if (r12 != 0) goto L_0x0958;
    L_0x0102:
        r5 = (r24 > r14 ? 1 : (r24 == r14 ? 0 : -1));
        if (r5 != 0) goto L_0x0958;
    L_0x0106:
        if (r20 == 0) goto L_0x0958;
    L_0x0108:
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r5.<init>();	 Catch:{ Exception -> 0x0046 }
        r13 = "custom_";
        r5 = r5.append(r13);	 Catch:{ Exception -> 0x0046 }
        r0 = r24;
        r5 = r5.append(r0);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x0046 }
        r13 = 0;
        r0 = r16;
        r5 = r0.getBoolean(r5, r13);	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x05b0;
    L_0x0127:
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r5.<init>();	 Catch:{ Exception -> 0x0046 }
        r13 = "smart_max_count_";
        r5 = r5.append(r13);	 Catch:{ Exception -> 0x0046 }
        r0 = r24;
        r5 = r5.append(r0);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x0046 }
        r13 = 2;
        r0 = r16;
        r13 = r0.getInt(r5, r13);	 Catch:{ Exception -> 0x0046 }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r5.<init>();	 Catch:{ Exception -> 0x0046 }
        r14 = "smart_delay_";
        r5 = r5.append(r14);	 Catch:{ Exception -> 0x0046 }
        r0 = r24;
        r5 = r5.append(r0);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x0046 }
        r14 = 180; // 0xb4 float:2.52E-43 double:8.9E-322;
        r0 = r16;
        r5 = r0.getInt(r5, r14);	 Catch:{ Exception -> 0x0046 }
        r14 = r13;
        r13 = r5;
    L_0x0164:
        if (r14 == 0) goto L_0x0958;
    L_0x0166:
        r0 = r33;
        r5 = r0.smartNotificationsDialogs;	 Catch:{ Exception -> 0x0046 }
        r15 = java.lang.Long.valueOf(r24);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.get(r15);	 Catch:{ Exception -> 0x0046 }
        r5 = (android.graphics.Point) r5;	 Catch:{ Exception -> 0x0046 }
        if (r5 != 0) goto L_0x05b7;
    L_0x0176:
        r5 = new android.graphics.Point;	 Catch:{ Exception -> 0x0046 }
        r13 = 1;
        r14 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0046 }
        r18 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r14 = r14 / r18;
        r14 = (int) r14;	 Catch:{ Exception -> 0x0046 }
        r5.<init>(r13, r14);	 Catch:{ Exception -> 0x0046 }
        r0 = r33;
        r13 = r0.smartNotificationsDialogs;	 Catch:{ Exception -> 0x0046 }
        r14 = java.lang.Long.valueOf(r24);	 Catch:{ Exception -> 0x0046 }
        r13.put(r14, r5);	 Catch:{ Exception -> 0x0046 }
        r19 = r12;
    L_0x0192:
        r5 = android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;	 Catch:{ Exception -> 0x0046 }
        r27 = r5.getPath();	 Catch:{ Exception -> 0x0046 }
        if (r19 != 0) goto L_0x094e;
    L_0x019a:
        r5 = "EnableInAppSounds";
        r8 = 1;
        r0 = r16;
        r14 = r0.getBoolean(r5, r8);	 Catch:{ Exception -> 0x0046 }
        r5 = "EnableInAppVibrate";
        r8 = 1;
        r0 = r16;
        r15 = r0.getBoolean(r5, r8);	 Catch:{ Exception -> 0x0046 }
        r5 = "EnableInAppPreview";
        r8 = 1;
        r0 = r16;
        r9 = r0.getBoolean(r5, r8);	 Catch:{ Exception -> 0x0046 }
        r5 = "EnableInAppPriority";
        r8 = 0;
        r0 = r16;
        r17 = r0.getBoolean(r5, r8);	 Catch:{ Exception -> 0x0046 }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r5.<init>();	 Catch:{ Exception -> 0x0046 }
        r8 = "custom_";
        r5 = r5.append(r8);	 Catch:{ Exception -> 0x0046 }
        r0 = r24;
        r5 = r5.append(r0);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x0046 }
        r8 = 0;
        r0 = r16;
        r18 = r0.getBoolean(r5, r8);	 Catch:{ Exception -> 0x0046 }
        if (r18 == 0) goto L_0x05f5;
    L_0x01e1:
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r5.<init>();	 Catch:{ Exception -> 0x0046 }
        r8 = "vibrate_";
        r5 = r5.append(r8);	 Catch:{ Exception -> 0x0046 }
        r0 = r24;
        r5 = r5.append(r0);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x0046 }
        r8 = 0;
        r0 = r16;
        r5 = r0.getInt(r5, r8);	 Catch:{ Exception -> 0x0046 }
        r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r8.<init>();	 Catch:{ Exception -> 0x0046 }
        r12 = "priority_";
        r8 = r8.append(r12);	 Catch:{ Exception -> 0x0046 }
        r0 = r24;
        r8 = r8.append(r0);	 Catch:{ Exception -> 0x0046 }
        r8 = r8.toString();	 Catch:{ Exception -> 0x0046 }
        r12 = 3;
        r0 = r16;
        r8 = r0.getInt(r8, r12);	 Catch:{ Exception -> 0x0046 }
        r12 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r12.<init>();	 Catch:{ Exception -> 0x0046 }
        r13 = "sound_path_";
        r12 = r12.append(r13);	 Catch:{ Exception -> 0x0046 }
        r0 = r24;
        r12 = r12.append(r0);	 Catch:{ Exception -> 0x0046 }
        r12 = r12.toString();	 Catch:{ Exception -> 0x0046 }
        r13 = 0;
        r0 = r16;
        r13 = r0.getString(r12, r13);	 Catch:{ Exception -> 0x0046 }
    L_0x0238:
        r12 = 0;
        if (r22 == 0) goto L_0x0609;
    L_0x023b:
        if (r13 == 0) goto L_0x05fa;
    L_0x023d:
        r0 = r27;
        r6 = r13.equals(r0);	 Catch:{ Exception -> 0x0046 }
        if (r6 == 0) goto L_0x05fa;
    L_0x0245:
        r11 = 0;
    L_0x0246:
        r6 = "vibrate_group";
        r10 = 0;
        r0 = r16;
        r6 = r0.getInt(r6, r10);	 Catch:{ Exception -> 0x0046 }
        r10 = "priority_group";
        r13 = 1;
        r0 = r16;
        r13 = r0.getInt(r10, r13);	 Catch:{ Exception -> 0x0046 }
        r10 = "GroupLed";
        r28 = -16776961; // 0xffffffffff0000ff float:-1.7014636E38 double:NaN;
        r0 = r16;
        r1 = r28;
        r10 = r0.getInt(r10, r1);	 Catch:{ Exception -> 0x0046 }
    L_0x0268:
        if (r18 == 0) goto L_0x02b2;
    L_0x026a:
        r18 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r18.<init>();	 Catch:{ Exception -> 0x0046 }
        r28 = "color_";
        r0 = r18;
        r1 = r28;
        r18 = r0.append(r1);	 Catch:{ Exception -> 0x0046 }
        r0 = r18;
        r1 = r24;
        r18 = r0.append(r1);	 Catch:{ Exception -> 0x0046 }
        r18 = r18.toString();	 Catch:{ Exception -> 0x0046 }
        r0 = r16;
        r1 = r18;
        r18 = r0.contains(r1);	 Catch:{ Exception -> 0x0046 }
        if (r18 == 0) goto L_0x02b2;
    L_0x0290:
        r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r10.<init>();	 Catch:{ Exception -> 0x0046 }
        r18 = "color_";
        r0 = r18;
        r10 = r10.append(r0);	 Catch:{ Exception -> 0x0046 }
        r0 = r24;
        r10 = r10.append(r0);	 Catch:{ Exception -> 0x0046 }
        r10 = r10.toString();	 Catch:{ Exception -> 0x0046 }
        r18 = 0;
        r0 = r16;
        r1 = r18;
        r10 = r0.getInt(r10, r1);	 Catch:{ Exception -> 0x0046 }
    L_0x02b2:
        r16 = 3;
        r0 = r16;
        if (r8 == r0) goto L_0x093d;
    L_0x02b8:
        r13 = 4;
        if (r6 != r13) goto L_0x02c2;
    L_0x02bb:
        r6 = 1;
        r12 = 0;
        r32 = r6;
        r6 = r12;
        r12 = r32;
    L_0x02c2:
        r13 = 2;
        if (r6 != r13) goto L_0x02cb;
    L_0x02c5:
        r13 = 1;
        if (r5 == r13) goto L_0x02d6;
    L_0x02c8:
        r13 = 3;
        if (r5 == r13) goto L_0x02d6;
    L_0x02cb:
        r13 = 2;
        if (r6 == r13) goto L_0x02d1;
    L_0x02ce:
        r13 = 2;
        if (r5 == r13) goto L_0x02d6;
    L_0x02d1:
        if (r5 == 0) goto L_0x093a;
    L_0x02d3:
        r13 = 4;
        if (r5 == r13) goto L_0x093a;
    L_0x02d6:
        r6 = org.telegram.messenger.ApplicationLoader.mainInterfacePaused;	 Catch:{ Exception -> 0x0046 }
        if (r6 != 0) goto L_0x02e3;
    L_0x02da:
        if (r14 != 0) goto L_0x02dd;
    L_0x02dc:
        r11 = 0;
    L_0x02dd:
        if (r15 != 0) goto L_0x02e0;
    L_0x02df:
        r5 = 2;
    L_0x02e0:
        if (r17 != 0) goto L_0x0648;
    L_0x02e2:
        r8 = 0;
    L_0x02e3:
        if (r12 == 0) goto L_0x0652;
    L_0x02e5:
        r6 = 2;
        if (r5 == r6) goto L_0x0652;
    L_0x02e8:
        r0 = r33;
        r6 = r0.audioManager;	 Catch:{ Exception -> 0x064e }
        r6 = r6.getRingerMode();	 Catch:{ Exception -> 0x064e }
        if (r6 == 0) goto L_0x02f6;
    L_0x02f2:
        r12 = 1;
        if (r6 == r12) goto L_0x02f6;
    L_0x02f5:
        r5 = 2;
    L_0x02f6:
        r14 = r8;
        r15 = r9;
        r16 = r10;
        r17 = r11;
        r18 = r5;
    L_0x02fe:
        r6 = new android.content.Intent;	 Catch:{ Exception -> 0x0046 }
        r5 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0046 }
        r8 = org.telegram.ui.LaunchActivity.class;
        r6.<init>(r5, r8);	 Catch:{ Exception -> 0x0046 }
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r5.<init>();	 Catch:{ Exception -> 0x0046 }
        r8 = "com.tmessages.openchat";
        r5 = r5.append(r8);	 Catch:{ Exception -> 0x0046 }
        r8 = java.lang.Math.random();	 Catch:{ Exception -> 0x0046 }
        r5 = r5.append(r8);	 Catch:{ Exception -> 0x0046 }
        r8 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r5 = r5.append(r8);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x0046 }
        r6.setAction(r5);	 Catch:{ Exception -> 0x0046 }
        r5 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        r6.setFlags(r5);	 Catch:{ Exception -> 0x0046 }
        r0 = r24;
        r5 = (int) r0;	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x06d5;
    L_0x0334:
        r0 = r33;
        r5 = r0.pushDialogs;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.size();	 Catch:{ Exception -> 0x0046 }
        r8 = 1;
        if (r5 != r8) goto L_0x0349;
    L_0x033f:
        if (r22 == 0) goto L_0x065c;
    L_0x0341:
        r5 = "chatId";
        r0 = r22;
        r6.putExtra(r5, r0);	 Catch:{ Exception -> 0x0046 }
    L_0x0349:
        r5 = 0;
        r5 = org.telegram.messenger.AndroidUtilities.needShowPasscode(r5);	 Catch:{ Exception -> 0x0046 }
        if (r5 != 0) goto L_0x0354;
    L_0x0350:
        r5 = org.telegram.messenger.UserConfig.isWaitingForPasscodeEnter;	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x0668;
    L_0x0354:
        r5 = 0;
        r13 = r5;
    L_0x0356:
        r5 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0046 }
        r7 = 0;
        r8 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r7 = android.app.PendingIntent.getActivity(r5, r7, r6, r8);	 Catch:{ Exception -> 0x0046 }
        r5 = 1;
        r0 = r24;
        r6 = (int) r0;	 Catch:{ Exception -> 0x0046 }
        if (r6 == 0) goto L_0x037b;
    L_0x0365:
        r0 = r33;
        r6 = r0.pushDialogs;	 Catch:{ Exception -> 0x0046 }
        r6 = r6.size();	 Catch:{ Exception -> 0x0046 }
        r8 = 1;
        if (r6 > r8) goto L_0x037b;
    L_0x0370:
        r6 = 0;
        r6 = org.telegram.messenger.AndroidUtilities.needShowPasscode(r6);	 Catch:{ Exception -> 0x0046 }
        if (r6 != 0) goto L_0x037b;
    L_0x0377:
        r6 = org.telegram.messenger.UserConfig.isWaitingForPasscodeEnter;	 Catch:{ Exception -> 0x0046 }
        if (r6 == 0) goto L_0x06ee;
    L_0x037b:
        r5 = "AppName";
        r6 = 2131230885; // 0x7f0800a5 float:1.8077835E38 double:1.0529679636E-314;
        r6 = org.telegram.messenger.LocaleController.getString(r5, r6);	 Catch:{ Exception -> 0x0046 }
        r5 = 0;
        r11 = r5;
        r12 = r6;
    L_0x0388:
        r0 = r33;
        r5 = r0.pushDialogs;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.size();	 Catch:{ Exception -> 0x0046 }
        r6 = 1;
        if (r5 != r6) goto L_0x0700;
    L_0x0393:
        r5 = "NewMessages";
        r0 = r33;
        r6 = r0.total_unread_count;	 Catch:{ Exception -> 0x0046 }
        r5 = org.telegram.messenger.LocaleController.formatPluralString(r5, r6);	 Catch:{ Exception -> 0x0046 }
        r10 = r5;
    L_0x039f:
        r5 = new android.support.v4.app.al$d;	 Catch:{ Exception -> 0x0046 }
        r6 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0046 }
        r5.<init>(r6);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.m1238a(r12);	 Catch:{ Exception -> 0x0046 }
        r6 = 2130838136; // 0x7f020278 float:1.7281246E38 double:1.05277392E-314;
        r5 = r5.m1227a(r6);	 Catch:{ Exception -> 0x0046 }
        r6 = 1;
        r5 = r5.m1240a(r6);	 Catch:{ Exception -> 0x0046 }
        r0 = r33;
        r6 = r0.total_unread_count;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.m1243b(r6);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.m1232a(r7);	 Catch:{ Exception -> 0x0046 }
        r6 = "messages";
        r5 = r5.m1250c(r6);	 Catch:{ Exception -> 0x0046 }
        r6 = 1;
        r5 = r5.m1251c(r6);	 Catch:{ Exception -> 0x0046 }
        r6 = -13851168; // 0xffffffffff2ca5e0 float:-2.2948849E38 double:NaN;
        r21 = r5.m1255e(r6);	 Catch:{ Exception -> 0x0046 }
        r5 = "msg";
        r0 = r21;
        r0.m1239a(r5);	 Catch:{ Exception -> 0x0046 }
        if (r20 != 0) goto L_0x040e;
    L_0x03df:
        if (r26 == 0) goto L_0x040e;
    L_0x03e1:
        r0 = r26;
        r5 = r0.phone;	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x040e;
    L_0x03e7:
        r0 = r26;
        r5 = r0.phone;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.length();	 Catch:{ Exception -> 0x0046 }
        if (r5 <= 0) goto L_0x040e;
    L_0x03f1:
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r5.<init>();	 Catch:{ Exception -> 0x0046 }
        r6 = "tel:+";
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x0046 }
        r0 = r26;
        r6 = r0.phone;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x0046 }
        r0 = r21;
        r0.m1246b(r5);	 Catch:{ Exception -> 0x0046 }
    L_0x040e:
        r7 = 2;
        r6 = 0;
        r0 = r33;
        r5 = r0.pushMessages;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.size();	 Catch:{ Exception -> 0x0046 }
        r8 = 1;
        if (r5 != r8) goto L_0x077a;
    L_0x041b:
        r0 = r33;
        r5 = r0.pushMessages;	 Catch:{ Exception -> 0x0046 }
        r6 = 0;
        r5 = r5.get(r6);	 Catch:{ Exception -> 0x0046 }
        r5 = (org.telegram.messenger.MessageObject) r5;	 Catch:{ Exception -> 0x0046 }
        r6 = 1;
        r8 = new boolean[r6];	 Catch:{ Exception -> 0x0046 }
        r6 = 0;
        r0 = r33;
        r6 = r0.getStringForMessage(r5, r6, r8);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.messageOwner;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.silent;	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x0738;
    L_0x0436:
        r7 = 1;
    L_0x0437:
        if (r6 == 0) goto L_0x0013;
    L_0x0439:
        if (r11 == 0) goto L_0x0937;
    L_0x043b:
        if (r20 == 0) goto L_0x073b;
    L_0x043d:
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r5.<init>();	 Catch:{ Exception -> 0x0046 }
        r8 = " @ ";
        r5 = r5.append(r8);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.append(r12);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x0046 }
        r8 = "";
        r5 = r6.replace(r5, r8);	 Catch:{ Exception -> 0x0046 }
    L_0x0458:
        r0 = r21;
        r0.m1245b(r5);	 Catch:{ Exception -> 0x0046 }
        r8 = new android.support.v4.app.al$c;	 Catch:{ Exception -> 0x0046 }
        r8.<init>();	 Catch:{ Exception -> 0x0046 }
        r5 = r8.m1223a(r5);	 Catch:{ Exception -> 0x0046 }
        r0 = r21;
        r0.m1237a(r5);	 Catch:{ Exception -> 0x0046 }
        r5 = r6;
    L_0x046c:
        r6 = new android.content.Intent;	 Catch:{ Exception -> 0x0046 }
        r8 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0046 }
        r9 = org.telegram.messenger.NotificationDismissReceiver.class;
        r6.<init>(r8, r9);	 Catch:{ Exception -> 0x0046 }
        r8 = "messageDate";
        r4 = r4.messageOwner;	 Catch:{ Exception -> 0x0046 }
        r4 = r4.date;	 Catch:{ Exception -> 0x0046 }
        r6.putExtra(r8, r4);	 Catch:{ Exception -> 0x0046 }
        r4 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0046 }
        r8 = 1;
        r9 = 134217728; // 0x8000000 float:3.85186E-34 double:6.63123685E-316;
        r4 = android.app.PendingIntent.getBroadcast(r4, r8, r6, r9);	 Catch:{ Exception -> 0x0046 }
        r0 = r21;
        r0.m1244b(r4);	 Catch:{ Exception -> 0x0046 }
        if (r13 == 0) goto L_0x04a6;
    L_0x048f:
        r4 = org.telegram.messenger.ImageLoader.getInstance();	 Catch:{ Exception -> 0x0046 }
        r6 = 0;
        r8 = "50_50";
        r4 = r4.getImageFromMemory(r13, r6, r8);	 Catch:{ Exception -> 0x0046 }
        if (r4 == 0) goto L_0x0867;
    L_0x049d:
        r4 = r4.getBitmap();	 Catch:{ Exception -> 0x0046 }
        r0 = r21;
        r0.m1233a(r4);	 Catch:{ Exception -> 0x0046 }
    L_0x04a6:
        if (r34 == 0) goto L_0x04ab;
    L_0x04a8:
        r4 = 1;
        if (r7 != r4) goto L_0x08a0;
    L_0x04ab:
        r4 = -1;
        r0 = r21;
        r0.m1253d(r4);	 Catch:{ Exception -> 0x0046 }
    L_0x04b1:
        r4 = 1;
        if (r7 == r4) goto L_0x08ff;
    L_0x04b4:
        if (r19 != 0) goto L_0x08ff;
    L_0x04b6:
        r4 = org.telegram.messenger.ApplicationLoader.mainInterfacePaused;	 Catch:{ Exception -> 0x0046 }
        if (r4 != 0) goto L_0x04bc;
    L_0x04ba:
        if (r15 == 0) goto L_0x04f0;
    L_0x04bc:
        r4 = r5.length();	 Catch:{ Exception -> 0x0046 }
        r6 = 100;
        if (r4 <= r6) goto L_0x0929;
    L_0x04c4:
        r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r4.<init>();	 Catch:{ Exception -> 0x0046 }
        r6 = 0;
        r7 = 100;
        r5 = r5.substring(r6, r7);	 Catch:{ Exception -> 0x0046 }
        r6 = 10;
        r7 = 32;
        r5 = r5.replace(r6, r7);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.trim();	 Catch:{ Exception -> 0x0046 }
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x0046 }
        r5 = "...";
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x0046 }
        r4 = r4.toString();	 Catch:{ Exception -> 0x0046 }
    L_0x04eb:
        r0 = r21;
        r0.m1249c(r4);	 Catch:{ Exception -> 0x0046 }
    L_0x04f0:
        r4 = org.telegram.messenger.MediaController.getInstance();	 Catch:{ Exception -> 0x0046 }
        r4 = r4.isRecordingAudio();	 Catch:{ Exception -> 0x0046 }
        if (r4 != 0) goto L_0x0519;
    L_0x04fa:
        if (r17 == 0) goto L_0x0519;
    L_0x04fc:
        r4 = "NoSound";
        r0 = r17;
        r4 = r0.equals(r4);	 Catch:{ Exception -> 0x0046 }
        if (r4 != 0) goto L_0x0519;
    L_0x0507:
        r0 = r17;
        r1 = r27;
        r4 = r0.equals(r1);	 Catch:{ Exception -> 0x0046 }
        if (r4 == 0) goto L_0x08c0;
    L_0x0511:
        r4 = android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;	 Catch:{ Exception -> 0x0046 }
        r5 = 5;
        r0 = r21;
        r0.m1235a(r4, r5);	 Catch:{ Exception -> 0x0046 }
    L_0x0519:
        if (r16 == 0) goto L_0x0526;
    L_0x051b:
        r4 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r5 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0 = r21;
        r1 = r16;
        r0.m1228a(r1, r4, r5);	 Catch:{ Exception -> 0x0046 }
    L_0x0526:
        r4 = 2;
        r0 = r18;
        if (r0 == r4) goto L_0x0535;
    L_0x052b:
        r4 = org.telegram.messenger.MediaController.getInstance();	 Catch:{ Exception -> 0x0046 }
        r4 = r4.isRecordingAudio();	 Catch:{ Exception -> 0x0046 }
        if (r4 == 0) goto L_0x08cc;
    L_0x0535:
        r4 = 2;
        r4 = new long[r4];	 Catch:{ Exception -> 0x0046 }
        r4 = {0, 0};	 Catch:{ Exception -> 0x0046 }
        r0 = r21;
        r0.m1241a(r4);	 Catch:{ Exception -> 0x0046 }
    L_0x0540:
        r4 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x0046 }
        r5 = 24;
        if (r4 >= r5) goto L_0x057e;
    L_0x0546:
        r4 = org.telegram.messenger.UserConfig.passcodeHash;	 Catch:{ Exception -> 0x0046 }
        r4 = r4.length();	 Catch:{ Exception -> 0x0046 }
        if (r4 != 0) goto L_0x057e;
    L_0x054e:
        r4 = r33.hasMessagesToReply();	 Catch:{ Exception -> 0x0046 }
        if (r4 == 0) goto L_0x057e;
    L_0x0554:
        r4 = new android.content.Intent;	 Catch:{ Exception -> 0x0046 }
        r5 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0046 }
        r6 = org.telegram.messenger.PopupReplyReceiver.class;
        r4.<init>(r5, r6);	 Catch:{ Exception -> 0x0046 }
        r5 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x0046 }
        r6 = 19;
        if (r5 > r6) goto L_0x090c;
    L_0x0563:
        r5 = 2130837877; // 0x7f020175 float:1.728072E38 double:1.052773792E-314;
        r6 = "Reply";
        r7 = 2131232228; // 0x7f0805e4 float:1.808056E38 double:1.052968627E-314;
        r6 = org.telegram.messenger.LocaleController.getString(r6, r7);	 Catch:{ Exception -> 0x0046 }
        r7 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0046 }
        r8 = 2;
        r9 = 134217728; // 0x8000000 float:3.85186E-34 double:6.63123685E-316;
        r4 = android.app.PendingIntent.getBroadcast(r7, r8, r4, r9);	 Catch:{ Exception -> 0x0046 }
        r0 = r21;
        r0.m1230a(r5, r6, r4);	 Catch:{ Exception -> 0x0046 }
    L_0x057e:
        r0 = r33;
        r1 = r21;
        r2 = r34;
        r0.showExtraNotifications(r1, r2);	 Catch:{ Exception -> 0x0046 }
        r0 = r33;
        r4 = r0.notificationManager;	 Catch:{ Exception -> 0x0046 }
        r5 = 1;
        r6 = r21.m1242b();	 Catch:{ Exception -> 0x0046 }
        r4.m1383a(r5, r6);	 Catch:{ Exception -> 0x0046 }
        r33.scheduleNotificationRepeat();	 Catch:{ Exception -> 0x0046 }
        goto L_0x0013;
    L_0x0598:
        r5 = r4.messageOwner;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.to_id;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.channel_id;	 Catch:{ Exception -> 0x0046 }
        r22 = r5;
        goto L_0x006e;
    L_0x05a2:
        r6 = org.telegram.messenger.UserConfig.getClientUserId();	 Catch:{ Exception -> 0x0046 }
        if (r5 != r6) goto L_0x0960;
    L_0x05a8:
        r5 = r4.messageOwner;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.from_id;	 Catch:{ Exception -> 0x0046 }
        r21 = r5;
        goto L_0x007c;
    L_0x05b0:
        r13 = 2;
        r5 = 180; // 0xb4 float:2.52E-43 double:8.9E-322;
        r14 = r13;
        r13 = r5;
        goto L_0x0164;
    L_0x05b7:
        r15 = r5.y;	 Catch:{ Exception -> 0x0046 }
        r13 = r13 + r15;
        r0 = (long) r13;	 Catch:{ Exception -> 0x0046 }
        r18 = r0;
        r28 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0046 }
        r30 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r28 = r28 / r30;
        r13 = (r18 > r28 ? 1 : (r18 == r28 ? 0 : -1));
        if (r13 >= 0) goto L_0x05da;
    L_0x05c9:
        r13 = 1;
        r14 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0046 }
        r18 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r14 = r14 / r18;
        r14 = (int) r14;	 Catch:{ Exception -> 0x0046 }
        r5.set(r13, r14);	 Catch:{ Exception -> 0x0046 }
        r19 = r12;
        goto L_0x0192;
    L_0x05da:
        r13 = r5.x;	 Catch:{ Exception -> 0x0046 }
        if (r13 >= r14) goto L_0x05f0;
    L_0x05de:
        r13 = r13 + 1;
        r14 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0046 }
        r18 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r14 = r14 / r18;
        r14 = (int) r14;	 Catch:{ Exception -> 0x0046 }
        r5.set(r13, r14);	 Catch:{ Exception -> 0x0046 }
        r19 = r12;
        goto L_0x0192;
    L_0x05f0:
        r5 = 1;
        r19 = r5;
        goto L_0x0192;
    L_0x05f5:
        r5 = 0;
        r8 = 3;
        r13 = 0;
        goto L_0x0238;
    L_0x05fa:
        if (r13 != 0) goto L_0x094b;
    L_0x05fc:
        r6 = "GroupSoundPath";
        r0 = r16;
        r1 = r27;
        r11 = r0.getString(r6, r1);	 Catch:{ Exception -> 0x0046 }
        goto L_0x0246;
    L_0x0609:
        if (r21 == 0) goto L_0x0943;
    L_0x060b:
        if (r13 == 0) goto L_0x063a;
    L_0x060d:
        r0 = r27;
        r6 = r13.equals(r0);	 Catch:{ Exception -> 0x0046 }
        if (r6 == 0) goto L_0x063a;
    L_0x0615:
        r11 = 0;
    L_0x0616:
        r6 = "vibrate_messages";
        r10 = 0;
        r0 = r16;
        r6 = r0.getInt(r6, r10);	 Catch:{ Exception -> 0x0046 }
        r10 = "priority_group";
        r13 = 1;
        r0 = r16;
        r13 = r0.getInt(r10, r13);	 Catch:{ Exception -> 0x0046 }
        r10 = "MessagesLed";
        r28 = -16776961; // 0xffffffffff0000ff float:-1.7014636E38 double:NaN;
        r0 = r16;
        r1 = r28;
        r10 = r0.getInt(r10, r1);	 Catch:{ Exception -> 0x0046 }
        goto L_0x0268;
    L_0x063a:
        if (r13 != 0) goto L_0x0940;
    L_0x063c:
        r6 = "GlobalSoundPath";
        r0 = r16;
        r1 = r27;
        r11 = r0.getString(r6, r1);	 Catch:{ Exception -> 0x0046 }
        goto L_0x0616;
    L_0x0648:
        r6 = 2;
        if (r8 != r6) goto L_0x02e3;
    L_0x064b:
        r8 = 1;
        goto L_0x02e3;
    L_0x064e:
        r6 = move-exception;
        org.telegram.messenger.FileLog.m13728e(r6);	 Catch:{ Exception -> 0x0046 }
    L_0x0652:
        r14 = r8;
        r15 = r9;
        r16 = r10;
        r17 = r11;
        r18 = r5;
        goto L_0x02fe;
    L_0x065c:
        if (r21 == 0) goto L_0x0349;
    L_0x065e:
        r5 = "userId";
        r0 = r21;
        r6.putExtra(r5, r0);	 Catch:{ Exception -> 0x0046 }
        goto L_0x0349;
    L_0x0668:
        r0 = r33;
        r5 = r0.pushDialogs;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.size();	 Catch:{ Exception -> 0x0046 }
        r8 = 1;
        if (r5 != r8) goto L_0x06eb;
    L_0x0673:
        if (r20 == 0) goto L_0x06a4;
    L_0x0675:
        r0 = r20;
        r5 = r0.photo;	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x06eb;
    L_0x067b:
        r0 = r20;
        r5 = r0.photo;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.photo_small;	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x06eb;
    L_0x0683:
        r0 = r20;
        r5 = r0.photo;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.photo_small;	 Catch:{ Exception -> 0x0046 }
        r8 = r5.volume_id;	 Catch:{ Exception -> 0x0046 }
        r10 = 0;
        r5 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r5 == 0) goto L_0x06eb;
    L_0x0691:
        r0 = r20;
        r5 = r0.photo;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.photo_small;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.local_id;	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x06eb;
    L_0x069b:
        r0 = r20;
        r5 = r0.photo;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.photo_small;	 Catch:{ Exception -> 0x0046 }
        r13 = r5;
        goto L_0x0356;
    L_0x06a4:
        if (r26 == 0) goto L_0x06eb;
    L_0x06a6:
        r0 = r26;
        r5 = r0.photo;	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x06eb;
    L_0x06ac:
        r0 = r26;
        r5 = r0.photo;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.photo_small;	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x06eb;
    L_0x06b4:
        r0 = r26;
        r5 = r0.photo;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.photo_small;	 Catch:{ Exception -> 0x0046 }
        r8 = r5.volume_id;	 Catch:{ Exception -> 0x0046 }
        r10 = 0;
        r5 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r5 == 0) goto L_0x06eb;
    L_0x06c2:
        r0 = r26;
        r5 = r0.photo;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.photo_small;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.local_id;	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x06eb;
    L_0x06cc:
        r0 = r26;
        r5 = r0.photo;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.photo_small;	 Catch:{ Exception -> 0x0046 }
        r13 = r5;
        goto L_0x0356;
    L_0x06d5:
        r0 = r33;
        r5 = r0.pushDialogs;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.size();	 Catch:{ Exception -> 0x0046 }
        r8 = 1;
        if (r5 != r8) goto L_0x06eb;
    L_0x06e0:
        r5 = "encId";
        r8 = 32;
        r8 = r24 >> r8;
        r8 = (int) r8;	 Catch:{ Exception -> 0x0046 }
        r6.putExtra(r5, r8);	 Catch:{ Exception -> 0x0046 }
    L_0x06eb:
        r13 = r7;
        goto L_0x0356;
    L_0x06ee:
        if (r20 == 0) goto L_0x06f8;
    L_0x06f0:
        r0 = r20;
        r6 = r0.title;	 Catch:{ Exception -> 0x0046 }
        r11 = r5;
        r12 = r6;
        goto L_0x0388;
    L_0x06f8:
        r6 = org.telegram.messenger.UserObject.getUserName(r26);	 Catch:{ Exception -> 0x0046 }
        r11 = r5;
        r12 = r6;
        goto L_0x0388;
    L_0x0700:
        r5 = "NotificationMessagesPeopleDisplayOrder";
        r6 = 2131231991; // 0x7f0804f7 float:1.8080079E38 double:1.05296851E-314;
        r8 = 2;
        r8 = new java.lang.Object[r8];	 Catch:{ Exception -> 0x0046 }
        r9 = 0;
        r10 = "NewMessages";
        r0 = r33;
        r0 = r0.total_unread_count;	 Catch:{ Exception -> 0x0046 }
        r21 = r0;
        r0 = r21;
        r10 = org.telegram.messenger.LocaleController.formatPluralString(r10, r0);	 Catch:{ Exception -> 0x0046 }
        r8[r9] = r10;	 Catch:{ Exception -> 0x0046 }
        r9 = 1;
        r10 = "FromChats";
        r0 = r33;
        r0 = r0.pushDialogs;	 Catch:{ Exception -> 0x0046 }
        r21 = r0;
        r21 = r21.size();	 Catch:{ Exception -> 0x0046 }
        r0 = r21;
        r10 = org.telegram.messenger.LocaleController.formatPluralString(r10, r0);	 Catch:{ Exception -> 0x0046 }
        r8[r9] = r10;	 Catch:{ Exception -> 0x0046 }
        r5 = org.telegram.messenger.LocaleController.formatString(r5, r6, r8);	 Catch:{ Exception -> 0x0046 }
        r10 = r5;
        goto L_0x039f;
    L_0x0738:
        r7 = 0;
        goto L_0x0437;
    L_0x073b:
        r5 = 0;
        r5 = r8[r5];	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x075d;
    L_0x0740:
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r5.<init>();	 Catch:{ Exception -> 0x0046 }
        r5 = r5.append(r12);	 Catch:{ Exception -> 0x0046 }
        r8 = ": ";
        r5 = r5.append(r8);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x0046 }
        r8 = "";
        r5 = r6.replace(r5, r8);	 Catch:{ Exception -> 0x0046 }
        goto L_0x0458;
    L_0x075d:
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r5.<init>();	 Catch:{ Exception -> 0x0046 }
        r5 = r5.append(r12);	 Catch:{ Exception -> 0x0046 }
        r8 = " ";
        r5 = r5.append(r8);	 Catch:{ Exception -> 0x0046 }
        r5 = r5.toString();	 Catch:{ Exception -> 0x0046 }
        r8 = "";
        r5 = r6.replace(r5, r8);	 Catch:{ Exception -> 0x0046 }
        goto L_0x0458;
    L_0x077a:
        r0 = r21;
        r0.m1245b(r10);	 Catch:{ Exception -> 0x0046 }
        r22 = new android.support.v4.app.al$h;	 Catch:{ Exception -> 0x0046 }
        r22.<init>();	 Catch:{ Exception -> 0x0046 }
        r0 = r22;
        r0.m1279a(r12);	 Catch:{ Exception -> 0x0046 }
        r5 = 10;
        r0 = r33;
        r8 = r0.pushMessages;	 Catch:{ Exception -> 0x0046 }
        r8 = r8.size();	 Catch:{ Exception -> 0x0046 }
        r24 = java.lang.Math.min(r5, r8);	 Catch:{ Exception -> 0x0046 }
        r5 = 1;
        r0 = new boolean[r5];	 Catch:{ Exception -> 0x0046 }
        r25 = r0;
        r5 = 0;
        r8 = r5;
    L_0x079e:
        r0 = r24;
        if (r8 >= r0) goto L_0x085c;
    L_0x07a2:
        r0 = r33;
        r5 = r0.pushMessages;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.get(r8);	 Catch:{ Exception -> 0x0046 }
        r5 = (org.telegram.messenger.MessageObject) r5;	 Catch:{ Exception -> 0x0046 }
        r9 = 0;
        r0 = r33;
        r1 = r25;
        r9 = r0.getStringForMessage(r5, r9, r1);	 Catch:{ Exception -> 0x0046 }
        if (r9 == 0) goto L_0x0930;
    L_0x07b7:
        r0 = r5.messageOwner;	 Catch:{ Exception -> 0x0046 }
        r26 = r0;
        r0 = r26;
        r0 = r0.date;	 Catch:{ Exception -> 0x0046 }
        r26 = r0;
        r0 = r26;
        r1 = r23;
        if (r0 > r1) goto L_0x07cf;
    L_0x07c7:
        r5 = r6;
        r6 = r7;
    L_0x07c9:
        r7 = r8 + 1;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        goto L_0x079e;
    L_0x07cf:
        r26 = 2;
        r0 = r26;
        if (r7 != r0) goto L_0x092c;
    L_0x07d5:
        r5 = r5.messageOwner;	 Catch:{ Exception -> 0x0046 }
        r5 = r5.silent;	 Catch:{ Exception -> 0x0046 }
        if (r5 == 0) goto L_0x0815;
    L_0x07db:
        r5 = 1;
    L_0x07dc:
        r6 = r5;
        r5 = r9;
    L_0x07de:
        r0 = r33;
        r7 = r0.pushDialogs;	 Catch:{ Exception -> 0x0046 }
        r7 = r7.size();	 Catch:{ Exception -> 0x0046 }
        r26 = 1;
        r0 = r26;
        if (r7 != r0) goto L_0x0934;
    L_0x07ec:
        if (r11 == 0) goto L_0x0934;
    L_0x07ee:
        if (r20 == 0) goto L_0x0817;
    L_0x07f0:
        r7 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r7.<init>();	 Catch:{ Exception -> 0x0046 }
        r26 = " @ ";
        r0 = r26;
        r7 = r7.append(r0);	 Catch:{ Exception -> 0x0046 }
        r7 = r7.append(r12);	 Catch:{ Exception -> 0x0046 }
        r7 = r7.toString();	 Catch:{ Exception -> 0x0046 }
        r26 = "";
        r0 = r26;
        r7 = r9.replace(r7, r0);	 Catch:{ Exception -> 0x0046 }
    L_0x080f:
        r0 = r22;
        r0.m1281c(r7);	 Catch:{ Exception -> 0x0046 }
        goto L_0x07c9;
    L_0x0815:
        r5 = 0;
        goto L_0x07dc;
    L_0x0817:
        r7 = 0;
        r7 = r25[r7];	 Catch:{ Exception -> 0x0046 }
        if (r7 == 0) goto L_0x083c;
    L_0x081c:
        r7 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r7.<init>();	 Catch:{ Exception -> 0x0046 }
        r7 = r7.append(r12);	 Catch:{ Exception -> 0x0046 }
        r26 = ": ";
        r0 = r26;
        r7 = r7.append(r0);	 Catch:{ Exception -> 0x0046 }
        r7 = r7.toString();	 Catch:{ Exception -> 0x0046 }
        r26 = "";
        r0 = r26;
        r7 = r9.replace(r7, r0);	 Catch:{ Exception -> 0x0046 }
        goto L_0x080f;
    L_0x083c:
        r7 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0046 }
        r7.<init>();	 Catch:{ Exception -> 0x0046 }
        r7 = r7.append(r12);	 Catch:{ Exception -> 0x0046 }
        r26 = " ";
        r0 = r26;
        r7 = r7.append(r0);	 Catch:{ Exception -> 0x0046 }
        r7 = r7.toString();	 Catch:{ Exception -> 0x0046 }
        r26 = "";
        r0 = r26;
        r7 = r9.replace(r7, r0);	 Catch:{ Exception -> 0x0046 }
        goto L_0x080f;
    L_0x085c:
        r0 = r22;
        r0.m1280b(r10);	 Catch:{ Exception -> 0x0046 }
        r21.m1237a(r22);	 Catch:{ Exception -> 0x0046 }
        r5 = r6;
        goto L_0x046c;
    L_0x0867:
        r4 = 1;
        r6 = org.telegram.messenger.FileLoader.getPathToAttach(r13, r4);	 Catch:{ Throwable -> 0x089b }
        r4 = r6.exists();	 Catch:{ Throwable -> 0x089b }
        if (r4 == 0) goto L_0x04a6;
    L_0x0872:
        r4 = 1126170624; // 0x43200000 float:160.0 double:5.564022167E-315;
        r8 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        r8 = org.telegram.messenger.AndroidUtilities.dp(r8);	 Catch:{ Throwable -> 0x089b }
        r8 = (float) r8;	 Catch:{ Throwable -> 0x089b }
        r4 = r4 / r8;
        r8 = new android.graphics.BitmapFactory$Options;	 Catch:{ Throwable -> 0x089b }
        r8.<init>();	 Catch:{ Throwable -> 0x089b }
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r9 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1));
        if (r9 >= 0) goto L_0x089e;
    L_0x0887:
        r4 = 1;
    L_0x0888:
        r8.inSampleSize = r4;	 Catch:{ Throwable -> 0x089b }
        r4 = r6.getAbsolutePath();	 Catch:{ Throwable -> 0x089b }
        r4 = android.graphics.BitmapFactory.decodeFile(r4, r8);	 Catch:{ Throwable -> 0x089b }
        if (r4 == 0) goto L_0x04a6;
    L_0x0894:
        r0 = r21;
        r0.m1233a(r4);	 Catch:{ Throwable -> 0x089b }
        goto L_0x04a6;
    L_0x089b:
        r4 = move-exception;
        goto L_0x04a6;
    L_0x089e:
        r4 = (int) r4;
        goto L_0x0888;
    L_0x08a0:
        if (r14 != 0) goto L_0x08aa;
    L_0x08a2:
        r4 = 0;
        r0 = r21;
        r0.m1253d(r4);	 Catch:{ Exception -> 0x0046 }
        goto L_0x04b1;
    L_0x08aa:
        r4 = 1;
        if (r14 != r4) goto L_0x08b5;
    L_0x08ad:
        r4 = 1;
        r0 = r21;
        r0.m1253d(r4);	 Catch:{ Exception -> 0x0046 }
        goto L_0x04b1;
    L_0x08b5:
        r4 = 2;
        if (r14 != r4) goto L_0x04b1;
    L_0x08b8:
        r4 = 2;
        r0 = r21;
        r0.m1253d(r4);	 Catch:{ Exception -> 0x0046 }
        goto L_0x04b1;
    L_0x08c0:
        r4 = android.net.Uri.parse(r17);	 Catch:{ Exception -> 0x0046 }
        r5 = 5;
        r0 = r21;
        r0.m1235a(r4, r5);	 Catch:{ Exception -> 0x0046 }
        goto L_0x0519;
    L_0x08cc:
        r4 = 1;
        r0 = r18;
        if (r0 != r4) goto L_0x08de;
    L_0x08d1:
        r4 = 4;
        r4 = new long[r4];	 Catch:{ Exception -> 0x0046 }
        r4 = {0, 100, 0, 100};	 Catch:{ Exception -> 0x0046 }
        r0 = r21;
        r0.m1241a(r4);	 Catch:{ Exception -> 0x0046 }
        goto L_0x0540;
    L_0x08de:
        if (r18 == 0) goto L_0x08e5;
    L_0x08e0:
        r4 = 4;
        r0 = r18;
        if (r0 != r4) goto L_0x08ed;
    L_0x08e5:
        r4 = 2;
        r0 = r21;
        r0.m1248c(r4);	 Catch:{ Exception -> 0x0046 }
        goto L_0x0540;
    L_0x08ed:
        r4 = 3;
        r0 = r18;
        if (r0 != r4) goto L_0x0540;
    L_0x08f2:
        r4 = 2;
        r4 = new long[r4];	 Catch:{ Exception -> 0x0046 }
        r4 = {0, 1000};	 Catch:{ Exception -> 0x0046 }
        r0 = r21;
        r0.m1241a(r4);	 Catch:{ Exception -> 0x0046 }
        goto L_0x0540;
    L_0x08ff:
        r4 = 2;
        r4 = new long[r4];	 Catch:{ Exception -> 0x0046 }
        r4 = {0, 0};	 Catch:{ Exception -> 0x0046 }
        r0 = r21;
        r0.m1241a(r4);	 Catch:{ Exception -> 0x0046 }
        goto L_0x0540;
    L_0x090c:
        r5 = 2130837876; // 0x7f020174 float:1.7280718E38 double:1.0527737914E-314;
        r6 = "Reply";
        r7 = 2131232228; // 0x7f0805e4 float:1.808056E38 double:1.052968627E-314;
        r6 = org.telegram.messenger.LocaleController.getString(r6, r7);	 Catch:{ Exception -> 0x0046 }
        r7 = org.telegram.messenger.ApplicationLoader.applicationContext;	 Catch:{ Exception -> 0x0046 }
        r8 = 2;
        r9 = 134217728; // 0x8000000 float:3.85186E-34 double:6.63123685E-316;
        r4 = android.app.PendingIntent.getBroadcast(r7, r8, r4, r9);	 Catch:{ Exception -> 0x0046 }
        r0 = r21;
        r0.m1230a(r5, r6, r4);	 Catch:{ Exception -> 0x0046 }
        goto L_0x057e;
    L_0x0929:
        r4 = r5;
        goto L_0x04eb;
    L_0x092c:
        r5 = r6;
        r6 = r7;
        goto L_0x07de;
    L_0x0930:
        r5 = r6;
        r6 = r7;
        goto L_0x07c9;
    L_0x0934:
        r7 = r9;
        goto L_0x080f;
    L_0x0937:
        r5 = r6;
        goto L_0x0458;
    L_0x093a:
        r5 = r6;
        goto L_0x02d6;
    L_0x093d:
        r8 = r13;
        goto L_0x02b8;
    L_0x0940:
        r11 = r13;
        goto L_0x0616;
    L_0x0943:
        r32 = r6;
        r6 = r11;
        r11 = r13;
        r13 = r32;
        goto L_0x0268;
    L_0x094b:
        r11 = r13;
        goto L_0x0246;
    L_0x094e:
        r14 = r6;
        r15 = r8;
        r16 = r10;
        r17 = r9;
        r18 = r11;
        goto L_0x02fe;
    L_0x0958:
        r19 = r12;
        goto L_0x0192;
    L_0x095c:
        r20 = r5;
        goto L_0x0099;
    L_0x0960:
        r21 = r5;
        goto L_0x007c;
    L_0x0964:
        r14 = r24;
        goto L_0x005b;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.showOrUpdateNotification(boolean):void");
    }

    public static void updateServerNotificationsSettings(long j) {
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.notificationsSettingsUpdated, new Object[0]);
        if (((int) j) != 0) {
            SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
            TLObject tL_account_updateNotifySettings = new TL_account_updateNotifySettings();
            tL_account_updateNotifySettings.settings = new TLRPC$TL_inputPeerNotifySettings();
            tL_account_updateNotifySettings.settings.sound = "default";
            int i = sharedPreferences.getInt("notify2_" + j, 0);
            if (i == 3) {
                tL_account_updateNotifySettings.settings.mute_until = sharedPreferences.getInt("notifyuntil_" + j, 0);
            } else {
                tL_account_updateNotifySettings.settings.mute_until = i != 2 ? 0 : Integer.MAX_VALUE;
            }
            tL_account_updateNotifySettings.settings.show_previews = sharedPreferences.getBoolean("preview_" + j, true);
            tL_account_updateNotifySettings.settings.silent = sharedPreferences.getBoolean("silent_" + j, false);
            tL_account_updateNotifySettings.peer = new TLRPC$TL_inputNotifyPeer();
            ((TLRPC$TL_inputNotifyPeer) tL_account_updateNotifySettings.peer).peer = MessagesController.getInputPeer((int) j);
            ConnectionsManager.getInstance().sendRequest(tL_account_updateNotifySettings, new RequestDelegate() {
                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                }
            });
        }
    }

    public void cleanup() {
        this.popupMessages.clear();
        this.popupReplyMessages.clear();
        this.notificationsQueue.postRunnable(new C33242());
    }

    protected void forceShowPopupForReply() {
        this.notificationsQueue.postRunnable(new C33296());
    }

    public boolean hasMessagesToReply() {
        for (int i = 0; i < this.pushMessages.size(); i++) {
            MessageObject messageObject = (MessageObject) this.pushMessages.get(i);
            long dialogId = messageObject.getDialogId();
            if ((!messageObject.messageOwner.mentioned || !(messageObject.messageOwner.action instanceof TLRPC$TL_messageActionPinMessage)) && ((int) dialogId) != 0 && (messageObject.messageOwner.to_id.channel_id == 0 || messageObject.isMegagroup())) {
                return true;
            }
        }
        return false;
    }

    public void playOutChatSound() {
        if (this.inChatSoundEnabled && !MediaController.getInstance().isRecordingAudio()) {
            try {
                if (this.audioManager.getRingerMode() == 0) {
                    return;
                }
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
            this.notificationsQueue.postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.NotificationsController$17$1 */
                class C33221 implements OnLoadCompleteListener {
                    C33221() {
                    }

                    public void onLoadComplete(SoundPool soundPool, int i, int i2) {
                        if (i2 == 0) {
                            try {
                                soundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
                            } catch (Throwable e) {
                                FileLog.m13728e(e);
                            }
                        }
                    }
                }

                public void run() {
                    try {
                        if (Math.abs(System.currentTimeMillis() - NotificationsController.this.lastSoundOutPlay) > 100) {
                            NotificationsController.this.lastSoundOutPlay = System.currentTimeMillis();
                            if (NotificationsController.this.soundPool == null) {
                                NotificationsController.this.soundPool = new SoundPool(3, 1, 0);
                                NotificationsController.this.soundPool.setOnLoadCompleteListener(new C33221());
                            }
                            if (NotificationsController.this.soundOut == 0 && !NotificationsController.this.soundOutLoaded) {
                                NotificationsController.this.soundOutLoaded = true;
                                NotificationsController.this.soundOut = NotificationsController.this.soundPool.load(ApplicationLoader.applicationContext, R.raw.sound_out, 1);
                            }
                            if (NotificationsController.this.soundOut != 0) {
                                try {
                                    NotificationsController.this.soundPool.play(NotificationsController.this.soundOut, 1.0f, 1.0f, 1, 0, 1.0f);
                                } catch (Throwable e) {
                                    FileLog.m13728e(e);
                                }
                            }
                        }
                    } catch (Throwable e2) {
                        FileLog.m13728e(e2);
                    }
                }
            });
        }
    }

    public void processDialogsUpdateRead(final HashMap<Long, Integer> hashMap) {
        final ArrayList arrayList = this.popupMessages.isEmpty() ? null : new ArrayList(this.popupMessages);
        this.notificationsQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.NotificationsController$11$1 */
            class C33191 implements Runnable {
                C33191() {
                }

                public void run() {
                    NotificationsController.this.popupMessages = arrayList;
                }
            }

            public void run() {
                int access$400 = NotificationsController.this.total_unread_count;
                SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                for (Entry entry : hashMap.entrySet()) {
                    Integer num;
                    boolean z;
                    Integer num2;
                    int i;
                    MessageObject messageObject;
                    long j;
                    long longValue = ((Long) entry.getKey()).longValue();
                    boolean access$2000 = NotificationsController.this.getNotifyOverride(sharedPreferences, longValue);
                    if (NotificationsController.this.notifyCheck) {
                        num = (Integer) NotificationsController.this.pushDialogsOverrideMention.get(Long.valueOf(longValue));
                        if (num != null && num.intValue() == 1) {
                            NotificationsController.this.pushDialogsOverrideMention.put(Long.valueOf(longValue), Integer.valueOf(0));
                            z = true;
                            access$2000 = z && ((sharedPreferences.getBoolean("EnableAll", true) && (((int) longValue) >= 0 || sharedPreferences.getBoolean("EnableGroup", true))) || z);
                            num = (Integer) NotificationsController.this.pushDialogs.get(Long.valueOf(longValue));
                            num2 = (Integer) entry.getValue();
                            if (num2.intValue() == 0) {
                                NotificationsController.this.smartNotificationsDialogs.remove(Long.valueOf(longValue));
                            }
                            if (num2.intValue() < 0) {
                                if (num != null) {
                                    num2 = Integer.valueOf(num2.intValue() + num.intValue());
                                }
                            }
                            if ((access$2000 || num2.intValue() == 0) && num != null) {
                                NotificationsController.this.total_unread_count = NotificationsController.this.total_unread_count - num.intValue();
                            }
                            if (num2.intValue() == 0) {
                                NotificationsController.this.pushDialogs.remove(Long.valueOf(longValue));
                                NotificationsController.this.pushDialogsOverrideMention.remove(Long.valueOf(longValue));
                                i = 0;
                                while (i < NotificationsController.this.pushMessages.size()) {
                                    messageObject = (MessageObject) NotificationsController.this.pushMessages.get(i);
                                    if (messageObject.getDialogId() != longValue) {
                                        if (NotificationsController.this.isPersonalMessage(messageObject)) {
                                            NotificationsController.this.personal_count = NotificationsController.this.personal_count - 1;
                                        }
                                        NotificationsController.this.pushMessages.remove(i);
                                        i--;
                                        NotificationsController.this.delayedPushMessages.remove(messageObject);
                                        j = (long) messageObject.messageOwner.id;
                                        if (messageObject.messageOwner.to_id.channel_id != 0) {
                                            j |= ((long) messageObject.messageOwner.to_id.channel_id) << 32;
                                        }
                                        NotificationsController.this.pushMessagesDict.remove(Long.valueOf(j));
                                        if (arrayList != null) {
                                            arrayList.remove(messageObject);
                                        }
                                    }
                                    i++;
                                }
                                if (!(arrayList == null || !NotificationsController.this.pushMessages.isEmpty() || arrayList.isEmpty())) {
                                    arrayList.clear();
                                }
                            } else if (access$2000) {
                                NotificationsController.this.total_unread_count = NotificationsController.this.total_unread_count + num2.intValue();
                                NotificationsController.this.pushDialogs.put(Long.valueOf(longValue), num2);
                            }
                        }
                    }
                    z = access$2000;
                    if (z) {
                    }
                    num = (Integer) NotificationsController.this.pushDialogs.get(Long.valueOf(longValue));
                    num2 = (Integer) entry.getValue();
                    if (num2.intValue() == 0) {
                        NotificationsController.this.smartNotificationsDialogs.remove(Long.valueOf(longValue));
                    }
                    if (num2.intValue() < 0) {
                        if (num != null) {
                            num2 = Integer.valueOf(num2.intValue() + num.intValue());
                        }
                    }
                    NotificationsController.this.total_unread_count = NotificationsController.this.total_unread_count - num.intValue();
                    if (num2.intValue() == 0) {
                        NotificationsController.this.pushDialogs.remove(Long.valueOf(longValue));
                        NotificationsController.this.pushDialogsOverrideMention.remove(Long.valueOf(longValue));
                        i = 0;
                        while (i < NotificationsController.this.pushMessages.size()) {
                            messageObject = (MessageObject) NotificationsController.this.pushMessages.get(i);
                            if (messageObject.getDialogId() != longValue) {
                                if (NotificationsController.this.isPersonalMessage(messageObject)) {
                                    NotificationsController.this.personal_count = NotificationsController.this.personal_count - 1;
                                }
                                NotificationsController.this.pushMessages.remove(i);
                                i--;
                                NotificationsController.this.delayedPushMessages.remove(messageObject);
                                j = (long) messageObject.messageOwner.id;
                                if (messageObject.messageOwner.to_id.channel_id != 0) {
                                    j |= ((long) messageObject.messageOwner.to_id.channel_id) << 32;
                                }
                                NotificationsController.this.pushMessagesDict.remove(Long.valueOf(j));
                                if (arrayList != null) {
                                    arrayList.remove(messageObject);
                                }
                            }
                            i++;
                        }
                        arrayList.clear();
                    } else if (access$2000) {
                        NotificationsController.this.total_unread_count = NotificationsController.this.total_unread_count + num2.intValue();
                        NotificationsController.this.pushDialogs.put(Long.valueOf(longValue), num2);
                    }
                }
                if (arrayList != null) {
                    AndroidUtilities.runOnUIThread(new C33191());
                }
                if (access$400 != NotificationsController.this.total_unread_count) {
                    if (NotificationsController.this.notifyCheck) {
                        NotificationsController.this.scheduleNotificationDelay(NotificationsController.this.lastOnlineFromOtherDevice > ConnectionsManager.getInstance().getCurrentTime());
                    } else {
                        NotificationsController.this.delayedPushMessages.clear();
                        NotificationsController.this.showOrUpdateNotification(NotificationsController.this.notifyCheck);
                    }
                }
                NotificationsController.this.notifyCheck = false;
                if (sharedPreferences.getBoolean("badgeNumber", true)) {
                    NotificationsController.this.setBadge(NotificationsController.this.total_unread_count);
                }
            }
        });
    }

    public void processLoadedUnreadMessages(final HashMap<Long, Integer> hashMap, final ArrayList<Message> arrayList, ArrayList<User> arrayList2, ArrayList<Chat> arrayList3, ArrayList<EncryptedChat> arrayList4) {
        MessagesController.getInstance().putUsers(arrayList2, true);
        MessagesController.getInstance().putChats(arrayList3, true);
        MessagesController.getInstance().putEncryptedChats(arrayList4, true);
        this.notificationsQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.NotificationsController$12$1 */
            class C33201 implements Runnable {
                C33201() {
                }

                public void run() {
                    NotificationsController.this.popupMessages.clear();
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.pushMessagesUpdated, new Object[0]);
                }
            }

            public void run() {
                int i;
                long j;
                NotificationsController.this.pushDialogs.clear();
                NotificationsController.this.pushMessages.clear();
                NotificationsController.this.pushMessagesDict.clear();
                NotificationsController.this.total_unread_count = 0;
                NotificationsController.this.personal_count = 0;
                SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                HashMap hashMap = new HashMap();
                if (arrayList != null) {
                    for (i = 0; i < arrayList.size(); i++) {
                        Message message = (Message) arrayList.get(i);
                        long j2 = (long) message.id;
                        if (message.to_id.channel_id != 0) {
                            j2 |= ((long) message.to_id.channel_id) << 32;
                        }
                        if (!NotificationsController.this.pushMessagesDict.containsKey(Long.valueOf(j2))) {
                            MessageObject messageObject = new MessageObject(message, null, false);
                            if (NotificationsController.this.isPersonalMessage(messageObject)) {
                                NotificationsController.this.personal_count = NotificationsController.this.personal_count + 1;
                            }
                            long dialogId = messageObject.getDialogId();
                            j = messageObject.messageOwner.mentioned ? (long) messageObject.messageOwner.from_id : dialogId;
                            Boolean bool = (Boolean) hashMap.get(Long.valueOf(j));
                            if (bool == null) {
                                int access$2000 = NotificationsController.this.getNotifyOverride(sharedPreferences, j);
                                boolean z = access$2000 != 2 && ((sharedPreferences.getBoolean("EnableAll", true) && (((int) j) >= 0 || sharedPreferences.getBoolean("EnableGroup", true))) || access$2000 != 0);
                                bool = Boolean.valueOf(z);
                                hashMap.put(Long.valueOf(j), bool);
                            }
                            if (bool.booleanValue() && !(j == NotificationsController.this.opened_dialog_id && ApplicationLoader.isScreenOn)) {
                                NotificationsController.this.pushMessagesDict.put(Long.valueOf(j2), messageObject);
                                NotificationsController.this.pushMessages.add(0, messageObject);
                                if (dialogId != j) {
                                    NotificationsController.this.pushDialogsOverrideMention.put(Long.valueOf(dialogId), Integer.valueOf(1));
                                }
                            }
                        }
                    }
                }
                for (Entry entry : hashMap.entrySet()) {
                    j = ((Long) entry.getKey()).longValue();
                    Boolean bool2 = (Boolean) hashMap.get(Long.valueOf(j));
                    if (bool2 == null) {
                        int access$20002 = NotificationsController.this.getNotifyOverride(sharedPreferences, j);
                        Integer num = (Integer) NotificationsController.this.pushDialogsOverrideMention.get(Long.valueOf(j));
                        if (num == null || num.intValue() != 1) {
                            i = access$20002;
                        } else {
                            NotificationsController.this.pushDialogsOverrideMention.put(Long.valueOf(j), Integer.valueOf(0));
                            i = 1;
                        }
                        boolean z2 = i != 2 && ((sharedPreferences.getBoolean("EnableAll", true) && (((int) j) >= 0 || sharedPreferences.getBoolean("EnableGroup", true))) || i != 0);
                        bool2 = Boolean.valueOf(z2);
                        hashMap.put(Long.valueOf(j), bool2);
                    }
                    if (bool2.booleanValue()) {
                        access$2000 = ((Integer) entry.getValue()).intValue();
                        NotificationsController.this.pushDialogs.put(Long.valueOf(j), Integer.valueOf(access$2000));
                        NotificationsController.this.total_unread_count = access$2000 + NotificationsController.this.total_unread_count;
                    }
                }
                if (NotificationsController.this.total_unread_count == 0) {
                    AndroidUtilities.runOnUIThread(new C33201());
                }
                NotificationsController.this.showOrUpdateNotification(SystemClock.uptimeMillis() / 1000 < 60);
                if (sharedPreferences.getBoolean("badgeNumber", true)) {
                    NotificationsController.this.setBadge(NotificationsController.this.total_unread_count);
                }
            }
        });
    }

    public void processNewMessages(final ArrayList<MessageObject> arrayList, final boolean z) {
        if (!arrayList.isEmpty()) {
            final ArrayList arrayList2 = new ArrayList(this.popupMessages);
            this.notificationsQueue.postRunnable(new Runnable() {
                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                    r23 = this;
                    r6 = 0;
                    r0 = r23;
                    r4 = r0;
                    r16 = r4.size();
                    r17 = new java.util.HashMap;
                    r17.<init>();
                    r4 = org.telegram.messenger.ApplicationLoader.applicationContext;
                    r5 = "Notifications";
                    r7 = 0;
                    r18 = r4.getSharedPreferences(r5, r7);
                    r4 = "PinnedMessages";
                    r5 = 1;
                    r0 = r18;
                    r19 = r0.getBoolean(r4, r5);
                    r5 = 0;
                    r4 = 0;
                    r7 = r5;
                    r5 = r6;
                    r6 = r4;
                L_0x0027:
                    r0 = r23;
                    r4 = r4;
                    r4 = r4.size();
                    if (r6 >= r4) goto L_0x01f0;
                L_0x0031:
                    r0 = r23;
                    r4 = r4;
                    r4 = r4.get(r6);
                    r4 = (org.telegram.messenger.MessageObject) r4;
                    r8 = r4.messageOwner;
                    r8 = r8.id;
                    r8 = (long) r8;
                    r10 = r4.messageOwner;
                    r10 = r10.to_id;
                    r10 = r10.channel_id;
                    if (r10 == 0) goto L_0x0053;
                L_0x0048:
                    r10 = r4.messageOwner;
                    r10 = r10.to_id;
                    r10 = r10.channel_id;
                    r10 = (long) r10;
                    r12 = 32;
                    r10 = r10 << r12;
                    r8 = r8 | r10;
                L_0x0053:
                    r0 = r23;
                    r10 = org.telegram.messenger.NotificationsController.this;
                    r10 = r10.pushMessagesDict;
                    r11 = java.lang.Long.valueOf(r8);
                    r10 = r10.containsKey(r11);
                    if (r10 == 0) goto L_0x0073;
                L_0x0065:
                    r22 = r7;
                    r7 = r5;
                    r5 = r22;
                L_0x006a:
                    r4 = r6 + 1;
                    r6 = r4;
                    r22 = r5;
                    r5 = r7;
                    r7 = r22;
                    goto L_0x0027;
                L_0x0073:
                    r12 = r4.getDialogId();
                    r0 = r23;
                    r10 = org.telegram.messenger.NotificationsController.this;
                    r10 = r10.opened_dialog_id;
                    r10 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1));
                    if (r10 != 0) goto L_0x0094;
                L_0x0083:
                    r10 = org.telegram.messenger.ApplicationLoader.isScreenOn;
                    if (r10 == 0) goto L_0x0094;
                L_0x0087:
                    r0 = r23;
                    r4 = org.telegram.messenger.NotificationsController.this;
                    r4.playInChatSound();
                    r22 = r7;
                    r7 = r5;
                    r5 = r22;
                    goto L_0x006a;
                L_0x0094:
                    r10 = r4.messageOwner;
                    r10 = r10.mentioned;
                    if (r10 == 0) goto L_0x0228;
                L_0x009a:
                    if (r19 != 0) goto L_0x00aa;
                L_0x009c:
                    r10 = r4.messageOwner;
                    r10 = r10.action;
                    r10 = r10 instanceof org.telegram.tgnet.TLRPC$TL_messageActionPinMessage;
                    if (r10 == 0) goto L_0x00aa;
                L_0x00a4:
                    r22 = r7;
                    r7 = r5;
                    r5 = r22;
                    goto L_0x006a;
                L_0x00aa:
                    r5 = r4.messageOwner;
                    r5 = r5.from_id;
                    r10 = (long) r5;
                L_0x00af:
                    r0 = r23;
                    r5 = org.telegram.messenger.NotificationsController.this;
                    r5 = r5.isPersonalMessage(r4);
                    if (r5 == 0) goto L_0x00c0;
                L_0x00b9:
                    r0 = r23;
                    r5 = org.telegram.messenger.NotificationsController.this;
                    r5.personal_count = r5.personal_count + 1;
                L_0x00c0:
                    r14 = 1;
                    r5 = java.lang.Long.valueOf(r10);
                    r0 = r17;
                    r5 = r0.get(r5);
                    r5 = (java.lang.Boolean) r5;
                    r0 = (int) r10;
                    r20 = r0;
                    if (r20 >= 0) goto L_0x01d0;
                L_0x00d2:
                    r15 = 1;
                L_0x00d3:
                    if (r20 == 0) goto L_0x0129;
                L_0x00d5:
                    r7 = new java.lang.StringBuilder;
                    r7.<init>();
                    r20 = "custom_";
                    r0 = r20;
                    r7 = r7.append(r0);
                    r7 = r7.append(r10);
                    r7 = r7.toString();
                    r20 = 0;
                    r0 = r18;
                    r1 = r20;
                    r7 = r0.getBoolean(r7, r1);
                    if (r7 == 0) goto L_0x01d3;
                L_0x00f7:
                    r7 = new java.lang.StringBuilder;
                    r7.<init>();
                    r20 = "popup_";
                    r0 = r20;
                    r7 = r7.append(r0);
                    r7 = r7.append(r10);
                    r7 = r7.toString();
                    r20 = 0;
                    r0 = r18;
                    r1 = r20;
                    r7 = r0.getInt(r7, r1);
                L_0x0117:
                    if (r7 != 0) goto L_0x01db;
                L_0x0119:
                    r7 = (int) r10;
                    if (r7 >= 0) goto L_0x01d6;
                L_0x011c:
                    r7 = "popupGroup";
                L_0x011f:
                    r20 = 0;
                    r0 = r18;
                    r1 = r20;
                    r7 = r0.getInt(r7, r1);
                L_0x0129:
                    if (r5 != 0) goto L_0x016d;
                L_0x012b:
                    r0 = r23;
                    r5 = org.telegram.messenger.NotificationsController.this;
                    r0 = r18;
                    r5 = r5.getNotifyOverride(r0, r10);
                    r20 = 2;
                    r0 = r20;
                    if (r5 == r0) goto L_0x01ed;
                L_0x013b:
                    r20 = "EnableAll";
                    r21 = 1;
                    r0 = r18;
                    r1 = r20;
                    r2 = r21;
                    r20 = r0.getBoolean(r1, r2);
                    if (r20 == 0) goto L_0x015d;
                L_0x014c:
                    if (r15 == 0) goto L_0x015f;
                L_0x014e:
                    r15 = "EnableGroup";
                    r20 = 1;
                    r0 = r18;
                    r1 = r20;
                    r15 = r0.getBoolean(r15, r1);
                    if (r15 != 0) goto L_0x015f;
                L_0x015d:
                    if (r5 == 0) goto L_0x01ed;
                L_0x015f:
                    r5 = 1;
                L_0x0160:
                    r5 = java.lang.Boolean.valueOf(r5);
                    r15 = java.lang.Long.valueOf(r10);
                    r0 = r17;
                    r0.put(r15, r5);
                L_0x016d:
                    r15 = r5;
                    if (r7 == 0) goto L_0x0225;
                L_0x0170:
                    r5 = r4.messageOwner;
                    r5 = r5.to_id;
                    r5 = r5.channel_id;
                    if (r5 == 0) goto L_0x0225;
                L_0x0178:
                    r5 = r4.isMegagroup();
                    if (r5 != 0) goto L_0x0225;
                L_0x017e:
                    r5 = 0;
                L_0x017f:
                    r7 = r15.booleanValue();
                    if (r7 == 0) goto L_0x01cd;
                L_0x0185:
                    if (r5 == 0) goto L_0x018f;
                L_0x0187:
                    r0 = r23;
                    r7 = r0;
                    r15 = 0;
                    r7.add(r15, r4);
                L_0x018f:
                    r0 = r23;
                    r7 = org.telegram.messenger.NotificationsController.this;
                    r7 = r7.delayedPushMessages;
                    r7.add(r4);
                    r0 = r23;
                    r7 = org.telegram.messenger.NotificationsController.this;
                    r7 = r7.pushMessages;
                    r15 = 0;
                    r7.add(r15, r4);
                    r0 = r23;
                    r7 = org.telegram.messenger.NotificationsController.this;
                    r7 = r7.pushMessagesDict;
                    r8 = java.lang.Long.valueOf(r8);
                    r7.put(r8, r4);
                    r4 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1));
                    if (r4 == 0) goto L_0x01cd;
                L_0x01b9:
                    r0 = r23;
                    r4 = org.telegram.messenger.NotificationsController.this;
                    r4 = r4.pushDialogsOverrideMention;
                    r7 = java.lang.Long.valueOf(r12);
                    r8 = 1;
                    r8 = java.lang.Integer.valueOf(r8);
                    r4.put(r7, r8);
                L_0x01cd:
                    r7 = r14;
                    goto L_0x006a;
                L_0x01d0:
                    r15 = 0;
                    goto L_0x00d3;
                L_0x01d3:
                    r7 = 0;
                    goto L_0x0117;
                L_0x01d6:
                    r7 = "popupAll";
                    goto L_0x011f;
                L_0x01db:
                    r20 = 1;
                    r0 = r20;
                    if (r7 != r0) goto L_0x01e4;
                L_0x01e1:
                    r7 = 3;
                    goto L_0x0129;
                L_0x01e4:
                    r20 = 2;
                    r0 = r20;
                    if (r7 != r0) goto L_0x0129;
                L_0x01ea:
                    r7 = 0;
                    goto L_0x0129;
                L_0x01ed:
                    r5 = 0;
                    goto L_0x0160;
                L_0x01f0:
                    if (r5 == 0) goto L_0x01fd;
                L_0x01f2:
                    r0 = r23;
                    r4 = org.telegram.messenger.NotificationsController.this;
                    r0 = r23;
                    r5 = r5;
                    r4.notifyCheck = r5;
                L_0x01fd:
                    r0 = r23;
                    r4 = r0;
                    r4 = r4.isEmpty();
                    if (r4 != 0) goto L_0x0224;
                L_0x0207:
                    r0 = r23;
                    r4 = r0;
                    r4 = r4.size();
                    r0 = r16;
                    if (r0 == r4) goto L_0x0224;
                L_0x0213:
                    r4 = 0;
                    r4 = org.telegram.messenger.AndroidUtilities.needShowPasscode(r4);
                    if (r4 != 0) goto L_0x0224;
                L_0x021a:
                    r4 = new org.telegram.messenger.NotificationsController$10$1;
                    r0 = r23;
                    r4.<init>(r7);
                    org.telegram.messenger.AndroidUtilities.runOnUIThread(r4);
                L_0x0224:
                    return;
                L_0x0225:
                    r5 = r7;
                    goto L_0x017f;
                L_0x0228:
                    r10 = r12;
                    goto L_0x00af;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.10.run():void");
                }
            });
        }
    }

    public void processReadMessages(SparseArray<Long> sparseArray, long j, int i, int i2, boolean z) {
        final ArrayList arrayList = this.popupMessages.isEmpty() ? null : new ArrayList(this.popupMessages);
        final SparseArray<Long> sparseArray2 = sparseArray;
        final long j2 = j;
        final int i3 = i2;
        final int i4 = i;
        final boolean z2 = z;
        this.notificationsQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.NotificationsController$9$1 */
            class C33341 implements Runnable {
                C33341() {
                }

                public void run() {
                    NotificationsController.this.popupMessages = arrayList;
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.pushMessagesUpdated, new Object[0]);
                }
            }

            public void run() {
                int i;
                int i2;
                MessageObject messageObject;
                int size = arrayList != null ? arrayList.size() : 0;
                if (sparseArray2 != null) {
                    for (i = 0; i < sparseArray2.size(); i++) {
                        int keyAt = sparseArray2.keyAt(i);
                        long longValue = ((Long) sparseArray2.get(keyAt)).longValue();
                        i2 = 0;
                        while (i2 < NotificationsController.this.pushMessages.size()) {
                            messageObject = (MessageObject) NotificationsController.this.pushMessages.get(i2);
                            if (messageObject.getDialogId() == ((long) keyAt) && messageObject.getId() <= ((int) longValue)) {
                                if (NotificationsController.this.isPersonalMessage(messageObject)) {
                                    NotificationsController.this.personal_count = NotificationsController.this.personal_count - 1;
                                }
                                if (arrayList != null) {
                                    arrayList.remove(messageObject);
                                }
                                long j = (long) messageObject.messageOwner.id;
                                if (messageObject.messageOwner.to_id.channel_id != 0) {
                                    j |= ((long) messageObject.messageOwner.to_id.channel_id) << 32;
                                }
                                NotificationsController.this.pushMessagesDict.remove(Long.valueOf(j));
                                NotificationsController.this.delayedPushMessages.remove(messageObject);
                                NotificationsController.this.pushMessages.remove(i2);
                                i2--;
                            }
                            i2++;
                        }
                    }
                    if (!(arrayList == null || !NotificationsController.this.pushMessages.isEmpty() || arrayList.isEmpty())) {
                        arrayList.clear();
                    }
                }
                if (!(j2 == 0 || (i3 == 0 && i4 == 0))) {
                    i = 0;
                    while (i < NotificationsController.this.pushMessages.size()) {
                        messageObject = (MessageObject) NotificationsController.this.pushMessages.get(i);
                        if (messageObject.getDialogId() == j2) {
                            Object obj;
                            if (i4 != 0) {
                                if (messageObject.messageOwner.date <= i4) {
                                    obj = 1;
                                }
                                obj = null;
                            } else if (z2) {
                                if (messageObject.getId() == i3 || i3 < 0) {
                                    i2 = 1;
                                }
                                obj = null;
                            } else {
                                if (messageObject.getId() <= i3 || i3 < 0) {
                                    i2 = 1;
                                }
                                obj = null;
                            }
                            if (obj != null) {
                                if (NotificationsController.this.isPersonalMessage(messageObject)) {
                                    NotificationsController.this.personal_count = NotificationsController.this.personal_count - 1;
                                }
                                NotificationsController.this.pushMessages.remove(i);
                                NotificationsController.this.delayedPushMessages.remove(messageObject);
                                if (arrayList != null) {
                                    arrayList.remove(messageObject);
                                }
                                long j2 = (long) messageObject.messageOwner.id;
                                if (messageObject.messageOwner.to_id.channel_id != 0) {
                                    j2 |= ((long) messageObject.messageOwner.to_id.channel_id) << 32;
                                }
                                NotificationsController.this.pushMessagesDict.remove(Long.valueOf(j2));
                                i--;
                            }
                        }
                        i++;
                    }
                    if (!(arrayList == null || !NotificationsController.this.pushMessages.isEmpty() || arrayList.isEmpty())) {
                        arrayList.clear();
                    }
                }
                if (arrayList != null && size != arrayList.size()) {
                    AndroidUtilities.runOnUIThread(new C33341());
                }
            }
        });
    }

    public void removeDeletedHisoryFromNotifications(final SparseArray<Integer> sparseArray) {
        final ArrayList arrayList = this.popupMessages.isEmpty() ? null : new ArrayList(this.popupMessages);
        this.notificationsQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.NotificationsController$8$1 */
            class C33321 implements Runnable {
                C33321() {
                }

                public void run() {
                    NotificationsController.this.popupMessages = arrayList;
                }
            }

            public void run() {
                int access$400 = NotificationsController.this.total_unread_count;
                SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                for (int i = 0; i < sparseArray.size(); i++) {
                    int keyAt = sparseArray.keyAt(i);
                    long j = (long) (-keyAt);
                    Integer num = (Integer) sparseArray.get(keyAt);
                    Integer num2 = (Integer) NotificationsController.this.pushDialogs.get(Long.valueOf(j));
                    Integer valueOf = num2 == null ? Integer.valueOf(0) : num2;
                    int i2 = 0;
                    Integer num3 = valueOf;
                    while (i2 < NotificationsController.this.pushMessages.size()) {
                        int i3;
                        Integer num4;
                        MessageObject messageObject = (MessageObject) NotificationsController.this.pushMessages.get(i2);
                        if (messageObject.getDialogId() != j || messageObject.getId() > num.intValue()) {
                            i3 = i2;
                            num4 = num3;
                        } else {
                            NotificationsController.this.pushMessagesDict.remove(Long.valueOf(messageObject.getIdWithChannel()));
                            NotificationsController.this.delayedPushMessages.remove(messageObject);
                            NotificationsController.this.pushMessages.remove(messageObject);
                            i2--;
                            if (NotificationsController.this.isPersonalMessage(messageObject)) {
                                NotificationsController.this.personal_count = NotificationsController.this.personal_count - 1;
                            }
                            if (arrayList != null) {
                                arrayList.remove(messageObject);
                            }
                            i3 = i2;
                            num4 = Integer.valueOf(num3.intValue() - 1);
                        }
                        num3 = num4;
                        i2 = i3 + 1;
                    }
                    if (num3.intValue() <= 0) {
                        num3 = Integer.valueOf(0);
                        NotificationsController.this.smartNotificationsDialogs.remove(Long.valueOf(j));
                    }
                    if (!num3.equals(valueOf)) {
                        NotificationsController.this.total_unread_count = NotificationsController.this.total_unread_count - valueOf.intValue();
                        NotificationsController.this.total_unread_count = NotificationsController.this.total_unread_count + num3.intValue();
                        NotificationsController.this.pushDialogs.put(Long.valueOf(j), num3);
                    }
                    if (num3.intValue() == 0) {
                        NotificationsController.this.pushDialogs.remove(Long.valueOf(j));
                        NotificationsController.this.pushDialogsOverrideMention.remove(Long.valueOf(j));
                        if (!(arrayList == null || !NotificationsController.this.pushMessages.isEmpty() || arrayList.isEmpty())) {
                            arrayList.clear();
                        }
                    }
                }
                if (arrayList != null) {
                    AndroidUtilities.runOnUIThread(new C33321());
                }
                if (access$400 != NotificationsController.this.total_unread_count) {
                    if (NotificationsController.this.notifyCheck) {
                        NotificationsController.this.scheduleNotificationDelay(NotificationsController.this.lastOnlineFromOtherDevice > ConnectionsManager.getInstance().getCurrentTime());
                    } else {
                        NotificationsController.this.delayedPushMessages.clear();
                        NotificationsController.this.showOrUpdateNotification(NotificationsController.this.notifyCheck);
                    }
                }
                NotificationsController.this.notifyCheck = false;
                if (sharedPreferences.getBoolean("badgeNumber", true)) {
                    NotificationsController.this.setBadge(NotificationsController.this.total_unread_count);
                }
            }
        });
    }

    public void removeDeletedMessagesFromNotifications(final SparseArray<ArrayList<Integer>> sparseArray) {
        final ArrayList arrayList = this.popupMessages.isEmpty() ? null : new ArrayList(this.popupMessages);
        this.notificationsQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.NotificationsController$7$1 */
            class C33301 implements Runnable {
                C33301() {
                }

                public void run() {
                    NotificationsController.this.popupMessages = arrayList;
                }
            }

            public void run() {
                int access$400 = NotificationsController.this.total_unread_count;
                SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                for (int i = 0; i < sparseArray.size(); i++) {
                    int keyAt = sparseArray.keyAt(i);
                    long j = (long) (-keyAt);
                    ArrayList arrayList = (ArrayList) sparseArray.get(keyAt);
                    Integer num = (Integer) NotificationsController.this.pushDialogs.get(Long.valueOf(j));
                    Integer valueOf = num == null ? Integer.valueOf(0) : num;
                    Integer num2 = valueOf;
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        long intValue = ((long) ((Integer) arrayList.get(i2)).intValue()) | (((long) keyAt) << 32);
                        MessageObject messageObject = (MessageObject) NotificationsController.this.pushMessagesDict.get(Long.valueOf(intValue));
                        if (messageObject != null) {
                            NotificationsController.this.pushMessagesDict.remove(Long.valueOf(intValue));
                            NotificationsController.this.delayedPushMessages.remove(messageObject);
                            NotificationsController.this.pushMessages.remove(messageObject);
                            if (NotificationsController.this.isPersonalMessage(messageObject)) {
                                NotificationsController.this.personal_count = NotificationsController.this.personal_count - 1;
                            }
                            if (arrayList != null) {
                                arrayList.remove(messageObject);
                            }
                            num2 = Integer.valueOf(num2.intValue() - 1);
                        }
                    }
                    if (num2.intValue() <= 0) {
                        num2 = Integer.valueOf(0);
                        NotificationsController.this.smartNotificationsDialogs.remove(Long.valueOf(j));
                    }
                    if (!num2.equals(valueOf)) {
                        NotificationsController.this.total_unread_count = NotificationsController.this.total_unread_count - valueOf.intValue();
                        NotificationsController.this.total_unread_count = NotificationsController.this.total_unread_count + num2.intValue();
                        NotificationsController.this.pushDialogs.put(Long.valueOf(j), num2);
                    }
                    if (num2.intValue() == 0) {
                        NotificationsController.this.pushDialogs.remove(Long.valueOf(j));
                        NotificationsController.this.pushDialogsOverrideMention.remove(Long.valueOf(j));
                        if (!(arrayList == null || !NotificationsController.this.pushMessages.isEmpty() || arrayList.isEmpty())) {
                            arrayList.clear();
                        }
                    }
                }
                if (arrayList != null) {
                    AndroidUtilities.runOnUIThread(new C33301());
                }
                if (access$400 != NotificationsController.this.total_unread_count) {
                    if (NotificationsController.this.notifyCheck) {
                        NotificationsController.this.scheduleNotificationDelay(NotificationsController.this.lastOnlineFromOtherDevice > ConnectionsManager.getInstance().getCurrentTime());
                    } else {
                        NotificationsController.this.delayedPushMessages.clear();
                        NotificationsController.this.showOrUpdateNotification(NotificationsController.this.notifyCheck);
                    }
                }
                NotificationsController.this.notifyCheck = false;
                if (sharedPreferences.getBoolean("badgeNumber", true)) {
                    NotificationsController.this.setBadge(NotificationsController.this.total_unread_count);
                }
            }
        });
    }

    public void removeNotificationsForDialog(long j) {
        getInstance().processReadMessages(null, j, 0, Integer.MAX_VALUE, false);
        HashMap hashMap = new HashMap();
        hashMap.put(Long.valueOf(j), Integer.valueOf(0));
        getInstance().processDialogsUpdateRead(hashMap);
    }

    protected void repeatNotificationMaybe() {
        this.notificationsQueue.postRunnable(new Runnable() {
            public void run() {
                int i = Calendar.getInstance().get(11);
                if (i < 11 || i > 22) {
                    NotificationsController.this.scheduleNotificationRepeat();
                    return;
                }
                NotificationsController.this.notificationManager.m1382a(1);
                NotificationsController.this.showOrUpdateNotification(true);
            }
        });
    }

    public void setBadgeEnabled(boolean z) {
        setBadge(z ? this.total_unread_count : 0);
    }

    public void setInChatSoundEnabled(boolean z) {
        this.inChatSoundEnabled = z;
    }

    public void setLastOnlineFromOtherDevice(final int i) {
        this.notificationsQueue.postRunnable(new Runnable() {
            public void run() {
                FileLog.m13726e("set last online from other device = " + i);
                NotificationsController.this.lastOnlineFromOtherDevice = i;
            }
        });
    }

    public void setOpenedDialogId(final long j) {
        this.notificationsQueue.postRunnable(new Runnable() {
            public void run() {
                NotificationsController.this.opened_dialog_id = j;
            }
        });
    }

    protected void showSingleBackgroundNotification() {
        this.notificationsQueue.postRunnable(new C33275());
    }
}
