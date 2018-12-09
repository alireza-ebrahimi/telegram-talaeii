package org.telegram.messenger.exoplayer2.source.hls.playlist;

import com.google.android.gms.stats.netstats.NetstatsParserPatterns;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.exoplayer2.C3446C;
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
    private static final Pattern REGEX_DEFAULT = compileBooleanAttrPattern(NetstatsParserPatterns.TYPE_BACKGROUND_PATTERN);
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

        public LineIterator(Queue<String> queue, BufferedReader bufferedReader) {
            this.extraLines = queue;
            this.reader = bufferedReader;
        }

        public boolean hasNext() {
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

        public String next() {
            if (!hasNext()) {
                return null;
            }
            String str = this.next;
            this.next = null;
            return str;
        }
    }

    private static boolean checkPlaylistHeader(BufferedReader bufferedReader) {
        int read = bufferedReader.read();
        if (read == 239) {
            if (bufferedReader.read() != 187 || bufferedReader.read() != 191) {
                return false;
            }
            read = bufferedReader.read();
        }
        char skipIgnorableWhitespace = skipIgnorableWhitespace(bufferedReader, true, read);
        int length = PLAYLIST_HEADER.length();
        char c = skipIgnorableWhitespace;
        for (read = 0; read < length; read++) {
            if (c != PLAYLIST_HEADER.charAt(read)) {
                return false;
            }
            c = bufferedReader.read();
        }
        return Util.isLinebreak(skipIgnorableWhitespace(bufferedReader, false, c));
    }

    private static Pattern compileBooleanAttrPattern(String str) {
        return Pattern.compile(str + "=(" + BOOLEAN_FALSE + "|" + BOOLEAN_TRUE + ")");
    }

    private static boolean parseBooleanAttribute(String str, Pattern pattern, boolean z) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? matcher.group(1).equals(BOOLEAN_TRUE) : z;
    }

    private static double parseDoubleAttr(String str, Pattern pattern) {
        return Double.parseDouble(parseStringAttr(str, pattern));
    }

    private static int parseIntAttr(String str, Pattern pattern) {
        return Integer.parseInt(parseStringAttr(str, pattern));
    }

    private static HlsMasterPlaylist parseMasterPlaylist(LineIterator lineIterator, String str) {
        HashSet hashSet = new HashSet();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        Format format = null;
        List list = null;
        int i = 0;
        while (lineIterator.hasNext()) {
            String next = lineIterator.next();
            if (next.startsWith(TAG_PREFIX)) {
                arrayList4.add(next);
            }
            String parseStringAttr;
            String parseStringAttr2;
            int parseInt;
            if (next.startsWith(TAG_MEDIA)) {
                List list2;
                Format format2;
                int parseSelectionFlags = parseSelectionFlags(next);
                String parseOptionalStringAttr = parseOptionalStringAttr(next, REGEX_URI);
                parseStringAttr = parseStringAttr(next, REGEX_NAME);
                String parseOptionalStringAttr2 = parseOptionalStringAttr(next, REGEX_LANGUAGE);
                parseStringAttr2 = parseStringAttr(next, REGEX_TYPE);
                Object obj = -1;
                switch (parseStringAttr2.hashCode()) {
                    case -959297733:
                        if (parseStringAttr2.equals(TYPE_SUBTITLES)) {
                            obj = 1;
                            break;
                        }
                        break;
                    case -333210994:
                        if (parseStringAttr2.equals(TYPE_CLOSED_CAPTIONS)) {
                            obj = 2;
                            break;
                        }
                        break;
                    case 62628790:
                        if (parseStringAttr2.equals(TYPE_AUDIO)) {
                            obj = null;
                            break;
                        }
                        break;
                }
                switch (obj) {
                    case null:
                        Format createAudioContainerFormat = Format.createAudioContainerFormat(parseStringAttr, MimeTypes.APPLICATION_M3U8, null, null, -1, -1, -1, null, parseSelectionFlags, parseOptionalStringAttr2);
                        if (parseOptionalStringAttr != null) {
                            arrayList2.add(new HlsUrl(parseOptionalStringAttr, createAudioContainerFormat));
                            list2 = list;
                            format2 = format;
                            break;
                        }
                        format2 = createAudioContainerFormat;
                        list2 = list;
                        break;
                    case 1:
                        arrayList3.add(new HlsUrl(parseOptionalStringAttr, Format.createTextContainerFormat(parseStringAttr, MimeTypes.APPLICATION_M3U8, MimeTypes.TEXT_VTT, null, -1, parseSelectionFlags, parseOptionalStringAttr2)));
                        list2 = list;
                        format2 = format;
                        break;
                    case 2:
                        String str2;
                        String parseStringAttr3 = parseStringAttr(next, REGEX_INSTREAM_ID);
                        if (parseStringAttr3.startsWith("CC")) {
                            str2 = MimeTypes.APPLICATION_CEA608;
                            parseInt = Integer.parseInt(parseStringAttr3.substring(2));
                        } else {
                            str2 = MimeTypes.APPLICATION_CEA708;
                            parseInt = Integer.parseInt(parseStringAttr3.substring(7));
                        }
                        List arrayList5 = list == null ? new ArrayList() : list;
                        arrayList5.add(Format.createTextContainerFormat(parseStringAttr, null, str2, null, -1, parseSelectionFlags, parseOptionalStringAttr2, parseInt));
                        list2 = arrayList5;
                        format2 = format;
                        break;
                    default:
                        list2 = list;
                        format2 = format;
                        break;
                }
                list = list2;
                format = format2;
            } else if (next.startsWith(TAG_STREAM_INF)) {
                int i2;
                int i3;
                int parseIntAttr = parseIntAttr(next, REGEX_BANDWIDTH);
                parseStringAttr = parseOptionalStringAttr(next, REGEX_AVERAGE_BANDWIDTH);
                if (parseStringAttr != null) {
                    parseIntAttr = Integer.parseInt(parseStringAttr);
                }
                parseStringAttr2 = parseOptionalStringAttr(next, REGEX_CODECS);
                parseStringAttr = parseOptionalStringAttr(next, REGEX_RESOLUTION);
                parseInt = i | next.contains(ATTR_CLOSED_CAPTIONS_NONE);
                if (parseStringAttr != null) {
                    String[] split = parseStringAttr.split("x");
                    int parseInt2 = Integer.parseInt(split[0]);
                    int parseInt3 = Integer.parseInt(split[1]);
                    if (parseInt2 <= 0 || parseInt3 <= 0) {
                        parseInt2 = -1;
                        parseInt3 = -1;
                    }
                    i2 = parseInt3;
                    i3 = parseInt2;
                } else {
                    i3 = -1;
                    i2 = -1;
                }
                String next2 = lineIterator.next();
                if (hashSet.add(next2)) {
                    arrayList.add(new HlsUrl(next2, Format.createVideoContainerFormat(Integer.toString(arrayList.size()), MimeTypes.APPLICATION_M3U8, null, parseStringAttr2, parseIntAttr, i3, i2, -1.0f, null, 0)));
                }
                i = parseInt;
            }
        }
        return new HlsMasterPlaylist(str, arrayList4, arrayList, arrayList2, arrayList3, format, i != 0 ? Collections.emptyList() : list);
    }

    private static HlsMediaPlaylist parseMediaPlaylist(LineIterator lineIterator, String str) {
        List arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        long j = 0;
        long j2 = -1;
        boolean z = false;
        String str2 = null;
        long j3 = 0;
        long j4 = -9223372036854775807L;
        int i = 0;
        long j5 = -9223372036854775807L;
        int i2 = 0;
        int i3 = 0;
        boolean z2 = false;
        String str3 = null;
        Segment segment = null;
        int i4 = 1;
        long j6 = 0;
        int i5 = 0;
        boolean z3 = false;
        boolean z4 = false;
        int i6 = 0;
        long j7 = 0;
        while (lineIterator.hasNext()) {
            String next = lineIterator.next();
            if (next.startsWith(TAG_PREFIX)) {
                arrayList2.add(next);
            }
            String parseStringAttr;
            int i7;
            if (next.startsWith(TAG_PLAYLIST_TYPE)) {
                parseStringAttr = parseStringAttr(next, REGEX_PLAYLIST_TYPE);
                i7 = "VOD".equals(parseStringAttr) ? 1 : "EVENT".equals(parseStringAttr) ? 2 : i2;
                i2 = i7;
            } else if (next.startsWith(TAG_START)) {
                j5 = (long) (parseDoubleAttr(next, REGEX_TIME_OFFSET) * 1000000.0d);
            } else if (next.startsWith(TAG_INIT_SEGMENT)) {
                long parseLong;
                r3 = parseStringAttr(next, REGEX_URI);
                parseStringAttr = parseOptionalStringAttr(next, REGEX_ATTR_BYTERANGE);
                if (parseStringAttr != null) {
                    r2 = parseStringAttr.split("@");
                    parseLong = Long.parseLong(r2[0]);
                    if (r2.length > 1) {
                        j = Long.parseLong(r2[1]);
                    }
                } else {
                    parseLong = j2;
                }
                Segment segment2 = new Segment(r3, j, parseLong);
                j = 0;
                j2 = -1;
                segment = segment2;
            } else if (next.startsWith(TAG_TARGET_DURATION)) {
                j4 = ((long) parseIntAttr(next, REGEX_TARGET_DURATION)) * C3446C.MICROS_PER_SECOND;
            } else if (next.startsWith(TAG_MEDIA_SEQUENCE)) {
                i7 = parseIntAttr(next, REGEX_MEDIA_SEQUENCE);
                i6 = i7;
                i = i7;
            } else if (next.startsWith(TAG_VERSION)) {
                i4 = parseIntAttr(next, REGEX_VERSION);
            } else if (next.startsWith(TAG_MEDIA_DURATION)) {
                j3 = (long) (parseDoubleAttr(next, REGEX_MEDIA_DURATION) * 1000000.0d);
            } else if (next.startsWith(TAG_KEY)) {
                z = METHOD_AES128.equals(parseStringAttr(next, REGEX_METHOD));
                if (z) {
                    r3 = parseStringAttr(next, REGEX_URI);
                    parseStringAttr = parseOptionalStringAttr(next, REGEX_IV);
                } else {
                    r3 = null;
                    parseStringAttr = null;
                }
                str3 = parseStringAttr;
                str2 = r3;
            } else if (next.startsWith(TAG_BYTERANGE)) {
                r2 = parseStringAttr(next, REGEX_BYTERANGE).split("@");
                j2 = Long.parseLong(r2[0]);
                j = r2.length > 1 ? Long.parseLong(r2[1]) : j;
            } else if (next.startsWith(TAG_DISCONTINUITY_SEQUENCE)) {
                i3 = Integer.parseInt(next.substring(next.indexOf(58) + 1));
                z2 = true;
            } else if (next.equals(TAG_DISCONTINUITY)) {
                i5++;
            } else if (next.startsWith(TAG_PROGRAM_DATE_TIME)) {
                if (j6 == 0) {
                    j6 = C3446C.msToUs(Util.parseXsDateTime(next.substring(next.indexOf(58) + 1))) - j7;
                }
            } else if (!next.startsWith("#")) {
                String toHexString = !z ? null : str3 != null ? str3 : Integer.toHexString(i6);
                int i8 = i6 + 1;
                long j8 = j2 == -1 ? 0 : j;
                arrayList.add(new Segment(next, j3, i5, j7, z, str2, toHexString, j8, j2));
                j = j7 + j3;
                long j9 = j2 != -1 ? j8 + j2 : j8;
                j2 = -1;
                i6 = i8;
                j7 = j;
                j3 = 0;
                j = j9;
            } else if (next.equals(TAG_INDEPENDENT_SEGMENTS)) {
                z4 = true;
            } else if (next.equals(TAG_ENDLIST)) {
                z3 = true;
            }
        }
        return new HlsMediaPlaylist(i2, str, arrayList2, j5, j6, z2, i3, i, i4, j4, z4, z3, j6 != 0, segment, arrayList);
    }

    private static String parseOptionalStringAttr(String str, Pattern pattern) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? matcher.group(1) : null;
    }

    private static int parseSelectionFlags(String str) {
        int i = 0;
        int i2 = (parseBooleanAttribute(str, REGEX_DEFAULT, false) ? 1 : 0) | (parseBooleanAttribute(str, REGEX_FORCED, false) ? 2 : 0);
        if (parseBooleanAttribute(str, REGEX_AUTOSELECT, false)) {
            i = 4;
        }
        return i2 | i;
    }

    private static String parseStringAttr(String str, Pattern pattern) {
        Matcher matcher = pattern.matcher(str);
        if (matcher.find() && matcher.groupCount() == 1) {
            return matcher.group(1);
        }
        throw new ParserException("Couldn't match " + pattern.pattern() + " in " + str);
    }

    private static int skipIgnorableWhitespace(BufferedReader bufferedReader, boolean z, int i) {
        while (i != -1 && Character.isWhitespace(i) && (z || !Util.isLinebreak(i))) {
            i = bufferedReader.read();
        }
        return i;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylist parse(android.net.Uri r5, java.io.InputStream r6) {
        /*
        r4 = this;
        r1 = new java.io.BufferedReader;
        r0 = new java.io.InputStreamReader;
        r0.<init>(r6);
        r1.<init>(r0);
        r0 = new java.util.LinkedList;
        r0.<init>();
        r2 = checkPlaylistHeader(r1);	 Catch:{ all -> 0x001e }
        if (r2 != 0) goto L_0x0023;
    L_0x0015:
        r0 = new org.telegram.messenger.exoplayer2.source.UnrecognizedInputFormatException;	 Catch:{ all -> 0x001e }
        r2 = "Input does not start with the #EXTM3U header.";
        r0.<init>(r2, r5);	 Catch:{ all -> 0x001e }
        throw r0;	 Catch:{ all -> 0x001e }
    L_0x001e:
        r0 = move-exception;
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r1);
        throw r0;
    L_0x0023:
        r2 = r1.readLine();	 Catch:{ all -> 0x001e }
        if (r2 == 0) goto L_0x00b1;
    L_0x0029:
        r2 = r2.trim();	 Catch:{ all -> 0x001e }
        r3 = r2.isEmpty();	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0023;
    L_0x0033:
        r3 = "#EXT-X-STREAM-INF";
        r3 = r2.startsWith(r3);	 Catch:{ all -> 0x001e }
        if (r3 == 0) goto L_0x0050;
    L_0x003c:
        r0.add(r2);	 Catch:{ all -> 0x001e }
        r2 = new org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylistParser$LineIterator;	 Catch:{ all -> 0x001e }
        r2.<init>(r0, r1);	 Catch:{ all -> 0x001e }
        r0 = r5.toString();	 Catch:{ all -> 0x001e }
        r0 = parseMasterPlaylist(r2, r0);	 Catch:{ all -> 0x001e }
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r1);
    L_0x004f:
        return r0;
    L_0x0050:
        r3 = "#EXT-X-TARGETDURATION";
        r3 = r2.startsWith(r3);	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0098;
    L_0x0059:
        r3 = "#EXT-X-MEDIA-SEQUENCE";
        r3 = r2.startsWith(r3);	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0098;
    L_0x0062:
        r3 = "#EXTINF";
        r3 = r2.startsWith(r3);	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0098;
    L_0x006b:
        r3 = "#EXT-X-KEY";
        r3 = r2.startsWith(r3);	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0098;
    L_0x0074:
        r3 = "#EXT-X-BYTERANGE";
        r3 = r2.startsWith(r3);	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0098;
    L_0x007d:
        r3 = "#EXT-X-DISCONTINUITY";
        r3 = r2.equals(r3);	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0098;
    L_0x0086:
        r3 = "#EXT-X-DISCONTINUITY-SEQUENCE";
        r3 = r2.equals(r3);	 Catch:{ all -> 0x001e }
        if (r3 != 0) goto L_0x0098;
    L_0x008f:
        r3 = "#EXT-X-ENDLIST";
        r3 = r2.equals(r3);	 Catch:{ all -> 0x001e }
        if (r3 == 0) goto L_0x00ac;
    L_0x0098:
        r0.add(r2);	 Catch:{ all -> 0x001e }
        r2 = new org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylistParser$LineIterator;	 Catch:{ all -> 0x001e }
        r2.<init>(r0, r1);	 Catch:{ all -> 0x001e }
        r0 = r5.toString();	 Catch:{ all -> 0x001e }
        r0 = parseMediaPlaylist(r2, r0);	 Catch:{ all -> 0x001e }
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r1);
        goto L_0x004f;
    L_0x00ac:
        r0.add(r2);	 Catch:{ all -> 0x001e }
        goto L_0x0023;
    L_0x00b1:
        org.telegram.messenger.exoplayer2.util.Util.closeQuietly(r1);
        r0 = new org.telegram.messenger.exoplayer2.ParserException;
        r1 = "Failed to parse the playlist, could not identify any tags.";
        r0.<init>(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylistParser.parse(android.net.Uri, java.io.InputStream):org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylist");
    }
}
