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

    public AdaptationSet(int id, int type, List<Representation> representations, List<Descriptor> accessibilityDescriptors, List<Descriptor> supplementalProperties) {
        List emptyList;
        this.id = id;
        this.type = type;
        this.representations = Collections.unmodifiableList(representations);
        if (accessibilityDescriptors == null) {
            emptyList = Collections.emptyList();
        } else {
            emptyList = Collections.unmodifiableList(accessibilityDescriptors);
        }
        this.accessibilityDescriptors = emptyList;
        if (supplementalProperties == null) {
            emptyList = Collections.emptyList();
        } else {
            emptyList = Collections.unmodifiableList(supplementalProperties);
        }
        this.supplementalProperties = emptyList;
    }
}
