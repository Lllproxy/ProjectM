package com.tc.web.controller;

import com.tc.common.CatchUrl;
import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.ShareCompany;
import com.tc.model.mysql.ShareErro;
import com.tc.model.mysql.ShareInfo;
import com.tc.service.mysql.ShareCompanyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tc.service.mysql.ShareErroService;
import com.tc.service.mysql.ShareInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Resource
    private ShareInfoService shareInfoService;

    @Resource
    private ShareErroService shareErroService;

    @Value("${yhzq.sharecompany.url}")
    private String getSharecompanyUrl;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
        String erroUrl="";
        long sta=System.currentTimeMillis();
        int count=0;
        try {
            //调用东方财富网接口网爬取取股票基金基本数据
            List<Map<String,String>> list=new ArrayList<>();
            //获取所有的shareId;
            List<ShareInfo> sL=shareInfoService.findAll();
            for (ShareInfo sha: sL
                 ) {
                //每次循环前休眠三秒
//                Thread.sleep(1000);
                //循环每个share获取详细公司信息
                System.out.println("获取>>>   "+sha.getShareId()+"   的公司信息。  获取地址："+getSharecompanyUrl+sha.getShareId());
                Map<String,String> map=new HashMap<>();
                try {
                    map=new CatchUrl().getShareCompany(getSharecompanyUrl+sha.getShareId(),"ul");

                }catch (Exception e){
                    logger.error("失败异常： "+e.getMessage());
                    erroUrl=getSharecompanyUrl+sha.getShareId();
                }
                if ("".equals(map.get("comNameZh"))){
                    System.out.println(sha.getShareId()+">>>>>>>>获取数据空，可能股基下线。");
                    logger.error("股基可能下线： "+sha.getShareId());
                    continue;
                }
                System.out.println(map);
                if (map.size()==0){
                    logger.error("获取数据为空： "+getSharecompanyUrl+sha.getShareId());
                    continue;
                }
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
                if (null!=map.get("regCapital")&&!"".equals(map.get("regCapital"))){
                    shareCompany.setRegCapital(Float.valueOf(map.get("regCapital").replace(",","")));
                }
                shareCompany.setMarDate(map.get("marDate"));
                shareCompany.setMaket(map.get("maket"));
                if (null!=map.get("totShares")&&!"".equals(map.get("totShares"))){
                shareCompany.setTotShares(Float.valueOf(map.get("totShares").replace(",","")));
                }
                shareCompany.setShareId(map.get("shareId"));
                shareCompany.setIndustry(map.get("industry"));
                shareCompanyService.save(shareCompany);
                count++;
            }
        }catch (Exception e){
            logger.error("异常"+   e.getMessage());
            ShareErro shareErro=new ShareErro();
            shareErro.setUrl(erroUrl);
            shareErroService.save(shareErro);
            ResultGenerator.genFailResult(e.getMessage());
        }
        long end=System.currentTimeMillis();
        long total=end-sta;
        return ResultGenerator.genSuccessResult("初始化成功"+count+"条数据；总耗时"+total+"毫秒");
    }
}
