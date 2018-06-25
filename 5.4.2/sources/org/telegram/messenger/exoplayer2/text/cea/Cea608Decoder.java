package org.telegram.messenger.exoplayer2.text.cea;

import android.text.Layout.Alignment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.exoplayer2.text.Cue;
import org.telegram.messenger.exoplayer2.text.Subtitle;
import org.telegram.messenger.exoplayer2.text.SubtitleInputBuffer;
import org.telegram.messenger.exoplayer2.text.SubtitleOutputBuffer;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;

public final class Cea608Decoder extends CeaDecoder {
    private static final int[] BASIC_CHARACTER_SET = new int[]{32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 225, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 233, 93, 237, 243, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 231, 247, 209, 241, 9632};
    private static final int CC_FIELD_FLAG = 1;
    private static final byte CC_IMPLICIT_DATA_HEADER = (byte) -4;
    private static final int CC_MODE_PAINT_ON = 3;
    private static final int CC_MODE_POP_ON = 2;
    private static final int CC_MODE_ROLL_UP = 1;
    private static final int CC_MODE_UNKNOWN = 0;
    private static final int CC_TYPE_FLAG = 2;
    private static final int CC_VALID_608_ID = 4;
    private static final int CC_VALID_FLAG = 4;
    private static final int[] COLORS = new int[]{-1, -16711936, -16776961, -16711681, -65536, -256, -65281};
    private static final int[] COLUMN_INDICES = new int[]{0, 4, 8, 12, 16, 20, 24, 28};
    private static final byte CTRL_BACKSPACE = (byte) 33;
    private static final byte CTRL_CARRIAGE_RETURN = (byte) 45;
    private static final byte CTRL_DELETE_TO_END_OF_ROW = (byte) 36;
    private static final byte CTRL_END_OF_CAPTION = (byte) 47;
    private static final byte CTRL_ERASE_DISPLAYED_MEMORY = (byte) 44;
    private static final byte CTRL_ERASE_NON_DISPLAYED_MEMORY = (byte) 46;
    private static final byte CTRL_RESUME_CAPTION_LOADING = (byte) 32;
    private static final byte CTRL_RESUME_DIRECT_CAPTIONING = (byte) 41;
    private static final byte CTRL_ROLL_UP_CAPTIONS_2_ROWS = (byte) 37;
    private static final byte CTRL_ROLL_UP_CAPTIONS_3_ROWS = (byte) 38;
    private static final byte CTRL_ROLL_UP_CAPTIONS_4_ROWS = (byte) 39;
    private static final int DEFAULT_CAPTIONS_ROW_COUNT = 4;
    private static final int NTSC_CC_FIELD_1 = 0;
    private static final int NTSC_CC_FIELD_2 = 1;
    private static final int[] ROW_INDICES = new int[]{11, 1, 3, 12, 14, 5, 7, 9};
    private static final int[] SPECIAL_CHARACTER_SET = new int[]{174, 176, PsExtractor.PRIVATE_STREAM_1, 191, 8482, 162, 163, 9834, 224, 32, 232, 226, 234, 238, 244, 251};
    private static final int[] SPECIAL_ES_FR_CHARACTER_SET = new int[]{193, 201, 211, 218, 220, 252, 8216, 161, 42, 39, 8212, 169, 8480, 8226, 8220, 8221, PsExtractor.AUDIO_STREAM, 194, 199, Callback.DEFAULT_DRAG_ANIMATION_DURATION, 202, 203, 235, 206, 207, 239, 212, 217, 249, 219, 171, 187};
    private static final int[] SPECIAL_PT_DE_CHARACTER_SET = new int[]{195, 227, 205, 204, 236, 210, 242, 213, 245, 123, 125, 92, 94, 95, 124, 126, 196, 228, 214, 246, 223, 165, 164, 9474, 197, 229, 216, 248, 9484, 9488, 9492, 9496};
    private int captionMode;
    private int captionRowCount;
    private final ParsableByteArray ccData = new ParsableByteArray();
    private final LinkedList<CueBuilder> cueBuilders = new LinkedList();
    private List<Cue> cues;
    private CueBuilder currentCueBuilder = new CueBuilder(0, 4);
    private List<Cue> lastCues;
    private final int packetLength;
    private byte repeatableControlCc1;
    private byte repeatableControlCc2;
    private boolean repeatableControlSet;
    private final int selectedField;

