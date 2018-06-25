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
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationCompat.CarExtender;
import android.support.v4.app.NotificationCompat.CarExtender.UnreadConversation;
import android.support.v4.app.NotificationCompat.InboxStyle;
import android.support.v4.app.NotificationCompat.MessagingStyle;
import android.support.v4.app.NotificationCompat.Style;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.text.TextUtils;
import android.util.SparseArray;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import org.aspectj.lang.JoinPoint;
import org.ir.talaeii.R;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$PhoneCallDiscardReason;
import org.telegram.tgnet.TLRPC$TL_account_updateNotifySettings;
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
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.LaunchActivity;
import org.telegram.ui.PopupNotificationActivity;
import utils.app.AppPreferences;

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
    private NotificationManagerCompat notificationManager;
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
    class C15861 implements Runnable {
        C15861() {
        }

        public void run() {
            FileLog.e("delay reached");
            if (!NotificationsController.this.delayedPushMessages.isEmpty()) {
                NotificationsController.this.showOrUpdateNotification(true);
                NotificationsController.this.delayedPushMessages.clear();
            }
            try {
                if (NotificationsController.this.notificationDelayWakelock.isHeld()) {
                    NotificationsController.this.notificationDelayWakelock.release();
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    /* renamed from: org.telegram.messenger.NotificationsController$2 */
    class C15872 implements Runnable {
        C15872() {
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
            } catch (Exception e) {
                FileLog.e(e);
            }
            NotificationsController.this.setBadge(0);
            Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            editor.clear();
            editor.commit();
        }
    }

    /* renamed from: org.telegram.messenger.NotificationsController$5 */
    class C15905 implements Runnable {
        C15905() {
        }

        public void run() {
            try {
                if (ApplicationLoader.mainInterfacePaused) {
                    SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                    boolean notifyDisabled = false;
                    int needVibrate = 0;
                    String choosenSoundPath = null;
                    int ledColor = -16776961;
                    int priority = 0;
                    if (!preferences.getBoolean("EnableAll", true)) {
                        notifyDisabled = true;
                    }
                    String defaultPath = System.DEFAULT_NOTIFICATION_URI.getPath();
                    if (!notifyDisabled) {
                        choosenSoundPath = null;
                        boolean vibrateOnlyIfSilent = false;
                        if (choosenSoundPath != null && choosenSoundPath.equals(defaultPath)) {
                            choosenSoundPath = null;
                        } else if (choosenSoundPath == null) {
                            choosenSoundPath = preferences.getString("GlobalSoundPath", defaultPath);
                        }
                        needVibrate = preferences.getInt("vibrate_messages", 0);
                        priority = preferences.getInt("priority_group", 1);
                        ledColor = preferences.getInt("MessagesLed", -16776961);
                        if (needVibrate == 4) {
                            vibrateOnlyIfSilent = true;
                            needVibrate = 0;
                        }
                        if ((needVibrate == 2 && (0 == 1 || 0 == 3)) || ((needVibrate != 2 && 0 == 2) || !(null == null || 0 == 4))) {
                            needVibrate = 0;
                        }
                        if (vibrateOnlyIfSilent && needVibrate != 2) {
                            try {
                                int mode = NotificationsController.this.audioManager.getRingerMode();
                                if (!(mode == 0 || mode == 1)) {
                                    needVibrate = 2;
                                }
                            } catch (Exception e) {
                                FileLog.e(e);
                            }
                        }
                    }
                    Intent intent = new Intent(ApplicationLoader.applicationContext, LaunchActivity.class);
                    intent.setAction("com.tmessages.openchat" + Math.random() + Integer.MAX_VALUE);
                    intent.setFlags(32768);
                    Builder mBuilder = new Builder(ApplicationLoader.applicationContext).setContentTitle(LocaleController.getString("AppName", R.string.AppName)).setSmallIcon(R.drawable.notification).setAutoCancel(true).setNumber(NotificationsController.this.total_unread_count).setContentIntent(PendingIntent.getActivity(ApplicationLoader.applicationContext, 0, intent, 1073741824)).setGroup("messages").setGroupSummary(true).setColor(-13851168);
                    mBuilder.setCategory("msg");
                    String lastMessage = LocaleController.getString("YouHaveNewMessage", R.string.YouHaveNewMessage);
                    mBuilder.setContentText(lastMessage);
                    mBuilder.setStyle(new BigTextStyle().bigText(lastMessage));
                    if (priority == 0) {
                        mBuilder.setPriority(0);
                    } else if (priority == 1) {
                        mBuilder.setPriority(1);
                    } else if (priority == 2) {
                        mBuilder.setPriority(2);
                    }
                    long[] jArr;
                    if (notifyDisabled) {
                        jArr = new long[2];
                        mBuilder.setVibrate(new long[]{0, 0});
                    } else {
                        if (lastMessage.length() > 100) {
                            lastMessage = lastMessage.substring(0, 100).replace('\n', ' ').trim() + "...";
                        }
                        mBuilder.setTicker(lastMessage);
                        if (!(choosenSoundPath == null || choosenSoundPath.equals("NoSound"))) {
                            if (choosenSoundPath.equals(defaultPath)) {
                                mBuilder.setSound(System.DEFAULT_NOTIFICATION_URI, 5);
                            } else {
                                mBuilder.setSound(Uri.parse(choosenSoundPath), 5);
                            }
                        }
                        if (ledColor != 0) {
                            mBuilder.setLights(ledColor, 1000, 1000);
                        }
                        if (needVibrate == 2 || MediaController.getInstance().isRecordingAudio()) {
                            jArr = new long[2];
                            mBuilder.setVibrate(new long[]{0, 0});
                        } else if (needVibrate == 1) {
                            jArr = new long[4];
                            mBuilder.setVibrate(new long[]{0, 100, 0, 100});
                        } else if (needVibrate == 0 || needVibrate == 4) {
                            mBuilder.setDefaults(2);
                        } else if (needVibrate == 3) {
                            jArr = new long[2];
                            mBuilder.setVibrate(new long[]{0, 1000});
                        }
                    }
                    NotificationsController.this.notificationManager.notify(1, mBuilder.build());
                }
            } catch (Exception e2) {
                FileLog.e(e2);
            }
        }
    }

    /* renamed from: org.telegram.messenger.NotificationsController$6 */
    class C15926 implements Runnable {
        C15926() {
        }

        public void run() {
            final ArrayList<MessageObject> popupArray = new ArrayList();
            for (int a = 0; a < NotificationsController.this.pushMessages.size(); a++) {
                MessageObject messageObject = (MessageObject) NotificationsController.this.pushMessages.get(a);
                long dialog_id = messageObject.getDialogId();
                if (!((messageObject.messageOwner.mentioned && (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionPinMessage)) || ((int) dialog_id) == 0 || (messageObject.messageOwner.to_id.channel_id != 0 && !messageObject.isMegagroup()))) {
                    popupArray.add(0, messageObject);
                }
            }
            if (!popupArray.isEmpty() && !AndroidUtilities.needShowPasscode(false)) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        NotificationsController.this.popupReplyMessages = popupArray;
                        Intent popupIntent = new Intent(ApplicationLoader.applicationContext, PopupNotificationActivity.class);
                        popupIntent.putExtra("force", true);
                        popupIntent.setFlags(268763140);
                        ApplicationLoader.applicationContext.startActivity(popupIntent);
                        ApplicationLoader.applicationContext.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
                    }
                });
            }
        }
    }

    public static NotificationsController getInstance() {
        NotificationsController localInstance = Instance;
        if (localInstance == null) {
            synchronized (NotificationsController.class) {
                try {
                    localInstance = Instance;
                    if (localInstance == null) {
                        NotificationsController localInstance2 = new NotificationsController();
                        try {
                            Instance = localInstance2;
                            localInstance = localInstance2;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            localInstance = localInstance2;
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    throw th2;
                }
            }
        }
        return localInstance;
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
        this.notificationManager = NotificationManagerCompat.from(ApplicationLoader.applicationContext);
        this.inChatSoundEnabled = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("EnableInChatSound", true);
        try {
            this.audioManager = (AudioManager) ApplicationLoader.applicationContext.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        } catch (Exception e) {
            FileLog.e(e);
        }
        try {
            this.alarmManager = (AlarmManager) ApplicationLoader.applicationContext.getSystemService("alarm");
        } catch (Exception e2) {
            FileLog.e(e2);
        }
        try {
            this.notificationDelayWakelock = ((PowerManager) ApplicationLoader.applicationContext.getSystemService("power")).newWakeLock(1, JoinPoint.SYNCHRONIZATION_LOCK);
            this.notificationDelayWakelock.setReferenceCounted(false);
        } catch (Exception e22) {
            FileLog.e(e22);
        }
        this.notificationDelayRunnable = new C15861();
    }

    public void cleanup() {
        this.popupMessages.clear();
        this.popupReplyMessages.clear();
        this.notificationsQueue.postRunnable(new C15872());
    }

    public void setInChatSoundEnabled(boolean value) {
        this.inChatSoundEnabled = value;
    }

    public void setOpenedDialogId(final long dialog_id) {
        this.notificationsQueue.postRunnable(new Runnable() {
            public void run() {
                NotificationsController.this.opened_dialog_id = dialog_id;
            }
        });
    }

    public void setLastOnlineFromOtherDevice(final int time) {
        this.notificationsQueue.postRunnable(new Runnable() {
            public void run() {
                FileLog.e("set last online from other device = " + time);
                NotificationsController.this.lastOnlineFromOtherDevice = time;
            }
        });
    }

    public void removeNotificationsForDialog(long did) {
        getInstance().processReadMessages(null, did, 0, Integer.MAX_VALUE, false);
        HashMap<Long, Integer> dialogsToUpdate = new HashMap();
        dialogsToUpdate.put(Long.valueOf(did), Integer.valueOf(0));
        getInstance().processDialogsUpdateRead(dialogsToUpdate);
    }

    public boolean hasMessagesToReply() {
        for (int a = 0; a < this.pushMessages.size(); a++) {
            MessageObject messageObject = (MessageObject) this.pushMessages.get(a);
            long dialog_id = messageObject.getDialogId();
            if ((!messageObject.messageOwner.mentioned || !(messageObject.messageOwner.action instanceof TLRPC$TL_messageActionPinMessage)) && ((int) dialog_id) != 0 && (messageObject.messageOwner.to_id.channel_id == 0 || messageObject.isMegagroup())) {
                return true;
            }
        }
        return false;
    }

    protected void showSingleBackgroundNotification() {
        this.notificationsQueue.postRunnable(new C15905());
    }

    protected void forceShowPopupForReply() {
        this.notificationsQueue.postRunnable(new C15926());
    }

    public void removeDeletedMessagesFromNotifications(final SparseArray<ArrayList<Integer>> deletedMessages) {
        final ArrayList<MessageObject> popupArray = this.popupMessages.isEmpty() ? null : new ArrayList(this.popupMessages);
        this.notificationsQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.NotificationsController$7$1 */
            class C15931 implements Runnable {
                C15931() {
                }

                public void run() {
                    NotificationsController.this.popupMessages = popupArray;
                }
            }

            public void run() {
                int old_unread_count = NotificationsController.this.total_unread_count;
                SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                for (int a = 0; a < deletedMessages.size(); a++) {
                    int key = deletedMessages.keyAt(a);
                    long dialog_id = (long) (-key);
                    ArrayList<Integer> mids = (ArrayList) deletedMessages.get(key);
                    Integer currentCount = (Integer) NotificationsController.this.pushDialogs.get(Long.valueOf(dialog_id));
                    if (currentCount == null) {
                        currentCount = Integer.valueOf(0);
                    }
                    Integer newCount = currentCount;
                    for (int b = 0; b < mids.size(); b++) {
                        long mid = ((long) ((Integer) mids.get(b)).intValue()) | (((long) key) << 32);
                        MessageObject messageObject = (MessageObject) NotificationsController.this.pushMessagesDict.get(Long.valueOf(mid));
                        if (messageObject != null) {
                            NotificationsController.this.pushMessagesDict.remove(Long.valueOf(mid));
                            NotificationsController.this.delayedPushMessages.remove(messageObject);
                            NotificationsController.this.pushMessages.remove(messageObject);
                            if (NotificationsController.this.isPersonalMessage(messageObject)) {
                                NotificationsController.this.personal_count = NotificationsController.this.personal_count - 1;
                            }
                            if (popupArray != null) {
                                popupArray.remove(messageObject);
                            }
                            newCount = Integer.valueOf(newCount.intValue() - 1);
                        }
                    }
                    if (newCount.intValue() <= 0) {
                        newCount = Integer.valueOf(0);
                        NotificationsController.this.smartNotificationsDialogs.remove(Long.valueOf(dialog_id));
                    }
                    if (!newCount.equals(currentCount)) {
                        NotificationsController.this.total_unread_count = NotificationsController.this.total_unread_count - currentCount.intValue();
                        NotificationsController.this.total_unread_count = NotificationsController.this.total_unread_count + newCount.intValue();
                        NotificationsController.this.pushDialogs.put(Long.valueOf(dialog_id), newCount);
                    }
                    if (newCount.intValue() == 0) {
                        NotificationsController.this.pushDialogs.remove(Long.valueOf(dialog_id));
                        NotificationsController.this.pushDialogsOverrideMention.remove(Long.valueOf(dialog_id));
                        if (!(popupArray == null || !NotificationsController.this.pushMessages.isEmpty() || popupArray.isEmpty())) {
                            popupArray.clear();
                        }
                    }
                }
                if (popupArray != null) {
                    AndroidUtilities.runOnUIThread(new C15931());
                }
                if (old_unread_count != NotificationsController.this.total_unread_count) {
                    if (NotificationsController.this.notifyCheck) {
                        NotificationsController.this.scheduleNotificationDelay(NotificationsController.this.lastOnlineFromOtherDevice > ConnectionsManager.getInstance().getCurrentTime());
                    } else {
                        NotificationsController.this.delayedPushMessages.clear();
                        NotificationsController.this.showOrUpdateNotification(NotificationsController.this.notifyCheck);
                    }
                }
                NotificationsController.this.notifyCheck = false;
                if (preferences.getBoolean("badgeNumber", true)) {
                    NotificationsController.this.setBadge(NotificationsController.this.total_unread_count);
                }
            }
        });
    }

    public void removeDeletedHisoryFromNotifications(final SparseArray<Integer> deletedMessages) {
        final ArrayList<MessageObject> popupArray = this.popupMessages.isEmpty() ? null : new ArrayList(this.popupMessages);
        this.notificationsQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.NotificationsController$8$1 */
            class C15951 implements Runnable {
                C15951() {
                }

                public void run() {
                    NotificationsController.this.popupMessages = popupArray;
                }
            }

            public void run() {
                int old_unread_count = NotificationsController.this.total_unread_count;
                SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                for (int a = 0; a < deletedMessages.size(); a++) {
                    int key = deletedMessages.keyAt(a);
                    long dialog_id = (long) (-key);
                    Integer id = (Integer) deletedMessages.get(key);
                    Integer currentCount = (Integer) NotificationsController.this.pushDialogs.get(Long.valueOf(dialog_id));
                    if (currentCount == null) {
                        currentCount = Integer.valueOf(0);
                    }
                    Integer newCount = currentCount;
                    int c = 0;
                    while (c < NotificationsController.this.pushMessages.size()) {
                        MessageObject messageObject = (MessageObject) NotificationsController.this.pushMessages.get(c);
                        if (messageObject.getDialogId() == dialog_id && messageObject.getId() <= id.intValue()) {
                            NotificationsController.this.pushMessagesDict.remove(Long.valueOf(messageObject.getIdWithChannel()));
                            NotificationsController.this.delayedPushMessages.remove(messageObject);
                            NotificationsController.this.pushMessages.remove(messageObject);
                            c--;
                            if (NotificationsController.this.isPersonalMessage(messageObject)) {
                                NotificationsController.this.personal_count = NotificationsController.this.personal_count - 1;
                            }
                            if (popupArray != null) {
                                popupArray.remove(messageObject);
                            }
                            newCount = Integer.valueOf(newCount.intValue() - 1);
                        }
                        c++;
                    }
                    if (newCount.intValue() <= 0) {
                        newCount = Integer.valueOf(0);
                        NotificationsController.this.smartNotificationsDialogs.remove(Long.valueOf(dialog_id));
                    }
                    if (!newCount.equals(currentCount)) {
                        NotificationsController.this.total_unread_count = NotificationsController.this.total_unread_count - currentCount.intValue();
                        NotificationsController.this.total_unread_count = NotificationsController.this.total_unread_count + newCount.intValue();
                        NotificationsController.this.pushDialogs.put(Long.valueOf(dialog_id), newCount);
                    }
                    if (newCount.intValue() == 0) {
                        NotificationsController.this.pushDialogs.remove(Long.valueOf(dialog_id));
                        NotificationsController.this.pushDialogsOverrideMention.remove(Long.valueOf(dialog_id));
                        if (!(popupArray == null || !NotificationsController.this.pushMessages.isEmpty() || popupArray.isEmpty())) {
                            popupArray.clear();
                        }
                    }
                }
                if (popupArray != null) {
                    AndroidUtilities.runOnUIThread(new C15951());
                }
                if (old_unread_count != NotificationsController.this.total_unread_count) {
                    if (NotificationsController.this.notifyCheck) {
                        NotificationsController.this.scheduleNotificationDelay(NotificationsController.this.lastOnlineFromOtherDevice > ConnectionsManager.getInstance().getCurrentTime());
                    } else {
                        NotificationsController.this.delayedPushMessages.clear();
                        NotificationsController.this.showOrUpdateNotification(NotificationsController.this.notifyCheck);
                    }
                }
                NotificationsController.this.notifyCheck = false;
                if (preferences.getBoolean("badgeNumber", true)) {
                    NotificationsController.this.setBadge(NotificationsController.this.total_unread_count);
                }
            }
        });
    }

    public void processReadMessages(SparseArray<Long> inbox, long dialog_id, int max_date, int max_id, boolean isPopup) {
        final ArrayList<MessageObject> popupArray = this.popupMessages.isEmpty() ? null : new ArrayList(this.popupMessages);
        final SparseArray<Long> sparseArray = inbox;
        final long j = dialog_id;
        final int i = max_id;
        final int i2 = max_date;
        final boolean z = isPopup;
        this.notificationsQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.NotificationsController$9$1 */
            class C15971 implements Runnable {
                C15971() {
                }

                public void run() {
                    NotificationsController.this.popupMessages = popupArray;
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.pushMessagesUpdated, new Object[0]);
                }
            }

            public void run() {
                int a;
                MessageObject messageObject;
                long mid;
                int oldCount = popupArray != null ? popupArray.size() : 0;
                if (sparseArray != null) {
                    for (int b = 0; b < sparseArray.size(); b++) {
                        int key = sparseArray.keyAt(b);
                        long messageId = ((Long) sparseArray.get(key)).longValue();
                        a = 0;
                        while (a < NotificationsController.this.pushMessages.size()) {
                            messageObject = (MessageObject) NotificationsController.this.pushMessages.get(a);
                            if (messageObject.getDialogId() == ((long) key) && messageObject.getId() <= ((int) messageId)) {
                                if (NotificationsController.this.isPersonalMessage(messageObject)) {
                                    NotificationsController.this.personal_count = NotificationsController.this.personal_count - 1;
                                }
                                if (popupArray != null) {
                                    popupArray.remove(messageObject);
                                }
                                mid = (long) messageObject.messageOwner.id;
                                if (messageObject.messageOwner.to_id.channel_id != 0) {
                                    mid |= ((long) messageObject.messageOwner.to_id.channel_id) << 32;
                                }
                                NotificationsController.this.pushMessagesDict.remove(Long.valueOf(mid));
                                NotificationsController.this.delayedPushMessages.remove(messageObject);
                                NotificationsController.this.pushMessages.remove(a);
                                a--;
                            }
                            a++;
                        }
                    }
                    if (!(popupArray == null || !NotificationsController.this.pushMessages.isEmpty() || popupArray.isEmpty())) {
                        popupArray.clear();
                    }
                }
                if (!(j == 0 || (i == 0 && i2 == 0))) {
                    a = 0;
                    while (a < NotificationsController.this.pushMessages.size()) {
                        messageObject = (MessageObject) NotificationsController.this.pushMessages.get(a);
                        if (messageObject.getDialogId() == j) {
                            boolean remove = false;
                            if (i2 != 0) {
                                if (messageObject.messageOwner.date <= i2) {
                                    remove = true;
                                }
                            } else if (z) {
                                if (messageObject.getId() == i || i < 0) {
                                    remove = true;
                                }
                            } else if (messageObject.getId() <= i || i < 0) {
                                remove = true;
                            }
                            if (remove) {
                                if (NotificationsController.this.isPersonalMessage(messageObject)) {
                                    NotificationsController.this.personal_count = NotificationsController.this.personal_count - 1;
                                }
                                NotificationsController.this.pushMessages.remove(a);
                                NotificationsController.this.delayedPushMessages.remove(messageObject);
                                if (popupArray != null) {
                                    popupArray.remove(messageObject);
                                }
                                mid = (long) messageObject.messageOwner.id;
                                if (messageObject.messageOwner.to_id.channel_id != 0) {
                                    mid |= ((long) messageObject.messageOwner.to_id.channel_id) << 32;
                                }
                                NotificationsController.this.pushMessagesDict.remove(Long.valueOf(mid));
                                a--;
                            }
                        }
                        a++;
                    }
                    if (!(popupArray == null || !NotificationsController.this.pushMessages.isEmpty() || popupArray.isEmpty())) {
                        popupArray.clear();
                    }
                }
                if (popupArray != null && oldCount != popupArray.size()) {
                    AndroidUtilities.runOnUIThread(new C15971());
                }
            }
        });
    }

    public void processNewMessages(final ArrayList<MessageObject> messageObjects, final boolean isLast) {
        if (!messageObjects.isEmpty()) {
            final ArrayList<MessageObject> popupArray = new ArrayList(this.popupMessages);
            this.notificationsQueue.postRunnable(new Runnable() {
                public void run() {
                    boolean added = false;
                    int oldCount = popupArray.size();
                    HashMap<Long, Boolean> settingsCache = new HashMap();
                    SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                    boolean allowPinned = preferences.getBoolean("PinnedMessages", true);
                    int popup = 0;
                    for (int a = 0; a < messageObjects.size(); a++) {
                        MessageObject messageObject = (MessageObject) messageObjects.get(a);
                        long mid = (long) messageObject.messageOwner.id;
                        if (messageObject.messageOwner.to_id.channel_id != 0) {
                            mid |= ((long) messageObject.messageOwner.to_id.channel_id) << 32;
                        }
                        if (!NotificationsController.this.pushMessagesDict.containsKey(Long.valueOf(mid))) {
                            long dialog_id = messageObject.getDialogId();
                            long original_dialog_id = dialog_id;
                            if (dialog_id == NotificationsController.this.opened_dialog_id && ApplicationLoader.isScreenOn) {
                                NotificationsController.this.playInChatSound();
                            } else {
                                if (messageObject.messageOwner.mentioned) {
                                    if (allowPinned || !(messageObject.messageOwner.action instanceof TLRPC$TL_messageActionPinMessage)) {
                                        dialog_id = (long) messageObject.messageOwner.from_id;
                                    }
                                }
                                if (NotificationsController.this.isPersonalMessage(messageObject)) {
                                    NotificationsController.this.personal_count = NotificationsController.this.personal_count + 1;
                                }
                                added = true;
                                Boolean value = (Boolean) settingsCache.get(Long.valueOf(dialog_id));
                                int lower_id = (int) dialog_id;
                                boolean isChat = lower_id < 0;
                                if (lower_id != 0) {
                                    if (preferences.getBoolean("custom_" + dialog_id, false)) {
                                        popup = preferences.getInt("popup_" + dialog_id, 0);
                                    } else {
                                        popup = 0;
                                    }
                                    if (popup == 0) {
                                        popup = preferences.getInt(((int) dialog_id) < 0 ? "popupGroup" : "popupAll", 0);
                                    } else if (popup == 1) {
                                        popup = 3;
                                    } else if (popup == 2) {
                                        popup = 0;
                                    }
                                }
                                if (value == null) {
                                    int notifyOverride = NotificationsController.this.getNotifyOverride(preferences, dialog_id);
                                    boolean z = notifyOverride != 2 && ((preferences.getBoolean("EnableAll", true) && (!isChat || preferences.getBoolean("EnableGroup", true))) || notifyOverride != 0);
                                    value = Boolean.valueOf(z);
                                    settingsCache.put(Long.valueOf(dialog_id), value);
                                }
                                if (!(popup == 0 || messageObject.messageOwner.to_id.channel_id == 0 || messageObject.isMegagroup())) {
                                    popup = 0;
                                }
                                if (value.booleanValue()) {
                                    if (popup != 0) {
                                        popupArray.add(0, messageObject);
                                    }
                                    NotificationsController.this.delayedPushMessages.add(messageObject);
                                    NotificationsController.this.pushMessages.add(0, messageObject);
                                    NotificationsController.this.pushMessagesDict.put(Long.valueOf(mid), messageObject);
                                    if (original_dialog_id != dialog_id) {
                                        NotificationsController.this.pushDialogsOverrideMention.put(Long.valueOf(original_dialog_id), Integer.valueOf(1));
                                    }
                                }
                            }
                        }
                    }
                    if (added) {
                        NotificationsController.this.notifyCheck = isLast;
                    }
                    if (!popupArray.isEmpty() && oldCount != popupArray.size() && !AndroidUtilities.needShowPasscode(false)) {
                        final int i = popup;
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                NotificationsController.this.popupMessages = popupArray;
                                if (!ApplicationLoader.mainInterfacePaused && (ApplicationLoader.isScreenOn || UserConfig.isWaitingForPasscodeEnter)) {
                                    return;
                                }
                                if (i == 3 || ((i == 1 && ApplicationLoader.isScreenOn) || (i == 2 && !ApplicationLoader.isScreenOn))) {
                                    Intent popupIntent = new Intent(ApplicationLoader.applicationContext, PopupNotificationActivity.class);
                                    popupIntent.setFlags(268763140);
                                    ApplicationLoader.applicationContext.startActivity(popupIntent);
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    public void processDialogsUpdateRead(final HashMap<Long, Integer> dialogsToUpdate) {
        final ArrayList<MessageObject> popupArray = this.popupMessages.isEmpty() ? null : new ArrayList(this.popupMessages);
        this.notificationsQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.NotificationsController$11$1 */
            class C15821 implements Runnable {
                C15821() {
                }

                public void run() {
                    NotificationsController.this.popupMessages = popupArray;
                }
            }

            public void run() {
                int old_unread_count = NotificationsController.this.total_unread_count;
                SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                for (Entry<Long, Integer> entry : dialogsToUpdate.entrySet()) {
                    long dialog_id = ((Long) entry.getKey()).longValue();
                    int notifyOverride = NotificationsController.this.getNotifyOverride(preferences, dialog_id);
                    if (NotificationsController.this.notifyCheck) {
                        Integer override = (Integer) NotificationsController.this.pushDialogsOverrideMention.get(Long.valueOf(dialog_id));
                        if (override != null && override.intValue() == 1) {
                            NotificationsController.this.pushDialogsOverrideMention.put(Long.valueOf(dialog_id), Integer.valueOf(0));
                            notifyOverride = 1;
                        }
                    }
                    boolean canAddValue = notifyOverride != 2 && ((preferences.getBoolean("EnableAll", true) && (((int) dialog_id) >= 0 || preferences.getBoolean("EnableGroup", true))) || notifyOverride != 0);
                    Integer currentCount = (Integer) NotificationsController.this.pushDialogs.get(Long.valueOf(dialog_id));
                    Integer newCount = (Integer) entry.getValue();
                    if (newCount.intValue() == 0) {
                        NotificationsController.this.smartNotificationsDialogs.remove(Long.valueOf(dialog_id));
                    }
                    if (newCount.intValue() < 0) {
                        if (currentCount != null) {
                            newCount = Integer.valueOf(currentCount.intValue() + newCount.intValue());
                        }
                    }
                    if ((canAddValue || newCount.intValue() == 0) && currentCount != null) {
                        NotificationsController.this.total_unread_count = NotificationsController.this.total_unread_count - currentCount.intValue();
                    }
                    if (newCount.intValue() == 0) {
                        NotificationsController.this.pushDialogs.remove(Long.valueOf(dialog_id));
                        NotificationsController.this.pushDialogsOverrideMention.remove(Long.valueOf(dialog_id));
                        int a = 0;
                        while (a < NotificationsController.this.pushMessages.size()) {
                            MessageObject messageObject = (MessageObject) NotificationsController.this.pushMessages.get(a);
                            if (messageObject.getDialogId() == dialog_id) {
                                if (NotificationsController.this.isPersonalMessage(messageObject)) {
                                    NotificationsController.this.personal_count = NotificationsController.this.personal_count - 1;
                                }
                                NotificationsController.this.pushMessages.remove(a);
                                a--;
                                NotificationsController.this.delayedPushMessages.remove(messageObject);
                                long mid = (long) messageObject.messageOwner.id;
                                if (messageObject.messageOwner.to_id.channel_id != 0) {
                                    mid |= ((long) messageObject.messageOwner.to_id.channel_id) << 32;
                                }
                                NotificationsController.this.pushMessagesDict.remove(Long.valueOf(mid));
                                if (popupArray != null) {
                                    popupArray.remove(messageObject);
                                }
                            }
                            a++;
                        }
                        if (!(popupArray == null || !NotificationsController.this.pushMessages.isEmpty() || popupArray.isEmpty())) {
                            popupArray.clear();
                        }
                    } else if (canAddValue) {
                        NotificationsController.this.total_unread_count = NotificationsController.this.total_unread_count + newCount.intValue();
                        NotificationsController.this.pushDialogs.put(Long.valueOf(dialog_id), newCount);
                    }
                }
                if (popupArray != null) {
                    AndroidUtilities.runOnUIThread(new C15821());
                }
                if (old_unread_count != NotificationsController.this.total_unread_count) {
                    if (NotificationsController.this.notifyCheck) {
                        NotificationsController.this.scheduleNotificationDelay(NotificationsController.this.lastOnlineFromOtherDevice > ConnectionsManager.getInstance().getCurrentTime());
                    } else {
                        NotificationsController.this.delayedPushMessages.clear();
                        NotificationsController.this.showOrUpdateNotification(NotificationsController.this.notifyCheck);
                    }
                }
                NotificationsController.this.notifyCheck = false;
                if (preferences.getBoolean("badgeNumber", true)) {
                    NotificationsController.this.setBadge(NotificationsController.this.total_unread_count);
                }
            }
        });
    }

    public void processLoadedUnreadMessages(final HashMap<Long, Integer> dialogs, final ArrayList<TLRPC$Message> messages, ArrayList<User> users, ArrayList<TLRPC$Chat> chats, ArrayList<TLRPC$EncryptedChat> encryptedChats) {
        MessagesController.getInstance().putUsers(users, true);
        MessagesController.getInstance().putChats(chats, true);
        MessagesController.getInstance().putEncryptedChats(encryptedChats, true);
        this.notificationsQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.NotificationsController$12$1 */
            class C15831 implements Runnable {
                C15831() {
                }

                public void run() {
                    NotificationsController.this.popupMessages.clear();
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.pushMessagesUpdated, new Object[0]);
                }
            }

            public void run() {
                long dialog_id;
                Boolean value;
                NotificationsController.this.pushDialogs.clear();
                NotificationsController.this.pushMessages.clear();
                NotificationsController.this.pushMessagesDict.clear();
                NotificationsController.this.total_unread_count = 0;
                NotificationsController.this.personal_count = 0;
                SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                HashMap<Long, Boolean> settingsCache = new HashMap();
                if (messages != null) {
                    for (int a = 0; a < messages.size(); a++) {
                        TLRPC$Message message = (TLRPC$Message) messages.get(a);
                        long mid = (long) message.id;
                        if (message.to_id.channel_id != 0) {
                            mid |= ((long) message.to_id.channel_id) << 32;
                        }
                        if (!NotificationsController.this.pushMessagesDict.containsKey(Long.valueOf(mid))) {
                            MessageObject messageObject = new MessageObject(message, null, false);
                            if (NotificationsController.this.isPersonalMessage(messageObject)) {
                                NotificationsController.this.personal_count = NotificationsController.this.personal_count + 1;
                            }
                            dialog_id = messageObject.getDialogId();
                            long original_dialog_id = dialog_id;
                            if (messageObject.messageOwner.mentioned) {
                                dialog_id = (long) messageObject.messageOwner.from_id;
                            }
                            value = (Boolean) settingsCache.get(Long.valueOf(dialog_id));
                            if (value == null) {
                                int notifyOverride = NotificationsController.this.getNotifyOverride(preferences, dialog_id);
                                boolean z = notifyOverride != 2 && ((preferences.getBoolean("EnableAll", true) && (((int) dialog_id) >= 0 || preferences.getBoolean("EnableGroup", true))) || notifyOverride != 0);
                                value = Boolean.valueOf(z);
                                settingsCache.put(Long.valueOf(dialog_id), value);
                            }
                            if (value.booleanValue() && !(dialog_id == NotificationsController.this.opened_dialog_id && ApplicationLoader.isScreenOn)) {
                                NotificationsController.this.pushMessagesDict.put(Long.valueOf(mid), messageObject);
                                NotificationsController.this.pushMessages.add(0, messageObject);
                                if (original_dialog_id != dialog_id) {
                                    NotificationsController.this.pushDialogsOverrideMention.put(Long.valueOf(original_dialog_id), Integer.valueOf(1));
                                }
                            }
                        }
                    }
                }
                for (Entry<Long, Integer> entry : dialogs.entrySet()) {
                    dialog_id = ((Long) entry.getKey()).longValue();
                    value = (Boolean) settingsCache.get(Long.valueOf(dialog_id));
                    if (value == null) {
                        notifyOverride = NotificationsController.this.getNotifyOverride(preferences, dialog_id);
                        Integer override = (Integer) NotificationsController.this.pushDialogsOverrideMention.get(Long.valueOf(dialog_id));
                        if (override != null && override.intValue() == 1) {
                            NotificationsController.this.pushDialogsOverrideMention.put(Long.valueOf(dialog_id), Integer.valueOf(0));
                            notifyOverride = 1;
                        }
                        z = notifyOverride != 2 && ((preferences.getBoolean("EnableAll", true) && (((int) dialog_id) >= 0 || preferences.getBoolean("EnableGroup", true))) || notifyOverride != 0);
                        value = Boolean.valueOf(z);
                        settingsCache.put(Long.valueOf(dialog_id), value);
                    }
                    if (value.booleanValue()) {
                        int count = ((Integer) entry.getValue()).intValue();
                        NotificationsController.this.pushDialogs.put(Long.valueOf(dialog_id), Integer.valueOf(count));
                        NotificationsController.this.total_unread_count = NotificationsController.this.total_unread_count + count;
                    }
                }
                if (NotificationsController.this.total_unread_count == 0) {
                    AndroidUtilities.runOnUIThread(new C15831());
                }
                NotificationsController.this.showOrUpdateNotification(SystemClock.uptimeMillis() / 1000 < 60);
                if (preferences.getBoolean("badgeNumber", true)) {
                    NotificationsController.this.setBadge(NotificationsController.this.total_unread_count);
                }
            }
        });
    }

    public void setBadgeEnabled(boolean enabled) {
        setBadge(enabled ? this.total_unread_count : 0);
    }

    private void setBadge(final int count) {
        this.notificationsQueue.postRunnable(new Runnable() {
            public void run() {
                if (NotificationsController.this.lastBadgeCount != count) {
                    NotificationsController.this.lastBadgeCount = count;
                    NotificationBadge.applyCount(count);
                }
            }
        });
    }

    private String getStringForMessage(MessageObject messageObject, boolean shortMessage, boolean[] text) {
        User user;
        TLRPC$Chat chat;
        long dialog_id = messageObject.messageOwner.dialog_id;
        int chat_id = messageObject.messageOwner.to_id.chat_id != 0 ? messageObject.messageOwner.to_id.chat_id : messageObject.messageOwner.to_id.channel_id;
        int from_id = messageObject.messageOwner.to_id.user_id;
        if (from_id == 0) {
            if (messageObject.isFromUser() || messageObject.getId() < 0) {
                from_id = messageObject.messageOwner.from_id;
            } else {
                from_id = -chat_id;
            }
        } else if (from_id == UserConfig.getClientUserId()) {
            from_id = messageObject.messageOwner.from_id;
        }
        if (dialog_id == 0) {
            if (chat_id != 0) {
                dialog_id = (long) (-chat_id);
            } else if (from_id != 0) {
                dialog_id = (long) from_id;
            }
        }
        String name = null;
        if (from_id > 0) {
            user = MessagesController.getInstance().getUser(Integer.valueOf(from_id));
            if (user != null) {
                name = UserObject.getUserName(user);
            }
        } else {
            chat = MessagesController.getInstance().getChat(Integer.valueOf(-from_id));
            if (chat != null) {
                name = chat.title;
            }
        }
        if (name == null) {
            return null;
        }
        chat = null;
        if (chat_id != 0) {
            chat = MessagesController.getInstance().getChat(Integer.valueOf(chat_id));
            if (chat == null) {
                return null;
            }
        }
        if (((int) dialog_id) == 0 || AndroidUtilities.needShowPasscode(false) || UserConfig.isWaitingForPasscodeEnter) {
            return LocaleController.getString("YouHaveNewMessage", R.string.YouHaveNewMessage);
        }
        String msg;
        if (chat_id == 0 && from_id != 0) {
            if (!ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("EnablePreviewAll", true)) {
                return LocaleController.formatString("NotificationMessageNoText", R.string.NotificationMessageNoText, new Object[]{name});
            } else if (messageObject.messageOwner instanceof TLRPC$TL_messageService) {
                if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionUserJoined) {
                    return LocaleController.formatString("NotificationContactJoined", R.string.NotificationContactJoined, new Object[]{name});
                } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionUserUpdatedPhoto) {
                    return LocaleController.formatString("NotificationContactNewPhoto", R.string.NotificationContactNewPhoto, new Object[]{name});
                } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionLoginUnknownLocation) {
                    String date = LocaleController.formatString("formatDateAtTime", R.string.formatDateAtTime, new Object[]{LocaleController.getInstance().formatterYear.format(((long) messageObject.messageOwner.date) * 1000), LocaleController.getInstance().formatterDay.format(((long) messageObject.messageOwner.date) * 1000)});
                    return LocaleController.formatString("NotificationUnrecognizedDevice", R.string.NotificationUnrecognizedDevice, new Object[]{UserConfig.getCurrentUser().first_name, date, messageObject.messageOwner.action.title, messageObject.messageOwner.action.address});
                } else if ((messageObject.messageOwner.action instanceof TLRPC$TL_messageActionGameScore) || (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionPaymentSent)) {
                    return messageObject.messageText.toString();
                } else {
                    if (!(messageObject.messageOwner.action instanceof TLRPC$TL_messageActionPhoneCall)) {
                        return null;
                    }
                    TLRPC$PhoneCallDiscardReason reason = messageObject.messageOwner.action.reason;
                    if (messageObject.isOut() || !(reason instanceof TLRPC$TL_phoneCallDiscardReasonMissed)) {
                        return null;
                    }
                    return LocaleController.getString("CallMessageIncomingMissed", R.string.CallMessageIncomingMissed);
                }
            } else if (messageObject.isMediaEmpty()) {
                if (shortMessage) {
                    return LocaleController.formatString("NotificationMessageNoText", R.string.NotificationMessageNoText, new Object[]{name});
                } else if (messageObject.messageOwner.message == null || messageObject.messageOwner.message.length() == 0) {
                    return LocaleController.formatString("NotificationMessageNoText", R.string.NotificationMessageNoText, new Object[]{name});
                } else {
                    msg = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, new Object[]{name, messageObject.messageOwner.message});
                    text[0] = true;
                    return msg;
                }
            } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) {
                if (!shortMessage && VERSION.SDK_INT >= 19 && !TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                    msg = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, new Object[]{name, "🖼 " + messageObject.messageOwner.media.caption});
                    text[0] = true;
                    return msg;
                } else if (messageObject.messageOwner.media.ttl_seconds != 0) {
                    return LocaleController.formatString("NotificationMessageSDPhoto", R.string.NotificationMessageSDPhoto, new Object[]{name});
                } else {
                    return LocaleController.formatString("NotificationMessagePhoto", R.string.NotificationMessagePhoto, new Object[]{name});
                }
            } else if (messageObject.isVideo()) {
                if (!shortMessage && VERSION.SDK_INT >= 19 && !TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                    msg = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, new Object[]{name, "📹 " + messageObject.messageOwner.media.caption});
                    text[0] = true;
                    return msg;
                } else if (messageObject.messageOwner.media.ttl_seconds != 0) {
                    return LocaleController.formatString("NotificationMessageSDVideo", R.string.NotificationMessageSDVideo, new Object[]{name});
                } else {
                    return LocaleController.formatString("NotificationMessageVideo", R.string.NotificationMessageVideo, new Object[]{name});
                }
            } else if (messageObject.isGame()) {
                return LocaleController.formatString("NotificationMessageGame", R.string.NotificationMessageGame, new Object[]{name, messageObject.messageOwner.media.game.title});
            } else if (messageObject.isVoice()) {
                return LocaleController.formatString("NotificationMessageAudio", R.string.NotificationMessageAudio, new Object[]{name});
            } else if (messageObject.isRoundVideo()) {
                return LocaleController.formatString("NotificationMessageRound", R.string.NotificationMessageRound, new Object[]{name});
            } else if (messageObject.isMusic()) {
                return LocaleController.formatString("NotificationMessageMusic", R.string.NotificationMessageMusic, new Object[]{name});
            } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaContact) {
                return LocaleController.formatString("NotificationMessageContact", R.string.NotificationMessageContact, new Object[]{name});
            } else if ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeo) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaVenue)) {
                return LocaleController.formatString("NotificationMessageMap", R.string.NotificationMessageMap, new Object[]{name});
            } else if (!(messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaDocument)) {
                return null;
            } else {
                if (messageObject.isSticker()) {
                    if (messageObject.getStickerEmoji() != null) {
                        return LocaleController.formatString("NotificationMessageStickerEmoji", R.string.NotificationMessageStickerEmoji, new Object[]{name, messageObject.getStickerEmoji()});
                    }
                    return LocaleController.formatString("NotificationMessageSticker", R.string.NotificationMessageSticker, new Object[]{name});
                } else if (messageObject.isGif()) {
                    if (shortMessage || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                        return LocaleController.formatString("NotificationMessageGif", R.string.NotificationMessageGif, new Object[]{name});
                    }
                    msg = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, new Object[]{name, "🎬 " + messageObject.messageOwner.media.caption});
                    text[0] = true;
                    return msg;
                } else if (shortMessage || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                    return LocaleController.formatString("NotificationMessageDocument", R.string.NotificationMessageDocument, new Object[]{name});
                } else {
                    msg = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, new Object[]{name, "📎 " + messageObject.messageOwner.media.caption});
                    text[0] = true;
                    return msg;
                }
            }
        } else if (chat_id == 0) {
            return null;
        } else {
            if (ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("EnablePreviewGroup", true)) {
                if (messageObject.messageOwner instanceof TLRPC$TL_messageService) {
                    if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatAddUser) {
                        int singleUserId = messageObject.messageOwner.action.user_id;
                        if (singleUserId == 0 && messageObject.messageOwner.action.users.size() == 1) {
                            singleUserId = ((Integer) messageObject.messageOwner.action.users.get(0)).intValue();
                        }
                        if (singleUserId == 0) {
                            StringBuilder stringBuilder = new StringBuilder("");
                            for (int a = 0; a < messageObject.messageOwner.action.users.size(); a++) {
                                user = MessagesController.getInstance().getUser((Integer) messageObject.messageOwner.action.users.get(a));
                                if (user != null) {
                                    String name2 = UserObject.getUserName(user);
                                    if (stringBuilder.length() != 0) {
                                        stringBuilder.append(", ");
                                    }
                                    stringBuilder.append(name2);
                                }
                            }
                            return LocaleController.formatString("NotificationGroupAddMember", R.string.NotificationGroupAddMember, new Object[]{name, chat.title, stringBuilder.toString()});
                        } else if (messageObject.messageOwner.to_id.channel_id != 0 && !chat.megagroup) {
                            return LocaleController.formatString("ChannelAddedByNotification", R.string.ChannelAddedByNotification, new Object[]{name, chat.title});
                        } else if (singleUserId == UserConfig.getClientUserId()) {
                            return LocaleController.formatString("NotificationInvitedToGroup", R.string.NotificationInvitedToGroup, new Object[]{name, chat.title});
                        } else {
                            User u2 = MessagesController.getInstance().getUser(Integer.valueOf(singleUserId));
                            if (u2 == null) {
                                return null;
                            }
                            if (from_id != u2.id) {
                                return LocaleController.formatString("NotificationGroupAddMember", R.string.NotificationGroupAddMember, new Object[]{name, chat.title, UserObject.getUserName(u2)});
                            } else if (chat.megagroup) {
                                return LocaleController.formatString("NotificationGroupAddSelfMega", R.string.NotificationGroupAddSelfMega, new Object[]{name, chat.title});
                            } else {
                                return LocaleController.formatString("NotificationGroupAddSelf", R.string.NotificationGroupAddSelf, new Object[]{name, chat.title});
                            }
                        }
                    } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatJoinedByLink) {
                        return LocaleController.formatString("NotificationInvitedToGroupByLink", R.string.NotificationInvitedToGroupByLink, new Object[]{name, chat.title});
                    } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatEditTitle) {
                        return LocaleController.formatString("NotificationEditedGroupName", R.string.NotificationEditedGroupName, new Object[]{name, messageObject.messageOwner.action.title});
                    } else if ((messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatEditPhoto) || (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatDeletePhoto)) {
                        if (messageObject.messageOwner.to_id.channel_id == 0 || chat.megagroup) {
                            return LocaleController.formatString("NotificationEditedGroupPhoto", R.string.NotificationEditedGroupPhoto, new Object[]{name, chat.title});
                        }
                        return LocaleController.formatString("ChannelPhotoEditNotification", R.string.ChannelPhotoEditNotification, new Object[]{chat.title});
                    } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatDeleteUser) {
                        if (messageObject.messageOwner.action.user_id == UserConfig.getClientUserId()) {
                            return LocaleController.formatString("NotificationGroupKickYou", R.string.NotificationGroupKickYou, new Object[]{name, chat.title});
                        } else if (messageObject.messageOwner.action.user_id == from_id) {
                            return LocaleController.formatString("NotificationGroupLeftMember", R.string.NotificationGroupLeftMember, new Object[]{name, chat.title});
                        } else {
                            if (MessagesController.getInstance().getUser(Integer.valueOf(messageObject.messageOwner.action.user_id)) == null) {
                                return null;
                            }
                            return LocaleController.formatString("NotificationGroupKickMember", R.string.NotificationGroupKickMember, new Object[]{name, chat.title, UserObject.getUserName(MessagesController.getInstance().getUser(Integer.valueOf(messageObject.messageOwner.action.user_id)))});
                        }
                    } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatCreate) {
                        return messageObject.messageText.toString();
                    } else {
                        if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChannelCreate) {
                            return messageObject.messageText.toString();
                        }
                        if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChatMigrateTo) {
                            return LocaleController.formatString("ActionMigrateFromGroupNotify", R.string.ActionMigrateFromGroupNotify, new Object[]{chat.title});
                        } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionChannelMigrateFrom) {
                            return LocaleController.formatString("ActionMigrateFromGroupNotify", R.string.ActionMigrateFromGroupNotify, new Object[]{messageObject.messageOwner.action.title});
                        } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionScreenshotTaken) {
                            return messageObject.messageText.toString();
                        } else {
                            if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionPinMessage) {
                                MessageObject object;
                                String message;
                                CharSequence message2;
                                if (chat == null || !chat.megagroup) {
                                    if (messageObject.replyMessageObject == null) {
                                        return LocaleController.formatString("NotificationActionPinnedNoTextChannel", R.string.NotificationActionPinnedNoTextChannel, new Object[]{chat.title});
                                    }
                                    object = messageObject.replyMessageObject;
                                    if (object.isMusic()) {
                                        return LocaleController.formatString("NotificationActionPinnedMusicChannel", R.string.NotificationActionPinnedMusicChannel, new Object[]{chat.title});
                                    } else if (object.isVideo()) {
                                        if (VERSION.SDK_INT < 19 || TextUtils.isEmpty(object.messageOwner.media.caption)) {
                                            return LocaleController.formatString("NotificationActionPinnedVideoChannel", R.string.NotificationActionPinnedVideoChannel, new Object[]{chat.title});
                                        }
                                        message = "📹 " + object.messageOwner.media.caption;
                                        return LocaleController.formatString("NotificationActionPinnedTextChannel", R.string.NotificationActionPinnedTextChannel, new Object[]{chat.title, message});
                                    } else if (object.isGif()) {
                                        if (VERSION.SDK_INT < 19 || TextUtils.isEmpty(object.messageOwner.media.caption)) {
                                            return LocaleController.formatString("NotificationActionPinnedGifChannel", R.string.NotificationActionPinnedGifChannel, new Object[]{chat.title});
                                        }
                                        message = "🎬 " + object.messageOwner.media.caption;
                                        return LocaleController.formatString("NotificationActionPinnedTextChannel", R.string.NotificationActionPinnedTextChannel, new Object[]{chat.title, message});
                                    } else if (object.isVoice()) {
                                        return LocaleController.formatString("NotificationActionPinnedVoiceChannel", R.string.NotificationActionPinnedVoiceChannel, new Object[]{chat.title});
                                    } else if (object.isRoundVideo()) {
                                        return LocaleController.formatString("NotificationActionPinnedRoundChannel", R.string.NotificationActionPinnedRoundChannel, new Object[]{chat.title});
                                    } else if (object.isSticker()) {
                                        if (object.getStickerEmoji() != null) {
                                            return LocaleController.formatString("NotificationActionPinnedStickerEmojiChannel", R.string.NotificationActionPinnedStickerEmojiChannel, new Object[]{chat.title, object.getStickerEmoji()});
                                        }
                                        return LocaleController.formatString("NotificationActionPinnedStickerChannel", R.string.NotificationActionPinnedStickerChannel, new Object[]{chat.title});
                                    } else if (object.messageOwner.media instanceof TLRPC$TL_messageMediaDocument) {
                                        if (VERSION.SDK_INT < 19 || TextUtils.isEmpty(object.messageOwner.media.caption)) {
                                            return LocaleController.formatString("NotificationActionPinnedFileChannel", R.string.NotificationActionPinnedFileChannel, new Object[]{chat.title});
                                        }
                                        message = "📎 " + object.messageOwner.media.caption;
                                        return LocaleController.formatString("NotificationActionPinnedTextChannel", R.string.NotificationActionPinnedTextChannel, new Object[]{chat.title, message});
                                    } else if (object.messageOwner.media instanceof TLRPC$TL_messageMediaGeo) {
                                        return LocaleController.formatString("NotificationActionPinnedGeoChannel", R.string.NotificationActionPinnedGeoChannel, new Object[]{chat.title});
                                    } else if (object.messageOwner.media instanceof TLRPC$TL_messageMediaGeoLive) {
                                        return LocaleController.formatString("NotificationActionPinnedGeoLiveChannel", R.string.NotificationActionPinnedGeoLiveChannel, new Object[]{chat.title});
                                    } else if (object.messageOwner.media instanceof TLRPC$TL_messageMediaContact) {
                                        return LocaleController.formatString("NotificationActionPinnedContactChannel", R.string.NotificationActionPinnedContactChannel, new Object[]{chat.title});
                                    } else if (object.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) {
                                        if (VERSION.SDK_INT < 19 || TextUtils.isEmpty(object.messageOwner.media.caption)) {
                                            return LocaleController.formatString("NotificationActionPinnedPhotoChannel", R.string.NotificationActionPinnedPhotoChannel, new Object[]{chat.title});
                                        }
                                        message = "🖼 " + object.messageOwner.media.caption;
                                        return LocaleController.formatString("NotificationActionPinnedTextChannel", R.string.NotificationActionPinnedTextChannel, new Object[]{chat.title, message});
                                    } else if (object.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                                        return LocaleController.formatString("NotificationActionPinnedGameChannel", R.string.NotificationActionPinnedGameChannel, new Object[]{chat.title});
                                    } else if (object.messageText == null || object.messageText.length() <= 0) {
                                        return LocaleController.formatString("NotificationActionPinnedNoTextChannel", R.string.NotificationActionPinnedNoTextChannel, new Object[]{chat.title});
                                    } else {
                                        message2 = object.messageText;
                                        if (message2.length() > 20) {
                                            message2 = message2.subSequence(0, 20) + "...";
                                        }
                                        return LocaleController.formatString("NotificationActionPinnedTextChannel", R.string.NotificationActionPinnedTextChannel, new Object[]{chat.title, message2});
                                    }
                                } else if (messageObject.replyMessageObject == null) {
                                    return LocaleController.formatString("NotificationActionPinnedNoText", R.string.NotificationActionPinnedNoText, new Object[]{name, chat.title});
                                } else {
                                    object = messageObject.replyMessageObject;
                                    if (object.isMusic()) {
                                        return LocaleController.formatString("NotificationActionPinnedMusic", R.string.NotificationActionPinnedMusic, new Object[]{name, chat.title});
                                    } else if (object.isVideo()) {
                                        if (VERSION.SDK_INT < 19 || TextUtils.isEmpty(object.messageOwner.media.caption)) {
                                            return LocaleController.formatString("NotificationActionPinnedVideo", R.string.NotificationActionPinnedVideo, new Object[]{name, chat.title});
                                        }
                                        message = "📹 " + object.messageOwner.media.caption;
                                        return LocaleController.formatString("NotificationActionPinnedText", R.string.NotificationActionPinnedText, new Object[]{name, message, chat.title});
                                    } else if (object.isGif()) {
                                        if (VERSION.SDK_INT < 19 || TextUtils.isEmpty(object.messageOwner.media.caption)) {
                                            return LocaleController.formatString("NotificationActionPinnedGif", R.string.NotificationActionPinnedGif, new Object[]{name, chat.title});
                                        }
                                        message = "🎬 " + object.messageOwner.media.caption;
                                        return LocaleController.formatString("NotificationActionPinnedText", R.string.NotificationActionPinnedText, new Object[]{name, message, chat.title});
                                    } else if (object.isVoice()) {
                                        return LocaleController.formatString("NotificationActionPinnedVoice", R.string.NotificationActionPinnedVoice, new Object[]{name, chat.title});
                                    } else if (object.isRoundVideo()) {
                                        return LocaleController.formatString("NotificationActionPinnedRound", R.string.NotificationActionPinnedRound, new Object[]{name, chat.title});
                                    } else if (object.isSticker()) {
                                        if (object.getStickerEmoji() != null) {
                                            return LocaleController.formatString("NotificationActionPinnedStickerEmoji", R.string.NotificationActionPinnedStickerEmoji, new Object[]{name, chat.title, object.getStickerEmoji()});
                                        }
                                        return LocaleController.formatString("NotificationActionPinnedSticker", R.string.NotificationActionPinnedSticker, new Object[]{name, chat.title});
                                    } else if (object.messageOwner.media instanceof TLRPC$TL_messageMediaDocument) {
                                        if (VERSION.SDK_INT < 19 || TextUtils.isEmpty(object.messageOwner.media.caption)) {
                                            return LocaleController.formatString("NotificationActionPinnedFile", R.string.NotificationActionPinnedFile, new Object[]{name, chat.title});
                                        }
                                        message = "📎 " + object.messageOwner.media.caption;
                                        return LocaleController.formatString("NotificationActionPinnedText", R.string.NotificationActionPinnedText, new Object[]{name, message, chat.title});
                                    } else if (object.messageOwner.media instanceof TLRPC$TL_messageMediaGeo) {
                                        return LocaleController.formatString("NotificationActionPinnedGeo", R.string.NotificationActionPinnedGeo, new Object[]{name, chat.title});
                                    } else if (object.messageOwner.media instanceof TLRPC$TL_messageMediaGeoLive) {
                                        return LocaleController.formatString("NotificationActionPinnedGeoLive", R.string.NotificationActionPinnedGeoLive, new Object[]{name, chat.title});
                                    } else if (object.messageOwner.media instanceof TLRPC$TL_messageMediaContact) {
                                        return LocaleController.formatString("NotificationActionPinnedContact", R.string.NotificationActionPinnedContact, new Object[]{name, chat.title});
                                    } else if (object.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) {
                                        if (VERSION.SDK_INT < 19 || TextUtils.isEmpty(object.messageOwner.media.caption)) {
                                            return LocaleController.formatString("NotificationActionPinnedPhoto", R.string.NotificationActionPinnedPhoto, new Object[]{name, chat.title});
                                        }
                                        message = "🖼 " + object.messageOwner.media.caption;
                                        return LocaleController.formatString("NotificationActionPinnedText", R.string.NotificationActionPinnedText, new Object[]{name, message, chat.title});
                                    } else if (object.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                                        return LocaleController.formatString("NotificationActionPinnedGame", R.string.NotificationActionPinnedGame, new Object[]{name, chat.title});
                                    } else if (object.messageText == null || object.messageText.length() <= 0) {
                                        return LocaleController.formatString("NotificationActionPinnedNoText", R.string.NotificationActionPinnedNoText, new Object[]{name, chat.title});
                                    } else {
                                        message2 = object.messageText;
                                        if (message2.length() > 20) {
                                            message2 = message2.subSequence(0, 20) + "...";
                                        }
                                        return LocaleController.formatString("NotificationActionPinnedText", R.string.NotificationActionPinnedText, new Object[]{name, message2, chat.title});
                                    }
                                }
                            } else if (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionGameScore) {
                                return messageObject.messageText.toString();
                            } else {
                                return null;
                            }
                        }
                    }
                } else if (!ChatObject.isChannel(chat) || chat.megagroup) {
                    if (messageObject.isMediaEmpty()) {
                        if (shortMessage || messageObject.messageOwner.message == null || messageObject.messageOwner.message.length() == 0) {
                            return LocaleController.formatString("NotificationMessageGroupNoText", R.string.NotificationMessageGroupNoText, new Object[]{name, chat.title});
                        }
                        return LocaleController.formatString("NotificationMessageGroupText", R.string.NotificationMessageGroupText, new Object[]{name, chat.title, messageObject.messageOwner.message});
                    } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) {
                        if (shortMessage || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                            return LocaleController.formatString("NotificationMessageGroupPhoto", R.string.NotificationMessageGroupPhoto, new Object[]{name, chat.title});
                        }
                        return LocaleController.formatString("NotificationMessageGroupText", R.string.NotificationMessageGroupText, new Object[]{name, chat.title, "🖼 " + messageObject.messageOwner.media.caption});
                    } else if (messageObject.isVideo()) {
                        if (shortMessage || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                            return LocaleController.formatString("NotificationMessageGroupVideo", R.string.NotificationMessageGroupVideo, new Object[]{name, chat.title});
                        }
                        return LocaleController.formatString("NotificationMessageGroupText", R.string.NotificationMessageGroupText, new Object[]{name, chat.title, "📹 " + messageObject.messageOwner.media.caption});
                    } else if (messageObject.isVoice()) {
                        return LocaleController.formatString("NotificationMessageGroupAudio", R.string.NotificationMessageGroupAudio, new Object[]{name, chat.title});
                    } else if (messageObject.isRoundVideo()) {
                        return LocaleController.formatString("NotificationMessageGroupRound", R.string.NotificationMessageGroupRound, new Object[]{name, chat.title});
                    } else if (messageObject.isMusic()) {
                        return LocaleController.formatString("NotificationMessageGroupMusic", R.string.NotificationMessageGroupMusic, new Object[]{name, chat.title});
                    } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaContact) {
                        return LocaleController.formatString("NotificationMessageGroupContact", R.string.NotificationMessageGroupContact, new Object[]{name, chat.title});
                    } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                        return LocaleController.formatString("NotificationMessageGroupGame", R.string.NotificationMessageGroupGame, new Object[]{name, chat.title, messageObject.messageOwner.media.game.title});
                    } else if ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeo) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaVenue)) {
                        return LocaleController.formatString("NotificationMessageGroupMap", R.string.NotificationMessageGroupMap, new Object[]{name, chat.title});
                    } else if (!(messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaDocument)) {
                        return null;
                    } else {
                        if (messageObject.isSticker()) {
                            if (messageObject.getStickerEmoji() != null) {
                                return LocaleController.formatString("NotificationMessageGroupStickerEmoji", R.string.NotificationMessageGroupStickerEmoji, new Object[]{name, chat.title, messageObject.getStickerEmoji()});
                            }
                            return LocaleController.formatString("NotificationMessageGroupSticker", R.string.NotificationMessageGroupSticker, new Object[]{name, chat.title});
                        } else if (messageObject.isGif()) {
                            if (shortMessage || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                                return LocaleController.formatString("NotificationMessageGroupGif", R.string.NotificationMessageGroupGif, new Object[]{name, chat.title});
                            }
                            return LocaleController.formatString("NotificationMessageGroupText", R.string.NotificationMessageGroupText, new Object[]{name, chat.title, "🎬 " + messageObject.messageOwner.media.caption});
                        } else if (shortMessage || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                            return LocaleController.formatString("NotificationMessageGroupDocument", R.string.NotificationMessageGroupDocument, new Object[]{name, chat.title});
                        } else {
                            return LocaleController.formatString("NotificationMessageGroupText", R.string.NotificationMessageGroupText, new Object[]{name, chat.title, "📎 " + messageObject.messageOwner.media.caption});
                        }
                    }
                } else if (messageObject.isMediaEmpty()) {
                    if (shortMessage || messageObject.messageOwner.message == null || messageObject.messageOwner.message.length() == 0) {
                        return LocaleController.formatString("ChannelMessageNoText", R.string.ChannelMessageNoText, new Object[]{name});
                    }
                    msg = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, new Object[]{name, messageObject.messageOwner.message});
                    text[0] = true;
                    return msg;
                } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) {
                    if (shortMessage || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                        return LocaleController.formatString("ChannelMessagePhoto", R.string.ChannelMessagePhoto, new Object[]{name});
                    }
                    msg = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, new Object[]{name, "🖼 " + messageObject.messageOwner.media.caption});
                    text[0] = true;
                    return msg;
                } else if (messageObject.isVideo()) {
                    if (shortMessage || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                        return LocaleController.formatString("ChannelMessageVideo", R.string.ChannelMessageVideo, new Object[]{name});
                    }
                    msg = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, new Object[]{name, "📹 " + messageObject.messageOwner.media.caption});
                    text[0] = true;
                    return msg;
                } else if (messageObject.isVoice()) {
                    return LocaleController.formatString("ChannelMessageAudio", R.string.ChannelMessageAudio, new Object[]{name});
                } else if (messageObject.isRoundVideo()) {
                    return LocaleController.formatString("ChannelMessageRound", R.string.ChannelMessageRound, new Object[]{name});
                } else if (messageObject.isMusic()) {
                    return LocaleController.formatString("ChannelMessageMusic", R.string.ChannelMessageMusic, new Object[]{name});
                } else if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaContact) {
                    return LocaleController.formatString("ChannelMessageContact", R.string.ChannelMessageContact, new Object[]{name});
                } else if ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeo) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaVenue)) {
                    return LocaleController.formatString("ChannelMessageMap", R.string.ChannelMessageMap, new Object[]{name});
                } else if (!(messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaDocument)) {
                    return null;
                } else {
                    if (messageObject.isSticker()) {
                        if (messageObject.getStickerEmoji() != null) {
                            return LocaleController.formatString("ChannelMessageStickerEmoji", R.string.ChannelMessageStickerEmoji, new Object[]{name, messageObject.getStickerEmoji()});
                        }
                        return LocaleController.formatString("ChannelMessageSticker", R.string.ChannelMessageSticker, new Object[]{name});
                    } else if (messageObject.isGif()) {
                        if (shortMessage || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                            return LocaleController.formatString("ChannelMessageGIF", R.string.ChannelMessageGIF, new Object[]{name});
                        }
                        msg = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, new Object[]{name, "🎬 " + messageObject.messageOwner.media.caption});
                        text[0] = true;
                        return msg;
                    } else if (shortMessage || VERSION.SDK_INT < 19 || TextUtils.isEmpty(messageObject.messageOwner.media.caption)) {
                        return LocaleController.formatString("ChannelMessageDocument", R.string.ChannelMessageDocument, new Object[]{name});
                    } else {
                        msg = LocaleController.formatString("NotificationMessageText", R.string.NotificationMessageText, new Object[]{name, "📎 " + messageObject.messageOwner.media.caption});
                        text[0] = true;
                        return msg;
                    }
                }
            } else if (!ChatObject.isChannel(chat) || chat.megagroup) {
                return LocaleController.formatString("NotificationMessageGroupNoText", R.string.NotificationMessageGroupNoText, new Object[]{name, chat.title});
            } else {
                return LocaleController.formatString("ChannelMessageNoText", R.string.ChannelMessageNoText, new Object[]{name, chat.title});
            }
        }
    }

    private void scheduleNotificationRepeat() {
        try {
            PendingIntent pintent = PendingIntent.getService(ApplicationLoader.applicationContext, 0, new Intent(ApplicationLoader.applicationContext, NotificationRepeat.class), 0);
            int minutes = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getInt("repeat_messages", 60);
            if (minutes <= 0 || this.personal_count <= 0) {
                this.alarmManager.cancel(pintent);
            } else {
                this.alarmManager.set(2, SystemClock.elapsedRealtime() + ((long) ((minutes * 60) * 1000)), pintent);
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    private boolean isPersonalMessage(MessageObject messageObject) {
        return messageObject.messageOwner.to_id != null && messageObject.messageOwner.to_id.chat_id == 0 && messageObject.messageOwner.to_id.channel_id == 0 && (messageObject.messageOwner.action == null || (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionEmpty));
    }

    private int getNotifyOverride(SharedPreferences preferences, long dialog_id) {
        int notifyOverride = preferences.getInt("notify2_" + dialog_id, 0);
        if (notifyOverride != 3 || preferences.getInt("notifyuntil_" + dialog_id, 0) < ConnectionsManager.getInstance().getCurrentTime()) {
            return notifyOverride;
        }
        return 2;
    }

    private void dismissNotification() {
        try {
            this.notificationManager.cancel(1);
            this.pushMessages.clear();
            this.pushMessagesDict.clear();
            for (Entry<Long, Integer> entry : this.wearNotificationsIds.entrySet()) {
                this.notificationManager.cancel(((Integer) entry.getValue()).intValue());
            }
            this.wearNotificationsIds.clear();
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.pushMessagesUpdated, new Object[0]);
                }
            });
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    private void playInChatSound() {
        if (this.inChatSoundEnabled && !MediaController.getInstance().isRecordingAudio()) {
            try {
                if (this.audioManager.getRingerMode() == 0) {
                    return;
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            try {
                if (getNotifyOverride(ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0), this.opened_dialog_id) != 2) {
                    this.notificationsQueue.postRunnable(new Runnable() {

                        /* renamed from: org.telegram.messenger.NotificationsController$15$1 */
                        class C15841 implements OnLoadCompleteListener {
                            C15841() {
                            }

                            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                                if (status == 0) {
                                    try {
                                        soundPool.play(sampleId, 1.0f, 1.0f, 1, 0, 1.0f);
                                    } catch (Exception e) {
                                        FileLog.e(e);
                                    }
                                }
                            }
                        }

                        public void run() {
                            if (Math.abs(System.currentTimeMillis() - NotificationsController.this.lastSoundPlay) > 500) {
                                try {
                                    if (NotificationsController.this.soundPool == null) {
                                        NotificationsController.this.soundPool = new SoundPool(3, 1, 0);
                                        NotificationsController.this.soundPool.setOnLoadCompleteListener(new C15841());
                                    }
                                    if (NotificationsController.this.soundIn == 0 && !NotificationsController.this.soundInLoaded) {
                                        NotificationsController.this.soundInLoaded = true;
                                        NotificationsController.this.soundIn = NotificationsController.this.soundPool.load(ApplicationLoader.applicationContext, R.raw.sound_in, 1);
                                    }
                                    if (NotificationsController.this.soundIn != 0) {
                                        try {
                                            NotificationsController.this.soundPool.play(NotificationsController.this.soundIn, 1.0f, 1.0f, 1, 0, 1.0f);
                                        } catch (Exception e) {
                                            FileLog.e(e);
                                        }
                                    }
                                } catch (Exception e2) {
                                    FileLog.e(e2);
                                }
                            }
                        }
                    });
                }
            } catch (Exception e2) {
                FileLog.e(e2);
            }
        }
    }

    private void scheduleNotificationDelay(boolean onlineReason) {
        try {
            FileLog.e("delay notification start, onlineReason = " + onlineReason);
            this.notificationDelayWakelock.acquire(10000);
            AndroidUtilities.cancelRunOnUIThread(this.notificationDelayRunnable);
            AndroidUtilities.runOnUIThread(this.notificationDelayRunnable, (long) (onlineReason ? 3000 : 1000));
        } catch (Exception e) {
            FileLog.e(e);
            showOrUpdateNotification(this.notifyCheck);
        }
    }

    protected void repeatNotificationMaybe() {
        this.notificationsQueue.postRunnable(new Runnable() {
            public void run() {
                int hour = Calendar.getInstance().get(11);
                if (hour < 11 || hour > 22) {
                    NotificationsController.this.scheduleNotificationRepeat();
                    return;
                }
                NotificationsController.this.notificationManager.cancel(1);
                NotificationsController.this.showOrUpdateNotification(true);
            }
        });
    }

    private void showOrUpdateNotification(boolean notifyAboutLast) {
        if (!UserConfig.isClientActivated() || this.pushMessages.isEmpty()) {
            dismissNotification();
            return;
        }
        try {
            ConnectionsManager.getInstance().resumeNetworkMaybe();
            MessageObject lastMessageObject = (MessageObject) this.pushMessages.get(0);
            SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
            int dismissDate = preferences.getInt("dismissDate", 0);
            if (lastMessageObject.messageOwner.date <= dismissDate) {
                dismissNotification();
                return;
            }
            int count;
            String name;
            String detailText;
            long dialog_id = lastMessageObject.getDialogId();
            long override_dialog_id = dialog_id;
            if (lastMessageObject.messageOwner.mentioned) {
                override_dialog_id = (long) lastMessageObject.messageOwner.from_id;
            }
            int mid = lastMessageObject.getId();
            int chat_id = lastMessageObject.messageOwner.to_id.chat_id != 0 ? lastMessageObject.messageOwner.to_id.chat_id : lastMessageObject.messageOwner.to_id.channel_id;
            int user_id = lastMessageObject.messageOwner.to_id.user_id;
            if (user_id == 0) {
                user_id = lastMessageObject.messageOwner.from_id;
            } else if (user_id == UserConfig.getClientUserId()) {
                user_id = lastMessageObject.messageOwner.from_id;
            }
            User user = MessagesController.getInstance().getUser(Integer.valueOf(user_id));
            TLRPC$Chat chat = null;
            if (chat_id != 0) {
                chat = MessagesController.getInstance().getChat(Integer.valueOf(chat_id));
            }
            if (user != null) {
                if (AppPreferences.isHiddenChat(ApplicationLoader.applicationContext, (long) user.id) && !AppPreferences.isShowHiddenDialogs(ApplicationLoader.applicationContext)) {
                    return;
                }
            }
            if (chat != null) {
                if (AppPreferences.isHiddenChat(ApplicationLoader.applicationContext, (long) chat.id) && !AppPreferences.isShowHiddenDialogs(ApplicationLoader.applicationContext)) {
                    return;
                }
            }
            TLObject photoPath = null;
            boolean notifyDisabled = false;
            int needVibrate = 0;
            String choosenSoundPath = null;
            int ledColor = -16776961;
            boolean inAppPreview = false;
            int priority = 0;
            int notifyOverride = getNotifyOverride(preferences, override_dialog_id);
            if (!notifyAboutLast || notifyOverride == 2 || (!(preferences.getBoolean("EnableAll", true) && (chat_id == 0 || preferences.getBoolean("EnableGroup", true))) && notifyOverride == 0)) {
                notifyDisabled = true;
            }
            if (!(notifyDisabled || dialog_id != override_dialog_id || chat == null)) {
                int notifyMaxCount;
                int notifyDelay;
                if (preferences.getBoolean("custom_" + dialog_id, false)) {
                    notifyMaxCount = preferences.getInt("smart_max_count_" + dialog_id, 2);
                    notifyDelay = preferences.getInt("smart_delay_" + dialog_id, 180);
                } else {
                    notifyMaxCount = 2;
                    notifyDelay = 180;
                }
                if (notifyMaxCount != 0) {
                    Point dialogInfo = (Point) this.smartNotificationsDialogs.get(Long.valueOf(dialog_id));
                    if (dialogInfo == null) {
                        this.smartNotificationsDialogs.put(Long.valueOf(dialog_id), new Point(1, (int) (System.currentTimeMillis() / 1000)));
                    } else if (((long) (dialogInfo.y + notifyDelay)) < System.currentTimeMillis() / 1000) {
                        dialogInfo.set(1, (int) (System.currentTimeMillis() / 1000));
                    } else {
                        count = dialogInfo.x;
                        if (count < notifyMaxCount) {
                            dialogInfo.set(count + 1, (int) (System.currentTimeMillis() / 1000));
                        } else {
                            notifyDisabled = true;
                        }
                    }
                }
            }
            String defaultPath = System.DEFAULT_NOTIFICATION_URI.getPath();
            if (!notifyDisabled) {
                int vibrateOverride;
                int priorityOverride;
                boolean inAppSounds = preferences.getBoolean("EnableInAppSounds", true);
                boolean inAppVibrate = preferences.getBoolean("EnableInAppVibrate", true);
                inAppPreview = preferences.getBoolean("EnableInAppPreview", true);
                boolean inAppPriority = preferences.getBoolean("EnableInAppPriority", false);
                boolean custom = preferences.getBoolean("custom_" + dialog_id, false);
                if (custom) {
                    vibrateOverride = preferences.getInt("vibrate_" + dialog_id, 0);
                    priorityOverride = preferences.getInt("priority_" + dialog_id, 3);
                    choosenSoundPath = preferences.getString("sound_path_" + dialog_id, null);
                } else {
                    vibrateOverride = 0;
                    priorityOverride = 3;
                    choosenSoundPath = null;
                }
                boolean vibrateOnlyIfSilent = false;
                if (chat_id != 0) {
                    if (choosenSoundPath != null && choosenSoundPath.equals(defaultPath)) {
                        choosenSoundPath = null;
                    } else if (choosenSoundPath == null) {
                        choosenSoundPath = preferences.getString("GroupSoundPath", defaultPath);
                    }
                    needVibrate = preferences.getInt("vibrate_group", 0);
                    priority = preferences.getInt("priority_group", 1);
                    ledColor = preferences.getInt("GroupLed", -16776961);
                } else if (user_id != 0) {
                    if (choosenSoundPath != null && choosenSoundPath.equals(defaultPath)) {
                        choosenSoundPath = null;
                    } else if (choosenSoundPath == null) {
                        choosenSoundPath = preferences.getString("GlobalSoundPath", defaultPath);
                    }
                    needVibrate = preferences.getInt("vibrate_messages", 0);
                    priority = preferences.getInt("priority_group", 1);
                    ledColor = preferences.getInt("MessagesLed", -16776961);
                }
                if (custom && preferences.contains("color_" + dialog_id)) {
                    ledColor = preferences.getInt("color_" + dialog_id, 0);
                }
                if (priorityOverride != 3) {
                    priority = priorityOverride;
                }
                if (needVibrate == 4) {
                    vibrateOnlyIfSilent = true;
                    needVibrate = 0;
                }
                if ((needVibrate == 2 && (vibrateOverride == 1 || vibrateOverride == 3)) || ((needVibrate != 2 && vibrateOverride == 2) || !(vibrateOverride == 0 || vibrateOverride == 4))) {
                    needVibrate = vibrateOverride;
                }
                if (!ApplicationLoader.mainInterfacePaused) {
                    if (!inAppSounds) {
                        choosenSoundPath = null;
                    }
                    if (!inAppVibrate) {
                        needVibrate = 2;
                    }
                    if (!inAppPriority) {
                        priority = 0;
                    } else if (priority == 2) {
                        priority = 1;
                    }
                }
                if (vibrateOnlyIfSilent && needVibrate != 2) {
                    try {
                        int mode = this.audioManager.getRingerMode();
                        if (!(mode == 0 || mode == 1)) {
                            needVibrate = 2;
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            }
            Intent intent = new Intent(ApplicationLoader.applicationContext, LaunchActivity.class);
            intent.setAction("com.tmessages.openchat" + Math.random() + Integer.MAX_VALUE);
            intent.setFlags(32768);
            if (((int) dialog_id) != 0) {
                if (this.pushDialogs.size() == 1) {
                    if (chat_id != 0) {
                        intent.putExtra("chatId", chat_id);
                    } else if (user_id != 0) {
                        intent.putExtra("userId", user_id);
                    }
                }
                if (AndroidUtilities.needShowPasscode(false) || UserConfig.isWaitingForPasscodeEnter) {
                    photoPath = null;
                } else if (this.pushDialogs.size() == 1) {
                    if (chat != null) {
                        if (!(chat.photo == null || chat.photo.photo_small == null || chat.photo.photo_small.volume_id == 0 || chat.photo.photo_small.local_id == 0)) {
                            photoPath = chat.photo.photo_small;
                        }
                    } else if (!(user == null || user.photo == null || user.photo.photo_small == null || user.photo.photo_small.volume_id == 0 || user.photo.photo_small.local_id == 0)) {
                        photoPath = user.photo.photo_small;
                    }
                }
            } else if (this.pushDialogs.size() == 1) {
                intent.putExtra("encId", (int) (dialog_id >> 32));
            }
            PendingIntent contentIntent = PendingIntent.getActivity(ApplicationLoader.applicationContext, 0, intent, 1073741824);
            boolean replace = true;
            if (((int) dialog_id) == 0 || this.pushDialogs.size() > 1 || AndroidUtilities.needShowPasscode(false) || UserConfig.isWaitingForPasscodeEnter) {
                name = LocaleController.getString("AppName", R.string.AppName);
                replace = false;
            } else if (chat != null) {
                name = chat.title;
            } else {
                name = UserObject.getUserName(user);
            }
            if (this.pushDialogs.size() == 1) {
                detailText = LocaleController.formatPluralString("NewMessages", this.total_unread_count);
            } else {
                detailText = LocaleController.formatString("NotificationMessagesPeopleDisplayOrder", R.string.NotificationMessagesPeopleDisplayOrder, new Object[]{LocaleController.formatPluralString("NewMessages", this.total_unread_count), LocaleController.formatPluralString("FromChats", this.pushDialogs.size())});
            }
            Builder mBuilder = new Builder(ApplicationLoader.applicationContext).setContentTitle(name).setSmallIcon(R.drawable.notification).setAutoCancel(true).setNumber(this.total_unread_count).setContentIntent(contentIntent).setGroup("messages").setGroupSummary(true).setColor(-13851168);
            mBuilder.setCategory("msg");
            if (chat == null && user != null && user.phone != null && user.phone.length() > 0) {
                mBuilder.addPerson("tel:+" + user.phone);
            }
            int silent = 2;
            String lastMessage = null;
            MessageObject messageObject;
            boolean[] text;
            String message;
            if (this.pushMessages.size() == 1) {
                messageObject = (MessageObject) this.pushMessages.get(0);
                text = new boolean[1];
                lastMessage = getStringForMessage(messageObject, false, text);
                message = lastMessage;
                silent = messageObject.messageOwner.silent ? 1 : 0;
                if (message != null) {
                    if (replace) {
                        if (chat != null) {
                            message = message.replace(" @ " + name, "");
                        } else if (text[0]) {
                            message = message.replace(name + ": ", "");
                        } else {
                            message = message.replace(name + " ", "");
                        }
                    }
                    mBuilder.setContentText(message);
                    mBuilder.setStyle(new BigTextStyle().bigText(message));
                } else {
                    return;
                }
            }
            mBuilder.setContentText(detailText);
            Style inboxStyle = new InboxStyle();
            inboxStyle.setBigContentTitle(name);
            count = Math.min(10, this.pushMessages.size());
            text = new boolean[1];
            for (int i = 0; i < count; i++) {
                messageObject = (MessageObject) this.pushMessages.get(i);
                message = getStringForMessage(messageObject, false, text);
                if (message != null && messageObject.messageOwner.date > dismissDate) {
                    if (silent == 2) {
                        lastMessage = message;
                        silent = messageObject.messageOwner.silent ? 1 : 0;
                    }
                    if (this.pushDialogs.size() == 1 && replace) {
                        message = chat != null ? message.replace(" @ " + name, "") : text[0] ? message.replace(name + ": ", "") : message.replace(name + " ", "");
                    }
                    inboxStyle.addLine(message);
                }
            }
            inboxStyle.setSummaryText(detailText);
            mBuilder.setStyle(inboxStyle);
            intent = new Intent(ApplicationLoader.applicationContext, NotificationDismissReceiver.class);
            intent.putExtra("messageDate", lastMessageObject.messageOwner.date);
            mBuilder.setDeleteIntent(PendingIntent.getBroadcast(ApplicationLoader.applicationContext, 1, intent, 134217728));
            if (photoPath != null) {
                BitmapDrawable img = ImageLoader.getInstance().getImageFromMemory(photoPath, null, "50_50");
                if (img != null) {
                    mBuilder.setLargeIcon(img.getBitmap());
                } else {
                    try {
                        File file = FileLoader.getPathToAttach(photoPath, true);
                        if (file.exists()) {
                            int i2;
                            float scaleFactor = 160.0f / ((float) AndroidUtilities.dp(50.0f));
                            Options options = new Options();
                            if (scaleFactor < 1.0f) {
                                i2 = 1;
                            } else {
                                i2 = (int) scaleFactor;
                            }
                            options.inSampleSize = i2;
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                            if (bitmap != null) {
                                mBuilder.setLargeIcon(bitmap);
                            }
                        }
                    } catch (Throwable th) {
                    }
                }
            }
            if (!notifyAboutLast || silent == 1) {
                mBuilder.setPriority(-1);
            } else if (priority == 0) {
                mBuilder.setPriority(0);
            } else if (priority == 1) {
                mBuilder.setPriority(1);
            } else if (priority == 2) {
                mBuilder.setPriority(2);
            }
            long[] jArr;
            if (silent == 1 || notifyDisabled) {
                jArr = new long[2];
                mBuilder.setVibrate(new long[]{0, 0});
            } else {
                if (ApplicationLoader.mainInterfacePaused || inAppPreview) {
                    if (lastMessage.length() > 100) {
                        lastMessage = lastMessage.substring(0, 100).replace('\n', ' ').trim() + "...";
                    }
                    mBuilder.setTicker(lastMessage);
                }
                if (!(MediaController.getInstance().isRecordingAudio() || choosenSoundPath == null || choosenSoundPath.equals("NoSound"))) {
                    if (choosenSoundPath.equals(defaultPath)) {
                        mBuilder.setSound(System.DEFAULT_NOTIFICATION_URI, 5);
                    } else {
                        mBuilder.setSound(Uri.parse(choosenSoundPath), 5);
                    }
                }
                if (ledColor != 0) {
                    mBuilder.setLights(ledColor, 1000, 1000);
                }
                if (needVibrate == 2 || MediaController.getInstance().isRecordingAudio()) {
                    jArr = new long[2];
                    mBuilder.setVibrate(new long[]{0, 0});
                } else if (needVibrate == 1) {
                    jArr = new long[4];
                    mBuilder.setVibrate(new long[]{0, 100, 0, 100});
                } else if (needVibrate == 0 || needVibrate == 4) {
                    mBuilder.setDefaults(2);
                } else if (needVibrate == 3) {
                    jArr = new long[2];
                    mBuilder.setVibrate(new long[]{0, 1000});
                }
            }
            if (VERSION.SDK_INT < 24 && UserConfig.passcodeHash.length() == 0 && hasMessagesToReply()) {
                intent = new Intent(ApplicationLoader.applicationContext, PopupReplyReceiver.class);
                if (VERSION.SDK_INT <= 19) {
                    mBuilder.addAction(R.drawable.ic_ab_reply2, LocaleController.getString("Reply", R.string.Reply), PendingIntent.getBroadcast(ApplicationLoader.applicationContext, 2, intent, 134217728));
                } else {
                    mBuilder.addAction(R.drawable.ic_ab_reply, LocaleController.getString("Reply", R.string.Reply), PendingIntent.getBroadcast(ApplicationLoader.applicationContext, 2, intent, 134217728));
                }
            }
            showExtraNotifications(mBuilder, notifyAboutLast);
            this.notificationManager.notify(1, mBuilder.build());
            scheduleNotificationRepeat();
        } catch (Exception e2) {
            FileLog.e(e2);
        }
    }

    @SuppressLint({"InlinedApi"})
    private void showExtraNotifications(Builder notificationBuilder, boolean notifyAboutLast) {
        if (VERSION.SDK_INT >= 18) {
            int a;
            MessageObject messageObject;
            long dialog_id;
            ArrayList<Long> sortedDialogs = new ArrayList();
            HashMap<Long, ArrayList<MessageObject>> messagesByDialogs = new HashMap();
            for (a = 0; a < this.pushMessages.size(); a++) {
                messageObject = (MessageObject) this.pushMessages.get(a);
                dialog_id = messageObject.getDialogId();
                if (((int) dialog_id) != 0) {
                    ArrayList<MessageObject> arrayList = (ArrayList) messagesByDialogs.get(Long.valueOf(dialog_id));
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        messagesByDialogs.put(Long.valueOf(dialog_id), arrayList);
                        sortedDialogs.add(0, Long.valueOf(dialog_id));
                    }
                    arrayList.add(messageObject);
                }
            }
            HashMap<Long, Integer> oldIdsWear = new HashMap();
            oldIdsWear.putAll(this.wearNotificationsIds);
            this.wearNotificationsIds.clear();
            for (int b = 0; b < sortedDialogs.size(); b++) {
                dialog_id = ((Long) sortedDialogs.get(b)).longValue();
                ArrayList<MessageObject> messageObjects = (ArrayList) messagesByDialogs.get(Long.valueOf(dialog_id));
                int max_id = ((MessageObject) messageObjects.get(0)).getId();
                int max_date = ((MessageObject) messageObjects.get(0)).messageOwner.date;
                TLRPC$Chat chat = null;
                User user = null;
                TLObject photoPath;
                String name;
                Integer notificationId;
                UnreadConversation.Builder unreadConvBuilder;
                Intent intent;
                Action wearReplyAction;
                PendingIntent replyPendingIntent;
                RemoteInput remoteInputWear;
                String replyToString;
                Integer count;
                Style messagingStyle;
                StringBuilder text;
                boolean[] isText;
                String message;
                PendingIntent contentIntent;
                WearableExtender wearableExtender;
                String dismissalID;
                WearableExtender summaryExtender;
                Builder builder;
                BitmapDrawable img;
                File file;
                float scaleFactor;
                Options options;
                int i;
                Bitmap bitmap;
                if (dialog_id > 0) {
                    user = MessagesController.getInstance().getUser(Integer.valueOf((int) dialog_id));
                    if (user == null) {
                    }
                    photoPath = null;
                    if (!AndroidUtilities.needShowPasscode(false) || UserConfig.isWaitingForPasscodeEnter) {
                        name = LocaleController.getString("AppName", R.string.AppName);
                    } else {
                        if (chat != null) {
                            name = chat.title;
                        } else {
                            name = UserObject.getUserName(user);
                        }
                        if (chat != null) {
                            if (!(chat.photo == null || chat.photo.photo_small == null || chat.photo.photo_small.volume_id == 0 || chat.photo.photo_small.local_id == 0)) {
                                photoPath = chat.photo.photo_small;
                            }
                        } else if (!(user.photo == null || user.photo.photo_small == null || user.photo.photo_small.volume_id == 0 || user.photo.photo_small.local_id == 0)) {
                            photoPath = user.photo.photo_small;
                        }
                    }
                    notificationId = (Integer) oldIdsWear.get(Long.valueOf(dialog_id));
                    if (notificationId != null) {
                        notificationId = Integer.valueOf((int) dialog_id);
                    } else {
                        oldIdsWear.remove(Long.valueOf(dialog_id));
                    }
                    unreadConvBuilder = new UnreadConversation.Builder(name).setLatestTimestamp(((long) max_date) * 1000);
                    intent = new Intent(ApplicationLoader.applicationContext, AutoMessageHeardReceiver.class);
                    intent.addFlags(32);
                    intent.setAction("org.telegram.messenger.ACTION_MESSAGE_HEARD");
                    intent.putExtra("dialog_id", dialog_id);
                    intent.putExtra("max_id", max_id);
                    unreadConvBuilder.setReadPendingIntent(PendingIntent.getBroadcast(ApplicationLoader.applicationContext, notificationId.intValue(), intent, 134217728));
                    wearReplyAction = null;
                    if (!((ChatObject.isChannel(chat) && (chat == null || !chat.megagroup)) || AndroidUtilities.needShowPasscode(false) || UserConfig.isWaitingForPasscodeEnter)) {
                        intent = new Intent(ApplicationLoader.applicationContext, AutoMessageReplyReceiver.class);
                        intent.addFlags(32);
                        intent.setAction("org.telegram.messenger.ACTION_MESSAGE_REPLY");
                        intent.putExtra("dialog_id", dialog_id);
                        intent.putExtra("max_id", max_id);
                        unreadConvBuilder.setReplyAction(PendingIntent.getBroadcast(ApplicationLoader.applicationContext, notificationId.intValue(), intent, 134217728), new RemoteInput.Builder(EXTRA_VOICE_REPLY).setLabel(LocaleController.getString("Reply", R.string.Reply)).build());
                        intent = new Intent(ApplicationLoader.applicationContext, WearReplyReceiver.class);
                        intent.putExtra("dialog_id", dialog_id);
                        intent.putExtra("max_id", max_id);
                        replyPendingIntent = PendingIntent.getBroadcast(ApplicationLoader.applicationContext, notificationId.intValue(), intent, 134217728);
                        remoteInputWear = new RemoteInput.Builder(EXTRA_VOICE_REPLY).setLabel(LocaleController.getString("Reply", R.string.Reply)).build();
                        if (chat == null) {
                            replyToString = LocaleController.formatString("ReplyToGroup", R.string.ReplyToGroup, new Object[]{name});
                        } else {
                            replyToString = LocaleController.formatString("ReplyToUser", R.string.ReplyToUser, new Object[]{name});
                        }
                        wearReplyAction = new Action.Builder(R.drawable.ic_reply_icon, replyToString, replyPendingIntent).setAllowGeneratedReplies(true).addRemoteInput(remoteInputWear).build();
                    }
                    count = (Integer) this.pushDialogs.get(Long.valueOf(dialog_id));
                    if (count == null) {
                        count = Integer.valueOf(0);
                    }
                    messagingStyle = new MessagingStyle(null).setConversationTitle(String.format("%1$s (%2$s)", new Object[]{name, LocaleController.formatPluralString("NewMessages", Math.max(count.intValue(), messageObjects.size()))}));
                    text = new StringBuilder();
                    isText = new boolean[1];
                    for (a = messageObjects.size() - 1; a >= 0; a--) {
                        messageObject = (MessageObject) messageObjects.get(a);
                        message = getStringForMessage(messageObject, false, isText);
                        if (message != null) {
                            if (chat != null) {
                                message = message.replace(" @ " + name, "");
                            } else if (isText[0]) {
                                message = message.replace(name + " ", "");
                            } else {
                                message = message.replace(name + ": ", "");
                            }
                            if (text.length() > 0) {
                                text.append("\n\n");
                            }
                            text.append(message);
                            unreadConvBuilder.addMessage(message);
                            messagingStyle.addMessage(message, ((long) messageObject.messageOwner.date) * 1000, null);
                        }
                    }
                    intent = new Intent(ApplicationLoader.applicationContext, LaunchActivity.class);
                    intent.setAction("com.tmessages.openchat" + Math.random() + Integer.MAX_VALUE);
                    intent.setFlags(32768);
                    if (chat != null) {
                        intent.putExtra("chatId", chat.id);
                    } else if (user != null) {
                        intent.putExtra("userId", user.id);
                    }
                    contentIntent = PendingIntent.getActivity(ApplicationLoader.applicationContext, 0, intent, 1073741824);
                    wearableExtender = new WearableExtender();
                    if (wearReplyAction != null) {
                        wearableExtender.addAction(wearReplyAction);
                    }
                    dismissalID = null;
                    if (chat != null) {
                        dismissalID = "tgchat" + chat.id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + max_id;
                    } else if (user != null) {
                        dismissalID = "tguser" + user.id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + max_id;
                    }
                    wearableExtender.setDismissalId(dismissalID);
                    summaryExtender = new WearableExtender();
                    summaryExtender.setDismissalId("summary_" + dismissalID);
                    notificationBuilder.extend(summaryExtender);
                    builder = new Builder(ApplicationLoader.applicationContext).setContentTitle(name).setSmallIcon(R.drawable.notification).setGroup("messages").setContentText(text.toString()).setAutoCancel(true).setNumber(messageObjects.size()).setColor(-13851168).setGroupSummary(false).setWhen(((long) ((MessageObject) messageObjects.get(0)).messageOwner.date) * 1000).setStyle(messagingStyle).setContentIntent(contentIntent).extend(wearableExtender).extend(new CarExtender().setUnreadConversation(unreadConvBuilder.build())).setCategory("msg");
                    if (photoPath != null) {
                        img = ImageLoader.getInstance().getImageFromMemory(photoPath, null, "50_50");
                        if (img == null) {
                            builder.setLargeIcon(img.getBitmap());
                        } else {
                            try {
                                file = FileLoader.getPathToAttach(photoPath, true);
                                if (file.exists()) {
                                    scaleFactor = 160.0f / ((float) AndroidUtilities.dp(50.0f));
                                    options = new Options();
                                    if (scaleFactor >= 1.0f) {
                                        i = 1;
                                    } else {
                                        i = (int) scaleFactor;
                                    }
                                    options.inSampleSize = i;
                                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                                    if (bitmap != null) {
                                        builder.setLargeIcon(bitmap);
                                    }
                                }
                            } catch (Throwable th) {
                            }
                        }
                    }
                    if (chat == null && user != null && user.phone != null && user.phone.length() > 0) {
                        builder.addPerson("tel:+" + user.phone);
                    }
                    this.notificationManager.notify(notificationId.intValue(), builder.build());
                    this.wearNotificationsIds.put(Long.valueOf(dialog_id), notificationId);
                } else {
                    chat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) dialog_id)));
                    if (chat == null) {
                    }
                    photoPath = null;
                    if (AndroidUtilities.needShowPasscode(false)) {
                    }
                    name = LocaleController.getString("AppName", R.string.AppName);
                    notificationId = (Integer) oldIdsWear.get(Long.valueOf(dialog_id));
                    if (notificationId != null) {
                        oldIdsWear.remove(Long.valueOf(dialog_id));
                    } else {
                        notificationId = Integer.valueOf((int) dialog_id);
                    }
                    unreadConvBuilder = new UnreadConversation.Builder(name).setLatestTimestamp(((long) max_date) * 1000);
                    intent = new Intent(ApplicationLoader.applicationContext, AutoMessageHeardReceiver.class);
                    intent.addFlags(32);
                    intent.setAction("org.telegram.messenger.ACTION_MESSAGE_HEARD");
                    intent.putExtra("dialog_id", dialog_id);
                    intent.putExtra("max_id", max_id);
                    unreadConvBuilder.setReadPendingIntent(PendingIntent.getBroadcast(ApplicationLoader.applicationContext, notificationId.intValue(), intent, 134217728));
                    wearReplyAction = null;
                    intent = new Intent(ApplicationLoader.applicationContext, AutoMessageReplyReceiver.class);
                    intent.addFlags(32);
                    intent.setAction("org.telegram.messenger.ACTION_MESSAGE_REPLY");
                    intent.putExtra("dialog_id", dialog_id);
                    intent.putExtra("max_id", max_id);
                    unreadConvBuilder.setReplyAction(PendingIntent.getBroadcast(ApplicationLoader.applicationContext, notificationId.intValue(), intent, 134217728), new RemoteInput.Builder(EXTRA_VOICE_REPLY).setLabel(LocaleController.getString("Reply", R.string.Reply)).build());
                    intent = new Intent(ApplicationLoader.applicationContext, WearReplyReceiver.class);
                    intent.putExtra("dialog_id", dialog_id);
                    intent.putExtra("max_id", max_id);
                    replyPendingIntent = PendingIntent.getBroadcast(ApplicationLoader.applicationContext, notificationId.intValue(), intent, 134217728);
                    remoteInputWear = new RemoteInput.Builder(EXTRA_VOICE_REPLY).setLabel(LocaleController.getString("Reply", R.string.Reply)).build();
                    if (chat == null) {
                        replyToString = LocaleController.formatString("ReplyToUser", R.string.ReplyToUser, new Object[]{name});
                    } else {
                        replyToString = LocaleController.formatString("ReplyToGroup", R.string.ReplyToGroup, new Object[]{name});
                    }
                    wearReplyAction = new Action.Builder(R.drawable.ic_reply_icon, replyToString, replyPendingIntent).setAllowGeneratedReplies(true).addRemoteInput(remoteInputWear).build();
                    count = (Integer) this.pushDialogs.get(Long.valueOf(dialog_id));
                    if (count == null) {
                        count = Integer.valueOf(0);
                    }
                    messagingStyle = new MessagingStyle(null).setConversationTitle(String.format("%1$s (%2$s)", new Object[]{name, LocaleController.formatPluralString("NewMessages", Math.max(count.intValue(), messageObjects.size()))}));
                    text = new StringBuilder();
                    isText = new boolean[1];
                    for (a = messageObjects.size() - 1; a >= 0; a--) {
                        messageObject = (MessageObject) messageObjects.get(a);
                        message = getStringForMessage(messageObject, false, isText);
                        if (message != null) {
                            if (chat != null) {
                                message = message.replace(" @ " + name, "");
                            } else if (isText[0]) {
                                message = message.replace(name + " ", "");
                            } else {
                                message = message.replace(name + ": ", "");
                            }
                            if (text.length() > 0) {
                                text.append("\n\n");
                            }
                            text.append(message);
                            unreadConvBuilder.addMessage(message);
                            messagingStyle.addMessage(message, ((long) messageObject.messageOwner.date) * 1000, null);
                        }
                    }
                    intent = new Intent(ApplicationLoader.applicationContext, LaunchActivity.class);
                    intent.setAction("com.tmessages.openchat" + Math.random() + Integer.MAX_VALUE);
                    intent.setFlags(32768);
                    if (chat != null) {
                        intent.putExtra("chatId", chat.id);
                    } else if (user != null) {
                        intent.putExtra("userId", user.id);
                    }
                    contentIntent = PendingIntent.getActivity(ApplicationLoader.applicationContext, 0, intent, 1073741824);
                    wearableExtender = new WearableExtender();
                    if (wearReplyAction != null) {
                        wearableExtender.addAction(wearReplyAction);
                    }
                    dismissalID = null;
                    if (chat != null) {
                        dismissalID = "tgchat" + chat.id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + max_id;
                    } else if (user != null) {
                        dismissalID = "tguser" + user.id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + max_id;
                    }
                    wearableExtender.setDismissalId(dismissalID);
                    summaryExtender = new WearableExtender();
                    summaryExtender.setDismissalId("summary_" + dismissalID);
                    notificationBuilder.extend(summaryExtender);
                    builder = new Builder(ApplicationLoader.applicationContext).setContentTitle(name).setSmallIcon(R.drawable.notification).setGroup("messages").setContentText(text.toString()).setAutoCancel(true).setNumber(messageObjects.size()).setColor(-13851168).setGroupSummary(false).setWhen(((long) ((MessageObject) messageObjects.get(0)).messageOwner.date) * 1000).setStyle(messagingStyle).setContentIntent(contentIntent).extend(wearableExtender).extend(new CarExtender().setUnreadConversation(unreadConvBuilder.build())).setCategory("msg");
                    if (photoPath != null) {
                        img = ImageLoader.getInstance().getImageFromMemory(photoPath, null, "50_50");
                        if (img == null) {
                            file = FileLoader.getPathToAttach(photoPath, true);
                            if (file.exists()) {
                                scaleFactor = 160.0f / ((float) AndroidUtilities.dp(50.0f));
                                options = new Options();
                                if (scaleFactor >= 1.0f) {
                                    i = (int) scaleFactor;
                                } else {
                                    i = 1;
                                }
                                options.inSampleSize = i;
                                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                                if (bitmap != null) {
                                    builder.setLargeIcon(bitmap);
                                }
                            }
                        } else {
                            builder.setLargeIcon(img.getBitmap());
                        }
                    }
                    builder.addPerson("tel:+" + user.phone);
                    this.notificationManager.notify(notificationId.intValue(), builder.build());
                    this.wearNotificationsIds.put(Long.valueOf(dialog_id), notificationId);
                }
            }
            for (Entry<Long, Integer> entry : oldIdsWear.entrySet()) {
                this.notificationManager.cancel(((Integer) entry.getValue()).intValue());
            }
        }
    }

    public void playOutChatSound() {
        if (this.inChatSoundEnabled && !MediaController.getInstance().isRecordingAudio()) {
            try {
                if (this.audioManager.getRingerMode() == 0) {
                    return;
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            this.notificationsQueue.postRunnable(new Runnable() {

                /* renamed from: org.telegram.messenger.NotificationsController$17$1 */
                class C15851 implements OnLoadCompleteListener {
                    C15851() {
                    }

                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        if (status == 0) {
                            try {
                                soundPool.play(sampleId, 1.0f, 1.0f, 1, 0, 1.0f);
                            } catch (Exception e) {
                                FileLog.e(e);
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
                                NotificationsController.this.soundPool.setOnLoadCompleteListener(new C15851());
                            }
                            if (NotificationsController.this.soundOut == 0 && !NotificationsController.this.soundOutLoaded) {
                                NotificationsController.this.soundOutLoaded = true;
                                NotificationsController.this.soundOut = NotificationsController.this.soundPool.load(ApplicationLoader.applicationContext, R.raw.sound_out, 1);
                            }
                            if (NotificationsController.this.soundOut != 0) {
                                try {
                                    NotificationsController.this.soundPool.play(NotificationsController.this.soundOut, 1.0f, 1.0f, 1, 0, 1.0f);
                                } catch (Exception e) {
                                    FileLog.e(e);
                                }
                            }
                        }
                    } catch (Exception e2) {
                        FileLog.e(e2);
                    }
                }
            });
        }
    }

    public static void updateServerNotificationsSettings(long dialog_id) {
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.notificationsSettingsUpdated, new Object[0]);
        if (((int) dialog_id) != 0) {
            SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
            TLRPC$TL_account_updateNotifySettings req = new TLRPC$TL_account_updateNotifySettings();
            req.settings = new TLRPC$TL_inputPeerNotifySettings();
            req.settings.sound = "default";
            int mute_type = preferences.getInt("notify2_" + dialog_id, 0);
            if (mute_type == 3) {
                req.settings.mute_until = preferences.getInt("notifyuntil_" + dialog_id, 0);
            } else {
                req.settings.mute_until = mute_type != 2 ? 0 : Integer.MAX_VALUE;
            }
            req.settings.show_previews = preferences.getBoolean("preview_" + dialog_id, true);
            req.settings.silent = preferences.getBoolean("silent_" + dialog_id, false);
            req.peer = new TLRPC$TL_inputNotifyPeer();
            ((TLRPC$TL_inputNotifyPeer) req.peer).peer = MessagesController.getInputPeer((int) dialog_id);
            ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(TLObject response, TLRPC$TL_error error) {
                }
            });
        }
    }
}
