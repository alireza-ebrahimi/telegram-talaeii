package org.telegram.messenger.exoplayer2.trackselection;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.telegram.messenger.exoplayer2.RendererCapabilities;
import org.telegram.messenger.exoplayer2.RendererConfiguration;
import org.telegram.messenger.exoplayer2.source.TrackGroup;
import org.telegram.messenger.exoplayer2.source.TrackGroupArray;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection.Factory;
import org.telegram.messenger.exoplayer2.util.Util;

public abstract class MappingTrackSelector extends TrackSelector {
    private MappedTrackInfo currentMappedTrackInfo;
    private final SparseBooleanArray rendererDisabledFlags = new SparseBooleanArray();
    private final SparseArray<Map<TrackGroupArray, SelectionOverride>> selectionOverrides = new SparseArray();
    private int tunnelingAudioSessionId = 0;

    public static final class MappedTrackInfo {
        public static final int RENDERER_SUPPORT_EXCEEDS_CAPABILITIES_TRACKS = 2;
        public static final int RENDERER_SUPPORT_NO_TRACKS = 0;
        public static final int RENDERER_SUPPORT_PLAYABLE_TRACKS = 3;
        public static final int RENDERER_SUPPORT_UNSUPPORTED_TRACKS = 1;
        private final int[][][] formatSupport;
        public final int length;
        private final int[] mixedMimeTypeAdaptiveSupport;
        private final int[] rendererTrackTypes;
        private final TrackGroupArray[] trackGroups;
        private final TrackGroupArray unassociatedTrackGroups;

        MappedTrackInfo(int[] iArr, TrackGroupArray[] trackGroupArrayArr, int[] iArr2, int[][][] iArr3, TrackGroupArray trackGroupArray) {
            this.rendererTrackTypes = iArr;
            this.trackGroups = trackGroupArrayArr;
            this.formatSupport = iArr3;
            this.mixedMimeTypeAdaptiveSupport = iArr2;
            this.unassociatedTrackGroups = trackGroupArray;
            this.length = trackGroupArrayArr.length;
        }

        public int getAdaptiveSupport(int i, int i2, boolean z) {
            int i3 = 0;
            int i4 = this.trackGroups[i].get(i2).length;
            int[] iArr = new int[i4];
            for (int i5 = 0; i5 < i4; i5++) {
                int trackFormatSupport = getTrackFormatSupport(i, i2, i5);
                if (trackFormatSupport == 4 || (z && trackFormatSupport == 3)) {
                    trackFormatSupport = i3 + 1;
                    iArr[i3] = i5;
                    i3 = trackFormatSupport;
                }
            }
            return getAdaptiveSupport(i, i2, Arrays.copyOf(iArr, i3));
        }

        public int getAdaptiveSupport(int i, int i2, int[] iArr) {
            String str = null;
            int i3 = 0;
            int i4 = 0;
            int i5 = 16;
            int i6 = 0;
            while (i3 < iArr.length) {
                int i7;
                String str2 = this.trackGroups[i].get(i2).getFormat(iArr[i3]).sampleMimeType;
                int i8 = i6 + 1;
                if (i6 == 0) {
                    i7 = i4;
                } else {
                    String str3 = str;
                    i7 = (!Util.areEqual(str, str2) ? 1 : 0) | i4;
                    str2 = str3;
                }
                i5 = Math.min(i5, this.formatSupport[i][i2][i3] & 24);
                i3++;
                i6 = i8;
                i4 = i7;
                str = str2;
            }
            return i4 != 0 ? Math.min(i5, this.mixedMimeTypeAdaptiveSupport[i]) : i5;
        }

        public int getRendererSupport(int i) {
            int[][] iArr = this.formatSupport[i];
            int i2 = 0;
            for (int i3 = 0; i3 < iArr.length; i3++) {
                int i4 = 0;
                while (i4 < iArr[i3].length) {
                    int i5;
                    switch (iArr[i3][i4] & 7) {
                        case 3:
                            i5 = 2;
                            break;
                        case 4:
                            return 3;
                        default:
                            i5 = 1;
                            break;
                    }
                    i4++;
                    i2 = Math.max(i2, i5);
                }
            }
            return i2;
        }

