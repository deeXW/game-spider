package com.zdf.webspider.processor.mj;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zdf.bean.mj.MjDirectory;
import com.zdf.bean.mj.MjDirectoryLinks;
import com.zdf.service.mj.MjDirectoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.alibaba.fastjson.JSONObject;
import com.zdf.bean.mj.MeijuUpdateNotice;
import com.zdf.service.mj.MeijuUpdateNoticeService;
import com.zdf.service.mj.MjDirectoryLinksService;
import com.zdf.util.MjWebsiteConstant;
import com.zdf.util.JuTypeUtil.JuType;

/**
 * 美剧天天
 * http://www.meijutt.com/
 * @author dee
 */
@Component
public class MeijuttProcessor implements PageProcessor {

    private final Logger logger = Logger.getLogger(MeijuttProcessor.class);

	@Autowired
	private MeijuUpdateNoticeService meijuUpdateNoticeService;

    @Autowired
    private MjDirectoryLinksService mMjDirectoryLinksService;

    @Autowired
    private MjDirectoryService mjDirectoryService;

	private final Site site = Site.me().setRetryTimes(3).setSleepTime(100)
    		.setDomain("www.meijutt.com")
			.addHeader("Upgrade-Insecure-Requests", "1")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");

    @Override
    public void process(Page page) {
    	Long directoryId = (Long) page.getRequest().getExtra("directoryId");
    	//0表示第一个模块，1表示第二个模块
        String rowNum = (String) page.getRequest().getExtra("rowNum");
        String pStart = (String) page.getRequest().getExtra("pStart");
        String pEnd = (String) page.getRequest().getExtra("pEnd");

        //获取已经有的下载连接
        Integer curLinksCount = mMjDirectoryLinksService.getLinksCount(directoryId);

        //获取当前更新的集数
        MjDirectory mjDirectory = mjDirectoryService.getById(directoryId);
        Integer updateCount = Integer.valueOf(mjDirectory.getUpdateCount());


        String name = page.getHtml().xpath("//*[@class=info-title]/h1/text()").toString();
        //根据rowNum 获取对应tab中的 连接和名称
        List<String> names = page.getHtml()
            .css("div.down_list ul li input[name=down_url_list_"+rowNum+"] + p").xpath("//a/text()").all();
        List<String> links = page.getHtml()
            .css("div.down_list ul li input[name=down_url_list_"+rowNum+"] + p").xpath("//a/@href").all();

        if(null == links || links.size() < 1){
        	System.out.println(name + " " + directoryId + " 获取数据为空  连接: " + page.getUrl());
        	page.setSkip(true);
        }else{
//            String a = "INSERT INTO mj_directory_links(sort,directory_id,download_name,download_link,download_name_720,download_link_720,is_distinguish,is_subtitle,pixel,create_time,create_by,change_time,change_by) VALUES(";
//            String b = "'','','0','1','11',NOW(),'0000',NOW(),'0000');";
            String isEnd = "";
            if(links.size() > curLinksCount){
                Set<Long> hashSet = new HashSet<Long>();
                String regEx = pStart+"(.*?)"+pEnd;
                Pattern pattern = Pattern.compile(regEx);

                System.out.println("-- " + name);
                for (int i = curLinksCount; i < links.size(); i++) {
                    if(names.get(i).toUpperCase().contains("END")){
                        isEnd = " -- 已剧终！！！";
                    }

                    Matcher m = pattern.matcher(names.get(i));
                    Long jishu = 0l;
                    if(m.find()) {
                        String result = m.group(1);
                        if(!"".equals(result)){
                            String[] array = result.split("\\.");
                            String array0 = array[0];
                            String[] arrayEs = array0.split("E");
                            for (String string: arrayEs) {
                                jishu = Long.valueOf(string);
                                hashSet.add(jishu);
                            }

                        }
                    }

                    MjDirectoryLinks mjDirectoryLinks = new MjDirectoryLinks();
                    mjDirectoryLinks.setSort(i+1);
                    mjDirectoryLinks.setDirectoryId(directoryId);
                    mjDirectoryLinks.setDownloadName(names.get(i));
                    mjDirectoryLinks.setDownloadLink(links.get(i));

                    mjDirectoryLinks.setDownloadName720("");
                    mjDirectoryLinks.setDownloadLink720("");
                    mjDirectoryLinks.setIsDistinguish("0");
                    mjDirectoryLinks.setIsSubtitle("1");
                    mjDirectoryLinks.setPixel("11");
                    boolean flag = mMjDirectoryLinksService.insert(mjDirectoryLinks);

                    logger.info(mjDirectory.getTitle() + "--> 第" + jishu + "集 directoryId：" + directoryId +" 新增状态:"+flag);

                    //sql 打印
//                    System.out.println(names.get(i) + "--> " + links.get(i));
//                    System.out.println(a + (i+1) +",'" + directoryId + "','"+ names.get(i) + "','"+links.get(i)+ "',"+b);
                }

                Long number = Collections.max(hashSet);
                System.out.println("hashSet容器中的最大值是：" + number + " updateCount:"+updateCount);
                if(number > updateCount){
                    MjDirectory mjDirectoryUpdate = new MjDirectory();
                    mjDirectoryUpdate.setId(Long.valueOf(directoryId));
                    mjDirectoryUpdate.setUpdateCount(String.valueOf(number));

                    boolean flag2 = mjDirectoryService.update(mjDirectoryUpdate);

                    logger.info(mjDirectory.getTitle() + "-->  更新主表updateCount 第" + number + "集 修改状态：" + flag2 + isEnd);
                }
            }else{
                System.out.println(name + "-- 无更新");
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
    
    @SuppressWarnings({ "unchecked", "static-access" })
    public void handleLinks(MeijuttProcessor meijuttProcessor){
    	List<MeijuUpdateNotice> list = meijuUpdateNoticeService.getMeijuUpdateNoticeListByType(MjWebsiteConstant.MEIJUTT,JuType.MEIJU.getCode());
    	if(null != list && list.size() > 0){
    		JSONObject json = new JSONObject();
    		Spider spider = Spider.create(meijuttProcessor);
    		Request request = null;
            int index = 1;
    		for (MeijuUpdateNotice meijuUpdateNotice : list) {
    			if("".equals(meijuUpdateNotice.getLink()) || null == meijuUpdateNotice.getLink()){
    				continue;
    			}
    			Map<String,String> mapExt = json.parseObject(meijuUpdateNotice.getExtContent(), Map.class);
    			String rowNum = (String)mapExt.get("rowNum");
    			request = new Request(meijuUpdateNotice.getLink());
    			request.putExtra("id", meijuUpdateNotice.getId());
    			request.putExtra("nowUpdateCount", meijuUpdateNotice.getNowUpdateCount());
                request.putExtra("directoryName", meijuUpdateNotice.getDirectoryName());
                request.putExtra("directoryId", meijuUpdateNotice.getDirectoryId());
                request.putExtra("rowNum", rowNum);
                request.putExtra("pStart", (String)mapExt.get("pStart"));
                request.putExtra("pEnd", (String)mapExt.get("pEnd"));
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
    
}
