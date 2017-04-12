package ren.ashin.shanbay.crawler.util;

import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Mutable;

/**
 * @ClassName: MainConfig
 * @Description: TODO
 * @author renzx
 * @date Apr 12, 2017
 */
@Sources({"file:conf/config.properties"})
public interface MainConfig extends Mutable {

    @Key("cronTask")
    String cronTask();

    @Key("cronUser")
    String cronUser();
}