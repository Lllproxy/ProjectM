package com.tc.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.*;
import com.tc.service.mysql.PowerServerService;
import com.tc.service.mysql.ServerInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
*
 * @author bocheng.luo
 * @date 2020/02/11
 */
@RestController
@RequestMapping("/server/info")
@Api(tags = "服务管理")
public class ServerInfoController {
    @Resource
    private ServerInfoService serverInfoService;
    
    @Resource
    private PowerServerService powerServerService;
    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="", notes="")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "{id}", value = "", required = true, dataType = "String")
	}) 
    public Result detail(@PathVariable Integer id) {
        ServerInfo serverInfo = serverInfoService.findById(id);
        return ResultGenerator.genSuccessResult(serverInfo);
    }

    @GetMapping("/list")
    @ApiOperation(httpMethod="GET",value="查询服务列表", notes="页大小默认size=10")
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

    @PostMapping("/add")
    @ApiOperation(httpMethod="POST",value="添加服务", notes="{s_dsc :服务说明（必传）,s_status ：服务状态 1-在线（默认） 2-下线 ,api :服务地址（必传）}")
    public Result add(@RequestBody  @ApiParam(name = "data",value ="新增json" , required = true) JSONObject data) {
        try {
            String sId= UUID.randomUUID().toString();
            if(null==data.get("s_dsc")||"".equals(data.get("s_dsc").toString())){
                return  ResultGenerator.genFailResult("s_dsc必传递");
            }
            if(null==data.get("api")||"".equals(data.get("api").toString())){
                return  ResultGenerator.genFailResult("api必传递");
            }
            ServerInfo serverInfo = new ServerInfo();
            //设置是否启用
            serverInfo.setsDsc(data.get("s_dsc").toString());
            serverInfo.setsId(sId);
            serverInfo.setsStatus(null==data.get("s_status")?1:Integer.valueOf(data.get("s_status").toString()));
            serverInfo.setApi(data.getString("api"));
            serverInfoService.save(serverInfo);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @ApiOperation(httpMethod="POST",value="更新服务信息", notes="{s_id:用户id（必传）,s_status ：服务状态 1-在线（默认） 2-下线 ,api :服务地址（必传）}")
    public Result update(@RequestBody  @ApiParam(name = "data",value ="更新json" , required = true) JSONObject data) {
        try {
            if(null==data.get("s_id")||"".equals(data.get("s_id").toString())){
                return  ResultGenerator.genFailResult("s_id必传递");
            }
            String s_id=data.get("s_id").toString();
            ServerInfo serverInfo = serverInfoService.findById(s_id);
            if (null!=data.get("s_status")&&!"".equals(data.get("s_status").toString())){
                serverInfo.setsStatus(Integer.valueOf(data.get("s_status").toString()));
            }
            if (null!=data.get("s_dsc")&&!"".equals(data.get("s_dsc").toString())){
                serverInfo.setsDsc(data.get("s_dsc").toString());
            }
            serverInfoService.update(serverInfo);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/delete/{type}/{s_id}")
    @ApiOperation(httpMethod="DELETE",value="删除服务", notes="默认DELETE方法 s_id不能为空,type=1 禁用 type=2 物理级联删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "{type}", value = "删除类型", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "{s_id}", value = "用户组id", required = true, dataType = "String")
    })
    public Result delete(@PathVariable Integer type,@PathVariable String  s_id) {
        String optype;
        try {
            if (type.equals(1)){
                optype="禁用";
                //禁用服务
                //1.查询该服务
                ServerInfo ser=serverInfoService.findById(s_id);
                //2.设置禁用
                ser.setsStatus(2);
                serverInfoService.update(ser);
            }else if (type.equals(2)){
                optype="删除";
                //级联删除服务
                //1.删除服务权限
                //1.1.查询该服务对应的的服务权限关系list
                Condition condition=new Condition(PowerServer.class);
                Example.Criteria criteria = condition.createCriteria();
                criteria.andEqualTo("sId",s_id);
                List<PowerServer> uguList= powerServerService.findByCondition(condition);
                //1.2.循环删除用户组用户关系
                for (PowerServer ugu:uguList
                ) {
                    powerServerService.deleteById(ugu.getPsId());
                }
                //2.删除服务
                serverInfoService.deleteById(s_id);
            }else{
                optype="非法";
                return  ResultGenerator.genFailResult("type类型参数不对，只能是1/2");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult(optype+"操作用户组成功");
    }
}
