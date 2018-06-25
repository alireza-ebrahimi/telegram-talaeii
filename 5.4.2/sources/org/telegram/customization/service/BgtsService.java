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
import org.telegram.customization.Model.StickerModel;
import org.telegram.customization.dynamicadapter.data.SLSChannel;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.ui.ChatActivity;
import utils.C3792d;
import utils.p178a.C3791b;

public class BgtsService extends C2827a implements C2497d, NotificationCenterDelegate {
    /* renamed from: a */
    volatile int f9296a = 0;
    /* renamed from: b */
    volatile ArrayList<SLSChannel> f9297b = new ArrayList();
    /* renamed from: c */
    volatile ArrayList<Long> f9298c = new ArrayList();

    /* renamed from: a */
    public static Calendar m13169a() {
        Calendar instance = Calendar.getInstance();
        instance.set(11, 11);
        instance.set(12, C3792d.m14088c());
        instance.set(13, 0);
        return instance;
    }

    /* renamed from: b */
    public static void m13170b(Context context) {
        C2827a.m13164a(context, new Intent(context, BgtsService.class), m13169a(), 86400000);
    }

    /* renamed from: b */
    public boolean m13171b() {
        return C3791b.m13898D(getApplicationContext()) + C3791b.m13900E(getApplicationContext()) < System.currentTimeMillis();
    }

