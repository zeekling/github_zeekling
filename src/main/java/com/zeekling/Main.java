package com.zeekling;

import com.zeekling.blog.BlogUpdateService;
import com.zeekling.blog.BlogUpdateServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author zeekling [lingzhaohui@zeekling.cn]
 * @version 1.0
 * @apiNote
 * @since 2020-07-26
 */
public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        String configPath = System.getProperty("configPath");
        BlogUpdateService updateService = new BlogUpdateServiceImpl(configPath);
        int res  = updateService.update();
        if (res == 0){
            LOGGER.info("update github success!");
            return;
        }
        LOGGER.error("update github failed!");
    }

}
