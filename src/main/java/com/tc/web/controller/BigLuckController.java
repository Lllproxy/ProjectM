package com.tc.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tc.common.CatchUrl;
import com.tc.core.Result;
import com.tc.core.ResultGenerator;
import com.tc.model.mysql.ShareErro;
import com.tc.model.mysql.ShareInfo;
import com.tc.model.mysql.ShareValueBase;
import com.tc.service.mysql.ShareErroService;
import com.tc.service.mysql.ShareInfoService;
import com.tc.service.mysql.ShareValueBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

}
