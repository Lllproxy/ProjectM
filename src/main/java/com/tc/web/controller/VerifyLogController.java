package com.tc.web.controller;

import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.*;
import com.tc.service.mysql.*;
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
@RequestMapping("/verify/log")
@Api(tags = "鉴权（日志）管理")
public class VerifyLogController {
    @Resource
    private VerifyLogService verifyLogService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UsergroupUserService usergroupUserService;

    @Resource
    private UsergroupService usergroupService;

    @Resource
    private UsergroupRoleService usergroupRoleService;

    @Resource
    private RoleService roleService;

    @Resource
    private RolePowerService rolePowerService;

    @Resource
    private PowerService powerServices;

    @Resource
    private WebInfoService webInfoService;

    @Resource
    private ServerInfoService serverInfoService;

    @Resource
    private UserPowerService userPowerService;

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
    ,String ug_id,String u_name) {
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
            criteria.andLike("uName","%"+u_name+"%");
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
    @GetMapping("/verify/{u_id}/{p_id}")
    @ApiOperation(httpMethod="GET",value="权限验证", notes="u_id 用户id,p_id 权限id(必传)}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "u_id", value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "p_id", value = "权限id", required = true, dataType = "String")
    })
    public Result verify(@PathVariable String u_id,@PathVariable String p_id) {
        try {
            //默认2：1-通过 2-失败
            int v_result=2;
            VerifyLog verifyLog = new VerifyLog();
            verifyLog.setpId(p_id);
            verifyLog.setuId(u_id);
            //1.查询用户是否启用
            lab:
            {
                UserInfo userInfo = userInfoService.findBy("uId", u_id);
                if (null != userInfo) {
                    if (2 == userInfo.getIsWork()) {
                        //如果用户被禁用
                        verifyLog.setVrDec("用户被禁用");
                    } else {
                        //检查资源是否在线
                        Power pp = powerServices.findBy("pId", p_id);
                        if ("s".equals(pp.getpType())) {
                            ServerInfo serverInfo = serverInfoService.findBy("sID", pp.getpId());
                            if (2 == serverInfo.getsStatus()) {
                                verifyLog.setVrDec("权限离线");
                                break lab;
                            }
                        } else if ("w".equals(pp.getpType())) {
                            WebInfo webInfo = webInfoService.findBy("wId", pp.getpId());
                            if (1 == webInfo.getwStatus()) {
                                verifyLog.setVrDec("权限离线");
                                break lab;
                            }
                        } else {
                            Exception e = new Exception("发现权限类型异常");
                            e.printStackTrace();
                            break lab;
                        }
                        //2.查询用户和权限是否直接关联
                        Condition condition0 = new Condition(UserPower.class);
                        Example.Criteria criteria0 = condition0.createCriteria();
                        criteria0.andEqualTo("uId", u_id).andEqualTo("pId", p_id);
                        List<UserPower> upL = userPowerService.findByCondition(condition0);
                        for (UserPower up : upL
                        ) {
                            if (1 == up.getIsWork()) {
                                v_result = 1;
                                verifyLog.setVrDec("权限鉴定通过");
                                break lab;
                            }
                        }
                        //2.用户状态正常，接着验证用户组
                        //2.1.查询用户对应的用户组
                        Condition condition = new Condition(UsergroupUser.class);
                        Example.Criteria criteria = condition.createCriteria();
                        criteria.andEqualTo("uId", u_id);
                        List<UsergroupUser> ugL = usergroupUserService.findByCondition(condition);
                        if (ugL.size() > 0) {
                            //如果存在用户组继续判断
                            //2.2.循环所有所属用户组是否具备该权限
                            boolean haveUgIsWork = false;
                            boolean haveRole = false;
                            boolean haveRoleIsWork = false;
                            boolean haveRolePower = false;
                            boolean haveRolePowerIsWork = false;
                            //获取用户组对应的角色
                            for (UsergroupUser uu : ugL
                            ) {
                                //查询用户组状态
                                Usergroup usergroup = usergroupService.findBy("ugId", uu.getUgId());
                                if (1 == usergroup.getIsWork()) {
                                    haveUgIsWork = true;
                                    //用户组启用才执行下面方法
                                    //查询用户组角色关系
                                    Condition condition2 = new Condition(UsergroupRole.class);
                                    Example.Criteria criteria2 = condition2.createCriteria();
                                    criteria2.andEqualTo("ugId", uu.getUgId());
                                    List<UsergroupRole> ugR = usergroupRoleService.findByCondition(condition2);
                                    for (UsergroupRole ur : ugR
                                    ) {
                                        //2.2.1。判断角色是否禁用
                                        Role role = roleService.findBy("rId", ur.getrId());
                                        if (role != null) {
                                            haveRole = true;
                                        }
                                        if (1 == role.getIsWork()) {
                                            haveRoleIsWork = true;
                                            //如果未禁用，则查询该角色的对应的角色权限是否启用，且资源服务是否在线
                                            Condition condition3 = new Condition(RolePower.class);
                                            Example.Criteria criteria3 = condition3.createCriteria();
                                            criteria3.andEqualTo("rId", role.getpRId()).andEqualTo("pId", p_id);
                                            List<RolePower> rpL = rolePowerService.findByCondition(condition3);
                                            if (rpL.size() > 0) {
                                                haveRolePower = true;
                                                for (RolePower rp : rpL
                                                ) {
                                                    if (1 == rp.getIsWork()) {
                                                        haveRolePowerIsWork = true;
                                                        v_result = 1;
                                                        verifyLog.setVrDec("权限鉴定通过");
                                                        break lab;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!haveRoleIsWork) {
                                verifyLog.setVrDec("用户所有角色被禁用");
                            }

                            if (!haveRolePower) {
                                verifyLog.setVrDec("用户对应角色未匹配到对应权限");
                            }

                            if (!haveUgIsWork) {
                                verifyLog.setVrDec("用户所属全部用户组被禁用");
                            }


                            if (!haveRole) {
                                verifyLog.setVrDec("用户没有对应的角色");
                            }

                            if (!haveRolePowerIsWork) {
                                verifyLog.setVrDec("用户没有启用的该角色权限");
                            }
                        }

                    }
                } else {
                    return ResultGenerator.genFailResult("未找到对应的用户，请确认用户是否注册");
                }
            }
            verifyLog.setvResult(v_result);
            verifyLogService.save(verifyLog);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }
}
