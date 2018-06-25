package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

    public void draw(Canvas canvas) {
        if (this.waveformBytes != null && this.width != 0) {
            float dp = (float) (this.width / AndroidUtilities.dp(3.0f));
            if (dp > 0.1f) {
                int length = (this.waveformBytes.length * 8) / 5;
                float f = ((float) length) / dp;
                Paint paint = paintInner;
                int i = (this.messageObject == null || this.messageObject.isOutOwner() || !this.messageObject.isContentUnread()) ? this.selected ? this.selectedColor : this.innerColor : this.outerColor;
                paint.setColor(i);
                paintOuter.setColor(this.outerColor);
                int dp2 = (this.height - AndroidUtilities.dp(14.0f)) / 2;
                int i2 = 0;
                int i3 = 0;
                i = 0;
                float f2 = 0.0f;
                while (i3 < length) {
                    int i4;
                    if (i3 != i) {
                        i4 = i2;
                        i2 = i;
                        i = i4;
                    } else {
                        int i5;
                        int i6 = 0;
                        int i7 = i;
                        float f3 = f2;
                        while (i == i7) {
                            f3 += f;
                            i7 = (int) f3;
                            i6++;
                        }
                        i = i3 * 5;
                        int i8 = i / 8;
                        i -= i8 * 8;
                        int i9 = 8 - i;
                        int i10 = 5 - i9;
                        byte min = (byte) ((this.waveformBytes[i8] >> i) & ((2 << (Math.min(5, i9) - 1)) - 1));
                        if (i10 > 0) {
                            i5 = (byte) (((byte) (min << i10)) | (this.waveformBytes[i8 + 1] & ((2 << (i10 - 1)) - 1)));
                        } else {
                            byte b = min;
                        }
                        int i11 = i2;
                        for (int i12 = 0; i12 < i6; i12++) {
                            int dp3 = i11 * AndroidUtilities.dp(3.0f);
                            if (dp3 >= this.thumbX || AndroidUtilities.dp(2.0f) + dp3 >= this.thumbX) {
                                canvas.drawRect((float) dp3, (float) (AndroidUtilities.dp(14.0f - Math.max(1.0f, (14.0f * ((float) i5)) / 31.0f)) + dp2), (float) (AndroidUtilities.dp(2.0f) + dp3), (float) (AndroidUtilities.dp(14.0f) + dp2), paintInner);
                                if (dp3 < this.thumbX) {
                                    canvas.drawRect((float) dp3, (float) (AndroidUtilities.dp(14.0f - Math.max(1.0f, (14.0f * ((float) i5)) / 31.0f)) + dp2), (float) this.thumbX, (float) (AndroidUtilities.dp(14.0f) + dp2), paintOuter);
                                }
                            } else {
                                canvas.drawRect((float) dp3, (float) (AndroidUtilities.dp(14.0f - Math.max(1.0f, (14.0f * ((float) i5)) / 31.0f)) + dp2), (float) (AndroidUtilities.dp(2.0f) + dp3), (float) (AndroidUtilities.dp(14.0f) + dp2), paintOuter);
                            }
                            i11++;
                        }
                        i = i11;
                        i2 = i7;
                        f2 = f3;
                    }
                    i3++;
                    i4 = i;
                    i = i2;
                    i2 = i4;
                }
            }
        }
    }

    public boolean isDragging() {
        return this.pressed;
    }

    public boolean isStartDraging() {
        return this.startDraging;
    }

    public boolean onTouch(int i, float f, float f2) {
        if (i == 0) {
            if (BitmapDescriptorFactory.HUE_RED <= f && f <= ((float) this.width) && f2 >= BitmapDescriptorFactory.HUE_RED && f2 <= ((float) this.height)) {
                this.startX = f;
                this.pressed = true;
                this.thumbDX = (int) (f - ((float) this.thumbX));
                this.startDraging = false;
                return true;
            }
        } else if (i == 1 || i == 3) {
            if (this.pressed) {
                if (i == 1 && this.delegate != null) {
                    this.delegate.onSeekBarDrag(((float) this.thumbX) / ((float) this.width));
                }
                this.pressed = false;
                return true;
            }
        } else if (i == 2 && this.pressed) {
            if (this.startDraging) {
                this.thumbX = (int) (f - ((float) this.thumbDX));
                if (this.thumbX < 0) {
                    this.thumbX = 0;
                } else if (this.thumbX > this.width) {
                    this.thumbX = this.width;
                }
            }
            if (this.startX == -1.0f || Math.abs(f - this.startX) <= AndroidUtilities.getPixelsInCM(0.2f, true)) {
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

    public void setColors(int i, int i2, int i3) {
        this.innerColor = i;
        this.outerColor = i2;
        this.selectedColor = i3;
    }

    public void setDelegate(SeekBarDelegate seekBarDelegate) {
        this.delegate = seekBarDelegate;
    }

    public void setMessageObject(MessageObject messageObject) {
        this.messageObject = messageObject;
    }

    public void setParentView(View view) {
        this.parentView = view;
    }

    public void setProgress(float f) {
        this.thumbX = (int) Math.ceil((double) (((float) this.width) * f));
        if (this.thumbX < 0) {
            this.thumbX = 0;
        } else if (this.thumbX > this.width) {
            this.thumbX = this.width;
        }
    }

    public void setSelected(boolean z) {
        this.selected = z;
    }

    public void setSize(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public void setWaveform(byte[] bArr) {
        this.waveformBytes = bArr;
    }
}