    private static class CueBuilder {
        private static final int BASE_ROW = 15;
        private static final int POSITION_UNSET = -1;
        private static final int SCREEN_CHARWIDTH = 32;
        private int captionMode;
        private int captionRowCount;
        private final SpannableStringBuilder captionStringBuilder = new SpannableStringBuilder();
        private int indent;
        private final List<CueStyle> midrowStyles = new ArrayList();
        private final List<CharacterStyle> preambleStyles = new ArrayList();
        private final List<SpannableString> rolledUpCaptions = new LinkedList();
        private int row;
        private int tabOffset;
        private int underlineStartPosition;

        private static class CueStyle {
            public final int nextStyleIncrement;
            public final int start;
            public final CharacterStyle style;

            public CueStyle(CharacterStyle characterStyle, int i, int i2) {
                this.style = characterStyle;
                this.start = i;
                this.nextStyleIncrement = i2;
            }
        }

        public CueBuilder(int i, int i2) {
            reset(i, i2);
        }

        public void append(char c) {
            this.captionStringBuilder.append(c);
        }

        public void backspace() {
            int length = this.captionStringBuilder.length();
            if (length > 0) {
                this.captionStringBuilder.delete(length - 1, length);
            }
        }

        public Cue build() {
            int i;
            int i2 = 2;
            CharSequence spannableStringBuilder = new SpannableStringBuilder();
            for (i = 0; i < this.rolledUpCaptions.size(); i++) {
                spannableStringBuilder.append((CharSequence) this.rolledUpCaptions.get(i));
                spannableStringBuilder.append('\n');
            }
            spannableStringBuilder.append(buildSpannableString());
            if (spannableStringBuilder.length() == 0) {
                return null;
            }
            float f;
            int i3;
            int i4;
            int i5 = this.indent + this.tabOffset;
            i = (32 - i5) - spannableStringBuilder.length();
            int i6 = i5 - i;
            if (this.captionMode == 2 && Math.abs(i6) < 3) {
                f = 0.5f;
                i3 = 1;
            } else if (this.captionMode != 2 || i6 <= 0) {
                f = ((((float) i5) / 32.0f) * 0.8f) + 0.1f;
                i3 = 0;
            } else {
                f = ((((float) (32 - i)) / 32.0f) * 0.8f) + 0.1f;
                i3 = 2;
            }
            if (this.captionMode == 1 || this.row > 7) {
                i4 = (this.row - 15) - 2;
            } else {
                i2 = 0;
                i4 = this.row;
            }
            return new Cue(spannableStringBuilder, Alignment.ALIGN_NORMAL, (float) i4, 1, i2, f, i3, Float.MIN_VALUE);
        }

        public SpannableString buildSpannableString() {
            int length = this.captionStringBuilder.length();
            for (int i = 0; i < this.preambleStyles.size(); i++) {
                this.captionStringBuilder.setSpan(this.preambleStyles.get(i), 0, length, 33);
            }
            int i2 = 0;
            while (i2 < this.midrowStyles.size()) {
                CueStyle cueStyle = (CueStyle) this.midrowStyles.get(i2);
                this.captionStringBuilder.setSpan(cueStyle.style, cueStyle.start, i2 < this.midrowStyles.size() - cueStyle.nextStyleIncrement ? ((CueStyle) this.midrowStyles.get(cueStyle.nextStyleIncrement + i2)).start : length, 33);
                i2++;
            }
            if (this.underlineStartPosition != -1) {
                this.captionStringBuilder.setSpan(new UnderlineSpan(), this.underlineStartPosition, length, 33);
            }
            return new SpannableString(this.captionStringBuilder);
        }

