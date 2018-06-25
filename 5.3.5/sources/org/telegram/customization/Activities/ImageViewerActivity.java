package org.telegram.customization.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.ir.talaeii.R;
import org.telegram.customization.Adapters.HeightImageAdapter;
import org.telegram.customization.Adapters.WidthImageAdapter;
import org.telegram.customization.util.ZoomOutPageTransformer;
import org.telegram.customization.util.view.zoom.ExtendedViewPager;
import org.telegram.news.NewsDescriptionActivity;
import utils.view.Constants;
import utils.view.FarsiTextView;
import utils.view.TitleTextView;

public class ImageViewerActivity extends Activity implements OnClickListener {
    Activity act;
    ProgressDialog barProgressDialog;
    View btnCancle;
    View btnDone;
    String currentImage;
    int currentPosition = 0;
    String currentStoreImageName;
    /* renamed from: d */
    Dialog f53d;
    TitleTextView ftvImageNuber;
    TextView ftvMessage;
    Gson gson = new Gson();
    ArrayList<String> imageUrls = new ArrayList();
    private boolean isDownloadedImage = false;
    boolean isPortrat;
    View ivBack;
    View ivDownload;
    View ivShare;
    View ivShowNews;
    View ivWallpaper;
    ArrayList<String> newsIds;
    ArrayList<String> newsTitle;
    FarsiTextView newsTitleView;
    View root;
    View root2;
    ExtendedViewPager viewPager;

    /* renamed from: org.telegram.customization.Activities.ImageViewerActivity$1 */
    class C10261 extends TypeToken<ArrayList<String>> {
        C10261() {
        }
    }

    /* renamed from: org.telegram.customization.Activities.ImageViewerActivity$2 */
    class C10272 implements OnClickListener {
        C10272() {
        }

        public void onClick(View v) {
            ImageViewerActivity.this.onBackPressed();
        }
    }

    /* renamed from: org.telegram.customization.Activities.ImageViewerActivity$3 */
    class C10283 implements OnPageChangeListener {
        C10283() {
        }

        public void onPageScrollStateChanged(int state) {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            ImageViewerActivity.this.currentPosition = position;
            Log.d("sadegh", "imagevieweract pos2:" + ImageViewerActivity.this.currentPosition);
            ImageViewerActivity.this.setCurrentImage((String) ImageViewerActivity.this.imageUrls.get(position));
            Log.d("sadegh", "imagevieweract url:" + ((String) ImageViewerActivity.this.imageUrls.get(position)));
            ImageViewerActivity.this.setNewsTitle();
            ImageViewerActivity.this.handleShowNews();
        }
    }

    /* renamed from: org.telegram.customization.Activities.ImageViewerActivity$4 */
    class C10294 implements OnPageChangeListener {
        C10294() {
        }

        public void onPageScrollStateChanged(int state) {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            ImageViewerActivity.this.currentPosition = position;
            ImageViewerActivity.this.setCurrentImage((String) ImageViewerActivity.this.imageUrls.get(position));
            ImageViewerActivity.this.setNewsTitle();
            ImageViewerActivity.this.handleShowNews();
        }
    }

    public static String spliturl(String f_url) {
        String[] temp = new String(f_url).split("/");
        return temp[temp.length - 1];
    }

    public static Bitmap loadFromFile(String filename) {
        Bitmap bitmap = null;
        try {
            if (new File(filename).exists()) {
                bitmap = BitmapFactory.decodeFile(filename);
            }
        } catch (Exception e) {
        }
        return bitmap;
    }

    public String getCurrentStoreImageName() {
        return this.currentStoreImageName;
    }

    public void setCurrentStoreImageName(String currentStoreImageName) {
        this.currentStoreImageName = currentStoreImageName;
    }

    public String getCurrentImage() {
        return this.currentImage;
    }

