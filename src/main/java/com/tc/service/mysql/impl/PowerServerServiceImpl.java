package com.tc.service.mysql.impl;

import com.tc.dao.mysql.PowerServerMapper;
import com.tc.model.mysql.PowerServer;
import com.tc.service.mysql.PowerServerService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/11.
 */
@Service
@Transactional
public class PowerServerServiceImpl extends AbstractService<PowerServer> implements PowerServerService {
    @Resource
    private PowerServerMapper powerServerMapper;

    @Override
    public void deleteById(String id) {

    }

    @Override
    public PowerServer findById(Integer id) {
        return null;
    }
}
