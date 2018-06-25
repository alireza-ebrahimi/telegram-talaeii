package org.telegram.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.text.Html.ImageGetter;
import android.text.Layout;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.ImageViewerActivity;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.util.AppImageLoader;
import org.telegram.news.model.News;
import org.telegram.ui.ActionBar.Theme;
import utils.Utilities;
import utils.app.AppPreferences;
import utils.view.CircularProgress;
import utils.view.Constants;
import utils.view.FarsiTextView;

public class NewsPage extends LinearLayout implements ImageGetter, IResponseReceiver, ObservableScrollViewCallbacks, OnClickListener, IFontSizeChanging, OnPageChangeListener {
    TextView Duration;
    Activity activity;
    TextView agencyName;
    View btnAddComment;
    private boolean calledWebService = false;
    TextView creationDate;
    float fontSize = 0.0f;
    TextView ftvNewsTitle;
    TextView ftvSendReport;
    News fullDetailsNews = new News();
    private boolean haveImage = false;
    View headerGrid;
    View imageCountView;
    LayoutInflater inflater;
    public boolean isOKToAddImage = false;
    boolean isScrollLog = false;
    ImageView ivAgencyLogo;
    LinearLayout ivArchive;
    ImageView ivBack;
    ImageView ivDislike;
    ImageView ivFave;
    ImageView ivFirstImage;
    ImageView ivLike;
    ImageView ivShare;
    LinearLayout llChild;
    LinearLayout llComments;
    LinearLayout llDislike;
    LinearLayout llExtraContent;
    LinearLayout llInfo;
    LinearLayout llLike;
    LinearLayout llRetry;
    LinearLayout llShare;
    LinearLayout llShowMoreComments;
    LinearLayout llThumbnailContainer;
    final Integer lockBitmap = Integer.valueOf(1);
    private int mActionBarSize;
    private int mFlexibleSpaceImageHeight;
    private View mImageView;
    private View mOverlayView;
    private ObservableScrollView mScrollView;
    private Toolbar mToolbar;
    View metaData;
    LinearLayout myLL;
    private View newsDateLayout;
    Activity newsDescriptionActivity;
    private DisplayImageOptions options;
    View pageLoading;
    CircularProgress pbImage;
    View playBtn;
    View polStatus;
    int position;
    ProgressBar progress;
    View rlContext;
    private String searchTerm;
    News singleNews;
    LinearLayout tagContainer;
    private int tagId = 0;
    volatile ArrayList<WeakReference<TextView>> textViews = new ArrayList();
    TextView title;
    View vTop;
    View videoCountView;
    View view;

    /* renamed from: org.telegram.news.NewsPage$2 */
    class C19292 implements Runnable {
        C19292() {
        }

        public void run() {
            NewsPage.this.mScrollView.scrollTo(0, NewsPage.this.mFlexibleSpaceImageHeight - NewsPage.this.mActionBarSize);
            NewsPage.this.mScrollView.scrollTo(0, 1);
            NewsPage.this.mScrollView.scrollTo(0, 0);
        }
    }

    /* renamed from: org.telegram.news.NewsPage$3 */
    class C19303 implements OnClickListener {
        C19303() {
        }

        public void onClick(View v) {
            NewsPage.this.callWebservice(NewsPage.this.singleNews);
        }
    }

    /* renamed from: org.telegram.news.NewsPage$4 */
    class C19314 implements Runnable {
        C19314() {
        }

        public void run() {
            LayoutParams lp = NewsPage.this.view.getLayoutParams();
            lp.height = -1;
            lp.width = -1;
            NewsPage.this.view.setLayoutParams(lp);
        }
    }

    /* renamed from: org.telegram.news.NewsPage$5 */
    class C19415 implements Runnable {

        /* renamed from: org.telegram.news.NewsPage$5$1 */
        class C19321 implements OnClickListener {
            C19321() {
            }

            public void onClick(View view) {
            }
        }

        /* renamed from: org.telegram.news.NewsPage$5$6 */
        class C19376 implements OnClickListener {
            C19376() {
            }

            public void onClick(View view) {
            }
        }

        /* renamed from: org.telegram.news.NewsPage$5$8 */
        class C19398 implements ImageLoadingListener {
            C19398() {
            }

            public void onLoadingStarted(String imageUri, View view) {
            }

            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            }

            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                synchronized (NewsPage.this.lockBitmap) {
                    NewsPage.this.ivFirstImage.setImageDrawable(new MyBitmapDrawable(NewsPage.this.activity.getResources(), loadedImage));
                    NewsPage.this.onScrollChanged(1, true, true);
                    NewsPage.this.pbImage.setVisibility(8);
                    FadeInBitmapDisplayer.animate(view, 500);
                }
            }

