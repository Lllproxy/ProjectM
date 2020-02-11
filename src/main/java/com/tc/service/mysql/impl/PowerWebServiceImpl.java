package com.tc.service.mysql.impl;

import com.tc.dao.mysql.PowerWebMapper;
import com.tc.model.mysql.PowerWeb;
import com.tc.service.mysql.PowerWebService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/11.
 */
@Service
@Transactional
public class PowerWebServiceImpl extends AbstractService<PowerWeb> implements PowerWebService {
    @Resource
    private PowerWebMapper powerWebMapper;

    @Override
    public void deleteById(String id) {

    }

    @Override
    public PowerWeb findById(Integer id) {
        return null;
    }
}
