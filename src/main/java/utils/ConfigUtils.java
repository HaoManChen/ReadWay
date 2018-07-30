package utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**读取配置
 * @author haoman
 * @Date 2018/7/3 下午5:32
 */
@Slf4j
public class ConfigUtils {

    public static String getConfig(Object config,String... keys){
        Map configMap = (Map)config;
        for (int i = 0; i < keys.length; i++) {
            Object searchConfig = configMap.get(keys[i]);
            if (ObjectUtils.isEmpty(searchConfig)){
                return null;
            }
            try {
                configMap = (Map)searchConfig;
            }catch (Exception e){
                if (i == keys.length-1){
                    return searchConfig.toString();
                }else {
                    log.info("配置格式错误，错误结构是{}",keys);
                    return null;
                }
            }
        }
        return null;

    }

}
