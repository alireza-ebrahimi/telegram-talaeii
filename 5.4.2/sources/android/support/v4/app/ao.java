package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Action;
import android.app.Notification.Builder;
import android.app.Notification.MessagingStyle;
import android.app.Notification.MessagingStyle.Message;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ap.C0261a;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(24)
class ao {

    /* renamed from: android.support.v4.app.ao$a */
    public static class C0290a implements aj, ak {
        /* renamed from: a */
        private Builder f949a;

        public C0290a(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int i2, int i3, boolean z, boolean z2, boolean z3, int i4, CharSequence charSequence4, boolean z4, String str, ArrayList<String> arrayList, Bundle bundle, int i5, int i6, Notification notification2, String str2, boolean z5, String str3, CharSequence[] charSequenceArr, RemoteViews remoteViews2, RemoteViews remoteViews3, RemoteViews remoteViews4) {
            this.f949a = new Builder(context).setWhen(notification.when).setShowWhen(z2).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence4).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(pendingIntent2, (notification.flags & 128) != 0).setLargeIcon(bitmap).setNumber(i).setUsesChronometer(z3).setPriority(i4).setProgress(i2, i3, z).setLocalOnly(z4).setExtras(bundle).setGroup(str2).setGroupSummary(z5).setSortKey(str3).setCategory(str).setColor(i5).setVisibility(i6).setPublicVersion(notification2).setRemoteInputHistory(charSequenceArr);
            if (remoteViews2 != null) {
                this.f949a.setCustomContentView(remoteViews2);
            }
            if (remoteViews3 != null) {
                this.f949a.setCustomBigContentView(remoteViews3);
            }
            if (remoteViews4 != null) {
                this.f949a.setCustomHeadsUpContentView(remoteViews4);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.f949a.addPerson((String) it.next());
            }
        }

        /* renamed from: a */
        public Builder mo227a() {
            return this.f949a;
        }

        /* renamed from: a */
        public void mo228a(C0261a c0261a) {
            Action.Builder builder = new Action.Builder(c0261a.mo209a(), c0261a.mo210b(), c0261a.mo211c());
            if (c0261a.mo214g() != null) {
                for (RemoteInput addRemoteInput : az.m1409a(c0261a.mo214g())) {
                    builder.addRemoteInput(addRemoteInput);
                }
            }
            Bundle bundle = c0261a.mo212d() != null ? new Bundle(c0261a.mo212d()) : new Bundle();
            bundle.putBoolean("android.support.allowGeneratedReplies", c0261a.mo213e());
            builder.addExtras(bundle);
            builder.setAllowGeneratedReplies(c0261a.mo213e());
            this.f949a.addAction(builder.build());
        }

        /* renamed from: b */
        public Notification mo229b() {
            return this.f949a.build();
        }
    }

    /* renamed from: a */
    public static void m1334a(ak akVar, CharSequence charSequence, CharSequence charSequence2, List<CharSequence> list, List<Long> list2, List<CharSequence> list3, List<String> list4, List<Uri> list5) {
        MessagingStyle conversationTitle = new MessagingStyle(charSequence).setConversationTitle(charSequence2);
        for (int i = 0; i < list.size(); i++) {
            Message message = new Message((CharSequence) list.get(i), ((Long) list2.get(i)).longValue(), (CharSequence) list3.get(i));
            if (list4.get(i) != null) {
                message.setData((String) list4.get(i), (Uri) list5.get(i));
            }
            conversationTitle.addMessage(message);
        }
        conversationTitle.setBuilder(akVar.mo227a());
    }
}
