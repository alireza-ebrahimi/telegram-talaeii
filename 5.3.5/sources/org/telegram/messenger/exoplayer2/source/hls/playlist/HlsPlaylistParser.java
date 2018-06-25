package org.telegram.messenger.exoplayer2.source.hls.playlist;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsMasterPlaylist.HlsUrl;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsMediaPlaylist.Segment;
import org.telegram.messenger.exoplayer2.upstream.ParsingLoadable.Parser;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.Util;

public final class HlsPlaylistParser implements Parser<HlsPlaylist> {
    private static final String ATTR_CLOSED_CAPTIONS_NONE = "CLOSED-CAPTIONS=NONE";
    private static final String BOOLEAN_FALSE = "NO";
    private static final String BOOLEAN_TRUE = "YES";
    private static final String METHOD_AES128 = "AES-128";
    private static final String METHOD_NONE = "NONE";
    private static final String PLAYLIST_HEADER = "#EXTM3U";
    private static final Pattern REGEX_ATTR_BYTERANGE = Pattern.compile("BYTERANGE=\"(\\d+(?:@\\d+)?)\\b\"");
    private static final Pattern REGEX_AUTOSELECT = compileBooleanAttrPattern("AUTOSELECT");
    private static final Pattern REGEX_AVERAGE_BANDWIDTH = Pattern.compile("AVERAGE-BANDWIDTH=(\\d+)\\b");
    private static final Pattern REGEX_BANDWIDTH = Pattern.compile("[^-]BANDWIDTH=(\\d+)\\b");
    private static final Pattern REGEX_BYTERANGE = Pattern.compile("#EXT-X-BYTERANGE:(\\d+(?:@\\d+)?)\\b");
    private static final Pattern REGEX_CODECS = Pattern.compile("CODECS=\"(.+?)\"");
    private static final Pattern REGEX_DEFAULT = compileBooleanAttrPattern("DEFAULT");
    private static final Pattern REGEX_FORCED = compileBooleanAttrPattern("FORCED");
    private static final Pattern REGEX_INSTREAM_ID = Pattern.compile("INSTREAM-ID=\"((?:CC|SERVICE)\\d+)\"");
    private static final Pattern REGEX_IV = Pattern.compile("IV=([^,.*]+)");
    private static final Pattern REGEX_LANGUAGE = Pattern.compile("LANGUAGE=\"(.+?)\"");
    private static final Pattern REGEX_MEDIA_DURATION = Pattern.compile("#EXTINF:([\\d\\.]+)\\b");
    private static final Pattern REGEX_MEDIA_SEQUENCE = Pattern.compile("#EXT-X-MEDIA-SEQUENCE:(\\d+)\\b");
    private static final Pattern REGEX_METHOD = Pattern.compile("METHOD=(NONE|AES-128)");
    private static final Pattern REGEX_NAME = Pattern.compile("NAME=\"(.+?)\"");
    private static final Pattern REGEX_PLAYLIST_TYPE = Pattern.compile("#EXT-X-PLAYLIST-TYPE:(.+)\\b");
    private static final Pattern REGEX_RESOLUTION = Pattern.compile("RESOLUTION=(\\d+x\\d+)");
    private static final Pattern REGEX_TARGET_DURATION = Pattern.compile("#EXT-X-TARGETDURATION:(\\d+)\\b");
    private static final Pattern REGEX_TIME_OFFSET = Pattern.compile("TIME-OFFSET=(-?[\\d\\.]+)\\b");
    private static final Pattern REGEX_TYPE = Pattern.compile("TYPE=(AUDIO|VIDEO|SUBTITLES|CLOSED-CAPTIONS)");
    private static final Pattern REGEX_URI = Pattern.compile("URI=\"(.+?)\"");
    private static final Pattern REGEX_VERSION = Pattern.compile("#EXT-X-VERSION:(\\d+)\\b");
    private static final String TAG_BYTERANGE = "#EXT-X-BYTERANGE";
    private static final String TAG_DISCONTINUITY = "#EXT-X-DISCONTINUITY";
    private static final String TAG_DISCONTINUITY_SEQUENCE = "#EXT-X-DISCONTINUITY-SEQUENCE";
    private static final String TAG_ENDLIST = "#EXT-X-ENDLIST";
    private static final String TAG_INDEPENDENT_SEGMENTS = "#EXT-X-INDEPENDENT-SEGMENTS";
    private static final String TAG_INIT_SEGMENT = "#EXT-X-MAP";
    private static final String TAG_KEY = "#EXT-X-KEY";
    private static final String TAG_MEDIA = "#EXT-X-MEDIA";
    private static final String TAG_MEDIA_DURATION = "#EXTINF";
    private static final String TAG_MEDIA_SEQUENCE = "#EXT-X-MEDIA-SEQUENCE";
    private static final String TAG_PLAYLIST_TYPE = "#EXT-X-PLAYLIST-TYPE";
    private static final String TAG_PREFIX = "#EXT";
    private static final String TAG_PROGRAM_DATE_TIME = "#EXT-X-PROGRAM-DATE-TIME";
    private static final String TAG_START = "#EXT-X-START";
    private static final String TAG_STREAM_INF = "#EXT-X-STREAM-INF";
    private static final String TAG_TARGET_DURATION = "#EXT-X-TARGETDURATION";
    private static final String TAG_VERSION = "#EXT-X-VERSION";
    private static final String TYPE_AUDIO = "AUDIO";
    private static final String TYPE_CLOSED_CAPTIONS = "CLOSED-CAPTIONS";
    private static final String TYPE_SUBTITLES = "SUBTITLES";
    private static final String TYPE_VIDEO = "VIDEO";

