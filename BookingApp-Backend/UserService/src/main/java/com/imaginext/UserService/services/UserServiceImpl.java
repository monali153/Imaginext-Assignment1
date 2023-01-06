package com.imaginext.UserService.services;

import com.imaginext.UserService.domain.User;
import com.imaginext.UserService.exception.UserAlreadyExistsException;
import com.imaginext.UserService.exception.UserNotFoundException;
import com.imaginext.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public User addUser(User user) throws UserAlreadyExistsException {
        if(userRepository.findById(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) throws UserNotFoundException {
        User user = userRepository.findByEmailAndPassword(email,password);
        System.out.println(email + password);
        if(user == null){
            throw  new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User getUserById(String email) {
        return userRepository.findById(email).get();
    }
}