        public int getRow() {
            return this.row;
        }

        public boolean isEmpty() {
            return this.preambleStyles.isEmpty() && this.midrowStyles.isEmpty() && this.rolledUpCaptions.isEmpty() && this.captionStringBuilder.length() == 0;
        }

        public void reset(int i, int i2) {
            this.preambleStyles.clear();
            this.midrowStyles.clear();
            this.rolledUpCaptions.clear();
            this.captionStringBuilder.clear();
            this.row = 15;
            this.indent = 0;
            this.tabOffset = 0;
            this.captionMode = i;
            this.captionRowCount = i2;
            this.underlineStartPosition = -1;
        }

        public void rollUp() {
            this.rolledUpCaptions.add(buildSpannableString());
            this.captionStringBuilder.clear();
            this.preambleStyles.clear();
            this.midrowStyles.clear();
            this.underlineStartPosition = -1;
            int min = Math.min(this.captionRowCount, this.row);
            while (this.rolledUpCaptions.size() >= min) {
                this.rolledUpCaptions.remove(0);
            }
        }

        public void setIndent(int i) {
            this.indent = i;
        }

        public void setMidrowStyle(CharacterStyle characterStyle, int i) {
            this.midrowStyles.add(new CueStyle(characterStyle, this.captionStringBuilder.length(), i));
        }

        public void setPreambleStyle(CharacterStyle characterStyle) {
            this.preambleStyles.add(characterStyle);
        }

        public void setRow(int i) {
            this.row = i;
        }

        public void setTab(int i) {
            this.tabOffset = i;
        }

        public void setUnderline(boolean z) {
            if (z) {
                this.underlineStartPosition = this.captionStringBuilder.length();
            } else if (this.underlineStartPosition != -1) {
                this.captionStringBuilder.setSpan(new UnderlineSpan(), this.underlineStartPosition, this.captionStringBuilder.length(), 33);
                this.underlineStartPosition = -1;
            }
        }

        public String toString() {
            return this.captionStringBuilder.toString();
        }
    }

    public Cea608Decoder(String str, int i) {
        this.packetLength = MimeTypes.APPLICATION_MP4CEA608.equals(str) ? 2 : 3;
        switch (i) {
            case 3:
            case 4:
                this.selectedField = 2;
                break;
            default:
                this.selectedField = 1;
                break;
        }
        setCaptionMode(0);
        resetCueBuilders();
    }

    private static char getChar(byte b) {
        return (char) BASIC_CHARACTER_SET[(b & 127) - 32];
    }

    private List<Cue> getDisplayCues() {
        List<Cue> arrayList = new ArrayList();
        for (int i = 0; i < this.cueBuilders.size(); i++) {
            Cue build = ((CueBuilder) this.cueBuilders.get(i)).build();
            if (build != null) {
                arrayList.add(build);
            }
        }
        return arrayList;
    }

    private static char getExtendedEsFrChar(byte b) {
        return (char) SPECIAL_ES_FR_CHARACTER_SET[b & 31];
    }

    private static char getExtendedPtDeChar(byte b) {
        return (char) SPECIAL_PT_DE_CHARACTER_SET[b & 31];
    }

    private static char getSpecialChar(byte b) {
        return (char) SPECIAL_CHARACTER_SET[b & 15];
    }

    private boolean handleCtrl(byte b, byte b2) {
        boolean isRepeatable = isRepeatable(b);
        if (isRepeatable) {
            if (this.repeatableControlSet && this.repeatableControlCc1 == b && this.repeatableControlCc2 == b2) {
                this.repeatableControlSet = false;
                return true;
            }
            this.repeatableControlSet = true;
            this.repeatableControlCc1 = b;
            this.repeatableControlCc2 = b2;
        }
        if (isMidrowCtrlCode(b, b2)) {
            handleMidrowCtrl(b2);
        } else if (isPreambleAddressCode(b, b2)) {
            handlePreambleAddressCode(b, b2);
        } else if (isTabCtrlCode(b, b2)) {
            this.currentCueBuilder.setTab(b2 - 32);
        } else if (isMiscCode(b, b2)) {
            handleMiscCode(b2);
        }
        return isRepeatable;
    }

