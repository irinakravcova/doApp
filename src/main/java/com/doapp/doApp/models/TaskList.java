package com.doapp.doApp.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "task_list")
public class TaskList {
    @Id
    @GeneratedValue
    private Integer listId;
    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private User owner;
    @OneToMany
    private List<ListPermission> listPermission;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskList taskList = (TaskList) o;

        return listId.equals(taskList.listId);
    }

    @Override
    public int hashCode() {
        return listId.hashCode();
    }
}
