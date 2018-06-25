package org.telegram.messenger.video;

import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import com.p054b.p055a.p056a.C1248b;
import com.p054b.p055a.p056a.C1260a;
import com.p054b.p055a.p056a.C1274q;
import com.p054b.p055a.p056a.C1279u;
import com.p054b.p055a.p056a.aa;
import com.p054b.p055a.p056a.p059a.C1253b;
import com.p054b.p055a.p056a.p059a.C1256d;
import com.p057c.p058a.p060a.p061a.C1312b;
import com.p057c.p058a.p060a.p061a.p062a.C1297a;
import com.p057c.p058a.p060a.p061a.p062a.C1300e;
import com.p057c.p058a.p060a.p061a.p062a.C1303h;
import com.p057c.p058a.p060a.p061a.p062a.C1309n;
import com.p069d.p070a.p071a.C1479a;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.tgnet.TLRPC;

public class Track {
    private static Map<Integer, Integer> samplingFrequencyIndexMap = new HashMap();
    private Date creationTime = new Date();
    private long duration = 0;
    private boolean first = true;
    private String handler;
    private C1260a headerBox = null;
    private int height;
    private boolean isAudio = false;
    private int[] sampleCompositions;
    private C1274q sampleDescriptionBox = null;
    private long[] sampleDurations;
    private ArrayList<SamplePresentationTime> samplePresentationTimes = new ArrayList();
    private ArrayList<Sample> samples = new ArrayList();
    private LinkedList<Integer> syncSamples = null;
    private int timeScale;
    private long trackId = 0;
    private float volume = BitmapDescriptorFactory.HUE_RED;
    private int width;

    /* renamed from: org.telegram.messenger.video.Track$1 */
    class C37011 implements Comparator<SamplePresentationTime> {
        C37011() {
        }

        public int compare(SamplePresentationTime samplePresentationTime, SamplePresentationTime samplePresentationTime2) {
            return samplePresentationTime.presentationTime > samplePresentationTime2.presentationTime ? 1 : samplePresentationTime.presentationTime < samplePresentationTime2.presentationTime ? -1 : 0;
        }
    }

    private class SamplePresentationTime {
        private long dt;
        private int index;
        private long presentationTime;

        public SamplePresentationTime(int i, long j) {
            this.index = i;
            this.presentationTime = j;
        }
    }

    static {
        samplingFrequencyIndexMap.put(Integer.valueOf(96000), Integer.valueOf(0));
        samplingFrequencyIndexMap.put(Integer.valueOf(88200), Integer.valueOf(1));
        samplingFrequencyIndexMap.put(Integer.valueOf(64000), Integer.valueOf(2));
        samplingFrequencyIndexMap.put(Integer.valueOf(48000), Integer.valueOf(3));
        samplingFrequencyIndexMap.put(Integer.valueOf(44100), Integer.valueOf(4));
        samplingFrequencyIndexMap.put(Integer.valueOf(32000), Integer.valueOf(5));
        samplingFrequencyIndexMap.put(Integer.valueOf(24000), Integer.valueOf(6));
        samplingFrequencyIndexMap.put(Integer.valueOf(22050), Integer.valueOf(7));
        samplingFrequencyIndexMap.put(Integer.valueOf(16000), Integer.valueOf(8));
        samplingFrequencyIndexMap.put(Integer.valueOf(12000), Integer.valueOf(9));
        samplingFrequencyIndexMap.put(Integer.valueOf(11025), Integer.valueOf(10));
        samplingFrequencyIndexMap.put(Integer.valueOf(8000), Integer.valueOf(11));
    }

