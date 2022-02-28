package com.doapp.doApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class TaskListDTO {
    private Integer listId;
    private String name;
}
