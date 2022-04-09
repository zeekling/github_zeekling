package com.zeekling.blog;

import org.json.JSONArray;

/**
 * @author zeekling [lingzhaohui@zeekling.cn]
 * @version 1.0
 * @apiNote
 * @since 2020-07-25
 */
public interface BlogUpdateService {


    int update();

    JSONArray getGitHubRepos();

}
