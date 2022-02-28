package com.doapp.doApp.models;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "task")
public class Task {
    @Id
    private Integer taskId;
    private Integer userId;
    private Integer listId;
    private String name;
    private Calendar dueDate;
    @Enumerated(EnumType.ORDINAL)
    @Embedded
    private Status status;
}
