package org.telegram.ui.Cells;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.TextUtils.TruncateAt;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.p149a.C2488b;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.LayoutHelper;

public class DrawerProfileCell extends FrameLayout {
    private BackupImageView avatarImageView;
    private Integer currentColor;
    private Rect destRect = new Rect();
    private TextView nameTextView;
    private Paint paint = new Paint();
    private TextView phoneTextView;
    private ImageView shadowView;
    private Rect srcRect = new Rect();

    public DrawerProfileCell(Context context) {
        super(context);
        this.shadowView = new ImageView(context);
        this.shadowView.setVisibility(4);
        this.shadowView.setScaleType(ScaleType.FIT_XY);
        this.shadowView.setImageResource(R.drawable.bottom_shadow);
        addView(this.shadowView, LayoutHelper.createFrame(-1, 70, 83));
        this.avatarImageView = new BackupImageView(context);
        this.avatarImageView.getImageReceiver().setRoundRadius(AndroidUtilities.dp(32.0f));
        addView(this.avatarImageView, LayoutHelper.createFrame(64, 64.0f, 83, 16.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 67.0f));
        this.nameTextView = new TextView(context);
        this.nameTextView.setTextSize(1, 15.0f);
        this.nameTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.nameTextView.setLines(1);
        this.nameTextView.setMaxLines(1);
        this.nameTextView.setSingleLine(true);
        this.nameTextView.setGravity(3);
        this.nameTextView.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
        this.nameTextView.setEllipsize(TruncateAt.END);
        addView(this.nameTextView, LayoutHelper.createFrame(-1, -2.0f, 83, 16.0f, BitmapDescriptorFactory.HUE_RED, 76.0f, 28.0f));
        this.phoneTextView = new TextView(context);
        this.phoneTextView.setTextSize(1, 13.0f);
        this.phoneTextView.setLines(1);
        this.phoneTextView.setMaxLines(1);
        this.phoneTextView.setSingleLine(true);
        this.phoneTextView.setGravity(3);
        this.phoneTextView.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
        addView(this.phoneTextView, LayoutHelper.createFrame(-1, -2.0f, 83, 16.0f, BitmapDescriptorFactory.HUE_RED, 76.0f, 9.0f));
    }

    public void invalidate() {
        super.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        Drawable cachedWallpaper = Theme.getCachedWallpaper();
        int color = Theme.hasThemeKey(Theme.key_chats_menuTopShadow) ? Theme.getColor(Theme.key_chats_menuTopShadow) : Theme.getServiceMessageColor() | Theme.ACTION_BAR_VIDEO_EDIT_COLOR;
        if (this.currentColor == null || this.currentColor.intValue() != color) {
            this.currentColor = Integer.valueOf(color);
            this.shadowView.getDrawable().setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
        }
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_chats_menuName));
        if (!Theme.isCustomTheme() || cachedWallpaper == null) {
            this.shadowView.setVisibility(4);
            this.phoneTextView.setTextColor(Theme.getColor(Theme.key_chats_menuPhoneCats));
            super.onDraw(canvas);
            return;
        }
        this.phoneTextView.setTextColor(Theme.getColor(Theme.key_chats_menuPhone));
        this.shadowView.setVisibility(0);
        if (cachedWallpaper instanceof ColorDrawable) {
            cachedWallpaper.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            cachedWallpaper.draw(canvas);
        } else if (cachedWallpaper instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) cachedWallpaper).getBitmap();
            float measuredWidth = ((float) getMeasuredWidth()) / ((float) bitmap.getWidth());
            float measuredHeight = ((float) getMeasuredHeight()) / ((float) bitmap.getHeight());
            if (measuredWidth >= measuredHeight) {
                measuredHeight = measuredWidth;
            }
            color = (int) (((float) getMeasuredWidth()) / measuredHeight);
            int measuredHeight2 = (int) (((float) getMeasuredHeight()) / measuredHeight);
            int width = (bitmap.getWidth() - color) / 2;
            int height = (bitmap.getHeight() - measuredHeight2) / 2;
            this.srcRect.set(width, height, color + width, measuredHeight2 + height);
            this.destRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
            try {
                canvas.drawBitmap(bitmap, this.srcRect, this.destRect, this.paint);
            } catch (Throwable th) {
                FileLog.e(th);
            }
        }
    }

    protected void onMeasure(int i, int i2) {
        if (VERSION.SDK_INT >= 21) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(148.0f) + AndroidUtilities.statusBarHeight, 1073741824));
        } else {
            try {
                super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(148.0f), 1073741824));
            } catch (Throwable e) {
                setMeasuredDimension(MeasureSpec.getSize(i), AndroidUtilities.dp(148.0f));
                FileLog.e(e);
            }
        }
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
        if (!sharedPreferences.getBoolean("hideMobile", false) || sharedPreferences.getBoolean("showUsername", false)) {
            this.phoneTextView.setVisibility(0);
        } else {
            this.phoneTextView.setVisibility(8);
        }
    }

    public void setUser(User user) {
        if (user != null) {
            TLObject tLObject = null;
            if (user.photo != null) {
                tLObject = user.photo.photo_small;
            }
            CharSequence string = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getBoolean("showUsername", false) ? (user.username == null || user.username.length() == 0) ? LocaleController.getString("UsernameEmpty", R.string.UsernameEmpty) : "@" + user.username : C2488b.a().e("+" + user.phone);
            this.nameTextView.setText(UserObject.getUserName(user));
            this.phoneTextView.setText(string);
            Drawable avatarDrawable = new AvatarDrawable(user);
            avatarDrawable.setColor(Theme.getColor(Theme.key_avatar_backgroundInProfileBlue));
            this.avatarImageView.setImage(tLObject, "50_50", avatarDrawable);
        }
    }
}
