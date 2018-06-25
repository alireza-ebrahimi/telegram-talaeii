package org.telegram.customization.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.util.view.zoom.TouchImageView;
import org.telegram.messenger.ApplicationLoader;

public class HeightImageAdapter extends PagerAdapter implements OnClickListener {
    private final Activity act;
    Context context;
    ArrayList<String> imageUrls = new ArrayList();
    LayoutInflater inflater;

    public HeightImageAdapter(Context context, Activity activity, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.act = activity;
    }

    public int getCount() {
        return this.imageUrls.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View view = this.inflater.inflate(R.layout.height_screen_shot_full_item, null);
        final TouchImageView imageView = (TouchImageView) view.findViewById(R.id.iv_screen_shot);
        final ProgressBar pbImageLoading = (ProgressBar) view.findViewById(R.id.pb_loading);
        String url = (String) this.imageUrls.get(position);
        if (url != null) {
            view.findViewById(R.id.root).setOnClickListener(this);
            ImageLoader.getInstance().displayImage(url, imageView, ApplicationLoader.getImageOptions(), new ImageLoadingListener() {
                public void onLoadingStarted(String imageUri, View view) {
                }

                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                }

                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    Log.d("sadegh", "onLoadingComplete1");
                    imageView.setImageBitmap(loadedImage);
                    imageView.setVisibility(0);
                    pbImageLoading.setVisibility(8);
                    imageView.setZoom(1.0f);
                }

                public void onLoadingCancelled(String imageUri, View view) {
                }
            });
        }
        ((ViewPager) container).addView(view, 0);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.root:
                this.act.finish();
                return;
            default:
                return;
        }
    }
}
