package org.telegram.messenger.exoplayer2.trackselection;

import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.RendererCapabilities;
import org.telegram.messenger.exoplayer2.source.TrackGroup;
import org.telegram.messenger.exoplayer2.source.TrackGroupArray;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection.Factory;
import org.telegram.messenger.exoplayer2.upstream.BandwidthMeter;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Util;

public class DefaultTrackSelector extends MappingTrackSelector {
    private static final float FRACTION_TO_CONSIDER_FULLSCREEN = 0.98f;
    private static final int[] NO_TRACKS = new int[0];
    private static final int WITHIN_RENDERER_CAPABILITIES_BONUS = 1000;
    private final Factory adaptiveTrackSelectionFactory;
    private final AtomicReference<Parameters> paramsReference;

    private static final class AudioConfigurationTuple {
        public final int channelCount;
        public final String mimeType;
        public final int sampleRate;

        public AudioConfigurationTuple(int i, int i2, String str) {
            this.channelCount = i;
            this.sampleRate = i2;
            this.mimeType = str;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            AudioConfigurationTuple audioConfigurationTuple = (AudioConfigurationTuple) obj;
            return this.channelCount == audioConfigurationTuple.channelCount && this.sampleRate == audioConfigurationTuple.sampleRate && TextUtils.equals(this.mimeType, audioConfigurationTuple.mimeType);
        }

        public int hashCode() {
            return (this.mimeType != null ? this.mimeType.hashCode() : 0) + (((this.channelCount * 31) + this.sampleRate) * 31);
        }
    }

    public static final class Parameters {
        public final boolean allowMixedMimeAdaptiveness;
        public final boolean allowNonSeamlessAdaptiveness;
        public final boolean exceedRendererCapabilitiesIfNecessary;
        public final boolean exceedVideoConstraintsIfNecessary;
        public final int maxVideoBitrate;
        public final int maxVideoHeight;
        public final int maxVideoWidth;
        public final String preferredAudioLanguage;
        public final String preferredTextLanguage;
        public final int viewportHeight;
        public final boolean viewportOrientationMayChange;
        public final int viewportWidth;

        public Parameters() {
            this(null, null, false, true, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, true, true, Integer.MAX_VALUE, Integer.MAX_VALUE, true);
        }

        public Parameters(String str, String str2, boolean z, boolean z2, int i, int i2, int i3, boolean z3, boolean z4, int i4, int i5, boolean z5) {
            this.preferredAudioLanguage = str;
            this.preferredTextLanguage = str2;
            this.allowMixedMimeAdaptiveness = z;
            this.allowNonSeamlessAdaptiveness = z2;
            this.maxVideoWidth = i;
            this.maxVideoHeight = i2;
            this.maxVideoBitrate = i3;
            this.exceedVideoConstraintsIfNecessary = z3;
            this.exceedRendererCapabilitiesIfNecessary = z4;
            this.viewportWidth = i4;
            this.viewportHeight = i5;
            this.viewportOrientationMayChange = z5;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Parameters parameters = (Parameters) obj;
            return this.allowMixedMimeAdaptiveness == parameters.allowMixedMimeAdaptiveness && this.allowNonSeamlessAdaptiveness == parameters.allowNonSeamlessAdaptiveness && this.maxVideoWidth == parameters.maxVideoWidth && this.maxVideoHeight == parameters.maxVideoHeight && this.exceedVideoConstraintsIfNecessary == parameters.exceedVideoConstraintsIfNecessary && this.exceedRendererCapabilitiesIfNecessary == parameters.exceedRendererCapabilitiesIfNecessary && this.viewportOrientationMayChange == parameters.viewportOrientationMayChange && this.viewportWidth == parameters.viewportWidth && this.viewportHeight == parameters.viewportHeight && this.maxVideoBitrate == parameters.maxVideoBitrate && TextUtils.equals(this.preferredAudioLanguage, parameters.preferredAudioLanguage) && TextUtils.equals(this.preferredTextLanguage, parameters.preferredTextLanguage);
        }

