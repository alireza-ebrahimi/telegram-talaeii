package org.telegram.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_wallPaper;
import org.telegram.tgnet.TLRPC$TL_wallPaperSolid;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.tgnet.TLRPC$WallPaper;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.TL_account_getWallPapers;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.WallpaperCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RadialProgressView;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.WallpaperUpdater;
import org.telegram.ui.Components.WallpaperUpdater.WallpaperUpdaterDelegate;

public class WallpapersActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int done_button = 1;
    private ImageView backgroundImage;
    private View doneButton;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private String loadingFile = null;
    private File loadingFileObject = null;
    private PhotoSize loadingSize = null;
    private boolean overrideThemeWallpaper;
    private RadialProgressView progressBar;
    private FrameLayout progressView;
    private View progressViewBackground;
    private int selectedBackground;
    private int selectedColor;
    private Drawable themedWallpaper;
    private WallpaperUpdater updater;
    private ArrayList<TLRPC$WallPaper> wallPapers = new ArrayList();
    private File wallpaperFile;
    private HashMap<Integer, TLRPC$WallPaper> wallpappersByIds = new HashMap();

    /* renamed from: org.telegram.ui.WallpapersActivity$1 */
    class C52901 implements WallpaperUpdaterDelegate {
        C52901() {
        }

        public void didSelectWallpaper(File file, Bitmap bitmap) {
            WallpapersActivity.this.selectedBackground = -1;
            WallpapersActivity.this.overrideThemeWallpaper = true;
            WallpapersActivity.this.selectedColor = 0;
            WallpapersActivity.this.wallpaperFile = file;
            WallpapersActivity.this.backgroundImage.getDrawable();
            WallpapersActivity.this.backgroundImage.setImageBitmap(bitmap);
        }

        public void needOpenColorPicker() {
        }
    }

    /* renamed from: org.telegram.ui.WallpapersActivity$2 */
    class C52912 extends ActionBarMenuOnItemClick {
        C52912() {
        }

        public void onItemClick(int i) {
            boolean copyFile;
            Editor edit;
            String str;
            boolean z = false;
            if (i == -1) {
                WallpapersActivity.this.finishFragment();
            } else if (i == 1) {
                TLRPC$WallPaper tLRPC$WallPaper = (TLRPC$WallPaper) WallpapersActivity.this.wallpappersByIds.get(Integer.valueOf(WallpapersActivity.this.selectedBackground));
                if (tLRPC$WallPaper != null && tLRPC$WallPaper.id != 1000001 && (tLRPC$WallPaper instanceof TLRPC$TL_wallPaper)) {
                    int i2 = AndroidUtilities.displaySize.x;
                    int i3 = AndroidUtilities.displaySize.y;
                    if (i2 <= i3) {
                        int i4 = i3;
                        i3 = i2;
                        i2 = i4;
                    }
                    PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(tLRPC$WallPaper.sizes, Math.min(i3, i2));
                    try {
                        copyFile = AndroidUtilities.copyFile(new File(FileLoader.getInstance().getDirectory(4), closestPhotoSizeWithSize.location.volume_id + "_" + closestPhotoSizeWithSize.location.local_id + ".jpg"), new File(ApplicationLoader.getFilesDirFixed(), "wallpaper.jpg"));
                    } catch (Throwable e) {
                        FileLog.e(e);
                        copyFile = false;
                        if (copyFile) {
                            edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                            edit.putInt("selectedBackground", WallpapersActivity.this.selectedBackground);
                            edit.putInt("selectedColor", WallpapersActivity.this.selectedColor);
                            str = "overrideThemeWallpaper";
                            z = true;
                            edit.putBoolean(str, z);
                            edit.commit();
                            Theme.reloadWallpaper();
                        }
                        WallpapersActivity.this.finishFragment();
                    }
                } else if (WallpapersActivity.this.selectedBackground == -1) {
                    try {
                        copyFile = AndroidUtilities.copyFile(WallpapersActivity.this.updater.getCurrentWallpaperPath(), new File(ApplicationLoader.getFilesDirFixed(), "wallpaper.jpg"));
                    } catch (Throwable e2) {
                        FileLog.e(e2);
                        copyFile = false;
                        if (copyFile) {
                            edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                            edit.putInt("selectedBackground", WallpapersActivity.this.selectedBackground);
                            edit.putInt("selectedColor", WallpapersActivity.this.selectedColor);
                            str = "overrideThemeWallpaper";
                            z = true;
                            edit.putBoolean(str, z);
                            edit.commit();
                            Theme.reloadWallpaper();
                        }
                        WallpapersActivity.this.finishFragment();
                    }
                } else {
                    copyFile = true;
                }
                if (copyFile) {
                    edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                    edit.putInt("selectedBackground", WallpapersActivity.this.selectedBackground);
                    edit.putInt("selectedColor", WallpapersActivity.this.selectedColor);
                    str = "overrideThemeWallpaper";
                    if (Theme.hasWallpaperFromTheme() && WallpapersActivity.this.overrideThemeWallpaper) {
                        z = true;
                    }
                    edit.putBoolean(str, z);
                    edit.commit();
                    Theme.reloadWallpaper();
                }
                WallpapersActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.WallpapersActivity$3 */
    class C52923 implements OnTouchListener {
        C52923() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.WallpapersActivity$4 */
    class C52934 implements OnItemClickListener {
        C52934() {
        }

        public void onItemClick(View view, int i) {
            if (i == 0) {
                WallpapersActivity.this.updater.showAlert(false);
                return;
            }
            int i2;
            if (!Theme.hasWallpaperFromTheme()) {
                i2 = i - 1;
            } else if (i == 1) {
                WallpapersActivity.this.selectedBackground = -2;
                WallpapersActivity.this.overrideThemeWallpaper = false;
                WallpapersActivity.this.listAdapter.notifyDataSetChanged();
                WallpapersActivity.this.processSelectedBackground();
                return;
            } else {
                i2 = i - 2;
            }
            WallpapersActivity.this.selectedBackground = ((TLRPC$WallPaper) WallpapersActivity.this.wallPapers.get(i2)).id;
            WallpapersActivity.this.overrideThemeWallpaper = true;
            WallpapersActivity.this.listAdapter.notifyDataSetChanged();
            WallpapersActivity.this.processSelectedBackground();
        }
    }

    /* renamed from: org.telegram.ui.WallpapersActivity$5 */
    class C52955 implements RequestDelegate {
        C52955() {
        }

        public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            if (tLRPC$TL_error == null) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        WallpapersActivity.this.wallPapers.clear();
                        TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject;
                        WallpapersActivity.this.wallpappersByIds.clear();
                        Iterator it = tLRPC$Vector.objects.iterator();
                        while (it.hasNext()) {
                            Object next = it.next();
                            WallpapersActivity.this.wallPapers.add((TLRPC$WallPaper) next);
                            WallpapersActivity.this.wallpappersByIds.put(Integer.valueOf(((TLRPC$WallPaper) next).id), (TLRPC$WallPaper) next);
                        }
                        if (WallpapersActivity.this.listAdapter != null) {
                            WallpapersActivity.this.listAdapter.notifyDataSetChanged();
                        }
                        if (WallpapersActivity.this.backgroundImage != null) {
                            WallpapersActivity.this.processSelectedBackground();
                        }
                        MessagesStorage.getInstance().putWallpapers(WallpapersActivity.this.wallPapers);
                    }
                });
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            int size = WallpapersActivity.this.wallPapers.size() + 1;
            return Theme.hasWallpaperFromTheme() ? size + 1 : size;
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return true;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            int i2 = -2;
            WallpaperCell wallpaperCell = (WallpaperCell) viewHolder.itemView;
            if (i == 0) {
                int access$000 = (!Theme.hasWallpaperFromTheme() || WallpapersActivity.this.overrideThemeWallpaper) ? WallpapersActivity.this.selectedBackground : -2;
                wallpaperCell.setWallpaper(null, access$000, null, false);
                return;
            }
            if (!Theme.hasWallpaperFromTheme()) {
                access$000 = i - 1;
            } else if (i == 1) {
                if (WallpapersActivity.this.overrideThemeWallpaper) {
                    i2 = -1;
                }
                wallpaperCell.setWallpaper(null, i2, WallpapersActivity.this.themedWallpaper, true);
                return;
            } else {
                access$000 = i - 2;
            }
            TLRPC$WallPaper tLRPC$WallPaper = (TLRPC$WallPaper) WallpapersActivity.this.wallPapers.get(access$000);
            if (!Theme.hasWallpaperFromTheme() || WallpapersActivity.this.overrideThemeWallpaper) {
                i2 = WallpapersActivity.this.selectedBackground;
            }
            wallpaperCell.setWallpaper(tLRPC$WallPaper, i2, null, false);
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new Holder(new WallpaperCell(this.mContext));
        }
    }

    private void loadWallpapers() {
        ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(new TL_account_getWallPapers(), new C52955()), this.classGuid);
    }

    private void processSelectedBackground() {
        if (!Theme.hasWallpaperFromTheme() || this.overrideThemeWallpaper) {
            TLRPC$WallPaper tLRPC$WallPaper = (TLRPC$WallPaper) this.wallpappersByIds.get(Integer.valueOf(this.selectedBackground));
            if (this.selectedBackground == -1 || this.selectedBackground == 1000001 || tLRPC$WallPaper == null || !(tLRPC$WallPaper instanceof TLRPC$TL_wallPaper)) {
                if (this.loadingFile != null) {
                    FileLoader.getInstance().cancelLoadFile(this.loadingSize);
                }
                if (this.selectedBackground == 1000001) {
                    this.backgroundImage.setImageResource(R.drawable.background_hd);
                    this.backgroundImage.setBackgroundColor(0);
                    this.selectedColor = 0;
                } else if (this.selectedBackground == -1) {
                    File file = this.wallpaperFile != null ? this.wallpaperFile : new File(ApplicationLoader.getFilesDirFixed(), "wallpaper.jpg");
                    if (file.exists()) {
                        this.backgroundImage.setImageURI(Uri.fromFile(file));
                    } else {
                        this.selectedBackground = 1000001;
                        this.overrideThemeWallpaper = true;
                        processSelectedBackground();
                    }
                } else if (tLRPC$WallPaper == null) {
                    return;
                } else {
                    if (tLRPC$WallPaper instanceof TLRPC$TL_wallPaperSolid) {
                        this.backgroundImage.getDrawable();
                        this.backgroundImage.setImageBitmap(null);
                        this.selectedColor = tLRPC$WallPaper.bg_color | Theme.ACTION_BAR_VIDEO_EDIT_COLOR;
                        this.backgroundImage.setBackgroundColor(this.selectedColor);
                    }
                }
                this.loadingFileObject = null;
                this.loadingFile = null;
                this.loadingSize = null;
                this.doneButton.setEnabled(true);
                this.progressView.setVisibility(8);
                return;
            }
            int i = AndroidUtilities.displaySize.x;
            int i2 = AndroidUtilities.displaySize.y;
            if (i <= i2) {
                int i3 = i2;
                i2 = i;
                i = i3;
            }
            PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(tLRPC$WallPaper.sizes, Math.min(i2, i));
            if (closestPhotoSizeWithSize != null) {
                String str = closestPhotoSizeWithSize.location.volume_id + "_" + closestPhotoSizeWithSize.location.local_id + ".jpg";
                File file2 = new File(FileLoader.getInstance().getDirectory(4), str);
                if (file2.exists()) {
                    if (this.loadingFile != null) {
                        FileLoader.getInstance().cancelLoadFile(this.loadingSize);
                    }
                    this.loadingFileObject = null;
                    this.loadingFile = null;
                    this.loadingSize = null;
                    try {
                        this.backgroundImage.setImageURI(Uri.fromFile(file2));
                    } catch (Throwable th) {
                        FileLog.e(th);
                    }
                    this.backgroundImage.setBackgroundColor(0);
                    this.selectedColor = 0;
                    this.doneButton.setEnabled(true);
                    this.progressView.setVisibility(8);
                    return;
                }
                this.progressViewBackground.getBackground().setColorFilter(new PorterDuffColorFilter(AndroidUtilities.calcDrawableColor(this.backgroundImage.getDrawable())[0], Mode.MULTIPLY));
                this.loadingFile = str;
                this.loadingFileObject = file2;
                this.doneButton.setEnabled(false);
                this.progressView.setVisibility(0);
                this.loadingSize = closestPhotoSizeWithSize;
                this.selectedColor = 0;
                FileLoader.getInstance().loadFile(closestPhotoSizeWithSize, null, 1);
                this.backgroundImage.setBackgroundColor(0);
                return;
            }
            return;
        }
        this.backgroundImage.setImageDrawable(Theme.getThemedWallpaper(false));
    }

    public View createView(Context context) {
        this.themedWallpaper = Theme.getThemedWallpaper(true);
        this.updater = new WallpaperUpdater(getParentActivity(), new C52901());
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("ChatBackground", R.string.ChatBackground));
        this.actionBar.setActionBarMenuOnItemClick(new C52912());
        this.doneButton = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        View frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        this.backgroundImage = new ImageView(context);
        this.backgroundImage.setScaleType(ScaleType.CENTER_CROP);
        frameLayout.addView(this.backgroundImage, LayoutHelper.createFrame(-1, -1.0f));
        this.backgroundImage.setOnTouchListener(new C52923());
        this.progressView = new FrameLayout(context);
        this.progressView.setVisibility(4);
        frameLayout.addView(this.progressView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 52.0f));
        this.progressViewBackground = new View(context);
        this.progressViewBackground.setBackgroundResource(R.drawable.system_loader);
        this.progressView.addView(this.progressViewBackground, LayoutHelper.createFrame(36, 36, 17));
        this.progressBar = new RadialProgressView(context);
        this.progressBar.setSize(AndroidUtilities.dp(28.0f));
        this.progressBar.setProgressColor(-1);
        this.progressView.addView(this.progressBar, LayoutHelper.createFrame(32, 32, 17));
        this.listView = new RecyclerListView(context);
        this.listView.setClipToPadding(false);
        this.listView.setTag(Integer.valueOf(8));
        this.listView.setPadding(AndroidUtilities.dp(40.0f), 0, AndroidUtilities.dp(40.0f), 0);
        LayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(0);
        this.listView.setLayoutManager(linearLayoutManager);
        this.listView.setDisallowInterceptTouchEvents(true);
        this.listView.setOverScrollMode(2);
        RecyclerListView recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.listAdapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, 102, 83));
        this.listView.setOnItemClickListener(new C52934());
        processSelectedBackground();
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        String str;
        if (i == NotificationCenter.FileDidFailedLoad) {
            str = (String) objArr[0];
            if (this.loadingFile != null && this.loadingFile.equals(str)) {
                this.loadingFileObject = null;
                this.loadingFile = null;
                this.loadingSize = null;
                this.progressView.setVisibility(8);
                this.doneButton.setEnabled(false);
            }
        } else if (i == NotificationCenter.FileDidLoaded) {
            str = (String) objArr[0];
            if (this.loadingFile != null && this.loadingFile.equals(str)) {
                this.backgroundImage.setImageURI(Uri.fromFile(this.loadingFileObject));
                this.progressView.setVisibility(8);
                this.backgroundImage.setBackgroundColor(0);
                this.doneButton.setEnabled(true);
                this.loadingFileObject = null;
                this.loadingFile = null;
                this.loadingSize = null;
            }
        } else if (i == NotificationCenter.wallpapersDidLoaded) {
            this.wallPapers = (ArrayList) objArr[0];
            this.wallpappersByIds.clear();
            Iterator it = this.wallPapers.iterator();
            while (it.hasNext()) {
                TLRPC$WallPaper tLRPC$WallPaper = (TLRPC$WallPaper) it.next();
                this.wallpappersByIds.put(Integer.valueOf(tLRPC$WallPaper.id), tLRPC$WallPaper);
            }
            if (this.listAdapter != null) {
                this.listAdapter.notifyDataSetChanged();
            }
            if (!(this.wallPapers.isEmpty() || this.backgroundImage == null)) {
                processSelectedBackground();
            }
            loadWallpapers();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[7];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[2] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[6] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        return themeDescriptionArr;
    }

    public void onActivityResultFragment(int i, int i2, Intent intent) {
        this.updater.onActivityResult(i, i2, intent);
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidFailedLoad);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.wallpapersDidLoaded);
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        this.selectedBackground = sharedPreferences.getInt("selectedBackground", 1000001);
        this.overrideThemeWallpaper = sharedPreferences.getBoolean("overrideThemeWallpaper", false);
        this.selectedColor = sharedPreferences.getInt("selectedColor", 0);
        MessagesStorage.getInstance().getWallpapers();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        this.updater.cleanup();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileDidFailedLoad);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.wallpapersDidLoaded);
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
        processSelectedBackground();
    }

    public void restoreSelfArgs(Bundle bundle) {
        this.updater.setCurrentPicturePath(bundle.getString("path"));
    }

    public void saveSelfArgs(Bundle bundle) {
        String currentPicturePath = this.updater.getCurrentPicturePath();
        if (currentPicturePath != null) {
            bundle.putString("path", currentPicturePath);
        }
    }
}
