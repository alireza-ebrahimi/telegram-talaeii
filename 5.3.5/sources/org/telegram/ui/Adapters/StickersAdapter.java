package org.telegram.ui.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.ui.Cells.StickerCell;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class StickersAdapter extends SelectionAdapter implements NotificationCenterDelegate {
    private StickersAdapterDelegate delegate;
    private String lastSticker;
    private Context mContext;
    private ArrayList<TLRPC$Document> stickers;
    private ArrayList<String> stickersToLoad = new ArrayList();
    private boolean visible;

    public interface StickersAdapterDelegate {
        void needChangePanelVisibility(boolean z);
    }

    public StickersAdapter(Context context, StickersAdapterDelegate delegate) {
        this.mContext = context;
        this.delegate = delegate;
        StickersQuery.checkStickers(0);
        StickersQuery.checkStickers(1);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidFailedLoad);
    }

    public void onDestroy() {
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileDidFailedLoad);
    }

    public void didReceivedNotification(int id, Object... args) {
        boolean z = false;
        if ((id == NotificationCenter.FileDidLoaded || id == NotificationCenter.FileDidFailedLoad) && this.stickers != null && !this.stickers.isEmpty() && !this.stickersToLoad.isEmpty() && this.visible) {
            this.stickersToLoad.remove(args[0]);
            if (this.stickersToLoad.isEmpty()) {
                StickersAdapterDelegate stickersAdapterDelegate = this.delegate;
                if (!(this.stickers == null || this.stickers.isEmpty() || !this.stickersToLoad.isEmpty())) {
                    z = true;
                }
                stickersAdapterDelegate.needChangePanelVisibility(z);
            }
        }
    }

    private boolean checkStickerFilesExistAndDownload() {
        if (this.stickers == null) {
            return false;
        }
        this.stickersToLoad.clear();
        int size = Math.min(10, this.stickers.size());
        for (int a = 0; a < size; a++) {
            TLRPC$Document document = (TLRPC$Document) this.stickers.get(a);
            if (!FileLoader.getPathToAttach(document.thumb, "webp", true).exists()) {
                this.stickersToLoad.add(FileLoader.getAttachFileName(document.thumb, "webp"));
                FileLoader.getInstance().loadFile(document.thumb.location, "webp", 0, 1);
            }
        }
        return this.stickersToLoad.isEmpty();
    }

    public void loadStikersForEmoji(CharSequence emoji) {
        boolean search;
        if (emoji == null || emoji.length() <= 0 || emoji.length() > 14) {
            search = false;
        } else {
            search = true;
        }
        if (search) {
            int length = emoji.length();
            int a = 0;
            while (a < length) {
                if (a < length - 1 && ((emoji.charAt(a) == '?' && emoji.charAt(a + 1) >= '?' && emoji.charAt(a + 1) <= '?') || (emoji.charAt(a) == '‍' && (emoji.charAt(a + 1) == '♀' || emoji.charAt(a + 1) == '♂')))) {
                    emoji = TextUtils.concat(new CharSequence[]{emoji.subSequence(0, a), emoji.subSequence(a + 2, emoji.length())});
                    length -= 2;
                    a--;
                } else if (emoji.charAt(a) == '️') {
                    emoji = TextUtils.concat(new CharSequence[]{emoji.subSequence(0, a), emoji.subSequence(a + 1, emoji.length())});
                    length--;
                    a--;
                }
                a++;
            }
            this.lastSticker = emoji.toString();
            HashMap<String, ArrayList<TLRPC$Document>> allStickers = StickersQuery.getAllStickers();
            if (allStickers != null) {
                ArrayList<TLRPC$Document> newStickers = (ArrayList) allStickers.get(this.lastSticker);
                if (this.stickers == null || newStickers != null) {
                    ArrayList arrayList = (newStickers == null || newStickers.isEmpty()) ? null : new ArrayList(newStickers);
                    this.stickers = arrayList;
                    if (this.stickers != null) {
                        final ArrayList<TLRPC$Document> recentStickers = StickersQuery.getRecentStickersNoCopy(0);
                        final ArrayList<TLRPC$Document> favsStickers = StickersQuery.getRecentStickersNoCopy(2);
                        if (!recentStickers.isEmpty()) {
                            Collections.sort(this.stickers, new Comparator<TLRPC$Document>() {
                                private int getIndex(long id) {
                                    int a;
                                    for (a = 0; a < favsStickers.size(); a++) {
                                        if (((TLRPC$Document) favsStickers.get(a)).id == id) {
                                            return a + 1000;
                                        }
                                    }
                                    for (a = 0; a < recentStickers.size(); a++) {
                                        if (((TLRPC$Document) recentStickers.get(a)).id == id) {
                                            return a;
                                        }
                                    }
                                    return -1;
                                }

                                public int compare(TLRPC$Document lhs, TLRPC$Document rhs) {
                                    int idx1 = getIndex(lhs.id);
                                    int idx2 = getIndex(rhs.id);
                                    if (idx1 > idx2) {
                                        return -1;
                                    }
                                    if (idx1 < idx2) {
                                        return 1;
                                    }
                                    return 0;
                                }
                            });
                        }
                    }
                    checkStickerFilesExistAndDownload();
                    StickersAdapterDelegate stickersAdapterDelegate = this.delegate;
                    boolean z = (this.stickers == null || this.stickers.isEmpty() || !this.stickersToLoad.isEmpty()) ? false : true;
                    stickersAdapterDelegate.needChangePanelVisibility(z);
                    notifyDataSetChanged();
                    this.visible = true;
                } else if (this.visible) {
                    this.delegate.needChangePanelVisibility(false);
                    this.visible = false;
                }
            }
        }
        if (!search && this.visible && this.stickers != null) {
            this.visible = false;
            this.delegate.needChangePanelVisibility(false);
        }
    }

    public void clearStickers() {
        this.lastSticker = null;
        this.stickers = null;
        this.stickersToLoad.clear();
        notifyDataSetChanged();
    }

    public int getItemCount() {
        return this.stickers != null ? this.stickers.size() : 0;
    }

    public TLRPC$Document getItem(int i) {
        return (this.stickers == null || i < 0 || i >= this.stickers.size()) ? null : (TLRPC$Document) this.stickers.get(i);
    }

    public boolean isEnabled(ViewHolder holder) {
        return true;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(new StickerCell(this.mContext));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        int side = 0;
        if (i == 0) {
            if (this.stickers.size() == 1) {
                side = 2;
            } else {
                side = -1;
            }
        } else if (i == this.stickers.size() - 1) {
            side = 1;
        }
        ((StickerCell) viewHolder.itemView).setSticker((TLRPC$Document) this.stickers.get(i), side);
    }
}
