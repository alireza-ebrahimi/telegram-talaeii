package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.tgnet.TLRPC$Chat;
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

    public AvatarDrawable(User user) {
        this(user, false);
    }

    public AvatarDrawable(TLRPC$Chat chat) {
        this(chat, false);
    }

    public AvatarDrawable(User user, boolean profile) {
        this();
        this.isProfile = profile;
        if (user != null) {
            setInfo(user.id, user.first_name, user.last_name, false, null);
        }
    }

    public AvatarDrawable(TLRPC$Chat chat, boolean profile) {
        this();
        this.isProfile = profile;
        if (chat != null) {
            setInfo(chat.id, chat.title, null, chat.id < 0, null);
        }
    }

    public void setProfile(boolean value) {
        this.isProfile = value;
    }

    public static int getColorIndex(int id) {
        return (id < 0 || id >= 7) ? Math.abs(id % Theme.keys_avatar_background.length) : id;
    }

    public static int getColorForId(int id) {
        return Theme.getColor(Theme.keys_avatar_background[getColorIndex(id)]);
    }

    public static int getButtonColorForId(int id) {
        return Theme.getColor(Theme.keys_avatar_actionBarSelector[getColorIndex(id)]);
    }

    public static int getIconColorForId(int id) {
        return Theme.getColor(Theme.keys_avatar_actionBarIcon[getColorIndex(id)]);
    }

    public static int getProfileColorForId(int id) {
        return Theme.getColor(Theme.keys_avatar_backgroundInProfile[getColorIndex(id)]);
    }

    public static int getProfileTextColorForId(int id) {
        return Theme.getColor(Theme.keys_avatar_subtitleInProfile[getColorIndex(id)]);
    }

    public static int getProfileBackColorForId(int id) {
        return Theme.getColor(Theme.keys_avatar_backgroundActionBar[getColorIndex(id)]);
    }

    public static int getNameColorForId(int id) {
        return Theme.getColor(Theme.keys_avatar_nameInMessage[getColorIndex(id)]);
    }

    public void setInfo(User user) {
        if (user != null) {
            setInfo(user.id, user.first_name, user.last_name, false, null);
        }
    }

    public void setSavedMessages(int value) {
        this.savedMessages = value;
        this.color = Theme.getColor(Theme.key_avatar_backgroundSaved);
    }

    public void setInfo(TLRPC$Chat chat) {
        if (chat != null) {
            setInfo(chat.id, chat.title, null, chat.id < 0, null);
        }
    }

    public void setColor(int value) {
        this.color = value;
    }

    public void setTextSize(int size) {
        this.namePaint.setTextSize((float) size);
    }

    public void setInfo(int id, String firstName, String lastName, boolean isBroadcast) {
        setInfo(id, firstName, lastName, isBroadcast, null);
    }

    public int getColor() {
        return this.color;
    }

    public void setInfo(int id, String firstName, String lastName, boolean isBroadcast, String custom) {
        if (this.isProfile) {
            this.color = getProfileColorForId(id);
        } else {
            this.color = getColorForId(id);
        }
        this.drawBrodcast = isBroadcast;
        this.savedMessages = 0;
        if (firstName == null || firstName.length() == 0) {
            firstName = lastName;
            lastName = null;
        }
        this.stringBuilder.setLength(0);
        if (custom != null) {
            this.stringBuilder.append(custom);
        } else {
            if (firstName != null && firstName.length() > 0) {
                this.stringBuilder.appendCodePoint(firstName.codePointAt(0));
            }
            int a;
            if (lastName != null && lastName.length() > 0) {
                Integer lastch = null;
                a = lastName.length() - 1;
                while (a >= 0 && (lastch == null || lastName.charAt(a) != ' ')) {
                    lastch = Integer.valueOf(lastName.codePointAt(a));
                    a--;
                }
                if (VERSION.SDK_INT >= 17) {
                    this.stringBuilder.append("‌");
                }
                this.stringBuilder.appendCodePoint(lastch.intValue());
            } else if (firstName != null && firstName.length() > 0) {
                a = firstName.length() - 1;
                while (a >= 0) {
                    if (firstName.charAt(a) != ' ' || a == firstName.length() - 1 || firstName.charAt(a + 1) == ' ') {
                        a--;
                    } else {
                        if (VERSION.SDK_INT >= 17) {
                            this.stringBuilder.append("‌");
                        }
                        this.stringBuilder.appendCodePoint(firstName.codePointAt(a + 1));
                    }
                }
            }
        }
        if (this.stringBuilder.length() > 0) {
            try {
                this.textLayout = new StaticLayout(this.stringBuilder.toString().toUpperCase(), this.namePaint, AndroidUtilities.dp(100.0f), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                if (this.textLayout.getLineCount() > 0) {
                    this.textLeft = this.textLayout.getLineLeft(0);
                    this.textWidth = this.textLayout.getLineWidth(0);
                    this.textHeight = (float) this.textLayout.getLineBottom(0);
                    return;
                }
                return;
            } catch (Exception e) {
                FileLog.e(e);
                return;
            }
        }
        this.textLayout = null;
    }

    public void setDrawPhoto(boolean value) {
        this.drawPhoto = value;
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        if (bounds != null) {
            int size = bounds.width();
            this.namePaint.setColor(Theme.getColor(Theme.key_avatar_text));
            Theme.avatar_backgroundPaint.setColor(this.color);
            canvas.save();
            canvas.translate((float) bounds.left, (float) bounds.top);
            canvas.drawCircle(((float) size) / 2.0f, ((float) size) / 2.0f, ((float) size) / 2.0f, Theme.avatar_backgroundPaint);
            int x;
            int y;
            if (this.savedMessages != 0 && Theme.avatar_savedDrawable != null) {
                int w = Theme.avatar_savedDrawable.getIntrinsicWidth();
                int h = Theme.avatar_savedDrawable.getIntrinsicHeight();
                if (this.savedMessages == 2) {
                    w = (int) (((float) w) * 0.8f);
                    h = (int) (((float) h) * 0.8f);
                }
                x = (size - w) / 2;
                y = (size - h) / 2;
                Theme.avatar_savedDrawable.setBounds(x, y, x + w, y + h);
                Theme.avatar_savedDrawable.draw(canvas);
            } else if (this.drawBrodcast && Theme.avatar_broadcastDrawable != null) {
                x = (size - Theme.avatar_broadcastDrawable.getIntrinsicWidth()) / 2;
                y = (size - Theme.avatar_broadcastDrawable.getIntrinsicHeight()) / 2;
                Theme.avatar_broadcastDrawable.setBounds(x, y, Theme.avatar_broadcastDrawable.getIntrinsicWidth() + x, Theme.avatar_broadcastDrawable.getIntrinsicHeight() + y);
                Theme.avatar_broadcastDrawable.draw(canvas);
            } else if (this.textLayout != null) {
                canvas.translate(((((float) size) - this.textWidth) / 2.0f) - this.textLeft, (((float) size) - this.textHeight) / 2.0f);
                this.textLayout.draw(canvas);
            } else if (this.drawPhoto && Theme.avatar_photoDrawable != null) {
                x = (size - Theme.avatar_photoDrawable.getIntrinsicWidth()) / 2;
                y = (size - Theme.avatar_photoDrawable.getIntrinsicHeight()) / 2;
                Theme.avatar_photoDrawable.setBounds(x, y, Theme.avatar_photoDrawable.getIntrinsicWidth() + x, Theme.avatar_photoDrawable.getIntrinsicHeight() + y);
                Theme.avatar_photoDrawable.draw(canvas);
            }
            canvas.restore();
        }
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return -2;
    }

    public int getIntrinsicWidth() {
        return 0;
    }

    public int getIntrinsicHeight() {
        return 0;
    }
}
