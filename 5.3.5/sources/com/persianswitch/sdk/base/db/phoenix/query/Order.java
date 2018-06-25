package com.persianswitch.sdk.base.db.phoenix.query;

import android.support.annotation.NonNull;
import com.persianswitch.sdk.base.db.phoenix.Column;
import com.persianswitch.sdk.base.utils.PreConditions;
import java.util.ArrayList;
import java.util.List;

public final class Order implements SQLStatement {
    private List<OrderCondition> mOrderList = new ArrayList();

    Order(OrderCondition firstOrderCondition) {
        PreConditions.checkNotNull(firstOrderCondition, "order can not be null");
        this.mOrderList.add(firstOrderCondition);
    }

    public static Order by(Column column) {
        return new Order(new OrderCondition(column));
    }

    public static Order by(Column column, boolean asc) {
        return new Order(new OrderCondition(column, asc));
    }

    public Order and(OrderCondition anotherCondition) {
        this.mOrderList.add(anotherCondition);
        return this;
    }

    @NonNull
    public String toSQL() {
        StringBuilder orderBuilder = new StringBuilder(30);
        orderBuilder.append("ORDER BY ");
        for (int i = 0; i < this.mOrderList.size(); i++) {
            orderBuilder.append(((OrderCondition) this.mOrderList.get(i)).toSQL());
            if (i < this.mOrderList.size() - 1) {
                orderBuilder.append(", ");
            }
        }
        return orderBuilder.toString();
    }
}
