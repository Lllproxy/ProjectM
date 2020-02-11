package com.tc.web.controller;

import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.ServerInfo;
import com.tc.service.mysql.ServerInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/server/info")
public class ServerInfoController {
    @Resource
    private ServerInfoService serverInfoService;
    
    
    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="", notes="")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "{id}", value = "", required = true, dataType = "String")
	}) 
    public Result detail(@PathVariable Integer id) {
        ServerInfo serverInfo = serverInfoService.findById(id);
        return ResultGenerator.genSuccessResult(serverInfo);
    }

    @GetMapping
    @ApiOperation(httpMethod="GET",value="", notes="")
	@ApiImplicitParams({
            @ApiImplicitParam(name = "{page}", value = "页号", required = true, dataType = "int"),
            @ApiImplicitParam(name = "{size}", value = "页大小", required = true, dataType = "int")
	}) 
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ServerInfo> list = serverInfoService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
