package br.com.goncalves.pugnotification.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.app.RemoteInput;
import br.com.goncalves.pugnotification.C0402R;
import br.com.goncalves.pugnotification.interfaces.PendingIntentNotification;
import java.util.List;

public class Wear extends Builder {
    private RemoteInput remoteInput;
    private WearableExtender wearableExtender = new WearableExtender();

    public Wear(Builder builder, int identifier, String tag) {
        super(builder, identifier, tag);
    }

    public Wear hideIcon(boolean hideIcon) {
        this.wearableExtender.setHintHideIcon(hideIcon);
        return this;
    }

    public Wear contentIcon(@DrawableRes int contentIcon) {
        this.wearableExtender.setContentIcon(contentIcon);
        return this;
    }

    public Wear addPages(Notification notification) {
        if (notification == null) {
            throw new IllegalArgumentException("Notification Must Not Be Null.");
        }
        this.wearableExtender.addPage(notification);
        return this;
    }

    public Wear addPages(List<Notification> notificationList) {
        if (notificationList == null || notificationList.isEmpty()) {
            throw new IllegalArgumentException("List Notitifcation Must Not Be Null And Empty!");
        }
        this.wearableExtender.addPages(notificationList);
        return this;
    }

    public Wear button(@DrawableRes int icon, String title, PendingIntent pendingIntent) {
        if (icon < 0) {
            throw new IllegalArgumentException("Resource ID Should Not Be Less Than Or Equal To Zero!");
        } else if (title == null) {
            throw new IllegalStateException("Title Must Not Be Null!");
        } else if (pendingIntent == null) {
            throw new IllegalArgumentException("PendingIntent Must Not Be Null.");
        } else {
            this.wearableExtender.addAction(new Action(icon, title, pendingIntent));
            return this;
        }
    }

    public Wear remoteInput(@DrawableRes int icon, @StringRes int title, PendingIntentNotification pendingIntentNotification, RemoteInput remoteInput) {
        remoteInput(icon, PugNotification.mSingleton.mContext.getString(title), pendingIntentNotification.onSettingPendingIntent(), remoteInput);
        return this;
    }

    public Wear remoteInput(@DrawableRes int icon, String title, PendingIntentNotification pendingIntentNotification, RemoteInput remoteInput) {
        remoteInput(icon, title, pendingIntentNotification.onSettingPendingIntent(), remoteInput);
        return this;
    }

    public Wear remoteInput(@DrawableRes int icon, @StringRes int title, PendingIntent pendingIntent, RemoteInput remoteInput) {
        remoteInput(icon, PugNotification.mSingleton.mContext.getString(title), pendingIntent, remoteInput);
        return this;
    }

    public Wear remoteInput(@DrawableRes int icon, String title, PendingIntent pendingIntent, RemoteInput remoteInput) {
        if (icon <= 0) {
            throw new IllegalArgumentException("Resource ID Icon Should Not Be Less Than Or Equal To Zero!");
        } else if (title == null) {
            throw new IllegalArgumentException("Title Must Not Be Null!");
        } else if (pendingIntent == null) {
            throw new IllegalArgumentException("PendingIntent Must Not Be Null!");
        } else if (remoteInput == null) {
            throw new IllegalArgumentException("RemoteInput Must Not Be Null!");
        } else {
            this.remoteInput = remoteInput;
            this.wearableExtender.addAction(new Action.Builder(icon, title, pendingIntent).addRemoteInput(remoteInput).build());
            return this;
        }
    }

    public Wear remoteInput(@DrawableRes int icon, String title, PendingIntent pendingIntent) {
        if (icon <= 0) {
            throw new IllegalArgumentException("Resource ID Icon Should Not Be Less Than Or Equal To Zero!");
        } else if (title == null) {
            throw new IllegalArgumentException("Title Must Not Be Null!");
        } else if (pendingIntent == null) {
            throw new IllegalArgumentException("PendingIntent Must Not Be Null!");
        } else {
            this.remoteInput = new RemoteInput.Builder(PugNotification.mSingleton.mContext.getString(C0402R.string.pugnotification_key_voice_reply)).setLabel(PugNotification.mSingleton.mContext.getString(C0402R.string.pugnotification_label_voice_reply)).setChoices(PugNotification.mSingleton.mContext.getResources().getStringArray(C0402R.array.pugnotification_reply_choices)).build();
            this.wearableExtender.addAction(new Action.Builder(icon, title, pendingIntent).addRemoteInput(this.remoteInput).build());
            return this;
        }
    }

    public Wear remoteInput(@DrawableRes int icon, @StringRes int title, PendingIntent pendingIntent, String replyLabel, String[] replyChoices) {
        return remoteInput(icon, PugNotification.mSingleton.mContext.getString(title), pendingIntent, replyLabel, replyChoices);
    }

    public Wear remoteInput(@DrawableRes int icon, String title, PendingIntent pendingIntent, String replyLabel, String[] replyChoices) {
        if (icon <= 0) {
            throw new IllegalArgumentException("Resource ID Icon Should Not Be Less Than Or Equal To Zero!");
        } else if (title == null) {
            throw new IllegalArgumentException("Title Must Not Be Null!");
        } else if (replyChoices == null) {
            throw new IllegalArgumentException("Reply Choices Must Not Be Null!");
        } else if (pendingIntent == null) {
            throw new IllegalArgumentException("PendingIntent Must Not Be Null!");
        } else if (replyLabel == null) {
            throw new IllegalArgumentException("Reply Label Must Not Be Null!");
        } else {
            this.remoteInput = new RemoteInput.Builder(PugNotification.mSingleton.mContext.getString(C0402R.string.pugnotification_key_voice_reply)).setLabel(replyLabel).setChoices(replyChoices).build();
            this.wearableExtender.addAction(new Action.Builder(icon, title, pendingIntent).addRemoteInput(this.remoteInput).build());
            return this;
        }
    }

    public Wear background(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap Must Not Be Null.");
        }
        this.wearableExtender.setBackground(bitmap);
        return this;
    }

    public Wear background(@DrawableRes int background) {
        if (background <= 0) {
            throw new IllegalArgumentException("Resource ID Background Should Not Be Less Than Or Equal To Zero!");
        }
        this.wearableExtender.setBackground(BitmapFactory.decodeResource(PugNotification.mSingleton.mContext.getResources(), background));
        return this;
    }

    public Wear startScrollBottom(boolean startScrollBottom) {
        this.wearableExtender.setStartScrollBottom(startScrollBottom);
        return this;
    }

    public void build() {
        this.builder.extend(this.wearableExtender);
        super.build();
        super.notificationNotify();
    }
}
