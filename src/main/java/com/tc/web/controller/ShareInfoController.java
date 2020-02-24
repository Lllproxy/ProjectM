package com.tc.web.controller;

import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.ShareInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tc.service.mysql.ShareInfoService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import javax.annotation.Resource;
import java.util.List;

/**
*
 * @author bocheng.luo
 * @date 2020/02/25
 */
@RestController
@RequestMapping("/share/info")
@Api("股票基本信息管理")
public class ShareInfoController {
    @Resource
    private ShareInfoService shareInfoService;
    
    
    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="", notes="")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "{id}", value = "", required = true, dataType = "String")
	}) 
    public Result detail(@PathVariable Integer id) {
        ShareInfo shareInfo = shareInfoService.findById(id);
        return ResultGenerator.genSuccessResult(shareInfo);
    }

    @GetMapping("list/")
    @ApiOperation(httpMethod="GET",value="获取股票基本信息列表", notes="默认page=0,size=10")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "{page}", value = "页号", required = true, dataType = "String"),
        @ApiImplicitParam(name = "{size}", value = "页大小", required = true, dataType = "String")
	}) 
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ShareInfo> list = shareInfoService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("init/")
    @ApiOperation(httpMethod="GET",value="初始化股票信息", notes="无参方法")
    public Result init() {
        long sta=System.currentTimeMillis();
        int count=0;
        //调用东方财富网接口网爬取取股票基金基本数据

        long end=System.currentTimeMillis();
        long total=end-sta;
        return ResultGenerator.genSuccessResult("初始化成功"+count+"条数据；总耗时"+total+"毫秒");
    }
}
