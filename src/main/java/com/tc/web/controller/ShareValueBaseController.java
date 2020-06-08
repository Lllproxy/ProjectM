package com.tc.web.controller;

import com.tc.common.CatchUrl;
import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.ShareCompany;
import com.tc.model.mysql.ShareErro;
import com.tc.model.mysql.ShareInfo;
import com.tc.model.mysql.ShareValueBase;
import com.tc.service.mysql.ShareErroService;
import com.tc.service.mysql.ShareInfoService;
import com.tc.service.mysql.ShareValueBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*
 * @author bocheng.luo
 * @date 2020/03/05
 */
@RestController
@RequestMapping("/share/value/base")
@Api(tags = "股票基本价值信息管理")
public class ShareValueBaseController {
    @Resource
    private ShareValueBaseService shareValueBaseService;

    @Resource
    private ShareInfoService shareInfoService;

    @Resource
    private ShareErroService shareErroService;

    @Value("${yhzq.sharebasevalue.url}")
    private String getShareBaseValueUrl;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    @ApiOperation(httpMethod="GET",value="", notes="")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "", required = true, dataType = "String")
	}) 
    public Result detail(@PathVariable Integer id) {
        ShareValueBase shareValueBase = shareValueBaseService.findById(id);
        return ResultGenerator.genSuccessResult(shareValueBase);
    }

    @GetMapping("/list")
    @ApiOperation(httpMethod="GET",value="", notes="")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "{page}", value = "页号", required = true, dataType = "String"),
        @ApiImplicitParam(name = "{size}", value = "页大小", required = true, dataType = "String")
	}) 
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ShareValueBase> list = shareValueBaseService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/init")
    @ApiOperation(httpMethod="GET",value="初始化股票基本价值信息", notes="无参方法")
    //周一到周五每天上午10:15分执行
    @Scheduled(cron = "0 15 10 ? * MON-FRI ")
    public Result init() {
        String erroUrl="";
        long sta=System.currentTimeMillis();
        int count=0;
        try {
            //调用东方财富网接口网爬取取股票基金基本数据
            List<Map<String,String>> list=new ArrayList<>();
            //获取所有的shareId;
            List<ShareInfo> sL=shareInfoService.findAll();
            List<String> sIdL=new ArrayList<>();
            List<ShareValueBase> scL=shareValueBaseService.findAll();
            List<String> scIdL=new ArrayList<>();
            for (ShareInfo shareInfo:sL
            ) {
                sIdL.add(shareInfo.getShareId());
            }
            for (ShareValueBase sha:scL
            ) {
                scIdL.add(sha.getShareId());
            }

            sIdL.removeAll(scIdL);
            System.out.println("剩余获取数量：>>>>>>>   "+sIdL.size());
            for (String  shaId: sIdL
            ) {
                //每次循环前休眠三秒
                Thread.sleep(3000);
                System.out.println("获取>>>   "+shaId+"   的基本价值信息。  获取地址："+getShareBaseValueUrl.replace("@",shaId));
                Map<String,String> map=new HashMap<>();
                try {
                    map=new CatchUrl().getShareBaseValue(getShareBaseValueUrl.replace("@",shaId));

                }catch (Exception e){
                    logger.error("失败异常： "+e.getMessage());
                    erroUrl=getShareBaseValueUrl.replace("@",shaId);
                }
                if ("".equals(map.get("comNameZh"))){
                    System.out.println(shaId+">>>>>>>>获取数据空，可能股基下线。");
                    logger.error("股基可能下线： "+shaId);
                    continue;
                }
                System.out.println(map);
                if (map.size()==0){
                    logger.error("获取数据为空： "+getShareBaseValueUrl.replace("@",shaId));
                    continue;
                }
                ShareValueBase shareValueBase=new ShareValueBase();
                shareValueBase.setShareId(shaId);
                if (null!=map.get("real_price_each")&&!"".equals(map.get("real_price_each").toString().replace("元",""))){
                    shareValueBase.setRealPriceEach(Float.valueOf(map.get("real_price_each").replace("元","")));
                }else{
                    shareValueBase.setRealPriceEach(0f);
                }
                if (null!=map.get("push_price")&&!"".equals(map.get("push_price").toString().replace("元",""))){
                    shareValueBase.setPushValue(Float.valueOf(map.get("push_price").replace("元","")));
                }else{
                    shareValueBase.setPushValue(0f);
                }
                if (null!=map.get("profit_each")&&!"".equals(map.get("profit_each").toString().replace("元",""))){
                    shareValueBase.setPorfitEach(Float.valueOf(map.get("profit_each").replace("元","")));
                }else{
                    shareValueBase.setPorfitEach(0f);
                }
                shareValueBaseService.save(shareValueBase);
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
