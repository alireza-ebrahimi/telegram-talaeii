package com.googlecode.mp4parser.authoring.builder;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;

public interface Mp4Builder {
    Container build(Movie movie);
}
