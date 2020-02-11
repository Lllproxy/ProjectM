package com.tc.service.mysql.impl;

import com.tc.dao.mysql.UsergroupUserMapper;
import com.tc.model.mysql.UsergroupUser;
import com.tc.service.mysql.UsergroupUserService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/11.
 */
@Service
@Transactional
public class UsergroupUserServiceImpl extends AbstractService<UsergroupUser> implements UsergroupUserService {
    @Resource
    private UsergroupUserMapper usergroupUserMapper;

    @Override
    public void deleteById(String id) {

    }

    @Override
    public UsergroupUser findById(Integer id) {
        return null;
    }
}
