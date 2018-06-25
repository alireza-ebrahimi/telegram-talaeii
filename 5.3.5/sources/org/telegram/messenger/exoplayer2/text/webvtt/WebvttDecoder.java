package org.telegram.messenger.exoplayer2.text.webvtt;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.exoplayer2.text.SimpleSubtitleDecoder;
import org.telegram.messenger.exoplayer2.text.SubtitleDecoderException;
import org.telegram.messenger.exoplayer2.text.webvtt.WebvttCue.Builder;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class WebvttDecoder extends SimpleSubtitleDecoder {
    private static final String COMMENT_START = "NOTE";
    private static final int EVENT_COMMENT = 1;
    private static final int EVENT_CUE = 3;
    private static final int EVENT_END_OF_FILE = 0;
    private static final int EVENT_NONE = -1;
    private static final int EVENT_STYLE_BLOCK = 2;
    private static final String STYLE_START = "STYLE";
    private final CssParser cssParser = new CssParser();
    private final WebvttCueParser cueParser = new WebvttCueParser();
    private final List<WebvttCssStyle> definedStyles = new ArrayList();
    private final ParsableByteArray parsableWebvttData = new ParsableByteArray();
    private final Builder webvttCueBuilder = new Builder();

    public WebvttDecoder() {
        super("WebvttDecoder");
    }

    protected WebvttSubtitle decode(byte[] bytes, int length, boolean reset) throws SubtitleDecoderException {
        this.parsableWebvttData.reset(bytes, length);
        this.webvttCueBuilder.reset();
        this.definedStyles.clear();
        WebvttParserUtil.validateWebvttHeaderLine(this.parsableWebvttData);
        do {
        } while (!TextUtils.isEmpty(this.parsableWebvttData.readLine()));
        ArrayList<WebvttCue> subtitles = new ArrayList();
        while (true) {
            int event = getNextEvent(this.parsableWebvttData);
            if (event == 0) {
                return new WebvttSubtitle(subtitles);
            }
            if (event == 1) {
                skipComment(this.parsableWebvttData);
            } else if (event == 2) {
                if (subtitles.isEmpty()) {
                    this.parsableWebvttData.readLine();
                    WebvttCssStyle styleBlock = this.cssParser.parseBlock(this.parsableWebvttData);
                    if (styleBlock != null) {
                        this.definedStyles.add(styleBlock);
                    }
                } else {
                    throw new SubtitleDecoderException("A style block was found after the first cue.");
                }
            } else if (event == 3 && this.cueParser.parseCue(this.parsableWebvttData, this.webvttCueBuilder, this.definedStyles)) {
                subtitles.add(this.webvttCueBuilder.build());
                this.webvttCueBuilder.reset();
            }
        }
    }

    private static int getNextEvent(ParsableByteArray parsableWebvttData) {
        int foundEvent = -1;
        int currentInputPosition = 0;
        while (foundEvent == -1) {
            currentInputPosition = parsableWebvttData.getPosition();
            String line = parsableWebvttData.readLine();
            if (line == null) {
                foundEvent = 0;
            } else if (STYLE_START.equals(line)) {
                foundEvent = 2;
            } else if (COMMENT_START.startsWith(line)) {
                foundEvent = 1;
            } else {
                foundEvent = 3;
            }
        }
        parsableWebvttData.setPosition(currentInputPosition);
        return foundEvent;
    }

    private static void skipComment(ParsableByteArray parsableWebvttData) {
        do {
        } while (!TextUtils.isEmpty(parsableWebvttData.readLine()));
    }
}
