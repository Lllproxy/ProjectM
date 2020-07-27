package com.tc.service.mysql.impl;

import com.tc.dao.mysql.BigLuckAllMapper;
import com.tc.model.mysql.BigLuckAll;
import com.tc.service.mysql.BigLuckAllService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/07/24.
 */
@Service
@Transactional
public class BigLuckAllServiceImpl extends AbstractService<BigLuckAll> implements BigLuckAllService {
    @Resource
    private BigLuckAllMapper bigLuckAllMapper;

}
