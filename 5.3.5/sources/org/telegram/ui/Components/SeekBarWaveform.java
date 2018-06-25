package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MessageObject;
import org.telegram.ui.Components.SeekBar.SeekBarDelegate;

public class SeekBarWaveform {
    private static Paint paintInner;
    private static Paint paintOuter;
    private SeekBarDelegate delegate;
    private int height;
    private int innerColor;
    private MessageObject messageObject;
    private int outerColor;
    private View parentView;
    private boolean pressed = false;
    private boolean selected;
    private int selectedColor;
    private boolean startDraging = false;
    private float startX;
    private int thumbDX = 0;
    private int thumbX = 0;
    private byte[] waveformBytes;
    private int width;

    public SeekBarWaveform(Context context) {
        if (paintInner == null) {
            paintInner = new Paint();
            paintOuter = new Paint();
        }
    }

    public void setDelegate(SeekBarDelegate seekBarDelegate) {
        this.delegate = seekBarDelegate;
    }

    public void setColors(int inner, int outer, int selected) {
        this.innerColor = inner;
        this.outerColor = outer;
        this.selectedColor = selected;
    }

    public void setWaveform(byte[] waveform) {
        this.waveformBytes = waveform;
    }

    public void setSelected(boolean value) {
        this.selected = value;
    }

    public void setMessageObject(MessageObject object) {
        this.messageObject = object;
    }

    public void setParentView(View view) {
        this.parentView = view;
    }

    public boolean isStartDraging() {
        return this.startDraging;
    }

    public boolean onTouch(int action, float x, float y) {
        if (action == 0) {
            if (0.0f <= x && x <= ((float) this.width) && y >= 0.0f && y <= ((float) this.height)) {
                this.startX = x;
                this.pressed = true;
                this.thumbDX = (int) (x - ((float) this.thumbX));
                this.startDraging = false;
                return true;
            }
        } else if (action == 1 || action == 3) {
            if (this.pressed) {
                if (action == 1 && this.delegate != null) {
                    this.delegate.onSeekBarDrag(((float) this.thumbX) / ((float) this.width));
                }
                this.pressed = false;
                return true;
            }
        } else if (action == 2 && this.pressed) {
            if (this.startDraging) {
                this.thumbX = (int) (x - ((float) this.thumbDX));
                if (this.thumbX < 0) {
                    this.thumbX = 0;
                } else if (this.thumbX > this.width) {
                    this.thumbX = this.width;
                }
            }
            if (this.startX == -1.0f || Math.abs(x - this.startX) <= AndroidUtilities.getPixelsInCM(0.2f, true)) {
                return true;
            }
            if (!(this.parentView == null || this.parentView.getParent() == null)) {
                this.parentView.getParent().requestDisallowInterceptTouchEvent(true);
            }
            this.startDraging = true;
            this.startX = -1.0f;
            return true;
        }
        return false;
    }

    public void setProgress(float progress) {
        this.thumbX = (int) Math.ceil((double) (((float) this.width) * progress));
        if (this.thumbX < 0) {
            this.thumbX = 0;
        } else if (this.thumbX > this.width) {
            this.thumbX = this.width;
        }
    }

    public boolean isDragging() {
        return this.pressed;
    }

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
    }

    public void draw(Canvas canvas) {
        if (this.waveformBytes != null && this.width != 0) {
            float totalBarsCount = (float) (this.width / AndroidUtilities.dp(3.0f));
            if (totalBarsCount > 0.1f) {
                int samplesCount = (this.waveformBytes.length * 8) / 5;
                float samplesPerBar = ((float) samplesCount) / totalBarsCount;
                float barCounter = 0.0f;
                int nextBarNum = 0;
                Paint paint = paintInner;
                int i = (this.messageObject == null || this.messageObject.isOutOwner() || !this.messageObject.isContentUnread()) ? this.selected ? this.selectedColor : this.innerColor : this.outerColor;
                paint.setColor(i);
                paintOuter.setColor(this.outerColor);
                int y = (this.height - AndroidUtilities.dp(14.0f)) / 2;
                int barNum = 0;
                for (int a = 0; a < samplesCount; a++) {
                    if (a == nextBarNum) {
                        int drawBarCount = 0;
                        int lastBarNum = nextBarNum;
                        while (lastBarNum == nextBarNum) {
                            barCounter += samplesPerBar;
                            nextBarNum = (int) barCounter;
                            drawBarCount++;
                        }
                        int bitPointer = a * 5;
                        int byteNum = bitPointer / 8;
                        int byteBitOffset = bitPointer - (byteNum * 8);
                        int currentByteCount = 8 - byteBitOffset;
                        int nextByteRest = 5 - currentByteCount;
                        byte value = (byte) ((this.waveformBytes[byteNum] >> byteBitOffset) & ((2 << (Math.min(5, currentByteCount) - 1)) - 1));
                        if (nextByteRest > 0) {
                            value = (byte) ((this.waveformBytes[byteNum + 1] & ((2 << (nextByteRest - 1)) - 1)) | ((byte) (value << nextByteRest)));
                        }
                        for (int b = 0; b < drawBarCount; b++) {
                            int x = barNum * AndroidUtilities.dp(3.0f);
                            if (x >= this.thumbX || AndroidUtilities.dp(2.0f) + x >= this.thumbX) {
                                canvas.drawRect((float) x, (float) (AndroidUtilities.dp(14.0f - Math.max(1.0f, (14.0f * ((float) value)) / 31.0f)) + y), (float) (AndroidUtilities.dp(2.0f) + x), (float) (AndroidUtilities.dp(14.0f) + y), paintInner);
                                if (x < this.thumbX) {
                                    canvas.drawRect((float) x, (float) (AndroidUtilities.dp(14.0f - Math.max(1.0f, (14.0f * ((float) value)) / 31.0f)) + y), (float) this.thumbX, (float) (AndroidUtilities.dp(14.0f) + y), paintOuter);
                                }
                            } else {
                                canvas.drawRect((float) x, (float) (AndroidUtilities.dp(14.0f - Math.max(1.0f, (14.0f * ((float) value)) / 31.0f)) + y), (float) (AndroidUtilities.dp(2.0f) + x), (float) (AndroidUtilities.dp(14.0f) + y), paintOuter);
                            }
                            barNum++;
                        }
                    }
                }
            }
        }
    }
}
