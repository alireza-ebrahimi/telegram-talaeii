package br.com.goncalves.pugnotification.model;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.net.Uri;

public class NotificationBean {
    private boolean autoCancel;
    private int background;
    private String bigTextStyle;
    private PendingIntent clickPendingIntent;
    private int colorLight;
    private PendingIntent dismissPendingIntent;
    private int identifier;
    private Bitmap largeIcon;
    private int ledOffMs;
    private int ledOnMs;
    private String message;
    private int smallIcon;
    private Uri sound;
    private String ticker;
    private String title;
    private long[] vibratorTime;
    private long when;

    public int getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBigTextStyle() {
        return this.bigTextStyle;
    }

    public void setBigTextStyle(String bigTextStyle) {
        this.bigTextStyle = bigTextStyle;
    }

    public String getTicker() {
        return this.ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getBackground() {
        return this.background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getSmallIcon() {
        return this.smallIcon;
    }

    public void setSmallIcon(int smallIcon) {
        this.smallIcon = smallIcon;
    }

    public int getColorLight() {
        return this.colorLight;
    }

    public void setColorLight(int colorLight) {
        this.colorLight = colorLight;
    }

    public int getLedOnMs() {
        return this.ledOnMs;
    }

    public void setLedOnMs(int ledOnMs) {
        this.ledOnMs = ledOnMs;
    }

    public int getLedOffMs() {
        return this.ledOffMs;
    }

    public void setLedOffMs(int ledOffMs) {
        this.ledOffMs = ledOffMs;
    }

    public long getWhen() {
        return this.when;
    }

    public void setWhen(long when) {
        this.when = when;
    }

    public long[] getVibratorTime() {
        return this.vibratorTime;
    }

    public void setVibratorTime(long[] vibratorTime) {
        this.vibratorTime = vibratorTime;
    }

    public boolean isAutoCancel() {
        return this.autoCancel;
    }

    public void setAutoCancel(boolean autoCancel) {
        this.autoCancel = autoCancel;
    }

    public Bitmap getLargeIcon() {
        return this.largeIcon;
    }

    public void setLargeIcon(Bitmap largeIcon) {
        this.largeIcon = largeIcon;
    }

    public Uri getSound() {
        return this.sound;
    }

    public void setSound(Uri sound) {
        this.sound = sound;
    }

    public PendingIntent getClickPendingIntent() {
        return this.clickPendingIntent;
    }

    public void setClickPendingIntent(PendingIntent clickPendingIntent) {
        this.clickPendingIntent = clickPendingIntent;
    }

    public PendingIntent getDismissPendingIntent() {
        return this.dismissPendingIntent;
    }

    public void setDismissPendingIntent(PendingIntent dismissPendingIntent) {
        this.dismissPendingIntent = dismissPendingIntent;
    }
}
