package com.buffet.hrmanagement.service;

import com.buffet.hrmanagement.model.User;

public interface UserService {
    User findByUsername(String username);
    void save(User user);
}
