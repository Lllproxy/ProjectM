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
 * demo class
 *
 * @author: bocheng.luo
 * @date: 2020/2/26
 */
public class CatchUrl {

    //根据url获取数据
    public Document getHtmlTextByUrl(String url){
        Document document=null;
        try{
            int i=(int)(Math.random()*1000);////做一个随机延时，防止网站屏蔽
            while (i!=0) {
                i--;
            }
//            document= Jsoup.connect(url)
//                    .data("query","Java")
//                    .userAgent("Mozilla")
//                    .cookie("auth", "token")
//                    .timeout(300000).post();
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

    //根据元素属性获取某个元素内的elements列表
    public Elements getElementByClass(Document document, String className){
        Elements elements=null;
        elements=document.select(className);
        return elements;
    }

    //根据标签类型获取某个元素内的elements列表
    public Elements getElementsByTag(Document document, String tag){
        Elements elements=null;
        elements=document.getElementsByTag(tag);
        return elements;
    }

    public ArrayList getProvice(String url, String type){
        ArrayList result=new ArrayList();
        String classtype="."+type;
        //从网络上获取网页
        Document document=getHtmlTextByUrl(url);
        if (document!=null) {
            Elements elements=getElementByClass(document,classtype);// tr的集合
            for(Element e:elements){// 依次循环每个元素，也就是一个tr
                if(e!=null){
                    for(Element li:e.children()){// 一个tr的子元素td，td内包含a标签
                        String[] prv = new String[4];
                        if(li.children().first()!=null){
                            prv[0]=url;// 原来的url
                            prv[1]=li.children().first().ownText();
                            System.out.println(prv[1]);//身份名称

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


    public List getShareInfo(String url, String name){
        List<Map<String,String>> rsmL=new ArrayList<>();
        int count=0;
        //从网络上获取网页
        Document document=getHtmlTextByUrl(url);
        if (document!=null) {
            Elements elements=getElementsByTag(document,name);// ul的集合
            for(Element ul:elements){// 依次循环每个元素，也就是一个ul
                if(ul!=null){
                    System.out.println("ul  "+ul);
                    for(Element li:ul.children()){// 一个ul的子元素li，li内包含a标签
                        //System.out.println("li "+li);
                        if(li.children()!=null){
                            if (li.children().toString().contains("(")&&li.children().toString().contains(")")){
                                count++;
                                String shareUrl=li.children().first().attr("abs:href");
                                String rzu=li.children().first().ownText();
                                String shareCode=rzu.substring(rzu.indexOf("("),rzu.indexOf(")"));
                                String shareName=rzu.substring(0,rzu.indexOf("("));
                                String shareType="NULL";
                                if (shareUrl.contains("/sh")){
                                    shareType="SH";
                                }else if (shareUrl.contains("/sz")){
                                    shareType="SZ";
                                }
                                Map map =new HashMap();
                                map.put("share_id",shareCode);
                                map.put("share_name",shareName);
                                map.put("share_type",shareType);
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
        String url="http://quote.eastmoney.com/stock_list.html";
        String tag="ul";
//        String type="provincetr";
        System.out.println(new CatchUrl().getShareInfo(url, tag));
    }
}
