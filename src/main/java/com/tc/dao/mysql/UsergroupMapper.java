package com.tc.dao.mysql;

import com.tc.core.Mapper;

public interface UsergroupMapper extends Mapper<Usergroup> {
    /**
     * 根据id删除用户组
     * @param ug_id
     */
    void deleteById(String ug_id);
}