    private static class LineIterator {
        private final Queue<String> extraLines;
        private String next;
        private final BufferedReader reader;

        public LineIterator(Queue<String> extraLines, BufferedReader reader) {
            this.extraLines = extraLines;
            this.reader = reader;
        }

        public boolean hasNext() throws IOException {
            if (this.next != null) {
                return true;
            }
            if (this.extraLines.isEmpty()) {
                do {
                    String readLine = this.reader.readLine();
                    this.next = readLine;
                    if (readLine == null) {
                        return false;
                    }
                    this.next = this.next.trim();
                } while (this.next.isEmpty());
                return true;
            }
            this.next = (String) this.extraLines.poll();
            return true;
        }

        public String next() throws IOException {
            if (!hasNext()) {
                return null;
            }
            String result = this.next;
            this.next = null;
            return result;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylist parse(android.net.Uri r6, java.io.InputStream r7) throws java.io.IOException {
        /*
        r5 = this;
        r2 = new java.io.BufferedReader;
        r3 = new java.io.InputStreamReader;
        r3.<init>(r7);
        r2.<init>(r3);
        r0 = new java.util.LinkedList;
        r0.<init>();
        r3 = checkPlaylistHeader(r2);	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0023;
    L_0x0015:
        r3 = new org.telegram.messenger.exoplayer2.source.UnrecognizedInputFormatException;	 Catch:{ all -> 0x001e }
        r4 = "Input does not start with the #EXTM3U header.";
        r3.<init>(r4, r6);	 Catch:{ all -> 0x001e }
        throw r3;	 Catch:{ all -> 0x001e }
    L_0x001e:
        r3 = move-exception;
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r2);
        throw r3;
    L_0x0023:
        r1 = r2.readLine();	 Catch:{ all -> 0x001e }
        if (r1 == 0) goto L_0x00b1;
    L_0x0029:
        r1 = r1.trim();	 Catch:{ all -> 0x001e }
        r3 = r1.isEmpty();	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0023;
    L_0x0033:
        r3 = "#EXT-X-STREAM-INF";
        r3 = r1.startsWith(r3);	 Catch:{ all -> 0x001e }
        if (r3 == 0) goto L_0x0050;
    L_0x003c:
        r0.add(r1);	 Catch:{ all -> 0x001e }
        r3 = new org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylistParser$LineIterator;	 Catch:{ all -> 0x001e }
        r3.<init>(r0, r2);	 Catch:{ all -> 0x001e }
        r4 = r6.toString();	 Catch:{ all -> 0x001e }
        r3 = parseMasterPlaylist(r3, r4);	 Catch:{ all -> 0x001e }
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r2);
    L_0x004f:
        return r3;
    L_0x0050:
        r3 = "#EXT-X-TARGETDURATION";
        r3 = r1.startsWith(r3);	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0098;
    L_0x0059:
        r3 = "#EXT-X-MEDIA-SEQUENCE";
        r3 = r1.startsWith(r3);	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0098;
    L_0x0062:
        r3 = "#EXTINF";
        r3 = r1.startsWith(r3);	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0098;
    L_0x006b:
        r3 = "#EXT-X-KEY";
        r3 = r1.startsWith(r3);	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0098;
    L_0x0074:
        r3 = "#EXT-X-BYTERANGE";
        r3 = r1.startsWith(r3);	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0098;
    L_0x007d:
        r3 = "#EXT-X-DISCONTINUITY";
        r3 = r1.equals(r3);	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0098;
    L_0x0086:
        r3 = "#EXT-X-DISCONTINUITY-SEQUENCE";
        r3 = r1.equals(r3);	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0098;
    L_0x008f:
        r3 = "#EXT-X-ENDLIST";
        r3 = r1.equals(r3);	 Catch:{ all -> 0x001e }
        if (r3 == 0) goto L_0x00ac;
    L_0x0098:
        r0.add(r1);	 Catch:{ all -> 0x001e }
        r3 = new org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylistParser$LineIterator;	 Catch:{ all -> 0x001e }
        r3.<init>(r0, r2);	 Catch:{ all -> 0x001e }
        r4 = r6.toString();	 Catch:{ all -> 0x001e }
        r3 = parseMediaPlaylist(r3, r4);	 Catch:{ all -> 0x001e }
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r2);
        goto L_0x004f;
    L_0x00ac:
        r0.add(r1);	 Catch:{ all -> 0x001e }
        goto L_0x0023;
    L_0x00b1:
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r2);
        r3 = new org.telegram.messenger.exoplayer2.ParserException;
        r4 = "Failed to parse the playlist, could not identify any tags.";
        r3.<init>(r4);
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylistParser.parse(android.net.Uri, java.io.InputStream):org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylist");
    }

    private static boolean checkPlaylistHeader(BufferedReader reader) throws IOException {
        int last = reader.read();
        if (last == 239) {
            if (reader.read() != 187 || reader.read() != 191) {
                return false;
            }
            last = reader.read();
        }
        char last2 = skipIgnorableWhitespace(reader, true, last);
        int playlistHeaderLength = PLAYLIST_HEADER.length();
        for (int i = 0; i < playlistHeaderLength; i++) {
            if (last2 != PLAYLIST_HEADER.charAt(i)) {
                return false;
            }
            last2 = reader.read();
        }
        return Util.isLinebreak(skipIgnorableWhitespace(reader, false, last2));
    }

    private static int skipIgnorableWhitespace(BufferedReader reader, boolean skipLinebreaks, int c) throws IOException {
        while (c != -1 && Character.isWhitespace(c) && (skipLinebreaks || !Util.isLinebreak(c))) {
            c = reader.read();
        }
        return c;
    }

    private static HlsMasterPlaylist parseMasterPlaylist(LineIterator iterator, String baseUri) throws IOException {
        HashSet<String> variantUrls = new HashSet();
        ArrayList<HlsUrl> variants = new ArrayList();
        ArrayList<HlsUrl> audios = new ArrayList();
        ArrayList<HlsUrl> subtitles = new ArrayList();
        ArrayList<String> tags = new ArrayList();
        Format muxedAudioFormat = null;
        List<Format> muxedCaptionFormats = null;
        boolean noClosedCaptions = false;
        while (iterator.hasNext()) {
            String line = iterator.next();
            if (line.startsWith(TAG_PREFIX)) {
                tags.add(line);
            }
            if (line.startsWith(TAG_MEDIA)) {
                int selectionFlags = parseSelectionFlags(line);
                String uri = parseOptionalStringAttr(line, REGEX_URI);
                String id = parseStringAttr(line, REGEX_NAME);
                String language = parseOptionalStringAttr(line, REGEX_LANGUAGE);
                String parseStringAttr = parseStringAttr(line, REGEX_TYPE);
                Object obj = -1;
                switch (parseStringAttr.hashCode()) {
                    case -959297733:
                        if (parseStringAttr.equals(TYPE_SUBTITLES)) {
                            obj = 1;
                            break;
                        }
                        break;
                    case -333210994:
                        if (parseStringAttr.equals(TYPE_CLOSED_CAPTIONS)) {
                            obj = 2;
                            break;
                        }
                        break;
                    case 62628790:
                        if (parseStringAttr.equals(TYPE_AUDIO)) {
                            obj = null;
                            break;
                        }
                        break;
                }
                switch (obj) {
                    case null:
                        Format format = Format.createAudioContainerFormat(id, MimeTypes.APPLICATION_M3U8, null, null, -1, -1, -1, null, selectionFlags, language);
                        if (uri != null) {
                            audios.add(new HlsUrl(uri, format));
                            break;
                        }
                        muxedAudioFormat = format;
                        break;
                    case 1:
                        subtitles.add(new HlsUrl(uri, Format.createTextContainerFormat(id, MimeTypes.APPLICATION_M3U8, MimeTypes.TEXT_VTT, null, -1, selectionFlags, language)));
                        break;
                    case 2:
                        String mimeType;
                        int accessibilityChannel;
                        String instreamId = parseStringAttr(line, REGEX_INSTREAM_ID);
                        if (instreamId.startsWith("CC")) {
                            mimeType = MimeTypes.APPLICATION_CEA608;
                            accessibilityChannel = Integer.parseInt(instreamId.substring(2));
                        } else {
                            mimeType = MimeTypes.APPLICATION_CEA708;
                            accessibilityChannel = Integer.parseInt(instreamId.substring(7));
                        }
                        if (muxedCaptionFormats == null) {
                            muxedCaptionFormats = new ArrayList();
                        }
                        muxedCaptionFormats.add(Format.createTextContainerFormat(id, null, mimeType, null, -1, selectionFlags, language, accessibilityChannel));
                        break;
                    default:
                        break;
                }
            }
            if (line.startsWith(TAG_STREAM_INF)) {
                int width;
                int height;
                int bitrate = parseIntAttr(line, REGEX_BANDWIDTH);
                String averageBandwidthString = parseOptionalStringAttr(line, REGEX_AVERAGE_BANDWIDTH);
                if (averageBandwidthString != null) {
                    bitrate = Integer.parseInt(averageBandwidthString);
                }
                String codecs = parseOptionalStringAttr(line, REGEX_CODECS);
                String resolutionString = parseOptionalStringAttr(line, REGEX_RESOLUTION);
                noClosedCaptions |= line.contains(ATTR_CLOSED_CAPTIONS_NONE);
                if (resolutionString != null) {
                    String[] widthAndHeight = resolutionString.split("x");
                    width = Integer.parseInt(widthAndHeight[0]);
                    height = Integer.parseInt(widthAndHeight[1]);
                    if (width <= 0 || height <= 0) {
                        width = -1;
                        height = -1;
                    }
                } else {
                    width = -1;
                    height = -1;
                }
                line = iterator.next();
                if (variantUrls.add(line)) {
                    variants.add(new HlsUrl(line, Format.createVideoContainerFormat(Integer.toString(variants.size()), MimeTypes.APPLICATION_M3U8, null, codecs, bitrate, width, height, -1.0f, null, 0)));
                }
            }
        }
        if (noClosedCaptions) {
            muxedCaptionFormats = Collections.emptyList();
        }
        return new HlsMasterPlaylist(baseUri, tags, variants, audios, subtitles, muxedAudioFormat, muxedCaptionFormats);
    }

    private static int parseSelectionFlags(String line) {
        int i;
        int i2;
        int i3 = 0;
        if (parseBooleanAttribute(line, REGEX_DEFAULT, false)) {
            i = 1;
        } else {
            i = 0;
        }
        if (parseBooleanAttribute(line, REGEX_FORCED, false)) {
            i2 = 2;
        } else {
            i2 = 0;
        }
        i |= i2;
        if (parseBooleanAttribute(line, REGEX_AUTOSELECT, false)) {
            i3 = 4;
        }
        return i | i3;
    }

    private static HlsMediaPlaylist parseMediaPlaylist(LineIterator iterator, String baseUri) throws IOException {
        int playlistType = 0;
        long startOffsetUs = C0907C.TIME_UNSET;
        int mediaSequence = 0;
        int version = 1;
        long targetDurationUs = C0907C.TIME_UNSET;
        boolean hasIndependentSegmentsTag = false;
        boolean hasEndTag = false;
        Segment initializationSegment = null;
        List<Segment> segments = new ArrayList();
        List<String> tags = new ArrayList();
        long segmentDurationUs = 0;
        boolean hasDiscontinuitySequence = false;
        int playlistDiscontinuitySequence = 0;
        int relativeDiscontinuitySequence = 0;
        long playlistStartTimeUs = 0;
        long segmentStartTimeUs = 0;
        long segmentByteRangeOffset = 0;
        long segmentByteRangeLength = -1;
        int segmentMediaSequence = 0;
        boolean isEncrypted = false;
        String encryptionKeyUri = null;
        String encryptionIV = null;
        while (iterator.hasNext()) {
            String line = iterator.next();
            if (line.startsWith(TAG_PREFIX)) {
                tags.add(line);
            }
            if (line.startsWith(TAG_PLAYLIST_TYPE)) {
                String playlistTypeString = parseStringAttr(line, REGEX_PLAYLIST_TYPE);
                if ("VOD".equals(playlistTypeString)) {
                    playlistType = 1;
                } else if ("EVENT".equals(playlistTypeString)) {
                    playlistType = 2;
                }
            } else if (line.startsWith(TAG_START)) {
                startOffsetUs = (long) (parseDoubleAttr(line, REGEX_TIME_OFFSET) * 1000000.0d);
            } else if (line.startsWith(TAG_INIT_SEGMENT)) {
                String uri = parseStringAttr(line, REGEX_URI);
                String byteRange = parseOptionalStringAttr(line, REGEX_ATTR_BYTERANGE);
                if (byteRange != null) {
                    splitByteRange = byteRange.split("@");
                    segmentByteRangeLength = Long.parseLong(splitByteRange[0]);
                    if (splitByteRange.length > 1) {
                        segmentByteRangeOffset = Long.parseLong(splitByteRange[1]);
                    }
                }
                initializationSegment = new Segment(uri, segmentByteRangeOffset, segmentByteRangeLength);
                segmentByteRangeOffset = 0;
                segmentByteRangeLength = -1;
            } else if (line.startsWith(TAG_TARGET_DURATION)) {
                targetDurationUs = ((long) parseIntAttr(line, REGEX_TARGET_DURATION)) * C0907C.MICROS_PER_SECOND;
            } else if (line.startsWith(TAG_MEDIA_SEQUENCE)) {
                mediaSequence = parseIntAttr(line, REGEX_MEDIA_SEQUENCE);
                segmentMediaSequence = mediaSequence;
            } else if (line.startsWith(TAG_VERSION)) {
                version = parseIntAttr(line, REGEX_VERSION);
            } else if (line.startsWith(TAG_MEDIA_DURATION)) {
                segmentDurationUs = (long) (parseDoubleAttr(line, REGEX_MEDIA_DURATION) * 1000000.0d);
            } else if (line.startsWith(TAG_KEY)) {
                isEncrypted = METHOD_AES128.equals(parseStringAttr(line, REGEX_METHOD));
                if (isEncrypted) {
                    encryptionKeyUri = parseStringAttr(line, REGEX_URI);
                    encryptionIV = parseOptionalStringAttr(line, REGEX_IV);
                } else {
                    encryptionKeyUri = null;
                    encryptionIV = null;
                }
            } else if (line.startsWith(TAG_BYTERANGE)) {
                splitByteRange = parseStringAttr(line, REGEX_BYTERANGE).split("@");
                segmentByteRangeLength = Long.parseLong(splitByteRange[0]);
                if (splitByteRange.length > 1) {
                    segmentByteRangeOffset = Long.parseLong(splitByteRange[1]);
                }
            } else if (line.startsWith(TAG_DISCONTINUITY_SEQUENCE)) {
                hasDiscontinuitySequence = true;
                playlistDiscontinuitySequence = Integer.parseInt(line.substring(line.indexOf(58) + 1));
            } else if (line.equals(TAG_DISCONTINUITY)) {
                relativeDiscontinuitySequence++;
            } else if (line.startsWith(TAG_PROGRAM_DATE_TIME)) {
                if (playlistStartTimeUs == 0) {
                    playlistStartTimeUs = C0907C.msToUs(Util.parseXsDateTime(line.substring(line.indexOf(58) + 1))) - segmentStartTimeUs;
                }
            } else if (!line.startsWith("#")) {
                String segmentEncryptionIV;
                if (!isEncrypted) {
                    segmentEncryptionIV = null;
                } else if (encryptionIV != null) {
                    segmentEncryptionIV = encryptionIV;
                } else {
                    segmentEncryptionIV = Integer.toHexString(segmentMediaSequence);
                }
                segmentMediaSequence++;
                if (segmentByteRangeLength == -1) {
                    segmentByteRangeOffset = 0;
                }
                segments.add(new Segment(line, segmentDurationUs, relativeDiscontinuitySequence, segmentStartTimeUs, isEncrypted, encryptionKeyUri, segmentEncryptionIV, segmentByteRangeOffset, segmentByteRangeLength));
                segmentStartTimeUs += segmentDurationUs;
                segmentDurationUs = 0;
                if (segmentByteRangeLength != -1) {
                    segmentByteRangeOffset += segmentByteRangeLength;
                }
                segmentByteRangeLength = -1;
            } else if (line.equals(TAG_INDEPENDENT_SEGMENTS)) {
                hasIndependentSegmentsTag = true;
            } else if (line.equals(TAG_ENDLIST)) {
                hasEndTag = true;
            }
        }
        return new HlsMediaPlaylist(playlistType, baseUri, tags, startOffsetUs, playlistStartTimeUs, hasDiscontinuitySequence, playlistDiscontinuitySequence, mediaSequence, version, targetDurationUs, hasIndependentSegmentsTag, hasEndTag, playlistStartTimeUs != 0, initializationSegment, segments);
    }

    private static int parseIntAttr(String line, Pattern pattern) throws ParserException {
        return Integer.parseInt(parseStringAttr(line, pattern));
    }

    private static double parseDoubleAttr(String line, Pattern pattern) throws ParserException {
        return Double.parseDouble(parseStringAttr(line, pattern));
    }

    private static String parseOptionalStringAttr(String line, Pattern pattern) {
        Matcher matcher = pattern.matcher(line);
        return matcher.find() ? matcher.group(1) : null;
    }

    private static String parseStringAttr(String line, Pattern pattern) throws ParserException {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find() && matcher.groupCount() == 1) {
            return matcher.group(1);
        }
        throw new ParserException("Couldn't match " + pattern.pattern() + " in " + line);
    }

    private static boolean parseBooleanAttribute(String line, Pattern pattern, boolean defaultValue) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1).equals(BOOLEAN_TRUE);
        }
        return defaultValue;
    }

    private static Pattern compileBooleanAttrPattern(String attribute) {
        return Pattern.compile(attribute + "=(" + BOOLEAN_FALSE + "|" + BOOLEAN_TRUE + ")");
    }
}
