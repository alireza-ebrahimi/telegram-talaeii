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
import java.util.Locale;
import org.telegram.SQLite.C2486a;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.customization.dynamicadapter.data.SLSChannel;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.ui.ChatActivity;
import utils.C3792d;
import utils.p178a.C3791b;

public class SgsService extends C2827a implements C2497d {
    /* renamed from: a */
    ArrayList<Long> f9318a = new ArrayList();
    /* renamed from: b */
    int f9319b = 0;
    /* renamed from: c */
    ArrayList<SLSChannel> f9320c = new ArrayList();

    /* renamed from: a */
    public static Calendar m13193a() {
        Calendar instance = Calendar.getInstance();
        instance.set(11, 14);
        instance.set(12, C3792d.m14088c());
        instance.set(13, 0);
        return instance;
    }

    /* renamed from: b */
    public static void m13194b(Context context) {
        C2827a.m13164a(context, new Intent(context, SgsService.class), m13193a(), C3791b.m13904H(context));
    }

    /* renamed from: b */
    public boolean m13195b() {
        return C3791b.m13905I(getApplicationContext()) + C3791b.m13904H(getApplicationContext()) < System.currentTimeMillis();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case 17:
                C3791b.m14027p(getApplicationContext(), System.currentTimeMillis());
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
        } else if (z || m13195b()) {
            try {
                MessagesController.getInstance().loadDialogs(0, ChatActivity.startAllServices, true);
                new Handler().postDelayed(new Runnable(this) {
                    /* renamed from: b */
                    final /* synthetic */ SgsService f9317b;

                    /* renamed from: org.telegram.customization.service.SgsService$1$1 */
                    class C28371 implements Runnable {
                        /* renamed from: a */
                        final /* synthetic */ C28381 f9315a;

                        /* renamed from: org.telegram.customization.service.SgsService$1$1$1 */
                        class C28361 extends Thread {
                            /* renamed from: a */
                            final /* synthetic */ C28371 f9314a;

                            C28361(C28371 c28371) {
                                this.f9314a = c28371;
                            }

                            public void run() {
                                SQLiteCursor b;
                                long d;
                                C2486a e;
                                Exception e2;
                                int i;
                                Iterator it;
                                Chat chat;
                                SgsService sgsService;
                                SLSChannel sLSChannel;
                                String a;
                                super.run();
                                try {
                                    b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT did FROM dialogs", new Object[0]), new Object[0]);
                                    while (b.m12152a()) {
                                        try {
                                            d = b.m12158d(0);
                                            if (d < 0 && (-d) < 2147483647L) {
                                                this.f9314a.f9315a.f9317b.f9318a.add(Long.valueOf(d));
                                            }
                                        } catch (C2486a e3) {
                                            e = e3;
                                        } catch (Exception e4) {
                                            e2 = e4;
                                        }
                                    }
                                } catch (C2486a e5) {
                                    C2486a c2486a = e5;
                                    b = null;
                                    e = c2486a;
                                    e.printStackTrace();
                                    b.m12155b();
                                    i = UserConfig.getCurrentUser() != null ? 0 : UserConfig.getCurrentUser().id;
                                    it = this.f9314a.f9315a.f9317b.f9318a.iterator();
                                    while (it.hasNext()) {
                                        d = -((Long) it.next()).longValue();
                                        chat = MessagesController.getInstance().getChat(Integer.valueOf(Integer.parseInt(TtmlNode.ANONYMOUS_REGION_ID + d)));
                                        sgsService = this.f9314a.f9315a.f9317b;
                                        sgsService.f9319b++;
                                        Log.d("LEE", chat.username + TtmlNode.ANONYMOUS_REGION_ID);
                                        sLSChannel = new SLSChannel();
                                        sLSChannel.setChannelId(chat.username);
                                        sLSChannel.setName(chat.title);
                                        sLSChannel.setId(d);
                                        sLSChannel.setUserId(i);
                                        this.f9314a.f9315a.f9317b.f9320c.add(sLSChannel);
                                    }
                                    if (this.f9314a.f9315a.f9317b.f9319b == 0) {
                                        this.f9314a.f9315a.f9317b.stopSelf();
                                    }
                                    synchronized (this) {
                                        a = new C1768f().m8395a(this.f9314a.f9315a.f9317b.f9320c);
                                        C2818c.m13087a(this.f9314a.f9315a.f9317b.getApplicationContext(), this.f9314a.f9315a.f9317b).m13137g(a);
                                    }
                                } catch (Exception e6) {
                                    Exception exception = e6;
                                    b = null;
                                    e2 = exception;
                                    e2.printStackTrace();
                                    b.m12155b();
                                    if (UserConfig.getCurrentUser() != null) {
                                    }
                                    it = this.f9314a.f9315a.f9317b.f9318a.iterator();
                                    while (it.hasNext()) {
                                        d = -((Long) it.next()).longValue();
                                        chat = MessagesController.getInstance().getChat(Integer.valueOf(Integer.parseInt(TtmlNode.ANONYMOUS_REGION_ID + d)));
                                        sgsService = this.f9314a.f9315a.f9317b;
                                        sgsService.f9319b++;
                                        Log.d("LEE", chat.username + TtmlNode.ANONYMOUS_REGION_ID);
                                        sLSChannel = new SLSChannel();
                                        sLSChannel.setChannelId(chat.username);
                                        sLSChannel.setName(chat.title);
                                        sLSChannel.setId(d);
                                        sLSChannel.setUserId(i);
                                        this.f9314a.f9315a.f9317b.f9320c.add(sLSChannel);
                                    }
                                    if (this.f9314a.f9315a.f9317b.f9319b == 0) {
                                        this.f9314a.f9315a.f9317b.stopSelf();
                                    }
                                    synchronized (this) {
                                        a = new C1768f().m8395a(this.f9314a.f9315a.f9317b.f9320c);
                                        C2818c.m13087a(this.f9314a.f9315a.f9317b.getApplicationContext(), this.f9314a.f9315a.f9317b).m13137g(a);
                                    }
                                }
                                b.m12155b();
                                try {
                                    if (UserConfig.getCurrentUser() != null) {
                                    }
                                    it = this.f9314a.f9315a.f9317b.f9318a.iterator();
                                    while (it.hasNext()) {
                                        d = -((Long) it.next()).longValue();
                                        chat = MessagesController.getInstance().getChat(Integer.valueOf(Integer.parseInt(TtmlNode.ANONYMOUS_REGION_ID + d)));
                                        if (chat != null && chat.megagroup) {
                                            sgsService = this.f9314a.f9315a.f9317b;
                                            sgsService.f9319b++;
                                            Log.d("LEE", chat.username + TtmlNode.ANONYMOUS_REGION_ID);
                                            sLSChannel = new SLSChannel();
                                            sLSChannel.setChannelId(chat.username);
                                            sLSChannel.setName(chat.title);
                                            sLSChannel.setId(d);
                                            sLSChannel.setUserId(i);
                                            this.f9314a.f9315a.f9317b.f9320c.add(sLSChannel);
                                        }
                                    }
                                } catch (Exception e62) {
                                    e62.printStackTrace();
                                }
                                if (this.f9314a.f9315a.f9317b.f9319b == 0) {
                                    this.f9314a.f9315a.f9317b.stopSelf();
                                }
                                synchronized (this) {
                                    a = new C1768f().m8395a(this.f9314a.f9315a.f9317b.f9320c);
                                    if (z || (this.f9314a.f9315a.f9317b.m13195b() && this.f9314a.f9315a.f9317b.f9320c.size() > 0)) {
                                        C2818c.m13087a(this.f9314a.f9315a.f9317b.getApplicationContext(), this.f9314a.f9315a.f9317b).m13137g(a);
                                    }
                                }
                            }
                        }

                        C28371(C28381 c28381) {
                            this.f9315a = c28381;
                        }

                        public void run() {
                            new C28361(this).start();
                        }
                    }

                    public void run() {
                        MessagesStorage.getInstance().getStorageQueue().postRunnable(new C28371(this));
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
