package factory;

import lombok.extern.slf4j.Slf4j;
import manager.ConfigurationManager;
import service.Books;
import utils.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;

/**
 * @author haoman
 * @Date 2018/7/3 下午2:56
 */
@Slf4j
public class BooksFactory {
    private String baseClassName = "service.impl.ReadBooks.";
    private Observable observable;
    public BooksFactory(Observable ob){
        this.observable = ob;
    }
    public Books getBooks(String bookWebName){
        if (StringUtils.isEmpty(bookWebName)){
            return null;
        }
        try {
            return (Books)Class.forName(baseClassName+bookWebName).getDeclaredConstructor(new Class[]{ConfigurationManager.class}).newInstance(observable);
        } catch (InstantiationException e) {
            e.printStackTrace();
            //未找到网址的内容匹配
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.err.println("未找到这个带参构造器");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
