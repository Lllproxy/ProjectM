package com.tc.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.*;
import com.tc.service.mysql.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tc.service.mysql.UserRoleService;
import com.tc.service.mysql.UsergroupRoleService;
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
@RequestMapping("/role")
@Api(tags = "角色管理")
public class RoleController {
    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UsergroupRoleService usergroupRoleService;

    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="", notes="查询角色详细")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "", required = true, dataType = "String")
	}) 
    public Result detail(@PathVariable String id) {
        Role role = roleService.findById(id);
        return ResultGenerator.genSuccessResult(role);
    }

    @GetMapping("/list")
    @ApiOperation(httpMethod="GET",value="查询角色列表", notes="page默认=0  size=10")
	@ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页号", required = true, dataType = "int"),
            @ApiImplicitParam(name = "size", value = "页大小", required = true, dataType = "int")
	}) 
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<Role> list = roleService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/add")
    @ApiOperation(httpMethod="POST",value="新增角色", notes="{r_name:角色名称（必传）,is_work:是否启用（默认1-启用）}")
    public Result add(@RequestBody  @ApiParam(name = "data",value ="新增json" , required = true) JSONObject data) {
        try {
            String rId= UUID.randomUUID().toString();
            if(null==data.get("r_name")||"".equals(data.get("r_name").toString())){
                return  ResultGenerator.genFailResult("r_name必传递");
            }
            Role role = new Role();
            //设置是否启用
            role.setIsWork(null==data.get("is_work")?1:Integer.valueOf(data.get("password").toString()));
            role.setpRId(rId);
            role.setrName(data.get("r_name").toString());
            roleService.save(role);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @ApiOperation(httpMethod="POST",value="更新角色信息", notes="{r_id:角色id（必传）,r_name: 角色名}")
    public Result update(@RequestBody  @ApiParam(name = "data",value ="更新json" , required = true) JSONObject data) {
        try {
            Role role = new Role();
            if(null==data.get("r_id")||"".equals(data.get("r_id").toString())){
                return  ResultGenerator.genFailResult("r_id必传递");
            }
            String rId= data.get("r_id").toString();
            //设置用户组id
            role.setrId(rId);
            //设置用户组名-检查用户组名是否存在
            //设置用户组名
            if (null!=data.get("r_name")){
                String r_name=data.get("r_name").toString();
                Role f_r=roleService.findBy("rName",r_name);
                if(null!=f_r){
                    if (!f_r.getrId().equals(rId)){
                        return ResultGenerator.genFailResult("r_name已存在，请更换名称");
                    }
                }else{
                    role.setrName(r_name);
                }
            }
            //设置是否启用
            if (null!=data.get("is_work")&&!"".equals(data.get("is_work").toString())){
                Integer isWork=data.getInteger("is_work");
                role.setIsWork(isWork);
            }
            roleService.update(role);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/delete/{r_id}")
    @ApiOperation(httpMethod="DELETE",value="删除角色", notes="默认DELETE方法 r_id不能为空,type=1 禁用 type=2 物理级联删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "r_id", value = "角色id", required = true, dataType = "String")
    })
    public Result delete(@PathVariable String  r_id) {
        String optype;
        try {
            optype="删除";
            //级联删除角色
            //1.删除角色用户关系
            //1.1.查询该角色对应的的角色用户关系list
            Condition condition=new Condition(UserRole.class);
            tk.mybatis.mapper.entity.Example.Criteria criteria = condition.createCriteria();
            criteria.andEqualTo("rId",r_id);
            List<UserRole> urList= userRoleService.findByCondition(condition);
            //1.2.循环删除用户组用户关系
            for (UserRole ur:urList
            ) {
                userRoleService.deleteById(ur.getrId());
            }
            //2.删除用户组角色
            //2.1.查询用户组角色关系list
            criteria=null;
            condition=null;
            System.gc();
            Condition condition2=new Condition(UsergroupRole.class);
            Example.Criteria criteria2 = condition2.createCriteria();
            criteria2.andEqualTo("rId",r_id);
            List<UsergroupRole> ugrList= usergroupRoleService.findByCondition(condition2);
            //2.2.循环删除
            for (UsergroupRole ur:ugrList
            ) {
                usergroupRoleService.deleteById(ur.getUgrId());
            }
            //3.删除角色
            roleService.deleteById(r_id);
        }catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult(e.getMessage());
        }
        return ResultGenerator.genSuccessResult(optype+"操作角色成功");
    }
}
