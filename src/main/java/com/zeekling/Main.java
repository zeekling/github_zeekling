package com.zeekling;

import com.zeekling.blog.BlogUpdateService;
import com.zeekling.blog.BlogUpdateServiceImpl;

/**
 * @author zeekling [lingzhaohui@zeekling.cn]
 * @version 1.0
 * @apiNote
 * @since 2020-07-26
 */
public class Main {

    public static void main(String[] args) {
        BlogUpdateService updateService = new BlogUpdateServiceImpl("/home/zeek/project/github_zeekling/src/main/resources/blog.properties");
        int res  = updateService.update();
        System.out.println(res);
    }

}
