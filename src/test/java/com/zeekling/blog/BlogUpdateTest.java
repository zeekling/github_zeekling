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
        String pat = "068783457fa6e084aad1342ed2730f33a0255b97";
        final JSONObject gitHubUser = GitHubs.getGitHubUser(pat);
        if (null == gitHubUser) {
            return;
        }

        final String loginName = gitHubUser.optString("login");
        final String repoName = "zeekling";
        boolean ok = GitHubs.createOrUpdateGitHubRepo(pat, loginName, repoName, "跟新主页", "https://www.zeekling.cn/");
        if (!ok) {
            return;
        }
//        final String readme = genSoloBlogReadme(clientTitle, clientSubtitle, preference.optString(Option.ID_C_FAVICON_URL), loginName + "/" + repoName);
    }

}
