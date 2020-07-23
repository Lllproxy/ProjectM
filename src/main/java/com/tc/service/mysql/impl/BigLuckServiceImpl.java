package com.tc.service.mysql.impl;

import com.tc.dao.mysql.BigLuckMapper;
import com.tc.model.mysql.BigLuck;
import com.tc.service.mysql.BigLuckService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/07/23.
 */
@Service
@Transactional
public class BigLuckServiceImpl extends AbstractService<BigLuck> implements BigLuckService {
    @Resource
    private BigLuckMapper bigLuckMapper;

}
