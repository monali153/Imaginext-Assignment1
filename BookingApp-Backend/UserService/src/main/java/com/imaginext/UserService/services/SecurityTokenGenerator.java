package com.imaginext.UserService.services;

import com.imaginext.UserService.domain.User;

import java.util.Map;

public interface SecurityTokenGenerator {

    Map<String,String> generateToken(User user);
}
