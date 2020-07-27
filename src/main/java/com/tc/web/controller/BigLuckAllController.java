package com.tc.web.controller;

import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.BigLuckAll;
import com.tc.service.mysql.BigLuckAllService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by bocheng.luo on 2020/07/24.
*/
@RestController
@RequestMapping("/big/luck/all")
public class BigLuckAllController {
    @Resource
    private BigLuckAllService bigLuckAllService;
    
    
    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="", notes="")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "{id}", value = "", required = true, dataType = "String")
	}) 
    public Result detail(@PathVariable Integer id) {
        BigLuckAll bigLuckAll = bigLuckAllService.findById(id);
        return ResultGenerator.genSuccessResult(bigLuckAll);
    }

    @GetMapping
    @ApiOperation(httpMethod="GET",value="", notes="")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "{page}", value = "页号", required = true, dataType = "String"),
        @ApiImplicitParam(name = "{size}", value = "页大小", required = true, dataType = "String")
	}) 
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<BigLuckAll> list = bigLuckAllService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
