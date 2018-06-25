package com.mp4parser.iso14496.part15;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.GroupEntry;
import java.nio.ByteBuffer;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;

public class TemporalLayerSampleGroup extends GroupEntry {
    public static final String TYPE = "tscl";
    int temporalLayerId;
    int tlAvgBitRate;
    int tlAvgFrameRate;
    int tlConstantFrameRate;
    int tlMaxBitRate;
    long tlconstraint_indicator_flags;
    int tllevel_idc;
    long tlprofile_compatibility_flags;
    int tlprofile_idc;
    int tlprofile_space;
    boolean tltier_flag;

    public String getType() {
        return TYPE;
    }

    public int getTemporalLayerId() {
        return this.temporalLayerId;
    }

    public void setTemporalLayerId(int temporalLayerId) {
        this.temporalLayerId = temporalLayerId;
    }

    public int getTlprofile_space() {
        return this.tlprofile_space;
    }

    public void setTlprofile_space(int tlprofile_space) {
        this.tlprofile_space = tlprofile_space;
    }

    public boolean isTltier_flag() {
        return this.tltier_flag;
    }

    public void setTltier_flag(boolean tltier_flag) {
        this.tltier_flag = tltier_flag;
    }

    public int getTlprofile_idc() {
        return this.tlprofile_idc;
    }

    public void setTlprofile_idc(int tlprofile_idc) {
        this.tlprofile_idc = tlprofile_idc;
    }

    public long getTlprofile_compatibility_flags() {
        return this.tlprofile_compatibility_flags;
    }

    public void setTlprofile_compatibility_flags(long tlprofile_compatibility_flags) {
        this.tlprofile_compatibility_flags = tlprofile_compatibility_flags;
    }

    public long getTlconstraint_indicator_flags() {
        return this.tlconstraint_indicator_flags;
    }

    public void setTlconstraint_indicator_flags(long tlconstraint_indicator_flags) {
        this.tlconstraint_indicator_flags = tlconstraint_indicator_flags;
    }

    public int getTllevel_idc() {
        return this.tllevel_idc;
    }

    public void setTllevel_idc(int tllevel_idc) {
        this.tllevel_idc = tllevel_idc;
    }

    public int getTlMaxBitRate() {
        return this.tlMaxBitRate;
    }

    public void setTlMaxBitRate(int tlMaxBitRate) {
        this.tlMaxBitRate = tlMaxBitRate;
    }

    public int getTlAvgBitRate() {
        return this.tlAvgBitRate;
    }

    public void setTlAvgBitRate(int tlAvgBitRate) {
        this.tlAvgBitRate = tlAvgBitRate;
    }

    public int getTlConstantFrameRate() {
        return this.tlConstantFrameRate;
    }

    public void setTlConstantFrameRate(int tlConstantFrameRate) {
        this.tlConstantFrameRate = tlConstantFrameRate;
    }

    public int getTlAvgFrameRate() {
        return this.tlAvgFrameRate;
    }

    public void setTlAvgFrameRate(int tlAvgFrameRate) {
        this.tlAvgFrameRate = tlAvgFrameRate;
    }

    public void parse(ByteBuffer byteBuffer) {
        this.temporalLayerId = IsoTypeReader.readUInt8(byteBuffer);
        int a = IsoTypeReader.readUInt8(byteBuffer);
        this.tlprofile_space = (a & PsExtractor.AUDIO_STREAM) >> 6;
        this.tltier_flag = (a & 32) > 0;
        this.tlprofile_idc = a & 31;
        this.tlprofile_compatibility_flags = IsoTypeReader.readUInt32(byteBuffer);
        this.tlconstraint_indicator_flags = IsoTypeReader.readUInt48(byteBuffer);
        this.tllevel_idc = IsoTypeReader.readUInt8(byteBuffer);
        this.tlMaxBitRate = IsoTypeReader.readUInt16(byteBuffer);
        this.tlAvgBitRate = IsoTypeReader.readUInt16(byteBuffer);
        this.tlConstantFrameRate = IsoTypeReader.readUInt8(byteBuffer);
        this.tlAvgFrameRate = IsoTypeReader.readUInt16(byteBuffer);
    }

