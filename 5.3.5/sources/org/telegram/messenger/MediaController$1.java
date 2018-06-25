package org.telegram.messenger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class MediaController$1 implements Runnable {
    final /* synthetic */ MediaController this$0;

    MediaController$1(MediaController this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        if (MediaController.access$000(this.this$0) != null) {
            ByteBuffer buffer;
            if (MediaController.access$100(this.this$0).isEmpty()) {
                buffer = ByteBuffer.allocateDirect(MediaController.access$200(this.this$0));
                buffer.order(ByteOrder.nativeOrder());
            } else {
                buffer = (ByteBuffer) MediaController.access$100(this.this$0).get(0);
                MediaController.access$100(this.this$0).remove(0);
            }
            buffer.rewind();
            int len = MediaController.access$000(this.this$0).read(buffer, buffer.capacity());
            if (len > 0) {
                buffer.limit(len);
                double d = 0.0d;
                try {
                    float sampleStep;
                    long newSamplesCount = MediaController.access$300(this.this$0) + ((long) (len / 2));
                    int currentPart = (int) ((((double) MediaController.access$300(this.this$0)) / ((double) newSamplesCount)) * ((double) MediaController.access$400(this.this$0).length));
                    int newPart = MediaController.access$400(this.this$0).length - currentPart;
                    if (currentPart != 0) {
                        sampleStep = ((float) MediaController.access$400(this.this$0).length) / ((float) currentPart);
                        float currentNum = 0.0f;
                        for (int a = 0; a < currentPart; a++) {
                            MediaController.access$400(this.this$0)[a] = MediaController.access$400(this.this$0)[(int) currentNum];
                            currentNum += sampleStep;
                        }
                    }
                    int currentNum2 = currentPart;
                    float nextNum = 0.0f;
                    sampleStep = (((float) len) / 2.0f) / ((float) newPart);
                    for (int i = 0; i < len / 2; i++) {
                        short peak = buffer.getShort();
                        if (peak > (short) 2500) {
                            d += (double) (peak * peak);
                        }
                        if (i == ((int) nextNum) && currentNum2 < MediaController.access$400(this.this$0).length) {
                            MediaController.access$400(this.this$0)[currentNum2] = peak;
                            nextNum += sampleStep;
                            currentNum2++;
                        }
                    }
                    MediaController.access$302(this.this$0, newSamplesCount);
                } catch (Exception e) {
                    FileLog.e(e);
                }
                buffer.position(0);
                final double amplitude = Math.sqrt((d / ((double) len)) / 2.0d);
                final ByteBuffer finalBuffer = buffer;
                final boolean flush = len != buffer.capacity();
                if (len != 0) {
                    MediaController.access$900(this.this$0).postRunnable(new Runnable() {

                        /* renamed from: org.telegram.messenger.MediaController$1$1$1 */
                        class C14131 implements Runnable {
                            C14131() {
                            }

                            public void run() {
                                MediaController.access$100(MediaController$1.this.this$0).add(finalBuffer);
                            }
                        }

                        public void run() {
                            while (finalBuffer.hasRemaining()) {
                                int oldLimit = -1;
                                if (finalBuffer.remaining() > MediaController.access$500(MediaController$1.this.this$0).remaining()) {
                                    oldLimit = finalBuffer.limit();
                                    finalBuffer.limit(MediaController.access$500(MediaController$1.this.this$0).remaining() + finalBuffer.position());
                                }
                                MediaController.access$500(MediaController$1.this.this$0).put(finalBuffer);
                                if (MediaController.access$500(MediaController$1.this.this$0).position() == MediaController.access$500(MediaController$1.this.this$0).limit() || flush) {
                                    if (MediaController.access$600(MediaController$1.this.this$0, MediaController.access$500(MediaController$1.this.this$0), !flush ? MediaController.access$500(MediaController$1.this.this$0).limit() : finalBuffer.position()) != 0) {
                                        MediaController.access$500(MediaController$1.this.this$0).rewind();
                                        MediaController.access$702(MediaController$1.this.this$0, MediaController.access$700(MediaController$1.this.this$0) + ((long) ((MediaController.access$500(MediaController$1.this.this$0).limit() / 2) / 16)));
                                    }
                                }
                                if (oldLimit != -1) {
                                    finalBuffer.limit(oldLimit);
                                }
                            }
                            MediaController.access$800(MediaController$1.this.this$0).postRunnable(new C14131());
                        }
                    });
                }
                MediaController.access$800(this.this$0).postRunnable(MediaController.access$1000(this.this$0));
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.recordProgressChanged, new Object[]{Long.valueOf(System.currentTimeMillis() - MediaController.access$1100(MediaController$1.this.this$0)), Double.valueOf(amplitude)});
                    }
                });
                return;
            }
            MediaController.access$100(this.this$0).add(buffer);
            MediaController.access$1300(this.this$0, MediaController.access$1200(this.this$0));
        }
    }
}
