package com.zeekling.blog;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.FeedException;
import com.zeekling.conf.BlogConfigure;
import com.zeekling.util.ConfigureUtil;
import com.zeekling.util.FeedXmlUtil;
import com.zeekling.util.FileUtils;
import com.zeekling.util.GitHubs;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * @author zeekling [lingzhaohui@zeekling.cn]
 * @version 1.0
 * @apiNote
 * @since 2020-07-26
 */
public class BlogUpdateServiceImpl implements BlogUpdateService {

    private static final Logger LOGGER = LogManager.getLogger(BlogUpdateServiceImpl.class);

    private BlogConfigure blogConfigure = null;

    public BlogUpdateServiceImpl(String confPath) {
        // init blogConfigure
        try {
            blogConfigure = ConfigureUtil.getNewInstants(confPath, BlogConfigure.class);
        } catch (IllegalAccessException | InstantiationException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int update() {
        if (blogConfigure == null) {
            return -1;
        }
        final JSONObject gitHubUser = GitHubs.getGitHubUser(blogConfigure.getPat());
        if (null == gitHubUser) {
            LOGGER.error("login failed");
            return -1;
        }
        LOGGER.info("login success");
        final String loginName = gitHubUser.optString("login");
        boolean ok = GitHubs.createOrUpdateGitHubRepo(blogConfigure.getPat(), loginName, blogConfigure.getRepoName(),
                blogConfigure.getClientSubtitle(), blogConfigure.getHome());
        if (!ok) {
            LOGGER.error("get " + blogConfigure.getRepoName() + " failed");
            return -1;
        }
        final String readme = genSoloBlogReadme(loginName + "/" + blogConfigure.getRepoName());
        LOGGER.log(Level.INFO, "begin get README.md");
        final String oldReadme = FileUtils.readFile(blogConfigure.getGithubTmp() + "/README.md");
        if (oldReadme != null && oldReadme.equals(readme)) {
        	LOGGER.info("not need update readme.");
        	return 0;
        }
        ok = GitHubs.updateFile(blogConfigure.getPat(), loginName, blogConfigure.getRepoName(), "README.md", readme.getBytes(StandardCharsets.UTF_8));
        if (ok) {
            LOGGER.log(Level.INFO, "Exported public articles to your repo [" + blogConfigure.getRepoName() + "]");
        }
        FileUtils.saveDataToFile(blogConfigure.getGithubTmp() + "/README.md", readme);
        return 0;
    }

    private String genSoloBlogReadme(final String repoFullName) {
        final StringBuilder bodyBuilder = new StringBuilder("### 最新文章\n");
        try {
            List<SyndEntry> entries = FeedXmlUtil.parseXml(blogConfigure.getRss());
            for (SyndEntry syndEntry: entries){
                bodyBuilder.append("\n* \uD83D\uDCDD [")
                    .append(syndEntry.getTitle())
                    .append("](")
                    .append(syndEntry.getLink())
                    .append(") \n ");
            }
        } catch (FeedException | MalformedURLException e) {
            e.printStackTrace();
        }
        bodyBuilder.append("\n\n");

        String ret = "### Hey \uD83D\uDC4B, I'm [${title}](${home})! \n" +
            "\n![Github Stats](https://github-readme-stats.vercel.app/api?username=${username}&show_icons=true) \n\n" +
            "### 我在博客[${title}](${home})的近期动态\n" +
            "⭐️ Star [个人主页](https://github.com/${username}) 后会自动更新" +
            "\n\n<p align=\"center\"><img alt=\"${title}\" src=\"${favicon}\"></p>" +
            "<h2 align=\"center\"> ${title} </h2>\n" +
            "\n" +
            "<h4 align=\"center\">${subtitle}</h4>\n" +
            "<p align=\"center\">" +
            "<a title=\"${title}\" target=\"_blank\" href=\"https://github.com/${repoFullName}\"><img src=\"https://img.shields.io/github/last-commit/${repoFullName}.svg?style=flat-square&color=FF9900\"></a>\n" +
            "<a title=\"GitHub repo size in bytes\" target=\"_blank\" href=\"https://github.com/${repoFullName}\"><img src=\"https://img.shields.io/github/repo-size/${repoFullName}.svg?style=flat-square\"></a>\n" +
            "<a title=\"Hits\" target=\"_blank\" href=\"https://github.com/${username}/hits\">" +
            "<img src=\"https://hits.b3log.org/${repoFullName}.svg\"></a>" +
            "</p>\n" +
            "\n" +
            "${body}\n\n" +
            "\n";
        ret = ret.replace("${title}", blogConfigure.getClientTitle()).
            replace("${subtitle}", blogConfigure.getClientSubtitle()).
            replace("${favicon}", blogConfigure.getFavicon()).
            replace("${repoFullName}", repoFullName).
            replace("${body}", bodyBuilder.toString()).
            replace("${username}", blogConfigure.getRepoName()).
            replace("${home}", blogConfigure.getHome());
        return ret;
    }

    @Override
    public JSONArray getGitHubRepos() {
        if (blogConfigure == null) {
            return null;
        }
        final JSONObject gitHubUser = GitHubs.getGitHubUser(blogConfigure.getPat());
        if (null == gitHubUser) {
            LOGGER.error("login failed");
            return null;
        }
        LOGGER.info("login success");
        final String myGitHubID = gitHubUser.optString("login");
        return GitHubs.getGitHubRepos(myGitHubID);
    }

}
