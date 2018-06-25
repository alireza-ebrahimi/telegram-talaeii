package com.coremedia.iso.boxes;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractFullBox;
import com.googlecode.mp4parser.RequiresParseDetailAspect;
import com.googlecode.mp4parser.util.CastUtils;
import com.thin.downloadmanager.BuildConfig;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.runtime.reflect.Factory;

public class TimeToSampleBox extends AbstractFullBox {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final String TYPE = "stts";
    private static final /* synthetic */ StaticPart ajc$tjp_0 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_1 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_2 = null;
    static Map<List<Entry>, SoftReference<long[]>> cache = new WeakHashMap();
    List<Entry> entries = Collections.emptyList();

    public static class Entry {
        long count;
        long delta;

        public Entry(long count, long delta) {
            this.count = count;
            this.delta = delta;
        }

        public long getCount() {
            return this.count;
        }

        public long getDelta() {
            return this.delta;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public void setDelta(long delta) {
            this.delta = delta;
        }

        public String toString() {
            return "Entry{count=" + this.count + ", delta=" + this.delta + '}';
        }
    }

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("TimeToSampleBox.java", TimeToSampleBox.class);
        ajc$tjp_0 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, factory.makeMethodSig(BuildConfig.VERSION_NAME, "getEntries", "com.coremedia.iso.boxes.TimeToSampleBox", "", "", "", "java.util.List"), 79);
        ajc$tjp_1 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, factory.makeMethodSig(BuildConfig.VERSION_NAME, "setEntries", "com.coremedia.iso.boxes.TimeToSampleBox", "java.util.List", "entries", "", "void"), 83);
        ajc$tjp_2 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, factory.makeMethodSig(BuildConfig.VERSION_NAME, "toString", "com.coremedia.iso.boxes.TimeToSampleBox", "", "", "", "java.lang.String"), 87);
    }

    static {
        boolean z;
        ajc$preClinit();
        if (TimeToSampleBox.class.desiredAssertionStatus()) {
            z = false;
        } else {
            z = true;
        }
        $assertionsDisabled = z;
    }

    public TimeToSampleBox() {
        super(TYPE);
    }

    protected long getContentSize() {
        return (long) ((this.entries.size() * 8) + 8);
    }

    public void _parseDetails(ByteBuffer content) {
        parseVersionAndFlags(content);
        int entryCount = CastUtils.l2i(IsoTypeReader.readUInt32(content));
        this.entries = new ArrayList(entryCount);
        for (int i = 0; i < entryCount; i++) {
            this.entries.add(new Entry(IsoTypeReader.readUInt32(content), IsoTypeReader.readUInt32(content)));
        }
    }

    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        IsoTypeWriter.writeUInt32(byteBuffer, (long) this.entries.size());
        for (Entry entry : this.entries) {
            IsoTypeWriter.writeUInt32(byteBuffer, entry.getCount());
            IsoTypeWriter.writeUInt32(byteBuffer, entry.getDelta());
        }
    }

    public List<Entry> getEntries() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_0, this, this));
        return this.entries;
    }

    public void setEntries(List<Entry> entries) {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_1, this, this, entries));
        this.entries = entries;
    }

    public String toString() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_2, this, this));
        return "TimeToSampleBox[entryCount=" + this.entries.size() + "]";
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized long[] blowupTimeToSamples(java.util.List<com.coremedia.iso.boxes.TimeToSampleBox.Entry> r18) {
        /*
        r12 = com.coremedia.iso.boxes.TimeToSampleBox.class;
        monitor-enter(r12);
        r9 = cache;	 Catch:{ all -> 0x0036 }
        r0 = r18;
        r2 = r9.get(r0);	 Catch:{ all -> 0x0036 }
        r2 = (java.lang.ref.SoftReference) r2;	 Catch:{ all -> 0x0036 }
        if (r2 == 0) goto L_0x0019;
    L_0x000f:
        r3 = r2.get();	 Catch:{ all -> 0x0036 }
        r3 = (long[]) r3;	 Catch:{ all -> 0x0036 }
        if (r3 == 0) goto L_0x0019;
    L_0x0017:
        monitor-exit(r12);
        return r3;
    L_0x0019:
        r10 = 0;
        r9 = r18.iterator();	 Catch:{ all -> 0x0036 }
    L_0x001f:
        r13 = r9.hasNext();	 Catch:{ all -> 0x0036 }
        if (r13 != 0) goto L_0x0039;
    L_0x0025:
        r9 = $assertionsDisabled;	 Catch:{ all -> 0x0036 }
        if (r9 != 0) goto L_0x0045;
    L_0x0029:
        r14 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r9 = (r10 > r14 ? 1 : (r10 == r14 ? 0 : -1));
        if (r9 <= 0) goto L_0x0045;
    L_0x0030:
        r9 = new java.lang.AssertionError;	 Catch:{ all -> 0x0036 }
        r9.<init>();	 Catch:{ all -> 0x0036 }
        throw r9;	 Catch:{ all -> 0x0036 }
    L_0x0036:
        r9 = move-exception;
        monitor-exit(r12);
        throw r9;
    L_0x0039:
        r7 = r9.next();	 Catch:{ all -> 0x0036 }
        r7 = (com.coremedia.iso.boxes.TimeToSampleBox.Entry) r7;	 Catch:{ all -> 0x0036 }
        r14 = r7.getCount();	 Catch:{ all -> 0x0036 }
        r10 = r10 + r14;
        goto L_0x001f;
    L_0x0045:
        r9 = (int) r10;	 Catch:{ all -> 0x0036 }
        r6 = new long[r9];	 Catch:{ all -> 0x0036 }
        r4 = 0;
        r9 = r18.iterator();	 Catch:{ all -> 0x0036 }
    L_0x004d:
        r13 = r9.hasNext();	 Catch:{ all -> 0x0036 }
        if (r13 != 0) goto L_0x0061;
    L_0x0053:
        r9 = cache;	 Catch:{ all -> 0x0036 }
        r13 = new java.lang.ref.SoftReference;	 Catch:{ all -> 0x0036 }
        r13.<init>(r6);	 Catch:{ all -> 0x0036 }
        r0 = r18;
        r9.put(r0, r13);	 Catch:{ all -> 0x0036 }
        r3 = r6;
        goto L_0x0017;
    L_0x0061:
        r7 = r9.next();	 Catch:{ all -> 0x0036 }
        r7 = (com.coremedia.iso.boxes.TimeToSampleBox.Entry) r7;	 Catch:{ all -> 0x0036 }
        r8 = 0;
        r5 = r4;
    L_0x0069:
        r14 = (long) r8;	 Catch:{ all -> 0x0036 }
        r16 = r7.getCount();	 Catch:{ all -> 0x0036 }
        r13 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r13 < 0) goto L_0x0074;
    L_0x0072:
        r4 = r5;
        goto L_0x004d;
    L_0x0074:
        r4 = r5 + 1;
        r14 = r7.getDelta();	 Catch:{ all -> 0x0036 }
        r6[r5] = r14;	 Catch:{ all -> 0x0036 }
        r8 = r8 + 1;
        r5 = r4;
        goto L_0x0069;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.coremedia.iso.boxes.TimeToSampleBox.blowupTimeToSamples(java.util.List):long[]");
    }
}
