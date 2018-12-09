package org.telegram.ui;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Audio.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController.AudioEntry;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_documentAttributeFilename;
import org.telegram.tgnet.TLRPC$TL_message;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.tgnet.TLRPC$TL_photoSizeEmpty;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageMedia;
import org.telegram.tgnet.TLRPC.Peer;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.AudioCell;
import org.telegram.ui.Cells.AudioCell.AudioCellDelegate;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.PickerBottomLayout;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class AudioSelectActivity extends BaseFragment implements NotificationCenterDelegate {
    private ArrayList<AudioEntry> audioEntries = new ArrayList();
    private PickerBottomLayout bottomLayout;
    private AudioSelectActivityDelegate delegate;
    private RecyclerListView listView;
    private ListAdapter listViewAdapter;
    private boolean loadingAudio;
    private MessageObject playingAudio;
    private EmptyTextProgressView progressView;
    private HashMap<Long, AudioEntry> selectedAudios = new HashMap();
    private View shadow;

    /* renamed from: org.telegram.ui.AudioSelectActivity$1 */
    class C39421 extends ActionBarMenuOnItemClick {
        C39421() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                AudioSelectActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.AudioSelectActivity$2 */
    class C39432 implements OnItemClickListener {
        C39432() {
        }

        public void onItemClick(View view, int i) {
            AudioCell audioCell = (AudioCell) view;
            AudioEntry audioEntry = audioCell.getAudioEntry();
            if (AudioSelectActivity.this.selectedAudios.containsKey(Long.valueOf(audioEntry.id))) {
                AudioSelectActivity.this.selectedAudios.remove(Long.valueOf(audioEntry.id));
                audioCell.setChecked(false);
            } else {
                AudioSelectActivity.this.selectedAudios.put(Long.valueOf(audioEntry.id), audioEntry);
                audioCell.setChecked(true);
            }
            AudioSelectActivity.this.updateBottomLayoutCount();
        }
    }

    /* renamed from: org.telegram.ui.AudioSelectActivity$3 */
    class C39443 implements OnClickListener {
        C39443() {
        }

        public void onClick(View view) {
            AudioSelectActivity.this.finishFragment();
        }
    }

    /* renamed from: org.telegram.ui.AudioSelectActivity$4 */
    class C39454 implements OnClickListener {
        C39454() {
        }

        public void onClick(View view) {
            if (AudioSelectActivity.this.delegate != null) {
                ArrayList arrayList = new ArrayList();
                for (Entry value : AudioSelectActivity.this.selectedAudios.entrySet()) {
                    arrayList.add(((AudioEntry) value.getValue()).messageObject);
                }
                AudioSelectActivity.this.delegate.didSelectAudio(arrayList);
            }
            AudioSelectActivity.this.finishFragment();
        }
    }

    /* renamed from: org.telegram.ui.AudioSelectActivity$5 */
    class C39475 implements Runnable {
        C39475() {
        }

        public void run() {
            Throwable e;
            String[] strArr = new String[]{"_id", "artist", "title", "_data", "duration", "album"};
            final ArrayList arrayList = new ArrayList();
            Cursor query;
            try {
                query = ApplicationLoader.applicationContext.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, strArr, "is_music != 0", null, "title");
                int i = -2000000000;
                while (query.moveToNext()) {
                    try {
                        AudioEntry audioEntry = new AudioEntry();
                        audioEntry.id = (long) query.getInt(0);
                        audioEntry.author = query.getString(1);
                        audioEntry.title = query.getString(2);
                        audioEntry.path = query.getString(3);
                        audioEntry.duration = (int) (query.getLong(4) / 1000);
                        audioEntry.genre = query.getString(5);
                        File file = new File(audioEntry.path);
                        Message tLRPC$TL_message = new TLRPC$TL_message();
                        tLRPC$TL_message.out = true;
                        tLRPC$TL_message.id = i;
                        tLRPC$TL_message.to_id = new TLRPC$TL_peerUser();
                        Peer peer = tLRPC$TL_message.to_id;
                        int clientUserId = UserConfig.getClientUserId();
                        tLRPC$TL_message.from_id = clientUserId;
                        peer.user_id = clientUserId;
                        tLRPC$TL_message.date = (int) (System.currentTimeMillis() / 1000);
                        tLRPC$TL_message.message = "-1";
                        tLRPC$TL_message.attachPath = audioEntry.path;
                        tLRPC$TL_message.media = new TLRPC$TL_messageMediaDocument();
                        MessageMedia messageMedia = tLRPC$TL_message.media;
                        messageMedia.flags |= 3;
                        tLRPC$TL_message.media.document = new TLRPC$TL_document();
                        tLRPC$TL_message.flags |= 768;
                        String fileExtension = FileLoader.getFileExtension(file);
                        tLRPC$TL_message.media.document.id = 0;
                        tLRPC$TL_message.media.document.access_hash = 0;
                        tLRPC$TL_message.media.document.date = tLRPC$TL_message.date;
                        Document document = tLRPC$TL_message.media.document;
                        StringBuilder append = new StringBuilder().append("audio/");
                        if (fileExtension.length() <= 0) {
                            fileExtension = "mp3";
                        }
                        document.mime_type = append.append(fileExtension).toString();
                        tLRPC$TL_message.media.document.size = (int) file.length();
                        tLRPC$TL_message.media.document.thumb = new TLRPC$TL_photoSizeEmpty();
                        tLRPC$TL_message.media.document.thumb.type = "s";
                        tLRPC$TL_message.media.document.dc_id = 0;
                        TLRPC$TL_documentAttributeAudio tLRPC$TL_documentAttributeAudio = new TLRPC$TL_documentAttributeAudio();
                        tLRPC$TL_documentAttributeAudio.duration = audioEntry.duration;
                        tLRPC$TL_documentAttributeAudio.title = audioEntry.title;
                        tLRPC$TL_documentAttributeAudio.performer = audioEntry.author;
                        tLRPC$TL_documentAttributeAudio.flags |= 3;
                        tLRPC$TL_message.media.document.attributes.add(tLRPC$TL_documentAttributeAudio);
                        TLRPC$TL_documentAttributeFilename tLRPC$TL_documentAttributeFilename = new TLRPC$TL_documentAttributeFilename();
                        tLRPC$TL_documentAttributeFilename.file_name = file.getName();
                        tLRPC$TL_message.media.document.attributes.add(tLRPC$TL_documentAttributeFilename);
                        audioEntry.messageObject = new MessageObject(tLRPC$TL_message, null, false);
                        arrayList.add(audioEntry);
                        i--;
                    } catch (Exception e2) {
                        e = e2;
                    }
                }
                if (query != null) {
                    query.close();
                }
            } catch (Exception e3) {
                e = e3;
                query = null;
                try {
                    FileLog.e(e);
                    if (query != null) {
                        query.close();
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            AudioSelectActivity.this.audioEntries = arrayList;
                            AudioSelectActivity.this.progressView.showTextView();
                            AudioSelectActivity.this.listViewAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Throwable th) {
                    e = th;
                    if (query != null) {
                        query.close();
                    }
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                query = null;
                if (query != null) {
                    query.close();
                }
                throw e;
            }
            AndroidUtilities.runOnUIThread(/* anonymous class already generated */);
        }
    }

    public interface AudioSelectActivityDelegate {
        void didSelectAudio(ArrayList<MessageObject> arrayList);
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.AudioSelectActivity$ListAdapter$1 */
        class C39481 implements AudioCellDelegate {
            C39481() {
            }

            public void startedPlayingAudio(MessageObject messageObject) {
                AudioSelectActivity.this.playingAudio = messageObject;
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public Object getItem(int i) {
            return AudioSelectActivity.this.audioEntries.get(i);
        }

        public int getItemCount() {
            return AudioSelectActivity.this.audioEntries.size();
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return true;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            ((AudioCell) viewHolder.itemView).setAudio((AudioEntry) AudioSelectActivity.this.audioEntries.get(i), i != AudioSelectActivity.this.audioEntries.size() + -1, AudioSelectActivity.this.selectedAudios.containsKey(Long.valueOf(((AudioEntry) AudioSelectActivity.this.audioEntries.get(i)).id)));
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View audioCell = new AudioCell(this.mContext);
            audioCell.setDelegate(new C39481());
            return new Holder(audioCell);
        }
    }

    private void loadAudio() {
        this.loadingAudio = true;
        if (this.progressView != null) {
            this.progressView.showProgress();
        }
        Utilities.globalQueue.postRunnable(new C39475());
    }

    private void updateBottomLayoutCount() {
        this.bottomLayout.updateSelectedCount(this.selectedAudios.size(), true);
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("AttachMusic", R.string.AttachMusic));
        this.actionBar.setActionBarMenuOnItemClick(new C39421());
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.progressView = new EmptyTextProgressView(context);
        this.progressView.setText(LocaleController.getString("NoAudio", R.string.NoAudio));
        frameLayout.addView(this.progressView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView = new RecyclerListView(context);
        this.listView.setEmptyView(this.progressView);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        RecyclerListView recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.listViewAdapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        this.listView.setVerticalScrollbarPosition(LocaleController.isRTL ? 1 : 2);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
        this.listView.setOnItemClickListener(new C39432());
        this.bottomLayout = new PickerBottomLayout(context, false);
        frameLayout.addView(this.bottomLayout, LayoutHelper.createFrame(-1, 48, 80));
        this.bottomLayout.cancelButton.setOnClickListener(new C39443());
        this.bottomLayout.doneButton.setOnClickListener(new C39454());
        View view = new View(context);
        view.setBackgroundResource(R.drawable.header_shadow_reverse);
        frameLayout.addView(view, LayoutHelper.createFrame(-1, 3.0f, 83, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
        if (this.loadingAudio) {
            this.progressView.showProgress();
        } else {
            this.progressView.showTextView();
        }
        updateBottomLayoutCount();
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.closeChats) {
            removeSelfFromStack();
        } else if (i == NotificationCenter.messagePlayingDidReset && this.listViewAdapter != null) {
            this.listViewAdapter.notifyDataSetChanged();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        r9 = new ThemeDescription[24];
        r9[7] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r9[8] = new ThemeDescription(this.progressView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_emptyListPlaceholder);
        r9[9] = new ThemeDescription(this.progressView, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_progressCircle);
        r9[10] = new ThemeDescription(this.listView, 0, new Class[]{AudioCell.class}, new String[]{"titleTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[11] = new ThemeDescription(this.listView, 0, new Class[]{AudioCell.class}, new String[]{"genreTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        r9[12] = new ThemeDescription(this.listView, 0, new Class[]{AudioCell.class}, new String[]{"authorTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        r9[13] = new ThemeDescription(this.listView, 0, new Class[]{AudioCell.class}, new String[]{"timeTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText3);
        r9[14] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOX, new Class[]{AudioCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_musicPicker_checkbox);
        r9[15] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{AudioCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_musicPicker_checkboxCheck);
        r9[16] = new ThemeDescription(this.listView, ThemeDescription.FLAG_USEBACKGROUNDDRAWABLE, new Class[]{AudioCell.class}, new String[]{"playButton"}, null, null, null, Theme.key_musicPicker_buttonIcon);
        r9[17] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_USEBACKGROUNDDRAWABLE, new Class[]{AudioCell.class}, new String[]{"playButton"}, null, null, null, Theme.key_musicPicker_buttonBackground);
        r9[18] = new ThemeDescription(this.bottomLayout, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        r9[19] = new ThemeDescription(this.bottomLayout, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{PickerBottomLayout.class}, new String[]{"cancelButton"}, null, null, null, Theme.key_picker_enabledButton);
        r9[20] = new ThemeDescription(this.bottomLayout, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{PickerBottomLayout.class}, new String[]{"doneButtonTextView"}, null, null, null, Theme.key_picker_enabledButton);
        r9[21] = new ThemeDescription(this.bottomLayout, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{PickerBottomLayout.class}, new String[]{"doneButtonTextView"}, null, null, null, Theme.key_picker_disabledButton);
        r9[22] = new ThemeDescription(this.bottomLayout, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{PickerBottomLayout.class}, new String[]{"doneButtonBadgeTextView"}, null, null, null, Theme.key_picker_badgeText);
        r9[23] = new ThemeDescription(this.bottomLayout, ThemeDescription.FLAG_USEBACKGROUNDDRAWABLE, new Class[]{PickerBottomLayout.class}, new String[]{"doneButtonBadgeTextView"}, null, null, null, Theme.key_picker_badge);
        return r9;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.closeChats);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingDidReset);
        loadAudio();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeChats);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidReset);
        if (this.playingAudio != null && MediaController.getInstance().isPlayingMessage(this.playingAudio)) {
            MediaController.getInstance().cleanupPlayer(true, true);
        }
    }

    public void setDelegate(AudioSelectActivityDelegate audioSelectActivityDelegate) {
        this.delegate = audioSelectActivityDelegate;
    }
}
