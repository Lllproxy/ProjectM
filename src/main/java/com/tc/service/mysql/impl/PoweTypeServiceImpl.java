package com.tc.service.mysql.impl;

import com.tc.dao.mysql.PoweTypeMapper;
import com.tc.model.mysql.PoweType;
import com.tc.service.mysql.PoweTypeService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/11.
 */
@Service
@Transactional
public class PoweTypeServiceImpl extends AbstractService<PoweType> implements PoweTypeService {
    @Resource
    private PoweTypeMapper poweTypeMapper;

    @Override
    public void deleteById(String id) {

    }

    @Override
    public PoweType findById(Integer id) {
        return null;
    }
}
