package org.telegram.customization.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.google.p098a.C1768f;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import org.telegram.customization.dynamicadapter.data.SLSChannel;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.StatsController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC.User;
import utils.C3792d;
import utils.p178a.C3791b;

public class BgsService extends C2827a implements C2497d {
    /* renamed from: a */
    ArrayList<Long> f9287a = new ArrayList();
    /* renamed from: b */
    int f9288b = 0;
    /* renamed from: c */
    ArrayList<SLSChannel> f9289c = new ArrayList();

    /* renamed from: a */
    public static Calendar m13166a() {
        Calendar instance = Calendar.getInstance();
        instance.set(11, 15);
        instance.set(12, C3792d.m14088c());
        instance.set(13, 0);
        return instance;
    }

    /* renamed from: b */
    public static void m13167b(Context context) {
        C2827a.m13164a(context, new Intent(context, BgsService.class), m13166a(), C3791b.m13908L(context));
    }

    /* renamed from: b */
    public boolean m13168b() {
        return C3791b.m13909M(getApplicationContext()) + C3791b.m13908L(getApplicationContext()) < System.currentTimeMillis();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case 18:
                C3791b.m14044t(getApplicationContext(), System.currentTimeMillis());
                break;
        }
        stopSelf();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        boolean z = false;
        if (intent != null) {
            z = intent.getBooleanExtra("EXTRA_IS_FORCE", false);
        }
        if (!C3791b.m13938a((Context) this)) {
            stopSelf();
            return super.onStartCommand(intent, i, i2);
        } else if (z || m13168b()) {
            try {
                new Handler().postDelayed(new Runnable(this) {
                    /* renamed from: b */
                    final /* synthetic */ BgsService f9286b;

                    /* renamed from: org.telegram.customization.service.BgsService$1$1 */
                    class C28251 extends Thread {
                        /* renamed from: a */
                        final /* synthetic */ C28261 f9284a;

                        C28251(C28261 c28261) {
                            this.f9284a = c28261;
                        }

                        public void run() {
                            super.run();
                            int i = UserConfig.getCurrentUser() != null ? UserConfig.getCurrentUser().id : 0;
                            ArrayList arrayList = MessagesController.staticBotArr;
                            if (arrayList != null && arrayList.size() > 0) {
                                Iterator it = arrayList.iterator();
                                while (it.hasNext()) {
                                    TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) it.next();
                                    if (tLRPC$TL_dialog != null) {
                                        User user = MessagesController.getInstance().getUser(Integer.valueOf((int) tLRPC$TL_dialog.id));
                                        BgsService bgsService = this.f9284a.f9286b;
                                        bgsService.f9288b++;
                                        if (user != null) {
                                            Log.d("LEE", user.username + TtmlNode.ANONYMOUS_REGION_ID);
                                            SLSChannel sLSChannel = new SLSChannel();
                                            sLSChannel.setChannelId(user.username);
                                            sLSChannel.setName(TtmlNode.ANONYMOUS_REGION_ID);
                                            sLSChannel.setId((long) user.id);
                                            sLSChannel.setUserId(i);
                                            this.f9284a.f9286b.f9289c.add(sLSChannel);
                                        }
                                    }
                                }
                            }
                            if (this.f9284a.f9286b.f9288b == 0) {
                                this.f9284a.f9286b.stopSelf();
                            }
                            try {
                                synchronized (this) {
                                    String a = new C1768f().m8395a(this.f9284a.f9286b.f9289c);
                                    if (z || (this.f9284a.f9286b.m13168b() && this.f9284a.f9286b.f9289c.size() > 0)) {
                                        C2818c.m13087a(this.f9284a.f9286b.getApplicationContext(), this.f9284a.f9286b).m13139h(a);
                                        C2818c.m13087a(this.f9284a.f9286b.getApplicationContext(), this.f9284a.f9286b).m13141i(new C1768f().m8395a(StatsController.getInstance().getNetworkUsageStatistics()));
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    }

                    public void run() {
                        new C28251(this).start();
                    }
                }, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        } else {
            stopSelf();
            return super.onStartCommand(intent, i, i2);
        }
    }
}
