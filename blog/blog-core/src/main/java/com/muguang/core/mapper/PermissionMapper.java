package com.muguang.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muguang.core.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    Set<Permission> selectPermissionsByUserId(@Param("userId") Integer userId);

}