        public int hashCode() {
            int i = 1;
            int hashCode = ((this.exceedRendererCapabilitiesIfNecessary ? 1 : 0) + (((this.exceedVideoConstraintsIfNecessary ? 1 : 0) + (((((((((this.allowNonSeamlessAdaptiveness ? 1 : 0) + (((this.allowMixedMimeAdaptiveness ? 1 : 0) + (((this.preferredAudioLanguage.hashCode() * 31) + this.preferredTextLanguage.hashCode()) * 31)) * 31)) * 31) + this.maxVideoWidth) * 31) + this.maxVideoHeight) * 31) + this.maxVideoBitrate) * 31)) * 31)) * 31;
            if (!this.viewportOrientationMayChange) {
                i = 0;
            }
            return ((((hashCode + i) * 31) + this.viewportWidth) * 31) + this.viewportHeight;
        }

        public Parameters withAllowMixedMimeAdaptiveness(boolean z) {
            if (z == this.allowMixedMimeAdaptiveness) {
                return this;
            }
            return new Parameters(this.preferredAudioLanguage, this.preferredTextLanguage, z, this.allowNonSeamlessAdaptiveness, this.maxVideoWidth, this.maxVideoHeight, this.maxVideoBitrate, this.exceedVideoConstraintsIfNecessary, this.exceedRendererCapabilitiesIfNecessary, this.viewportWidth, this.viewportHeight, this.viewportOrientationMayChange);
        }

        public Parameters withAllowNonSeamlessAdaptiveness(boolean z) {
            if (z == this.allowNonSeamlessAdaptiveness) {
                return this;
            }
            return new Parameters(this.preferredAudioLanguage, this.preferredTextLanguage, this.allowMixedMimeAdaptiveness, z, this.maxVideoWidth, this.maxVideoHeight, this.maxVideoBitrate, this.exceedVideoConstraintsIfNecessary, this.exceedRendererCapabilitiesIfNecessary, this.viewportWidth, this.viewportHeight, this.viewportOrientationMayChange);
        }

        public Parameters withExceedRendererCapabilitiesIfNecessary(boolean z) {
            if (z == this.exceedRendererCapabilitiesIfNecessary) {
                return this;
            }
            return new Parameters(this.preferredAudioLanguage, this.preferredTextLanguage, this.allowMixedMimeAdaptiveness, this.allowNonSeamlessAdaptiveness, this.maxVideoWidth, this.maxVideoHeight, this.maxVideoBitrate, this.exceedVideoConstraintsIfNecessary, z, this.viewportWidth, this.viewportHeight, this.viewportOrientationMayChange);
        }

        public Parameters withExceedVideoConstraintsIfNecessary(boolean z) {
            if (z == this.exceedVideoConstraintsIfNecessary) {
                return this;
            }
            return new Parameters(this.preferredAudioLanguage, this.preferredTextLanguage, this.allowMixedMimeAdaptiveness, this.allowNonSeamlessAdaptiveness, this.maxVideoWidth, this.maxVideoHeight, this.maxVideoBitrate, z, this.exceedRendererCapabilitiesIfNecessary, this.viewportWidth, this.viewportHeight, this.viewportOrientationMayChange);
        }

        public Parameters withMaxVideoBitrate(int i) {
            if (i == this.maxVideoBitrate) {
                return this;
            }
            return new Parameters(this.preferredAudioLanguage, this.preferredTextLanguage, this.allowMixedMimeAdaptiveness, this.allowNonSeamlessAdaptiveness, this.maxVideoWidth, this.maxVideoHeight, i, this.exceedVideoConstraintsIfNecessary, this.exceedRendererCapabilitiesIfNecessary, this.viewportWidth, this.viewportHeight, this.viewportOrientationMayChange);
        }

        public Parameters withMaxVideoSize(int i, int i2) {
            if (i == this.maxVideoWidth && i2 == this.maxVideoHeight) {
                return this;
            }
            return new Parameters(this.preferredAudioLanguage, this.preferredTextLanguage, this.allowMixedMimeAdaptiveness, this.allowNonSeamlessAdaptiveness, i, i2, this.maxVideoBitrate, this.exceedVideoConstraintsIfNecessary, this.exceedRendererCapabilitiesIfNecessary, this.viewportWidth, this.viewportHeight, this.viewportOrientationMayChange);
        }

        public Parameters withMaxVideoSizeSd() {
            return withMaxVideoSize(1279, 719);
        }

