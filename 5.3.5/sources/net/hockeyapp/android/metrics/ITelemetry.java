package net.hockeyapp.android.metrics;

import java.util.Map;
import net.hockeyapp.android.metrics.model.Domain;

public abstract class ITelemetry extends Domain {
    public abstract String getBaseType();

    public abstract String getEnvelopeName();

    public abstract Map<String, String> getProperties();

    public abstract void setProperties(Map<String, String> map);

    public abstract void setVer(int i);
}
