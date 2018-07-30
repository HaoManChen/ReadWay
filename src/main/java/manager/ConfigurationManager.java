package manager;

import lombok.extern.slf4j.Slf4j;
import utils.YmlUtils;

import java.util.Observable;

/**
 * @author haoman
 * @Date 2018/7/3 下午4:48
 */
@Slf4j
public class ConfigurationManager extends Observable {
    /**
     * 配置信息
     */
    private Object configurationInfo;
    public void change(){
        Object newConfiguration = YmlUtils.readConfiguration();
        if (newConfiguration==null){
            log.info("没有读取到配置信息");
        }else {
            if (!newConfiguration.equals(configurationInfo)){
                this.configurationInfo = newConfiguration;
                setChanged();
                notifyObservers(configurationInfo);
            }
        }
    }
}
