package com.muguang.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muguang.core.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
