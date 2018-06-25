package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;

public class AvatarDrawable extends Drawable {
    private int color;
    private boolean drawBrodcast;
    private boolean drawPhoto;
    private boolean isProfile;
    private TextPaint namePaint;
    private int savedMessages;
    private StringBuilder stringBuilder;
    private float textHeight;
    private StaticLayout textLayout;
    private float textLeft;
    private float textWidth;

    public AvatarDrawable() {
        this.stringBuilder = new StringBuilder(5);
        this.namePaint = new TextPaint(1);
        this.namePaint.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.namePaint.setTextSize((float) AndroidUtilities.dp(18.0f));
    }

    public AvatarDrawable(Chat chat) {
        this(chat, false);
    }

    public AvatarDrawable(Chat chat, boolean z) {
        this();
        this.isProfile = z;
        if (chat != null) {
            setInfo(chat.id, chat.title, null, chat.id < 0, null);
        }
    }

    public AvatarDrawable(User user) {
        this(user, false);
    }

    public AvatarDrawable(User user, boolean z) {
        this();
        this.isProfile = z;
        if (user != null) {
            setInfo(user.id, user.first_name, user.last_name, false, null);
        }
    }

    public static int getButtonColorForId(int i) {
        return Theme.getColor(Theme.keys_avatar_actionBarSelector[getColorIndex(i)]);
    }

    public static int getColorForId(int i) {
        return Theme.getColor(Theme.keys_avatar_background[getColorIndex(i)]);
    }

    public static int getColorIndex(int i) {
        return (i < 0 || i >= 7) ? Math.abs(i % Theme.keys_avatar_background.length) : i;
    }

    public static int getIconColorForId(int i) {
        return Theme.getColor(Theme.keys_avatar_actionBarIcon[getColorIndex(i)]);
    }

    public static int getNameColorForId(int i) {
        return Theme.getColor(Theme.keys_avatar_nameInMessage[getColorIndex(i)]);
    }

    public static int getProfileBackColorForId(int i) {
        return Theme.getColor(Theme.keys_avatar_backgroundActionBar[getColorIndex(i)]);
    }

    public static int getProfileColorForId(int i) {
        return Theme.getColor(Theme.keys_avatar_backgroundInProfile[getColorIndex(i)]);
    }

    public static int getProfileTextColorForId(int i) {
        return Theme.getColor(Theme.keys_avatar_subtitleInProfile[getColorIndex(i)]);
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        if (bounds != null) {
            int width = bounds.width();
            this.namePaint.setColor(Theme.getColor(Theme.key_avatar_text));
            Theme.avatar_backgroundPaint.setColor(this.color);
            canvas.save();
            canvas.translate((float) bounds.left, (float) bounds.top);
            canvas.drawCircle(((float) width) / 2.0f, ((float) width) / 2.0f, ((float) width) / 2.0f, Theme.avatar_backgroundPaint);
            int intrinsicWidth;
            int intrinsicHeight;
            if (this.savedMessages != 0 && Theme.avatar_savedDrawable != null) {
                intrinsicWidth = Theme.avatar_savedDrawable.getIntrinsicWidth();
                intrinsicHeight = Theme.avatar_savedDrawable.getIntrinsicHeight();
                if (this.savedMessages == 2) {
                    intrinsicWidth = (int) (((float) intrinsicWidth) * 0.8f);
                    intrinsicHeight = (int) (((float) intrinsicHeight) * 0.8f);
                }
                int i = (width - intrinsicWidth) / 2;
                width = (width - intrinsicHeight) / 2;
                Theme.avatar_savedDrawable.setBounds(i, width, intrinsicWidth + i, intrinsicHeight + width);
                Theme.avatar_savedDrawable.draw(canvas);
            } else if (this.drawBrodcast && Theme.avatar_broadcastDrawable != null) {
                intrinsicHeight = (width - Theme.avatar_broadcastDrawable.getIntrinsicWidth()) / 2;
                intrinsicWidth = (width - Theme.avatar_broadcastDrawable.getIntrinsicHeight()) / 2;
                Theme.avatar_broadcastDrawable.setBounds(intrinsicHeight, intrinsicWidth, Theme.avatar_broadcastDrawable.getIntrinsicWidth() + intrinsicHeight, Theme.avatar_broadcastDrawable.getIntrinsicHeight() + intrinsicWidth);
                Theme.avatar_broadcastDrawable.draw(canvas);
            } else if (this.textLayout != null) {
                canvas.translate(((((float) width) - this.textWidth) / 2.0f) - this.textLeft, (((float) width) - this.textHeight) / 2.0f);
                this.textLayout.draw(canvas);
            } else if (this.drawPhoto && Theme.avatar_photoDrawable != null) {
                intrinsicHeight = (width - Theme.avatar_photoDrawable.getIntrinsicWidth()) / 2;
                intrinsicWidth = (width - Theme.avatar_photoDrawable.getIntrinsicHeight()) / 2;
                Theme.avatar_photoDrawable.setBounds(intrinsicHeight, intrinsicWidth, Theme.avatar_photoDrawable.getIntrinsicWidth() + intrinsicHeight, Theme.avatar_photoDrawable.getIntrinsicHeight() + intrinsicWidth);
                Theme.avatar_photoDrawable.draw(canvas);
            }
            canvas.restore();
        }
    }

