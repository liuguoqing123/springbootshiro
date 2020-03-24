package com.core.mapper;

import com.core.bean.UserShrio;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    public UserShrio findByName(String username);

    public UserShrio findById(int  id);

    public int update(UserShrio user);

    public List<UserShrio> selectAll();
}
