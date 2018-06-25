package org.telegram.ui.ActionBar;

import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.ChatBigEmptyView;
import org.telegram.ui.Components.CheckBox;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.ContextProgressView;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.EditTextCaption;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.GroupCreateCheckBox;
import org.telegram.ui.Components.GroupCreateSpan;
import org.telegram.ui.Components.LetterDrawable;
import org.telegram.ui.Components.LineProgressView;
import org.telegram.ui.Components.NumberTextView;
import org.telegram.ui.Components.RadialProgressView;
import org.telegram.ui.Components.RadioButton;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.Switch;

public class ThemeDescription {
    public static int FLAG_AB_AM_BACKGROUND = 1048576;
    public static int FLAG_AB_AM_ITEMSCOLOR = 512;
    public static int FLAG_AB_AM_SELECTORCOLOR = AccessibilityEventCompat.TYPE_WINDOWS_CHANGED;
    public static int FLAG_AB_AM_TOPBACKGROUND = 2097152;
    public static int FLAG_AB_ITEMSCOLOR = 64;
    public static int FLAG_AB_SEARCH = 134217728;
    public static int FLAG_AB_SEARCHPLACEHOLDER = ConnectionsManager.FileTypeFile;
    public static int FLAG_AB_SELECTORCOLOR = 256;
    public static int FLAG_AB_SUBMENUBACKGROUND = Integer.MIN_VALUE;
    public static int FLAG_AB_SUBMENUITEM = 1073741824;
    public static int FLAG_AB_SUBTITLECOLOR = 1024;
    public static int FLAG_AB_TITLECOLOR = 128;
    public static int FLAG_BACKGROUND = 1;
    public static int FLAG_BACKGROUNDFILTER = 32;
    public static int FLAG_CELLBACKGROUNDCOLOR = 16;
    public static int FLAG_CHECKBOX = 8192;
    public static int FLAG_CHECKBOXCHECK = 16384;
    public static int FLAG_CHECKTAG = 262144;
    public static int FLAG_CURSORCOLOR = 16777216;
    public static int FLAG_DRAWABLESELECTEDSTATE = 65536;
    public static int FLAG_FASTSCROLL = ConnectionsManager.FileTypeVideo;
    public static int FLAG_HINTTEXTCOLOR = 8388608;
    public static int FLAG_IMAGECOLOR = 8;
    public static int FLAG_LINKCOLOR = 2;
    public static int FLAG_LISTGLOWCOLOR = 32768;
    public static int FLAG_PROGRESSBAR = 2048;
    public static int FLAG_SECTIONS = 524288;
    public static int FLAG_SELECTOR = 4096;
    public static int FLAG_SELECTORWHITE = 268435456;
    public static int FLAG_SERVICEBACKGROUND = 536870912;
    public static int FLAG_TEXTCOLOR = 4;
    public static int FLAG_USEBACKGROUNDDRAWABLE = 131072;
    private HashMap<String, Field> cachedFields;
    private int changeFlags;
    private int currentColor;
    private String currentKey;
    private int defaultColor;
    private ThemeDescriptionDelegate delegate;
    private Drawable[] drawablesToUpdate;
    private Class[] listClasses;
    private String[] listClassesFieldName;
    private Paint[] paintToUpdate;
    private int previousColor;
    private boolean[] previousIsDefault;
    private View viewToInvalidate;

    public interface ThemeDescriptionDelegate {
        void didSetColor(int i);
    }

    public ThemeDescription(View view, int flags, Class[] classes, Paint[] paint, Drawable[] drawables, ThemeDescriptionDelegate themeDescriptionDelegate, String key, Object unused) {
        this.previousIsDefault = new boolean[1];
        this.currentKey = key;
        this.paintToUpdate = paint;
        this.drawablesToUpdate = drawables;
        this.viewToInvalidate = view;
        this.changeFlags = flags;
        this.listClasses = classes;
        this.delegate = themeDescriptionDelegate;
    }

