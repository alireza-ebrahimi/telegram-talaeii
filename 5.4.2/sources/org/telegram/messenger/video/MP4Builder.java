package org.telegram.messenger.video;

import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import com.p054b.p055a.C1286b;
import com.p054b.p055a.C1289d;
import com.p054b.p055a.C1291f;
import com.p054b.p055a.p056a.C1246e;
import com.p054b.p055a.p056a.C1248b;
import com.p054b.p055a.p056a.C1263d;
import com.p054b.p055a.p056a.C1263d.C1262a;
import com.p054b.p055a.p056a.C1264f;
import com.p054b.p055a.p056a.C1265g;
import com.p054b.p055a.p056a.C1266h;
import com.p054b.p055a.p056a.C1267i;
import com.p054b.p055a.p056a.C1268k;
import com.p054b.p055a.p056a.C1269l;
import com.p054b.p055a.p056a.C1270m;
import com.p054b.p055a.p056a.C1271n;
import com.p054b.p055a.p056a.C1272o;
import com.p054b.p055a.p056a.C1273p;
import com.p054b.p055a.p056a.C1275r;
import com.p054b.p055a.p056a.C1276s;
import com.p054b.p055a.p056a.C1278t;
import com.p054b.p055a.p056a.C1278t.C1277a;
import com.p054b.p055a.p056a.C1280v;
import com.p054b.p055a.p056a.C1281w;
import com.p054b.p055a.p056a.C1283x;
import com.p054b.p055a.p056a.C1283x.C1282a;
import com.p054b.p055a.p056a.C1284y;
import com.p054b.p055a.p056a.C1285z;
import com.p057c.p058a.C1254e;
import com.p057c.p058a.p063b.C1320g;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MP4Builder {
    private Mp4Movie currentMp4Movie = null;
    private long dataOffset = 0;
    private FileChannel fc = null;
    private FileOutputStream fos = null;
    private InterleaveChunkMdat mdat = null;
    private ByteBuffer sizeBuffer = null;
    private HashMap<Track, long[]> track2SampleSizes = new HashMap();
    private boolean writeNewMdat = true;
    private long writedSinceLastMdat = 0;

    private class InterleaveChunkMdat implements C1248b {
        private long contentSize;
        private long dataOffset;
        private C1246e parent;

        private InterleaveChunkMdat() {
            this.contentSize = 1073741824;
            this.dataOffset = 0;
        }

        private boolean isSmallBox(long j) {
            return 8 + j < 4294967296L;
        }

        public void getBox(WritableByteChannel writableByteChannel) {
            ByteBuffer allocate = ByteBuffer.allocate(16);
            long size = getSize();
            if (isSmallBox(size)) {
                C1291f.m6684b(allocate, size);
            } else {
                C1291f.m6684b(allocate, 1);
            }
            allocate.put(C1289d.m6665a("mdat"));
            if (isSmallBox(size)) {
                allocate.put(new byte[8]);
            } else {
                C1291f.m6680a(allocate, size);
            }
            allocate.rewind();
            writableByteChannel.write(allocate);
        }

        public long getContentSize() {
            return this.contentSize;
        }

        public long getOffset() {
            return this.dataOffset;
        }

        public C1246e getParent() {
            return this.parent;
        }

        public long getSize() {
            return 16 + this.contentSize;
        }

        public String getType() {
            return "mdat";
        }

        public void parse(C1254e c1254e, ByteBuffer byteBuffer, long j, C1286b c1286b) {
        }

        public void setContentSize(long j) {
            this.contentSize = j;
        }

        public void setDataOffset(long j) {
            this.dataOffset = j;
        }

        public void setParent(C1246e c1246e) {
            this.parent = c1246e;
        }
    }

    private void flushCurrentMdat() {
        long position = this.fc.position();
        this.fc.position(this.mdat.getOffset());
        this.mdat.getBox(this.fc);
        this.fc.position(position);
        this.mdat.setDataOffset(0);
        this.mdat.setContentSize(0);
        this.fos.flush();
    }

    public static long gcd(long j, long j2) {
        return j2 == 0 ? j : gcd(j2, j % j2);
    }

    public int addTrack(MediaFormat mediaFormat, boolean z) {
        return this.currentMp4Movie.addTrack(mediaFormat, z);
    }

    protected void createCtts(Track track, C1276s c1276s) {
        int[] sampleCompositions = track.getSampleCompositions();
        if (sampleCompositions != null) {
            List arrayList = new ArrayList();
            C1262a c1262a = null;
            for (int i : sampleCompositions) {
                if (c1262a == null || c1262a.m6545b() != i) {
                    c1262a = new C1262a(1, i);
                    arrayList.add(c1262a);
                } else {
                    c1262a.m6544a(c1262a.m6543a() + 1);
                }
            }
            C1248b c1263d = new C1263d();
            c1263d.m6548a(arrayList);
            c1276s.m6482a(c1263d);
        }
    }

    protected C1267i createFileTypeBox() {
        List linkedList = new LinkedList();
        linkedList.add("isom");
        linkedList.add("iso2");
        linkedList.add("avc1");
        linkedList.add("mp41");
        return new C1267i("isom", 512, linkedList);
    }

    public MP4Builder createMovie(Mp4Movie mp4Movie) {
        this.currentMp4Movie = mp4Movie;
        this.fos = new FileOutputStream(mp4Movie.getCacheFile());
        this.fc = this.fos.getChannel();
        C1267i createFileTypeBox = createFileTypeBox();
        createFileTypeBox.getBox(this.fc);
        this.dataOffset = createFileTypeBox.getSize() + this.dataOffset;
        this.writedSinceLastMdat += this.dataOffset;
        this.mdat = new InterleaveChunkMdat();
        this.sizeBuffer = ByteBuffer.allocateDirect(4);
        return this;
    }

    protected C1272o createMovieBox(Mp4Movie mp4Movie) {
        C1272o c1272o = new C1272o();
        C1248b c1273p = new C1273p();
        c1273p.m6584a(new Date());
        c1273p.m6588b(new Date());
        c1273p.m6582a(C1320g.f3985j);
        long timescale = getTimescale(mp4Movie);
        Iterator it = mp4Movie.getTracks().iterator();
        long j = 0;
        while (it.hasNext()) {
            Track track = (Track) it.next();
            track.prepare();
            long duration = (track.getDuration() * timescale) / ((long) track.getTimeScale());
            if (duration <= j) {
                duration = j;
            }
            j = duration;
        }
        c1273p.m6586b(j);
        c1273p.m6581a(timescale);
        c1273p.m6590c((long) (mp4Movie.getTracks().size() + 1));
        c1272o.m6482a(c1273p);
        Iterator it2 = mp4Movie.getTracks().iterator();
        while (it2.hasNext()) {
            c1272o.m6482a(createTrackBox((Track) it2.next(), mp4Movie));
        }
        return c1272o;
    }

    protected C1248b createStbl(Track track) {
        C1248b c1276s = new C1276s();
        createStsd(track, c1276s);
        createStts(track, c1276s);
        createCtts(track, c1276s);
        createStss(track, c1276s);
        createStsc(track, c1276s);
        createStsz(track, c1276s);
        createStco(track, c1276s);
        return c1276s;
    }

    protected void createStco(Track track, C1276s c1276s) {
        ArrayList arrayList = new ArrayList();
        Iterator it = track.getSamples().iterator();
        long j = -1;
        while (it.hasNext()) {
            Sample sample = (Sample) it.next();
            long offset = sample.getOffset();
            if (!(j == -1 || j == offset)) {
                j = -1;
            }
            if (j == -1) {
                arrayList.add(Long.valueOf(offset));
            }
            j = sample.getSize() + offset;
        }
        long[] jArr = new long[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            jArr[i] = ((Long) arrayList.get(i)).longValue();
        }
        C1248b c1280v = new C1280v();
        c1280v.m6618a(jArr);
        c1276s.m6482a(c1280v);
    }

    protected void createStsc(Track track, C1276s c1276s) {
        C1248b c1278t = new C1278t();
        c1278t.m6609a(new LinkedList());
        int i = 1;
        int i2 = 0;
        int i3 = -1;
        int size = track.getSamples().size();
        int i4 = 0;
        while (i4 < size) {
            int i5;
            int i6;
            Sample sample = (Sample) track.getSamples().get(i4);
            i2++;
            Object obj = i4 != size + -1 ? sample.getOffset() + sample.getSize() != ((Sample) track.getSamples().get(i4 + 1)).getOffset() ? 1 : null : 1;
            if (obj != null) {
                if (i3 != i2) {
                    c1278t.mo1112b().add(new C1277a((long) i, (long) i2, 1));
                    i5 = i2;
                } else {
                    i5 = i3;
                }
                i2 = 0;
                i6 = i + 1;
            } else {
                i5 = i3;
                i6 = i;
            }
            i4++;
            i = i6;
            i3 = i5;
        }
        c1276s.m6482a(c1278t);
    }

    protected void createStsd(Track track, C1276s c1276s) {
        c1276s.m6482a(track.getSampleDescriptionBox());
    }

    protected void createStss(Track track, C1276s c1276s) {
        long[] syncSamples = track.getSyncSamples();
        if (syncSamples != null && syncSamples.length > 0) {
            C1248b c1281w = new C1281w();
            c1281w.m6623a(syncSamples);
            c1276s.m6482a(c1281w);
        }
    }

    protected void createStsz(Track track, C1276s c1276s) {
        C1248b c1275r = new C1275r();
        c1275r.m6598a((long[]) this.track2SampleSizes.get(track));
        c1276s.m6482a(c1275r);
    }

    protected void createStts(Track track, C1276s c1276s) {
        List arrayList = new ArrayList();
        long[] sampleDurations = track.getSampleDurations();
        C1282a c1282a = null;
        for (long j : sampleDurations) {
            if (c1282a == null || c1282a.m6627b() != j) {
                c1282a = new C1282a(1, j);
                arrayList.add(c1282a);
            } else {
                c1282a.m6626a(c1282a.m6625a() + 1);
            }
        }
        C1248b c1283x = new C1283x();
        c1283x.m6630a(arrayList);
        c1276s.m6482a(c1283x);
    }

    protected C1284y createTrackBox(Track track, Mp4Movie mp4Movie) {
        C1284y c1284y = new C1284y();
        Object c1285z = new C1285z();
        c1285z.m6642a(true);
        c1285z.m6649b(true);
        c1285z.m6651c(true);
        if (track.isAudio()) {
            c1285z.m6639a(C1320g.f3985j);
        } else {
            c1285z.m6639a(mp4Movie.getMatrix());
        }
        c1285z.m6645b(0);
        c1285z.m6641a(track.getCreationTime());
        c1285z.m6646b((track.getDuration() * getTimescale(mp4Movie)) / ((long) track.getTimeScale()));
        c1285z.m6644b((double) track.getHeight());
        c1285z.m6635a((double) track.getWidth());
        c1285z.m6637a(0);
        c1285z.m6648b(new Date());
        c1285z.m6638a(track.getTrackId() + 1);
        c1285z.m6636a(track.getVolume());
        c1284y.m6482a((C1248b) c1285z);
        C1269l c1269l = new C1269l();
        c1284y.m6482a((C1248b) c1269l);
        C1248b c1270m = new C1270m();
        c1270m.m6571a(track.getCreationTime());
        c1270m.m6573b(track.getDuration());
        c1270m.m6568a((long) track.getTimeScale());
        c1270m.m6569a("eng");
        c1269l.m6482a(c1270m);
        C1248b c1268k = new C1268k();
        c1268k.m6559a(track.isAudio() ? "SoundHandle" : "VideoHandle");
        c1268k.m6562b(track.getHandler());
        c1269l.m6482a(c1268k);
        c1270m = new C1271n();
        c1270m.m6482a(track.getMediaHeaderBox());
        c1268k = new C1265g();
        Object c1266h = new C1266h();
        c1268k.m6482a((C1248b) c1266h);
        C1248b c1264f = new C1264f();
        c1264f.m6532d(1);
        c1266h.m6482a(c1264f);
        c1270m.m6482a(c1268k);
        c1270m.m6482a(createStbl(track));
        c1269l.m6482a(c1270m);
        return c1284y;
    }

    public void finishMovie() {
        if (this.mdat.getContentSize() != 0) {
            flushCurrentMdat();
        }
        Iterator it = this.currentMp4Movie.getTracks().iterator();
        while (it.hasNext()) {
            Track track = (Track) it.next();
            List samples = track.getSamples();
            Object obj = new long[samples.size()];
            for (int i = 0; i < obj.length; i++) {
                obj[i] = ((Sample) samples.get(i)).getSize();
            }
            this.track2SampleSizes.put(track, obj);
        }
        createMovieBox(this.currentMp4Movie).getBox(this.fc);
        this.fos.flush();
        this.fc.close();
        this.fos.close();
    }

    public long getTimescale(Mp4Movie mp4Movie) {
        long j = 0;
        if (!mp4Movie.getTracks().isEmpty()) {
            j = (long) ((Track) mp4Movie.getTracks().iterator().next()).getTimeScale();
        }
        Iterator it = mp4Movie.getTracks().iterator();
        long j2 = j;
        while (it.hasNext()) {
            j2 = gcd((long) ((Track) it.next()).getTimeScale(), j2);
        }
        return j2;
    }

    public boolean writeSampleData(int i, ByteBuffer byteBuffer, BufferInfo bufferInfo, boolean z) {
        boolean z2 = true;
        if (this.writeNewMdat) {
            this.mdat.setContentSize(0);
            this.mdat.getBox(this.fc);
            this.mdat.setDataOffset(this.dataOffset);
            this.dataOffset += 16;
            this.writedSinceLastMdat += 16;
            this.writeNewMdat = false;
        }
        this.mdat.setContentSize(this.mdat.getContentSize() + ((long) bufferInfo.size));
        this.writedSinceLastMdat += (long) bufferInfo.size;
        if (this.writedSinceLastMdat >= 32768) {
            flushCurrentMdat();
            this.writeNewMdat = true;
            this.writedSinceLastMdat -= 32768;
        } else {
            z2 = false;
        }
        this.currentMp4Movie.addSample(i, this.dataOffset, bufferInfo);
        byteBuffer.position((!z ? 0 : 4) + bufferInfo.offset);
        byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
        if (z) {
            this.sizeBuffer.position(0);
            this.sizeBuffer.putInt(bufferInfo.size - 4);
            this.sizeBuffer.position(0);
            this.fc.write(this.sizeBuffer);
        }
        this.fc.write(byteBuffer);
        this.dataOffset += (long) bufferInfo.size;
        if (z2) {
            this.fos.flush();
        }
        return z2;
    }
}
