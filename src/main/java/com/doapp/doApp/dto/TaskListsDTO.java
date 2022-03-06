package com.doapp.doApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class TaskListsDTO {

    List<TaskListDTO> list = new ArrayList<>();

}
