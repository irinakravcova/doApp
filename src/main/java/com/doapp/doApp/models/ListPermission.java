package com.doapp.doApp.models;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "list_permission")
public class ListPermission {
    @EmbeddedId
    ListPermissionId listPermissionId;
}
