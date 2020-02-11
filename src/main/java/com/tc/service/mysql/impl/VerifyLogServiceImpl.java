package com.tc.service.mysql.impl;

import com.tc.dao.mysql.VerifyLogMapper;
import com.tc.model.mysql.VerifyLog;
import com.tc.service.mysql.VerifyLogService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/11.
 */
@Service
@Transactional
public class VerifyLogServiceImpl extends AbstractService<VerifyLog> implements VerifyLogService {
    @Resource
    private VerifyLogMapper verifyLogMapper;

    @Override
    public void deleteById(String id) {

    }

    @Override
    public VerifyLog findById(Integer id) {
        return null;
    }
}
