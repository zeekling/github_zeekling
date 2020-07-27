package com.zeekling.util;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.FeedException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author zeekling [lingzhaohui@zeekling.cn]
 * @version 1.0
 * @apiNote
 * @since 2020-07-26
 */
public class FeedXmlUtilTest {

    @Test
    public void parseXml(){
        URL url = null;
        try {
            url = new URL("https://www.zeekling.cn/rss.xml");
            List<SyndEntry> entries = FeedXmlUtil.parseXml(url);
            for (SyndEntry syndEntry: entries){
                System.out.println(syndEntry.getTitle() + "\n\t" + syndEntry.getDescription().getValue());
            }
        } catch (MalformedURLException | FeedException e) {
            e.printStackTrace();
        }
    }

}
