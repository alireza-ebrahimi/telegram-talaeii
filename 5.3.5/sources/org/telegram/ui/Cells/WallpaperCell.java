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
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_photoCachedSize;
import org.telegram.tgnet.TLRPC$TL_wallPaperSolid;
import org.telegram.tgnet.TLRPC$WallPaper;
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

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(100.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(102.0f), 1073741824));
    }

    public void setWallpaper(TLRPC$WallPaper wallpaper, int selectedBackground, Drawable themedWallpaper, boolean themed) {
        int i = 0;
        if (wallpaper == null) {
            this.imageView.setVisibility(4);
            this.imageView2.setVisibility(0);
            if (themed) {
                this.selectionView.setVisibility(selectedBackground == -2 ? 0 : 4);
                this.imageView2.setImageDrawable(themedWallpaper);
                this.imageView2.setScaleType(ScaleType.CENTER_CROP);
                return;
            }
            View view = this.selectionView;
            if (selectedBackground != -1) {
                i = 4;
            }
            view.setVisibility(i);
            ImageView imageView = this.imageView2;
            int i2 = (selectedBackground == -1 || selectedBackground == 1000001) ? 1514625126 : 1509949440;
            imageView.setBackgroundColor(i2);
            this.imageView2.setScaleType(ScaleType.CENTER);
            return;
        }
        this.imageView.setVisibility(0);
        this.imageView2.setVisibility(4);
        View view2 = this.selectionView;
        if (selectedBackground != wallpaper.id) {
            i = 4;
        }
        view2.setVisibility(i);
        if (wallpaper instanceof TLRPC$TL_wallPaperSolid) {
            this.imageView.setImageBitmap(null);
            this.imageView.setBackgroundColor(-16777216 | wallpaper.bg_color);
            return;
        }
        int side = AndroidUtilities.dp(100.0f);
        TLRPC$PhotoSize size = null;
        for (int a = 0; a < wallpaper.sizes.size(); a++) {
            TLRPC$PhotoSize obj = (TLRPC$PhotoSize) wallpaper.sizes.get(a);
            if (obj != null) {
                int currentSide = obj.f78w >= obj.f77h ? obj.f78w : obj.f77h;
                if (size == null || ((side > 100 && size.location != null && size.location.dc_id == Integer.MIN_VALUE) || (obj instanceof TLRPC$TL_photoCachedSize) || currentSide <= side)) {
                    size = obj;
                }
            }
        }
        if (!(size == null || size.location == null)) {
            this.imageView.setImage(size.location, "100_100", (Drawable) null);
        }
        this.imageView.setBackgroundColor(1514625126);
    }
}
