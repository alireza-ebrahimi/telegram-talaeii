package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.customization.util.C2872c;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocaleController.LocaleInfo;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.LanguageCell;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class LanguageSelectActivity extends BaseFragment implements NotificationCenterDelegate {
    private EmptyTextProgressView emptyView;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private ListAdapter searchListViewAdapter;
    private ArrayList<LocaleInfo> searchResult;
    private Timer searchTimer;
    private boolean searchWas;
    private boolean searching;
    private ArrayList<LocaleInfo> sortedLanguages;

    /* renamed from: org.telegram.ui.LanguageSelectActivity$1 */
    class C48231 extends ActionBarMenuOnItemClick {
        C48231() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                LanguageSelectActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.LanguageSelectActivity$2 */
    class C48242 extends ActionBarMenuItemSearchListener {
        C48242() {
        }

        public void onSearchCollapse() {
            LanguageSelectActivity.this.search(null);
            LanguageSelectActivity.this.searching = false;
            LanguageSelectActivity.this.searchWas = false;
            if (LanguageSelectActivity.this.listView != null) {
                LanguageSelectActivity.this.emptyView.setVisibility(8);
                LanguageSelectActivity.this.listView.setAdapter(LanguageSelectActivity.this.listAdapter);
            }
        }

        public void onSearchExpand() {
            LanguageSelectActivity.this.searching = true;
        }

        public void onTextChanged(EditText editText) {
            String obj = editText.getText().toString();
            LanguageSelectActivity.this.search(obj);
            if (obj.length() != 0) {
                LanguageSelectActivity.this.searchWas = true;
                if (LanguageSelectActivity.this.listView != null) {
                    LanguageSelectActivity.this.listView.setAdapter(LanguageSelectActivity.this.searchListViewAdapter);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.LanguageSelectActivity$3 */
    class C48273 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.LanguageSelectActivity$3$1 */
        class C48251 implements Runnable {
            C48251() {
            }

            public void run() {
                if (LanguageSelectActivity.this.getParentActivity() != null) {
                    Toast.makeText(LanguageSelectActivity.this.getParentActivity(), LocaleController.getString("AppWillRestart", R.string.AppWillRestart), 0).show();
                }
            }
        }

        /* renamed from: org.telegram.ui.LanguageSelectActivity$3$2 */
        class C48262 implements Runnable {
            C48262() {
            }

            public void run() {
                C2872c.a();
            }
        }

        C48273() {
        }

        public void onItemClick(View view, int i) {
            if (LanguageSelectActivity.this.getParentActivity() != null && LanguageSelectActivity.this.parentLayout != null) {
                LocaleInfo localeInfo;
                if (LanguageSelectActivity.this.searching && LanguageSelectActivity.this.searchWas) {
                    if (i >= 0 && i < LanguageSelectActivity.this.searchResult.size()) {
                        localeInfo = (LocaleInfo) LanguageSelectActivity.this.searchResult.get(i);
                    }
                    localeInfo = null;
                } else {
                    if (i >= 0 && i < LanguageSelectActivity.this.sortedLanguages.size()) {
                        localeInfo = (LocaleInfo) LanguageSelectActivity.this.sortedLanguages.get(i);
                    }
                    localeInfo = null;
                }
                if (localeInfo != null) {
                    LocaleController.getInstance().applyLanguage(localeInfo, true, false, false, true);
                    LanguageSelectActivity.this.parentLayout.rebuildAllFragmentViews(false, false);
                    C2872c.f9483a = true;
                    AndroidUtilities.runOnUIThread(new C48251());
                    if (C2872c.f9483a) {
                        new Handler().postDelayed(new C48262(), 2000);
                    }
                }
                LanguageSelectActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.LanguageSelectActivity$4 */
    class C48294 implements OnItemLongClickListener {
        C48294() {
        }

        public boolean onItemClick(View view, int i) {
            LocaleInfo localeInfo;
            if (LanguageSelectActivity.this.searching && LanguageSelectActivity.this.searchWas) {
                if (i >= 0 && i < LanguageSelectActivity.this.searchResult.size()) {
                    localeInfo = (LocaleInfo) LanguageSelectActivity.this.searchResult.get(i);
                }
                localeInfo = null;
            } else {
                if (i >= 0 && i < LanguageSelectActivity.this.sortedLanguages.size()) {
                    localeInfo = (LocaleInfo) LanguageSelectActivity.this.sortedLanguages.get(i);
                }
                localeInfo = null;
            }
            if (localeInfo == null || localeInfo.pathToFile == null || LanguageSelectActivity.this.getParentActivity() == null || localeInfo.isRemote()) {
                return false;
            }
            Builder builder = new Builder(LanguageSelectActivity.this.getParentActivity());
            builder.setMessage(LocaleController.getString("DeleteLocalization", R.string.DeleteLocalization));
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            builder.setPositiveButton(LocaleController.getString("Delete", R.string.Delete), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (LocaleController.getInstance().deleteLanguage(localeInfo)) {
                        LanguageSelectActivity.this.fillLanguages();
                        if (LanguageSelectActivity.this.searchResult != null) {
                            LanguageSelectActivity.this.searchResult.remove(localeInfo);
                        }
                        if (LanguageSelectActivity.this.listAdapter != null) {
                            LanguageSelectActivity.this.listAdapter.notifyDataSetChanged();
                        }
                        if (LanguageSelectActivity.this.searchListViewAdapter != null) {
                            LanguageSelectActivity.this.searchListViewAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            LanguageSelectActivity.this.showDialog(builder.create());
            return true;
        }
    }

    /* renamed from: org.telegram.ui.LanguageSelectActivity$5 */
    class C48305 extends OnScrollListener {
        C48305() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 1 && LanguageSelectActivity.this.searching && LanguageSelectActivity.this.searchWas) {
                AndroidUtilities.hideKeyboard(LanguageSelectActivity.this.getParentActivity().getCurrentFocus());
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;
        private boolean search;

        public ListAdapter(Context context, boolean z) {
            this.mContext = context;
            this.search = z;
        }

        public int getItemCount() {
            return this.search ? LanguageSelectActivity.this.searchResult == null ? 0 : LanguageSelectActivity.this.searchResult.size() : LanguageSelectActivity.this.sortedLanguages.size();
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return true;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            LocaleInfo localeInfo;
            boolean z;
            boolean z2 = true;
            LanguageCell languageCell = (LanguageCell) viewHolder.itemView;
            if (this.search) {
                localeInfo = (LocaleInfo) LanguageSelectActivity.this.searchResult.get(i);
                z = i == LanguageSelectActivity.this.searchResult.size() + -1;
            } else {
                localeInfo = (LocaleInfo) LanguageSelectActivity.this.sortedLanguages.get(i);
                z = i == LanguageSelectActivity.this.sortedLanguages.size() + -1;
            }
            if (localeInfo.isLocal()) {
                languageCell.setLanguage(localeInfo, String.format("%1$s (%2$s)", new Object[]{localeInfo.name, LocaleController.getString("LanguageCustom", R.string.LanguageCustom)}), !z);
            } else {
                languageCell.setLanguage(localeInfo, null, !z);
            }
            if (localeInfo != LocaleController.getInstance().getCurrentLocaleInfo()) {
                z2 = false;
            }
            languageCell.setLanguageSelected(z2);
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new Holder(new LanguageCell(this.mContext, false));
        }
    }

    private void fillLanguages() {
        this.sortedLanguages = new ArrayList(LocaleController.getInstance().languages);
        final LocaleInfo currentLocaleInfo = LocaleController.getInstance().getCurrentLocaleInfo();
        Collections.sort(this.sortedLanguages, new Comparator<LocaleInfo>() {
            public int compare(LocaleInfo localeInfo, LocaleInfo localeInfo2) {
                return localeInfo == currentLocaleInfo ? -1 : localeInfo2 == currentLocaleInfo ? 1 : localeInfo.name.compareTo(localeInfo2.name);
            }
        });
    }

    private void processSearch(final String str) {
        Utilities.searchQueue.postRunnable(new Runnable() {
            public void run() {
                if (str.trim().toLowerCase().length() == 0) {
                    LanguageSelectActivity.this.updateSearchResults(new ArrayList());
                    return;
                }
                System.currentTimeMillis();
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < LanguageSelectActivity.this.sortedLanguages.size(); i++) {
                    LocaleInfo localeInfo = (LocaleInfo) LanguageSelectActivity.this.sortedLanguages.get(i);
                    if (localeInfo.name.toLowerCase().startsWith(str) || localeInfo.nameEnglish.toLowerCase().startsWith(str)) {
                        arrayList.add(localeInfo);
                    }
                }
                LanguageSelectActivity.this.updateSearchResults(arrayList);
            }
        });
    }

    private void updateSearchResults(final ArrayList<LocaleInfo> arrayList) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                LanguageSelectActivity.this.searchResult = arrayList;
                LanguageSelectActivity.this.searchListViewAdapter.notifyDataSetChanged();
            }
        });
    }

    public View createView(Context context) {
        this.searching = false;
        this.searchWas = false;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("Language", R.string.Language));
        this.actionBar.setActionBarMenuOnItemClick(new C48231());
        this.actionBar.createMenu().addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C48242()).getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        this.listAdapter = new ListAdapter(context, false);
        this.searchListViewAdapter = new ListAdapter(context, true);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.emptyView = new EmptyTextProgressView(context);
        this.emptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
        this.emptyView.showTextView();
        this.emptyView.setShowAtCenter(true);
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView = new RecyclerListView(context);
        this.listView.setEmptyView(this.emptyView);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setAdapter(this.listAdapter);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setOnItemClickListener(new C48273());
        this.listView.setOnItemLongClickListener(new C48294());
        this.listView.setOnScrollListener(new C48305());
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.suggestedLangpack && this.listAdapter != null) {
            fillLanguages();
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        r9 = new ThemeDescription[14];
        r9[10] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r9[11] = new ThemeDescription(this.listView, 0, new Class[]{LanguageCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[12] = new ThemeDescription(this.listView, 0, new Class[]{LanguageCell.class}, new String[]{"textView2"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        r9[13] = new ThemeDescription(this.listView, 0, new Class[]{LanguageCell.class}, new String[]{"checkImage"}, null, null, null, Theme.key_featuredStickers_addedIcon);
        return r9;
    }

    public boolean onFragmentCreate() {
        fillLanguages();
        LocaleController.getInstance().loadRemoteLanguages();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.suggestedLangpack);
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.suggestedLangpack);
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public void search(final String str) {
        if (str == null) {
            this.searchResult = null;
            return;
        }
        try {
            if (this.searchTimer != null) {
                this.searchTimer.cancel();
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
        this.searchTimer = new Timer();
        this.searchTimer.schedule(new TimerTask() {
            public void run() {
                try {
                    LanguageSelectActivity.this.searchTimer.cancel();
                    LanguageSelectActivity.this.searchTimer = null;
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                LanguageSelectActivity.this.processSearch(str);
            }
        }, 100, 300);
    }
}
