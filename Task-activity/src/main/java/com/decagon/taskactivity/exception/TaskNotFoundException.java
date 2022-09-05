package com.decagon.taskactivity.exception;

public class TaskNotFoundException extends RuntimeException{
    public  TaskNotFoundException(String message){
        super(message);
    }
}