    public ThemeDescription(View view, int flags, Class[] classes, Paint paint, Drawable[] drawables, ThemeDescriptionDelegate themeDescriptionDelegate, String key) {
        this.previousIsDefault = new boolean[1];
        this.currentKey = key;
        if (paint != null) {
            this.paintToUpdate = new Paint[]{paint};
        }
        this.drawablesToUpdate = drawables;
        this.viewToInvalidate = view;
        this.changeFlags = flags;
        this.listClasses = classes;
        this.delegate = themeDescriptionDelegate;
    }

    public ThemeDescription(View view, int flags, Class[] classes, String[] classesFields, Paint[] paint, Drawable[] drawables, ThemeDescriptionDelegate themeDescriptionDelegate, String key) {
        this.previousIsDefault = new boolean[1];
        this.currentKey = key;
        this.paintToUpdate = paint;
        this.drawablesToUpdate = drawables;
        this.viewToInvalidate = view;
        this.changeFlags = flags;
        this.listClasses = classes;
        this.listClassesFieldName = classesFields;
        this.delegate = themeDescriptionDelegate;
        this.cachedFields = new HashMap();
    }

    public void setColor(int color, boolean useDefault) {
        int a;
        Drawable drawable;
        Theme.setColor(this.currentKey, color, useDefault);
        if (this.paintToUpdate != null) {
            a = 0;
            while (a < this.paintToUpdate.length) {
                if ((this.changeFlags & FLAG_LINKCOLOR) == 0 || !(this.paintToUpdate[a] instanceof TextPaint)) {
                    this.paintToUpdate[a].setColor(color);
                } else {
                    ((TextPaint) this.paintToUpdate[a]).linkColor = color;
                }
                a++;
            }
        }
        if (this.drawablesToUpdate != null) {
            for (a = 0; a < this.drawablesToUpdate.length; a++) {
                if (this.drawablesToUpdate[a] != null) {
                    if (this.drawablesToUpdate[a] instanceof CombinedDrawable) {
                        if ((this.changeFlags & FLAG_BACKGROUNDFILTER) != 0) {
                            ((CombinedDrawable) this.drawablesToUpdate[a]).getBackground().setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
                        } else {
                            ((CombinedDrawable) this.drawablesToUpdate[a]).getIcon().setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
                        }
                    } else if (this.drawablesToUpdate[a] instanceof AvatarDrawable) {
                        ((AvatarDrawable) this.drawablesToUpdate[a]).setColor(color);
                    } else {
                        this.drawablesToUpdate[a].setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
                    }
                }
            }
        }
        if (this.viewToInvalidate != null && this.listClasses == null && this.listClassesFieldName == null && ((this.changeFlags & FLAG_CHECKTAG) == 0 || ((this.changeFlags & FLAG_CHECKTAG) != 0 && this.currentKey.equals(this.viewToInvalidate.getTag())))) {
            if ((this.changeFlags & FLAG_BACKGROUND) != 0) {
                this.viewToInvalidate.setBackgroundColor(color);
            }
            if ((this.changeFlags & FLAG_BACKGROUNDFILTER) != 0) {
                drawable = this.viewToInvalidate.getBackground();
                if (drawable instanceof CombinedDrawable) {
                    if ((this.changeFlags & FLAG_DRAWABLESELECTEDSTATE) != 0) {
                        drawable = ((CombinedDrawable) drawable).getBackground();
                    } else {
                        drawable = ((CombinedDrawable) drawable).getIcon();
                    }
                }
                if (drawable != null) {
                    if ((drawable instanceof StateListDrawable) || (VERSION.SDK_INT >= 21 && (drawable instanceof RippleDrawable))) {
                        Theme.setSelectorDrawableColor(drawable, color, (this.changeFlags & FLAG_DRAWABLESELECTEDSTATE) != 0);
                    } else {
                        drawable.setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
                    }
                }
            }
        }
        if (this.viewToInvalidate instanceof ActionBar) {
            if ((this.changeFlags & FLAG_AB_ITEMSCOLOR) != 0) {
                ((ActionBar) this.viewToInvalidate).setItemsColor(color, false);
            }
            if ((this.changeFlags & FLAG_AB_TITLECOLOR) != 0) {
                ((ActionBar) this.viewToInvalidate).setTitleColor(color);
            }
            if ((this.changeFlags & FLAG_AB_SELECTORCOLOR) != 0) {
                ((ActionBar) this.viewToInvalidate).setItemsBackgroundColor(color, false);
            }
            if ((this.changeFlags & FLAG_AB_AM_SELECTORCOLOR) != 0) {
                ((ActionBar) this.viewToInvalidate).setItemsBackgroundColor(color, true);
            }
            if ((this.changeFlags & FLAG_AB_AM_ITEMSCOLOR) != 0) {
                ((ActionBar) this.viewToInvalidate).setItemsColor(color, true);
            }
            if ((this.changeFlags & FLAG_AB_SUBTITLECOLOR) != 0) {
                ((ActionBar) this.viewToInvalidate).setSubtitleColor(color);
            }
            if ((this.changeFlags & FLAG_AB_AM_BACKGROUND) != 0) {
                ((ActionBar) this.viewToInvalidate).setActionModeColor(color);
            }
            if ((this.changeFlags & FLAG_AB_AM_TOPBACKGROUND) != 0) {
                ((ActionBar) this.viewToInvalidate).setActionModeTopColor(color);
            }
            if ((this.changeFlags & FLAG_AB_SEARCHPLACEHOLDER) != 0) {
                ((ActionBar) this.viewToInvalidate).setSearchTextColor(color, true);
            }
            if ((this.changeFlags & FLAG_AB_SEARCH) != 0) {
                ((ActionBar) this.viewToInvalidate).setSearchTextColor(color, false);
            }
            if ((this.changeFlags & FLAG_AB_SUBMENUITEM) != 0) {
                ((ActionBar) this.viewToInvalidate).setPopupItemsColor(color);
            }
            if ((this.changeFlags & FLAG_AB_SUBMENUBACKGROUND) != 0) {
                ((ActionBar) this.viewToInvalidate).setPopupBackgroundColor(color);
            }
        }
        if (this.viewToInvalidate instanceof EmptyTextProgressView) {
            if ((this.changeFlags & FLAG_TEXTCOLOR) != 0) {
                ((EmptyTextProgressView) this.viewToInvalidate).setTextColor(color);
            } else if ((this.changeFlags & FLAG_PROGRESSBAR) != 0) {
                ((EmptyTextProgressView) this.viewToInvalidate).setProgressBarColor(color);
            }
        }
        if (this.viewToInvalidate instanceof RadialProgressView) {
            ((RadialProgressView) this.viewToInvalidate).setProgressColor(color);
        } else if (this.viewToInvalidate instanceof LineProgressView) {
            if ((this.changeFlags & FLAG_PROGRESSBAR) != 0) {
                ((LineProgressView) this.viewToInvalidate).setProgressColor(color);
            } else {
                ((LineProgressView) this.viewToInvalidate).setBackColor(color);
            }
        } else if (this.viewToInvalidate instanceof ContextProgressView) {
            ((ContextProgressView) this.viewToInvalidate).updateColors();
        }
        if ((this.changeFlags & FLAG_TEXTCOLOR) != 0 && ((this.changeFlags & FLAG_CHECKTAG) == 0 || !(this.viewToInvalidate == null || (this.changeFlags & FLAG_CHECKTAG) == 0 || !this.currentKey.equals(this.viewToInvalidate.getTag())))) {
            if (this.viewToInvalidate instanceof TextView) {
                ((TextView) this.viewToInvalidate).setTextColor(color);
            } else if (this.viewToInvalidate instanceof NumberTextView) {
                ((NumberTextView) this.viewToInvalidate).setTextColor(color);
            } else if (this.viewToInvalidate instanceof SimpleTextView) {
                ((SimpleTextView) this.viewToInvalidate).setTextColor(color);
            } else if (this.viewToInvalidate instanceof ChatBigEmptyView) {
                ((ChatBigEmptyView) this.viewToInvalidate).setTextColor(color);
            }
        }
        if ((this.changeFlags & FLAG_CURSORCOLOR) != 0 && (this.viewToInvalidate instanceof EditTextBoldCursor)) {
            ((EditTextBoldCursor) this.viewToInvalidate).setCursorColor(color);
        }
        if ((this.changeFlags & FLAG_HINTTEXTCOLOR) != 0) {
            if (this.viewToInvalidate instanceof EditTextBoldCursor) {
                ((EditTextBoldCursor) this.viewToInvalidate).setHintColor(color);
            } else if (this.viewToInvalidate instanceof EditText) {
                ((EditText) this.viewToInvalidate).setHintTextColor(color);
            }
        }
        if (!(this.viewToInvalidate == null || (this.changeFlags & FLAG_SERVICEBACKGROUND) == 0)) {
            Drawable background = this.viewToInvalidate.getBackground();
            if (background != null) {
                background.setColorFilter(Theme.colorFilter);
            }
        }
        if ((this.changeFlags & FLAG_IMAGECOLOR) != 0 && ((this.changeFlags & FLAG_CHECKTAG) == 0 || ((this.changeFlags & FLAG_CHECKTAG) != 0 && this.currentKey.equals(this.viewToInvalidate.getTag())))) {
            if (this.viewToInvalidate instanceof ImageView) {
                if ((this.changeFlags & FLAG_USEBACKGROUNDDRAWABLE) != 0) {
                    drawable = ((ImageView) this.viewToInvalidate).getDrawable();
                    if ((drawable instanceof StateListDrawable) || (VERSION.SDK_INT >= 21 && (drawable instanceof RippleDrawable))) {
                        Theme.setSelectorDrawableColor(drawable, color, (this.changeFlags & FLAG_DRAWABLESELECTEDSTATE) != 0);
                    }
                } else {
                    ((ImageView) this.viewToInvalidate).setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
                }
            } else if (this.viewToInvalidate instanceof BackupImageView) {
            }
        }
        if ((this.viewToInvalidate instanceof ScrollView) && (this.changeFlags & FLAG_LISTGLOWCOLOR) != 0) {
            AndroidUtilities.setScrollViewEdgeEffectColor((ScrollView) this.viewToInvalidate, color);
        }
        if (this.viewToInvalidate instanceof RecyclerListView) {
            RecyclerListView recyclerListView = this.viewToInvalidate;
            if ((this.changeFlags & FLAG_SELECTOR) != 0 && this.currentKey.equals(Theme.key_listSelector)) {
                recyclerListView.setListSelectorColor(color);
            }
            if ((this.changeFlags & FLAG_FASTSCROLL) != 0) {
                recyclerListView.updateFastScrollColors();
            }
            if ((this.changeFlags & FLAG_LISTGLOWCOLOR) != 0) {
                recyclerListView.setGlowColor(color);
            }
            if ((this.changeFlags & FLAG_SECTIONS) != 0) {
                ArrayList<View> headers = recyclerListView.getHeaders();
                if (headers != null) {
                    for (a = 0; a < headers.size(); a++) {
                        processViewColor((View) headers.get(a), color);
                    }
                }
                headers = recyclerListView.getHeadersCache();
                if (headers != null) {
                    for (a = 0; a < headers.size(); a++) {
                        processViewColor((View) headers.get(a), color);
                    }
                }
                View header = recyclerListView.getPinnedHeader();
                if (header != null) {
                    processViewColor(header, color);
                }
            }
        } else if (this.viewToInvalidate != null) {
            if ((this.changeFlags & FLAG_SELECTOR) != 0) {
                this.viewToInvalidate.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            } else if ((this.changeFlags & FLAG_SELECTORWHITE) != 0) {
                this.viewToInvalidate.setBackgroundDrawable(Theme.getSelectorDrawable(true));
            }
        }
        if (this.listClasses != null) {
            if (this.viewToInvalidate instanceof ViewGroup) {
                ViewGroup viewGroup = this.viewToInvalidate;
                int count = viewGroup.getChildCount();
                for (a = 0; a < count; a++) {
                    processViewColor(viewGroup.getChildAt(a), color);
                }
            }
            processViewColor(this.viewToInvalidate, color);
        }
        this.currentColor = color;
        if (this.delegate != null) {
            this.delegate.didSetColor(color);
        }
        if (this.viewToInvalidate != null) {
            this.viewToInvalidate.invalidate();
        }
    }

