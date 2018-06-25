package org.telegram.messenger.exoplayer2.extractor;

import java.lang.reflect.Constructor;
import org.telegram.messenger.exoplayer2.extractor.flv.FlvExtractor;
import org.telegram.messenger.exoplayer2.extractor.mkv.MatroskaExtractor;
import org.telegram.messenger.exoplayer2.extractor.mp3.Mp3Extractor;
import org.telegram.messenger.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import org.telegram.messenger.exoplayer2.extractor.mp4.Mp4Extractor;
import org.telegram.messenger.exoplayer2.extractor.ogg.OggExtractor;
import org.telegram.messenger.exoplayer2.extractor.ts.Ac3Extractor;
import org.telegram.messenger.exoplayer2.extractor.ts.AdtsExtractor;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;
import org.telegram.messenger.exoplayer2.extractor.wav.WavExtractor;

public final class DefaultExtractorsFactory implements ExtractorsFactory {
    private static final Constructor<? extends Extractor> FLAC_EXTRACTOR_CONSTRUCTOR;
    private int fragmentedMp4Flags;
    private int matroskaFlags;
    private int mp3Flags;
    private int mp4Flags;
    private int tsFlags;
    private int tsMode = 1;

    static {
        Constructor constructor = null;
        try {
            constructor = Class.forName("org.telegram.messenger.exoplayer2.ext.flac.FlacExtractor").asSubclass(Extractor.class).getConstructor(new Class[0]);
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e2) {
        }
        FLAC_EXTRACTOR_CONSTRUCTOR = constructor;
    }

    public synchronized Extractor[] createExtractors() {
        Extractor[] extractorArr;
        int i = 11;
        synchronized (this) {
            if (FLAC_EXTRACTOR_CONSTRUCTOR != null) {
                i = 12;
            }
            extractorArr = new Extractor[i];
            extractorArr[0] = new MatroskaExtractor(this.matroskaFlags);
            extractorArr[1] = new FragmentedMp4Extractor(this.fragmentedMp4Flags);
            extractorArr[2] = new Mp4Extractor(this.mp4Flags);
            extractorArr[3] = new Mp3Extractor(this.mp3Flags);
            extractorArr[4] = new AdtsExtractor();
            extractorArr[5] = new Ac3Extractor();
            extractorArr[6] = new TsExtractor(this.tsMode, this.tsFlags);
            extractorArr[7] = new FlvExtractor();
            extractorArr[8] = new OggExtractor();
            extractorArr[9] = new PsExtractor();
            extractorArr[10] = new WavExtractor();
            if (FLAC_EXTRACTOR_CONSTRUCTOR != null) {
                try {
                    extractorArr[11] = (Extractor) FLAC_EXTRACTOR_CONSTRUCTOR.newInstance(new Object[0]);
                } catch (Throwable e) {
                    throw new IllegalStateException("Unexpected error creating FLAC extractor", e);
                }
            }
        }
        return extractorArr;
    }

    public synchronized DefaultExtractorsFactory setFragmentedMp4ExtractorFlags(int i) {
        this.fragmentedMp4Flags = i;
        return this;
    }

    public synchronized DefaultExtractorsFactory setMatroskaExtractorFlags(int i) {
        this.matroskaFlags = i;
        return this;
    }

    public synchronized DefaultExtractorsFactory setMp3ExtractorFlags(int i) {
        this.mp3Flags = i;
        return this;
    }

    public synchronized DefaultExtractorsFactory setMp4ExtractorFlags(int i) {
        this.mp4Flags = i;
        return this;
    }

    public synchronized DefaultExtractorsFactory setTsExtractorFlags(int i) {
        this.tsFlags = i;
        return this;
    }

    public synchronized DefaultExtractorsFactory setTsExtractorMode(int i) {
        this.tsMode = i;
        return this;
    }
}