    public int getColor() {
        return this.color;
    }

    public int getIntrinsicHeight() {
        return 0;
    }

    public int getIntrinsicWidth() {
        return 0;
    }

    public int getOpacity() {
        return -2;
    }

    public void setAlpha(int i) {
    }

    public void setColor(int i) {
        this.color = i;
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setDrawPhoto(boolean z) {
        this.drawPhoto = z;
    }

    public void setInfo(int i, String str, String str2, boolean z) {
        setInfo(i, str, str2, z, null);
    }

    public void setInfo(int i, String str, String str2, boolean z, String str3) {
        if (this.isProfile) {
            this.color = getProfileColorForId(i);
        } else {
            this.color = getColorForId(i);
        }
        this.drawBrodcast = z;
        this.savedMessages = 0;
        if (str == null || str.length() == 0) {
            str = str2;
            str2 = null;
        }
        this.stringBuilder.setLength(0);
        if (str3 != null) {
            this.stringBuilder.append(str3);
        } else {
            if (str != null && str.length() > 0) {
                this.stringBuilder.appendCodePoint(str.codePointAt(0));
            }
            int length;
            if (str2 != null && str2.length() > 0) {
                length = str2.length() - 1;
                Integer num = null;
                while (length >= 0 && (num == null || str2.charAt(length) != ' ')) {
                    num = Integer.valueOf(str2.codePointAt(length));
                    length--;
                }
                if (VERSION.SDK_INT >= 17) {
                    this.stringBuilder.append("‌");
                }
                this.stringBuilder.appendCodePoint(num.intValue());
            } else if (str != null && str.length() > 0) {
                length = str.length() - 1;
                while (length >= 0) {
                    if (str.charAt(length) != ' ' || length == str.length() - 1 || str.charAt(length + 1) == ' ') {
                        length--;
                    } else {
                        if (VERSION.SDK_INT >= 17) {
                            this.stringBuilder.append("‌");
                        }
                        this.stringBuilder.appendCodePoint(str.codePointAt(length + 1));
                    }
                }
            }
        }
        if (this.stringBuilder.length() > 0) {
            try {
                this.textLayout = new StaticLayout(this.stringBuilder.toString().toUpperCase(), this.namePaint, AndroidUtilities.dp(100.0f), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                if (this.textLayout.getLineCount() > 0) {
                    this.textLeft = this.textLayout.getLineLeft(0);
                    this.textWidth = this.textLayout.getLineWidth(0);
                    this.textHeight = (float) this.textLayout.getLineBottom(0);
                    return;
                }
                return;
            } catch (Throwable e) {
                FileLog.e(e);
                return;
            }
        }
        this.textLayout = null;
    }

    public void setInfo(Chat chat) {
        if (chat != null) {
            setInfo(chat.id, chat.title, null, chat.id < 0, null);
        }
    }

    public void setInfo(User user) {
        if (user != null) {
            setInfo(user.id, user.first_name, user.last_name, false, null);
        }
    }

    public void setProfile(boolean z) {
        this.isProfile = z;
    }

    public void setSavedMessages(int i) {
        this.savedMessages = i;
        this.color = Theme.getColor(Theme.key_avatar_backgroundSaved);
    }

    public void setTextSize(int i) {
        this.namePaint.setTextSize((float) i);
    }
}
