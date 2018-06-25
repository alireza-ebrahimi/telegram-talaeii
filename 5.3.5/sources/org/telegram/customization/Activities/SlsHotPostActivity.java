package org.telegram.customization.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import org.ir.talaeii.R;
import org.telegram.customization.DataPool.PostPoolMulti;
import org.telegram.customization.Dialogs.FilterDialog;
import org.telegram.customization.Interfaces.FilterChangeListener;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Internet.WebservicePropertis;
import org.telegram.customization.Model.FilterItem;
import org.telegram.customization.dynamicadapter.DynamicAdapter;
import org.telegram.customization.dynamicadapter.Helper;
import org.telegram.customization.dynamicadapter.TagFilterAdapter;
import org.telegram.customization.dynamicadapter.data.ExtraData;
import org.telegram.customization.dynamicadapter.data.MediaType;
import org.telegram.customization.dynamicadapter.data.More;
import org.telegram.customization.dynamicadapter.data.NoResultType;
import org.telegram.customization.dynamicadapter.data.ObjBase;
import org.telegram.customization.dynamicadapter.data.SlsFilter;
import org.telegram.customization.dynamicadapter.data.SlsTag;
import org.telegram.customization.speechrecognitionview.RecognitionProgressView;
import org.telegram.customization.speechrecognitionview.adapters.RecognitionListenerAdapter;
import org.telegram.customization.util.Constants;
import org.telegram.customization.util.Prefs;
import org.telegram.customization.util.view.observerRecyclerView.ObservableRecyclerView;
import org.telegram.customization.util.view.observerRecyclerView.ScrollPositionChangesListener;
import org.telegram.customization.util.view.observerRecyclerView.ScrollState;
import org.telegram.customization.util.view.sva.JJSearchView;
import org.telegram.customization.util.view.sva.anim.controller.JJChangeArrowController;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.LaunchActivity;
import utils.app.AnimationUtil;
import utils.app.AppPreferences;
import utils.view.FarsiCheckBox;

public class SlsHotPostActivity extends FrameLayout implements ScrollPositionChangesListener, OnItemClickListener, IResponseReceiver, OnRefreshListener, Observer, OnClickListener, FilterChangeListener {
    public static ArrayList<FilterItem> CATEGORIES = null;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION_CODE = 1;
    ActionBar actionBar;
    TextView advanceSearch;
    AppBarLayout appbar;
    FarsiCheckBox cbPhraseSearch;
    ArrayList<SlsTag> channelList = new ArrayList();
    boolean channelListAdded = false;
    private Activity context;
    CoordinatorLayout coordinatorLayout;
    int counter = 0;
    private View errorView;
    EditText etTermSearchHolder;
    private boolean existMorePost = true;
    RecyclerView filterRecycleView;
    ArrayList<SlsFilter> filters1 = new ArrayList();
    int frgType = 0;
    private int fullChannelSize = 0;
    boolean hasSearchHolder;
    boolean hasStatisticHolder;
    boolean isCollapse = true;
    private View ivBack;
    ImageView ivVoiceSearch;
    ImageView ivVoiceSearch1;
    String lastTerm;
    private final int limit = 20;
    View llAdvanceContainer;
    private RelativeLayout llRootSearchHolder;
    /* renamed from: m */
    MediaType f54m;
    private JJSearchView mJJSearchViewSearchHolder;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        /* renamed from: org.telegram.customization.Activities.SlsHotPostActivity$17$1 */
        class C10941 implements Runnable {
            C10941() {
            }

