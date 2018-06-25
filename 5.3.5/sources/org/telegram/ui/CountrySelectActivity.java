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
    class C28491 extends ActionBarMenuOnItemClick {
        C28491() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                CountrySelectActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.CountrySelectActivity$2 */
    class C28502 extends ActionBarMenuItemSearchListener {
        C28502() {
        }

        public void onSearchExpand() {
            CountrySelectActivity.this.searching = true;
        }

        public void onSearchCollapse() {
            CountrySelectActivity.this.searchListViewAdapter.search(null);
            CountrySelectActivity.this.searching = false;
            CountrySelectActivity.this.searchWas = false;
            CountrySelectActivity.this.listView.setAdapter(CountrySelectActivity.this.listViewAdapter);
            CountrySelectActivity.this.listView.setFastScrollVisible(true);
            CountrySelectActivity.this.emptyView.setText(LocaleController.getString("ChooseCountry", R.string.ChooseCountry));
        }

        public void onTextChanged(EditText editText) {
            String text = editText.getText().toString();
            CountrySelectActivity.this.searchListViewAdapter.search(text);
            if (text.length() != 0) {
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
    class C28513 implements OnItemClickListener {
        C28513() {
        }

        public void onItemClick(View view, int position) {
            Country country;
            if (CountrySelectActivity.this.searching && CountrySelectActivity.this.searchWas) {
                country = CountrySelectActivity.this.searchListViewAdapter.getItem(position);
            } else {
                int section = CountrySelectActivity.this.listViewAdapter.getSectionForPosition(position);
                int row = CountrySelectActivity.this.listViewAdapter.getPositionInSectionForPosition(position);
                if (row >= 0 && section >= 0) {
                    country = CountrySelectActivity.this.listViewAdapter.getItem(section, row);
                } else {
                    return;
                }
            }
            if (position >= 0) {
                CountrySelectActivity.this.finishFragment();
                if (country != null && CountrySelectActivity.this.delegate != null) {
                    CountrySelectActivity.this.delegate.didSelectCountry(country.name, country.shortname);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.CountrySelectActivity$4 */
    class C28524 extends OnScrollListener {
        C28524() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == 1 && CountrySelectActivity.this.searching && CountrySelectActivity.this.searchWas) {
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
            this.mContext = context;
            try {
                InputStream stream = ApplicationLoader.applicationContext.getResources().getAssets().open("countries.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                while (true) {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    ArrayList<Country> arr;
                    String[] args = line.split(";");
                    Country c = new Country();
                    c.name = args[2];
                    c.code = args[0];
                    c.shortname = args[1];
                    String n = c.name.substring(0, 1).toUpperCase();
                    arr = (ArrayList) this.countries.get(n);
                    if (arr == null) {
                        arr = new ArrayList();
                        this.countries.put(n, arr);
                        this.sortedCountries.add(n);
                    }
                    arr.add(c);
                }
                reader.close();
                stream.close();
            } catch (Exception e) {
                FileLog.e(e);
            }
            Collections.sort(this.sortedCountries, new Comparator<String>(CountrySelectActivity.this) {
                public int compare(String lhs, String rhs) {
                    return lhs.compareTo(rhs);
                }
            });
            for (ArrayList<Country> arr2 : this.countries.values()) {
                Collections.sort(arr2, new Comparator<Country>(CountrySelectActivity.this) {
                    public int compare(Country country, Country country2) {
                        return country.name.compareTo(country2.name);
                    }
                });
            }
        }

        public HashMap<String, ArrayList<Country>> getCountries() {
            return this.countries;
        }

        public Country getItem(int section, int position) {
            if (section < 0 || section >= this.sortedCountries.size()) {
                return null;
            }
            ArrayList<Country> arr = (ArrayList) this.countries.get(this.sortedCountries.get(section));
            if (position < 0 || position >= arr.size()) {
                return null;
            }
            return (Country) arr.get(position);
        }

        public boolean isEnabled(int section, int row) {
            return row < ((ArrayList) this.countries.get(this.sortedCountries.get(section))).size();
        }

        public int getSectionCount() {
            return this.sortedCountries.size();
        }

        public int getCountForSection(int section) {
            int count = ((ArrayList) this.countries.get(this.sortedCountries.get(section))).size();
            if (section != this.sortedCountries.size() - 1) {
                return count + 1;
            }
            return count;
        }

        public View getSectionHeaderView(int section, View view) {
            if (view == null) {
                view = new LetterSectionCell(this.mContext);
                ((LetterSectionCell) view).setCellHeight(AndroidUtilities.dp(48.0f));
            }
            ((LetterSectionCell) view).setLetter(((String) this.sortedCountries.get(section)).toUpperCase());
            return view;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            float f = 72.0f;
            float f2 = 54.0f;
            int dp;
            switch (viewType) {
                case 0:
                    view = new TextSettingsCell(this.mContext);
                    dp = AndroidUtilities.dp(LocaleController.isRTL ? 16.0f : 54.0f);
                    if (!LocaleController.isRTL) {
                        f2 = 16.0f;
                    }
                    view.setPadding(dp, 0, AndroidUtilities.dp(f2), 0);
                    break;
                default:
                    float f3;
                    view = new DividerCell(this.mContext);
                    if (LocaleController.isRTL) {
                        f3 = 24.0f;
                    } else {
                        f3 = 72.0f;
                    }
                    dp = AndroidUtilities.dp(f3);
                    if (!LocaleController.isRTL) {
                        f = 24.0f;
                    }
                    view.setPadding(dp, 0, AndroidUtilities.dp(f), 0);
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(int section, int position, ViewHolder holder) {
            if (holder.getItemViewType() == 0) {
                Country c = (Country) ((ArrayList) this.countries.get(this.sortedCountries.get(section))).get(position);
                ((TextSettingsCell) holder.itemView).setTextAndValue(c.name, CountrySelectActivity.this.needPhoneCode ? "+" + c.code : null, false);
            }
        }

        public int getItemViewType(int section, int position) {
            return position < ((ArrayList) this.countries.get(this.sortedCountries.get(section))).size() ? 0 : 1;
        }

        public String getLetter(int position) {
            int section = getSectionForPosition(position);
            if (section == -1) {
                section = this.sortedCountries.size() - 1;
            }
            return (String) this.sortedCountries.get(section);
        }

        public int getPositionForScrollProgress(float progress) {
            return (int) (((float) getItemCount()) * progress);
        }
    }

    public class CountrySearchAdapter extends SelectionAdapter {
        private HashMap<String, ArrayList<Country>> countries;
        private Context mContext;
        private ArrayList<Country> searchResult;
        private Timer searchTimer;

        public CountrySearchAdapter(Context context, HashMap<String, ArrayList<Country>> countries) {
            this.mContext = context;
            this.countries = countries;
        }

        public void search(final String query) {
            if (query == null) {
                this.searchResult = null;
                return;
            }
            try {
                if (this.searchTimer != null) {
                    this.searchTimer.cancel();
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            this.searchTimer = new Timer();
            this.searchTimer.schedule(new TimerTask() {
                public void run() {
                    try {
                        CountrySearchAdapter.this.searchTimer.cancel();
                        CountrySearchAdapter.this.searchTimer = null;
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                    CountrySearchAdapter.this.processSearch(query);
                }
            }, 100, 300);
        }

        private void processSearch(final String query) {
            Utilities.searchQueue.postRunnable(new Runnable() {
                public void run() {
                    if (query.trim().toLowerCase().length() == 0) {
                        CountrySearchAdapter.this.updateSearchResults(new ArrayList());
                        return;
                    }
                    ArrayList<Country> resultArray = new ArrayList();
                    ArrayList<Country> arr = (ArrayList) CountrySearchAdapter.this.countries.get(query.substring(0, 1).toUpperCase());
                    if (arr != null) {
                        Iterator it = arr.iterator();
                        while (it.hasNext()) {
                            Country c = (Country) it.next();
                            if (c.name.toLowerCase().startsWith(query)) {
                                resultArray.add(c);
                            }
                        }
                    }
                    CountrySearchAdapter.this.updateSearchResults(resultArray);
                }
            });
        }

        private void updateSearchResults(final ArrayList<Country> arrCounties) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    CountrySearchAdapter.this.searchResult = arrCounties;
                    CountrySearchAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public boolean isEnabled(ViewHolder holder) {
            return true;
        }

        public int getItemCount() {
            if (this.searchResult == null) {
                return 0;
            }
            return this.searchResult.size();
        }

        public Country getItem(int i) {
            if (i < 0 || i >= this.searchResult.size()) {
                return null;
            }
            return (Country) this.searchResult.get(i);
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(new TextSettingsCell(this.mContext));
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            Country c = (Country) this.searchResult.get(position);
            ((TextSettingsCell) holder.itemView).setTextAndValue(c.name, CountrySelectActivity.this.needPhoneCode ? "+" + c.code : null, position != this.searchResult.size() + -1);
        }

        public int getItemViewType(int i) {
            return 0;
        }
    }

    public CountrySelectActivity(boolean phoneCode) {
        this.needPhoneCode = phoneCode;
    }

    public boolean onFragmentCreate() {
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }

    public View createView(Context context) {
        int i = 1;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("ChooseCountry", R.string.ChooseCountry));
        this.actionBar.setActionBarMenuOnItemClick(new C28491());
        this.actionBar.createMenu().addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C28502()).getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        this.searching = false;
        this.searchWas = false;
        this.listViewAdapter = new CountryAdapter(context);
        this.searchListViewAdapter = new CountrySearchAdapter(context, this.listViewAdapter.getCountries());
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = this.fragmentView;
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
        this.listView.setOnItemClickListener(new C28513());
        this.listView.setOnScrollListener(new C28524());
        return this.fragmentView;
    }

    public void onResume() {
        super.onResume();
        if (this.listViewAdapter != null) {
            this.listViewAdapter.notifyDataSetChanged();
        }
    }

    public void setCountrySelectActivityDelegate(CountrySelectActivityDelegate delegate) {
        this.delegate = delegate;
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
}
