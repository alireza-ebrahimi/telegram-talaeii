package com.coremedia.iso;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.DataSource;
import java.io.IOException;

public interface BoxParser {
    Box parseBox(DataSource dataSource, Container container) throws IOException;
}
