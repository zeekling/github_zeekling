package com.zeekling.util;

import com.zeekling.blog.BlogUpdateService;
import com.zeekling.blog.BlogUpdateServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

/**
 * @author zeekling [lingzhaohui@zeekling.cn]
 * @version 1.0
 * @apiNote
 * @since 2020-07-25
 */
public class GitHubsTest {

  @Test
  public void getAll() {
    String configPath = "/home/zeekling/project/ling/github_zeekling/src/main/resources/blog.properties";
    BlogUpdateService updateService = new BlogUpdateServiceImpl(configPath);
    JSONArray result = updateService.getGitHubRepos();
    JSONArray compatibleResult = new JSONArray();
    for (int i = 0; i < result.length(); i++){
      JSONObject resultObject = result.optJSONObject(i);
      JSONObject compatibleObject = new JSONObject();

      if (resultObject.getBoolean("fork")) {
        continue;
      }
      compatibleObject.put("githubrepoId", resultObject.optString("id"));
      compatibleObject.put("githubrepoStatus", 0);
      compatibleObject.put("oId", "" + System.currentTimeMillis());
      compatibleObject.put("githubrepoDescription", resultObject.optString("description"));
      compatibleObject.put("githubrepoHomepage", resultObject.optString("homepage"));
      compatibleObject.put("githubrepoForksCount", resultObject.optLong("forks_count"));
      compatibleObject.put("githubrepoOwnerId", resultObject.optJSONObject("owner").optString("id"));
      compatibleObject.put("githubrepoStargazersCount", resultObject.optLong("stargazers_count"));
      compatibleObject.put("githubrepoWatchersCount", resultObject.optLong("watchers_count"));
      compatibleObject.put("githubrepoOwnerLogin", resultObject.optJSONObject("owner").optString("login"));
      compatibleObject.put("githubrepoHTMLURL", resultObject.optString("html_url"));
      compatibleObject.put("githubrepoLanguage", resultObject.optString("language"));
      compatibleObject.put("githubrepoName", resultObject.optString("name"));
      compatibleObject.put("githubrepoFullName", resultObject.optString("full_name"));

      compatibleResult.put(compatibleObject);
    }
    System.out.println(compatibleResult);
  }

}
