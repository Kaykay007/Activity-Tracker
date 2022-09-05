package com.decagon.taskactivity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private  String title;
    private String description;
    private String status;
    private int user_id;

    public TaskDto(String status, int user_id) {
        this.status = status;
        this.user_id = user_id;
    }
}
