package android.support.v7.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompatBase.Action;
import android.support.v7.appcompat.C0299R;
import android.widget.RemoteViews;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@TargetApi(9)
@RequiresApi(9)
class NotificationCompatImplBase {
    private static final int MAX_ACTION_BUTTONS = 3;
    static final int MAX_MEDIA_BUTTONS = 5;
    static final int MAX_MEDIA_BUTTONS_IN_COMPACT = 3;

    NotificationCompatImplBase() {
    }

    @TargetApi(11)
    @RequiresApi(11)
    public static <T extends Action> RemoteViews overrideContentViewMedia(NotificationBuilderWithBuilderAccessor builder, Context context, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, int number, Bitmap largeIcon, CharSequence subText, boolean useChronometer, long when, int priority, List<T> actions, int[] actionsToShowInCompact, boolean showCancelButton, PendingIntent cancelButtonIntent, boolean isDecoratedCustomView) {
        RemoteViews views = generateContentViewMedia(context, contentTitle, contentText, contentInfo, number, largeIcon, subText, useChronometer, when, priority, actions, actionsToShowInCompact, showCancelButton, cancelButtonIntent, isDecoratedCustomView);
        builder.getBuilder().setContent(views);
        if (showCancelButton) {
            builder.getBuilder().setOngoing(true);
        }
        return views;
    }

    @TargetApi(11)
    @RequiresApi(11)
    private static <T extends Action> RemoteViews generateContentViewMedia(Context context, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, int number, Bitmap largeIcon, CharSequence subText, boolean useChronometer, long when, int priority, List<T> actions, int[] actionsToShowInCompact, boolean showCancelButton, PendingIntent cancelButtonIntent, boolean isDecoratedCustomView) {
        int N;
        RemoteViews view = applyStandardTemplate(context, contentTitle, contentText, contentInfo, number, 0, largeIcon, subText, useChronometer, when, priority, 0, isDecoratedCustomView ? C0299R.layout.notification_template_media_custom : C0299R.layout.notification_template_media, true);
        int numActions = actions.size();
        if (actionsToShowInCompact == null) {
            N = 0;
        } else {
            N = Math.min(actionsToShowInCompact.length, 3);
        }
        view.removeAllViews(C0299R.id.media_actions);
        if (N > 0) {
            for (int i = 0; i < N; i++) {
                if (i >= numActions) {
                    throw new IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", new Object[]{Integer.valueOf(i), Integer.valueOf(numActions - 1)}));
                }
                view.addView(C0299R.id.media_actions, generateMediaActionButton(context, (Action) actions.get(actionsToShowInCompact[i])));
            }
        }
        if (showCancelButton) {
            view.setViewVisibility(C0299R.id.end_padder, 8);
            view.setViewVisibility(C0299R.id.cancel_action, 0);
            view.setOnClickPendingIntent(C0299R.id.cancel_action, cancelButtonIntent);
            view.setInt(C0299R.id.cancel_action, "setAlpha", context.getResources().getInteger(C0299R.integer.cancel_button_image_alpha));
        } else {
            view.setViewVisibility(C0299R.id.end_padder, 0);
            view.setViewVisibility(C0299R.id.cancel_action, 8);
        }
        return view;
    }

    @TargetApi(16)
    @RequiresApi(16)
    public static <T extends Action> void overrideMediaBigContentView(Notification n, Context context, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, int number, Bitmap largeIcon, CharSequence subText, boolean useChronometer, long when, int priority, int color, List<T> actions, boolean showCancelButton, PendingIntent cancelButtonIntent, boolean decoratedCustomView) {
        n.bigContentView = generateMediaBigView(context, contentTitle, contentText, contentInfo, number, largeIcon, subText, useChronometer, when, priority, color, actions, showCancelButton, cancelButtonIntent, decoratedCustomView);
        if (showCancelButton) {
            n.flags |= 2;
        }
    }