        public int getTrackFormatSupport(int i, int i2, int i3) {
            return this.formatSupport[i][i2][i3] & 7;
        }

        public TrackGroupArray getTrackGroups(int i) {
            return this.trackGroups[i];
        }

        public int getTrackTypeRendererSupport(int i) {
            int i2 = 0;
            int i3 = 0;
            while (i2 < this.length) {
                if (this.rendererTrackTypes[i2] == i) {
                    i3 = Math.max(i3, getRendererSupport(i2));
                }
                i2++;
            }
            return i3;
        }

        public TrackGroupArray getUnassociatedTrackGroups() {
            return this.unassociatedTrackGroups;
        }
    }

    public static final class SelectionOverride {
        public final Factory factory;
        public final int groupIndex;
        public final int length;
        public final int[] tracks;

        public SelectionOverride(Factory factory, int i, int... iArr) {
            this.factory = factory;
            this.groupIndex = i;
            this.tracks = iArr;
            this.length = iArr.length;
        }

        public boolean containsTrack(int i) {
            for (int i2 : this.tracks) {
                if (i2 == i) {
                    return true;
                }
            }
            return false;
        }

        public TrackSelection createTrackSelection(TrackGroupArray trackGroupArray) {
            return this.factory.createTrackSelection(trackGroupArray.get(this.groupIndex), this.tracks);
        }
    }

    private static int findRenderer(RendererCapabilities[] rendererCapabilitiesArr, TrackGroup trackGroup) {
        int i = 0;
        int length = rendererCapabilitiesArr.length;
        for (int i2 = 0; i2 < rendererCapabilitiesArr.length; i2++) {
            RendererCapabilities rendererCapabilities = rendererCapabilitiesArr[i2];
            int i3 = 0;
            while (i3 < trackGroup.length) {
                int supportsFormat = rendererCapabilities.supportsFormat(trackGroup.getFormat(i3)) & 7;
                if (supportsFormat <= i) {
                    supportsFormat = length;
                    length = i;
                } else if (supportsFormat == 4) {
                    return i2;
                } else {
                    length = supportsFormat;
                    supportsFormat = i2;
                }
                i3++;
                i = length;
                length = supportsFormat;
            }
        }
        return length;
    }

    private static int[] getFormatSupport(RendererCapabilities rendererCapabilities, TrackGroup trackGroup) {
        int[] iArr = new int[trackGroup.length];
        for (int i = 0; i < trackGroup.length; i++) {
            iArr[i] = rendererCapabilities.supportsFormat(trackGroup.getFormat(i));
        }
        return iArr;
    }

    private static int[] getMixedMimeTypeAdaptationSupport(RendererCapabilities[] rendererCapabilitiesArr) {
        int[] iArr = new int[rendererCapabilitiesArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = rendererCapabilitiesArr[i].supportsMixedMimeTypeAdaptation();
        }
        return iArr;
    }

    private static void maybeConfigureRenderersForTunneling(RendererCapabilities[] rendererCapabilitiesArr, TrackGroupArray[] trackGroupArrayArr, int[][][] iArr, RendererConfiguration[] rendererConfigurationArr, TrackSelection[] trackSelectionArr, int i) {
        int i2 = 0;
        if (i != 0) {
            int i3 = 0;
            int i4 = -1;
            int i5 = -1;
            while (i3 < rendererCapabilitiesArr.length) {
                int trackType = rendererCapabilitiesArr[i3].getTrackType();
                TrackSelection trackSelection = trackSelectionArr[i3];
                if ((trackType == 1 || trackType == 2) && trackSelection != null && rendererSupportsTunneling(iArr[i3], trackGroupArrayArr[i3], trackSelection)) {
                    if (trackType == 1) {
                        if (i5 != -1) {
                            i3 = 0;
                            break;
                        }
                        i5 = i3;
                    } else if (i4 != -1) {
                        i3 = 0;
                        break;
                    } else {
                        i4 = i3;
                    }
                }
                i3++;
            }
            i3 = 1;
            if (!(i5 == -1 || i4 == -1)) {
                i2 = 1;
            }
            if ((i3 & i2) != 0) {
                RendererConfiguration rendererConfiguration = new RendererConfiguration(i);
                rendererConfigurationArr[i5] = rendererConfiguration;
                rendererConfigurationArr[i4] = rendererConfiguration;
            }
        }
    }

