package com.zeekling.util;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * @author zeekling [lingzhaohui@zeekling.cn]
 * @version 1.0
 * @apiNote
 * @since 2020-07-26
 */
public final class FeedXmlUtil {

    private FeedXmlUtil(){}

    public static List<SyndEntry> parseXml(String urlStr)
            throws IllegalArgumentException, FeedException, MalformedURLException {
        URL url = new URL(urlStr);
        return parseXml(url);
    }

    /**
     * 根据链接地址得到数据
     */
    public static List<SyndEntry> parseXml(URL url)
            throws IllegalArgumentException, FeedException {
        List<SyndEntry> entries = null;
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = null;
            URLConnection conn;
            conn = url.openConnection();
            String content_encoding = conn.getHeaderField("Content-Encoding");
            if (content_encoding != null && content_encoding.contains("gzip")) {
                System.out.println("conent encoding is gzip");
                GZIPInputStream gzin = new GZIPInputStream(conn.getInputStream());
                feed = input.build(new XmlReader(gzin));
            } else {
                feed = input.build(new XmlReader(conn.getInputStream()));
            }

            entries = feed.getEntries();// 得到所有的标题<title></title>
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;

    }
}