        public Parameters withPreferredAudioLanguage(String str) {
            Object normalizeLanguageCode = Util.normalizeLanguageCode(str);
            return TextUtils.equals(normalizeLanguageCode, this.preferredAudioLanguage) ? this : new Parameters(normalizeLanguageCode, this.preferredTextLanguage, this.allowMixedMimeAdaptiveness, this.allowNonSeamlessAdaptiveness, this.maxVideoWidth, this.maxVideoHeight, this.maxVideoBitrate, this.exceedVideoConstraintsIfNecessary, this.exceedRendererCapabilitiesIfNecessary, this.viewportWidth, this.viewportHeight, this.viewportOrientationMayChange);
        }

        public Parameters withPreferredTextLanguage(String str) {
            Object normalizeLanguageCode = Util.normalizeLanguageCode(str);
            return TextUtils.equals(normalizeLanguageCode, this.preferredTextLanguage) ? this : new Parameters(this.preferredAudioLanguage, normalizeLanguageCode, this.allowMixedMimeAdaptiveness, this.allowNonSeamlessAdaptiveness, this.maxVideoWidth, this.maxVideoHeight, this.maxVideoBitrate, this.exceedVideoConstraintsIfNecessary, this.exceedRendererCapabilitiesIfNecessary, this.viewportWidth, this.viewportHeight, this.viewportOrientationMayChange);
        }

        public Parameters withViewportSize(int i, int i2, boolean z) {
            return (i == this.viewportWidth && i2 == this.viewportHeight && z == this.viewportOrientationMayChange) ? this : new Parameters(this.preferredAudioLanguage, this.preferredTextLanguage, this.allowMixedMimeAdaptiveness, this.allowNonSeamlessAdaptiveness, this.maxVideoWidth, this.maxVideoHeight, this.maxVideoBitrate, this.exceedVideoConstraintsIfNecessary, this.exceedRendererCapabilitiesIfNecessary, i, i2, z);
        }

        public Parameters withViewportSizeFromContext(Context context, boolean z) {
            Point physicalDisplaySize = Util.getPhysicalDisplaySize(context);
            return withViewportSize(physicalDisplaySize.x, physicalDisplaySize.y, z);
        }

        public Parameters withoutVideoSizeConstraints() {
            return withMaxVideoSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        public Parameters withoutViewportSizeConstraints() {
            return withViewportSize(Integer.MAX_VALUE, Integer.MAX_VALUE, true);
        }
    }

    public DefaultTrackSelector() {
        this((Factory) null);
    }

    public DefaultTrackSelector(Factory factory) {
        this.adaptiveTrackSelectionFactory = factory;
        this.paramsReference = new AtomicReference(new Parameters());
    }

    public DefaultTrackSelector(BandwidthMeter bandwidthMeter) {
        this(new AdaptiveTrackSelection.Factory(bandwidthMeter));
    }

    private static int compareFormatValues(int i, int i2) {
        return i == -1 ? i2 == -1 ? 0 : -1 : i2 == -1 ? 1 : i - i2;
    }

    private static void filterAdaptiveVideoTrackCountForMimeType(TrackGroup trackGroup, int[] iArr, int i, String str, int i2, int i3, int i4, List<Integer> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            int intValue = ((Integer) list.get(size)).intValue();
            if (!isSupportedAdaptiveVideoTrack(trackGroup.getFormat(intValue), str, iArr[intValue], i, i2, i3, i4)) {
                list.remove(size);
            }
        }
    }

    protected static boolean formatHasLanguage(Format format, String str) {
        return str != null && TextUtils.equals(str, Util.normalizeLanguageCode(format.language));
    }

    private static int getAdaptiveAudioTrackCount(TrackGroup trackGroup, int[] iArr, AudioConfigurationTuple audioConfigurationTuple) {
        int i = 0;
        int i2 = 0;
        while (i < trackGroup.length) {
            if (isSupportedAdaptiveAudioTrack(trackGroup.getFormat(i), iArr[i], audioConfigurationTuple)) {
                i2++;
            }
            i++;
        }
        return i2;
    }

