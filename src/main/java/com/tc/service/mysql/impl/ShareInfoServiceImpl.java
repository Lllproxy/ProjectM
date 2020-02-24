package com.tc.service.mysql.impl;

import com.tc.dao.mysql.ShareInfoMapper;
import com.tc.model.mysql.ShareInfo;
import com.tc.service.mysql.ShareInfoService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/25.
 */
@Service
@Transactional
public class ShareInfoServiceImpl extends AbstractService<ShareInfo> implements ShareInfoService {
    @Resource
    private ShareInfoMapper shareInfoMapper;

}
