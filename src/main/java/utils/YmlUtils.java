package utils;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URL;

/**读取项目中的yml文件转为object
 * @author haoman
 * @Date 2018/7/3 下午4:54
 */
public class YmlUtils {
    public static Object read(URL url){
        Yaml yaml = new Yaml();
        try {
            return yaml.load(new FileInputStream(url.getFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Object readConfiguration(){
        URL url = YmlUtils.class.getClassLoader().getResource("configuration.yml");
        return read(url);
    }
    public static Object readMyLastRead(){
        URL url = YmlUtils.class.getClassLoader().getResource("test.yml");
        return read(url);
    }
    public static <T> void writeReadInfo(T obj){
        URL url = YmlUtils.class.getClassLoader().getResource("test.yml");
        write(obj,url);
    }
    public static <T> void  write(T obj,URL url){

        Yaml yaml = new Yaml();
        Writer out = null;
        String filePath = url.getFile();
        try {
            out = new FileWriter(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            yaml.dump(obj,out);
        }catch (Exception e){

        }
    }
}