    private static int[] getAdaptiveAudioTracks(TrackGroup trackGroup, int[] iArr, boolean z) {
        int i = 0;
        HashSet hashSet = new HashSet();
        int i2 = 0;
        AudioConfigurationTuple audioConfigurationTuple = null;
        int i3 = 0;
        while (i2 < trackGroup.length) {
            int adaptiveAudioTrackCount;
            int i4;
            Format format = trackGroup.getFormat(i2);
            AudioConfigurationTuple audioConfigurationTuple2 = new AudioConfigurationTuple(format.channelCount, format.sampleRate, z ? null : format.sampleMimeType);
            if (hashSet.add(audioConfigurationTuple2)) {
                adaptiveAudioTrackCount = getAdaptiveAudioTrackCount(trackGroup, iArr, audioConfigurationTuple2);
                if (adaptiveAudioTrackCount > i3) {
                    i4 = adaptiveAudioTrackCount;
                    i2++;
                    i3 = i4;
                    audioConfigurationTuple = audioConfigurationTuple2;
                }
            }
            audioConfigurationTuple2 = audioConfigurationTuple;
            i4 = i3;
            i2++;
            i3 = i4;
            audioConfigurationTuple = audioConfigurationTuple2;
        }
        if (i3 <= 1) {
            return NO_TRACKS;
        }
        int[] iArr2 = new int[i3];
        int i5 = 0;
        while (i < trackGroup.length) {
            if (isSupportedAdaptiveAudioTrack(trackGroup.getFormat(i), iArr[i], audioConfigurationTuple)) {
                adaptiveAudioTrackCount = i5 + 1;
                iArr2[i5] = i;
                i5 = adaptiveAudioTrackCount;
            }
            i++;
        }
        return iArr2;
    }

    private static int getAdaptiveVideoTrackCountForMimeType(TrackGroup trackGroup, int[] iArr, int i, String str, int i2, int i3, int i4, List<Integer> list) {
        int i5 = 0;
        int i6 = 0;
        while (i5 < list.size()) {
            int intValue = ((Integer) list.get(i5)).intValue();
            i5++;
            i6 = isSupportedAdaptiveVideoTrack(trackGroup.getFormat(intValue), str, iArr[intValue], i, i2, i3, i4) ? i6 + 1 : i6;
        }
        return i6;
    }

    private static int[] getAdaptiveVideoTracksForGroup(TrackGroup trackGroup, int[] iArr, boolean z, int i, int i2, int i3, int i4, int i5, int i6, boolean z2) {
        if (trackGroup.length < 2) {
            return NO_TRACKS;
        }
        List viewportFilteredTrackIndices = getViewportFilteredTrackIndices(trackGroup, i5, i6, z2);
        if (viewportFilteredTrackIndices.size() < 2) {
            return NO_TRACKS;
        }
        String str;
        String str2 = null;
        if (z) {
            str = null;
        } else {
            HashSet hashSet = new HashSet();
            int i7 = 0;
            int i8 = 0;
            while (i8 < viewportFilteredTrackIndices.size()) {
                int adaptiveVideoTrackCountForMimeType;
                str = trackGroup.getFormat(((Integer) viewportFilteredTrackIndices.get(i8)).intValue()).sampleMimeType;
                if (hashSet.add(str)) {
                    adaptiveVideoTrackCountForMimeType = getAdaptiveVideoTrackCountForMimeType(trackGroup, iArr, i, str, i2, i3, i4, viewportFilteredTrackIndices);
                    if (adaptiveVideoTrackCountForMimeType > i7) {
                        i8++;
                        i7 = adaptiveVideoTrackCountForMimeType;
                        str2 = str;
                    }
                }
                adaptiveVideoTrackCountForMimeType = i7;
                str = str2;
                i8++;
                i7 = adaptiveVideoTrackCountForMimeType;
                str2 = str;
            }
            str = str2;
        }
        filterAdaptiveVideoTrackCountForMimeType(trackGroup, iArr, i, str, i2, i3, i4, viewportFilteredTrackIndices);
        return viewportFilteredTrackIndices.size() < 2 ? NO_TRACKS : Util.toArray(viewportFilteredTrackIndices);
    }

    private static int getAudioTrackScore(int i, String str, Format format) {
        int i2 = 1;
        if ((format.selectionFlags & 1) != 0) {
            int i3 = 1;
        } else {
            boolean z = false;
        }
        if (formatHasLanguage(format, str)) {
            i2 = i3 != 0 ? 4 : 3;
        } else if (i3 != 0) {
            i2 = 2;
        }
        return isSupported(i, false) ? i2 + 1000 : i2;
    }