    private static boolean rendererSupportsTunneling(int[][] iArr, TrackGroupArray trackGroupArray, TrackSelection trackSelection) {
        if (trackSelection == null) {
            return false;
        }
        int indexOf = trackGroupArray.indexOf(trackSelection.getTrackGroup());
        for (int i = 0; i < trackSelection.length(); i++) {
            if ((iArr[indexOf][trackSelection.getIndexInTrackGroup(i)] & 32) != 32) {
                return false;
            }
        }
        return true;
    }

    public final void clearSelectionOverride(int i, TrackGroupArray trackGroupArray) {
        Map map = (Map) this.selectionOverrides.get(i);
        if (map != null && map.containsKey(trackGroupArray)) {
            map.remove(trackGroupArray);
            if (map.isEmpty()) {
                this.selectionOverrides.remove(i);
            }
            invalidate();
        }
    }

    public final void clearSelectionOverrides() {
        if (this.selectionOverrides.size() != 0) {
            this.selectionOverrides.clear();
            invalidate();
        }
    }

    public final void clearSelectionOverrides(int i) {
        Map map = (Map) this.selectionOverrides.get(i);
        if (map != null && !map.isEmpty()) {
            this.selectionOverrides.remove(i);
            invalidate();
        }
    }

    public final MappedTrackInfo getCurrentMappedTrackInfo() {
        return this.currentMappedTrackInfo;
    }

    public final boolean getRendererDisabled(int i) {
        return this.rendererDisabledFlags.get(i);
    }

    public final SelectionOverride getSelectionOverride(int i, TrackGroupArray trackGroupArray) {
        Map map = (Map) this.selectionOverrides.get(i);
        return map != null ? (SelectionOverride) map.get(trackGroupArray) : null;
    }

    public final boolean hasSelectionOverride(int i, TrackGroupArray trackGroupArray) {
        Map map = (Map) this.selectionOverrides.get(i);
        return map != null && map.containsKey(trackGroupArray);
    }

    public final void onSelectionActivated(Object obj) {
        this.currentMappedTrackInfo = (MappedTrackInfo) obj;
    }

