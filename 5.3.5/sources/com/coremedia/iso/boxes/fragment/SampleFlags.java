package com.coremedia.iso.boxes.fragment;

import android.support.v4.media.session.PlaybackStateCompat;
import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import java.nio.ByteBuffer;

public class SampleFlags {
    private byte is_leading;
    private byte reserved;
    private int sampleDegradationPriority;
    private byte sampleDependsOn;
    private byte sampleHasRedundancy;
    private byte sampleIsDependedOn;
    private boolean sampleIsDifferenceSample;
    private byte samplePaddingValue;

    public SampleFlags(ByteBuffer bb) {
        long a = IsoTypeReader.readUInt32(bb);
        this.reserved = (byte) ((int) ((-268435456 & a) >> 28));
        this.is_leading = (byte) ((int) ((201326592 & a) >> 26));
        this.sampleDependsOn = (byte) ((int) ((50331648 & a) >> 24));
        this.sampleIsDependedOn = (byte) ((int) ((12582912 & a) >> 22));
        this.sampleHasRedundancy = (byte) ((int) ((3145728 & a) >> 20));
        this.samplePaddingValue = (byte) ((int) ((917504 & a) >> 17));
        this.sampleIsDifferenceSample = ((PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH & a) >> 16) > 0;
        this.sampleDegradationPriority = (int) (65535 & a);
    }

    public void getContent(ByteBuffer os) {
        IsoTypeWriter.writeUInt32(os, (((((((0 | ((long) (this.reserved << 28))) | ((long) (this.is_leading << 26))) | ((long) (this.sampleDependsOn << 24))) | ((long) (this.sampleIsDependedOn << 22))) | ((long) (this.sampleHasRedundancy << 20))) | ((long) (this.samplePaddingValue << 17))) | ((long) ((this.sampleIsDifferenceSample ? 1 : 0) << 16))) | ((long) this.sampleDegradationPriority));
    }

    public int getReserved() {
        return this.reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = (byte) reserved;
    }

    public int getSampleDependsOn() {
        return this.sampleDependsOn;
    }

    public void setSampleDependsOn(int sampleDependsOn) {
        this.sampleDependsOn = (byte) sampleDependsOn;
    }

    public int getSampleIsDependedOn() {
        return this.sampleIsDependedOn;
    }

    public void setSampleIsDependedOn(int sampleIsDependedOn) {
        this.sampleIsDependedOn = (byte) sampleIsDependedOn;
    }

    public int getSampleHasRedundancy() {
        return this.sampleHasRedundancy;
    }

    public void setSampleHasRedundancy(int sampleHasRedundancy) {
        this.sampleHasRedundancy = (byte) sampleHasRedundancy;
    }

    public int getSamplePaddingValue() {
        return this.samplePaddingValue;
    }

    public void setSamplePaddingValue(int samplePaddingValue) {
        this.samplePaddingValue = (byte) samplePaddingValue;
    }

    public boolean isSampleIsDifferenceSample() {
        return this.sampleIsDifferenceSample;
    }

    public void setSampleIsDifferenceSample(boolean sampleIsDifferenceSample) {
        this.sampleIsDifferenceSample = sampleIsDifferenceSample;
    }

    public int getSampleDegradationPriority() {
        return this.sampleDegradationPriority;
    }

    public void setSampleDegradationPriority(int sampleDegradationPriority) {
        this.sampleDegradationPriority = sampleDegradationPriority;
    }

    public String toString() {
        return "SampleFlags{reserved=" + this.reserved + ", isLeading=" + this.is_leading + ", depOn=" + this.sampleDependsOn + ", isDepOn=" + this.sampleIsDependedOn + ", hasRedundancy=" + this.sampleHasRedundancy + ", padValue=" + this.samplePaddingValue + ", isDiffSample=" + this.sampleIsDifferenceSample + ", degradPrio=" + this.sampleDegradationPriority + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SampleFlags that = (SampleFlags) o;
        if (this.is_leading != that.is_leading) {
            return false;
        }
        if (this.reserved != that.reserved) {
            return false;
        }
        if (this.sampleDegradationPriority != that.sampleDegradationPriority) {
            return false;
        }
        if (this.sampleDependsOn != that.sampleDependsOn) {
            return false;
        }
        if (this.sampleHasRedundancy != that.sampleHasRedundancy) {
            return false;
        }
        if (this.sampleIsDependedOn != that.sampleIsDependedOn) {
            return false;
        }
        if (this.sampleIsDifferenceSample != that.sampleIsDifferenceSample) {
            return false;
        }
        if (this.samplePaddingValue != that.samplePaddingValue) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((((((((((this.reserved * 31) + this.is_leading) * 31) + this.sampleDependsOn) * 31) + this.sampleIsDependedOn) * 31) + this.sampleHasRedundancy) * 31) + this.samplePaddingValue) * 31) + (this.sampleIsDifferenceSample ? 1 : 0)) * 31) + this.sampleDegradationPriority;
    }
}