    public void setCurrentImage(String currentImage) {
        this.currentImage = currentImage;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageviewer);
        overridePendingTransition(R.anim.screen_shot_open, R.anim.no_anim);
        this.ivShare = findViewById(R.id.iv_share);
        this.ivBack = findViewById(R.id.iv_back);
        this.ivShowNews = findViewById(R.id.iv_shownews);
        this.ivDownload = findViewById(R.id.iv_download);
        this.ivWallpaper = findViewById(R.id.iv_wallpaper);
        this.ftvImageNuber = (TitleTextView) findViewById(R.id.ftv_image_number);
        this.newsTitleView = (FarsiTextView) findViewById(R.id.news_title);
        this.root = findViewById(R.id.root);
        this.root2 = findViewById(R.id.root2);
        this.act = this;
        this.f53d = new Dialog(this.act);
        if (getResources().getConfiguration().orientation == 2) {
            this.isPortrat = false;
        } else {
            this.isPortrat = true;
        }
        if (getIntent().getExtras() != null) {
            this.imageUrls = (ArrayList) this.gson.fromJson(getIntent().getStringExtra("id"), new C10261().getType());
            if (this.newsIds != null && this.newsIds.size() > 0) {
                this.ivShowNews.setVisibility(0);
            }
            if (this.isDownloadedImage) {
                this.ivDownload.setVisibility(8);
            }
            this.currentPosition = getIntent().getIntExtra(Constants.CURRENT_PIC_ID, 0);
        }
        if (this.newsTitle != null) {
            for (int i = 0; i < this.newsTitle.size(); i++) {
                if (TextUtils.isEmpty((CharSequence) this.newsTitle.get(i))) {
                    this.newsTitle.set(i, " ");
                }
            }
        }
        this.viewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
        this.ftvImageNuber = (TitleTextView) findViewById(R.id.ftv_image_number);
        handleView();
        setCurrentImage((String) this.imageUrls.get(this.currentPosition));
        this.ivBack.setOnClickListener(new C10272());
        this.ivShare.setVisibility(8);
        this.root.setOnClickListener(this);
        this.root2.setOnClickListener(this);
    }

    private void handleView() {
        if (this.isPortrat) {
            Log.d("sadegh", "imagevieweract pos1:" + this.currentPosition);
            this.viewPager.setAdapter(new WidthImageAdapter(this, this.act, this.isDownloadedImage ? makeImageFilePath(this.imageUrls) : this.imageUrls));
            this.viewPager.setCurrentItem(this.currentPosition);
            this.viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
            this.viewPager.setOnPageChangeListener(new C10283());
        } else {
            HeightImageAdapter adapter = new HeightImageAdapter(this, this, this.isDownloadedImage ? makeImageFilePath(this.imageUrls) : this.imageUrls);
            this.viewPager.setAdapter(adapter);
            this.viewPager.setOnClickListener(adapter);
            this.viewPager.setCurrentItem(this.currentPosition);
            this.viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
            this.viewPager.setOnPageChangeListener(new C10294());
        }
        setNewsTitle();
        handleShowNews();
    }

    private String makeImageFilePath(String path) {
        return "file://" + path;
    }

    private ArrayList<String> makeImageFilePath(ArrayList<String> imageUrls) {
        ArrayList<String> result = new ArrayList();
        if (imageUrls != null) {
            Iterator it = imageUrls.iterator();
            while (it.hasNext()) {
                result.add(makeImageFilePath((String) it.next()));
            }
        }
        return result;
    }

    private void setNewsTitle() {
        if (this.newsTitle != null && this.newsTitle.size() > this.currentPosition) {
            this.newsTitleView.setText((CharSequence) this.newsTitle.get(this.currentPosition));
        }
        this.ftvImageNuber.setText((this.currentPosition + 1) + "/" + this.imageUrls.size());
    }

    private void handleShowNews() {
        if (this.newsIds == null || this.newsIds.size() <= this.currentPosition) {
        }
        if (this.newsIds != null && this.newsIds.size() > this.currentPosition) {
            final String newsId = (String) this.newsIds.get(this.currentPosition);
            String title = (String) this.newsTitle.get(this.currentPosition);
            String image = (String) this.imageUrls.get(this.currentPosition);
            this.ivShowNews.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(ImageViewerActivity.this.act, NewsDescriptionActivity.class);
                    intent.setAction(System.currentTimeMillis() + "");
                    intent.putExtra(Constants.SPECIAL_NEWS, newsId);
                    intent.putExtra(Constants.EXTRA_BACK_TO_HOME, false);
                    ImageViewerActivity.this.act.startActivity(intent);
                }
            });
        }
    }

    protected void onPause() {
        super.onPause();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == 2) {
            this.isPortrat = false;
        } else if (newConfig.orientation == 1) {
            this.isPortrat = true;
        }
        handleView();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.root:
            case R.id.root2:
                finish();
                return;
            default:
                return;
        }
    }

    private void shareImage(String path) {
        if (new File(path).exists()) {
            if (!path.startsWith("file://")) {
                path = makeImageFilePath(path);
            }
            Intent share = new Intent("android.intent.action.SEND");
            share.setType("image/jpeg");
            share.putExtra("android.intent.extra.STREAM", Uri.parse(path));
            startActivity(Intent.createChooser(share, "Share Image"));
        }
    }

    protected void onResume() {
        super.onResume();
        try {
            handleView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
