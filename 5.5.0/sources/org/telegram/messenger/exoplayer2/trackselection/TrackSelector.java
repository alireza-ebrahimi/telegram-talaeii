package org.telegram.messenger.exoplayer2.trackselection;

import org.telegram.messenger.exoplayer2.RendererCapabilities;
import org.telegram.messenger.exoplayer2.source.TrackGroupArray;

public abstract class TrackSelector {
    private InvalidationListener listener;

    public interface InvalidationListener {
        void onTrackSelectionsInvalidated();
    }

    public final void init(InvalidationListener invalidationListener) {
        this.listener = invalidationListener;
    }

    protected final void invalidate() {
        if (this.listener != null) {
            this.listener.onTrackSelectionsInvalidated();
        }
    }

    public abstract void onSelectionActivated(Object obj);

    public abstract TrackSelectorResult selectTracks(RendererCapabilities[] rendererCapabilitiesArr, TrackGroupArray trackGroupArray);
}
