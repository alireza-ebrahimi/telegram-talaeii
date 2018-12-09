package org.telegram.ui.ActionBar;

import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.source.ExtractorMediaSource;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC;
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
    public static int FLAG_AB_AM_BACKGROUND = ExtractorMediaSource.DEFAULT_LOADING_CHECK_INTERVAL_BYTES;
    public static int FLAG_AB_AM_ITEMSCOLOR = 512;
    public static int FLAG_AB_AM_SELECTORCOLOR = 4194304;
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
    public static int FLAG_CHECKBOX = MessagesController.UPDATE_MASK_CHANNEL;
    public static int FLAG_CHECKBOXCHECK = MessagesController.UPDATE_MASK_CHAT_ADMINS;
    public static int FLAG_CHECKTAG = 262144;
    public static int FLAG_CURSORCOLOR = 16777216;
    public static int FLAG_DRAWABLESELECTEDSTATE = C3446C.DEFAULT_BUFFER_SEGMENT_SIZE;
    public static int FLAG_FASTSCROLL = ConnectionsManager.FileTypeVideo;
    public static int FLAG_HINTTEXTCOLOR = 8388608;
    public static int FLAG_IMAGECOLOR = 8;
    public static int FLAG_LINKCOLOR = 2;
    public static int FLAG_LISTGLOWCOLOR = TLRPC.MESSAGE_FLAG_EDITED;
    public static int FLAG_PROGRESSBAR = 2048;
    public static int FLAG_SECTIONS = 524288;
    public static int FLAG_SELECTOR = 4096;
    public static int FLAG_SELECTORWHITE = ErrorDialogData.BINDER_CRASH;
    public static int FLAG_SERVICEBACKGROUND = ErrorDialogData.DYNAMITE_CRASH;
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

    public ThemeDescription(View view, int i, Class[] clsArr, Paint paint, Drawable[] drawableArr, ThemeDescriptionDelegate themeDescriptionDelegate, String str) {
        this.previousIsDefault = new boolean[1];
        this.currentKey = str;
        if (paint != null) {
            this.paintToUpdate = new Paint[]{paint};
        }
        this.drawablesToUpdate = drawableArr;
        this.viewToInvalidate = view;
        this.changeFlags = i;
        this.listClasses = clsArr;
        this.delegate = themeDescriptionDelegate;
    }

    public ThemeDescription(View view, int i, Class[] clsArr, Paint[] paintArr, Drawable[] drawableArr, ThemeDescriptionDelegate themeDescriptionDelegate, String str, Object obj) {
        this.previousIsDefault = new boolean[1];
        this.currentKey = str;
        this.paintToUpdate = paintArr;
        this.drawablesToUpdate = drawableArr;
        this.viewToInvalidate = view;
        this.changeFlags = i;
        this.listClasses = clsArr;
        this.delegate = themeDescriptionDelegate;
    }

    public ThemeDescription(View view, int i, Class[] clsArr, String[] strArr, Paint[] paintArr, Drawable[] drawableArr, ThemeDescriptionDelegate themeDescriptionDelegate, String str) {
        this.previousIsDefault = new boolean[1];
        this.currentKey = str;
        this.paintToUpdate = paintArr;
        this.drawablesToUpdate = drawableArr;
        this.viewToInvalidate = view;
        this.changeFlags = i;
        this.listClasses = clsArr;
        this.listClassesFieldName = strArr;
        this.delegate = themeDescriptionDelegate;
        this.cachedFields = new HashMap();
    }

    private void processViewColor(View view, int i) {
        for (int i2 = 0; i2 < this.listClasses.length; i2++) {
            if (this.listClasses[i2].isInstance(view)) {
                Drawable background;
                view.invalidate();
                Object obj;
                if ((this.changeFlags & FLAG_CHECKTAG) == 0 || ((this.changeFlags & FLAG_CHECKTAG) != 0 && this.currentKey.equals(view.getTag()))) {
                    view.invalidate();
                    if ((this.changeFlags & FLAG_BACKGROUNDFILTER) != 0) {
                        background = view.getBackground();
                        if (background != null) {
                            if ((this.changeFlags & FLAG_CELLBACKGROUNDCOLOR) == 0) {
                                if (background instanceof CombinedDrawable) {
                                    background = ((CombinedDrawable) background).getIcon();
                                }
                                background.setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
                            } else if (background instanceof CombinedDrawable) {
                                background = ((CombinedDrawable) background).getBackground();
                                if (background instanceof ColorDrawable) {
                                    ((ColorDrawable) background).setColor(i);
                                }
                            }
                        }
                        obj = 1;
                    } else if ((this.changeFlags & FLAG_CELLBACKGROUNDCOLOR) != 0) {
                        view.setBackgroundColor(i);
                        r5 = 1;
                    } else {
                        if ((this.changeFlags & FLAG_TEXTCOLOR) != 0) {
                            if (view instanceof TextView) {
                                ((TextView) view).setTextColor(i);
                                r5 = 1;
                            }
                        } else if ((this.changeFlags & FLAG_SERVICEBACKGROUND) != 0) {
                            background = view.getBackground();
                            if (background != null) {
                                background.setColorFilter(Theme.colorFilter);
                            }
                            r5 = 1;
                        }
                        r5 = 1;
                    }
                } else {
                    obj = null;
                }
                if (this.listClassesFieldName != null) {
                    String str = this.listClasses[i2] + "_" + this.listClassesFieldName[i2];
                    Field field = (Field) this.cachedFields.get(str);
                    if (field == null) {
                        field = this.listClasses[i2].getDeclaredField(this.listClassesFieldName[i2]);
                        if (field != null) {
                            field.setAccessible(true);
                            this.cachedFields.put(str, field);
                        }
                    }
                    Field field2 = field;
                    if (field2 != null) {
                        Drawable drawable = field2.get(view);
                        if (!(drawable == null || (r5 == null && (drawable instanceof View) && !this.currentKey.equals(((View) drawable).getTag())))) {
                            try {
                                if (drawable instanceof View) {
                                    ((View) drawable).invalidate();
                                }
                                background = ((this.changeFlags & FLAG_USEBACKGROUNDDRAWABLE) == 0 || !(drawable instanceof View)) ? drawable : ((View) drawable).getBackground();
                                if ((this.changeFlags & FLAG_BACKGROUND) != 0 && (background instanceof View)) {
                                    ((View) background).setBackgroundColor(i);
                                } else if (background instanceof Switch) {
                                    ((Switch) background).checkColorFilters();
                                } else if (background instanceof EditTextCaption) {
                                    if ((this.changeFlags & FLAG_HINTTEXTCOLOR) != 0) {
                                        ((EditTextCaption) background).setHintColor(i);
                                        ((EditTextCaption) background).setHintTextColor(i);
                                    } else {
                                        ((EditTextCaption) background).setTextColor(i);
                                    }
                                } else if (background instanceof SimpleTextView) {
                                    if ((this.changeFlags & FLAG_LINKCOLOR) != 0) {
                                        ((SimpleTextView) background).setLinkTextColor(i);
                                    } else {
                                        ((SimpleTextView) background).setTextColor(i);
                                    }
                                } else if (background instanceof TextView) {
                                    if ((this.changeFlags & FLAG_IMAGECOLOR) != 0) {
                                        Drawable[] compoundDrawables = ((TextView) background).getCompoundDrawables();
                                        if (compoundDrawables != null) {
                                            for (Drawable colorFilter : compoundDrawables) {
                                                colorFilter.setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
                                            }
                                        }
                                    } else if ((this.changeFlags & FLAG_LINKCOLOR) != 0) {
                                        ((TextView) background).getPaint().linkColor = i;
                                        ((TextView) background).invalidate();
                                    } else {
                                        ((TextView) background).setTextColor(i);
                                    }
                                } else if (background instanceof ImageView) {
                                    ((ImageView) background).setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
                                } else if (background instanceof BackupImageView) {
                                    background = ((BackupImageView) background).getImageReceiver().getStaticThumb();
                                    if (background instanceof CombinedDrawable) {
                                        if ((this.changeFlags & FLAG_BACKGROUNDFILTER) != 0) {
                                            ((CombinedDrawable) background).getBackground().setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
                                        } else {
                                            ((CombinedDrawable) background).getIcon().setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
                                        }
                                    } else if (background != null) {
                                        background.setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
                                    }
                                } else if (background instanceof Drawable) {
                                    if (background instanceof LetterDrawable) {
                                        if ((this.changeFlags & FLAG_BACKGROUNDFILTER) != 0) {
                                            ((LetterDrawable) background).setBackgroundColor(i);
                                        } else {
                                            ((LetterDrawable) background).setColor(i);
                                        }
                                    } else if (background instanceof CombinedDrawable) {
                                        if ((this.changeFlags & FLAG_BACKGROUNDFILTER) != 0) {
                                            ((CombinedDrawable) background).getBackground().setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
                                        } else {
                                            ((CombinedDrawable) background).getIcon().setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
                                        }
                                    } else if ((background instanceof StateListDrawable) || (VERSION.SDK_INT >= 21 && (background instanceof RippleDrawable))) {
                                        Theme.setSelectorDrawableColor(background, i, (this.changeFlags & FLAG_DRAWABLESELECTEDSTATE) != 0);
                                    } else {
                                        background.setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
                                    }
                                } else if (background instanceof CheckBox) {
                                    if ((this.changeFlags & FLAG_CHECKBOX) != 0) {
                                        ((CheckBox) background).setBackgroundColor(i);
                                    } else if ((this.changeFlags & FLAG_CHECKBOXCHECK) != 0) {
                                        ((CheckBox) background).setCheckColor(i);
                                    }
                                } else if (background instanceof GroupCreateCheckBox) {
                                    ((GroupCreateCheckBox) background).updateColors();
                                } else if (background instanceof Integer) {
                                    field2.set(view, Integer.valueOf(i));
                                } else if (background instanceof RadioButton) {
                                    if ((this.changeFlags & FLAG_CHECKBOX) != 0) {
                                        ((RadioButton) background).setBackgroundColor(i);
                                        ((RadioButton) background).invalidate();
                                    } else if ((this.changeFlags & FLAG_CHECKBOXCHECK) != 0) {
                                        ((RadioButton) background).setCheckedColor(i);
                                        ((RadioButton) background).invalidate();
                                    }
                                } else if (background instanceof TextPaint) {
                                    if ((this.changeFlags & FLAG_LINKCOLOR) != 0) {
                                        ((TextPaint) background).linkColor = i;
                                    } else {
                                        ((TextPaint) background).setColor(i);
                                    }
                                } else if (background instanceof LineProgressView) {
                                    if ((this.changeFlags & FLAG_PROGRESSBAR) != 0) {
                                        ((LineProgressView) background).setProgressColor(i);
                                    } else {
                                        ((LineProgressView) background).setBackColor(i);
                                    }
                                } else if (background instanceof Paint) {
                                    ((Paint) background).setColor(i);
                                }
                            } catch (Throwable th) {
                                FileLog.e(th);
                            }
                        }
                    }
                } else if (view instanceof GroupCreateSpan) {
                    ((GroupCreateSpan) view).updateColors();
                }
            }
        }
    }

    public int getCurrentColor() {
        return this.currentColor;
    }

    public String getCurrentKey() {
        return this.currentKey;
    }

    public int getSetColor() {
        return Theme.getColor(this.currentKey);
    }

    public String getTitle() {
        return this.currentKey;
    }

    public void setColor(int i, boolean z) {
        int i2;
        Drawable background;
        int i3 = 0;
        Theme.setColor(this.currentKey, i, z);
        if (this.paintToUpdate != null) {
            i2 = 0;
            while (i2 < this.paintToUpdate.length) {
                if ((this.changeFlags & FLAG_LINKCOLOR) == 0 || !(this.paintToUpdate[i2] instanceof TextPaint)) {
                    this.paintToUpdate[i2].setColor(i);
                } else {
                    ((TextPaint) this.paintToUpdate[i2]).linkColor = i;
                }
                i2++;
            }
        }
        if (this.drawablesToUpdate != null) {
            for (i2 = 0; i2 < this.drawablesToUpdate.length; i2++) {
                if (this.drawablesToUpdate[i2] != null) {
                    if (this.drawablesToUpdate[i2] instanceof CombinedDrawable) {
                        if ((this.changeFlags & FLAG_BACKGROUNDFILTER) != 0) {
                            ((CombinedDrawable) this.drawablesToUpdate[i2]).getBackground().setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
                        } else {
                            ((CombinedDrawable) this.drawablesToUpdate[i2]).getIcon().setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
                        }
                    } else if (this.drawablesToUpdate[i2] instanceof AvatarDrawable) {
                        ((AvatarDrawable) this.drawablesToUpdate[i2]).setColor(i);
                    } else {
                        this.drawablesToUpdate[i2].setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
                    }
                }
            }
        }
        if (this.viewToInvalidate != null && this.listClasses == null && this.listClassesFieldName == null && ((this.changeFlags & FLAG_CHECKTAG) == 0 || ((this.changeFlags & FLAG_CHECKTAG) != 0 && this.currentKey.equals(this.viewToInvalidate.getTag())))) {
            if ((this.changeFlags & FLAG_BACKGROUND) != 0) {
                this.viewToInvalidate.setBackgroundColor(i);
            }
            if ((this.changeFlags & FLAG_BACKGROUNDFILTER) != 0) {
                Drawable background2 = this.viewToInvalidate.getBackground();
                background = background2 instanceof CombinedDrawable ? (this.changeFlags & FLAG_DRAWABLESELECTEDSTATE) != 0 ? ((CombinedDrawable) background2).getBackground() : ((CombinedDrawable) background2).getIcon() : background2;
                if (background != null) {
                    if ((background instanceof StateListDrawable) || (VERSION.SDK_INT >= 21 && (background instanceof RippleDrawable))) {
                        Theme.setSelectorDrawableColor(background, i, (this.changeFlags & FLAG_DRAWABLESELECTEDSTATE) != 0);
                    } else {
                        background.setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
                    }
                }
            }
        }
        if (this.viewToInvalidate instanceof ActionBar) {
            if ((this.changeFlags & FLAG_AB_ITEMSCOLOR) != 0) {
                ((ActionBar) this.viewToInvalidate).setItemsColor(i, false);
            }
            if ((this.changeFlags & FLAG_AB_TITLECOLOR) != 0) {
                ((ActionBar) this.viewToInvalidate).setTitleColor(i);
            }
            if ((this.changeFlags & FLAG_AB_SELECTORCOLOR) != 0) {
                ((ActionBar) this.viewToInvalidate).setItemsBackgroundColor(i, false);
            }
            if ((this.changeFlags & FLAG_AB_AM_SELECTORCOLOR) != 0) {
                ((ActionBar) this.viewToInvalidate).setItemsBackgroundColor(i, true);
            }
            if ((this.changeFlags & FLAG_AB_AM_ITEMSCOLOR) != 0) {
                ((ActionBar) this.viewToInvalidate).setItemsColor(i, true);
            }
            if ((this.changeFlags & FLAG_AB_SUBTITLECOLOR) != 0) {
                ((ActionBar) this.viewToInvalidate).setSubtitleColor(i);
            }
            if ((this.changeFlags & FLAG_AB_AM_BACKGROUND) != 0) {
                ((ActionBar) this.viewToInvalidate).setActionModeColor(i);
            }
            if ((this.changeFlags & FLAG_AB_AM_TOPBACKGROUND) != 0) {
                ((ActionBar) this.viewToInvalidate).setActionModeTopColor(i);
            }
            if ((this.changeFlags & FLAG_AB_SEARCHPLACEHOLDER) != 0) {
                ((ActionBar) this.viewToInvalidate).setSearchTextColor(i, true);
            }
            if ((this.changeFlags & FLAG_AB_SEARCH) != 0) {
                ((ActionBar) this.viewToInvalidate).setSearchTextColor(i, false);
            }
            if ((this.changeFlags & FLAG_AB_SUBMENUITEM) != 0) {
                ((ActionBar) this.viewToInvalidate).setPopupItemsColor(i);
            }
            if ((this.changeFlags & FLAG_AB_SUBMENUBACKGROUND) != 0) {
                ((ActionBar) this.viewToInvalidate).setPopupBackgroundColor(i);
            }
        }
        if (this.viewToInvalidate instanceof EmptyTextProgressView) {
            if ((this.changeFlags & FLAG_TEXTCOLOR) != 0) {
                ((EmptyTextProgressView) this.viewToInvalidate).setTextColor(i);
            } else if ((this.changeFlags & FLAG_PROGRESSBAR) != 0) {
                ((EmptyTextProgressView) this.viewToInvalidate).setProgressBarColor(i);
            }
        }
        if (this.viewToInvalidate instanceof RadialProgressView) {
            ((RadialProgressView) this.viewToInvalidate).setProgressColor(i);
        } else if (this.viewToInvalidate instanceof LineProgressView) {
            if ((this.changeFlags & FLAG_PROGRESSBAR) != 0) {
                ((LineProgressView) this.viewToInvalidate).setProgressColor(i);
            } else {
                ((LineProgressView) this.viewToInvalidate).setBackColor(i);
            }
        } else if (this.viewToInvalidate instanceof ContextProgressView) {
            ((ContextProgressView) this.viewToInvalidate).updateColors();
        }
        if ((this.changeFlags & FLAG_TEXTCOLOR) != 0 && ((this.changeFlags & FLAG_CHECKTAG) == 0 || !(this.viewToInvalidate == null || (this.changeFlags & FLAG_CHECKTAG) == 0 || !this.currentKey.equals(this.viewToInvalidate.getTag())))) {
            if (this.viewToInvalidate instanceof TextView) {
                ((TextView) this.viewToInvalidate).setTextColor(i);
            } else if (this.viewToInvalidate instanceof NumberTextView) {
                ((NumberTextView) this.viewToInvalidate).setTextColor(i);
            } else if (this.viewToInvalidate instanceof SimpleTextView) {
                ((SimpleTextView) this.viewToInvalidate).setTextColor(i);
            } else if (this.viewToInvalidate instanceof ChatBigEmptyView) {
                ((ChatBigEmptyView) this.viewToInvalidate).setTextColor(i);
            }
        }
        if ((this.changeFlags & FLAG_CURSORCOLOR) != 0 && (this.viewToInvalidate instanceof EditTextBoldCursor)) {
            ((EditTextBoldCursor) this.viewToInvalidate).setCursorColor(i);
        }
        if ((this.changeFlags & FLAG_HINTTEXTCOLOR) != 0) {
            if (this.viewToInvalidate instanceof EditTextBoldCursor) {
                ((EditTextBoldCursor) this.viewToInvalidate).setHintColor(i);
            } else if (this.viewToInvalidate instanceof EditText) {
                ((EditText) this.viewToInvalidate).setHintTextColor(i);
            }
        }
        if (!(this.viewToInvalidate == null || (this.changeFlags & FLAG_SERVICEBACKGROUND) == 0)) {
            background2 = this.viewToInvalidate.getBackground();
            if (background2 != null) {
                background2.setColorFilter(Theme.colorFilter);
            }
        }
        if ((this.changeFlags & FLAG_IMAGECOLOR) != 0 && ((this.changeFlags & FLAG_CHECKTAG) == 0 || ((this.changeFlags & FLAG_CHECKTAG) != 0 && this.currentKey.equals(this.viewToInvalidate.getTag())))) {
            if (this.viewToInvalidate instanceof ImageView) {
                if ((this.changeFlags & FLAG_USEBACKGROUNDDRAWABLE) != 0) {
                    background = ((ImageView) this.viewToInvalidate).getDrawable();
                    if ((background instanceof StateListDrawable) || (VERSION.SDK_INT >= 21 && (background instanceof RippleDrawable))) {
                        Theme.setSelectorDrawableColor(background, i, (this.changeFlags & FLAG_DRAWABLESELECTEDSTATE) != 0);
                    }
                } else {
                    ((ImageView) this.viewToInvalidate).setColorFilter(new PorterDuffColorFilter(i, Mode.MULTIPLY));
                }
            } else if (this.viewToInvalidate instanceof BackupImageView) {
            }
        }
        if ((this.viewToInvalidate instanceof ScrollView) && (this.changeFlags & FLAG_LISTGLOWCOLOR) != 0) {
            AndroidUtilities.setScrollViewEdgeEffectColor((ScrollView) this.viewToInvalidate, i);
        }
        if (this.viewToInvalidate instanceof RecyclerListView) {
            RecyclerListView recyclerListView = (RecyclerListView) this.viewToInvalidate;
            if ((this.changeFlags & FLAG_SELECTOR) != 0 && this.currentKey.equals(Theme.key_listSelector)) {
                recyclerListView.setListSelectorColor(i);
            }
            if ((this.changeFlags & FLAG_FASTSCROLL) != 0) {
                recyclerListView.updateFastScrollColors();
            }
            if ((this.changeFlags & FLAG_LISTGLOWCOLOR) != 0) {
                recyclerListView.setGlowColor(i);
            }
            if ((this.changeFlags & FLAG_SECTIONS) != 0) {
                int i4;
                ArrayList headers = recyclerListView.getHeaders();
                if (headers != null) {
                    for (i4 = 0; i4 < headers.size(); i4++) {
                        processViewColor((View) headers.get(i4), i);
                    }
                }
                headers = recyclerListView.getHeadersCache();
                if (headers != null) {
                    for (i4 = 0; i4 < headers.size(); i4++) {
                        processViewColor((View) headers.get(i4), i);
                    }
                }
                View pinnedHeader = recyclerListView.getPinnedHeader();
                if (pinnedHeader != null) {
                    processViewColor(pinnedHeader, i);
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
                ViewGroup viewGroup = (ViewGroup) this.viewToInvalidate;
                i2 = viewGroup.getChildCount();
                while (i3 < i2) {
                    processViewColor(viewGroup.getChildAt(i3), i);
                    i3++;
                }
            }
            processViewColor(this.viewToInvalidate, i);
        }
        this.currentColor = i;
        if (this.delegate != null) {
            this.delegate.didSetColor(i);
        }
        if (this.viewToInvalidate != null) {
            this.viewToInvalidate.invalidate();
        }
    }

    public void setDefaultColor() {
        setColor(Theme.getDefaultColor(this.currentKey), true);
    }

    public void setPreviousColor() {
        setColor(this.previousColor, this.previousIsDefault[0]);
    }

    public void startEditing() {
        int color = Theme.getColor(this.currentKey, this.previousIsDefault);
        this.previousColor = color;
        this.currentColor = color;
    }
}