    private static Point getMaxVideoSizeInViewport(boolean z, int i, int i2, int i3, int i4) {
        Object obj = 1;
        if (z) {
            Object obj2 = i3 > i4 ? 1 : null;
            if (i <= i2) {
                obj = null;
            }
            if (obj2 != obj) {
                int i5 = i;
                i = i2;
                i2 = i5;
            }
        }
        return i3 * i2 >= i4 * i ? new Point(i, Util.ceilDivide(i * i4, i3)) : new Point(Util.ceilDivide(i2 * i3, i4), i2);
    }

    private static List<Integer> getViewportFilteredTrackIndices(TrackGroup trackGroup, int i, int i2, boolean z) {
        int i3;
        int i4 = 0;
        ArrayList arrayList = new ArrayList(trackGroup.length);
        for (i3 = 0; i3 < trackGroup.length; i3++) {
            arrayList.add(Integer.valueOf(i3));
        }
        if (i == Integer.MAX_VALUE || i2 == Integer.MAX_VALUE) {
            return arrayList;
        }
        int i5 = Integer.MAX_VALUE;
        while (i4 < trackGroup.length) {
            Format format = trackGroup.getFormat(i4);
            if (format.width > 0 && format.height > 0) {
                Point maxVideoSizeInViewport = getMaxVideoSizeInViewport(z, i, i2, format.width, format.height);
                i3 = format.width * format.height;
                if (format.width >= ((int) (((float) maxVideoSizeInViewport.x) * FRACTION_TO_CONSIDER_FULLSCREEN)) && format.height >= ((int) (((float) maxVideoSizeInViewport.y) * FRACTION_TO_CONSIDER_FULLSCREEN)) && i3 < i5) {
                    i4++;
                    i5 = i3;
                }
            }
            i3 = i5;
            i4++;
            i5 = i3;
        }
        if (i5 != Integer.MAX_VALUE) {
            for (i4 = arrayList.size() - 1; i4 >= 0; i4--) {
                i3 = trackGroup.getFormat(((Integer) arrayList.get(i4)).intValue()).getPixelCount();
                if (i3 == -1 || i3 > i5) {
                    arrayList.remove(i4);
                }
            }
        }
        return arrayList;
    }

    protected static boolean isSupported(int i, boolean z) {
        int i2 = i & 7;
        return i2 == 4 || (z && i2 == 3);
    }

    private static boolean isSupportedAdaptiveAudioTrack(Format format, int i, AudioConfigurationTuple audioConfigurationTuple) {
        return (isSupported(i, false) && format.channelCount == audioConfigurationTuple.channelCount && format.sampleRate == audioConfigurationTuple.sampleRate) ? audioConfigurationTuple.mimeType == null || TextUtils.equals(audioConfigurationTuple.mimeType, format.sampleMimeType) : false;
    }

    private static boolean isSupportedAdaptiveVideoTrack(Format format, String str, int i, int i2, int i3, int i4, int i5) {
        return (!isSupported(i, false) || (i & i2) == 0) ? false : (str == null || Util.areEqual(format.sampleMimeType, str)) ? (format.width == -1 || format.width <= i3) ? (format.height == -1 || format.height <= i4) ? format.bitrate == -1 || format.bitrate <= i5 : false : false : false;
    }

    private static TrackSelection selectAdaptiveVideoTrack(RendererCapabilities rendererCapabilities, TrackGroupArray trackGroupArray, int[][] iArr, Parameters parameters, Factory factory) {
        int i = parameters.allowNonSeamlessAdaptiveness ? 24 : 16;
        boolean z = parameters.allowMixedMimeAdaptiveness && (rendererCapabilities.supportsMixedMimeTypeAdaptation() & i) != 0;
        for (int i2 = 0; i2 < trackGroupArray.length; i2++) {
            TrackGroup trackGroup = trackGroupArray.get(i2);
            int[] adaptiveVideoTracksForGroup = getAdaptiveVideoTracksForGroup(trackGroup, iArr[i2], z, i, parameters.maxVideoWidth, parameters.maxVideoHeight, parameters.maxVideoBitrate, parameters.viewportWidth, parameters.viewportHeight, parameters.viewportOrientationMayChange);
            if (adaptiveVideoTracksForGroup.length > 0) {
                return factory.createTrackSelection(trackGroup, adaptiveVideoTracksForGroup);
            }
        }
        return null;
    }

