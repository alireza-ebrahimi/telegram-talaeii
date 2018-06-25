package org.telegram.customization.fetch.callback;

import android.support.annotation.NonNull;
import org.telegram.customization.fetch.Fetch;

public interface FetchTask {
    void onProcess(@NonNull Fetch fetch);
}
