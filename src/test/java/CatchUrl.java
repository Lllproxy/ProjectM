import org.jsoup.Connection;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

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
        String url="http://kaijiang.500.com/shtml/dlt/20066.shtml";
        String tag="ul";
//        String type="provincetr";
        System.out.println(new CatchUrl().getBigLuck(url, tag));
//        long today=System.currentTimeMillis();
//        for (int i = 0; i <today ; i++) {
//            bingo(i);
//        }
    }

    private Map<String, String> getBigLuck(String url, String tag) {
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

    public static void bingo(int rowno) {
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

    }
}
