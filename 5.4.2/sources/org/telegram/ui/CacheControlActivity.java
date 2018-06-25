package org.telegram.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import java.io.File;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLiteDatabase;
import org.telegram.SQLite.SQLitePreparedStatement;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ClearCacheService;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.query.BotQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.AbstractSerializedData;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet.BottomSheetCell;
import org.telegram.ui.ActionBar.BottomSheet.Builder;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.CheckBoxCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class CacheControlActivity extends BaseFragment {
    private long audioSize = -1;
    private int cacheInfoRow;
    private int cacheRow;
    private long cacheSize = -1;
    private boolean calculating = true;
    private volatile boolean canceled = false;
    private boolean[] clear = new boolean[6];
    private int databaseInfoRow;
    private int databaseRow;
    private long databaseSize = -1;
    private long documentsSize = -1;
    private int keepMediaInfoRow;
    private int keepMediaRow;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private long musicSize = -1;
    private long photoSize = -1;
    private int rowCount;
    private long totalSize = -1;
    private long videoSize = -1;

    /* renamed from: org.telegram.ui.CacheControlActivity$1 */
    class C39551 implements Runnable {

        /* renamed from: org.telegram.ui.CacheControlActivity$1$1 */
        class C39541 implements Runnable {
            C39541() {
            }

            public void run() {
                CacheControlActivity.this.calculating = false;
                if (CacheControlActivity.this.listAdapter != null) {
                    CacheControlActivity.this.listAdapter.notifyDataSetChanged();
                }
            }
        }

        C39551() {
        }

        public void run() {
            CacheControlActivity.this.cacheSize = CacheControlActivity.this.getDirectorySize(FileLoader.getInstance().checkDirectory(4), 0);
            if (!CacheControlActivity.this.canceled) {
                CacheControlActivity.this.photoSize = CacheControlActivity.this.getDirectorySize(FileLoader.getInstance().checkDirectory(0), 0);
                if (!CacheControlActivity.this.canceled) {
                    CacheControlActivity.this.videoSize = CacheControlActivity.this.getDirectorySize(FileLoader.getInstance().checkDirectory(2), 0);
                    if (!CacheControlActivity.this.canceled) {
                        CacheControlActivity.this.documentsSize = CacheControlActivity.this.getDirectorySize(FileLoader.getInstance().checkDirectory(3), 1);
                        if (!CacheControlActivity.this.canceled) {
                            CacheControlActivity.this.musicSize = CacheControlActivity.this.getDirectorySize(FileLoader.getInstance().checkDirectory(3), 2);
                            if (!CacheControlActivity.this.canceled) {
                                CacheControlActivity.this.audioSize = CacheControlActivity.this.getDirectorySize(FileLoader.getInstance().checkDirectory(1), 0);
                                CacheControlActivity.this.totalSize = ((((CacheControlActivity.this.cacheSize + CacheControlActivity.this.videoSize) + CacheControlActivity.this.audioSize) + CacheControlActivity.this.photoSize) + CacheControlActivity.this.documentsSize) + CacheControlActivity.this.musicSize;
                                AndroidUtilities.runOnUIThread(new C39541());
                            }
                        }
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.CacheControlActivity$3 */
    class C39583 extends ActionBarMenuOnItemClick {
        C39583() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                CacheControlActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.CacheControlActivity$4 */
    class C39654 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.CacheControlActivity$4$1 */
        class C39591 implements OnClickListener {
            C39591() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit();
                if (i == 0) {
                    edit.putInt("keep_media", 3).commit();
                } else if (i == 1) {
                    edit.putInt("keep_media", 0).commit();
                } else if (i == 2) {
                    edit.putInt("keep_media", 1).commit();
                } else if (i == 3) {
                    edit.putInt("keep_media", 2).commit();
                }
                if (CacheControlActivity.this.listAdapter != null) {
                    CacheControlActivity.this.listAdapter.notifyDataSetChanged();
                }
                PendingIntent service = PendingIntent.getService(ApplicationLoader.applicationContext, 0, new Intent(ApplicationLoader.applicationContext, ClearCacheService.class), 0);
                AlarmManager alarmManager = (AlarmManager) ApplicationLoader.applicationContext.getSystemService("alarm");
                if (i == 2) {
                    alarmManager.cancel(service);
                } else {
                    alarmManager.setInexactRepeating(2, 86400000, 86400000, service);
                }
            }
        }

        /* renamed from: org.telegram.ui.CacheControlActivity$4$2 */
        class C39622 implements OnClickListener {
            C39622() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                final AlertDialog alertDialog = new AlertDialog(CacheControlActivity.this.getParentActivity(), 1);
                alertDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setCancelable(false);
                alertDialog.show();
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {

                    /* renamed from: org.telegram.ui.CacheControlActivity$4$2$1$1 */
                    class C39601 implements Runnable {
                        C39601() {
                        }

                        public void run() {
                            try {
                                alertDialog.dismiss();
                            } catch (Throwable e) {
                                FileLog.e(e);
                            }
                            if (CacheControlActivity.this.listAdapter != null) {
                                CacheControlActivity.this.databaseSize = new File(ApplicationLoader.getFilesDirFixed(), "cache4.db").length();
                                CacheControlActivity.this.listAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    public void run() {
                        try {
                            SQLiteDatabase database = MessagesStorage.getInstance().getDatabase();
                            ArrayList arrayList = new ArrayList();
                            SQLiteCursor b = database.b("SELECT did FROM dialogs WHERE 1", new Object[0]);
                            StringBuilder stringBuilder = new StringBuilder();
                            while (b.a()) {
                                long d = b.d(0);
                                int i = (int) (d >> 32);
                                if (!(((int) d) == 0 || i == 1)) {
                                    arrayList.add(Long.valueOf(d));
                                }
                            }
                            b.b();
                            SQLitePreparedStatement a = database.a("REPLACE INTO messages_holes VALUES(?, ?, ?)");
                            SQLitePreparedStatement a2 = database.a("REPLACE INTO media_holes_v2 VALUES(?, ?, ?, ?)");
                            database.d();
                            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                Long l = (Long) arrayList.get(i2);
                                int i3 = 0;
                                SQLiteCursor b2 = database.b("SELECT COUNT(mid) FROM messages WHERE uid = " + l, new Object[0]);
                                if (b2.a()) {
                                    i3 = b2.b(0);
                                }
                                b2.b();
                                if (i3 > 2) {
                                    SQLiteCursor b3 = database.b("SELECT last_mid_i, last_mid FROM dialogs WHERE did = " + l, new Object[0]);
                                    i3 = -1;
                                    if (b3.a()) {
                                        long d2 = b3.d(0);
                                        long d3 = b3.d(1);
                                        SQLiteCursor b4 = database.b("SELECT data FROM messages WHERE uid = " + l + " AND mid IN (" + d2 + "," + d3 + ")", new Object[0]);
                                        while (b4.a()) {
                                            try {
                                                AbstractSerializedData g = b4.g(0);
                                                if (g != null) {
                                                    Message TLdeserialize = Message.TLdeserialize(g, g.readInt32(false), false);
                                                    g.reuse();
                                                    if (TLdeserialize != null) {
                                                        i3 = TLdeserialize.id;
                                                    }
                                                }
                                            } catch (Throwable e) {
                                                FileLog.e(e);
                                            }
                                        }
                                        b4.b();
                                        database.a("DELETE FROM messages WHERE uid = " + l + " AND mid != " + d2 + " AND mid != " + d3).c().e();
                                        database.a("DELETE FROM messages_holes WHERE uid = " + l).c().e();
                                        database.a("DELETE FROM bot_keyboard WHERE uid = " + l).c().e();
                                        database.a("DELETE FROM media_counts_v2 WHERE uid = " + l).c().e();
                                        database.a("DELETE FROM media_v2 WHERE uid = " + l).c().e();
                                        database.a("DELETE FROM media_holes_v2 WHERE uid = " + l).c().e();
                                        BotQuery.clearBotKeyboard(l.longValue(), null);
                                        if (i3 != -1) {
                                            MessagesStorage.createFirstHoles(l.longValue(), a, a2, i3);
                                        }
                                    }
                                    b3.b();
                                }
                            }
                            a.e();
                            a2.e();
                            database.e();
                            database.a("VACUUM").c().e();
                        } catch (Throwable e2) {
                            FileLog.e(e2);
                        } finally {
                            AndroidUtilities.runOnUIThread(new C39601());
                        }
                    }
                });
            }
        }

        /* renamed from: org.telegram.ui.CacheControlActivity$4$3 */
        class C39633 implements View.OnClickListener {
            C39633() {
            }

            public void onClick(View view) {
                CheckBoxCell checkBoxCell = (CheckBoxCell) view;
                int intValue = ((Integer) checkBoxCell.getTag()).intValue();
                CacheControlActivity.this.clear[intValue] = !CacheControlActivity.this.clear[intValue];
                checkBoxCell.setChecked(CacheControlActivity.this.clear[intValue], true);
            }
        }

        /* renamed from: org.telegram.ui.CacheControlActivity$4$4 */
        class C39644 implements View.OnClickListener {
            C39644() {
            }

            public void onClick(View view) {
                try {
                    if (CacheControlActivity.this.visibleDialog != null) {
                        CacheControlActivity.this.visibleDialog.dismiss();
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                CacheControlActivity.this.cleanupFolders();
            }
        }

        C39654() {
        }

        public void onItemClick(View view, int i) {
            if (CacheControlActivity.this.getParentActivity() != null) {
                if (i == CacheControlActivity.this.keepMediaRow) {
                    Builder builder = new Builder(CacheControlActivity.this.getParentActivity());
                    builder.setItems(new CharSequence[]{LocaleController.formatPluralString("Days", 3), LocaleController.formatPluralString("Weeks", 1), LocaleController.formatPluralString("Months", 1), LocaleController.getString("KeepMediaForever", R.string.KeepMediaForever)}, new C39591());
                    CacheControlActivity.this.showDialog(builder.create());
                } else if (i == CacheControlActivity.this.databaseRow) {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(CacheControlActivity.this.getParentActivity());
                    builder2.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder2.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    builder2.setMessage(LocaleController.getString("LocalDatabaseClear", R.string.LocalDatabaseClear));
                    builder2.setPositiveButton(LocaleController.getString("CacheClear", R.string.CacheClear), new C39622());
                    CacheControlActivity.this.showDialog(builder2.create());
                } else if (i == CacheControlActivity.this.cacheRow && CacheControlActivity.this.totalSize > 0 && CacheControlActivity.this.getParentActivity() != null) {
                    Builder builder3 = new Builder(CacheControlActivity.this.getParentActivity());
                    builder3.setApplyTopPadding(false);
                    builder3.setApplyBottomPadding(false);
                    View linearLayout = new LinearLayout(CacheControlActivity.this.getParentActivity());
                    linearLayout.setOrientation(1);
                    for (int i2 = 0; i2 < 6; i2++) {
                        long j = 0;
                        String str = null;
                        if (i2 == 0) {
                            j = CacheControlActivity.this.photoSize;
                            str = LocaleController.getString("LocalPhotoCache", R.string.LocalPhotoCache);
                        } else if (i2 == 1) {
                            j = CacheControlActivity.this.videoSize;
                            str = LocaleController.getString("LocalVideoCache", R.string.LocalVideoCache);
                        } else if (i2 == 2) {
                            j = CacheControlActivity.this.documentsSize;
                            str = LocaleController.getString("LocalDocumentCache", R.string.LocalDocumentCache);
                        } else if (i2 == 3) {
                            j = CacheControlActivity.this.musicSize;
                            str = LocaleController.getString("LocalMusicCache", R.string.LocalMusicCache);
                        } else if (i2 == 4) {
                            j = CacheControlActivity.this.audioSize;
                            str = LocaleController.getString("LocalAudioCache", R.string.LocalAudioCache);
                        } else if (i2 == 5) {
                            j = CacheControlActivity.this.cacheSize;
                            str = LocaleController.getString("LocalCache", R.string.LocalCache);
                        }
                        if (j > 0) {
                            CacheControlActivity.this.clear[i2] = true;
                            View checkBoxCell = new CheckBoxCell(CacheControlActivity.this.getParentActivity(), true);
                            checkBoxCell.setTag(Integer.valueOf(i2));
                            checkBoxCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                            linearLayout.addView(checkBoxCell, LayoutHelper.createLinear(-1, 48));
                            checkBoxCell.setText(str, AndroidUtilities.formatFileSize(j), true, true);
                            checkBoxCell.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                            checkBoxCell.setOnClickListener(new C39633());
                        } else {
                            CacheControlActivity.this.clear[i2] = false;
                        }
                    }
                    View bottomSheetCell = new BottomSheetCell(CacheControlActivity.this.getParentActivity(), 1);
                    bottomSheetCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                    bottomSheetCell.setTextAndIcon(LocaleController.getString("ClearMediaCache", R.string.ClearMediaCache).toUpperCase(), 0);
                    bottomSheetCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText));
                    bottomSheetCell.setOnClickListener(new C39644());
                    linearLayout.addView(bottomSheetCell, LayoutHelper.createLinear(-1, 48));
                    builder3.setCustomView(linearLayout);
                    CacheControlActivity.this.showDialog(builder3.create());
                }
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return CacheControlActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return (i == CacheControlActivity.this.databaseInfoRow || i == CacheControlActivity.this.cacheInfoRow || i == CacheControlActivity.this.keepMediaInfoRow) ? 1 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return adapterPosition == CacheControlActivity.this.databaseRow || ((adapterPosition == CacheControlActivity.this.cacheRow && CacheControlActivity.this.totalSize > 0) || adapterPosition == CacheControlActivity.this.keepMediaRow);
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            switch (viewHolder.getItemViewType()) {
                case 0:
                    TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                    if (i == CacheControlActivity.this.databaseRow) {
                        textSettingsCell.setTextAndValue(LocaleController.getString("LocalDatabase", R.string.LocalDatabase), AndroidUtilities.formatFileSize(CacheControlActivity.this.databaseSize), false);
                        return;
                    } else if (i == CacheControlActivity.this.cacheRow) {
                        if (CacheControlActivity.this.calculating) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("ClearMediaCache", R.string.ClearMediaCache), LocaleController.getString("CalculatingSize", R.string.CalculatingSize), false);
                            return;
                        } else {
                            textSettingsCell.setTextAndValue(LocaleController.getString("ClearMediaCache", R.string.ClearMediaCache), CacheControlActivity.this.totalSize == 0 ? LocaleController.getString("CacheEmpty", R.string.CacheEmpty) : AndroidUtilities.formatFileSize(CacheControlActivity.this.totalSize), false);
                            return;
                        }
                    } else if (i == CacheControlActivity.this.keepMediaRow) {
                        int i2 = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getInt("keep_media", 2);
                        String formatPluralString = i2 == 0 ? LocaleController.formatPluralString("Weeks", 1) : i2 == 1 ? LocaleController.formatPluralString("Months", 1) : i2 == 3 ? LocaleController.formatPluralString("Days", 3) : LocaleController.getString("KeepMediaForever", R.string.KeepMediaForever);
                        textSettingsCell.setTextAndValue(LocaleController.getString("KeepMedia", R.string.KeepMedia), formatPluralString, false);
                        return;
                    } else {
                        return;
                    }
                case 1:
                    TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                    if (i == CacheControlActivity.this.databaseInfoRow) {
                        textInfoPrivacyCell.setText(LocaleController.getString("LocalDatabaseInfo", R.string.LocalDatabaseInfo));
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == CacheControlActivity.this.cacheInfoRow) {
                        textInfoPrivacyCell.setText(TtmlNode.ANONYMOUS_REGION_ID);
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == CacheControlActivity.this.keepMediaInfoRow) {
                        textInfoPrivacyCell.setText(AndroidUtilities.replaceTags(LocaleController.getString("KeepMediaInfo", R.string.KeepMediaInfo)));
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View textSettingsCell;
            switch (i) {
                case 0:
                    textSettingsCell = new TextSettingsCell(this.mContext);
                    textSettingsCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                default:
                    textSettingsCell = new TextInfoPrivacyCell(this.mContext);
                    break;
            }
            return new Holder(textSettingsCell);
        }
    }

    private void cleanupFolders() {
        final AlertDialog alertDialog = new AlertDialog(getParentActivity(), 1);
        alertDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
        Utilities.globalQueue.postRunnable(new Runnable() {
            public void run() {
                boolean z = false;
                for (int i = 0; i < 6; i++) {
                    if (CacheControlActivity.this.clear[i]) {
                        int i2;
                        int i3;
                        if (i == 0) {
                            i2 = 0;
                            i3 = 0;
                        } else if (i == 1) {
                            i2 = 0;
                            i3 = 2;
                        } else if (i == 2) {
                            i2 = 1;
                            i3 = 3;
                        } else if (i == 3) {
                            i2 = 2;
                            i3 = 3;
                        } else if (i == 4) {
                            i2 = 0;
                            i3 = 1;
                        } else if (i == 5) {
                            i2 = 0;
                            i3 = 4;
                        } else {
                            i2 = 0;
                            i3 = -1;
                        }
                        if (i3 != -1) {
                            File checkDirectory = FileLoader.getInstance().checkDirectory(i3);
                            if (checkDirectory != null) {
                                try {
                                    File[] listFiles = checkDirectory.listFiles();
                                    if (listFiles != null) {
                                        int i4 = 0;
                                        while (i4 < listFiles.length) {
                                            String toLowerCase = listFiles[i4].getName().toLowerCase();
                                            if (i2 == 1 || i2 == 2) {
                                                if (toLowerCase.endsWith(".mp3") || toLowerCase.endsWith(".m4a")) {
                                                    if (i2 == 1) {
                                                        i4++;
                                                    }
                                                } else if (i2 == 2) {
                                                    i4++;
                                                }
                                            }
                                            if (!toLowerCase.equals(".nomedia") && listFiles[i4].isFile()) {
                                                listFiles[i4].delete();
                                            }
                                            i4++;
                                        }
                                    }
                                } catch (Throwable th) {
                                    FileLog.e(th);
                                }
                            }
                            if (i3 == 4) {
                                CacheControlActivity.this.cacheSize = CacheControlActivity.this.getDirectorySize(FileLoader.getInstance().checkDirectory(4), i2);
                                z = true;
                            } else if (i3 == 1) {
                                CacheControlActivity.this.audioSize = CacheControlActivity.this.getDirectorySize(FileLoader.getInstance().checkDirectory(1), i2);
                            } else if (i3 == 3) {
                                if (i2 == 1) {
                                    CacheControlActivity.this.documentsSize = CacheControlActivity.this.getDirectorySize(FileLoader.getInstance().checkDirectory(3), i2);
                                } else {
                                    CacheControlActivity.this.musicSize = CacheControlActivity.this.getDirectorySize(FileLoader.getInstance().checkDirectory(3), i2);
                                }
                            } else if (i3 == 0) {
                                CacheControlActivity.this.photoSize = CacheControlActivity.this.getDirectorySize(FileLoader.getInstance().checkDirectory(0), i2);
                                z = true;
                            } else if (i3 == 2) {
                                CacheControlActivity.this.videoSize = CacheControlActivity.this.getDirectorySize(FileLoader.getInstance().checkDirectory(2), i2);
                            }
                        }
                    }
                }
                CacheControlActivity.this.totalSize = ((((CacheControlActivity.this.cacheSize + CacheControlActivity.this.videoSize) + CacheControlActivity.this.audioSize) + CacheControlActivity.this.photoSize) + CacheControlActivity.this.documentsSize) + CacheControlActivity.this.musicSize;
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        if (z) {
                            ImageLoader.getInstance().clearMemory();
                        }
                        if (CacheControlActivity.this.listAdapter != null) {
                            CacheControlActivity.this.listAdapter.notifyDataSetChanged();
                        }
                        try {
                            alertDialog.dismiss();
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    }
                });
            }
        });
    }

    private long getDirectorySize(File file, int i) {
        Throwable th;
        if (file == null || this.canceled) {
            return 0;
        }
        long j;
        if (file.isDirectory()) {
            try {
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    int i2 = 0;
                    j = 0;
                    while (i2 < listFiles.length) {
                        try {
                            if (this.canceled) {
                                return 0;
                            }
                            File file2 = listFiles[i2];
                            if (i == 1 || i == 2) {
                                String toLowerCase = file2.getName().toLowerCase();
                                if (toLowerCase.endsWith(".mp3") || toLowerCase.endsWith(".m4a")) {
                                    if (i == 1) {
                                        i2++;
                                    }
                                } else if (i == 2) {
                                    i2++;
                                }
                            }
                            j = file2.isDirectory() ? j + getDirectorySize(file2, i) : j + file2.length();
                            i2++;
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                }
                j = 0;
            } catch (Throwable th3) {
                Throwable th4 = th3;
                j = 0;
                th = th4;
                FileLog.e(th);
                return j;
            }
        }
        if (file.isFile()) {
            j = file.length() + 0;
        }
        j = 0;
        return j;
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("StorageUsage", R.string.StorageUsage));
        this.actionBar.setActionBarMenuOnItemClick(new C39583());
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.listView = new RecyclerListView(context);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C39654());
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        r9 = new ThemeDescription[12];
        r9[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextSettingsCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        r9[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        r9[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        r9[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        r9[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        r9[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        r9[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        r9[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r9[8] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[9] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        r9[10] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r9[11] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        return r9;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        this.rowCount = 0;
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.keepMediaRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.keepMediaInfoRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.cacheRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.cacheInfoRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.databaseRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.databaseInfoRow = i;
        this.databaseSize = new File(ApplicationLoader.getFilesDirFixed(), "cache4.db").length();
        Utilities.globalQueue.postRunnable(new C39551());
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        this.canceled = true;
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }
}
