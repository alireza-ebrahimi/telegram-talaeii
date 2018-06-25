package uk.co.samuelwall.materialtaptargetprompt;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.annotation.StyleableRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public interface ResourceFinder {
    @Nullable
    View findViewById(@IdRes int i);

    Context getContext();

    @Nullable
    Drawable getDrawable(@DrawableRes int i);

    ViewGroup getPromptParentView();

    Resources getResources();

    @NonNull
    String getString(@StringRes int i);

    Theme getTheme();

    Window getWindow();

    TypedArray obtainStyledAttributes(@StyleRes int i, @StyleableRes int[] iArr);
}
