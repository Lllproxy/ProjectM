package com.tc.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.Usergroup;
import com.tc.model.mysql.UsergroupRole;
import com.tc.model.mysql.UsergroupUser;
import com.tc.service.mysql.UsergroupRoleService;
import com.tc.service.mysql.UsergroupService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tc.service.mysql.UsergroupUserService;
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
@RequestMapping("/usergroup")
@Api(tags = "用户组管理")
public class UserGroupController {
    @Resource
    private UsergroupService usergroupService;
    @Resource
    private UsergroupUserService usergroupUserService;
    @Resource
    private UsergroupRoleService usergroupRoleService;



    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="根据id查询用户组信息", notes="id不能为空")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "{id}", value = "", required = true, dataType = "String")
    })
    public Result detail(@PathVariable Integer id) {
        Usergroup usergroup = usergroupService.findById(id);
        return ResultGenerator.genSuccessResult(usergroup);
    }

    @GetMapping("/list")
    @ApiOperation(httpMethod="GET",value="查询用户组列表", notes="全量查询，分页默认页大小10")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "{page}", value = "页号", required = true, dataType = "int"),
            @ApiImplicitParam(name = "{size}", value = "页大小", required = true, dataType = "int")
    })
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<Usergroup> list = usergroupService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/add")
    @ApiOperation(httpMethod="POST",value="新增用户组", notes="默认POST方法 {ug_name :is_work :} ")
    public Result add(@RequestBody  @ApiParam(name = "data",value ="新增json" , required = true) JSONObject data) {
        try {
            String uId= UUID.randomUUID().toString();
            if(null==data.get("ug_name")||"".equals(data.get("ug_name").toString())){
                return  ResultGenerator.genFailResult("ug_name必传递");
            }
            //检查用户组名是否存在
            String ug_name=data.get("ug_name").toString();
            if(null!=usergroupService.findBy("ugName",ug_name)){
                return ResultGenerator.genFailResult("ug_name已存在，请更换名称");
            }
            //设置是否启用
            Integer isWork=data.getInteger("is_work");
            Usergroup usergroup = new Usergroup();
            usergroup.setIsWork(isWork);
            usergroup.setUgId(uId);
            usergroup.setUgName(ug_name);
            usergroupService.save(usergroup);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @ApiOperation(httpMethod="POST",value="更新用户组", notes="默认POST方法 ug_id为空")
    public Result update(@RequestBody  @ApiParam(name = "data",value ="更新json" , required = true) JSONObject data) {
        try {
            Usergroup usergroup = new Usergroup();
            if(null==data.get("ug_id")||"".equals(data.get("ug_id"))){
                return  ResultGenerator.genFailResult("ug_id必传递");
            }
            String ug_id= data.get("ug_id").toString();
            //设置用户组id
            usergroup.setUgId(ug_id);
            //设置用户组名-检查用户组名是否存在
            //设置用户组名
            if (null!=data.get("ug_name")){
                String ug_name=data.get("ug_name").toString();
                Usergroup f_ug=usergroupService.findBy("ugName",ug_name);
                if(null!=f_ug){
                    if (!f_ug.getUgId().equals(ug_id)){
                        return ResultGenerator.genFailResult("ug_name已存在，请更换名称");
                    }
                }else{
                    usergroup.setUgName(ug_name);
                }
            }
            String ug_name=data.get("ug_name").toString();

            //设置是否启用
            if (null!=data.get("is_work")){
                Integer isWork=data.getInteger("is_work");
                usergroup.setIsWork(isWork);
            }

            usergroupService.update(usergroup);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/delete/{type}/{ug_id}")
    @ApiOperation(httpMethod="DELETE",value="删除用户组", notes="默认DELETE方法 ug_id不能为空,type=1 禁用 type=2 物理级联删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "{type}", value = "删除类型", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "{ug_id}", value = "用户组id", required = true, dataType = "String")
    })
    public Result delete(@PathVariable Integer type,@PathVariable String  ug_id) {
        String optype;
        try {
            if (type.equals(1)){
                optype="禁用";
                //禁用用户组
                //1.查询改用户
                Usergroup ug=usergroupService.findById(ug_id);
                //2.设置禁用
                ug.setIsWork(2);
                usergroupService.update(ug);
            }else if (type.equals(2)){
                optype="删除";
                //级联删除用户组
                //1.删除用户组用户
                //1.1.查询该用户组对应的的用户组用户关系list
                Condition condition=new Condition(UsergroupUser.class);
                Example.Criteria criteria = condition.createCriteria();
                criteria.andEqualTo("ugId",ug_id);
                List<UsergroupUser> uguList= usergroupUserService.findByCondition(condition);
                //1.2.循环删除用户组用户关系
                for (UsergroupUser ugu:uguList
                     ) {
                    usergroupUserService.deleteById(ugu.getUgId());
                }
                //2.删除用户组角色
                //2.1.查询用户组角色关系list
                criteria=null;
                condition=null;
                System.gc();
                Condition condition2=new Condition(UsergroupRole.class);
                Example.Criteria criteria2 = condition2.createCriteria();
                criteria2.andEqualTo("ugId",ug_id);
                List<UsergroupRole> ugrList= usergroupRoleService.findByCondition(condition2);
                //2.2.循环删除
                for (UsergroupRole ugr:ugrList
                ) {
                    usergroupRoleService.deleteById(ugr.getUgId());
                }
                //3.删除用户组
                usergroupService.deleteById(ug_id);
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