            public void onLoadingCancelled(String imageUri, View view) {
            }
        }

        /* renamed from: org.telegram.news.NewsPage$5$9 */
        class C19409 implements OnClickListener {
            C19409() {
            }

            public void onClick(View v) {
                Intent intent = new Intent(NewsPage.this.activity, ImageViewerActivity.class);
                intent.putExtra("id", new Gson().toJson(NewsPage.this.fullDetailsNews.getImageUrls()));
                NewsPage.this.activity.startActivity(intent);
            }
        }

        C19415() {
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r43 = this;
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.llRetry;
            r7 = 8;
            r6.setVisibility(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.pageLoading;
            r7 = 8;
            r6.setVisibility(r7);
            r21 = 0;
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.llRetry;
            r7 = 8;
            r6.setVisibility(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.rlContext;
            r7 = 0;
            r6.setVisibility(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.agencyName;
            r0 = r43;
            r7 = org.telegram.news.NewsPage.this;
            r7 = r7.fullDetailsNews;
            r7 = r7.getAgencyName();
            r6.setText(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.agencyName;
            r0 = r43;
            r7 = org.telegram.news.NewsPage.this;
            r6.setOnClickListener(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.ivAgencyLogo;
            r0 = r43;
            r7 = org.telegram.news.NewsPage.this;
            r6.setOnClickListener(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r6 = r6.getVideoCount();
            if (r6 <= 0) goto L_0x02bb;
        L_0x0066:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.videoCountView;
            r7 = 0;
            r6.setVisibility(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.videoCountView;
            r7 = 2131689997; // 0x7f0f020d float:1.9009025E38 double:1.053194795E-314;
            r6 = r6.findViewById(r7);
            r6 = (android.widget.TextView) r6;
            r7 = new java.lang.StringBuilder;
            r7.<init>();
            r0 = r43;
            r0 = org.telegram.news.NewsPage.this;
            r37 = r0;
            r0 = r37;
            r0 = r0.fullDetailsNews;
            r37 = r0;
            r37 = r37.getVideoCount();
            r0 = r37;
            r7 = r7.append(r0);
            r37 = "";
            r0 = r37;
            r7 = r7.append(r0);
            r7 = r7.toString();
            r6.setText(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.playBtn;
            r7 = 0;
            r6.setVisibility(r7);
        L_0x00b4:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r6 = r6.getImageCount();
            r7 = 1;
            if (r6 <= r7) goto L_0x02e0;
        L_0x00c1:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.imageCountView;
            r7 = 0;
            r6.setVisibility(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.imageCountView;
            r7 = 2131690000; // 0x7f0f0210 float:1.9009031E38 double:1.0531947966E-314;
            r6 = r6.findViewById(r7);
            r6 = (android.widget.TextView) r6;
            r7 = new java.lang.StringBuilder;
            r7.<init>();
            r0 = r43;
            r0 = org.telegram.news.NewsPage.this;
            r37 = r0;
            r0 = r37;
            r0 = r0.fullDetailsNews;
            r37 = r0;
            r37 = r37.getImageCount();
            r0 = r37;
            r7 = r7.append(r0);
            r37 = "";
            r0 = r37;
            r7 = r7.append(r0);
            r7 = r7.toString();
            r6.setText(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r6 = r6.getVideoCount();
            r7 = 1;
            if (r6 >= r7) goto L_0x02d3;
        L_0x0112:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.headerGrid;
            r7 = 0;
            r6.setVisibility(r7);
        L_0x011c:
            r18 = new java.util.Date;	 Catch:{ Exception -> 0x02f8 }
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;	 Catch:{ Exception -> 0x02f8 }
            r6 = r6.fullDetailsNews;	 Catch:{ Exception -> 0x02f8 }
            r6 = r6.getCreationDate();	 Catch:{ Exception -> 0x02f8 }
            r6 = (double) r6;	 Catch:{ Exception -> 0x02f8 }
            r38 = 4652007308841189376; // 0x408f400000000000 float:0.0 double:1000.0;
            r6 = r6 * r38;
            r6 = (long) r6;	 Catch:{ Exception -> 0x02f8 }
            r0 = r18;
            r0.<init>(r6);	 Catch:{ Exception -> 0x02f8 }
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;	 Catch:{ Exception -> 0x02f8 }
            r6 = r6.creationDate;	 Catch:{ Exception -> 0x02f8 }
            r7 = utils.Utilities.getCurrentShamsidate(r18);	 Catch:{ Exception -> 0x02f8 }
            r6.setText(r7);	 Catch:{ Exception -> 0x02f8 }
        L_0x0143:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.ftvNewsTitle;
            r0 = r43;
            r7 = org.telegram.news.NewsPage.this;
            r7 = r7.fullDetailsNews;
            r7 = r7.getTitle();
            r6.setText(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.title;
            r0 = r43;
            r7 = org.telegram.news.NewsPage.this;
            r7 = r7.fullDetailsNews;
            r7 = r7.getTitle();
            r6.setText(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.myLL;
            r6.removeAllViews();
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.llExtraContent;
            r6.removeAllViews();
            r25 = 0;
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r6 = r6.getContent();
            if (r6 == 0) goto L_0x0679;
        L_0x0189:
            r24 = -1;
            r17 = -1;
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r7 = r6.getContent();
            r0 = r7.length;
            r37 = r0;
            r6 = 0;
        L_0x019b:
            r0 = r37;
            if (r6 >= r0) goto L_0x0242;
        L_0x019f:
            r15 = r7[r6];
            r17 = r17 + 1;
            r38 = r15.getType();
            r39 = 3;
            r0 = r38;
            r1 = r39;
            if (r0 != r1) goto L_0x031e;
        L_0x01af:
            r21 = 1;
            r24 = r17;
            r13 = new android.widget.LinearLayout$LayoutParams;
            r6 = -1;
            r7 = -2;
            r13.<init>(r6, r7);
            r6 = 0;
            r7 = 0;
            r37 = 0;
            r38 = 0;
            r0 = r37;
            r1 = r38;
            r13.setMargins(r6, r7, r0, r1);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.rlContext;
            r6.setLayoutParams(r13);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.Duration;
            r7 = 0;
            r6.setVisibility(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.playBtn;
            r7 = 0;
            r6.setVisibility(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.headerGrid;
            r7 = 8;
            r6.setVisibility(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.Duration;
            r7 = r15.getDuration();
            r6.setText(r7);
            r6 = "sadegh";
            r7 = "vTop.setOnClickListenerL : video for1";
            android.util.Log.d(r6, r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.vTop;
            r7 = new org.telegram.news.NewsPage$5$1;
            r0 = r43;
            r7.<init>();
            r6.setOnClickListener(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r7 = 1;
            r6.isOKToAddImage = r7;
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r7 = 1;
            r6.haveImage = r7;
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r0 = r6.position;
            r26 = r0;
            r36 = r15.getVideoImage();
            r6 = android.text.TextUtils.isEmpty(r36);
            if (r6 == 0) goto L_0x02fe;
        L_0x0236:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.ivFirstImage;
            r7 = 2130838202; // 0x7f0202ba float:1.728138E38 double:1.0527739525E-314;
            r6.setImageResource(r7);
        L_0x0242:
            r6 = "LEE";
            r7 = new java.lang.StringBuilder;
            r7.<init>();
            r37 = "ContentDesc:";
            r0 = r37;
            r7 = r7.append(r0);
            r0 = r43;
            r0 = org.telegram.news.NewsPage.this;
            r37 = r0;
            r0 = r37;
            r0 = r0.fullDetailsNews;
            r37 = r0;
            r37 = r37.getContent();
            r0 = r37;
            r0 = r0.length;
            r37 = r0;
            r0 = r37;
            r7 = r7.append(r0);
            r7 = r7.toString();
            android.util.Log.d(r6, r7);
            r17 = -1;
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r38 = r6.getContent();
            r0 = r38;
            r0 = r0.length;
            r39 = r0;
            r6 = 0;
            r37 = r6;
        L_0x0289:
            r0 = r37;
            r1 = r39;
            if (r0 >= r1) goto L_0x0679;
        L_0x028f:
            r15 = r38[r37];
            r17 = r17 + 1;
            r6 = r15.getType();
            switch(r6) {
                case 1: goto L_0x0322;
                case 2: goto L_0x0401;
                case 3: goto L_0x0563;
                case 4: goto L_0x029a;
                case 5: goto L_0x029a;
                case 6: goto L_0x029a;
                case 7: goto L_0x05fc;
                default: goto L_0x029a;
            };
        L_0x029a:
            r6 = r25.getParent();	 Catch:{ Exception -> 0x0921 }
            r6 = (android.view.ViewGroup) r6;	 Catch:{ Exception -> 0x0921 }
            r0 = r25;
            r6.removeView(r0);	 Catch:{ Exception -> 0x0921 }
        L_0x02a5:
            r0 = r24;
            r1 = r17;
            if (r0 == r1) goto L_0x02b6;
        L_0x02ab:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.myLL;
            r0 = r25;
            r6.addView(r0);
        L_0x02b6:
            r6 = r37 + 1;
            r37 = r6;
            goto L_0x0289;
        L_0x02bb:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.videoCountView;
            r7 = 8;
            r6.setVisibility(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.playBtn;
            r7 = 8;
            r6.setVisibility(r7);
            goto L_0x00b4;
        L_0x02d3:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.headerGrid;
            r7 = 8;
            r6.setVisibility(r7);
            goto L_0x011c;
        L_0x02e0:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.imageCountView;
            r7 = 8;
            r6.setVisibility(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.headerGrid;
            r7 = 8;
            r6.setVisibility(r7);
            goto L_0x011c;
        L_0x02f8:
            r19 = move-exception;
            r19.printStackTrace();
            goto L_0x0143;
        L_0x02fe:
            r6 = org.telegram.news.AminImageLoader.getInstance();
            r0 = r43;
            r7 = org.telegram.news.NewsPage.this;
            r7 = r7.getOptions();
            r37 = new org.telegram.news.NewsPage$5$2;
            r0 = r37;
            r1 = r43;
            r2 = r26;
            r0.<init>(r2);
            r0 = r36;
            r1 = r37;
            r6.loadImage(r0, r7, r1);
            goto L_0x0242;
        L_0x031e:
            r6 = r6 + 1;
            goto L_0x019b;
        L_0x0322:
            r6 = "LEE";
            r7 = new java.lang.StringBuilder;
            r7.<init>();
            r40 = "ContentDesc:";
            r0 = r40;
            r7 = r7.append(r0);
            r40 = r15.getDescription();
            r0 = r40;
            r7 = r7.append(r0);
            r7 = r7.toString();
            android.util.Log.d(r6, r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.activity;
            r6 = android.view.LayoutInflater.from(r6);
            r7 = 2130903126; // 0x7f030056 float:1.7413061E38 double:1.052806029E-314;
            r0 = r43;
            r0 = org.telegram.news.NewsPage.this;
            r40 = r0;
            r0 = r40;
            r0 = r0.myLL;
            r40 = r0;
            r41 = 0;
            r0 = r40;
            r1 = r41;
            r25 = r6.inflate(r7, r0, r1);
            r6 = 2131689844; // 0x7f0f0174 float:1.9008715E38 double:1.0531947195E-314;
            r0 = r25;
            r31 = r0.findViewById(r6);
            r31 = (android.widget.TextView) r31;
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.activity;
            r6 = org.telegram.news.NewsPage.MyMovementMethod.getInstance(r6);
            r0 = r31;
            r0.setMovementMethod(r6);
            r6 = r15.getDescription();
            r7 = "<blockquote>";
            r40 = "";
            r0 = r40;
            r6 = r6.replace(r7, r0);
            r7 = "</blockquote>";
            r40 = "<br/>";
            r0 = r40;
            r6 = r6.replace(r7, r0);
            r7 = "<p>";
            r40 = "";
            r0 = r40;
            r6 = r6.replace(r7, r0);
            r7 = "</p>";
            r40 = "";
            r0 = r40;
            r6 = r6.replace(r7, r0);
            r0 = r43;
            r7 = org.telegram.news.NewsPage.this;
            r40 = 0;
            r0 = r40;
            r22 = android.text.Html.fromHtml(r6, r7, r0);
            r27 = new android.text.SpannedString;
            r0 = r27;
            r1 = r22;
            r0.<init>(r1);
            r0 = r31;
            r1 = r27;
            r0.setText(r1);
            r6 = 2;
            r0 = r43;
            r7 = org.telegram.news.NewsPage.this;
            r7 = r7.fontSize;
            r0 = r31;
            r0.setTextSize(r6, r7);
            r6 = 1;
            r0 = r31;
            r0.setTextIsSelectable(r6);
            r6 = 1;
            r0 = r31;
            r0.setFocusable(r6);
            r6 = 1;
            r0 = r31;
            r0.setFocusableInTouchMode(r6);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.textViews;
            r7 = new java.lang.ref.WeakReference;
            r0 = r31;
            r7.<init>(r0);
            r6.add(r7);
            goto L_0x029a;
        L_0x0401:
            r6 = r15.getUrls();	 Catch:{ Exception -> 0x0927 }
            r7 = 0;
            r6 = r6[r7];	 Catch:{ Exception -> 0x0927 }
            if (r6 == 0) goto L_0x02b6;
        L_0x040a:
            r6 = r15.getUrls();	 Catch:{ Exception -> 0x0927 }
            r7 = 0;
            r6 = r6[r7];	 Catch:{ Exception -> 0x0927 }
            r6 = r6.length();	 Catch:{ Exception -> 0x0927 }
            r7 = 10;
            if (r6 < r7) goto L_0x02b6;
        L_0x0419:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;	 Catch:{ Exception -> 0x0927 }
            r6 = r6.fullDetailsNews;	 Catch:{ Exception -> 0x0927 }
            r6 = r6.getImageUrls();	 Catch:{ Exception -> 0x0927 }
            r7 = 0;
            r6 = r6.get(r7);	 Catch:{ Exception -> 0x0927 }
            r6 = (java.lang.String) r6;	 Catch:{ Exception -> 0x0927 }
            r7 = r15.getUrls();	 Catch:{ Exception -> 0x0927 }
            r40 = 0;
            r7 = r7[r40];	 Catch:{ Exception -> 0x0927 }
            r6 = r6.equals(r7);	 Catch:{ Exception -> 0x0927 }
            if (r6 == 0) goto L_0x0438;
        L_0x0438:
            if (r21 != 0) goto L_0x0560;
        L_0x043a:
            r10 = 1;
        L_0x043b:
            if (r10 == 0) goto L_0x04c9;
        L_0x043d:
            r24 = r17;
            r21 = 1;
            r13 = new android.widget.LinearLayout$LayoutParams;
            r6 = -1;
            r7 = -2;
            r13.<init>(r6, r7);
            r6 = 0;
            r7 = 0;
            r40 = 0;
            r41 = 0;
            r0 = r40;
            r1 = r41;
            r13.setMargins(r6, r7, r0, r1);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.rlContext;
            r6.setLayoutParams(r13);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.metaData;
            r7 = 0;
            r6.setVisibility(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.Duration;
            r7 = 8;
            r6.setVisibility(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.playBtn;
            r7 = 8;
            r6.setVisibility(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.Duration;
            r7 = r15.getDuration();
            r6.setText(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.metaData;
            r7 = 2131690000; // 0x7f0f0210 float:1.9009031E38 double:1.0531947966E-314;
            r30 = r6.findViewById(r7);
            r30 = (android.widget.TextView) r30;
            r6 = r15.getUrls();
            r6 = r6.length;
            r6 = java.lang.String.valueOf(r6);
            r0 = r30;
            r0.setText(r6);
            r6 = "sadegh";
            r7 = "vTop.setOnClickListenerL : img for2";
            android.util.Log.d(r6, r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.vTop;
            r7 = new org.telegram.news.NewsPage$5$3;
            r0 = r43;
            r7.<init>(r15);
            r6.setOnClickListener(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r7 = 1;
            r6.haveImage = r7;
        L_0x04c9:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.activity;
            r6 = android.view.LayoutInflater.from(r6);
            r7 = 2130903124; // 0x7f030054 float:1.7413057E38 double:1.052806028E-314;
            r0 = r43;
            r0 = org.telegram.news.NewsPage.this;
            r40 = r0;
            r0 = r40;
            r0 = r0.myLL;
            r40 = r0;
            r41 = 0;
            r0 = r40;
            r1 = r41;
            r25 = r6.inflate(r7, r0, r1);
            r6 = 2131689838; // 0x7f0f016e float:1.9008703E38 double:1.0531947165E-314;
            r0 = r25;
            r9 = r0.findViewById(r6);
            r9 = (android.widget.ImageView) r9;
            r6 = 2131689840; // 0x7f0f0170 float:1.9008707E38 double:1.0531947175E-314;
            r0 = r25;
            r16 = r0.findViewById(r6);
            r16 = (android.widget.TextView) r16;
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "";
            r6 = r6.append(r7);
            r7 = r15.getUrls();
            r7 = r7.length;
            r6 = r6.append(r7);
            r6 = r6.toString();
            r0 = r16;
            r0.setText(r6);
            r6 = 2131689664; // 0x7f0f00c0 float:1.900835E38 double:1.0531946306E-314;
            r0 = r25;
            r8 = r0.findViewById(r6);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r11 = r6.position;
            r6 = new org.telegram.news.NewsPage$5$4;
            r0 = r43;
            r6.<init>(r15);
            r0 = r25;
            r0.setOnClickListener(r6);
            r40 = org.telegram.news.AminImageLoader.getInstance();
            r6 = r15.getUrls();
            r7 = 0;
            r41 = r6[r7];
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r42 = r6.getOptions();
            r6 = new org.telegram.news.NewsPage$5$5;
            r7 = r43;
            r6.<init>(r8, r9, r10, r11);
            r0 = r40;
            r1 = r41;
            r2 = r42;
            r0.loadImage(r1, r2, r6);
            goto L_0x029a;
        L_0x0560:
            r10 = 0;
            goto L_0x043b;
        L_0x0563:
            r6 = r15.getVideoUrl();	 Catch:{ Exception -> 0x0924 }
            if (r6 == 0) goto L_0x02b6;
        L_0x0569:
            r6 = r15.getVideoUrl();	 Catch:{ Exception -> 0x0924 }
            r6 = r6.length();	 Catch:{ Exception -> 0x0924 }
            r7 = 10;
            if (r6 < r7) goto L_0x02b6;
        L_0x0575:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.activity;
            r6 = android.view.LayoutInflater.from(r6);
            r7 = 2130903125; // 0x7f030055 float:1.741306E38 double:1.0528060287E-314;
            r0 = r43;
            r0 = org.telegram.news.NewsPage.this;
            r40 = r0;
            r0 = r40;
            r0 = r0.myLL;
            r40 = r0;
            r41 = 0;
            r0 = r40;
            r1 = r41;
            r25 = r6.inflate(r7, r0, r1);
            r6 = 2131689838; // 0x7f0f016e float:1.9008703E38 double:1.0531947165E-314;
            r0 = r25;
            r34 = r0.findViewById(r6);
            r34 = (android.widget.ImageView) r34;
            r6 = 2131689664; // 0x7f0f00c0 float:1.900835E38 double:1.0531946306E-314;
            r0 = r25;
            r35 = r0.findViewById(r6);
            r6 = 2131689843; // 0x7f0f0173 float:1.9008713E38 double:1.053194719E-314;
            r0 = r25;
            r12 = r0.findViewById(r6);
            r12 = (utils.view.TitleTextView) r12;
            r6 = r15.getDuration();
            r12.setText(r6);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r0 = r6.position;
            r33 = r0;
            r6 = new org.telegram.news.NewsPage$5$6;
            r0 = r43;
            r6.<init>();
            r0 = r25;
            r0.setOnClickListener(r6);
            r6 = org.telegram.news.AminImageLoader.getInstance();
            r7 = r15.getVideoImage();
            r0 = r43;
            r0 = org.telegram.news.NewsPage.this;
            r40 = r0;
            r40 = r40.getOptions();
            r41 = new org.telegram.news.NewsPage$5$7;
            r0 = r41;
            r1 = r43;
            r2 = r35;
            r3 = r34;
            r4 = r33;
            r0.<init>(r2, r3, r4);
            r0 = r40;
            r1 = r41;
            r6.loadImage(r7, r0, r1);
            goto L_0x029a;
        L_0x05fc:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.activity;
            r6 = android.view.LayoutInflater.from(r6);
            r7 = 2130903126; // 0x7f030056 float:1.7413061E38 double:1.052806029E-314;
            r0 = r43;
            r0 = org.telegram.news.NewsPage.this;
            r40 = r0;
            r0 = r40;
            r0 = r0.myLL;
            r40 = r0;
            r41 = 0;
            r0 = r40;
            r1 = r41;
            r25 = r6.inflate(r7, r0, r1);
            r6 = 2131689844; // 0x7f0f0174 float:1.9008715E38 double:1.0531947195E-314;
            r0 = r25;
            r32 = r0.findViewById(r6);
            r32 = (android.widget.TextView) r32;
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.activity;
            r6 = org.telegram.news.NewsPage.MyMovementMethod.getInstance(r6);
            r0 = r32;
            r0.setMovementMethod(r6);
            r6 = r15.getDescription();
            r0 = r43;
            r7 = org.telegram.news.NewsPage.this;
            r40 = 0;
            r0 = r40;
            r23 = android.text.Html.fromHtml(r6, r7, r0);
            r28 = new android.text.SpannedString;
            r0 = r28;
            r1 = r23;
            r0.<init>(r1);
            r0 = r32;
            r1 = r28;
            r0.setText(r1);
            r6 = 2;
            r0 = r43;
            r7 = org.telegram.news.NewsPage.this;
            r7 = r7.fontSize;
            r0 = r32;
            r0.setTextSize(r6, r7);
            r6 = 1;
            r0 = r32;
            r0.setTextIsSelectable(r6);
            r6 = 1;
            r0 = r32;
            r0.setFocusable(r6);
            r6 = 1;
            r0 = r32;
            r0.setFocusableInTouchMode(r6);
            goto L_0x029a;
        L_0x0679:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6.handleRelatedNews();
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r7 = 1;
            r6.isOKToAddImage = r7;
            r6 = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
            r0 = r43;
            r7 = org.telegram.news.NewsPage.this;
            r7 = r7.fullDetailsNews;
            r7 = r7.getAgencyLogo();
            r0 = r43;
            r0 = org.telegram.news.NewsPage.this;
            r37 = r0;
            r0 = r37;
            r0 = r0.ivAgencyLogo;
            r37 = r0;
            r0 = r43;
            r0 = org.telegram.news.NewsPage.this;
            r38 = r0;
            r38 = r38.getOptions();
            r0 = r37;
            r1 = r38;
            r6.displayImage(r7, r0, r1);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r6 = r6.getImageUrls();
            if (r6 == 0) goto L_0x06ce;
        L_0x06be:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r6 = r6.getImageUrls();
            r6 = r6.size();
            if (r6 != 0) goto L_0x06fd;
        L_0x06ce:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r6 = r6.getImageUrl();
            r6 = android.text.TextUtils.isEmpty(r6);
            if (r6 != 0) goto L_0x06fd;
        L_0x06de:
            r29 = new java.util.ArrayList;
            r29.<init>();
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r6 = r6.getImageUrl();
            r0 = r29;
            r0.add(r6);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r0 = r29;
            r6.setImageUrls(r0);
        L_0x06fd:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r6 = r6.getImageUrls();
            if (r6 == 0) goto L_0x08c6;
        L_0x0709:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r6 = r6.getImageUrls();
            r6 = r6.size();
            if (r6 <= 0) goto L_0x08c6;
        L_0x0719:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r7 = r6.fullDetailsNews;
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r6 = r6.getImageUrls();
            r37 = 0;
            r0 = r37;
            r6 = r6.get(r0);
            r6 = (java.lang.String) r6;
            r7.setImageUrl(r6);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.metaData;
            r7 = 2131690000; // 0x7f0f0210 float:1.9009031E38 double:1.0531947966E-314;
            r30 = r6.findViewById(r7);
            r30 = (android.widget.TextView) r30;
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r6 = r6.getImageUrls();
            r6 = r6.size();
            r6 = java.lang.String.valueOf(r6);
            r0 = r30;
            r0.setText(r6);
            r7 = "LEE";
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r37 = "test:";
            r0 = r37;
            r37 = r6.append(r0);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r6 = r6.getImageUrls();
            r38 = 0;
            r0 = r38;
            r6 = r6.get(r0);
            r6 = (java.lang.String) r6;
            r0 = r37;
            r6 = r0.append(r6);
            r6 = r6.toString();
            android.util.Log.d(r7, r6);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r7 = 1;
            r6.isOKToAddImage = r7;
            r7 = org.telegram.news.AminImageLoader.getInstance();
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r6 = r6.getImageUrls();
            r37 = 0;
            r0 = r37;
            r6 = r6.get(r0);
            r6 = (java.lang.String) r6;
            r0 = r43;
            r0 = org.telegram.news.NewsPage.this;
            r37 = r0;
            r37 = r37.getOptions();
            r38 = new org.telegram.news.NewsPage$5$8;
            r0 = r38;
            r1 = r43;
            r0.<init>();
            r0 = r37;
            r1 = r38;
            r7.loadImage(r6, r0, r1);
            r6 = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
            r0 = r43;
            r7 = org.telegram.news.NewsPage.this;
            r7 = r7.fullDetailsNews;
            r7 = r7.getAgencyLogo();
            r0 = r43;
            r0 = org.telegram.news.NewsPage.this;
            r37 = r0;
            r0 = r37;
            r0 = r0.ivAgencyLogo;
            r37 = r0;
            r0 = r37;
            r6.displayImage(r7, r0);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.vTop;
            r7 = new org.telegram.news.NewsPage$5$9;
            r0 = r43;
            r7.<init>();
            r6.setOnClickListener(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r7 = 1;
            r6.haveImage = r7;
            r13 = new android.widget.LinearLayout$LayoutParams;
            r6 = -1;
            r7 = -2;
            r13.<init>(r6, r7);
            r6 = 0;
            r7 = 0;
            r37 = 0;
            r38 = 0;
            r0 = r37;
            r1 = r38;
            r13.setMargins(r6, r7, r0, r1);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.rlContext;
            r6.setLayoutParams(r13);
        L_0x081b:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.mToolbar;
            r7 = "actionBarDefault";
            r7 = org.telegram.ui.ActionBar.Theme.getColor(r7);
            r6.setBackgroundColor(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.activity;
            r7 = "actionBarDefault";
            r7 = org.telegram.ui.ActionBar.Theme.getColor(r7);
            org.telegram.news.NewsPage.setStatusBarColor(r6, r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.calledWebService;
            if (r6 == 0) goto L_0x08a8;
        L_0x0847:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.fullDetailsNews;
            r6 = r6.getNewsUrl();
            r6 = android.text.TextUtils.isEmpty(r6);
            if (r6 != 0) goto L_0x08a8;
        L_0x0857:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.view;
            r7 = 2131690016; // 0x7f0f0220 float:1.9009064E38 double:1.0531948045E-314;
            r20 = r6.findViewById(r7);
            r20 = (android.widget.TextView) r20;
            r6 = 0;
            r0 = r20;
            r0.setVisibility(r6);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.ftvSendReport;
            r7 = 0;
            r6.setVisibility(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.view;
            r7 = 2131690039; // 0x7f0f0237 float:1.900911E38 double:1.053194816E-314;
            r14 = r6.findViewById(r7);
            r14 = (android.widget.TextView) r14;
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.activity;
            r7 = "attention.txt";
            r31 = org.telegram.customization.util.ReadTextFile.getFileText(r6, r7);
            r0 = r31;
            r14.setText(r0);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.view;
            r7 = 2131690038; // 0x7f0f0236 float:1.9009108E38 double:1.0531948154E-314;
            r6 = r6.findViewById(r7);
            r7 = 0;
            r6.setVisibility(r7);
        L_0x08a8:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.view;
            r7 = new org.telegram.news.NewsPage$5$10;
            r0 = r43;
            r7.<init>();
            r6.post(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r0 = r43;
            r7 = org.telegram.news.NewsPage.this;
            r7 = r7.fullDetailsNews;
            r6.generateThumbnails(r7);
            return;
        L_0x08c6:
            if (r21 != 0) goto L_0x081b;
        L_0x08c8:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.calledWebService;
            if (r6 == 0) goto L_0x081b;
        L_0x08d2:
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.vTop;
            r7 = 8;
            r6.setVisibility(r7);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r7 = 0;
            r6.haveImage = r7;
            r13 = new android.widget.LinearLayout$LayoutParams;
            r6 = -1;
            r7 = -2;
            r13.<init>(r6, r7);
            r6 = 0;
            r0 = r43;
            r7 = org.telegram.news.NewsPage.this;
            r7 = r7.mToolbar;
            r7 = r7.getHeight();
            r37 = 0;
            r38 = 0;
            r0 = r37;
            r1 = r38;
            r13.setMargins(r6, r7, r0, r1);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.rlContext;
            r6.setLayoutParams(r13);
            r0 = r43;
            r6 = org.telegram.news.NewsPage.this;
            r6 = r6.mToolbar;
            r7 = "actionBarDefault";
            r7 = org.telegram.ui.ActionBar.Theme.getColor(r7);
            r6.setBackgroundColor(r7);
            goto L_0x081b;
        L_0x0921:
            r6 = move-exception;
            goto L_0x02a5;
        L_0x0924:
            r6 = move-exception;
            goto L_0x0575;
        L_0x0927:
            r6 = move-exception;
            goto L_0x0438;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.news.NewsPage.5.run():void");
        }
    }

    class MyBitmapDrawable extends BitmapDrawable {
        public MyBitmapDrawable(Resources res, InputStream is) {
            super(res, is);
        }

        public MyBitmapDrawable(InputStream is) {
            super(is);
        }

        public MyBitmapDrawable(Resources res, String filepath) {
            super(res, filepath);
        }

        public MyBitmapDrawable(String filepath) {
            super(filepath);
        }

        public MyBitmapDrawable(Resources res, Bitmap bitmap) {
            super(res, bitmap);
        }

        public MyBitmapDrawable(Bitmap bitmap) {
            super(bitmap);
        }

        public MyBitmapDrawable(Resources res) {
            super(res);
        }

        public void draw(Canvas canvas) {
            try {
                super.draw(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class MyMovementMethod extends LinkMovementMethod {
        private static MyMovementMethod linkMovementMethod = new MyMovementMethod();
        private static Context movementContext;

        MyMovementMethod() {
        }

        public static MovementMethod getInstance(Context c) {
            movementContext = c;
            return linkMovementMethod;
        }

        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            if (event.getAction() != 1) {
                return true;
            }
            int x = (((int) event.getX()) - widget.getTotalPaddingLeft()) + widget.getScrollX();
            int y = (((int) event.getY()) - widget.getTotalPaddingTop()) + widget.getScrollY();
            Layout layout = widget.getLayout();
            int off = layout.getOffsetForHorizontal(layout.getLineForVertical(y), (float) x);
            URLSpan[] link = (URLSpan[]) buffer.getSpans(off, off, URLSpan.class);
            if (link.length != 0) {
                return shouldOverrideUrlLoading(link[0].getURL());
            }
            return true;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean shouldOverrideUrlLoading(java.lang.String r21) {
            /*
            r20 = this;
            r17 = "LEE";
            r18 = new java.lang.StringBuilder;
            r18.<init>();
            r19 = "PackageNameNotif123:";
            r18 = r18.append(r19);
            r0 = r18;
            r1 = r21;
            r18 = r0.append(r1);
            r18 = r18.toString();
            android.util.Log.d(r17, r18);
            r5 = 0;
            r12 = 0;
            r10 = 0;
            r13 = 0;
            r13 = android.net.Uri.parse(r21);	 Catch:{ Exception -> 0x00cb }
        L_0x0026:
            if (r13 == 0) goto L_0x015f;
        L_0x0028:
            r17 = r13.getScheme();
            r18 = "tag";
            r17 = r17.equals(r18);
            if (r17 == 0) goto L_0x015f;
        L_0x0035:
            r17 = "tag://open/img";
            r0 = r21;
            r1 = r17;
            r17 = r0.contains(r1);
            if (r17 == 0) goto L_0x00d1;
        L_0x0042:
            r17 = "url";
            r0 = r17;
            r8 = r13.getQueryParameter(r0);
            if (r8 == 0) goto L_0x00c8;
        L_0x004d:
            r17 = android.text.TextUtils.isEmpty(r8);
            if (r17 != 0) goto L_0x00c8;
        L_0x0053:
            r6 = r8.toLowerCase();
            r17 = "http";
            r0 = r17;
            r17 = r6.startsWith(r0);
            if (r17 == 0) goto L_0x00c8;
        L_0x0062:
            r17 = ".png";
            r0 = r17;
            r17 = r6.endsWith(r0);
            if (r17 != 0) goto L_0x0099;
        L_0x006d:
            r17 = ".jpg";
            r0 = r17;
            r17 = r6.endsWith(r0);
            if (r17 != 0) goto L_0x0099;
        L_0x0078:
            r17 = ".jpeg";
            r0 = r17;
            r17 = r6.endsWith(r0);
            if (r17 != 0) goto L_0x0099;
        L_0x0083:
            r17 = ".gif";
            r0 = r17;
            r17 = r6.endsWith(r0);
            if (r17 != 0) goto L_0x0099;
        L_0x008e:
            r17 = ".webp";
            r0 = r17;
            r17 = r6.endsWith(r0);
            if (r17 == 0) goto L_0x00c8;
        L_0x0099:
            r7 = new android.content.Intent;
            r17 = movementContext;
            r18 = org.telegram.customization.Activities.ImageViewerActivity.class;
            r0 = r17;
            r1 = r18;
            r7.<init>(r0, r1);
            r4 = new com.google.gson.Gson;
            r4.<init>();
            r14 = new java.util.ArrayList;
            r14.<init>();
            r14.add(r8);
            r17 = "id";
            r18 = r4.toJson(r14);
            r0 = r17;
            r1 = r18;
            r7.putExtra(r0, r1);
            r17 = movementContext;
            r0 = r17;
            r0.startActivity(r7);
        L_0x00c8:
            r17 = 1;
        L_0x00ca:
            return r17;
        L_0x00cb:
            r3 = move-exception;
            r3.printStackTrace();
            goto L_0x0026;
        L_0x00d1:
            r17 = "tag://open/video";
            r0 = r21;
            r1 = r17;
            r17 = r0.contains(r1);
            if (r17 == 0) goto L_0x0116;
        L_0x00de:
            r17 = "url";
            r0 = r17;
            r9 = r13.getQueryParameter(r0);
            r17 = "title";
            r0 = r17;
            r15 = r13.getQueryParameter(r0);
            if (r15 != 0) goto L_0x00f5;
        L_0x00f2:
            r15 = "";
        L_0x00f5:
            if (r9 == 0) goto L_0x0113;
        L_0x00f7:
            r17 = android.text.TextUtils.isEmpty(r9);
            if (r17 != 0) goto L_0x0113;
        L_0x00fd:
            r16 = r9.toLowerCase();
            r17 = "http";
            r17 = r16.startsWith(r17);
            if (r17 != 0) goto L_0x0113;
        L_0x010a:
            r17 = "rtsp";
            r17 = r16.startsWith(r17);
            if (r17 == 0) goto L_0x0113;
        L_0x0113:
            r17 = 1;
            goto L_0x00ca;
        L_0x0116:
            r17 = "tag://open/news";
            r0 = r21;
            r1 = r17;
            r17 = r0.contains(r1);
            if (r17 == 0) goto L_0x0126;
        L_0x0123:
            r17 = 1;
            goto L_0x00ca;
        L_0x0126:
            r17 = "tagId";
            r0 = r17;
            r11 = r13.getQueryParameter(r0);
            r17 = "tagName";
            r0 = r17;
            r12 = r13.getQueryParameter(r0);
            r17 = "tagColor";
            r0 = r17;
            r10 = r13.getQueryParameter(r0);
            r17 = android.text.TextUtils.isEmpty(r11);
            if (r17 != 0) goto L_0x014d;
        L_0x0147:
            r17 = android.text.TextUtils.isEmpty(r12);
            if (r17 == 0) goto L_0x0151;
        L_0x014d:
            r17 = 1;
            goto L_0x00ca;
        L_0x0151:
            r17 = android.text.TextUtils.isEmpty(r11);
            if (r17 != 0) goto L_0x015b;
        L_0x0157:
            r5 = java.lang.Integer.parseInt(r11);
        L_0x015b:
            r17 = 1;
            goto L_0x00ca;
        L_0x015f:
            r2 = new android.content.Intent;
            r17 = "android.intent.action.VIEW";
            r0 = r17;
            r2.<init>(r0, r13);
            r17 = movementContext;
            r0 = r17;
            r0.startActivity(r2);
            r17 = 1;
            goto L_0x00ca;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.news.NewsPage.MyMovementMethod.shouldOverrideUrlLoading(java.lang.String):boolean");
        }
    }

    public NewsPage(Context context, AttributeSet attrs, int defStyleAttr, int tagId) {
        super(context, attrs, defStyleAttr);
        this.tagId = tagId;
    }

    public NewsPage(Context context, AttributeSet attrs, int tagId) {
        super(context, attrs);
        this.tagId = tagId;
    }

    public NewsPage(Context context) {
        super(context);
    }

    public void setPos(int p) {
        this.position = p;
    }

    public void initNewsDescPage2(Activity newActivity, LayoutInflater inflater, News singleNews, ViewGroup container, int tagId, int currentItem) {
        this.activity = newActivity;
        setStatusBarColor(this.activity, Theme.getColor(Theme.key_actionBarDefault));
        this.newsDescriptionActivity = newActivity;
        this.inflater = inflater;
        this.tagId = tagId;
        this.singleNews = singleNews;
        initViews();
        handleRetryAction();
        preSet();
        removeAllViews();
        addView(this.view);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        callWebservice(singleNews);
    }

    public void updateMe(News singleNews, int tagId, int p) {
        this.position = p;
        this.isScrollLog = false;
        this.mScrollView.scrollTo(0, this.mFlexibleSpaceImageHeight - this.mActionBarSize);
        this.mScrollView.scrollTo(0, 1);
        this.mScrollView.scrollTo(0, 0);
        this.vTop.setVisibility(4);
        this.newsDateLayout.setVisibility(4);
        this.ftvNewsTitle.setVisibility(4);
        this.view.findViewById(R.id.devider).setVisibility(4);
        this.view.findViewById(R.id.divider2).setVisibility(4);
        this.myLL.setVisibility(4);
        this.view.findViewById(R.id.ll_show_source).setVisibility(4);
        this.view.findViewById(R.id.ll_tag_container).setVisibility(4);
        this.view.findViewById(R.id.ll_buttons).setVisibility(4);
        this.view.findViewById(R.id.ll_btn_add_commnet).setVisibility(4);
        this.view.findViewById(R.id.ll_comments).setVisibility(4);
        this.llThumbnailContainer.setVisibility(8);
        this.llExtraContent.setVisibility(4);
        this.view.findViewById(R.id.report_container).setVisibility(4);
        try {
            this.ivFirstImage.setImageBitmap(null);
            this.ivFirstImage.setImageDrawable(null);
        } catch (Exception e) {
        }
        this.mImageView.setVisibility(4);
        this.pageLoading.setVisibility(0);
        this.calledWebService = false;
        this.tagId = tagId;
        this.singleNews = singleNews;
        callWebservice(singleNews);
    }

    private void handleRelatedNews() {
        this.llExtraContent.removeAllViews();
        if (this.fullDetailsNews.getRelatedNews() != null) {
            Iterator it = this.fullDetailsNews.getRelatedNews().iterator();
            while (it.hasNext()) {
                final News news = (News) it.next();
                View view = this.inflater.inflate(R.layout.small_news_item_new, this.llExtraContent, false);
                view.setBackgroundColor(ContextCompat.getColor(this.activity, R.color.white));
                TextView tvAgency = (TextView) view.findViewById(R.id.txt_source);
                TextView tvTitle = (TextView) view.findViewById(R.id.txt_title);
                TextView tvPicCount = (TextView) view.findViewById(R.id.ftv_pic_count);
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_news_image);
                ImageView ivAgency = (ImageView) view.findViewById(R.id.iv_agency);
                TextView tvTime = (TextView) view.findViewById(R.id.txt_time);
                view.findViewById(R.id.v_home_divider).setVisibility(0);
                view.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(NewsPage.this.activity, NewsDescriptionActivity.class);
                        intent.setAction(System.currentTimeMillis() + "");
                        intent.putExtra(Constants.SPECIAL_NEWS, news.getNewsId());
                        intent.putExtra(Constants.EXTRA_BACK_TO_HOME, false);
                        NewsPage.this.activity.startActivity(intent);
                    }
                });
                if (news.getImageCount() > 1) {
                    tvPicCount.setVisibility(0);
                    tvPicCount.setText(news.getImageCount() + "");
                } else {
                    tvPicCount.setVisibility(8);
                }
                AppImageLoader.loadImage(imageView, news.getImageUrl());
                tvTitle.setText(news.getTitle());
                tvTitle.setTextColor(ContextCompat.getColor(this.activity, R.color.black));
                tvAgency.setText(news.getAgencyName());
                AppImageLoader.loadImage(ivAgency, news.getAgencyLogo());
                News.setNewsPublishTime(tvTime, news);
                this.llExtraContent.addView(view);
            }
        }
    }

    private void callWebservice(News singleNews) {
        this.calledWebService = false;
        HandleRequest.getNew(this.activity, this).getNewsById((long) this.tagId, singleNews.getNewsId(), this.view);
    }

    private void preSet() {
        this.mFlexibleSpaceImageHeight = this.activity.getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        this.mActionBarSize = getActionBarSize();
        this.agencyName.setMovementMethod(MyMovementMethod.getInstance(this.activity));
        this.mScrollView.setScrollViewCallbacks(this);
        this.newsDescriptionActivity.setTitle(null);
        ScrollUtils.addOnGlobalLayoutListener(this.mScrollView, new C19292());
    }

    private void handleRetryAction() {
        this.llRetry.setOnClickListener(new C19303());
    }

    private void initViews() {
        this.view = this.inflater.inflate(R.layout.news_page, null);
        this.view.post(new C19314());
        this.polStatus = this.view.findViewById(R.id.iv_political);
        this.llChild = (LinearLayout) this.view.findViewById(R.id.scrollContent);
        this.agencyName = (TextView) this.view.findViewById(R.id.ftv_agency_name);
        this.creationDate = (TextView) this.view.findViewById(R.id.ftv_news_creation_date);
        this.ivBack = (ImageView) this.view.findViewById(R.id.action_back);
        this.ivBack.setOnClickListener(this);
        this.myLL = (LinearLayout) this.view.findViewById(R.id.myLL);
        this.llExtraContent = (LinearLayout) this.view.findViewById(R.id.ll_extra_content);
        this.title = (TextView) this.view.findViewById(R.id.actvity_title);
        this.progress = (ProgressBar) this.view.findViewById(R.id.pb_loading_news);
        this.tagContainer = (LinearLayout) this.view.findViewById(R.id.ll_tag_container);
        this.ftvSendReport = (FarsiTextView) this.view.findViewById(R.id.ftv_send_report);
        this.ivFirstImage = (ImageView) this.view.findViewById(R.id.iv_first_image);
        this.ftvNewsTitle = (TextView) this.view.findViewById(R.id.ftv_news_title);
        this.ivAgencyLogo = (ImageView) this.view.findViewById(R.id.iv_agency);
        this.mToolbar = (Toolbar) this.view.findViewById(R.id.toolbar);
        this.rlContext = this.view.findViewById(R.id.rel_context);
        this.metaData = this.view.findViewById(R.id.v_news_metadata);
        this.mImageView = this.view.findViewById(R.id.header_view);
        this.mOverlayView = this.view.findViewById(R.id.overlay);
        this.mScrollView = (ObservableScrollView) this.view.findViewById(R.id.scroll);
        this.vTop = this.view.findViewById(R.id.top_view);
        this.pageLoading = this.view.findViewById(R.id.pb_page_loading);
        this.pbImage = (CircularProgress) this.view.findViewById(R.id.pb_loading);
        this.Duration = (TextView) this.view.findViewById(R.id.duration);
        this.playBtn = this.view.findViewById(R.id.playBtn);
        this.llRetry = (LinearLayout) this.view.findViewById(R.id.ll_error);
        this.btnAddComment = this.view.findViewById(R.id.btn_add_comment);
        this.btnAddComment.setOnClickListener(this);
        this.ivArchive = (LinearLayout) this.view.findViewById(R.id.ll_fave);
        this.ivArchive.setOnClickListener(this);
        this.ivFave = (ImageView) this.view.findViewById(R.id.iv_fave);
        this.llLike = (LinearLayout) this.view.findViewById(R.id.ll_like);
        this.llDislike = (LinearLayout) this.view.findViewById(R.id.ll_dislike);
        this.ivLike = (ImageView) this.view.findViewById(R.id.iv_like);
        this.ivDislike = (ImageView) this.view.findViewById(R.id.iv_dislike);
        this.ivShare = (ImageView) this.view.findViewById(R.id.btn_share);
        this.llShowMoreComments = (LinearLayout) this.view.findViewById(R.id.show_more_comments);
        this.llShowMoreComments.setOnClickListener(this);
        this.llShare = (LinearLayout) this.view.findViewById(R.id.ll_share);
        this.llShare.setOnClickListener(this);
        this.llLike.setOnClickListener(this);
        this.llDislike.setOnClickListener(this);
        this.ivShare.setOnClickListener(this);
        this.mToolbar.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        this.llComments = (LinearLayout) this.view.findViewById(R.id.ll_comments);
        this.vTop.setVisibility(4);
        this.newsDateLayout = this.view.findViewById(R.id.news_data_layout);
        this.llInfo = (LinearLayout) this.view.findViewById(R.id.ll_more_info);
        this.llInfo.setOnClickListener(this);
        this.headerGrid = this.view.findViewById(R.id.grid);
        this.videoCountView = this.view.findViewById(R.id.videoCountContainer);
        this.imageCountView = this.view.findViewById(R.id.picCountContainer);
        this.llThumbnailContainer = (LinearLayout) this.view.findViewById(R.id.ll_thumbnail_container);
    }

    private void fillViewsByNewsDetails() {
        if (this.fontSize == 0.0f) {
            this.fontSize = AppPreferences.getTextSize(this.activity);
        }
        this.activity.runOnUiThread(new C19415());
        if (this.fullDetailsNews != null) {
            if (TextUtils.isEmpty(this.fullDetailsNews.getImageUrl()) && this.fullDetailsNews.getImageUrls().size() > 0) {
                this.fullDetailsNews.setImageUrl((String) this.fullDetailsNews.getImageUrls().get(0));
            }
            this.fullDetailsNews.setLastSeenTime(System.currentTimeMillis());
        }
    }

    private void generateThumbnails(final News fullDetailsNews) {
        if (fullDetailsNews == null || fullDetailsNews.getImageCount() <= 1) {
            this.llThumbnailContainer.setVisibility(8);
            return;
        }
        this.llThumbnailContainer.setVisibility(0);
        this.llThumbnailContainer.removeAllViews();
        this.llThumbnailContainer.setWeightSum(100.0f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, Utilities.convertDpToPixel((float) (Utilities.getScreenWidth(getContext()) / 5), getContext()));
        layoutParams.weight = 20.0f;
        int imageCounter = 0;
        Iterator it = fullDetailsNews.getImageUrls().iterator();
        while (it.hasNext()) {
            String url = (String) it.next();
            imageCounter++;
            LinearLayout ll = (LinearLayout) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.item_thumbnail, null);
            ll.setLayoutParams(layoutParams);
            ll.setTag(Integer.valueOf(imageCounter));
            AppImageLoader.loadImage((ImageView) ll.findViewById(R.id.thumbnail), url);
            this.llThumbnailContainer.addView(ll);
            ll.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(NewsPage.this.activity, ImageViewerActivity.class);
                    intent.putExtra("id", new Gson().toJson(fullDetailsNews.getImageUrls()));
                    if (view.getTag() != null) {
                        intent.putExtra(Constants.CURRENT_PIC_ID, Integer.parseInt(view.getTag().toString()) - 1);
                    }
                    NewsPage.this.activity.startActivity(intent);
                }
            });
            if (imageCounter == 4 && fullDetailsNews.getImageCount() > 5) {
                LinearLayout llMore = (LinearLayout) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.item_thumbnail, null);
                llMore.setLayoutParams(layoutParams);
                ImageView ivMore = (ImageView) llMore.findViewById(R.id.thumbnail);
                ivMore.setPadding(20, 20, 20, 20);
                AppImageLoader.loadImage(ivMore, "drawable://2130838096");
                this.llThumbnailContainer.addView(llMore);
                llMore.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(NewsPage.this.activity, ImageViewerActivity.class);
                        intent.putExtra("id", new Gson().toJson(fullDetailsNews.getImageUrls()));
                        NewsPage.this.activity.startActivity(intent);
                    }
                });
                return;
            }
        }
    }

    private void showAllViewByAnimation() {
        boolean viewShowed = false;
        try {
            if ((this.activity instanceof NewsDescriptionActivity) && ((NewsDescriptionActivity) this.activity).viewPager.getCurrentItem() != this.position) {
                viewShowed = true;
                if (this.haveImage) {
                    this.vTop.setVisibility(0);
                    this.mImageView.setVisibility(0);
                }
                this.newsDateLayout.setVisibility(0);
                this.ftvNewsTitle.setVisibility(0);
                this.view.findViewById(R.id.devider).setVisibility(0);
                this.view.findViewById(R.id.divider2).setVisibility(0);
                this.view.findViewById(R.id.ll_show_source).setVisibility(0);
                this.view.findViewById(R.id.ll_tag_container).setVisibility(0);
                this.tagContainer.setVisibility(0);
                this.myLL.setVisibility(0);
                this.view.findViewById(R.id.ll_buttons).setVisibility(0);
                this.view.findViewById(R.id.ll_comments).setVisibility(0);
                this.view.findViewById(R.id.ll_btn_add_commnet).setVisibility(0);
                this.view.findViewById(R.id.report_container).setVisibility(0);
                this.llExtraContent.setVisibility(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!viewShowed) {
            final Animation animation;
            final Animation animation2;
            if (this.haveImage) {
                Animation a0 = AnimationUtils.loadAnimation(this.activity, R.anim.fadein);
                a0.setAnimationListener(new SimpleAnimationListener(this.vTop));
                Animation a0_1 = AnimationUtils.loadAnimation(this.activity, R.anim.fadein);
                a0_1.setAnimationListener(new SimpleAnimationListener(this.mImageView));
                animation = a0;
                animation2 = a0_1;
                this.mImageView.postDelayed(new Runnable() {
                    public void run() {
                        NewsPage.this.vTop.startAnimation(animation);
                        NewsPage.this.mImageView.startAnimation(animation2);
                    }
                }, 100);
            }
            Animation a1 = AnimationUtils.loadAnimation(this.activity, R.anim.fadein);
            a1.setAnimationListener(new SimpleAnimationListener(this.newsDateLayout));
            Animation a2 = AnimationUtils.loadAnimation(this.activity, R.anim.fadein);
            a2.setAnimationListener(new SimpleAnimationListener(this.ftvNewsTitle));
            animation = a1;
            animation2 = a2;
            this.ftvNewsTitle.postDelayed(new Runnable() {
                public void run() {
                    NewsPage.this.newsDateLayout.startAnimation(animation);
                    NewsPage.this.ftvNewsTitle.startAnimation(animation2);
                }
            }, 200);
            final Animation a2_1 = AnimationUtils.loadAnimation(this.activity, R.anim.fadein);
            a2_1.setAnimationListener(new SimpleAnimationListener(this.view.findViewById(R.id.devider)));
            final Animation a2_11 = AnimationUtils.loadAnimation(this.activity, R.anim.fadein);
            a2_11.setAnimationListener(new SimpleAnimationListener(this.view.findViewById(R.id.divider2)));
            final Animation a3 = AnimationUtils.loadAnimation(this.activity, R.anim.fadein);
            a3.setAnimationListener(new SimpleAnimationListener(this.myLL));
            final Animation a2_111 = AnimationUtils.loadAnimation(this.activity, R.anim.fadein);
            a2_111.setAnimationListener(new SimpleAnimationListener(this.view.findViewById(R.id.ll_show_source)));
            final Animation a4 = AnimationUtils.loadAnimation(this.activity, R.anim.fadein);
            a4.setAnimationListener(new SimpleAnimationListener(this.view.findViewById(R.id.ll_tag_container)));
            final Animation a44 = AnimationUtils.loadAnimation(this.activity, R.anim.fadein);
            a44.setAnimationListener(new SimpleAnimationListener(this.view.findViewById(R.id.ll_buttons)));
            final Animation a444 = AnimationUtils.loadAnimation(this.activity, R.anim.fadein);
            a444.setAnimationListener(new SimpleAnimationListener(this.view.findViewById(R.id.ll_comments)));
            final Animation a445 = AnimationUtils.loadAnimation(this.activity, R.anim.fadein);
            a445.setAnimationListener(new SimpleAnimationListener(this.view.findViewById(R.id.ll_btn_add_commnet)));
            final Animation a6 = AnimationUtils.loadAnimation(this.activity, R.anim.fadein);
            a6.setAnimationListener(new SimpleAnimationListener(this.llExtraContent));
            final View reportL = this.view.findViewById(R.id.report_container);
            final Animation a5 = AnimationUtils.loadAnimation(this.activity, R.anim.fadein);
            a5.setAnimationListener(new SimpleAnimationListener(reportL));
            reportL.postDelayed(new Runnable() {
                public void run() {
                    NewsPage.this.view.findViewById(R.id.devider).startAnimation(a2_1);
                    NewsPage.this.view.findViewById(R.id.divider2).startAnimation(a2_11);
                    NewsPage.this.myLL.startAnimation(a3);
                    NewsPage.this.view.findViewById(R.id.ll_show_source).startAnimation(a2_111);
                    NewsPage.this.view.findViewById(R.id.ll_tag_container).startAnimation(a4);
                    NewsPage.this.view.findViewById(R.id.ll_buttons).startAnimation(a44);
                    NewsPage.this.view.findViewById(R.id.ll_comments).startAnimation(a444);
                    NewsPage.this.view.findViewById(R.id.ll_btn_add_commnet).startAnimation(a445);
                    NewsPage.this.llExtraContent.startAnimation(a6);
                    reportL.startAnimation(a5);
                }
            }, 300);
        }
    }

    public View getView() {
        return this.view;
    }

    public void onResult(final Object object, int StatusCode) {
        Log.d("alireza", "alireza call webservice onresult111" + StatusCode);
        switch (StatusCode) {
            case Constants.ERROR_GET_NEWS /*-12*/:
                this.calledWebService = true;
                this.progress.setVisibility(8);
                this.pageLoading.setVisibility(8);
                this.llRetry.setVisibility(0);
                return;
            case 12:
                this.calledWebService = true;
                Log.d("alireza", "alireza call webservice onresult");
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            News tmpNews = ((Object[]) object)[1];
                            Log.d("LEE", "lllliio:2" + tmpNews.getTitle() + "newsId: " + tmpNews.getNewsId() + tmpNews.getImageUrl());
                            NewsPage.this.fullDetailsNews.setNewsId(tmpNews.getNewsId());
                            NewsPage.this.fullDetailsNews.setTitle(tmpNews.getTitle());
                            NewsPage.this.fullDetailsNews.setDescription(tmpNews.getDescription());
                            NewsPage.this.fullDetailsNews.setText(tmpNews.getText());
                            NewsPage.this.fullDetailsNews.setTags(tmpNews.getTags());
                            NewsPage.this.fullDetailsNews.setImageUrls(null);
                            NewsPage.this.fullDetailsNews.setImageUrl(tmpNews.getImageUrl());
                            NewsPage.this.fullDetailsNews.setAgencyLogo(tmpNews.getAgencyLogo());
                            NewsPage.this.fullDetailsNews.setAgencyName(tmpNews.getAgencyName());
                            NewsPage.this.fullDetailsNews.setCreationDate(tmpNews.getCreationDate());
                            NewsPage.this.fullDetailsNews.setNewsUrl(tmpNews.getNewsUrl());
                            NewsPage.this.fullDetailsNews.setImageCount(tmpNews.getImageCount());
                            NewsPage.this.fullDetailsNews.setNewsTagId(NewsPage.this.singleNews.getNewsTagId());
                            NewsPage.this.fullDetailsNews.setNewsTagName(NewsPage.this.singleNews.getNewsTagName());
                            NewsPage.this.fullDetailsNews.setShareContent(tmpNews.getShareContent());
                            NewsPage.this.fullDetailsNews.setVideoCount(tmpNews.getVideoCount());
                            NewsPage.this.fullDetailsNews.setContent(tmpNews.getContent());
                            NewsPage.this.fullDetailsNews.setExtraContents(tmpNews.getExtraContents());
                            NewsPage.this.fullDetailsNews.setJsonObject(new Gson().toJson(tmpNews));
                            NewsPage.this.fullDetailsNews.setRelatedNews(tmpNews.getRelatedNews());
                            NewsPage.this.fullDetailsNews.setCommentCount(tmpNews.getCommentCount());
                            NewsPage.this.fullDetailsNews.setAccessCommenting(tmpNews.getAccessCommenting());
                            NewsPage.this.fullDetailsNews.setPoliticalColor(tmpNews.getPoliticalColor());
                            NewsPage.this.fillViewsByNewsDetails();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                return;
            default:
                return;
        }
    }

    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        float flexibleRange = (float) (this.mFlexibleSpaceImageHeight - this.mActionBarSize);
        int minOverlayTransitionY = this.mActionBarSize - this.mOverlayView.getHeight();
        int n = 1;
        if (this.haveImage) {
            int tmp = Math.abs(this.mScrollView.getChildAt(0).getHeight() - Utilities.getScreenHeight(this.activity));
            if (tmp == 0) {
                tmp = 1;
            }
            if (tmp < this.mFlexibleSpaceImageHeight) {
                n = this.mFlexibleSpaceImageHeight / tmp;
            }
            if (n < 1) {
            }
            int tmpSY = scrollY;
            if (!canScrool()) {
                tmpSY = (int) flexibleRange;
            }
            if (!canScrool()) {
            }
        }
    }

    private boolean canScrool() {
        return this.mScrollView.canScrollVertically(this.mScrollView.getCurrentScrollY() + 1);
    }

    public void onDownMotionEvent() {
    }

    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    protected int getActionBarSize() {
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        TypedArray a = this.activity.obtainStyledAttributes(new TypedValue().data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(0, -1);
        try {
            a.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return actionBarSize;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_comment:
                if (this.fullDetailsNews != null && TextUtils.isEmpty(this.fullDetailsNews.getNewsId())) {
                    return;
                }
                return;
            case R.id.action_back:
                try {
                    this.activity.onBackPressed();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            case R.id.btn_share:
                this.activity.startActivity(Share.createShareIntent(this.fullDetailsNews.getTitle(), this.fullDetailsNews.getDescription(), this.fullDetailsNews.getNewsUrl(), true));
                return;
            default:
                return;
        }
    }

    public DisplayImageOptions getOptions() {
        if (this.options == null) {
            this.options = new Builder().showImageOnLoading(R.drawable.transparent).showImageForEmptyUri(R.drawable.transparent).showImageOnFail(R.drawable.transparent).cacheInMemory(false).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Config.RGB_565).build();
        }
        return this.options;
    }

    public void fontChanged(float f) {
        this.fontSize = f;
        if (this.textViews != null) {
            Iterator it = this.textViews.iterator();
            while (it.hasNext()) {
                try {
                    ((TextView) ((WeakReference) it.next()).get()).setTextSize(2, f);
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }
    }

    public Drawable getDrawable(String s) {
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = this.activity.getResources().getDrawable(R.drawable.ic_launcher);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
        return d;
    }

    public void draw(Canvas canvas) {
        try {
            super.draw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    public void onPageSelected(int position) {
    }

    public void onPageScrollStateChanged(int state) {
    }

    public static void setStatusBarColor(Activity activity, int color) {
        if (VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(Utilities.darker(color, 0.8f));
        }
    }
}
