package utils;

/**
 * @author haoman
 * @Date 2018/7/3 下午5:41
 */
public class ObjectUtils {
    public static Boolean isEmpty(Object obj){
        if (null == obj){
            return true;
        }
        return false;
    }
}
