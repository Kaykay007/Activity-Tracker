package com.decagon.taskactivity.enterprise;

import com.decagon.taskactivity.dto.TaskDto;
import com.decagon.taskactivity.dto.UserDto;
import com.decagon.taskactivity.model.Task;
import com.decagon.taskactivity.model.User;

import java.util.List;

public interface UserEnterprise {
    User registerUser(UserDto userDto);
    String loginUser (String email, String password);
    Task createTask(TaskDto taskDto);
    Task updateTitleAndDescription(TaskDto taskDto, int id);
    Task markTaskCompleted(int id);
    List<Task> allTaskByUserId(int id);
    Task getTaskById(int id);
    List<Task> viewAllTasks();
    int updateTaskStatus(String status, int id);
    List <Task> viewAllTaskByStatus (String status);
    List<Task> findAllByUser_idAndStatus(int user_id , String status);
    boolean deleteById(int id);
    User getUserByEmail(String email);

}
