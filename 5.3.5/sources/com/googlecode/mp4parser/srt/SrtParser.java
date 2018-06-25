package com.googlecode.mp4parser.srt;

import com.googlecode.mp4parser.authoring.tracks.TextTrackImpl;
import com.googlecode.mp4parser.authoring.tracks.TextTrackImpl.Line;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class SrtParser {
    public static TextTrackImpl parse(InputStream is) throws IOException {
        LineNumberReader r = new LineNumberReader(new InputStreamReader(is, "UTF-8"));
        TextTrackImpl track = new TextTrackImpl();
        while (r.readLine() != null) {
            String timeString = r.readLine();
            String lineString = "";
            while (true) {
                String s = r.readLine();
                if (s == null || s.trim().equals("")) {
                    track.getSubs().add(new Line(parse(timeString.split("-->")[0]), parse(timeString.split("-->")[1]), lineString));
                } else {
                    lineString = new StringBuilder(String.valueOf(lineString)).append(s).append(LogCollector.LINE_SEPARATOR).toString();
                }
            }
            track.getSubs().add(new Line(parse(timeString.split("-->")[0]), parse(timeString.split("-->")[1]), lineString));
        }
        return track;
    }

    private static long parse(String in) {
        long hours = Long.parseLong(in.split(":")[0].trim());
        long minutes = Long.parseLong(in.split(":")[1].trim());
        long seconds = Long.parseLong(in.split(":")[2].split(",")[0].trim());
        return (((((60 * hours) * 60) * 1000) + ((60 * minutes) * 1000)) + (1000 * seconds)) + Long.parseLong(in.split(":")[2].split(",")[1].trim());
    }
}
