package application;

import enums.BookWebEnum;
import factory.BooksFactory;
import manager.ConfigurationManager;
import pojo.MyInfo;
import service.Books;
import utils.ConfigUtils;
import utils.YmlUtils;

import java.util.*;
import java.util.concurrent.*;

/**
 * @author haoman
 * @Date 2018/7/3 下午6:34
 */
public class BookReadApplication implements Observer {
    private  volatile boolean findButton = false;
    private volatile Integer readTitle = 3;
    public void run(){
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.addObserver(this);
        BooksFactory  booksFactory = new BooksFactory(configurationManager);
        Books book = booksFactory.getBooks(BookWebEnum.笔趣阁.getName());
        configurationManager.change();
        //要看的小说名称
        book.findBooks("修真聊天群");
        //书的章节
        book.readBooks(readTitle);
        ExecutorService pool=Executors.newFixedThreadPool(2);
//        Thread t1=new Thread();

//        ExecutorService singleThreadPool = new ThreadPoolExecutor(3, 2,
//                1000L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>(1024), new ThreadPoolExecutor.AbortPolicy());
        pool.execute(()->readTitle(book));
        pool.execute(()->nextListener(book));

        pool.shutdown();

    }
    private void readTitle(Books book){

        while (true){
            if (findButton){
                for (int i = 0; i < 300; i++) {
                    System.out.println("");
                }
                System.err.println("正在寻找"+readTitle+"章");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                book.readBooks(readTitle);
                findButton = false;

            }
        }
    }
    private void nextListener(Books book){
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()) {
            String out = scan.next();
            if ("n".equals(out)){
                readTitle = readTitle+1;
                findButton = true;
            }else if ("b".equals(out)){
                readTitle = readTitle-1;
                findButton = true;
            }else if ("s".equals(out)){
                MyInfo myInfo = new MyInfo();
                myInfo.setReadTitle(readTitle);
                YmlUtils.writeReadInfo(myInfo);
            }else if("r".equals(out)){
                MyInfo myInfo = (MyInfo)YmlUtils.readMyLastRead();
                readTitle = myInfo.getReadTitle();
                findButton = true;
            }
            else{
                try {
                    readTitle = Integer.parseInt(out);
                    findButton = true;

                }catch (Exception e){
                    for (int i = 0; i < 300; i++) {
                        System.out.println("");
                    }
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.readTitle = Integer.parseInt(ConfigUtils.getConfig(arg,"my","readTitle"));
    }
}
