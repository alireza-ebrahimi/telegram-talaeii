package org.telegram.ui.Components.Paint;

import android.graphics.PointF;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class RenderState {
    private static final int DEFAULT_STATE_SIZE = 256;
    private int allocatedCount;
    public float alpha;
    public float angle;
    public float baseWeight;
    private ByteBuffer buffer;
    private int count;
    public double remainder;
    public float scale;
    public float spacing;

    public boolean addPoint(PointF pointF, float f, float f2, float f3, int i) {
        if ((i == -1 || i < this.allocatedCount) && this.buffer.position() != this.buffer.limit()) {
            if (i != -1) {
                this.buffer.position((i * 5) * 4);
            }
            this.buffer.putFloat(pointF.x);
            this.buffer.putFloat(pointF.y);
            this.buffer.putFloat(f);
            this.buffer.putFloat(f2);
            this.buffer.putFloat(f3);
            return true;
        }
        resizeBuffer();
        return false;
    }

    public void appendValuesCount(int i) {
        int i2 = this.count + i;
        if (i2 > this.allocatedCount || this.buffer == null) {
            resizeBuffer();
        }
        this.count = i2;
    }

    public int getCount() {
        return this.count;
    }

    public void prepare() {
        this.count = 0;
        if (this.buffer == null) {
            this.allocatedCount = 256;
            this.buffer = ByteBuffer.allocateDirect((this.allocatedCount * 5) * 4);
            this.buffer.order(ByteOrder.nativeOrder());
            this.buffer.position(0);
        }
    }

    public float read() {
        return this.buffer.getFloat();
    }

    public void reset() {
        this.count = 0;
        this.remainder = 0.0d;
        if (this.buffer != null) {
            this.buffer.position(0);
        }
    }

    public void resizeBuffer() {
        if (this.buffer != null) {
            this.buffer = null;
        }
        this.allocatedCount = Math.max(this.allocatedCount * 2, 256);
        this.buffer = ByteBuffer.allocateDirect((this.allocatedCount * 5) * 4);
        this.buffer.order(ByteOrder.nativeOrder());
        this.buffer.position(0);
    }

    public void setPosition(int i) {
        if (this.buffer != null && i >= 0 && i < this.allocatedCount) {
            this.buffer.position((i * 5) * 4);
        }
    }
}
