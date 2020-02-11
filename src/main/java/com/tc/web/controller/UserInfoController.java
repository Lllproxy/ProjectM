package com.tc.web.controller;

import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.UserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tc.service.mysql.UserInfoService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import javax.annotation.Resource;
import java.util.List;

/**
*
 * @author bocheng.luo
 * @date 2020/02/11
 */
@RestController
@RequestMapping("/user/info")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;


    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="", notes="")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "{id}", value = "", required = true, dataType = "String")
	}) 
    public Result detail(@PathVariable String id) {
        UserInfo userInfo = userInfoService.findById(id);
        return ResultGenerator.genSuccessResult(userInfo);
    }

    @GetMapping
    @ApiOperation(httpMethod="GET",value="", notes="")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "{page}", value = "页号", required = true, dataType = "integer"),
        @ApiImplicitParam(name = "{size}", value = "页大小", required = true, dataType = "integer")
	}) 
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<UserInfo> list = userInfoService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
