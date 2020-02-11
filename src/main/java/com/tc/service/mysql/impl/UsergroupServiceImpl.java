package com.tc.service.mysql.impl;

import com.tc.dao.mysql.UsergroupMapper;
import com.tc.model.mysql.Usergroup;
import com.tc.service.mysql.UsergroupService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/11.
 */
@Service
@Transactional
public class UsergroupServiceImpl extends AbstractService<Usergroup> implements UsergroupService {
    @Resource
    private UsergroupMapper usergroupMapper;

    @Override
    public void deleteById(String id) {

    }

    @Override
    public Usergroup findById(Integer id) {
        return null;
    }
}
