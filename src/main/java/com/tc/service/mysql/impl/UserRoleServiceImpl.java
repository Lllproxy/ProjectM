package com.tc.service.mysql.impl;

import com.tc.dao.mysql.UserRoleMapper;
import com.tc.model.mysql.UserRole;
import com.tc.service.mysql.UserRoleService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 *
 * @author bocheng.luo
 * @date 2020/02/11
 */
@Service
@Transactional
public class UserRoleServiceImpl extends AbstractService<UserRole> implements UserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;

//    @Override
//    public void deleteById(String id) {
//        userRoleMapper.deleteByPrimaryKey(id);
//    }
//
//    @Override
//    public UserRole findById(Integer id) {
//        return null;
//    }
}
