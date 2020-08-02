package com.zeekling.blog;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.FeedException;
import com.zeekling.conf.BlogConfigure;
import com.zeekling.util.ConfigureUtil;
import com.zeekling.util.FeedXmlUtil;
import com.zeekling.util.GitHubs;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zeekling [lingzhaohui@zeekling.cn]
 * @version 1.0
 * @apiNote
 * @since 2020-07-26
 */
public class BlogUpdateServiceImpl implements BlogUpdateService {

    private static final Logger LOGGER = LogManager.getLogger(BlogUpdateServiceImpl.class);

    private BlogConfigure blogConfigure = null;

    private static String HTML_REGEX="<[^>]+>";

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
        ok = GitHubs.updateFile(blogConfigure.getPat(), loginName, blogConfigure.getRepoName(), "README.md", readme.getBytes(StandardCharsets.UTF_8));
        if (ok) {
            LOGGER.log(Level.INFO, "Exported public articles to your repo [" + blogConfigure.getRepoName() + "]");
        }
        return 0;
    }

    private String genSoloBlogReadme(final String repoFullName) {
        final StringBuilder bodyBuilder = new StringBuilder("### 最新\n");
        try {
            List<SyndEntry> entries = FeedXmlUtil.parseXml(blogConfigure.getRss());
            for (SyndEntry syndEntry: entries){
                String des = syndEntry.getDescription().getValue();
                des = des.replaceAll("\n", "\n    > ");
                bodyBuilder.append("\n* \uD83D\uDCDD [").append(syndEntry.getTitle()).append("](").append(syndEntry.getLink())
                        .append(") \n    > ").append(des);
            }
        } catch (FeedException | MalformedURLException e) {
            e.printStackTrace();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = format.format(new Date());
        bodyBuilder.append("\n\n");

        String ret = "### Hey \uD83D\uDC4B, I'm [Zeek Ling](https://www/zeekling.cn)! \n" +
                "![Github Stats](https://github-readme-stats.vercel.app/api?username=zeekling&show_icons=true) \n" +
                "### 我在[小令童鞋](https://www.zeekling.cn)的近期动态\n" +
                "\n" +
                "⭐️ Star [个人主页](https://github.com/zeekling/zeekling) 后会自动更新，最近更新时间：`" + currentDate + "`\n" +
                "\n<p align=\"center\"><img alt=\"${title}\" src=\"${favicon}\"></p><h2 align=\"center\">" +
                "${title}\n" +
                "</h2>\n" +
                "\n" +
                "<h4 align=\"center\">${subtitle}</h4>\n" +
                "<p align=\"center\">" +
                "<a title=\"${title}\" target=\"_blank\" href=\"https://github.com/${repoFullName}\"><img src=\"https://img.shields.io/github/last-commit/${repoFullName}.svg?style=flat-square&color=FF9900\"></a>\n" +
                "<a title=\"GitHub repo size in bytes\" target=\"_blank\" href=\"https://github.com/${repoFullName}\"><img src=\"https://img.shields.io/github/repo-size/${repoFullName}.svg?style=flat-square\"></a>\n" +
                "<a title=\"Hits\" target=\"_blank\" href=\"https://github.com/zeekling/hits\"><img src=\"https://hits.b3log.org/${repoFullName}.svg\"></a>" +
                "</p>\n" +
                "\n" +
                "${body}\n\n" +
                "\n" ;
        ret = ret.replace("${title}", blogConfigure.getClientTitle()).
                replace("${subtitle}", blogConfigure.getClientSubtitle()).
                replace("${favicon}", blogConfigure.getFavicon()).
                replace("${repoFullName}", repoFullName).
                replace("${body}", bodyBuilder.toString());
        return ret;
    }

}