    public final TrackSelectorResult selectTracks(RendererCapabilities[] rendererCapabilitiesArr, TrackGroupArray trackGroupArray) {
        int i;
        int[] formatSupport;
        int i2 = 0;
        int[] iArr = new int[(rendererCapabilitiesArr.length + 1)];
        TrackGroup[][] trackGroupArr = new TrackGroup[(rendererCapabilitiesArr.length + 1)][];
        int[][][] iArr2 = new int[(rendererCapabilitiesArr.length + 1)][][];
        for (i = 0; i < trackGroupArr.length; i++) {
            trackGroupArr[i] = new TrackGroup[trackGroupArray.length];
            iArr2[i] = new int[trackGroupArray.length][];
        }
        int[] mixedMimeTypeAdaptationSupport = getMixedMimeTypeAdaptationSupport(rendererCapabilitiesArr);
        for (i = 0; i < trackGroupArray.length; i++) {
            TrackGroup trackGroup = trackGroupArray.get(i);
            int findRenderer = findRenderer(rendererCapabilitiesArr, trackGroup);
            formatSupport = findRenderer == rendererCapabilitiesArr.length ? new int[trackGroup.length] : getFormatSupport(rendererCapabilitiesArr[findRenderer], trackGroup);
            int i3 = iArr[findRenderer];
            trackGroupArr[findRenderer][i3] = trackGroup;
            iArr2[findRenderer][i3] = formatSupport;
            iArr[findRenderer] = iArr[findRenderer] + 1;
        }
        TrackGroupArray[] trackGroupArrayArr = new TrackGroupArray[rendererCapabilitiesArr.length];
        formatSupport = new int[rendererCapabilitiesArr.length];
        for (findRenderer = 0; findRenderer < rendererCapabilitiesArr.length; findRenderer++) {
            i3 = iArr[findRenderer];
            trackGroupArrayArr[findRenderer] = new TrackGroupArray((TrackGroup[]) Arrays.copyOf(trackGroupArr[findRenderer], i3));
            iArr2[findRenderer] = (int[][]) Arrays.copyOf(iArr2[findRenderer], i3);
            formatSupport[findRenderer] = rendererCapabilitiesArr[findRenderer].getTrackType();
        }
        TrackGroupArray trackGroupArray2 = new TrackGroupArray((TrackGroup[]) Arrays.copyOf(trackGroupArr[rendererCapabilitiesArr.length], iArr[rendererCapabilitiesArr.length]));
        TrackSelection[] selectTracks = selectTracks(rendererCapabilitiesArr, trackGroupArrayArr, iArr2);
        for (int i4 = 0; i4 < rendererCapabilitiesArr.length; i4++) {
            if (this.rendererDisabledFlags.get(i4)) {
                selectTracks[i4] = null;
            } else {
                TrackGroupArray trackGroupArray3 = trackGroupArrayArr[i4];
                if (hasSelectionOverride(i4, trackGroupArray3)) {
                    SelectionOverride selectionOverride = (SelectionOverride) ((Map) this.selectionOverrides.get(i4)).get(trackGroupArray3);
                    selectTracks[i4] = selectionOverride == null ? null : selectionOverride.createTrackSelection(trackGroupArray3);
                }
            }
        }
        MappedTrackInfo mappedTrackInfo = new MappedTrackInfo(formatSupport, trackGroupArrayArr, mixedMimeTypeAdaptationSupport, iArr2, trackGroupArray2);
        RendererConfiguration[] rendererConfigurationArr = new RendererConfiguration[rendererCapabilitiesArr.length];
        while (i2 < rendererCapabilitiesArr.length) {
            rendererConfigurationArr[i2] = selectTracks[i2] != null ? RendererConfiguration.DEFAULT : null;
            i2++;
        }
        maybeConfigureRenderersForTunneling(rendererCapabilitiesArr, trackGroupArrayArr, iArr2, rendererConfigurationArr, selectTracks, this.tunnelingAudioSessionId);
        return new TrackSelectorResult(trackGroupArray, new TrackSelectionArray(selectTracks), mappedTrackInfo, rendererConfigurationArr);
    }

    protected abstract TrackSelection[] selectTracks(RendererCapabilities[] rendererCapabilitiesArr, TrackGroupArray[] trackGroupArrayArr, int[][][] iArr);

    public final void setRendererDisabled(int i, boolean z) {
        if (this.rendererDisabledFlags.get(i) != z) {
            this.rendererDisabledFlags.put(i, z);
            invalidate();
        }
    }

    public final void setSelectionOverride(int i, TrackGroupArray trackGroupArray, SelectionOverride selectionOverride) {
        Map map = (Map) this.selectionOverrides.get(i);
        if (map == null) {
            map = new HashMap();
            this.selectionOverrides.put(i, map);
        }
        if (!map.containsKey(trackGroupArray) || !Util.areEqual(map.get(trackGroupArray), selectionOverride)) {
            map.put(trackGroupArray, selectionOverride);
            invalidate();
        }
    }

    public void setTunnelingAudioSessionId(int i) {
        if (this.tunnelingAudioSessionId != i) {
            this.tunnelingAudioSessionId = i;
            invalidate();
        }
    }
}
