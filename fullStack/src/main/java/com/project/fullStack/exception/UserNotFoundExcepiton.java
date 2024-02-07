package com.project.fullStack.exception;

public class UserNotFoundExcepiton extends RuntimeException{

    public UserNotFoundExcepiton(long id) {
        super("Could not Found data with the ID: "+id);
    }

}
