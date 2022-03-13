package com.doapp.doApp.models;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class ListPermissionId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "list_id")
    private TaskList taskList;
    @ManyToOne
    @JoinColumn(name = "grantee_user_id")
    private User grantee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListPermissionId that = (ListPermissionId) o;

        if (!taskList.equals(that.taskList)) return false;
        return grantee.equals(that.grantee);
    }

    @Override
    public int hashCode() {
        int result = taskList.hashCode();
        result = 31 * result + grantee.hashCode();
        return result;
    }
}
