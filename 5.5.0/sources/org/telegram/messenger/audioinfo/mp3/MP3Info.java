package org.telegram.messenger.audioinfo.mp3;

import java.io.EOFException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.messenger.audioinfo.AudioInfo;
import org.telegram.messenger.audioinfo.mp3.MP3Frame.Header;

public class MP3Info extends AudioInfo {
    static final Logger LOGGER = Logger.getLogger(MP3Info.class.getName());

    interface StopReadCondition {
        boolean stopRead(MP3Input mP3Input);
    }

    public MP3Info(InputStream inputStream, long j) {
        this(inputStream, j, Level.FINEST);
    }

    public MP3Info(InputStream inputStream, final long j, Level level) {
        this.brand = "MP3";
        this.version = "0";
        InputStream mP3Input = new MP3Input(inputStream);
        if (ID3v2Info.isID3v2StartPosition(mP3Input)) {
            ID3v2Info iD3v2Info = new ID3v2Info(mP3Input, level);
            this.album = iD3v2Info.getAlbum();
            this.albumArtist = iD3v2Info.getAlbumArtist();
            this.artist = iD3v2Info.getArtist();
            this.comment = iD3v2Info.getComment();
            this.cover = iD3v2Info.getCover();
            this.smallCover = iD3v2Info.getSmallCover();
            this.compilation = iD3v2Info.isCompilation();
            this.composer = iD3v2Info.getComposer();
            this.copyright = iD3v2Info.getCopyright();
            this.disc = iD3v2Info.getDisc();
            this.discs = iD3v2Info.getDiscs();
            this.duration = iD3v2Info.getDuration();
            this.genre = iD3v2Info.getGenre();
            this.grouping = iD3v2Info.getGrouping();
            this.lyrics = iD3v2Info.getLyrics();
            this.title = iD3v2Info.getTitle();
            this.track = iD3v2Info.getTrack();
            this.tracks = iD3v2Info.getTracks();
            this.year = iD3v2Info.getYear();
        }
        if (this.duration <= 0 || this.duration >= 3600000) {
            try {
                this.duration = calculateDuration(mP3Input, j, new StopReadCondition() {
                    final long stopPosition = (j - 128);

                    public boolean stopRead(MP3Input mP3Input) {
                        return mP3Input.getPosition() == this.stopPosition && ID3v1Info.isID3v1StartPosition(mP3Input);
                    }
                });
            } catch (Throwable e) {
                if (LOGGER.isLoggable(level)) {
                    LOGGER.log(level, "Could not determine MP3 duration", e);
                }
            }
        }
        if ((this.title == null || this.album == null || this.artist == null) && mP3Input.getPosition() <= j - 128) {
            mP3Input.skipFully((j - 128) - mP3Input.getPosition());
            if (ID3v1Info.isID3v1StartPosition(inputStream)) {
                ID3v1Info iD3v1Info = new ID3v1Info(inputStream);
                if (this.album == null) {
                    this.album = iD3v1Info.getAlbum();
                }
                if (this.artist == null) {
                    this.artist = iD3v1Info.getArtist();
                }
                if (this.comment == null) {
                    this.comment = iD3v1Info.getComment();
                }
                if (this.genre == null) {
                    this.genre = iD3v1Info.getGenre();
                }
                if (this.title == null) {
                    this.title = iD3v1Info.getTitle();
                }
                if (this.track == (short) 0) {
                    this.track = iD3v1Info.getTrack();
                }
                if (this.year == (short) 0) {
                    this.year = iD3v1Info.getYear();
                }
            }
        }
    }

    long calculateDuration(MP3Input mP3Input, long j, StopReadCondition stopReadCondition) {
        MP3Frame readFirstFrame = readFirstFrame(mP3Input, stopReadCondition);
        if (readFirstFrame != null) {
            int numberOfFrames = readFirstFrame.getNumberOfFrames();
            if (numberOfFrames > 0) {
                return readFirstFrame.getHeader().getTotalDuration((long) (numberOfFrames * readFirstFrame.getSize()));
            }
            int i = 1;
            long position = mP3Input.getPosition() - ((long) readFirstFrame.getSize());
            long size = (long) readFirstFrame.getSize();
            int bitrate = readFirstFrame.getHeader().getBitrate();
            long j2 = (long) bitrate;
            Object obj = null;
            int duration = 10000 / readFirstFrame.getHeader().getDuration();
            while (true) {
                if (i == duration && r4 == null && j > 0) {
                    return readFirstFrame.getHeader().getTotalDuration(j - position);
                }
                readFirstFrame = readNextFrame(mP3Input, stopReadCondition, readFirstFrame);
                if (readFirstFrame == null) {
                    return ((((long) i) * (size * 1000)) * 8) / j2;
                }
                int bitrate2 = readFirstFrame.getHeader().getBitrate();
                if (bitrate2 != bitrate) {
                    obj = 1;
                }
                j2 += (long) bitrate2;
                size += (long) readFirstFrame.getSize();
                i++;
            }
        } else {
            throw new MP3Exception("No audio frame");
        }
    }

