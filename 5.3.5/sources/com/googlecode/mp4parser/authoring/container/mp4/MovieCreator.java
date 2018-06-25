package com.googlecode.mp4parser.authoring.container.mp4;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.SchemeTypeBox;
import com.coremedia.iso.boxes.TrackBox;
import com.googlecode.mp4parser.AbstractContainerBox;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.authoring.CencMp4TrackImplImpl;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Mp4TrackImpl;
import com.googlecode.mp4parser.util.Path;
import java.io.File;
import java.io.IOException;
import org.telegram.messenger.exoplayer2.C0907C;

public class MovieCreator {
    public static Movie build(String file) throws IOException {
        return build(new FileDataSourceImpl(new File(file)));
    }

    public static Movie build(DataSource channel) throws IOException {
        IsoFile isoFile = new IsoFile(channel);
        Movie m = new Movie();
        for (AbstractContainerBox trackBox : isoFile.getMovieBox().getBoxes(TrackBox.class)) {
            SchemeTypeBox schm = (SchemeTypeBox) Path.getPath(trackBox, "mdia[0]/minf[0]/stbl[0]/stsd[0]/enc.[0]/sinf[0]/schm[0]");
            if (schm == null || !(schm.getSchemeType().equals(C0907C.CENC_TYPE_cenc) || schm.getSchemeType().equals(C0907C.CENC_TYPE_cbc1))) {
                m.addTrack(new Mp4TrackImpl(channel.toString() + "[" + trackBox.getTrackHeaderBox().getTrackId() + "]", trackBox, new IsoFile[0]));
            } else {
                m.addTrack(new CencMp4TrackImplImpl(channel.toString() + "[" + trackBox.getTrackHeaderBox().getTrackId() + "]", trackBox, new IsoFile[0]));
            }
        }
        m.setMatrix(isoFile.getMovieBox().getMovieHeaderBox().getMatrix());
        return m;
    }
}