    @TargetApi(11)
    @RequiresApi(11)
    public static <T extends Action> RemoteViews generateMediaBigView(Context context, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, int number, Bitmap largeIcon, CharSequence subText, boolean useChronometer, long when, int priority, int color, List<T> actions, boolean showCancelButton, PendingIntent cancelButtonIntent, boolean decoratedCustomView) {
        int actionCount = Math.min(actions.size(), 5);
        RemoteViews big = applyStandardTemplate(context, contentTitle, contentText, contentInfo, number, 0, largeIcon, subText, useChronometer, when, priority, color, getBigMediaLayoutResource(decoratedCustomView, actionCount), false);
        big.removeAllViews(C0299R.id.media_actions);
        if (actionCount > 0) {
            for (int i = 0; i < actionCount; i++) {
                RemoteViews button = generateMediaActionButton(context, (Action) actions.get(i));
                big.addView(C0299R.id.media_actions, button);
            }
        }
        if (showCancelButton) {
            big.setViewVisibility(C0299R.id.cancel_action, 0);
            big.setInt(C0299R.id.cancel_action, "setAlpha", context.getResources().getInteger(C0299R.integer.cancel_button_image_alpha));
            big.setOnClickPendingIntent(C0299R.id.cancel_action, cancelButtonIntent);
        } else {
            big.setViewVisibility(C0299R.id.cancel_action, 8);
        }
        return big;
    }

    @TargetApi(11)
    @RequiresApi(11)
    private static RemoteViews generateMediaActionButton(Context context, Action action) {
        boolean tombstone = action.getActionIntent() == null;
        RemoteViews button = new RemoteViews(context.getPackageName(), C0299R.layout.notification_media_action);
        button.setImageViewResource(C0299R.id.action0, action.getIcon());
        if (!tombstone) {
            button.setOnClickPendingIntent(C0299R.id.action0, action.getActionIntent());
        }
        if (VERSION.SDK_INT >= 15) {
            button.setContentDescription(C0299R.id.action0, action.getTitle());
        }
        return button;
    }

    @TargetApi(11)
    @RequiresApi(11)
    private static int getBigMediaLayoutResource(boolean decoratedCustomView, int actionCount) {
        return actionCount <= 3 ? decoratedCustomView ? C0299R.layout.notification_template_big_media_narrow_custom : C0299R.layout.notification_template_big_media_narrow : decoratedCustomView ? C0299R.layout.notification_template_big_media_custom : C0299R.layout.notification_template_big_media;
    }

    public static RemoteViews applyStandardTemplateWithActions(Context context, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, int number, int smallIcon, Bitmap largeIcon, CharSequence subText, boolean useChronometer, long when, int priority, int color, int resId, boolean fitIn1U, ArrayList<NotificationCompat.Action> actions) {
        RemoteViews remoteViews = applyStandardTemplate(context, contentTitle, contentText, contentInfo, number, smallIcon, largeIcon, subText, useChronometer, when, priority, color, resId, fitIn1U);
        remoteViews.removeAllViews(C0299R.id.actions);
        boolean actionsVisible = false;
        if (actions != null) {
            int N = actions.size();
            if (N > 0) {
                actionsVisible = true;
                if (N > 3) {
                    N = 3;
                }
                for (int i = 0; i < N; i++) {
                    remoteViews.addView(C0299R.id.actions, generateActionButton(context, (NotificationCompat.Action) actions.get(i)));
                }
            }
        }
        int actionVisibility = actionsVisible ? 0 : 8;
        remoteViews.setViewVisibility(C0299R.id.actions, actionVisibility);
        remoteViews.setViewVisibility(C0299R.id.action_divider, actionVisibility);
        return remoteViews;
    }

    private static RemoteViews generateActionButton(Context context, NotificationCompat.Action action) {
        int actionTombstoneLayoutResource;
        boolean tombstone = action.actionIntent == null;
        String packageName = context.getPackageName();
        if (tombstone) {
            actionTombstoneLayoutResource = getActionTombstoneLayoutResource();
        } else {
            actionTombstoneLayoutResource = getActionLayoutResource();
        }
        RemoteViews button = new RemoteViews(packageName, actionTombstoneLayoutResource);
        button.setImageViewBitmap(C0299R.id.action_image, createColoredBitmap(context, action.getIcon(), context.getResources().getColor(C0299R.color.notification_action_color_filter)));
        button.setTextViewText(C0299R.id.action_text, action.title);
        if (!tombstone) {
            button.setOnClickPendingIntent(C0299R.id.action_container, action.actionIntent);
        }
        if (VERSION.SDK_INT >= 15) {
            button.setContentDescription(C0299R.id.action_container, action.title);
        }
        return button;
    }

