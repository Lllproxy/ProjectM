import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

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
            document= Jsoup.connect(url)
                    .data("query","Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(300000).post();
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

    public ArrayList getProvice(String url, String type){
        ArrayList result=new ArrayList();
        String classtype="."+type;
        //从网络上获取网页
        Document document=getHtmlTextByUrl(url);
        if (document!=null) {
            Elements elements=getElementByClass(document,classtype);// tr的集合
            for(Element e:elements){// 依次循环每个元素，也就是一个tr
                if(e!=null){
                    for(Element ec:e.children()){// 一个tr的子元素td，td内包含a标签
                        String[] prv = new String[4];
                        if(ec.children().first()!=null){
                            prv[0]=url;// 原来的url
                            prv[1]=ec.children().first().ownText();
                            System.out.println(prv[1]);//身份名称

                            String ownurl=ec.children().first().attr("abs:href");
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

    public static void main(String[] args) {
        String url="http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/index.html";
        String type="provincetr";
        System.out.println(new CatchUrl().getProvice(url, type));
    }
}
