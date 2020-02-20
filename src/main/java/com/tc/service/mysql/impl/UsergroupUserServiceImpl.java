package com.tc.service.mysql.impl;

import com.tc.dao.mysql.UsergroupUserMapper;
import com.tc.model.mysql.UsergroupUser;
import com.tc.service.mysql.UsergroupUserService;
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
public class UsergroupUserServiceImpl extends AbstractService<UsergroupUser> implements UsergroupUserService {
    @Resource
    private UsergroupUserMapper usergroupUserMapper;

//    @Override
//    public void deleteById(String id) {
//        usergroupUserMapper.deleteByPrimaryKey(id);
//    }
//
//    @Override
//    public UsergroupUser findById(Integer id) {
//        return usergroupUserMapper.selectByPrimaryKey(id);
//    }
//
//    @Override
//    public UsergroupUser findById(String  id) {
//        return usergroupUserMapper.selectByPrimaryKey(id);
//    }
}
