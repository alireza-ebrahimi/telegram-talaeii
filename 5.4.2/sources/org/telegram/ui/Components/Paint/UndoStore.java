package org.telegram.ui.Components.Paint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.telegram.messenger.AndroidUtilities;

public class UndoStore {
    private UndoStoreDelegate delegate;
    private List<UUID> operations = new ArrayList();
    private Map<UUID, Runnable> uuidToOperationMap = new HashMap();

    /* renamed from: org.telegram.ui.Components.Paint.UndoStore$1 */
    class C44881 implements Runnable {
        C44881() {
        }

        public void run() {
            if (UndoStore.this.delegate != null) {
                UndoStore.this.delegate.historyChanged();
            }
        }
    }

    public interface UndoStoreDelegate {
        void historyChanged();
    }

    private void notifyOfHistoryChanges() {
        AndroidUtilities.runOnUIThread(new C44881());
    }

    public boolean canUndo() {
        return !this.operations.isEmpty();
    }

    public void registerUndo(UUID uuid, Runnable runnable) {
        this.uuidToOperationMap.put(uuid, runnable);
        this.operations.add(uuid);
        notifyOfHistoryChanges();
    }

    public void reset() {
        this.operations.clear();
        this.uuidToOperationMap.clear();
        notifyOfHistoryChanges();
    }

    public void setDelegate(UndoStoreDelegate undoStoreDelegate) {
        this.delegate = undoStoreDelegate;
    }

    public void undo() {
        if (this.operations.size() != 0) {
            int size = this.operations.size() - 1;
            UUID uuid = (UUID) this.operations.get(size);
            Runnable runnable = (Runnable) this.uuidToOperationMap.get(uuid);
            this.uuidToOperationMap.remove(uuid);
            this.operations.remove(size);
            runnable.run();
            notifyOfHistoryChanges();
        }
    }

    public void unregisterUndo(UUID uuid) {
        this.uuidToOperationMap.remove(uuid);
        this.operations.remove(uuid);
        notifyOfHistoryChanges();
    }
}