    private void handleMidrowCtrl(byte b) {
        this.currentCueBuilder.setUnderline((b & 1) == 1);
        int i = (b >> 1) & 15;
        if (i == 7) {
            this.currentCueBuilder.setMidrowStyle(new StyleSpan(2), 2);
            this.currentCueBuilder.setMidrowStyle(new ForegroundColorSpan(-1), 1);
            return;
        }
        this.currentCueBuilder.setMidrowStyle(new ForegroundColorSpan(COLORS[i]), 1);
    }

    private void handleMiscCode(byte b) {
        switch (b) {
            case (byte) 32:
                setCaptionMode(2);
                return;
            case (byte) 37:
                this.captionRowCount = 2;
                setCaptionMode(1);
                return;
            case (byte) 38:
                this.captionRowCount = 3;
                setCaptionMode(1);
                return;
            case (byte) 39:
                this.captionRowCount = 4;
                setCaptionMode(1);
                return;
            case (byte) 41:
                setCaptionMode(3);
                return;
            default:
                if (this.captionMode != 0) {
                    switch (b) {
                        case (byte) 33:
                            this.currentCueBuilder.backspace();
                            return;
                        case (byte) 44:
                            this.cues = null;
                            if (this.captionMode == 1 || this.captionMode == 3) {
                                resetCueBuilders();
                                return;
                            }
                            return;
                        case (byte) 45:
                            if (this.captionMode == 1 && !this.currentCueBuilder.isEmpty()) {
                                this.currentCueBuilder.rollUp();
                                return;
                            }
                            return;
                        case (byte) 46:
                            resetCueBuilders();
                            return;
                        case (byte) 47:
                            this.cues = getDisplayCues();
                            resetCueBuilders();
                            return;
                        default:
                            return;
                    }
                }
                return;
        }
    }

    private void handlePreambleAddressCode(byte b, byte b2) {
        int i = ROW_INDICES[b & 7];
        if (((b2 & 32) != 0 ? 1 : null) != null) {
            i++;
        }
        if (i != this.currentCueBuilder.getRow()) {
            if (!(this.captionMode == 1 || this.currentCueBuilder.isEmpty())) {
                this.currentCueBuilder = new CueBuilder(this.captionMode, this.captionRowCount);
                this.cueBuilders.add(this.currentCueBuilder);
            }
            this.currentCueBuilder.setRow(i);
        }
        if ((b2 & 1) == 1) {
            this.currentCueBuilder.setPreambleStyle(new UnderlineSpan());
        }
        i = (b2 >> 1) & 15;
        if (i > 7) {
            this.currentCueBuilder.setIndent(COLUMN_INDICES[i & 7]);
        } else if (i == 7) {
            this.currentCueBuilder.setPreambleStyle(new StyleSpan(2));
            this.currentCueBuilder.setPreambleStyle(new ForegroundColorSpan(-1));
        } else {
            this.currentCueBuilder.setPreambleStyle(new ForegroundColorSpan(COLORS[i]));
        }
    }

    private static boolean isMidrowCtrlCode(byte b, byte b2) {
        return (b & 247) == 17 && (b2 & PsExtractor.VIDEO_STREAM_MASK) == 32;
    }

    private static boolean isMiscCode(byte b, byte b2) {
        return (b & 247) == 20 && (b2 & PsExtractor.VIDEO_STREAM_MASK) == 32;
    }

    private static boolean isPreambleAddressCode(byte b, byte b2) {
        return (b & PsExtractor.VIDEO_STREAM_MASK) == 16 && (b2 & PsExtractor.AUDIO_STREAM) == 64;
    }

    private static boolean isRepeatable(byte b) {
        return (b & PsExtractor.VIDEO_STREAM_MASK) == 16;
    }

