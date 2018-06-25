package com.coremedia.iso.boxes;

public interface FullBox extends Box {
    int getFlags();

    int getVersion();

    void setFlags(int i);

    void setVersion(int i);
}
