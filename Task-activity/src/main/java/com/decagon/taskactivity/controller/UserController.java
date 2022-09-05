package com.decagon.taskactivity.controller;

import com.decagon.taskactivity.dto.TaskDto;
import com.decagon.taskactivity.dto.UserDto;
import com.decagon.taskactivity.enterprise.UserEnterprise;
import com.decagon.taskactivity.model.Task;
import com.decagon.taskactivity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private  final UserEnterprise userEnterprise;

    @Autowired
    public  UserController(UserEnterprise userEnterprise){
        this.userEnterprise=userEnterprise;
    }

    @GetMapping("/dashboard")
    public  String index (Model model, HttpSession session) {
        List<Task> allTasks = userEnterprise.allTaskByUserId((Integer) session.getAttribute("id"));
        model.addAttribute("newTaskDetails" , new TaskDto("pending", (Integer)session.getAttribute("id")));
        model.addAttribute("tasks" , allTasks);
        return "dashboard";
    }

    @GetMapping(value = "/login")
    public String displayLoginPage(Model model){
        model.addAttribute("userDetails" , new UserDto());
        return "login";
}

    @GetMapping(value = "/logout")
    public String logout (HttpSession session){
        session.removeAttribute("id");
        session.removeAttribute("email");
        session.removeAttribute("name");
        session.invalidate();
        return "redirect:/user/login";
    }
    @PostMapping(value = "/loginUser")
    public String loginUser(@RequestParam String email , @RequestParam String password, HttpSession session , Model model){
        String message= userEnterprise.loginUser(email, password);
    System.out.println(email + password);
        if (message.equals("Success")){
            User user =userEnterprise.getUserByEmail(email);
            session.setAttribute("email" , user.getEmail());
            session.setAttribute("id" , user.getId());
            session.setAttribute("name" , user.getName());
            return "redirect:/user/dashboard";
        }else{
            model.addAttribute("errorMessage" , message);
            return "redirect:/user/login";
        }
}

@GetMapping(value = "/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("userRegistrationDetails", new UserDto());
        return "register";
}
@PostMapping("/userRegistration")
    public  String registration(@ModelAttribute UserDto userDto){
        User registeredUser = userEnterprise.registerUser(userDto);
        if (registeredUser != null){
            return "redirect:/user/login";
    }else {
            return "redirect:/user/register";
        }
}

@GetMapping(value = "/task/{status}")
    public String taskByStatus(@PathVariable(name = "status") String status , Model model , HttpSession session){
        int user_id = (int) session.getAttribute("id");
        List<Task> listOfTaskByStatus = userEnterprise.findAllByUser_idAndStatus(user_id , status);
        model.addAttribute("tasksByStatus" , listOfTaskByStatus);
        return "task-by-status";
}
@PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable(name = "id") Integer id){
       userEnterprise.deleteById(id);
       return "redirect:/user/dashboard";

}
@GetMapping(value =  "/editPage/{id}")
    public String showEditPage(@PathVariable(name = "id") Integer id, Model model){
    Task task = userEnterprise.getTaskById(id);
    model.addAttribute("singleTask" , task);
    model.addAttribute("taskBody",new TaskDto());
    return "editTask";
}
    @PostMapping(value = "/edit/{id}")
    public String editTask(@PathVariable( name = "id") String id , @ModelAttribute TaskDto taskDto){
        int taskId = Integer.parseInt(id);
        userEnterprise.updateTitleAndDescription(taskDto , taskId);
        return "redirect:/user/dashboard";
    }
@PostMapping(value = "/addNewTask")
    public  String addTask(Model model){
        model.addAttribute("newTask" , new TaskDto());
        return "addTask";
}
@PostMapping(value = "/addTask")
    public String  CreateTask(@ModelAttribute TaskDto taskDto){
    System.out.println(taskDto.getStatus());
        userEnterprise.createTask(taskDto);
        return "redirect:/user/dashboard";
}
    @PostMapping(value = "/changeStatus/{id}")
    public String moveToInProgress(@PathVariable(name = "id")   String id, @RequestParam String status ){
        int taskId = Integer.parseInt(id);
        userEnterprise.updateTaskStatus(status, taskId);
        return "redirect:/user/dashboard";
    }

    @PostMapping(value = "/complete/{id}")
    public String complete(@PathVariable(name = "id")   String id){
        int taskId = Integer.parseInt(id);
        userEnterprise.markTaskCompleted(taskId);
        return "redirect:/user/dashboard";
    }

    @GetMapping(value = "/singleTask/{id}")
    public String getSingleTask(@PathVariable(name = "id") String id , Model model){
        Task task = userEnterprise.getTaskById(Integer.parseInt(id));
        model.addAttribute("task" , task);
        return "singleTask";
    }

}
