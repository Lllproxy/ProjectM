package com.tc.web.controller;

import com.tc.common.CatchUrl;
import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.ShareInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tc.service.mysql.ShareInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
*
 * @author bocheng.luo
 * @date 2020/02/25
 */
@RestController
@RequestMapping("/share/info")
@Api(tags = "股票基本信息管理")
public class ShareInfoController {
    @Resource
    private ShareInfoService shareInfoService;

    @Value("${dfcf.shareinfo.url}")
    private String getShareInfoUrl;
    
    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="获取股基详细信息", notes="id为股基代码，必传")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "股基代码", required = true, dataType = "String")
	}) 
    public Result detail(@PathVariable Integer id) {
        ShareInfo shareInfo = shareInfoService.findById(id);
        return ResultGenerator.genSuccessResult(shareInfo);
    }

    @GetMapping("/list")
    @ApiOperation(httpMethod="GET",value="获取股基列表", notes="默认page=0,size=10，share_id:股基代码，share_type:SH/SZ,share_name:名称")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "页号", required = true, dataType = "String"),
        @ApiImplicitParam(name = "size", value = "页大小", required = true, dataType = "String"),
        @ApiImplicitParam(name = "share_id", value = "股基代码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "share_type", value = "交易所", required = true, dataType = "String"),
            @ApiImplicitParam(name = "share_name", value = "名称", required = true, dataType = "String")
	})
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,String share_id,String share_type,String share_name) {
        PageHelper.startPage(page, size);
        List<ShareInfo> list =new ArrayList<>();
        if (("".equals(share_id)||null==share_id)&&("".equals(share_type)||null==share_type)&&("".equals(share_name)||null==share_name)){
            list = shareInfoService.findAll();
        }else{
            Condition condition=new Condition(ShareInfo.class);
            Example.Criteria criteria=condition.createCriteria();
            if (!"".equals(share_id)||null!=share_id){
                criteria.andLike("shareId","%"+share_id+"%");
            }
            if (!"".equals(share_type)||null!=share_type) {
                criteria.andLike("shareType", "%" + share_type + "%");
            }
            if (!"".equals(share_name)||null!=share_name) {

                criteria.andLike("shareName", "%" + share_name + "%");
            }
            list=shareInfoService.findByCondition(condition);
        }
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/init")
    @ApiOperation(httpMethod="GET",value="初始化股票信息", notes="无参方法")
    public Result init() {
        long sta=System.currentTimeMillis();
        int count=0;
        try {
            //调用东方财富网接口网爬取取股票基金基本数据
            List<Map<String,String>> list=new CatchUrl().getShareInfo(getShareInfoUrl,"ul");
            List<ShareInfo> sL=new ArrayList<>();
            for (Map<String,String > map:list
            ) {
                count++;
                ShareInfo shareInfo=new ShareInfo();
                shareInfo.setShareId(map.get("share_id"));
                shareInfo.setShareName(map.get("share_name"));
                shareInfo.setHouse(map.get("share_house"));
                shareInfo.setShareUrl(map.get("share_url"));
                shareInfoService.save(shareInfo);
            }
            //shareInfoService.save(sL);
        }catch (Exception e){
                e.printStackTrace();
        }
        long end=System.currentTimeMillis();
        long total=end-sta;
        return ResultGenerator.genSuccessResult("初始化成功"+count+"条数据；总耗时"+total+"毫秒");
    }
}
