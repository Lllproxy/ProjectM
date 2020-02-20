package com.tc.service.mysql.impl;

import com.tc.dao.mysql.RoleMapper;
import com.tc.model.mysql.Role;
import com.tc.service.mysql.RoleService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/11.
 */
@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {
    @Resource
    private RoleMapper roleMapper;

//    @Override
//    public void deleteById(String id) {
//
//    }
//
//    @Override
//    public Role findById(Integer id) {
//        return null;
//    }
}
