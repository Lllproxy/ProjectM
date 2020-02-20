package com.tc.service.mysql.impl;

import com.tc.dao.mysql.RolePowerMapper;
import com.tc.model.mysql.RolePower;
import com.tc.service.mysql.RolePowerService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/11.
 */
@Service
@Transactional
public class RolePowerServiceImpl extends AbstractService<RolePower> implements RolePowerService {
    @Resource
    private RolePowerMapper rolePowerMapper;

//    @Override
//    public void deleteById(String id) {
//
//    }
//
//    @Override
//    public RolePower findById(Integer id) {
//        return null;
//    }
}
