package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.text.TextUtils;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocationController.SharingLocationInfo;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.LocationActivity.LiveLocation;

public class SharingLiveLocationCell extends FrameLayout {
    private AvatarDrawable avatarDrawable;
    private BackupImageView avatarImageView;
    private SharingLocationInfo currentInfo;
    private SimpleTextView distanceTextView;
    private Runnable invalidateRunnable = new C40291();
    private LiveLocation liveLocation;
    private Location location = new Location("network");
    private SimpleTextView nameTextView;
    private RectF rect = new RectF();

    /* renamed from: org.telegram.ui.Cells.SharingLiveLocationCell$1 */
    class C40291 implements Runnable {
        C40291() {
        }

        public void run() {
            SharingLiveLocationCell.this.invalidate(((int) SharingLiveLocationCell.this.rect.left) - 5, ((int) SharingLiveLocationCell.this.rect.top) - 5, ((int) SharingLiveLocationCell.this.rect.right) + 5, ((int) SharingLiveLocationCell.this.rect.bottom) + 5);
            AndroidUtilities.runOnUIThread(SharingLiveLocationCell.this.invalidateRunnable, 1000);
        }
    }

    public SharingLiveLocationCell(Context context, boolean z) {
        int i = 5;
        super(context);
        this.avatarImageView = new BackupImageView(context);
        this.avatarImageView.setRoundRadius(AndroidUtilities.dp(20.0f));
        this.avatarDrawable = new AvatarDrawable();
        this.nameTextView = new SimpleTextView(context);
        this.nameTextView.setTextSize(16);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.nameTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.nameTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        if (z) {
            addView(this.avatarImageView, LayoutHelper.createFrame(40, 40.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 17.0f, 13.0f, LocaleController.isRTL ? 17.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            addView(this.nameTextView, LayoutHelper.createFrame(-1, 20.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 54.0f : 73.0f, 12.0f, LocaleController.isRTL ? 73.0f : 54.0f, BitmapDescriptorFactory.HUE_RED));
            this.distanceTextView = new SimpleTextView(context);
            this.distanceTextView.setTextSize(14);
            this.distanceTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
            this.distanceTextView.setGravity(LocaleController.isRTL ? 5 : 3);
            View view = this.distanceTextView;
            if (!LocaleController.isRTL) {
                i = 3;
            }
            addView(view, LayoutHelper.createFrame(-1, 20.0f, i | 48, LocaleController.isRTL ? 54.0f : 73.0f, 37.0f, LocaleController.isRTL ? 73.0f : 54.0f, BitmapDescriptorFactory.HUE_RED));
        } else {
            addView(this.avatarImageView, LayoutHelper.createFrame(40, 40.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 17.0f, 7.0f, LocaleController.isRTL ? 17.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            View view2 = this.nameTextView;
            if (!LocaleController.isRTL) {
                i = 3;
            }
            addView(view2, LayoutHelper.createFrame(-2, -2.0f, i | 48, LocaleController.isRTL ? 54.0f : 74.0f, 17.0f, LocaleController.isRTL ? 74.0f : 54.0f, BitmapDescriptorFactory.HUE_RED));
        }
        setWillNotDraw(false);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        AndroidUtilities.runOnUIThread(this.invalidateRunnable);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        AndroidUtilities.cancelRunOnUIThread(this.invalidateRunnable);
    }

    protected void onDraw(Canvas canvas) {
        float f = 42.0f;
        float f2 = 18.0f;
        if (this.currentInfo != null || this.liveLocation != null) {
            int i;
            int i2;
            int i3;
            if (this.currentInfo != null) {
                i3 = this.currentInfo.stopTime;
                i = this.currentInfo.period;
                i2 = i3;
            } else {
                i3 = this.liveLocation.object.media.period + this.liveLocation.object.date;
                i = this.liveLocation.object.media.period;
                i2 = i3;
            }
            int currentTime = ConnectionsManager.getInstance().getCurrentTime();
            if (i2 >= currentTime) {
                float abs = ((float) Math.abs(i2 - currentTime)) / ((float) i);
                if (LocaleController.isRTL) {
                    this.rect.set((float) AndroidUtilities.dp(13.0f), (float) AndroidUtilities.dp(this.distanceTextView != null ? 18.0f : 12.0f), (float) AndroidUtilities.dp(43.0f), (float) AndroidUtilities.dp(this.distanceTextView != null ? 48.0f : 42.0f));
                } else {
                    RectF rectF = this.rect;
                    float measuredWidth = (float) (getMeasuredWidth() - AndroidUtilities.dp(43.0f));
                    if (this.distanceTextView == null) {
                        f2 = 12.0f;
                    }
                    f2 = (float) AndroidUtilities.dp(f2);
                    float measuredWidth2 = (float) (getMeasuredWidth() - AndroidUtilities.dp(13.0f));
                    if (this.distanceTextView != null) {
                        f = 48.0f;
                    }
                    rectF.set(measuredWidth, f2, measuredWidth2, (float) AndroidUtilities.dp(f));
                }
                i = this.distanceTextView == null ? Theme.getColor("location_liveLocationProgress") : Theme.getColor("location_liveLocationProgress");
                Theme.chat_radialProgress2Paint.setColor(i);
                Theme.chat_livePaint.setColor(i);
                canvas.drawArc(this.rect, -90.0f, -360.0f * abs, false, Theme.chat_radialProgress2Paint);
                String formatLocationLeftTime = LocaleController.formatLocationLeftTime(i2 - currentTime);
                canvas.drawText(formatLocationLeftTime, this.rect.centerX() - (Theme.chat_livePaint.measureText(formatLocationLeftTime) / 2.0f), (float) AndroidUtilities.dp(this.distanceTextView != null ? 37.0f : 31.0f), Theme.chat_livePaint);
            }
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(this.distanceTextView != null ? 66.0f : 54.0f), 1073741824));
    }

    public void setDialog(SharingLocationInfo sharingLocationInfo) {
        TLObject tLObject;
        this.currentInfo = sharingLocationInfo;
        int i = (int) sharingLocationInfo.did;
        if (i > 0) {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(i));
            if (user != null) {
                this.avatarDrawable.setInfo(user);
                this.nameTextView.setText(ContactsController.formatName(user.first_name, user.last_name));
                if (!(user.photo == null || user.photo.photo_small == null)) {
                    tLObject = user.photo.photo_small;
                }
            }
            tLObject = null;
        } else {
            Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-i));
            if (chat != null) {
                this.avatarDrawable.setInfo(chat);
                this.nameTextView.setText(chat.title);
                if (!(chat.photo == null || chat.photo.photo_small == null)) {
                    tLObject = chat.photo.photo_small;
                }
            }
            tLObject = null;
        }
        this.avatarImageView.setImage(tLObject, null, this.avatarDrawable);
    }

