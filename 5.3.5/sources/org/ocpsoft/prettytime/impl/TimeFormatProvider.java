package org.ocpsoft.prettytime.impl;

import org.ocpsoft.prettytime.TimeFormat;
import org.ocpsoft.prettytime.TimeUnit;

public interface TimeFormatProvider {
    TimeFormat getFormatFor(TimeUnit timeUnit);
}
