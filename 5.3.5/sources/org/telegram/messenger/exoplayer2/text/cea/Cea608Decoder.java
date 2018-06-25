package org.telegram.messenger.exoplayer2.text.cea;

import android.support.v4.internal.view.SupportMenu;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.InputDeviceCompat;
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
import org.telegram.customization.fetch.FetchConst;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.exoplayer2.text.Cue;
import org.telegram.messenger.exoplayer2.text.Subtitle;
import org.telegram.messenger.exoplayer2.text.SubtitleDecoderException;
import org.telegram.messenger.exoplayer2.text.SubtitleInputBuffer;
import org.telegram.messenger.exoplayer2.text.SubtitleOutputBuffer;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class Cea608Decoder extends CeaDecoder {
    private static final int[] BASIC_CHARACTER_SET = new int[]{32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 225, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 233, 93, 237, 243, 250, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 231, 247, 209, 241, 9632};
    private static final int CC_FIELD_FLAG = 1;
    private static final byte CC_IMPLICIT_DATA_HEADER = (byte) -4;
    private static final int CC_MODE_PAINT_ON = 3;
    private static final int CC_MODE_POP_ON = 2;
    private static final int CC_MODE_ROLL_UP = 1;
    private static final int CC_MODE_UNKNOWN = 0;
    private static final int CC_TYPE_FLAG = 2;
    private static final int CC_VALID_608_ID = 4;
    private static final int CC_VALID_FLAG = 4;
    private static final int[] COLORS = new int[]{-1, -16711936, -16776961, -16711681, SupportMenu.CATEGORY_MASK, InputDeviceCompat.SOURCE_ANY, -65281};
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
    private static final int[] SPECIAL_ES_FR_CHARACTER_SET = new int[]{193, FetchConst.NETWORK_WIFI, 211, 218, 220, 252, 8216, 161, 42, 39, 8212, 169, 8480, 8226, 8220, 8221, PsExtractor.AUDIO_STREAM, 194, 199, 200, 202, 203, 235, 206, 207, 239, 212, 217, 249, 219, 171, 187};
    private static final int[] SPECIAL_PT_DE_CHARACTER_SET = new int[]{195, 227, 205, 204, 236, 210, 242, 213, 245, 123, 125, 92, 94, 95, 124, TransportMediator.KEYCODE_MEDIA_PLAY, 196, 228, 214, 246, 223, 165, 164, 9474, 197, 229, 216, 248, 9484, 9488, 9492, 9496};
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

            public CueStyle(CharacterStyle style, int start, int nextStyleIncrement) {
                this.style = style;
                this.start = start;
                this.nextStyleIncrement = nextStyleIncrement;
            }
        }

        public CueBuilder(int captionMode, int captionRowCount) {
            reset(captionMode, captionRowCount);
        }

        public void reset(int captionMode, int captionRowCount) {
            this.preambleStyles.clear();
            this.midrowStyles.clear();
            this.rolledUpCaptions.clear();
            this.captionStringBuilder.clear();
            this.row = 15;
            this.indent = 0;
            this.tabOffset = 0;
            this.captionMode = captionMode;
            this.captionRowCount = captionRowCount;
            this.underlineStartPosition = -1;
        }

        public boolean isEmpty() {
            return this.preambleStyles.isEmpty() && this.midrowStyles.isEmpty() && this.rolledUpCaptions.isEmpty() && this.captionStringBuilder.length() == 0;
        }

        public void backspace() {
            int length = this.captionStringBuilder.length();
            if (length > 0) {
                this.captionStringBuilder.delete(length - 1, length);
            }
        }

        public int getRow() {
            return this.row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public void rollUp() {
            this.rolledUpCaptions.add(buildSpannableString());
            this.captionStringBuilder.clear();
            this.preambleStyles.clear();
            this.midrowStyles.clear();
            this.underlineStartPosition = -1;
            int numRows = Math.min(this.captionRowCount, this.row);
            while (this.rolledUpCaptions.size() >= numRows) {
                this.rolledUpCaptions.remove(0);
            }
        }

        public void setIndent(int indent) {
            this.indent = indent;
        }

        public void setTab(int tabs) {
            this.tabOffset = tabs;
        }

        public void setPreambleStyle(CharacterStyle style) {
            this.preambleStyles.add(style);
        }

        public void setMidrowStyle(CharacterStyle style, int nextStyleIncrement) {
            this.midrowStyles.add(new CueStyle(style, this.captionStringBuilder.length(), nextStyleIncrement));
        }

        public void setUnderline(boolean enabled) {
            if (enabled) {
                this.underlineStartPosition = this.captionStringBuilder.length();
            } else if (this.underlineStartPosition != -1) {
                this.captionStringBuilder.setSpan(new UnderlineSpan(), this.underlineStartPosition, this.captionStringBuilder.length(), 33);
                this.underlineStartPosition = -1;
            }
        }

        public void append(char text) {
            this.captionStringBuilder.append(text);
        }

        public SpannableString buildSpannableString() {
            int i;
            int length = this.captionStringBuilder.length();
            for (i = 0; i < this.preambleStyles.size(); i++) {
                this.captionStringBuilder.setSpan(this.preambleStyles.get(i), 0, length, 33);
            }
            for (i = 0; i < this.midrowStyles.size(); i++) {
                int end;
                CueStyle cueStyle = (CueStyle) this.midrowStyles.get(i);
                if (i < this.midrowStyles.size() - cueStyle.nextStyleIncrement) {
                    end = ((CueStyle) this.midrowStyles.get(cueStyle.nextStyleIncrement + i)).start;
                } else {
                    end = length;
                }
                this.captionStringBuilder.setSpan(cueStyle.style, cueStyle.start, end, 33);
            }
            if (this.underlineStartPosition != -1) {
                this.captionStringBuilder.setSpan(new UnderlineSpan(), this.underlineStartPosition, length, 33);
            }
            return new SpannableString(this.captionStringBuilder);
        }

        public Cue build() {
            SpannableStringBuilder cueString = new SpannableStringBuilder();
            for (int i = 0; i < this.rolledUpCaptions.size(); i++) {
                cueString.append((CharSequence) this.rolledUpCaptions.get(i));
                cueString.append('\n');
            }
            cueString.append(buildSpannableString());
            if (cueString.length() == 0) {
                return null;
            }
            float position;
            int positionAnchor;
            int lineAnchor;
            int line;
            int startPadding = this.indent + this.tabOffset;
            int endPadding = (32 - startPadding) - cueString.length();
            int startEndPaddingDelta = startPadding - endPadding;
            if (this.captionMode == 2 && Math.abs(startEndPaddingDelta) < 3) {
                position = 0.5f;
                positionAnchor = 1;
            } else if (this.captionMode != 2 || startEndPaddingDelta <= 0) {
                position = (0.8f * (((float) startPadding) / 32.0f)) + 0.1f;
                positionAnchor = 0;
            } else {
                position = (0.8f * (((float) (32 - endPadding)) / 32.0f)) + 0.1f;
                positionAnchor = 2;
            }
            if (this.captionMode == 1 || this.row > 7) {
                lineAnchor = 2;
                line = (this.row - 15) - 2;
            } else {
                lineAnchor = 0;
                line = this.row;
            }
            return new Cue(cueString, Alignment.ALIGN_NORMAL, (float) line, 1, lineAnchor, position, positionAnchor, Float.MIN_VALUE);
        }

        public String toString() {
            return this.captionStringBuilder.toString();
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

    public /* bridge */ /* synthetic */ void setPositionUs(long j) {
        super.setPositionUs(j);
    }

    public Cea608Decoder(String mimeType, int accessibilityChannel) {
        this.packetLength = MimeTypes.APPLICATION_MP4CEA608.equals(mimeType) ? 2 : 3;
        switch (accessibilityChannel) {
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

    public String getName() {
        return "Cea608Decoder";
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

    public void release() {
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
        boolean captionDataProcessed = false;
        boolean isRepeatableControl = false;
        while (this.ccData.bytesLeft() >= this.packetLength) {
            byte ccDataHeader;
            if (this.packetLength == 2) {
                ccDataHeader = CC_IMPLICIT_DATA_HEADER;
            } else {
                ccDataHeader = (byte) this.ccData.readUnsignedByte();
            }
            byte ccData1 = (byte) (this.ccData.readUnsignedByte() & 127);
            byte ccData2 = (byte) (this.ccData.readUnsignedByte() & 127);
            if ((ccDataHeader & 6) == 4 && ((this.selectedField != 1 || (ccDataHeader & 1) == 0) && ((this.selectedField != 2 || (ccDataHeader & 1) == 1) && !(ccData1 == (byte) 0 && ccData2 == (byte) 0)))) {
                captionDataProcessed = true;
                if ((ccData1 & 247) == 17 && (ccData2 & PsExtractor.VIDEO_STREAM_MASK) == 48) {
                    this.currentCueBuilder.append(getSpecialChar(ccData2));
                } else if ((ccData1 & 246) == 18 && (ccData2 & 224) == 32) {
                    this.currentCueBuilder.backspace();
                    if ((ccData1 & 1) == 0) {
                        this.currentCueBuilder.append(getExtendedEsFrChar(ccData2));
                    } else {
                        this.currentCueBuilder.append(getExtendedPtDeChar(ccData2));
                    }
                } else if ((ccData1 & 224) == 0) {
                    isRepeatableControl = handleCtrl(ccData1, ccData2);
                } else {
                    this.currentCueBuilder.append(getChar(ccData1));
                    if ((ccData2 & 224) != 0) {
                        this.currentCueBuilder.append(getChar(ccData2));
                    }
                }
            }
        }
        if (captionDataProcessed) {
            if (!isRepeatableControl) {
                this.repeatableControlSet = false;
            }
            if (this.captionMode == 1 || this.captionMode == 3) {
                this.cues = getDisplayCues();
            }
        }
    }

    private boolean handleCtrl(byte cc1, byte cc2) {
        boolean isRepeatableControl = isRepeatable(cc1);
        if (isRepeatableControl) {
            if (this.repeatableControlSet && this.repeatableControlCc1 == cc1 && this.repeatableControlCc2 == cc2) {
                this.repeatableControlSet = false;
                return true;
            }
            this.repeatableControlSet = true;
            this.repeatableControlCc1 = cc1;
            this.repeatableControlCc2 = cc2;
        }
        if (isMidrowCtrlCode(cc1, cc2)) {
            handleMidrowCtrl(cc2);
            return isRepeatableControl;
        } else if (isPreambleAddressCode(cc1, cc2)) {
            handlePreambleAddressCode(cc1, cc2);
            return isRepeatableControl;
        } else if (isTabCtrlCode(cc1, cc2)) {
            this.currentCueBuilder.setTab(cc2 - 32);
            return isRepeatableControl;
        } else if (!isMiscCode(cc1, cc2)) {
            return isRepeatableControl;
        } else {
            handleMiscCode(cc2);
            return isRepeatableControl;
        }
    }

    private void handleMidrowCtrl(byte cc2) {
        this.currentCueBuilder.setUnderline((cc2 & 1) == 1);
        int attribute = (cc2 >> 1) & 15;
        if (attribute == 7) {
            this.currentCueBuilder.setMidrowStyle(new StyleSpan(2), 2);
            this.currentCueBuilder.setMidrowStyle(new ForegroundColorSpan(-1), 1);
            return;
        }
        this.currentCueBuilder.setMidrowStyle(new ForegroundColorSpan(COLORS[attribute]), 1);
    }

    private void handlePreambleAddressCode(byte cc1, byte cc2) {
        int row = ROW_INDICES[cc1 & 7];
        if ((cc2 & 32) != 0) {
            row++;
        }
        if (row != this.currentCueBuilder.getRow()) {
            if (!(this.captionMode == 1 || this.currentCueBuilder.isEmpty())) {
                this.currentCueBuilder = new CueBuilder(this.captionMode, this.captionRowCount);
                this.cueBuilders.add(this.currentCueBuilder);
            }
            this.currentCueBuilder.setRow(row);
        }
        if ((cc2 & 1) == 1) {
            this.currentCueBuilder.setPreambleStyle(new UnderlineSpan());
        }
        int attribute = (cc2 >> 1) & 15;
        if (attribute > 7) {
            this.currentCueBuilder.setIndent(COLUMN_INDICES[attribute & 7]);
        } else if (attribute == 7) {
            this.currentCueBuilder.setPreambleStyle(new StyleSpan(2));
            this.currentCueBuilder.setPreambleStyle(new ForegroundColorSpan(-1));
        } else {
            this.currentCueBuilder.setPreambleStyle(new ForegroundColorSpan(COLORS[attribute]));
        }
    }

    private void handleMiscCode(byte cc2) {
        switch (cc2) {
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
                    switch (cc2) {
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

    private List<Cue> getDisplayCues() {
        List<Cue> displayCues = new ArrayList();
        for (int i = 0; i < this.cueBuilders.size(); i++) {
            Cue cue = ((CueBuilder) this.cueBuilders.get(i)).build();
            if (cue != null) {
                displayCues.add(cue);
            }
        }
        return displayCues;
    }

    private void setCaptionMode(int captionMode) {
        if (this.captionMode != captionMode) {
            int oldCaptionMode = this.captionMode;
            this.captionMode = captionMode;
            resetCueBuilders();
            if (oldCaptionMode == 3 || captionMode == 1 || captionMode == 0) {
                this.cues = null;
            }
        }
    }

    private void resetCueBuilders() {
        this.currentCueBuilder.reset(this.captionMode, this.captionRowCount);
        this.cueBuilders.clear();
        this.cueBuilders.add(this.currentCueBuilder);
    }

    private static char getChar(byte ccData) {
        return (char) BASIC_CHARACTER_SET[(ccData & 127) - 32];
    }

    private static char getSpecialChar(byte ccData) {
        return (char) SPECIAL_CHARACTER_SET[ccData & 15];
    }

    private static char getExtendedEsFrChar(byte ccData) {
        return (char) SPECIAL_ES_FR_CHARACTER_SET[ccData & 31];
    }

    private static char getExtendedPtDeChar(byte ccData) {
        return (char) SPECIAL_PT_DE_CHARACTER_SET[ccData & 31];
    }

    private static boolean isMidrowCtrlCode(byte cc1, byte cc2) {
        return (cc1 & 247) == 17 && (cc2 & PsExtractor.VIDEO_STREAM_MASK) == 32;
    }

    private static boolean isPreambleAddressCode(byte cc1, byte cc2) {
        return (cc1 & PsExtractor.VIDEO_STREAM_MASK) == 16 && (cc2 & PsExtractor.AUDIO_STREAM) == 64;
    }

    private static boolean isTabCtrlCode(byte cc1, byte cc2) {
        return (cc1 & 247) == 23 && cc2 >= CTRL_BACKSPACE && cc2 <= (byte) 35;
    }

    private static boolean isMiscCode(byte cc1, byte cc2) {
        return (cc1 & 247) == 20 && (cc2 & PsExtractor.VIDEO_STREAM_MASK) == 32;
    }

    private static boolean isRepeatable(byte cc1) {
        return (cc1 & PsExtractor.VIDEO_STREAM_MASK) == 16;
    }
}
