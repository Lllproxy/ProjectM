package com.tc.web.controller;

import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.*;
import com.tc.service.mysql.PowerServerService;
import com.tc.service.mysql.PowerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tc.service.mysql.PowerWebService;
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
@RequestMapping("/power")
@Api(tags = "权限管理")
public class PowerController {
    @Resource
    private PowerService powerService;

    @Resource
    private PowerServerService powerServerService;

    @Resource
    private PowerWebService powerWebService;
    
    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="查询权限详细", notes="id 权限id必传")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "权限id", required = true, dataType = "String")
	}) 
    public Result detail(@PathVariable String id) {
        Power power = powerService.findById(id);
        PowerDetail powerDetail=new PowerDetail();
        //查询权限对应的详细
        String type=power.getpType();
        powerDetail.setType(type);
        if ("s".equals(type)){
            PowerServer ps=powerServerService.findBy("pId",power.getpId());
            powerDetail.setIsWork(ps.getIsWork());
            powerDetail.setPswId(ps.getPsId());
            powerDetail.setpId(ps.getpId());
            powerDetail.setsId(ps.getsId());
        }else  if ("w".equals(type)){
            PowerWeb pw=powerWebService.findBy("pId",power.getpId());
            powerDetail.setIsWork(pw.getIsWork());
            powerDetail.setPswId(pw.getPwId());
            powerDetail.setpId(pw.getpId());
            powerDetail.setsId(pw.getwId());
        }else {
            return ResultGenerator.genFailResult("检索到服务类型"+type+"非法");
        }
        PowerUnionAll powerUnionAll=new PowerUnionAll(power,powerDetail);
        return ResultGenerator.genSuccessResult(powerUnionAll);
    }

    @GetMapping("list")
    @ApiOperation(httpMethod="GET",value="查询权限列表", notes="page（必传）,size（必传）,p_type权限类型（必传）, ")
	@ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页号", required = true, dataType = "int"),
            @ApiImplicitParam(name = "size", value = "页大小", required = true, dataType = "int")
	}) 
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Power> list = powerService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
