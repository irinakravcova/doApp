package com.doapp.doApp.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private Integer ownerUserId;
    private String name;
}
