package com.zeekling.util;

import com.zeekling.blog.BlogUpdateService;
import com.zeekling.blog.BlogUpdateServiceImpl;
import org.apache.commons.lang.StringUtils;
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
  public void genGitHubInfo() {
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

    final StringBuilder contentBuilder = new StringBuilder();
    String stats = "\n![Github Stats](https://github-readme-stats.vercel.app/api?username={username}&show_icons=true) \n\n";
    stats = stats.replace("{username}", "zeekling");
    contentBuilder.append("![GitHub Repo](/images/github_repo.jpg)\n\n");
    contentBuilder.append("## Github Stats\n").append(stats);
    contentBuilder.append("## 所有开源项目\n");
    contentBuilder.append("| 仓库 |  项目简介 | 收藏数 | fork数 | 项目主页 | 主要编程语言 |\n | ---- | ---- | ---- | ---- | ---- | ---- |\n");
    for (int i = 0; i < compatibleResult.length(); i++) {
      final JSONObject repo = compatibleResult.optJSONObject(i);
      final String url = repo.optString("githubrepoHTMLURL");
      final String desc = repo.optString("githubrepoDescription");
      final String name = repo.optString("githubrepoName");
      final String stars = repo.optString("githubrepoStargazersCount");
      final String forks = repo.optString("githubrepoForksCount");
      final String lang = repo.optString("githubrepoLanguage");
      final String hp = repo.optString("githubrepoHomepage");
      contentBuilder.append("| [").append(name).append("](").append(url).append(") | ")
          .append(desc).append(" | ")
          .append(stars).append(" | ")
          .append(forks).append(" | ")
          .append(hp).append(" | ")
          .append(lang).append("|\n");
    }

    FileUtils.saveDataToFile("/tmp/GITHUB.md", contentBuilder.toString());
  }

}
