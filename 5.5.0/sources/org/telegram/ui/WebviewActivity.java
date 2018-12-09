package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.net.URLEncoder;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.SerializedData;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Components.ContextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.ShareAlert;

public class WebviewActivity extends BaseFragment {
    private static final int open_in = 2;
    private static final int share = 1;
    private String currentBot;
    private String currentGame;
    private MessageObject currentMessageObject;
    private String currentUrl;
    private String linkToCopy;
    private ActionBarMenuItem progressItem;
    private ContextProgressView progressView;
    private String short_param;
    public Runnable typingRunnable = new C52961();
    private WebView webView;

    /* renamed from: org.telegram.ui.WebviewActivity$1 */
    class C52961 implements Runnable {
        C52961() {
        }

        public void run() {
            if (WebviewActivity.this.currentMessageObject != null && WebviewActivity.this.getParentActivity() != null && WebviewActivity.this.typingRunnable != null) {
                MessagesController.getInstance().sendTyping(WebviewActivity.this.currentMessageObject.getDialogId(), 6, 0);
                AndroidUtilities.runOnUIThread(WebviewActivity.this.typingRunnable, 25000);
            }
        }
    }

    /* renamed from: org.telegram.ui.WebviewActivity$2 */
    class C52972 extends ActionBarMenuOnItemClick {
        C52972() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                WebviewActivity.this.finishFragment();
            } else if (i == 1) {
                WebviewActivity.this.currentMessageObject.messageOwner.with_my_score = false;
                WebviewActivity.this.showDialog(ShareAlert.createShareAlert(WebviewActivity.this.getParentActivity(), WebviewActivity.this.currentMessageObject, null, false, WebviewActivity.this.linkToCopy, false));
            } else if (i == 2) {
                WebviewActivity.openGameInBrowser(WebviewActivity.this.currentUrl, WebviewActivity.this.currentMessageObject, WebviewActivity.this.getParentActivity(), WebviewActivity.this.short_param, WebviewActivity.this.currentBot);
            }
        }
    }

    /* renamed from: org.telegram.ui.WebviewActivity$3 */
    class C52993 extends WebViewClient {

        /* renamed from: org.telegram.ui.WebviewActivity$3$1 */
        class C52981 extends AnimatorListenerAdapter {
            C52981() {
            }

            public void onAnimationEnd(Animator animator) {
                WebviewActivity.this.progressView.setVisibility(4);
            }
        }

        C52993() {
        }

        private boolean isInternalUrl(String str) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            Uri parse = Uri.parse(str);
            if (!"tg".equals(parse.getScheme())) {
                return false;
            }
            WebviewActivity.this.finishFragment(false);
            try {
                Intent intent = new Intent("android.intent.action.VIEW", parse);
                intent.setComponent(new ComponentName(ApplicationLoader.applicationContext.getPackageName(), LaunchActivity.class.getName()));
                intent.putExtra("com.android.browser.application_id", ApplicationLoader.applicationContext.getPackageName());
                ApplicationLoader.applicationContext.startActivity(intent);
            } catch (Throwable e) {
                FileLog.e(e);
            }
            return true;
        }

        public void onLoadResource(WebView webView, String str) {
            if (!isInternalUrl(str)) {
                super.onLoadResource(webView, str);
            }
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            WebviewActivity.this.progressItem.getImageView().setVisibility(0);
            WebviewActivity.this.progressItem.setEnabled(true);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(WebviewActivity.this.progressView, "scaleX", new float[]{1.0f, 0.1f}), ObjectAnimator.ofFloat(WebviewActivity.this.progressView, "scaleY", new float[]{1.0f, 0.1f}), ObjectAnimator.ofFloat(WebviewActivity.this.progressView, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}), ObjectAnimator.ofFloat(WebviewActivity.this.progressItem.getImageView(), "scaleX", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(WebviewActivity.this.progressItem.getImageView(), "scaleY", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(WebviewActivity.this.progressItem.getImageView(), "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
            animatorSet.addListener(new C52981());
            animatorSet.setDuration(150);
            animatorSet.start();
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            return isInternalUrl(str) || super.shouldOverrideUrlLoading(webView, str);
        }
    }

    public WebviewActivity(String str, String str2, String str3, String str4, MessageObject messageObject) {
        this.currentUrl = str;
        this.currentBot = str2;
        this.currentGame = str3;
        this.currentMessageObject = messageObject;
        this.short_param = str4;
        this.linkToCopy = "https://" + MessagesController.getInstance().linkPrefix + "/" + this.currentBot + (TextUtils.isEmpty(str4) ? TtmlNode.ANONYMOUS_REGION_ID : "?game=" + str4);
    }

    public static void openGameInBrowser(String str, MessageObject messageObject, Activity activity, String str2, String str3) {
        try {
            int i;
            String str4;
            SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("botshare", 0);
            String string = sharedPreferences.getString(TtmlNode.ANONYMOUS_REGION_ID + messageObject.getId(), null);
            CharSequence stringBuilder = new StringBuilder(string != null ? string : TtmlNode.ANONYMOUS_REGION_ID);
            StringBuilder stringBuilder2 = new StringBuilder("tgShareScoreUrl=" + URLEncoder.encode("tgb://share_game_score?hash=", C3446C.UTF8_NAME));
            if (string == null) {
                char[] toCharArray = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
                for (i = 0; i < 20; i++) {
                    stringBuilder.append(toCharArray[Utilities.random.nextInt(toCharArray.length)]);
                }
            }
            stringBuilder2.append(stringBuilder);
            i = str.indexOf(35);
            if (i < 0) {
                str4 = str + "#" + stringBuilder2;
            } else {
                String substring = str.substring(i + 1);
                str4 = (substring.indexOf(61) >= 0 || substring.indexOf(63) >= 0) ? str + "&" + stringBuilder2 : substring.length() > 0 ? str + "?" + stringBuilder2 : str + stringBuilder2;
            }
            Editor edit = sharedPreferences.edit();
            edit.putInt(stringBuilder + "_date", (int) (System.currentTimeMillis() / 1000));
            AbstractSerializedData serializedData = new SerializedData(messageObject.messageOwner.getObjectSize());
            messageObject.messageOwner.serializeToStream(serializedData);
            edit.putString(stringBuilder + "_m", Utilities.bytesToHex(serializedData.toByteArray()));
            edit.putString(stringBuilder + "_link", "https://" + MessagesController.getInstance().linkPrefix + "/" + str3 + (TextUtils.isEmpty(str2) ? TtmlNode.ANONYMOUS_REGION_ID : "?game=" + str2));
            edit.commit();
            Browser.openUrl(activity, str4, false);
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    public static boolean supportWebview() {
        return ("samsung".equals(Build.MANUFACTURER) && "GT-I9500".equals(Build.MODEL)) ? false : true;
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    public View createView(Context context) {
        this.swipeBackEnabled = false;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(this.currentGame);
        this.actionBar.setSubtitle("@" + this.currentBot);
        this.actionBar.setActionBarMenuOnItemClick(new C52972());
        ActionBarMenu createMenu = this.actionBar.createMenu();
        this.progressItem = createMenu.addItemWithWidth(1, R.drawable.share, AndroidUtilities.dp(54.0f));
        this.progressView = new ContextProgressView(context, 1);
        this.progressItem.addView(this.progressView, LayoutHelper.createFrame(-1, -1.0f));
        this.progressItem.getImageView().setVisibility(4);
        createMenu.addItem(0, (int) R.drawable.ic_ab_other).addSubItem(2, LocaleController.getString("OpenInExternalApp", R.string.OpenInExternalApp));
        this.webView = new WebView(context);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setDomStorageEnabled(true);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        if (VERSION.SDK_INT >= 21) {
            this.webView.getSettings().setMixedContentMode(0);
            CookieManager.getInstance().setAcceptThirdPartyCookies(this.webView, true);
            this.webView.addJavascriptInterface(new WebviewActivity$TelegramWebviewProxy(this, null), "TelegramWebviewProxy");
        }
        this.webView.setWebViewClient(new C52993());
        frameLayout.addView(this.webView, LayoutHelper.createFrame(-1, -1.0f));
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[9];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SUBMENUBACKGROUND, null, null, null, null, Theme.key_actionBarDefaultSubmenuBackground);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SUBMENUITEM, null, null, null, null, Theme.key_actionBarDefaultSubmenuItem);
        themeDescriptionArr[7] = new ThemeDescription(this.progressView, 0, null, null, null, null, Theme.key_contextProgressInner2);
        themeDescriptionArr[8] = new ThemeDescription(this.progressView, 0, null, null, null, null, Theme.key_contextProgressOuter2);
        return themeDescriptionArr;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        AndroidUtilities.cancelRunOnUIThread(this.typingRunnable);
        this.typingRunnable = null;
        try {
            ViewParent parent = this.webView.getParent();
            if (parent != null) {
                ((FrameLayout) parent).removeView(this.webView);
            }
            this.webView.stopLoading();
            this.webView.loadUrl("about:blank");
            this.webView.destroy();
            this.webView = null;
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    public void onResume() {
        super.onResume();
        AndroidUtilities.cancelRunOnUIThread(this.typingRunnable);
        this.typingRunnable.run();
    }

    protected void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z && !z2 && this.webView != null) {
            this.webView.loadUrl(this.currentUrl);
        }
    }
}
