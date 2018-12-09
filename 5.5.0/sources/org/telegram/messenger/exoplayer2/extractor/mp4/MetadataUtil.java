package org.telegram.messenger.exoplayer2.extractor.mp4;

import android.util.Log;
import org.telegram.messenger.exoplayer2.metadata.Metadata.Entry;
import org.telegram.messenger.exoplayer2.metadata.id3.ApicFrame;
import org.telegram.messenger.exoplayer2.metadata.id3.CommentFrame;
import org.telegram.messenger.exoplayer2.metadata.id3.Id3Frame;
import org.telegram.messenger.exoplayer2.metadata.id3.TextInformationFrame;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.Util;

final class MetadataUtil {
    private static final String LANGUAGE_UNDEFINED = "und";
    private static final int SHORT_TYPE_ALBUM = Util.getIntegerCodeForString("alb");
    private static final int SHORT_TYPE_ARTIST = Util.getIntegerCodeForString("ART");
    private static final int SHORT_TYPE_COMMENT = Util.getIntegerCodeForString("cmt");
    private static final int SHORT_TYPE_COMPOSER_1 = Util.getIntegerCodeForString("com");
    private static final int SHORT_TYPE_COMPOSER_2 = Util.getIntegerCodeForString("wrt");
    private static final int SHORT_TYPE_ENCODER = Util.getIntegerCodeForString("too");
    private static final int SHORT_TYPE_GENRE = Util.getIntegerCodeForString("gen");
    private static final int SHORT_TYPE_LYRICS = Util.getIntegerCodeForString("lyr");
    private static final int SHORT_TYPE_NAME_1 = Util.getIntegerCodeForString("nam");
    private static final int SHORT_TYPE_NAME_2 = Util.getIntegerCodeForString("trk");
    private static final int SHORT_TYPE_YEAR = Util.getIntegerCodeForString("day");
    private static final String[] STANDARD_GENRES = new String[]{"Blues", "Classic Rock", "Country", "Dance", "Disco", "Funk", "Grunge", "Hip-Hop", "Jazz", "Metal", "New Age", "Oldies", "Other", "Pop", "R&B", "Rap", "Reggae", "Rock", "Techno", "Industrial", "Alternative", "Ska", "Death Metal", "Pranks", "Soundtrack", "Euro-Techno", "Ambient", "Trip-Hop", "Vocal", "Jazz+Funk", "Fusion", "Trance", "Classical", "Instrumental", "Acid", "House", "Game", "Sound Clip", "Gospel", "Noise", "AlternRock", "Bass", "Soul", "Punk", "Space", "Meditative", "Instrumental Pop", "Instrumental Rock", "Ethnic", "Gothic", "Darkwave", "Techno-Industrial", "Electronic", "Pop-Folk", "Eurodance", "Dream", "Southern Rock", "Comedy", "Cult", "Gangsta", "Top 40", "Christian Rap", "Pop/Funk", "Jungle", "Native American", "Cabaret", "New Wave", "Psychadelic", "Rave", "Showtunes", "Trailer", "Lo-Fi", "Tribal", "Acid Punk", "Acid Jazz", "Polka", "Retro", "Musical", "Rock & Roll", "Hard Rock", "Folk", "Folk-Rock", "National Folk", "Swing", "Fast Fusion", "Bebob", "Latin", "Revival", "Celtic", "Bluegrass", "Avantgarde", "Gothic Rock", "Progressive Rock", "Psychedelic Rock", "Symphonic Rock", "Slow Rock", "Big Band", "Chorus", "Easy Listening", "Acoustic", "Humour", "Speech", "Chanson", "Opera", "Chamber Music", "Sonata", "Symphony", "Booty Bass", "Primus", "Porn Groove", "Satire", "Slow Jam", "Club", "Tango", "Samba", "Folklore", "Ballad", "Power Ballad", "Rhythmic Soul", "Freestyle", "Duet", "Punk Rock", "Drum Solo", "A capella", "Euro-House", "Dance Hall", "Goa", "Drum & Bass", "Club-House", "Hardcore", "Terror", "Indie", "BritPop", "Negerpunk", "Polsk Punk", "Beat", "Christian Gangsta Rap", "Heavy Metal", "Black Metal", "Crossover", "Contemporary Christian", "Christian Rock", "Merengue", "Salsa", "Thrash Metal", "Anime", "Jpop", "Synthpop"};
    private static final String TAG = "MetadataUtil";
    private static final int TYPE_ALBUM_ARTIST = Util.getIntegerCodeForString("aART");
    private static final int TYPE_COMPILATION = Util.getIntegerCodeForString("cpil");
    private static final int TYPE_COVER_ART = Util.getIntegerCodeForString("covr");
    private static final int TYPE_DISK_NUMBER = Util.getIntegerCodeForString("disk");
    private static final int TYPE_GAPLESS_ALBUM = Util.getIntegerCodeForString("pgap");
    private static final int TYPE_GENRE = Util.getIntegerCodeForString("gnre");
    private static final int TYPE_GROUPING = Util.getIntegerCodeForString("grp");
    private static final int TYPE_INTERNAL = Util.getIntegerCodeForString("----");
    private static final int TYPE_RATING = Util.getIntegerCodeForString("rtng");
    private static final int TYPE_SORT_ALBUM = Util.getIntegerCodeForString("soal");
    private static final int TYPE_SORT_ALBUM_ARTIST = Util.getIntegerCodeForString("soaa");
    private static final int TYPE_SORT_ARTIST = Util.getIntegerCodeForString("soar");
    private static final int TYPE_SORT_COMPOSER = Util.getIntegerCodeForString("soco");
    private static final int TYPE_SORT_TRACK_NAME = Util.getIntegerCodeForString("sonm");
    private static final int TYPE_TEMPO = Util.getIntegerCodeForString("tmpo");
    private static final int TYPE_TRACK_NUMBER = Util.getIntegerCodeForString("trkn");
    private static final int TYPE_TV_SHOW = Util.getIntegerCodeForString("tvsh");
    private static final int TYPE_TV_SORT_SHOW = Util.getIntegerCodeForString("sosn");

