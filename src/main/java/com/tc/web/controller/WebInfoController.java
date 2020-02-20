package com.tc.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.PowerServer;
import com.tc.model.mysql.PowerWeb;
import com.tc.model.mysql.WebInfo;
import com.tc.service.mysql.PowerWebService;
import com.tc.service.mysql.WebInfoService;
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
@RequestMapping("/web/info")
@Api(tags = "资源管理")
public class WebInfoController {
    @Resource
    private WebInfoService webInfoService;

    @Resource
    private PowerWebService powerWebService;


    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="查询资源详细", notes="id作为路径参数必传")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "", required = true, dataType = "String")
	}) 
    public Result detail(@PathVariable String id) {
        WebInfo webInfo = webInfoService.findById(id);
        return ResultGenerator.genSuccessResult(webInfo);
    }

    @GetMapping("/list")
    @ApiOperation(httpMethod="GET",value="查询资源列表", notes="页大小默认size=10")
	@ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页号", required = true, dataType = "int"),
            @ApiImplicitParam(name = "size", value = "页大小", required = true, dataType = "int")
	}) 
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<WebInfo> list = webInfoService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/add")
    @ApiOperation(httpMethod="POST",value="添加服务", notes="{w_dsc :服务说明（必传）,w_status ：服务状态 1-在线（默认） 2-下线 ,url :服务地址（必传）}")
    public Result add(@RequestBody  @ApiParam(name = "data",value ="新增json" , required = true) JSONObject data) {
        try {
            String sId= UUID.randomUUID().toString();
            if(null==data.get("w_dsc")||"".equals(data.get("w_dsc").toString())){
                return  ResultGenerator.genFailResult("w_dsc必传递");
            }
            if(null==data.get("url")||"".equals(data.get("url").toString())){
                return  ResultGenerator.genFailResult("url必传递");
            }
            WebInfo webInfo = new WebInfo();
            //设置是否启用
            webInfo.setwDsc(data.get("w_dsc").toString());
            webInfo.setwId(sId);
            webInfo.setwStatus(null==data.get("w_status")?1:Integer.valueOf(data.get("w_status").toString()));
            webInfo.setUrl(data.getString("url"));
            webInfoService.save(webInfo);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @ApiOperation(httpMethod="POST",value="更新资源信息", notes="{w_id:用户id（必传）,w_status ：服务状态 1-在线（默认） 2-下线 ,url :服务地址（必传）}")
    public Result update(@RequestBody  @ApiParam(name = "data",value ="更新json" , required = true) JSONObject data) {
        try {
            if(null==data.get("w_id")||"".equals(data.get("w_id").toString())){
                return  ResultGenerator.genFailResult("w_id必传递");
            }
            String s_id=data.get("w_id").toString();
            WebInfo webInfo = webInfoService.findById(s_id);
            if (null!=data.get("w_status")&&!"".equals(data.get("w_status").toString())){
                webInfo.setwStatus(Integer.valueOf(data.get("w_status").toString()));
            }
            if (null!=data.get("w_dsc")&&!"".equals(data.get("w_dsc").toString())){
                webInfo.setwDsc(data.get("w_dsc").toString());
            }
            webInfoService.update(webInfo);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/delete/{w_id}")
    @ApiOperation(httpMethod="DELETE",value="删除服务", notes="默认DELETE方法 s_id不能为空物理级联删除")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "w_id", value = "用户组id", required = true, dataType = "String")
    })
    public Result delete(@PathVariable String  w_id) {
        String optype;
        try {
            optype="删除";
            //级联删除服务
            //1.删除服务权限

            //1.1.查询该服务对应的的服务权限关系list
            Condition condition=new Condition(PowerServer.class);
            Example.Criteria criteria = condition.createCriteria();
            criteria.andEqualTo("wId",w_id);
            List<PowerWeb> uguList= powerWebService.findByCondition(condition);
            //1.2.循环删除用户组用户关系
            for (PowerWeb ugu:uguList
            ) {
                powerWebService.deleteById(ugu.getPwId());
            }
            //2.删除服务
            webInfoService.deleteById(w_id);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult(optype+"操作用资源成功");
    }
}
