package com.doapp.doApp.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ListPermissionId implements Serializable {
    private Integer listId;
    private Integer granteeUserId;
}
