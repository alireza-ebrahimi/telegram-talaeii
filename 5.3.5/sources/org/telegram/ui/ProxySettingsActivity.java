package org.telegram.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.support.v4.internal.view.SupportMenu;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Model.ProxyServerModel;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.LayoutHelper;
import utils.app.AppPreferences;

public class ProxySettingsActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int FIELD_IP = 0;
    private static final int FIELD_PASSWORD = 3;
    private static final int FIELD_PORT = 1;
    private static final int FIELD_USER = 2;
    private static final int share_item = 1;
    private TextInfoPrivacyCell bottomCell;
    private TextCheckCell checkCell1;
    private ArrayList<View> dividers = new ArrayList();
    private HeaderCell headerCell;
    private boolean ignoreOnTextChange;
    private EditTextBoldCursor[] inputFields;
    private LinearLayout linearLayout2;
    private ScrollView scrollView;
    private ShadowSectionCell sectionCell;
    private ActionBarMenuItem shareItem;
    private TextCheckCell useForCallsCell;
    private boolean useProxyForCalls;
    private boolean useProxySettings;

    /* renamed from: org.telegram.ui.ProxySettingsActivity$1 */
    class C33081 extends ActionBarMenuOnItemClick {
        C33081() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                ProxySettingsActivity.this.finishFragment();
            } else if (id == 1 && ProxySettingsActivity.this.getParentActivity() != null) {
                StringBuilder params = new StringBuilder("");
                String address = ProxySettingsActivity.this.inputFields[0].getText().toString();
                String password = ProxySettingsActivity.this.inputFields[3].getText().toString();
                String user = ProxySettingsActivity.this.inputFields[2].getText().toString();
                String port = ProxySettingsActivity.this.inputFields[1].getText().toString();
                try {
                    if (!TextUtils.isEmpty(address)) {
                        params.append("server=").append(URLEncoder.encode(address, "UTF-8"));
                    }
                    if (!TextUtils.isEmpty(port)) {
                        if (params.length() != 0) {
                            params.append("&");
                        }
                        params.append("port=").append(URLEncoder.encode(port, "UTF-8"));
                    }
                    if (!TextUtils.isEmpty(user)) {
                        if (params.length() != 0) {
                            params.append("&");
                        }
                        params.append("user=").append(URLEncoder.encode(user, "UTF-8"));
                    }
                    if (!TextUtils.isEmpty(password)) {
                        if (params.length() != 0) {
                            params.append("&");
                        }
                        params.append("pass=").append(URLEncoder.encode(password, "UTF-8"));
                    }
                    if (params.length() != 0) {
                        Intent shareIntent = new Intent("android.intent.action.SEND");
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra("android.intent.extra.TEXT", "https://t.me/socks?" + params.toString());
                        Intent chooserIntent = Intent.createChooser(shareIntent, LocaleController.getString("ShareLink", R.string.ShareLink));
                        chooserIntent.setFlags(268435456);
                        ProxySettingsActivity.this.getParentActivity().startActivity(chooserIntent);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.ProxySettingsActivity$3 */
    class C33103 implements TextWatcher {
        C33103() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            ProxySettingsActivity.this.checkShareButton();
        }
    }

    /* renamed from: org.telegram.ui.ProxySettingsActivity$4 */
    class C33114 implements TextWatcher {
        C33114() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (!ProxySettingsActivity.this.ignoreOnTextChange) {
                EditText phoneField = ProxySettingsActivity.this.inputFields[1];
                int start = phoneField.getSelectionStart();
                String chars = "0123456789";
                String str = phoneField.getText().toString();
                StringBuilder builder = new StringBuilder(str.length());
                for (int a = 0; a < str.length(); a++) {
                    String ch = str.substring(a, a + 1);
                    if (chars.contains(ch)) {
                        builder.append(ch);
                    }
                }
                ProxySettingsActivity.this.ignoreOnTextChange = true;
                int port = Utilities.parseInt(builder.toString()).intValue();
                if (port < 0 || port > SupportMenu.USER_MASK || !str.equals(builder.toString())) {
                    if (port < 0) {
                        phoneField.setText("0");
                    } else if (port > SupportMenu.USER_MASK) {
                        phoneField.setText("65535");
                    } else {
                        phoneField.setText(builder.toString());
                    }
                } else if (start >= 0) {
                    if (start > phoneField.length()) {
                        start = phoneField.length();
                    }
                    phoneField.setSelection(start);
                }
                ProxySettingsActivity.this.ignoreOnTextChange = false;
                ProxySettingsActivity.this.checkShareButton();
            }
        }
    }

    /* renamed from: org.telegram.ui.ProxySettingsActivity$5 */
    class C33125 implements OnEditorActionListener {
        C33125() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == 5) {
                int num = ((Integer) textView.getTag()).intValue();
                if (num + 1 < ProxySettingsActivity.this.inputFields.length) {
                    ProxySettingsActivity.this.inputFields[num + 1].requestFocus();
                }
                return true;
            } else if (i != 6) {
                return false;
            } else {
                ProxySettingsActivity.this.finishFragment();
                return true;
            }
        }
    }

    /* renamed from: org.telegram.ui.ProxySettingsActivity$6 */
    class C33136 implements OnClickListener {
        C33136() {
        }

        public void onClick(View v) {
            ProxySettingsActivity.this.useProxyForCalls = !ProxySettingsActivity.this.useProxyForCalls;
            ProxySettingsActivity.this.useForCallsCell.setChecked(ProxySettingsActivity.this.useProxyForCalls);
        }
    }

    public void onResume() {
        super.onResume();
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
    }

    public boolean onFragmentCreate() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.proxySettingsChanged);
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        boolean useSLSProxyForce;
        if (AppPreferences.getProxyEnable(ApplicationLoader.applicationContext) <= 0 || !TextUtils.isEmpty(this.inputFields[0].getText().toString())) {
            useSLSProxyForce = false;
        } else {
            useSLSProxyForce = true;
        }
        if (useSLSProxyForce) {
            this.useProxySettings = true;
            this.useProxyForCalls = true;
        }
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.proxySettingsChanged);
        Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        editor.putBoolean("proxy_enabled", this.useProxySettings);
        editor.putBoolean("proxy_enabled_calls", this.useProxyForCalls);
        String address = this.inputFields[0].getText().toString();
        String password = this.inputFields[3].getText().toString();
        String user = this.inputFields[2].getText().toString();
        int port = Utilities.parseInt(this.inputFields[1].getText().toString()).intValue();
        if (useSLSProxyForce) {
            address = AppPreferences.getProxyAddress(ApplicationLoader.applicationContext);
            password = AppPreferences.getProxyPassword(ApplicationLoader.applicationContext);
            user = AppPreferences.getProxyUsername(ApplicationLoader.applicationContext);
            try {
                port = Utilities.parseInt(AppPreferences.getProxyPort(ApplicationLoader.applicationContext)).intValue();
            } catch (Exception e) {
            }
        }
        editor.putString("proxy_ip", address);
        editor.putString("proxy_pass", password);
        editor.putString("proxy_user", user);
        editor.putInt("proxy_port", port);
        editor.commit();
        if (this.useProxySettings) {
            ConnectionsManager.native_setProxySettings(address, port, user, password);
        } else {
            ConnectionsManager.native_setProxySettings("", 0, "", "");
        }
        super.onFragmentDestroy();
    }

    public View createView(Context context) {
        final SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        this.useProxySettings = preferences.getBoolean("proxy_enabled", false);
        this.useProxyForCalls = preferences.getBoolean("proxy_enabled_calls", false);
        this.actionBar.setTitle(LocaleController.getString("ProxySettings", R.string.ProxySettings));
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setActionBarMenuOnItemClick(new C33081());
        this.shareItem = this.actionBar.createMenu().addItem(1, (int) R.drawable.abc_ic_menu_share_mtrl_alpha);
        this.shareItem.setVisibility(8);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = this.fragmentView;
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.scrollView = new ScrollView(context);
        this.scrollView.setFillViewport(true);
        AndroidUtilities.setScrollViewEdgeEffectColor(this.scrollView, Theme.getColor(Theme.key_actionBarDefault));
        frameLayout.addView(this.scrollView, LayoutHelper.createFrame(-1, -1.0f));
        this.linearLayout2 = new LinearLayout(context);
        this.linearLayout2.setOrientation(1);
        this.scrollView.addView(this.linearLayout2, new LayoutParams(-1, -2));
        this.checkCell1 = new TextCheckCell(context);
        this.checkCell1.setBackgroundDrawable(Theme.getSelectorDrawable(true));
        this.checkCell1.setTextAndCheck(LocaleController.getString("UseProxySettings", R.string.UseProxySettings), this.useProxySettings, false);
        this.linearLayout2.addView(this.checkCell1, LayoutHelper.createLinear(-1, -2));
        this.checkCell1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ProxySettingsActivity.this.useProxySettings = !ProxySettingsActivity.this.useProxySettings;
                ProxySettingsActivity.this.checkCell1.setChecked(ProxySettingsActivity.this.useProxySettings);
                if (!ProxySettingsActivity.this.useProxySettings) {
                    ProxySettingsActivity.this.useForCallsCell.setChecked(false);
                    preferences.edit().putBoolean("proxy_enabled_calls", false).apply();
                }
                ProxySettingsActivity.this.useForCallsCell.setEnabled(ProxySettingsActivity.this.useProxySettings);
            }
        });
        this.sectionCell = new ShadowSectionCell(context);
        this.linearLayout2.addView(this.sectionCell, LayoutHelper.createLinear(-1, -2));
        this.inputFields = new EditTextBoldCursor[4];
        int a = 0;
        while (a < 4) {
            FrameLayout container = new FrameLayout(context);
            this.linearLayout2.addView(container, LayoutHelper.createLinear(-1, 48));
            container.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            if (a != 3) {
                View divider = new View(context);
                this.dividers.add(divider);
                divider.setBackgroundColor(Theme.getColor(Theme.key_divider));
                container.addView(divider, new LayoutParams(-1, 1, 83));
            }
            this.inputFields[a] = new EditTextBoldCursor(context);
            this.inputFields[a].setTag(Integer.valueOf(a));
            this.inputFields[a].setTextSize(1, 16.0f);
            this.inputFields[a].setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            this.inputFields[a].setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.inputFields[a].setBackgroundDrawable(null);
            this.inputFields[a].setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.inputFields[a].setCursorSize(AndroidUtilities.dp(20.0f));
            this.inputFields[a].setCursorWidth(1.5f);
            if (a == 0) {
                this.inputFields[a].addTextChangedListener(new C33103());
            } else if (a == 1) {
                this.inputFields[a].setInputType(2);
                this.inputFields[a].addTextChangedListener(new C33114());
            } else if (a == 3) {
                this.inputFields[a].setInputType(TsExtractor.TS_STREAM_TYPE_AC3);
                this.inputFields[a].setTypeface(Typeface.DEFAULT);
                this.inputFields[a].setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                this.inputFields[a].setInputType(1);
            }
            this.inputFields[a].setImeOptions(268435461);
            switch (a) {
                case 0:
                    this.inputFields[a].setHint(LocaleController.getString("UseProxyAddress", R.string.UseProxyAddress));
                    this.inputFields[a].setText(preferences.getString("proxy_ip", ""));
                    break;
                case 1:
                    this.inputFields[a].setHint(LocaleController.getString("UseProxyPort", R.string.UseProxyPort));
                    this.inputFields[a].setText("" + preferences.getInt("proxy_port", 1080));
                    break;
                case 2:
                    this.inputFields[a].setHint(LocaleController.getString("UseProxyUsername", R.string.UseProxyUsername));
                    this.inputFields[a].setText(preferences.getString("proxy_user", ""));
                    break;
                case 3:
                    this.inputFields[a].setHint(LocaleController.getString("UseProxyPassword", R.string.UseProxyPassword));
                    this.inputFields[a].setText(preferences.getString("proxy_pass", ""));
                    break;
            }
            ProxyServerModel slsProxy = ProxyServerModel.getFromShared();
            switch (a) {
                case 0:
                    if (preferences.getString("proxy_ip", "").equals(slsProxy.getIp())) {
                        this.inputFields[a].setText("");
                        break;
                    }
                    break;
                case 1:
                    if (preferences.getInt("proxy_port", 1080) == slsProxy.getPort()) {
                        this.inputFields[a].setText("1080");
                        break;
                    }
                    break;
                case 2:
                    if (preferences.getString("proxy_user", "").equals(slsProxy.getUserName())) {
                        this.inputFields[a].setText("");
                        break;
                    }
                    break;
                case 3:
                    if (preferences.getString("proxy_pass", "").equals(slsProxy.getPassWord())) {
                        this.inputFields[a].setText("");
                        break;
                    }
                    break;
            }
            this.inputFields[a].setSelection(this.inputFields[a].length());
            this.inputFields[a].setPadding(0, 0, 0, AndroidUtilities.dp(6.0f));
            this.inputFields[a].setGravity(LocaleController.isRTL ? 5 : 3);
            container.addView(this.inputFields[a], LayoutHelper.createFrame(-1, -2.0f, 51, 17.0f, 12.0f, 17.0f, 6.0f));
            this.inputFields[a].setOnEditorActionListener(new C33125());
            a++;
        }
        this.bottomCell = new TextInfoPrivacyCell(context);
        this.bottomCell.setBackgroundDrawable(Theme.getThemedDrawable(context, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
        this.bottomCell.setText(LocaleController.getString("UseProxyInfo", R.string.UseProxyInfo));
        this.linearLayout2.addView(this.bottomCell, LayoutHelper.createLinear(-1, -2));
        this.useForCallsCell = new TextCheckCell(context);
        this.useForCallsCell.setBackgroundDrawable(Theme.getSelectorDrawable(true));
        this.useForCallsCell.setTextAndCheck(LocaleController.getString("UseProxyForCalls", R.string.UseProxyForCalls), this.useProxyForCalls, false);
        this.useForCallsCell.setEnabled(this.useProxySettings);
        this.linearLayout2.addView(this.useForCallsCell, LayoutHelper.createLinear(-1, -2));
        this.useForCallsCell.setOnClickListener(new C33136());
        TextInfoPrivacyCell useForCallsInfoCell = new TextInfoPrivacyCell(context);
        useForCallsInfoCell.setBackgroundDrawable(Theme.getThemedDrawable(context, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
        useForCallsInfoCell.setText(LocaleController.getString("UseProxyForCallsInfo", R.string.UseProxyForCallsInfo));
        this.linearLayout2.addView(useForCallsInfoCell, LayoutHelper.createLinear(-1, -2));
        checkShareButton();
        return this.fragmentView;
    }

    private void checkShareButton() {
        if (this.inputFields[0] != null && this.inputFields[1] != null) {
            if (this.inputFields[0].length() == 0 || Utilities.parseInt(this.inputFields[1].getText().toString()).intValue() == 0) {
                this.shareItem.setAlpha(0.5f);
                this.shareItem.setEnabled(false);
                return;
            }
            this.shareItem.setAlpha(1.0f);
            this.shareItem.setEnabled(true);
        }
    }

    protected void onTransitionAnimationEnd(boolean isOpen, boolean backward) {
        if (isOpen && !backward) {
            this.inputFields[0].requestFocus();
            AndroidUtilities.showKeyboard(this.inputFields[0]);
        }
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.proxySettingsChanged && this.checkCell1 != null) {
            SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
            this.useProxySettings = preferences.getBoolean("proxy_enabled", false);
            if (this.useProxySettings) {
                this.checkCell1.setChecked(true);
                for (int a = 0; a < 4; a++) {
                    switch (a) {
                        case 0:
                            this.inputFields[a].setText(preferences.getString("proxy_ip", ""));
                            break;
                        case 1:
                            this.inputFields[a].setText("" + preferences.getInt("proxy_port", 1080));
                            break;
                        case 2:
                            this.inputFields[a].setText(preferences.getString("proxy_user", ""));
                            break;
                        case 3:
                            this.inputFields[a].setText(preferences.getString("proxy_pass", ""));
                            break;
                        default:
                            break;
                    }
                }
                return;
            }
            this.checkCell1.setChecked(false);
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        int a;
        ArrayList<ThemeDescription> arrayList = new ArrayList();
        arrayList.add(new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault));
        arrayList.add(new ThemeDescription(this.scrollView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCH, null, null, null, null, Theme.key_actionBarDefaultSearch));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SEARCHPLACEHOLDER, null, null, null, null, Theme.key_actionBarDefaultSearchPlaceholder));
        arrayList.add(new ThemeDescription(this.linearLayout2, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider));
        if (this.inputFields != null) {
            for (a = 0; a < this.inputFields.length; a++) {
                arrayList.add(new ThemeDescription((View) this.inputFields[a].getParent(), ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
                arrayList.add(new ThemeDescription(this.inputFields[a], ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
                arrayList.add(new ThemeDescription(this.inputFields[a], ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText));
            }
        } else {
            arrayList.add(new ThemeDescription(null, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
            arrayList.add(new ThemeDescription(null, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText));
        }
        arrayList.add(new ThemeDescription(this.headerCell, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(this.headerCell, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader));
        arrayList.add(new ThemeDescription(this.sectionCell, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow));
        arrayList.add(new ThemeDescription(this.bottomCell, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow));
        arrayList.add(new ThemeDescription(this.bottomCell, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4));
        arrayList.add(new ThemeDescription(this.bottomCell, ThemeDescription.FLAG_LINKCOLOR, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteLinkText));
        for (a = 0; a < this.dividers.size(); a++) {
            arrayList.add(new ThemeDescription((View) this.dividers.get(a), ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_divider));
        }
        arrayList.add(new ThemeDescription(this.checkCell1, 0, new Class[]{TextCheckCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.add(new ThemeDescription(this.checkCell1, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumb));
        arrayList.add(new ThemeDescription(this.checkCell1, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrack));
        arrayList.add(new ThemeDescription(this.checkCell1, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumbChecked));
        arrayList.add(new ThemeDescription(this.checkCell1, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrackChecked));
        arrayList.add(new ThemeDescription(this.checkCell1, ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(this.checkCell1, ThemeDescription.FLAG_SELECTORWHITE, null, null, null, null, Theme.key_listSelector));
        return (ThemeDescription[]) arrayList.toArray(new ThemeDescription[arrayList.size()]);
    }
}
