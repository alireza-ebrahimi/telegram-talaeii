package com.coremedia.iso.boxes;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractBox;
import com.googlecode.mp4parser.RequiresParseDetailAspect;
import com.thin.downloadmanager.BuildConfig;
import java.nio.ByteBuffer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.runtime.reflect.Factory;

public class TrackReferenceTypeBox extends AbstractBox {
    public static final String TYPE1 = "hint";
    public static final String TYPE2 = "cdsc";
    private static final /* synthetic */ StaticPart ajc$tjp_0 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_1 = null;
    private long[] trackIds;

    static {
        ajc$preClinit();
    }

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("TrackReferenceTypeBox.java", TrackReferenceTypeBox.class);
        ajc$tjp_0 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, factory.makeMethodSig(BuildConfig.VERSION_NAME, "getTrackIds", "com.coremedia.iso.boxes.TrackReferenceTypeBox", "", "", "", "[J"), 40);
        ajc$tjp_1 = factory.makeSJP(JoinPoint.METHOD_EXECUTION, factory.makeMethodSig(BuildConfig.VERSION_NAME, "toString", "com.coremedia.iso.boxes.TrackReferenceTypeBox", "", "", "", "java.lang.String"), 65);
    }

    public TrackReferenceTypeBox(String type) {
        super(type);
    }

    public long[] getTrackIds() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_0, this, this));
        return this.trackIds;
    }

    public void _parseDetails(ByteBuffer content) {
        int count = content.remaining() / 4;
        this.trackIds = new long[count];
        for (int i = 0; i < count; i++) {
            this.trackIds[i] = IsoTypeReader.readUInt32(content);
        }
    }

    protected void getContent(ByteBuffer byteBuffer) {
        for (long trackId : this.trackIds) {
            IsoTypeWriter.writeUInt32(byteBuffer, trackId);
        }
    }

    protected long getContentSize() {
        return (long) (this.trackIds.length * 4);
    }

    public String toString() {
        RequiresParseDetailAspect.aspectOf().before(Factory.makeJP(ajc$tjp_1, this, this));
        StringBuilder buffer = new StringBuilder();
        buffer.append("TrackReferenceTypeBox[type=").append(getType());
        for (int i = 0; i < this.trackIds.length; i++) {
            buffer.append(";trackId");
            buffer.append(i);
            buffer.append("=");
            buffer.append(this.trackIds[i]);
        }
        buffer.append("]");
        return buffer.toString();
    }
}
