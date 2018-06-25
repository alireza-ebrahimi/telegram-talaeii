package com.persianswitch.sdk.base.db.phoenix.query;

import android.support.annotation.NonNull;
import com.persianswitch.sdk.base.utils.PreConditions;
import java.util.ArrayList;
import java.util.List;

public final class Project implements SQLStatement {
    private final List<ColumnSelect> mProjectList = new ArrayList();

    public Project(ColumnSelect aColumnSelect) {
        PreConditions.checkNotNull(aColumnSelect, "project can not be null");
        this.mProjectList.add(aColumnSelect);
    }

    public Project and(ColumnSelect aColumnSelect) {
        this.mProjectList.add(aColumnSelect);
        return this;
    }

    public static Project only(ColumnSelect columnSelect) {
        return new Project(columnSelect);
    }

    @NonNull
    public String toSQL() {
        StringBuilder projectBuilder = new StringBuilder(20);
        for (int i = 0; i < this.mProjectList.size(); i++) {
            projectBuilder.append(((ColumnSelect) this.mProjectList.get(i)).toSQL());
            if (i < this.mProjectList.size() - 1) {
                projectBuilder.append(", ");
            }
        }
        return projectBuilder.toString();
    }
}
