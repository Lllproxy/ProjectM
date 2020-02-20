package com.tc.service.mysql.impl;

import com.tc.dao.mysql.UserPowerMapper;
import com.tc.model.mysql.UserPower;
import com.tc.service.mysql.UserPowerService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/11.
 */
@Service
@Transactional
public class UserPowerServiceImpl extends AbstractService<UserPower> implements UserPowerService {
    @Resource
    private UserPowerMapper userPowerMapper;

//    @Override
//    public void deleteById(String id) {
//
//    }
//
//    @Override
//    public UserPower findById(Integer id) {
//        return null;
//    }
}
