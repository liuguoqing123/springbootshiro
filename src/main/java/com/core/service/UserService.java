package com.core.service
        ;

import com.core.bean.UserShrio;

import java.util.List;


public interface UserService {

    public UserShrio findByName(String username);
    public UserShrio findById(int id);
    public int update(UserShrio user);
    public List<UserShrio> selectAll();

}
