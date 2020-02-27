package com.tc.service.mysql.impl;

import com.tc.dao.mysql.ShareCompanyMapper;
import com.tc.model.mysql.ShareCompany;
import com.tc.service.mysql.ShareCompanyService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/27.
 */
@Service
@Transactional
public class ShareCompanyServiceImpl extends AbstractService<ShareCompany> implements ShareCompanyService {
    @Resource
    private ShareCompanyMapper shareCompanyMapper;

}
