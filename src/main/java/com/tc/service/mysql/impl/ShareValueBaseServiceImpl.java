package com.tc.service.mysql.impl;

import com.tc.dao.mysql.ShareValueBaseMapper;
import com.tc.model.mysql.ShareValueBase;
import com.tc.service.mysql.ShareValueBaseService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/03/05.
 */
@Service
@Transactional
public class ShareValueBaseServiceImpl extends AbstractService<ShareValueBase> implements ShareValueBaseService {
    @Resource
    private ShareValueBaseMapper shareValueBaseMapper;

}