    public ByteBuffer get() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        IsoTypeWriter.writeUInt8(byteBuffer, this.temporalLayerId);
        IsoTypeWriter.writeUInt8(byteBuffer, ((this.tltier_flag ? 32 : 0) + (this.tlprofile_space << 6)) + this.tlprofile_idc);
        IsoTypeWriter.writeUInt32(byteBuffer, this.tlprofile_compatibility_flags);
        IsoTypeWriter.writeUInt48(byteBuffer, this.tlconstraint_indicator_flags);
        IsoTypeWriter.writeUInt8(byteBuffer, this.tllevel_idc);
        IsoTypeWriter.writeUInt16(byteBuffer, this.tlMaxBitRate);
        IsoTypeWriter.writeUInt16(byteBuffer, this.tlAvgBitRate);
        IsoTypeWriter.writeUInt8(byteBuffer, this.tlConstantFrameRate);
        IsoTypeWriter.writeUInt16(byteBuffer, this.tlAvgFrameRate);
        return (ByteBuffer) byteBuffer.rewind();
    }

    public int size() {
        return 20;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TemporalLayerSampleGroup that = (TemporalLayerSampleGroup) o;
        if (this.temporalLayerId != that.temporalLayerId) {
            return false;
        }
        if (this.tlAvgBitRate != that.tlAvgBitRate) {
            return false;
        }
        if (this.tlAvgFrameRate != that.tlAvgFrameRate) {
            return false;
        }
        if (this.tlConstantFrameRate != that.tlConstantFrameRate) {
            return false;
        }
        if (this.tlMaxBitRate != that.tlMaxBitRate) {
            return false;
        }
        if (this.tlconstraint_indicator_flags != that.tlconstraint_indicator_flags) {
            return false;
        }
        if (this.tllevel_idc != that.tllevel_idc) {
            return false;
        }
        if (this.tlprofile_compatibility_flags != that.tlprofile_compatibility_flags) {
            return false;
        }
        if (this.tlprofile_idc != that.tlprofile_idc) {
            return false;
        }
        if (this.tlprofile_space != that.tlprofile_space) {
            return false;
        }
        if (this.tltier_flag != that.tltier_flag) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((((((((((((((((this.temporalLayerId * 31) + this.tlprofile_space) * 31) + (this.tltier_flag ? 1 : 0)) * 31) + this.tlprofile_idc) * 31) + ((int) (this.tlprofile_compatibility_flags ^ (this.tlprofile_compatibility_flags >>> 32)))) * 31) + ((int) (this.tlconstraint_indicator_flags ^ (this.tlconstraint_indicator_flags >>> 32)))) * 31) + this.tllevel_idc) * 31) + this.tlMaxBitRate) * 31) + this.tlAvgBitRate) * 31) + this.tlConstantFrameRate) * 31) + this.tlAvgFrameRate;
    }

    public String toString() {
        return "TemporalLayerSampleGroup{temporalLayerId=" + this.temporalLayerId + ", tlprofile_space=" + this.tlprofile_space + ", tltier_flag=" + this.tltier_flag + ", tlprofile_idc=" + this.tlprofile_idc + ", tlprofile_compatibility_flags=" + this.tlprofile_compatibility_flags + ", tlconstraint_indicator_flags=" + this.tlconstraint_indicator_flags + ", tllevel_idc=" + this.tllevel_idc + ", tlMaxBitRate=" + this.tlMaxBitRate + ", tlAvgBitRate=" + this.tlAvgBitRate + ", tlConstantFrameRate=" + this.tlConstantFrameRate + ", tlAvgFrameRate=" + this.tlAvgFrameRate + '}';
    }
}
