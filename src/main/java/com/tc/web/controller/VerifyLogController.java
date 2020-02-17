package com.tc.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.UserInfo;
import com.tc.model.mysql.UsergroupUser;
import com.tc.model.mysql.VerifyLog;
import com.tc.service.mysql.UserInfoService;
import com.tc.service.mysql.UsergroupUserService;
import com.tc.service.mysql.VerifyLogService;
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
@RequestMapping("/verify/log")
@Api(tags = "鉴权（日志）管理")
public class VerifyLogController {
    @Resource
    private VerifyLogService verifyLogService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UsergroupUserService usergroupUserService;

    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="", notes="")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "", required = true, dataType = "String")
	}) 
    public Result detail(@PathVariable Integer id) {
        VerifyLog verifyLog = verifyLogService.findById(id);
        return ResultGenerator.genSuccessResult(verifyLog);
    }

    @GetMapping("/list")
    @ApiOperation(httpMethod="GET",value="查询鉴权日志列表", notes="page ,size 必传 ; ug_id,u_name 可以为空")
	@ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页号", required = true, dataType = "int"),
            @ApiImplicitParam(name = "size", value = "页大小", required = true, dataType = "int"),
            @ApiImplicitParam(name = "ug_id", value = "用户组id",  dataType = "int"),
            @ApiImplicitParam(name = "u_name", value = "用户名称", dataType = "int")
	}) 
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size
    ,@RequestParam String ug_id,@RequestParam String u_name) {
        List<VerifyLog> list=new ArrayList<>();
        PageHelper.startPage(page, size);
        //u_name对应的用户集合
        List<String> listUid=new ArrayList<>();
        //ug_id对应的用户集合
        List<String> listUid0=new ArrayList<>();

        //获取用户组id对应的用户
        if(ug_id!=null&&!"".equals(ug_id)){
            Condition condition0=new Condition(UsergroupUser.class);
            Example.Criteria criteria0=condition0.createCriteria();
            criteria0.andEqualTo("ugId",ug_id);
            List<UsergroupUser> listUgu=usergroupUserService.findByCondition(condition0);
            for (UsergroupUser ugu:listUgu
                 ) {
                listUid0.add(ugu.getuId());
            }
        }

        //获取u_name对应的用户
        if (u_name!=null&&!"".equals(u_name)){
            Condition condition=new Condition(UserInfo.class);
            Example.Criteria criteria=condition.createCriteria();
            criteria.andLike("uName",u_name);
            List<UserInfo> listU=userInfoService.findByCondition(condition);
            for (UserInfo u:listU
            ) {
                listUid.add(u.getuId());
            }
        }

        //查询用户list
        List<String> queryUserList=new ArrayList<>();
        if ((u_name!=null&&!"".equals(u_name)&&ug_id!=null&&!"".equals(ug_id))){
            //用户组id非空，且用户名非空 求交集
            listUid.retainAll(listUid0);
            queryUserList.addAll(listUid);
        }else if ((u_name==null||"".equals(u_name))&&ug_id!=null&&!"".equals(ug_id)){
            //用户组id非空，且用户名空 取用户组对应用户id
            queryUserList.addAll(listUid0);
        }else if(u_name!=null&&!"".equals(u_name)&&(ug_id==null||"".equals(ug_id))){
            //用户组id空，且用户名非空 取用户名对应用户id
            queryUserList.addAll(listUid);
        }else if ((u_name==null||"".equals(u_name))&&(ug_id==null||"".equals(ug_id))){
            //用户组id空，且用户名空 取所有
        }

        if (queryUserList.size()>0) {
            //如果查询用户列表有数据，则条件查询
            Condition condition2 = new Condition(VerifyLog.class);
            Example.Criteria criteria2 = condition2.createCriteria();
            criteria2.andIn("uId", queryUserList);
            list = verifyLogService.findByCondition(condition2);
        }else{
            //如果未发现查询指定用户，则查询所有
            list=verifyLogService.findAll();
        }

        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 权限验证
     * @param
     */
    @PostMapping("/verify/{u_id}/{p_id}")
    @ApiOperation(httpMethod="GET",value="权限验证", notes="u_id 用户id,p_id 权限id(必传)}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "u_id", value = "", required = true, dataType = "String"),
            @ApiImplicitParam(name = "p_id", value = "", required = true, dataType = "String")
    })
    public Result verify(@PathVariable String u_id,@PathVariable String p_id) {
        try {
            //默认2：1-通过 2-失败
            int v_result=2;
            VerifyLog verifyLog = new VerifyLog();
            //1.查询用户是否启用
            UserInfo userInfo= userInfoService.findBy("",u_id);
            if (null!=userInfo){
                if (2==userInfo.getIsWork()){
                    //如果用户被禁用
                    verifyLog.setVrDec("用户被禁用");
                }
            }
            //2.查询权限是否启用

            //

            verifyLog.setvResult(v_result);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }
}
