package com.zdf.webspider.processor.mj;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.zdf.bean.mj.MeijuUpdateNotice;
import com.zdf.mail.JavaMail;
import com.zdf.service.mj.MeijuUpdateNoticeService;
import com.zdf.util.JuTypeUtil.JuType;
import com.zdf.util.MjWebsiteConstant;

/**
 * www.cn163.net
 * @author dee
 *
 */
@Component
public class Cn163Processor implements PageProcessor {
    private final Logger             logger = Logger.getLogger(Cn163Processor.class);

    private StringBuffer             sb     = null;

    private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
    		.setDomain("www.cn163.net")
			.addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");

    @Autowired
	private MeijuUpdateNoticeService meijuUpdateNoticeService;
    
    @Autowired
    private JavaMail                 javaMail;

    @Override
    public void process(Page page) {
        //可以获取到第一个class数据
        List<String> links = page.getHtml().css("div.entry").xpath("//a/@href").all();

        String directoryName = (String) page.getRequest().getExtra("directoryName");
        Long directoryId = (Long) page.getRequest().getExtra("directoryId");
        Integer curCount = links.size();
        String nowUpdateCount = (String)page.getRequest().getExtra("nowUpdateCount");
        if(!curCount.equals(Integer.valueOf(nowUpdateCount))){
            Long id = (Long) page.getRequest().getExtra("id");
            String s = "【" + directoryName + "】 已更新 (原链接数据："+nowUpdateCount+" 新链接数据："+curCount+")" +
            			"--> 目录Id:" + directoryId + " 三方链接:" + page.getUrl();
            //logger.warn(s);
            sb.append(s + "\n");
        	MeijuUpdateNotice meijuUpdateNotice = new MeijuUpdateNotice();
        	meijuUpdateNotice.setId(id);
        	meijuUpdateNotice.setNowUpdateCount(String.valueOf(curCount));
        	meijuUpdateNoticeService.updateCount(meijuUpdateNotice);
        }else{
        	System.out.println("无更新 --> 剧名："+directoryName+" 目录Id: " + directoryId + "当前记录:"+curCount+ "已有记录:"+nowUpdateCount);
        }
        if ((boolean) page.getRequest().getExtra("endFlag") && sb.length() > 0) {
//            logger.warn(sb.toString());
        	System.out.println("-- 更新列表");
            System.out.println(sb.toString());
            javaMail.doSendHtmlEmail("美剧更新提醒", sb.toString().replace("\n", "<br>"),
                "woikmj@163.com");
            //657159029@qq.com
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void handleLinks(Cn163Processor cn163Processor){
    	List<MeijuUpdateNotice> list = meijuUpdateNoticeService.getMeijuUpdateNoticeListByType(MjWebsiteConstant.CN163,JuType.MEIJU.getCode());
    	if(null != list && list.size() > 0){
            sb = new StringBuffer();
    		Spider spider = Spider.create(cn163Processor);
    		Request request = null;
            int index = 1;
    		for (MeijuUpdateNotice meijuUpdateNotice : list) {
    			request = new Request(meijuUpdateNotice.getLink());
    			request.putExtra("id", meijuUpdateNotice.getId());
    			request.putExtra("nowUpdateCount", meijuUpdateNotice.getNowUpdateCount());
                request.putExtra("directoryName", meijuUpdateNotice.getDirectoryName());
                request.putExtra("directoryId", meijuUpdateNotice.getDirectoryId());
                request.putExtra("endFlag", false);
                if (index == list.size()) {
                    request.putExtra("endFlag", true);
                }
    			spider.addRequest(request);
                index++;
			}
    		spider.thread(1).run();
    	}
    }
    
    public static void main(String[] args) {
    	Request request = new Request("http://cn163.net/archives/17663/");
    	request.putExtra("1", "nihao");
    	
    	Cn163Processor cn163Processor = new Cn163Processor();
    	//http://cn163.net/archives/17663/
        Spider spider = Spider.create(cn163Processor);
        spider.thread(1).addRequest(request).run();
    }
}
