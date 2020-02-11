package com.tc.service.mysql.impl;

import com.tc.dao.mysql.UsergroupRoleMapper;
import com.tc.model.mysql.UsergroupRole;
import com.tc.service.mysql.UsergroupRoleService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/11.
 */
@Service
@Transactional
public class UsergroupRoleServiceImpl extends AbstractService<UsergroupRole> implements UsergroupRoleService {
    @Resource
    private UsergroupRoleMapper usergroupRoleMapper;

    @Override
    public void deleteById(String id) {

    }

    @Override
    public UsergroupRole findById(Integer id) {
        return null;
    }
}
