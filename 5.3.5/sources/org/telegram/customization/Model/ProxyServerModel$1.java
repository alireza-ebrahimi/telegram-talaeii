package org.telegram.customization.Model;

import java.util.Comparator;

class ProxyServerModel$1 implements Comparator {
    ProxyServerModel$1() {
    }

    public int compare(Object a, Object b) {
        if ((a instanceof ProxyServerModel) && (b instanceof ProxyServerModel)) {
            return (int) (((ProxyServerModel) b).getExpireDateSecs() - ((ProxyServerModel) a).getExpireDateSecs());
        }
        return 0;
    }
}
