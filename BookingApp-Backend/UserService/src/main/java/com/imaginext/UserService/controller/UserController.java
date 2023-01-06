package com.imaginext.UserService.controller;

import com.imaginext.UserService.domain.User;
import com.imaginext.UserService.exception.UserAlreadyExistsException;
import com.imaginext.UserService.exception.UserNotFoundException;
import com.imaginext.UserService.services.SecurityTokenGenerator;
import com.imaginext.UserService.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:4200")
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    private SecurityTokenGenerator securityTokenGenerator;



    public UserController(UserServiceImpl userService,SecurityTokenGenerator securityTokenGenerator){
        this.userService = userService;
        this.securityTokenGenerator = securityTokenGenerator;
    }
    @PostMapping("/register")
    public ResponseEntity<?> insertUser(@RequestBody User user) throws UserAlreadyExistsException {
        try {
            return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
        }catch (UserAlreadyExistsException ex){
            throw new UserAlreadyExistsException();
        }
    }

    @GetMapping("/getProfile/{email}")
    public ResponseEntity<?> getProfile(@PathVariable String email){
        return new ResponseEntity(userService.getUserById(email),HttpStatus.ACCEPTED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) throws UserNotFoundException {
        Map<String,String> map=null;
        try{
            User user1 = userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());
            if(user1.getEmail().equals(user.getEmail())){
                map=securityTokenGenerator.generateToken(user);
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Try after sometimes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
