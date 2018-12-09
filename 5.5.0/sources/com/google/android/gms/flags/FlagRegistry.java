package com.google.android.gms.flags;

import android.content.Context;
import com.google.android.gms.flags.Flag.StringFlag;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FlagRegistry {
    private final Collection<Flag> zzacc = new ArrayList();
    private final Collection<StringFlag> zzacd = new ArrayList();
    private final Collection<StringFlag> zzace = new ArrayList();

    public static void initialize(Context context) {
        Singletons.flagValueProvider().initialize(context);
    }

    public List<String> getExperimentIdsFromClient() {
        List<String> arrayList = new ArrayList();
        for (StringFlag stringFlag : this.zzacd) {
            String str = (String) stringFlag.get();
            if (str != null) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public void registerClientExperimentId(StringFlag stringFlag) {
        this.zzacd.add(stringFlag);
    }

    public void registerFlag(Flag flag) {
        this.zzacc.add(flag);
    }

    public void registerServiceExperimentId(StringFlag stringFlag) {
        this.zzace.add(stringFlag);
    }

    public Collection<Flag> registeredFlags() {
        return Collections.unmodifiableCollection(this.zzacc);
    }

    public Collection<StringFlag> registeredServiceExperimentIdFlags() {
        return Collections.unmodifiableCollection(this.zzace);
    }
}
