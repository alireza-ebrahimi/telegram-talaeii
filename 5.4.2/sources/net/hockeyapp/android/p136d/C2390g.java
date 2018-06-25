package net.hockeyapp.android.p136d;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.android.gms.common.internal.ImagesContract;
import java.util.ArrayList;
import net.hockeyapp.android.C2368b;
import net.hockeyapp.android.C2376c;
import net.hockeyapp.android.FeedbackActivity;
import net.hockeyapp.android.p135c.C2373d;
import net.hockeyapp.android.p135c.C2374e;
import net.hockeyapp.android.p137e.C2399c;
import net.hockeyapp.android.p137e.C2408i;

/* renamed from: net.hockeyapp.android.d.g */
public class C2390g extends AsyncTask<Void, Void, C2374e> {
    /* renamed from: a */
    private Context f8045a;
    /* renamed from: b */
    private String f8046b;
    /* renamed from: c */
    private Handler f8047c;
    /* renamed from: d */
    private String f8048d;
    /* renamed from: e */
    private String f8049e = null;

    public C2390g(Context context, String str, Handler handler, String str2) {
        this.f8045a = context;
        this.f8046b = str;
        this.f8047c = handler;
        this.f8048d = str2;
    }

    /* renamed from: a */
    private void m11822a(Context context) {
        if (this.f8049e != null) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            int identifier = context.getResources().getIdentifier("ic_menu_refresh", "drawable", "android");
            Class cls = null;
            if (C2368b.m11726a() != null) {
                cls = C2368b.m11726a().m11772a();
            }
            if (cls == null) {
                cls = FeedbackActivity.class;
            }
            Intent intent = new Intent();
            intent.setFlags(805306368);
            intent.setClass(context, cls);
            intent.putExtra(ImagesContract.URL, this.f8049e);
            Notification a = C2408i.m11879a(context, PendingIntent.getActivity(context, 0, intent, 1073741824), "HockeyApp Feedback", "A new answer to your feedback is available.", identifier);
            if (a != null) {
                notificationManager.notify(2, a);
            }
        }
    }

    /* renamed from: a */
    private void m11823a(ArrayList<C2373d> arrayList) {
        C2373d c2373d = (C2373d) arrayList.get(arrayList.size() - 1);
        int c = c2373d.m11754c();
        SharedPreferences sharedPreferences = this.f8045a.getSharedPreferences("net.hockeyapp.android.feedback", 0);
        if (this.f8048d.equals("send")) {
            sharedPreferences.edit().putInt("idLastMessageSend", c).putInt("idLastMessageProcessed", c).apply();
        } else if (this.f8048d.equals("fetch")) {
            int i = sharedPreferences.getInt("idLastMessageSend", -1);
            int i2 = sharedPreferences.getInt("idLastMessageProcessed", -1);
            if (c != i && c != i2) {
                sharedPreferences.edit().putInt("idLastMessageProcessed", c).apply();
                C2376c a = C2368b.m11726a();
                if (!(a != null ? a.m11773a(c2373d) : false)) {
                    m11822a(this.f8045a);
                }
            }
        }
    }

    /* renamed from: a */
    protected C2374e m11824a(Void... voidArr) {
        if (this.f8045a == null || this.f8046b == null) {
            return null;
        }
        C2374e a = C2399c.m11838a().m11839a(this.f8046b);
        if (a == null || a.m11769b() == null) {
            return a;
        }
        ArrayList a2 = a.m11769b().m11731a();
        if (a2 == null || a2.isEmpty()) {
            return a;
        }
        m11823a(a2);
        return a;
    }

    /* renamed from: a */
    protected void m11825a(C2374e c2374e) {
        if (c2374e != null && this.f8047c != null) {
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putSerializable("parse_feedback_response", c2374e);
            message.setData(bundle);
            this.f8047c.sendMessage(message);
        }
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return m11824a((Void[]) objArr);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        m11825a((C2374e) obj);
    }
}
