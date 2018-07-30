package service.impl.pojo;

import lombok.Data;

import java.util.Map;

/**
 * @author haoman
 * @Date 2018/7/3 下午5:15
 */
@Data
public class BooksPojo {
    public Long urlCode;
    public String title;
    public BooksPojo(Long urlCode,String title){
        this.urlCode = urlCode;
        this.title = title;
    }
    public BooksPojo(){

    }

}
