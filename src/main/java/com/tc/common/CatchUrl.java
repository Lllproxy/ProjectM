package com.tc.common;

import com.sun.codemodel.internal.JCase;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抓取网页数据（局限性针对东方财富的网页）
 *
 * @author: bocheng.luo
 * @date: 2020/2/26
 */
public class CatchUrl {

    /**
     * 根据url获取数据
     */
    public Document getHtmlTextByUrl(String url){
        Document document=null;
        try{
            int i;
            i = (int)(Math.random()*1000);
            //做一个随机延时，防止网站屏蔽
            while (i!=0) {
                i--;
            }
//            document= Jsoup.connect(url)
//                    .data("query","Java")
//                    .userAgent("Mozilla")
//                    .cookie("auth", "token")
//                    .timeout(300000).get();
            Connection conn = Jsoup.connect(url).timeout(30000);
            conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            conn.header("Accept-Encoding", "gzip, deflate, sdch");
            conn.header("Accept-Language", "zh-CN,zh;q=0.8");
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
            document=conn.get();
        }catch(Exception e){
            e.printStackTrace();
            try{
                document=Jsoup.connect(url).timeout(5000000).get();
            }catch(Exception e1){
                e1.printStackTrace();
            }
        }
        return document;
    }

    /**
     * 根据元素属性获取某个元素内的elements列表
     * @param document
     * @param className
     * @return
     */
    public Elements getElementByClass(Document document, String className){
        Elements elements=null;
        elements=document.select(className);
        return elements;
    }

    /**
     * 根据标签类型获取某个元素内的elements列表
     * @param document
     * @param tag
     * @return
     */
    public Elements getElementsByTag(Document document, String tag){
        Elements elements=null;
        elements=document.getElementsByTag(tag);
        return elements;
    }


    public Elements getElementsByClassId(Document document, String classId){
        Element element=document.getElementById(classId);
        Elements elements=document.getElementsByClass(".dumascroll_area");
        return elements;
    }

