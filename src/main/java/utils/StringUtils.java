package utils;

/**
 * @author haoman
 * @Date 2018/7/3 下午3:03
 */
public class StringUtils {
    public static Boolean isEmpty(String words){
        if (null == words||words.replaceAll(" ","").equals("null")){
            return true;
        }
        return false;
    }
}
