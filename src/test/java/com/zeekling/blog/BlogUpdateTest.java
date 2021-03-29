package com.zeekling.blog;

import com.zeekling.util.GitHubs;
import org.json.JSONObject;
import org.testng.annotations.Test;

/**
 * @author zeekling [lingzhaohui@zeekling.cn]
 * @version 1.0
 * @apiNote
 * @since 2020-07-25
 */
public class BlogUpdateTest {

    @Test
    public void updateGitHub(){
        String configPath = "/home/zeek/project/github_zeekling/src/main/resources/blog.properties";
        BlogUpdateService updateService = new BlogUpdateServiceImpl(configPath);
        int res  = updateService.update();
        if (res == 0){
            System.out.println("update github success!");
            return;
        }
        System.out.println("update github failed!");
    }

}
