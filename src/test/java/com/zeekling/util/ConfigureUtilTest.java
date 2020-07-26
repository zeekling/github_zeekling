package com.zeekling.util;

import com.zeekling.conf.BlogConfigure;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author zeekling [lingzhaohui@zeekling.cn]
 * @version 1.0
 * @apiNote
 * @since 2020-07-26
 */
public class ConfigureUtilTest {

    @Test
    public void getNewInstants(){
        try {
            BlogConfigure blogConfigure = ConfigureUtil.getNewInstants("/home/zeek/project/github_zeekling/src/main/resources/blog.properties", BlogConfigure.class);
            System.out.println(new JSONObject(blogConfigure));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
