package com.tc.service.mysql.impl;

import com.tc.dao.mysql.ShareErroMapper;
import com.tc.model.mysql.ShareErro;
import com.tc.service.mysql.ShareErroService;
import com.tc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by bocheng.luo on 2020/02/28.
 */
@Service
@Transactional
public class ShareErroServiceImpl extends AbstractService<ShareErro> implements ShareErroService {
    @Resource
    private ShareErroMapper shareErroMapper;

}
