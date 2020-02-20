package com.tc.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.UserInfo;
import com.tc.model.mysql.UserRole;
import com.tc.model.mysql.Usergroup;
import com.tc.model.mysql.UsergroupUser;
import com.tc.service.mysql.UserInfoService;
import com.tc.service.mysql.UsergroupService;
import com.tc.service.mysql.UsergroupUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/usergroup/user")
@Api(tags = "用户组用户关系管理")
public class UsergroupUserController {
    @Resource
    private UsergroupUserService usergroupUserService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UsergroupService usergroupService;
    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="查询用户组用户关系详细", notes="id必传用户组用户关系id")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "", required = true, dataType = "String")
	}) 
    public Result detail(@PathVariable String id) {
        UsergroupUser usergroupUser = usergroupUserService.findById(id);
        return ResultGenerator.genSuccessResult(usergroupUser);
    }

    @GetMapping("/list")
    @ApiOperation(httpMethod="GET",value="查询用户组用户关系列表", notes="page,size必传，其他非必传")
	@ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页号", required = true, dataType = "int" ,defaultValue ="0"),
            @ApiImplicitParam(name = "size", value = "页大小", required = true, dataType = "int" ,defaultValue ="10"),
            @ApiImplicitParam(name = "u_name", value = "用户名",  dataType = "String"),
            @ApiImplicitParam(name = "ug_name", value = "用户组名",  dataType = "String")
	}) 
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "10") Integer size, String u_name,String ug_name) {
        PageHelper.startPage(page, size);
        List<UsergroupUser> list=new ArrayList<UsergroupUser>();
        Condition condition=new Condition(UsergroupUser.class);
        Example.Criteria criteria=condition.createCriteria();
        if ((null==ug_name||"".equals(ug_name))&&(null==u_name||"".equals(u_name))){
            list = usergroupUserService.findAll();
        }else{
            if (null!=u_name&&!"".equals(u_name)){
                //1.查询对应的user对应的id，TODO
                Condition condition1=new Condition(UserInfo.class);
                Example.Criteria criteria1=condition1.createCriteria();
                criteria1.andLike("uName","%"+u_name+"%");
                List<UserInfo> uL=userInfoService.findByCondition(condition1);
                List<String> uIds=new ArrayList<>();
                for (UserInfo u:uL
                     ) {
                    uIds.add(u.getuId());
                }
                //2.添加到条件中
                if (uIds.size()==0) {
                    uIds.add("-1");
                }
                criteria.andIn("uId", uIds);
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
            list=usergroupUserService.findByCondition(condition);
        }
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/add")
    @ApiOperation(httpMethod="POST",value="新增用户组用户关系", notes="{ug_id：用户组id（必传）, u_id: 用户id（必传）,is_work 默认 1-启用}")
    public Result add(@RequestBody  @ApiParam(name = "data",value ="新增json" , required = true) JSONObject data) {
        try {
            String uguId= UUID.randomUUID().toString();
            if(null==data.get("u_id")||"".equals(data.get("u_id").toString())){
                return  ResultGenerator.genFailResult("u_id必传递");
            }
            if(null==data.get("ug_id")||"".equals(data.get("ug_id").toString())){
                return  ResultGenerator.genFailResult("ug_id必传递");
            }
                Usergroup ug=usergroupService.findById(data.get("ug_id").toString());
                if (null==ug){
                    return  ResultGenerator.genFailResult("未匹配到相应的用户组信息");
                }

            UserInfo u=userInfoService.findById(data.get("u_id").toString());
            if (null==u){
                return  ResultGenerator.genFailResult("未匹配到相应的用户信息");
            }
            UsergroupUser ugu = new UsergroupUser();
            //设置是否启用
            ugu.setIsWork(null==data.get("is_work")?1:Integer.valueOf(data.get("is_work").toString()));
            ugu.setuId(data.get("u_id").toString());
            ugu.setUgId(data.get("ug_id").toString());
            ugu.setUguId(uguId);
            usergroupUserService.save(ugu);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @ApiOperation(httpMethod="POST",value="更新用户组用户关系信息", notes="{ugu_id:关系id（必传）,ug_id:用户组id,u_id: 用户id,is_work:是否启用}")
    public Result update(@RequestBody  @ApiParam(name = "data",value ="更新json" , required = true) JSONObject data) {
        try {

            if(null==data.get("ugu_id")||"".equals(data.get("ugu_id").toString())){
                return  ResultGenerator.genFailResult("ugu_id必传递");
            }
            //查询用户组用户关系
            UsergroupUser ugu=usergroupUserService.findById(data.get("ugu_id").toString());
            //设置用户组id
            if(null!=data.get("u_id")&&!"".equals(data.get("u_id").toString())){
                ugu.setuId(data.get("u_id").toString());
            }
            //设置是否启用
            if (null!=data.get("is_work")&&!"".equals(data.get("is_work").toString())){
                Integer isWork=data.getInteger("is_work");
                ugu.setIsWork(isWork);
            }
            usergroupUserService.update(ugu);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/delete/{ugu_id}")
    @ApiOperation(httpMethod="DELETE",value="删除用户组用户关系", notes="默认DELETE方法 u_id不能为空 物理级联删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ugu_id", value = "用户组用户关系id", required = true, dataType = "String")
    })
    public Result delete(@PathVariable String  ugu_id) {
        String optype;
        try {
                optype="删除";
                //3.删除用户
                usergroupUserService.deleteById(ugu_id);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult(optype+"操作用户组用户关系成功");
    }
}