    private static TrackSelection selectFixedVideoTrack(TrackGroupArray trackGroupArray, int[][] iArr, Parameters parameters) {
        TrackGroup trackGroup = null;
        int i = 0;
        int i2 = -1;
        int i3 = -1;
        int i4 = 0;
        for (int i5 = 0; i5 < trackGroupArray.length; i5++) {
            TrackGroup trackGroup2 = trackGroupArray.get(i5);
            List viewportFilteredTrackIndices = getViewportFilteredTrackIndices(trackGroup2, parameters.viewportWidth, parameters.viewportHeight, parameters.viewportOrientationMayChange);
            int[] iArr2 = iArr[i5];
            int i6 = 0;
            while (i6 < trackGroup2.length) {
                int i7;
                TrackGroup trackGroup3;
                if (isSupported(iArr2[i6], parameters.exceedRendererCapabilitiesIfNecessary)) {
                    Format format = trackGroup2.getFormat(i6);
                    Object obj = (!viewportFilteredTrackIndices.contains(Integer.valueOf(i6)) || ((format.width != -1 && format.width > parameters.maxVideoWidth) || ((format.height != -1 && format.height > parameters.maxVideoHeight) || (format.bitrate != -1 && format.bitrate > parameters.maxVideoBitrate)))) ? null : 1;
                    if (obj != null || parameters.exceedVideoConstraintsIfNecessary) {
                        i7 = obj != null ? 2 : 1;
                        boolean isSupported = isSupported(iArr2[i6], false);
                        if (isSupported) {
                            i7 += 1000;
                        }
                        Object obj2 = i7 > i4 ? 1 : null;
                        if (i7 == i4) {
                            int compareFormatValues = format.getPixelCount() != i2 ? compareFormatValues(format.getPixelCount(), i2) : compareFormatValues(format.bitrate, i3);
                            obj2 = (!isSupported || obj == null) ? compareFormatValues < 0 ? 1 : null : compareFormatValues > 0 ? 1 : null;
                        }
                        if (obj2 != null) {
                            i3 = format.bitrate;
                            i2 = format.getPixelCount();
                            i4 = i7;
                            trackGroup3 = trackGroup2;
                            i7 = i6;
                            i6++;
                            trackGroup = trackGroup3;
                            i = i7;
                        }
                    } else {
                        i7 = i;
                        trackGroup3 = trackGroup;
                        i6++;
                        trackGroup = trackGroup3;
                        i = i7;
                    }
                }
                i7 = i;
                trackGroup3 = trackGroup;
                i6++;
                trackGroup = trackGroup3;
                i = i7;
            }
        }
        return trackGroup == null ? null : new FixedTrackSelection(trackGroup, i);
    }

    public Parameters getParameters() {
        return (Parameters) this.paramsReference.get();
    }

    protected TrackSelection selectAudioTrack(TrackGroupArray trackGroupArray, int[][] iArr, Parameters parameters, Factory factory) {
        int i = -1;
        int i2 = -1;
        int i3 = 0;
        for (int i4 = 0; i4 < trackGroupArray.length; i4++) {
            TrackGroup trackGroup = trackGroupArray.get(i4);
            int[] iArr2 = iArr[i4];
            int i5 = 0;
            while (i5 < trackGroup.length) {
                int audioTrackScore;
                if (isSupported(iArr2[i5], parameters.exceedRendererCapabilitiesIfNecessary)) {
                    audioTrackScore = getAudioTrackScore(iArr2[i5], parameters.preferredAudioLanguage, trackGroup.getFormat(i5));
                    if (audioTrackScore > i3) {
                        i2 = audioTrackScore;
                        i = i5;
                        audioTrackScore = i4;
                        i5++;
                        i3 = i2;
                        i2 = i;
                        i = audioTrackScore;
                    }
                }
                audioTrackScore = i;
                i = i2;
                i2 = i3;
                i5++;
                i3 = i2;
                i2 = i;
                i = audioTrackScore;
            }
        }
        if (i == -1) {
            return null;
        }
        TrackGroup trackGroup2 = trackGroupArray.get(i);
        if (factory != null) {
            int[] adaptiveAudioTracks = getAdaptiveAudioTracks(trackGroup2, iArr[i], parameters.allowMixedMimeAdaptiveness);
            if (adaptiveAudioTracks.length > 0) {
                return factory.createTrackSelection(trackGroup2, adaptiveAudioTracks);
            }
        }
        return new FixedTrackSelection(trackGroup2, i2);
    }

