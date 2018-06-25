package org.telegram.messenger.support.customtabs;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.BundleCompat;
import android.widget.RemoteViews;
import java.util.ArrayList;

public final class CustomTabsIntent$Builder {
    private ArrayList<Bundle> mActionButtons;
    private boolean mInstantAppsEnabled;
    private final Intent mIntent;
    private ArrayList<Bundle> mMenuItems;
    private Bundle mStartAnimationBundle;

    public CustomTabsIntent$Builder() {
        this(null);
    }

    public CustomTabsIntent$Builder(@Nullable CustomTabsSession session) {
        IBinder iBinder = null;
        this.mIntent = new Intent("android.intent.action.VIEW");
        this.mMenuItems = null;
        this.mStartAnimationBundle = null;
        this.mActionButtons = null;
        this.mInstantAppsEnabled = true;
        if (session != null) {
            this.mIntent.setPackage(session.getComponentName().getPackageName());
        }
        Bundle bundle = new Bundle();
        String str = "android.support.customtabs.extra.SESSION";
        if (session != null) {
            iBinder = session.getBinder();
        }
        BundleCompat.putBinder(bundle, str, iBinder);
        this.mIntent.putExtras(bundle);
    }

    public CustomTabsIntent$Builder setToolbarColor(@ColorInt int color) {
        this.mIntent.putExtra("android.support.customtabs.extra.TOOLBAR_COLOR", color);
        return this;
    }

    public CustomTabsIntent$Builder enableUrlBarHiding() {
        this.mIntent.putExtra("android.support.customtabs.extra.ENABLE_URLBAR_HIDING", true);
        return this;
    }

    public CustomTabsIntent$Builder setCloseButtonIcon(@NonNull Bitmap icon) {
        this.mIntent.putExtra("android.support.customtabs.extra.CLOSE_BUTTON_ICON", icon);
        return this;
    }

    public CustomTabsIntent$Builder setShowTitle(boolean showTitle) {
        this.mIntent.putExtra("android.support.customtabs.extra.TITLE_VISIBILITY", showTitle ? 1 : 0);
        return this;
    }

    public CustomTabsIntent$Builder addMenuItem(@NonNull String label, @NonNull PendingIntent pendingIntent) {
        if (this.mMenuItems == null) {
            this.mMenuItems = new ArrayList();
        }
        Bundle bundle = new Bundle();
        bundle.putString("android.support.customtabs.customaction.MENU_ITEM_TITLE", label);
        bundle.putParcelable("android.support.customtabs.customaction.PENDING_INTENT", pendingIntent);
        this.mMenuItems.add(bundle);
        return this;
    }

    public CustomTabsIntent$Builder addDefaultShareMenuItem() {
        this.mIntent.putExtra("android.support.customtabs.extra.SHARE_MENU_ITEM", true);
        return this;
    }

    public CustomTabsIntent$Builder setActionButton(@NonNull Bitmap icon, @NonNull String description, @NonNull PendingIntent pendingIntent, boolean shouldTint) {
        Bundle bundle = new Bundle();
        bundle.putInt("android.support.customtabs.customaction.ID", 0);
        bundle.putParcelable("android.support.customtabs.customaction.ICON", icon);
        bundle.putString("android.support.customtabs.customaction.DESCRIPTION", description);
        bundle.putParcelable("android.support.customtabs.customaction.PENDING_INTENT", pendingIntent);
        this.mIntent.putExtra("android.support.customtabs.extra.ACTION_BUTTON_BUNDLE", bundle);
        this.mIntent.putExtra("android.support.customtabs.extra.TINT_ACTION_BUTTON", shouldTint);
        return this;
    }

    public CustomTabsIntent$Builder setActionButton(@NonNull Bitmap icon, @NonNull String description, @NonNull PendingIntent pendingIntent) {
        return setActionButton(icon, description, pendingIntent, false);
    }

    @Deprecated
    public CustomTabsIntent$Builder addToolbarItem(int id, @NonNull Bitmap icon, @NonNull String description, PendingIntent pendingIntent) throws IllegalStateException {
        if (this.mActionButtons == null) {
            this.mActionButtons = new ArrayList();
        }
        if (this.mActionButtons.size() >= 5) {
            throw new IllegalStateException("Exceeded maximum toolbar item count of 5");
        }
        Bundle bundle = new Bundle();
        bundle.putInt("android.support.customtabs.customaction.ID", id);
        bundle.putParcelable("android.support.customtabs.customaction.ICON", icon);
        bundle.putString("android.support.customtabs.customaction.DESCRIPTION", description);
        bundle.putParcelable("android.support.customtabs.customaction.PENDING_INTENT", pendingIntent);
        this.mActionButtons.add(bundle);
        return this;
    }

    public CustomTabsIntent$Builder setSecondaryToolbarColor(@ColorInt int color) {
        this.mIntent.putExtra("android.support.customtabs.extra.SECONDARY_TOOLBAR_COLOR", color);
        return this;
    }

    public CustomTabsIntent$Builder setSecondaryToolbarViews(@NonNull RemoteViews remoteViews, @Nullable int[] clickableIDs, @Nullable PendingIntent pendingIntent) {
        this.mIntent.putExtra("android.support.customtabs.extra.EXTRA_REMOTEVIEWS", remoteViews);
        this.mIntent.putExtra("android.support.customtabs.extra.EXTRA_REMOTEVIEWS_VIEW_IDS", clickableIDs);
        this.mIntent.putExtra("android.support.customtabs.extra.EXTRA_REMOTEVIEWS_PENDINGINTENT", pendingIntent);
        return this;
    }

    public CustomTabsIntent$Builder setInstantAppsEnabled(boolean enabled) {
        this.mInstantAppsEnabled = enabled;
        return this;
    }

    public CustomTabsIntent$Builder setStartAnimations(@NonNull Context context, @AnimRes int enterResId, @AnimRes int exitResId) {
        this.mStartAnimationBundle = ActivityOptionsCompat.makeCustomAnimation(context, enterResId, exitResId).toBundle();
        return this;
    }

    public CustomTabsIntent$Builder setExitAnimations(@NonNull Context context, @AnimRes int enterResId, @AnimRes int exitResId) {
        this.mIntent.putExtra("android.support.customtabs.extra.EXIT_ANIMATION_BUNDLE", ActivityOptionsCompat.makeCustomAnimation(context, enterResId, exitResId).toBundle());
        return this;
    }

    public CustomTabsIntent build() {
        if (this.mMenuItems != null) {
            this.mIntent.putParcelableArrayListExtra("android.support.customtabs.extra.MENU_ITEMS", this.mMenuItems);
        }
        if (this.mActionButtons != null) {
            this.mIntent.putParcelableArrayListExtra("android.support.customtabs.extra.TOOLBAR_ITEMS", this.mActionButtons);
        }
        this.mIntent.putExtra("android.support.customtabs.extra.EXTRA_ENABLE_INSTANT_APPS", this.mInstantAppsEnabled);
        return new CustomTabsIntent(this.mIntent, this.mStartAnimationBundle, null);
    }
}