    MP3Frame readFirstFrame(MP3Input mP3Input, StopReadCondition stopReadCondition) {
        int i = 0;
        int read = stopReadCondition.stopRead(mP3Input) ? -1 : mP3Input.read();
        while (read != -1) {
            if (i == 255 && (read & 224) == 224) {
                mP3Input.mark(2);
                int read2 = stopReadCondition.stopRead(mP3Input) ? -1 : mP3Input.read();
                if (read2 == -1) {
                    break;
                }
                int read3 = stopReadCondition.stopRead(mP3Input) ? -1 : mP3Input.read();
                if (read3 == -1) {
                    break;
                }
                Header header;
                try {
                    header = new Header(read, read2, read3);
                } catch (MP3Exception e) {
                    header = null;
                }
                if (header != null) {
                    mP3Input.reset();
                    mP3Input.mark(header.getFrameSize() + 2);
                    byte[] bArr = new byte[header.getFrameSize()];
                    bArr[0] = (byte) -1;
                    bArr[1] = (byte) read;
                    try {
                        mP3Input.readFully(bArr, 2, bArr.length - 2);
                        MP3Frame mP3Frame = new MP3Frame(header, bArr);
                        if (!mP3Frame.isChecksumError()) {
                            i = stopReadCondition.stopRead(mP3Input) ? -1 : mP3Input.read();
                            read2 = stopReadCondition.stopRead(mP3Input) ? -1 : mP3Input.read();
                            if (i == -1 || read2 == -1) {
                                return mP3Frame;
                            }
                            if (i == 255 && (read2 & 254) == (read & 254)) {
                                i = stopReadCondition.stopRead(mP3Input) ? -1 : mP3Input.read();
                                read3 = stopReadCondition.stopRead(mP3Input) ? -1 : mP3Input.read();
                                if (i == -1 || read3 == -1) {
                                    return mP3Frame;
                                }
                                try {
                                    if (new Header(read2, i, read3).isCompatible(header)) {
                                        mP3Input.reset();
                                        mP3Input.skipFully((long) (bArr.length - 2));
                                        return mP3Frame;
                                    }
                                } catch (MP3Exception e2) {
                                }
                            }
                        }
                    } catch (EOFException e3) {
                    }
                }
                mP3Input.reset();
            }
            i = read;
            read = stopReadCondition.stopRead(mP3Input) ? -1 : mP3Input.read();
        }
        return null;
    }

    MP3Frame readNextFrame(MP3Input mP3Input, StopReadCondition stopReadCondition, MP3Frame mP3Frame) {
        Header header = mP3Frame.getHeader();
        mP3Input.mark(4);
        int read = stopReadCondition.stopRead(mP3Input) ? -1 : mP3Input.read();
        int read2 = stopReadCondition.stopRead(mP3Input) ? -1 : mP3Input.read();
        if (read == -1 || read2 == -1) {
            return null;
        }
        if (read == 255 && (read2 & 224) == 224) {
            int read3 = stopReadCondition.stopRead(mP3Input) ? -1 : mP3Input.read();
            int read4 = stopReadCondition.stopRead(mP3Input) ? -1 : mP3Input.read();
            if (read3 == -1 || read4 == -1) {
                return null;
            }
            Header header2;
            try {
                header2 = new Header(read2, read3, read4);
            } catch (MP3Exception e) {
                header2 = null;
            }
            if (header2 != null && header2.isCompatible(header)) {
                byte[] bArr = new byte[header2.getFrameSize()];
                bArr[0] = (byte) read;
                bArr[1] = (byte) read2;
                bArr[2] = (byte) read3;
                bArr[3] = (byte) read4;
                try {
                    mP3Input.readFully(bArr, 4, bArr.length - 4);
                    return new MP3Frame(header2, bArr);
                } catch (EOFException e2) {
                    return null;
                }
            }
        }
        mP3Input.reset();
        return null;
    }
}
