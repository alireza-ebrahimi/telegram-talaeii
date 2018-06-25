package org.telegram.customization.DataPool;

import java.util.Observable;

public class NewsPoolObservable extends Observable {
    public void dataChanged() {
        setChanged();
        notifyObservers("");
    }
}
