package com.tc.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tc.model.mysql.UserInfo;
import com.tc.service.mysql.UserInfoService;
import com.tc.service.mysql.UserRoleService;
import com.tc.service.mysql.UsergroupService;
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
@RequestMapping("/user/info")
@Api(tags = "用户管理")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UsergroupUserService usergroupUserService;

    @Resource
    private UsergroupService usergroupService;


    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="", notes="")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "", required = true, dataType = "String")
	}) 
    public Result detail(@PathVariable String id) {
        UserInfo userInfo = userInfoService.findById(id);
        return ResultGenerator.genSuccessResult(userInfo);
    }

    @GetMapping("/list")
    @ApiOperation(httpMethod="GET",value="查询用户列表", notes="页大小默认size=10")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "页号", required = true, dataType = "int"),
        @ApiImplicitParam(name = "size", value = "页大小", required = true, dataType = "int")
	}) 
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<UserInfo> list = userInfoService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/add")
    @ApiOperation(httpMethod="POST",value="新增用户", notes="{u_name必传 is_work 默认 1-启用,password 密码,ug_id 用户组id}")
    public Result add(@RequestBody  @ApiParam(name = "data",value ="新增json" , required = true) JSONObject data) {
        try {

            if(null!=data.get("ug_id")&&!"".equals(data.get("ug_id").toString())){
                Usergroup ug=usergroupService.findById(data.get("ug_id").toString());
                if (null==ug){
                    return  ResultGenerator.genFailResult("未匹配到相应的用户组信息");
                }
            }
            String uId=UUID.randomUUID().toString();
            if(null==data.get("u_name")||"".equals(data.get("u_name").toString())){
                return  ResultGenerator.genFailResult("u_name必传递");
            }
            UserInfo userInfo = new UserInfo();
            //设置是否启用
            userInfo.setIsWork(null==data.get("is_work")?1:Integer.valueOf(data.get("is_work").toString()));
            userInfo.setuId(uId);
            userInfo.setuName(data.get("u_name").toString());
            userInfo.setuPassword(null==data.get("password")?"123456":data.get("password").toString());
            userInfoService.save(userInfo);

            //添加用户组用户信息
            if(null!=data.get("ug_id")&&!"".equals(data.get("ug_id").toString())) {
                UsergroupUser usergroupUser=new UsergroupUser();
                usergroupUser.setIsWork(1);
                usergroupUser.setUgId(data.get("ug_id").toString());
                usergroupUser.setuId(uId);
                usergroupUser.setUguId(UUID.randomUUID().toString());
                usergroupUserService.save(usergroupUser);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @ApiOperation(httpMethod="POST",value="跟新用户信息", notes="{u_id:用户id（必传）,password: 密码,u_name: 用户名}")
    public Result update(@RequestBody  @ApiParam(name = "data",value ="更新json" , required = true) JSONObject data) {
        try {
            UserInfo userInfo = new UserInfo();
            if(null==data.get("u_id")||"".equals(data.get("u_id").toString())){
                return  ResultGenerator.genFailResult("u_id必传递");
            }
            String uId= data.get("u_id").toString();
            //设置用户组id
            userInfo.setuId(uId);
            //设置用户组名-检查用户组名是否存在
            //设置用户组名
            if (null!=data.get("u_name")){
                String u_name=data.get("u_name").toString();
                UserInfo f_ug=userInfoService.findBy("uName",u_name);
                if(null!=f_ug){
                    if (!f_ug.getuId().equals(uId)){
                        return ResultGenerator.genFailResult("u_name已存在，请更换名称");
                    }
                }else{
                    userInfo.setuName(u_name);
                }
            }

            //设置是否启用
            if (null!=data.get("is_work")&&!"".equals(data.get("is_work").toString())){
                Integer isWork=data.getInteger("is_work");
                userInfo.setIsWork(isWork);
            }
            //设置密码
            if(null!=data.get("password")&&!"".equals(data.get("password").toString())){
                String password=data.getString("password");
                userInfo.setuPassword(password);
            }
            userInfoService.update(userInfo);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/delete/{type}/{u_id}")
    @ApiOperation(httpMethod="DELETE",value="删除用户组", notes="默认DELETE方法 u_id不能为空,type=1 禁用 type=2 物理级联删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "{type}", value = "删除类型", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "{u_id}", value = "用户组id", required = true, dataType = "String")
    })
    public Result delete(@PathVariable Integer type,@PathVariable String  u_id) {
        String optype;
        try {
            if (type.equals(1)){
                optype="禁用";
                //禁用用户组
                //1.查询改用户
                UserInfo ug=userInfoService.findById(u_id);
                //2.设置禁用
                ug.setIsWork(2);
                userInfoService.update(ug);
            }else if (type.equals(2)){
                optype="删除";
                //级联删除用户组
                //1.删除用户组用户
                //1.1.查询该用户组对应的的用户组用户关系list
                Condition condition=new Condition(UsergroupUser.class);
                Example.Criteria criteria = condition.createCriteria();
                criteria.andEqualTo("uId",u_id);
                List<UsergroupUser> uguList= usergroupUserService.findByCondition(condition);
                //1.2.循环删除用户组用户关系
                for (UsergroupUser ugu:uguList
                ) {
                    usergroupUserService.deleteById(ugu.getUgId());
                }
                //2.删除用户角色
                //2.1.查询用户角色关系list
                criteria=null;
                condition=null;
                System.gc();
                Condition condition2=new Condition(UserRole.class);
                Example.Criteria criteria2 = condition2.createCriteria();
                criteria2.andEqualTo("uId",u_id);
                List<UserRole> urList= userRoleService.findByCondition(condition2);
                //2.2.循环删除
                for (UserRole ur:urList
                ) {
                    userRoleService.deleteById(ur.getuId());
                }
                //3.删除用户
                userInfoService.deleteById(u_id);
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