    public ArrayList getProvice(String url, String type){
        ArrayList result=new ArrayList();
        String classtype="."+type;
        //从网络上获取网页
        Document document=getHtmlTextByUrl(url);
        if (document!=null) {
            // tr的集合
            Elements elements=getElementByClass(document,classtype);
            for(Element e:elements){
                // 依次循环每个元素，也就是一个tr
                if(e!=null){
                    for(Element li:e.children()){
                        // 一个tr的子元素td，td内包含a标签
                        String[] prv = new String[4];
                        if(li.children().first()!=null){
                            // 原来的url
                            prv[0]=url;
                            prv[1]=li.children().first().ownText();
                            //省份名称
                            System.out.println(prv[1]);
                            String ownurl=li.children().first().attr("abs:href");
                            prv[2]=ownurl;
                            System.out.println(prv[2]);
                            prv[3]=type;
                            result.add(prv);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取股票基金基本信息
     * @param url
     * @param name
     * @return
     */
    public List getShareInfo(String url, String name){
        List<Map<String,String>> rsmL=new ArrayList<>();
        int count=0;
        //从网络上获取网页
        Document document=getHtmlTextByUrl(url);
        if (document!=null) {
            // ul的集合
            Elements elements=getElementsByTag(document,name);
            for(Element ul:elements){
                // 依次循环每个元素，也就是一个ul
                if(ul!=null){
//                    System.out.println("ul  "+ul);
                    for(Element li:ul.children()){
                        // 一个ul的子元素li，li内包含a标签
                        //System.out.println("li "+li);
                        if(li.children()!=null){
                            if (li.children().toString().contains("(")&&li.children().toString().contains(")")){
                                count++;
                                String shareUrl=li.children().first().attr("abs:href");
                                String rzu=li.children().first().ownText();
                                String shareCode=rzu.substring(rzu.indexOf("(")+1,rzu.indexOf(")"));
                                String shareName=rzu.substring(0,rzu.indexOf("("));
                                String shareHouse="NULL";
                                if (shareUrl.contains("/sh")){
                                    shareHouse="SH";
                                }else if (shareUrl.contains("/sz")){
                                    shareHouse="SZ";
                                }
                                Map map =new HashMap();
                                System.out.println("shareCode:   "+shareCode);
                                System.out.println("shareName: "+shareName);
                                System.out.println("share_house："+shareHouse);
                                System.out.println("shareUrl："+shareUrl);
                                map.put("share_id",shareCode);
                                map.put("share_name",shareName);
                                map.put("share_house",shareHouse);
                                map.put("share_url",shareUrl);
                                rsmL.add(map);
                                System.out.println("插入第 "+count+" 条数据");
                            }
                        }
                    }
                }
            }
        }
        return rsmL;
    }

    public static void main(String[] args) {
//        String url="http://quote.eastmoney.com/stock_list.html";
        String tag="ul";
        String url="http://www.chinastock.com.cn/yhwz/astock/index600168.shtml";
//        String className="";
//        String type="provincetr";
        System.out.println(new CatchUrl().getShareBaseValue(url));
    }

    public Map<String, String> getShareCompany(String url) {
        List<Map<String,String>> rsmL=new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        int count=0;
        try{
            //从网络上获取网页
            Document document=getHtmlTextByUrl(url);
            if (document!=null) {
                //获取表单tbody元素内容;
                Elements elements = getElementsByTag(document, "tbody");
                for (Element el : elements
                ) {
                    if (el.toString().contains("tdBg")) {
                        Elements element = el.children().tagName("td");
                        String[] sL = element.toString().replace("> <", ">δ<").split("δ");
                        List<String> rsL = new ArrayList<>();
                        for (String st : sL
                        ) {
//                        System.out.println(st);
                            if (st.equals("<td>") || "</td>".equals(st) || "<td>\n</td>".equals(st) || "</td>\n<td>".equals(st)) {
//                            System.out.println("移除无效标签");
                            } else {
//                            System.out.println("添加到待处理数组");
                                st = st.replace("<td class=\"tdBg\"><code>", "").replace("</code></td>", "");
                                st = st.replace("<td>", "").replace("</td>", "");
                                st = st.replace("<td colspan=\"3\">", "");
                                rsL.add(st);
                            }
//                        System.out.println("=============================================");
                        }
                        //检验结果
                        String[] skL = new String[rsL.size()];
                        int countt = 0;
                        for (String sg : rsL
                        ) {
                            skL[countt] = sg;
//                        System.out.println(sg);
//                        System.out.println(countt + "**************************");
                            countt++;
                        }
                        //做K-V映射
                        for (int i = 0; i < skL.length; i++) {
                            String key = getKey(skL[i]);
                            if (!"".equals(key)) {
                                map.put(key, skL[i + 1]);
                            }
                        }
//                    System.out.println(map);
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return map;
    }


    public Map<String, String> getShareBaseValue(String url) {
        List<Map<String,String>> rsmL=new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        int count=0;
        try{
            //从网络上获取网页
            Document document=getHtmlTextByUrl(url);
            if (document!=null) {
                //获取表单tbody元素内容;
                Elements elements = getElementsByTag(document, "tbody");
                for (Element el : elements
                ) {
                    //TODO根据不同的className，或者特殊标识抓去不同类数据
                        //System.out.println(el.toString());
                        //如果是基本信息的div标签
                    if (el.toString().contains("发 行 价")){
                        //定位到基本价值块
                        Elements els=el.children();
                        for (Element em:els
                             ) {
                            System.out.println(em.toString());
                            System.out.println("*******************");
                            String key=em.child(0).toString();
                            key=key.substring(key.indexOf("<b>")+3,key.indexOf("</b>"));
                            String keyR=getKey(key);
                            if (!"".equals(keyR)) {
                                map.put(keyR, em.child(1).ownText());
                            }
                        }
                        }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return map;
    }

    private String getKey(String stg) {
        String key;
        switch (stg){
            case "公司名称":
               key="comNameZh";
            break;
            case "英文名称":
               key="comNameEn";
            break;
            case "公司简称":
               key="comSNameZh";
            break;
            case "股票代码":
               key="shareId";
            break;
            case "注册资本(万元)":
               key="regCapital";
            break;
            case "成立日期":
               key="bulDate";
            break;
            case "上市日期":
               key="marDate";
            break;
            case "总股本(万股)":
               key="totShares";
            break;
            case "流通股(万股)":
               key="";
            break;
            case "所属区域":
               key="area";
            break;
            case "上市市场":
               key="maket";
            break;
            case "所属行业":
               key="industry";
            break;
            case "法人代表":
               key="corporateRep";
            break;
            case "信息披露网址":
               key="";
            break;
            case "公司董秘":
               key="";
            break;
            case "公司电话":
               key="comPhone";
            break;
            case "公司传真":
               key="comTax";
            break;
            case "董秘电子邮箱":
               key="";
            break;
            case "公司网址":
               key="comWebsite";
            break;
            case "注册地址":
               key="regAdress";
            break;
            case "经营范围":
               key="rang";
            break;
            case "公司介绍":
               key="comDesc";
            break;
            case "发 行 价：":
                key="push_price";
                break;
            case "每股收益：":
                key="profit_each";
                break;
            case "每股净资产：":
                key="real_price_each";
                break;
            default:
               key="";
            break;
        }
        return key;
    }

    public Map<String, String> getBigLuck(String url) {
        Map<String ,String> map=new HashMap<>();
        //从网络上获取网页
        Document document=getHtmlTextByUrl(url);
        if (document!=null) {
            Elements elements=getElementsByTag(document,"tbody");// ul的集合
            for(Element tbody:elements){// 依次循环每个元素，也就是一个tbody
                if(tbody!=null){
                    //System.out.println("table  "+tbody);
                    for(Element li:tbody.children()){// 一个ul的子元素li，li内包含a标签
                        //System.out.println("li "+li);
                        if(li.children()!=null&&li.toString().contains("出球顺序")){
                            //System.out.println("td "+li.children());
                            for (Element lis:li.children()
                            ) {
                                // System.out.println("lis>>>  "+lis);
                                if (lis.toString().contains("|")&&!lis.toString().contains("table")){
                                    System.out.println("lis>>>  "+lis.toString().replace("<td>","").replace("</td>",""));
                                    String[] mubers=lis.toString().replace("<td>","").replace("</td>","").split("\\|");
                                    String[] red5=mubers[0].split(" ");
                                    String[] blue2=mubers[1].split(" ");
                                    map.put("r1",red5[0]);
                                    map.put("r2",red5[1]);
                                    map.put("r3",red5[2]);
                                    map.put("r4",red5[3]);
                                    map.put("r5",red5[4]);
                                    map.put("b1",blue2[0]);
                                    map.put("b2",blue2[1]);
                                }
                            }

                        }
                    }
                }

            }
        }
        return  map;
    }
}