    public void setDialog(MessageObject messageObject, Location location) {
        CharSequence userName;
        int i = messageObject.messageOwner.from_id;
        if (messageObject.isForwarded()) {
            i = messageObject.messageOwner.fwd_from.channel_id != 0 ? -messageObject.messageOwner.fwd_from.channel_id : messageObject.messageOwner.fwd_from.from_id;
        }
        CharSequence charSequence = !TextUtils.isEmpty(messageObject.messageOwner.media.address) ? messageObject.messageOwner.media.address : null;
        if (TextUtils.isEmpty(messageObject.messageOwner.media.title)) {
            TLObject tLObject;
            String str = TtmlNode.ANONYMOUS_REGION_ID;
            this.avatarDrawable = null;
            TLObject tLObject2;
            Object obj;
            if (i > 0) {
                User user = MessagesController.getInstance().getUser(Integer.valueOf(i));
                if (user != null) {
                    tLObject2 = user.photo != null ? user.photo.photo_small : null;
                    this.avatarDrawable = new AvatarDrawable(user);
                    tLObject = tLObject2;
                    userName = UserObject.getUserName(user);
                }
                obj = str;
                tLObject = null;
            } else {
                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-i));
                if (chat != null) {
                    tLObject2 = chat.photo != null ? chat.photo.photo_small : null;
                    this.avatarDrawable = new AvatarDrawable(chat);
                    tLObject = tLObject2;
                    obj = chat.title;
                }
                obj = str;
                tLObject = null;
            }
            this.avatarImageView.setImage(tLObject, null, this.avatarDrawable);
        } else {
            userName = messageObject.messageOwner.media.title;
            Drawable drawable = getResources().getDrawable(R.drawable.pin);
            drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_location_sendLocationIcon), Mode.MULTIPLY));
            int color = Theme.getColor(Theme.key_location_placeLocationBackground);
            Drawable combinedDrawable = new CombinedDrawable(Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(40.0f), color, color), drawable);
            combinedDrawable.setCustomSize(AndroidUtilities.dp(40.0f), AndroidUtilities.dp(40.0f));
            combinedDrawable.setIconSize(AndroidUtilities.dp(24.0f), AndroidUtilities.dp(24.0f));
            this.avatarImageView.setImageDrawable(combinedDrawable);
        }
        this.nameTextView.setText(userName);
        this.location.setLatitude(messageObject.messageOwner.media.geo.lat);
        this.location.setLongitude(messageObject.messageOwner.media.geo._long);
        if (location != null) {
            float distanceTo = this.location.distanceTo(location);
            if (charSequence != null) {
                if (distanceTo < 1000.0f) {
                    this.distanceTextView.setText(String.format("%s - %d %s", new Object[]{charSequence, Integer.valueOf((int) distanceTo), LocaleController.getString("MetersAway", R.string.MetersAway)}));
                } else {
                    this.distanceTextView.setText(String.format("%s - %.2f %s", new Object[]{charSequence, Float.valueOf(distanceTo / 1000.0f), LocaleController.getString("KMetersAway", R.string.KMetersAway)}));
                }
            } else if (distanceTo < 1000.0f) {
                this.distanceTextView.setText(String.format("%d %s", new Object[]{Integer.valueOf((int) distanceTo), LocaleController.getString("MetersAway", R.string.MetersAway)}));
            } else {
                this.distanceTextView.setText(String.format("%.2f %s", new Object[]{Float.valueOf(distanceTo / 1000.0f), LocaleController.getString("KMetersAway", R.string.KMetersAway)}));
            }
        } else if (charSequence != null) {
            this.distanceTextView.setText(charSequence);
        } else {
            this.distanceTextView.setText(LocaleController.getString("Loading", R.string.Loading));
        }
    }

    public void setDialog(LiveLocation liveLocation, Location location) {
        this.liveLocation = liveLocation;
        int i = liveLocation.id;
        TLObject tLObject = null;
        if (i > 0) {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(i));
            this.avatarDrawable.setInfo(user);
            if (user != null) {
                this.nameTextView.setText(ContactsController.formatName(user.first_name, user.last_name));
                if (!(user.photo == null || user.photo.photo_small == null)) {
                    tLObject = user.photo.photo_small;
                }
            }
        } else {
            Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-i));
            if (chat != null) {
                this.avatarDrawable.setInfo(chat);
                this.nameTextView.setText(chat.title);
                if (!(chat.photo == null || chat.photo.photo_small == null)) {
                    tLObject = chat.photo.photo_small;
                }
            }
        }
        LatLng position = liveLocation.marker.getPosition();
        this.location.setLatitude(position.latitude);
        this.location.setLongitude(position.longitude);
        CharSequence formatLocationUpdateDate = LocaleController.formatLocationUpdateDate(liveLocation.object.edit_date != 0 ? (long) liveLocation.object.edit_date : (long) liveLocation.object.date);
        if (location != null) {
            if (this.location.distanceTo(location) < 1000.0f) {
                this.distanceTextView.setText(String.format("%s - %d %s", new Object[]{formatLocationUpdateDate, Integer.valueOf((int) this.location.distanceTo(location)), LocaleController.getString("MetersAway", R.string.MetersAway)}));
            } else {
                this.distanceTextView.setText(String.format("%s - %.2f %s", new Object[]{formatLocationUpdateDate, Float.valueOf(this.location.distanceTo(location) / 1000.0f), LocaleController.getString("KMetersAway", R.string.KMetersAway)}));
            }
        } else {
            this.distanceTextView.setText(formatLocationUpdateDate);
        }
        this.avatarImageView.setImage(tLObject, null, this.avatarDrawable);
    }
}
