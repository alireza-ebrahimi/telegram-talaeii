package com.crashlytics.android.core;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import io.fabric.sdk.android.services.settings.PromptSettingsData;
import java.util.concurrent.CountDownLatch;

class CrashPromptDialog {
    private final Builder dialog;
    private final OptInLatch latch;

    interface AlwaysSendCallback {
        void sendUserReportsWithoutPrompting(boolean z);
    }

    private static class OptInLatch {
        private final CountDownLatch latch;
        private boolean send;

        private OptInLatch() {
            this.send = false;
            this.latch = new CountDownLatch(1);
        }

        void setOptIn(boolean optIn) {
            this.send = optIn;
            this.latch.countDown();
        }

        boolean getOptIn() {
            return this.send;
        }

        void await() {
            try {
                this.latch.await();
            } catch (InterruptedException e) {
            }
        }
    }

    public static CrashPromptDialog create(Activity activity, PromptSettingsData promptData, final AlwaysSendCallback alwaysSendCallback) {
        final OptInLatch latch = new OptInLatch();
        DialogStringResolver stringResolver = new DialogStringResolver(activity, promptData);
        Builder builder = new Builder(activity);
        ScrollView scrollView = createDialogView(activity, stringResolver.getMessage());
        builder.setView(scrollView).setTitle(stringResolver.getTitle()).setCancelable(false).setNeutralButton(stringResolver.getSendButtonTitle(), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                latch.setOptIn(true);
                dialog.dismiss();
            }
        });
        if (promptData.showCancelButton) {
            builder.setNegativeButton(stringResolver.getCancelButtonTitle(), new OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    latch.setOptIn(false);
                    dialog.dismiss();
                }
            });
        }
        if (promptData.showAlwaysSendButton) {
            builder.setPositiveButton(stringResolver.getAlwaysSendButtonTitle(), new OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    alwaysSendCallback.sendUserReportsWithoutPrompting(true);
                    latch.setOptIn(true);
                    dialog.dismiss();
                }
            });
        }
        return new CrashPromptDialog(builder, latch);
    }

    private static ScrollView createDialogView(Activity activity, String message) {
        float density = activity.getResources().getDisplayMetrics().density;
        int textViewPadding = dipsToPixels(density, 5);
        TextView textView = new TextView(activity);
        textView.setAutoLinkMask(15);
        textView.setText(message);
        textView.setTextAppearance(activity, 16973892);
        textView.setPadding(textViewPadding, textViewPadding, textViewPadding, textViewPadding);
        textView.setFocusable(false);
        ScrollView scrollView = new ScrollView(activity);
        scrollView.setPadding(dipsToPixels(density, 14), dipsToPixels(density, 2), dipsToPixels(density, 10), dipsToPixels(density, 12));
        scrollView.addView(textView);
        return scrollView;
    }

    private static int dipsToPixels(float density, int dips) {
        return (int) (((float) dips) * density);
    }

    private CrashPromptDialog(Builder dialog, OptInLatch latch) {
        this.latch = latch;
        this.dialog = dialog;
    }

    public void show() {
        this.dialog.show();
    }

    public void await() {
        this.latch.await();
    }

    public boolean getOptIn() {
        return this.latch.getOptIn();
    }
}
