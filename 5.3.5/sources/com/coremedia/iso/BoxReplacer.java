package com.coremedia.iso;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.util.Path;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BoxReplacer {
    static final /* synthetic */ boolean $assertionsDisabled = (!BoxReplacer.class.desiredAssertionStatus());

    public static void replace(Map<String, Box> replacements, File file) throws IOException {
        Container isoFile = new IsoFile(new FileDataSourceImpl(new RandomAccessFile(file, "r").getChannel()));
        Map<String, Box> replacementSanitised = new HashMap();
        Map<String, Long> positions = new HashMap();
        for (Entry<String, Box> e : replacements.entrySet()) {
            Box b = Path.getPath(isoFile, (String) e.getKey());
            replacementSanitised.put(Path.createPath(b), (Box) e.getValue());
            positions.put(Path.createPath(b), Long.valueOf(b.getOffset()));
            if (!$assertionsDisabled && b.getSize() != ((Box) e.getValue()).getSize()) {
                throw new AssertionError();
            }
        }
        isoFile.close();
        FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();
        for (String path : replacementSanitised.keySet()) {
            b = (Box) replacementSanitised.get(path);
            fileChannel.position(((Long) positions.get(path)).longValue());
            b.getBox(fileChannel);
        }
        fileChannel.close();
    }
}
