package org.ocpsoft.prettytime;

public interface TimeFormat {
    String decorate(Duration duration, String str);

    String decorateUnrounded(Duration duration, String str);

    String format(Duration duration);

    String formatUnrounded(Duration duration);
}
