package com.tc.web.controller;

import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.UserInfo;
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

/**
*
 * @author bocheng.luo
 * @date 2020/02/11
 */
@RestController
@RequestMapping("/usergroup/user")
public class UsergroupUserController {
    @Resource
    private UsergroupUserService usergroupUserService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UsergroupService usergroupService;
    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="", notes="")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "", required = true, dataType = "String")
	}) 
    public Result detail(@PathVariable String id) {
        UsergroupUser usergroupUser = usergroupUserService.findById(id);
        return ResultGenerator.genSuccessResult(usergroupUser);
    }

    @GetMapping
    @ApiOperation(httpMethod="GET",value="", notes="")
	@ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页号", required = true, dataType = "int"),
            @ApiImplicitParam(name = "size", value = "页大小", required = true, dataType = "int"),
            @ApiImplicitParam(name = "u_name", value = "用户名",  dataType = "String"),
            @ApiImplicitParam(name = "ug_name", value = "用户组名",  dataType = "String"),
            @ApiImplicitParam(name = "u_id", value = "用户id",   dataType = "String"),
            @ApiImplicitParam(name = "ug_id", value = "用户组id",  dataType = "String")
	}) 
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size
    ,@RequestParam String u_name,@RequestParam String ug_name,@RequestParam String u_id,@RequestParam String ug_id) {
        PageHelper.startPage(page, size);
        List<UsergroupUser> list=new ArrayList<UsergroupUser>();
        Condition condition=new Condition(UsergroupUser.class);
        Example.Criteria criteria=condition.createCriteria();
        if ((null==ug_id|| "".equals(ug_id))&(null==ug_name||"".equals(ug_name))&(null==u_name||"".equals(u_name))&(null==u_id||"".equals(u_id))){
            list = usergroupUserService.findAll();
        }else{
            if (null!=ug_id&&!"".equals(ug_id)){
                criteria.andEqualTo("ugId",ug_id);
            }
            if (null!=u_id&&!"".equals(u_id)){
                criteria.andEqualTo("uId",u_id);
            }
            if (null!=u_name&&!"".equals(u_name)){
                //1.查询对应的user对应的id，TODO
                Condition condition1=new Condition(UserInfo.class);
                Example.Criteria criteria1=condition1.createCriteria();
                criteria1.andLike("uName",u_name);
                List<UserInfo> uL=userInfoService.findByCondition(condition1);
                List<String> uIds=new ArrayList<>();
                for (UserInfo u:uL
                     ) {
                    uIds.add(u.getuId());
                }
                //2.添加到条件中
                criteria.andIn("uId",uIds);
            }
            if (null!=ug_name&&!"".equals(ug_name)){
                //1.查询对应的user对应的id，TODO
                Condition condition2=new Condition(Usergroup.class);
                Example.Criteria criteria2=condition2.createCriteria();
                criteria2.andLike("ugName",ug_name);
                List<Usergroup> ugL=usergroupService.findByCondition(condition2);
                List<String> ugIds=new ArrayList<>();
                for (Usergroup ug:ugL
                ) {
                    ugIds.add(ug.getUgId());
                }
                //2.添加到条件中
                criteria.andIn("ugId",ugIds);
            }
            list=usergroupUserService.findByCondition(condition);
        }
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