    private static Bitmap createColoredBitmap(Context context, int iconId, int color) {
        return createColoredBitmap(context, iconId, color, 0);
    }

    private static Bitmap createColoredBitmap(Context context, int iconId, int color, int size) {
        int width;
        int height;
        Drawable drawable = context.getResources().getDrawable(iconId);
        if (size == 0) {
            width = drawable.getIntrinsicWidth();
        } else {
            width = size;
        }
        if (size == 0) {
            height = drawable.getIntrinsicHeight();
        } else {
            height = size;
        }
        Bitmap resultBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        drawable.setBounds(0, 0, width, height);
        if (color != 0) {
            drawable.mutate().setColorFilter(new PorterDuffColorFilter(color, Mode.SRC_IN));
        }
        drawable.draw(new Canvas(resultBitmap));
        return resultBitmap;
    }

    private static int getActionLayoutResource() {
        return C0299R.layout.notification_action;
    }

    private static int getActionTombstoneLayoutResource() {
        return C0299R.layout.notification_action_tombstone;
    }

    public static RemoteViews applyStandardTemplate(Context context, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, int number, int smallIcon, Bitmap largeIcon, CharSequence subText, boolean useChronometer, long when, int priority, int color, int resId, boolean fitIn1U) {
        Resources res = context.getResources();
        RemoteViews contentView = new RemoteViews(context.getPackageName(), resId);
        boolean showLine3 = false;
        boolean showLine2 = false;
        boolean minPriority = priority < -1;
        if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT < 21) {
            if (minPriority) {
                contentView.setInt(C0299R.id.notification_background, "setBackgroundResource", C0299R.drawable.notification_bg_low);
                contentView.setInt(C0299R.id.icon, "setBackgroundResource", C0299R.drawable.notification_template_icon_low_bg);
            } else {
                contentView.setInt(C0299R.id.notification_background, "setBackgroundResource", C0299R.drawable.notification_bg);
                contentView.setInt(C0299R.id.icon, "setBackgroundResource", C0299R.drawable.notification_template_icon_bg);
            }
        }
        if (largeIcon != null) {
            if (VERSION.SDK_INT >= 16) {
                contentView.setViewVisibility(C0299R.id.icon, 0);
                contentView.setImageViewBitmap(C0299R.id.icon, largeIcon);
            } else {
                contentView.setViewVisibility(C0299R.id.icon, 8);
            }
            if (smallIcon != 0) {
                int backgroundSize = res.getDimensionPixelSize(C0299R.dimen.notification_right_icon_size);
                int iconSize = backgroundSize - (res.getDimensionPixelSize(C0299R.dimen.notification_small_icon_background_padding) * 2);
                if (VERSION.SDK_INT >= 21) {
                    contentView.setImageViewBitmap(C0299R.id.right_icon, createIconWithBackground(context, smallIcon, backgroundSize, iconSize, color));
                } else {
                    contentView.setImageViewBitmap(C0299R.id.right_icon, createColoredBitmap(context, smallIcon, -1));
                }
                contentView.setViewVisibility(C0299R.id.right_icon, 0);
            }
        } else if (smallIcon != 0) {
            contentView.setViewVisibility(C0299R.id.icon, 0);
            if (VERSION.SDK_INT >= 21) {
                contentView.setImageViewBitmap(C0299R.id.icon, createIconWithBackground(context, smallIcon, res.getDimensionPixelSize(C0299R.dimen.notification_large_icon_width) - res.getDimensionPixelSize(C0299R.dimen.notification_big_circle_margin), res.getDimensionPixelSize(C0299R.dimen.notification_small_icon_size_as_large), color));
            } else {
                contentView.setImageViewBitmap(C0299R.id.icon, createColoredBitmap(context, smallIcon, -1));
            }
        }
        if (contentTitle != null) {
            contentView.setTextViewText(C0299R.id.title, contentTitle);
        }
        if (contentText != null) {
            contentView.setTextViewText(C0299R.id.text, contentText);
            showLine3 = true;
        }
        boolean hasRightSide = VERSION.SDK_INT < 21 && largeIcon != null;
        if (contentInfo != null) {
            contentView.setTextViewText(C0299R.id.info, contentInfo);
            contentView.setViewVisibility(C0299R.id.info, 0);
            showLine3 = true;
            hasRightSide = true;
        } else if (number > 0) {
            if (number > res.getInteger(C0299R.integer.status_bar_notification_info_maxnum)) {
                contentView.setTextViewText(C0299R.id.info, res.getString(C0299R.string.status_bar_notification_info_overflow));
            } else {
                contentView.setTextViewText(C0299R.id.info, NumberFormat.getIntegerInstance().format((long) number));
            }
            contentView.setViewVisibility(C0299R.id.info, 0);
            showLine3 = true;
            hasRightSide = true;
        } else {
            contentView.setViewVisibility(C0299R.id.info, 8);
        }
        if (subText != null && VERSION.SDK_INT >= 16) {
            contentView.setTextViewText(C0299R.id.text, subText);
            if (contentText != null) {
                contentView.setTextViewText(C0299R.id.text2, contentText);
                contentView.setViewVisibility(C0299R.id.text2, 0);
                showLine2 = true;
            } else {
                contentView.setViewVisibility(C0299R.id.text2, 8);
            }
        }
        if (showLine2 && VERSION.SDK_INT >= 16) {
            if (fitIn1U) {
                contentView.setTextViewTextSize(C0299R.id.text, 0, (float) res.getDimensionPixelSize(C0299R.dimen.notification_subtext_size));
            }
            contentView.setViewPadding(C0299R.id.line1, 0, 0, 0, 0);
        }
        if (when != 0) {
            if (!useChronometer || VERSION.SDK_INT < 16) {
                contentView.setViewVisibility(C0299R.id.time, 0);
                contentView.setLong(C0299R.id.time, "setTime", when);
            } else {
                contentView.setViewVisibility(C0299R.id.chronometer, 0);
                contentView.setLong(C0299R.id.chronometer, "setBase", (SystemClock.elapsedRealtime() - System.currentTimeMillis()) + when);
                contentView.setBoolean(C0299R.id.chronometer, "setStarted", true);
            }
            hasRightSide = true;
        }
        contentView.setViewVisibility(C0299R.id.right_side, hasRightSide ? 0 : 8);
        contentView.setViewVisibility(C0299R.id.line3, showLine3 ? 0 : 8);
        return contentView;
    }

    public static Bitmap createIconWithBackground(Context ctx, int iconId, int size, int iconSize, int color) {
        int i = C0299R.drawable.notification_icon_background;
        if (color == 0) {
            color = 0;
        }
        Bitmap coloredBitmap = createColoredBitmap(ctx, i, color, size);
        Canvas canvas = new Canvas(coloredBitmap);
        Drawable icon = ctx.getResources().getDrawable(iconId).mutate();
        icon.setFilterBitmap(true);
        int inset = (size - iconSize) / 2;
        icon.setBounds(inset, inset, iconSize + inset, iconSize + inset);
        icon.setColorFilter(new PorterDuffColorFilter(-1, Mode.SRC_ATOP));
        icon.draw(canvas);
        return coloredBitmap;
    }

    public static void buildIntoRemoteViews(Context ctx, RemoteViews outerView, RemoteViews innerView) {
        hideNormalContent(outerView);
        outerView.removeAllViews(C0299R.id.notification_main_column);
        outerView.addView(C0299R.id.notification_main_column, innerView.clone());
        outerView.setViewVisibility(C0299R.id.notification_main_column, 0);
        if (VERSION.SDK_INT >= 21) {
            outerView.setViewPadding(C0299R.id.notification_main_column_container, 0, calculateTopPadding(ctx), 0, 0);
        }
    }

    private static void hideNormalContent(RemoteViews outerView) {
        outerView.setViewVisibility(C0299R.id.title, 8);
        outerView.setViewVisibility(C0299R.id.text2, 8);
        outerView.setViewVisibility(C0299R.id.text, 8);
    }

    public static int calculateTopPadding(Context ctx) {
        float largeFactor = (constrain(ctx.getResources().getConfiguration().fontScale, 1.0f, 1.3f) - 1.0f) / 0.29999995f;
        return Math.round(((1.0f - largeFactor) * ((float) ctx.getResources().getDimensionPixelSize(C0299R.dimen.notification_top_pad))) + (((float) ctx.getResources().getDimensionPixelSize(C0299R.dimen.notification_top_pad_large_text)) * largeFactor));
    }

    public static float constrain(float amount, float low, float high) {
        if (amount < low) {
            return low;
        }
        return amount > high ? high : amount;
    }
}
