package com.crashlytics.android.core;

import android.os.Process;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.IdManager;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

class CLSUUID {
    private static String _clsId;
    private static final AtomicLong _sequenceNumber = new AtomicLong(0);

    public CLSUUID(IdManager idManager) {
        bytes = new byte[10];
        populateTime(bytes);
        populateSequenceNumber(bytes);
        populatePID(bytes);
        String idSha = CommonUtils.sha1(idManager.getAppInstallIdentifier());
        String timeSeqPid = CommonUtils.hexify(bytes);
        _clsId = String.format(Locale.US, "%s-%s-%s-%s", new Object[]{timeSeqPid.substring(0, 12), timeSeqPid.substring(12, 16), timeSeqPid.subSequence(16, 20), idSha.substring(0, 12)}).toUpperCase(Locale.US);
    }

    private void populateTime(byte[] bytes) {
        long time = new Date().getTime();
        long tvUsec = time % 1000;
        byte[] timeBytes = convertLongToFourByteBuffer(time / 1000);
        bytes[0] = timeBytes[0];
        bytes[1] = timeBytes[1];
        bytes[2] = timeBytes[2];
        bytes[3] = timeBytes[3];
        byte[] msecsBytes = convertLongToTwoByteBuffer(tvUsec);
        bytes[4] = msecsBytes[0];
        bytes[5] = msecsBytes[1];
    }

    private void populateSequenceNumber(byte[] bytes) {
        byte[] sequenceBytes = convertLongToTwoByteBuffer(_sequenceNumber.incrementAndGet());
        bytes[6] = sequenceBytes[0];
        bytes[7] = sequenceBytes[1];
    }

    private void populatePID(byte[] bytes) {
        byte[] pidBytes = convertLongToTwoByteBuffer((long) Integer.valueOf(Process.myPid()).shortValue());
        bytes[8] = pidBytes[0];
        bytes[9] = pidBytes[1];
    }

    private static byte[] convertLongToFourByteBuffer(long value) {
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.putInt((int) value);
        buf.order(ByteOrder.BIG_ENDIAN);
        buf.position(0);
        return buf.array();
    }

    private static byte[] convertLongToTwoByteBuffer(long value) {
        ByteBuffer buf = ByteBuffer.allocate(2);
        buf.putShort((short) ((int) value));
        buf.order(ByteOrder.BIG_ENDIAN);
        buf.position(0);
        return buf.array();
    }

    public String toString() {
        return _clsId;
    }
}