    private void processViewColor(View child, int color) {
        for (int b = 0; b < this.listClasses.length; b++) {
            if (this.listClasses[b].isInstance(child)) {
                Drawable drawable;
                child.invalidate();
                boolean passedCheck;
                if ((this.changeFlags & FLAG_CHECKTAG) == 0 || ((this.changeFlags & FLAG_CHECKTAG) != 0 && this.currentKey.equals(child.getTag()))) {
                    passedCheck = true;
                    child.invalidate();
                    if ((this.changeFlags & FLAG_BACKGROUNDFILTER) != 0) {
                        drawable = child.getBackground();
                        if (drawable != null) {
                            if ((this.changeFlags & FLAG_CELLBACKGROUNDCOLOR) == 0) {
                                if (drawable instanceof CombinedDrawable) {
                                    drawable = ((CombinedDrawable) drawable).getIcon();
                                }
                                drawable.setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
                            } else if (drawable instanceof CombinedDrawable) {
                                Drawable back = ((CombinedDrawable) drawable).getBackground();
                                if (back instanceof ColorDrawable) {
                                    ((ColorDrawable) back).setColor(color);
                                }
                            }
                        }
                    } else if ((this.changeFlags & FLAG_CELLBACKGROUNDCOLOR) != 0) {
                        child.setBackgroundColor(color);
                    } else if ((this.changeFlags & FLAG_TEXTCOLOR) != 0) {
                        if (child instanceof TextView) {
                            ((TextView) child).setTextColor(color);
                        }
                    } else if ((this.changeFlags & FLAG_SERVICEBACKGROUND) != 0) {
                        Drawable background = child.getBackground();
                        if (background != null) {
                            background.setColorFilter(Theme.colorFilter);
                        }
                    }
                } else {
                    passedCheck = false;
                }
                if (this.listClassesFieldName != null) {
                    String key = this.listClasses[b] + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.listClassesFieldName[b];
                    Field field = (Field) this.cachedFields.get(key);
                    if (field == null) {
                        field = this.listClasses[b].getDeclaredField(this.listClassesFieldName[b]);
                        if (field != null) {
                            field.setAccessible(true);
                            this.cachedFields.put(key, field);
                        }
                    }
                    if (field != null) {
                        Object obj = field.get(child);
                        if (obj != null && (passedCheck || !(obj instanceof View) || this.currentKey.equals(((View) obj).getTag()))) {
                            try {
                                if (obj instanceof View) {
                                    ((View) obj).invalidate();
                                }
                                if ((this.changeFlags & FLAG_USEBACKGROUNDDRAWABLE) != 0 && (obj instanceof View)) {
                                    obj = ((View) obj).getBackground();
                                }
                                if ((this.changeFlags & FLAG_BACKGROUND) != 0 && (obj instanceof View)) {
                                    ((View) obj).setBackgroundColor(color);
                                } else if (obj instanceof Switch) {
                                    ((Switch) obj).checkColorFilters();
                                } else if (obj instanceof EditTextCaption) {
                                    if ((this.changeFlags & FLAG_HINTTEXTCOLOR) != 0) {
                                        ((EditTextCaption) obj).setHintColor(color);
                                        ((EditTextCaption) obj).setHintTextColor(color);
                                    } else {
                                        ((EditTextCaption) obj).setTextColor(color);
                                    }
                                } else if (obj instanceof SimpleTextView) {
                                    if ((this.changeFlags & FLAG_LINKCOLOR) != 0) {
                                        ((SimpleTextView) obj).setLinkTextColor(color);
                                    } else {
                                        ((SimpleTextView) obj).setTextColor(color);
                                    }
                                } else if (obj instanceof TextView) {
                                    if ((this.changeFlags & FLAG_IMAGECOLOR) != 0) {
                                        Drawable[] drawables = ((TextView) obj).getCompoundDrawables();
                                        if (drawables != null) {
                                            for (Drawable colorFilter : drawables) {
                                                colorFilter.setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
                                            }
                                        }
                                    } else if ((this.changeFlags & FLAG_LINKCOLOR) != 0) {
                                        ((TextView) obj).getPaint().linkColor = color;
                                        ((TextView) obj).invalidate();
                                    } else {
                                        ((TextView) obj).setTextColor(color);
                                    }
                                } else if (obj instanceof ImageView) {
                                    ((ImageView) obj).setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
                                } else if (obj instanceof BackupImageView) {
                                    drawable = ((BackupImageView) obj).getImageReceiver().getStaticThumb();
                                    if (drawable instanceof CombinedDrawable) {
                                        if ((this.changeFlags & FLAG_BACKGROUNDFILTER) != 0) {
                                            ((CombinedDrawable) drawable).getBackground().setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
                                        } else {
                                            ((CombinedDrawable) drawable).getIcon().setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
                                        }
                                    } else if (drawable != null) {
                                        drawable.setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
                                    }
                                } else if (obj instanceof Drawable) {
                                    if (obj instanceof LetterDrawable) {
                                        if ((this.changeFlags & FLAG_BACKGROUNDFILTER) != 0) {
                                            ((LetterDrawable) obj).setBackgroundColor(color);
                                        } else {
                                            ((LetterDrawable) obj).setColor(color);
                                        }
                                    } else if (obj instanceof CombinedDrawable) {
                                        if ((this.changeFlags & FLAG_BACKGROUNDFILTER) != 0) {
                                            ((CombinedDrawable) obj).getBackground().setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
                                        } else {
                                            ((CombinedDrawable) obj).getIcon().setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
                                        }
                                    } else if ((obj instanceof StateListDrawable) || (VERSION.SDK_INT >= 21 && (obj instanceof RippleDrawable))) {
                                        Theme.setSelectorDrawableColor((Drawable) obj, color, (this.changeFlags & FLAG_DRAWABLESELECTEDSTATE) != 0);
                                    } else {
                                        ((Drawable) obj).setColorFilter(new PorterDuffColorFilter(color, Mode.MULTIPLY));
                                    }
                                } else if (obj instanceof CheckBox) {
                                    if ((this.changeFlags & FLAG_CHECKBOX) != 0) {
                                        ((CheckBox) obj).setBackgroundColor(color);
                                    } else if ((this.changeFlags & FLAG_CHECKBOXCHECK) != 0) {
                                        ((CheckBox) obj).setCheckColor(color);
                                    }
                                } else if (obj instanceof GroupCreateCheckBox) {
                                    ((GroupCreateCheckBox) obj).updateColors();
                                } else if (obj instanceof Integer) {
                                    field.set(child, Integer.valueOf(color));
                                } else if (obj instanceof RadioButton) {
                                    if ((this.changeFlags & FLAG_CHECKBOX) != 0) {
                                        ((RadioButton) obj).setBackgroundColor(color);
                                        ((RadioButton) obj).invalidate();
                                    } else if ((this.changeFlags & FLAG_CHECKBOXCHECK) != 0) {
                                        ((RadioButton) obj).setCheckedColor(color);
                                        ((RadioButton) obj).invalidate();
                                    }
                                } else if (obj instanceof TextPaint) {
                                    if ((this.changeFlags & FLAG_LINKCOLOR) != 0) {
                                        ((TextPaint) obj).linkColor = color;
                                    } else {
                                        ((TextPaint) obj).setColor(color);
                                    }
                                } else if (obj instanceof LineProgressView) {
                                    if ((this.changeFlags & FLAG_PROGRESSBAR) != 0) {
                                        ((LineProgressView) obj).setProgressColor(color);
                                    } else {
                                        ((LineProgressView) obj).setBackColor(color);
                                    }
                                } else if (obj instanceof Paint) {
                                    ((Paint) obj).setColor(color);
                                }
                            } catch (Throwable e) {
                                FileLog.e(e);
                            }
                        }
                    }
                } else if (child instanceof GroupCreateSpan) {
                    ((GroupCreateSpan) child).updateColors();
                }
            }
        }
    }

    public String getCurrentKey() {
        return this.currentKey;
    }

    public void startEditing() {
        int color = Theme.getColor(this.currentKey, this.previousIsDefault);
        this.previousColor = color;
        this.currentColor = color;
    }

    public int getCurrentColor() {
        return this.currentColor;
    }

    public int getSetColor() {
        return Theme.getColor(this.currentKey);
    }

    public void setDefaultColor() {
        setColor(Theme.getDefaultColor(this.currentKey), true);
    }

    public void setPreviousColor() {
        setColor(this.previousColor, this.previousIsDefault[0]);
    }

    public String getTitle() {
        return this.currentKey;
    }
}
