package org.telegram.messenger.exoplayer2.source.dash.manifest;

import java.util.Collections;
import java.util.List;

public class AdaptationSet {
    public static final int ID_UNSET = -1;
    public final List<Descriptor> accessibilityDescriptors;
    public final int id;
    public final List<Representation> representations;
    public final List<Descriptor> supplementalProperties;
    public final int type;

    public AdaptationSet(int i, int i2, List<Representation> list, List<Descriptor> list2, List<Descriptor> list3) {
        this.id = i;
        this.type = i2;
        this.representations = Collections.unmodifiableList(list);
        this.accessibilityDescriptors = list2 == null ? Collections.emptyList() : Collections.unmodifiableList(list2);
        this.supplementalProperties = list3 == null ? Collections.emptyList() : Collections.unmodifiableList(list3);
    }
}
