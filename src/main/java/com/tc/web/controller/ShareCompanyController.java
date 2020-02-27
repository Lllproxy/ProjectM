package com.tc.web.controller;

import com.tc.common.CatchUrl;
import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.ShareCompany;
import com.tc.model.mysql.ShareInfo;
import com.tc.service.mysql.ShareCompanyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
*
 * @author bocheng.luo
 * @date 2020/02/27
 */
@RestController
@RequestMapping("/share/company")
@Api(tags = "股票/基金公司信息管理")
public class ShareCompanyController {
    @Resource
    private ShareCompanyService shareCompanyService;

    @Value("${yhzq.sharecompany.url}")
    private String getSharecompanyUrl;
    
    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="查询股票/基金公司详细", notes="id 股票、基金代码 必传")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "", required = true, dataType = "String")
	}) 
    public Result detail(@PathVariable String id) {
        ShareCompany shareCompany = shareCompanyService.findById(id);
        return ResultGenerator.genSuccessResult(shareCompany);
    }

    @GetMapping("/list")
    @ApiOperation(httpMethod="GET",value="查询股票/基金公司列表", notes="默认page=0,size=10，share_id:股基代码，industry：所属行业，area：区域，rang：经验范围")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "页号", required = true, dataType = "Integer"),
        @ApiImplicitParam(name = "size", value = "页大小", required = true, dataType = "Integer"),
        @ApiImplicitParam(name = "share_id", value = "股基代买", required = true, dataType = "String"),
        @ApiImplicitParam(name = "industry", value = "所属行业", required = true, dataType = "String"),
        @ApiImplicitParam(name = "area", value = "区域", required = true, dataType = "String"),
        @ApiImplicitParam(name = "rang", value = "经验范围", required = true, dataType = "String")
    })
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size,String share_id,String industry,String area,String rang) {
        PageHelper.startPage(page, size);
        List<ShareCompany> list = new ArrayList<>();
        if (("".equals(share_id)||null==share_id)&&("".equals(industry)||null==industry)&&("".equals(area)||null==area)&&("".equals(rang)||null==rang)){
            list = shareCompanyService.findAll();
        }else {
            Condition condition=new Condition(ShareCompany.class);
            Example.Criteria criteria=condition.createCriteria();
            if (!"".equals(share_id)||null!=share_id){
                criteria.andLike("shareId","%"+share_id+"%");
            }
            if (!"".equals(industry)||null!=industry) {
                criteria.andLike("industry", "%" + industry + "%");
            }
            if (!"".equals(area)||null!=area) {

                criteria.andLike("area", "%" + area + "%");
            }
            if (!"".equals(rang)||null!=rang) {

                criteria.andLike("rang", "%" + rang + "%");
            }
            list=shareCompanyService.findByCondition(condition);

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
            List<Map<String,String>> list=new CatchUrl().getShareCompany(getSharecompanyUrl,"ul");
            List<ShareInfo> sL=new ArrayList<>();
            for (Map<String,String > map:list
            ) {
                count++;
                ShareCompany shareCompany=new ShareCompany();
                shareCompany.setArea(map.get("area"));
                shareCompany.setBulDate(map.get("bulDate"));
                shareCompany.setComDesc(map.get("comDesc"));
                shareCompany.setComNameEn(map.get("comNameEn"));
                shareCompany.setComNameZh(map.get("comNameZh"));
                shareCompany.setComPhone(map.get("comPhone"));
                shareCompany.setComSNameZh(map.get("comSNameZh"));
                shareCompany.setComTax(map.get("comTax"));
                shareCompany.setComWebsite(map.get("comWebsite"));
                shareCompany.setCorporateRep(map.get("corporateRep"));
                shareCompany.setRang(map.get("rang"));
                shareCompany.setRegAdress(map.get("regAdress"));
                shareCompany.setRegCapital(Float.valueOf(map.get("regCapital")));
                shareCompany.setMarDate(map.get("marDate"));
                shareCompany.setMaket(map.get("maket"));
                shareCompany.setTotShares(Float.valueOf(map.get("totShares")));
                shareCompany.setShareId(map.get("shareId"));
                shareCompany.setIndustry(map.get("industry"));
                shareCompanyService.save(shareCompany);
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
