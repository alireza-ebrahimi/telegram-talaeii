package org.telegram.ui.Cells;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MediaController.AlbumEntry;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.LayoutHelper;

public class PhotoPickerAlbumsCell extends FrameLayout {
    private AlbumEntry[] albumEntries = new AlbumEntry[4];
    private AlbumView[] albumViews = new AlbumView[4];
    private int albumsCount;
    private PhotoPickerAlbumsCellDelegate delegate;

    /* renamed from: org.telegram.ui.Cells.PhotoPickerAlbumsCell$1 */
    class C40191 implements OnClickListener {
        C40191() {
        }

        public void onClick(View view) {
            if (PhotoPickerAlbumsCell.this.delegate != null) {
                PhotoPickerAlbumsCell.this.delegate.didSelectAlbum(PhotoPickerAlbumsCell.this.albumEntries[((Integer) view.getTag()).intValue()]);
            }
        }
    }

    private class AlbumView extends FrameLayout {
        private TextView countTextView;
        private BackupImageView imageView;
        private TextView nameTextView;
        private View selector;

        public AlbumView(Context context) {
            super(context);
            this.imageView = new BackupImageView(context);
            addView(this.imageView, LayoutHelper.createFrame(-1, -1.0f));
            View linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(0);
            linearLayout.setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
            addView(linearLayout, LayoutHelper.createFrame(-1, 28, 83));
            this.nameTextView = new TextView(context);
            this.nameTextView.setTextSize(1, 13.0f);
            this.nameTextView.setTextColor(-1);
            this.nameTextView.setSingleLine(true);
            this.nameTextView.setEllipsize(TruncateAt.END);
            this.nameTextView.setMaxLines(1);
            this.nameTextView.setGravity(16);
            linearLayout.addView(this.nameTextView, LayoutHelper.createLinear(0, -1, 1.0f, 8, 0, 0, 0));
            this.countTextView = new TextView(context);
            this.countTextView.setTextSize(1, 13.0f);
            this.countTextView.setTextColor(-5592406);
            this.countTextView.setSingleLine(true);
            this.countTextView.setEllipsize(TruncateAt.END);
            this.countTextView.setMaxLines(1);
            this.countTextView.setGravity(16);
            linearLayout.addView(this.countTextView, LayoutHelper.createLinear(-2, -1, 4.0f, BitmapDescriptorFactory.HUE_RED, 4.0f, BitmapDescriptorFactory.HUE_RED));
            this.selector = new View(context);
            this.selector.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            addView(this.selector, LayoutHelper.createFrame(-1, -1.0f));
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (VERSION.SDK_INT >= 21) {
                this.selector.drawableHotspotChanged(motionEvent.getX(), motionEvent.getY());
            }
            return super.onTouchEvent(motionEvent);
        }
    }

    public interface PhotoPickerAlbumsCellDelegate {
        void didSelectAlbum(AlbumEntry albumEntry);
    }

    public PhotoPickerAlbumsCell(Context context) {
        super(context);
        for (int i = 0; i < 4; i++) {
            this.albumViews[i] = new AlbumView(context);
            addView(this.albumViews[i]);
            this.albumViews[i].setVisibility(4);
            this.albumViews[i].setTag(Integer.valueOf(i));
            this.albumViews[i].setOnClickListener(new C40191());
        }
    }

    protected void onMeasure(int i, int i2) {
        int dp = AndroidUtilities.isTablet() ? (AndroidUtilities.dp(490.0f) - ((this.albumsCount + 1) * AndroidUtilities.dp(4.0f))) / this.albumsCount : (AndroidUtilities.displaySize.x - ((this.albumsCount + 1) * AndroidUtilities.dp(4.0f))) / this.albumsCount;
        for (int i3 = 0; i3 < this.albumsCount; i3++) {
            LayoutParams layoutParams = (LayoutParams) this.albumViews[i3].getLayoutParams();
            layoutParams.topMargin = AndroidUtilities.dp(4.0f);
            layoutParams.leftMargin = (AndroidUtilities.dp(4.0f) + dp) * i3;
            layoutParams.width = dp;
            layoutParams.height = dp;
            layoutParams.gravity = 51;
            this.albumViews[i3].setLayoutParams(layoutParams);
        }
        super.onMeasure(i, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(4.0f) + dp, 1073741824));
    }

    public void setAlbum(int i, AlbumEntry albumEntry) {
        this.albumEntries[i] = albumEntry;
        if (albumEntry != null) {
            AlbumView albumView = this.albumViews[i];
            albumView.imageView.setOrientation(0, true);
            if (albumEntry.coverPhoto == null || albumEntry.coverPhoto.path == null) {
                albumView.imageView.setImageResource(R.drawable.nophotos);
            } else {
                albumView.imageView.setOrientation(albumEntry.coverPhoto.orientation, true);
                if (albumEntry.coverPhoto.isVideo) {
                    albumView.imageView.setImage("vthumb://" + albumEntry.coverPhoto.imageId + ":" + albumEntry.coverPhoto.path, null, getContext().getResources().getDrawable(R.drawable.nophotos));
                } else {
                    albumView.imageView.setImage("thumb://" + albumEntry.coverPhoto.imageId + ":" + albumEntry.coverPhoto.path, null, getContext().getResources().getDrawable(R.drawable.nophotos));
                }
            }
            albumView.nameTextView.setText(albumEntry.bucketName);
            albumView.countTextView.setText(String.format("%d", new Object[]{Integer.valueOf(albumEntry.photos.size())}));
            return;
        }
        this.albumViews[i].setVisibility(4);
    }

    public void setAlbumsCount(int i) {
        int i2 = 0;
        while (i2 < this.albumViews.length) {
            this.albumViews[i2].setVisibility(i2 < i ? 0 : 4);
            i2++;
        }
        this.albumsCount = i;
    }

    public void setDelegate(PhotoPickerAlbumsCellDelegate photoPickerAlbumsCellDelegate) {
        this.delegate = photoPickerAlbumsCellDelegate;
    }
}
