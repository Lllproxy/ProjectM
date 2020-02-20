package com.tc.service.mysql.impl;

import com.tc.dao.mysql.UserInfoMapper;
import com.tc.model.mysql.UserInfo;
import com.tc.service.mysql.UserInfoService;
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
public class UserInfoServiceImpl extends AbstractService<UserInfo> implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

//    @Override
//    public void deleteById(String id) {
//        userInfoMapper.deleteByPrimaryKey(id);
//    }
//
//    @Override
//    public UserInfo findById(Integer id) {
//        return userInfoMapper.selectByPrimaryKey(id);
//    }
}