    public void didReceivedNotification(final int i, Object... objArr) {
        new Thread(this) {
            /* renamed from: b */
            final /* synthetic */ BgtsService f9295b;

            public void run() {
                super.run();
                if (i == NotificationCenter.stickersDidLoaded) {
                    ArrayList stickerSets = StickersQuery.getStickerSets(0);
                    Object arrayList = new ArrayList();
                    if (stickerSets != null && stickerSets.size() > 0) {
                        Iterator it = stickerSets.iterator();
                        while (it.hasNext()) {
                            TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) it.next();
                            StickerModel stickerModel = new StickerModel();
                            stickerModel.setId(tLRPC$TL_messages_stickerSet.set.id);
                            stickerModel.setName(tLRPC$TL_messages_stickerSet.set.short_name);
                            stickerModel.setTitle(tLRPC$TL_messages_stickerSet.set.title);
                            arrayList.add(stickerModel);
                        }
                    }
                    if (arrayList.size() > 0) {
                        String a = new C1768f().m8395a(arrayList);
                        if (this.f9295b.m13171b()) {
                            C2818c.m13087a(this.f9295b.getApplicationContext(), this.f9295b).m13133e(a);
                            return;
                        }
                        return;
                    }
                    this.f9295b.stopSelf();
                }
            }
        }.start();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.stickersDidLoaded);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case 16:
                C3791b.m14007k(getApplicationContext(), System.currentTimeMillis());
                break;
        }
        stopSelf();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        final boolean booleanExtra = intent != null ? intent.getBooleanExtra("EXTRA_IS_FORCE", false) : false;
        if (!C3791b.m13938a((Context) this)) {
            stopSelf();
            return super.onStartCommand(intent, i, i2);
        } else if (booleanExtra || m13171b()) {
            MessagesController.getInstance().loadDialogs(0, ChatActivity.startAllServices, true);
            try {
                NotificationCenter.getInstance().addObserver(this, NotificationCenter.stickersDidLoaded);
            } catch (Exception e) {
                e.printStackTrace();
            }
            StickersQuery.loadStickers(0, true, true);
            new Handler().postDelayed(new Runnable(this) {
                /* renamed from: b */
                final /* synthetic */ BgtsService f9293b;

                /* renamed from: org.telegram.customization.service.BgtsService$1$1 */
                class C28291 implements Runnable {
                    /* renamed from: a */
                    final /* synthetic */ C28301 f9291a;

                    /* renamed from: org.telegram.customization.service.BgtsService$1$1$1 */
                    class C28281 extends Thread {
                        /* renamed from: a */
                        final /* synthetic */ C28291 f9290a;

                        C28281(C28291 c28291) {
                            this.f9290a = c28291;
                        }

                        public void run() {
                            SQLiteCursor b;
                            C2486a e;
                            Exception e2;
                            int i;
                            Iterator it;
                            Long l;
                            long j;
                            Chat chat;
                            BgtsService bgtsService;
                            SLSChannel sLSChannel;
                            String a;
                            super.run();
                            try {
                                b = MessagesStorage.getInstance().getDatabase().m12165b(String.format(Locale.US, "SELECT did FROM dialogs", new Object[0]), new Object[0]);
                                while (b.m12152a()) {
                                    try {
                                        long d = b.m12158d(0);
                                        if (d < 0 && (-d) < 2147483647L) {
                                            this.f9290a.f9291a.f9293b.f9298c.add(Long.valueOf(d));
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
                                it = this.f9290a.f9291a.f9293b.f9298c.iterator();
                                while (it.hasNext()) {
                                    l = (Long) it.next();
                                    j = -l.longValue();
                                    chat = MessagesController.getInstance().getChat(Integer.valueOf((int) j));
                                    bgtsService = this.f9290a.f9291a.f9293b;
                                    bgtsService.f9296a++;
                                    Log.d("LEE", chat.username + TtmlNode.ANONYMOUS_REGION_ID);
                                    sLSChannel = new SLSChannel();
                                    sLSChannel.setChannelId(chat.username);
                                    sLSChannel.setName(chat.title);
                                    sLSChannel.setId(j);
                                    sLSChannel.setUserId(i);
                                    sLSChannel.setAdmin(ChatObject.isCanWriteToChannel(chat.id));
                                    sLSChannel.setInChat(ChatObject.isNotInChat(chat));
                                    sLSChannel.setMute(MessagesController.getInstance().isDialogMuted(l.longValue()));
                                    this.f9290a.f9291a.f9293b.f9297b.add(sLSChannel);
                                }
                                if (this.f9290a.f9291a.f9293b.f9296a == 0) {
                                    this.f9290a.f9291a.f9293b.stopSelf();
                                }
                                synchronized (this) {
                                    a = new C1768f().m8395a(this.f9290a.f9291a.f9293b.f9297b);
                                    C2818c.m13087a(this.f9290a.f9291a.f9293b.getApplicationContext(), this.f9290a.f9291a.f9293b).m13135f(a);
                                }
                            } catch (Exception e6) {
                                Exception exception = e6;
                                b = null;
                                e2 = exception;
                                e2.printStackTrace();
                                b.m12155b();
                                if (UserConfig.getCurrentUser() != null) {
                                }
                                it = this.f9290a.f9291a.f9293b.f9298c.iterator();
                                while (it.hasNext()) {
                                    l = (Long) it.next();
                                    j = -l.longValue();
                                    chat = MessagesController.getInstance().getChat(Integer.valueOf((int) j));
                                    bgtsService = this.f9290a.f9291a.f9293b;
                                    bgtsService.f9296a++;
                                    Log.d("LEE", chat.username + TtmlNode.ANONYMOUS_REGION_ID);
                                    sLSChannel = new SLSChannel();
                                    sLSChannel.setChannelId(chat.username);
                                    sLSChannel.setName(chat.title);
                                    sLSChannel.setId(j);
                                    sLSChannel.setUserId(i);
                                    sLSChannel.setAdmin(ChatObject.isCanWriteToChannel(chat.id));
                                    if (ChatObject.isNotInChat(chat)) {
                                    }
                                    sLSChannel.setInChat(ChatObject.isNotInChat(chat));
                                    sLSChannel.setMute(MessagesController.getInstance().isDialogMuted(l.longValue()));
                                    this.f9290a.f9291a.f9293b.f9297b.add(sLSChannel);
                                }
                                if (this.f9290a.f9291a.f9293b.f9296a == 0) {
                                    this.f9290a.f9291a.f9293b.stopSelf();
                                }
                                synchronized (this) {
                                    a = new C1768f().m8395a(this.f9290a.f9291a.f9293b.f9297b);
                                    C2818c.m13087a(this.f9290a.f9291a.f9293b.getApplicationContext(), this.f9290a.f9291a.f9293b).m13135f(a);
                                }
                            }
                            b.m12155b();
                            try {
                                if (UserConfig.getCurrentUser() != null) {
                                }
                                it = this.f9290a.f9291a.f9293b.f9298c.iterator();
                                while (it.hasNext()) {
                                    l = (Long) it.next();
                                    j = -l.longValue();
                                    chat = MessagesController.getInstance().getChat(Integer.valueOf((int) j));
                                    if (!(chat == null || !ChatObject.isChannel(chat) || chat.megagroup)) {
                                        bgtsService = this.f9290a.f9291a.f9293b;
                                        bgtsService.f9296a++;
                                        Log.d("LEE", chat.username + TtmlNode.ANONYMOUS_REGION_ID);
                                        sLSChannel = new SLSChannel();
                                        sLSChannel.setChannelId(chat.username);
                                        sLSChannel.setName(chat.title);
                                        sLSChannel.setId(j);
                                        sLSChannel.setUserId(i);
                                        sLSChannel.setAdmin(ChatObject.isCanWriteToChannel(chat.id));
                                        if (ChatObject.isNotInChat(chat)) {
                                        }
                                        sLSChannel.setInChat(ChatObject.isNotInChat(chat));
                                        sLSChannel.setMute(MessagesController.getInstance().isDialogMuted(l.longValue()));
                                        this.f9290a.f9291a.f9293b.f9297b.add(sLSChannel);
                                    }
                                }
                            } catch (Exception e7) {
                            }
                            if (this.f9290a.f9291a.f9293b.f9296a == 0) {
                                this.f9290a.f9291a.f9293b.stopSelf();
                            }
                            try {
                                synchronized (this) {
                                    a = new C1768f().m8395a(this.f9290a.f9291a.f9293b.f9297b);
                                    if (booleanExtra || this.f9290a.f9291a.f9293b.m13171b()) {
                                        C2818c.m13087a(this.f9290a.f9291a.f9293b.getApplicationContext(), this.f9290a.f9291a.f9293b).m13135f(a);
                                    }
                                }
                            } catch (Exception e8) {
                            }
                        }
                    }

                    C28291(C28301 c28301) {
                        this.f9291a = c28301;
                    }

                    public void run() {
                        new C28281(this).start();
                    }
                }

                public void run() {
                    MessagesStorage.getInstance().getStorageQueue().postRunnable(new C28291(this));
                }
            }, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
            return 1;
        } else {
            stopSelf();
            return super.onStartCommand(intent, i, i2);
        }
    }
}
