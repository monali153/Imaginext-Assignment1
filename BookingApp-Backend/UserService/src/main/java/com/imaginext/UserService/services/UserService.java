package com.imaginext.UserService.services;

import com.imaginext.UserService.domain.User;
import com.imaginext.UserService.exception.UserAlreadyExistsException;
import com.imaginext.UserService.exception.UserNotFoundException;

public interface UserService {

    User addUser(User user) throws UserAlreadyExistsException;
    User getUserByEmailAndPassword(String email,String password) throws UserNotFoundException;

    User getUserById(String email);
}
