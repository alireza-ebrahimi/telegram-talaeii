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
import org.telegram.messenger.exoplayer2.text.SubtitleDecoderException;
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

        public boolean isEmpty() {
            return !isDefined() || (this.rolledUpCaptions.isEmpty() && this.captionStringBuilder.length() == 0);
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

        public void clear() {
            this.rolledUpCaptions.clear();
            this.captionStringBuilder.clear();
            this.italicsStartPosition = -1;
            this.underlineStartPosition = -1;
            this.foregroundColorStartPosition = -1;
            this.backgroundColorStartPosition = -1;
            this.row = 0;
        }

        public boolean isDefined() {
            return this.defined;
        }

        public void setVisibility(boolean visible) {
            this.visible = visible;
        }

        public boolean isVisible() {
            return this.visible;
        }

        public void defineWindow(boolean visible, boolean rowLock, boolean columnLock, int priority, boolean relativePositioning, int verticalAnchor, int horizontalAnchor, int rowCount, int columnCount, int anchorId, int windowStyleId, int penStyleId) {
            this.defined = true;
            this.visible = visible;
            this.rowLock = rowLock;
            this.priority = priority;
            this.relativePositioning = relativePositioning;
            this.verticalAnchor = verticalAnchor;
            this.horizontalAnchor = horizontalAnchor;
            this.anchorId = anchorId;
            if (this.rowCount != rowCount + 1) {
                this.rowCount = rowCount + 1;
                while (true) {
                    if ((!rowLock || this.rolledUpCaptions.size() < this.rowCount) && this.rolledUpCaptions.size() < 15) {
                        break;
                    }
                    this.rolledUpCaptions.remove(0);
                }
            }
            if (!(windowStyleId == 0 || this.windowStyleId == windowStyleId)) {
                this.windowStyleId = windowStyleId;
                int windowStyleIdIndex = windowStyleId - 1;
                setWindowAttributes(WINDOW_STYLE_FILL[windowStyleIdIndex], COLOR_TRANSPARENT, WINDOW_STYLE_WORD_WRAP[windowStyleIdIndex], 0, WINDOW_STYLE_PRINT_DIRECTION[windowStyleIdIndex], WINDOW_STYLE_SCROLL_DIRECTION[windowStyleIdIndex], WINDOW_STYLE_JUSTIFICATION[windowStyleIdIndex]);
            }
            if (penStyleId != 0 && this.penStyleId != penStyleId) {
                this.penStyleId = penStyleId;
                int penStyleIdIndex = penStyleId - 1;
                setPenAttributes(0, 1, 1, false, false, PEN_STYLE_EDGE_TYPE[penStyleIdIndex], PEN_STYLE_FONT_STYLE[penStyleIdIndex]);
                setPenColor(COLOR_SOLID_WHITE, PEN_STYLE_BACKGROUND[penStyleIdIndex], COLOR_SOLID_BLACK);
            }
        }

        public void setWindowAttributes(int fillColor, int borderColor, boolean wordWrapToggle, int borderType, int printDirection, int scrollDirection, int justification) {
            this.windowFillColor = fillColor;
            this.justification = justification;
        }

        public void setPenAttributes(int textTag, int offset, int penSize, boolean italicsToggle, boolean underlineToggle, int edgeType, int fontStyle) {
            if (this.italicsStartPosition != -1) {
                if (!italicsToggle) {
                    this.captionStringBuilder.setSpan(new StyleSpan(2), this.italicsStartPosition, this.captionStringBuilder.length(), 33);
                    this.italicsStartPosition = -1;
                }
            } else if (italicsToggle) {
                this.italicsStartPosition = this.captionStringBuilder.length();
            }
            if (this.underlineStartPosition != -1) {
                if (!underlineToggle) {
                    this.captionStringBuilder.setSpan(new UnderlineSpan(), this.underlineStartPosition, this.captionStringBuilder.length(), 33);
                    this.underlineStartPosition = -1;
                }
            } else if (underlineToggle) {
                this.underlineStartPosition = this.captionStringBuilder.length();
            }
        }

        public void setPenColor(int foregroundColor, int backgroundColor, int edgeColor) {
            if (!(this.foregroundColorStartPosition == -1 || this.foregroundColor == foregroundColor)) {
                this.captionStringBuilder.setSpan(new ForegroundColorSpan(this.foregroundColor), this.foregroundColorStartPosition, this.captionStringBuilder.length(), 33);
            }
            if (foregroundColor != COLOR_SOLID_WHITE) {
                this.foregroundColorStartPosition = this.captionStringBuilder.length();
                this.foregroundColor = foregroundColor;
            }
            if (!(this.backgroundColorStartPosition == -1 || this.backgroundColor == backgroundColor)) {
                this.captionStringBuilder.setSpan(new BackgroundColorSpan(this.backgroundColor), this.backgroundColorStartPosition, this.captionStringBuilder.length(), 33);
            }
            if (backgroundColor != COLOR_SOLID_BLACK) {
                this.backgroundColorStartPosition = this.captionStringBuilder.length();
                this.backgroundColor = backgroundColor;
            }
        }

        public void setPenLocation(int row, int column) {
            if (this.row != row) {
                append('\n');
            }
            this.row = row;
        }

        public void backspace() {
            int length = this.captionStringBuilder.length();
            if (length > 0) {
                this.captionStringBuilder.delete(length - 1, length);
            }
        }

        public void append(char text) {
            if (text == '\n') {
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
            this.captionStringBuilder.append(text);
        }

        public SpannableString buildSpannableString() {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.captionStringBuilder);
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

        public Cea708Cue build() {
            boolean windowColorSet = true;
            if (isEmpty()) {
                return null;
            }
            Alignment alignment;
            float position;
            float line;
            int verticalAnchorType;
            int horizontalAnchorType;
            SpannableStringBuilder cueString = new SpannableStringBuilder();
            for (int i = 0; i < this.rolledUpCaptions.size(); i++) {
                cueString.append((CharSequence) this.rolledUpCaptions.get(i));
                cueString.append('\n');
            }
            cueString.append(buildSpannableString());
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
                position = ((float) this.horizontalAnchor) / 99.0f;
                line = ((float) this.verticalAnchor) / 99.0f;
            } else {
                position = ((float) this.horizontalAnchor) / 209.0f;
                line = ((float) this.verticalAnchor) / 74.0f;
            }
            position = (position * 0.9f) + 0.05f;
            line = (line * 0.9f) + 0.05f;
            if (this.anchorId % 3 == 0) {
                verticalAnchorType = 0;
            } else if (this.anchorId % 3 == 1) {
                verticalAnchorType = 1;
            } else {
                verticalAnchorType = 2;
            }
            if (this.anchorId / 3 == 0) {
                horizontalAnchorType = 0;
            } else if (this.anchorId / 3 == 1) {
                horizontalAnchorType = 1;
            } else {
                horizontalAnchorType = 2;
            }
            if (this.windowFillColor == COLOR_SOLID_BLACK) {
                windowColorSet = false;
            }
            return new Cea708Cue(cueString, alignment, line, 0, verticalAnchorType, position, horizontalAnchorType, Float.MIN_VALUE, windowColorSet, this.windowFillColor, this.priority);
        }

        public static int getArgbColorFromCeaColor(int red, int green, int blue) {
            return getArgbColorFromCeaColor(red, green, blue, 0);
        }

        public static int getArgbColorFromCeaColor(int red, int green, int blue, int opacity) {
            int alpha;
            int i;
            int i2;
            int i3 = 255;
            Assertions.checkIndex(red, 0, 4);
            Assertions.checkIndex(green, 0, 4);
            Assertions.checkIndex(blue, 0, 4);
            Assertions.checkIndex(opacity, 0, 4);
            switch (opacity) {
                case 0:
                case 1:
                    alpha = 255;
                    break;
                case 2:
                    alpha = 127;
                    break;
                case 3:
                    alpha = 0;
                    break;
                default:
                    alpha = 255;
                    break;
            }
            if (red > 1) {
                i = 255;
            } else {
                i = 0;
            }
            if (green > 1) {
                i2 = 255;
            } else {
                i2 = 0;
            }
            if (blue <= 1) {
                i3 = 0;
            }
            return Color.argb(alpha, i, i2, i3);
        }
    }

    private static final class DtvCcPacket {
        int currentIndex = 0;
        public final byte[] packetData;
        public final int packetSize;
        public final int sequenceNumber;

        public DtvCcPacket(int sequenceNumber, int packetSize) {
            this.sequenceNumber = sequenceNumber;
            this.packetSize = packetSize;
            this.packetData = new byte[((packetSize * 2) - 1)];
        }
    }

    public /* bridge */ /* synthetic */ SubtitleInputBuffer dequeueInputBuffer() throws SubtitleDecoderException {
        return super.dequeueInputBuffer();
    }

    public /* bridge */ /* synthetic */ SubtitleOutputBuffer dequeueOutputBuffer() throws SubtitleDecoderException {
        return super.dequeueOutputBuffer();
    }

    public /* bridge */ /* synthetic */ void queueInputBuffer(SubtitleInputBuffer subtitleInputBuffer) throws SubtitleDecoderException {
        super.queueInputBuffer(subtitleInputBuffer);
    }

    public /* bridge */ /* synthetic */ void release() {
        super.release();
    }

    public /* bridge */ /* synthetic */ void setPositionUs(long j) {
        super.setPositionUs(j);
    }

    public Cea708Decoder(int accessibilityChannel) {
        if (accessibilityChannel == -1) {
            accessibilityChannel = 1;
        }
        this.selectedServiceNumber = accessibilityChannel;
        this.cueBuilders = new CueBuilder[8];
        for (int i = 0; i < 8; i++) {
            this.cueBuilders[i] = new CueBuilder();
        }
        this.currentCueBuilder = this.cueBuilders[0];
        resetCueBuilders();
    }

    public String getName() {
        return TAG;
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

    protected boolean isNewSubtitleDataAvailable() {
        return this.cues != this.lastCues;
    }

    protected Subtitle createSubtitle() {
        this.lastCues = this.cues;
        return new CeaSubtitle(this.cues);
    }

    protected void decode(SubtitleInputBuffer inputBuffer) {
        this.ccData.reset(inputBuffer.data.array(), inputBuffer.data.limit());
        while (this.ccData.bytesLeft() >= 3) {
            int ccTypeAndValid = this.ccData.readUnsignedByte() & 7;
            int ccType = ccTypeAndValid & 3;
            boolean ccValid = (ccTypeAndValid & 4) == 4;
            byte ccData1 = (byte) this.ccData.readUnsignedByte();
            byte ccData2 = (byte) this.ccData.readUnsignedByte();
            if ((ccType == 2 || ccType == 3) && ccValid) {
                byte[] bArr;
                DtvCcPacket dtvCcPacket;
                int i;
                if (ccType == 3) {
                    finalizeCurrentPacket();
                    int sequenceNumber = (ccData1 & PsExtractor.AUDIO_STREAM) >> 6;
                    int packetSize = ccData1 & 63;
                    if (packetSize == 0) {
                        packetSize = 64;
                    }
                    this.currentDtvCcPacket = new DtvCcPacket(sequenceNumber, packetSize);
                    bArr = this.currentDtvCcPacket.packetData;
                    dtvCcPacket = this.currentDtvCcPacket;
                    i = dtvCcPacket.currentIndex;
                    dtvCcPacket.currentIndex = i + 1;
                    bArr[i] = ccData2;
                } else {
                    Assertions.checkArgument(ccType == 2);
                    if (this.currentDtvCcPacket == null) {
                        Log.e(TAG, "Encountered DTVCC_PACKET_DATA before DTVCC_PACKET_START");
                    } else {
                        bArr = this.currentDtvCcPacket.packetData;
                        dtvCcPacket = this.currentDtvCcPacket;
                        i = dtvCcPacket.currentIndex;
                        dtvCcPacket.currentIndex = i + 1;
                        bArr[i] = ccData1;
                        bArr = this.currentDtvCcPacket.packetData;
                        dtvCcPacket = this.currentDtvCcPacket;
                        i = dtvCcPacket.currentIndex;
                        dtvCcPacket.currentIndex = i + 1;
                        bArr[i] = ccData2;
                    }
                }
                if (this.currentDtvCcPacket.currentIndex == (this.currentDtvCcPacket.packetSize * 2) - 1) {
                    finalizeCurrentPacket();
                }
            }
        }
    }

    private void finalizeCurrentPacket() {
        if (this.currentDtvCcPacket != null) {
            processCurrentPacket();
            this.currentDtvCcPacket = null;
        }
    }

    private void processCurrentPacket() {
        if (this.currentDtvCcPacket.currentIndex != (this.currentDtvCcPacket.packetSize * 2) - 1) {
            Log.w(TAG, "DtvCcPacket ended prematurely; size is " + ((this.currentDtvCcPacket.packetSize * 2) - 1) + ", but current index is " + this.currentDtvCcPacket.currentIndex + " (sequence number " + this.currentDtvCcPacket.sequenceNumber + "); ignoring packet");
            return;
        }
        this.serviceBlockPacket.reset(this.currentDtvCcPacket.packetData, this.currentDtvCcPacket.currentIndex);
        int serviceNumber = this.serviceBlockPacket.readBits(3);
        int blockSize = this.serviceBlockPacket.readBits(5);
        if (serviceNumber == 7) {
            this.serviceBlockPacket.skipBits(2);
            serviceNumber += this.serviceBlockPacket.readBits(6);
        }
        if (blockSize == 0) {
            if (serviceNumber != 0) {
                Log.w(TAG, "serviceNumber is non-zero (" + serviceNumber + ") when blockSize is 0");
            }
        } else if (serviceNumber == this.selectedServiceNumber) {
            boolean cuesNeedUpdate = false;
            while (this.serviceBlockPacket.bitsLeft() > 0) {
                int command = this.serviceBlockPacket.readBits(8);
                if (command == 16) {
                    command = this.serviceBlockPacket.readBits(8);
                    if (command <= 31) {
                        handleC2Command(command);
                    } else if (command <= 127) {
                        handleG2Character(command);
                        cuesNeedUpdate = true;
                    } else if (command <= 159) {
                        handleC3Command(command);
                    } else if (command <= 255) {
                        handleG3Character(command);
                        cuesNeedUpdate = true;
                    } else {
                        Log.w(TAG, "Invalid extended command: " + command);
                    }
                } else if (command <= 31) {
                    handleC0Command(command);
                } else if (command <= 127) {
                    handleG0Character(command);
                    cuesNeedUpdate = true;
                } else if (command <= 159) {
                    handleC1Command(command);
                    cuesNeedUpdate = true;
                } else if (command <= 255) {
                    handleG1Character(command);
                    cuesNeedUpdate = true;
                } else {
                    Log.w(TAG, "Invalid base command: " + command);
                }
            }
            if (cuesNeedUpdate) {
                this.cues = getDisplayCues();
            }
        }
    }

    private void handleC0Command(int command) {
        switch (command) {
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
                if (command >= 17 && command <= 23) {
                    Log.w(TAG, "Currently unsupported COMMAND_EXT1 Command: " + command);
                    this.serviceBlockPacket.skipBits(8);
                    return;
                } else if (command < 24 || command > 31) {
                    Log.w(TAG, "Invalid C0 command: " + command);
                    return;
                } else {
                    Log.w(TAG, "Currently unsupported COMMAND_P16 Command: " + command);
                    this.serviceBlockPacket.skipBits(16);
                    return;
                }
        }
    }

    private void handleC1Command(int command) {
        int window;
        int i;
        switch (command) {
            case 128:
            case 129:
            case 130:
            case COMMAND_CW3 /*131*/:
            case COMMAND_CW4 /*132*/:
            case COMMAND_CW5 /*133*/:
            case 134:
            case 135:
                window = command - 128;
                if (this.currentWindow != window) {
                    this.currentWindow = window;
                    this.currentCueBuilder = this.cueBuilders[window];
                    return;
                }
                return;
            case COMMAND_CLW /*136*/:
                for (i = 1; i <= 8; i++) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueBuilders[8 - i].clear();
                    }
                }
                return;
            case COMMAND_DSW /*137*/:
                for (i = 1; i <= 8; i++) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueBuilders[8 - i].setVisibility(true);
                    }
                }
                return;
            case 138:
                for (i = 1; i <= 8; i++) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueBuilders[8 - i].setVisibility(false);
                    }
                }
                return;
            case COMMAND_TGW /*139*/:
                for (i = 1; i <= 8; i++) {
                    if (this.serviceBlockPacket.readBit()) {
                        boolean z;
                        CueBuilder cueBuilder = this.cueBuilders[8 - i];
                        if (cueBuilder.isVisible()) {
                            z = false;
                        } else {
                            z = true;
                        }
                        cueBuilder.setVisibility(z);
                    }
                }
                return;
            case COMMAND_DLW /*140*/:
                for (i = 1; i <= 8; i++) {
                    if (this.serviceBlockPacket.readBit()) {
                        this.cueBuilders[8 - i].reset();
                    }
                }
                return;
            case COMMAND_DLY /*141*/:
                this.serviceBlockPacket.skipBits(8);
                return;
            case COMMAND_DLC /*142*/:
                return;
            case COMMAND_RST /*143*/:
                resetCueBuilders();
                return;
            case COMMAND_SPA /*144*/:
                if (this.currentCueBuilder.isDefined()) {
                    handleSetPenAttributes();
                    return;
                } else {
                    this.serviceBlockPacket.skipBits(16);
                    return;
                }
            case COMMAND_SPC /*145*/:
                if (this.currentCueBuilder.isDefined()) {
                    handleSetPenColor();
                    return;
                } else {
                    this.serviceBlockPacket.skipBits(24);
                    return;
                }
            case COMMAND_SPL /*146*/:
                if (this.currentCueBuilder.isDefined()) {
                    handleSetPenLocation();
                    return;
                } else {
                    this.serviceBlockPacket.skipBits(16);
                    return;
                }
            case COMMAND_SWA /*151*/:
                if (this.currentCueBuilder.isDefined()) {
                    handleSetWindowAttributes();
                    return;
                } else {
                    this.serviceBlockPacket.skipBits(32);
                    return;
                }
            case COMMAND_DF0 /*152*/:
            case COMMAND_DF1 /*153*/:
            case COMMAND_DF2 /*154*/:
            case COMMAND_DF3 /*155*/:
            case COMMAND_DS4 /*156*/:
            case COMMAND_DF5 /*157*/:
            case COMMAND_DF6 /*158*/:
            case 159:
                window = command - 152;
                handleDefineWindow(window);
                if (this.currentWindow != window) {
                    this.currentWindow = window;
                    this.currentCueBuilder = this.cueBuilders[window];
                    return;
                }
                return;
            default:
                Log.w(TAG, "Invalid C1 command: " + command);
                return;
        }
    }

    private void handleC2Command(int command) {
        if (command > 7) {
            if (command <= 15) {
                this.serviceBlockPacket.skipBits(8);
            } else if (command <= 23) {
                this.serviceBlockPacket.skipBits(16);
            } else if (command <= 31) {
                this.serviceBlockPacket.skipBits(24);
            }
        }
    }

    private void handleC3Command(int command) {
        if (command <= 135) {
            this.serviceBlockPacket.skipBits(32);
        } else if (command <= COMMAND_RST) {
            this.serviceBlockPacket.skipBits(40);
        } else if (command <= 159) {
            this.serviceBlockPacket.skipBits(2);
            this.serviceBlockPacket.skipBits(this.serviceBlockPacket.readBits(6) * 8);
        }
    }

    private void handleG0Character(int characterCode) {
        if (characterCode == 127) {
            this.currentCueBuilder.append('♫');
        } else {
            this.currentCueBuilder.append((char) (characterCode & 255));
        }
    }

    private void handleG1Character(int characterCode) {
        this.currentCueBuilder.append((char) (characterCode & 255));
    }

    private void handleG2Character(int characterCode) {
        switch (characterCode) {
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
            case 126:
                this.currentCueBuilder.append('┘');
                return;
            case 127:
                this.currentCueBuilder.append('┌');
                return;
            default:
                Log.w(TAG, "Invalid G2 character: " + characterCode);
                return;
        }
    }

    private void handleG3Character(int characterCode) {
        if (characterCode == 160) {
            this.currentCueBuilder.append('㏄');
            return;
        }
        Log.w(TAG, "Invalid G3 character: " + characterCode);
        this.currentCueBuilder.append('_');
    }

    private void handleSetPenAttributes() {
        this.currentCueBuilder.setPenAttributes(this.serviceBlockPacket.readBits(4), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBit(), this.serviceBlockPacket.readBit(), this.serviceBlockPacket.readBits(3), this.serviceBlockPacket.readBits(3));
    }

    private void handleSetPenColor() {
        int foregroundColor = CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        int backgroundColor = CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        this.serviceBlockPacket.skipBits(2);
        this.currentCueBuilder.setPenColor(foregroundColor, backgroundColor, CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2)));
    }

    private void handleSetPenLocation() {
        this.serviceBlockPacket.skipBits(4);
        int row = this.serviceBlockPacket.readBits(4);
        this.serviceBlockPacket.skipBits(2);
        this.currentCueBuilder.setPenLocation(row, this.serviceBlockPacket.readBits(6));
    }

    private void handleSetWindowAttributes() {
        int fillColor = CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        int borderType = this.serviceBlockPacket.readBits(2);
        int borderColor = CueBuilder.getArgbColorFromCeaColor(this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2), this.serviceBlockPacket.readBits(2));
        if (this.serviceBlockPacket.readBit()) {
            borderType |= 4;
        }
        boolean wordWrapToggle = this.serviceBlockPacket.readBit();
        int printDirection = this.serviceBlockPacket.readBits(2);
        int scrollDirection = this.serviceBlockPacket.readBits(2);
        int justification = this.serviceBlockPacket.readBits(2);
        this.serviceBlockPacket.skipBits(8);
        this.currentCueBuilder.setWindowAttributes(fillColor, borderColor, wordWrapToggle, borderType, printDirection, scrollDirection, justification);
    }

    private void handleDefineWindow(int window) {
        CueBuilder cueBuilder = this.cueBuilders[window];
        this.serviceBlockPacket.skipBits(2);
        boolean visible = this.serviceBlockPacket.readBit();
        boolean rowLock = this.serviceBlockPacket.readBit();
        boolean columnLock = this.serviceBlockPacket.readBit();
        int priority = this.serviceBlockPacket.readBits(3);
        boolean relativePositioning = this.serviceBlockPacket.readBit();
        int verticalAnchor = this.serviceBlockPacket.readBits(7);
        int horizontalAnchor = this.serviceBlockPacket.readBits(8);
        int anchorId = this.serviceBlockPacket.readBits(4);
        int rowCount = this.serviceBlockPacket.readBits(4);
        this.serviceBlockPacket.skipBits(2);
        int columnCount = this.serviceBlockPacket.readBits(6);
        this.serviceBlockPacket.skipBits(2);
        cueBuilder.defineWindow(visible, rowLock, columnLock, priority, relativePositioning, verticalAnchor, horizontalAnchor, rowCount, columnCount, anchorId, this.serviceBlockPacket.readBits(3), this.serviceBlockPacket.readBits(3));
    }

    private List<Cue> getDisplayCues() {
        List<Cea708Cue> displayCues = new ArrayList();
        int i = 0;
        while (i < 8) {
            if (!this.cueBuilders[i].isEmpty() && this.cueBuilders[i].isVisible()) {
                displayCues.add(this.cueBuilders[i].build());
            }
            i++;
        }
        Collections.sort(displayCues);
        return Collections.unmodifiableList(displayCues);
    }

    private void resetCueBuilders() {
        for (int i = 0; i < 8; i++) {
            this.cueBuilders[i].reset();
        }
    }
}
