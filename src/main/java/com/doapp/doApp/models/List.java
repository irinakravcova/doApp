package com.doapp.doApp.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "task_list")
public class List {
    @Id
    private Integer listId;
    private Integer userId;
    private String name;
}