    private MetadataUtil() {
    }

    private static CommentFrame parseCommentAttribute(int i, ParsableByteArray parsableByteArray) {
        int readInt = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == Atom.TYPE_data) {
            parsableByteArray.skipBytes(8);
            String readNullTerminatedString = parsableByteArray.readNullTerminatedString(readInt - 16);
            return new CommentFrame(LANGUAGE_UNDEFINED, readNullTerminatedString, readNullTerminatedString);
        }
        Log.w(TAG, "Failed to parse comment attribute: " + Atom.getAtomTypeString(i));
        return null;
    }

    private static ApicFrame parseCoverArt(ParsableByteArray parsableByteArray) {
        int readInt = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == Atom.TYPE_data) {
            int parseFullAtomFlags = Atom.parseFullAtomFlags(parsableByteArray.readInt());
            String str = parseFullAtomFlags == 13 ? "image/jpeg" : parseFullAtomFlags == 14 ? "image/png" : null;
            if (str == null) {
                Log.w(TAG, "Unrecognized cover art flags: " + parseFullAtomFlags);
                return null;
            }
            parsableByteArray.skipBytes(4);
            byte[] bArr = new byte[(readInt - 16)];
            parsableByteArray.readBytes(bArr, 0, bArr.length);
            return new ApicFrame(str, null, 3, bArr);
        }
        Log.w(TAG, "Failed to parse cover art attribute");
        return null;
    }

    public static Entry parseIlstElement(ParsableByteArray parsableByteArray) {
        int readInt = parsableByteArray.readInt() + parsableByteArray.getPosition();
        int readInt2 = parsableByteArray.readInt();
        int i = (readInt2 >> 24) & 255;
        Entry parseCommentAttribute;
        if (i == 169 || i == 65533) {
            i = 16777215 & readInt2;
            try {
                if (i == SHORT_TYPE_COMMENT) {
                    parseCommentAttribute = parseCommentAttribute(readInt2, parsableByteArray);
                    return parseCommentAttribute;
                } else if (i == SHORT_TYPE_NAME_1 || i == SHORT_TYPE_NAME_2) {
                    parseCommentAttribute = parseTextAttribute(readInt2, "TIT2", parsableByteArray);
                    parsableByteArray.setPosition(readInt);
                    return parseCommentAttribute;
                } else if (i == SHORT_TYPE_COMPOSER_1 || i == SHORT_TYPE_COMPOSER_2) {
                    parseCommentAttribute = parseTextAttribute(readInt2, "TCOM", parsableByteArray);
                    parsableByteArray.setPosition(readInt);
                    return parseCommentAttribute;
                } else if (i == SHORT_TYPE_YEAR) {
                    parseCommentAttribute = parseTextAttribute(readInt2, "TDRC", parsableByteArray);
                    parsableByteArray.setPosition(readInt);
                    return parseCommentAttribute;
                } else if (i == SHORT_TYPE_ARTIST) {
                    parseCommentAttribute = parseTextAttribute(readInt2, "TPE1", parsableByteArray);
                    parsableByteArray.setPosition(readInt);
                    return parseCommentAttribute;
                } else if (i == SHORT_TYPE_ENCODER) {
                    parseCommentAttribute = parseTextAttribute(readInt2, "TSSE", parsableByteArray);
                    parsableByteArray.setPosition(readInt);
                    return parseCommentAttribute;
                } else if (i == SHORT_TYPE_ALBUM) {
                    parseCommentAttribute = parseTextAttribute(readInt2, "TALB", parsableByteArray);
                    parsableByteArray.setPosition(readInt);
                    return parseCommentAttribute;
                } else if (i == SHORT_TYPE_LYRICS) {
                    parseCommentAttribute = parseTextAttribute(readInt2, "USLT", parsableByteArray);
                    parsableByteArray.setPosition(readInt);
                    return parseCommentAttribute;
                } else if (i == SHORT_TYPE_GENRE) {
                    parseCommentAttribute = parseTextAttribute(readInt2, "TCON", parsableByteArray);
                    parsableByteArray.setPosition(readInt);
                    return parseCommentAttribute;
                } else if (i == TYPE_GROUPING) {
                    parseCommentAttribute = parseTextAttribute(readInt2, "TIT1", parsableByteArray);
                    parsableByteArray.setPosition(readInt);
                    return parseCommentAttribute;
                }
            } finally {
                parsableByteArray.setPosition(readInt);
            }
        } else if (readInt2 == TYPE_GENRE) {
            parseCommentAttribute = parseStandardGenreAttribute(parsableByteArray);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        } else if (readInt2 == TYPE_DISK_NUMBER) {
            parseCommentAttribute = parseIndexAndCountAttribute(readInt2, "TPOS", parsableByteArray);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        } else if (readInt2 == TYPE_TRACK_NUMBER) {
            parseCommentAttribute = parseIndexAndCountAttribute(readInt2, "TRCK", parsableByteArray);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        } else if (readInt2 == TYPE_TEMPO) {
            parseCommentAttribute = parseUint8Attribute(readInt2, "TBPM", parsableByteArray, true, false);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        } else if (readInt2 == TYPE_COMPILATION) {
            parseCommentAttribute = parseUint8Attribute(readInt2, "TCMP", parsableByteArray, true, true);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        } else if (readInt2 == TYPE_COVER_ART) {
            parseCommentAttribute = parseCoverArt(parsableByteArray);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        } else if (readInt2 == TYPE_ALBUM_ARTIST) {
            parseCommentAttribute = parseTextAttribute(readInt2, "TPE2", parsableByteArray);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        } else if (readInt2 == TYPE_SORT_TRACK_NAME) {
            parseCommentAttribute = parseTextAttribute(readInt2, "TSOT", parsableByteArray);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        } else if (readInt2 == TYPE_SORT_ALBUM) {
            parseCommentAttribute = parseTextAttribute(readInt2, "TSO2", parsableByteArray);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        } else if (readInt2 == TYPE_SORT_ARTIST) {
            parseCommentAttribute = parseTextAttribute(readInt2, "TSOA", parsableByteArray);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        } else if (readInt2 == TYPE_SORT_ALBUM_ARTIST) {
            parseCommentAttribute = parseTextAttribute(readInt2, "TSOP", parsableByteArray);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        } else if (readInt2 == TYPE_SORT_COMPOSER) {
            parseCommentAttribute = parseTextAttribute(readInt2, "TSOC", parsableByteArray);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        } else if (readInt2 == TYPE_RATING) {
            parseCommentAttribute = parseUint8Attribute(readInt2, "ITUNESADVISORY", parsableByteArray, false, false);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        } else if (readInt2 == TYPE_GAPLESS_ALBUM) {
            parseCommentAttribute = parseUint8Attribute(readInt2, "ITUNESGAPLESS", parsableByteArray, false, true);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        } else if (readInt2 == TYPE_TV_SORT_SHOW) {
            parseCommentAttribute = parseTextAttribute(readInt2, "TVSHOWSORT", parsableByteArray);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        } else if (readInt2 == TYPE_TV_SHOW) {
            parseCommentAttribute = parseTextAttribute(readInt2, "TVSHOW", parsableByteArray);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        } else if (readInt2 == TYPE_INTERNAL) {
            parseCommentAttribute = parseInternalAttribute(parsableByteArray, readInt);
            parsableByteArray.setPosition(readInt);
            return parseCommentAttribute;
        }
        Log.d(TAG, "Skipped unknown metadata entry: " + Atom.getAtomTypeString(readInt2));
        parsableByteArray.setPosition(readInt);
        return null;
    }

    private static TextInformationFrame parseIndexAndCountAttribute(int i, String str, ParsableByteArray parsableByteArray) {
        int readInt = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == Atom.TYPE_data && readInt >= 22) {
            parsableByteArray.skipBytes(10);
            readInt = parsableByteArray.readUnsignedShort();
            if (readInt > 0) {
                String str2 = TtmlNode.ANONYMOUS_REGION_ID + readInt;
                int readUnsignedShort = parsableByteArray.readUnsignedShort();
                if (readUnsignedShort > 0) {
                    str2 = str2 + "/" + readUnsignedShort;
                }
                return new TextInformationFrame(str, null, str2);
            }
        }
        Log.w(TAG, "Failed to parse index/count attribute: " + Atom.getAtomTypeString(i));
        return null;
    }

    private static Id3Frame parseInternalAttribute(ParsableByteArray parsableByteArray, int i) {
        int i2 = -1;
        int i3 = -1;
        String str = null;
        Object obj = null;
        while (parsableByteArray.getPosition() < i) {
            int position = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            int readInt2 = parsableByteArray.readInt();
            parsableByteArray.skipBytes(4);
            if (readInt2 == Atom.TYPE_mean) {
                obj = parsableByteArray.readNullTerminatedString(readInt - 12);
            } else if (readInt2 == Atom.TYPE_name) {
                str = parsableByteArray.readNullTerminatedString(readInt - 12);
            } else {
                if (readInt2 == Atom.TYPE_data) {
                    i2 = readInt;
                    i3 = position;
                }
                parsableByteArray.skipBytes(readInt - 12);
            }
        }
        if (!"com.apple.iTunes".equals(obj) || !"iTunSMPB".equals(str) || i3 == -1) {
            return null;
        }
        parsableByteArray.setPosition(i3);
        parsableByteArray.skipBytes(16);
        return new CommentFrame(LANGUAGE_UNDEFINED, str, parsableByteArray.readNullTerminatedString(i2 - 16));
    }

    private static TextInformationFrame parseStandardGenreAttribute(ParsableByteArray parsableByteArray) {
        int parseUint8AttributeValue = parseUint8AttributeValue(parsableByteArray);
        String str = (parseUint8AttributeValue <= 0 || parseUint8AttributeValue > STANDARD_GENRES.length) ? null : STANDARD_GENRES[parseUint8AttributeValue - 1];
        if (str != null) {
            return new TextInformationFrame("TCON", null, str);
        }
        Log.w(TAG, "Failed to parse standard genre code");
        return null;
    }

    private static TextInformationFrame parseTextAttribute(int i, String str, ParsableByteArray parsableByteArray) {
        int readInt = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == Atom.TYPE_data) {
            parsableByteArray.skipBytes(8);
            return new TextInformationFrame(str, null, parsableByteArray.readNullTerminatedString(readInt - 16));
        }
        Log.w(TAG, "Failed to parse text attribute: " + Atom.getAtomTypeString(i));
        return null;
    }

    private static Id3Frame parseUint8Attribute(int i, String str, ParsableByteArray parsableByteArray, boolean z, boolean z2) {
        int parseUint8AttributeValue = parseUint8AttributeValue(parsableByteArray);
        int min = z2 ? Math.min(1, parseUint8AttributeValue) : parseUint8AttributeValue;
        if (min >= 0) {
            return z ? new TextInformationFrame(str, null, Integer.toString(min)) : new CommentFrame(LANGUAGE_UNDEFINED, str, Integer.toString(min));
        } else {
            Log.w(TAG, "Failed to parse uint8 attribute: " + Atom.getAtomTypeString(i));
            return null;
        }
    }

    private static int parseUint8AttributeValue(ParsableByteArray parsableByteArray) {
        parsableByteArray.skipBytes(4);
        if (parsableByteArray.readInt() == Atom.TYPE_data) {
            parsableByteArray.skipBytes(8);
            return parsableByteArray.readUnsignedByte();
        }
        Log.w(TAG, "Failed to parse uint8 attribute value");
        return -1;
    }
}
