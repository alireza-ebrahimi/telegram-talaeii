package org.telegram.messenger.exoplayer2.extractor.mp4;

import android.util.Log;
import com.coremedia.iso.boxes.GenreBox;
import com.coremedia.iso.boxes.RatingBox;
import org.telegram.messenger.exoplayer2.metadata.Metadata$Entry;
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
    private static final int TYPE_GENRE = Util.getIntegerCodeForString(GenreBox.TYPE);
    private static final int TYPE_GROUPING = Util.getIntegerCodeForString("grp");
    private static final int TYPE_INTERNAL = Util.getIntegerCodeForString("----");
    private static final int TYPE_RATING = Util.getIntegerCodeForString(RatingBox.TYPE);
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

    public static Metadata$Entry parseIlstElement(ParsableByteArray ilst) {
        int endPosition = ilst.getPosition() + ilst.readInt();
        int type = ilst.readInt();
        int typeTopByte = (type >> 24) & 255;
        Metadata$Entry parseCommentAttribute;
        if (typeTopByte == 169 || typeTopByte == 65533) {
            int shortType = type & 16777215;
            try {
                if (shortType == SHORT_TYPE_COMMENT) {
                    parseCommentAttribute = parseCommentAttribute(type, ilst);
                    return parseCommentAttribute;
                } else if (shortType == SHORT_TYPE_NAME_1 || shortType == SHORT_TYPE_NAME_2) {
                    parseCommentAttribute = parseTextAttribute(type, "TIT2", ilst);
                    ilst.setPosition(endPosition);
                    return parseCommentAttribute;
                } else if (shortType == SHORT_TYPE_COMPOSER_1 || shortType == SHORT_TYPE_COMPOSER_2) {
                    parseCommentAttribute = parseTextAttribute(type, "TCOM", ilst);
                    ilst.setPosition(endPosition);
                    return parseCommentAttribute;
                } else if (shortType == SHORT_TYPE_YEAR) {
                    parseCommentAttribute = parseTextAttribute(type, "TDRC", ilst);
                    ilst.setPosition(endPosition);
                    return parseCommentAttribute;
                } else if (shortType == SHORT_TYPE_ARTIST) {
                    parseCommentAttribute = parseTextAttribute(type, "TPE1", ilst);
                    ilst.setPosition(endPosition);
                    return parseCommentAttribute;
                } else if (shortType == SHORT_TYPE_ENCODER) {
                    parseCommentAttribute = parseTextAttribute(type, "TSSE", ilst);
                    ilst.setPosition(endPosition);
                    return parseCommentAttribute;
                } else if (shortType == SHORT_TYPE_ALBUM) {
                    parseCommentAttribute = parseTextAttribute(type, "TALB", ilst);
                    ilst.setPosition(endPosition);
                    return parseCommentAttribute;
                } else if (shortType == SHORT_TYPE_LYRICS) {
                    parseCommentAttribute = parseTextAttribute(type, "USLT", ilst);
                    ilst.setPosition(endPosition);
                    return parseCommentAttribute;
                } else if (shortType == SHORT_TYPE_GENRE) {
                    parseCommentAttribute = parseTextAttribute(type, "TCON", ilst);
                    ilst.setPosition(endPosition);
                    return parseCommentAttribute;
                } else if (shortType == TYPE_GROUPING) {
                    parseCommentAttribute = parseTextAttribute(type, "TIT1", ilst);
                    ilst.setPosition(endPosition);
                    return parseCommentAttribute;
                }
            } finally {
                ilst.setPosition(endPosition);
            }
        } else if (type == TYPE_GENRE) {
            parseCommentAttribute = parseStandardGenreAttribute(ilst);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        } else if (type == TYPE_DISK_NUMBER) {
            parseCommentAttribute = parseIndexAndCountAttribute(type, "TPOS", ilst);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        } else if (type == TYPE_TRACK_NUMBER) {
            parseCommentAttribute = parseIndexAndCountAttribute(type, "TRCK", ilst);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        } else if (type == TYPE_TEMPO) {
            parseCommentAttribute = parseUint8Attribute(type, "TBPM", ilst, true, false);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        } else if (type == TYPE_COMPILATION) {
            parseCommentAttribute = parseUint8Attribute(type, "TCMP", ilst, true, true);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        } else if (type == TYPE_COVER_ART) {
            parseCommentAttribute = parseCoverArt(ilst);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        } else if (type == TYPE_ALBUM_ARTIST) {
            parseCommentAttribute = parseTextAttribute(type, "TPE2", ilst);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        } else if (type == TYPE_SORT_TRACK_NAME) {
            parseCommentAttribute = parseTextAttribute(type, "TSOT", ilst);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        } else if (type == TYPE_SORT_ALBUM) {
            parseCommentAttribute = parseTextAttribute(type, "TSO2", ilst);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        } else if (type == TYPE_SORT_ARTIST) {
            parseCommentAttribute = parseTextAttribute(type, "TSOA", ilst);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        } else if (type == TYPE_SORT_ALBUM_ARTIST) {
            parseCommentAttribute = parseTextAttribute(type, "TSOP", ilst);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        } else if (type == TYPE_SORT_COMPOSER) {
            parseCommentAttribute = parseTextAttribute(type, "TSOC", ilst);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        } else if (type == TYPE_RATING) {
            parseCommentAttribute = parseUint8Attribute(type, "ITUNESADVISORY", ilst, false, false);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        } else if (type == TYPE_GAPLESS_ALBUM) {
            parseCommentAttribute = parseUint8Attribute(type, "ITUNESGAPLESS", ilst, false, true);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        } else if (type == TYPE_TV_SORT_SHOW) {
            parseCommentAttribute = parseTextAttribute(type, "TVSHOWSORT", ilst);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        } else if (type == TYPE_TV_SHOW) {
            parseCommentAttribute = parseTextAttribute(type, "TVSHOW", ilst);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        } else if (type == TYPE_INTERNAL) {
            parseCommentAttribute = parseInternalAttribute(ilst, endPosition);
            ilst.setPosition(endPosition);
            return parseCommentAttribute;
        }
        Log.d(TAG, "Skipped unknown metadata entry: " + Atom.getAtomTypeString(type));
        ilst.setPosition(endPosition);
        return null;
    }

    private static TextInformationFrame parseTextAttribute(int type, String id, ParsableByteArray data) {
        int atomSize = data.readInt();
        if (data.readInt() == Atom.TYPE_data) {
            data.skipBytes(8);
            return new TextInformationFrame(id, null, data.readNullTerminatedString(atomSize - 16));
        }
        Log.w(TAG, "Failed to parse text attribute: " + Atom.getAtomTypeString(type));
        return null;
    }

    private static CommentFrame parseCommentAttribute(int type, ParsableByteArray data) {
        int atomSize = data.readInt();
        if (data.readInt() == Atom.TYPE_data) {
            data.skipBytes(8);
            String value = data.readNullTerminatedString(atomSize - 16);
            return new CommentFrame(LANGUAGE_UNDEFINED, value, value);
        }
        Log.w(TAG, "Failed to parse comment attribute: " + Atom.getAtomTypeString(type));
        return null;
    }

    private static Id3Frame parseUint8Attribute(int type, String id, ParsableByteArray data, boolean isTextInformationFrame, boolean isBoolean) {
        int value = parseUint8AttributeValue(data);
        if (isBoolean) {
            value = Math.min(1, value);
        }
        if (value < 0) {
            Log.w(TAG, "Failed to parse uint8 attribute: " + Atom.getAtomTypeString(type));
            return null;
        } else if (isTextInformationFrame) {
            return new TextInformationFrame(id, null, Integer.toString(value));
        } else {
            return new CommentFrame(LANGUAGE_UNDEFINED, id, Integer.toString(value));
        }
    }

    private static TextInformationFrame parseIndexAndCountAttribute(int type, String attributeName, ParsableByteArray data) {
        int atomSize = data.readInt();
        if (data.readInt() == Atom.TYPE_data && atomSize >= 22) {
            data.skipBytes(10);
            int index = data.readUnsignedShort();
            if (index > 0) {
                String value = "" + index;
                int count = data.readUnsignedShort();
                if (count > 0) {
                    value = value + "/" + count;
                }
                return new TextInformationFrame(attributeName, null, value);
            }
        }
        Log.w(TAG, "Failed to parse index/count attribute: " + Atom.getAtomTypeString(type));
        return null;
    }

    private static TextInformationFrame parseStandardGenreAttribute(ParsableByteArray data) {
        String genreString;
        int genreCode = parseUint8AttributeValue(data);
        if (genreCode <= 0 || genreCode > STANDARD_GENRES.length) {
            genreString = null;
        } else {
            genreString = STANDARD_GENRES[genreCode - 1];
        }
        if (genreString != null) {
            return new TextInformationFrame("TCON", null, genreString);
        }
        Log.w(TAG, "Failed to parse standard genre code");
        return null;
    }

    private static ApicFrame parseCoverArt(ParsableByteArray data) {
        int atomSize = data.readInt();
        if (data.readInt() == Atom.TYPE_data) {
            int flags = Atom.parseFullAtomFlags(data.readInt());
            String mimeType = flags == 13 ? "image/jpeg" : flags == 14 ? "image/png" : null;
            if (mimeType == null) {
                Log.w(TAG, "Unrecognized cover art flags: " + flags);
                return null;
            }
            data.skipBytes(4);
            byte[] pictureData = new byte[(atomSize - 16)];
            data.readBytes(pictureData, 0, pictureData.length);
            return new ApicFrame(mimeType, null, 3, pictureData);
        }
        Log.w(TAG, "Failed to parse cover art attribute");
        return null;
    }

    private static Id3Frame parseInternalAttribute(ParsableByteArray data, int endPosition) {
        String domain = null;
        String name = null;
        int dataAtomPosition = -1;
        int dataAtomSize = -1;
        while (data.getPosition() < endPosition) {
            int atomPosition = data.getPosition();
            int atomSize = data.readInt();
            int atomType = data.readInt();
            data.skipBytes(4);
            if (atomType == Atom.TYPE_mean) {
                domain = data.readNullTerminatedString(atomSize - 12);
            } else if (atomType == Atom.TYPE_name) {
                name = data.readNullTerminatedString(atomSize - 12);
            } else {
                if (atomType == Atom.TYPE_data) {
                    dataAtomPosition = atomPosition;
                    dataAtomSize = atomSize;
                }
                data.skipBytes(atomSize - 12);
            }
        }
        if (!"com.apple.iTunes".equals(domain) || !"iTunSMPB".equals(name) || dataAtomPosition == -1) {
            return null;
        }
        data.setPosition(dataAtomPosition);
        data.skipBytes(16);
        return new CommentFrame(LANGUAGE_UNDEFINED, name, data.readNullTerminatedString(dataAtomSize - 16));
    }

    private static int parseUint8AttributeValue(ParsableByteArray data) {
        data.skipBytes(4);
        if (data.readInt() == Atom.TYPE_data) {
            data.skipBytes(8);
            return data.readUnsignedByte();
        }
        Log.w(TAG, "Failed to parse uint8 attribute value");
        return -1;
    }
}
