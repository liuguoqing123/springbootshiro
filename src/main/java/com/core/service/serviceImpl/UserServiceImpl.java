package com.core.service.serviceImpl;

import com.core.bean.UserShrio;
import com.core.mapper.UserMapper;
import com.core.service.UserService;
import com.core.bean.UserShrio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserShrio findByName(String username) {
        System.out.println("userService findeByName"+username);
        return userMapper.findByName(username);
    }

    @Override
    public UserShrio findById(int  id) {
        return userMapper.findById(id);
    }

    @Override
    public int update(UserShrio user) {
        return userMapper.update(user);
    }

    @Override
    public List<UserShrio> selectAll() {
        return userMapper.selectAll();
    }
}
