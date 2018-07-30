package service.impl.ReadBooks;

import lombok.extern.slf4j.Slf4j;
import manager.ConfigurationManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import service.Books;
import service.impl.pojo.BooksPojo;
import utils.ConfigUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author haoman
 * @Date 2018/7/3 下午2:43
 */
@Slf4j
public class BeQuGeBooks  implements Books,Observer {
    private String searchUrl;
    private String newBookUrl;
    private Integer outTime  = 10000;
    private List<BooksPojo> bookInfos;

    public BeQuGeBooks(ConfigurationManager ob){
        ob.addObserver(this);
        ob.change();
    }

    @Override
    public void findBooks(String bookName) {
        searchUrl = searchUrl.replaceAll("\\{0\\}",bookName);
        try {
            Document doc= Jsoup.parse(new URL(searchUrl),outTime);
            String pattern = "http://www.shuquge.com/modules/article/reader.php\\?aid=[0-9]+";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(doc.toString());
            if (m.find()){
                String bastUrl = m.group(0);
                //因为盗版被屏蔽，所以这个页面无法打开，但是根据aid置换一个url就能打开笔趣阁
                //进行二次匹配
                String pattern2 = "[0-9]+";
                r = Pattern.compile(pattern2);
                m = r.matcher(bastUrl.toString());
                if (m.find()){
                    newBookUrl = newBookUrl.replaceAll("\\{0\\}",m.group(0));
                }
            }
            doc= Jsoup.parse(new URL(newBookUrl),outTime);
            String pattern3 = " <a href=\"[0-9]+.html\">第[\\u4e00-\\u9fa5_a-zA-Z0-9]+\\s+[\\u4e00-\\u9fa5_a-zA-Z0-9]+";
            r = Pattern.compile(pattern3);
            m = r.matcher(doc.outerHtml());
            String info;
            String[] infos;
            bookInfos = new ArrayList<>();
            while (m.find()){
                info = m.group();
                info = info.replaceAll("<a href=\"","");
                infos = info.split(".html\">");
                bookInfos.add(new BooksPojo(Long.parseLong(infos[0].replaceAll(" ","")),infos[1]));
            }
            bookInfos.sort((a,b)->{
                return a.getUrlCode()>b.getUrlCode()?1:-1;
            });
            System.out.println("获取图书列表成功");

        } catch (IOException e) {
            System.err.println("站内屏蔽，将继续尝试刷取页面");
            System.err.println(searchUrl);
            this.findBooks(bookName);
        }

    }

    @Override
    public void readBooks(Integer bookCount) {
        if (bookInfos == null){
            System.err.println("看书前请查找书");
            return;
        }
        BooksPojo bookInfo = bookInfos.get(bookCount-1);
        try {
            Document doc= Jsoup.parse(new URL(new StringBuilder(newBookUrl).append("/").append(bookInfo.getUrlCode()).append(".html").toString()),outTime);
            System.out.println(doc.outerHtml().replaceAll("&nbsp;","").replaceAll("<br>","").replaceAll("<[\\sa-zA-Z\\d~\\!@#\\$%\\^&\\*\\(\\)_\\-\\+=\\{\\[\\}\\]\\|\\\\:;\\\"\\'\\<,\\>\\.\\?\\/]+>","").replaceAll("<[\\s\\S]*>",""));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(Observable o, Object arg) {
        this.searchUrl = ConfigUtils.getConfig(arg,"biquge","searchUrl");
        this.newBookUrl = ConfigUtils.getConfig(arg,"biquge","newBookUrl");

    }
}
