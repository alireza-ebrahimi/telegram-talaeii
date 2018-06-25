package org.telegram.ui.Cells;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

public class PhotoPickerSearchCell extends LinearLayout {
    private PhotoPickerSearchCellDelegate delegate;

    /* renamed from: org.telegram.ui.Cells.PhotoPickerSearchCell$1 */
    class C40221 implements OnClickListener {
        C40221() {
        }

        public void onClick(View view) {
            if (PhotoPickerSearchCell.this.delegate != null) {
                PhotoPickerSearchCell.this.delegate.didPressedSearchButton(0);
            }
        }
    }

    /* renamed from: org.telegram.ui.Cells.PhotoPickerSearchCell$2 */
    class C40232 implements OnClickListener {
        C40232() {
        }

        public void onClick(View view) {
            if (PhotoPickerSearchCell.this.delegate != null) {
                PhotoPickerSearchCell.this.delegate.didPressedSearchButton(1);
            }
        }
    }

    public interface PhotoPickerSearchCellDelegate {
        void didPressedSearchButton(int i);
    }

    private class SearchButton extends FrameLayout {
        private ImageView imageView;
        private View selector;
        private TextView textView1;
        private TextView textView2;

        public SearchButton(Context context) {
            super(context);
            setBackgroundColor(-15066598);
            this.selector = new View(context);
            this.selector.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            addView(this.selector, LayoutHelper.createFrame(-1, -1.0f));
            this.imageView = new ImageView(context);
            this.imageView.setScaleType(ScaleType.CENTER);
            addView(this.imageView, LayoutHelper.createFrame(48, 48, 51));
            this.textView1 = new TextView(context);
            this.textView1.setGravity(16);
            this.textView1.setTextSize(1, 14.0f);
            this.textView1.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.textView1.setTextColor(-1);
            this.textView1.setSingleLine(true);
            this.textView1.setEllipsize(TruncateAt.END);
            addView(this.textView1, LayoutHelper.createFrame(-1, -2.0f, 51, 51.0f, 8.0f, 4.0f, BitmapDescriptorFactory.HUE_RED));
            this.textView2 = new TextView(context);
            this.textView2.setGravity(16);
            this.textView2.setTextSize(1, 10.0f);
            this.textView2.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.textView2.setTextColor(-10066330);
            this.textView2.setSingleLine(true);
            this.textView2.setEllipsize(TruncateAt.END);
            addView(this.textView2, LayoutHelper.createFrame(-1, -2.0f, 51, 51.0f, 26.0f, 4.0f, BitmapDescriptorFactory.HUE_RED));
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (VERSION.SDK_INT >= 21) {
                this.selector.drawableHotspotChanged(motionEvent.getX(), motionEvent.getY());
            }
            return super.onTouchEvent(motionEvent);
        }
    }

    public PhotoPickerSearchCell(Context context, boolean z) {
        super(context);
        setOrientation(0);
        View searchButton = new SearchButton(context);
        searchButton.textView1.setText(LocaleController.getString("SearchImages", R.string.SearchImages));
        searchButton.textView2.setText(LocaleController.getString("SearchImagesInfo", R.string.SearchImagesInfo));
        searchButton.imageView.setImageResource(R.drawable.search_web);
        addView(searchButton);
        LayoutParams layoutParams = (LayoutParams) searchButton.getLayoutParams();
        layoutParams.weight = 0.5f;
        layoutParams.topMargin = AndroidUtilities.dp(4.0f);
        layoutParams.height = AndroidUtilities.dp(48.0f);
        layoutParams.width = 0;
        searchButton.setLayoutParams(layoutParams);
        searchButton.setOnClickListener(new C40221());
        searchButton = new FrameLayout(context);
        searchButton.setBackgroundColor(0);
        addView(searchButton);
        layoutParams = (LayoutParams) searchButton.getLayoutParams();
        layoutParams.topMargin = AndroidUtilities.dp(4.0f);
        layoutParams.height = AndroidUtilities.dp(48.0f);
        layoutParams.width = AndroidUtilities.dp(4.0f);
        searchButton.setLayoutParams(layoutParams);
        searchButton = new SearchButton(context);
        searchButton.textView1.setText(LocaleController.getString("SearchGifs", R.string.SearchGifs));
        searchButton.textView2.setText("GIPHY");
        searchButton.imageView.setImageResource(R.drawable.search_gif);
        addView(searchButton);
        layoutParams = (LayoutParams) searchButton.getLayoutParams();
        layoutParams.weight = 0.5f;
        layoutParams.topMargin = AndroidUtilities.dp(4.0f);
        layoutParams.height = AndroidUtilities.dp(48.0f);
        layoutParams.width = 0;
        searchButton.setLayoutParams(layoutParams);
        if (z) {
            searchButton.setOnClickListener(new C40232());
        } else {
            searchButton.setAlpha(0.5f);
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(52.0f), 1073741824));
    }

    public void setDelegate(PhotoPickerSearchCellDelegate photoPickerSearchCellDelegate) {
        this.delegate = photoPickerSearchCellDelegate;
    }
}
