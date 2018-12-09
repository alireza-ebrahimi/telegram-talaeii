package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.tgnet.TLRPC$TL_photoCachedSize;
import org.telegram.tgnet.TLRPC$TL_wallPaperSolid;
import org.telegram.tgnet.TLRPC$WallPaper;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.LayoutHelper;

public class WallpaperCell extends FrameLayout {
    private BackupImageView imageView;
    private ImageView imageView2;
    private View selectionView;

    public WallpaperCell(Context context) {
        super(context);
        this.imageView = new BackupImageView(context);
        addView(this.imageView, LayoutHelper.createFrame(100, 100, 83));
        this.imageView2 = new ImageView(context);
        this.imageView2.setImageResource(R.drawable.ic_gallery_background);
        this.imageView2.setScaleType(ScaleType.CENTER);
        addView(this.imageView2, LayoutHelper.createFrame(100, 100, 83));
        this.selectionView = new View(context);
        this.selectionView.setBackgroundResource(R.drawable.wall_selection);
        addView(this.selectionView, LayoutHelper.createFrame(100, 102.0f));
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(100.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(102.0f), 1073741824));
    }

    public void setWallpaper(TLRPC$WallPaper tLRPC$WallPaper, int i, Drawable drawable, boolean z) {
        int i2 = 4;
        int i3 = 0;
        if (tLRPC$WallPaper == null) {
            this.imageView.setVisibility(4);
            this.imageView2.setVisibility(0);
            View view;
            if (z) {
                view = this.selectionView;
                if (i != -2) {
                    i3 = 4;
                }
                view.setVisibility(i3);
                this.imageView2.setImageDrawable(drawable);
                this.imageView2.setScaleType(ScaleType.CENTER_CROP);
                return;
            }
            view = this.selectionView;
            if (i != -1) {
                i3 = 4;
            }
            view.setVisibility(i3);
            ImageView imageView = this.imageView2;
            i3 = (i == -1 || i == 1000001) ? 1514625126 : 1509949440;
            imageView.setBackgroundColor(i3);
            this.imageView2.setScaleType(ScaleType.CENTER);
            return;
        }
        this.imageView.setVisibility(0);
        this.imageView2.setVisibility(4);
        View view2 = this.selectionView;
        if (i == tLRPC$WallPaper.id) {
            i2 = 0;
        }
        view2.setVisibility(i2);
        if (tLRPC$WallPaper instanceof TLRPC$TL_wallPaperSolid) {
            this.imageView.setImageBitmap(null);
            this.imageView.setBackgroundColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR | tLRPC$WallPaper.bg_color);
            return;
        }
        int dp = AndroidUtilities.dp(100.0f);
        int i4 = 0;
        PhotoSize photoSize = null;
        while (i4 < tLRPC$WallPaper.sizes.size()) {
            PhotoSize photoSize2 = (PhotoSize) tLRPC$WallPaper.sizes.get(i4);
            if (photoSize2 == null) {
                photoSize2 = photoSize;
            } else {
                int i5 = photoSize2.f10147w >= photoSize2.f10146h ? photoSize2.f10147w : photoSize2.f10146h;
                if (photoSize != null && ((dp <= 100 || photoSize.location == null || photoSize.location.dc_id != Integer.MIN_VALUE) && !(photoSize2 instanceof TLRPC$TL_photoCachedSize) && i5 > dp)) {
                    photoSize2 = photoSize;
                }
            }
            i4++;
            photoSize = photoSize2;
        }
        if (!(photoSize == null || photoSize.location == null)) {
            this.imageView.setImage(photoSize.location, "100_100", (Drawable) null);
        }
        this.imageView.setBackgroundColor(1514625126);
    }
}
