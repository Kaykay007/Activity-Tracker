package com.decagon.taskactivity.EnterpriseImpl;


import com.decagon.taskactivity.dto.TaskDto;
import com.decagon.taskactivity.dto.UserDto;
import com.decagon.taskactivity.enterpriseImpl.UserEnterpriseImpl;
import com.decagon.taskactivity.exception.UserNotFoundException;
import com.decagon.taskactivity.model.Task;
import com.decagon.taskactivity.model.User;
import com.decagon.taskactivity.repository.TaskRepository;
import com.decagon.taskactivity.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static java.util.Calendar.AUGUST;
import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserEnterpriseImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    UserEnterpriseImpl userEnterpriseImpl;

    private User user;
    private UserDto userDto;
    private TaskDto taskDto;
    private Task task;
    private LocalDateTime time;
    List<Task> taskList;
    @BeforeEach
    void setUp() {
        time = LocalDateTime.of(2022, Month.SEPTEMBER,2,6,30,40,50000);
        taskList = new ArrayList<>();
        taskList.add(task);
        this.user = new User(1, "jane" , "g@gmai.com" , "tasker" , taskList);
        this.task = new Task(1, "Write Code" , "Code till 7am" , "pending" , time , time , time , user);
        this.taskDto = new TaskDto("Write Code" , "Code till 7am" , "pending" , 1);
        this.userDto = new UserDto("Jane" , "g@gmai.com", "");
        when(userRepository.save(user)).thenReturn(user);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskRepository.findAll()).thenReturn(taskList);
        when(taskRepository.listOfTasksByStatus("pending")).thenReturn(taskList);
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        when(taskRepository.findById(1)).thenReturn(Optional.ofNullable(task));
        when(userRepository.findUserByEmail("g@gmai.com.com")).thenReturn(Optional.of(user));
        when(taskRepository.updateTaskByIdAndStatus("ongoing" , 1)).thenReturn(1);

    }

    @Test
    void registerUser() {
        when(userEnterpriseImpl.registerUser(userDto)).thenReturn(user);
        var actual = userEnterpriseImpl.registerUser(userDto);
        var expected = user;
        assertEquals( expected , actual );
    }

    @Test
    void loginUser_Successfull() {
        String message = "Success";
        assertEquals(message , userEnterpriseImpl.loginUser("hameed.korede.hk@gmail.com" , "tasker"));
    }

    @Test
    void loginUser_Unsuccessfull() {
        String message = "incorrect password";
        assertEquals(message , userEnterpriseImpl.loginUser("hameed.korede.hk@gmail.com" , "tasker"));
    }


    @Test
    void createTask() {
        when(userEnterpriseImpl.createTask(taskDto)).thenReturn(task);
        assertEquals(task , userEnterpriseImpl.createTask(taskDto));
    }

    @Test
    void updateTitleAndDescription() {
        assertEquals(task , userEnterpriseImpl.updateTitleAndDescription(taskDto , 1));
    }

    @Test
    void viewAllTasks() {
        assertEquals(1 , userEnterpriseImpl.viewAllTasks().size());
    }

    @Test
    void viewAllTaskByStatus() {

        assertEquals(taskList , userEnterpriseImpl.viewAllTaskByStatus("pending"));
    }

    @Test
    void deleteById() {
        userEnterpriseImpl.deleteById(1);
        verify(taskRepository).deleteById(any());
    }



    @Test
    void getUserByEmail() {
        assertEquals(user , userEnterpriseImpl.getUserByEmail("g@gmai.com"));
    }

    @Test
    void getUserByEmail_ThrowsUserNotFoundException()  throws UserNotFoundException {
        assertThrows( UserNotFoundException.class, ()->  userEnterpriseImpl.getUserByEmail("hameed.korede.hk@gmail.com"));
    }

    @Test
    void getTaskById() {
        assertEquals(task, userEnterpriseImpl.getTaskById(1));
    }
}
