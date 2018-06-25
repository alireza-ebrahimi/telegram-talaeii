package org.telegram.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
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
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
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
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
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
import utils.p178a.C3791b;

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
    class C51471 extends ActionBarMenuOnItemClick {
        C51471() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                ProxySettingsActivity.this.finishFragment();
            } else if (i == 1 && ProxySettingsActivity.this.getParentActivity() != null) {
                StringBuilder stringBuilder = new StringBuilder(TtmlNode.ANONYMOUS_REGION_ID);
                Object obj = ProxySettingsActivity.this.inputFields[0].getText().toString();
                Object obj2 = ProxySettingsActivity.this.inputFields[3].getText().toString();
                Object obj3 = ProxySettingsActivity.this.inputFields[2].getText().toString();
                Object obj4 = ProxySettingsActivity.this.inputFields[1].getText().toString();
                try {
                    if (!TextUtils.isEmpty(obj)) {
                        stringBuilder.append("server=").append(URLEncoder.encode(obj, C3446C.UTF8_NAME));
                    }
                    if (!TextUtils.isEmpty(obj4)) {
                        if (stringBuilder.length() != 0) {
                            stringBuilder.append("&");
                        }
                        stringBuilder.append("port=").append(URLEncoder.encode(obj4, C3446C.UTF8_NAME));
                    }
                    if (!TextUtils.isEmpty(obj3)) {
                        if (stringBuilder.length() != 0) {
                            stringBuilder.append("&");
                        }
                        stringBuilder.append("user=").append(URLEncoder.encode(obj3, C3446C.UTF8_NAME));
                    }
                    if (!TextUtils.isEmpty(obj2)) {
                        if (stringBuilder.length() != 0) {
                            stringBuilder.append("&");
                        }
                        stringBuilder.append("pass=").append(URLEncoder.encode(obj2, C3446C.UTF8_NAME));
                    }
                    if (stringBuilder.length() != 0) {
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.setType("text/plain");
                        intent.putExtra("android.intent.extra.TEXT", "https://t.me/socks?" + stringBuilder.toString());
                        Intent createChooser = Intent.createChooser(intent, LocaleController.getString("ShareLink", R.string.ShareLink));
                        createChooser.setFlags(ErrorDialogData.BINDER_CRASH);
                        ProxySettingsActivity.this.getParentActivity().startActivity(createChooser);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.ProxySettingsActivity$3 */
    class C51493 implements TextWatcher {
        C51493() {
        }

        public void afterTextChanged(Editable editable) {
            ProxySettingsActivity.this.checkShareButton();
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: org.telegram.ui.ProxySettingsActivity$4 */
    class C51504 implements TextWatcher {
        C51504() {
        }

        public void afterTextChanged(Editable editable) {
            if (!ProxySettingsActivity.this.ignoreOnTextChange) {
                int i;
                EditText editText = ProxySettingsActivity.this.inputFields[1];
                int selectionStart = editText.getSelectionStart();
                String str = "0123456789";
                String obj = editText.getText().toString();
                StringBuilder stringBuilder = new StringBuilder(obj.length());
                for (i = 0; i < obj.length(); i++) {
                    Object substring = obj.substring(i, i + 1);
                    if (str.contains(substring)) {
                        stringBuilder.append(substring);
                    }
                }
                ProxySettingsActivity.this.ignoreOnTextChange = true;
                i = Utilities.parseInt(stringBuilder.toString()).intValue();
                if (i < 0 || i > 65535 || !obj.equals(stringBuilder.toString())) {
                    if (i < 0) {
                        editText.setText("0");
                    } else if (i > 65535) {
                        editText.setText("65535");
                    } else {
                        editText.setText(stringBuilder.toString());
                    }
                } else if (selectionStart >= 0) {
                    editText.setSelection(selectionStart <= editText.length() ? selectionStart : editText.length());
                }
                ProxySettingsActivity.this.ignoreOnTextChange = false;
                ProxySettingsActivity.this.checkShareButton();
            }
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: org.telegram.ui.ProxySettingsActivity$5 */
    class C51515 implements OnEditorActionListener {
        C51515() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == 5) {
                int intValue = ((Integer) textView.getTag()).intValue();
                if (intValue + 1 < ProxySettingsActivity.this.inputFields.length) {
                    ProxySettingsActivity.this.inputFields[intValue + 1].requestFocus();
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
    class C51526 implements OnClickListener {
        C51526() {
        }

        public void onClick(View view) {
            ProxySettingsActivity.this.useProxyForCalls = !ProxySettingsActivity.this.useProxyForCalls;
            ProxySettingsActivity.this.useForCallsCell.setChecked(ProxySettingsActivity.this.useProxyForCalls);
        }
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

    public View createView(Context context) {
        View view;
        final SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        this.useProxySettings = sharedPreferences.getBoolean("proxy_enabled", false);
        this.useProxyForCalls = sharedPreferences.getBoolean("proxy_enabled_calls", false);
        this.actionBar.setTitle(LocaleController.getString("ProxySettings", R.string.ProxySettings));
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setActionBarMenuOnItemClick(new C51471());
        this.shareItem = this.actionBar.createMenu().addItem(1, (int) R.drawable.abc_ic_menu_share_mtrl_alpha);
        this.shareItem.setVisibility(8);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
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
            public void onClick(View view) {
                ProxySettingsActivity.this.useProxySettings = !ProxySettingsActivity.this.useProxySettings;
                ProxySettingsActivity.this.checkCell1.setChecked(ProxySettingsActivity.this.useProxySettings);
                if (!ProxySettingsActivity.this.useProxySettings) {
                    ProxySettingsActivity.this.useForCallsCell.setChecked(false);
                    sharedPreferences.edit().putBoolean("proxy_enabled_calls", false).apply();
                }
                ProxySettingsActivity.this.useForCallsCell.setEnabled(ProxySettingsActivity.this.useProxySettings);
            }
        });
        this.sectionCell = new ShadowSectionCell(context);
        this.linearLayout2.addView(this.sectionCell, LayoutHelper.createLinear(-1, -2));
        this.inputFields = new EditTextBoldCursor[4];
        int i = 0;
        while (i < 4) {
            View frameLayout2 = new FrameLayout(context);
            this.linearLayout2.addView(frameLayout2, LayoutHelper.createLinear(-1, 48));
            frameLayout2.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            if ((i != 3 ? 1 : null) != null) {
                view = new View(context);
                this.dividers.add(view);
                view.setBackgroundColor(Theme.getColor(Theme.key_divider));
                frameLayout2.addView(view, new LayoutParams(-1, 1, 83));
            }
            this.inputFields[i] = new EditTextBoldCursor(context);
            this.inputFields[i].setTag(Integer.valueOf(i));
            this.inputFields[i].setTextSize(1, 16.0f);
            this.inputFields[i].setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            this.inputFields[i].setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.inputFields[i].setBackgroundDrawable(null);
            this.inputFields[i].setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.inputFields[i].setCursorSize(AndroidUtilities.dp(20.0f));
            this.inputFields[i].setCursorWidth(1.5f);
            if (i == 0) {
                this.inputFields[i].addTextChangedListener(new C51493());
            } else if (i == 1) {
                this.inputFields[i].setInputType(2);
                this.inputFields[i].addTextChangedListener(new C51504());
            } else if (i == 3) {
                this.inputFields[i].setInputType(129);
                this.inputFields[i].setTypeface(Typeface.DEFAULT);
                this.inputFields[i].setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                this.inputFields[i].setInputType(1);
            }
            this.inputFields[i].setImeOptions(268435461);
            switch (i) {
                case 0:
                    this.inputFields[i].setHint(LocaleController.getString("UseProxyAddress", R.string.UseProxyAddress));
                    this.inputFields[i].setText(sharedPreferences.getString("proxy_ip", TtmlNode.ANONYMOUS_REGION_ID));
                    break;
                case 1:
                    this.inputFields[i].setHint(LocaleController.getString("UseProxyPort", R.string.UseProxyPort));
                    this.inputFields[i].setText(TtmlNode.ANONYMOUS_REGION_ID + sharedPreferences.getInt("proxy_port", 1080));
                    break;
                case 2:
                    this.inputFields[i].setHint(LocaleController.getString("UseProxyUsername", R.string.UseProxyUsername));
                    this.inputFields[i].setText(sharedPreferences.getString("proxy_user", TtmlNode.ANONYMOUS_REGION_ID));
                    break;
                case 3:
                    this.inputFields[i].setHint(LocaleController.getString("UseProxyPassword", R.string.UseProxyPassword));
                    this.inputFields[i].setText(sharedPreferences.getString("proxy_pass", TtmlNode.ANONYMOUS_REGION_ID));
                    break;
            }
            ProxyServerModel fromShared = ProxyServerModel.getFromShared();
            switch (i) {
                case 0:
                    if (sharedPreferences.getString("proxy_ip", TtmlNode.ANONYMOUS_REGION_ID).equals(fromShared.getIp())) {
                        this.inputFields[i].setText(TtmlNode.ANONYMOUS_REGION_ID);
                        break;
                    }
                    break;
                case 1:
                    if (sharedPreferences.getInt("proxy_port", 1080) == fromShared.getPort()) {
                        this.inputFields[i].setText("1080");
                        break;
                    }
                    break;
                case 2:
                    if (sharedPreferences.getString("proxy_user", TtmlNode.ANONYMOUS_REGION_ID).equals(fromShared.getUserName())) {
                        this.inputFields[i].setText(TtmlNode.ANONYMOUS_REGION_ID);
                        break;
                    }
                    break;
                case 3:
                    if (sharedPreferences.getString("proxy_pass", TtmlNode.ANONYMOUS_REGION_ID).equals(fromShared.getPassWord())) {
                        this.inputFields[i].setText(TtmlNode.ANONYMOUS_REGION_ID);
                        break;
                    }
                    break;
            }
            this.inputFields[i].setSelection(this.inputFields[i].length());
            this.inputFields[i].setPadding(0, 0, 0, AndroidUtilities.dp(6.0f));
            this.inputFields[i].setGravity(LocaleController.isRTL ? 5 : 3);
            frameLayout2.addView(this.inputFields[i], LayoutHelper.createFrame(-1, -2.0f, 51, 17.0f, 12.0f, 17.0f, 6.0f));
            this.inputFields[i].setOnEditorActionListener(new C51515());
            i++;
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
        this.useForCallsCell.setOnClickListener(new C51526());
        view = new TextInfoPrivacyCell(context);
        view.setBackgroundDrawable(Theme.getThemedDrawable(context, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
        view.setText(LocaleController.getString("UseProxyForCallsInfo", R.string.UseProxyForCallsInfo));
        this.linearLayout2.addView(view, LayoutHelper.createLinear(-1, -2));
        checkShareButton();
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        int i2 = 0;
        if (i == NotificationCenter.proxySettingsChanged && this.checkCell1 != null) {
            SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
            this.useProxySettings = sharedPreferences.getBoolean("proxy_enabled", false);
            if (this.useProxySettings) {
                this.checkCell1.setChecked(true);
                while (i2 < 4) {
                    switch (i2) {
                        case 0:
                            this.inputFields[i2].setText(sharedPreferences.getString("proxy_ip", TtmlNode.ANONYMOUS_REGION_ID));
                            break;
                        case 1:
                            this.inputFields[i2].setText(TtmlNode.ANONYMOUS_REGION_ID + sharedPreferences.getInt("proxy_port", 1080));
                            break;
                        case 2:
                            this.inputFields[i2].setText(sharedPreferences.getString("proxy_user", TtmlNode.ANONYMOUS_REGION_ID));
                            break;
                        case 3:
                            this.inputFields[i2].setText(sharedPreferences.getString("proxy_pass", TtmlNode.ANONYMOUS_REGION_ID));
                            break;
                        default:
                            break;
                    }
                    i2++;
                }
                return;
            }
            this.checkCell1.setChecked(false);
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        int i;
        ArrayList arrayList = new ArrayList();
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
            for (i = 0; i < this.inputFields.length; i++) {
                arrayList.add(new ThemeDescription((View) this.inputFields[i].getParent(), ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
                arrayList.add(new ThemeDescription(this.inputFields[i], ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText));
                arrayList.add(new ThemeDescription(this.inputFields[i], ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText));
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
        for (i = 0; i < this.dividers.size(); i++) {
            arrayList.add(new ThemeDescription((View) this.dividers.get(i), ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_divider));
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

    public boolean onFragmentCreate() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.proxySettingsChanged);
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        boolean z = C3791b.am(ApplicationLoader.applicationContext) > 0 && TextUtils.isEmpty(this.inputFields[0].getText().toString());
        if (z) {
            this.useProxySettings = true;
            this.useProxyForCalls = true;
        }
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.proxySettingsChanged);
        Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
        edit.putBoolean("proxy_enabled", this.useProxySettings);
        edit.putBoolean("proxy_enabled_calls", this.useProxyForCalls);
        String obj = this.inputFields[0].getText().toString();
        String obj2 = this.inputFields[3].getText().toString();
        String obj3 = this.inputFields[2].getText().toString();
        int intValue = Utilities.parseInt(this.inputFields[1].getText().toString()).intValue();
        if (z) {
            obj = C3791b.ai(ApplicationLoader.applicationContext);
            obj2 = C3791b.al(ApplicationLoader.applicationContext);
            obj3 = C3791b.ak(ApplicationLoader.applicationContext);
            try {
                intValue = Utilities.parseInt(C3791b.aj(ApplicationLoader.applicationContext)).intValue();
            } catch (Exception e) {
            }
        }
        edit.putString("proxy_ip", obj);
        edit.putString("proxy_pass", obj2);
        edit.putString("proxy_user", obj3);
        edit.putInt("proxy_port", intValue);
        edit.commit();
        if (this.useProxySettings) {
            ConnectionsManager.native_setProxySettings(obj, intValue, obj3, obj2);
        } else {
            ConnectionsManager.native_setProxySettings(TtmlNode.ANONYMOUS_REGION_ID, 0, TtmlNode.ANONYMOUS_REGION_ID, TtmlNode.ANONYMOUS_REGION_ID);
        }
        super.onFragmentDestroy();
    }

    public void onResume() {
        super.onResume();
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
    }

    protected void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z && !z2) {
            this.inputFields[0].requestFocus();
            AndroidUtilities.showKeyboard(this.inputFields[0]);
        }
    }
}
