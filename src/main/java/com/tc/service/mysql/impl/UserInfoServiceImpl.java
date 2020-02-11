package com.tc.service.mysql.impl;

import com.tc.dao.mysql.UserInfoMapper;
import com.tc.model.mysql.UserInfo;
import com.tc.service.mysql.UserInfoService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/11.
 */
@Service
@Transactional
public class UserInfoServiceImpl extends AbstractService<UserInfo> implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public void deleteById(String id) {

    }

    @Override
    public UserInfo findById(Integer id) {
        return null;
    }
}
