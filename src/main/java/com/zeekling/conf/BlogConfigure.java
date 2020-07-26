package com.zeekling.conf;

/**
 * @author zeekling [lingzhaohui@zeekling.cn]
 * @version 1.0
 * @apiNote 博客信息配置
 * @since 2020-07-26
 */
public final class BlogConfigure {

    @Value(name = "blog.title")
    private  String clientTitle;

    @Value(name = "blog.subTitle")
    private  String clientSubtitle;

    @Value(name = "blog.favicon")
    private  String favicon;

    @Value(name = "github.pat")
    private String pat;

    @Value(name = "github.repoName")
    private String repoName;

    @Value(name = "blog.home")
    private String home;

    @Value(name = "blog.rss")
    private String rss;

    public String getClientTitle() {
        return clientTitle;
    }

    public void setClientTitle(String clientTitle) {
        this.clientTitle = clientTitle;
    }

    public String getClientSubtitle() {
        return clientSubtitle;
    }

    public void setClientSubtitle(String clientSubtitle) {
        this.clientSubtitle = clientSubtitle;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public String getPat() {
        return pat;
    }

    public void setPat(String pat) {
        this.pat = pat;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getRss() {
        return rss;
    }

    public void setRss(String rss) {
        this.rss = rss;
    }
}