    public Track(int i, MediaFormat mediaFormat, boolean z) {
        this.trackId = (long) i;
        this.isAudio = z;
        if (this.isAudio) {
            this.volume = 1.0f;
            this.timeScale = mediaFormat.getInteger("sample-rate");
            this.handler = "soun";
            this.headerBox = new C1279u();
            this.sampleDescriptionBox = new C1274q();
            C1248b c1253b = new C1253b("mp4a");
            c1253b.m6492b(mediaFormat.getInteger("channel-count"));
            c1253b.m6490a((long) mediaFormat.getInteger("sample-rate"));
            c1253b.m6489a(1);
            c1253b.m6494c(16);
            C1248b c1312b = new C1312b();
            C1303h c1303h = new C1303h();
            c1303h.m6730a(0);
            C1309n c1309n = new C1309n();
            c1309n.m6740a(2);
            c1303h.m6732a(c1309n);
            C1300e c1300e = new C1300e();
            c1300e.m6718a(64);
            c1300e.m6723b(5);
            c1300e.m6725c(1536);
            if (mediaFormat.containsKey("max-bitrate")) {
                c1300e.m6719a((long) mediaFormat.getInteger("max-bitrate"));
            } else {
                c1300e.m6719a(96000);
            }
            c1300e.m6724b((long) this.timeScale);
            C1297a c1297a = new C1297a();
            c1297a.m6708a(2);
            c1297a.m6711b(((Integer) samplingFrequencyIndexMap.get(Integer.valueOf((int) c1253b.m6493c()))).intValue());
            c1297a.m6712c(c1253b.m6491b());
            c1300e.m6720a(c1297a);
            c1303h.m6731a(c1300e);
            ByteBuffer b = c1303h.m6734b();
            c1312b.m6750a(c1303h);
            c1312b.m6748e(b);
            c1253b.m6482a(c1312b);
            this.sampleDescriptionBox.m6482a(c1253b);
            return;
        }
        this.width = mediaFormat.getInteger("width");
        this.height = mediaFormat.getInteger("height");
        this.timeScale = 90000;
        this.syncSamples = new LinkedList();
        this.handler = "vide";
        this.headerBox = new aa();
        this.sampleDescriptionBox = new C1274q();
        String string = mediaFormat.getString("mime");
        C1248b c1256d;
        if (string.equals("video/avc")) {
            int integer;
            c1256d = new C1256d("avc1");
            c1256d.m6489a(1);
            c1256d.m6516e(24);
            c1256d.m6514d(1);
            c1256d.m6507a(72.0d);
            c1256d.m6509b(72.0d);
            c1256d.m6510b(this.width);
            c1256d.m6512c(this.height);
            C1479a c1479a = new C1479a();
            if (mediaFormat.getByteBuffer("csd-0") != null) {
                List arrayList = new ArrayList();
                ByteBuffer byteBuffer = mediaFormat.getByteBuffer("csd-0");
                byteBuffer.position(4);
                Object obj = new byte[byteBuffer.remaining()];
                byteBuffer.get(obj);
                arrayList.add(obj);
                List arrayList2 = new ArrayList();
                ByteBuffer byteBuffer2 = mediaFormat.getByteBuffer("csd-1");
                byteBuffer2.position(4);
                Object obj2 = new byte[byteBuffer2.remaining()];
                byteBuffer2.get(obj2);
                arrayList2.add(obj2);
                c1479a.m7321a(arrayList);
                c1479a.m7324b(arrayList2);
            }
            if (mediaFormat.containsKey(C1797b.LEVEL)) {
                integer = mediaFormat.getInteger(C1797b.LEVEL);
                if (integer == 1) {
                    c1479a.m7326d(1);
                } else if (integer == 32) {
                    c1479a.m7326d(2);
                } else if (integer == 4) {
                    c1479a.m7326d(11);
                } else if (integer == 8) {
                    c1479a.m7326d(12);
                } else if (integer == 16) {
                    c1479a.m7326d(13);
                } else if (integer == 64) {
                    c1479a.m7326d(21);
                } else if (integer == 128) {
                    c1479a.m7326d(22);
                } else if (integer == 256) {
                    c1479a.m7326d(3);
                } else if (integer == 512) {
                    c1479a.m7326d(31);
                } else if (integer == 1024) {
                    c1479a.m7326d(32);
                } else if (integer == 2048) {
                    c1479a.m7326d(4);
                } else if (integer == 4096) {
                    c1479a.m7326d(41);
                } else if (integer == MessagesController.UPDATE_MASK_CHANNEL) {
                    c1479a.m7326d(42);
                } else if (integer == MessagesController.UPDATE_MASK_CHAT_ADMINS) {
                    c1479a.m7326d(5);
                } else if (integer == TLRPC.MESSAGE_FLAG_EDITED) {
                    c1479a.m7326d(51);
                } else if (integer == C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) {
                    c1479a.m7326d(52);
                } else if (integer == 2) {
                    c1479a.m7326d(27);
                }
            } else {
                c1479a.m7326d(13);
            }
            if (mediaFormat.containsKey(Scopes.PROFILE)) {
                integer = mediaFormat.getInteger(Scopes.PROFILE);
                if (integer == 1) {
                    c1479a.m7322b(66);
                } else if (integer == 2) {
                    c1479a.m7322b(77);
                } else if (integer == 4) {
                    c1479a.m7322b(88);
                } else if (integer == 8) {
                    c1479a.m7322b(100);
                } else if (integer == 16) {
                    c1479a.m7322b(110);
                } else if (integer == 32) {
                    c1479a.m7322b(122);
                } else if (integer == 64) {
                    c1479a.m7322b(244);
                }
            } else {
                c1479a.m7322b(100);
            }
            c1479a.m7329g(-1);
            c1479a.m7330h(-1);
            c1479a.m7328f(-1);
            c1479a.m7319a(1);
            c1479a.m7327e(3);
            c1479a.m7325c(0);
            c1256d.m6482a((C1248b) c1479a);
            this.sampleDescriptionBox.m6482a(c1256d);
        } else if (string.equals("video/mp4v")) {
            c1256d = new C1256d("mp4v");
            c1256d.m6489a(1);
            c1256d.m6516e(24);
            c1256d.m6514d(1);
            c1256d.m6507a(72.0d);
            c1256d.m6509b(72.0d);
            c1256d.m6510b(this.width);
            c1256d.m6512c(this.height);
            this.sampleDescriptionBox.m6482a(c1256d);
        }
    }