            public void run() {
                SlsHotPostActivity.this.appbar.setExpanded(true);
                SlsHotPostActivity.this.ivBack.setVisibility(0);
            }
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.ACTION_SET_TAG_ID)) {
                if (SlsHotPostActivity.this.getPoolId() == intent.getIntExtra(utils.view.Constants.EXTRA_POOL_ID, 0)) {
                    Log.d("LEE", "CallApi Search onReceive");
                    try {
                        long tagId = intent.getLongExtra("EXTRA_TAG_ID", 0);
                        String tagName = intent.getStringExtra(Constants.EXTRA_TAG_NAME);
                        SlsHotPostActivity.this.setTagId((long) ((int) tagId));
                        if (SlsHotPostActivity.this.frgType == 2) {
                            SlsHotPostActivity.this.mediaType = -100;
                            SlsHotPostActivity.this.filters1 = new ArrayList();
                            SlsFilter slsFilter = new SlsFilter();
                            slsFilter.setName(tagName);
                            slsFilter.setId((int) tagId);
                            slsFilter.setDeletable(true);
                            slsFilter.setSelected(true);
                            slsFilter.setClickable(false);
                            SlsHotPostActivity.this.filters1.add(slsFilter);
                            SlsHotPostActivity.this.filterRecycleView.setVisibility(0);
                            SlsHotPostActivity.this.notifyFilterAdapter();
                            new Handler().postDelayed(new C10941(), 100);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SlsHotPostActivity.this.mainAdapter.getItems().clear();
                    SlsHotPostActivity.this.mainAdapter.notifyDataSetChanged();
                    SlsHotPostActivity.this.setExistMorePost(true);
                }
            } else if (intent.getAction().equals(Constants.ACTION_SEARCH)) {
                String searchTerm = intent.getStringExtra(Constants.EXTRA_SEARCH_STRING);
                long argMediaType = intent.getLongExtra(Constants.EXTRA_MEDIA_TYPE, 0);
                if (!TextUtils.isEmpty(searchTerm)) {
                    SlsHotPostActivity.this.etTermSearchHolder.setText(searchTerm);
                    SlsHotPostActivity.this.mediaType = argMediaType;
                }
                SlsHotPostActivity.this.executeSearching(true, argMediaType);
            } else if (intent.getAction().equals(Constants.ACTION_SHOW_SORT_DIALOG) && SlsHotPostActivity.this.frgType == 2) {
                SlsHotPostActivity.this.showSortOption();
            } else if (intent.getAction().equals(Constants.ACTION_SHOW_MORE)) {
                SlsHotPostActivity.this.mainAdapter.removeAllChannelItem();
                SlsHotPostActivity.this.mainAdapter.getItems().addAll(0, SlsHotPostActivity.this.channelList);
                SlsHotPostActivity.this.mainAdapter.notifyDataSetChanged();
                if (SlsHotPostActivity.this.more != null) {
                    SlsHotPostActivity.this.more.setTagCount(9999);
                }
            } else if (intent.getAction().equals(Constants.ACTION_SHOW_LESS)) {
                SlsHotPostActivity.this.mainAdapter.removeAllChannelItem();
                try {
                    if (SlsHotPostActivity.this.channelList != null && SlsHotPostActivity.this.channelList.size() > SlsHotPostActivity.this.numberOfChannelInCollapseMode) {
                        SlsHotPostActivity.this.mainAdapter.getItems().addAll(0, SlsHotPostActivity.this.channelList.subList(0, SlsHotPostActivity.this.numberOfChannelInCollapseMode));
                    }
                } catch (Exception e2) {
                }
                SlsHotPostActivity.this.mainAdapter.notifyDataSetChanged();
                if (SlsHotPostActivity.this.more != null) {
                    SlsHotPostActivity.this.more.setTagCount(3456789);
                }
                SlsHotPostActivity.this.recycler.scrollVerticallyToPosition(0);
            } else if (intent.getAction().equals(Constants.ACTION_SET_MEDIA_TYPE)) {
                long mediaT = intent.getLongExtra(Constants.EXTRA_MEDIA_TYPE, 0);
                boolean forHot = intent.getBooleanExtra(Constants.EXTRA_MEDIA_TYPE_IN_HOT, false);
                Log.d("LEE", "MediaType:" + mediaT);
                if (SlsHotPostActivity.this.mediaType != mediaT) {
                    SlsHotPostActivity.this.setExistMorePost(true);
                    if (SlsHotPostActivity.this.frgType != 2 || forHot) {
                        SlsHotPostActivity.this.mediaType = 0;
                        SlsHotPostActivity.this.setMediaType(mediaT);
                        int counter = 0;
                        if (SlsHotPostActivity.this.tagFilterAdapter != null && SlsHotPostActivity.this.tagFilterAdapter.getData() != null && SlsHotPostActivity.this.tagFilterAdapter.getData().size() > 0) {
                            Iterator it = SlsHotPostActivity.this.tagFilterAdapter.getData().iterator();
                            while (it.hasNext() && ((long) ((SlsFilter) it.next()).getId()) != mediaT) {
                                counter++;
                            }
                            SlsHotPostActivity.this.tagFilterAdapter.changeSelectedItem(counter);
                            return;
                        }
                        return;
                    }
                    SlsHotPostActivity.this.mediaType = mediaT;
                    SlsHotPostActivity.this.mainAdapter.updateMediaTypeItem(mediaT);
                    SlsHotPostActivity.this.mainAdapter.removeAllMessageItem();
                    SlsHotPostActivity.this.mainAdapter.removeNoResultItem();
                    PostPoolMulti.reset(SlsHotPostActivity.this.poolId);
                    SlsHotPostActivity.this.mainAdapter.notifyDataSetChanged();
                    SlsHotPostActivity.this.callApi(true);
                }
            }
        }
    };
    private DynamicAdapter mainAdapter;
    private long mediaType = 0;
    boolean mediaTypeAdded = false;
    More more;
    boolean moreHolderAdded = false;
    private int numberOfChannelInCollapseMode = 3;
    ProgressDialog pd = null;
    boolean phraseSearch;
    public int poolId;
    RecognitionProgressView recognitionProgressView;
    ObservableRecyclerView recycler;
    private View rootView;
    View searchHolder;
    View selectCategory;
    View selectFilterMediatype;
    private AlertDialog sortDialog;
    private View sortResult;
    private SpeechRecognizer speechRecognizer;
    View statisticHolder;
    private SwipeRefreshLayout swipeLayout;
    TagFilterAdapter tagFilterAdapter;
    private long tagId;
    private TextWatcher textWatcherSearchHolder;
    private long tmpMediaType = 0;
    TextView tvSearchHelp;
    TextView tvSelectedFilters;
    TextView tvSimpleSearch;
    private boolean viewCreated = false;

    /* renamed from: org.telegram.customization.Activities.SlsHotPostActivity$1 */
    class C10951 extends RecognitionListenerAdapter {
        C10951() {
        }

        public void onResults(Bundle results) {
            SlsHotPostActivity.this.recognitionProgressView.setVisibility(8);
            SlsHotPostActivity.this.showResults(results);
        }

        public void onError(int i) {
            if (i == 7) {
                SlsHotPostActivity.this.recognitionProgressView.stop();
                SlsHotPostActivity.this.recognitionProgressView.setVisibility(8);
                Log.d("alireza", "alireza on error speach");
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.SlsHotPostActivity$2 */
    class C10972 implements OnClickListener {

        /* renamed from: org.telegram.customization.Activities.SlsHotPostActivity$2$1 */
        class C10961 implements Runnable {
            C10961() {
            }

            public void run() {
                SlsHotPostActivity.this.startRecognition();
            }
        }

        C10972() {
        }

        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(SlsHotPostActivity.this.getContext(), "android.permission.RECORD_AUDIO") != 0) {
                SlsHotPostActivity.this.requestPermission();
                return;
            }
            SlsHotPostActivity.this.recognitionProgressView.setVisibility(0);
            SlsHotPostActivity.this.recognitionProgressView.play();
            SlsHotPostActivity.this.startRecognition();
            SlsHotPostActivity.this.recognitionProgressView.postDelayed(new C10961(), 50);
        }
    }

    /* renamed from: org.telegram.customization.Activities.SlsHotPostActivity$3 */
    class C10993 implements OnClickListener {

        /* renamed from: org.telegram.customization.Activities.SlsHotPostActivity$3$1 */
        class C10981 implements Runnable {
            C10981() {
            }

            public void run() {
                SlsHotPostActivity.this.startRecognition();
            }
        }

        C10993() {
        }

        public void onClick(View view) {
            SlsHotPostActivity.this.recognitionProgressView.setVisibility(0);
            SlsHotPostActivity.this.recognitionProgressView.play();
            SlsHotPostActivity.this.startRecognition();
            SlsHotPostActivity.this.recognitionProgressView.postDelayed(new C10981(), 50);
        }
    }

    /* renamed from: org.telegram.customization.Activities.SlsHotPostActivity$4 */
    class C11004 implements OnClickListener {
        C11004() {
        }

        public void onClick(View v) {
            ((InputMethodManager) SlsHotPostActivity.this.getContext().getSystemService("input_method")).toggleSoftInput(2, 0);
            if (!SlsHotPostActivity.this.isCollapse) {
                SlsHotPostActivity.this.toggleSearchView();
            }
            SlsHotPostActivity.this.executeSearching(false);
        }
    }

    /* renamed from: org.telegram.customization.Activities.SlsHotPostActivity$5 */
    class C11015 implements TextWatcher {
        C11015() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                SlsHotPostActivity.this.mJJSearchViewSearchHolder.resetAnim();
                SlsHotPostActivity.this.executeSearching(false);
                return;
            }
            SlsHotPostActivity.this.mJJSearchViewSearchHolder.startAnim();
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: org.telegram.customization.Activities.SlsHotPostActivity$6 */
    class C11026 implements OnEditorActionListener {
        C11026() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 3) {
                return false;
            }
            ((InputMethodManager) SlsHotPostActivity.this.getContext().getSystemService("input_method")).toggleSoftInput(2, 0);
            if (!SlsHotPostActivity.this.isCollapse) {
                SlsHotPostActivity.this.toggleSearchView();
            }
            SlsHotPostActivity.this.executeSearching(false);
            return true;
        }
    }

    /* renamed from: org.telegram.customization.Activities.SlsHotPostActivity$7 */
    class C11047 implements OnFocusChangeListener {

        /* renamed from: org.telegram.customization.Activities.SlsHotPostActivity$7$1 */
        class C11031 implements Runnable {
            C11031() {
            }

            public void run() {
                SlsHotPostActivity.this.etTermSearchHolder.selectAll();
            }
        }

        C11047() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            FileLog.d("onFocusChange has: " + hasFocus);
            if (hasFocus && SlsHotPostActivity.this.etTermSearchHolder.getText().toString().length() > 0) {
                SlsHotPostActivity.this.etTermSearchHolder.post(new C11031());
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.SlsHotPostActivity$8 */
    class C11058 implements Runnable {
        C11058() {
        }

        public void run() {
            SlsHotPostActivity.this.appbar.setVisibility(8);
            SlsHotPostActivity.this.ivBack.setVisibility(8);
        }
    }

    /* renamed from: org.telegram.customization.Activities.SlsHotPostActivity$9 */
    class C11069 implements Runnable {
        C11069() {
        }

        public void run() {
            SlsHotPostActivity.this.appbar.setVisibility(0);
            SlsHotPostActivity.this.tagFilterAdapter = new TagFilterAdapter(SlsHotPostActivity.this.context, SlsHotPostActivity.this.filters1, SlsHotPostActivity.this.filterRecycleView, SlsHotPostActivity.this);
            SlsHotPostActivity.this.tagFilterAdapter.setSelected(SlsHotPostActivity.this.mediaType);
            SlsHotPostActivity.this.filterRecycleView.setAdapter(SlsHotPostActivity.this.tagFilterAdapter);
        }
    }

    public boolean isPhraseSearch() {
        return this.phraseSearch;
    }

    public void setPhraseSearch(boolean phraseSearch) {
        this.phraseSearch = phraseSearch;
    }

    public boolean isMediaTypeAdded() {
        return this.mediaTypeAdded;
    }

    public void setMediaTypeAdded(boolean mediaTypeAdded) {
        this.mediaTypeAdded = mediaTypeAdded;
    }

    public SlsHotPostActivity(Activity context, int type, int poolId, ActionBar actionBar, String searchTerm) {
        super(context);
        this.lastTerm = searchTerm;
        this.frgType = type;
        this.actionBar = actionBar;
        setPoolId(poolId);
        init(context);
    }

    public SlsHotPostActivity(Activity context, int type, int poolId, ActionBar actionBar) {
        super(context);
        this.frgType = type;
        this.actionBar = actionBar;
        setPoolId(poolId);
        init(context);
    }

    public SlsHotPostActivity(Activity context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlsHotPostActivity(Activity context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public boolean isExistMorePost() {
        return this.existMorePost;
    }

    public void setExistMorePost(boolean existMorePost) {
        this.existMorePost = existMorePost;
    }

    public String getTerm() {
        this.lastTerm = this.etTermSearchHolder.getText().toString();
        if (this.lastTerm == null) {
            this.lastTerm = "";
        }
        return this.lastTerm;
    }

    public long getMediaType() {
        return this.mediaType;
    }

    public void setMediaType(long mediaType) {
        if (mediaType != this.mediaType) {
            reset();
            this.mediaType = mediaType;
            callApi(true);
        }
    }

    public int getType() {
        return this.frgType;
    }

    public void setType(int type) {
        this.frgType = type;
    }

    void init(Activity ctx) {
        this.context = ctx;
        addView(onCreateView((LayoutInflater) this.context.getSystemService("layout_inflater"), null, null), -1, -1);
        HandleRequest.getNew(this.context, this).getFilters();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout = new LinearLayout(this.context);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LayoutParams(-1, -1));
        linearLayout.setBackgroundColor(-1);
        this.rootView = inflater.inflate(R.layout.sls_fragment_hot, linearLayout, false);
        linearLayout.addView(this.rootView);
        this.rootView.findViewById(R.id.ll_container).setBackgroundResource(R.drawable.background_hd);
        initView();
        return linearLayout;
    }

    private void initView() {
        this.viewCreated = true;
        this.coordinatorLayout = (CoordinatorLayout) this.rootView.findViewById(R.id.quickreturn_coordinator);
        this.appbar = (AppBarLayout) this.rootView.findViewById(R.id.appBarLayout);
        RelativeLayout appbarCon = (RelativeLayout) this.appbar.findViewById(R.id.appbar_container);
        if (!Theme.getCurrentThemeName().contentEquals("hotgram")) {
            appbarCon.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        }
        hideActionbarItems();
        View llcon = this.rootView.findViewById(R.id.ll_container);
        llcon.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        if (Theme.getCurrentThemeName().contentEquals("Dark")) {
            llcon.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        } else {
            llcon.setBackgroundDrawable(Theme.getCachedWallpaper());
        }
        this.ivBack = this.appbar.findViewById(R.id.iv_back);
        this.ivBack.setOnClickListener(this);
        this.errorView = this.rootView.findViewById(R.id.error_layout);
        setExistMorePost(true);
        this.swipeLayout = (SwipeRefreshLayout) this.rootView.findViewById(R.id.refresher);
        this.swipeLayout.setOnRefreshListener(this);
        int[] iArr = new int[3];
        this.swipeLayout.setColorSchemeResources(new int[]{R.color.flag_text_color, R.color.elv_btn_pressed, R.color.pink});
        this.swipeLayout.setProgressViewOffset(true, 200, ScheduleDownloadActivity.CHECK_CELL2);
        this.filterRecycleView = (RecyclerView) this.rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.context, 0, false);
        layoutManager.setReverseLayout(true);
        this.filterRecycleView.setLayoutManager(layoutManager);
        this.statisticHolder = this.rootView.findViewById(R.id.statisticHolder);
        this.searchHolder = this.rootView.findViewById(R.id.searchHolder);
        this.etTermSearchHolder = (EditText) this.rootView.findViewById(R.id.et_term);
        this.llRootSearchHolder = (RelativeLayout) this.rootView.findViewById(R.id.ll_root);
        this.mJJSearchViewSearchHolder = (JJSearchView) this.rootView.findViewById(R.id.jjsv);
        this.mJJSearchViewSearchHolder.setController(new JJChangeArrowController());
        this.advanceSearch = (TextView) this.rootView.findViewById(R.id.tv_advance_search);
        this.tvSimpleSearch = (TextView) this.rootView.findViewById(R.id.tv_simple_saerch);
        this.tvSimpleSearch.setOnClickListener(this);
        this.advanceSearch.setOnClickListener(this);
        this.tvSearchHelp = (TextView) this.rootView.findViewById(R.id.tv_search_help);
        this.llAdvanceContainer = this.rootView.findViewById(R.id.ll_advance_container);
        this.selectCategory = this.rootView.findViewById(R.id.select_category);
        this.selectFilterMediatype = this.rootView.findViewById(R.id.select_filter_type);
        this.searchHolder.findViewById(R.id.btn_search).setOnClickListener(this);
        this.cbPhraseSearch = (FarsiCheckBox) this.rootView.findViewById(R.id.cb_search_exact);
        this.tvSelectedFilters = (TextView) this.rootView.findViewById(R.id.tv_selected_filters);
        this.ivVoiceSearch = (ImageView) this.searchHolder.findViewById(R.id.iv_voice_search);
        this.ivVoiceSearch1 = (ImageView) this.searchHolder.findViewById(R.id.iv_voice_search1);
        int[] colors = new int[]{ContextCompat.getColor(getContext(), R.color.color1), ContextCompat.getColor(getContext(), R.color.color2), ContextCompat.getColor(getContext(), R.color.color3), ContextCompat.getColor(getContext(), R.color.color4), ContextCompat.getColor(getContext(), R.color.color5)};
        int i = 5;
        int[] heights = new int[]{20, 24, 18, 23, 16};
        this.speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        this.recognitionProgressView = (RecognitionProgressView) this.searchHolder.findViewById(R.id.recognition_view);
        this.recognitionProgressView.setSpeechRecognizer(this.speechRecognizer);
        this.recognitionProgressView.setColors(colors);
        this.recognitionProgressView.setBarMaxHeightsInDp(heights);
        this.recognitionProgressView.setCircleRadiusInDp(2);
        this.recognitionProgressView.setSpacingInDp(2);
        this.recognitionProgressView.setIdleStateAmplitudeInDp(2);
        this.recognitionProgressView.setRotationRadiusInDp(10);
        if (!TextUtils.isEmpty(this.lastTerm)) {
            this.etTermSearchHolder.setText(this.lastTerm);
        }
        this.recognitionProgressView.setRecognitionListener(new C10951());
        this.ivVoiceSearch.setOnClickListener(new C10972());
        this.ivVoiceSearch1.setOnClickListener(new C10993());
        ((TextView) this.selectFilterMediatype.findViewById(R.id.title)).setText(R.string.serch_in_media);
        ((TextView) this.selectCategory.findViewById(R.id.title)).setText(R.string.serch_in_category);
        this.selectCategory.setOnClickListener(this);
        this.selectFilterMediatype.setOnClickListener(this);
        String mystring = new String("جستجوی پیشرفته");
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        this.advanceSearch.setText(content);
        this.advanceSearch.setOnClickListener(this);
        String mystring1 = new String("راهنمای جستجوی پیشرفته");
        SpannableString content1 = new SpannableString(mystring1);
        content1.setSpan(new UnderlineSpan(), 0, mystring1.length(), 0);
        this.tvSearchHelp.setText(content1);
        String mystring2 = new String("جستجوی ساده");
        SpannableString content2 = new SpannableString(mystring2);
        content2.setSpan(new UnderlineSpan(), 0, mystring2.length(), 0);
        this.tvSimpleSearch.setText(content2);
        this.mJJSearchViewSearchHolder.setOnClickListener(new C11004());
        if (this.textWatcherSearchHolder == null) {
            this.textWatcherSearchHolder = new C11015();
            this.etTermSearchHolder.addTextChangedListener(this.textWatcherSearchHolder);
            this.etTermSearchHolder.setOnEditorActionListener(new C11026());
        }
        this.etTermSearchHolder.setOnFocusChangeListener(new C11047());
        this.etTermSearchHolder.setSelectAllOnFocus(true);
        this.recycler = (ObservableRecyclerView) this.rootView.findViewById(R.id.recycler);
        this.recycler.setScrollPositionChangesListener(this);
        this.recycler.setLayoutManager(new LinearLayoutManager(this.context, 1, false));
        ExtraData extraData = new ExtraData();
        extraData.setPoolId(getPoolId());
        extraData.setTagId(this.tagId);
        this.mainAdapter = new DynamicAdapter(this.context, extraData);
        this.recycler.setAdapter(this.mainAdapter);
        callApi(true);
        notifyFilterAdapter();
        disableDrawer();
    }

    private void hideActionbarItems() {
        if (this.actionBar != null) {
            ActionBarMenu i = this.actionBar.createMenu();
            try {
                i.getItem(2).setVisibility(8);
                i.getItem(0).setVisibility(8);
                i.getItem(4).setVisibility(8);
                i.getItem(1).setVisibility(8);
                i.getItem(8).setVisibility(8);
                i.getItem(5).setVisibility(8);
                i.getItem(7).setVisibility(8);
                i.getItem(6).setVisibility(8);
                this.actionBar.getTitleTextView().setGravity(5);
            } catch (Exception e) {
            }
        }
    }

    private void executeSearching(boolean justScrollToTop) {
        executeSearching(justScrollToTop, 0);
    }

    private void executeSearching(boolean justScrollToTop, long argMediaType) {
        if (TextUtils.isEmpty(getTerm())) {
            this.ivVoiceSearch.setVisibility(0);
            this.recognitionProgressView.stop();
            this.recognitionProgressView.setVisibility(8);
        }
        reset();
        if (argMediaType != 0) {
            this.mediaType = argMediaType;
        }
        this.lastTerm = this.etTermSearchHolder.getText().toString();
        this.moreHolderAdded = false;
        if (this.filters1 != null) {
            this.filters1.clear();
        }
        this.appbar.setExpanded(false);
        AndroidUtilities.runOnUIThread(new C11058());
        PostPoolMulti.reset(getPoolId());
        this.tagId = 0;
        this.mainAdapter.setItems(new ArrayList());
        this.recycler.setAdapter(this.mainAdapter);
        endLoading();
        AndroidUtilities.hideKeyboard(this.rootView);
        callApi(false);
    }

    private void notifyFilterAdapter() {
        notifyFilterAdapter(false);
    }

    private void notifyFilterAdapter(boolean apiCalled) {
        if (this.frgType != 2 && (this.filters1 == null || this.filters1.isEmpty())) {
            this.filters1 = Prefs.getFilters(this.context);
        }
        if (this.filters1 == null || this.filters1.size() <= 0) {
            if (!apiCalled) {
                HandleRequest.getNew(this.context, this).getFilters();
            }
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    SlsHotPostActivity.this.appbar.setVisibility(8);
                }
            });
            return;
        }
        this.filterRecycleView.post(new C11069());
    }

    void startLoading(final boolean getOlderPost) {
        this.swipeLayout.post(new Runnable() {
            public void run() {
                if (!getOlderPost || SlsHotPostActivity.this.mainAdapter.getItems().size() < 1) {
                    SlsHotPostActivity.this.swipeLayout.setRefreshing(true);
                }
            }
        });
        if (getOlderPost) {
            this.recycler.setLoadingStart(true);
        } else {
            this.recycler.setLoadingEnd(true);
        }
        hideErrorView();
    }

    void endLoading() {
        this.swipeLayout.post(new Runnable() {
            public void run() {
                SlsHotPostActivity.this.swipeLayout.setRefreshing(false);
            }
        });
        this.recycler.setLoadingEnd(false);
        this.recycler.setLoadingStart(false);
    }

    void callApi(boolean getOlderPost) {
        disableDrawer();
        if (isExistMorePost() || !getOlderPost) {
            startLoading(getOlderPost);
            if (this.frgType == 1) {
                this.filterRecycleView.setVisibility(0);
                try {
                    HandleRequest.getNew(this.context, this).getHome(getTagId(), getOlderPost ? getLastPostId() : getFirstPostId(), getOlderPost, getMediaType(), 20);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            } else if (this.frgType == 2) {
                long lastPostId;
                if (getTerm().isEmpty() && getTagId() == 0) {
                    setExistMorePost(false);
                    this.mainAdapter.setHaveProgress(false);
                } else {
                    setExistMorePost(true);
                    this.mainAdapter.setHaveProgress(true);
                }
                if (getTagId() == 0) {
                    this.filterRecycleView.setVisibility(8);
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            SlsHotPostActivity.this.appbar.setVisibility(8);
                            SlsHotPostActivity.this.ivBack.setVisibility(8);
                        }
                    });
                }
                HandleRequest.cancelApi(WebservicePropertis.WS_GET_SEARCH);
                HandleRequest handleRequest = HandleRequest.getNew(this.context, this);
                long tagId = getTagId();
                if (getOlderPost) {
                    lastPostId = getLastPostId();
                } else {
                    lastPostId = getFirstPostId();
                }
                handleRequest.getSearchPost(tagId, lastPostId, getOlderPost, getTerm(), getMediaType(), 20, PostPoolMulti.getSortOrder(getPoolId()), PostPoolMulti.isPhraseSearch(this.poolId));
                return;
            } else if (this.frgType == 3) {
                endLoading();
                HandleRequest.getNew(this.context, this).getDashboard(getTagId(), getOlderPost ? getLastPostId() : getFirstPostId(), getOlderPost, getMediaType(), 20);
                return;
            } else {
                return;
            }
        }
        endLoading();
        this.mainAdapter.setHaveProgress(false);
    }

    private void disableDrawer() {
        ((LaunchActivity) this.context).drawerLayoutContainer.setAllowOpenDrawer(false, false);
    }

    private void enableDrawer() {
        ((LaunchActivity) this.context).drawerLayoutContainer.setAllowOpenDrawer(true, false);
    }

    private long getFirstPostId() {
        if (this.mainAdapter == null || this.mainAdapter.getItems().isEmpty()) {
            return 0;
        }
        return (long) ((ObjBase) this.mainAdapter.getItems().get(0)).getRow();
    }

    private long getLastPostId() {
        if (this.mainAdapter == null || this.mainAdapter.getItems().isEmpty()) {
            return 0;
        }
        return (long) ((ObjBase) this.mainAdapter.getItems().get((this.mainAdapter.getItemCount() - 1) - (this.mainAdapter.isHaveProgress() ? 1 : 0))).getRow();
    }

    void reset() {
        this.moreHolderAdded = false;
        this.channelListAdded = false;
        setMediaTypeAdded(false);
        Log.d("LEE", "TagId:" + getTagId());
        this.channelList = new ArrayList();
        setExistMorePost(true);
        PostPoolMulti.reset(getPoolId());
        this.tagId = 0;
        this.mediaType = 0;
        this.rootView.findViewById(R.id.error_layout).setVisibility(8);
        this.recycler.setVisibility(0);
        this.recycler.invalidate();
        this.appbar.setExpanded(false);
        this.appbar.setExpanded(true);
        this.mainAdapter.setItems(new ArrayList());
        this.recycler.setAdapter(this.mainAdapter);
        if (!ConnectionsManager.isNetworkOnline()) {
            ReachedEnd(this.rootView);
        }
        this.hasSearchHolder = false;
        this.hasStatisticHolder = false;
    }

    public void ReachedEnd(View view) {
        this.swipeLayout.setEnabled(false);
        callApi(true);
    }

    public void ReachedStart(View view) {
        this.recycler.setLoadingEnd(false);
        this.recycler.setLoadingStart(false);
        this.swipeLayout.setEnabled(true);
    }

    public void LeavedBoundaries(View view, int pos) {
        this.swipeLayout.setEnabled(false);
    }

    public void handleScrollState(ScrollState ss) {
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (((SlsFilter) this.tagFilterAdapter.getData().get(position)).isClickable()) {
            Log.d("alireza", "alireza  ttttt " + position + "---- " + ((SlsFilter) this.tagFilterAdapter.getData().get(position)).getId());
            this.tagFilterAdapter.changeSelectedItem(position);
            setMediaType((long) ((SlsFilter) this.tagFilterAdapter.getData().get(position)).getId());
        }
    }

    public void onResult(Object object, int StatusCode) {
        switch (StatusCode) {
            case -1000:
                endLoading();
                showErrorView(-1000);
                Log.d("LEE", "showErrorView 3");
                return;
            case -1:
                showErrorView(utils.view.Constants.ERROR_GET_DATA);
                Log.d("LEE", "showErrorView 2");
                endLoading();
                return;
            case 1:
                ArrayList<ObjBase> items;
                if (object != null) {
                    items = (ArrayList) object;
                } else {
                    items = new ArrayList();
                }
                if (items.size() < 20) {
                    setExistMorePost(false);
                    this.mainAdapter.setHaveProgress(false);
                }
                final ArrayList<ObjBase> tmpItem = new ArrayList();
                if (this.frgType == 2) {
                    setUpStatisticView(Helper.getStatisticsItem(items));
                    setUpSearchView(Helper.getSearchItem(items));
                    if (TextUtils.isEmpty(this.lastTerm) && Helper.getImportantItem(items) != null) {
                        tmpItem.add(Helper.getImportantItem(items));
                    }
                    if (TextUtils.isEmpty(this.lastTerm) && Helper.getSuggestedSearchItem(items) != null) {
                        tmpItem.add(Helper.getSuggestedSearchItem(items));
                    }
                    if (this.searchHolder != null) {
                        this.searchHolder.setVisibility(this.hasSearchHolder ? 0 : 8);
                    }
                    if (this.statisticHolder != null) {
                        this.statisticHolder.setVisibility(this.hasStatisticHolder ? 0 : 8);
                    }
                    if (Helper.getFullChannelItems(items).size() > 0) {
                        this.channelList = Helper.getFullChannelItems(items);
                    }
                    if (TextUtils.isEmpty(getTerm())) {
                        tmpItem.addAll(this.channelList);
                    } else {
                        if (!this.channelListAdded) {
                            if (this.channelList.size() > this.numberOfChannelInCollapseMode) {
                                tmpItem.addAll(new ArrayList(Helper.getLimitedChannelItems(this.channelList, this.numberOfChannelInCollapseMode)));
                            } else {
                                tmpItem.addAll(new ArrayList(this.channelList));
                            }
                        }
                        if (this.channelList.size() > 0) {
                            this.channelListAdded = true;
                        }
                        if (!this.moreHolderAdded) {
                            this.more = new More();
                            this.more.setSortTitle(getSortOptionTitle(PostPoolMulti.getSortOrder(this.poolId)));
                            tmpItem.add(tmpItem.size(), this.more);
                            this.moreHolderAdded = true;
                            if (this.channelList.size() > this.numberOfChannelInCollapseMode) {
                                this.more.setShowMoreButtonVisibility(true);
                            } else {
                                this.more.setShowMoreButtonVisibility(false);
                            }
                        }
                        if (!isMediaTypeAdded()) {
                            this.f54m = new MediaType();
                            this.f54m.setSelectedMediaType(this.mediaType);
                            tmpItem.add(tmpItem.size(), this.f54m);
                            setMediaTypeAdded(true);
                        }
                    }
                }
                ArrayList<ObjBase> messageList = Helper.getMessageItems(items);
                tmpItem.addAll(messageList);
                if (!TextUtils.isEmpty(this.lastTerm) && messageList.size() == 0) {
                    NoResultType objBase = new NoResultType();
                    objBase.setType(110);
                    tmpItem.add(objBase);
                    setExistMorePost(false);
                    this.mainAdapter.setHaveProgress(false);
                    this.mainAdapter.notifyDataSetChanged();
                }
                PostPoolMulti.addAll(getPoolId(), tmpItem);
                PostPoolMulti.setSearchTerm(getPoolId(), getTerm());
                PostPoolMulti.setTakeNewsLimit(getPoolId(), 20);
                PostPoolMulti.setMediaType(getPoolId(), getMediaType());
                this.mainAdapter.addItemsBySort(tmpItem);
                try {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                SlsHotPostActivity.this.mainAdapter.notifyItemRangeInserted(SlsHotPostActivity.this.mainAdapter.getItemCount(), tmpItem.size());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 150);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final int scrollPos = AppPreferences.getHomeFragmentPosition(ApplicationLoader.applicationContext, this.frgType);
                if (scrollPos > 0) {
                    this.recycler.post(new Runnable() {
                        public void run() {
                            SlsHotPostActivity.this.recycler.getLayoutManager().scrollToPosition(scrollPos);
                            AppPreferences.setHomeFragmentPosition(ApplicationLoader.applicationContext, SlsHotPostActivity.this.frgType, 0);
                        }
                    });
                }
                endLoading();
                return;
            case 2:
                Log.d("LEE", "showErrorView 4");
                notifyFilterAdapter(true);
                return;
            default:
                return;
        }
    }

    private void setUpSearchView(ObjBase searchItem) {
        if (searchItem != null) {
            if (!TextUtils.isEmpty(searchItem.getTitle())) {
                this.etTermSearchHolder.setHint(searchItem.getTitle() + "");
            }
            if (this.etTermSearchHolder.getText().toString().length() <= 0) {
                this.etTermSearchHolder.clearFocus();
                this.rootView.findViewById(R.id.linearLayout_focus).requestFocus();
                this.hasSearchHolder = true;
            } else {
                this.etTermSearchHolder.clearFocus();
                this.rootView.findViewById(R.id.linearLayout_focus).requestFocus();
                this.hasSearchHolder = true;
            }
        }
    }

    private void setUpStatisticView(ObjBase statisticsItem) {
        if (statisticsItem != null) {
            if (this.actionBar != null) {
                this.actionBar.searchTabTitle = statisticsItem.getTitle();
                this.actionBar.actionBarFontSize = 12;
                this.actionBar.setTitle(statisticsItem.getTitle() + "");
            }
            this.hasStatisticHolder = true;
        }
    }

    private void showErrorView(int errorCode) {
        if (this.frgType != 2) {
            this.recycler.setVisibility(8);
        }
        this.mainAdapter.setHaveProgress(false);
        this.mainAdapter.notifyDataSetChanged();
        setExistMorePost(false);
        this.errorView.setVisibility(0);
        this.appbar.setExpanded(false);
        TextView tvMessage = (TextView) this.errorView.findViewById(R.id.txt_error);
        ImageView ivError = (ImageView) this.errorView.findViewById(R.id.iv_error);
        endLoading();
        switch (errorCode) {
            case utils.view.Constants.ERROR_EMPTY /*-3000*/:
                tvMessage.setText(getResources().getString(R.string.err_empty));
                ivError.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.sad));
                break;
            case utils.view.Constants.ERROR_GET_DATA /*-2000*/:
                tvMessage.setText(getResources().getString(R.string.err_get_data));
                ivError.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.sad));
                break;
            case -1000:
                tvMessage.setText(getResources().getString(R.string.network_error));
                ivError.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.wifi_off));
                break;
        }
        this.swipeLayout.setEnabled(true);
    }

    private void hideErrorView() {
        this.recycler.setVisibility(0);
        this.errorView.setVisibility(8);
    }

    public void onRefresh() {
        this.filters1.clear();
        notifyFilterAdapter();
        reset();
        callApi(true);
    }

    public void update(Observable observable, Object data) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (SlsHotPostActivity.this.mainAdapter != null) {
                    try {
                        SlsHotPostActivity.this.mainAdapter.notifyItemRangeChanged(SlsHotPostActivity.this.mainAdapter.getItemCount(), 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 100);
    }

    public void saveYourself() {
        try {
            AppPreferences.setHomeFragmentPosition(ApplicationLoader.applicationContext, this.frgType, ((LinearLayoutManager) this.recycler.getLayoutManager()).findFirstCompletelyVisibleItemPosition());
        } catch (Exception e) {
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        PostPoolMulti.getObservable().addObserver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_SET_TAG_ID);
        intentFilter.addAction(Constants.ACTION_SHOW_MORE);
        intentFilter.addAction(Constants.ACTION_SHOW_LESS);
        intentFilter.addAction(Constants.ACTION_SET_MEDIA_TYPE);
        intentFilter.addAction(Constants.ACTION_SHOW_SORT_DIALOG);
        if (this.frgType == 2) {
            intentFilter.addAction(Constants.ACTION_SEARCH);
        }
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this.mMessageReceiver, intentFilter);
        new Handler().postDelayed(new Runnable() {
            public void run() {
            }
        }, 100);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        PostPoolMulti.getObservable().deleteObserver(this);
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this.mMessageReceiver);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                executeSearching(true);
                return;
            case R.id.tv_advance_search:
                toggleSearchView();
                return;
            case R.id.select_category:
                new FilterDialog(this.context, "جستجو براساس دسته بندی", 1, getCategories(), this).show();
                return;
            case R.id.select_filter_type:
                ArrayList<FilterItem> items = new ArrayList();
                Iterator it = Prefs.getFilters(this.context).iterator();
                while (it.hasNext()) {
                    SlsFilter filter = (SlsFilter) it.next();
                    FilterItem filterItem = new FilterItem();
                    filterItem.setId((long) filter.getId());
                    filterItem.setName(filter.getName());
                    filterItem.setType(2);
                    items.add(filterItem);
                }
                new FilterDialog(this.context, "جستجو براساس نوع محتوا", 2, items, this).show();
                return;
            case R.id.tv_simple_saerch:
                toggleSearchView();
                return;
            case R.id.btn_search:
                toggleSearchView();
                this.mainAdapter.getItems().clear();
                this.mainAdapter.notifyDataSetChanged();
                PostPoolMulti.setPhraseSearch(getPoolId(), this.cbPhraseSearch.isChecked());
                this.tvSelectedFilters.setText(getSelectedFilterString());
                callApi(false);
                return;
            default:
                return;
        }
    }

    private void toggleSearchView() {
        if (this.isCollapse) {
            AnimationUtil.expand(this.llAdvanceContainer);
        } else {
            AnimationUtil.collapse(this.llAdvanceContainer);
        }
        this.isCollapse = !this.isCollapse;
    }

    private String getSortOptionTitle(long optionId) {
        Iterator it = Prefs.getSorts(getContext()).iterator();
        while (it.hasNext()) {
            SlsFilter filter = (SlsFilter) it.next();
            if (((long) filter.getId()) == optionId) {
                return filter.getName();
            }
        }
        return "مرتب سازی";
    }

    private void showSortOption() {
        final ArrayList<SlsFilter> sorts = Prefs.getSorts(getContext());
        if (sorts != null && !sorts.isEmpty()) {
            final Dialog dialog = new Dialog(this.context, R.style.Theme_Dialog);
            dialog.requestWindowFeature(1);
            dialog.setContentView(R.layout.dialog_sort_filter);
            this.context.getWindow().setLayout(-1, -1);
            ((TextView) dialog.findViewById(R.id.header)).setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
            TextView btnDone = (TextView) dialog.findViewById(R.id.done);
            final LinearLayout llSortContainer = (LinearLayout) dialog.findViewById(R.id.ll_sort_container);
            final RecyclerView filterRec = (RecyclerView) dialog.findViewById(R.id.filterRecyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.context, 0, false);
            layoutManager.setReverseLayout(true);
            filterRec.setLayoutManager(layoutManager);
            this.filters1 = Prefs.getFilters(this.context);
            if (this.filters1 != null && this.filters1.size() > 0) {
                filterRec.post(new Runnable() {
                    public void run() {
                        SlsHotPostActivity.this.tagFilterAdapter = new TagFilterAdapter(SlsHotPostActivity.this.context, SlsHotPostActivity.this.filters1, filterRec, SlsHotPostActivity.this);
                        filterRec.setAdapter(SlsHotPostActivity.this.tagFilterAdapter);
                    }
                });
            }
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
            Iterator it = sorts.iterator();
            while (it.hasNext()) {
                SlsFilter sort = (SlsFilter) it.next();
                View viewCheckbox = inflater.inflate(R.layout.checkbox_option_item, llSortContainer, false);
                FarsiCheckBox checkBox = (FarsiCheckBox) viewCheckbox.findViewById(R.id.checkbox2);
                checkBox.setId(sort.getId());
                checkBox.setTag(Integer.valueOf(sort.getId()));
                checkBox.setTitle(sort.getName());
                if (PostPoolMulti.getSortOrder(this.poolId) == ((long) sort.getId())) {
                    checkBox.setChecked(true);
                }
                checkBox.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Iterator it = sorts.iterator();
                        while (it.hasNext()) {
                            SlsFilter sort = (SlsFilter) it.next();
                            if (((Integer) view.getTag()).intValue() != sort.getId()) {
                                FarsiCheckBox cb = (FarsiCheckBox) llSortContainer.findViewById(sort.getId());
                                cb.setChecked(false);
                                cb.setSelected(false);
                            }
                        }
                    }
                });
                llSortContainer.addView(viewCheckbox);
            }
            btnDone.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    int selected = 0;
                    Iterator it = sorts.iterator();
                    while (it.hasNext()) {
                        SlsFilter sort = (SlsFilter) it.next();
                        if (((FarsiCheckBox) llSortContainer.findViewById(sort.getId())).isChecked()) {
                            selected = sort.getId();
                            break;
                        }
                    }
                    try {
                        int value = ((SlsFilter) sorts.get(selected)).getValue();
                        Log.d("alireza", "alireza sort option id : " + value);
                        PostPoolMulti.setSortOrder(SlsHotPostActivity.this.getPoolId(), (long) value);
                        SlsHotPostActivity.this.reset();
                        SlsHotPostActivity.this.callApi(false);
                    } catch (Exception e) {
                        FileLog.d(e + "");
                    }
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    public void onResume() {
        if (this.viewCreated) {
            disableDrawer();
        }
    }

    public int getPoolId() {
        return this.poolId;
    }

    public void setPoolId(int poolId) {
        this.poolId = poolId;
    }

    public long getTagId() {
        return this.tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }

    public void onFilterChange(FilterItem item) {
        switch (item.getType()) {
            case 1:
                ((TextView) this.selectCategory.findViewById(R.id.title)).setText(item.getName());
                setTagId(item.getId());
                return;
            case 2:
                ((TextView) this.selectFilterMediatype.findViewById(R.id.title)).setText(item.getName());
                this.mediaType = item.getId();
                return;
            case 3:
                PostPoolMulti.setSortOrder(getPoolId(), item.getId());
                return;
            default:
                return;
        }
    }

    public ArrayList<FilterItem> getCategories() {
        if (CATEGORIES == null) {
            CATEGORIES = new ArrayList();
            Iterator it = this.mainAdapter.getItems().iterator();
            while (it.hasNext()) {
                ObjBase objBase = (ObjBase) it.next();
                if (objBase.getType() == 101) {
                    SlsTag myObj = (SlsTag) objBase;
                    FilterItem filterItem = new FilterItem();
                    filterItem.setId(myObj.getId());
                    filterItem.setName(myObj.getShowName());
                    filterItem.setType(1);
                    CATEGORIES.add(filterItem);
                }
            }
        }
        return CATEGORIES;
    }

    public String getSelectedFilterString() {
        String ans = "";
        if (PostPoolMulti.isPhraseSearch(this.poolId)) {
            return "جستجوی دقیق فعال است";
        }
        return ans;
    }

    private void startRecognition() {
        Log.d("alireza", "alireza voice search clicked1");
        Intent recognizerIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        recognizerIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        recognizerIntent.putExtra("android.speech.extra.LANGUAGE", "fa-IR");
        recognizerIntent.putExtra("android.speech.extra.LANGUAGE_PREFERENCE", "fa-IR");
        recognizerIntent.putExtra("android.speech.extra.MAX_RESULTS", 1);
        this.speechRecognizer.startListening(recognizerIntent);
    }

    private void showResults(Bundle results) {
        this.etTermSearchHolder.setText((CharSequence) results.getStringArrayList("results_recognition").get(0));
        this.mJJSearchViewSearchHolder.performClick();
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.context, "android.permission.RECORD_AUDIO")) {
            Toast.makeText(getContext(), "Requires RECORD_AUDIO permission", 0).show();
            return;
        }
        ActivityCompat.requestPermissions(this.context, new String[]{"android.permission.RECORD_AUDIO"}, 1);
    }
}
