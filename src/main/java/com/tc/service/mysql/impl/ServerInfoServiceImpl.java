package com.tc.service.mysql.impl;

import com.tc.dao.mysql.ServerInfoMapper;
import com.tc.model.mysql.ServerInfo;
import com.tc.service.mysql.ServerInfoService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/11.
 */
@Service
@Transactional
public class ServerInfoServiceImpl extends AbstractService<ServerInfo> implements ServerInfoService {
    @Resource
    private ServerInfoMapper serverInfoMapper;

    @Override
    public void deleteById(String id) {

    }

    @Override
    public ServerInfo findById(Integer id) {
        return null;
    }
}