    protected TrackSelection selectOtherTrack(int i, TrackGroupArray trackGroupArray, int[][] iArr, Parameters parameters) {
        TrackGroup trackGroup = null;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < trackGroupArray.length; i4++) {
            TrackGroup trackGroup2 = trackGroupArray.get(i4);
            int[] iArr2 = iArr[i4];
            int i5 = 0;
            while (i5 < trackGroup2.length) {
                int i6;
                TrackGroup trackGroup3;
                if (isSupported(iArr2[i5], parameters.exceedRendererCapabilitiesIfNecessary)) {
                    i6 = ((trackGroup2.getFormat(i5).selectionFlags & 1) != 0 ? 1 : null) != null ? 2 : 1;
                    if (isSupported(iArr2[i5], false)) {
                        i6 += 1000;
                    }
                    if (i6 > i3) {
                        i3 = i5;
                        trackGroup3 = trackGroup2;
                        i5++;
                        trackGroup = trackGroup3;
                        i2 = i3;
                        i3 = i6;
                    }
                }
                i6 = i3;
                i3 = i2;
                trackGroup3 = trackGroup;
                i5++;
                trackGroup = trackGroup3;
                i2 = i3;
                i3 = i6;
            }
        }
        return trackGroup == null ? null : new FixedTrackSelection(trackGroup, i2);
    }

    protected TrackSelection selectTextTrack(TrackGroupArray trackGroupArray, int[][] iArr, Parameters parameters) {
        TrackGroup trackGroup = null;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < trackGroupArray.length; i3++) {
            TrackGroup trackGroup2 = trackGroupArray.get(i3);
            int[] iArr2 = iArr[i3];
            int i4 = 0;
            while (i4 < trackGroup2.length) {
                int i5;
                TrackGroup trackGroup3;
                if (isSupported(iArr2[i4], parameters.exceedRendererCapabilitiesIfNecessary)) {
                    Format format = trackGroup2.getFormat(i4);
                    Object obj = (format.selectionFlags & 1) != 0 ? 1 : null;
                    Object obj2 = (format.selectionFlags & 2) != 0 ? 1 : null;
                    if (formatHasLanguage(format, parameters.preferredTextLanguage)) {
                        i5 = obj != null ? 6 : obj2 == null ? 5 : 4;
                    } else if (obj != null) {
                        i5 = 3;
                    } else if (obj2 != null) {
                        i5 = formatHasLanguage(format, parameters.preferredAudioLanguage) ? 2 : 1;
                    }
                    if (isSupported(iArr2[i4], false)) {
                        i5 += 1000;
                    }
                    if (i5 > i2) {
                        i2 = i5;
                        trackGroup3 = trackGroup2;
                        i5 = i4;
                        i4++;
                        trackGroup = trackGroup3;
                        i = i5;
                    }
                }
                i5 = i;
                trackGroup3 = trackGroup;
                i4++;
                trackGroup = trackGroup3;
                i = i5;
            }
        }
        return trackGroup == null ? null : new FixedTrackSelection(trackGroup, i);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected org.telegram.messenger.exoplayer2.trackselection.TrackSelection[] selectTracks(org.telegram.messenger.exoplayer2.RendererCapabilities[] r12, org.telegram.messenger.exoplayer2.source.TrackGroupArray[] r13, int[][][] r14) {
        /*
        r11 = this;
        r8 = r12.length;
        r9 = new org.telegram.messenger.exoplayer2.trackselection.TrackSelection[r8];
        r0 = r11.paramsReference;
        r4 = r0.get();
        r4 = (org.telegram.messenger.exoplayer2.trackselection.DefaultTrackSelector.Parameters) r4;
        r6 = 0;
        r1 = 0;
        r0 = 0;
        r7 = r0;
        r0 = r1;
    L_0x0010:
        if (r7 >= r8) goto L_0x0042;
    L_0x0012:
        r1 = 2;
        r2 = r12[r7];
        r2 = r2.getTrackType();
        if (r1 != r2) goto L_0x009f;
    L_0x001b:
        if (r0 != 0) goto L_0x0031;
    L_0x001d:
        r1 = r12[r7];
        r2 = r13[r7];
        r3 = r14[r7];
        r5 = r11.adaptiveTrackSelectionFactory;
        r0 = r11;
        r0 = r0.selectVideoTrack(r1, r2, r3, r4, r5);
        r9[r7] = r0;
        r0 = r9[r7];
        if (r0 == 0) goto L_0x003e;
    L_0x0030:
        r0 = 1;
    L_0x0031:
        r1 = r13[r7];
        r1 = r1.length;
        if (r1 <= 0) goto L_0x0040;
    L_0x0037:
        r1 = 1;
    L_0x0038:
        r1 = r1 | r6;
    L_0x0039:
        r2 = r7 + 1;
        r7 = r2;
        r6 = r1;
        goto L_0x0010;
    L_0x003e:
        r0 = 0;
        goto L_0x0031;
    L_0x0040:
        r1 = 0;
        goto L_0x0038;
    L_0x0042:
        r2 = 0;
        r1 = 0;
        r0 = 0;
        r3 = r0;
    L_0x0046:
        if (r3 >= r8) goto L_0x009e;
    L_0x0048:
        r0 = r12[r3];
        r0 = r0.getTrackType();
        switch(r0) {
            case 1: goto L_0x006c;
            case 2: goto L_0x0069;
            case 3: goto L_0x0089;
            default: goto L_0x0051;
        };
    L_0x0051:
        r0 = r12[r3];
        r0 = r0.getTrackType();
        r5 = r13[r3];
        r7 = r14[r3];
        r0 = r11.selectOtherTrack(r0, r5, r7, r4);
        r9[r3] = r0;
    L_0x0061:
        r0 = r1;
        r1 = r2;
    L_0x0063:
        r2 = r3 + 1;
        r3 = r2;
        r2 = r1;
        r1 = r0;
        goto L_0x0046;
    L_0x0069:
        r0 = r1;
        r1 = r2;
        goto L_0x0063;
    L_0x006c:
        if (r2 != 0) goto L_0x0061;
    L_0x006e:
        r2 = r13[r3];
        r5 = r14[r3];
        if (r6 == 0) goto L_0x0084;
    L_0x0074:
        r0 = 0;
    L_0x0075:
        r0 = r11.selectAudioTrack(r2, r5, r4, r0);
        r9[r3] = r0;
        r0 = r9[r3];
        if (r0 == 0) goto L_0x0087;
    L_0x007f:
        r0 = 1;
    L_0x0080:
        r10 = r1;
        r1 = r0;
        r0 = r10;
        goto L_0x0063;
    L_0x0084:
        r0 = r11.adaptiveTrackSelectionFactory;
        goto L_0x0075;
    L_0x0087:
        r0 = 0;
        goto L_0x0080;
    L_0x0089:
        if (r1 != 0) goto L_0x0061;
    L_0x008b:
        r0 = r13[r3];
        r1 = r14[r3];
        r0 = r11.selectTextTrack(r0, r1, r4);
        r9[r3] = r0;
        r0 = r9[r3];
        if (r0 == 0) goto L_0x009c;
    L_0x0099:
        r0 = 1;
    L_0x009a:
        r1 = r2;
        goto L_0x0063;
    L_0x009c:
        r0 = 0;
        goto L_0x009a;
    L_0x009e:
        return r9;
    L_0x009f:
        r1 = r6;
        goto L_0x0039;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.trackselection.DefaultTrackSelector.selectTracks(org.telegram.messenger.exoplayer2.RendererCapabilities[], org.telegram.messenger.exoplayer2.source.TrackGroupArray[], int[][][]):org.telegram.messenger.exoplayer2.trackselection.TrackSelection[]");
    }

    protected TrackSelection selectVideoTrack(RendererCapabilities rendererCapabilities, TrackGroupArray trackGroupArray, int[][] iArr, Parameters parameters, Factory factory) {
        TrackSelection trackSelection = null;
        if (factory != null) {
            trackSelection = selectAdaptiveVideoTrack(rendererCapabilities, trackGroupArray, iArr, parameters, factory);
        }
        return trackSelection == null ? selectFixedVideoTrack(trackGroupArray, iArr, parameters) : trackSelection;
    }

    public void setParameters(Parameters parameters) {
        Assertions.checkNotNull(parameters);
        if (!((Parameters) this.paramsReference.getAndSet(parameters)).equals(parameters)) {
            invalidate();
        }
    }
}
