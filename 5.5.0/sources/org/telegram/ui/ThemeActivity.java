package org.telegram.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Vibrator;
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map.Entry;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.Theme.ThemeInfo;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Cells.ThemeCell;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.ThemeEditorView;

public class ThemeActivity extends BaseFragment {
    private ListAdapter listAdapter;
    private RecyclerListView listView;

    /* renamed from: org.telegram.ui.ThemeActivity$1 */
    class C52231 extends ActionBarMenuOnItemClick {
        C52231() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                ThemeActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.ThemeActivity$2 */
    class C52292 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.ThemeActivity$2$1 */
        class C52241 implements OnClickListener {
            C52241() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }

        /* renamed from: org.telegram.ui.ThemeActivity$2$2 */
        class C52252 implements OnEditorActionListener {
            C52252() {
            }

            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                AndroidUtilities.hideKeyboard(textView);
                return false;
            }
        }

        C52292() {
        }

        public void onItemClick(View view, int i) {
            if (i != 0) {
                int i2 = i - 2;
                if (i2 >= 0 && i2 < Theme.themes.size()) {
                    Theme.applyTheme((ThemeInfo) Theme.themes.get(i2));
                    if (ThemeActivity.this.parentLayout != null) {
                        ThemeActivity.this.parentLayout.rebuildAllFragmentViews(false, false);
                    }
                    ThemeActivity.this.finishFragment();
                }
            } else if (ThemeActivity.this.getParentActivity() != null) {
                final View editTextBoldCursor = new EditTextBoldCursor(ThemeActivity.this.getParentActivity());
                editTextBoldCursor.setBackgroundDrawable(Theme.createEditTextDrawable(ThemeActivity.this.getParentActivity(), true));
                Builder builder = new Builder(ThemeActivity.this.getParentActivity());
                builder.setTitle(LocaleController.getString("NewTheme", R.string.NewTheme));
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C52241());
                View linearLayout = new LinearLayout(ThemeActivity.this.getParentActivity());
                linearLayout.setOrientation(1);
                builder.setView(linearLayout);
                View textView = new TextView(ThemeActivity.this.getParentActivity());
                textView.setText(LocaleController.formatString("EnterThemeName", R.string.EnterThemeName, new Object[0]));
                textView.setTextSize(16.0f);
                textView.setPadding(AndroidUtilities.dp(23.0f), AndroidUtilities.dp(12.0f), AndroidUtilities.dp(23.0f), AndroidUtilities.dp(6.0f));
                textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2));
                editTextBoldCursor.setTextSize(1, 16.0f);
                editTextBoldCursor.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                editTextBoldCursor.setMaxLines(1);
                editTextBoldCursor.setLines(1);
                editTextBoldCursor.setInputType(16385);
                editTextBoldCursor.setGravity(51);
                editTextBoldCursor.setSingleLine(true);
                editTextBoldCursor.setImeOptions(6);
                editTextBoldCursor.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                editTextBoldCursor.setCursorSize(AndroidUtilities.dp(20.0f));
                editTextBoldCursor.setCursorWidth(1.5f);
                editTextBoldCursor.setPadding(0, AndroidUtilities.dp(4.0f), 0, 0);
                linearLayout.addView(editTextBoldCursor, LayoutHelper.createLinear(-1, 36, 51, 24, 6, 24, 0));
                editTextBoldCursor.setOnEditorActionListener(new C52252());
                final Dialog create = builder.create();
                create.setOnShowListener(new OnShowListener() {

                    /* renamed from: org.telegram.ui.ThemeActivity$2$3$1 */
                    class C52261 implements Runnable {
                        C52261() {
                        }

                        public void run() {
                            editTextBoldCursor.requestFocus();
                            AndroidUtilities.showKeyboard(editTextBoldCursor);
                        }
                    }

                    public void onShow(DialogInterface dialogInterface) {
                        AndroidUtilities.runOnUIThread(new C52261());
                    }
                });
                ThemeActivity.this.showDialog(create);
                create.getButton(-1).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (editTextBoldCursor.length() == 0) {
                            Vibrator vibrator = (Vibrator) ApplicationLoader.applicationContext.getSystemService("vibrator");
                            if (vibrator != null) {
                                vibrator.vibrate(200);
                            }
                            AndroidUtilities.shakeView(editTextBoldCursor, 2.0f, 0);
                            return;
                        }
                        ThemeEditorView themeEditorView = new ThemeEditorView();
                        String str = editTextBoldCursor.getText().toString() + ".attheme";
                        themeEditorView.show(ThemeActivity.this.getParentActivity(), str);
                        Theme.saveCurrentTheme(str, true);
                        ThemeActivity.this.listAdapter.notifyDataSetChanged();
                        create.dismiss();
                        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                        if (!sharedPreferences.getBoolean("themehint", false)) {
                            sharedPreferences.edit().putBoolean("themehint", true).commit();
                            try {
                                Toast.makeText(ThemeActivity.this.getParentActivity(), LocaleController.getString("CreateNewThemeHelp", R.string.CreateNewThemeHelp), 1).show();
                            } catch (Throwable e) {
                                FileLog.e(e);
                            }
                        }
                    }
                });
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.ThemeActivity$ListAdapter$1 */
        class C52321 implements View.OnClickListener {
            C52321() {
            }

            public void onClick(View view) {
                final ThemeInfo currentThemeInfo = ((ThemeCell) view.getParent()).getCurrentThemeInfo();
                if (ThemeActivity.this.getParentActivity() != null) {
                    BottomSheet.Builder builder = new BottomSheet.Builder(ThemeActivity.this.getParentActivity());
                    builder.setItems(currentThemeInfo.pathToFile == null ? new CharSequence[]{LocaleController.getString("ShareFile", R.string.ShareFile)} : new CharSequence[]{LocaleController.getString("ShareFile", R.string.ShareFile), LocaleController.getString("Edit", R.string.Edit), LocaleController.getString("Delete", R.string.Delete)}, new OnClickListener() {

                        /* renamed from: org.telegram.ui.ThemeActivity$ListAdapter$1$1$1 */
                        class C52301 implements OnClickListener {
                            C52301() {
                            }

                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (Theme.deleteTheme(currentThemeInfo)) {
                                    ThemeActivity.this.parentLayout.rebuildAllFragmentViews(true, true);
                                }
                                if (ThemeActivity.this.listAdapter != null) {
                                    ThemeActivity.this.listAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            File file;
                            FileOutputStream fileOutputStream;
                            Throwable e;
                            File file2;
                            Intent intent;
                            Throwable th;
                            if (i == 0) {
                                if (currentThemeInfo.pathToFile == null && currentThemeInfo.assetName == null) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for (Entry entry : Theme.getDefaultColors().entrySet()) {
                                        stringBuilder.append((String) entry.getKey()).append("=").append(entry.getValue()).append("\n");
                                    }
                                    file = new File(ApplicationLoader.getFilesDirFixed(), "default_theme.attheme");
                                    try {
                                        fileOutputStream = new FileOutputStream(file);
                                        try {
                                            fileOutputStream.write(stringBuilder.toString().getBytes());
                                            if (fileOutputStream != null) {
                                                try {
                                                    fileOutputStream.close();
                                                } catch (Throwable e2) {
                                                    FileLog.e("tmessage", e2);
                                                }
                                            }
                                        } catch (Exception e3) {
                                            e2 = e3;
                                            try {
                                                FileLog.e(e2);
                                                if (fileOutputStream != null) {
                                                    try {
                                                        fileOutputStream.close();
                                                    } catch (Throwable e22) {
                                                        FileLog.e("tmessage", e22);
                                                    }
                                                }
                                                file2 = new File(FileLoader.getInstance().getDirectory(4), file.getName());
                                                if (!AndroidUtilities.copyFile(file, file2)) {
                                                    intent = new Intent("android.intent.action.SEND");
                                                    intent.setType("text/xml");
                                                    if (VERSION.SDK_INT >= 24) {
                                                        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file2));
                                                    } else {
                                                        try {
                                                            intent.putExtra("android.intent.extra.STREAM", FileProvider.a(ThemeActivity.this.getParentActivity(), "org.ir.talaeii.provider", file2));
                                                            intent.setFlags(1);
                                                        } catch (Exception e4) {
                                                            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file2));
                                                        }
                                                    }
                                                    ThemeActivity.this.startActivityForResult(Intent.createChooser(intent, LocaleController.getString("ShareFile", R.string.ShareFile)), ChatActivity.startAllServices);
                                                }
                                            } catch (Throwable th2) {
                                                th = th2;
                                                if (fileOutputStream != null) {
                                                    try {
                                                        fileOutputStream.close();
                                                    } catch (Throwable e222) {
                                                        FileLog.e("tmessage", e222);
                                                    }
                                                }
                                                throw th;
                                            }
                                        }
                                    } catch (Exception e5) {
                                        e222 = e5;
                                        fileOutputStream = null;
                                        FileLog.e(e222);
                                        if (fileOutputStream != null) {
                                            fileOutputStream.close();
                                        }
                                        file2 = new File(FileLoader.getInstance().getDirectory(4), file.getName());
                                        if (!AndroidUtilities.copyFile(file, file2)) {
                                            intent = new Intent("android.intent.action.SEND");
                                            intent.setType("text/xml");
                                            if (VERSION.SDK_INT >= 24) {
                                                intent.putExtra("android.intent.extra.STREAM", FileProvider.a(ThemeActivity.this.getParentActivity(), "org.ir.talaeii.provider", file2));
                                                intent.setFlags(1);
                                            } else {
                                                intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file2));
                                            }
                                            ThemeActivity.this.startActivityForResult(Intent.createChooser(intent, LocaleController.getString("ShareFile", R.string.ShareFile)), ChatActivity.startAllServices);
                                        }
                                    } catch (Throwable th3) {
                                        th = th3;
                                        fileOutputStream = null;
                                        if (fileOutputStream != null) {
                                            fileOutputStream.close();
                                        }
                                        throw th;
                                    }
                                }
                                file = currentThemeInfo.assetName != null ? Theme.getAssetFile(currentThemeInfo.assetName) : new File(currentThemeInfo.pathToFile);
                                file2 = new File(FileLoader.getInstance().getDirectory(4), file.getName());
                                try {
                                    if (!AndroidUtilities.copyFile(file, file2)) {
                                        intent = new Intent("android.intent.action.SEND");
                                        intent.setType("text/xml");
                                        if (VERSION.SDK_INT >= 24) {
                                            intent.putExtra("android.intent.extra.STREAM", FileProvider.a(ThemeActivity.this.getParentActivity(), "org.ir.talaeii.provider", file2));
                                            intent.setFlags(1);
                                        } else {
                                            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file2));
                                        }
                                        ThemeActivity.this.startActivityForResult(Intent.createChooser(intent, LocaleController.getString("ShareFile", R.string.ShareFile)), ChatActivity.startAllServices);
                                    }
                                } catch (Throwable th4) {
                                    FileLog.e(th4);
                                }
                            } else if (i == 1) {
                                if (ThemeActivity.this.parentLayout != null) {
                                    Theme.applyTheme(currentThemeInfo);
                                    ThemeActivity.this.parentLayout.rebuildAllFragmentViews(true, true);
                                    new ThemeEditorView().show(ThemeActivity.this.getParentActivity(), currentThemeInfo.name);
                                }
                            } else if (ThemeActivity.this.getParentActivity() != null) {
                                Builder builder = new Builder(ThemeActivity.this.getParentActivity());
                                builder.setMessage(LocaleController.getString("DeleteThemeAlert", R.string.DeleteThemeAlert));
                                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                builder.setPositiveButton(LocaleController.getString("Delete", R.string.Delete), new C52301());
                                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                                ThemeActivity.this.showDialog(builder.create());
                            }
                        }
                    });
                    ThemeActivity.this.showDialog(builder.create());
                }
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return Theme.themes.size() + 3;
        }

        public int getItemViewType(int i) {
            return i == 0 ? 1 : i == 1 ? 2 : i == Theme.themes.size() + 2 ? 3 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            return itemViewType == 0 || itemViewType == 1;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (viewHolder.getItemViewType() == 0) {
                int i2 = i - 2;
                ((ThemeCell) viewHolder.itemView).setTheme((ThemeInfo) Theme.themes.get(i2), i2 != Theme.themes.size() + -1);
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View themeCell;
            switch (i) {
                case 0:
                    themeCell = new ThemeCell(this.mContext);
                    themeCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    ((ThemeCell) themeCell).setOnOptionsClick(new C52321());
                    break;
                case 1:
                    themeCell = new TextSettingsCell(this.mContext);
                    ((TextSettingsCell) themeCell).setText(LocaleController.getString("CreateNewTheme", R.string.CreateNewTheme), false);
                    themeCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 2:
                    themeCell = new TextInfoPrivacyCell(this.mContext);
                    ((TextInfoPrivacyCell) themeCell).setText(LocaleController.getString("CreateNewThemeInfo", R.string.CreateNewThemeInfo));
                    themeCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    break;
                default:
                    themeCell = new ShadowSectionCell(this.mContext);
                    themeCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    break;
            }
            return new Holder(themeCell);
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(false);
        this.actionBar.setTitle(LocaleController.getString("Theme", R.string.Theme));
        this.actionBar.setActionBarMenuOnItemClick(new C52231());
        this.listAdapter = new ListAdapter(context);
        View frameLayout = new FrameLayout(context);
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.fragmentView = frameLayout;
        this.listView = new RecyclerListView(context);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setAdapter(this.listAdapter);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setOnItemClickListener(new C52292());
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[15];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[2] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[6] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[7] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[8] = new ThemeDescription(this.listView, 0, new Class[]{ThemeCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[9] = new ThemeDescription(this.listView, 0, new Class[]{ThemeCell.class}, new String[]{"checkImage"}, null, null, null, Theme.key_featuredStickers_addedIcon);
        themeDescriptionArr[10] = new ThemeDescription(this.listView, 0, new Class[]{ThemeCell.class}, new String[]{"optionsButton"}, null, null, null, Theme.key_stickers_menu);
        themeDescriptionArr[11] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[12] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        themeDescriptionArr[14] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        return themeDescriptionArr;
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }
}
