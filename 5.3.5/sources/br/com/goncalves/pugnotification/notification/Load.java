package br.com.goncalves.pugnotification.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationCompat.InboxStyle;
import android.text.Spanned;
import br.com.goncalves.pugnotification.interfaces.PendingIntentNotification;
import br.com.goncalves.pugnotification.pendingintent.ClickPendingIntentActivity;
import br.com.goncalves.pugnotification.pendingintent.ClickPendingIntentBroadCast;
import br.com.goncalves.pugnotification.pendingintent.DismissPendingIntentActivity;
import br.com.goncalves.pugnotification.pendingintent.DismissPendingIntentBroadCast;

public class Load {
    private static final String TAG = "Pugnotification.Load";
    private Builder builder = new Builder(PugNotification.mSingleton.mContext);
    private String message;
    private Spanned messageSpanned;
    private int notificationId;
    private int smallIcon;
    private String tag;
    private String title;

    public Load() {
        this.builder.setContentIntent(PendingIntent.getBroadcast(PugNotification.mSingleton.mContext, 0, new Intent(), 134217728));
    }

    public Load identifier(int identifier) {
        if (identifier <= 0) {
            throw new IllegalStateException("Identifier Should Not Be Less Than Or Equal To Zero!");
        }
        this.notificationId = identifier;
        return this;
    }

    public Load tag(@NonNull String tag) {
        this.tag = tag;
        return this;
    }

