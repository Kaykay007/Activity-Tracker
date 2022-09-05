package com.decagon.taskactivity.enterpriseImpl;

import com.decagon.taskactivity.dto.TaskDto;
import com.decagon.taskactivity.dto.UserDto;
import com.decagon.taskactivity.enterprise.UserEnterprise;
import com.decagon.taskactivity.exception.TaskNotFoundException;
import com.decagon.taskactivity.exception.UserNotFoundException;
import com.decagon.taskactivity.model.Task;
import com.decagon.taskactivity.model.User;
import com.decagon.taskactivity.repository.TaskRepository;
import com.decagon.taskactivity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class UserEnterpriseImpl implements UserEnterprise {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public UserEnterpriseImpl(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public User registerUser (UserDto userDto){
        User user=new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return  userRepository.save(user);
    }

    @Override
    public String loginUser (String email, String password){
        String message = "";
        User user= getUserByEmail(email);
        System.out.println(user.getEmail() + user.getPassword());
        if (user.getPassword().equals(password)){
            message="Success";
        }else {
            message = "incorrect password";
        }
        return message;
    }

    @Override
    public Task createTask (TaskDto taskDto){
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        System.out.println(taskDto.getStatus());
        task.setUser(userRepository.findById(taskDto.getUser_id()).get());
        return taskRepository.save(task);
    }
    @Override
    public Task updateTitleAndDescription(TaskDto taskDto , int id){
        Task task = getTaskById(id);
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        System.out.println(task);
        return taskRepository.save(task);
    }
    @Override
    public List<Task> viewAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> allTaskByUserId(int id){
        return  taskRepository.findAllByUser_Id(id);
    }

    @Override
    public List<Task> viewAllTaskByStatus(String status) {
        return taskRepository.listOfTasksByStatus(status);
    }

    @Override
    public boolean deleteById(int id) {
        taskRepository.deleteById(id);
        return  true;
    }

    @Override
    public int updateTaskStatus(String status, int id){
        return taskRepository.updateTaskByIdAndStatus(status , id);
    }

    @Override
    public Task markTaskCompleted(int id){
        Task task = getTaskById(id);
        if (task.getStatus().equals("in progress")){
            task.setCompletedAt(LocalDateTime.now());
            task.setStatus("completed");
        }
        return  taskRepository.save(task);
    }
    @Override
    public List<Task> findAllByUser_idAndStatus(int user_id , String status){
        return taskRepository.findAllByUser_idAndStatus(user_id , status);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email + " " +"not found in the database"));
    }

    @Override
    public Task getTaskById(int id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException( "Task not found in the database"));
    }


}

