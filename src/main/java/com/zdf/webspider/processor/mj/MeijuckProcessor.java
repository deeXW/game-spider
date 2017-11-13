//package com.zdf.webspider.processor.mj;
//
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.zdf.bean.mj.MeijuUpdateNotice;
//import com.zdf.mail.JavaMail;
//import com.zdf.service.mj.MeijuUpdateNoticeService;
//import com.zdf.service.mj.MjDirectoryVideoService;
//
//import us.codecraft.webmagic.Page;
//import us.codecraft.webmagic.Request;
//import us.codecraft.webmagic.Site;
//import us.codecraft.webmagic.Spider;
//import us.codecraft.webmagic.processor.PageProcessor;
//
///**
// * 美剧仓库
// * http://www.meijuck.com/
// * @author dee
// *
// */
//public class MeijuckProcessor implements PageProcessor {
//	private final Logger             logger = Logger.getLogger(MeijuckProcessor.class);
//
//    private final Site site = Site.me().setRetryTimes(3).setSleepTime(300);
//
//    private StringBuffer             sb     = null;
//
//    @Autowired
//	private MeijuUpdateNoticeService meijuUpdateNoticeService;
//
//    @Autowired
//    private MjDirectoryVideoService mjDirectoryVideoService;
//
//    @Autowired
//    private JavaMail                 javaMail;
//
//	@Override
//	public void process(Page page) {
//
//	}
//
//	@Override
//	public Site getSite() {
//		return site;
//	}
//
//	public void handleLinks(MeijuHuiProcessor meijuHuiProcessor){ }
//
//    public static void main(String[] args) { }
//}
