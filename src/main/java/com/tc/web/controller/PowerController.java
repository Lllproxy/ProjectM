package com.tc.web.controller;

import com.alibaba.fastjson.JSONObject;
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
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
            @ApiImplicitParam(name = "size", value = "页大小", required = true, dataType = "int"),
            @ApiImplicitParam(name = "type", value = "权限类型：s-服务 w-资源",dataType = "String"),
            @ApiImplicitParam(name = "p_desc", value = "权限说明", dataType = "String")
	}) 
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,String type,String p_desc) {
        PageHelper.startPage(page, size);
        List<Power> list=new ArrayList<>();
        Condition condition=new Condition(Power.class);
        Example.Criteria criteria=condition.createCriteria();
        if(("".equals(type)||null==type&&("".equals(p_desc)||null==p_desc))){
            list= powerService.findAll();
        }else{
            if (!"".equals(type)&&null!=type){
                criteria.andEqualTo("pType",type);
            }
            if (!"".equals(p_desc)&&null!=p_desc){
                criteria.andLike("pDsc","%"+p_desc+"%");
            }
            list=powerService.findByCondition(condition);
        }
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/add")
    @ApiOperation(httpMethod="POST",value="新增权限", notes="{p_type：权限类型（必传）, p_dsc: 权限描述（必传）}")
    public Result add(@RequestBody  @ApiParam(name = "data",value ="新增json" , required = true) JSONObject data) {
        try {
            String pId= UUID.randomUUID().toString();
            if(null==data.get("p_type")||"".equals(data.get("p_type").toString())){
                return  ResultGenerator.genFailResult("p_type必传递");
            }
            if(null==data.get("p_dsc")||"".equals(data.get("p_dsc").toString())){
                return  ResultGenerator.genFailResult("p_dsc必传递");
            }
            Power power=new Power();
            power.setpId(pId);
            power.setpDsc(data.get("p_dsc").toString());
            power.setpType(data.get("p_type").toString());
            //设置是否启用
            powerService.save(power);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @ApiOperation(httpMethod="POST",value="更新权限信息", notes="{p_id:id（必传）,p_type:类型,p_dec:描述}")
    public Result update(@RequestBody  @ApiParam(name = "data",value ="更新json" , required = true) JSONObject data) {
        try {

            if(null==data.get("p_id")||"".equals(data.get("p_id").toString())){
                return  ResultGenerator.genFailResult("p_id必传递");
            }
            //查询用户组用户关系
            Power power=powerService.findById(data.get("p_id").toString());
            //设置用户组id
            if(null!=data.get("p_type")&&!"".equals(data.get("p_type").toString())){
                power.setpType(data.get("p_type").toString());
            }
            //设置是否启用
            if (null!=data.get("p_dec")&&!"".equals(data.get("p_dec").toString())){
                power.setpDsc(data.get("p_dec").toString());
            }
            powerService.update(power);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/delete/{p_id}")
    @ApiOperation(httpMethod="DELETE",value="删除权限信息", notes="默认DELETE方法 ugr_id不能为空 物理级联删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "p_id", value = "权限id", required = true, dataType = "String")
    })
    public Result delete(@PathVariable String  p_id) {
        String optype;
        try {
            optype="删除";
            //1.先删除权限关系表
            Power power=powerService.findById(p_id);
            String ty=power.getpType();
            if ("s".equals(ty)){
                PowerServer ps=powerServerService.findBy("pId",p_id);
                powerServerService.deleteById(ps.getPsId());
            }else if ("w".equals(ty)){
                PowerWeb pw=powerWebService.findBy("pId",p_id);
                powerWebService.deleteById(pw.getPwId());
            }else{
                return  ResultGenerator.genFailResult("权限类型"+ty+"非法");
            }
            powerService.deleteById(p_id);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult(optype+"操作用户组角色关系成功");
    }
}