    public Load title(@StringRes int title) {
        if (title <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        this.title = PugNotification.mSingleton.mContext.getResources().getString(title);
        this.builder.setContentTitle(this.title);
        return this;
    }

    public Load title(String title) {
        if (title == null) {
            throw new IllegalStateException("Title Must Not Be Null!");
        } else if (title.trim().length() == 0) {
            throw new IllegalArgumentException("Title Must Not Be Empty!");
        } else {
            this.title = title;
            this.builder.setContentTitle(this.title);
            return this;
        }
    }

    public Load message(@StringRes int message) {
        if (message <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        this.message = PugNotification.mSingleton.mContext.getResources().getString(message);
        this.builder.setContentText(this.message);
        return this;
    }

    public Load message(@NonNull String message) {
        if (message.trim().length() == 0) {
            throw new IllegalArgumentException("Message Must Not Be Empty!");
        }
        this.message = message;
        this.builder.setContentText(message);
        return this;
    }

    public Load message(@NonNull Spanned messageSpanned) {
        if (messageSpanned.length() == 0) {
            throw new IllegalArgumentException("Message Must Not Be Empty!");
        }
        this.messageSpanned = messageSpanned;
        this.builder.setContentText(messageSpanned);
        return this;
    }

    public Load color(@ColorRes int color) {
        if (color <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        Context context = PugNotification.mSingleton.mContext;
        if (VERSION.SDK_INT >= 23) {
            this.builder.setColor(context.getColor(color));
        } else {
            this.builder.setColor(context.getResources().getColor(color));
        }
        return this;
    }

    public Load ticker(@StringRes int ticker) {
        if (ticker <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        this.builder.setTicker(PugNotification.mSingleton.mContext.getResources().getString(ticker));
        return this;
    }

    public Load ticker(String ticker) {
        if (ticker == null) {
            throw new IllegalStateException("Ticker Must Not Be Null!");
        } else if (ticker.trim().length() == 0) {
            throw new IllegalArgumentException("Ticker Must Not Be Empty!");
        } else {
            this.builder.setTicker(ticker);
            return this;
        }
    }

    public Load when(long when) {
        if (when <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        this.builder.setWhen(when);
        return this;
    }

    public Load bigTextStyle(@StringRes int bigTextStyle) {
        if (bigTextStyle > 0) {
            return bigTextStyle(PugNotification.mSingleton.mContext.getResources().getString(bigTextStyle), null);
        }
        throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
    }

    public Load bigTextStyle(@StringRes int bigTextStyle, @StringRes int summaryText) {
        if (bigTextStyle > 0) {
            return bigTextStyle(PugNotification.mSingleton.mContext.getResources().getString(bigTextStyle), PugNotification.mSingleton.mContext.getResources().getString(summaryText));
        }
        throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
    }

    public Load bigTextStyle(@NonNull String bigTextStyle) {
        if (bigTextStyle.trim().length() != 0) {
            return bigTextStyle(bigTextStyle, null);
        }
        throw new IllegalArgumentException("Big Text Style Must Not Be Empty!");
    }

    public Load bigTextStyle(@NonNull String bigTextStyle, String summaryText) {
        if (bigTextStyle.trim().length() == 0) {
            throw new IllegalArgumentException("Big Text Style Must Not Be Empty!");
        }
        BigTextStyle bigStyle = new BigTextStyle();
        bigStyle.bigText(bigTextStyle);
        if (summaryText != null) {
            bigStyle.setSummaryText(summaryText);
        }
        this.builder.setStyle(bigStyle);
        return this;
    }

    public Load bigTextStyle(@NonNull Spanned bigTextStyle, String summaryText) {
        if (bigTextStyle.length() == 0) {
            throw new IllegalArgumentException("Big Text Style Must Not Be Empty!");
        }
        BigTextStyle bigStyle = new BigTextStyle();
        bigStyle.bigText(bigTextStyle);
        if (summaryText != null) {
            bigStyle.setSummaryText(summaryText);
        }
        this.builder.setStyle(bigStyle);
        return this;
    }

    public Load inboxStyle(@NonNull String[] inboxLines, @NonNull String title, String summary) {
        if (inboxLines.length <= 0) {
            throw new IllegalArgumentException("Inbox Lines Must Have At Least One Text!");
        } else if (title.trim().length() == 0) {
            throw new IllegalArgumentException("Title Must Not Be Empty!");
        } else {
            InboxStyle inboxStyle = new InboxStyle();
            for (String inboxLine : inboxLines) {
                inboxStyle.addLine(inboxLine);
            }
            inboxStyle.setBigContentTitle(title);
            if (summary != null) {
                inboxStyle.setSummaryText(summary);
            }
            this.builder.setStyle(inboxStyle);
            return this;
        }
    }

    public Load autoCancel(boolean autoCancel) {
        this.builder.setAutoCancel(autoCancel);
        return this;
    }

    public Load ongoing(boolean ongoing) {
        this.builder.setOngoing(ongoing);
        return this;
    }

    public Load smallIcon(@DrawableRes int smallIcon) {
        this.smallIcon = smallIcon;
        this.builder.setSmallIcon(smallIcon);
        return this;
    }

    public Load largeIcon(@NonNull Bitmap bitmap) {
        this.builder.setLargeIcon(bitmap);
        return this;
    }

    public Load largeIcon(@DrawableRes int largeIcon) {
        if (largeIcon <= 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        }
        this.builder.setLargeIcon(BitmapFactory.decodeResource(PugNotification.mSingleton.mContext.getResources(), largeIcon));
        return this;
    }

    public Load group(@NonNull String groupKey) {
        if (groupKey.trim().length() == 0) {
            throw new IllegalArgumentException("Group Key Must Not Be Empty!");
        }
        this.builder.setGroup(groupKey);
        return this;
    }

    public Load groupSummary(boolean groupSummary) {
        this.builder.setGroupSummary(groupSummary);
        return this;
    }

    public Load number(int number) {
        this.builder.setNumber(number);
        return this;
    }

    public Load vibrate(@NonNull long[] vibrate) {
        for (long aVibrate : vibrate) {
            if (aVibrate <= 0) {
                throw new IllegalArgumentException("Vibrate Time " + aVibrate + " Invalid!");
            }
        }
        this.builder.setVibrate(vibrate);
        return this;
    }

    public Load lights(int color, int ledOnMs, int ledOfMs) {
        if (ledOnMs < 0) {
            throw new IllegalStateException("Led On Milliseconds Invalid!");
        } else if (ledOfMs < 0) {
            throw new IllegalStateException("Led Off Milliseconds Invalid!");
        } else {
            this.builder.setLights(color, ledOnMs, ledOfMs);
            return this;
        }
    }

    public Load sound(@NonNull Uri sound) {
        this.builder.setSound(sound);
        return this;
    }

    public Load onlyAlertOnce(boolean onlyAlertOnce) {
        this.builder.setOnlyAlertOnce(onlyAlertOnce);
        return this;
    }

    public Load addPerson(@NonNull String uri) {
        if (uri.length() == 0) {
            throw new IllegalArgumentException("URI Must Not Be Empty!");
        }
        this.builder.addPerson(uri);
        return this;
    }

    public Load button(@DrawableRes int icon, @NonNull String title, @NonNull PendingIntent pendingIntent) {
        this.builder.addAction(icon, title, pendingIntent);
        return this;
    }

    public Load button(@DrawableRes int icon, @NonNull String title, @NonNull PendingIntentNotification pendingIntentNotification) {
        this.builder.addAction(icon, title, pendingIntentNotification.onSettingPendingIntent());
        return this;
    }

    public Load button(@NonNull Action action) {
        this.builder.addAction(action);
        return this;
    }

    public Load click(@NonNull Class<?> activity, Bundle bundle) {
        this.builder.setContentIntent(new ClickPendingIntentActivity(activity, bundle, this.notificationId).onSettingPendingIntent());
        return this;
    }

    public Load click(@NonNull Class<?> activity) {
        click(activity, null);
        return this;
    }

    public Load click(@NonNull Bundle bundle) {
        this.builder.setContentIntent(new ClickPendingIntentBroadCast(bundle, this.notificationId).onSettingPendingIntent());
        return this;
    }

    public Load click(@NonNull PendingIntentNotification pendingIntentNotification) {
        this.builder.setContentIntent(pendingIntentNotification.onSettingPendingIntent());
        return this;
    }

    public Load priority(int priority) {
        if (priority <= 0) {
            throw new IllegalArgumentException("Priority Should Not Be Less Than Or Equal To Zero!");
        }
        this.builder.setPriority(priority);
        return this;
    }

    public Load flags(int defaults) {
        this.builder.setDefaults(defaults);
        return this;
    }

    public Load click(@NonNull PendingIntent pendingIntent) {
        this.builder.setContentIntent(pendingIntent);
        return this;
    }

    public Load dismiss(@NonNull Class<?> activity, Bundle bundle) {
        this.builder.setDeleteIntent(new DismissPendingIntentActivity(activity, bundle, this.notificationId).onSettingPendingIntent());
        return this;
    }

    public Load dismiss(@NonNull Class<?> activity) {
        dismiss(activity, null);
        return this;
    }

    public Load dismiss(@NonNull Bundle bundle) {
        this.builder.setDeleteIntent(new DismissPendingIntentBroadCast(bundle, this.notificationId).onSettingPendingIntent());
        return this;
    }

    public Load dismiss(@NonNull PendingIntentNotification pendingIntentNotification) {
        this.builder.setDeleteIntent(pendingIntentNotification.onSettingPendingIntent());
        return this;
    }

    public Load dismiss(@NonNull PendingIntent pendingIntent) {
        this.builder.setDeleteIntent(pendingIntent);
        return this;
    }

    public Custom custom() {
        notificationShallContainAtLeastThoseSmallIconValid();
        return new Custom(this.builder, this.notificationId, this.title, this.message, this.messageSpanned, this.smallIcon, this.tag);
    }

    public Simple simple() {
        notificationShallContainAtLeastThoseSmallIconValid();
        return new Simple(this.builder, this.notificationId, this.tag);
    }

    public Wear wear() {
        notificationShallContainAtLeastThoseSmallIconValid();
        return new Wear(this.builder, this.notificationId, this.tag);
    }

    public Progress progress() {
        notificationShallContainAtLeastThoseSmallIconValid();
        return new Progress(this.builder, this.notificationId, this.tag);
    }

    private void notificationShallContainAtLeastThoseSmallIconValid() {
        if (this.smallIcon <= 0) {
            throw new IllegalArgumentException("This is required. Notifications with an invalid icon resource will not be shown.");
        }
    }
}
