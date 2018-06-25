package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ba.C0312a;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@TargetApi(9)
public class ap {
    /* renamed from: a */
    private static Method f950a;

    /* renamed from: android.support.v4.app.ap$a */
    public static abstract class C0261a {

        /* renamed from: android.support.v4.app.ap$a$a */
        public interface C0258a {
        }

        /* renamed from: a */
        public abstract int mo209a();

        /* renamed from: b */
        public abstract CharSequence mo210b();

        /* renamed from: c */
        public abstract PendingIntent mo211c();

        /* renamed from: d */
        public abstract Bundle mo212d();

        /* renamed from: e */
        public abstract boolean mo213e();

        /* renamed from: g */
        public abstract C0312a[] mo214g();
    }

    /* renamed from: android.support.v4.app.ap$b */
    public static abstract class C0271b {

        /* renamed from: android.support.v4.app.ap$b$a */
        public interface C0268a {
        }

        /* renamed from: a */
        abstract String[] mo215a();

        /* renamed from: c */
        abstract PendingIntent mo216c();

        /* renamed from: d */
        abstract PendingIntent mo217d();

        /* renamed from: e */
        abstract String[] mo218e();

        /* renamed from: f */
        abstract long mo219f();

        /* renamed from: g */
        abstract C0312a mo220g();
    }

    /* renamed from: a */
    public static Notification m1335a(Notification notification, Context context, CharSequence charSequence, CharSequence charSequence2, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        if (f950a == null) {
            try {
                f950a = Notification.class.getMethod("setLatestEventInfo", new Class[]{Context.class, CharSequence.class, CharSequence.class, PendingIntent.class});
            } catch (Throwable e) {
                Throwable e2;
                throw new RuntimeException(e2);
            }
        }
        try {
            f950a.invoke(notification, new Object[]{context, charSequence, charSequence2, pendingIntent});
            notification.fullScreenIntent = pendingIntent2;
            return notification;
        } catch (IllegalAccessException e3) {
            e2 = e3;
            throw new RuntimeException(e2);
        } catch (InvocationTargetException e4) {
            e2 = e4;
            throw new RuntimeException(e2);
        }
    }
}
