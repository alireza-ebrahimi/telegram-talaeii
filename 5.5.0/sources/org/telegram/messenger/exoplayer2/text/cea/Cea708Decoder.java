package org.telegram.messenger.exoplayer2.text.cea;

import android.graphics.Color;
import android.text.Layout.Alignment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.exoplayer2.text.Cue;
import org.telegram.messenger.exoplayer2.text.Subtitle;
import org.telegram.messenger.exoplayer2.text.SubtitleInputBuffer;
import org.telegram.messenger.exoplayer2.text.SubtitleOutputBuffer;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.ParsableBitArray;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class Cea708Decoder extends CeaDecoder {
    private static final int CC_VALID_FLAG = 4;
    private static final int CHARACTER_BIG_CARONS = 42;
    private static final int CHARACTER_BIG_OE = 44;
    private static final int CHARACTER_BOLD_BULLET = 53;
    private static final int CHARACTER_CLOSE_DOUBLE_QUOTE = 52;
    private static final int CHARACTER_CLOSE_SINGLE_QUOTE = 50;
    private static final int CHARACTER_DIAERESIS_Y = 63;
    private static final int CHARACTER_ELLIPSIS = 37;
    private static final int CHARACTER_FIVE_EIGHTHS = 120;
    private static final int CHARACTER_HORIZONTAL_BORDER = 125;
    private static final int CHARACTER_LOWER_LEFT_BORDER = 124;
    private static final int CHARACTER_LOWER_RIGHT_BORDER = 126;
    private static final int CHARACTER_MN = 127;
    private static final int CHARACTER_NBTSP = 33;
    private static final int CHARACTER_ONE_EIGHTH = 118;
    private static final int CHARACTER_OPEN_DOUBLE_QUOTE = 51;
    private static final int CHARACTER_OPEN_SINGLE_QUOTE = 49;
    private static final int CHARACTER_SEVEN_EIGHTHS = 121;
    private static final int CHARACTER_SM = 61;
    private static final int CHARACTER_SMALL_CARONS = 58;
    private static final int CHARACTER_SMALL_OE = 60;
    private static final int CHARACTER_SOLID_BLOCK = 48;
    private static final int CHARACTER_THREE_EIGHTHS = 119;
    private static final int CHARACTER_TM = 57;
    private static final int CHARACTER_TSP = 32;
    private static final int CHARACTER_UPPER_LEFT_BORDER = 127;
    private static final int CHARACTER_UPPER_RIGHT_BORDER = 123;
    private static final int CHARACTER_VERTICAL_BORDER = 122;
    private static final int COMMAND_BS = 8;
    private static final int COMMAND_CLW = 136;
    private static final int COMMAND_CR = 13;
    private static final int COMMAND_CW0 = 128;
    private static final int COMMAND_CW1 = 129;
    private static final int COMMAND_CW2 = 130;
    private static final int COMMAND_CW3 = 131;
    private static final int COMMAND_CW4 = 132;
    private static final int COMMAND_CW5 = 133;
    private static final int COMMAND_CW6 = 134;
    private static final int COMMAND_CW7 = 135;
    private static final int COMMAND_DF0 = 152;
    private static final int COMMAND_DF1 = 153;
    private static final int COMMAND_DF2 = 154;
    private static final int COMMAND_DF3 = 155;
    private static final int COMMAND_DF5 = 157;
    private static final int COMMAND_DF6 = 158;
    private static final int COMMAND_DF7 = 159;
    private static final int COMMAND_DLC = 142;
    private static final int COMMAND_DLW = 140;
    private static final int COMMAND_DLY = 141;
    private static final int COMMAND_DS4 = 156;
    private static final int COMMAND_DSW = 137;
    private static final int COMMAND_ETX = 3;
    private static final int COMMAND_EXT1 = 16;
    private static final int COMMAND_EXT1_END = 23;
    private static final int COMMAND_EXT1_START = 17;
    private static final int COMMAND_FF = 12;
    private static final int COMMAND_HCR = 14;
    private static final int COMMAND_HDW = 138;
    private static final int COMMAND_NUL = 0;
    private static final int COMMAND_P16_END = 31;
    private static final int COMMAND_P16_START = 24;
    private static final int COMMAND_RST = 143;
    private static final int COMMAND_SPA = 144;
    private static final int COMMAND_SPC = 145;
    private static final int COMMAND_SPL = 146;
    private static final int COMMAND_SWA = 151;
    private static final int COMMAND_TGW = 139;
    private static final int DTVCC_PACKET_DATA = 2;
    private static final int DTVCC_PACKET_START = 3;
    private static final int GROUP_C0_END = 31;
    private static final int GROUP_C1_END = 159;
    private static final int GROUP_C2_END = 31;
    private static final int GROUP_C3_END = 159;
    private static final int GROUP_G0_END = 127;
    private static final int GROUP_G1_END = 255;
    private static final int GROUP_G2_END = 127;
    private static final int GROUP_G3_END = 255;
    private static final int NUM_WINDOWS = 8;
    private static final String TAG = "Cea708Decoder";
    private final ParsableByteArray ccData = new ParsableByteArray();
    private final CueBuilder[] cueBuilders;
    private List<Cue> cues;
    private CueBuilder currentCueBuilder;
    private DtvCcPacket currentDtvCcPacket;
    private int currentWindow;
    private List<Cue> lastCues;
    private final int selectedServiceNumber;
    private final ParsableBitArray serviceBlockPacket = new ParsableBitArray();

    private static final class CueBuilder {
        private static final int BORDER_AND_EDGE_TYPE_NONE = 0;
        private static final int BORDER_AND_EDGE_TYPE_UNIFORM = 3;
        public static final int COLOR_SOLID_BLACK = getArgbColorFromCeaColor(0, 0, 0, 0);
        public static final int COLOR_SOLID_WHITE = getArgbColorFromCeaColor(2, 2, 2, 0);
        public static final int COLOR_TRANSPARENT = getArgbColorFromCeaColor(0, 0, 0, 3);
        private static final int DEFAULT_PRIORITY = 4;
        private static final int DIRECTION_BOTTOM_TO_TOP = 3;
        private static final int DIRECTION_LEFT_TO_RIGHT = 0;
        private static final int DIRECTION_RIGHT_TO_LEFT = 1;
        private static final int DIRECTION_TOP_TO_BOTTOM = 2;
        private static final int HORIZONTAL_SIZE = 209;
        private static final int JUSTIFICATION_CENTER = 2;
        private static final int JUSTIFICATION_FULL = 3;
        private static final int JUSTIFICATION_LEFT = 0;
        private static final int JUSTIFICATION_RIGHT = 1;
        private static final int MAXIMUM_ROW_COUNT = 15;
        private static final int PEN_FONT_STYLE_DEFAULT = 0;
        private static final int PEN_FONT_STYLE_MONOSPACED_WITHOUT_SERIFS = 3;
        private static final int PEN_FONT_STYLE_MONOSPACED_WITH_SERIFS = 1;
        private static final int PEN_FONT_STYLE_PROPORTIONALLY_SPACED_WITHOUT_SERIFS = 4;
        private static final int PEN_FONT_STYLE_PROPORTIONALLY_SPACED_WITH_SERIFS = 2;
        private static final int PEN_OFFSET_NORMAL = 1;
        private static final int PEN_SIZE_STANDARD = 1;
        private static final int[] PEN_STYLE_BACKGROUND = new int[]{COLOR_SOLID_BLACK, COLOR_SOLID_BLACK, COLOR_SOLID_BLACK, COLOR_SOLID_BLACK, COLOR_SOLID_BLACK, COLOR_TRANSPARENT, COLOR_TRANSPARENT};
        private static final int[] PEN_STYLE_EDGE_TYPE = new int[]{0, 0, 0, 0, 0, 3, 3};
        private static final int[] PEN_STYLE_FONT_STYLE = new int[]{0, 1, 2, 3, 4, 3, 4};
        private static final int RELATIVE_CUE_SIZE = 99;
        private static final int VERTICAL_SIZE = 74;
        private static final int[] WINDOW_STYLE_FILL = new int[]{COLOR_SOLID_BLACK, COLOR_TRANSPARENT, COLOR_SOLID_BLACK, COLOR_SOLID_BLACK, COLOR_TRANSPARENT, COLOR_SOLID_BLACK, COLOR_SOLID_BLACK};
        private static final int[] WINDOW_STYLE_JUSTIFICATION = new int[]{0, 0, 0, 0, 0, 2, 0};
        private static final int[] WINDOW_STYLE_PRINT_DIRECTION = new int[]{0, 0, 0, 0, 0, 0, 2};
        private static final int[] WINDOW_STYLE_SCROLL_DIRECTION = new int[]{3, 3, 3, 3, 3, 3, 1};
        private static final boolean[] WINDOW_STYLE_WORD_WRAP = new boolean[]{false, false, false, true, true, true, false};
        private int anchorId;
        private int backgroundColor;
        private int backgroundColorStartPosition;
        private final SpannableStringBuilder captionStringBuilder = new SpannableStringBuilder();
        private boolean defined;
        private int foregroundColor;
        private int foregroundColorStartPosition;
        private int horizontalAnchor;
        private int italicsStartPosition;
        private int justification;
        private int penStyleId;
        private int priority;
        private boolean relativePositioning;
        private final List<SpannableString> rolledUpCaptions = new LinkedList();
        private int row;
        private int rowCount;
        private boolean rowLock;
        private int underlineStartPosition;
        private int verticalAnchor;
        private boolean visible;
        private int windowFillColor;
        private int windowStyleId;

        public CueBuilder() {
            reset();
        }

        public static int getArgbColorFromCeaColor(int i, int i2, int i3) {
            return getArgbColorFromCeaColor(i, i2, i3, 0);
        }

        public static int getArgbColorFromCeaColor(int i, int i2, int i3, int i4) {
            int i5;
            int i6 = 255;
            Assertions.checkIndex(i, 0, 4);
            Assertions.checkIndex(i2, 0, 4);
            Assertions.checkIndex(i3, 0, 4);
            Assertions.checkIndex(i4, 0, 4);
            switch (i4) {
                case 0:
                case 1:
                    i5 = 255;
                    break;
                case 2:
                    i5 = 127;
                    break;
                case 3:
                    i5 = 0;
                    break;
                default:
                    i5 = 255;
                    break;
            }
            int i7 = i > 1 ? 255 : 0;
            int i8 = i2 > 1 ? 255 : 0;
            if (i3 <= 1) {
                i6 = 0;
            }
            return Color.argb(i5, i7, i8, i6);
        }

        public void append(char c) {
            if (c == '\n') {
                this.rolledUpCaptions.add(buildSpannableString());
                this.captionStringBuilder.clear();
                if (this.italicsStartPosition != -1) {
                    this.italicsStartPosition = 0;
                }
                if (this.underlineStartPosition != -1) {
                    this.underlineStartPosition = 0;
                }
                if (this.foregroundColorStartPosition != -1) {
                    this.foregroundColorStartPosition = 0;
                }
                if (this.backgroundColorStartPosition != -1) {
                    this.backgroundColorStartPosition = 0;
                }
                while (true) {
                    if ((this.rowLock && this.rolledUpCaptions.size() >= this.rowCount) || this.rolledUpCaptions.size() >= 15) {
                        this.rolledUpCaptions.remove(0);
                    } else {
                        return;
                    }
                }
            }
            this.captionStringBuilder.append(c);
        }

        public void backspace() {
            int length = this.captionStringBuilder.length();
            if (length > 0) {
                this.captionStringBuilder.delete(length - 1, length);
            }
        }

        public Cea708Cue build() {
            if (isEmpty()) {
                return null;
            }
            Alignment alignment;
            float f;
            float f2;
            CharSequence spannableStringBuilder = new SpannableStringBuilder();
            for (int i = 0; i < this.rolledUpCaptions.size(); i++) {
                spannableStringBuilder.append((CharSequence) this.rolledUpCaptions.get(i));
                spannableStringBuilder.append('\n');
            }
            spannableStringBuilder.append(buildSpannableString());
            switch (this.justification) {
                case 0:
                case 3:
                    alignment = Alignment.ALIGN_NORMAL;
                    break;
                case 1:
                    alignment = Alignment.ALIGN_OPPOSITE;
                    break;
                case 2:
                    alignment = Alignment.ALIGN_CENTER;
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected justification value: " + this.justification);
            }
            if (this.relativePositioning) {
                f = ((float) this.horizontalAnchor) / 99.0f;
                f2 = ((float) this.verticalAnchor) / 99.0f;
            } else {
                f = ((float) this.horizontalAnchor) / 209.0f;
                f2 = ((float) this.verticalAnchor) / 74.0f;
            }
            float f3 = (f * 0.9f) + 0.05f;
            f = (f2 * 0.9f) + 0.05f;
            int i2 = this.anchorId % 3 == 0 ? 0 : this.anchorId % 3 == 1 ? 1 : 2;
            int i3 = this.anchorId / 3 == 0 ? 0 : this.anchorId / 3 == 1 ? 1 : 2;
            return new Cea708Cue(spannableStringBuilder, alignment, f, 0, i2, f3, i3, Float.MIN_VALUE, this.windowFillColor != COLOR_SOLID_BLACK, this.windowFillColor, this.priority);
        }

        public SpannableString buildSpannableString() {
            CharSequence spannableStringBuilder = new SpannableStringBuilder(this.captionStringBuilder);
            int length = spannableStringBuilder.length();
            if (length > 0) {
                if (this.italicsStartPosition != -1) {
                    spannableStringBuilder.setSpan(new StyleSpan(2), this.italicsStartPosition, length, 33);
                }
                if (this.underlineStartPosition != -1) {
                    spannableStringBuilder.setSpan(new UnderlineSpan(), this.underlineStartPosition, length, 33);
                }
                if (this.foregroundColorStartPosition != -1) {
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(this.foregroundColor), this.foregroundColorStartPosition, length, 33);
                }
                if (this.backgroundColorStartPosition != -1) {
                    spannableStringBuilder.setSpan(new BackgroundColorSpan(this.backgroundColor), this.backgroundColorStartPosition, length, 33);
                }
            }
            return new SpannableString(spannableStringBuilder);
        }

        public void clear() {
            this.rolledUpCaptions.clear();
            this.captionStringBuilder.clear();
            this.italicsStartPosition = -1;
            this.underlineStartPosition = -1;
            this.foregroundColorStartPosition = -1;
            this.backgroundColorStartPosition = -1;
            this.row = 0;
        }

        public void defineWindow(boolean z, boolean z2, boolean z3, int i, boolean z4, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            this.defined = true;
            this.visible = z;
            this.rowLock = z2;
            this.priority = i;
            this.relativePositioning = z4;
            this.verticalAnchor = i2;
            this.horizontalAnchor = i3;
            this.anchorId = i6;
            if (this.rowCount != i4 + 1) {
                this.rowCount = i4 + 1;
                while (true) {
                    if ((!z2 || this.rolledUpCaptions.size() < this.rowCount) && this.rolledUpCaptions.size() < 15) {
                        break;
                    }
                    this.rolledUpCaptions.remove(0);
                }
            }
            if (!(i7 == 0 || this.windowStyleId == i7)) {
                this.windowStyleId = i7;
                int i9 = i7 - 1;
                setWindowAttributes(WINDOW_STYLE_FILL[i9], COLOR_TRANSPARENT, WINDOW_STYLE_WORD_WRAP[i9], 0, WINDOW_STYLE_PRINT_DIRECTION[i9], WINDOW_STYLE_SCROLL_DIRECTION[i9], WINDOW_STYLE_JUSTIFICATION[i9]);
            }
            if (i8 != 0 && this.penStyleId != i8) {
                this.penStyleId = i8;
                int i10 = i8 - 1;
                setPenAttributes(0, 1, 1, false, false, PEN_STYLE_EDGE_TYPE[i10], PEN_STYLE_FONT_STYLE[i10]);
                setPenColor(COLOR_SOLID_WHITE, PEN_STYLE_BACKGROUND[i10], COLOR_SOLID_BLACK);
            }
        }

        public boolean isDefined() {
            return this.defined;
        }

        public boolean isEmpty() {
            return !isDefined() || (this.rolledUpCaptions.isEmpty() && this.captionStringBuilder.length() == 0);
        }

        public boolean isVisible() {
            return this.visible;
        }

        public void reset() {
            clear();
            this.defined = false;
            this.visible = false;
            this.priority = 4;
            this.relativePositioning = false;
            this.verticalAnchor = 0;
            this.horizontalAnchor = 0;
            this.anchorId = 0;
            this.rowCount = 15;
            this.rowLock = true;
            this.justification = 0;
            this.windowStyleId = 0;
            this.penStyleId = 0;
            this.windowFillColor = COLOR_SOLID_BLACK;
            this.foregroundColor = COLOR_SOLID_WHITE;
            this.backgroundColor = COLOR_SOLID_BLACK;
        }

        public void setPenAttributes(int i, int i2, int i3, boolean z, boolean z2, int i4, int i5) {
            if (this.italicsStartPosition != -1) {
                if (!z) {
                    this.captionStringBuilder.setSpan(new StyleSpan(2), this.italicsStartPosition, this.captionStringBuilder.length(), 33);
                    this.italicsStartPosition = -1;
                }
            } else if (z) {
                this.italicsStartPosition = this.captionStringBuilder.length();
            }
            if (this.underlineStartPosition != -1) {
                if (!z2) {
                    this.captionStringBuilder.setSpan(new UnderlineSpan(), this.underlineStartPosition, this.captionStringBuilder.length(), 33);
                    this.underlineStartPosition = -1;
                }
            } else if (z2) {
                this.underlineStartPosition = this.captionStringBuilder.length();
            }
        }

        public void setPenColor(int i, int i2, int i3) {
            if (!(this.foregroundColorStartPosition == -1 || this.foregroundColor == i)) {
                this.captionStringBuilder.setSpan(new ForegroundColorSpan(this.foregroundColor), this.foregroundColorStartPosition, this.captionStringBuilder.length(), 33);
            }
            if (i != COLOR_SOLID_WHITE) {
                this.foregroundColorStartPosition = this.captionStringBuilder.length();
                this.foregroundColor = i;
            }
            if (!(this.backgroundColorStartPosition == -1 || this.backgroundColor == i2)) {
                this.captionStringBuilder.setSpan(new BackgroundColorSpan(this.backgroundColor), this.backgroundColorStartPosition, this.captionStringBuilder.length(), 33);
            }
            if (i2 != COLOR_SOLID_BLACK) {
                this.backgroundColorStartPosition = this.captionStringBuilder.length();
                this.backgroundColor = i2;
            }
        }

        public void setPenLocation(int i, int i2) {
            if (this.row != i) {
                append('\n');
            }
            this.row = i;
        }

        public void setVisibility(boolean z) {
            this.visible = z;
        }

        public void setWindowAttributes(int i, int i2, boolean z, int i3, int i4, int i5, int i6) {
            this.windowFillColor = i;
            this.justification = i6;
        }
    }

    private static final class DtvCcPacket {
        int currentIndex = 0;
        public final byte[] packetData;
        public final int packetSize;
        public final int sequenceNumber;

        public DtvCcPacket(int i, int i2) {
            this.sequenceNumber = i;
            this.packetSize = i2;
            this.packetData = new byte[((i2 * 2) - 1)];
        }
    }

    public Cea708Decoder(int i) {
        if (i == -1) {
            i = 1;
        }
        this.selectedServiceNumber = i;
        this.cueBuilders = new CueBuilder[8];
        for (int i2 = 0; i2 < 8; i2++) {
            this.cueBuilders[i2] = new CueBuilder();
        }
        this.currentCueBuilder = this.cueBuilders[0];
        resetCueBuilders();
    }

    private void finalizeCurrentPacket() {
        if (this.currentDtvCcPacket != null) {
            processCurrentPacket();
            this.currentDtvCcPacket = null;
        }
    }

    private List<Cue> getDisplayCues() {
        List arrayList = new ArrayList();
        int i = 0;
        while (i < 8) {
            if (!this.cueBuilders[i].isEmpty() && this.cueBuilders[i].isVisible()) {
                arrayList.add(this.cueBuilders[i].build());
            }
            i++;
        }
        Collections.sort(arrayList);
        return Collections.unmodifiableList(arrayList);
    }

    private void handleC0Command(int i) {
        switch (i) {
            case 0:
            case 14:
                return;
            case 3:
                this.cues = getDisplayCues();
                return;
            case 8:
                this.currentCueBuilder.backspace();
                return;
            case 12:
                resetCueBuilders();
                return;
            case 13:
                this.currentCueBuilder.append('\n');
                return;
            default:
                if (i >= 17 && i <= 23) {
                    Log.w(TAG, "Currently unsupported COMMAND_EXT1 Command: " + i);
                    this.serviceBlockPacket.skipBits(8);
                    return;
                } else if (i < 24 || i > 31) {
                    Log.w(TAG, "Invalid C0 command: " + i);
                    return;
                } else {
                    Log.w(TAG, "Currently unsupported COMMAND_P16 Command: " + i);
                    this.serviceBlockPacket.skipBits(16);
                    return;
                }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handleC1Command(int r7) {
        /*
        r6 = this;
        r3 = 16;
        r2 = 0;
        r5 = 8;
        r1 = 1;
        switch(r7) {
            case 128: goto L_0x0024;
            case 129: goto L_0x0024;
            case 130: goto L_0x0024;
            case 131: goto L_0x0024;
            case 132: goto L_0x0024;
            case 133: goto L_0x0024;
            case 134: goto L_0x0024;
            case 135: goto L_0x0024;
            case 136: goto L_0x0033;
            case 137: goto L_0x0049;
            case 138: goto L_0x0060;
            case 139: goto L_0x0076;
            case 140: goto L_0x0097;
            case 141: goto L_0x00ad;
            case 142: goto L_0x0023;
            case 143: goto L_0x00b4;
            case 144: goto L_0x00b9;
            case 145: goto L_0x00cd;
            case 146: goto L_0x00e3;
            case 147: goto L_0x0009;
            case 148: goto L_0x0009;
            case 149: goto L_0x0009;
            case 150: goto L_0x0009;
            case 151: goto L_0x00f7;
            case 152: goto L_0x010d;
            case 153: goto L_0x010d;
            case 154: goto L_0x010d;
            case 155: goto L_0x010d;
            case 156: goto L_0x010d;
            case 157: goto L_0x010d;
            case 158: goto L_0x010d;
            case 159: goto L_0x010d;
            default: goto L_0x0009;
        };
    L_0x0009:
        r0 = "Cea708Decoder";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Invalid C1 command: ";
        r1 = r1.append(r2);
        r1 = r1.append(r7);
        r1 = r1.toString();
        android.util.Log.w(r0, r1);
    L_0x0023:
        return;
    L_0x0024:
        r0 = r7 + -128;
        r1 = r6.currentWindow;
        if (r1 == r0) goto L_0x0023;
    L_0x002a:
        r6.currentWindow = r0;
        r1 = r6.cueBuilders;
        r0 = r1[r0];
        r6.currentCueBuilder = r0;
        goto L_0x0023;
    L_0x0033:
        if (r1 > r5) goto L_0x0023;
    L_0x0035:
        r0 = r6.serviceBlockPacket;
        r0 = r0.readBit();
        if (r0 == 0) goto L_0x0046;
    L_0x003d:
        r0 = r6.cueBuilders;
        r2 = 8 - r1;
        r0 = r0[r2];
        r0.clear();
    L_0x0046:
        r1 = r1 + 1;
        goto L_0x0033;
    L_0x0049:
        r0 = r1;
    L_0x004a:
        if (r0 > r5) goto L_0x0023;
    L_0x004c:
        r2 = r6.serviceBlockPacket;
        r2 = r2.readBit();
        if (r2 == 0) goto L_0x005d;
    L_0x0054:
        r2 = r6.cueBuilders;
        r3 = 8 - r0;
        r2 = r2[r3];
        r2.setVisibility(r1);
    L_0x005d:
        r0 = r0 + 1;
        goto L_0x004a;
    L_0x0060:
        if (r1 > r5) goto L_0x0023;
    L_0x0062:
        r0 = r6.serviceBlockPacket;
        r0 = r0.readBit();
        if (r0 == 0) goto L_0x0073;
    L_0x006a:
        r0 = r6.cueBuilders;
        r3 = 8 - r1;
        r0 = r0[r3];
        r0.setVisibility(r2);
    L_0x0073:
        r1 = r1 + 1;
        goto L_0x0060;
    L_0x0076:
        r3 = r1;
    L_0x0077:
        if (r3 > r5) goto L_0x0023;
    L_0x0079:
        r0 = r6.serviceBlockPacket;
        r0 = r0.readBit();
        if (r0 == 0) goto L_0x0091;
    L_0x0081:
        r0 = r6.cueBuilders;
        r4 = 8 - r3;
        r4 = r0[r4];
        r0 = r4.isVisible();
        if (r0 != 0) goto L_0x0095;
    L_0x008d:
        r0 = r1;
    L_0x008e:
        r4.setVisibility(r0);
    L_0x0091:
        r0 = r3 + 1;
        r3 = r0;
        goto L_0x0077;
    L_0x0095:
        r0 = r2;
        goto L_0x008e;
    L_0x0097:
        if (r1 > r5) goto L_0x0023;
    L_0x0099:
        r0 = r6.serviceBlockPacket;
        r0 = r0.readBit();
        if (r0 == 0) goto L_0x00aa;
    L_0x00a1:
        r0 = r6.cueBuilders;
        r2 = 8 - r1;
        r0 = r0[r2];
        r0.reset();
    L_0x00aa:
        r1 = r1 + 1;
        goto L_0x0097;
    L_0x00ad:
        r0 = r6.serviceBlockPacket;
        r0.skipBits(r5);
        goto L_0x0023;
    L_0x00b4:
        r6.resetCueBuilders();
        goto L_0x0023;
    L_0x00b9:
        r0 = r6.currentCueBuilder;
        r0 = r0.isDefined();
        if (r0 != 0) goto L_0x00c8;
    L_0x00c1:
        r0 = r6.serviceBlockPacket;
        r0.skipBits(r3);
        goto L_0x0023;
    L_0x00c8:
        r6.handleSetPenAttributes();
        goto L_0x0023;
    L_0x00cd:
        r0 = r6.currentCueBuilder;
        r0 = r0.isDefined();
        if (r0 != 0) goto L_0x00de;
    L_0x00d5:
        r0 = r6.serviceBlockPacket;
        r1 = 24;
        r0.skipBits(r1);
        goto L_0x0023;
    L_0x00de:
        r6.handleSetPenColor();
        goto L_0x0023;
    L_0x00e3:
        r0 = r6.currentCueBuilder;
        r0 = r0.isDefined();
        if (r0 != 0) goto L_0x00f2;
    L_0x00eb:
        r0 = r6.serviceBlockPacket;
        r0.skipBits(r3);
        goto L_0x0023;
    L_0x00f2:
        r6.handleSetPenLocation();
        goto L_0x0023;
    L_0x00f7:
        r0 = r6.currentCueBuilder;
        r0 = r0.isDefined();
        if (r0 != 0) goto L_0x0108;
    L_0x00ff:
        r0 = r6.serviceBlockPacket;
        r1 = 32;
        r0.skipBits(r1);
        goto L_0x0023;
    L_0x0108:
        r6.handleSetWindowAttributes();
        goto L_0x0023;
    L_0x010d:
        r0 = r7 + -152;
        r6.handleDefineWindow(r0);
        r1 = r6.currentWindow;
        if (r1 == r0) goto L_0x0023;
    L_0x0116:
        r6.currentWindow = r0;
        r1 = r6.cueBuilders;
        r0 = r1[r0];
        r6.currentCueBuilder = r0;
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.text.cea.Cea708Decoder.handleC1Command(int):void");
    }

    private void handleC2Command(int i) {
        if (i > 7) {
            if (i <= 15) {
                this.serviceBlockPacket.skipBits(8);
            } else if (i <= 23) {
                this.serviceBlockPacket.skipBits(16);
            } else if (i <= 31) {
                this.serviceBlockPacket.skipBits(24);
            }
        }
    }

    private void handleC3Command(int i) {
        if (i <= 135) {
            this.serviceBlockPacket.skipBits(32);
        } else if (i <= COMMAND_RST) {
            this.serviceBlockPacket.skipBits(40);
        } else if (i <= 159) {
            this.serviceBlockPacket.skipBits(2);
            this.serviceBlockPacket.skipBits(this.serviceBlockPacket.readBits(6) * 8);
        }
    }

    private void handleDefineWindow(int i) {
        CueBuilder cueBuilder = this.cueBuilders[i];
        this.serviceBlockPacket.skipBits(2);
        boolean readBit = this.serviceBlockPacket.readBit();
        boolean readBit2 = this.serviceBlockPacket.readBit();
        boolean readBit3 = this.serviceBlockPacket.readBit();
        int readBits = this.serviceBlockPacket.readBits(3);
        boolean readBit4 = this.serviceBlockPacket.readBit();
        int readBits2 = this.serviceBlockPacket.readBits(7);
        int readBits3 = this.serviceBlockPacket.readBits(8);
        int readBits4 = this.serviceBlockPacket.readBits(4);
        int readBits5 = this.serviceBlockPacket.readBits(4);
        this.serviceBlockPacket.skipBits(2);
        int readBits6 = this.serviceBlockPacket.readBits(6);
        this.serviceBlockPacket.skipBits(2);
        cueBuilder.defineWindow(readBit, readBit2, readBit3, readBits, readBit4, readBits2, readBits3, readBits5, readBits6, readBits4, this.serviceBlockPacket.readBits(3), this.serviceBlockPacket.readBits(3));
    }

    private void handleG0Character(int i) {
        if (i == 127) {
            this.currentCueBuilder.append('♫');
        } else {
            this.currentCueBuilder.append((char) (i & 255));
        }
    }

    private void handleG1Character(int i) {
        this.currentCueBuilder.append((char) (i & 255));
    }

    private void handleG2Character(int i) {
        switch (i) {
            case 32:
                this.currentCueBuilder.append(' ');
                return;
            case 33:
                this.currentCueBuilder.append(' ');
                return;
            case 37:
                this.currentCueBuilder.append('…');
                return;
            case 42:
                this.currentCueBuilder.append('Š');
                return;
            case 44:
                this.currentCueBuilder.append('Œ');
                return;
            case 48:
                this.currentCueBuilder.append('█');
                return;
            case 49:
                this.currentCueBuilder.append('‘');
                return;
            case 50:
                this.currentCueBuilder.append('’');
                return;
            case 51:
                this.currentCueBuilder.append('“');
                return;
            case 52:
                this.currentCueBuilder.append('”');
                return;
            case 53:
                this.currentCueBuilder.append('•');
                return;
            case 57:
                this.currentCueBuilder.append('™');
                return;
            case 58:
                this.currentCueBuilder.append('š');
                return;
            case 60:
                this.currentCueBuilder.append('œ');
                return;
            case 61:
                this.currentCueBuilder.append('℠');
                return;
            case 63:
                this.currentCueBuilder.append('Ÿ');
                return;
            case CHARACTER_ONE_EIGHTH /*118*/:
                this.currentCueBuilder.append('⅛');
                return;
            case CHARACTER_THREE_EIGHTHS /*119*/:
                this.currentCueBuilder.append('⅜');
                return;
            case CHARACTER_FIVE_EIGHTHS /*120*/:
                this.currentCueBuilder.append('⅝');
                return;
            case CHARACTER_SEVEN_EIGHTHS /*121*/:
                this.currentCueBuilder.append('⅞');
                return;
            case CHARACTER_VERTICAL_BORDER /*122*/:
                this.currentCueBuilder.append('│');
                return;
            case CHARACTER_UPPER_RIGHT_BORDER /*123*/:
                this.currentCueBuilder.append('┐');
                return;
            case CHARACTER_LOWER_LEFT_BORDER /*124*/:
                this.currentCueBuilder.append('└');
                return;
            case CHARACTER_HORIZONTAL_BORDER /*125*/:
                this.currentCueBuilder.append('─');
                return;
            case CHARACTER_LOWER_RIGHT_BORDER /*126*/:
                this.currentCueBuilder.append('┘');
                return;
            case 127:
                this.currentCueBuilder.append('┌');
                return;
            default:
                Log.w(TAG, "Invalid G2 character: " + i);
                return;
        }
    }

    private void handleG3Character(int i) {
        if (i == 160) {
            this.currentCueBuilder.append('㏄');
            return;
        }
        Log.w(TAG, "Invalid G3 character: " + i);
        this.currentCueBuilder.append('_');
    }

    private void handleSetPenAttributes() {
        this.currentCueBuilder.setPenAttributes(this.serviceBlockPacket.readBits(4), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBit(), this.serviceBlockPacket.readBit(), this.serviceBlockPacket.readBits(3), this.serviceBlockPacket.readBits(3));
    }

    private void handleSetPenColor() {
        int argbColorFromCeaColor = CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        int argbColorFromCeaColor2 = CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        this.serviceBlockPacket.skipBits(2);
        this.currentCueBuilder.setPenColor(argbColorFromCeaColor, argbColorFromCeaColor2, CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2)));
    }

    private void handleSetPenLocation() {
        this.serviceBlockPacket.skipBits(4);
        int readBits = this.serviceBlockPacket.readBits(4);
        this.serviceBlockPacket.skipBits(2);
        this.currentCueBuilder.setPenLocation(readBits, this.serviceBlockPacket.readBits(6));
    }

    private void handleSetWindowAttributes() {
        int argbColorFromCeaColor = CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        int readBits = this.serviceBlockPacket.readBits(2);
        int argbColorFromCeaColor2 = CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        if (this.serviceBlockPacket.readBit()) {
            readBits |= 4;
        }
        boolean readBit = this.serviceBlockPacket.readBit();
        int readBits2 = this.serviceBlockPacket.readBits(2);
        int readBits3 = this.serviceBlockPacket.readBits(2);
        int readBits4 = this.serviceBlockPacket.readBits(2);
        this.serviceBlockPacket.skipBits(8);
        this.currentCueBuilder.setWindowAttributes(argbColorFromCeaColor, argbColorFromCeaColor2, readBit, readBits, readBits2, readBits3, readBits4);
    }

    private void processCurrentPacket() {
        if (this.currentDtvCcPacket.currentIndex != (this.currentDtvCcPacket.packetSize * 2) - 1) {
            Log.w(TAG, "DtvCcPacket ended prematurely; size is " + ((this.currentDtvCcPacket.packetSize * 2) - 1) + ", but current index is " + this.currentDtvCcPacket.currentIndex + " (sequence number " + this.currentDtvCcPacket.sequenceNumber + "); ignoring packet");
            return;
        }
        this.serviceBlockPacket.reset(this.currentDtvCcPacket.packetData, this.currentDtvCcPacket.currentIndex);
        int readBits = this.serviceBlockPacket.readBits(3);
        int readBits2 = this.serviceBlockPacket.readBits(5);
        if (readBits == 7) {
            this.serviceBlockPacket.skipBits(2);
            readBits += this.serviceBlockPacket.readBits(6);
        }
        if (readBits2 == 0) {
            if (readBits != 0) {
                Log.w(TAG, "serviceNumber is non-zero (" + readBits + ") when blockSize is 0");
            }
        } else if (readBits == this.selectedServiceNumber) {
            Object obj = null;
            while (this.serviceBlockPacket.bitsLeft() > 0) {
                readBits2 = this.serviceBlockPacket.readBits(8);
                if (readBits2 == 16) {
                    readBits2 = this.serviceBlockPacket.readBits(8);
                    if (readBits2 <= 31) {
                        handleC2Command(readBits2);
                    } else if (readBits2 <= 127) {
                        handleG2Character(readBits2);
                        obj = 1;
                    } else if (readBits2 <= 159) {
                        handleC3Command(readBits2);
                    } else if (readBits2 <= 255) {
                        handleG3Character(readBits2);
                        obj = 1;
                    } else {
                        Log.w(TAG, "Invalid extended command: " + readBits2);
                    }
                } else if (readBits2 <= 31) {
                    handleC0Command(readBits2);
                } else if (readBits2 <= 127) {
                    handleG0Character(readBits2);
                    obj = 1;
                } else if (readBits2 <= 159) {
                    handleC1Command(readBits2);
                    obj = 1;
                } else if (readBits2 <= 255) {
                    handleG1Character(readBits2);
                    obj = 1;
                } else {
                    Log.w(TAG, "Invalid base command: " + readBits2);
                }
            }
            if (obj != null) {
                this.cues = getDisplayCues();
            }
        }
    }

    private void resetCueBuilders() {
        for (int i = 0; i < 8; i++) {
            this.cueBuilders[i].reset();
        }
    }

    protected Subtitle createSubtitle() {
        this.lastCues = this.cues;
        return new CeaSubtitle(this.cues);
    }

    protected void decode(SubtitleInputBuffer subtitleInputBuffer) {
        this.ccData.reset(subtitleInputBuffer.data.array(), subtitleInputBuffer.data.limit());
        while (this.ccData.bytesLeft() >= 3) {
            int readUnsignedByte = this.ccData.readUnsignedByte() & 7;
            int i = readUnsignedByte & 3;
            Object obj = (readUnsignedByte & 4) == 4 ? 1 : null;
            byte readUnsignedByte2 = (byte) this.ccData.readUnsignedByte();
            byte readUnsignedByte3 = (byte) this.ccData.readUnsignedByte();
            if ((i == 2 || i == 3) && obj != null) {
                byte[] bArr;
                DtvCcPacket dtvCcPacket;
                int i2;
                if (i == 3) {
                    finalizeCurrentPacket();
                    i = (readUnsignedByte2 & PsExtractor.AUDIO_STREAM) >> 6;
                    readUnsignedByte = readUnsignedByte2 & 63;
                    if (readUnsignedByte == 0) {
                        readUnsignedByte = 64;
                    }
                    this.currentDtvCcPacket = new DtvCcPacket(i, readUnsignedByte);
                    bArr = this.currentDtvCcPacket.packetData;
                    dtvCcPacket = this.currentDtvCcPacket;
                    i2 = dtvCcPacket.currentIndex;
                    dtvCcPacket.currentIndex = i2 + 1;
                    bArr[i2] = readUnsignedByte3;
                } else {
                    Assertions.checkArgument(i == 2);
                    if (this.currentDtvCcPacket == null) {
                        Log.e(TAG, "Encountered DTVCC_PACKET_DATA before DTVCC_PACKET_START");
                    } else {
                        bArr = this.currentDtvCcPacket.packetData;
                        dtvCcPacket = this.currentDtvCcPacket;
                        int i3 = dtvCcPacket.currentIndex;
                        dtvCcPacket.currentIndex = i3 + 1;
                        bArr[i3] = readUnsignedByte2;
                        bArr = this.currentDtvCcPacket.packetData;
                        dtvCcPacket = this.currentDtvCcPacket;
                        i2 = dtvCcPacket.currentIndex;
                        dtvCcPacket.currentIndex = i2 + 1;
                        bArr[i2] = readUnsignedByte3;
                    }
                }
                if (this.currentDtvCcPacket.currentIndex == (this.currentDtvCcPacket.packetSize * 2) - 1) {
                    finalizeCurrentPacket();
                }
            }
        }
    }

    public /* bridge */ /* synthetic */ SubtitleInputBuffer dequeueInputBuffer() {
        return super.dequeueInputBuffer();
    }

    public /* bridge */ /* synthetic */ SubtitleOutputBuffer dequeueOutputBuffer() {
        return super.dequeueOutputBuffer();
    }

    public void flush() {
        super.flush();
        this.cues = null;
        this.lastCues = null;
        this.currentWindow = 0;
        this.currentCueBuilder = this.cueBuilders[this.currentWindow];
        resetCueBuilders();
        this.currentDtvCcPacket = null;
    }

    public String getName() {
        return TAG;
    }

    protected boolean isNewSubtitleDataAvailable() {
        return this.cues != this.lastCues;
    }

    public /* bridge */ /* synthetic */ void queueInputBuffer(SubtitleInputBuffer subtitleInputBuffer) {
        super.queueInputBuffer(subtitleInputBuffer);
    }

    public /* bridge */ /* synthetic */ void release() {
        super.release();
    }

    public /* bridge */ /* synthetic */ void setPositionUs(long j) {
        super.setPositionUs(j);
    }
}