    public void addSample(long j, BufferInfo bufferInfo) {
        Object obj = (this.isAudio || (bufferInfo.flags & 1) == 0) ? null : 1;
        this.samples.add(new Sample(j, (long) bufferInfo.size));
        if (!(this.syncSamples == null || obj == null)) {
            this.syncSamples.add(Integer.valueOf(this.samples.size()));
        }
        this.samplePresentationTimes.add(new SamplePresentationTime(this.samplePresentationTimes.size(), ((bufferInfo.presentationTimeUs * ((long) this.timeScale)) + 500000) / C3446C.MICROS_PER_SECOND));
    }

    public Date getCreationTime() {
        return this.creationTime;
    }

    public long getDuration() {
        return this.duration;
    }

    public String getHandler() {
        return this.handler;
    }

    public int getHeight() {
        return this.height;
    }

    public C1260a getMediaHeaderBox() {
        return this.headerBox;
    }

    public int[] getSampleCompositions() {
        return this.sampleCompositions;
    }

    public C1274q getSampleDescriptionBox() {
        return this.sampleDescriptionBox;
    }

    public long[] getSampleDurations() {
        return this.sampleDurations;
    }

    public ArrayList<Sample> getSamples() {
        return this.samples;
    }

    public long[] getSyncSamples() {
        if (this.syncSamples == null || this.syncSamples.isEmpty()) {
            return null;
        }
        long[] jArr = new long[this.syncSamples.size()];
        for (int i = 0; i < this.syncSamples.size(); i++) {
            jArr[i] = (long) ((Integer) this.syncSamples.get(i)).intValue();
        }
        return jArr;
    }

    public int getTimeScale() {
        return this.timeScale;
    }

    public long getTrackId() {
        return this.trackId;
    }

    public float getVolume() {
        return this.volume;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isAudio() {
        return this.isAudio;
    }

    public void prepare() {
        int i;
        ArrayList arrayList = new ArrayList(this.samplePresentationTimes);
        Collections.sort(this.samplePresentationTimes, new C37011());
        this.sampleDurations = new long[this.samplePresentationTimes.size()];
        long j = 0;
        long j2 = Long.MAX_VALUE;
        Object obj = null;
        for (i = 0; i < this.samplePresentationTimes.size(); i++) {
            SamplePresentationTime samplePresentationTime = (SamplePresentationTime) this.samplePresentationTimes.get(i);
            long access$000 = samplePresentationTime.presentationTime - j;
            j = samplePresentationTime.presentationTime;
            this.sampleDurations[samplePresentationTime.index] = access$000;
            if (samplePresentationTime.index != 0) {
                this.duration += access$000;
            }
            if (access$000 != 0) {
                j2 = Math.min(j2, access$000);
            }
            if (samplePresentationTime.index != i) {
                obj = 1;
            }
        }
        if (this.sampleDurations.length > 0) {
            this.sampleDurations[0] = j2;
            this.duration += j2;
        }
        for (int i2 = 1; i2 < arrayList.size(); i2++) {
            ((SamplePresentationTime) arrayList.get(i2)).dt = this.sampleDurations[i2] + ((SamplePresentationTime) arrayList.get(i2 - 1)).dt;
        }
        if (obj != null) {
            this.sampleCompositions = new int[this.samplePresentationTimes.size()];
            for (i = 0; i < this.samplePresentationTimes.size(); i++) {
                samplePresentationTime = (SamplePresentationTime) this.samplePresentationTimes.get(i);
                this.sampleCompositions[samplePresentationTime.index] = (int) (samplePresentationTime.presentationTime - samplePresentationTime.dt);
            }
        }
    }
}
