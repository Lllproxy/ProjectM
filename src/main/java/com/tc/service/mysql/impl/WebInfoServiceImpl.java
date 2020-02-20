package com.tc.service.mysql.impl;

import com.tc.dao.mysql.WebInfoMapper;
import com.tc.model.mysql.WebInfo;
import com.tc.service.mysql.WebInfoService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/11.
 */
@Service
@Transactional
public class WebInfoServiceImpl extends AbstractService<WebInfo> implements WebInfoService {
    @Resource
    private WebInfoMapper webInfoMapper;

//    @Override
//    public void deleteById(String id) {
//
//    }
//
//    @Override
//    public WebInfo findById(Integer id) {
//        return null;
//    }
}
