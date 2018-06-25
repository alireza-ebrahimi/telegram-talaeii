package org.telegram.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Environment;
import android.os.StatFs;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.FrameLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BackDrawable;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.GraySectionCell;
import org.telegram.ui.Cells.SharedDocumentCell;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.NumberTextView;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class DocumentSelectActivity extends BaseFragment {
    private static final int done = 3;
    private ArrayList<View> actionModeViews = new ArrayList();
    private File currentDir;
    private DocumentSelectActivityDelegate delegate;
    private EmptyTextProgressView emptyView;
    public String fileFilter = "*";
    private ArrayList<HistoryEntry> history = new ArrayList();
    private ArrayList<ListItem> items = new ArrayList();
    private LinearLayoutManager layoutManager;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private BroadcastReceiver receiver = new C47341();
    private boolean receiverRegistered = false;
    private ArrayList<ListItem> recentItems = new ArrayList();
    private boolean scrolling;
    private HashMap<String, ListItem> selectedFiles = new HashMap();
    private NumberTextView selectedMessagesCountTextView;
    private long sizeLimit = 1610612736;

    public interface DocumentSelectActivityDelegate {
        void didSelectFiles(DocumentSelectActivity documentSelectActivity, ArrayList<String> arrayList);

        void startDocumentSelectActivity();
    }

    /* renamed from: org.telegram.ui.DocumentSelectActivity$1 */
    class C47341 extends BroadcastReceiver {

        /* renamed from: org.telegram.ui.DocumentSelectActivity$1$1 */
        class C47331 implements Runnable {
            C47331() {
            }

            public void run() {
                try {
                    if (DocumentSelectActivity.this.currentDir == null) {
                        DocumentSelectActivity.this.listRoots();
                    } else {
                        DocumentSelectActivity.this.listFiles(DocumentSelectActivity.this.currentDir);
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
        }

        C47341() {
        }

        public void onReceive(Context context, Intent intent) {
            Runnable c47331 = new C47331();
            if ("android.intent.action.MEDIA_UNMOUNTED".equals(intent.getAction())) {
                DocumentSelectActivity.this.listView.postDelayed(c47331, 1000);
            } else {
                c47331.run();
            }
        }
    }

    /* renamed from: org.telegram.ui.DocumentSelectActivity$2 */
    class C47352 extends ActionBarMenuOnItemClick {
        C47352() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                if (DocumentSelectActivity.this.actionBar.isActionModeShowed()) {
                    DocumentSelectActivity.this.selectedFiles.clear();
                    DocumentSelectActivity.this.actionBar.hideActionMode();
                    int childCount = DocumentSelectActivity.this.listView.getChildCount();
                    for (int i2 = 0; i2 < childCount; i2++) {
                        View childAt = DocumentSelectActivity.this.listView.getChildAt(i2);
                        if (childAt instanceof SharedDocumentCell) {
                            ((SharedDocumentCell) childAt).setChecked(false, true);
                        }
                    }
                    return;
                }
                DocumentSelectActivity.this.finishFragment();
            } else if (i == 3 && DocumentSelectActivity.this.delegate != null) {
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(DocumentSelectActivity.this.selectedFiles.keySet());
                DocumentSelectActivity.this.delegate.didSelectFiles(DocumentSelectActivity.this, arrayList);
                for (ListItem listItem : DocumentSelectActivity.this.selectedFiles.values()) {
                    listItem.date = System.currentTimeMillis();
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.DocumentSelectActivity$3 */
    class C47363 implements OnTouchListener {
        C47363() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.DocumentSelectActivity$4 */
    class C47374 extends OnScrollListener {
        C47374() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            DocumentSelectActivity.this.scrolling = i != 0;
        }
    }

    /* renamed from: org.telegram.ui.DocumentSelectActivity$5 */
    class C47385 implements OnItemLongClickListener {
        C47385() {
        }

        public boolean onItemClick(View view, int i) {
            if (DocumentSelectActivity.this.actionBar.isActionModeShowed()) {
                return false;
            }
            ListItem item = DocumentSelectActivity.this.listAdapter.getItem(i);
            if (item == null) {
                return false;
            }
            File file = item.file;
            if (!(file == null || file.isDirectory())) {
                if (!file.canRead()) {
                    DocumentSelectActivity.this.showErrorBox(LocaleController.getString("AccessError", R.string.AccessError));
                    return false;
                } else if (DocumentSelectActivity.this.sizeLimit != 0 && file.length() > DocumentSelectActivity.this.sizeLimit) {
                    DocumentSelectActivity.this.showErrorBox(LocaleController.formatString("FileUploadLimit", R.string.FileUploadLimit, new Object[]{AndroidUtilities.formatFileSize(DocumentSelectActivity.this.sizeLimit)}));
                    return false;
                } else if (file.length() == 0) {
                    return false;
                } else {
                    DocumentSelectActivity.this.selectedFiles.put(file.toString(), item);
                    DocumentSelectActivity.this.selectedMessagesCountTextView.setNumber(1, false);
                    AnimatorSet animatorSet = new AnimatorSet();
                    Collection arrayList = new ArrayList();
                    for (int i2 = 0; i2 < DocumentSelectActivity.this.actionModeViews.size(); i2++) {
                        View view2 = (View) DocumentSelectActivity.this.actionModeViews.get(i2);
                        AndroidUtilities.clearDrawableAnimation(view2);
                        arrayList.add(ObjectAnimator.ofFloat(view2, "scaleY", new float[]{0.1f, 1.0f}));
                    }
                    animatorSet.playTogether(arrayList);
                    animatorSet.setDuration(250);
                    animatorSet.start();
                    DocumentSelectActivity.this.scrolling = false;
                    if (view instanceof SharedDocumentCell) {
                        ((SharedDocumentCell) view).setChecked(true, true);
                    }
                    DocumentSelectActivity.this.actionBar.showActionMode();
                }
            }
            return true;
        }
    }

    /* renamed from: org.telegram.ui.DocumentSelectActivity$6 */
    class C47396 implements OnItemClickListener {
        C47396() {
        }

        public void onItemClick(View view, int i) {
            ListItem item = DocumentSelectActivity.this.listAdapter.getItem(i);
            if (item != null) {
                File file = item.file;
                if (file == null) {
                    if (item.icon == R.drawable.ic_storage_gallery) {
                        if (DocumentSelectActivity.this.delegate != null) {
                            DocumentSelectActivity.this.delegate.startDocumentSelectActivity();
                        }
                        DocumentSelectActivity.this.finishFragment(false);
                        return;
                    }
                    HistoryEntry historyEntry = (HistoryEntry) DocumentSelectActivity.this.history.remove(DocumentSelectActivity.this.history.size() - 1);
                    DocumentSelectActivity.this.actionBar.setTitle(historyEntry.title);
                    if (historyEntry.dir != null) {
                        DocumentSelectActivity.this.listFiles(historyEntry.dir);
                    } else {
                        DocumentSelectActivity.this.listRoots();
                    }
                    DocumentSelectActivity.this.layoutManager.scrollToPositionWithOffset(historyEntry.scrollItem, historyEntry.scrollOffset);
                } else if (file.isDirectory()) {
                    HistoryEntry historyEntry2 = new HistoryEntry();
                    historyEntry2.scrollItem = DocumentSelectActivity.this.layoutManager.findLastVisibleItemPosition();
                    View findViewByPosition = DocumentSelectActivity.this.layoutManager.findViewByPosition(historyEntry2.scrollItem);
                    if (findViewByPosition != null) {
                        historyEntry2.scrollOffset = findViewByPosition.getTop();
                    }
                    historyEntry2.dir = DocumentSelectActivity.this.currentDir;
                    historyEntry2.title = DocumentSelectActivity.this.actionBar.getTitle();
                    DocumentSelectActivity.this.history.add(historyEntry2);
                    if (DocumentSelectActivity.this.listFiles(file)) {
                        DocumentSelectActivity.this.actionBar.setTitle(item.title);
                    } else {
                        DocumentSelectActivity.this.history.remove(historyEntry2);
                    }
                } else {
                    if (!file.canRead()) {
                        DocumentSelectActivity.this.showErrorBox(LocaleController.getString("AccessError", R.string.AccessError));
                        file = new File("/mnt/sdcard");
                    }
                    if (DocumentSelectActivity.this.sizeLimit != 0 && file.length() > DocumentSelectActivity.this.sizeLimit) {
                        DocumentSelectActivity.this.showErrorBox(LocaleController.formatString("FileUploadLimit", R.string.FileUploadLimit, new Object[]{AndroidUtilities.formatFileSize(DocumentSelectActivity.this.sizeLimit)}));
                    } else if (file.length() == 0) {
                    } else {
                        if (DocumentSelectActivity.this.actionBar.isActionModeShowed()) {
                            if (DocumentSelectActivity.this.selectedFiles.containsKey(file.toString())) {
                                DocumentSelectActivity.this.selectedFiles.remove(file.toString());
                            } else {
                                DocumentSelectActivity.this.selectedFiles.put(file.toString(), item);
                            }
                            if (DocumentSelectActivity.this.selectedFiles.isEmpty()) {
                                DocumentSelectActivity.this.actionBar.hideActionMode();
                            } else {
                                DocumentSelectActivity.this.selectedMessagesCountTextView.setNumber(DocumentSelectActivity.this.selectedFiles.size(), true);
                            }
                            DocumentSelectActivity.this.scrolling = false;
                            if (view instanceof SharedDocumentCell) {
                                ((SharedDocumentCell) view).setChecked(DocumentSelectActivity.this.selectedFiles.containsKey(item.file.toString()), true);
                            }
                        } else if (DocumentSelectActivity.this.delegate != null) {
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(file.getAbsolutePath());
                            DocumentSelectActivity.this.delegate.didSelectFiles(DocumentSelectActivity.this, arrayList);
                        }
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.DocumentSelectActivity$7 */
    class C47407 implements Comparator<ListItem> {
        C47407() {
        }

        public int compare(ListItem listItem, ListItem listItem2) {
            long lastModified = listItem.file.lastModified();
            long lastModified2 = listItem2.file.lastModified();
            return lastModified == lastModified2 ? 0 : lastModified > lastModified2 ? -1 : 1;
        }
    }

    /* renamed from: org.telegram.ui.DocumentSelectActivity$8 */
    class C47418 implements OnPreDrawListener {
        C47418() {
        }

        public boolean onPreDraw() {
            DocumentSelectActivity.this.listView.getViewTreeObserver().removeOnPreDrawListener(this);
            DocumentSelectActivity.this.fixLayoutInternal();
            return true;
        }
    }

    /* renamed from: org.telegram.ui.DocumentSelectActivity$9 */
    class C47429 implements Comparator<File> {
        C47429() {
        }

        public int compare(File file, File file2) {
            return file.isDirectory() != file2.isDirectory() ? file.isDirectory() ? -1 : 1 : file.getName().compareToIgnoreCase(file2.getName());
        }
    }

    private class HistoryEntry {
        File dir;
        int scrollItem;
        int scrollOffset;
        String title;

        private HistoryEntry() {
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public ListItem getItem(int i) {
            if (i < DocumentSelectActivity.this.items.size()) {
                return (ListItem) DocumentSelectActivity.this.items.get(i);
            }
            if (!(!DocumentSelectActivity.this.history.isEmpty() || DocumentSelectActivity.this.recentItems.isEmpty() || i == DocumentSelectActivity.this.items.size())) {
                int size = i - (DocumentSelectActivity.this.items.size() + 1);
                if (size < DocumentSelectActivity.this.recentItems.size()) {
                    return (ListItem) DocumentSelectActivity.this.recentItems.get(size);
                }
            }
            return null;
        }

        public int getItemCount() {
            int size = DocumentSelectActivity.this.items.size();
            return (!DocumentSelectActivity.this.history.isEmpty() || DocumentSelectActivity.this.recentItems.isEmpty()) ? size : size + (DocumentSelectActivity.this.recentItems.size() + 1);
        }

        public int getItemViewType(int i) {
            return getItem(i) != null ? 1 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return viewHolder.getItemViewType() != 0;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = true;
            if (viewHolder.getItemViewType() == 1) {
                ListItem item = getItem(i);
                SharedDocumentCell sharedDocumentCell = (SharedDocumentCell) viewHolder.itemView;
                if (item.icon != 0) {
                    sharedDocumentCell.setTextAndValueAndTypeAndThumb(item.title, item.subtitle, null, null, item.icon);
                } else {
                    sharedDocumentCell.setTextAndValueAndTypeAndThumb(item.title, item.subtitle, item.ext.toUpperCase().substring(0, Math.min(item.ext.length(), 4)), item.thumb, 0);
                }
                if (item.file == null || !DocumentSelectActivity.this.actionBar.isActionModeShowed()) {
                    if (DocumentSelectActivity.this.scrolling) {
                        z = false;
                    }
                    sharedDocumentCell.setChecked(false, z);
                    return;
                }
                sharedDocumentCell.setChecked(DocumentSelectActivity.this.selectedFiles.containsKey(item.file.toString()), !DocumentSelectActivity.this.scrolling);
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View graySectionCell;
            switch (i) {
                case 0:
                    graySectionCell = new GraySectionCell(this.mContext);
                    ((GraySectionCell) graySectionCell).setText(LocaleController.getString("Recent", R.string.Recent).toUpperCase());
                    break;
                default:
                    graySectionCell = new SharedDocumentCell(this.mContext);
                    break;
            }
            return new Holder(graySectionCell);
        }
    }

    private class ListItem {
        long date;
        String ext;
        File file;
        int icon;
        String subtitle;
        String thumb;
        String title;

        private ListItem() {
            this.subtitle = TtmlNode.ANONYMOUS_REGION_ID;
            this.ext = TtmlNode.ANONYMOUS_REGION_ID;
        }
    }

    private void fixLayoutInternal() {
        if (this.selectedMessagesCountTextView != null) {
            if (AndroidUtilities.isTablet() || ApplicationLoader.applicationContext.getResources().getConfiguration().orientation != 2) {
                this.selectedMessagesCountTextView.setTextSize(20);
            } else {
                this.selectedMessagesCountTextView.setTextSize(18);
            }
        }
    }

    private String getRootSubtitle(String str) {
        try {
            StatFs statFs = new StatFs(str);
            long blockCount = ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
            long blockSize = ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
            if (blockCount == 0) {
                return TtmlNode.ANONYMOUS_REGION_ID;
            }
            return LocaleController.formatString("FreeOfTotal", R.string.FreeOfTotal, new Object[]{AndroidUtilities.formatFileSize(blockSize), AndroidUtilities.formatFileSize(blockCount)});
        } catch (Throwable e) {
            FileLog.e(e);
            return str;
        }
    }

    private boolean listFiles(File file) {
        if (file.canRead()) {
            try {
                File[] listFiles = file.listFiles();
                if (listFiles == null) {
                    showErrorBox(LocaleController.getString("UnknownError", R.string.UnknownError));
                    return false;
                }
                this.currentDir = file;
                this.items.clear();
                Arrays.sort(listFiles, new C47429());
                for (File file2 : listFiles) {
                    if (file2.getName().indexOf(46) != 0) {
                        ListItem listItem = new ListItem();
                        listItem.title = file2.getName();
                        listItem.file = file2;
                        if (file2.isDirectory()) {
                            listItem.icon = R.drawable.ic_directory;
                            listItem.subtitle = LocaleController.getString("Folder", R.string.Folder);
                        } else {
                            String name = file2.getName();
                            String[] split = name.split("\\.");
                            listItem.ext = split.length > 1 ? split[split.length - 1] : "?";
                            listItem.subtitle = AndroidUtilities.formatFileSize(file2.length());
                            String toLowerCase = name.toLowerCase();
                            if (toLowerCase.endsWith(".jpg") || toLowerCase.endsWith(".png") || toLowerCase.endsWith(".gif") || toLowerCase.endsWith(".jpeg")) {
                                listItem.thumb = file2.getAbsolutePath();
                            }
                        }
                        this.items.add(listItem);
                    }
                }
                ListItem listItem2 = new ListItem();
                listItem2.title = "..";
                if (this.history.size() > 0) {
                    HistoryEntry historyEntry = (HistoryEntry) this.history.get(this.history.size() - 1);
                    if (historyEntry.dir == null) {
                        listItem2.subtitle = LocaleController.getString("Folder", R.string.Folder);
                    } else {
                        listItem2.subtitle = historyEntry.dir.toString();
                    }
                } else {
                    listItem2.subtitle = LocaleController.getString("Folder", R.string.Folder);
                }
                listItem2.icon = R.drawable.ic_directory;
                listItem2.file = null;
                this.items.add(0, listItem2);
                AndroidUtilities.clearDrawableAnimation(this.listView);
                this.scrolling = true;
                this.listAdapter.notifyDataSetChanged();
                return true;
            } catch (Exception e) {
                showErrorBox(e.getLocalizedMessage());
                return false;
            }
        } else if ((!file.getAbsolutePath().startsWith(Environment.getExternalStorageDirectory().toString()) && !file.getAbsolutePath().startsWith("/sdcard") && !file.getAbsolutePath().startsWith("/mnt/sdcard")) || Environment.getExternalStorageState().equals("mounted") || Environment.getExternalStorageState().equals("mounted_ro")) {
            showErrorBox(LocaleController.getString("AccessError", R.string.AccessError));
            return false;
        } else {
            this.currentDir = file;
            this.items.clear();
            if ("shared".equals(Environment.getExternalStorageState())) {
                this.emptyView.setText(LocaleController.getString("UsbActive", R.string.UsbActive));
            } else {
                this.emptyView.setText(LocaleController.getString("NotMounted", R.string.NotMounted));
            }
            AndroidUtilities.clearDrawableAnimation(this.listView);
            this.scrolling = true;
            this.listAdapter.notifyDataSetChanged();
            return true;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.annotation.SuppressLint({"NewApi"})
    private void listRoots() {
        /*
        r8 = this;
        r6 = 2131232311; // 0x7f080637 float:1.8080728E38 double:1.052968668E-314;
        r5 = 2130837916; // 0x7f02019c float:1.72808E38 double:1.052773811E-314;
        r7 = 2130837913; // 0x7f020199 float:1.7280793E38 double:1.0527738097E-314;
        r2 = 0;
        r8.currentDir = r2;
        r0 = r8.items;
        r0.clear();
        r4 = new java.util.HashSet;
        r4.<init>();
        r0 = android.os.Environment.getExternalStorageDirectory();
        r0 = r0.getPath();
        android.os.Environment.isExternalStorageRemovable();
        r1 = android.os.Environment.getExternalStorageState();
        r3 = "mounted";
        r3 = r1.equals(r3);
        if (r3 != 0) goto L_0x0037;
    L_0x002e:
        r3 = "mounted_ro";
        r1 = r1.equals(r3);
        if (r1 == 0) goto L_0x0061;
    L_0x0037:
        r1 = new org.telegram.ui.DocumentSelectActivity$ListItem;
        r1.<init>();
        r3 = android.os.Environment.isExternalStorageRemovable();
        if (r3 == 0) goto L_0x01e4;
    L_0x0042:
        r3 = "SdCard";
        r3 = org.telegram.messenger.LocaleController.getString(r3, r6);
        r1.title = r3;
        r1.icon = r5;
    L_0x004d:
        r3 = r8.getRootSubtitle(r0);
        r1.subtitle = r3;
        r3 = android.os.Environment.getExternalStorageDirectory();
        r1.file = r3;
        r3 = r8.items;
        r3.add(r1);
        r4.add(r0);
    L_0x0061:
        r1 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x022c, all -> 0x0229 }
        r0 = new java.io.FileReader;	 Catch:{ Exception -> 0x022c, all -> 0x0229 }
        r3 = "/proc/mounts";
        r0.<init>(r3);	 Catch:{ Exception -> 0x022c, all -> 0x0229 }
        r1.<init>(r0);	 Catch:{ Exception -> 0x022c, all -> 0x0229 }
    L_0x006e:
        r0 = r1.readLine();	 Catch:{ Exception -> 0x014e }
        if (r0 == 0) goto L_0x020c;
    L_0x0074:
        r3 = "vfat";
        r3 = r0.contains(r3);	 Catch:{ Exception -> 0x014e }
        if (r3 != 0) goto L_0x0086;
    L_0x007d:
        r3 = "/mnt";
        r3 = r0.contains(r3);	 Catch:{ Exception -> 0x014e }
        if (r3 == 0) goto L_0x006e;
    L_0x0086:
        org.telegram.messenger.FileLog.e(r0);	 Catch:{ Exception -> 0x014e }
        r3 = new java.util.StringTokenizer;	 Catch:{ Exception -> 0x014e }
        r5 = " ";
        r3.<init>(r0, r5);	 Catch:{ Exception -> 0x014e }
        r3.nextToken();	 Catch:{ Exception -> 0x014e }
        r3 = r3.nextToken();	 Catch:{ Exception -> 0x014e }
        r5 = r4.contains(r3);	 Catch:{ Exception -> 0x014e }
        if (r5 != 0) goto L_0x006e;
    L_0x009e:
        r5 = "/dev/block/vold";
        r5 = r0.contains(r5);	 Catch:{ Exception -> 0x014e }
        if (r5 == 0) goto L_0x006e;
    L_0x00a7:
        r5 = "/mnt/secure";
        r5 = r0.contains(r5);	 Catch:{ Exception -> 0x014e }
        if (r5 != 0) goto L_0x006e;
    L_0x00b0:
        r5 = "/mnt/asec";
        r5 = r0.contains(r5);	 Catch:{ Exception -> 0x014e }
        if (r5 != 0) goto L_0x006e;
    L_0x00b9:
        r5 = "/mnt/obb";
        r5 = r0.contains(r5);	 Catch:{ Exception -> 0x014e }
        if (r5 != 0) goto L_0x006e;
    L_0x00c2:
        r5 = "/dev/mapper";
        r5 = r0.contains(r5);	 Catch:{ Exception -> 0x014e }
        if (r5 != 0) goto L_0x006e;
    L_0x00cb:
        r5 = "tmpfs";
        r0 = r0.contains(r5);	 Catch:{ Exception -> 0x014e }
        if (r0 != 0) goto L_0x006e;
    L_0x00d4:
        r0 = new java.io.File;	 Catch:{ Exception -> 0x014e }
        r0.<init>(r3);	 Catch:{ Exception -> 0x014e }
        r0 = r0.isDirectory();	 Catch:{ Exception -> 0x014e }
        if (r0 != 0) goto L_0x0230;
    L_0x00df:
        r0 = 47;
        r0 = r3.lastIndexOf(r0);	 Catch:{ Exception -> 0x014e }
        r5 = -1;
        if (r0 == r5) goto L_0x0230;
    L_0x00e8:
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x014e }
        r5.<init>();	 Catch:{ Exception -> 0x014e }
        r6 = "/storage/";
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x014e }
        r0 = r0 + 1;
        r0 = r3.substring(r0);	 Catch:{ Exception -> 0x014e }
        r0 = r5.append(r0);	 Catch:{ Exception -> 0x014e }
        r0 = r0.toString();	 Catch:{ Exception -> 0x014e }
        r5 = new java.io.File;	 Catch:{ Exception -> 0x014e }
        r5.<init>(r0);	 Catch:{ Exception -> 0x014e }
        r5 = r5.isDirectory();	 Catch:{ Exception -> 0x014e }
        if (r5 == 0) goto L_0x0230;
    L_0x010d:
        r4.add(r0);	 Catch:{ Exception -> 0x014e }
        r3 = new org.telegram.ui.DocumentSelectActivity$ListItem;	 Catch:{ Exception -> 0x0148 }
        r5 = 0;
        r3.<init>();	 Catch:{ Exception -> 0x0148 }
        r5 = r0.toLowerCase();	 Catch:{ Exception -> 0x0148 }
        r6 = "sd";
        r5 = r5.contains(r6);	 Catch:{ Exception -> 0x0148 }
        if (r5 == 0) goto L_0x01f7;
    L_0x0123:
        r5 = "SdCard";
        r6 = 2131232311; // 0x7f080637 float:1.8080728E38 double:1.052968668E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);	 Catch:{ Exception -> 0x0148 }
        r3.title = r5;	 Catch:{ Exception -> 0x0148 }
    L_0x012f:
        r5 = 2130837916; // 0x7f02019c float:1.72808E38 double:1.052773811E-314;
        r3.icon = r5;	 Catch:{ Exception -> 0x0148 }
        r5 = r8.getRootSubtitle(r0);	 Catch:{ Exception -> 0x0148 }
        r3.subtitle = r5;	 Catch:{ Exception -> 0x0148 }
        r5 = new java.io.File;	 Catch:{ Exception -> 0x0148 }
        r5.<init>(r0);	 Catch:{ Exception -> 0x0148 }
        r3.file = r5;	 Catch:{ Exception -> 0x0148 }
        r0 = r8.items;	 Catch:{ Exception -> 0x0148 }
        r0.add(r3);	 Catch:{ Exception -> 0x0148 }
        goto L_0x006e;
    L_0x0148:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);	 Catch:{ Exception -> 0x014e }
        goto L_0x006e;
    L_0x014e:
        r0 = move-exception;
    L_0x014f:
        org.telegram.messenger.FileLog.e(r0);	 Catch:{ all -> 0x0205 }
        if (r1 == 0) goto L_0x0157;
    L_0x0154:
        r1.close();	 Catch:{ Exception -> 0x0219 }
    L_0x0157:
        r0 = new org.telegram.ui.DocumentSelectActivity$ListItem;
        r0.<init>();
        r1 = "/";
        r0.title = r1;
        r1 = "SystemRoot";
        r3 = 2131232477; // 0x7f0806dd float:1.8081064E38 double:1.05296875E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r3);
        r0.subtitle = r1;
        r0.icon = r7;
        r1 = new java.io.File;
        r3 = "/";
        r1.<init>(r3);
        r0.file = r1;
        r1 = r8.items;
        r1.add(r0);
        r0 = new java.io.File;	 Catch:{ Exception -> 0x0224 }
        r1 = android.os.Environment.getExternalStorageDirectory();	 Catch:{ Exception -> 0x0224 }
        r3 = "Telegram";
        r0.<init>(r1, r3);	 Catch:{ Exception -> 0x0224 }
        r1 = r0.exists();	 Catch:{ Exception -> 0x0224 }
        if (r1 == 0) goto L_0x01ad;
    L_0x0190:
        r1 = new org.telegram.ui.DocumentSelectActivity$ListItem;	 Catch:{ Exception -> 0x0224 }
        r3 = 0;
        r1.<init>();	 Catch:{ Exception -> 0x0224 }
        r3 = "Telegram";
        r1.title = r3;	 Catch:{ Exception -> 0x0224 }
        r3 = r0.toString();	 Catch:{ Exception -> 0x0224 }
        r1.subtitle = r3;	 Catch:{ Exception -> 0x0224 }
        r3 = 2130837913; // 0x7f020199 float:1.7280793E38 double:1.0527738097E-314;
        r1.icon = r3;	 Catch:{ Exception -> 0x0224 }
        r1.file = r0;	 Catch:{ Exception -> 0x0224 }
        r0 = r8.items;	 Catch:{ Exception -> 0x0224 }
        r0.add(r1);	 Catch:{ Exception -> 0x0224 }
    L_0x01ad:
        r0 = new org.telegram.ui.DocumentSelectActivity$ListItem;
        r0.<init>();
        r1 = "Gallery";
        r3 = 2131231572; // 0x7f080354 float:1.8079229E38 double:1.052968303E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r3);
        r0.title = r1;
        r1 = "GalleryInfo";
        r3 = 2131231573; // 0x7f080355 float:1.807923E38 double:1.0529683036E-314;
        r1 = org.telegram.messenger.LocaleController.getString(r1, r3);
        r0.subtitle = r1;
        r1 = 2130837990; // 0x7f0201e6 float:1.728095E38 double:1.0527738477E-314;
        r0.icon = r1;
        r0.file = r2;
        r1 = r8.items;
        r1.add(r0);
        r0 = r8.listView;
        org.telegram.messenger.AndroidUtilities.clearDrawableAnimation(r0);
        r0 = 1;
        r8.scrolling = r0;
        r0 = r8.listAdapter;
        r0.notifyDataSetChanged();
        return;
    L_0x01e4:
        r3 = "InternalStorage";
        r5 = 2131231656; // 0x7f0803a8 float:1.80794E38 double:1.0529683446E-314;
        r3 = org.telegram.messenger.LocaleController.getString(r3, r5);
        r1.title = r3;
        r3 = 2130837989; // 0x7f0201e5 float:1.7280948E38 double:1.052773847E-314;
        r1.icon = r3;
        goto L_0x004d;
    L_0x01f7:
        r5 = "ExternalStorage";
        r6 = 2131231473; // 0x7f0802f1 float:1.8079028E38 double:1.052968254E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);	 Catch:{ Exception -> 0x0148 }
        r3.title = r5;	 Catch:{ Exception -> 0x0148 }
        goto L_0x012f;
    L_0x0205:
        r0 = move-exception;
    L_0x0206:
        if (r1 == 0) goto L_0x020b;
    L_0x0208:
        r1.close();	 Catch:{ Exception -> 0x021f }
    L_0x020b:
        throw r0;
    L_0x020c:
        if (r1 == 0) goto L_0x0157;
    L_0x020e:
        r1.close();	 Catch:{ Exception -> 0x0213 }
        goto L_0x0157;
    L_0x0213:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);
        goto L_0x0157;
    L_0x0219:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);
        goto L_0x0157;
    L_0x021f:
        r1 = move-exception;
        org.telegram.messenger.FileLog.e(r1);
        goto L_0x020b;
    L_0x0224:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);
        goto L_0x01ad;
    L_0x0229:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0206;
    L_0x022c:
        r0 = move-exception;
        r1 = r2;
        goto L_0x014f;
    L_0x0230:
        r0 = r3;
        goto L_0x010d;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.DocumentSelectActivity.listRoots():void");
    }

    private void showErrorBox(String str) {
        if (getParentActivity() != null) {
            new Builder(getParentActivity()).setTitle(LocaleController.getString("AppName", R.string.AppName)).setMessage(str).setPositiveButton(LocaleController.getString("OK", R.string.OK), null).show();
        }
    }

    public View createView(Context context) {
        if (!this.receiverRegistered) {
            this.receiverRegistered = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.MEDIA_BAD_REMOVAL");
            intentFilter.addAction("android.intent.action.MEDIA_CHECKING");
            intentFilter.addAction("android.intent.action.MEDIA_EJECT");
            intentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
            intentFilter.addAction("android.intent.action.MEDIA_NOFS");
            intentFilter.addAction("android.intent.action.MEDIA_REMOVED");
            intentFilter.addAction("android.intent.action.MEDIA_SHARED");
            intentFilter.addAction("android.intent.action.MEDIA_UNMOUNTABLE");
            intentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
            intentFilter.addDataScheme("file");
            ApplicationLoader.applicationContext.registerReceiver(this.receiver, intentFilter);
        }
        this.actionBar.setBackButtonDrawable(new BackDrawable(false));
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("SelectFile", R.string.SelectFile));
        this.actionBar.setActionBarMenuOnItemClick(new C47352());
        this.selectedFiles.clear();
        this.actionModeViews.clear();
        ActionBarMenu createActionMode = this.actionBar.createActionMode();
        this.selectedMessagesCountTextView = new NumberTextView(createActionMode.getContext());
        this.selectedMessagesCountTextView.setTextSize(18);
        this.selectedMessagesCountTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.selectedMessagesCountTextView.setTextColor(Theme.getColor(Theme.key_actionBarActionModeDefaultIcon));
        this.selectedMessagesCountTextView.setOnTouchListener(new C47363());
        createActionMode.addView(this.selectedMessagesCountTextView, LayoutHelper.createLinear(0, -1, 1.0f, 65, 0, 0, 0));
        this.actionModeViews.add(createActionMode.addItemWithWidth(3, R.drawable.ic_ab_done, AndroidUtilities.dp(54.0f)));
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.emptyView = new EmptyTextProgressView(context);
        this.emptyView.showTextView();
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView = new RecyclerListView(context);
        this.listView.setVerticalScrollBarEnabled(false);
        RecyclerListView recyclerListView = this.listView;
        LayoutManager linearLayoutManager = new LinearLayoutManager(context, 1, false);
        this.layoutManager = linearLayoutManager;
        recyclerListView.setLayoutManager(linearLayoutManager);
        this.listView.setEmptyView(this.emptyView);
        RecyclerListView recyclerListView2 = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.listAdapter = listAdapter;
        recyclerListView2.setAdapter(listAdapter);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setOnScrollListener(new C47374());
        this.listView.setOnItemLongClickListener(new C47385());
        this.listView.setOnItemClickListener(new C47396());
        listRoots();
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[22];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[2] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[6] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[7] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder);
        themeDescriptionArr[8] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_AM_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarActionModeDefaultIcon);
        themeDescriptionArr[9] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_AM_BACKGROUND, null, null, null, null, Theme.key_actionBarActionModeDefault);
        themeDescriptionArr[10] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_AM_TOPBACKGROUND, null, null, null, null, Theme.key_actionBarActionModeDefaultTop);
        themeDescriptionArr[11] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_AM_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarActionModeDefaultSelector);
        themeDescriptionArr[12] = new ThemeDescription(this.selectedMessagesCountTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_actionBarActionModeDefaultIcon);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, 0, new Class[]{GraySectionCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[14] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{GraySectionCell.class}, null, null, null, Theme.key_graySection);
        themeDescriptionArr[15] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{SharedDocumentCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[16] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{SharedDocumentCell.class}, new String[]{"dateTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        themeDescriptionArr[17] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOX, new Class[]{SharedDocumentCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_checkbox);
        themeDescriptionArr[18] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{SharedDocumentCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_checkboxCheck);
        themeDescriptionArr[19] = new ThemeDescription(this.listView, ThemeDescription.FLAG_IMAGECOLOR, new Class[]{SharedDocumentCell.class}, new String[]{"thumbImageView"}, null, null, null, Theme.key_files_folderIcon);
        themeDescriptionArr[20] = new ThemeDescription(this.listView, ThemeDescription.FLAG_IMAGECOLOR | ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{SharedDocumentCell.class}, new String[]{"thumbImageView"}, null, null, null, Theme.key_files_folderIconBackground);
        themeDescriptionArr[21] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{SharedDocumentCell.class}, new String[]{"extTextView"}, null, null, null, Theme.key_files_iconText);
        return themeDescriptionArr;
    }

    public void loadRecentFiles() {
        try {
            File[] listFiles = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).listFiles();
            for (File file : listFiles) {
                if (!file.isDirectory()) {
                    ListItem listItem = new ListItem();
                    listItem.title = file.getName();
                    listItem.file = file;
                    String name = file.getName();
                    String[] split = name.split("\\.");
                    listItem.ext = split.length > 1 ? split[split.length - 1] : "?";
                    listItem.subtitle = AndroidUtilities.formatFileSize(file.length());
                    String toLowerCase = name.toLowerCase();
                    if (toLowerCase.endsWith(".jpg") || toLowerCase.endsWith(".png") || toLowerCase.endsWith(".gif") || toLowerCase.endsWith(".jpeg")) {
                        listItem.thumb = file.getAbsolutePath();
                    }
                    this.recentItems.add(listItem);
                }
            }
            Collections.sort(this.recentItems, new C47407());
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    public boolean onBackPressed() {
        if (this.history.size() <= 0) {
            return super.onBackPressed();
        }
        HistoryEntry historyEntry = (HistoryEntry) this.history.remove(this.history.size() - 1);
        this.actionBar.setTitle(historyEntry.title);
        if (historyEntry.dir != null) {
            listFiles(historyEntry.dir);
        } else {
            listRoots();
        }
        this.layoutManager.scrollToPositionWithOffset(historyEntry.scrollItem, historyEntry.scrollOffset);
        return false;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.listView != null) {
            this.listView.getViewTreeObserver().addOnPreDrawListener(new C47418());
        }
    }

    public boolean onFragmentCreate() {
        loadRecentFiles();
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        try {
            if (this.receiverRegistered) {
                ApplicationLoader.applicationContext.unregisterReceiver(this.receiver);
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
        super.onFragmentDestroy();
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
        fixLayoutInternal();
    }

    public void setDelegate(DocumentSelectActivityDelegate documentSelectActivityDelegate) {
        this.delegate = documentSelectActivityDelegate;
    }
}
