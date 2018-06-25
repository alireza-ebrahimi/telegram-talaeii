package org.telegram.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.DividerCell;
import org.telegram.ui.Cells.LetterSectionCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SectionsAdapter;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class CountrySelectActivity extends BaseFragment {
    private CountrySelectActivityDelegate delegate;
    private EmptyTextProgressView emptyView;
    private RecyclerListView listView;
    private CountryAdapter listViewAdapter;
    private boolean needPhoneCode;
    private CountrySearchAdapter searchListViewAdapter;
    private boolean searchWas;
    private boolean searching;

    public interface CountrySelectActivityDelegate {
        void didSelectCountry(String str, String str2);
    }

    /* renamed from: org.telegram.ui.CountrySelectActivity$1 */
    class C46871 extends ActionBarMenuOnItemClick {
        C46871() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                CountrySelectActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.CountrySelectActivity$2 */
    class C46882 extends ActionBarMenuItemSearchListener {
        C46882() {
        }

        public void onSearchCollapse() {
            CountrySelectActivity.this.searchListViewAdapter.search(null);
            CountrySelectActivity.this.searching = false;
            CountrySelectActivity.this.searchWas = false;
            CountrySelectActivity.this.listView.setAdapter(CountrySelectActivity.this.listViewAdapter);
            CountrySelectActivity.this.listView.setFastScrollVisible(true);
            CountrySelectActivity.this.emptyView.setText(LocaleController.getString("ChooseCountry", R.string.ChooseCountry));
        }

        public void onSearchExpand() {
            CountrySelectActivity.this.searching = true;
        }

        public void onTextChanged(EditText editText) {
            String obj = editText.getText().toString();
            CountrySelectActivity.this.searchListViewAdapter.search(obj);
            if (obj.length() != 0) {
                CountrySelectActivity.this.searchWas = true;
                if (CountrySelectActivity.this.listView != null) {
                    CountrySelectActivity.this.listView.setAdapter(CountrySelectActivity.this.searchListViewAdapter);
                    CountrySelectActivity.this.listView.setFastScrollVisible(false);
                }
                if (CountrySelectActivity.this.emptyView == null) {
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.CountrySelectActivity$3 */
    class C46893 implements OnItemClickListener {
        C46893() {
        }

        public void onItemClick(View view, int i) {
            Country item;
            if (CountrySelectActivity.this.searching && CountrySelectActivity.this.searchWas) {
                item = CountrySelectActivity.this.searchListViewAdapter.getItem(i);
            } else {
                int sectionForPosition = CountrySelectActivity.this.listViewAdapter.getSectionForPosition(i);
                int positionInSectionForPosition = CountrySelectActivity.this.listViewAdapter.getPositionInSectionForPosition(i);
                if (positionInSectionForPosition >= 0 && sectionForPosition >= 0) {
                    item = CountrySelectActivity.this.listViewAdapter.getItem(sectionForPosition, positionInSectionForPosition);
                } else {
                    return;
                }
            }
            if (i >= 0) {
                CountrySelectActivity.this.finishFragment();
                if (item != null && CountrySelectActivity.this.delegate != null) {
                    CountrySelectActivity.this.delegate.didSelectCountry(item.name, item.shortname);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.CountrySelectActivity$4 */
    class C46904 extends OnScrollListener {
        C46904() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 1 && CountrySelectActivity.this.searching && CountrySelectActivity.this.searchWas) {
                AndroidUtilities.hideKeyboard(CountrySelectActivity.this.getParentActivity().getCurrentFocus());
            }
        }
    }

    public static class Country {
        public String code;
        public String name;
        public String shortname;
    }

    public class CountryAdapter extends SectionsAdapter {
        private HashMap<String, ArrayList<Country>> countries = new HashMap();
        private Context mContext;
        private ArrayList<String> sortedCountries = new ArrayList();

        public CountryAdapter(Context context) {
            ArrayList arrayList;
            this.mContext = context;
            try {
                InputStream open = ApplicationLoader.applicationContext.getResources().getAssets().open("countries.txt");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(open));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    String[] split = readLine.split(";");
                    Country country = new Country();
                    country.name = split[2];
                    country.code = split[0];
                    country.shortname = split[1];
                    String toUpperCase = country.name.substring(0, 1).toUpperCase();
                    arrayList = (ArrayList) this.countries.get(toUpperCase);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        this.countries.put(toUpperCase, arrayList);
                        this.sortedCountries.add(toUpperCase);
                    }
                    arrayList.add(country);
                }
                bufferedReader.close();
                open.close();
            } catch (Throwable e) {
                FileLog.e(e);
            }
            Collections.sort(this.sortedCountries, new Comparator<String>(CountrySelectActivity.this) {
                public int compare(String str, String str2) {
                    return str.compareTo(str2);
                }
            });
            for (ArrayList arrayList2 : this.countries.values()) {
                Collections.sort(arrayList2, new Comparator<Country>(CountrySelectActivity.this) {
                    public int compare(Country country, Country country2) {
                        return country.name.compareTo(country2.name);
                    }
                });
            }
        }

        public int getCountForSection(int i) {
            int size = ((ArrayList) this.countries.get(this.sortedCountries.get(i))).size();
            return i != this.sortedCountries.size() + -1 ? size + 1 : size;
        }

        public HashMap<String, ArrayList<Country>> getCountries() {
            return this.countries;
        }

        public Country getItem(int i, int i2) {
            if (i < 0 || i >= this.sortedCountries.size()) {
                return null;
            }
            ArrayList arrayList = (ArrayList) this.countries.get(this.sortedCountries.get(i));
            return (i2 < 0 || i2 >= arrayList.size()) ? null : (Country) arrayList.get(i2);
        }

        public int getItemViewType(int i, int i2) {
            return i2 < ((ArrayList) this.countries.get(this.sortedCountries.get(i))).size() ? 0 : 1;
        }

        public String getLetter(int i) {
            int sectionForPosition = getSectionForPosition(i);
            if (sectionForPosition == -1) {
                sectionForPosition = this.sortedCountries.size() - 1;
            }
            return (String) this.sortedCountries.get(sectionForPosition);
        }

        public int getPositionForScrollProgress(float f) {
            return (int) (((float) getItemCount()) * f);
        }

        public int getSectionCount() {
            return this.sortedCountries.size();
        }

        public View getSectionHeaderView(int i, View view) {
            View letterSectionCell;
            if (view == null) {
                letterSectionCell = new LetterSectionCell(this.mContext);
                ((LetterSectionCell) letterSectionCell).setCellHeight(AndroidUtilities.dp(48.0f));
            } else {
                letterSectionCell = view;
            }
            ((LetterSectionCell) letterSectionCell).setLetter(((String) this.sortedCountries.get(i)).toUpperCase());
            return letterSectionCell;
        }

        public boolean isEnabled(int i, int i2) {
            return i2 < ((ArrayList) this.countries.get(this.sortedCountries.get(i))).size();
        }

        public void onBindViewHolder(int i, int i2, ViewHolder viewHolder) {
            if (viewHolder.getItemViewType() == 0) {
                Country country = (Country) ((ArrayList) this.countries.get(this.sortedCountries.get(i))).get(i2);
                ((TextSettingsCell) viewHolder.itemView).setTextAndValue(country.name, CountrySelectActivity.this.needPhoneCode ? "+" + country.code : null, false);
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view;
            float f = 72.0f;
            float f2 = 54.0f;
            int dp;
            switch (i) {
                case 0:
                    View textSettingsCell = new TextSettingsCell(this.mContext);
                    dp = AndroidUtilities.dp(LocaleController.isRTL ? 16.0f : 54.0f);
                    if (!LocaleController.isRTL) {
                        f2 = 16.0f;
                    }
                    textSettingsCell.setPadding(dp, 0, AndroidUtilities.dp(f2), 0);
                    view = textSettingsCell;
                    break;
                default:
                    View dividerCell = new DividerCell(this.mContext);
                    dp = AndroidUtilities.dp(LocaleController.isRTL ? 24.0f : 72.0f);
                    if (!LocaleController.isRTL) {
                        f = 24.0f;
                    }
                    dividerCell.setPadding(dp, 0, AndroidUtilities.dp(f), 0);
                    view = dividerCell;
                    break;
            }
            return new Holder(view);
        }
    }

    public class CountrySearchAdapter extends SelectionAdapter {
        private HashMap<String, ArrayList<Country>> countries;
        private Context mContext;
        private ArrayList<Country> searchResult;
        private Timer searchTimer;

        public CountrySearchAdapter(Context context, HashMap<String, ArrayList<Country>> hashMap) {
            this.mContext = context;
            this.countries = hashMap;
        }

        private void processSearch(final String str) {
            Utilities.searchQueue.postRunnable(new Runnable() {
                public void run() {
                    if (str.trim().toLowerCase().length() == 0) {
                        CountrySearchAdapter.this.updateSearchResults(new ArrayList());
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = (ArrayList) CountrySearchAdapter.this.countries.get(str.substring(0, 1).toUpperCase());
                    if (arrayList2 != null) {
                        Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            Country country = (Country) it.next();
                            if (country.name.toLowerCase().startsWith(str)) {
                                arrayList.add(country);
                            }
                        }
                    }
                    CountrySearchAdapter.this.updateSearchResults(arrayList);
                }
            });
        }

        private void updateSearchResults(final ArrayList<Country> arrayList) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    CountrySearchAdapter.this.searchResult = arrayList;
                    CountrySearchAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public Country getItem(int i) {
            return (i < 0 || i >= this.searchResult.size()) ? null : (Country) this.searchResult.get(i);
        }

        public int getItemCount() {
            return this.searchResult == null ? 0 : this.searchResult.size();
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return true;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Country country = (Country) this.searchResult.get(i);
            ((TextSettingsCell) viewHolder.itemView).setTextAndValue(country.name, CountrySelectActivity.this.needPhoneCode ? "+" + country.code : null, i != this.searchResult.size() + -1);
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new Holder(new TextSettingsCell(this.mContext));
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
                        CountrySearchAdapter.this.searchTimer.cancel();
                        CountrySearchAdapter.this.searchTimer = null;
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                    CountrySearchAdapter.this.processSearch(str);
                }
            }, 100, 300);
        }
    }

    public CountrySelectActivity(boolean z) {
        this.needPhoneCode = z;
    }

    public View createView(Context context) {
        int i = 1;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("ChooseCountry", R.string.ChooseCountry));
        this.actionBar.setActionBarMenuOnItemClick(new C46871());
        this.actionBar.createMenu().addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C46882()).getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        this.searching = false;
        this.searchWas = false;
        this.listViewAdapter = new CountryAdapter(context);
        this.searchListViewAdapter = new CountrySearchAdapter(context, this.listViewAdapter.getCountries());
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.emptyView = new EmptyTextProgressView(context);
        this.emptyView.showTextView();
        this.emptyView.setShowAtCenter(true);
        this.emptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView = new RecyclerListView(context);
        this.listView.setSectionsType(1);
        this.listView.setEmptyView(this.emptyView);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setFastScrollEnabled();
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.listView.setAdapter(this.listViewAdapter);
        RecyclerListView recyclerListView = this.listView;
        if (!LocaleController.isRTL) {
            i = 2;
        }
        recyclerListView.setVerticalScrollbarPosition(i);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setOnItemClickListener(new C46893());
        this.listView.setOnScrollListener(new C46904());
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        r9 = new ThemeDescription[17];
        r9[9] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r9[10] = new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollActive);
        r9[11] = new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollInactive);
        r9[12] = new ThemeDescription(this.listView, ThemeDescription.FLAG_FASTSCROLL, null, null, null, null, Theme.key_fastScrollText);
        r9[13] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder);
        r9[14] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[15] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        r9[16] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SECTIONS, new Class[]{LetterSectionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        return r9;
    }

    public boolean onFragmentCreate() {
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }

    public void onResume() {
        super.onResume();
        if (this.listViewAdapter != null) {
            this.listViewAdapter.notifyDataSetChanged();
        }
    }

    public void setCountrySelectActivityDelegate(CountrySelectActivityDelegate countrySelectActivityDelegate) {
        this.delegate = countrySelectActivityDelegate;
    }
}
