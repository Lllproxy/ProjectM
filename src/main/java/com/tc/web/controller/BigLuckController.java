package com.tc.web.controller;

import com.tc.common.CatchUrl;
import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.ShareCompany;
import com.tc.model.mysql.ShareErro;
import com.tc.model.mysql.BigLuck;
import com.tc.service.mysql.BigLuckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
*
 * @author bocheng.luo
 * @date 2020/03/05
 */
@RestController
@RequestMapping("/big/luck")
@Api(tags = "大乐透控制器")
public class BigLuckController {
    @Resource
    private BigLuckService bigLuckService;

    @Value("${api.luck.url}")
    private String luckUrl;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/bingo/{times}")
    @ApiOperation(httpMethod="GET",value="", notes="")
    public Result getResut(@PathVariable int times) {
        String re = null;
        Random r=new Random();
        int tag=r.nextInt(times);
        for (int i = 0; i < times; i++) {
            bingo(i);
            logger.info(bingo(i)+"(x)");
            if (i==tag){
               re=bingo(i);
            }
        }
        return ResultGenerator.genSuccessResult(re);
    }

    public static String bingo(int rowno) {
        //随机因子
        Random r=new Random();
        //前区6位
        Set<String> head_six=new HashSet<>();
        //后区2位
        Set<String> end_two=new HashSet<>();
        int[] arr=new int[35];
        for (int i = 0; i < 35; i++) {
            arr[i]=r.nextInt(36);
            if (head_six.size()<5&&arr[i]>0){
                if (arr[i]>=10){
                    head_six.add(String.valueOf(arr[i]));
                }else{
                    head_six.add("0"+arr[i]);
                }

            }
        }
        //计算后区域
        int[] end_arr=new int[12];
        for (int i = 0; i < 12; i++) {
            end_arr[i]=r.nextInt(13);
            if (end_two.size()<2&&end_arr[i]>0){
                if (end_arr[i]>=10){
                    end_two.add(String.valueOf(end_arr[i]));
                }else{
                    end_two.add("0"+end_arr[i]);
                }
            }
        }
        StringBuilder sb=new StringBuilder();

        TreeSet<String> head_sortedSet = new TreeSet<>(((o1, o2) -> o1.compareTo(o2)));
        head_sortedSet.addAll(head_six);
        for (String num:head_sortedSet
        ) {
            sb.append(num+" ");
        }
        TreeSet<String> end_sortedSet = new TreeSet<>(((o1, o2) -> o1.compareTo(o2)));
        end_sortedSet.addAll(end_two);
        for (String num:end_sortedSet
        ) {
            sb.append(num+" ");
        }

    return sb.toString();
    }

    @GetMapping("/init")
    @ApiOperation(httpMethod="GET",value="初始化股票公司信息", notes="无参方法")
    //周一到周五每天上午10:15分执行
    //@Scheduled(cron = "0 15 10 ? * MON-FRI ")
    public Result init() {
        long sta=System.currentTimeMillis();
        int count=0;
        try {
            for (int i= 7001; i < 20067; i++) {
                String number="";
                if (i<10000){
                    number="0"+i;
                }
                //每次循环前休眠三秒,防止ip被封
                Thread.sleep(1000);
                String url=luckUrl.replace("@",number);
                System.out.println("获取>>>   "+number+"   期数的大乐透顺序号。  获取地址："+url);
                Map<String,String> map=new HashMap<>();
                try {
                    map=new CatchUrl().getBigLuck(url);
                }catch (Exception e){
                    logger.error("失败异常： "+e.getMessage());
                    continue;
                }
                System.out.println(map);
                if (map.size()==0){
                    continue;
                }
                BigLuck bigLuck=new BigLuck();
                bigLuck.setNumber(number);
                bigLuck.setRed1(map.get("r1"));
                bigLuck.setRed2(map.get("r2"));
                bigLuck.setRed3(map.get("r3"));
                bigLuck.setRed4(map.get("r4"));
                bigLuck.setRed5(map.get("r5"));
                bigLuck.setBlue1(map.get("b1"));
                bigLuck.setBlue2(map.get("b2"));
                bigLuckService.save(bigLuck);
                count++;
            }
        }catch (Exception e){
            logger.error("异常"+   e.getMessage());

        }
        long end=System.currentTimeMillis();
        long total=end-sta;
        return ResultGenerator.genSuccessResult("初始化成功"+count+"条数据；总耗时"+total+"毫秒");
    }
}