    private static boolean isTabCtrlCode(byte b, byte b2) {
        return (b & 247) == 23 && b2 >= CTRL_BACKSPACE && b2 <= (byte) 35;
    }

    private void resetCueBuilders() {
        this.currentCueBuilder.reset(this.captionMode, this.captionRowCount);
        this.cueBuilders.clear();
        this.cueBuilders.add(this.currentCueBuilder);
    }

    private void setCaptionMode(int i) {
        if (this.captionMode != i) {
            int i2 = this.captionMode;
            this.captionMode = i;
            resetCueBuilders();
            if (i2 == 3 || i == 1 || i == 0) {
                this.cues = null;
            }
        }
    }

    protected Subtitle createSubtitle() {
        this.lastCues = this.cues;
        return new CeaSubtitle(this.cues);
    }

    protected void decode(SubtitleInputBuffer subtitleInputBuffer) {
        this.ccData.reset(subtitleInputBuffer.data.array(), subtitleInputBuffer.data.limit());
        boolean z = false;
        boolean z2 = false;
        while (this.ccData.bytesLeft() >= this.packetLength) {
            int readUnsignedByte = this.packetLength == 2 ? -4 : (byte) this.ccData.readUnsignedByte();
            byte readUnsignedByte2 = (byte) (this.ccData.readUnsignedByte() & 127);
            byte readUnsignedByte3 = (byte) (this.ccData.readUnsignedByte() & 127);
            if ((readUnsignedByte & 6) == 4 && ((this.selectedField != 1 || (readUnsignedByte & 1) == 0) && ((this.selectedField != 2 || (readUnsignedByte & 1) == 1) && !(readUnsignedByte2 == (byte) 0 && readUnsignedByte3 == (byte) 0)))) {
                if ((readUnsignedByte2 & 247) == 17 && (readUnsignedByte3 & PsExtractor.VIDEO_STREAM_MASK) == 48) {
                    this.currentCueBuilder.append(getSpecialChar(readUnsignedByte3));
                    z2 = true;
                } else if ((readUnsignedByte2 & 246) == 18 && (readUnsignedByte3 & 224) == 32) {
                    this.currentCueBuilder.backspace();
                    if ((readUnsignedByte2 & 1) == 0) {
                        this.currentCueBuilder.append(getExtendedEsFrChar(readUnsignedByte3));
                        z2 = true;
                    } else {
                        this.currentCueBuilder.append(getExtendedPtDeChar(readUnsignedByte3));
                        z2 = true;
                    }
                } else if ((readUnsignedByte2 & 224) == 0) {
                    z = handleCtrl(readUnsignedByte2, readUnsignedByte3);
                    z2 = true;
                } else {
                    this.currentCueBuilder.append(getChar(readUnsignedByte2));
                    if ((readUnsignedByte3 & 224) != 0) {
                        this.currentCueBuilder.append(getChar(readUnsignedByte3));
                    }
                    z2 = true;
                }
            }
        }
        if (z2) {
            if (!z) {
                this.repeatableControlSet = false;
            }
            if (this.captionMode == 1 || this.captionMode == 3) {
                this.cues = getDisplayCues();
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
        setCaptionMode(0);
        resetCueBuilders();
        this.captionRowCount = 4;
        this.repeatableControlSet = false;
        this.repeatableControlCc1 = (byte) 0;
        this.repeatableControlCc2 = (byte) 0;
    }

    public String getName() {
        return "Cea608Decoder";
    }

    protected boolean isNewSubtitleDataAvailable() {
        return this.cues != this.lastCues;
    }

    public /* bridge */ /* synthetic */ void queueInputBuffer(SubtitleInputBuffer subtitleInputBuffer) {
        super.queueInputBuffer(subtitleInputBuffer);
    }

    public void release() {
    }

    public /* bridge */ /* synthetic */ void setPositionUs(long j) {
        super.setPositionUs(j);
    }
}
