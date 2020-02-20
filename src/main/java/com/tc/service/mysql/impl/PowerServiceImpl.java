package com.tc.service.mysql.impl;

import com.tc.dao.mysql.PowerMapper;
import com.tc.model.mysql.Power;
import com.tc.service.mysql.PowerService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/11.
 */
@Service
@Transactional
public class PowerServiceImpl extends AbstractService<Power> implements PowerService {
    @Resource
    private PowerMapper powerMapper;

//    @Override
//    public void deleteById(String id) {
//
//    }
//
//    @Override
//    public Power findById(Integer id) {
//        return null;
//    }
}
