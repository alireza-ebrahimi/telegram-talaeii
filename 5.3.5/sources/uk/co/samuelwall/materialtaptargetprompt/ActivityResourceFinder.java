package uk.co.samuelwall.materialtaptargetprompt;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.annotation.StyleableRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class ActivityResourceFinder implements ResourceFinder {
    private final Activity mActivity;

    public ActivityResourceFinder(Activity activity) {
        this.mActivity = activity;
    }

    public View findViewById(@IdRes int resId) {
        return this.mActivity.findViewById(resId);
    }

    public Window getWindow() {
        return this.mActivity.getWindow();
    }

    public ViewGroup getPromptParentView() {
        return (ViewGroup) getWindow().getDecorView();
    }

    public Context getContext() {
        return this.mActivity;
    }

    public Resources getResources() {
        return this.mActivity.getResources();
    }

    public Theme getTheme() {
        return this.mActivity.getTheme();
    }

    @NonNull
    public String getString(@StringRes int resId) {
        return this.mActivity.getString(resId);
    }

    public TypedArray obtainStyledAttributes(@StyleRes int resId, @StyleableRes int[] attrs) {
        return this.mActivity.obtainStyledAttributes(resId, attrs);
    }

    public Drawable getDrawable(@DrawableRes int resId) {
        if (VERSION.SDK_INT >= 21) {
            return this.mActivity.getDrawable(resId);
        }
        return this.mActivity.getResources().getDrawable(resId);
    }
}
