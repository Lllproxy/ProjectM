package com.tc.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.*;
import com.tc.service.mysql.RoleService;
import com.tc.service.mysql.UsergroupRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tc.service.mysql.UsergroupService;
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
@RequestMapping("/usergroup/role")
public class UsergroupRoleController {
    @Resource
    private UsergroupRoleService usergroupRoleService;

    @Resource
    private RoleService roleService;

    @Resource
    private UsergroupService usergroupService;


    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="", notes="")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "", required = true, dataType = "String")
	}) 
    public Result detail(@PathVariable String id) {
        UsergroupRole usergroupRole = usergroupRoleService.findById(id);
        return ResultGenerator.genSuccessResult(usergroupRole);
    }

    @GetMapping
    @ApiOperation(httpMethod="GET",value="", notes="")
	@ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页号", required = true, dataType = "int"),
            @ApiImplicitParam(name = "size", value = "页大小", required = true, dataType = "int"),
            @ApiImplicitParam(name = "r_name", value = "角色名",  dataType = "String"),
            @ApiImplicitParam(name = "ug_name", value = "用户组名",  dataType = "String")
	}) 
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,String r_name,String ug_name) {
        PageHelper.startPage(page, size);
        List<UsergroupRole> list = new ArrayList<>();
        Condition condition=new Condition(UsergroupRole.class);
        Example.Criteria criteria=condition.createCriteria();
        if ((null==ug_name||"".equals(ug_name))&&(null==r_name||"".equals(r_name))){
            list = usergroupRoleService.findAll();
        }else{
            if (null!=r_name&&!"".equals(r_name)){
                //1.查询对应的角色对应的id，TODO
                Condition condition1=new Condition(Role.class);
                Example.Criteria criteria1=condition1.createCriteria();
                criteria1.andLike("rName","%"+r_name+"%");
                List<Role> rL=roleService.findByCondition(condition1);
                List<String> rIds=new ArrayList<>();
                for (Role r:rL
                ) {
                    rIds.add(r.getrId());
                }
                //2.添加到条件中
                if (rIds.size()==0) {
                    rIds.add("-1");
                }
                criteria.andIn("rId", rIds);
            }
            if (null!=ug_name&&!"".equals(ug_name)){
                //1.查询对应的user对应的id，TODO
                Condition condition2=new Condition(Usergroup.class);
                Example.Criteria criteria2=condition2.createCriteria();
                criteria2.andLike("ugName","%"+ug_name+"%");
                List<Usergroup> ugL=usergroupService.findByCondition(condition2);
                List<String> ugIds=new ArrayList<>();
                for (Usergroup ug:ugL
                ) {
                    ugIds.add(ug.getUgId());
                }
                //2.添加到条件中
                if (ugIds.size()==0){
                    ugIds.add("-1");
                }
                criteria.andIn("ugId",ugIds);
            }
            list=usergroupRoleService.findByCondition(condition);
        }
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/add")
    @ApiOperation(httpMethod="POST",value="新增用户组角色关系", notes="{ug_id：用户组id（必传）, r_id: 角色id（必传）,is_work 默认 1-启用}")
    public Result add(@RequestBody  @ApiParam(name = "data",value ="新增json" , required = true) JSONObject data) {
        try {
            String uguId= UUID.randomUUID().toString();
            if(null==data.get("r_id")||"".equals(data.get("r_id").toString())){
                return  ResultGenerator.genFailResult("r_id必传递");
            }
            if(null==data.get("ug_id")||"".equals(data.get("ug_id").toString())){
                return  ResultGenerator.genFailResult("ug_id必传递");
            }
            Usergroup ug=usergroupService.findById(data.get("ug_id").toString());
            if (null==ug){
                return  ResultGenerator.genFailResult("未匹配到相应的用户组信息");
            }

            Role u=roleService.findById(data.get("r_id").toString());
            if (null==u){
                return  ResultGenerator.genFailResult("未匹配到相应的角色信息");
            }
            UsergroupRole ugu = new UsergroupRole();
            //设置是否启用
            ugu.setIsWork(null==data.get("is_work")?1:Integer.valueOf(data.get("is_work").toString()));
            ugu.setrId(data.get("r_id").toString());
            ugu.setUgId(data.get("ug_id").toString());
            ugu.setUgrId(uguId);
            usergroupRoleService.save(ugu);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }
}
