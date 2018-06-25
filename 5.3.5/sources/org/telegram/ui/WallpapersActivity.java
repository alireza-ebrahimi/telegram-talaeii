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
import io.fabric.sdk.android.services.events.EventsFilesManager;
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
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_account_getWallPapers;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_wallPaper;
import org.telegram.tgnet.TLRPC$TL_wallPaperSolid;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.tgnet.TLRPC$WallPaper;
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
    private TLRPC$PhotoSize loadingSize = null;
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
    class C34511 implements WallpaperUpdaterDelegate {
        C34511() {
        }

        public void didSelectWallpaper(File file, Bitmap bitmap) {
            WallpapersActivity.this.selectedBackground = -1;
            WallpapersActivity.this.overrideThemeWallpaper = true;
            WallpapersActivity.this.selectedColor = 0;
            WallpapersActivity.this.wallpaperFile = file;
            Drawable drawable = WallpapersActivity.this.backgroundImage.getDrawable();
            WallpapersActivity.this.backgroundImage.setImageBitmap(bitmap);
        }

        public void needOpenColorPicker() {
        }
    }

    /* renamed from: org.telegram.ui.WallpapersActivity$2 */
    class C34522 extends ActionBarMenuOnItemClick {
        C34522() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                WallpapersActivity.this.finishFragment();
            } else if (id == 1) {
                boolean done;
                TLRPC$WallPaper wallPaper = (TLRPC$WallPaper) WallpapersActivity.this.wallpappersByIds.get(Integer.valueOf(WallpapersActivity.this.selectedBackground));
                if (wallPaper != null && wallPaper.id != 1000001 && (wallPaper instanceof TLRPC$TL_wallPaper)) {
                    int width = AndroidUtilities.displaySize.x;
                    int height = AndroidUtilities.displaySize.y;
                    if (width > height) {
                        int temp = width;
                        width = height;
                        height = temp;
                    }
                    TLRPC$PhotoSize size = FileLoader.getClosestPhotoSizeWithSize(wallPaper.sizes, Math.min(width, height));
                    try {
                        done = AndroidUtilities.copyFile(new File(FileLoader.getInstance().getDirectory(4), size.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + size.location.local_id + ".jpg"), new File(ApplicationLoader.getFilesDirFixed(), "wallpaper.jpg"));
                    } catch (Exception e) {
                        done = false;
                        FileLog.e(e);
                    }
                } else if (WallpapersActivity.this.selectedBackground == -1) {
                    try {
                        done = AndroidUtilities.copyFile(WallpapersActivity.this.updater.getCurrentWallpaperPath(), new File(ApplicationLoader.getFilesDirFixed(), "wallpaper.jpg"));
                    } catch (Exception e2) {
                        done = false;
                        FileLog.e(e2);
                    }
                } else {
                    done = true;
                }
                if (done) {
                    Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                    editor.putInt("selectedBackground", WallpapersActivity.this.selectedBackground);
                    editor.putInt("selectedColor", WallpapersActivity.this.selectedColor);
                    String str = "overrideThemeWallpaper";
                    boolean z = Theme.hasWallpaperFromTheme() && WallpapersActivity.this.overrideThemeWallpaper;
                    editor.putBoolean(str, z);
                    editor.commit();
                    Theme.reloadWallpaper();
                }
                WallpapersActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.WallpapersActivity$3 */
    class C34533 implements OnTouchListener {
        C34533() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.WallpapersActivity$4 */
    class C34544 implements OnItemClickListener {
        C34544() {
        }

        public void onItemClick(View view, int position) {
            if (position == 0) {
                WallpapersActivity.this.updater.showAlert(false);
                return;
            }
            if (!Theme.hasWallpaperFromTheme()) {
                position--;
            } else if (position == 1) {
                WallpapersActivity.this.selectedBackground = -2;
                WallpapersActivity.this.overrideThemeWallpaper = false;
                WallpapersActivity.this.listAdapter.notifyDataSetChanged();
                WallpapersActivity.this.processSelectedBackground();
                return;
            } else {
                position -= 2;
            }
            WallpapersActivity.this.selectedBackground = ((TLRPC$WallPaper) WallpapersActivity.this.wallPapers.get(position)).id;
            WallpapersActivity.this.overrideThemeWallpaper = true;
            WallpapersActivity.this.listAdapter.notifyDataSetChanged();
            WallpapersActivity.this.processSelectedBackground();
        }
    }

    /* renamed from: org.telegram.ui.WallpapersActivity$5 */
    class C34565 implements RequestDelegate {
        C34565() {
        }

        public void run(final TLObject response, TLRPC$TL_error error) {
            if (error == null) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        WallpapersActivity.this.wallPapers.clear();
                        TLRPC$Vector res = response;
                        WallpapersActivity.this.wallpappersByIds.clear();
                        Iterator it = res.objects.iterator();
                        while (it.hasNext()) {
                            Object obj = it.next();
                            WallpapersActivity.this.wallPapers.add((TLRPC$WallPaper) obj);
                            WallpapersActivity.this.wallpappersByIds.put(Integer.valueOf(((TLRPC$WallPaper) obj).id), (TLRPC$WallPaper) obj);
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

        public boolean isEnabled(ViewHolder holder) {
            return true;
        }

        public int getItemCount() {
            int count = WallpapersActivity.this.wallPapers.size() + 1;
            if (Theme.hasWallpaperFromTheme()) {
                return count + 1;
            }
            return count;
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new Holder(new WallpaperCell(this.mContext));
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            int i2 = -2;
            WallpaperCell wallpaperCell = viewHolder.itemView;
            if (i == 0) {
                int access$000;
                if (!Theme.hasWallpaperFromTheme() || WallpapersActivity.this.overrideThemeWallpaper) {
                    access$000 = WallpapersActivity.this.selectedBackground;
                } else {
                    access$000 = -2;
                }
                wallpaperCell.setWallpaper(null, access$000, null, false);
                return;
            }
            if (!Theme.hasWallpaperFromTheme()) {
                i--;
            } else if (i == 1) {
                if (WallpapersActivity.this.overrideThemeWallpaper) {
                    i2 = -1;
                }
                wallpaperCell.setWallpaper(null, i2, WallpapersActivity.this.themedWallpaper, true);
                return;
            } else {
                i -= 2;
            }
            TLRPC$WallPaper tLRPC$WallPaper = (TLRPC$WallPaper) WallpapersActivity.this.wallPapers.get(i);
            if (!Theme.hasWallpaperFromTheme() || WallpapersActivity.this.overrideThemeWallpaper) {
                i2 = WallpapersActivity.this.selectedBackground;
            }
            wallpaperCell.setWallpaper(tLRPC$WallPaper, i2, null, false);
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidFailedLoad);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.wallpapersDidLoaded);
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        this.selectedBackground = preferences.getInt("selectedBackground", 1000001);
        this.overrideThemeWallpaper = preferences.getBoolean("overrideThemeWallpaper", false);
        this.selectedColor = preferences.getInt("selectedColor", 0);
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

    public View createView(Context context) {
        this.themedWallpaper = Theme.getThemedWallpaper(true);
        this.updater = new WallpaperUpdater(getParentActivity(), new C34511());
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("ChatBackground", R.string.ChatBackground));
        this.actionBar.setActionBarMenuOnItemClick(new C34522());
        this.doneButton = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        this.backgroundImage = new ImageView(context);
        this.backgroundImage.setScaleType(ScaleType.CENTER_CROP);
        frameLayout.addView(this.backgroundImage, LayoutHelper.createFrame(-1, -1.0f));
        this.backgroundImage.setOnTouchListener(new C34533());
        this.progressView = new FrameLayout(context);
        this.progressView.setVisibility(4);
        frameLayout.addView(this.progressView, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 0.0f, 0.0f, 52.0f));
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(0);
        this.listView.setLayoutManager(layoutManager);
        this.listView.setDisallowInterceptTouchEvents(true);
        this.listView.setOverScrollMode(2);
        RecyclerListView recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.listAdapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, 102, 83));
        this.listView.setOnItemClickListener(new C34544());
        processSelectedBackground();
        return this.fragmentView;
    }

    public void onActivityResultFragment(int requestCode, int resultCode, Intent data) {
        this.updater.onActivityResult(requestCode, resultCode, data);
    }

    public void saveSelfArgs(Bundle args) {
        String currentPicturePath = this.updater.getCurrentPicturePath();
        if (currentPicturePath != null) {
            args.putString("path", currentPicturePath);
        }
    }

    public void restoreSelfArgs(Bundle args) {
        this.updater.setCurrentPicturePath(args.getString("path"));
    }

    private void processSelectedBackground() {
        if (!Theme.hasWallpaperFromTheme() || this.overrideThemeWallpaper) {
            TLRPC$WallPaper wallPaper = (TLRPC$WallPaper) this.wallpappersByIds.get(Integer.valueOf(this.selectedBackground));
            if (this.selectedBackground == -1 || this.selectedBackground == 1000001 || wallPaper == null || !(wallPaper instanceof TLRPC$TL_wallPaper)) {
                if (this.loadingFile != null) {
                    FileLoader.getInstance().cancelLoadFile(this.loadingSize);
                }
                if (this.selectedBackground == 1000001) {
                    this.backgroundImage.setImageResource(R.drawable.background_hd);
                    this.backgroundImage.setBackgroundColor(0);
                    this.selectedColor = 0;
                } else if (this.selectedBackground == -1) {
                    File toFile;
                    if (this.wallpaperFile != null) {
                        toFile = this.wallpaperFile;
                    } else {
                        toFile = new File(ApplicationLoader.getFilesDirFixed(), "wallpaper.jpg");
                    }
                    if (toFile.exists()) {
                        this.backgroundImage.setImageURI(Uri.fromFile(toFile));
                    } else {
                        this.selectedBackground = 1000001;
                        this.overrideThemeWallpaper = true;
                        processSelectedBackground();
                    }
                } else if (wallPaper == null) {
                    return;
                } else {
                    if (wallPaper instanceof TLRPC$TL_wallPaperSolid) {
                        Drawable drawable = this.backgroundImage.getDrawable();
                        this.backgroundImage.setImageBitmap(null);
                        this.selectedColor = -16777216 | wallPaper.bg_color;
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
            int width = AndroidUtilities.displaySize.x;
            int height = AndroidUtilities.displaySize.y;
            if (width > height) {
                int temp = width;
                width = height;
                height = temp;
            }
            TLRPC$PhotoSize size = FileLoader.getClosestPhotoSizeWithSize(wallPaper.sizes, Math.min(width, height));
            if (size != null) {
                String fileName = size.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + size.location.local_id + ".jpg";
                File f = new File(FileLoader.getInstance().getDirectory(4), fileName);
                if (f.exists()) {
                    if (this.loadingFile != null) {
                        FileLoader.getInstance().cancelLoadFile(this.loadingSize);
                    }
                    this.loadingFileObject = null;
                    this.loadingFile = null;
                    this.loadingSize = null;
                    try {
                        this.backgroundImage.setImageURI(Uri.fromFile(f));
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                    this.backgroundImage.setBackgroundColor(0);
                    this.selectedColor = 0;
                    this.doneButton.setEnabled(true);
                    this.progressView.setVisibility(8);
                    return;
                }
                this.progressViewBackground.getBackground().setColorFilter(new PorterDuffColorFilter(AndroidUtilities.calcDrawableColor(this.backgroundImage.getDrawable())[0], Mode.MULTIPLY));
                this.loadingFile = fileName;
                this.loadingFileObject = f;
                this.doneButton.setEnabled(false);
                this.progressView.setVisibility(0);
                this.loadingSize = size;
                this.selectedColor = 0;
                FileLoader.getInstance().loadFile(size, null, 1);
                this.backgroundImage.setBackgroundColor(0);
                return;
            }
            return;
        }
        this.backgroundImage.setImageDrawable(Theme.getThemedWallpaper(false));
    }

    public void didReceivedNotification(int id, Object... args) {
        String location;
        if (id == NotificationCenter.FileDidFailedLoad) {
            location = args[0];
            if (this.loadingFile != null && this.loadingFile.equals(location)) {
                this.loadingFileObject = null;
                this.loadingFile = null;
                this.loadingSize = null;
                this.progressView.setVisibility(8);
                this.doneButton.setEnabled(false);
            }
        } else if (id == NotificationCenter.FileDidLoaded) {
            location = (String) args[0];
            if (this.loadingFile != null && this.loadingFile.equals(location)) {
                this.backgroundImage.setImageURI(Uri.fromFile(this.loadingFileObject));
                this.progressView.setVisibility(8);
                this.backgroundImage.setBackgroundColor(0);
                this.doneButton.setEnabled(true);
                this.loadingFileObject = null;
                this.loadingFile = null;
                this.loadingSize = null;
            }
        } else if (id == NotificationCenter.wallpapersDidLoaded) {
            this.wallPapers = (ArrayList) args[0];
            this.wallpappersByIds.clear();
            Iterator it = this.wallPapers.iterator();
            while (it.hasNext()) {
                TLRPC$WallPaper wallPaper = (TLRPC$WallPaper) it.next();
                this.wallpappersByIds.put(Integer.valueOf(wallPaper.id), wallPaper);
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

    private void loadWallpapers() {
        ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_account_getWallPapers(), new C34565()), this.classGuid);
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
        processSelectedBackground();
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
}
