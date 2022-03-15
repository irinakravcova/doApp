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
    @GeneratedValue
    private Integer taskId;
    @JoinColumn(name = "userId")
    @ManyToOne
    private User owner;
    @ManyToOne
    @JoinColumn(name = "listId")
    private TaskList list;
    private String name;
    private Calendar dueDate;
    private String status;
}
